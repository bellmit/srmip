package org.jeecgframework.poi.excel.view;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Excel模板视图
 * 
 * @author JueYue
 * @date 2014年6月30日 下午9:15:49
 */
@SuppressWarnings("unchecked")
public class JeecgTemplateExcelView extends AbstractView {

	private static final String CONTENT_TYPE = "application/vnd.ms-excel";

	public JeecgTemplateExcelView() {
		setContentType(CONTENT_TYPE);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String codedFileName = "临时文件.xls";
		if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
            String fileName = POIPublicUtil
                    .processFileName(request, (String) model.get(NormalExcelConstants.FILE_NAME));
            codedFileName = fileName + ".xls";
		}
        response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
		Workbook workbook = ExcelExportUtil.exportExcel(
				(TemplateExportParams)model.get(TemplateExcelConstants.PARAMS),
				(Class<?>) model.get(TemplateExcelConstants.CLASS),
				(List<?>)model.get(TemplateExcelConstants.LIST_DATA),
				(Map<String, Object>)model.get(TemplateExcelConstants.MAP_DATA));
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}
}
