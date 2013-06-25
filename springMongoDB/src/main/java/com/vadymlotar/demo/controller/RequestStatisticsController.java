package com.vadymlotar.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vadymlotar.demo.controller.model.RequestStatistics;
import com.vadymlotar.demo.domain.service.LogItemService;

@Controller
public class RequestStatisticsController {
	@Autowired
	private LogItemService responseItemService;
	
	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public ModelAndView showStatistics() {
		ModelAndView mv = new ModelAndView("/statistics");
		mv.addObject("model", fillStatistics());
		return mv;
	}

	private RequestStatistics fillStatistics() {
		return responseItemService.getRequestStatistics();
	}

}
