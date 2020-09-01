/*******************************************************************************
 * Copyright (c) Arinfotica.SRL - 2020
 * Autor: Alonzo Eric Arduz Campero
 * www.arinfotica.com
 * www.mi-conta.com
 * www.suri-app.com
 * Version 1.0
 * Software licenciado con Derechos de Autor.
********************************************************************************/
package myApp.C2_Servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import myApp.C3_Modelos.Account;
import myApp.CT_Accesorios.MyMtsReposException;

/**
 * The Interface accountService.
 */
@Component
public interface AccountService {

	/**
	 * Gets the one.
	 *
	 * @param testId the test id
	 * @return the one
	 * @throws MyMtsReposException 
	 */
	Account getOne(int testId) throws MyMtsReposException;

	/**
	 * Gets the page.
	 *
	 * @param parentId the parent id
	 * @param search the search
	 * @param orderby the orderby
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<Account> getPage(Integer parentId, String search, String orderby, Pageable pageable);

	/**
	 * Insert.
	 *
	 * @param account the cat test
	 * @param parentId the parent id
	 * @return the cat test
	 * @throws MyMtsReposException 
	 */
	Account insert(Account account, Integer parentId) throws MyMtsReposException;

	/**
	 * Update.
	 *
	 * @param account the cat test
	 * @param accountId the cat test id
	 * @return the cat test
	 * @throws MyMtsReposException 
	 */
	Account update(Account account, int accountId) throws MyMtsReposException;

	/**
	 * Delete.
	 *
	 * @param accountId the cat test id
	 * @throws MyMtsReposException 
	 */
	void delete(int accountId) throws MyMtsReposException;

}
