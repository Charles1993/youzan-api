package com.youzan.common.bean;

/**
 * 通用查询参数对象
 * @author  zhucy
 * @version  [Version NO, 2018年9月30日]
 * @see  [Related classes/methods]
 * @since  [product/module version]
 */
public class QueryParam extends Pager{

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String keyWords;
	
	private String uid;
	
	public QueryParam(){
		
	}
	
	public QueryParam(String keyWords){
		this.keyWords = keyWords;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
