/**************************************************************************
Author   :     Satish

Date     :     15th May 2010

File     :     MessageEventGenerator.java

Purpose  :     This class is responsible for generating the event and 
			   notify all the listeners. 

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.satish.message.Message;

/**
 * This class generate the event for all the listeners. This class is a thead
 * based class. once this class this instantiated the the thread each time will
 * get the message from the set of messages depending on a random index. With
 * the message body it will create the Message object and send the notification
 * to all the listeners.
 */
public class MessageEventGenerator extends Thread {
	/**
	 * Thread iterate latency, defaults to 1 seconds
	 */
	private long sampleInterval = 3000;

	/**
	 * The message to pass the listener
	 */
	private Message message;

	/**
	 * Is the thread currently running?
	 */
	private boolean thread = false;

	/**
	 * Set of listeners
	 */
	private Set listeners = new HashSet();

	/**
	 * Set of messages to be fetched randomly.
	 */
	String messageBody[] = { "THERE IS A RIVER ON THE WAY TO HELL",
			"WHY LORD OF THE RINGS HAS GOT A BAD TRANSLATION",
			"WHICH BOOK HAS GOT THIS WORD CALL ME ISHMAIL",
			"MY BIKE NEVER LET ME DOWN WHILE TOURING",
			"THE NAME OF MY NAME IS SATISH", "AFTER HELL THERE IS NOT WORLD",
			"INDIC LANGUAGE PDF USING JAVA IS POSSIBLE NOW",
			"LAST DAY AT LA IS GONNA BE FUN", "MUNEZ SANTIAGO WAS FROM BARKA",
			"I AM SUPPORTING SPAIN THIS WORLD CUP" };

	/**
	 * Default constructor that have time latency is 3000MS for updates every
	 * 3000ms
	 */
	public MessageEventGenerator() {
	}

	/**
	 * Creates a object with custom time interval.
	 * 
	 * @param sampleInterval -
	 *            thead run time inerval
	 * @param thread -
	 *            thead run status
	 */
	public MessageEventGenerator(long sampleInterval, boolean thread) {
		this.sampleInterval = sampleInterval;
		this.thread = thread;
	}

	/**
	 * Register a particular listener to a event.
	 * 
	 * @param l -
	 *            Listener to add.
	 */
	public synchronized void addMessageUpdateListener(MessageUpdateListener l) {
		this.listeners.add(l);
	}

	/**
	 * Unregister a particular listener from a event.
	 * 
	 * @param l -
	 *            Listener to remove.
	 */
	public synchronized void removeMessageUpdateListener(MessageUpdateListener l) {
		this.listeners.remove(l);
	}

	/**
	 * After one event generated, this method send the notifications to all the
	 * listeners registered for the event.
	 * 
	 * @param message -
	 *            After the event get generated the message to send the
	 *            listeners
	 * 
	 */
	protected synchronized void fireEvent(Message message) {
		for (Iterator i = this.listeners.iterator(); i.hasNext();) {
			MessageUpdateListener l = (MessageUpdateListener) i.next();
			l.updateMessage(message);
		}
	}

	/**
	 * Stop the the thread.
	 */
	public void stopThread() {
		this.thread = false;
	}

	/**
	 * The generator method notify the listener if any update happens. This this
	 * the run() method implementor for the thread class. In this implementation
	 * random number got generated and depending on that particular message has
	 * been set for a particular event and client.
	 */
	public synchronized void run() {

		try {
			/* Creating the message object */
			message = new Message();
			while (this.thread) {
				/* Creating teh random object */
				Random random = new Random();
				/*
				 * setting the client to a random number. one is added to the
				 * random number, because the random number generated includes 0
				 * and excludes 10. Our UI client parameters starts from 1 to
				 * 10.
				 */
				message.setClient(random.nextInt(10) + 1);
				/*
				 * Setting the message body to a message depending on the random
				 * number generated. we are regenerating the random number again
				 * to ensure a particular client will get different messages
				 * each time depending on the random numbers.
				 */
				message.setMessageBody(messageBody[random.nextInt(10)]);
				/* Notifying all the listener for the respective event. */
				this.fireEvent(message);

				sleep(this.sampleInterval);
			}
		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * The developer has to handle the exception depending on the
			 * requirement. As this is a demonstration this has not been taken
			 * care of.
			 */
		}
	}

}