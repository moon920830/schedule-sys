INSERT INTO SCHEDULE(ID ,FACILITY_ID, SHIFT_ID, SCHEDULE_DATE, STATUS_ID, POST_STATUS_ID, TIMESHEET_RECEIVED, SCHEDULE_COMMENT, CREATE_DATE, IS_DELETED, USER_ID, ASSIGNEE_ID)
	VALUES(1, 1, 1, current_date(), 1, 1, false, 'Comment on the schedule', current_date(), false, 2, 1);