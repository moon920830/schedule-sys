package com.rj.sys.dao;

import org.springframework.stereotype.Repository;

import com.rj.sys.domain.Facility;

@Repository
public class FacilityDao extends GenericDao<Facility> {
	
	public FacilityDao() {
		setClazz(Facility.class);
	}
	
	public Facility findByName(String name){
		Facility facility = entityManager.createQuery(
				"from Facility f where f.name =:name and isDeleted = 0", Facility.class)
				.setParameter("name", name)
				.getSingleResult();
		return facility;
	}
	
	public Facility findByPhoneNumber(String phoneNumber){
		Facility facility = entityManager.createQuery(
				"from Facility f where f.phoneNumber =:phoneNumber and isDeleted = 0", Facility.class)
				.setParameter("phoneNumber", phoneNumber)
				.getSingleResult();
		return facility;
	}
	
	public Facility findOne(Long id){
		Facility facility = entityManager.createQuery(
				"from Facility f where f.id =:id and isDeleted = 0", Facility.class)
				.setParameter("id", id)
				.getSingleResult();
		return facility;
	}
	
	public Facility delete(Long id){
		Facility facility = findOne(id);
		facility.setIsDeleted(true);
		return facility;
	}
}