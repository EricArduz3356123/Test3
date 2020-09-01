/**
 * Copyright (c) Arinfotica.SRL - 2020
 * Autor: Alonzo Eric Arduz Campero
 * www.arinfotica.com
 * www.mi-conta.com
 * www.suri-app.com
 * Version 1.0
 * Software con Derechos de Autor.
 */
package myApp.C0_ResourceServer;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * The Class SpringSecurityConfig.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() 
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}