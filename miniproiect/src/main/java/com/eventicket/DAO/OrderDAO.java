package com.eventicket.DAO;

import com.eventicket.Model.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

@Repository("OrderDAO")
public class OrderDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public OrderDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean createOrderItem(User user, Event event, String idt, Ticket toBuy) {
		Ticket ticket = jdbcTemplate.queryForObject("SELECT * FROM ticket WHERE idt='" + idt + "';",
				BeanPropertyRowMapper.newInstance(Ticket.class));
		if (toBuy.getQuantity() > ticket.getQuantity() || toBuy.getQuantity() <= 0) {
			return false;
		}
		try {
			OrderItem orderItem = new OrderItem(user.getId(), idt, ticket.getPrice() * toBuy.getQuantity());
			String queryString = "INSERT INTO orderItem(id, idOrder, price, idt, userId)" + "\n" + " VALUES('"
					+ orderItem.getId() + "', 'z', " + orderItem.getPrice() + ", '" + idt + "', '"
					+ orderItem.getUserId() + "')";
			jdbcTemplate.update(queryString);
			int nb = jdbcTemplate.queryForObject("SELECT numberOfTickets FROM event WHERE id='" + ticket.getId() + "';",
					Integer.class);
			jdbcTemplate.update("UPDATE event SET numberOfTickets=" + (nb - toBuy.getQuantity()) + "\n WHERE id='"
					+ ticket.getId() + "';");
			nb = jdbcTemplate.queryForObject("SELECT quantity FROM ticket WHERE idt='" + idt + "';", Integer.class);
			jdbcTemplate
					.update("UPDATE ticket SET quantity=" + (nb - toBuy.getQuantity()) + " WHERE idt='" + idt + "';");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String createOrder(String username) {
		String idString = jdbcTemplate.queryForObject("SELECT id FROM user WHERE username='" + username + "'",
				String.class);
		Orderc orderc = new Orderc(idString, 0);
		jdbcTemplate.update("INSERT INTO orderc(id, userId, total) VALUES('" + orderc.getId() + "', '" + idString
				+ "', " + 0 + ")");
		try {
			List<String> OrderItemIds = jdbcTemplate
					.queryForList("SELECT id FROM OrderItem WHERE userId='" + idString + "'", String.class);
			for (String id : OrderItemIds) {
				String isZ = jdbcTemplate.queryForObject("SELECT idOrder FROM orderItem WHERE id='" + id + "'",
						String.class);
				if (isZ.equals("z")) {
					jdbcTemplate.update("UPDATE OrderItem SET idOrder='" + orderc.getId() + "' \n WHERE idOrder='z' "
							+ " and id='" + id + "'");
					double price = jdbcTemplate.queryForObject("SELECT price FROM OrderItem WHERE id='" + id + "'",
							Double.class);
					orderc.setTotal(orderc.getTotal() + price);
					jdbcTemplate.update(
							"UPDATE orderc SET total='" + orderc.getTotal() + "' \n WHERE id='" + orderc.getId() + "'");
				}
			}
		} catch (Exception e) {
			return null;
		}
		return orderc.getId();
	}

}
