package org.jeecgframework.web.cgform.controller.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.autolist.ConfigServiceI;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.service.excel.ExcelTempletService;
import org.jeecgframework.web.cgform.service.impl.config.util.FieldNumComparator;
import org.jeecgframework.web.cgform.util.QueryParamUtil;
import org.jeecgframework.web.system.pojo.base.DictEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: excelTempletController
 * @Description: excel????????????
 * @author huiyong
 */
@Scope("prototype")
@Controller
@RequestMapping("/excelTempletController")
public class ExcelTempletController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(ExcelTempletController.class);
	private String message;
	@Autowired
	private ConfigServiceI configService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private DataBaseService dataBaseService;
	@Autowired
	private CgTableServiceI cgTableService;
	@Autowired
	private SystemService systemService;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * ??????excel??????
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("all")
	@RequestMapping(params = "exportXls")
	public void exportXls(HttpServletRequest request,
			HttpServletResponse response,String field, DataGrid dataGrid) {

		String codedFileName = "??????";
		String sheetName="????????????";
		List<CgFormFieldEntity> lists = null;
		if (StringUtil.isNotEmpty(request.getParameter("tableName"))) {
			String configId = request.getParameter("tableName");
			String jversion = cgFormFieldService.getCgFormVersionByTableName(configId);
			Map<String, Object> configs = configService.queryConfigs(configId,jversion);
			Map params =  new HashMap<String,Object>();
			//step.2 ???????????????????????????
			List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
			for(CgFormFieldEntity b:beans){
//				if(CgAutoListConstant.BOOL_TRUE.equals(b.getIsQuery())){
					QueryParamUtil.loadQueryParams(request,b,params);
//				}
			}
			List<Map<String, Object>> result=cgTableService.querySingle(configId, field.toString(), params,null,null, 1,99999 );
			
			//???????????????
			lists = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
			for (int i = lists.size() - 1; i >= 0; i--) {
				if(!field.contains(lists.get(i).getFieldName())){
					lists.remove(i);
				}
			}
			
			//????????????????????????checkbox????????????code??????????????????text?????????
			Map<String, Object> dicMap = new HashMap<String, Object>();
			for(CgFormFieldEntity b:beans){
				loadDic(dicMap, b);
				List<DictEntity> dicList = (List<DictEntity>)dicMap.get(CgAutoListConstant.FIELD_DICTLIST);
				if(dicList.size() > 0){
					for(Map<String, Object> resultMap:result){
						StringBuffer sb = new StringBuffer();
						String value = (String)resultMap.get(b.getFieldName());
						if(oConvertUtils.isEmpty(value)){continue;}
						String[] arrayVal = value.split(",");
						if(arrayVal.length > 1){
							for(String val:arrayVal){
								for(DictEntity dictEntity:dicList){
									if(val.equals(dictEntity.getTypecode())){
										sb.append(dictEntity.getTypename());
										sb.append(",");
									}
									
								}
							}
							resultMap.put(b.getFieldName(), sb.toString().substring(0, sb.toString().length()-1));
						}
						
					}
				}
			}
			dealDic(result,beans);
			// ???????????????????????????
			Collections.sort(lists, new FieldNumComparator());
			//??????????????????
			sheetName = (String)configs.get(CgAutoListConstant.CONFIG_NAME);
			//??????????????????
			String tableName = (String)configs.get(CgAutoListConstant.TABLENAME);
			//?????????????????? form???????????????-v?????????.xsl
			codedFileName = sheetName+"_"+tableName+"-v"+(String)configs.get(CgAutoListConstant.CONFIG_VERSION);
			// ?????????????????????
			response.setContentType("application/vnd.ms-excel");

			OutputStream fOut = null;
			try {

				// ?????????????????????????????????????????????????????????
				String browse = BrowserUtils.checkBrowse(request);
				if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) {
					response.setHeader("content-disposition",
							"attachment;filename="
									+ java.net.URLEncoder.encode(codedFileName,
											"UTF-8") + ".xls");
				} else {
					String newtitle = new String(codedFileName.getBytes("UTF-8"),
							"ISO8859-1");
					response.setHeader("content-disposition",
							"attachment;filename=" + newtitle + ".xls");
				}
				// ?????????????????????
				HSSFWorkbook workbook = null;
				workbook = ExcelTempletService.exportExcel(sheetName, lists,result);
				fOut = response.getOutputStream();
				workbook.write(fOut);
			} catch (UnsupportedEncodingException e1) {

			} catch (Exception e) {

			} finally {
				try {
					fOut.flush();
					fOut.close();
				} catch (IOException e) {

				}
			}
		} else {
			throw new BusinessException("????????????");
		}
		
	}

	@RequestMapping(params = "goImplXls" , method = RequestMethod.GET)
    public ModelAndView goImplXls(HttpServletRequest request) {
		request.setAttribute("tableName", request.getParameter("tableName"));
	    return  new ModelAndView("jeecg/cgform/excel/upload");
    }
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("all")
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		String message = "????????????";
		AjaxJson j = new AjaxJson();
		String configId = request.getParameter("tableName");
		String jversion = cgFormFieldService.getCgFormVersionByTableName(configId);
		Map<String, Object> configs = configService.queryConfigs(configId,jversion);
		//?????????????????????
		String version = (String)configs.get(CgAutoListConstant.CONFIG_VERSION);
		List<CgFormFieldEntity> lists = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// ????????????????????????
			//????????????????????????
			String docVersion = getDocVersion(file.getOriginalFilename());
			if (docVersion.equals(version)) {
				List<Map<String, Object>> listDate;
				try {
					//??????excel????????????
					listDate = (List<Map<String, Object>>) ExcelTempletService.importExcelByIs(file.getInputStream(), lists);
					if (listDate==null) {
						message = "????????????????????????";
						logger.error(message);
					}else{
						for (Map<String, Object> map : listDate) {
							map.put("id", UUIDGenerator.generate());
							int num = dataBaseService.insertTable(configId, map);
						}
						message = "?????????????????????";
					}
				} catch (Exception e) {
					message = "?????????????????????";
					logger.error(ExceptionUtil.getExceptionMessage(e));
				}
			}else{
				message = "????????????????????????????????????????????????????????????";
				logger.error(message);
			}
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * ??????????????????????????????
	 * ????????????????????? form???????????????-v?????????.xsl
	 * ?????????????????? form???????????????-v?????????(1).xsl
	 * @param docName
	 * @return
	 */
	private static String  getDocVersion(String docName){
		if(docName.indexOf("(")>0){
			return docName.substring(docName.indexOf("-v")+2, docName.indexOf("("));
		}else {
			return docName.substring(docName.indexOf("-v")+2, docName.indexOf("."));
		}
	}
	
	private void loadDic(Map m, CgFormFieldEntity bean) {
		String dicT = bean.getDictTable();//??????Table
		String dicF = bean.getDictField();//??????Code
		String dicText = bean.getDictText();//??????Text
		if(StringUtil.isEmpty(dicT)&& StringUtil.isEmpty(dicF)){
			//??????????????????????????????????????????????????????
			m.put(CgAutoListConstant.FIELD_DICTLIST, new ArrayList(0));
			return;
		}
		if(!bean.getShowType().equals("popup")){
			List<DictEntity> dicDatas = queryDic(dicT, dicF,dicText);
			m.put(CgAutoListConstant.FIELD_DICTLIST, dicDatas);
		}else{
			m.put(CgAutoListConstant.FIELD_DICTLIST, new ArrayList(0));
		}
	}
	
	private List<DictEntity> queryDic(String dicTable, String dicCode,String dicText) {
		return this.systemService.queryDict(dicTable, dicCode, dicText);
	}
	
	/**
	 * ??????????????????
	 * @param result ??????????????????
	 * @param beans ????????????
	 */
	@SuppressWarnings("unchecked")
	private void dealDic(List<Map<String, Object>> result,
			List<CgFormFieldEntity> beans) {
		for(CgFormFieldEntity bean:beans){
			String dicTable = bean.getDictTable();//??????Table
			String dicCode = bean.getDictField();//??????Code
			String dicText= bean.getDictText();//??????text
			if(StringUtil.isEmpty(dicTable)&& StringUtil.isEmpty(dicCode)){
				//?????????????????????
				continue;
			}else{
				if(!bean.getShowType().equals("popup")){
					List<DictEntity> dicDataList = queryDic(dicTable, dicCode,dicText);
					for(Map r:result){
						String value = String.valueOf(r.get(bean.getFieldName()));
						for(DictEntity dictEntity:dicDataList){
							if(value.equalsIgnoreCase(dictEntity.getTypecode())){
								//------------------update-begin------for:-???????????????-----------------------author:zhagndaihao------------
								r.put(bean.getFieldName(),MutiLangUtil.getMutiLangInstance().getLang(dictEntity.getTypename()));
								//------------------update-end-----for:-???????????????----------------------------author:zhagndaihao---------
							}
						}
					}
				}
			}
		}
	}
}
