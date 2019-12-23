package com.youzan.common.bean;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable{
	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String creator;
	
	private String creatorName;
	
	private Date createTime;
	
	private String modifier;
	
	private String modifierName;
	
	private Date modifyTime;

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
