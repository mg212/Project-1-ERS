package com.revature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.services.ReimbursementServiceImpl;
import com.revature.services.UserServiceImpl;

public class RequestHelper {

	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static ObjectMapper om = new ObjectMapper();
	private static UserServiceImpl userService = new UserServiceImpl();
	private static ReimbursementServiceImpl reimbService = new ReimbursementServiceImpl();

	public static void processLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString();
		LoginTemplate loginAttempt = om.readValue(body, LoginTemplate.class);
		String username = loginAttempt.getUsername();
		String password = loginAttempt.getPassword();
		User e = userService.confirmLogin(username, password);

		if (e != null) {
			HttpSession session = req.getSession();
			session.setAttribute("username", username);

			PrintWriter pw = res.getWriter();
			res.setContentType("application/json");

			UserDTO eDTO = userService.convertToDTO(e);
			pw.println(om.writeValueAsString(eDTO));
		} else {
			res.setContentType("application/json");
			res.setStatus(204);
		}
	}

	public static void processLogout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession(false);
		if (session != null) {
			String username = (String) session.getAttribute("username");
			session.invalidate();
		}
		res.setStatus(200);
	}

	public static void processViewAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<User> list = userService.getAllEmployees();
		List<UserDTO> listDTO = new ArrayList<>();
		
		if (list != null) {
			for (User u : list) {
				System.out.println(u);
				listDTO.add(userService.convertToDTO(u));
			}
			String json = om.writeValueAsString(listDTO);
			PrintWriter pw = response.getWriter();
			pw.println(json);
			response.setContentType("application/json");
			response.setStatus(200); 
		}else {
			response.setContentType("application/json");
			response.setStatus(204); 	}
	}

	public static void processProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession();
		User profile = userService.getUserByUsername((String) session.getAttribute("username"));
		UserDTO eDTO = userService.convertToDTO(profile);
		String json = om.writeValueAsString(eDTO);
		pw.println(json);
	}

	public static void processSubmitReimb(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		String body = s.toString();
		ReimbTemplate reimbAttempt = om.readValue(body, ReimbTemplate.class);
			Reimbursement r = reimbService.convertToReimb(reimbAttempt);
			int id = reimbService.addReimbursement(r);
			Reimbursement result = reimbService.getReimbursementById(id);
			if (result != null) {
				ReimbursementDTO reimbDTO = reimbService.convertToDTO(result);
				PrintWriter pw = response.getWriter();
				String json = om.writeValueAsString(reimbDTO);
				pw.println(json);
				response.setContentType("application/json");
				response.setStatus(200); 
			} else {
				response.setContentType("application/json");
				response.setStatus(204); 
			}
	}

	public static void processUpdateProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		String body = s.toString();
		UserDTO updateAttempt = om.readValue(body, UserDTO.class);
		User u = userService.convertToUser(updateAttempt);
		userService.modifyUser(u);
		User result = userService.getUserByUserId(u.getUserId());
		if (result != null) {
			UserDTO uDTO = userService.convertToDTO(result);
			PrintWriter pw = response.getWriter();
			String json = om.writeValueAsString(uDTO);
			pw.println(json);
			response.setContentType("application/json");
			response.setStatus(200);
		} else {
			response.setContentType("application/json");
			response.setStatus(204); 
		}
	}

	public static void processViewAllReimb(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Reimbursement> list = reimbService.getAllReimbursements();
		List<ReimbursementDTO> listDTO = new ArrayList<>();
		if (list != null) {
			for (Reimbursement r : list) {
				if(r.getResolutionDateTime() == null) {
					listDTO.add(reimbService.convertToDTO(r));
				}else {
					listDTO.add(reimbService.convertToDTOFull(r));
				}
			}
			String json = om.writeValueAsString(listDTO);
			PrintWriter pw = response.getWriter();
			pw.println(json);
			response.setContentType("application/json");
			response.setStatus(200); 
		}else {
			response.setContentType("application/json");
			response.setStatus(204); 
		}
	}
	public static void processViewAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<User> list = userService.getAllUsers();
		List<UserDTO> listDTO = new ArrayList<>();
		if (list != null) {
			for (User u : list) {
				listDTO.add(userService.convertToDTO(u));
			}
			String json = om.writeValueAsString(listDTO);
			PrintWriter pw = response.getWriter();
			pw.println(json);
			response.setContentType("application/json");
			response.setStatus(200); 
		}else {
			response.setContentType("application/json");
			response.setStatus(204); 
		}
	}
}
