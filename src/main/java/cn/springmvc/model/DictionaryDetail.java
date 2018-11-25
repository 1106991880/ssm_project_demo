package cn.springmvc.model;

import java.util.Date;

public class DictionaryDetail {

	private String dictDetailId;

	private String dictId;

	private String dictDetailCode;

	private String dictDetailName;

	private String dictDetailStatus;

	private Date createTime;

	private String creator;

	public DictionaryDetail() {

	}

	public String getDictDetailId() {
		return dictDetailId;
	}

	public void setDictDetailId(String dictDetailId) {
		this.dictDetailId = dictDetailId;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictDetailCode() {
		return dictDetailCode;
	}

	public void setDictDetailCode(String dictDetailCode) {
		this.dictDetailCode = dictDetailCode;
	}

	public String getDictDetailName() {
		return dictDetailName;
	}

	public void setDictDetailName(String dictDetailName) {
		this.dictDetailName = dictDetailName;
	}

	public String getDictDetailStatus() {
		return dictDetailStatus;
	}

	public void setDictDetailStatus(String dictDetailStatus) {
		this.dictDetailStatus = dictDetailStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}
