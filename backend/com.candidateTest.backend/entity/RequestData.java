package com.candidateTest.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "reqdata")
public class RequestData {

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
    private long timestamp;
    private String json;

    public RequestData() {}

    public RequestData(long timestamp, String json) {
        this.timestamp = timestamp;
        this.json = json;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "timestamp", nullable = false)
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Column(name = "json")
    public String getJson() {
        return json;
    }
    public void setJson(String json) {
        this.json = json;
    }
}