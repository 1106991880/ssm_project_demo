package cn.springmvc.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DeptInfo implements Serializable {

	private String deptId;

	private String deptNo;

	private String deptName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date establishTime;

	private String deptManager;

	private String superId;

	private String deptDesc;

	private String ifLeaf = "1";

	public DeptInfo() {
		super();
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getEstablishTime() {
		return establishTime;
	}

	public void setEstablishTime(Date establishTime) {
		this.establishTime = establishTime;
	}

	public String getDeptManager() {
		return deptManager;
	}

	public void setDeptManager(String deptManager) {
		this.deptManager = deptManager;
	}

	public String getSuperId() {
		return superId;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getIfLeaf() {
		return ifLeaf;
	}

	public void setIfLeaf(String ifLeaf) {
		this.ifLeaf = ifLeaf;
	}

	private static final long serialVersionUID = 1L;

}