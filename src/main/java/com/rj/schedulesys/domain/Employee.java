package com.rj.schedulesys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The persistent class for the EMPLOYEE database table.
 * @author ezerbo
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="EMPLOYEE")
@ToString(exclude = {"phoneNumbers", "employeeTests","position", "nurse", "careGiver"})
@EqualsAndHashCode(exclude = {"careGiver", "nurse"})
public class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "`COMMENT`", length = 254)
	private String comment;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_HIRE", nullable = false)
	private Date dateOfHire;

	@Column(name = "FIRST_NAME", nullable = false, length = 254)
	private String firstName;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_DAY_OF_WORK")
	private Date lastDayOfWork;	

	@Column(name = "LAST_NAME", nullable = false, length = 254)
	private String lastName;

	@Temporal(TemporalType.DATE)
	@Column(name = "REHIRE_DATE")
	private Date rehireDate;
	
	@Column(name = "EBC")
	private Boolean ebc;
	
	@Column(name = "INSVC")
	private Boolean insvc;
	
	@Column(name = "ACTIVE")
	private Boolean active;

	@OneToOne(mappedBy = "employee", fetch=FetchType.LAZY, orphanRemoval = true)
	private CareGiver careGiver;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "POSITION_ID", nullable = false)
	private Position position;

	@OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = true)
	private Nurse nurse;

	@OneToMany(mappedBy = "employee", orphanRemoval = true)
	private List<PhoneNumber> phoneNumbers;

	@OneToMany(mappedBy = "employee", orphanRemoval = true)
	private List<EmployeeTest> employeeTests;
	
	@PrePersist
	public void onCreate(){
		setInsvc(true);
		setActive(true);
		
	}

	

}