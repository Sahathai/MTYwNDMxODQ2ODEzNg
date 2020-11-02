package com.candidateTest.backend.dto;

public class DTOReqSaveData implements java.io.Serializable {
	private static final long serialVersionUID = 5098770179973313173L;

	private long timestamp;
	private String json;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
