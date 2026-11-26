package com.prevaj.evaluation.api.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPayload {

	private int id;
	private String email;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	private String avatar;
	private String name;
	private String job;
	private String createdAt;

	public UserPayload() {
	}

	public UserPayload(String name, String job) {
		this.name = name;
		this.job = job;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getName() {
		return name;
	}

	public String getJob() {
		return job;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
