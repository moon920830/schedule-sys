package com.ss.schedulesys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.schedulesys.domain.CompanyContact;

/**
 * Spring Data JPA repository for the Contact entity.
 */
public interface CompanyContactRepository extends JpaRepository<CompanyContact,Long> {
	
	public CompanyContact findByPhoneNumber(String phoneNumber);
}