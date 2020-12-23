package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDao {
	
	public void insertUser(User user);

	public List<User> selectAllUsers();
	public List<User> selectAllEmployees();
	public User selectUserByUsername(String username);
	public User selectUserByUserId(int userId);
	
	public void updateUser(User user);
	public void updatePassword(String username, String password);

	public void deleteUser(User user);
}
