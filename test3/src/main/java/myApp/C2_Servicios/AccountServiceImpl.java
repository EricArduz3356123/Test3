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
import myApp.C3_Repositorios.AccountRepository;
import myApp.C3_Repositorios.EventRepository;
import myApp.CT_Accesorios.ErroresCodes;
import myApp.CT_Accesorios.MyMtsReposException;

/**
 * The Class AccountServiceImpl.
 */
@Service
public class AccountServiceImpl implements AccountService {

	/** The account repository. */
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	EventRepository eventRepository;

	/**
	 * Gets the one.
	 *
	 * @param id the id
	 * @return the one
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Account getOne(int id) throws MyMtsReposException {

		return accountRepository.findById(id)
				.orElseThrow(() -> new MyMtsReposException(
						"Error en getOne. Identificador inexistente para AccountId: " + id, null,
						ErroresCodes.CTX1_CAT_SERVICE));

	}

	/**
	 * Gets the balance.
	 *
	 * @param id the id
	 * @return the balance
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public int getBalance(int id) throws MyMtsReposException {
		Account account = accountRepository.findById(id).orElseThrow(() -> new MyMtsReposException(
				"Error en getOne. Identificador inexistente para EventId: " + id, null, ErroresCodes.CTX1_CAT_SERVICE));
		return (account.getAmount());
	}

	/**
	 * Gets the page.
	 *
	 * @param parentId the parent id
	 * @param search   the search
	 * @param orderby  the orderby
	 * @param pageable the pageable
	 * @return the page
	 */
	public Page<Account> getPage(Integer parentId, String search, String orderby, Pageable pageable) {

		Page<Account> Accounts = accountRepository.findPage(parentId, search, orderby, pageable);

		return Accounts;

	}

	/**
	 * Insert.
	 *
	 * @param entidadBd the entidad bd
	 * @param parentId  the parent id
	 * @return the account
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Account insert(Account entidadBd, Integer parentId) throws MyMtsReposException {

		try {
			entidadBd = grabar(entidadBd);
		} catch (MyMtsReposException e) {
			throw e;
		}

		return entidadBd;
	}

	@Override
	public void reset() throws MyMtsReposException {
		try {
			accountRepository.deleteAll();
			eventRepository.deleteAll();
		} catch (Exception e) {
			throw new MyMtsReposException(
					"Error de Base de datos. No se pudo realizar reset." + e.getLocalizedMessage(), null,
					ErroresCodes.CTX1_CAT_SERVICE);
		}
		return;
	}

	/**
	 * Update.
	 *
	 * @param entidadModificada   the entidad modificada
	 * @param entidadModificadaId the entidad modificada id
	 * @return the account
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Account update(Account entidadModificada, int entidadModificadaId) throws MyMtsReposException {

		Account entidadBd = accountRepository.findById(entidadModificadaId)
				.orElseThrow(() -> new MyMtsReposException(
						"Error en update. Identificador inexistente de AccountId: " + entidadModificadaId, null,
						ErroresCodes.CTX1_CAT_SERVICE));

		entidadBd.setAccountId(entidadModificada.getAccountId());
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
	 * @param AccountId the account id
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public void delete(int AccountId) throws MyMtsReposException {
		try {
			accountRepository.deleteById(AccountId);
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
	 * @return the account
	 * @throws MyMtsReposException the my mts repos exception
	 */
	private Account grabar(Account entidadBd) throws MyMtsReposException {
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
