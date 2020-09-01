/*******************************************************************************
 * Copyright (c) Arinfotica.SRL - 2020
 * Autor: Alonzo Eric Arduz Campero
 * www.arinfotica.com
 * www.mi-conta.com
 * www.suri-app.com
 * Version 1.0
 * Software licenciado con Derechos de Autor.
********************************************************************************/
package myApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The Class MercatiousApplication.
 */
@SpringBootApplication
public class MyApp extends SpringBootServletInitializer {
	
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyApp.class, args);
    }
	
	
	 /**
 	 * Configure.
 	 *
 	 * @param builder the builder
 	 * @return the spring application builder
 	 */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyApp.class);
    }


	
	
}
