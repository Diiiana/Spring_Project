package com.eventicket.DAO;

import com.eventicket.Model.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;;

@Repository("userDAO")
public class UserDAO {
	private static List<User> users = new ArrayList<User>();
	public static Map<Boolean, User> activeUsers = new HashMap<Boolean, User>();
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setInctive() {
		for (User user2 : activeUsers.values()) {
			activeUsers.put(false, user2);
		}
		String queryString = "UPDATE usersession SET active=" + false;
		jdbcTemplate.update(queryString);
	}

	public void setActive(User user) {
		for (User user2 : getAllUsers()) {
			if (!user2.getUsername().equals(user.getUsername())) {
				activeUsers.put(false, user2);
			} else {
				activeUsers.put(true, user2);
				String queryString = "UPDATE usersession SET active=" + true + " WHERE id='" + user.getUsername() + "'";
				jdbcTemplate.update(queryString);
			}
		}
	}

	public String getActiveUser() {
		try {
			String queryString = "SELECT id FROM usersession WHERE active= true";
			return jdbcTemplate.queryForObject(queryString, String.class);
		} catch (Exception e) {
			return "";
		}
	}

	public static Map<Boolean, User> getActiveUsers() {
		return activeUsers;
	}

	public boolean insertUser(String id, User user) {
		String queryString = "SELECT * FROM user WHERE username='" + user.getUsername() + "' and id <> '" + id + "';";
		if (!jdbcTemplate.queryForList(queryString).isEmpty() || !isValidNumber(user.getPhoneNumber())
				|| user.getPassword().length() < 5) {
			return false;
		}
		queryString = "INSERT INTO user(id, username, firstName, lastName, phoneNumber, password, email, address)"
				+ "\n" + " VALUES('" + id.toString() + "', " + user.toInsert() + ")";
		jdbcTemplate.update(queryString);
		try {
			String queryString2 = "INSERT INTO usersession(id, active)" + "\n" + " VALUES('" + user.getUsername()
					+ "', " + false + ")";
			jdbcTemplate.update(queryString2);
		} catch (Exception e) {
		}
		return true;
	}

