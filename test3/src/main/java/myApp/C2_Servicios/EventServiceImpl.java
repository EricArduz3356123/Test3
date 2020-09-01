/*******************************************************************************
 * Copyright (c) Arinfotica.SRL - 2021
 * Autor: Alonzo Eric Arduz Campero
 * www.arinfotica.com
 * www.mi-conta.com
 * www.suri-app.com
 * Version 1.0
 * Software licenciado con Derechos de Autor.
********************************************************************************/

package myApp.C2_Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import myApp.C3_Modelos.Account;
import myApp.C3_Modelos.Event;
import myApp.C3_Repositorios.AccountRepository;
import myApp.C3_Repositorios.EventRepository;
import myApp.CT_Accesorios.ErroresCodes;
import myApp.CT_Accesorios.MyMtsReposException;

// TODO: Auto-generated Javadoc
/**
 * The Class EventServiceImpl.
 */
@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;

	/** The account repository. */
	@Autowired
	AccountRepository accountRepository;

	/**
	 * Gets the one.
	 *
	 * @param id the id
	 * @return the one
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Event getOne(int id) throws MyMtsReposException {

		// Desde la base de datos
		return eventRepository.findById(id).orElseThrow(() -> new MyMtsReposException(
				"Error en getOne. Identificador inexistente para EventId: " + id, null, ErroresCodes.CTX1_CAT_SERVICE));
	}

	/**
	 * Gets the page.
	 *
	 * @param parentId the parent id
	 * @param search the search
	 * @param orderby the orderby
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Event> getPage(Integer parentId, String search, String orderby, Pageable pageable) {

		Page<Event> Events = eventRepository.findPage(parentId, search, orderby, pageable);

		return Events;

	}

	/**
	 * Insert withdraw.
	 *
	 * @param entidadBd the entidad bd
	 * @return the event
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Event insertWithdraw(Event entidadBd) throws MyMtsReposException {

		// Validate from origin.
		Account account = null;
		final int origin = entidadBd.getOrigin();
		account = accountRepository.findById(origin)
				.orElseThrow(() -> new MyMtsReposException("Error on update. Origen Id does not exist for: " + origin,
						null, ErroresCodes.CTX1_CAT_SERVICE));
		if (account.getBalance() < entidadBd.getAmount())
			throw new MyMtsReposException("Invalid. Amount is bigger than balance for Origin Id: " + origin,
					ErroresCodes.FOLSERVICEIMPL_101);

		// Amount update.
		account.setBalance(account.getBalance() - entidadBd.getAmount());

		// Account saving.
		try {
			account = grabarAccount(account);
		} catch (MyMtsReposException e) {
			throw e;
		}

		// Event saving.
		try {
			entidadBd = grabar(entidadBd);
		} catch (MyMtsReposException e) {
			throw e;
		}

		return entidadBd;

	}

	/**
	 * Insert deposit.
	 *
	 * @param entidadBd the entidad bd
	 * @return the event
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Event insertDeposit(Event entidadBd) throws MyMtsReposException {

		// for destination.
		Account account = null;
		final int destination = entidadBd.getDestination();
		account = accountRepository.findById(destination).orElse(null);

		// if null then we insert the account
		if (account == null) {
			account = new Account(entidadBd.getDestination(), entidadBd.getAmount());
		} else {
			account.setBalance(account.getBalance() + entidadBd.getAmount());
		}

		// Account saving.
		try {
			account = grabarAccount(account);
		} catch (MyMtsReposException e) {
			throw e;
		}

		// Event saving.
		try {
			entidadBd = grabar(entidadBd);
		} catch (MyMtsReposException e) {
			throw e;
		}

		return entidadBd;

	}

	/**
	 * Insert transfer.
	 *
	 * @param entidadBd the entidad bd
	 * @return the event
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Event insertTransfer(Event entidadBd) throws MyMtsReposException {

		// From origin.
		Account accountOrigin = null;
		final int origin = entidadBd.getOrigin();
		accountOrigin = accountRepository.findById(origin)
				.orElseThrow(() -> new MyMtsReposException("Error on update. Origen Id does not exist for: " + origin,
						null, ErroresCodes.CTX1_CAT_SERVICE));

		// To destination
		Account accountDestination = null;
		final int destination = entidadBd.getDestination();
		accountDestination = accountRepository.findById(destination).orElse(null);

		// Amount validation.
		if (accountOrigin.getBalance() < entidadBd.getAmount())
			throw new MyMtsReposException("Invalid. Amount is bigger than balance for Origin Id: " + origin,
					ErroresCodes.FOLSERVICEIMPL_101);

		// Origin saving.
		accountOrigin.setBalance(accountOrigin.getBalance() - entidadBd.getAmount());
		try {
			accountOrigin = grabarAccount(accountOrigin);
		} catch (MyMtsReposException e) {
			throw e;
		}

		// Destination saving.
		if (accountDestination == null) {
			accountDestination = new Account(entidadBd.getDestination(), entidadBd.getAmount());
		} else {
			accountDestination.setBalance(accountDestination.getBalance() + entidadBd.getAmount());
		}
		try {
			accountDestination = grabarAccount(accountDestination);
		} catch (MyMtsReposException e) {
			throw e;
		}

		// Event saving.
		try {
			entidadBd = grabar(entidadBd);
		} catch (MyMtsReposException e) {
			throw e;
		}

		return entidadBd;

	}

	/**
	 * Update.
	 *
	 * @param entidadModificada the entidad modificada
	 * @param entidadModificadaId the entidad modificada id
	 * @return the event
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Event update(Event entidadModificada, int entidadModificadaId) throws MyMtsReposException {

		Event entidadBd = eventRepository.findById(entidadModificadaId)
				.orElseThrow(() -> new MyMtsReposException(
						"Error en update. Identificador inexistente de EventId: " + entidadModificadaId, null,
						ErroresCodes.CTX1_CAT_SERVICE));

		// entidadBd.setEventId(entidadModificada.getEventId());
		entidadBd.setType(entidadModificada.getType());
		entidadBd.setOrigin(entidadModificada.getOrigin());
		entidadBd.setDestination(entidadModificada.getDestination());
		entidadBd.setAmount(entidadModificada.getAmount());

		try {
			entidadBd = grabar(entidadBd);
		} catch (MyMtsReposException e) {
			throw e;
		}

		return entidadBd;
	}

	/**
	 * Delete.
	 *
	 * @param EventId the event id
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public void delete(int EventId) throws MyMtsReposException {
		try {
			eventRepository.deleteById(EventId);
		} catch (Exception e) {
			throw new MyMtsReposException("Error de Base de datos." + e.getLocalizedMessage(), null,
					ErroresCodes.CTX1_CAT_SERVICE);
		}
		return;
	}

	/**
	 * Delete all.
	 *
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public void deleteAll() throws MyMtsReposException {
		try {
			eventRepository.deleteAll();
		} catch (Exception e) {
			throw new MyMtsReposException("Error de Base de datos." + e.getLocalizedMessage(), null,
					ErroresCodes.CTX1_CAT_SERVICE);
		}
		return;
	}

	/**
	 * Grabar.
	 *
	 * @param entidadBd the entidad bd
	 * @return the event
	 * @throws MyMtsReposException the my mts repos exception
	 */
	private Event grabar(Event entidadBd) throws MyMtsReposException {
		Event entidad = new Event();
		try {
			entidad = eventRepository.save(entidadBd);
		} catch (DataIntegrityViolationException e) {
			throw new MyMtsReposException(
					"Error en la integridad de datos. " + e.getLocalizedMessage() + " " + e.getMessage(), null,
					ErroresCodes.CTX1_CAT_SERVICE);
		} catch (DataAccessException e2) {
			throw new MyMtsReposException(
					"Error de acceso a datos. " + e2.getLocalizedMessage() + " " + e2.getMessage(), null,
					ErroresCodes.CTX1_CAT_SERVICE);
		}
		return entidad;
	}

	/**
	 * Grabar account.
	 *
	 * @param entidadBd the entidad bd
	 * @return the account
	 * @throws MyMtsReposException the my mts repos exception
	 */
	private Account grabarAccount(Account entidadBd) throws MyMtsReposException {
		Account entidad = new Account();
		try {
			entidad = accountRepository.save(entidadBd);
		} catch (DataIntegrityViolationException e) {
			throw new MyMtsReposException(
					"Error en la integridad de datos. " + e.getLocalizedMessage() + " " + e.getMessage(), null,
					ErroresCodes.CTX1_CAT_SERVICE);
		} catch (DataAccessException e2) {
			throw new MyMtsReposException(
					"Error de acceso a datos. " + e2.getLocalizedMessage() + " " + e2.getMessage(), null,
					ErroresCodes.CTX1_CAT_SERVICE);
		}
		return entidad;
	}

}
