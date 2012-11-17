/**************************************************************************
Author   :     Satish

Date     :     15th May 2010

File     :     SessionManager.java

Purpose  :     This class is responsible to push the messages to the respective
			   clients.

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
package org.satish.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.RealScriptSession;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.proxy.ScriptProxy;
import org.directwebremoting.proxy.dwr.Util;
import org.satish.message.Message;

/**
 * This class is responsible to maintain the client script sessions and push
 * updates to the respective clients.
 * 
 * @author satishkumar
 * 
 */
public class SessionManager {

	/* This is the container which holds all the script sessions */
	private static Set<ScriptSession> scriptSessions = new HashSet<ScriptSession>();

	/**
	 * This method will get called by the client to register a script session
	 * with the application
	 * 
	 * @param param -
	 *            client parameter to set to the scriptSession object.
	 */
	@RemoteMethod
	public void addScriptSession(String param) {
		/* Creating the script session for the requested client. */
		ScriptSession scriptSession = WebContextFactory.get()
				.getScriptSession();
		/* Setting the attribute to the client */
		scriptSession.setAttribute("client", param);
		/*
		 * Adding the script session to the script session container. The
		 * synchronized block to ensure safe write to object.
		 */
		synchronized (scriptSessions) {
			scriptSessions.add(scriptSession);
		}
		System.out.println("Session added. Size : " + scriptSessions.size());
	}

	/**
	 * This method is responsible for processing the message and push the
	 * updates to the respective client.
	 * 
	 * @param message -
	 *            Message Object
	 */
	public void pushToClient(Message message) {
		System.out.println("Message : " + message.getMessageBody() + "[ "
				+ message.getClient() + " ]");
		/* Iterating the script session container for all the script sessions */
		for (ScriptSession scriptSession : this.scriptSessions) {

			/* Checks if the script session is a valid client or not */
			if (!scriptSession.isInvalidated()) {
				/*
				 * Checking the message client and script session client. if
				 * both the message client and script session client matches,
				 * the message will be pushed to the script session. This is to
				 * ensure that correct message is going to the correct client.
				 */
				if (new Integer((String) scriptSession.getAttribute("client"))
						.intValue() == message.getClient()) {
					System.out.println("Pushing the message..");
					// Util utilAll = new Util(scriptSession);
					// utilAll.addFunctionCall("update",message.getMessageBody()
					// + "[" + message.getClient() + "]");
					/*
					 * Calling the clent side javascript method with the
					 * parameters. Please check the client side java script
					 * method.
					 */
					new ScriptProxy(scriptSession).addFunctionCall("update",
							message.getMessageBody() + "["
									+ message.getClient() + "]");

				}
			} else {
				/*
				 * In case the script is invalidated. this will remove the
				 * script session object from the script session container. The
				 * synchronized block is to ensure writing of the data will be
				 * safe.
				 */
				synchronized (scriptSessions) {
					scriptSessions.remove(scriptSession);
				}
			}

		}
	}

	/**
	 * This method is used to remove the script session from the script session
	 * container
	 */
	@RemoteMethod
	public void removeScriptSession() {
		/* Getting the script session for the client. */
		ScriptSession scriptSession = WebContextFactory.get()
				.getScriptSession();
		/* Invalidate the particular script session. */
		scriptSession.invalidate();
		/*
		 * Remove the script session from the container. the synchronized block
		 * is to ensure safe removal.
		 */
		synchronized (scriptSessions) {
			scriptSessions.remove(scriptSession);
		}
		System.out.println("Session removed. Size : " + scriptSessions.size());
	}
}
