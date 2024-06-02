package com.example.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

	private static final String HOME_VIEW = "home";
	private static final String CRUD_VIEW = "crudcarer";
	

	@GetMapping("/")
	public String redirect() {
		return "redirect:/home/index";
	}

	@GetMapping("home/index")
	public ModelAndView index() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			userName = userDetails.getUsername();
		} else if (principal instanceof String) {
			userName = (String) principal;
		} else {
			userName = "Nombre de usuario desconocido";
		}
		ModelAndView mav = new ModelAndView(HOME_VIEW);
		mav.addObject("usuario", userName);
		return mav;
	}

	@GetMapping("home/index/crud")
	public ModelAndView Crudindex() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			userName = userDetails.getUsername();
		} else if (principal instanceof String) {
			userName = (String) principal;
		} else {
			userName = "Nombre de usuario desconocido";
		}
		ModelAndView mav = new ModelAndView(CRUD_VIEW);
		mav.addObject("usuario", userName);
		return mav;
	}
}
