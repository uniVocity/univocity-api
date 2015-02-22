package com.univocity.api.config.builders;

public interface CreateIndexExportOptions extends ExportOutput {
	
	public void restrictIndexNameLengthTo(int maxLength);
}
