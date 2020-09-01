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

import myApp.C3_Modelos.Event;
import myApp.CT_Accesorios.MyMtsReposException;

// TODO: Auto-generated Javadoc
/**
 * The Interface EventService.
 */
@Component
public interface EventService {

	/**
	 * Gets the one.
	 *
	 * @param testId the test id
	 * @return the one
	 * @throws MyMtsReposException the my mts repos exception
	 */
	Event getOne(int testId) throws MyMtsReposException;
	
	/**
	 * Gets the page.
	 *
	 * @param parentId the parent id
	 * @param search the search
	 * @param orderby the orderby
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<Event> getPage(Integer parentId, String search, String orderby, Pageable pageable);

	/**
	 * Insert withdraw.
	 *
	 * @param Event the event
	 * @return the event
	 * @throws MyMtsReposException the my mts repos exception
	 */
	Event insertWithdraw(Event Event) throws MyMtsReposException;

	/**
	 * Insert deposit.
	 *
	 * @param Event the event
	 * @return the event
	 * @throws MyMtsReposException the my mts repos exception
	 */
	Event insertDeposit(Event Event) throws MyMtsReposException;

	/**
	 * Insert transfer.
	 *
	 * @param Event the event
	 * @return the event
	 * @throws MyMtsReposException the my mts repos exception
	 */
	Event insertTransfer(Event Event) throws MyMtsReposException;

	/**
	 * Update.
	 *
	 * @param Event the event
	 * @param EventId the event id
	 * @return the event
	 * @throws MyMtsReposException the my mts repos exception
	 */
	Event update(Event Event, int EventId) throws MyMtsReposException;

	/**
	 * Delete.
	 *
	 * @param EventId the event id
	 * @throws MyMtsReposException the my mts repos exception
	 */
	void delete(int EventId) throws MyMtsReposException;

	/**
	 * Delete all.
	 *
	 * @throws MyMtsReposException the my mts repos exception
	 */
	void deleteAll() throws MyMtsReposException;

}
