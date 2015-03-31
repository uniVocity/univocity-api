package com.univocity.api.entity;

import java.util.*;

public abstract class EntityRelationship {

	public abstract Entity getEntity();

	public abstract List<DefaultEntityField> getFields();

	public abstract Entity getReferredEntity();

	public abstract List<DefaultEntityField> getReferredFields();
}
