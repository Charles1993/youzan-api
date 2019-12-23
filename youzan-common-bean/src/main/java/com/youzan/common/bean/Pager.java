package com.youzan.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类型
 * 
 * @author  zhucy
 * @version  [Version NO, 2018年10月9日]
 * @see  [Related classes/methods]
 * @since  [product/module version]
 */
public class Pager implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private List<?> list; // 对象记录结果集
	private int recordTotal = 0; // 总记录数
	private int pageSize = 20; // 每页显示记录数
	private int pageTotal = 1; // 总页数
	private int currPage = 1; // 当前页
	
	private boolean isFirstPage = false; // 是否为第一页
	private boolean isLastPage = false; // 是否为最后一页
	private boolean hasPreviousPage = false; // 是否有前一页
	private boolean hasNextPage = false; // 是否有下一页
	
	public Pager(){
		
	}

	public Pager(int recordTotal , int currPage) {
		init(recordTotal , currPage, pageSize);
	}

	public Pager(int rowCount, int currPage, int pageSize) {
		init(rowCount, currPage, pageSize);
	}

	private void init(int rowCount, int currPage, int pageSize) {
		// 设置基本参数
		this.recordTotal  = rowCount;
		this.pageSize = pageSize;
		this.pageTotal = (this.recordTotal - 1) / this.pageSize + 1;

		// 根据输入可能错误的当前号码进行自动纠正
		if (currPage < 1) {
			this.currPage = 1;
		} else if (currPage > this.pageTotal) {
			this.currPage = this.pageTotal;
		} else {
			this.currPage = currPage;
		}
		
		judgePageBoudary();
	}
	
	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = currPage == 1;
		isLastPage = currPage == pageTotal && currPage != 1;
		hasPreviousPage = currPage > 1;
		hasNextPage = currPage < pageTotal;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(int recordTotal) {
		this.recordTotal = recordTotal;
		this.pageTotal = (this.recordTotal - 1) / this.pageSize + 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}
}
