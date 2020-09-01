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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Event.
 */
@Entity
@Table(name = "events", catalog = "testdb")
public class Event {

	/** The event id. */
	// ************************************************************
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id", updatable = false, nullable = false)
	private int eventId;

	// *************************************************************

	/**
	 * Gets the event id.
	 *
	 * @return the event id
	 */
	public int getEventId() {
		return eventId;
	}

	/**
	 * Sets the event id.
	 *
	 * @param eventId the new event id
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	/** The type. */
	@Column(name = "type")
	private String type;

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/** The origin. */
	@Column(name = "origin")
	private int origin;

	/**
	 * Gets the origin.
	 *
	 * @return the origin
	 */
	public int getOrigin() {
		return origin;
	}

	/**
	 * Sets the origin.
	 *
	 * @param origin the new origin
	 */
	public void setOrigin(int origin) {
		this.origin = origin;
	}

	/** The destination. */
	@Column(name = "destination")
	private int destination;

	/**
	 * Gets the destination.
	 *
	 * @return the destination
	 */
	public int getDestination() {
		return destination;
	}

	/**
	 * Sets the destination.
	 *
	 * @param destination the new destination
	 */
	public void setDestination(int destination) {
		this.destination = destination;
	}

	/** The amount. */
	@Column(name = "amount")
	private int amount;

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Instantiates a new event.
	 */
	public Event() {
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", type=" + type + ", origin=" + origin + ", destination=" + destination
				+ ", amount=" + amount + "]";
	}

	/**
	 * Instantiates a new event.
	 *
	 * @param eventId the event id
	 * @param type the type
	 * @param origin the origin
	 * @param destination the destination
	 * @param amount the amount
	 */
	public Event(int eventId, String type, int origin, int destination, int amount) {
		super();
		this.eventId = eventId;
		this.type = type;
		this.origin = origin;
		this.destination = destination;
		this.amount = amount;
	}

}
