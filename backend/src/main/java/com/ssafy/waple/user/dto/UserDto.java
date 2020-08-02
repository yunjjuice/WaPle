package com.ssafy.waple.user.dto;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

public class UserDto {
	@SerializedName("User_ID")
	@ApiModelProperty(value = "회원 아이디", example = "1412733569")
	private long userId;

	@ApiModelProperty(value = "최근 방문일", example = "2020-07-23")
	private Date lastDate;

	@SerializedName("name")
	@ApiModelProperty(value = "회원 닉네임", example = "바나나먹는몽키")
	private String name;

	@SerializedName("admin")
	@ApiModelProperty(value = "관리자 여부", example = "True")
	private boolean managerFlag;

	public UserDto() {
	}

	public UserDto(long userId, String name) {
		this.userId = userId;
		this.name = name;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isManagerFlag() {
		return managerFlag;
	}

	public void setManagerFlag(boolean managerFlag) {
		this.managerFlag = managerFlag;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[User_ID : ").append(this.userId).append(", User_Name : ").append(this.name).append(", Last_Date : "
		).append(this.lastDate).append(", IsManager : ").append(this.managerFlag ? "Yes" : "No").append(" ]");
		return sb.toString();
	}
}
