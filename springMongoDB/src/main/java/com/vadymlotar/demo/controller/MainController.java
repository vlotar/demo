package com.vadymlotar.demo.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)  
	public ModelAndView show() throws InterruptedException {
		ModelAndView mv = new ModelAndView("/index");
		mv.addObject("message", "Recieved and logged one more request");
		
		//do some work
		Random random = new Random();
		int randomDelay = random.nextInt(200);
		Thread.sleep(randomDelay);
		
		return mv;
	}

}
