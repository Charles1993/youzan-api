package com.youzan.data.api.bean;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 自提对象
 * @author Evan.zhu
 *
 */
public class SelfFetchInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//详细地址
	@JSONField(name = "address_detail")
	private String addressDetail;
	
	@JSONField(name = "city")
	private String city;
	
	@JSONField(name = "county")
	private String country;
	
	@JSONField(name = "province")
	private String province;
	
	//自提点名称
	@JSONField(name = "name")
	private String name;
	
	@JSONField(name = "tel")
	private String tel;
	
	//用户名称
	@JSONField(name = "user_name")
	private String userName;
	
	//用户电话
	@JSONField(name = "user_tel")
	private String userTel;

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
}
