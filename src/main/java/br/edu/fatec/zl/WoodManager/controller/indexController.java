package br.edu.fatec.zl.WoodManager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import br.edu.fatec.zl.WoodManager.persistence.GenericDao;
import jakarta.servlet.http.HttpSession;

@Controller
public class indexController {

	@Autowired
	GenericDao gDao;

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
	public ModelAndView indexGet(@RequestParam Map<String, String> allRequestParam, ModelMap model,
			HttpSession session) {

		return new ModelAndView("index");
	}

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.POST)
	public ModelAndView indexPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		return new ModelAndView("index");
	}

}
