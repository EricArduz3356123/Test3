/**
 * ------------------------------------------------------
 * Copyright (c) Arinfotica.SRL - 2021
 * Autor: Alonzo Eric Arduz Campero
 * www.arinfotica.com
 * www.mi-conta.com
 * www.suri-app.com
 * Version 1.0
 * Software con Derechos de Autor.
 * ------------------------------------------------------
 */
package myApp.CT_Comunicacion;

import java.io.IOException;
import javax.mail.MessagingException;

import myApp.CT_Accesorios.MyMtsReposException;

/**
 * The Interface EmailService.
 */
public interface EmailService {
	
	/**
	 * Send email.
	 *
	 * @param emailDto the email dto
	 * @throws MessagingException the messaging exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws MyMtsReposException 
	 */
	void sendEmail(final EmailDto emailDto) throws MyMtsReposException;
	
}
