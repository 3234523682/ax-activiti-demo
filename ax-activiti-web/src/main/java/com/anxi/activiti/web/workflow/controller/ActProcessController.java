package com.anxi.activiti.web.workflow.controller;

import com.anxi.activiti.service.api.ActProcessService;
import com.anxi.activiti.vo.ActProcessDefinitionQuery;
import com.anxi.activiti.vo.ActProcessDefinitionVO;
import com.anxi.activiti.vo.ActProcessInsVO;
import com.anxi.activiti.vo.ActProcessInstanceQuery;
import com.anxi.activiti.vo.ActUserVO;
import com.anxi.activiti.vo.DeleteProcInsDTO;
import com.anxi.activiti.vo.ProcessResourceReadDTO;
import com.anxi.activiti.vo.SetProcessCategoryDTO;
import com.anxi.activiti.vo.SetProcessStateDTO;
import com.anxi.activiti.web.conf.FormPostParam;
import com.anxi.activiti.web.conf.UrlParam;
import com.anxi.activiti.web.workflow.util.Page;
import com.anxi.activiti.web.workflow.util.PageUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 流程定义相关Controller
 */
@Controller
@RequestMapping(value = "/workflow/process")
public class ActProcessController {

    @Resource
    private ActProcessService actProcessService;

    /**
     * 流程定义列表
     */
    @RequestMapping(value = {"list", ""})
    public String processList(@FormPostParam ActProcessDefinitionQuery actProcessDefinitionQuery, Model model) {
        PageInfo<ActProcessDefinitionVO> actProcessPageInfo = actProcessService.processDefinitionQuery(actProcessDefinitionQuery);
        Page<ActProcessDefinitionVO> page = new Page<>(actProcessDefinitionQuery.getPageNum(), actProcessDefinitionQuery.getPageSize(), actProcessPageInfo.getTotal(), actProcessPageInfo.getList());
        model.addAttribute("queryParam", actProcessDefinitionQuery);
        model.addAttribute("page", page);
        return "modules/act/actProcessList";
    }

    /**
     * 运行中的实例列表
     */
    @RequestMapping(value = "running")
    public String runningList(@FormPostParam ActProcessInstanceQuery queryParam, Model model) {
        PageInfo<ActProcessInsVO> processInstancePageInfo = actProcessService.processInstanceList(queryParam);
        Page<ActProcessInsVO> page = new Page<>(queryParam.getPageNum(), queryParam.getPageSize(), processInstancePageInfo.getTotal(), processInstancePageInfo.getList());
        model.addAttribute("queryParam", queryParam);
        model.addAttribute("page", page);
        return "modules/act/actProcessRunningList";
    }


    /**
     * 设置流程分类
     */
    @RequestMapping(value = "updateCategory")
    public String updateCategory(String procDefId, String category) {
        actProcessService.setProcessCategory(new SetProcessCategoryDTO(procDefId, category));
        return "redirect:/workflow/process/";
    }

    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "update/{state}")
    public String updateState(@PathVariable("state") boolean isActivate, String procDefId, RedirectAttributes redirectAttributes) {
        actProcessService.setProcessState(new SetProcessStateDTO(procDefId, isActivate));
        return "redirect:/workflow/process/";
    }

    /**
     * 将部署的流程转换为模型
     */
    @RequestMapping(value = "convert/toModel")
    public String convertToModel(String procDefId) throws Exception {
        actProcessService.convertToModel(procDefId);
        return "redirect:/workflow/model/list";
    }

    @RequestMapping(value = "actProcessResourceRead")
    public void actProcessResourceRead(@UrlParam ProcessResourceReadDTO processResourceReadDTO, HttpServletResponse response) throws IOException {
        byte[] bytes = actProcessService.actProcessResourceRead(processResourceReadDTO);
        response.getOutputStream().write(bytes);
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    @RequestMapping(value = "delete")
    public String delete(String deploymentId) {
        actProcessService.deleteDeployment(deploymentId);
        return "redirect:/workflow/process/";
    }

    /**
     * 删除流程实例
     *
     * @param procInsId 流程实例ID
     * @param reason    删除原因
     */
    @RequestMapping(value = "deleteProcIns")
    public String deleteProcIns(String procInsId, String reason, RedirectAttributes redirectAttributes) {
        actProcessService.deleteProcIns(new DeleteProcInsDTO(procInsId, reason));
        return "redirect:/workflow/process/running";
    }

}
