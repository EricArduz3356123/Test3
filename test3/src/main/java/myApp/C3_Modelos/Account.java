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
@Table(name = "accounts", catalog = "test11")
public class Account {

	/** The cat test id. */
	// ************************************************************
	@Id
	@Column(name = "account_id", updatable = true, nullable = false)
	private int accountId;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	/** The nit. */
	@Column(name = "amount")
	private int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Instantiates a new cat test.
	 */
	public Account() {
	}

	public Account(int accountId, int amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", amount=" + amount + "]";
	}

}
