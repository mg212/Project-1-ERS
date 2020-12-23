package com.revature.services;

import java.util.List;

import com.revature.models.User;


public interface UserService {
	
	public void addUser(User u);
	
	public List<User> getAllUsers();
	public List<User> getAllEmployees();
	public User getUserByUsername(String username);
	public User getUserByUserId(int userId);
	
	public void modifyUser(User u);
	
	public void deleteUser(User u);

	User confirmLogin(String username, String password);
	
}
