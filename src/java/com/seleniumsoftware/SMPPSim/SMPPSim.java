/****************************************************************************
 * SMPPSim.java
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
 * @author martin.woolley@seleniumsoftware.com
 * http://www.seleniumsoftware.com
 * http://www.seleniumsoftware.com
 * $Header: /var/cvsroot/SMPPSim2/distribution/2.6.0/SMPPSim/src/java/com/seleniumsoftware/SMPPSim/SMPPSim.java,v 1.1 2009/03/11 08:41:45 martin Exp $
 ****************************************************************************

This server simulates the basic behaviour of a Logical Aldiscom SMSC with respect
to SMPP based interaction between an application and the SMSC. Its intended purpose
is to allow basic testing of an SMPP application. It is not an SMSC. It is not a
full and comprehensive implementation. With this utility, sufficient testing, to be confident
that the main message types are implemented correctly can take place without the need for access
to a real SMSC. Once testing with this simulator is completed, final testing should take place
using such an SMSC.

Basic capabilities are:
- your application can bind as a receiver and transmitter to SMPPSim. It can then submit messages to the
  SMPPSim server for it to "deliver". SMPPSim will reply with appropriate SMPP messages, behaving as if it
  had delivered the message, assuming the message is properly formed. Obviously (just in case this still
  isn't clear!) it will not actually deliver such a message....because it is a simulator, not an actual
  SMSC.
- In addition, you can instruct SMPPSim to simulate the delivery of an inbound, mobile originated message
  to your application in a variety of ways. See the manual /docs/SMPPSim.html for details.

Features not supported currently and other constraints and "features":
- param_retrieve, query_last_messages & query_message_details are not supported. These message types
  have been removed at version 3.4 of the SMPP protocol as they were deemed vendor (ie Logica)
  specific.

================================================================================================================
Change Log
V2.2 04/01/2006
Rebranded as Selenium Software Ltd
Network callback feature introduced
Fixed issue with parameter substitution in the web console
Incorporated DATA_SM support, kindly contributed by Jean-Cedric Desrochers

V2.1.2 04/06/2005
Status reports now produced when registered_delivery=1 for all terminal message states.
V2.1.1
Fixed bug whereby decoded SME messages were not being written to the file. Also fixed display of version
number, which was incorrect.

V2.1.0
1. SUBMIT_SM now supports optional (Tag, Length, Value) parameters
2. PDU capturing introduced

V2.0.0
1. Client is now authenticated during BIND operations

One account can be specified in the properties file, as follows:
SYSTEM_ID=smppclient
PASSWORD=password

When handling BIND operations, the system_id and password supplied in such PDUs is compared with the account details specified in the properties file.

2. StandardConnectionHandler and StandardProtocolHandler classes are now user configurable

3. Removed error behaviours
The following properties, introduced in 1.21 have been removed. Error behaviour like this should now be produced by implementing custom StandardProtocolHandler classes.

PROBABILITY_SUBMIT_SM_ERROR parameter
PROBABILITY_NO_RESPONSE_TO_SUBMIT_SM parameter
PROBABILITY_DOUBLE_RESPONSE_TO_SUBMIT_SM parameter

4. An address_range of "*" is now accepted. Internally it is converted to the regular expression "[:alnum:]*"

5. Refactored protocol handling. Use of XML meta data to guide marshalling/demarshalling abandoned.

6. SUBMIT_MULTI support much improved.

7. Basic JUnit test suite included in distribution.

8. Classes renamed. "SMPP" prefix removed in all cases except one.

9. Proprietory logging system replaced with Java 1.4 standard logging. All logging configuration is now handled through the logging.properties config file.

10. Generic_Nak_Resp now correctly includes a cmd_status of ESME_RINVCMDID if the PDU is being returned in response to an unrecognised request PDU.

11. Source address and destination address are now validated on submit_sm and submit_multi. Both mandatory.

12. OutboundQueue and InboundQueue introduced to support message state lifecycle simulation. This introduced 
the following properties:

	# QUEUES
	# Maximum size paramaters are expressed as max number of objects the queue can hold
	OUTBOUND_QUEUE_MAX_SIZE=1000
	INBOUND_QUEUE_MAX_SIZE=1000

At the same time, LOOPBACK_DELAY was removed as this feature is now superceded by the new InboundQueue based
implementation.


V1.22 26/02/2004
: Fixed bug. Previously, a blank record in the delivery_messages.csv file caused the total number
  of records to be miscounted and subsequently ArrayIndexOutOfBoundsException to be thrown sometimes.
V1.21 23/01/2004
: Introduced PROBABILITY_SUBMIT_SM_ERROR parameter
: Introduced PROBABILITY_NO_RESPONSE_TO_SUBMIT_SM parameter
: Introduced PROBABILITY_DOUBLE_RESPONSE_TO_SUBMIT_SM parameter
V1.20 07/06/2003
: Bug fix. Delivery Receipts did not have the correct ESME class value and the message body did not have
  values set as required (e.g. message ID).
: BIND_TRANSCEIVER support added
: directory structure changed
V1.12 28/06/2002
: Refactored to allow transmitter/receiver sessions to use same port. SMPPReceiver and SMPPTransmitter classes rendered
  redundant and replaced with SMPPConnectionHandler and StandardProtocolHandler classes.
: Support for delivery receipts introduced
: Fixed readPacketInto bug which calculated packet length as negative sometimes
: System ID in BIND response messages had two null bytes terminating it instead of
  one. This caused some clients (notably the one from Logica) to fail when BINDing.
: smpp_submit_multi.xml file was wrong (cut and paste error).
: SMPPSim no longer null terminates OCTET-STRING fields.
: Fixed bug with http interface whereby message length was not being set correctly in the DELIVER_SM
  PDU.
V1.11 26/11/2001
: Moved props file to home directory and modified startsmppsim.* accordingly. Introduced windows variant and
  unix variants in standard distribution (props.win and props.unix).
: Implemented handling of regexp address_range when binding receivers
: Enhanced delivery_service. Takes input from a csv file of format: destination_address,source_address,message
  Loads file into Vector at start and randomly selects from it. regexp address_range processing ensures the
  destination_msisdn reults in delivery via appropriate Receiver object.
: Corrected XML type definitions re: strings. Now correctly differentiates between octet strings and
  c-octet strings and processes accordingy when converting messages to objects.
: Fixed synchronisation problem relating to use of XML parser objects

V1.10 11/11/2001
: All SMPP messages are now described by external XML files and transformed internally into corresponding
  java objects.
: Major refactoring completed.
: REPLACE_SM, CANCEL_SM now both supported. In summary, all SMPP3.3 message types are supported, except for param_retrieve,
  query_last_messages & query_message_details which have not been included in the SMPP version 3.4
  protocol. See http://smsforum.net/doc/public/FAQ/ProtocolFAQ.html.
: Configurable logging added
: Loopback feature introduced. When LOOPBACK property is set to true, all SUBMIT_SM messages received are
  reflected back to a bound receiver client as DELIVER_SM messages after a configurable
  delay.
: smppsim.html DELIVER_SM web page now supports all message attributes

V1.02 03/08/2001
: Fixed bug in makeInt reported by Zengping Tian.
: Added configurable, inbound (DELIVER_SM) message delivery thread for automatic, periodic delivery of
  messages to the SMS application.
: Fixed text heading in the ml-smppsim.html sms delivery form

V1.01 28/07/2001
: Added GNU GPL license details
: Fixed bug reported by Oran Kelly whereby the command length in getSubmitSMResponse was incorrectly set
  to 26 rather than 25 and as such was returning 2 null bytes at the end of these message types instead of
  the required single null byte.

V1.00 16/06/2001
: Fixed problem reading messages where read was returning without a complete packet in some cases. Added
        support for Enquire Link message type. Thanks to Al Cutter for this stuff.

*/

