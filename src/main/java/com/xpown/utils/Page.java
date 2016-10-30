package com.xpown.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 
 * 注意所有序号从1开始.
 * 
 * @param <T> Page中记录的类型.
 * 
 *  
 */
public class Page<T> {
	//-- 公共变量 --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	//-- 分页参数 --//
	protected int pageNo = 1;
	protected int pageSize = -1;
	//protected String orderBy = null;
	//protected String order = null;
	//protected boolean autoCount = true;
	
	private int pageCount; //总页数
	
	private int offset = 0; // 起始条数
	
	//-- 返回结果 --//
	protected List<T> result = new ArrayList<T>();
	protected int totalCount = 0;

	//-- 构造函数 --//
	/**
	private Page() {
	}
	*/

/**
 * 
	public Page<T> end() {
		
		// 1, 总页码
		pageCount = ((int)this.totalCount + pageSize - 1) / pageSize;
		// 2, startPageIndex（显示的页码列表的开始索引）与endPageIndex（显示的页码列表的结束索引）
		// a, 总页码不大于10页
		if (pageCount <= 10) {
			startPageIndex = 1;
			endPageIndex = pageCount;
		}
		// b, 总码大于10页
		else {
			// 在中间，显示前面4个，后面5个
			startPageIndex = pageNo - 4;
			endPageIndex = pageNo + 5;

			// 前面不足4个时，显示前10个页码
			if (startPageIndex < 1) {
				startPageIndex = 1;
				endPageIndex = 10;
			}
			// 后面不足5个时，显示后10个页码
			else if (endPageIndex > pageCount) {
				endPageIndex = pageCount;
				startPageIndex = pageCount - 10 + 1;
			}
		}
		return this;

	}
**/
	/*
	public Page(int pageSize) {
		this.pageSize = pageSize;
	}
	*/

	public Page(int pageNo,int pageSize){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.offset = (pageNo-1) * pageSize;
	}
	
	public Page(int pageNo,int pageSize,int totalCount){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.offset = (pageNo-1) * pageSize;
		this.totalCount = totalCount;
	}
	
	//-- 分页参数访问函数 --//
	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 返回Page对象自身的setPageNo函数,可用于连续设置。
	 */
	public Page<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	/**
	 * 获得每页的记录数量, 默认为-1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 返回Page对象自身的setPageSize函数,可用于连续设置。
	 */
	public Page<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	//-- 访问查询结果函数 --//

	/**
	 * 获得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 获得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final int totalCount) {
		this.totalCount = totalCount;
		this.pageCount = getTotalPages();
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public int getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		int count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}


    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
     * 用于Mysql,Hibernate.
     */
    public int getOffset() {
         return ((pageNo - 1) * pageSize);
    	//return offset;
    }

    
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     * 用于Oracle.
     */
    public int getStartRow() {
            return getOffset() + 1;
    }

    /**
     * 根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置, 序号从1开始.
     * 用于Oracle.
     */
    public int getEndRow() {
        return pageSize * pageNo;
    }


	public int getPageCount() {
		//return pageCount;
		pageCount = ((int)this.totalCount + pageSize - 1) / pageSize;
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	
    public int getOffsetFromPageNo(){
    	return ((pageNo - 1) * pageSize);
    }
	
    public int getPageNoFromOffset(){
    	return offset/pageSize + 1;
    }
	
}
