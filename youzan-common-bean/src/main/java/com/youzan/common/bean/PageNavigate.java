package com.youzan.common.bean;

/**
 * 分页扩展工具类型
 * @author  evan.zhu
 * @version  [Version NO, 2018年10月9日]
 * @see  [Related classes/methods]
 * @since  [product/module version]
 */
public class PageNavigate extends Pager{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private int navigatePages = 5; // 导航页码数
	private int[] navigatePageNumbers; // 所有导航页号

	public PageNavigate() {
		super();
	}

	public PageNavigate(int rowCount, int currPage, int pageSize) {
		super(rowCount, currPage, pageSize);
		// 基本参数设定之后进行导航页面的计算
		calcNavigatePageNumbers();
	}

	public PageNavigate(int recordTotal, int currPage) {
		super(recordTotal, currPage);
		// 基本参数设定之后进行导航页面的计算
		calcNavigatePageNumbers();
	}

	/**
	 * 计算导航页
	 */
	private void calcNavigatePageNumbers() {
		// 当总页数小于或等于导航页码数时
		if (this.getPageSize() <= navigatePages) {
			navigatePageNumbers = new int[this.getPageSize()];
			for (int i = 0; i < this.getPageSize(); i++) {
				navigatePageNumbers[i] = i + 1;
			}
		} else { // 当总页数大于导航页码数时
			navigatePageNumbers = new int[navigatePages];
			int startNum = this.getCurrPage() - navigatePages / 2;
			int endNum = this.getCurrPage() + navigatePages / 2;

			if (startNum < 1) {
				startNum = 1;
				// (最前navigatePages页
				for (int i = 0; i < navigatePages; i++) {
					navigatePageNumbers[i] = startNum++;
				}
			} else if (endNum > this.getPageSize()) {
				endNum = this.getPageSize();
				// 最后navigatePages页
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatePageNumbers[i] = endNum--;
				}
			} else {
				// 所有中间页
				for (int i = 0; i < navigatePages; i++) {
					navigatePageNumbers[i] = startNum++;
				}
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[").append("recordTotal=").append(this.getRecordTotal()).append(",pageTotal=")
				.append(this.getPageTotal()).append(",currPage=").append(this.getCurrPage())
				.append(",pageSize=").append(this.getPageSize()).append(",isFirstPage=")
				.append(this.isFirstPage()).append(",isLastPage=").append(this.isLastPage())
				.append(",hasPreviousPage=").append(this.isHasPreviousPage())
				.append(",hasNextPage=").append(this.isHasNextPage())
				.append(",navigatePageNumbers=");
		
		int len = navigatePageNumbers.length;
		
		if (len > 0){
			sb.append(navigatePageNumbers[0]);
		}
			
		for (int i = 1; i < len; i++) {
			sb.append(" " + navigatePageNumbers[i]);
		}
		
		int size = null == this.getList() ? 0 : this.getList().size();
		
		sb.append(",list.size=" + size);
		sb.append("]");
		
		return sb.toString();
	}
	
	public int[] getNavigatePageNumbers() {
		return navigatePageNumbers;
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}
}
