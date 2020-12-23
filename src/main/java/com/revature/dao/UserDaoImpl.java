package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class UserDaoImpl implements UserDao {
	
	private static Logger log = Logger.getLogger(Class.class);
	private static Session session = HibernateUtil.getSession();

	@Override
	public void insertUser(User user) {
		session.beginTransaction();

		try {
			session.save(user);
		} catch (Exception e) {
			log.warn("Failed to insert the user info. Error code: ", e);
		}

		session.getTransaction().commit();
	}

	@Override
	public List<User> selectAllUsers() {
		log.info("Calling ");

		List<User> userList = new ArrayList<User>();
		try {
			userList = session.createQuery("FROM User ORDER BY userId", User.class).getResultList();

		} catch (Exception e) {
			log.warn("User not found. Error code: ", e);
		}
		return userList;
	}

	@Override
	public List<User> selectAllEmployees() {
		log.info("Calling ");

		List<User> userList = session
				.createNativeQuery("Select * from ers_users where ers_user_role_id = 1", User.class).list();
		return userList;
	}

	@Override
	public User selectUserByUsername(String username) {
		User user = null;

		try {
			List<User> userList = session.createQuery("from User where username='" + username + "'", User.class).list();
			user = userList.get(0);

			log.info("User info: " + user);
		} catch (Exception e) {
			log.warn("User not found. Error code: ", e);
		}
		return user;
	}

	@Override
	public User selectUserByUserId(int userId) {
		log.info("Calling ");

		User user = null;

		try {
			List<User> userList = session.createQuery("from User where userId='" + userId + "'", User.class).list();
			user = userList.get(0);
			log.info("User info: " + user);
		} catch (Exception e) {
			log.warn("User not found. Error code: ", e);
		}
		return user;
	}

	@Override
	public void updateUser(User user) {
		session.beginTransaction();
		try {
			session.merge(user);
			session.update(user);
		} catch (Exception e) {
			log.warn("User not found. Error code: ", e);
		}
		session.getTransaction().commit();
	}

	@Override
	public void updatePassword(String username, String password) {
		log.info("Calling ");
		User user = null;
		session.beginTransaction();
		try {
			List<User> list = session.createQuery("from User where username='" + username + "'", User.class).list();

			if (list.size() == 0) {
				log.warn("Error");
				throw new NullPointerException();
			} else {
				user = list.get(0);
				if (user != null) {
					// update password
					user.setPassword(password);
					session.update(user);
					log.info("Password updated.");
				}
			}

		} catch (Exception e) {
			log.warn("Password update failed. Error code: ", e);
		}
		session.getTransaction().commit();
	}

	@Override
	public void deleteUser(User user) {
		log.info("Calling ");
		try {
			log.info("deleting user, " + user);
			session.delete(user);
			log.info("User info deleted.");
		} catch (Exception e) {
			log.warn("Failed to remove the user. Error code: ", e);
		}
		session.getTransaction().commit();
	}
}
