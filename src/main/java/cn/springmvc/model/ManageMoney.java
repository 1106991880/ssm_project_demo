package cn.springmvc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ManageMoney implements Serializable {

	private static final long serialVersionUID = 1L;

	private String manageId;

	private int inOrOut;

	private BigDecimal howMuch;

	private String incident;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date takeTime;

	private int ifTake;

	private String takeId;

	private String remark;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	private String createId;

	public ManageMoney() {
		super();
	}

	public ManageMoney(int inOrOut, BigDecimal howMuch, String incident,
			Date takeTime, int ifTake, String takeId, String remark,
			Date createTime, Date updateTime, String createId) {
		super();
		this.inOrOut = inOrOut;
		this.howMuch = howMuch;
		this.incident = incident;
		this.takeTime = takeTime;
		this.ifTake = ifTake;
		this.takeId = takeId;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createId = createId;
	}

	public String getManageId() {
		return manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public int getInOrOut() {
		return inOrOut;
	}

	public void setInOrOut(int inOrOut) {
		this.inOrOut = inOrOut;
	}

	public BigDecimal getHowMuch() {
		return howMuch;
	}

	public void setHowMuch(BigDecimal howMuch) {
		this.howMuch = howMuch;
	}

	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

	public Date getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Date takeTime) {
		this.takeTime = takeTime;
	}

	public int getIfTake() {
		return ifTake;
	}

	public void setIfTake(int ifTake) {
		this.ifTake = ifTake;
	}

	public String getTakeId() {
		return takeId;
	}

	public void setTakeId(String takeId) {
		this.takeId = takeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

}