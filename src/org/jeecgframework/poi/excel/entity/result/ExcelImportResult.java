package org.jeecgframework.poi.excel.entity.result;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 导入返回类
 * 
 * @author JueYue
 * @date 2014年6月29日 下午5:12:10
 */
@SuppressWarnings("rawtypes")
public class ExcelImportResult {

	public ExcelImportResult() {

	}

	public ExcelImportResult(List list, boolean verfiyFail, Workbook workbook) {
		this.list = list;
		this.verfiyFail = verfiyFail;
		this.workbook = workbook;
	}

	/**
	 * 结果集
	 */
	private List list;
	/**
	 * 是否存在校验失败
	 */
	private boolean verfiyFail;
	/**
	 * 数据源
	 */
	private Workbook workbook;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public boolean isVerfiyFail() {
		return verfiyFail;
	}

	public void setVerfiyFail(boolean verfiyFail) {
		this.verfiyFail = verfiyFail;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

}
