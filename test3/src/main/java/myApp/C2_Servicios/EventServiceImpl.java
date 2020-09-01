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

import myApp.C3_Modelos.Event;
import myApp.C3_Repositorios.EventRepository;
import myApp.CT_Accesorios.ErroresCodes;
import myApp.CT_Accesorios.MyMtsReposException;

/**
 * The Class EventServiceImpl.
 */
@Service
public class EventServiceImpl implements EventService {

	/** The cat test repository. */
	@Autowired
	EventRepository EventRepository;

	/**
	 * Gets the one.
	 *
	 * @param id the id
	 * @return the one
	 * @throws MyMtsReposException the my mts repos exception
	 */
	/** Comentar si parentId = null */
//	@Autowired
//	TEventRepository tEventRepository;

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
		return EventRepository.findById(id).orElseThrow(() -> new MyMtsReposException(
				"Error en getOne. Identificador inexistente para EventId: " + id, null, ErroresCodes.CTX1_CAT_SERVICE));
		// Si es fijo o constante.
//		return new Event(id, (long) 12345657, "nombreLegal " + id, "domicilioAdministrativo " + id, "email " + id,
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
	public Page<Event> getPage(Integer parentId, String search, String orderby, Pageable pageable) {

		Page<Event> Events = EventRepository.findPage(parentId, search, orderby, pageable);

		return Events;

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
	public Event insert(Event entidadBd, Integer parentId) throws MyMtsReposException {

		/** Comentar si parentId = null ---------------------- */
//		TEvent tEvent = tEventRepository.findById(parentId)
//				.orElseThrow(() -> new MyMtsReposException(
//						"Error en insert. Indentificador inexistente del parent para Event: " + parentId, null,
//						ErroresCodes.CTX2_BYS_SERVICE));
//		Event.setTEvent(tEvent);
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
//			return new Event(id, (long) 12345657, "nombreLegal " + id, "domicilioAdministrativo " + id, "email " + id,
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
	public Event update(Event entidadModificada, int entidadModificadaId) throws MyMtsReposException {

		Event entidadBd = EventRepository.findById(entidadModificadaId)
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
	 * @param EventId the cat test id
	 * @throws MyMtsReposException the my mts repos exception
	 */
	@Override
	public void delete(int EventId) throws MyMtsReposException {
		try {
			EventRepository.deleteById(EventId);
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
	private Event grabar(Event entidadBd) throws MyMtsReposException {
		Event entidad = new Event();
		try {
			entidad = EventRepository.save(entidadBd);
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
