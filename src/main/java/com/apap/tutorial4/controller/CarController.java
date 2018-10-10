package com.apap.tutorial4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial4.model.CarModel;
import com.apap.tutorial4.model.DealerModel;
import com.apap.tutorial4.service.CarService;
import com.apap.tutorial4.service.DealerService;

@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerID}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerID") Long dealerID, Model model) {
		CarModel car = new CarModel();
		DealerModel dealer = dealerService.getDealerDetailById(dealerID).get();
		car.setDealer(dealer);
		dealer.getListCar().add(car);
		model.addAttribute("dealer", dealer);
		model.addAttribute("car", car);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerID}", method = RequestMethod.POST)
	private String addCarSubmit(
			@ModelAttribute CarModel car, @PathVariable(value = "dealerID") Long dealerID, Model model) {
		carService.addCar(car);
		model.addAttribute("dealerId", dealerID);
		return "redirect:/dealer/view?dealerId=" + Long.toString(dealerID);
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String delete(@ModelAttribute DealerModel dealer, Model model) {
		for (CarModel car : dealer.getListCar()) {
			carService.deleteCar(car.getId());
		}
		
		return "delete";
		}
	
	
	
}
