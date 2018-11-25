package cn.springmvc.utildao;

import java.io.Serializable;

import org.springframework.stereotype.Service;

@Service
public class PageInfo<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pageSize;// 每页显示条数

	private int totalPages;// 总的页数

	private int totalRecords;// 数据总条数

	private int currentPage;// 当前页

	@SuppressWarnings("unused")
	private int fromRecord;// 从第几条开始获取数据

	public PageInfo() {
		this(1);
	}

	public int getTotalPages() {
		// 分页算法，计算总页数
		totalPages = (totalRecords + pageSize - 1) / pageSize;
		return totalPages;
	}

	public PageInfo(int currentPage) {
		// 默认每页显示10条记录
		this(currentPage, 10);
	}

	public PageInfo(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		if (pageSize > 0)
			this.pageSize = pageSize;
		// 错误处理
		if (this.currentPage < 1)
			this.currentPage = 1;
	}

	public int getFromRecord() {
		// 计算从第几条获取数据
		return (currentPage - 1) * pageSize;
	}

	public void setFromRecord(int fromRecord) {
		this.fromRecord = fromRecord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}