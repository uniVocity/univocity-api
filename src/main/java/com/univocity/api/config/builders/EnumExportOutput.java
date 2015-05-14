package com.univocity.api.config.builders;

public interface EnumExportOutput extends ExportOutput {

	/**
	 * Exports the result of this enumeration {@link Export} operation as a {@code String}. Produces the same result as the {@link #toEnums()} method.
	 * @return a String containing the enumerations generated using the {@link Export#asSourceCode()} operation
	 */
	@Override
	public String toObject();

	public String toEnums();
}