package com.seleniumsoftware.SMPPSim;
import com.seleniumsoftware.SMPPSim.pdu.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class SMPPSim {
	private static final String version = "2.6.0";

	// HTTP parameter names

	private static final String MESSAGEPARAM = "message";
	private static final String SOURCEMSISDNPARAM = "mo_msisdn";
	private static final String DESTMSISDNPARAM = "mt_msisdn";

	private static byte[] http200Response;
	private static final String http200Message =
		"HTTP/1.1 200\r\n\r\nDELIVER_SM invoked OK";
	private static byte[] http400Response;
	private static final String http400Message = "HTTP/1.1 400\r\n\r\n";

	// Misc constants and variables
	private static boolean loopback = false;
	private static int boundReceiverCount = 0;
	
	// Byte Stream Callback
	private static boolean callback = false;
	private static String callback_target_host;
	private static int callback_port;
	private static String callback_id;

	// Queue configuration
	private static int inbound_queue_capacity;
	private static int outbound_queue_capacity;
	private static int delayed_iqueue_period=60;
	private static int delayed_inbound_queue_max_attempts;
	private static int messageStateCheckFrequency;
	private static int maxTimeEnroute;
	private static int percentageThatTransition;
	private static int percentageDelivered;
	private static int percentageUndeliverable;
	private static int percentageAccepted;
	private static int percentageRejected;
	private static int discardFromQueueAfter;
	
	// Message ID allocation
	private static long start_at=0;
	private static String mid_prefix="";

	// Logging
	private static Logger logger = Logger.getLogger("com.seleniumsoftware.smppsim");

	// Other
	private static int smppPort;
	private static int HTTPPort;
	private static int HTTPThreads;
	private static String docroot;
	private static String authorisedFiles;
	private static String injectMoPage;
	private static int maxConnectionHandlers;
	private static String smscid;
	private static String [] systemids;
	private static String [] passwords;
	private static boolean outbind_enabled;
	private static String esme_ip_address;
	private static int esme_port;
	private static String esme_systemid;
	private static String esme_password;
	private static Socket receivesocket;
	private static String rcv_address;
	private static String deliverFile;
	private static int deliverMessagesPerMin = 0;
	private static String connectionHandlerClassName;
	private static String protocolHandlerClassName;
	private static String lifeCycleManagerClassName;

	private static int[] messageTypes =
		{
			PduConstants.BIND_RECEIVER,
			PduConstants.BIND_RECEIVER_RESP,
			PduConstants.BIND_TRANSMITTER,
			PduConstants.BIND_TRANSMITTER_RESP,
			PduConstants.BIND_TRANSCEIVER,
			PduConstants.BIND_TRANSCEIVER_RESP,
			PduConstants.OUTBIND,
			PduConstants.UNBIND,
			PduConstants.UNBIND_RESP,
			PduConstants.SUBMIT_SM,
			PduConstants.SUBMIT_SM_RESP,
			PduConstants.DELIVER_SM,
			PduConstants.DELIVER_SM_RESP,
			PduConstants.QUERY_SM,
			PduConstants.QUERY_SM_RESP,
			PduConstants.CANCEL_SM,
			PduConstants.CANCEL_SM_RESP,
			PduConstants.REPLACE_SM,
			PduConstants.REPLACE_SM_RESP,
			PduConstants.ENQUIRE_LINK,
			PduConstants.ENQUIRE_LINK_RESP,
			PduConstants.SUBMIT_MULTI,
			PduConstants.SUBMIT_MULTI_RESP,
			PduConstants.GENERIC_NAK };

	// PDU Capture
	private static boolean captureSmeBinary;
	private static String captureSmeBinaryToFile;
	private static boolean captureSmppsimBinary;
	private static String captureSmppsimBinaryToFile;
	private static boolean captureSmeDecoded;
	private static String captureSmeDecodedToFile;
	private static boolean captureSmppsimDecoded;
	private static String captureSmppsimDecodedToFile;
		
	public static void main(String args[]) throws Exception {
		System.out.println("SMPPSim is starting....");
		if ((args == null) || (args.length != 1)) {
			showUsage();
			return;
		}
		Properties props = new Properties();
		// load the given properties
		InputStream is = new FileInputStream(args[0]);
		props.load(is);
		initialise(props);
		showLegals();
		showConfiguration();
		SMPPSim SMPPSim = new SMPPSim();
		try {
			Smsc smsc = Smsc.getInstance();
			smsc.setInbound_queue_capacity(inbound_queue_capacity);
			smsc.setOutbound_queue_capacity(outbound_queue_capacity);
			smsc.start();
		} catch (Exception e) {
			logger.severe("Exception during start up " + e.getMessage());
			logger.severe("Exception is of type: " + e.getClass().getName());
			logger.log(Level.SEVERE, "Exception during start up", e);
		}
	}

	private static void showUsage() {
		System.out.println("Invalid or missing arguments:");
		System.out.println("Usage:");
		System.out.println(
			"java -Djava.util.logging.config.file=<logging.properties file> com/seleniumsoftware/SMPPSim/SMPPSim <properties file>");
		System.out.println("");
		System.out.println("Example:");
		System.out.println(
			"java -Djava.util.logging.config.file=conf\\logging.properties com/seleniumsoftware/SMPPSim/SMPPSim conf\\props.win");
		System.out.println("");
		System.out.println("Run terminated");
	}
	private static void initialise(Properties props) throws Exception {
		http200Response = http200Message.getBytes();
		http400Response = http400Message.getBytes();

		maxConnectionHandlers =
			Integer.parseInt(props.getProperty("SMPP_CONNECTION_HANDLERS"));
		smppPort = Integer.parseInt(props.getProperty("SMPP_PORT"));
		connectionHandlerClassName =
			props.getProperty("CONNECTION_HANDLER_CLASS");
		protocolHandlerClassName = props.getProperty("PROTOCOL_HANDLER_CLASS");
		lifeCycleManagerClassName = props.getProperty("LIFE_CYCLE_MANAGER");
		String systemid_list = props.getProperty("SYSTEM_IDS","");
		String password_list = props.getProperty("PASSWORDS","");
		systemids = systemid_list.split(",");
		passwords = password_list.split(",");
		if (systemids.length != passwords.length) {
			logger.severe("Number of SYSTEM_IDS elements is not the same as the number of PASSWORDS elements");
			throw new Exception("Number of SYSTEM_IDS elements is not the same as the number of PASSWORDS elements");
		}
		outbind_enabled = Boolean.valueOf(props.getProperty("OUTBIND_ENABLED")).booleanValue();
		if (outbind_enabled) {
			esme_ip_address = props.getProperty("OUTBIND_ESME_IP_ADDRESS","127.0.0.1");
			String ep = props.getProperty("OUTBIND_ESME_PORT");
			try {
				esme_port = Integer.parseInt(ep);
			} catch (NumberFormatException nfe) {
				logger.warning("ESME_PORT has invalid value "+ep+" - defaulting to 2776");
				esme_port = 2776;
			}
		}
		esme_systemid = props.getProperty("OUTBIND_ESME_SYSTEMID","smppclient1");
		esme_password = props.getProperty("OUTBIND_ESME_PASSWORD","password");
		HTTPPort =
			Integer.parseInt(props.getProperty("HTTP_PORT"));
		HTTPThreads =
			Integer.parseInt(props.getProperty("HTTP_THREADS"));
		docroot = props.getProperty("DOCROOT");
		authorisedFiles = props.getProperty("AUTHORISED_FILES");
		injectMoPage = props.getProperty("INJECT_MO_PAGE");
		smscid = props.getProperty("SMSCID");
		deliverMessagesPerMin =
			Integer.parseInt(props.getProperty("DELIVERY_MESSAGES_PER_MINUTE"));
		if (deliverMessagesPerMin > 0) {
			deliverFile = props.getProperty("DELIVER_MESSAGES_FILE");
		} else {
			deliverFile = "N/A";
		}
		
		Smsc.setDecodePdus(Boolean.valueOf(props.getProperty("DECODE_PDUS_IN_LOG")).booleanValue());

		setLoopback(Boolean.valueOf(props.getProperty("LOOPBACK")).booleanValue());
		
		inbound_queue_capacity = getIntProperty(props,"INBOUND_QUEUE_MAX_SIZE",1000);
		outbound_queue_capacity = getIntProperty(props,"OUTBOUND_QUEUE_MAX_SIZE",1000);
		messageStateCheckFrequency = getIntProperty(props,"MESSAGE_STATE_CHECK_FREQUENCY",10000);
		maxTimeEnroute = getIntProperty(props,"MAX_TIME_ENROUTE",2000);
		percentageThatTransition = getIntProperty(props,"PERCENTAGE_THAT_TRANSITION",75);
		percentageDelivered = getIntProperty(props,"PERCENTAGE_DELIVERED",90);
		percentageUndeliverable = getIntProperty(props,"PERCENTAGE_UNDELIVERABLE",6);
		percentageAccepted = getIntProperty(props,"PERCENTAGE_ACCEPTED",2);
		percentageRejected = getIntProperty(props,"PERCENTAGE_REJECTED",2);
		discardFromQueueAfter = getIntProperty(props,"DISCARD_FROM_QUEUE_AFTER",60000);
		delayed_iqueue_period = 1000 * getIntProperty(props,"DELAYED_INBOUND_QUEUE_PROCESSING_PERIOD",60);
		delayed_inbound_queue_max_attempts = getIntProperty(props,"DELAYED_INBOUND_QUEUE_MAX_ATTEMPTS",10);

		setCaptureSmeBinary(Boolean.valueOf(props.getProperty("CAPTURE_SME_BINARY")).booleanValue());
		setCaptureSmeBinaryToFile(props.getProperty("CAPTURE_SME_BINARY_TO_FILE"));
		setCaptureSmppsimBinary(Boolean.valueOf(props.getProperty("CAPTURE_SMPPSIM_BINARY")).booleanValue());
		setCaptureSmppsimBinaryToFile(props.getProperty("CAPTURE_SMPPSIM_BINARY_TO_FILE"));
		setCaptureSmeDecoded(Boolean.valueOf(props.getProperty("CAPTURE_SME_DECODED")).booleanValue());
		setCaptureSmeDecodedToFile(props.getProperty("CAPTURE_SME_DECODED_TO_FILE"));
		setCaptureSmppsimDecoded(Boolean.valueOf(props.getProperty("CAPTURE_SMPPSIM_DECODED")).booleanValue());
		setCaptureSmppsimDecodedToFile(props.getProperty("CAPTURE_SMPPSIM_DECODED_TO_FILE"));

		// Byte stream callbacks
		callback = Boolean.valueOf(props.getProperty("CALLBACK")).booleanValue();
		if (callback) {
			callback_target_host = props.getProperty("CALLBACK_TARGET_HOST");
			callback_port = Integer.parseInt(props.getProperty("CALLBACK_PORT"));
			callback_id = props.getProperty("CALLBACK_ID");
		}
		
		// Message ID allocations
		
		String mid_start = props.getProperty("START_MESSAGE_ID_AT");
		if (mid_start == null || mid_start.equals("")) 
			mid_start="0";
		if (mid_start.equalsIgnoreCase("random")) {
			start_at = (long) (Math.random()* 10000000);
		} else
			start_at = Long.parseLong(mid_start);
		
		mid_prefix = props.getProperty("MESSAGE_ID_PREFIX");
		if (mid_prefix == null)
			mid_prefix = "";

		// Misc
		byte [] s = new byte[smscid.length()];
		s = smscid.getBytes();
		Smsc.setSMSC_SYSTEMID(s);
	}

	private static void showLegals() {
		logger.info(
			"==============================================================");
		logger.info("=  SMPPSim Copyright (C) 2006 Selenium Software Ltd");
		logger.info(
			"=  SMPPSim comes with ABSOLUTELY NO WARRANTY; for details");
		logger.info(
			"=  read the license.txt file that was included in the SMPPSim distribution");
		logger.info(
			"=  This is free software, and you are welcome to redistribute it under");
		logger.info(
			"=  certain conditions; Again, see license.txt for details or read the GNU");
		logger.info("=  GPL license at http://www.gnu.org/licenses/gpl.html");
		logger.info("=  ...... end of legal stuff ......");

	}

	private static void showConfiguration() {
		logger.info(
			"==============================================================");
		logger.info("=  ");
		logger.info("=  com.seleniumsoftware.SMPPSim " + version);
		logger.info("=  by Martin Woolley (martin@seleniumsoftware.com)");
		logger.info("=  http://www.seleniumsoftware.com");
		logger.info("=  Running with the following parameters:");
		logger.info("=  SMPP_PORT                               :" + smppPort);
		logger.info("=  SMPP_CONNECTION_HANDLERS                :" + maxConnectionHandlers);
		logger.info("=  CONNECTION_HANDLER_CLASS                :" + connectionHandlerClassName);
		logger.info("=  PROTOCOL_HANDLER_CLASS                  :" + protocolHandlerClassName);
		logger.info("=  LIFE_CYCLE_MANAGER                      :" + lifeCycleManagerClassName);
		logger.info("=  SMPP_CONNECTION_HANDLERS                :" + maxConnectionHandlers);
		logger.info("=  INBOUND_QUEUE_CAPACITY                  :" + inbound_queue_capacity);
		logger.info("=  OUTBOUND_QUEUE_CAPACITY                 :" + outbound_queue_capacity);
		logger.info("=  MESSAGE_STATE_CHECK_FREQUENCY           :" + messageStateCheckFrequency);
		logger.info("=  MAX_TIME_ENROUTE                        :" + maxTimeEnroute);
		logger.info("=  PERCENTAGE_THAT_TRANSITION              :" + percentageThatTransition);
		logger.info("=  PERCENTAGE_DELIVERED                    :" + percentageDelivered);
		logger.info("=  PERCENTAGE_UNDELIVERABLE                :" + percentageUndeliverable);
		logger.info("=  PERCENTAGE_ACCEPTED                     :" + percentageAccepted);
		logger.info("=  PERCENTAGE_REJECTED                     :" + percentageRejected);
		logger.info("=  DISCARD_FROM_QUEUE_AFTER                :" + discardFromQueueAfter);
		logger.info("=  OUTBIND_ENABLED                         :" + outbind_enabled);
		logger.info("=  OUTBIND_ESME_IP_ADDRESS                 :" + esme_ip_address);
		logger.info("=  OUTBIND_ESME_PORT		                :" + esme_port);
		logger.info("=  OUTBIND_ESME_PASSWORD                   :" + esme_password);
		logger.info("=  HTTP_PORT                               :" + HTTPPort);
		logger.info("=  HTTP_THREADS                            :" + HTTPThreads);
		logger.info("=  DOCROOT                                 :" + docroot);
		logger.info("=  AUTHORISED_FILES                        :" + authorisedFiles);
		logger.info("=  INJECT_MO_PAGE                          :" + injectMoPage);
		logger.info("=  SMSCID                                  :" + smscid);
		logger.info("=  DELIVERY_MESSAGES_PER_MINUTE            :" + deliverMessagesPerMin);
		logger.info("=  DELIVER_MESSAGES_FILE                   :" + deliverFile);
		logger.info("=  LOOPBACK                                :" + isLoopback());
		logger.info("=  CAPTURE_REQUESTS_BINARY                 :" + isCaptureSmeBinary());
		logger.info("=  CAPTURE_REQUESTS_BINARY_TO_FILE         :" + captureSmeBinaryToFile);
		logger.info("=  CAPTURE_RESPONSES_BINARY                :" + isCaptureSmppsimBinary());
		logger.info("=  CAPTURE_RESPONSES_BINARY_TO_FILE        :" + captureSmppsimBinaryToFile);
		logger.info("=  CAPTURE_REQUESTS_DECODED                :" + isCaptureSmeDecoded());
		logger.info("=  CAPTURE_REQUESTS_DECODED_TO_FILE        :" + captureSmeDecodedToFile);
		logger.info("=  CAPTURE_RESPONSES_DECODED               :" + isCaptureSmppsimDecoded());
		logger.info("=  CAPTURE_RESPONSES_DECODED_TO_FILE       :" + captureSmppsimDecodedToFile);
		logger.info("=  CALLBACK                                :" + callback);
		if (callback) {
			logger.info("=  CALLBACK_TARGET_HOST                    :" + callback_target_host);
			logger.info("=  CALLBACK_PORT                           :" + callback_port);
		}
		logger.info("=  ");
		logger.info("==============================================================");

	}

	public SMPPSim() {
	}

	private static int getIntProperty(Properties props, String name, int defaultValue) {
		String x = props.getProperty(name);
		int value=defaultValue;
		if (x == null || x.equals("") ) {
			logger.warning(name+" not specified. Defaulting to "+defaultValue);
			return defaultValue;
		} else {
			try {
				value = Integer.parseInt(x);
				return value;
			}
			catch (NumberFormatException e) {
				logger.warning(name+" has invalid value "+x+". Defaulting to "+defaultValue);
				return defaultValue;				
			}
		}
	
	}
		
		
	/**
	 * @return
	 */
	public static String getProtocolHandlerClassName() {
		return protocolHandlerClassName;
	}

	/**
	 * @return
	 */
	public static int getBoundReceiverCount() {
		return boundReceiverCount;
	}

	/**
	 * @return
	 */
	public static String getConnectionHandlerClassName() {
		return connectionHandlerClassName;
	}

	/**
	 * @return
	 */
	public static String getDeliverFile() {
		return deliverFile;
	}

	/**
	 * @return
	 */
	public static int getDeliverMessagesPerMin() {
		return deliverMessagesPerMin;
	}

	/**
	 * @return
	 */
	public static String getDESTMSISDNPARAM() {
		return DESTMSISDNPARAM;
	}

	/**
	 * @return
	 */
	public static String getHttp200Message() {
		return http200Message;
	}

	/**
	 * @return
	 */
	public static byte[] getHttp200Response() {
		return http200Response;
	}

	/**
	 * @return
	 */
	public static String getHttp400Message() {
		return http400Message;
	}

	/**
	 * @return
	 */
	public static byte[] getHttp400Response() {
		return http400Response;
	}

	/**
	 * @return
	 */
	public static int getHTTPPort() {
		return HTTPPort;
	}

	/**
	 * @return
	 */
	public static int getHTTPThreads() {
		return HTTPThreads;
	}
	/**
	 * @return
	 */
	public static boolean isLoopback() {
		return loopback;
	}

	/**
	 * @return
	 */
	public static String getMESSAGEPARAM() {
		return MESSAGEPARAM;
	}

	/**
	 * @return
	 */
	public static String getRcv_address() {
		return rcv_address;
	}

	/**
	 * @return
	 */
	public static Socket getReceivesocket() {
		return receivesocket;
	}

	/**
	 * @return
	 */
	public static int getSmppPort() {
		return smppPort;
	}

	/**
	 * @return
	 */
	public static String getSmscid() {
		return smscid;
	}

	/**
	 * @return
	 */
	public static String getSOURCEMSISDNPARAM() {
		return SOURCEMSISDNPARAM;
	}

	/**
	 * @return
	 */
	public static String getVersion() {
		return version;
	}

	/**
	 * @return
	 */
	public static int[] getMessageTypes() {
		return messageTypes;
	}

	/**
	 * @param i
	 */
	public static void setBoundReceiverCount(int i) {
		boundReceiverCount = i;
	}

	/**
	 * 
	 */
	public static void incrementBoundReceiverCount() {
		boundReceiverCount++;
	}

	public static void decrementBoundReceiverCount() {
		boundReceiverCount--;
	}

	public static void showReceiverCount() {
		logger.info(boundReceiverCount + " receivers connected and bound");
	}

	/**
	 * @param string
	 */
	public static void setConnectionHandlerClassName(String string) {
		connectionHandlerClassName = string;
	}

	/**
	 * @param string
	 */
	public static void setDeliverFile(String string) {
		deliverFile = string;
	}

	/**
	 * @param i
	 */
	public static void setDeliverMessagesPerMin(int i) {
		deliverMessagesPerMin = i;
	}

	/**
	 * @param bs
	 */
	public static void setHttp200Response(byte[] bs) {
		http200Response = bs;
	}

	/**
	 * @param bs
	 */
	public static void setHttp400Response(byte[] bs) {
		http400Response = bs;
	}

	/**
	 * @param i
	 */
	public static void setHTTPPort(int i) {
		HTTPPort = i;
	}

	/**
	 * @param i
	 */
	public static void setHTTPThreads(int i) {
		HTTPThreads = i;
	}

	/**
	 * @param b
	 */
	public static void setLoopback(boolean b) {
		loopback = b;
	}

	/**
	 * @param string
	 */
	public static void setProtocolHandlerClassName(String string) {
		protocolHandlerClassName = string;
	}

	/**
	 * @param string
	 */
	public static void setRcv_address(String string) {
		rcv_address = string;
	}

	/**
	 * @param socket
	 */
	public static void setReceivesocket(Socket socket) {
		receivesocket = socket;
	}

	/**
	 * @param i
	 */
	public static void setSmppPort(int i) {
		smppPort = i;
	}

	/**
	 * @param string
	 */
	public static void setSmscid(String string) {
		smscid = string;
	}

	/**
	 * @param is
	 */
	public static void setMessageTypes(int[] is) {
		messageTypes = is;
	}

	/**
	 * @return
	 */
	public static int getMaxConnectionHandlers() {
		return maxConnectionHandlers;
	}

	/**
	 * @param i
	 */
	public static void setMaxConnectionHandlers(int i) {
		maxConnectionHandlers = i;
	}
	/**
	 * @return
	 */
	public static int getInbound_queue_capacity() {
		return inbound_queue_capacity;
	}

	/**
	 * @return
	 */
	public static int getOutbound_queue_capacity() {
		return outbound_queue_capacity;
	}

	/**
	 * @return
	 */
	public static String getLifeCycleManagerClassName() {
		return lifeCycleManagerClassName;
	}

	/**
	 * @return
	 */
	public static int getMessageStateCheckFrequency() {
		return messageStateCheckFrequency;
	}

	/**
	 * @return
	 */
	public static int getDiscardFromQueueAfter() {
		return discardFromQueueAfter;
	}

	/**
	 * @return
	 */
	public static int getMaxTimeEnroute() {
		return maxTimeEnroute;
	}

	/**
	 * @return
	 */
	public static int getPercentageAccepted() {
		return percentageAccepted;
	}

	/**
	 * @return
	 */
	public static int getPercentageDelivered() {
		return percentageDelivered;
	}

	/**
	 * @return
	 */
	public static int getPercentageRejected() {
		return percentageRejected;
	}

	/**
	 * @return
	 */
	public static int getPercentageThatTransition() {
		return percentageThatTransition;
	}

	/**
	 * @return
	 */
	public static int getPercentageUndeliverable() {
		return percentageUndeliverable;
	}

	/**
	 * @return
	 */
	public static String getAuthorisedFiles() {
		return authorisedFiles;
	}

	/**
	 * @return
	 */
	public static String getInjectMoPage() {
		return injectMoPage;
	}

	/**
	 * @return
	 */
	public static String getDocroot() {
		return docroot;
	}

	/**
	 * @return
	 */
	public static boolean isCaptureSmeBinary() {
		return captureSmeBinary;
	}

	/**
	 * @return
	 */
	public static String getCaptureSmeBinaryToFile() {
		return captureSmeBinaryToFile;
	}

	/**
	 * @return
	 */
	public static boolean isCaptureSmeDecoded() {
		return captureSmeDecoded;
	}

	/**
	 * @return
	 */
	public static String getCaptureSmeDecodedToFile() {
		return captureSmeDecodedToFile;
	}

	/**
	 * @return
	 */
	public static boolean isCaptureSmppsimBinary() {
		return captureSmppsimBinary;
	}

	/**
	 * @return
	 */
	public static String getCaptureSmppsimBinaryToFile() {
		return captureSmppsimBinaryToFile;
	}

	/**
	 * @return
	 */
	public static boolean isCaptureSmppsimDecoded() {
		return captureSmppsimDecoded;
	}

	/**
	 * @return
	 */
	public static String getCaptureSmppsimDecodedToFile() {
		return captureSmppsimDecodedToFile;
	}

	/**
	 * @param b
	 */
	public static void setCaptureSmeBinary(boolean b) {
		captureSmeBinary = b;
	}

	/**
	 * @param string
	 */
	public static void setCaptureSmeBinaryToFile(String string) {
		captureSmeBinaryToFile = string;
	}

	/**
	 * @param b
	 */
	public static void setCaptureSmeDecoded(boolean b) {
		captureSmeDecoded = b;
	}

	/**
	 * @param string
	 */
	public static void setCaptureSmeDecodedToFile(String string) {
		captureSmeDecodedToFile = string;
	}

	/**
	 * @param b
	 */
	public static void setCaptureSmppsimBinary(boolean b) {
		captureSmppsimBinary = b;
	}

	/**
	 * @param string
	 */
	public static void setCaptureSmppsimBinaryToFile(String string) {
		captureSmppsimBinaryToFile = string;
	}

	/**
	 * @param b
	 */
	public static void setCaptureSmppsimDecoded(boolean b) {
		captureSmppsimDecoded = b;
	}

	/**
	 * @param string
	 */
	public static void setCaptureSmppsimDecodedToFile(String string) {
		captureSmppsimDecodedToFile = string;
	}

	public static boolean isCallback() {
		return callback;
	}

	public static void setCallback(boolean callback) {
		SMPPSim.callback = callback;
	}

	public static String getCallback_target_host() {
		return callback_target_host;
	}

	public static void setCallback_target_host(String callback_target_host) {
		SMPPSim.callback_target_host = callback_target_host;
	}

	public static String getCallback_id() {
		return callback_id;
	}

	public static void setCallback_id(String callback_id) {
		SMPPSim.callback_id = callback_id;
	}

	public static int getCallback_port() {
		return callback_port;
	}

	public static void setCallback_port(int callback_port) {
		SMPPSim.callback_port = callback_port;
	}

	public static String getEsme_ip_address() {
		return esme_ip_address;
	}

	public static void setEsme_ip_address(String esme_ip_address) {
		SMPPSim.esme_ip_address = esme_ip_address;
	}

	public static String getEsme_password() {
		return esme_password;
	}

	public static void setEsme_password(String esme_password) {
		SMPPSim.esme_password = esme_password;
	}

	public static boolean isOutbind_enabled() {
		return outbind_enabled;
	}

	public static void setOutbind_enabled(boolean outbind_enabled) {
		SMPPSim.outbind_enabled = outbind_enabled;
	}

	public static int getEsme_port() {
		return esme_port;
	}

	public static void setEsme_port(int esme_port) {
		SMPPSim.esme_port = esme_port;
	}

	public static long getStart_at() {
		return start_at;
	}

	public static void setStart_at(long start_at) {
		SMPPSim.start_at = start_at;
	}

	public static String getMid_prefix() {
		return mid_prefix;
	}

	public static void setMid_prefix(String mid_prefix) {
		SMPPSim.mid_prefix = mid_prefix;
	}

	public static String[] getPasswords() {
		return passwords;
	}

	public static String[] getSystemids() {
		return systemids;
	}

	public static String getEsme_systemid() {
		return esme_systemid;
	}

	public static int getDelayed_iqueue_period() {
		return delayed_iqueue_period;
	}

	public static void setDelayed_iqueue_period(int delayed_iqueue_period) {
		SMPPSim.delayed_iqueue_period = delayed_iqueue_period;
	}

	public static int getDelayed_inbound_queue_max_attempts() {
		return delayed_inbound_queue_max_attempts;
	}

	public static void setDelayed_inbound_queue_max_attempts(
			int delayed_inbound_queue_max_attempts) {
		SMPPSim.delayed_inbound_queue_max_attempts = delayed_inbound_queue_max_attempts;
	}
}
