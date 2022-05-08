package com.fincity.nocode.core.db.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ConditionRequest implements Serializable{

	private static final long serialVersionUID = 5046197291639107728L;
	
	private String type;
	private List<ConditionRequest> parts;
	private String fieldName;
	private String value;
}
