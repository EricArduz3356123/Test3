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

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import myApp.C3_Modelos.Event;

/**
 * The Interface CatEmpRepository.
 */
@Repository
public interface EventRepository 
	extends PagingAndSortingRepository<Event, Integer>, EventRepositoryCustom {

	// Definir aquí interfaces con diferencia en el contrato de las interfaces estandar de JpaRepository.
		
}
