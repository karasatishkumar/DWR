/**************************************************************************
Author   :     Satish

Date     :     15th may 2010

File     :     MessageUpdateListener.java

Purpose  :     Provides listener notification methods when a event happened.

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

import java.util.List;

import org.satish.message.Message;

/**
 * This Interface provides listener notification methods when a event get
 * generated. All the listener class has to implement this listener and use the
 * updateMessage() method to implement their own action to be taken care for the
 * events.
 * 
 * @author satish
 */
public interface MessageUpdateListener {

	/**
	 * This method processes message object from the event object. Listener
	 * class has to implement their own version of action taker overriding this
	 * method. the registered listeners.
	 * 
	 * @param message -
	 *            messages from the event.
	 */
	public void updateMessage(Message message);
}