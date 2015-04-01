package com.univocity.api.entity;

import java.util.*;

public abstract class EntityRelationship {

	private String myStringRepresentation = null;

	public abstract Entity getEntity();

	public abstract List<DefaultEntityField> getFields();

	public abstract Entity getReferredEntity();

	public abstract List<DefaultEntityField> getReferredFields();

	public String toString() {
		if (myStringRepresentation == null) {
			myStringRepresentation = printEntityAndFields(getEntity(), getFields()) + "->" + printEntityAndFields(getReferredEntity(), getReferredFields());
		}
		return myStringRepresentation;
	}

	private String printEntityAndFields(Entity entity, List<DefaultEntityField> fields) {
		StringBuilder tmp = new StringBuilder();

		for (DefaultEntityField field : fields) {
			if (tmp.length() > 0) {
				tmp.append(',');
			}
			tmp.append(field.getName());
		}
		tmp.insert(0, '[');
		tmp.insert(0, entity.getEntityName());
		tmp.append(']');

		return tmp.toString();
	}
}
