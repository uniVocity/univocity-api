package com.univocity.api.config.builders;

public interface CreateIndexExportOptions extends ExportOutput {

	public CreateIndexExportOptions restrictIndexNameLengthTo(int maxLength);

}
