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

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import myApp.C3_Modelos.Account;
import myApp.C3_Repositorios.AccountRepository;
import myApp.CT_Accesorios.ErroresCodes;
import myApp.CT_Accesorios.MyMtsReposException;

/**
 * The Class AccountServiceImpl.
 */
@Service
public class AccountServiceImpl implements AccountService {

	/** The cat test repository. */
	@Autowired
	AccountRepository AccountRepository;

	/**
	 * Gets the one.
	 *
	 * @param id the id
	 * @return the one
	 * @throws MyMtsReposException the my mts repos exception
	 */
	/** Comentar si parentId = null */
//	@Autowired
//	TAccountRepository tAccountRepository;

	/**
	 * Gets the one.
	 *
	 * @param id the id
	 * @return the one
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Account getOne(int id) throws MyMtsReposException {

		// Desde la base de datos
		return AccountRepository.findById(id).orElseThrow(() -> new MyMtsReposException(
				"Error en getOne. Identificador inexistente para AccountId: " + id, null, ErroresCodes.CTX1_CAT_SERVICE));
		// Si es fijo o constante.
//		return new Account(id, (long) 12345657, "nombreLegal " + id, "domicilioAdministrativo " + id, "email " + id,
//				"nombreComercial " + id, true, LocalDateTime.now());

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

		Page<Account> Accounts = AccountRepository.findPage(parentId, search, orderby, pageable);

		return Accounts;

	}

	/**
	 * Insert.
	 *
	 * @param entidadBd the entidad bd
	 * @param parentId  the parent id
	 * @return the cat test
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Account insert(Account entidadBd, Integer parentId) throws MyMtsReposException {

		/** Comentar si parentId = null ---------------------- */
//		TAccount tAccount = tAccountRepository.findById(parentId)
//				.orElseThrow(() -> new MyMtsReposException(
//						"Error en insert. Indentificador inexistente del parent para Account: " + parentId, null,
//						ErroresCodes.CTX2_BYS_SERVICE));
//		Account.setTAccount(tAccount);
		/** -------------------------------------------------- */

		boolean deDB = false;

//		if (deDB) {

		try {
			entidadBd = grabar(entidadBd);
		} catch (MyMtsReposException e) {
			throw e;
		}

		return entidadBd;
//		} else {
//			int id = 10000;
//			return new Account(id, (long) 12345657, "nombreLegal " + id, "domicilioAdministrativo " + id, "email " + id,
//					"nombreComercial " + id, true, LocalDateTime.now());
//		}
	}

	/**
	 * Update.
	 *
	 * @param entidadModificada   the entidad modificada
	 * @param entidadModificadaId the entidad modificada id
	 * @return the cat test
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public Account update(Account entidadModificada, int entidadModificadaId) throws MyMtsReposException {

		Account entidadBd = AccountRepository.findById(entidadModificadaId)
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
	 * @param AccountId the cat test id
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public void delete(int AccountId) throws MyMtsReposException {
		try {
			AccountRepository.deleteById(AccountId);
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
	 * @return the cat test
	 * @throws MyMtsReposException the my mts repos exception
	 */
	private Account grabar(Account entidadBd) throws MyMtsReposException {
		Account entidad = new Account();
		try {
			entidad = AccountRepository.save(entidadBd);
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("email_UNIQUE")) {
				throw new MyMtsReposException("Error en la integridad de datos. El email ya existe!", null,
						ErroresCodes.CTX1_CAT_SERVICE);
			} else if (e.getMessage().contains("nit_UNIQUE")) {
				throw new MyMtsReposException("Error en la integridad de datos. El NIT ya existe!", null,
						ErroresCodes.CTX1_CAT_SERVICE);
			} else if (e.getMessage().contains("nombre_legal_UNIQUE")) {
				throw new MyMtsReposException("Error en la integridad de datos. El nombre legal ya existe!", null,
						ErroresCodes.CTX1_CAT_SERVICE);
			} else if (e.getMessage().contains("nombre_comercial_UNIQUE")) {
				throw new MyMtsReposException("Error en la integridad de datos. El nombre comercial ya existe!", null,
						ErroresCodes.CTX1_CAT_SERVICE);
			} else {
				throw new MyMtsReposException(
						"Error en la integridad de datos. " + e.getLocalizedMessage() + " " + e.getMessage(), null,
						ErroresCodes.CTX1_CAT_SERVICE);
			}
		} catch (DataAccessException e2) {
			throw new MyMtsReposException(
					"Error de acceso a datos. " + e2.getLocalizedMessage() + " " + e2.getMessage(), null,
					ErroresCodes.CTX1_CAT_SERVICE);
		}
		return entidad;
	}

}
