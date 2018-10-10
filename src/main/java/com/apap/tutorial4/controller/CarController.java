package com.apap.tutorial4.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	
	@RequestMapping(value = "/car/add/{dealerID}", method = RequestMethod.POST, params= {"save"})
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
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"addRow"})
	public String addRow(@ModelAttribute DealerModel dealer, BindingResult bindingResult, Model model) {
		if (dealer.getListCar() == null) {
            dealer.setListCar(new ArrayList<CarModel>());
        }
		dealer.getListCar().add(new CarModel());
		
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params={"removeRow"})
	public String removeRow(@ModelAttribute DealerModel dealer, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
	    final Integer row = Integer.valueOf(req.getParameter("removeRow"));
	    dealer.getListCar().remove(row.intValue());
	    
	    model.addAttribute("dealer", dealer);
	    return "addCar";
	}
	
}
