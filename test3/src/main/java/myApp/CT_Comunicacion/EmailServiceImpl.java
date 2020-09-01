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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import myApp.CT_Accesorios.ErroresCodes;
import myApp.CT_Accesorios.MyMtsReposException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

/**
 * The Class EmailServiceImpl.
 */
@Service("EmailService")
public class EmailServiceImpl implements EmailService {

	/** The configuration. */
	@Autowired
	private Configuration configuration;

	/** The mail sender. */
	@Autowired
	JavaMailSender mailSender;

	/** The logger. */
	private Logger logger = LogManager.getLogger();

	/**
	 * Send email with html template found in classpath resource.
	 *
	 * @param emailDto the email dto
	 * @return EmailDto
	 * @throws MyMtsReposException
	 */

	@Async
	public void sendEmail(final EmailDto emailDto) throws MyMtsReposException {

		logger.info("##### Started sending welcome email ####");

		MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		try {

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			/**
			 * Forma 4. Con configuración de FreeMarkerConfigurationFactoryBean, en
			 * Emailconfig.java
			 */
			Template template = configuration.getTemplate(
					"email/" + emailDto.getTemplateCategoria() + "/" + emailDto.getTemplateName() + ".ftlh");

			/**
			 * Obtenemos los parámetros de emailDto, y los reemplazamos dentro del template,
			 * y el resultado se obtiene en templateContent. Es necesario, antes de ejecutar
			 * esta sentencia, comprobar que el objeto emailDto, cuenta con todos los
			 * parámetros que requiere el archivo .ftlh.
			 */
			String templateContent = FreeMarkerTemplateUtils.processTemplateIntoString(template,
					emailDto.getParameterMap());

			helper.setTo(emailDto.getTo());

			if (emailDto.getCc() != null && emailDto.getCc().length != 0) {
				// getCc es un arreglo de strings, String[].
				helper.setCc(emailDto.getCc());
			}

			if (emailDto.getBcc() != null && emailDto.getBcc().length != 0) {
				// getBcc es un arreglo de strings, String[].
				helper.setBcc(emailDto.getBcc());
			}

			// Subject
			helper.setSubject(emailDto.getSubject());
			// Cuerpo del email.
			helper.setText(templateContent, true);

			// Cargamos las imágenes. A partir de static/.
			// Debe estar previamente insertado el
			// helper.setText(templateContent, true).
			if (emailDto.isHasImages()) {
				// Vaciamos de un Map a otro Map.
				Map<String, List<String>> imagesMap = emailDto.getImageMap();
				// Iteramos las variables key,value del Map.
				for (Map.Entry<String, List<String>> entry : imagesMap.entrySet()) {
					/**
					 * El ClassPathResource tiene tres argumentos: 1) static, 2) directorios dentro
					 * de static Value(0) y 3) nombre de la imagen con extensión. Value(1) Por
					 * ejemplo, se debe llegar a esto: helper.addInline("cidImage01", new
					 * ClassPathResource("static/general/logoarinfotica3.png"));
					 */
					ClassPathResource image = new ClassPathResource(
							"static/" + entry.getValue().get(0).toString() + "/" + entry.getValue().get(1).toString());
					helper.addInline(entry.getKey().toString(), image);
				}
			}

			// Cargamos los adjuntos a partir de static/.
			if (emailDto.isHasAttachment()) {

				// Vaciamos de un Map a otro Map.
				Map<String, Object> attachmentsMap = emailDto.getAttachmentsMap();
				// Iteramos las variables key,value del Map.
				for (Map.Entry<String, Object> entry : attachmentsMap.entrySet()) {
					/**
					 * El ClassPathResource tiene tres argumentos: 1) static y 2) directorios dentro
					 * de static (key) y 3) nombre del adjunto con extensión. Por ejemplo, se debe
					 * llegar a esto: helper.addAttachment("attachment.pdf", new
					 * ClassPathResource("static/attachment.pdf"));
					 */
					helper.addAttachment(entry.getValue().toString(), new ClassPathResource(
							"static/" + entry.getKey().toString() + "/" + entry.getValue().toString()));
				}
			}

//			// Adicionar después de setText. De lo contrario no sale la imagen.
//			ClassPathResource image = new ClassPathResource("static/logoarinfotica3.png");
//			helper.addInline("image01", image);
//			ClassPathResource pdf = new ClassPathResource("static/attachment.pdf");
//			helper.addAttachment("attachment.pdf", pdf);

			mailSender.send(mimeMessage);
			logger.info("##### Email sent ####");

			return;

		} catch (Exception e) {
			throw new MyMtsReposException("No se pudo enviar el email.... ", e.getCause(),
					ErroresCodes.FOLSERVICEIMPL_101);
		}

	}

}
