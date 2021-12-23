package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Event {
	private int eid;
	private String ename;
	private String edetail;
	private Date estartdate;
	private Date eenddate;
	private String eimage;
	private int eamount;
	private int elimit;
	private int eorder;
}
