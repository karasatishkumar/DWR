/**************************************************************************
Author   :     Satish

Date     :     20th May 2010

File     :     LogFileTailerListener.java

Purpose  :     Provides listener notification methods when a tailed log file is updated.

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

import java.util.List;

/**
 * This Interface provides listener notification methods when a tailed log file
 * is updated
 * 
 * @author satish
 */
public interface LogFileTailerListener {

	/**
	 * This method adds all the list of messages from the tailed log file to the
	 * registered listeners.
	 * 
	 * @param message -
	 *            list updated messages from the file.
	 */
	public void newLogFileLine(List<String> message);
}