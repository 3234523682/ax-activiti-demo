package com.anxi.activiti.web.workflow.controller;

import com.anxi.activiti.service.api.ActIdentityService;
import com.anxi.activiti.service.api.ActProcessService;
import com.anxi.activiti.service.api.ActRepositoryService;
import com.anxi.activiti.service.api.ActTaskService;
import com.anxi.activiti.vo.ActHistoricActivityVO;
import com.anxi.activiti.vo.ActHistoricFlowQuery;
import com.anxi.activiti.vo.ActHistoricTaskVO;
import com.anxi.activiti.vo.ActModelNodeInfoVo;
import com.anxi.activiti.vo.ActProcessDefinitionQuery;
import com.anxi.activiti.vo.ActProcessDefinitionVO;
import com.anxi.activiti.vo.ActProcessStartDTO;
import com.anxi.activiti.vo.ActTaskClaimDTO;
import com.anxi.activiti.vo.ActTaskCompleteDTO;
import com.anxi.activiti.vo.ActTaskDeleteDTO;
import com.anxi.activiti.vo.ActTaskPageQuery;
import com.anxi.activiti.vo.ActTaskVO;
import com.anxi.activiti.vo.ActUserQuery;
import com.anxi.activiti.vo.ActUserVO;
import com.anxi.activiti.web.conf.FormPostParam;
import com.anxi.activiti.web.workflow.util.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 流程个人任务相关Controller
 */
@Controller
@RequestMapping(value = "/workflow/task")
public class ActTaskController {

    @Resource
    private ActTaskService actTaskService;

    @Resource
    private ActProcessService actProcessService;

    @Resource
    private ActIdentityService actIdentityService;

    @Resource
    private ActRepositoryService actRepositoryService;

    /**
     * 获取未签收任务列表
     */
    @RequestMapping(value = "awaitClaimTaskList")
    public String awaitClaimTaskList(@FormPostParam ActTaskPageQuery actTaskPageQuery, Model model) {
        PageInfo<ActTaskVO> actTaskPageInfo = actTaskService.pageFindAwaitClaimTask(actTaskPageQuery);
        Page<ActTaskVO> page = new Page<>(actTaskPageQuery.getPageNum(), actTaskPageQuery.getPageSize(), actTaskPageInfo.getTotal(), actTaskPageInfo.getList());
        model.addAttribute("queryParam", actTaskPageQuery);
        model.addAttribute("page", page);
        return "modules/act/actClaimTaskList";
    }

    /**
     * 获取已签收任务列表
     */
    @RequestMapping(value = "alreadyClaimTaskList")
    public String alreadyClaimTaskList(@FormPostParam ActTaskPageQuery actTaskPageQuery, HttpServletRequest request, Model model) {
        ActUserVO actUser = (ActUserVO) request.getSession().getAttribute("actUser");
        if (null != actUser) {
            actTaskPageQuery.setUserId(actUser.getUserId());
        }
        PageInfo<ActTaskVO> actTaskPageInfo = actTaskService.pageFindAlreadyClaimTask(actTaskPageQuery);
        Page<ActTaskVO> page = new Page<>(actTaskPageQuery.getPageNum(), actTaskPageQuery.getPageSize(), actTaskPageInfo.getTotal(), actTaskPageInfo.getList());
        model.addAttribute("queryParam", actTaskPageQuery);
        model.addAttribute("page", page);
        return "modules/act/actAlreadyTaskList";
    }

    /**
     * 获取已办任务
     */
    @RequestMapping(value = "historicTaskList")
    public String historicTaskList(@FormPostParam ActTaskPageQuery actTaskPageQuery, Model model) {
        PageInfo<ActHistoricTaskVO> actHistoricTaskPageInfo = actTaskService.pageHistoricTask(actTaskPageQuery);
        Page<ActHistoricTaskVO> page = new Page<>(actTaskPageQuery.getPageNum(), actTaskPageQuery.getPageSize(), actHistoricTaskPageInfo.getTotal(), actHistoricTaskPageInfo.getList());
        model.addAttribute("queryParam", actTaskPageQuery);
        model.addAttribute("page", page);
        return "modules/act/actHistoricTaskList";
    }

    /**
     * 获取流转历史列表
     */
    @RequestMapping(value = "histoicFlow")
    public String histoicFlow(ActHistoricFlowQuery actHistoricFlowQuery, Model model) {
        List<ActHistoricActivityVO> actHistoricActivities = actTaskService.historicFlowList(actHistoricFlowQuery);
        model.addAttribute("histoicFlowList", actHistoricActivities);
        return "modules/act/actTaskHistoricFlow";
    }

    /**
     * 获取流程列表
     */
    @RequestMapping(value = "process")
    public String processList(ActProcessDefinitionQuery queryParam, Model model) {
        PageInfo<ActProcessDefinitionVO> actProcessDefinitionPageInfo = actProcessService.processDefinitionQuery(queryParam);
        Page<ActProcessDefinitionVO> page = new Page<>(queryParam.getPageNum(), queryParam.getPageSize(), actProcessDefinitionPageInfo.getTotal(), actProcessDefinitionPageInfo.getList());
        model.addAttribute("queryParam", queryParam);
        model.addAttribute("page", page);
        return "modules/act/actTaskProcessList";
    }

    /**
     * 启动流程
     */
    @RequestMapping(value = "start", method = RequestMethod.GET)
    public String start(ActProcessStartDTO startParam, Model model) {
        model.addAttribute("startParam", startParam);
        model.addAttribute("actUsers", actIdentityService.queryAllActUser(new ActUserQuery()));
        return "modules/act/actTaskStart";
    }

    /**
     * 启动流程
     */
    @ResponseBody
    @RequestMapping(value = "start", method = RequestMethod.POST)
    public String start(@FormPostParam ActProcessStartDTO actProcessStartDTO) throws IOException {
        actTaskService.startProcess(actProcessStartDTO);
        return "true";//adminPath + "/act/task";
    }

    /**
     * 签收任务
     */
    @ResponseBody
    @RequestMapping(value = "claim")
    public String claim(ActTaskClaimDTO actTaskClaimVo) {
        actTaskService.claimTask(actTaskClaimVo);
        return "true";//adminPath + "/act/task";
    }

    /**
     * 完成任务
     */
    @ResponseBody
    @RequestMapping(value = "complete")
    public String complete(@FormPostParam ActTaskCompleteDTO actTaskCompleteVo) throws IOException {
        actTaskService.completeTask(actTaskCompleteVo);
        return "true";//adminPath + "/act/task";
    }

    /**
     * 删除任务
     */
    @RequestMapping(value = "deleteTask")
    public String deleteTask(ActTaskDeleteDTO actTaskDeleteVo) {
        actTaskService.deleteTask(actTaskDeleteVo);
        return "/act/task";
    }

    @RequestMapping(value = "actTaskHandle", method = RequestMethod.GET)
    public String actTaskHandle(String taskId, Model model) {
        ActTaskVO actTask = actTaskService.getTaskById(taskId);
        List<ActModelNodeInfoVo> actModelNodes = actRepositoryService.getActModelNodes(actTask.getTaskProcDefId());
        model.addAttribute("actTask", actTask);
        model.addAttribute("actModelNodes", actModelNodes);
        return "modules/act/actTaskHandle";
    }
}
