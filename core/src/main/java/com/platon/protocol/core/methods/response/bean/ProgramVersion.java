package com.platon.protocol.core.methods.response.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class ProgramVersion {

    /**
     * 代码版本
     */
	@JsonProperty("Version")
    private BigInteger version;
    /**
     * 代码版本签名
     */
	@JsonProperty("Sign")
    private String sign;

    public ProgramVersion() {
    }

    @JsonIgnore
	public BigInteger getVersion() {
		return version;
	}

	public BigInteger getProgramVersion() {
		return version;
	}

	@JsonIgnore
	public void setVersion(BigInteger version) {
		this.version = version;
	}

	@JsonIgnore
	public String getSign() {
		return sign;
	}

	public String getProgramVersionSign() {
		return sign;
	}
	@JsonIgnore
	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProgramVersion other = (ProgramVersion) obj;
		if (sign == null) {
			if (other.sign != null)
				return false;
		} else if (!sign.equals(other.sign))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProgramVersion [version=" + version + ", sign=" + sign + "]";
	}
}
