package com.rj.schedulesys.service;

import java.util.LinkedList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rj.schedulesys.dao.EmployeeDao;
import com.rj.schedulesys.dao.NurseDao;
import com.rj.schedulesys.dao.PositionDao;
import com.rj.schedulesys.domain.Employee;
import com.rj.schedulesys.domain.Nurse;
import com.rj.schedulesys.domain.Position;
import com.rj.schedulesys.service.util.PhoneNumberUtil;
import com.rj.schedulesys.util.ObjectValidator;
import com.rj.schedulesys.view.model.NurseViewModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NurseService {

	@Autowired
	private NurseDao nurseDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private PositionDao positionDao;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	@Autowired
	private ObjectValidator<NurseViewModel> validator;
	
	/**
	 * @param viewModel
	 * @return
	 */
	@Transactional
	public NurseViewModel create(NurseViewModel viewModel){
		Employee employee = employeeDao.findOne(viewModel.getId());
		log.info("Employee id : {}", viewModel.getId());
		Nurse nurse = Nurse.builder()
				.cpr(viewModel.getCpr())
				.employee(employee)
				.build();
		nurseDao.merge(nurse);
		return dozerMapper.map(nurse, NurseViewModel.class);
	}
	
	/**
	 * @param viewModel
	 * @return
	 */
	@Transactional
	public NurseViewModel update(NurseViewModel viewModel){
		
		Assert.notNull(viewModel, "No nurse provided");
		
		Assert.notNull(viewModel.getId(), "No nurse Id provided");
		
		validator.validate(viewModel);

		log.debug("Creating/Updating nurse : {}", viewModel);
		
		Nurse nurse = nurseDao.findOne(viewModel.getId());
		
		if(nurse == null){
			log.error("No nurse found with id : " + viewModel.getId());
			throw new RuntimeException("No nurse found with id : " + viewModel.getId());
		}
		
		nurse.setCpr(viewModel.getCpr());
		
		nurse = nurseDao.merge(nurse);
		
		return dozerMapper.map(nurse.getEmployee(), NurseViewModel.class);
	}
	
	/**
	 * @param id
	 */
	@Transactional
	public void delete(Long id){
		log.debug("Deleting nurse with id : {}", id);
		Nurse nurse = nurseDao.findOne(id);
		if(nurse == null){
			log.error("No nurse found with id : {}", id);
			throw new RuntimeException("No nurse found with id : " + id);
		}
		employeeDao.delete(nurse.getEmployee());
		log.debug("Nurse with id : {} successfully deleted");
	}
	
	/**
	 * @param id
	 * @return
	 */
	@Transactional
	public NurseViewModel findOne(Long id){
		
		log.debug("Fetching nurse with id : {}", id);
		
		Nurse nurse = nurseDao.findOne(id);
		NurseViewModel viewModel = null;
		
		if(nurse == null){
			log.warn("No nurse found with id : {}", id);
		}else{
			Employee employee = nurse.getEmployee();
			viewModel = dozerMapper.map(employee, NurseViewModel.class);
			viewModel.setCpr(nurse.getCpr());
			viewModel.setPhoneNumbers(PhoneNumberUtil.convert(
					employee.getPhoneNumbers(), dozerMapper));
		}
		
		return viewModel;
	}
	
	/**
	 * @return
	 */
	@Transactional
	public List<NurseViewModel> findAll(){
		
		log.debug("Fetching all nurses");
		
		List<Nurse> nurses = nurseDao.findAll();
		List<NurseViewModel> viewModels = new LinkedList<>();
		
		for(Nurse nurse : nurses){
			Employee employee = nurse.getEmployee();
			NurseViewModel viewModel = dozerMapper.map(employee, NurseViewModel.class);
			viewModel.setCpr(nurse.getCpr());
			viewModel.setPhoneNumbers(PhoneNumberUtil.convert(
					employee.getPhoneNumbers(), dozerMapper));
			viewModels.add(viewModel);
		}
		
		log.debug("Nurses : {}", viewModels);
		
		return viewModels;
	}
	
	/**
	 * @param positionName
	 * @return
	 */
	@Transactional
	public List<NurseViewModel> findAllByPosition(String positionName){
		
		if(positionDao.findByName(positionName) == null){
			log.error("No position found with name : {}", positionName);
			throw new RuntimeException("No position found with name : " + positionName);
		}
		
		log.debug("Fetching all nurses with position : {}", positionName);
		
		List<Nurse> nurses = nurseDao.findAllByPosition(positionName);
		
		List<NurseViewModel> viewModels = new LinkedList<>();
		
		for(Nurse nurse : nurses){
			Employee employee = nurse.getEmployee();
			NurseViewModel viewModel = dozerMapper.map(employee, NurseViewModel.class);
			viewModel.setPhoneNumbers(PhoneNumberUtil.convert(employee.getPhoneNumbers(), dozerMapper));
			viewModel.setCpr(nurse.getCpr());
			viewModels.add(viewModel);
		}
		
		log.debug("Nurses : {}", viewModels);
		
		return viewModels;
	}
	
	/**
	 * @param positionName
	 * @return
	 */
	public Position validatePosition(String positionName){
		
		Position position = positionDao.findByName(positionName);
		
		if(position == null){
			log.error("No position found with name : {}", positionName);
			throw new RuntimeException("No position found with name : " + positionName);
		}
		
		return position;
	}
	
}