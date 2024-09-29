package com.coder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.coder.entity.User;
import com.coder.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/addNotes")
	public String addNotes() {
		return "add_notes";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session) {

		boolean f = userService.existEmailCheck(user.getEmail());

		if (f) {
			session.setAttribute("msg", "Email already exist");
		} else {
			User saveUser = userService.saveUser(user);
			if (saveUser != null) {
				session.setAttribute("msg", "Register success");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}
		}

		return "redirect:/register";
	}
	
	@GetMapping("/editNotes")
	public String editNotes() {
		return "edit_notes";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/viewNotes")
	public String viewNotes() {
		return "view_notes";
	}
}
