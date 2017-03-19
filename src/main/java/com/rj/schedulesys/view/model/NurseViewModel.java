package com.rj.schedulesys.view.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rj.schedulesys.util.JsonDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
public class NurseViewModel {
	
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 50)
	private String firstName;
	
	@NotBlank
	@Size(min = 3, max = 50)
	private String lastName;
	
	@NotBlank
	private String positionName;
	
	private Boolean ebc;
	
	private Boolean insvc;
	
	private Boolean active;
	
	private Boolean hasExpiredLicense;
	
	@NotNull
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date dateOfHire;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date rehireDate;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date lastDayOfWork;
	
	private String comment;
	
	private List<PhoneNumberViewModel> phoneNumbers;
	
}