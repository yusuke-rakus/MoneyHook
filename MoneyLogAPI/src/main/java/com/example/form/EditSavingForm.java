package com.example.form;

import com.example.common.message.ValidatingMessage;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.Date;

public class EditSavingForm extends form {

	@NotNull(message = ValidatingMessage.ID_EMPTY_ERROR)
	private Long savingId;

	@NotEmpty(message = ValidatingMessage.SAVING_NAME_EMPTY_ERROR)
	@Length(max = 32, message = ValidatingMessage.SAVING_NAME_LENGTH_ERROR)
	private String savingName;

	@NotNull(message = ValidatingMessage.SAVING_AMOUNT_EMPTY_ERROR)
	@Max(value = 9999999, message = ValidatingMessage.SAVING_AMOUNT_RANGE_ERROR)
	private BigInteger savingAmount;

	@NotNull(message = ValidatingMessage.DATE_EMPTY_ERROR)
	private Date savingDate;

	private Long savingTargetId;

	public Long getSavingId() {
		return savingId;
	}

	public void setSavingId(Long savingId) {
		this.savingId = savingId;
	}

	public String getSavingName() {
		return savingName;
	}

	public void setSavingName(String savingName) {
		this.savingName = savingName;
	}

	public BigInteger getSavingAmount() {
		return savingAmount;
	}

	public void setSavingAmount(BigInteger savingAmount) {
		this.savingAmount = savingAmount;
	}

	public Date getSavingDate() {
		return savingDate;
	}

	public void setSavingDate(Date savingDate) {
		this.savingDate = savingDate;
	}

	public Long getSavingTargetId() {
		return savingTargetId;
	}

	public void setSavingTargetId(Long savingTargetId) {
		this.savingTargetId = savingTargetId;
	}

	@Override
	public String toString() {
		return "EditSavingForm [savingId=" + savingId + ", savingName=" + savingName + ", savingAmount=" + savingAmount
				+ ", savingDate=" + savingDate + ", savingTargetId=" + savingTargetId + "]";
	}
}