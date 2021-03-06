package org.jeecgframework.poi.excel.export.base;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.params.MergeEntity;
import org.jeecgframework.poi.util.POIPublicUtil;

/**
 * 导出基础服务
 * 
 * @author JueYue
 * @date 2014年6月17日 下午6:15:13
 */
public abstract class ExcelExportBase extends ExportBase {

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param excelParams
	 */
	public void mergeCells(Sheet sheet, List<ExcelExportEntity> excelParams,
			int titleHeight) {
		Map<Integer, int[]> mergeMap = getMergeDataMap(excelParams);
		Map<Integer, MergeEntity> mergeDataMap = new HashMap<Integer, MergeEntity>();
		if (mergeMap.size() == 0) {
			return;
		}
		Row row;
		Set<Integer> sets = mergeMap.keySet();
		String text;
		for (int i = titleHeight; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			for (Integer index : sets) {
				if (row.getCell(index) == null) {
					mergeDataMap.get(index).setEndRow(i);
				} else {
					text = row.getCell(index).getStringCellValue();
					if (StringUtils.isNotEmpty(text)) {
						hanlderMergeCells(index, i, text, mergeDataMap, sheet,
								row.getCell(index), mergeMap.get(index));
					} else {
						mergeCellOrContinue(index, mergeDataMap, sheet);
					}
				}
			}
		}
		for (Integer index : sets) {
			sheet.addMergedRegion(new CellRangeAddress(mergeDataMap.get(index)
					.getStartRow(), mergeDataMap.get(index).getEndRow(), index,
					index));
		}

	}

	/**
	 * 字符为空的情况下判断
	 * 
	 * @param index
	 * @param mergeDataMap
	 * @param sheet
	 */
	private void mergeCellOrContinue(Integer index,
			Map<Integer, MergeEntity> mergeDataMap, Sheet sheet) {
		if (mergeDataMap.containsKey(index)
				&& mergeDataMap.get(index).getEndRow() != mergeDataMap.get(
						index).getStartRow()) {
			sheet.addMergedRegion(new CellRangeAddress(mergeDataMap.get(index)
					.getStartRow(), mergeDataMap.get(index).getEndRow(), index,
					index));
			mergeDataMap.remove(index);
		}
	}

	/**
	 * 处理合并单元格
	 * 
	 * @param index
	 * @param rowNum
	 * @param text
	 * @param mergeDataMap
	 * @param sheet
	 * @param cell
	 * @param delys
	 */
	private void hanlderMergeCells(Integer index, int rowNum, String text,
			Map<Integer, MergeEntity> mergeDataMap, Sheet sheet, Cell cell,
			int[] delys) {
		if (mergeDataMap.containsKey(index)) {
			if (checkIsEqualByCellContents(mergeDataMap.get(index), text, cell,
					delys, rowNum)) {
				mergeDataMap.get(index).setEndRow(rowNum);
			} else {
				sheet.addMergedRegion(new CellRangeAddress(mergeDataMap.get(
						index).getStartRow(), mergeDataMap.get(index)
						.getEndRow(), index, index));
				mergeDataMap.put(index,
						createMergeEntity(text, rowNum, cell, delys));
			}
		} else {
			mergeDataMap.put(index,
					createMergeEntity(text, rowNum, cell, delys));
		}
	}

	/**
	 * 获取一个单元格的值,确保这个单元格必须有值,不然向上查询
	 * 
	 * @param cell
	 * @param index
	 * @param rowNum
	 * @return
	 */
	private String getCellNotNullText(Cell cell, int index, int rowNum) {
		String temp = cell.getRow().getCell(index).getStringCellValue();
		while (StringUtils.isEmpty(temp)) {
			temp = cell.getRow().getSheet().getRow(--rowNum).getCell(index)
					.getStringCellValue();
		}
		return temp;
	}

	private MergeEntity createMergeEntity(String text, int rowNum, Cell cell,
			int[] delys) {
		MergeEntity mergeEntity = new MergeEntity(text, rowNum, rowNum);
		List<String> list = new ArrayList<String>(delys.length);
		mergeEntity.setRelyList(list);
		for (int i = 0; i < delys.length; i++) {
			list.add(getCellNotNullText(cell, delys[i], rowNum));
		}
		return mergeEntity;
	}

