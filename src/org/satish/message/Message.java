/**************************************************************************
Author   :     Satish

Date     :     15th May 2010

File     :     Message.java

Purpose  :     This is the message bean class.

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
package org.satish.message;

/**
 * This is message bean class to be used to store the message informations.
 * 
 * @author satishkumar
 * 
 */
public class Message {
	private int client;
	private String messageBody;

	/**
	 * @return the client
	 */
	public int getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(int client) {
		this.client = client;
	}

	/**
	 * @return the messageBody
	 */
	public String getMessageBody() {
		return messageBody;
	}

	/**
	 * @param messageBody
	 *            the messageBody to set
	 */
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
}
