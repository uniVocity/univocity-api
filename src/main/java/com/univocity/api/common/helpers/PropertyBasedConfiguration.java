package com.univocity.api.common.helpers;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.univocity.api.common.*;

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

			if (values.containsKey(key)) {
				var = values.get(key);
			} else {
				if ("user.home".equals(key)) {
					var = normalizeDirPath(System.getProperty("user.home"));
				} else {
					var = System.getProperty(key);
				}
			}

			if (var == null && parentProperty != null) {
				String parent = parentProperty;
				
				while (var == null) {
					var = values.get(parent + "." + key);
					if (var != null) {
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

			if (var == null) {
				throw new IllegalStateException("Invalid configuration! No value defined for ${" + key + "} in " + originalValue);
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

	protected String normalizeDirPath(String dirPath) {
		dirPath = dirPath.replaceAll("\\\\", "/");
		if (!dirPath.endsWith("/")) {
			dirPath = dirPath + "/";
		}
		return dirPath;
	}

	protected File getDirectory(String property, boolean validateRead, boolean validateWrite, boolean create) {
		String dirPath = getProperty(property);
		dirPath = normalizeDirPath(dirPath);

		File dir = new File(dirPath);
		if (create && (!dir.exists() || !dir.isDirectory())) {
			dir.mkdirs();
		}

		if ((validateRead && !dir.exists()) || (validateWrite && !dir.canWrite())) {
			throw new IllegalStateException("Unable to access directory '" + dir.getAbsolutePath() + "'");
		}

		return dir;

	}
}
