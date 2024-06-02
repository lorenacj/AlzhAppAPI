package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Carer;
import com.example.demo.model.CarerModel;
import com.example.demo.service.CarerService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private static final String LOGIN_VIEW = "login";
	private static final String REGISTER_VIEW = "register";
	
	@Autowired
	@Qualifier("carerService")
	private CarerService carerService;
	
	@GetMapping("/login")
	public String login(Model model, @RequestParam(name = "error", required = false) String error, @RequestParam(name = "logout", required=false) String logout, HttpServletRequest request) {
		model.addAttribute("carer", new Carer());
		model.addAttribute("logout", logout);
		model.addAttribute("error", error);
		if (logout != null) {
			model.addAttribute("logoutMessage", "¡Has cerrado sesión correctamente!");
		}
		
		return LOGIN_VIEW;
	}
	
	
	@GetMapping("/registerForm")
	public String registerForm(Model model) {
		model.addAttribute("carer", new CarerModel());
		return REGISTER_VIEW;
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute CarerModel carer, RedirectAttributes flash) {
		carerService.register(carer);
		flash.addFlashAttribute("success", "Carer registrado correctamente!");
		return "redirect:/auth/login";
	}
}
