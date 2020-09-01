/*******************************************************************************
 * Copyright (c) Arinfotica.SRL - 2020
 * Autor: Alonzo Eric Arduz Campero
 * www.arinfotica.com
 * www.mi-conta.com
 * www.suri-app.com
 * Version 1.0
 * Software licenciado con Derechos de Autor.
********************************************************************************/
package myApp.C3_Repositorios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import myApp.C3_Modelos.Event;

/**
 * The Class CatEmpRepositoryImpl.
 */
@Repository
public class EventRepositoryImpl implements EventRepositoryCustom {

	/** The entity manager. */
	@Autowired
	EntityManager entityManager;

	/**
	 * Find page paginable.
	 *
	 * @param search   the search
	 * @param orderby  the orderby
	 * @param pageable the pageable
	 * @return the page
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<Event> findPage(Integer parentId, String search, String orderby, Pageable pageable) {

//		boolean desdeLaBase = true;
//
//		if (desdeLaBase) {

			String selectDataQuerySentence = " select cl ";
			String selectCountQuerySentence = " select count(cl.EventId) ";
			String fromDataQuerySentence = " from Event cl  ";
			String andWhereQuerySentence = obtenerCriteria(search);
			String whereQuerySentence;
			if (parentId != null)
				whereQuerySentence = "	where cl.XXX.xxxId = :parentId ";
			else
				whereQuerySentence = "	 ";

			Query query = entityManager
					.createQuery(selectDataQuerySentence + fromDataQuerySentence + andWhereQuerySentence
							+ whereQuerySentence)
					.setMaxResults(pageable.getPageSize())
					.setFirstResult(pageable.getPageSize() * pageable.getPageNumber())
			// .setParameter("parentId", parentId) //Descomentado si parentId != null
			;

			List<Event> resultado = query.getResultList();

			/** Calculo de Parámetros del Page de retorno */
			Query queryTotal = entityManager.createQuery(
					selectCountQuerySentence + fromDataQuerySentence + andWhereQuerySentence + whereQuerySentence);
			long countResult = (long) queryTotal.getSingleResult();
			// Parámetro: totalPages
			// int totalPages = (int) (countResult / pageable.getPageSize());
			// Parámetro: lastPageNumber
			// int lastPageNumber = (int) ((countResult / pageable.getPageSize()) + 1);
			/**
			 * -------------------------------------------------------------------------------
			 */

			Page<Event> resultadoPag = new PageImpl<Event>(resultado, pageable, countResult);

			return resultadoPag;

		//}
//		// Objetos fijos
//		else {
//
//			Event Event1 = new Event(
//					1, 
//					(long)1234567, 
//					"nombreLegal1", 
//					"domicilioAdministrativo1", 
//					"email1",
//					"nombreComercial1", 
//					true, 
//					LocalDateTime.now());
//			
//			Event Event2 = new Event(
//					2, 
//					(long)12234567, 
//					"nombreLegal2", 
//					"domicilioAdministrativo2", 
//					"email2",
//					"nombreComercial2", 
//					true, 
//					LocalDateTime.now());
//			
//			Event Event3 = new Event(
//					3, 
//					(long)12345673, 
//					"nombreLegal3", 
//					"domicilioAdministrativo3", 
//					"email3",
//					"nombreComercial1", 
//					true, 
//					LocalDateTime.now());
//			
//			Event Event4 = new Event(
//					4, 
//					(long)12345647, 
//					"nombreLegal4", 
//					"domicilioAdministrativo4", 
//					"email4",
//					"nombreComercial4", 
//					true, 
//					LocalDateTime.now());
//			
//			Event Event5 = new Event(
//					5, 
//					(long)12345657, 
//					"nombreLegal5", 
//					"domicilioAdministrativo5", 
//					"email5",
//					"nombreComercial5", 
//					true, 
//					LocalDateTime.now());
//			
//			List<Event> EventList = new ArrayList<Event>();
//			
//			EventList.add(Event1);
//			EventList.add(Event2);
//			EventList.add(Event3);
//			EventList.add(Event4);
//			EventList.add(Event5);
//			
//			final Page<Event> resultadoPag = new PageImpl<>(EventList);
//			
//			//Page<Event> resultadoPag = new PageImpl<Event>(resultado, pageable, countResult);
//
//			return resultadoPag;
//		}

	}

	/**
	 * Obtener criteria.
	 *
	 * @param search the search
	 * @return the string
	 */
	private String obtenerCriteria(String search) {
		String criteria = "";
		if (search == "")
			return "";
		else {
			String[] specs = search.split(";"); // specs[0] = "global,:,g"
												// specs[1] = "parContactoGlobalId,:,1"
												// specs[2] = "nombresRazonSocial,:,ma"
			String[] spec;
			String str = "";
			for (int i = 0; i <= specs.length - 1; i++) {
				str = specs[i];
				spec = str.split(",");

				if ("global".equals(spec[0])) {
					criteria = criteria + " AND ( cdi.EventId LIKE '%" + spec[2] + "%' ";
					criteria = criteria + " OR cdi.nombreLegal LIKE '%" + spec[2] + "%' ";
					criteria = criteria + " ) ";
				} else {
					String operador = "";
					if (":".equals(spec[1])) {
						operador = " LIKE ";
						spec[2] = "'%" + spec[2] + "%'";
					} else {
						operador = spec[1] + "=";
					}
					criteria = criteria + " AND " + "cdi." + spec[0] + operador + spec[2];
				}

			}
			return criteria;
		}

	}

}
