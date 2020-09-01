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

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * The Class EmailTemplates.
 */
/** 1) Configura la dirección de los templates de Freemarker. */
/** 2) Configura el servidor de email, con base en emailserver.properties. */
/** classpath = src/main/resources  */
@Configuration
@PropertySources({ @PropertySource("classpath:/properties/emailserver.properties") })
public class EmailConfig {

	/** The env. */
	/**                                                                     **/
	/** Se utiliza para capturar las variables de emailserver.properties, y **/
	/** reemplazar los parámetros en mailSender(). **/
	/**                                                                     **/
	@Autowired
	private Environment env;

	/**
	 * Fmfbean. Permite obtener el template para ser utilizado en el servicio
	 * (EmailServiceImp.java)
	 * 
	 * @return the free marker configuration factory bean
	 */

	/**
	 * A continuación se muestran cuatro formas para obtener el path al folder del
	 * template. Las formas se deben implementar en el servicio (EmailServiceImpl).
	 * De la cuatro, la que la última (Forma 4) requiere el presente método. Si se
	 * implementa cualquiera de las tres primeras formas, ya no se necesita este
	 * método, y sólo se deben implementar las instrucciones en el servicio
	 * EmpServiceImp.
	 **/

	/** FORMA 1: Se debe implementar en EmailServiceImp. */
	// TemplateLoader templateLoader = new FileTemplateLoader(new
	// File("src/main/resources/templates"));
	// configuration.setTemplateLoader(templateLoader);
	// Template template = configuration.getTemplate("email/"+
	// emailDto.getTemplateCategoria() + "/" + emailDto.getTemplateName() +
	// ".ftlh");

	/** FORMA 2: Se debe implementar en EmailServiceImp. */
	// configuration.setClassForTemplateLoading(this.getClass(), "/templates");
	// Template template = configuration.getTemplate("email/"+
	// emailDto.getTemplateCategoria() + "/" + emailDto.getTemplateName() +
	// ".ftlh");

	/** FORMA 3: Se debe implementar en EmailServiceImp. */
	// configuration.setDirectoryForTemplateLoading(new File(
	// "/home/eric/ARINFOTICA/mi_conta_estandar/contexto_adm/src/main/resources/templates"));
	// Template template = configuration.getTemplate("email/"+
	// emailDto.getTemplateCategoria() + "/" + emailDto.getTemplateName() +
	// ".ftlh");

	/**
	 * FORMA 4. Utiliza fmfbean() y no requiere configuración en el servicio.
	 * Simplemente usa el template.
	 */
	// Template template = configuration.getTemplate("email/"+
	// emailDto.getTemplateCategoria() + "/" + emailDto.getTemplateName() +
	// ".ftlh");

	@Primary
	@Bean
	public FreeMarkerConfigurationFactoryBean fmfbean() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("classpath:/templates");
		return bean;
	}

	/**
	 * ************************************* JavaMailSender.
	 *
	 * @return the java mail sender
	 */

	@Bean

	public JavaMailSender mailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(env.getProperty("spring.mail.host"));

		mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));

		mailSender.setProtocol(env.getProperty("spring.mail.protocol"));

		mailSender.setUsername(env.getProperty("spring.mail.username"));

		mailSender.setPassword(env.getProperty("spring.mail.password"));

		// otras propiedades.
		Properties mailProperties = new Properties();

		mailProperties.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth"));
		mailProperties.put("mail.smtp.starttls.enable",
				env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
		mailProperties.put("mail.smtp.ssl.trust", env.getProperty("spring.mail.properties.mail.smtp.ssl.trust"));

		mailSender.setJavaMailProperties(mailProperties);

		return mailSender;

	}

}
