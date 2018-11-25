package cn.springmvc.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Memorandum implements Serializable {

	private static final long serialVersionUID = -4983048317514430170L;

	private String memorandumId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date memorandumDate;

	private String memorandumComplete;

	private String memorandumTitle;

	private String memorandumContent;

	private String createUser;

	private Date createTime;

	public String getMemorandumId() {
		return memorandumId;
	}

	public void setMemorandumId(String memorandumId) {
		this.memorandumId = memorandumId;
	}

	public Date getMemorandumDate() {
		return memorandumDate;
	}

	public void setMemorandumDate(Date memorandumDate) {
		this.memorandumDate = memorandumDate;
	}

	public String getMemorandumComplete() {
		return memorandumComplete;
	}

	public void setMemorandumComplete(String memorandumComplete) {
		this.memorandumComplete = memorandumComplete;
	}

	public String getMemorandumTitle() {
		return memorandumTitle;
	}

	public void setMemorandumTitle(String memorandumTitle) {
		this.memorandumTitle = memorandumTitle;
	}

	public String getMemorandumContent() {
		return memorandumContent;
	}

	public void setMemorandumContent(String memorandumContent) {
		this.memorandumContent = memorandumContent;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}