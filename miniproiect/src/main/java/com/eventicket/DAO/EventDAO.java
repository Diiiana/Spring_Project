package com.eventicket.DAO;

import com.eventicket.Model.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

@Repository("EventDAO")
public class EventDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static List<Event> events = new ArrayList<Event>();

	public EventDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Event> getAllEvents() {
		events.clear();
		events = jdbcTemplate.query("SELECT * FROM event", BeanPropertyRowMapper.newInstance(Event.class));
		return events;
	}

	public Event getEventById(String id) {
		Event event = jdbcTemplate.queryForObject("SELECT * FROM event WHERE id='" + id + "';",
				BeanPropertyRowMapper.newInstance(Event.class));
		return event;
	}

	public Event findEventByName(String name) {
		List<Event> events = getAllEvents();
		for (Event event : events) {
			if (event.getName().equals(name)) {
				return event;
			}
		}
		return null;
	}

	public Event findEventById(String id) {
		String queryString = "SELECT * FROM event WHERE id='" + id + "'";
		return jdbcTemplate.queryForObject(queryString, BeanPropertyRowMapper.newInstance(Event.class));
	}

	public boolean insertEvent(String id, Event event) {
		String queryString = "SELECT * FROM event WHERE id='" + id + "'";
		if (!jdbcTemplate.queryForList(queryString).isEmpty() || event.getDetails().length() < 5
				|| event.getName().length() < 5) {
			return false;
		}
		queryString = "INSERT INTO event(name, details, numberOfTickets, id)" + "\n" + " VALUES('" + event.getName()
				+ "', '" + event.getDetails() + "', " + event.getNumberOfTickets() + ", '" + id.toString() + "');";
		try {
			jdbcTemplate.update(queryString);
			events.add(event);
		} catch (Exception e) {
		}
		return true;
	}

	public boolean updateEvent(String id, Event event) {
		int index = 0;
		for (Event event2 : events) {
			if (event2.getId().equals(id)) {
				index = events.indexOf(event2);
				break;
			}
		}
		if (event.getName().length() < 5 || event.getDetails().length() < 5) {
			return false;
		}
		String queryString = "UPDATE event SET name ='" + event.getName() + "', details='" + event.getDetails()
				+ "', numberOfTickets=" + event.getNumberOfTickets() + "\n" + " WHERE id='" + id.toString() + "';";
		jdbcTemplate.update(queryString);
		events.add(index, event);
		return true;
	}

	public boolean deleteEvent(String id) {
		try {
			String queryString = "DELETE FROM event WHERE id='" + id.toString() + "';";
			jdbcTemplate.update(queryString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<Ticket> ticketsEvent(String id) {
		List<String> itdStrings = jdbcTemplate.queryForList("SELECT idt FROM ticket WHERE id='" + id + "'",
				String.class);
		List<Ticket> result = new ArrayList<Ticket>();
		for (String idString : itdStrings) {
			Ticket ticket = jdbcTemplate.queryForObject("SELECT * FROM ticket WHERE idt='" + idString + "'",
					BeanPropertyRowMapper.newInstance(Ticket.class));
			Ticket t = new Ticket(idString, id, ticket.getTypet(), ticket.getPrice(), ticket.getQuantity());
			result.add(t);
		}
		return result;
	}
}
