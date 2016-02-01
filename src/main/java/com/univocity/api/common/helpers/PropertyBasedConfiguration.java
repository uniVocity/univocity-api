package com.univocity.api.common.helpers;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.univocity.api.common.*;
import com.univocity.api.exception.*;

public abstract class PropertyBasedConfiguration {

	protected final Properties properties;
	private final Map<String, String> values = new LinkedHashMap<String, String>();

	protected PropertyBasedConfiguration(InputStream inputProperties) {

		properties = new OrderedProperties();
		try {
			properties.load(inputProperties);
		} catch (Exception e) {
			throw new IllegalStateException("Error loading configuration from properties " + getPropertiesDescription(), e);
		}

		Enumeration<?> keys = properties.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();

			String parent = null;
			int lastDot = key.lastIndexOf('.');
			if (lastDot > 0) {
				parent = key.substring(0, lastDot);
			}

			values.put(key, replaceProperty(properties.getProperty(key), parent));
		}
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder(getPropertiesDescription());
		for (Entry<String, String> e : values.entrySet()) {
			out.append('\n');
			out.append('\t');
			out.append(e.getKey());
			out.append('=');
			out.append(e.getValue());
		}
		return out.toString();
	}

	protected abstract String getPropertiesDescription();

	protected String replaceVariables(String s, String variable, String value) {
		variable = "${" + variable + "}";
		StringBuilder out = new StringBuilder();

		int start = 0;
		int end;
		while ((end = s.indexOf(variable, start)) != -1) {
			out.append(s.substring(start, end)).append(value);
			start = end + variable.length();
		}
		out.append(s.substring(start));
		return out.toString();
	}

	protected List<String> listVariables(String s) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		while (i < s.length() - 1) {
			int start = s.indexOf("${", i);
			if (start < 0) {
				break;
			}
			start += 2;
			int end = s.indexOf("}", start);
			if (end < 0) {
				break;
			}
			list.add(s.substring(start, end));
			i = end + 1;
		}
		return list;
	}

	protected void setSystemProperty(String property) {
		String value = System.getProperty(property);
		if (Args.isBlank(value)) {
			value = getProperty(property);
			if (Args.isNotBlank(value)) {
				System.setProperty(property, value);
			}
		}
	}

	private String replaceProperty(String value, String parentProperty) {
		if (Args.isBlank(value)) {
			return null;
		}
		String originalValue = value;

		for (String key : listVariables(value)) {
			String var;
			boolean found = false;

			if (values.containsKey(key)) {
				var = values.get(key);
				found = true;
			} else {
				if ("user.home".equals(key)) {
					var = normalizeFilePath(System.getProperty("user.home"));
					found = true;
				} else {
					var = System.getProperty(key);
				}
			}

			if (var == null && parentProperty != null) {
				String parent = parentProperty;

				while (var == null) {
					found = values.containsKey(parent + "." + key);
					if (found) {
						var = values.get(parent + "." + key);
						break;
					}
					int dot = parent.lastIndexOf('.');
					if (dot > 0) {
						parent = parent.substring(0, dot);
					} else {
						break;
					}
				}
			}

			if (var == null && !found) {
				throw new IllegalConfigurationException("Invalid configuration! No value defined for ${" + key + "} in " + originalValue);
			}
			value = replaceVariables(value, key, var);
		}
		return value;
	}

	public String getProperty(String property, String defaultValue) {
		if (!values.containsKey(property)) {
			return defaultValue;
		}

		return values.get(property);
	}

	public String getProperty(String property) {
		if (!values.containsKey(property)) {
			throw new IllegalStateException("Invalid configuration in " + getPropertiesDescription() + "! Property '" + property + "' could not be found.");
		}

		return values.get(property);
	}

	public String getProperty(String property, String... keyValuePairs) {
		String previous = getProperty(property);

		String result = previous;
		if (previous != null && keyValuePairs.length > 0) {
			do {
				previous = result;
				for (int i = 0; i < keyValuePairs.length; i += 2) {
					String key = keyValuePairs[i];
					String value = keyValuePairs[i + 1];
					result = result.replace("!{" + key + "}", value);
				}

			} while (!result.equals(previous));
		}

		return result;
	}

	protected String normalizeFilePath(String filePath) {
		if (filePath == null) {
			throw new IllegalConfigurationException("File path undefined");
		}
		filePath = filePath.replaceAll("\\\\", "/");
		if (!filePath.endsWith("/")) {
			filePath = filePath + "/";
		}
		return filePath;
	}

	private File getValidatedPath(String property, File defaultFile, boolean isDirectory, boolean mandatory, boolean validateRead, boolean validateWrite, boolean create, String... keyValuePairs) {
		String path = getProperty(property, keyValuePairs);
		String description = isDirectory ? "Directory" : "File";

		if (path == null) {
			if (mandatory) {
				throw new IllegalConfigurationException(description + " path undefined. Property '" + property + "' must be set with a valid path.");
			} else {
				return defaultFile;
			}
		}
		path = normalizeFilePath(path);

		File file = new File(path);

		String baseErrorMessage = ". Path defined by property '" + property + "' is: " + path;

		if (create && !file.exists()) {
			boolean created = false;
			if (isDirectory) {
				created = file.mkdirs();
			} else {
				try {
					created = file.createNewFile();
				} catch (IOException e) {
					throw new IllegalConfigurationException("Cannot create " + description + baseErrorMessage, e);
				}
			}
			if (!created) {
				throw new IllegalConfigurationException("Cannot create " + description + baseErrorMessage);
			}
		}

		if ((validateRead || validateWrite)) {
			if (!file.exists()) {
				throw new IllegalConfigurationException(description + " does not exist" + baseErrorMessage);
			}
			if(validateRead && !file.canRead()){
				throw new IllegalConfigurationException(description + " can't be read" + baseErrorMessage);
			}

			if(validateWrite && !file.canWrite()){
				throw new IllegalConfigurationException(description + " is not writable" + baseErrorMessage);
			}
		}

		return file;
	}

	protected File getDirectory(String property, boolean validateRead, boolean validateWrite, boolean create, String... keyValuePairs) {
		return getDirectory(property, true, validateWrite, validateWrite, create, keyValuePairs);
	}

	public File getDirectory(String property, boolean mandatory, boolean validateRead, boolean validateWrite, boolean create, String... keyValuePairs) {
		return getValidatedPath(property, null, true, mandatory, validateRead, validateWrite, create, keyValuePairs);
	}

	public File getDirectory(String property, File defaultDir, boolean validateRead, boolean validateWrite, String... keyValuePairs) {
		return getValidatedPath(property, defaultDir, true, false, validateRead, validateWrite, false, keyValuePairs);
	}

	public File getFile(String property, boolean mandatory, boolean validateRead, boolean validateWrite, boolean create, String... keyValuePairs) {
		return getValidatedPath(property, null, false, mandatory, validateRead, validateWrite, create, keyValuePairs);
	}

	public File getFile(String property, File defaultFile, boolean validateRead, boolean validateWrite, String... keyValuePairs) {
		return getValidatedPath(property, defaultFile, false, false, validateRead, validateWrite, false, keyValuePairs);
	}

}
