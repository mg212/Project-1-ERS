package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.services.UserServiceImpl;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(LoginServlet.class);
	private static UserServiceImpl userService = new UserServiceImpl();

	public LoginServlet() {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User e = userService.confirmLogin(username, password);
		if (e != null) {
			
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
			RequestDispatcher rd = req.getRequestDispatcher("home.html");
			rd.forward(req, resp);
		} else {

		}
	}
}
