package com.kingtake.project.controller.price;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.price.TPmContractPriceCoverEntity;
import com.kingtake.project.entity.price.TPmContractPriceMasterEntity;
import com.kingtake.project.service.price.TPmContractPriceCoverServiceI;



/**   
 * @Title: Controller
 * @Description: 合同价款计算书--封面
 * @author onlineGenerator
 * @date 2015-08-10 15:57:10
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractPriceCoverController")
public class TPmContractPriceCoverController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmContractPriceCoverController.class);

	@Autowired
	private TPmContractPriceCoverServiceI tPmContractPriceCoverService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 合同价款计算书 ：主页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmContractPriceCover")
	public ModelAndView tPmContractPriceCover(HttpServletRequest request, HttpServletResponse response) {
		String contractType = request.getParameter("contractType");
		String contractId = request.getParameter("contractId");
		// 是否只读的标志
		String read = StringUtil.isEmpty(request.getParameter("read")) ? SrmipConstants.NO : request.getParameter("read");
		request.setAttribute("read", read);
		
		TPmContractPriceCoverEntity cover = null;
		// 查询是否已经生产合同价款书
		List<TPmContractPriceCoverEntity> list = systemService.getSession().createCriteria(TPmContractPriceCoverEntity.class)
				.add(Restrictions.eq("contractId", contractId))
				.add(Restrictions.eq("contractType", contractType)).list();
		if(list == null || list.size() == 0){
			cover = tPmContractPriceCoverService.init(contractType, contractId);
		}else{
			cover = list.get(0);
		}
		
		// 封面
		request.setAttribute("cover", cover);
        request.setAttribute("contractId", contractId);
		request.setAttribute("CONTRACT_BUY", ProjectConstant.CONTRACT_BUY);
        request.setAttribute("CONTRACT_TECH", ProjectConstant.CONTRACT_TECH);
		
		// 采购类价款计算书不需要计算
        if (!ProjectConstant.CONTRACT_BUY.equals(contractType) && !ProjectConstant.CONTRACT_TECH.equals(contractType)) {
			// 计算有多少个tab页
			List<TPmContractPriceMasterEntity> masters = systemService.getSession().createCriteria(TPmContractPriceMasterEntity.class)
					.add(Restrictions.eq("tpId", cover.getId()))
					.addOrder(Order.asc("sortCode")).addOrder(Order.desc("parent")).list();
			// 主表
			TPmContractPriceMasterEntity master = new TPmContractPriceMasterEntity();
			if(masters.size() != 0){
				master = masters.get(0);
				request.setAttribute("master", master);
			}			
			
			// 明细表
			List<Map<String, Object>> deatails = new ArrayList<Map<String,Object>>();
			// 其它表
			Map<String, Object> other = new HashMap<String, Object>();
			String otherName = "";
			String otherUrl = ProjectConstant.CONTRACT_PRICE_DETAIL.get("4").get("url") + "?goUpdate&tpId="+cover.getId()+"&read="+read;
			for(int i = 1; i < masters.size(); i++){
				TPmContractPriceMasterEntity detail = masters.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("name", detail.getPriceBudgetName());
				map.put("url", ProjectConstant.CONTRACT_PRICE_DETAIL.get(detail.getShowFlag()).get("url")
						+"?goUpdate&tpId="+cover.getId()+"&typeId="+detail.getPriceBudgetId()+"&read="+read);
				
				if(master.getId().equals(detail.getParent())){
					deatails.add(map);
				}else{
					otherName += (detail.getPriceBudgetName()+"、");
				}
			}
			request.setAttribute("tabs", deatails);
			
			if(otherName.length() > 0){
				other.put("name", otherName.substring(0, otherName.length()-1));
			}else{
				other.put("name", otherName);
			}
			
            String encode = "";
            try {
            	if(otherName.length() > 0){
            		encode = URLEncoder.encode(otherName.substring(0, otherName.length() - 1), "UTF-8");
    			}else{
    				encode = URLEncoder.encode(otherName, "UTF-8");
    			}
                
                encode = URLEncoder.encode(encode, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            other.put("url", otherUrl + "&title=" + encode);
			request.setAttribute("other", other);
		}
		
		return new ModelAndView("com/kingtake/project/price/tPmContractPriceCoverTab");
	}


	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmContractPriceCoverEntity tPmContractPriceCover, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmContractPriceCover = systemService.getEntity(TPmContractPriceCoverEntity.class, tPmContractPriceCover.getId());
		message = "删除成功";
		try{
			tPmContractPriceCoverService.delete(tPmContractPriceCover);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	
	/**
	 * 更新
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmContractPriceCoverEntity tPmContractPriceCover, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		TPmContractPriceCoverEntity t = tPmContractPriceCoverService.get(
				TPmContractPriceCoverEntity.class, tPmContractPriceCover.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPmContractPriceCover, t);
			tPmContractPriceCoverService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmContractPriceCoverEntity tPmContractPriceCover, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmContractPriceCover.getId())) {
			tPmContractPriceCover = tPmContractPriceCoverService.getEntity(TPmContractPriceCoverEntity.class, tPmContractPriceCover.getId());
			req.setAttribute("cover", tPmContractPriceCover);
			req.setAttribute("title", ProjectConstant.planContractMap.get(tPmContractPriceCover.getContractType()));
			req.setAttribute("read", req.getParameter("read"));
			// 合同信息
			TPmOutcomeContractApprEntity contract = systemService.get(
					TPmOutcomeContractApprEntity.class, tPmContractPriceCover.getContractId());
			// 项目信息
			TPmProjectEntity project = contract.getProject();
			req.setAttribute("project", project);
		}
		return new ModelAndView("com/kingtake/project/price/tPmContractPriceCover-update");
	}

    /**
     * 预算导入
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String tpId = request.getParameter("tpId");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            try {
                HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
                tPmContractPriceCoverService.importExcel(wb, tpId);
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error("文件导入失败！", e);
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("tpId", req.getParameter("tpId"));
        return new ModelAndView("com/kingtake/project/price/tPmContractPriceCover-upload");
    }
}
