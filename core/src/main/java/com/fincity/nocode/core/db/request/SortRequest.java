package com.fincity.nocode.core.db.request;

import java.io.Serializable;

import org.springframework.data.domain.Sort.Direction;

import lombok.Data;

@Data
public class SortRequest implements Serializable {

	private static final long serialVersionUID = 9132257283770146316L;

	private String field;
	private Direction direction;
}
