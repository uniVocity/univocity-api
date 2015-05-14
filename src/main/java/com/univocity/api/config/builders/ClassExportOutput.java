package com.univocity.api.config.builders;

public interface ClassExportOutput extends ExportOutput {

	/**
	 * Exports the result of this class {@link Export} operation as a {@code String}. Produces the same result as the {@link #toClasses()} method.
	 * @return a String containing the classes generated using the {@link Export#asSourceCode()} operation
	 */
	@Override
	public String toObject();

	public String toClasses();
}
