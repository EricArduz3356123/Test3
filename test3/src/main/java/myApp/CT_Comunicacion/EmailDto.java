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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;

import myApp.CT_Accesorios.ErroresCodes;
import myApp.CT_Accesorios.MyMtsReposException;

/**
 * The Class EmailDto.
 */

/** Se utiliza este objeto EmailDto para inyectar como argumento al EmailService, */
/** para encapsular los detalles de un mensaje de email y su contenido. */
/**
 * Atributos Estáticos: Son aquellos genéricos y propios del envío de emails,
 * como por ejemplo To, From, Subject, Message, Cc, etc.
 * 
 * Atributos Dinámicos: Son aquellos no genéricos y propios de cada tipo de
 * email. Se incorporan al modelo ingresando los mismos externamente. Estos
 * valores no son de naturaleza estáticos, sino cualquier valor que se le pueda
 * asignar al modelo, dependiendo del tipo de plantilla del email. Existirán
 * tipos de plantillas de email que requieran atributos dinámicos como por
 * ejemplo: "nombreProfesional", otro "nombreCliente", "cantidadPedido",
 * "numeroHabitacion", etc., los cuales deben ser poblados en la plantilla
 * dependiendo de su contexto. Como las plantillas se crean orientadas a
 * diversos tipos de contextos, también los atributos deben ser creados
 * conjuntamente con la creación del EmailDto.
 * 
 * Los atributos Estáticos de EmailDto, son atributos fijos. Y los atributos
 * dinámicos se almacenan en un diccionario Map<String, Object>, inicializado
 * desde afuera.
 * 
 * La creación de un EmailDto se divide en dos momentos: a) Primeramente se
 * instancia un EmailDto. b) Se inicializan los atributos estáticos. c) Se
 * inicializa el atributo de valores dinámicos Map<String, Object> desde afuera,
 * y luego se pasa el mismo al setter del atributo model, para luego usar el
 * mismo en el llenado del contexto del email, utilizando el getter.
 * 
 * Luego de la creación de EmailDto, se crean estos valores (key pairs) en una
 * variable Map<String, Object>
 * 
 */

@Component
public class EmailDto {

	Logger logger = LogManager.getLogger();

	/** ATRIBUTOS. */

	/** Diccionarios de atributos dinámicos. */

	/** The parameter map. */
	private Map<String, Object> parameterMap;

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	/** The image map. */
	private Map<String, List<String>> imageMap;

	public Map<String, List<String>> getImageMap() {
		return imageMap;
	}

	public void setImageMap(Map<String, List<String>> imageMap) {
		this.imageMap = imageMap;
	}

	/** The attachments map. */
	private Map<String, Object> attachmentsMap;

	public Map<String, Object> getAttachmentsMap() {
		return attachmentsMap;
	}

	public void setAttachmentsMap(Map<String, Object> attachmentsMap) {
		this.attachmentsMap = attachmentsMap;
	}

	/** Atributos estáticos. */

	/** The from. */
	@NotNull(message = "From email address cannot be null")
	@Email(message = "From email address is not valid")
	private String from;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	/** The to. */
	@NotEmpty(message = "To email address cannot be empty")
	@Email(message = "To email address is not valid")
	private String[] to;

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	/** The cc. */
	@Email(message = "Cc email address is not valid")
	private String[] cc;

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	/** The bcc. */
	@Email(message = "Bcc email address is not valid")
	private String[] bcc;

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	/** The subject. */
	@NotNull(message = "Email subject cannot be null")
	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/** The message. */
	@NotNull(message = "Email message cannot be null")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/** The is html. */
	private boolean isHtml;

	public boolean isHtml() {
		return isHtml;
	}

	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}

	/** The is template. */
	private boolean isTemplate;

	public boolean isTemplate() {
		return isTemplate;
	}

	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	/** The has images. */
	private boolean hasImages;

	public boolean isHasImages() {
		return hasImages;
	}

	public void setHasImages(boolean hasImages) {
		this.hasImages = hasImages;
	}

	/** The has attachment. */
	private boolean hasAttachment;

	public boolean isHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	/** The template categoría. */
	private String templateCategoria;

	public String getTemplateCategoria() {
		return templateCategoria;
	}

	public void setTemplateCategoria(String templateCategoria) {
		this.templateCategoria = templateCategoria;
	}

	/** The template name. */
	private String templateName;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * CONSTRUCTORES.
	 */

	public EmailDto() {

	}

	public EmailDto(String subject, String message, String templateCategoria, String templateName) {
		this();

		// Atributos generales.
		this.setFrom("arinfotica@gmail.com");

		// Preparación de Destinatarios.
		String[] toos = new String[1];
		toos[0] = "ericarduzcampero@gmail.com";
		this.setTo(toos);

		this.subject = subject;
		this.message = message;

		this.setTemplateCategoria(templateCategoria); // templateCategoria = "html"
		this.setTemplateName(templateName); // templateName = "tReplicaInmediataFalse"

		// Atributos dinámicos que se encuentran en el archivo .ftlh.
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("contexto", "cat");
		parameterMap.put("detalle", message);
		this.setParameterMap(parameterMap);

		// Atributos estáticos de Imágenes (in-line).
		this.hasImages = true;
		Map<String, List<String>> imageMap = new HashMap<String, List<String>>();
		imageMap.put("image01", List.of("general", "logoarinfotica3.png"));
		this.setImageMap(imageMap);

	}

	/**
	 * Instantiates a new email dto.
	 *
	 * @param from    the from
	 * @param toList  the to list
	 * @param subject the subject
	 * @param message the message
	 */
