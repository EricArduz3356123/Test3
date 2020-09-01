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
import myApp.C3_Modelos.Account;
import myApp.CT_Accesorios.MyMtsReposException;
import myApp.CT_Comunicacion.EmailService;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountController.
 */
@RestController
@RequestMapping(value = "/api")
public class AccountController {

	/** The email service. */
	@Autowired
	EmailService emailService;

	/** The account service. */
	@Autowired
	AccountService accountService;

	/**
	 * Gets the accounts.
	 *
	 * @param $search the $search
	 * @param $orderby the $orderby
	 * @param $skip the $skip
	 * @param $top the $top
	 * @return the accounts
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@GetMapping(value = "/account")
	public ResponseEntity<?> getAccounts(@RequestParam(required = false) String $search,
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

		Page<Account> pageInfo;

		pageInfo = accountService.getPage(null, search, orderby, pageable);

		return new ResponseEntity<>(pageInfo, HttpStatus.OK);
	}

	/**
	 * Gets the account.
	 *
	 * @param accountId the account id
	 * @return the account
	 */
	@GetMapping(value = "/account_id/{id}")
	public ResponseEntity<?> getAccount(@PathVariable("id") final Integer accountId) {

		Map<String, Object> response = new HashMap<>();

		Account account = null;

		try {
			account = accountService.getOne(accountId);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error de base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	/**
	 * Gets the balance.
	 *
	 * @param accountId the account id
	 * @return the balance
	 */
	@GetMapping(value = "/balance")
	public ResponseEntity<?> getBalance(@RequestParam(required = true) final int account_id) {

		int balance;

		try {
			balance = accountService.getBalance(account_id);
		} catch (MyMtsReposException e) {
			String res = "404 0";
			return new ResponseEntity<String>(res, HttpStatus.NOT_FOUND );
		}

		String res = "200 " + balance;
		return new ResponseEntity<String>(res, HttpStatus.OK );
	}

	/**
	 * Creates the account.
	 *
	 * @param account the account
	 * @return the response entity
	 * @throws MyMtsReposException the my mts repos exception
	 * @throws InterruptedException the interrupted exception
	 */
	@PostMapping(value = "/account")
	public ResponseEntity<?> createAccount(@RequestBody Account account)
			throws MyMtsReposException, InterruptedException {

		Map<String, Object> response = new HashMap<>();

		Account accountNuevo = new Account();

		try {
			accountNuevo = accountService.insert(account, null);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error al realizar Insert en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La test ha sido creado con exito. ");
		response.put("accountNuevo", accountNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/**
	 * Reset.
	 *
	 * @return the response entity
	 * @throws MyMtsReposException the my mts repos exception
	 * @throws InterruptedException the interrupted exception
	 */
	@PostMapping(value = "/reset")
	public ResponseEntity<?> reset() throws MyMtsReposException, InterruptedException {

		Map<String, Object> response = new HashMap<>();

		try {
			accountService.reset();
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error al realizar Insert en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String res = "200 OK";
		return new ResponseEntity<String>(res, HttpStatus.OK );
	}

	/**
	 * Update account.
	 *
	 * @param account the account
	 * @param accountId the account id
	 * @param token the token
	 * @return the response entity
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@PutMapping(value = "/account/{id}")
	public ResponseEntity<?> updateAccount(@RequestBody Account account, @PathVariable("id") final int accountId,
			@RequestHeader(name = "Authorization") String token) throws MyMtsReposException {

		Map<String, Object> response = new HashMap<>();

		try {
			accountService.getOne(accountId);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error al consultar accountId en la base de datos " + accountId);
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Account accountActualizado = new Account();

		try {
			accountActualizado = accountService.update(account, accountId);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error al realizar la actualización en la base de datos.");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "¡Account updated!");
		response.put("accountresa:", accountActualizado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	/**
	 * Delete account.
	 *
	 * @param accountId the account id
	 * @param token the token
	 * @return the response entity
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@DeleteMapping(value = "/cattest/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable("id") final int accountId,
			@RequestHeader(name = "Authorization") String token) throws MyMtsReposException {
		Map<String, Object> response = new HashMap<>();

		try {
			accountService.delete(accountId);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el accountresa en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La accountresa ha sido eliminada con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

}
