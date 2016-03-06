package com.rj.sys.view.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rj.sys.service.ShiftService;
import com.rj.sys.view.model.ShiftViewModel;

@Slf4j
@Controller
@RequestMapping("/shifts")
public class ShiftController {
	
	private @Autowired ShiftService shiftService;
	 
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<?> findAllShifts(){
		
		log.info("Finding all shifts");
		
		List<ShiftViewModel> shifts = shiftService.findAll();
		
		if(shifts.isEmpty()){
			return new ResponseEntity<String>("No shift found", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<ShiftViewModel>>(shifts, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{idOrName}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<?> findByIdOrName(@PathVariable String idOrName){
		ShiftViewModel viewModel = null;
		if(StringUtils.isNumeric(idOrName)){
			log.info("Finding shift with id : {}", idOrName);
			viewModel = shiftService.findbyId(Long.valueOf(idOrName));
		}else{
			log.info("Finding shift with name : {}", idOrName);
			viewModel = shiftService.findByName(idOrName);
		}
		
		if(viewModel == null){
			return new ResponseEntity<String>(
					"No shift found with either id or name : " + idOrName, HttpStatus.NOT_FOUND
					);
		}
		
		return new ResponseEntity<ShiftViewModel>(viewModel, HttpStatus.OK);
	}
}