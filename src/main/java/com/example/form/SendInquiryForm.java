package com.example.form;

import java.sql.Date;

import javax.validation.constraints.NotBlank;

import com.example.common.message.ErrorMessage;

public class SendInquiryForm extends form {

	@NotBlank(message = ErrorMessage.INQUIRY_BLANK_ERROR)
	private String inquiry;

	private Date inquiryDate;

	public String getInquiry() {
		return inquiry;
	}

	public void setInquiry(String inquiry) {
		this.inquiry = inquiry;
	}

	public Date getInquiryDate() {
		return inquiryDate;
	}

	public void setInquiryDate(Date inquiryDate) {
		this.inquiryDate = inquiryDate;
	}

}
