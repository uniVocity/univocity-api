package com.univocity.api.config.builders;

import java.util.*;


public interface ValueReplacement {
	
	public UnmatchedReference with(Map<?,?> valueMap);

	public UnmatchedReference with(String entityName, String keyField, String valueField);
}

