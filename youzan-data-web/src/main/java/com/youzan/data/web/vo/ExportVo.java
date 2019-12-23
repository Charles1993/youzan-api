package com.youzan.data.web.vo;

import java.io.Serializable;

public class ExportVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//序号
	private Integer seqNo;
	
	//收货人姓名[姓名]
	private String receiverName;
	
	//买家昵称
	private String fansNickname;
	
	//商品名称
	private String itemName;
	
	//份数
	private Integer num;
	
	//总数量
	private Integer subTotalNum;
	
	//手机号码
	private String receiverTel;
	
	//订单配送方式
	private String expressType;
	
	//地址
	private String deliveryAddress;
	
	//备注
	private String buyerMessage;
	
	//自提点
	private String selfAddrName;
	
	//组别
	private String group;
	
	//跨行
	private int spanRow;

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getFansNickname() {
		return fansNickname;
	}

	public void setFansNickname(String fansNickname) {
		this.fansNickname = fansNickname;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getSubTotalNum() {
		return subTotalNum;
	}

	public void setSubTotalNum(Integer subTotalNum) {
		this.subTotalNum = subTotalNum;
	}

	public String getReceiverTel() {
		return receiverTel;
	}

	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	public String getExpressType() {
		return expressType;
	}

	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getSelfAddrName() {
		return selfAddrName;
	}

	public void setSelfAddrName(String selfAddrName) {
		this.selfAddrName = selfAddrName;
	}

	public int getSpanRow() {
		return spanRow;
	}

	public void setSpanRow(int spanRow) {
		this.spanRow = spanRow;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
}
