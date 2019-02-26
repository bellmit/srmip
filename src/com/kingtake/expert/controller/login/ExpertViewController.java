package com.kingtake.expert.controller.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.controller.core.LoginController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.approval.TPmProjectApprovalEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.member.TPmProjectMemberEntity;
import com.kingtake.project.entity.price.TPmContractPriceCoverEntity;
import com.kingtake.project.entity.price.TPmContractPriceMasterEntity;
import com.kingtake.project.entity.task.TPmTaskEntity;
import com.kingtake.project.entity.task.TPmTaskNodeEntity;
import com.kingtake.project.service.declare.army.TBPmDeclareArmyResearchServiceI;
import com.kingtake.project.service.price.TPmContractPriceCoverServiceI;

/**
 * 登陆初始化控制器
 * 
 * @author 张代浩
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/expertViewController")
public class ExpertViewController extends BaseController {
    private final Logger log = Logger.getLogger(LoginController.class);

    @Autowired
    private SystemService systemService;
    private final String message = null;

    @Autowired
    private TBPmDeclareArmyResearchServiceI tBPmDeclareArmyResearchService;

    @Autowired
    private TPmContractPriceCoverServiceI tPmContractPriceCoverService;

    /**
     * 项目基本信息页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goProjectInfoForExpert")
    public ModelAndView goUpdateForResearchGroup(TPmProjectEntity tPmProject, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmProject.getId())) {
            tPmProject = this.systemService.getEntity(TPmProjectEntity.class, tPmProject.getId());
            req.setAttribute("tPmProjectPage", tPmProject);
            req.setAttribute("planContractFlag", ProjectConstant.planContractMap.get(tPmProject.getPlanContractFlag()));
            CriteriaQuery mcq = new CriteriaQuery(TPmProjectEntity.class);
            mcq.eq("mergeProject.id", tPmProject.getId());
            mcq.add();
            List<TPmProjectEntity> mergeProjList = systemService.getListByCriteriaQuery(mcq, false);
            req.setAttribute("mergeProjList", mergeProjList);
        }
        return new ModelAndView("com/kingtake/project/manage/tPmProjectInfoForExpert");
    }

    /**
     * 项目组成员列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectMemberListForExpert")
    public ModelAndView tPmProjectMemberListForExpert(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/member/tPmProjectMemberListForExpert");
    }

    /**
     * 项目申报列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmDeclareForExpert")
    public ModelAndView tPmDeclareForExpert(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        if (project != null && project.getProjectType() != null) {
            String typeName = project.getProjectType().getProjectTypeName();
            if (typeName.indexOf("军内科研") != -1) {
                return new ModelAndView(
                        "redirect:tBPmDeclareArmyResearchController.do?tBPmDeclareArmyResearch&projectId=" + projectId
                                + "&read=1");
            } else if (typeName.indexOf("技术基础") != -1) {
                return new ModelAndView("redirect:tBPmDeclareTechnologyController.do?tBPmDeclareTechnology&projectId="
                        + projectId + "&read=1");
            } else if (typeName.indexOf("维修科研") != -1) {
                return new ModelAndView("redirect:tBPmDeclareRepairController.do?tBPmDeclareRepair&projectId="
                        + projectId + "&read=1");
            } else if (typeName.indexOf("后勤科研") != -1) {
                return new ModelAndView("redirect:tPmDeclareBackController.do?tPmDeclareBack&projectId=" + projectId
                        + "&read=1");
            }
        }
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/declare/tPmDeclareListForExpert");
    }

    /**
     * 跳转到课题组的立项论证编辑界面.
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectApprovalForExpert")
    public ModelAndView tPmProjectApprovalForExpert(TPmProjectApprovalEntity tPmProjectApproval,
            HttpServletRequest request) {
        if (StringUtil.isNotEmpty(tPmProjectApproval.getId())) {
            tPmProjectApproval = this.systemService.getEntity(TPmProjectApprovalEntity.class,
                    tPmProjectApproval.getId());
            request.setAttribute("tPmProjectApprovalPage", tPmProjectApproval);
        }
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            CriteriaQuery cq = new CriteriaQuery(TPmProjectApprovalEntity.class);
            cq.eq("project.id", projectId);
            cq.add();
            List<TPmProjectApprovalEntity> approvalList = systemService.getListByCriteriaQuery(cq, false);
            if (approvalList.size() > 0) {
                request.setAttribute("tPmProjectApprovalPage", approvalList.get(0));
            } else {
                TPmProjectEntity proj = this.systemService.get(TPmProjectEntity.class, projectId);
                TPmProjectApprovalEntity approvalEntity = new TPmProjectApprovalEntity();
                approvalEntity.setProject(proj);
                request.setAttribute("tPmProjectApprovalPage", approvalEntity);
            }
            TPmProjectEntity tPmProject = systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("tPmProjectPage", tPmProject);
            CriteriaQuery mcq = new CriteriaQuery(TPmProjectMemberEntity.class);
            mcq.eq("project.id", projectId);
            mcq.add();
            List<TPmProjectMemberEntity> memberList = systemService.getListByCriteriaQuery(mcq, false);
            request.setAttribute("memberList", memberList);
        }
        return new ModelAndView("com/kingtake/project/approval/tPmProjectApprovalForExpert");
    }

    /**
     * 减免垫支信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmAbateForExpert")
    public ModelAndView tPmAbateListForExpert(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/abatepay/tPmAbateListInfoForExpert");
    }

    /**
     * 垫支信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmPayfirstForExpert")
    public ModelAndView tPmPayfirstListForExpert(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/abatepay/tPmPayfirstListInfoForExpert");
    }
    
    /**
     * 进账合同审批列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeContractApprForExpert")
    public ModelAndView tPmIncomeContractAppr(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprListForExpert");
    }

    /**
     * 任务管理编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmTaskForExpert")
    public ModelAndView tPmTaskForExpert(TPmTaskEntity tPmTask, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmTask.getId())) {
            tPmTask = systemService.getEntity(TPmTaskEntity.class, tPmTask.getId());
            req.setAttribute("tPmTaskPage", tPmTask);
        }
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            CriteriaQuery cq = new CriteriaQuery(TPmTaskEntity.class);
            cq.eq("project.id", projectId);
            cq.add();
            List<TPmTaskEntity> taskList = this.systemService.getListByCriteriaQuery(cq, false);
            if (taskList.size() > 0) {
                req.setAttribute("tPmTaskPage", taskList.get(0));
            } else {
                TPmProjectEntity proj = this.systemService.get(TPmProjectEntity.class, projectId);
                TPmTaskEntity tPmTaskEntity = new TPmTaskEntity();
                tPmTaskEntity.setProject(proj);
                req.setAttribute("tPmTaskPage", tPmTaskEntity);
            }
        }
        return new ModelAndView("com/kingtake/project/task/tPmTaskForExpert");
    }

    /**
     * 加载明细列表[任务节点管理]
     * 
     * @return
     */
    @RequestMapping(params = "tPmTaskNodeListForExpert")
    public ModelAndView tPmTaskNodeListForExpert(TPmTaskEntity tPmTask, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tPmTask.getId();
        //===================================================================================
        //查询-任务节点管理
        String hql0 = "from TPmTaskNodeEntity where 1 = 1 AND t_P_ID = ? ";
        try {
            List<TPmTaskNodeEntity> tPmTaskNodeEntityList = systemService.findHql(hql0, id0);
            req.setAttribute("tPmTaskNodeList", tPmTaskNodeEntityList);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessException("查询任务节点失败", e);
        }
        return new ModelAndView("com/kingtake/project/task/tPmTaskNodeListForExpert");
    }

    /**
     * 来款节点管理列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeApplyForExpert")
    public ModelAndView tPmIncomeNodeForExpert(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeApplyListForExpert");
    }

    /**
     * 支付节点管理列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmPayNodeForExpert")
    public ModelAndView tPmPayNodeForExpert(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/m2pay/tPmPayNodeListForExpert");
    }

    /**
     * 项目预算管理列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectFundsApprForExpert")
    public ModelAndView tPmProjectFundsApprForExpert(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        TPmProjectEntity project = systemService.getEntity(TPmProjectEntity.class, projectId);
        request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
        request.setAttribute("projectId", projectId);
        request.setAttribute("planContractFlag", project.getPlanContractFlag());
        request.setAttribute("accountingCode", project.getAccountingCode());
        request.setAttribute("PLANFLAG", ProjectConstant.PROJECT_PLAN);
        return new ModelAndView("com/kingtake/project/funds/tPmProjectFundsApprListForExpert");
    }

    /**
     * 出账合同审批列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmOutcomeContractApprForExpert")
    public ModelAndView tPmOutcomeContractAppr(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprListForExpert");
    }

    /**
     * 合同价款计算书 ：主页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmContractPriceCoverForExpert")
    public ModelAndView tPmContractPriceCoverForExpert(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        String projectId = request.getParameter("projectId");
        // 是否只读的标志
        String read = StringUtil.isEmpty(request.getParameter("read")) ? SrmipConstants.NO : request
                .getParameter("read");
        request.setAttribute("read", read);

        TPmContractPriceCoverEntity cover = null;
        // 查询是否已经生产合同价款书
        List<TPmContractPriceCoverEntity> list = systemService.getSession()
                .createCriteria(TPmContractPriceCoverEntity.class).add(Restrictions.eq("projectId", projectId))
                .add(Restrictions.eq("priceType", type)).list();
        if (list == null || list.size() == 0) {
            cover = tPmContractPriceCoverService.init(type, projectId);
        } else {
            cover = list.get(0);
        }

        // 封面
        request.setAttribute("cover", cover);

        // 计算有多少个tab页
        List<TPmContractPriceMasterEntity> masters = systemService.getSession()
                .createCriteria(TPmContractPriceMasterEntity.class).add(Restrictions.eq("tpId", cover.getId()))
                .addOrder(Order.asc("sortCode")).addOrder(Order.desc("parent")).list();
        // 主表
        TPmContractPriceMasterEntity master = masters.get(0);
        request.setAttribute("master", master);

        // 明细表
        List<Map<String, Object>> deatails = new ArrayList<Map<String, Object>>();
        // 其它表
        Map<String, Object> other = new HashMap<String, Object>();
        String otherName = "";
        String otherUrl = ProjectConstant.CONTRACT_PRICE_DETAIL.get("4").get("url") + "?goUpdate&tpId=" + cover.getId()
                + "&read=" + read;
        for (int i = 1; i < masters.size(); i++) {
            TPmContractPriceMasterEntity detail = masters.get(i);
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("name", detail.getPriceBudgetName());
            map.put("url", ProjectConstant.CONTRACT_PRICE_DETAIL.get(detail.getShowFlag()).get("url")
                    + "?goUpdate&tpId=" + cover.getId() + "&typeId=" + detail.getPriceBudgetId() + "&read=" + read);

            if (master.getId().equals(detail.getParent())) {
                deatails.add(map);
            } else {
                otherName += (detail.getPriceBudgetName() + "、");
            }
        }
        request.setAttribute("tabs", deatails);

        other.put("name", otherName.substring(0, otherName.length() - 1));
        other.put("url", otherUrl + "&title=" + otherName.substring(0, otherName.length() - 1));
        request.setAttribute("other", other);

        return new ModelAndView("com/kingtake/project/price/tPmContractPriceCoverTab");
    }
    
    /**
     * 申报依据 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectReportForExpert")
    public ModelAndView tPmProjectMember(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        String planContractFlag = request.getParameter("planContractFlag");
        if (ProjectConstant.PROJECT_CONTRACT.equals(planContractFlag)) {
            return new ModelAndView("redirect:tPmProjectController.do?goUpdateForResearchGroup&id="+ projectId);
        }
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/report/tPmProjectReportListForExpert");
    }

}