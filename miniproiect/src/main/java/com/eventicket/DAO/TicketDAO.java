package com.eventicket.DAO;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.eventicket.Model.*;

@Repository("TicketDAO")
public class TicketDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insertTicket(Ticket ticket) {
		String sql = "SELECT * FROM event WHERE id='" + ticket.getId() + "';";
		if (jdbcTemplate.queryForList(sql).isEmpty() || ticket.getTypet().length() < 2 || ticket.getPrice() == 0) {
			return false;
		}
		try {
			String idString;
			if (ticket.getIdt() == null) {
				idString = UUID.randomUUID().toString();
			} else {
				idString = ticket.getIdt();
			}
			String queryString = "INSERT INTO ticket(id, typet, price, quantity, idt)" + "\n" + " VALUES('"
					+ ticket.getId() + "', '" + ticket.getTypet() + "', " + ticket.getPrice() + ", "
					+ ticket.getQuantity() + ", '" + idString + "');";
			jdbcTemplate.update(queryString);
			EventDAO eventDAO = new EventDAO(jdbcTemplate);
			int nr = eventDAO.findEventById(ticket.getId()).getNumberOfTickets() + ticket.getQuantity();
			String queryString2 = "UPDATE event SET numberOfTickets =" + nr + "\n" + " WHERE id='" + ticket.getId()
					+ "';";
			jdbcTemplate.update(queryString2);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Map<String, Object> getTicketById(String id) {
		String sqlString = "SELECT typet, price FROM ticket WHERE idt='" + id + "';";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = jdbcTemplate.queryForMap(sqlString);
			return resultMap;
		} catch (Exception e) {
			return null;
		}
	}

	public Ticket getTId(String id) {
		String sqlString = "SELECT * FROM ticket WHERE idt='" + id + "';";

		try {
			Ticket ticket = jdbcTemplate.queryForObject(sqlString, BeanPropertyRowMapper.newInstance(Ticket.class));
			Ticket ticket2 = new Ticket(id, ticket.getId(), ticket.getTypet(), ticket.getPrice(), ticket.getQuantity());
			return ticket2;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean deleteTicket(String id) {
		try {
			Ticket ticket2 = jdbcTemplate.queryForObject("SELECT * FROM ticket WHERE idt='" + id + "';",
					BeanPropertyRowMapper.newInstance(Ticket.class));
			if (ticket2 != null) {
				String sqlString = "DELETE FROM ticket WHERE idt='" + id + "';";
				jdbcTemplate.update(sqlString);
				int quantity = jdbcTemplate.queryForObject("SELECT quantity FROM ticket WHERE idt='" + id + "'",
						Integer.class);
				String idEvent = jdbcTemplate.queryForObject("SELECT id FROM ticket WHERE idt='" + id + "'",
						String.class);
				int quantityEv = jdbcTemplate.queryForObject("SELECT quantity FROM event WHERE id='" + idEvent + "'",
						Integer.class);
				jdbcTemplate
						.update("UPDATE event SET quantity =" + (quantity + quantityEv) + "WHERE id='" + idEvent + "'");

				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deleteTicketFromUser(String idOrderItem) {
		try {
			String ticketId = jdbcTemplate.queryForObject("SELECT idt FROM orderItem WHERE id='" + idOrderItem + "';",
					String.class);
			double total = jdbcTemplate.queryForObject("SELECT price FROM orderItem \n WHERE id='" + idOrderItem + "';",
					Double.class);
			double tickPrice = jdbcTemplate.queryForObject("SELECT price FROM ticket WHERE idt='" + ticketId + "';",
					Double.class);
			int quantity = (int) (total / tickPrice);
			String idEvent = jdbcTemplate.queryForObject("SELECT id FROM ticket WHERE idt='" + ticketId + "'",
					String.class);
			int quantityEv = jdbcTemplate
					.queryForObject("SELECT numberOfTickets FROM event \nWHERE id='" + idEvent + "'", Integer.class);
			jdbcTemplate.update(
					"UPDATE event SET numberOfTickets=" + (quantity + quantityEv) + "\nWHERE id='" + idEvent + "'");
			jdbcTemplate.update("DELETE FROM orderItem WHERE id='" + idOrderItem + "'");
			double quantityTick = jdbcTemplate
					.queryForObject("SELECT quantity FROM ticket WHERE idt='" + ticketId + "'", Double.class);
			jdbcTemplate.update(
					"UPDATE ticket SET quantity='" + (quantity + quantityTick) + "' WHERE idt='" + ticketId + "'");
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
