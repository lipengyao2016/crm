package com.crm.common;

import java.util.List;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings({ "serial", "deprecation" })
@ApiModel
public class NewPageInfo<T> extends PageInfo<T> {
	public NewPageInfo() {
		super();
	}

	public NewPageInfo(List<T> list) {
		super(list);
	}

	@ApiModelProperty(value = "当前第几页")
	public int getPageNum() {
		return super.getPageNum();
	}

	@ApiModelProperty(value = "每页数据条数")
	public int getPageSize() {
		return super.getPageSize();
	}

	@ApiModelProperty(value = "当前页数据条数")
	public int getSize() {
		return super.getSize();
	}

	@ApiModelProperty(value = "当前页起始行")
	public int getStartRow() {
		return super.getStartRow();
	}

	@ApiModelProperty(value = "当前页结束行")
	public int getEndRow() {
		return super.getEndRow();
	}

	@ApiModelProperty(value = "数据总条数")
	public long getTotal() {
		return super.getTotal();
	}

	@ApiModelProperty(value = "数据总页数")
	public int getPages() {
		return super.getPages();
	}

	@ApiModelProperty(value = "返回数据json")
	public List<T> getList() {
		return super.getList();
	}

	@ApiModelProperty(value = "当上一页")
	public int getPrePage() {
		return super.getPrePage();
	}

	@ApiModelProperty(value = "下一页")
	public int getNextPage() {
		return super.getNextPage();
	}

	@ApiModelProperty(value = "是否第一页")
	public boolean isIsFirstPage() {
		return super.isIsFirstPage();
	}

	@ApiModelProperty(value = "是否最后一页")
	public boolean isIsLastPage() {
		return super.isIsLastPage();
	}

	@ApiModelProperty(value = "是否有上一页")
	public boolean isHasPreviousPage() {
		return super.isHasPreviousPage();
	}

	@ApiModelProperty(value = "是否有下一页")
	public boolean isHasNextPage() {
		return super.isHasNextPage();
	}

	@ApiModelProperty(value = "忽略", hidden = true)
	public int getNavigatePages() {
		return super.getNavigatePages();
	}

	@ApiModelProperty(value = "忽略", hidden = true)
	public int[] getNavigatepageNums() {
		return super.getNavigatepageNums();
	}

	@ApiModelProperty(value = "忽略", hidden = true)
	public int getNavigateFirstPage() {
		return super.getNavigateFirstPage();
	}

	@ApiModelProperty(value = "忽略", hidden = true)
	public int getNavigateLastPage() {
		return super.getNavigateLastPage();
	}

	@ApiModelProperty(value = "忽略", hidden = true)
	public int getLastPage() {
		return super.getLastPage();
	}

	@ApiModelProperty(value = "忽略", hidden = true)
	public int getFirstPage() {
		return super.getFirstPage();
	}
}
