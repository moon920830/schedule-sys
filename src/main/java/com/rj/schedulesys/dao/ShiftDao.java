package com.rj.schedulesys.dao;

import org.springframework.stereotype.Repository;

import com.rj.schedulesys.domain.Shift;

@Repository
public class ShiftDao extends GenericDao<Shift> {
	
	public ShiftDao() {
		setClazz(Shift.class);
	}
	
	public Shift findByName(String shiftName){
		Shift shift = entityManager.createQuery(
				"from Shift s where s.shiftName =:shiftName", Shift.class)
				.setParameter("shiftName", shiftName)
				.getSingleResult();
		return shift;
	}
}
