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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

import myApp.C2_Servicios.EventService;
import myApp.C3_Modelos.Event;
import myApp.C3_Modelos.Event;
import myApp.CT_Accesorios.MyMtsReposException;
import myApp.CT_Comunicacion.EmailDto;
import myApp.CT_Comunicacion.EmailService;

/**
 * The Class EventController.
 */
@RestController
@RequestMapping(value = "/apitest1")
public class EventController {

	/** The email service. */
	@Autowired
	EmailService emailService;

	/** The cat test service. */
	@Autowired
	EventService EventService;

	/** The logger. */
	private Logger logger = LogManager.getLogger();

	/**
	 * Gets the cat tests.
	 *
	 * @param $search  the $search
	 * @param $orderby the $orderby
	 * @param $skip    the $skip
	 * @param $top     the $top
	 * @param token    the token
	 * @return the cat tests
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@GetMapping(value = "/events")
	public ResponseEntity<?> getEvents(@RequestParam(required = false) String $search,
			@RequestParam(required = false) String $orderby, @RequestParam final Integer $skip,
			@RequestParam final Integer $top, @RequestHeader(name = "Authorization") String token)
			throws MyMtsReposException {

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

		pageInfo = EventService.getPage(null, search, orderby, pageable);

		return new ResponseEntity<>(pageInfo, HttpStatus.OK);
	}

	/**
	 * Gets the cat test.
	 *
	 * @param EventId the cat test id
	 * @param token    the token
	 * @return the cat test
	 */
//	@Secured({ "ROLE_AMARILLO_CABM_MTS_OPE_OPERADOR", "ROLE_CAT_01" })
	@GetMapping(value = "/Event/{id}")
	public ResponseEntity<?> getEvent(@PathVariable("id") final Integer EventId,
			@RequestHeader(name = "Authorization") String token) {

		Map<String, Object> response = new HashMap<>();

		Event Event = null;

		try {
			Event = EventService.getOne(EventId);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error de base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Event>(Event, HttpStatus.OK);
	}

	/**
	 * Creates the cat test.
	 *
	 * @param Event the cat test
	 * @param token  the token
	 * @return the response entity
	 * @throws MyMtsReposException  the my mts repos exception
	 * @throws InterruptedException the interrupted exception
	 */
//	@Secured("ROLE_CAT_01")
	@PostMapping(value = "/Event")
//	public ResponseEntity<?> createEvent(@RequestBody Event Event,
//			@RequestHeader(name = "Authorization") String token) throws MyMtsReposException, InterruptedException {
		public ResponseEntity<?> createEvent(@RequestBody Event Event) throws MyMtsReposException, InterruptedException {

		/** Inicialización de la Variable de respuesta */
		Map<String, Object> response = new HashMap<>();

		Event EventNuevo = new Event();

		try {
			EventNuevo = EventService.insert(Event, null);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error al realizar Insert en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		/**
		 * ** PRODUCIR: Produce y publica evento. Si no logra producir por algún motivo,
		 * entonces envía un email al Administrador. Si por algún motivo no se puede
		 * enviar un Email, registra un log. En ningún caso detiene el funcionamiento
		 * del sitio.
		 */
//		if (EventNuevo.isReplicaInmediata()) {
//			/** ---- EMAIL ADMINISTRATIVO: NO ES REPLICA INMEDIATA ------ */
//			try {
//				// Preparamos el email.
//				EmailDto emailDto = new EmailDto("Se requiere réplica manual...", EventNuevo.toString(), "html",
//						"tReplicaInmediataFalse");
//				// Enviamos el email.
//				emailService.sendEmail(emailDto);
//			} catch (Exception e3) {
//				logger.info(" ##### 'Se requiere réplica manual...' Email could NOT be sent ####");
//			}
//			/** ----------------------------------------------------------- */
//		}
		/** ****************************************************************** */

		response.put("mensaje", "La test ha sido creado con exito. ");
		response.put("EventNuevo", EventNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/**
	 * Update cat test.
	 *
	 * @param Event   the cat test
	 * @param EventId the cat test id
	 * @param token    the token
	 * @return the response entity
	 * @throws MyMtsReposException the my mts repos exception
	 */
	/**
	 * Sólo un usuario de la testresa respectiva con el rol especial de ADMINISTRADOR
	 * puede modificar Event.
	 */
	//@Secured("ROLE_CAT_01")
	@PutMapping(value = "/Event/{id}")
	public ResponseEntity<?> updateEvent(@RequestBody Event Event, @PathVariable("id") final int EventId,
			@RequestHeader(name = "Authorization") String token) throws MyMtsReposException {

		Map<String, Object> response = new HashMap<>();

		try {
			// Verifica existencia de Id.
			EventService.getOne(EventId);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error al consultar EventId en la base de datos " + EventId);
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Event EventActualizado = new Event();

		try {
			// Actualiza en base de datos.
			EventActualizado = EventService.update(Event, EventId);
		} catch (MyMtsReposException e) {
			response.put("mensaje", "Error al realizar la actualización en la base de datos.");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		/**
		 * ** PRODUCIR: Produce y publica evento. Si no logra producir por algún motivo,
		 * entonces envía un email al Administrador. Si por algún motivo no se puede
		 * enviar un Email, registra un log. En ningún caso detiene el funcionamiento
		 * del sitio.
		 */
//		if (EventActualizado.isReplicaInmediata()) {
//			/** ---- EMAIL ADMINISTRATIVO: NO ES REPLICA INMEDIATA ------ */
//			try {
//				// Preparamos el email.
//				EmailDto emailDto = new EmailDto("Se requiere réplica manual...", EventActualizado.toString(), "html",
//						"tReplicaInmediataFalse");
//				// Enviamos el email.
//				emailService.sendEmail(emailDto);
//			} catch (Exception e3) {
//				logger.info(" ##### 'Se requiere réplica manual...' Email could NOT be sent ####");
//			}
//			/** ----------------------------------------------------------- */
//		}
		/** ****************************************************************** */

		// Se ha actualizado la testresa. Devolvemos la testresa actualizada.
		response.put("mensaje", "¡La testresa ha sido actualizada con éxito!");
		response.put("Eventresa:", EventActualizado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	/**
	 * Delete cat test.
	 *
	 * @param EventId the cat test id
	 * @param token    the token
	 * @return the response entity
	 * @throws MyMtsReposException
	 */
//	@Secured("ROLE_CAT_01")
	@DeleteMapping(value = "/Event/{id}")
	public ResponseEntity<?> deleteEvent(@PathVariable("id") final int EventId,
			@RequestHeader(name = "Authorization") String token) throws MyMtsReposException {
		Map<String, Object> response = new HashMap<>();

		try {
			EventService.delete(EventId);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el Eventresa en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Eventresa ha sido eliminada con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

}
