package com.anxi.activiti.web.workflow.controller;

import com.anxi.activiti.service.api.ActProcessService;
import com.anxi.activiti.vo.ActProcessDefinitionQuery;
import com.anxi.activiti.vo.ActProcessDefinitionVO;
import com.anxi.activiti.vo.ActProcessDeployDTO;
import com.anxi.activiti.vo.ActProcessInsVO;
import com.anxi.activiti.vo.ActProcessInstanceQuery;
import com.anxi.activiti.vo.DeleteProcInsDTO;
import com.anxi.activiti.vo.ProcessResourceReadDTO;
import com.anxi.activiti.vo.SetProcessCategoryDTO;
import com.anxi.activiti.vo.SetProcessStateDTO;
import com.anxi.activiti.web.conf.FormPostParam;
import com.anxi.activiti.web.conf.UrlParam;
import com.anxi.activiti.web.workflow.util.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

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
    public String processList(@FormPostParam ActProcessDefinitionQuery queryParam, Model model) {
        PageInfo<ActProcessDefinitionVO> actProcessPageInfo = actProcessService.processDefinitionQuery(queryParam);
        Page<ActProcessDefinitionVO> page = new Page<>(queryParam.getPageNum(), queryParam.getPageSize(), actProcessPageInfo.getTotal(), actProcessPageInfo.getList());
        model.addAttribute("queryParam", queryParam);
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

    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public String deploy(String category, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        ActProcessDeployDTO actProcessDeployDTO = new ActProcessDeployDTO();
        actProcessDeployDTO.setCategory(category);
        actProcessDeployDTO.setFileName(fileName);
        actProcessDeployDTO.setFileContentBytes(file.getBytes());
        actProcessService.processDeploy(actProcessDeployDTO);
        return "redirect:/workflow/process/";
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
    public String updateState(@PathVariable("state") boolean isActivate, String procDefId) {
        actProcessService.setProcessState(new SetProcessStateDTO(procDefId, isActivate));
        return "redirect:/workflow/process/";
    }

    /**
     * 部署流程
     */
    @RequestMapping(value = "/deploy", method = RequestMethod.GET)
    public String deploy() {
        return "modules/act/actProcessDeploy";
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
    public String deleteProcIns(String procInsId, String reason) {
        actProcessService.deleteProcIns(new DeleteProcInsDTO(procInsId, reason));
        return "redirect:/workflow/process/running";
    }

}
