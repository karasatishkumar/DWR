/**************************************************************************
Author   :     Satish

Date     :     20th May 2010

File     :     WebTailer.java

Purpose  :     This class push the web log messages to all the clients when there is 
			   a update in the catalina.out log file.

Compile  :     JDK 1.6

Functions:     See JavaDoc

Note     :

History  :
Date        Version  Person      		Change
--------------------------------------------------------------------------
20 May 10    0.1     Satish   	Initial Creation

Bug Fixes:
Date        Person      Bug Fix Details


TO DO:
-------------------------------------------------------------------------
1.
2.
3.

--------------------------------------------------------------------------

 ***************************************************************************/
package org.satish.service.log;

// Import the Java classes
import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;

import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.satish.event.log.LogFileTailer;
import org.satish.event.log.LogFileTailerListener;


/**
 * It Create the web log tailer. And depending on the log file update it push
 * the message to all the clients.
 */
public class WebTailer implements LogFileTailerListener {
	/**
	 * The log file tailer
	 */
	private LogFileTailer webTailer;
	private ServletContext servletContext = null;

	// catalina.out path
	private String logPath = "/usr/local/tomcat/logs";


	/**
	 * Creates a new Tail instance to follow the specified file
	 */
	public WebTailer() {
		webTailer = new LogFileTailer(new File(logPath
				+ File.separator + "catalina.txt"), 3000, true);
		// Registering to the event.
		webTailer.addLogFileTailerListener(this);
		webTailer.setPriority(10);
		webTailer.setName("Web");
		webTailer.start();
	}
	
	/**
	 * Creates a new Tail instance to follow the specified file
	 */
	public WebTailer(ServletContext servletContext) {
		webTailer = new LogFileTailer(new File(logPath
				+ File.separator + "catalina.out"), 3000, true);
		// Registering to the event.
		webTailer.addLogFileTailerListener(this);
		webTailer.setPriority(10);
		webTailer.setName("Web");
		webTailer.start();
		this.servletContext = servletContext;
	}

	/**
	 * This method get called by the event class object, when there is any
	 * update happened to the log file.
	 * 
	 * @param message
	 *            The list line that has been added to the tailed log file
	 */
	public void newLogFileLine(List<String> message) {
		for (String msg : message) {
			System.out.println("Message : "  + msg);
		}
		System.out.println("");
		try {
			ServerContext serverContext = ServerContextFactory
					.get(servletContext);
			if (serverContext != null) {
				Collection sessions = null;

				// get all the user sessions on the particaular page.
				sessions = serverContext
						.getScriptSessionsByPage("/DWR/log.html");
				if (sessions == null) {
					System.out.println("Sessions are null.");
				} else {
					// System.out.println("Painting Events to front end");
					Util utilAll = new Util(sessions);

					// calls the particulat javascript method of the client by
					// passing the list of messages.
					utilAll.addFunctionCall("paintLog", message);
				}
			}else{
				System.out.println("ServerContext is null");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Command-line launcher
	 */
	public static void main(String[] args) {
		WebTailer tail = new WebTailer();
	}
}