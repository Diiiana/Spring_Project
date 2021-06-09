package com.eventicket.Api;

import com.eventicket.DAO.*;
import com.eventicket.Model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import javax.validation.Valid;

@Controller
public class AppController {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private TicketDAO ticketDAO;

	List<Event> events;
	Map<String, Event> allEvents = new HashMap<>();

	List<User> users;
	Map<String, User> allUsers = new HashMap<>();

	public void setAllUsers() {
		List<User> dbUsers = userDAO.getAllUsers();
		for (User user : dbUsers) {
			allUsers.put(user.getId(), user);
		}
	}

	@RequestMapping("/index")
	public String welcome(Model model) {
		events = eventDAO.getAllEvents();
		model.addAttribute("events_list", events);
		return "index";
	}

	@RequestMapping("/list_events")
	public String listEvents(Model model) {
		List<Event> eventsList = eventDAO.getAllEvents();
		for (Event even : eventsList) {
			allEvents.put(even.getId(), even);
		}
		model.addAttribute("events_list", allEvents.values());
		return "list_events";
	}

	@GetMapping(value = "/view_event_details/{id}")
	public String viewEvent(@PathVariable("id") String idEvent, Model model) {
		if (allEvents.get(idEvent) != null) {
			model.addAttribute("events_list", allEvents.get(idEvent));
			model.addAttribute("event_tickets", eventDAO.ticketsEvent(idEvent));
			return "/view_event_details";
		} else {
			return "/index";
		}
	}

	@RequestMapping("/home")
	public String goBack() {
		return "index";
	}

	@RequestMapping("/aboutus")
	public String aboutUs() {
		return "aboutus";
	}

	@RequestMapping("/laboutus")
	public String laboutUs() {
		return "/laboutus";
	}

	@RequestMapping("/all_users")
	public String listUsers(Model model) {
		setAllUsers();
		model.addAttribute("user", userDAO.getAllUsers());
		return "all_users";
	}

	@GetMapping("/all_users/edit/{id}")
	public String editUser(@PathVariable("id") String id, Model model) {
		model.addAttribute("editUser", userDAO.getUserById(id));
		return "user_edit_form";
	}

