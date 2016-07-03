package com.rj.schedulesys.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The persistent class for the NURSE database table.
 * 
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="NURSE")
@ToString(exclude = {"nurseTests", "licences"})
public class Nurse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//In attribute 'id', a column is specified, but it is mapped by a relationship. IDs that are mapped by a relationship should not specify a column.
	//@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "CPR")
	private Boolean cpr;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID", nullable = false, insertable = false, updatable = false)
	private Employee employee;

	@OneToMany(mappedBy = "nurse", orphanRemoval = true)
	private List<NurseTest> nurseTests;
	
	@OneToMany(mappedBy = "nurse", orphanRemoval = true)
	private List<License> licences;

	public NurseTest addNurseTest(NurseTest nurseTest) {
		getNurseTests().add(nurseTest);
		nurseTest.setNurse(this);
		return nurseTest;
	}

	public NurseTest removeNurseTest(NurseTest nurseTest) {
		getNurseTests().remove(nurseTest);
		nurseTest.setNurse(null);
		return nurseTest;
	}
	
	public License addLicense(License license){
		getLicences().add(license);
		license.setNurse(this);
		return license;
	}
	
	public License removeLicense(License license){
		getLicences().remove(license);
		license.setNurse(null);
		return license;
	}

}