	private boolean checkIsEqualByCellContents(MergeEntity mergeEntity,
			String text, Cell cell, int[] delys, int rowNum) {
		// 没有依赖关系
		if (delys == null || delys.length == 0) {
			return mergeEntity.getText().equals(text);
		}
		// 存在依赖关系
		if (mergeEntity.getText().equals(text)) {
			for (int i = 0; i > delys.length; i++) {
				if (!getCellNotNullText(cell, delys[i], rowNum).equals(
						mergeEntity.getRelyList().get(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private Map<Integer, int[]> getMergeDataMap(
			List<ExcelExportEntity> excelParams) {
		Map<Integer, int[]> mergeMap = new HashMap<Integer, int[]>();
		// 设置参数顺序,为之后合并单元格做准备
		int i = 0;
		for (ExcelExportEntity entity : excelParams) {
			if (entity.isMergeVertical()) {
				mergeMap.put(i, entity.getMergeRely());
			}
			if (entity.getList() != null) {
				for (ExcelExportEntity inner : entity.getList()) {
					if (inner.isMergeVertical()) {
						mergeMap.put(i, inner.getMergeRely());
					}
					i++;
				}
			} else {
				i++;
			}
		}
		return mergeMap;
	}

	/**
	 * 创建 最主要的 Cells
	 * 
	 * @param styles
	 * @param rowHeight
	 * @throws Exception
	 */
	public int createCells(Drawing patriarch, int index, Object t,
			List<ExcelExportEntity> excelParams, Sheet sheet,
			Workbook workbook, Map<String, HSSFCellStyle> styles,
			short rowHeight) throws Exception {
		ExcelExportEntity entity;
		Row row = sheet.createRow(index);
		row.setHeight(rowHeight);
		int maxHeight = 1, cellNum = 0;
		for (int k = 0, paramSize = excelParams.size(); k < paramSize; k++) {
			entity = excelParams.get(k);
			if (entity.getList() != null) {
				Collection<?> list = (Collection<?>) entity.getMethod().invoke(
						t, new Object[] {});
				int listC = 0;
				for (Object obj : list) {
					createListCells(patriarch, index + listC, cellNum, obj,
							entity.getList(), sheet, workbook, styles);
					listC++;
				}
				cellNum += entity.getList().size();
				if (list != null && list.size() > maxHeight) {
					maxHeight = list.size();
				}
			} else {
				Object value = getCellValue(entity, t);
				if (entity.getType() == 1) {
					createStringCell(
							row,
							cellNum++,
							value == null ? "" : value.toString(),
							index % 2 == 0 ? getStyles(styles, false,
									entity.isWrap()) : getStyles(styles, true,
									entity.isWrap()), entity);
				} else {
					createImageCell(patriarch, entity, row, cellNum++,
							value == null ? "" : value.toString(), t);
				}
			}
		}
		// 合并需要合并的单元格
		cellNum = 0;
		for (int k = 0, paramSize = excelParams.size(); k < paramSize; k++) {
			entity = excelParams.get(k);
			if (entity.getList() != null) {
				cellNum += entity.getList().size();
			} else if (entity.isNeedMerge()) {
				sheet.addMergedRegion(new CellRangeAddress(index, index
						+ maxHeight - 1, cellNum, cellNum));
				cellNum++;
			}
		}
		return maxHeight;

	}

	public CellStyle getStyles(Map<String, HSSFCellStyle> styles, boolean b,
			boolean wrap) {
		return null;
	}

	/**
	 * 创建List之后的各个Cells
	 * 
	 * @param styles
	 */
	public void createListCells(Drawing patriarch, int index, int cellNum,
			Object obj, List<ExcelExportEntity> excelParams, Sheet sheet,
			Workbook workbook, Map<String, HSSFCellStyle> styles)
			throws Exception {
		ExcelExportEntity entity;
		Row row;
		if (sheet.getRow(index) == null) {
			row = sheet.createRow(index);
			row.setHeight(getRowHeight(excelParams));
		} else {
			row = sheet.getRow(index);
		}
		for (int k = 0, paramSize = excelParams.size(); k < paramSize; k++) {
			entity = excelParams.get(k);
			Object value = getCellValue(entity, obj);
			if (entity.getType() == 1) {
				createStringCell(
						row,
						cellNum++,
						value == null ? "" : value.toString(),
						row.getRowNum() % 2 == 0 ? getStyles(styles, false,
								entity.isWrap()) : getStyles(styles, true,
								entity.isWrap()), entity);
			} else {
				createImageCell(patriarch, entity, row, cellNum++,
						value == null ? "" : value.toString(), obj);
			}
		}
	}

	public void setCellWith(List<ExcelExportEntity> excelParams, Sheet sheet) {
		int index = 0;
		for (int i = 0; i < excelParams.size(); i++) {
			if (excelParams.get(i).getList() != null) {
				List<ExcelExportEntity> list = excelParams.get(i).getList();
				for (int j = 0; j < list.size(); j++) {
					sheet.setColumnWidth(index, 256 * list.get(j).getWidth());
					index++;
				}
			} else {
				sheet.setColumnWidth(index, 256 * excelParams.get(i).getWidth());
				index++;
			}
		}
	}

	/**
	 * 创建文本类型的Cell
	 * 
	 * @param row
	 * @param index
	 * @param text
	 * @param style
	 * @param entity
	 */
	public void createStringCell(Row row, int index, String text,
			CellStyle style, ExcelExportEntity entity) {
		Cell cell = row.createCell(index);
		RichTextString Rtext = new HSSFRichTextString(text);
		cell.setCellValue(Rtext);
		if (style != null) {
			cell.setCellStyle(style);
		}
	}

	/**
	 * 图片类型的Cell
	 * 
	 * @param patriarch
	 * @param entity
	 * @param row
	 * @param i
	 * @param string
	 * @param obj
	 * @throws Exception
	 */
	public void createImageCell(Drawing patriarch, ExcelExportEntity entity,
			Row row, int i, String string, Object obj) throws Exception {
		row.setHeight((short) (50 * entity.getHeight()));
		row.createCell(i);
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) i,
				row.getRowNum(), (short) (i + 1), row.getRowNum() + 1);
		if (StringUtils.isEmpty(string)) {
			return;
		}
		if (entity.getExportImageType() == 1) {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			BufferedImage bufferImg;
			try {
				String path = POIPublicUtil.getWebRootPath(string);
				path = path.replace("WEB-INF/classes/", "");
				path = path.replace("file:/", "");
				bufferImg = ImageIO.read(new File(path));
				ImageIO.write(
						bufferImg,
						string.substring(string.indexOf(".") + 1,
								string.length()), byteArrayOut);
				byte[] value = byteArrayOut.toByteArray();
				patriarch.createPicture(anchor, row.getSheet().getWorkbook()
						.addPicture(value, getImageType(value)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			byte[] value = (byte[]) (entity.getMethods() != null ? getFieldBySomeMethod(
					entity.getMethods(), obj) : entity.getMethod().invoke(obj,
					new Object[] {}));
			if (value != null) {
				patriarch.createPicture(anchor, row.getSheet().getWorkbook()
						.addPicture(value, getImageType(value)));
			}
		}

	}

	/**
	 * 获取图片类型,设置图片插入类型
	 * 
	 * @param value
	 * @return
	 * @Author JueYue
	 * @date 2013年11月25日
	 */
	public int getImageType(byte[] value) {
		String type = POIPublicUtil.getFileExtendName(value);
		if (type.equalsIgnoreCase("JPG")) {
			return HSSFWorkbook.PICTURE_TYPE_JPEG;
		} else if (type.equalsIgnoreCase("PNG")) {
			return HSSFWorkbook.PICTURE_TYPE_PNG;
		}
		return HSSFWorkbook.PICTURE_TYPE_JPEG;
	}

	/**
	 * 获取导出报表的字段总长度
	 * 
	 * @param excelParams
	 * @return
	 */
	public int getFieldWidth(List<ExcelExportEntity> excelParams) {
		int length = -1;// 从0开始计算单元格的
		for (ExcelExportEntity entity : excelParams) {
			length += entity.getList() != null ? entity.getList().size() : 1;
		}
		return length;
	}

}
