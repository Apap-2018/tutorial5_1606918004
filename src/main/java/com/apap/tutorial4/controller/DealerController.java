package com.apap.tutorial4.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial4.model.CarModel;
import com.apap.tutorial4.model.DealerModel;
import com.apap.tutorial4.service.CarService;
import com.apap.tutorial4.service.DealerService;

@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("dealer_list", dealerService.getAllDealer());
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}
	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
	private String viewDealer(@RequestParam(value = "dealerId") Long dealerId,
			Model model
			) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		
		List<CarModel> listCar = dealer.carListSorted();
		dealer.setListCar(listCar);
		model.addAttribute("dealer", dealer);
//		model.addAttribute("listCar", listCar);
		return "view-dealer";
	}
	
	@RequestMapping(value = "/dealer/delete/{dealerId}", method = RequestMethod.POST)
	private String deleteDealer(@PathVariable(value = "dealerId") Long dealerId) {
		dealerService.deleteDealer(dealerId);
		return "delete-dealer";
	}
	
	@RequestMapping("/dealer/viewall")
	public String viewall(Model model) {
		List<DealerModel> archive = dealerService.getAllDealer();
		
		model.addAttribute("listDealer", archive);
		return "viewall-dealer";
	}
	

}