//	public EmailDto(String from, String toList, String subject, String message) {
//		this();
//		this.from = from;
//		this.subject = subject;
//		this.message = message;
//		this.to = splitByComma(toList);
//	}

	/**
	 * Instantiates a new email dto.
	 *
	 * @param from   the from
	 * @param toList the to list
	 * @param ccList the cc list
	 */
	public EmailDto(String from, String toList, String ccList) {
		this();
		this.from = from;
		this.to = splitByComma(toList);
		this.cc = splitByComma(ccList);
	}

	/**
	 * Instantiates a new email dto.
	 *
	 * @param from   the from
	 * @param toList the to list
	 */
//	public EmailDto(String from, String toList) {
//		this();
//		this.from = from;
//		this.to = splitByComma(toList);
//	}

	/**
	 * Instantiates a new email dto.
	 *
	 * @param from    the from
	 * @param toList  the to list
	 * @param ccList  the cc list
	 * @param subject the subject
	 * @param message the message
	 */
//	public EmailDto(String from, String toList, String ccList, String subject, String message) {
//		this();
//		this.from = from;
//		this.subject = subject;
//		this.message = message;
//		this.to = splitByComma(toList);
//		this.cc = splitByComma(ccList);
//	}

	/**
	 * Split by comma.
	 *
	 * @param toMultiple the to multiple
	 * @return the string[]
	 */
	private String[] splitByComma(String toMultiple) {
		String[] toSplit = toMultiple.split(",");
		return toSplit;
	}

	/**
	 * Instantiates a new email dto.
	 *
	 * @param tipo            the tipo
	 * @param tablaReplicarEn the tabla replicar en
	 * @param mensaje         the mensaje
	 * @param updated         the updated
	 */
	/**
	 * Constructor específico para updated. Utiliza imágenes o adjuntos estáticos.
	 * 
	 * @throws MyMtsReposException
	 */
	public EmailDto(String tipo, String tablaReplicarEn, String mensaje, String error, String objectToString)
			throws MyMtsReposException {

		logger.info("........EmailDto ... Email sending initiated");

		try {
			// Atributos generales.
			this.setFrom("arinfotica@gmail.com");

			// Preparación de Destinatarios.
			String[] toos = new String[1];
			toos[0] = "ericarduzcampero@gmail.com";
			this.setTo(toos);

			this.setSubject("Inconveniente en 'misReplicated'. No se pudo replicar localmente.");
			this.setMessage(mensaje);
			this.setTemplateCategoria("html");
			this.setTemplateName("tMisUpdated");

			// Atributos dinámicos.
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("contexto", "adm");
			parameterMap.put("objeto", objectToString);
			parameterMap.put("error", error);
			this.setParameterMap(parameterMap);

			// Atributos estáticos de Imágenes (in-line).
			this.hasImages = true;
			Map<String, List<String>> imageMap = new HashMap<String, List<String>>();
			imageMap.put("image01", List.of("general", "logoarinfotica3.png"));
			this.setImageMap(imageMap);

			// Atributos estáticos de Attachments.
			this.hasAttachment = true;
			Map<String, Object> attachmentMap = new HashMap<String, Object>();
			attachmentMap.put("general", "attachment.pdf");
			this.setAttachmentsMap(attachmentMap);

		} catch (Exception e) {
			throw new MyMtsReposException("No se pudo crear emailDto para el envio del email.... ", e.getCause(),
					ErroresCodes.FOLSERVICEIMPL_101);
		}

	}

	@Override
	public String toString() {
		return "EmailDto [from=" + from + ", to=" + Arrays.toString(to) + ", cc=" + Arrays.toString(cc) + ", bcc="
				+ Arrays.toString(bcc) + ", subject=" + subject + ", message=" + message + ", isHtml=" + isHtml
				+ ", isTemplate=" + isTemplate + ", hasImages=" + hasImages + ", hasAttachment=" + hasAttachment
				+ ", templateCategoria=" + templateCategoria + ", templateName=" + templateName + "]";
	}

}
