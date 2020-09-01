/**
 * ------------------------------------------------------
 * Copyright (c) Arinfotica.SRL - 2020
 * Autor: Alonzo Eric Arduz Campero
 * www.arinfotica.com
 * www.mi-conta.com
 * www.suri-app.com
 * Version 1.0
 * Software con Derechos de Autor.
 * ------------------------------------------------------
 */

package myApp.C1_Controladores;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myApp.C2_Servicios.AccountService;
import myApp.C2_Servicios.EventService;
import myApp.C3_Modelos.Account;
import myApp.C3_Modelos.Event;
import myApp.CT_Accesorios.MyMtsReposException;

/**
 * The Class EventController.
 */
@RestController
@RequestMapping(value = "")
public class EventController {

	/** The event service. */
	@Autowired
	EventService eventService;

	/** The account service. */
	@Autowired
	AccountService accountService;

	/**
	 * Gets the events.
	 *
	 * @param $search  the $search
	 * @param $orderby the $orderby
	 * @param $skip    the $skip
	 * @param $top     the $top
	 * @return the events
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@GetMapping(value = "/events")
	public ResponseEntity<?> getEvents(@RequestParam(required = false) String $search,
			@RequestParam(required = false) String $orderby, @RequestParam final Integer $skip,
			@RequestParam final Integer $top) throws MyMtsReposException {

		/**
		 * Control de nulos en parámetros del request.
		 */
		String search = "";
		String orderby = "";

		// filter
		if ($search == null) {
			search = "";
		} else {
			search = $search;
		}

		// orderby
		if ($orderby == null) {
			orderby = "";
		} else {
			orderby = $orderby;
		}
		/**
		 * ----------------------------------------------------
		 */

		Pageable pageable = PageRequest.of($skip / $top, $top);

		Page<Event> pageInfo;

		pageInfo = eventService.getPage(null, search, orderby, pageable);

		return new ResponseEntity<>(pageInfo, HttpStatus.OK);
	}

	/**
	 * Gets the event.
	 *
	 * @param EventId the event id
	 * @return the event
	 */
	@GetMapping(value = "/event/{id}")
	public ResponseEntity<?> getEvent(@PathVariable("id") final Integer EventId) {

		Map<String, Object> response = new HashMap<>();

		Event Event = null;

		try {
			Event = eventService.getOne(EventId);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error de base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Event>(Event, HttpStatus.OK);
	}

	/**
	 * Creates the event.
	 *
	 * @param event the event
	 * @return the response entity
	 * @throws MyMtsReposException  the my mts repos exception
	 * @throws InterruptedException the interrupted exception
	 */
	@PostMapping(value = "/event")
	public ResponseEntity<?> createEvent(@RequestBody Event event) throws MyMtsReposException, InterruptedException {

		@SuppressWarnings("unused")
		Event eventNuevo = new Event();

		Account accountOrigin = null;
		Account accountDestination = null;

		if (event.getType().equals("withdraw")) {
			try {
				eventNuevo = eventService.insertWithdraw(event);
				accountOrigin = accountService.getOne(event.getOrigin());
			} catch (MyMtsReposException e) {
				// Withdraw from non-existing account
				String res = "0";
				return new ResponseEntity<String>(res, HttpStatus.NOT_FOUND);
			}
		}
		if (event.getType().equals("deposit")) {
			try {
				eventNuevo = eventService.insertDeposit(event);
				accountDestination = accountService.getOne(event.getDestination());
			} catch (Exception e) {
				// NA
				String res = "0";
				return new ResponseEntity<String>(res, HttpStatus.NOT_FOUND);
			}
		}
		if (event.getType().equals("transfer")) {
			try {
				eventNuevo = eventService.insertTransfer(event);
				accountOrigin = accountService.getOne(event.getOrigin());
				accountDestination = accountService.getOne(event.getDestination());
			} catch (MyMtsReposException e) {
				// Transfer from non-existing account
				String res = "0";
				return new ResponseEntity<String>(res, HttpStatus.NOT_FOUND);
			}
		}

		// Current balance state in origin and destination.
		switch (event.getType()) {
		case "withdraw": {
			// Withdraw from existing account
			String res = accountOrigin.fromOriginString();
			return new ResponseEntity<String>(res, HttpStatus.CREATED);
		}
		case "deposit": {
			// Deposit into existing account.
			String res = accountDestination.toDestinationString();
			return new ResponseEntity<String>(res, HttpStatus.CREATED);
		}
		default: // case "transfer":
		{
			// Transfer from existing account
			String res = textTransferFromExistingAccount(accountOrigin, accountDestination);
			return new ResponseEntity<String>(res, HttpStatus.CREATED);
		}
		}

	}

	String textTransferFromExistingAccount(Account accountOrigin, Account accountDestination) {
		// 201 {"origin": {"id":"100", "balance":0}, "destination": {"id":"300",
		// "balance":15}}
		String a = "{\"origin\": {\"id\":\"" + accountOrigin.getId() + "\", \"balance\":" + accountOrigin.getBalance()
				+ "}, ";
		String b = "\"destination\": {\"id\":\"" + accountDestination.getId() + "\", \"balance\":"
				+ accountDestination.getBalance() + "}}";
		return a + b;

	}

	/**
	 * Update event.
	 *
	 * @param Event   the event
	 * @param EventId the event id
	 * @param token   the token
	 * @return the response entity
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@PutMapping(value = "/event/{id}")
	public ResponseEntity<?> updateEvent(@RequestBody Event Event, @PathVariable("id") final int EventId,
			@RequestHeader(name = "Authorization") String token) throws MyMtsReposException {

		Map<String, Object> response = new HashMap<>();

		try {
			// Verifica existencia de Id.
			eventService.getOne(EventId);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error al consultar EventId en la base de datos " + EventId);
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Event EventActualizado = new Event();

		try {
			// Actualiza en base de datos.
			EventActualizado = eventService.update(Event, EventId);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error al realizar la actualización en la base de datos.");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "¡La testresa ha sido actualizada con éxito!");
		response.put("Eventresa:", EventActualizado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	/**
	 * Delete event.
	 *
	 * @param EventId the event id
	 * @param token   the token
	 * @return the response entity
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@DeleteMapping(value = "/event/{id}")
	public ResponseEntity<?> deleteEvent(@PathVariable("id") final int EventId,
			@RequestHeader(name = "Authorization") String token) throws MyMtsReposException {
		Map<String, Object> response = new HashMap<>();

		try {
			eventService.delete(EventId);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el Eventresa en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Eventresa ha sido eliminada con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

}
