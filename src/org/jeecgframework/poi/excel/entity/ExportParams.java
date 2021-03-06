package org.jeecgframework.poi.excel.entity;

import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel 导出参数
 * 
 * @author jueyue
 * @version 1.0 2013年8月24日
 */
public class ExportParams extends ExcelBaseParams {

	public ExportParams() {

	}

	public ExportParams(String title, String sheetName) {
		this.title = title;
		this.sheetName = sheetName;
	}

	public ExportParams(String title, String secondTitle, String sheetName) {
		this.title = title;
		this.secondTitle = secondTitle;
		this.sheetName = sheetName;
	}

	/**
	 * 表格名称
	 */
	private String title;
	/**
	 * 表格名称
	 */
	private short titleHeight = 20;
	/**
	 * 第二行名称
	 */
	private String secondTitle;
	/**
	 * 表格名称
	 */
	private short secondTitleHeight = 8;
	/**
	 * sheetName
	 */
	private String sheetName;
	/**
	 * 过滤的属性
	 */
	private String[] exclusions;
	/**
	 * 表头颜色
	 */
	private short color = HSSFColor.WHITE.index;

	/**
	 * 属性说明行的颜色 例如:HSSFColor.SKY_BLUE.index 默认
	 */
	private short headerColor = HSSFColor.SKY_BLUE.index;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public short getColor() {
		return color;
	}

	public void setColor(short color) {
		this.color = color;
	}

	public String[] getExclusions() {
		return exclusions;
	}

	public void setExclusions(String[] exclusions) {
		this.exclusions = exclusions;
	}

	public String getSecondTitle() {
		return secondTitle;
	}

	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}

	public short getHeaderColor() {
		return headerColor;
	}

	public void setHeaderColor(short headerColor) {
		this.headerColor = headerColor;
	}

	public short getTitleHeight() {
		return (short) (titleHeight * 50);
	}

	public void setTitleHeight(short titleHeight) {
		this.titleHeight = titleHeight;
	}

	public short getSecondTitleHeight() {
		return (short) (secondTitleHeight * 50);
	}

	public void setSecondTitleHeight(short secondTitleHeight) {
		this.secondTitleHeight = secondTitleHeight;
	}
}
