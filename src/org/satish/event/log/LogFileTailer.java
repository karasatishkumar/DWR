/**************************************************************************
Author   :     Satish

Date     :     20th May 2010

File     :     LogFileTailer.java

Purpose  :     This class is responsible for tailing the logs.

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
package org.satish.event.log;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * A log file tailer is designed to monitor a log file and send notifications
 * when new lines are added to the log file. This class has a notification
 * strategy implement the LogFileTailerListener interface, create a
 * LogFileTailer to tail your log file, add yourself as a listener, and start
 * the LogFileTailer. This tailer simply fires notifications containing new log
 * file lines, one at a time.
 */
public class LogFileTailer extends Thread {
	/**
	 * How frequently to check for file changes; defaults to 5 seconds
	 */
	private long sampleInterval = 5000;


	/**
	 * to hold the log messages
	 */
	private List<String> message = new ArrayList<String>();

	/**
	 * The log file to tail
	 */
	private File logfile;

	/**
	 * Defines whether the log file tailer should include the entire contents of
	 * the exising log file or tail from the end of the file when the tailer
	 * starts
	 */
	private boolean startAtBeginning = false;

	/**
	 * Is the tailer currently tailing?
	 */
	private boolean tailing = false;

	/**
	 * Set of listeners
	 */
	private Set listeners = new HashSet();

	/**
	 * Creates a new log file tailer that tails an existing file and checks the
	 * file - for updates every 5000ms
	 */
	public LogFileTailer(File file) {
		this.logfile = file;
	}

	/**
	 * Creates a new log file tailer
	 * 
	 * @param file -
	 *            The file to tail
	 * @param sampleInterval -
	 *            How often to check for updates to the log file (default =
	 *            5000ms)
	 * @param startAtBeginning -
	 *            Should the tailer simply tail or should it process the entire
	 *            file and continue tailing (true) or simply start tailing from
	 *            the end of the file
	 */
	public LogFileTailer(File file, long sampleInterval,
			boolean startAtBeginning) {
		this.logfile = file;
		this.sampleInterval = sampleInterval;
	}

	/**
	 * Adds a particular listener to a event.
	 * 
	 * @param l -
	 *            Listener to add.
	 */
	public synchronized void addLogFileTailerListener(LogFileTailerListener l) {
		this.listeners.add(l);
	}

	/**
	 * Removes a particular listener from a event.
	 * 
	 * @param l -
	 *            Listener to remove.
	 */
	public synchronized void removeLogFileTailerListener(LogFileTailerListener l) {
		this.listeners.remove(l);
	}

	/**
	 * After one event happened, this method send the notifications to all the
	 * listeners registered for the event.
	 * 
	 * @param message -
	 *            list of updated lines from the log file.
	 */
	protected synchronized void fireNewLogFileLine(List<String> message) {
		for (Iterator i = this.listeners.iterator(); i.hasNext();) {
			LogFileTailerListener l = (LogFileTailerListener) i.next();
			l.newLogFileLine(message);
		}
	}

	/**
	 * Stop the tailing of the file.
	 */
	public void stopTailing() {
		this.tailing = false;
	}

	/**
	 * Checks for the log file update in a specified time delay (specified) and
	 * notify the listener if any update happens.
	 */
	public synchronized void run() {
		// The file pointer keeps track of where we are in the file
		long filePointer = 0;

		// Determine start point
		if (this.startAtBeginning) {
			filePointer = 0;
		} else {
			filePointer = this.logfile.length();
		}

		try {
			// Start tailing
			this.tailing = true;
			RandomAccessFile file = new RandomAccessFile(logfile, "r");
			while (this.tailing) {
				try {
					// Compare the length of the file to the file pointer
					long fileLength = this.logfile.length();
					if (fileLength < filePointer) {
						// Log file must have been rotated or deleted;
						// reopen the file and reset the file pointer
						file.close();
						file = new RandomAccessFile(logfile, "r");
						filePointer = 0;
					}

					if (fileLength > filePointer) {
						// There is data to read
						this.message.clear();
						file.seek(filePointer);
						String line = file.readLine();
						while (line != null) {
							this.message.add(line);
							line = file.readLine();
						}
						this.fireNewLogFileLine(message);
						filePointer = file.getFilePointer();
					}

					// Sleep for the specified interval
					sleep(this.sampleInterval);
				} catch (Exception e) {
				}
			}

			// Close the file that we are tailing
			file.close();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}