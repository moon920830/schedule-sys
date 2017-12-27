package com.ss.schedulesys.domain;
// Generated Feb 11, 2017 7:27:59 PM by Hibernate Tools 5.0.0.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Schedule generated by hbm2java
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"scheduleUpdates"})
@Table(name = "schedule", catalog = "schedulesys_db")
public class Schedule implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3793343711156136376L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "care_company_id", nullable = false, foreignKey = @ForeignKey(name = "fk_schedule_carecompany"))
	private CareCompany careCompany;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "fk_schedule_employee"))
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_status_id", nullable = false, foreignKey = @ForeignKey(name = "fk_schedule_poststatus"))
	private SchedulePostStatus schedulePostStatus;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_id", nullable = false, foreignKey = @ForeignKey(name = "fk_schedule_status"))
	private ScheduleStatus scheduleStatus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_schedule_user"))
	private ScheduleSysUser scheduleSysUser;
	
	@NotNull
	@Column(name = "shift_start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date shiftStartTime;
	
	@NotNull
	@Column(name = "shift_end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date shiftEndTime;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "schedule_date", nullable = false, length = 10)
	private Date scheduleDate;
	
	@Column(name = "hours_worked", precision = 22, scale = 0)
	private Double hoursWorked;
	
	@Column(name = "overtime", precision = 22, scale = 0)
	private Double overtime;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", nullable = false, length = 10)
	private Date createDate;
	
	@Column(name = "time_sheet_received")
	private Boolean timeSheetReceived;
	
	@Column(name = "comment", length = 200)
	private String comment;
	
	@Column(name = "paid")
	private Boolean paid;
	
	@Column(name = "billed")
	private Boolean billed;
	
	@Column(name = "archived")
	private Boolean archived;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule", orphanRemoval = true)
	private Set<ScheduleUpdate> scheduleUpdates;
	
	@PrePersist
	public void onCreate(){
		createDate(new Date())
			.billed(false)
			.paid(false)
			.timeSheetReceived(false)
			.archived(false)
			.hoursWorked(0d)
			.overtime(0d);
	}
	
	public Schedule careCompany(CareCompany careCompany){
		this.careCompany = careCompany;
		return this;
	}
	
	public Schedule employee(Employee employee){
		this.employee = employee;
		return this;
	}
	
	public Schedule createDate(Date createDate){
		this.createDate = createDate;
		return this;
	}
	
	public Schedule postStatus(SchedulePostStatus schedulePostStatus){
		this.schedulePostStatus = schedulePostStatus;
		return this;
	}
	
	public Schedule status(ScheduleStatus scheduleStatus){
		this.scheduleStatus = scheduleStatus;
		return this;
	}
	
	public Schedule scheduleSysUser(ScheduleSysUser scheduleSysUser){
		this.scheduleSysUser = scheduleSysUser;
		return this;
	}
	
	public Schedule shiftStartTime(Date shiftTime){
		this.shiftStartTime = shiftTime;
		return this;
	}
	
	public Schedule shiftEndTime(Date shiftTime){
		this.shiftEndTime = shiftTime;
		return this;
	}
	
	public Schedule scheduleDate(Date scheduleDate){
		this.scheduleDate = scheduleDate;
		return this;
	}
	
	public Schedule hoursWorked(Double hoursWorked){
		this.hoursWorked = hoursWorked;
		return this;
	}
	
	public Schedule overtime(Double overtime){
		this.overtime = overtime;
		return this;
	}
	
	public Schedule timeSheetReceived(Boolean timeSheetReceived){
		this.timeSheetReceived = timeSheetReceived;
		return this;
	}
	
	public Schedule paid(Boolean paid){
		this.paid = paid;
		return this;
	}
	
	public Schedule billed(Boolean billed){
		this.billed = billed;
		return this;
	}
	
	public Schedule archived(Boolean archived){
		this.archived = archived;
		return this;
	}
	
	public Schedule comment(String comment){
		this.comment = comment;
		return this;
	}

	public boolean equals(Object obj){
		if ((this == obj))
			return true;
		if ((obj == null))
			return false;
		if (!(obj instanceof Schedule))
			return false;
		Schedule schedule = ((Schedule)obj);
		
		return (this.careCompany.getId() == schedule.getCareCompany().getId() && this.employee.getId() == schedule.getEmployee().getId()
				&& this.scheduleDate.compareTo(schedule.getScheduleDate()) == 0 && this.shiftStartTime.equals(schedule.getShiftStartTime())
				&& this.shiftEndTime.equals(schedule.getShiftEndTime()));
	}
	
	public int hashCode(){
		return 0;
	}
}
