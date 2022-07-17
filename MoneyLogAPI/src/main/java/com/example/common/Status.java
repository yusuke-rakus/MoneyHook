package com.example.common;

/**
 * Status.SUCCESS.getStatus; -> "success"
 * 
 * @author YusukeMatsumoto
 *
 */
public enum Status {

	SUCCESS("success"), ERROR("error");

	private String status;

	private Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
