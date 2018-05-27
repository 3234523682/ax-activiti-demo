package com.hc.lease.workflow.service.test;

import com.anxi.activiti.constant.ActAuditType;
import com.anxi.activiti.service.api.ActProcessService;
import com.anxi.activiti.service.api.ActRepositoryService;
import com.anxi.activiti.service.api.ActTaskService;
import com.anxi.activiti.vo.ActModelVO;
import com.anxi.activiti.vo.ActProcessDefinitionQuery;
import com.anxi.activiti.vo.ActProcessDefinitionVO;
import com.anxi.activiti.vo.ActProcessStartDTO;
import com.anxi.activiti.vo.ActTaskClaimDTO;
import com.anxi.activiti.vo.ActTaskCompleteDTO;
import com.anxi.activiti.vo.ActTaskPageQuery;
import com.anxi.activiti.vo.ActTaskVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LJ on 2018/3/26
 */
@Slf4j
public class ActServiceTest extends BaseJunit4Test {

    @Resource
    private ActProcessService actProcessService;

    @Resource
    private ActRepositoryService actRepositoryService;

    @Resource
    private ActTaskService actTaskService;


    @Test
    public void findAllModels() {
        List<ActModelVO> allModels = actRepositoryService.findAllModels();
        log.info("allModels ：{}", allModels);
    }

    @Test
    public void processDefinitionQuery() {
        PageInfo<ActProcessDefinitionVO> actProcessPageInfo = actProcessService.processDefinitionQuery(new ActProcessDefinitionQuery(null, 1, 10));
        log.info("actProcessPageInfo ：{}", actProcessPageInfo);
    }

    @Test
    public void actTaskTest() throws IOException {
        ActProcessStartDTO actProcessStartVo = new ActProcessStartDTO("admin", "purchase_requisition", "act工作流流程测试");
        actTaskService.startProcess(actProcessStartVo);
    }

    @Test
    public void pageFindAlreadyClaimTask() {
        ActTaskPageQuery actTaskPageQuery = new ActTaskPageQuery(1, 10, "admin", "purchase_requisition");
        PageInfo<ActTaskVO> taskPageInfo = actTaskService.pageFindAlreadyClaimTask(actTaskPageQuery);
        log.info("taskPageInfo :{}", taskPageInfo);
    }

    @Test
    public void pageFindAwaitClaimTask() {
        ActTaskPageQuery actTaskPageQuery = new ActTaskPageQuery(1, 10, "admin", "purchase_requisition");
        PageInfo<ActTaskVO> taskPageInfo = actTaskService.pageFindAwaitClaimTask(actTaskPageQuery);
        log.info("taskPageInfo :{}", taskPageInfo);
    }

    @Test
    public void claimTask() {
        ActTaskPageQuery actTaskPageQuery = new ActTaskPageQuery(1, 10, "admin", "purchase_requisition");
        PageInfo<ActTaskVO> taskPageInfo = actTaskService.pageFindAwaitClaimTask(actTaskPageQuery);
        ActTaskClaimDTO actTaskClaimVo = new ActTaskClaimDTO(taskPageInfo.getList().get(0).getTaskId(), "admin");
        actTaskService.claimTask(actTaskClaimVo);
    }

    @Test
    public void completeTask01() throws IOException {
        ActTaskPageQuery actTaskPageQuery = new ActTaskPageQuery(1, 10, "admin", "purchase_requisition");
        PageInfo<ActTaskVO> taskPageInfo = actTaskService.pageFindAwaitClaimTask(actTaskPageQuery);
        ActTaskVO task = taskPageInfo.getList().get(0);
        Map<String, Object> vars = new HashMap<>();
        vars.put("departmentHead", "Tom");
        ActTaskCompleteDTO actTaskCompleteVo = new ActTaskCompleteDTO(task.getTaskId(), task.getTaskProcInsId(), null, null, vars);
        actTaskService.completeTask(actTaskCompleteVo);
    }

    @Test
    public void completeTask02() throws IOException {
        ActTaskPageQuery actTaskPageQuery = new ActTaskPageQuery(1, 10, "Tom", "purchase_requisition");
        PageInfo<ActTaskVO> taskPageInfo = actTaskService.pageFindAwaitClaimTask(actTaskPageQuery);
        ActTaskVO task = taskPageInfo.getList().get(0);
        Map<String, Object> vars =  new HashMap<>();
        vars.put("pass", 0);
        ActTaskCompleteDTO actTaskCompleteVo = new ActTaskCompleteDTO(task.getTaskId(), task.getTaskProcInsId(), ActAuditType.reject.getValue(), "数据有异常，请修正！！", vars);
        actTaskService.completeTask(actTaskCompleteVo);
    }

    @Test
    public void completeTask03() throws IOException {
        ActTaskPageQuery actTaskPageQuery = new ActTaskPageQuery(1, 10, "admin", "purchase_requisition");
        PageInfo<ActTaskVO> taskPageInfo = actTaskService.pageFindAwaitClaimTask(actTaskPageQuery);
        ActTaskVO task = taskPageInfo.getList().get(0);
        Map<String, Object> vars = task.getProcessVariables();
        ActTaskCompleteDTO actTaskCompleteVo = new ActTaskCompleteDTO(task.getTaskId(), task.getTaskProcInsId(), null, null, vars);
        actTaskService.completeTask(actTaskCompleteVo);
    }

    @Test
    public void completeTask04() throws IOException {
        ActTaskPageQuery actTaskPageQuery = new ActTaskPageQuery(1, 10, "Tom", "purchase_requisition");
        PageInfo<ActTaskVO> taskPageInfo = actTaskService.pageFindAwaitClaimTask(actTaskPageQuery);
        ActTaskVO task = taskPageInfo.getList().get(0);
        Map<String, Object> vars =  new HashMap<>();
        vars.put("pass", 1);
        ActTaskCompleteDTO actTaskCompleteVo = new ActTaskCompleteDTO(task.getTaskId(), task.getTaskProcInsId(), ActAuditType.consent.getValue(), "审批通过！！", vars);
        actTaskService.completeTask(actTaskCompleteVo);
    }
}
