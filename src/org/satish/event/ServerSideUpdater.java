/**************************************************************************
Author   :     Satish

Date     :     15th May 2010

File     :     ServerSideUpdater.java

Purpose  :     This class is the implementer class for the MessageUpdateListener interface.

Compile  :     JDK 1.6

Functions:     See JavaDoc

Note     :

History  :
Date        Version  Person      		Change
--------------------------------------------------------------------------
15th May 2010    0.1     Satish   	Initial Creation

Bug Fixes:
Date        Person      Bug Fix Details


TO DO:
-------------------------------------------------------------------------
1.
2.
3.

--------------------------------------------------------------------------

 ***************************************************************************/
package org.satish.event;

import java.util.Date;

import org.satish.message.Message;
import org.satish.service.SessionManager;

/**
 * This class implements the MessageUpdateListener interface. It has got it's
 * own implementation for updateMessage(). It is meant to demonstrate the server
 * side pushing. So the implementation is according to that.
 */
public class ServerSideUpdater implements MessageUpdateListener {

	/**
	 * Creates a new generator instance
	 */
	public ServerSideUpdater() {

	}

	/**
	 * This method get called by the event class object, when there is any event
	 * get generated. And push the messages to SessionManager class's
	 * pushToClient() method. There the message has to be processed and push to
	 * the respective clients.
	 * 
	 * @param message -
	 *            Message received from the event generator
	 */
	public void updateMessage(Message message) {
		System.out.println("Got a new message for " + message.getClient()
				+ " - Message body is '" + message.getMessageBody() + "'");
		SessionManager sessionManager = new SessionManager();
		// Need to implement the pushing technology.
		sessionManager.pushToClient(message);
	}

}