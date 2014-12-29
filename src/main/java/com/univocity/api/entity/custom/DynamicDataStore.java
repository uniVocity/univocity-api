package com.univocity.api.entity.custom;

import java.util.*;

import com.univocity.api.entity.*;

public abstract class DynamicDataStore<E extends CustomReadableEntity> implements CustomDataStore<E> {
	
	public abstract <C extends Collection<T>, T extends DefaultEntityField> void createDataEntity(String entityName, C fields);
	
}
