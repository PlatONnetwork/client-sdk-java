package com.alaya.protocol.core.methods.response;

import java.util.List;

public class WasmAbiDefinition {
	private String name;
	private String type;

	private boolean constant;
	private List<NamedType> input;
	private String output;

	private int topic;

	private List<String> baseclass;
	private List<NamedType> fields;

	public WasmAbiDefinition() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isConstant() {
		return constant;
	}

	public void setConstant(boolean constant) {
		this.constant = constant;
	}

	public List<NamedType> getInput() {
		return input;
	}

	public void setInput(List<NamedType> input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public int getTopic() {
		return topic;
	}

	public void setTopic(int topic) {
		this.topic = topic;
	}

	public List<String> getBaseclass() {
		return baseclass;
	}

	public void setBaseclass(List<String> baseclass) {
		this.baseclass = baseclass;
	}

	public List<NamedType> getFields() {
		return fields;
	}

	public void setFields(List<NamedType> fields) {
		this.fields = fields;
	}

	public boolean hasOutputs() {
		return output != null && !output.equals("") && !output.equals("void");
	}

	public boolean isPayable() {
		return false;
	}

	public static class NamedType {
		private String name;
		private String type;
		private boolean indexed;

		public NamedType() {
		}

		public NamedType(String name, String type) {
			this.name = name;
			this.type = type;
		}

		public NamedType(String name, String type, boolean indexed) {
			this.name = name;
			this.type = type;
			this.indexed = indexed;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isIndexed() {
			return indexed;
		}

		public void setIndexed(boolean indexed) {
			this.indexed = indexed;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof NamedType)) {
				return false;
			}

			NamedType namedType = (NamedType) o;

			if (isIndexed() != namedType.isIndexed()) {
				return false;
			}

			if (getName() != null ? !getName().equals(namedType.getName()) : namedType.getName() != null) {
				return false;
			}
			return getType() != null ? getType().equals(namedType.getType()) : namedType.getType() == null;
		}

		@Override
		public int hashCode() {
			int result = getName() != null ? getName().hashCode() : 0;
			result = 31 * result + (getType() != null ? getType().hashCode() : 0);
			result = 31 * result + (isIndexed() ? 1 : 0);
			return result;
		}
	}
}
