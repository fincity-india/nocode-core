package com.fincity.nocode.core.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UniqueID {

	public static final String fullId() {
		return UUID.randomUUID()
		        .toString();
	}

	public static final String shortId() {

		return Long.toString(ByteBuffer.wrap(UUID.randomUUID()
		        .toString()
		        .getBytes())
		        .getLong(), Character.MAX_RADIX);
	}

	private UniqueID() {

	}
}
