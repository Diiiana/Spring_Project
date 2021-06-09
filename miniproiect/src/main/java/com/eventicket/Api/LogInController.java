package com.eventicket.Api;

import com.eventicket.DAO.*;

import java.util.*;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eventicket.Model.Event;
import com.eventicket.Model.User;
import org.springframework.validation.*;

@Controller
public class LogInController {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private EventDAO eventDAO;

	@RequestMapping(value = "log_in", method = RequestMethod.GET)
	public String getUser() {
		return "log_in";
	}

	@RequestMapping(value = "/log_in", method = RequestMethod.POST)
	public String login(@ModelAttribute(name = "user") User user, Model model) {
		if (userDAO.logIn(user)) {
			model.addAttribute("loggedUser", user);
			userDAO.setInctive();
			userDAO.setActive(user);
			model.addAttribute("error_login", "");
			return "/lindex";
		} else {
			model.addAttribute("error_login", "Invalid username or password.");
			return "/log_in";
		}
	}

	@RequestMapping("/lindex")
	public String llogIn(Model model) {
		List<Event> events = eventDAO.getAllEvents();
		model.addAttribute("events_list", events);
		return "lindex";
	}

	@RequestMapping(value = "create_account", method = RequestMethod.GET)
	public String createAccount(Model model) {
		model.addAttribute("user", new User());
		return "create_account";
	}

	@RequestMapping(value = "/create_account", method = RequestMethod.POST)
	public String createAccount(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "create_account";
		}
		UUID iUuid = UUID.randomUUID();
		if (userDAO.insertUser(iUuid.toString(), user)) {
			model.addAttribute("c_error", "Invalid data. Username is not unique.");
			return "/index";
		} else {
			model.addAttribute("c_error", "Invalid data. Username is not unique.");
			return "create_account";
		}
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String adminLogIn() {
		return "admin";
	}

	@RequestMapping(value = "/admin_page", method = RequestMethod.POST)
	public String adminData(@ModelAttribute(name = "user") User user, Model model) {

		if (user.getUsername().equals("admin") && user.getPassword().equals("admin")) {
			model.addAttribute("error_admin", "");
			return "admin_page";
		} else {
			model.addAttribute("error_admin", "Wrong name or password.");
			return "admin";
		}

	}

	@RequestMapping(value = "/admin_page", method = RequestMethod.GET)
	public String adminData() {
		return "admin_page";
	}

	@RequestMapping(value = "/log_out")
	public String logOut(Model model) {
		userDAO.setInctive();
		return "/index";
	}
}
