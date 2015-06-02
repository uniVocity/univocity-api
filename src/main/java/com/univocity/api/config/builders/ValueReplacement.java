package com.univocity.api.config.builders;

import java.util.*;

import com.univocity.api.entity.*;


public interface ValueReplacement {
	
	public UnmatchedReference with(Map<?,?> valueMap);

	public UnmatchedReference with(String entityName, String keyField, String valueField);
	
	public UnmatchedReference with(String entityName, FieldIdentifier keyField, FieldIdentifier valueField);
}

