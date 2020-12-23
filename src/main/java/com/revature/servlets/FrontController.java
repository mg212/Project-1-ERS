package com.revature.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.util.ConnectionUtil;
import com.revature.util.RequestHelper;

public class FrontController extends HttpServlet {
	
	private static Logger log = Logger.getLogger(Class.class);

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String URI = request.getRequestURI().replace("/project-1/", "");
		switch (URI) {
		case "login":
			log.info("login");
			RequestHelper.processLogin(request, response);
			break;
		case "logout":
			log.info("logout");
			RequestHelper.processLogout(request, response);
			break;
		case "user":
			log.info("profile");
			RequestHelper.processProfile(request, response);
			break;
		case "user/update":
			log.info("user update");
			RequestHelper.processUpdateProfile(request, response);
			break;
		case "reimbursement/submit":
			log.info("submit reimb");
			RequestHelper.processSubmitReimb(request, response);
			break;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String URI = request.getRequestURI().replace("/project-1/", "");
		switch (URI) {
		case "reimbursements/view-all":
			RequestHelper.processViewAllReimb(request, response);
			break;
		case "users/view-all":
			RequestHelper.processViewAllUsers(request, response);
			break;
		case "users/view-employees":
			RequestHelper.processViewAllEmployees(request, response);
			break;
		}
	}
}
