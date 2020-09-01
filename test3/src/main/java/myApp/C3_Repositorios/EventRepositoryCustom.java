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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import myApp.C3_Modelos.Event;

/**
 * The Interface CatEmpRepositoryCustom.
 */
@Repository
public interface EventRepositoryCustom {

	/**
	 * Find page paginable.
	 *
	 * @param search the search
	 * @param orderby the orderby
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<Event> findPage(Integer parentId, String search, String orderby,  Pageable pageable);

}
