package com.example.domain;

public class ThemeColor {

	private Long themeColorId;
	private String themeColorCode;
	private String themeColorGradientCode;

	public Long getThemeColorId() {
		return themeColorId;
	}

	public void setThemeColorId(Long themeColorId) {
		this.themeColorId = themeColorId;
	}

	public String getThemeColorCode() {
		return themeColorCode;
	}

	public void setThemeColorCode(String themeColorCode) {
		this.themeColorCode = themeColorCode;
	}

	public String getThemeColorGradientCode() {
		return themeColorGradientCode;
	}

	public void setThemeColorGradientCode(String themeColorGradientCode) {
		this.themeColorGradientCode = themeColorGradientCode;
	}

	@Override
	public String toString() {
		return "ThemeColor [themeColorId=" + themeColorId + ", themeColorCode=" + themeColorCode
				+ ", themeColorGradientCode=" + themeColorGradientCode + "]";
	}

}
