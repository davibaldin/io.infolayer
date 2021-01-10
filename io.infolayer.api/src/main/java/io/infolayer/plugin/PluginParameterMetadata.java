/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.infolayer.plugin;

import java.util.Objects;

public class PluginParameterMetadata implements Cloneable {

	private String name;
	private String value;
	private boolean required;
	private boolean credentials;
	
	public PluginParameterMetadata() { }
	
	public PluginParameterMetadata(String name, String value, boolean required, boolean credentials) {
		this.name = name;
		this.value = value;
		this.required = required;
		this.credentials = credentials;
	}
	
	public PluginParameterMetadata(String name, String value) {
		this(name, value, false, false);
	}

	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public void setCredentials(boolean credentials) {
		this.credentials = credentials;
	}
	
	public boolean isCredentials() {
		return credentials;
	}
	
	public boolean asBoolean() throws Exception {
		return Boolean.parseBoolean(value);
	}
	
	public Integer asInteger() throws Exception {
		return Integer.parseInt(value);
	}
	
	public Long asLong() throws Exception {
		return Long.parseLong(value);
	}
	
	@Override
	public PluginParameterMetadata clone() throws CloneNotSupportedException {
		return new PluginParameterMetadata(name, value, required, credentials);
	}

	@Override
	public int hashCode() {
		return Objects.hash(credentials, name, required, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PluginParameterMetadata other = (PluginParameterMetadata) obj;
		return credentials == other.credentials && Objects.equals(name, other.name) && required == other.required
				&& Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return String.format("PluginParameterMetadata [name=%s, value=%s, required=%s, credentials=%s]", name, value,
				required, credentials);
	}
	
}
