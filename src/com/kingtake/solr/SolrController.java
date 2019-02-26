package com.kingtake.solr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.tika.exception.TikaException;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.kingtake.common.constant.SrmipConstants;

/**
 * @Title: Controller
 * @Description: 全文检索
 * @author onlineGenerator
 * @date 2015-07-09 16:02:34
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/solrController")
public class SolrController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(SolrController.class);
    
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
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toPage")
	public ModelAndView toPage(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/solr/solr");
	}
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "search")
    public void search(HttpServletRequest request, HttpServletResponse response) {
    	int currentPage = request.getParameter("currentPage") == null ? 
    			1 : Integer.parseInt(request.getParameter("currentPage"));
    	int start = (currentPage-1) * SrmipConstants.PAGESIZE;
    	
    	QueryResponse query = SolrOperate.query(request.getParameter("key"), start);
    	SolrDocumentList list = query.getResults();
    	long count = list.getNumFound();
    	// 计算总页数
    	long pages = count%SrmipConstants.PAGESIZE == 0 ? 
    			count/SrmipConstants.PAGESIZE : count/SrmipConstants.PAGESIZE+1;
    	
    	// id = {title=[...], content=[...]}
    	Map<String, Map<String, List<String>>> map = query.getHighlighting();
    	if(map != null){
			for(SolrDocument doc : list){
				Map<String, List<String>> highFields = map.get(doc.get("id"));
				if(highFields != null){
					for(String field : highFields.keySet()){
						doc.setField(field, highFields.get(field).get(0));
					}
				}
                if (doc.containsKey("id")) {
                    String id = (String) doc.get("id");
                    TSFilesEntity file = this.systemService.get(TSFilesEntity.class, id);
                    if (file != null) {
                        doc.put("type", file.getType());
                    }
                }
			}
    	}
    	
    	PageList pageList = new PageList();
    	pageList.setCount((int) count);
    	pageList.setOffset((int)pages);
    	pageList.setResultList(list);
    	TagUtil.ListtoView(response, pageList);
    }

    /**
     * 初始化：将附件表中的所有文件添加到索引中
     * 
     * @param request
     * @param response
     * @throws TikaException 
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws SAXException 
     */

    @RequestMapping(params = "init")
    public void init(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException, TikaException, SAXException {
        // t_s_attachment 附件表
    	String sql = "SELECT ID, ATTACHMENTTITLE AS TITLE, REALPATH AS PATH, EXTEND FROM T_S_ATTACHMENT";
    	List<Map<String, Object>> list = systemService.findForJdbc(sql);
    	
    	String realPath = new File(request.getRealPath("/")).getParent();
    	SolrOperate.addFilesIndex(list, realPath);
    }

}
