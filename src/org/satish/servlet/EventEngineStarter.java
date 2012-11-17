/**************************************************************************
Author   :     Satish

Date     :     15th May 2010

File     :     EventEngineStarter.java

Purpose  :     This sevlet is responsible to start the event engine.

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
package org.satish.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.satish.event.Starter;
import org.satish.service.log.WebTailer;

/**
 * This servlet is responsible to start the event engine at the server start.
 * Servlet implementation class for Servlet: EventEngineStarter
 * 
 */
public class EventEngineStarter extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	/*
	 * Default constructor. Calling the super class constructor.
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public EventEngineStarter() {
		super();
	}

	/*
	 * This method will get called at the time of server starting and start the
	 * event engine.
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("Init block is called");
		/* starting the event engine. */
		Starter starter = new Starter(3000, true);
		WebTailer webTailer = new WebTailer(this.getServletContext());
	}
}