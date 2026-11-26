package com.prevaj.evaluation.api.payloads;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListResponse {

	private int page;

	@JsonProperty("per_page")
	private int perPage;

	private int total;

	@JsonProperty("total_pages")
	private int totalPages;

	private List<UserPayload> data;

	public int getPage() {
		return page;
	}

	public int getPerPage() {
		return perPage;
	}

	public int getTotal() {
		return total;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<UserPayload> getData() {
		return data;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setData(List<UserPayload> data) {
		this.data = data;
	}
}
