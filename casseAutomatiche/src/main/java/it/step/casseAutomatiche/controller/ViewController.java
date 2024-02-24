package it.step.casseAutomatiche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	
	@GetMapping(value="/home")
	public String home(){
	        return "home"; 
	}

}
