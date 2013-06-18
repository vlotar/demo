package com.vadymlotar.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)  
	public ModelAndView show() {
		ModelAndView mv = new ModelAndView("/index");
		mv.addObject("message", "Recieved and logged one more request");
		return mv;
	}

}