	public boolean isValidNumber(String phoneNumber) {
		if (phoneNumber.length() != 10) {
			return false;
		}
		for (int i = 0; i < phoneNumber.length(); i++) {
			if (!Character.isDigit(phoneNumber.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean updateUser(String id, User user) {
		String queryString = "SELECT * FROM user WHERE username='" + user.getUsername() + "' and id <> '" + id + "';";
		if (!jdbcTemplate.queryForList(queryString).isEmpty() || !isValidNumber(user.getPhoneNumber())
				|| user.getUsername().length() < 5 || user.getPassword().length() < 5
				|| user.getFirstName().length() < 5 || user.getLastName().length() < 5) {
			return false;
		}
		queryString = "UPDATE user SET username='" + user.getUsername() + "', firstName='" + user.getFirstName()
				+ "', lastName='" + user.getLastName() + "', phoneNumber='" + user.getPhoneNumber() + "', password='"
				+ user.getPassword() + "', email='" + user.getEmail() + "', address='" + user.getAddress()
				+ "' WHERE id='" + id.toString() + "'";
		jdbcTemplate.update(queryString);
		users.add(user);
		return true;
	}

	public boolean deleteUser(String id) {
		String sqlString = "SELECT username FROM user WHERE id='" + id + "'";
		String username = jdbcTemplate.queryForObject(sqlString, String.class);
		sqlString = "SELECT orderItemId FROM Orderc WHERE idUser='" + id + "'";
		try {
			List<String> idStrings = jdbcTemplate.query(sqlString, BeanPropertyRowMapper.newInstance(String.class));
			for (String ids : idStrings) {
				sqlString = "DELETE FROM OrderItem WHERE idOrder='" + ids + "'";
				jdbcTemplate.update(sqlString);
			}
			for (String ids : idStrings) {
				sqlString = "DELETE FROM Orderc WHERE id='" + ids + "'";
				jdbcTemplate.update(sqlString);
			}
		} catch (Exception e) {
		}
		String queryString = "DELETE FROM user WHERE id='" + id.toString() + "'";
		jdbcTemplate.update(queryString);
		String queryString2 = "DELETE FROM usersession WHERE id='" + username + "'";
		jdbcTemplate.update(queryString2);
		return true;
	}

	public boolean logIn(User user) {
		List<User> allUsers = getAllUsers();
		for (User user2 : allUsers) {
			if (user2.getUsername().equals(user.getUsername()) && user2.getPassword().equals(user.getPassword())) {
				return true;
			}
		}
		return false;
	}

	public List<User> getAllUsers() {
		users.clear();
		users = jdbcTemplate.query("SELECT * FROM user", BeanPropertyRowMapper.newInstance(User.class));
		return users;
	}

	public boolean logOut(User user) {
		String queryString = "UPDATE usersession SET active=" + false + " WHERE id='" + user.getUsername() + "';";
		jdbcTemplate.update(queryString);
		return true;
	}

	public Map<Ticket, Event> myTickets(String id) {
		Map<Ticket, Event> ticketsMap = new HashMap<Ticket, Event>();
		List<String> ordersItemIds = jdbcTemplate
				.queryForList("SELECT id FROM orderItem WHERE userId=" + "'" + id + "' and idOrder='z';", String.class);
		for (String item : ordersItemIds) {
			try {
				String tickIdString = jdbcTemplate
						.queryForObject("SELECT idt FROM orderItem WHERE id=" + "'" + item + "';", String.class);
				Ticket ticket = jdbcTemplate.queryForObject(
						"SELECT * FROM ticket WHERE idt=" + "'" + tickIdString + "';",
						BeanPropertyRowMapper.newInstance(Ticket.class));
				Event event = jdbcTemplate.queryForObject("SELECT * FROM event WHERE id=" + "'" + ticket.getId() + "';",
						BeanPropertyRowMapper.newInstance(Event.class));
				Double tickPrice = jdbcTemplate
						.queryForObject("SELECT price FROM orderItem WHERE id=" + "'" + item + "';", Double.class);
				ticket.setPrice(tickPrice);
				Ticket ticket2 = new Ticket(tickIdString, ticket.getId(), ticket.getTypet(), ticket.getPrice(),
						ticket.getQuantity());
				event.setId(item);
				ticketsMap.put(ticket2, event);
			} catch (Exception e) {
			}
		}
		return ticketsMap;

	}

	public Map<String, Object> tickets(String id) {
		String sqlString = "SELECT typet, price FROM ticket WHERE idt='" + id + "';";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = jdbcTemplate.queryForMap(sqlString);
			return resultMap;
		} catch (Exception e) {
			return null;
		}
	}

	public Map<Ticket, Event> tickEv(String username) {
		String id = jdbcTemplate.queryForObject("SELECT id FROM user WHERE username='" + username + "';", String.class);
		List<String> ordersItemIds = jdbcTemplate
				.queryForList("SELECT orderItemId FROM orderc WHERE userId=" + "'" + id + "';", String.class);
		List<Ticket> tickets = new ArrayList<Ticket>();
		List<OrderItem> allOrderItems = new ArrayList<OrderItem>();
		Map<Ticket, Event> map = new HashMap<Ticket, Event>();
		for (String oIId : ordersItemIds) {
			allOrderItems.clear();
			allOrderItems.add(jdbcTemplate.queryForObject("SELECT * from orderItem WHERE id='" + oIId + "';",
					BeanPropertyRowMapper.newInstance(OrderItem.class)));
			for (OrderItem orderItem : allOrderItems) {
				if (orderItem.getIdt() != null) {
					try {
						Ticket t = jdbcTemplate.queryForObject(
								"SELECT * from ticket WHERE idt='" + orderItem.getIdt() + "';",
								BeanPropertyRowMapper.newInstance(Ticket.class));
						Ticket ticket2 = new Ticket(orderItem.getIdt(), t.getId(), t.getTypet(), t.getPrice(),
								t.getQuantity());
						tickets.add(ticket2);
						Event e = jdbcTemplate.queryForObject("SELECT * from event WHERE id='" + t.getId() + "';",
								BeanPropertyRowMapper.newInstance(Event.class));
						e.setId(id);
						map.put(ticket2, e);
					} catch (Exception e) {
					}
				}
			}
		}
		return map;
	}

	public User getUserByUsername(String username) {
		try {
			String queryString = "SELECT * FROM user WHERE username='" + username + "'";
			return jdbcTemplate.queryForObject(queryString, BeanPropertyRowMapper.newInstance(User.class));
		} catch (Exception exception) {
			return null;
		}
	}

	public User getUserById(String id) {
		try {
			String queryString = "SELECT * FROM user WHERE id='" + id + "'";
			return jdbcTemplate.queryForObject(queryString, BeanPropertyRowMapper.newInstance(User.class));
		} catch (Exception exception) {
			return null;
		}
	}

	public Map<Ticket, Event> getMyOrders(String activeUser) {
		String idString = jdbcTemplate.queryForObject("SELECT id FROM user WHERE username = '" + activeUser + "'",
				String.class);
		List<String> orderIdList = jdbcTemplate.queryForList("SELECT id FROM orderc WHERE userId ='" + idString + "'",
				String.class);
		Map<Ticket, Event> myOrdersMap = new HashMap<Ticket, Event>();
		try {
			for (String id : orderIdList) {
				List<String> orderItemIdStrings = jdbcTemplate
						.queryForList("SELECT id FROM orderItem WHERE idOrder='" + id + "'", String.class);
				for (String idOrdItem : orderItemIdStrings) {
					String idTicketString = jdbcTemplate
							.queryForObject("SELECT idt FROM orderItem WHERE id='" + idOrdItem + "'", String.class);
					Double total = jdbcTemplate.queryForObject("SELECT total FROM orderc WHERE id='" + id + "'",
							Double.class);
					String idEventString = jdbcTemplate
							.queryForObject("SELECT id FROM ticket WHERE idt='" + idTicketString + "'", String.class);
					Ticket ticket = jdbcTemplate.queryForObject(
							"SELECT * FROM ticket WHERE idt='" + idTicketString + "'",
							BeanPropertyRowMapper.newInstance(Ticket.class));
					ticket.setPrice(total);
					Event event = jdbcTemplate.queryForObject("SELECT * FROM event WHERE id='" + idEventString + "'",
							BeanPropertyRowMapper.newInstance(Event.class));
					myOrdersMap.put(ticket, event);
				}
			}
		} catch (Exception e) {
			return null;
		}
		return myOrdersMap;
	}

}
