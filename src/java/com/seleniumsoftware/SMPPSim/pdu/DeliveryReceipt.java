/****************************************************************************
 * DeliveryReceipt.java
 *
 * Copyright (C) Selenium Software Ltd 2006
 *
 * This file is part of SMPPSim.
 *
 * SMPPSim is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * SMPPSim is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SMPPSim; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * @author martin@seleniumsoftware.com
 * http://www.woolleynet.com
 * http://www.seleniumsoftware.com
 * $Header: /var/cvsroot/SMPPSim2/distribution/2.6.0/SMPPSim/src/java/com/seleniumsoftware/SMPPSim/pdu/DeliveryReceipt.java,v 1.1 2009/03/11 08:41:45 martin Exp $
 ****************************************************************************/

package com.seleniumsoftware.SMPPSim.pdu;
import java.util.*;
import java.util.logging.Logger;

public class DeliveryReceipt extends DeliverSM {
	private static Logger logger = Logger.getLogger("com.seleniumsoftware.smppsim");
	// PDU fields
	private String message_id = "";
	private String sub = "";
	private String dlvrd = "";
	private String submit_date = "";
	private String done_date = "";
	private String err = "000";
	private String stat = "";
	private String text = "";

	public DeliveryReceipt(SubmitSM msg,int esm_class) {
		super(msg);
		setEsm_class(esm_class);
		setValidity_period("");
		setRegistered_delivery_flag(0);
	}

	public void setDeliveryReceiptMessage(byte state) {
		// id:IIIIIIIIII sub:SSS dlvrd:DDD submit date:YYMMDDhhmm done date:YYMMDDhhmm stat:DDDDDDD err:E Text: . . . . . . . . .
		Date d = new Date();
		String id = "id:" + message_id;
		String sb = " sub:" + sub;
		String dlv = " dlvrd:" + dlvrd;
		String sdate = " submit date:" + submit_date;
		String ddate = " done date:" + done_date;
		setStateText(state);
		String st = " stat:" + stat;
		String er = " err:" + err;
		String txt = " Text:" + text;
		setShort_message(new String(id + sb + dlv + sdate + ddate + st + er + txt).getBytes());
		setSm_length(getShort_message().length);
	}

	public void setStateText(byte state) {
		if (state == PduConstants.DELIVERED)
			stat = "DELIVRD";
		else if (state == PduConstants.EXPIRED)
			stat = "EXPIRED";
		else if (state == PduConstants.DELETED)
			stat = "DELETED";
		else if (state == PduConstants.UNDELIVERABLE)
			stat = "UNDELIV";
		else if (state == PduConstants.ACCEPTED)
			stat = "ACCEPTD";
		else if (state == PduConstants.UNKNOWN)
			stat = "UNKNOWN";
		else if (state == PduConstants.REJECTED)
			stat = "REJECTD";
		else if (state == PduConstants.ENROUTE)
			stat = "ENROUTE";
		else
			stat = "BADSTAT";
	}
	/**
	 * @return
	 */
	public String getDlvrd() {
		return dlvrd;
	}

	/**
	 * @return
	 */
	public String getDone_date() {
		return done_date;
	}

	/**
	 * @return
	 */
	public String getErr() {
		return err;
	}

	/**
	 * @return
	 */
	public String getMessage_id() {
		return message_id;
	}

	/**
	 * @return
	 */
	public String getStat() {
		return stat;
	}

	/**
	 * @return
	 */
	public String getSub() {
		return sub;
	}

	/**
	 * @return
	 */
	public String getSubmit_date() {
		return submit_date;
	}

	/**
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param string
	 */
	public void setDlvrd(String string) {
		dlvrd = string;
	}

	/**
	 * @param string
	 */
	public void setDone_date(String string) {
		logger.finest("Setting done_date="+string);
		done_date = string;
	}

	/**
	 * @param string
	 */
	public void setErr(String string) {
		err = string;
	}
	/**
	 * @param string
	 */
	public void setMessage_id(String string) {
		message_id = string;
	}

	/**
	 * @param string
	 */
	public void setStat(String string) {
		stat = string;
	}

	/**
	 * @param string
	 */
	public void setSub(String string) {
		sub = string;
	}

	/**
	 * @param string
	 */
	public void setSubmit_date(String string) {
		submit_date = string;
	}

	/**
	 * @param string
	 */
	public void setText(String string) {
		text = string;
	}

	/**
	 * *returns String representation of PDU
	 */
	public String toString() {
		return super.toString();
	}
}