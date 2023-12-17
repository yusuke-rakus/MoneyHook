package com.example.form;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

public class SendInquiryForm extends form {

	@NotBlank(message = "{validating-message.inquiry-blank-error}")
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
