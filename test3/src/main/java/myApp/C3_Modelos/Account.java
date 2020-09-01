/*******************************************************************************
 * Copyright (c) Arinfotica.SRL - 2020
 * Autor: Alonzo Eric Arduz Campero
 * www.arinfotica.com
 * www.mi-conta.com
 * www.suri-app.com
 * Version 1.0
 * Software licenciado con Derechos de Autor.
********************************************************************************/
package myApp.C3_Modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class CatTest.
 */
@Entity
@Table(name = "accounts", catalog = "testdb")
public class Account {

	/** The cat test id. */
	// ************************************************************
	@Id
	@Column(name = "account_id", updatable = true, nullable = false)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/** The nit. */
	@Column(name = "amount")
	private int balance;

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	/**
	 * Instantiates a new cat test.
	 */
	public Account() {
	}

	public Account(int id, int balance) {
		super();
		this.id = id;
		this.balance = balance;
	}

	public String toDestinationString() {
		//201 {"destination": {"id":"100", "balance":10}}
		return "{\"destination\": {\"id\":\"" + id + "\", \"balance\":" + balance + "}}";
	}
	
	public String fromOriginString() {
		//201 {"origin": {"id":"100", "balance":15}}
		return "{\"origin\": {\"id\":\"" + id + "\", \"balance\":" + balance + "}}";
	}

}