	@PostMapping("all_users/edit/{id}")
	public String updateUser(@PathVariable("id") String id, @ModelAttribute("editUser") User user,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "user_edit_form";
		}
		if (userDAO.updateUser(id, user)) {
			model.addAttribute("user", userDAO.getAllUsers());
			model.addAttribute("error_edit", "");
			return "all_users";
		} else {
			model.addAttribute("error_edit",
					"Invalid data. Please enter unique username, valid phone number and do not let empty fields. \n Username, password, first name and last name should have at least 5 characters.");
			return "user_edit_form";
		}
	}

	@GetMapping("/all_users/delete/{id}")
	public String deleteUser(@PathVariable("id") String usrid, Model model) {
		try {
			userDAO.deleteUser(usrid);
			allUsers.remove(usrid);
		} catch (Exception e) {
		}
		setAllUsers();
		model.addAttribute("user", allUsers.values());
		return "all_users";
	}

	@GetMapping("all_events/edit/{id}")
	public String editEvent(@PathVariable("id") String id, Model model) {
		model.addAttribute("editEvent", allEvents.get(id));
		return "event_edit_form";
	}

	@PostMapping("all_events/edit/{id}")
	public String updateEvent(@PathVariable("id") String id, @ModelAttribute("editEvent") Event event, Model model) {
		if (eventDAO.updateEvent(id, event)) {
			allEvents.put(id, event);
			model.addAttribute("events_list", allEvents.values());
			model.addAttribute("error_edit", "");
			return "all_events";
		} else {
			model.addAttribute("error_edit", "Invalid data. Name and details should have at least 5 characters.");
			return "event_edit_form";
		}

	}

	@GetMapping("/all_events/delete/{id}")
	public String deleteEvent(@PathVariable("id") String id, Model model) {
		allEvents.remove(id);
		eventDAO.deleteEvent(id);
		model.addAttribute("events_list", allEvents.values());
		return "all_events";
	}

	@GetMapping("/all_events/add_event")
	public String getEventForm() {
		return "add_event";
	}

	@PostMapping("/all_events/add_event")
	public String addEvent(@ModelAttribute("addEvent") Event event, Model model) {
		if (eventDAO.insertEvent(event.getId(), event)) {
			allEvents.put(event.getId(), event);
			model.addAttribute("events_list", allEvents.values());
			model.addAttribute("error_edit", "");
			model.addAttribute("tickets", new Ticket());
			return "/add_tickets";
		} else {
			model.addAttribute("error_edit",
					"Invalid data. Name and details should have at least 5 characters. ID should be unique.");
			return "add_event";
		}
	}

	@GetMapping("/all_events/add_tickets")
	public String getTicketForm(Model model) {
		model.addAttribute("tickets", new Ticket());
		return "add_tickets";
	}

	@PostMapping("/all_events/add_tickets")
	public String addTickets(@ModelAttribute("tickets") @Valid Ticket ticket, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "/add_tickets";
		}
		try {
			if (ticketDAO.insertTicket(ticket)) {
				model.addAttribute("events_list", eventDAO.getAllEvents());
				model.addAttribute("error_edit", "");
				return "/add_tickets";
			} else {
				model.addAttribute("error_edit",
						"Invalid data. Type should have at least 2 characters. Enter valid price.");
				return "/add_tickets";
			}
		} catch (Exception e) {
			model.addAttribute("error_edit", "");
			return "/add_tickets";
		}
	}

	@RequestMapping("/all_events")
	public String allEvents(Model model) {
		events = eventDAO.getAllEvents();
		for (Event event : events) {
			allEvents.put(event.getId(), event);
		}
		model.addAttribute("events_list", allEvents.values());
		return "/all_events";
	}

	@GetMapping("filtered")
	public String listFiltered(@RequestParam(defaultValue = "") String name, Model model) {
		List<Event> list = new ArrayList<Event>();
		Event foundEvent = eventDAO.findEventByName(name);
		if (foundEvent == null) {
			list.clear();
		} else {
			list.add(eventDAO.findEventByName(name));
		}
		model.addAttribute("events_list", list);
		return "list_events";
	}

	@RequestMapping("llist_events")
	public String llistEvents(Model model) {
		List<Event> eventsList = eventDAO.getAllEvents();
		for (Event even : eventsList) {
			allEvents.put(even.getId(), even);
		}
		model.addAttribute("events_llist", allEvents.values());
		return "llist_events";
	}

	@GetMapping(value = "/lview_event_details/{id}")
	public String lviewEvent(@PathVariable("id") String idEvent, Model model) {
		try {
		if (eventDAO.getEventById(idEvent) != null) {
			model.addAttribute("events_llist", eventDAO.getEventById(idEvent));
			model.addAttribute("levent_tickets", eventDAO.ticketsEvent(idEvent));
			return "/lview_event_details";
		} else {
			return "/lindex";
		}
		}catch (Exception e) {
			return "/lindex";
		}
	}

	@GetMapping("/lfiltered")
	public String llistFiltered(@RequestParam(defaultValue = "") String name, Model model) {
		List<Event> list = new ArrayList<Event>();
		Event foundEvent = eventDAO.findEventByName(name);
		if (foundEvent == null) {
			list.clear();
		} else {
			list.add(eventDAO.findEventByName(name));
		}
		model.addAttribute("events_llist", list);
		return "llist_events";
	}

	@GetMapping("/lview_event_details/buy/eventId/{eventId}/idt/{idt}")
	public String lviewGetEventBuyTicket(@PathVariable("eventId") String eventId, @PathVariable("idt") String ttype,
			Model model) {
		if (ticketDAO.getTId(ttype) != null) {
			model.addAttribute("buyTicket", ticketDAO.getTId(ttype));
			model.addAttribute("forEvent", allEvents.get(eventId));
			return "/buy_ticket";
		} else {
			return "/lindex";
		}
	}

	@RequestMapping("/buy_ticket")
	public String buyT(Model model) {
		return "buy_ticket";
	}

	@GetMapping("/lview_event_details/buy/q/{q}/idt/{idt}")
	public String lviewGetEventBuyTicket(@PathVariable("q") Double q, @PathVariable("idt") String idt,
			@ModelAttribute("buyTicket") Ticket ticket, Model model) {
		if (ticketDAO.getTId(idt) != null) {
			model.addAttribute("buyTicket", ticketDAO.getTId(idt));
			model.addAttribute("forEvent", allEvents.get(ticketDAO.getTId(idt).getId()));
			return "/buy_ticket";
		} else {
			return "/lindex";
		}

	}

	@PostMapping("/lview_event_details/buy/q/{q}/idt/{idt}")
	public String lviewEventBuyTicket(@PathVariable("q") Double q, @PathVariable("idt") String idt,
			@ModelAttribute("buyTicket") Ticket ticket, Model model) {
		Ticket ticket2 = ticketDAO.getTId(idt);
		if (ticket2.getQuantity() < ticket.getQuantity() || ticket.getQuantity() <= 0) {
			model.addAttribute("error", "Invalid quantity");
			model.addAttribute("buyTicket", ticket2);
			model.addAttribute("forEvent", allEvents.get(ticket2.getId()));
			return "/lindex";
		} else {
			model.addAttribute("error", "");
			orderDAO.createOrderItem(userDAO.getUserByUsername(userDAO.getActiveUser()),
					eventDAO.getEventById(ticket2.getId()), idt, ticket);
			return "my_tickets";
		}

	}

	@RequestMapping("/my_tickets")
	public String listTickets(Model model) {
		if (userDAO.myTickets(userDAO.getUserByUsername(userDAO.getActiveUser()).getId()).isEmpty()) {
			model.addAttribute("noTickets", "You have no tickets left to order.");
			return "my_tickets";
		} else {
			model.addAttribute("noTickets", "");
			model.addAttribute("tickets_list",
					userDAO.myTickets(userDAO.getUserByUsername(userDAO.getActiveUser()).getId()));
			return "my_tickets";
		}
	}

	@GetMapping("/delete_ticket/tick/{tick}")
	public String deleteTicket(@PathVariable("tick") String tick, Model model) {
		ticketDAO.deleteTicketFromUser(tick);
		model.addAttribute("tickets_list",
				userDAO.myTickets(userDAO.getUserByUsername(userDAO.getActiveUser()).getId()));
		return "/my_tickets";
	}

	@RequestMapping("/finish_order")
	public String createOrder(Model model) {
		orderDAO.createOrder(userDAO.getActiveUser());
		model.addAttribute("orders_list", userDAO.getMyOrders(userDAO.getActiveUser()));
		return "/my_orders";
	}

	@RequestMapping("/my_orders")
	public String myOrder(Model model) {
		model.addAttribute("orders_list", userDAO.getMyOrders(userDAO.getActiveUser()));
		return "/my_orders";
	}
}
