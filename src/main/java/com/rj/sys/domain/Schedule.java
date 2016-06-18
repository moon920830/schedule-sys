package com.rj.sys.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * The persistent class for the SCHEDULE database table.
 * 
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="SCHEDULE")
public class Schedule implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE", nullable=false)
	private Date createDate;

	@Column(name="HOURS")
	private int hours;

	@Column(name="OVERTIME")
	private int overtime;

	@Column(name="POST_STATUS_ID")
	private int postStatusId;

	@Column(name="SCHEDULE_COMMENT", length=254)
	private String scheduleComment;

	@Temporal(TemporalType.DATE)
	@Column(name="SCHEDULE_DATE", nullable=false)
	private Date scheduleDate;

	@Column(name="STATUS_ID", nullable=false)
	private int statusId;

	@Column(name="TIMESHEET_RECEIVED")
	private byte timesheetReceived;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID")
	private Employee employee;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FACILITY_ID", nullable=false)
	private Facility facility;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SHIFT_ID", nullable=false)
	private Shift shift;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
	private ScheduleSysUser scheduleSysUser;

}