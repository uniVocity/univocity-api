package com.univocity.api.config.builders;

import com.univocity.api.entity.*;

public interface ConditionalCopy {

	public CopyToMultipleFields copy(String... sourceFields);

	public CopyToMultipleFields copy(FieldIdentifier... sourceFields);

}
