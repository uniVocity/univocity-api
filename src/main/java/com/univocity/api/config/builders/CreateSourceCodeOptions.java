package com.univocity.api.config.builders;

public interface CreateSourceCodeOptions {

	public EnumExportOutput enumerations(String packageName);

	public ClassExportOutput classes(String packageName);
}
