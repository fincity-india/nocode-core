package com.fincity.nocode.core.db.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class FilterRequest implements Serializable {

	private static final long serialVersionUID = -7600941152076736565L;

	private ConditionRequest condition;
	private int page = 0;
	private int size = 10;
	private List<SortRequest> sort;
}
