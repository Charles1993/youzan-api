package com.youzan.data.api.constant;

/**
 * 常量类
 * @author Evan.zhu
 *
 */
public final class Constants {
	
	/**
	 * 订单状态：待付款
	 */
	public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
	
	/**
	 * 订单状态：待发货
	 */
	public static final String WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS";
	
	/**
	 * 订单状态：等待买家确认
	 */
	public static final String WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS";
	
	/**
	 * 订单状态：订单完成
	 */
	public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
	
	/**
	 * 订单状态：订单关闭
	 */
	public static final String TRADE_CLOSE = "TRADE_CLOSE";
	
	/**
	 * 订单状态：退款中
	 */
	public static final String TRADE_REFUND = "TRADE_REFUND";
}
