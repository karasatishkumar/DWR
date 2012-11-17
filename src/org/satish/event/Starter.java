/**************************************************************************
Author   :     Satish

Date     :     15th May 2010

File     :     Starter.java

Purpose  :     This class is responsible to start the event engine at the server.

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

/**
 * This class is responsible to start the event engine at the server. Once
 * servlet has been created and in the init block the object has been
 * instantiated to start the server, when the server get started. The servlet is
 * configured in web.xml to do the required job.
 * 
 * @author satishkumar
 * 
 */
public class Starter {
	/**
	 * Constructor to take the time interval for thread and the thread running
	 * status.
	 * 
	 * @param sampleInterval -
	 *            thread sleep interval
	 * @param thread -
	 *            thread running status
	 */
	public Starter(long sampleInterval, boolean thread) {
		/* Creating the event generator object */
		MessageEventGenerator messageEventGenerator = new MessageEventGenerator(
				sampleInterval, thread);

		/* Creating the listener to listen to the events */
		ServerSideUpdater serverSideUpdater = new ServerSideUpdater();

		/* Registering the listener to the event. */
		messageEventGenerator.addMessageUpdateListener(serverSideUpdater);

		messageEventGenerator.setName("Event Generator");
		messageEventGenerator.setPriority(10);
		/* Starting the event engine. */
		messageEventGenerator.start();
		System.out.println("Event engine started.............");
	}

}
