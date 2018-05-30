package com.anxi.activiti.service.impl;

import com.anxi.activiti.constant.ActCommonActivityType;
import com.anxi.activiti.service.api.ActTaskService;
import com.anxi.activiti.vo.ActHistoricActivityVO;
import com.anxi.activiti.vo.ActHistoricFlowQuery;
import com.anxi.activiti.vo.ActHistoricTaskVO;
import com.anxi.activiti.vo.ActProcessStartDTO;
import com.anxi.activiti.vo.ActTaskClaimDTO;
import com.anxi.activiti.vo.ActTaskCompleteDTO;
import com.anxi.activiti.vo.ActTaskDeleteDTO;
import com.anxi.activiti.vo.ActTaskPageQuery;
import com.anxi.activiti.vo.ActTaskVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.engine.task.TaskInfoQuery;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LJ on 2018/3/30
 */
@Slf4j
@Service("actTaskService")
public class ActTaskServiceImpl implements ActTaskService {

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private IdentityService identityService;

    @Resource
    private RuntimeService runtimeService;

    @Override
    public PageInfo<ActTaskVO> pageFindAwaitClaimTask(ActTaskPageQuery actTaskPageQuery) {
        TaskQuery todoTaskQuery = taskService.createTaskQuery();
        if (!StringUtils.isEmpty(actTaskPageQuery.getUserId())) {
            todoTaskQuery.taskInvolvedUser(actTaskPageQuery.getUserId());
        }
        todoTaskQuery.active().includeProcessVariables().orderByTaskCreateTime().desc();
        return this.pageFindActTask(todoTaskQuery, actTaskPageQuery);
    }

    @Override
    public PageInfo<ActTaskVO> pageFindAlreadyClaimTask(ActTaskPageQuery actTaskPageQuery) {
        String userId = actTaskPageQuery.getUserId();
        TaskQuery toClaimQuery = taskService.createTaskQuery();
        if (!StringUtils.isEmpty(userId)) {
            toClaimQuery.taskAssignee(userId);
        }
        toClaimQuery.includeProcessVariables().active().orderByTaskCreateTime().desc();
        return this.pageFindActTask(toClaimQuery, actTaskPageQuery);
    }

    @Override
    public PageInfo<ActHistoricTaskVO> pageHistoricTask(ActTaskPageQuery actTaskPageQuery) {
        String userId = actTaskPageQuery.getUserId();
        int pageNum = actTaskPageQuery.getPageNum();
        int pageSize = actTaskPageQuery.getPageSize();
        HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery();
        if (!StringUtils.isEmpty(userId)) {
            histTaskQuery.taskCandidateUser(userId);
        }
        histTaskQuery.finished().includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
        //设置查询条件
        this.taskQueryConditionSet(histTaskQuery, actTaskPageQuery);
        PageInfo<ActHistoricTaskVO> pageTasks = new PageInfo<>();
        pageTasks.setTotal(histTaskQuery.count());
        List<HistoricTaskInstance> historicTaskInstances = histTaskQuery.listPage((pageNum - 1) * pageSize, pageSize);
        List<ActHistoricTaskVO> actHistoricTasks = new ArrayList<>(historicTaskInstances.size());
        ActHistoricTaskVO actHistoricTask;
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().asc();
        HistProcInsQueryEntity histProcInsQueryEntity = new HistProcInsQueryEntity();
        HistoricProcessInstance historicProcessInstance;
        for (HistoricTaskInstance hi : historicTaskInstances) {
            histProcInsQueryEntity.setProcessInstanceId(hi.getProcessInstanceId());
            histProcInsQueryEntity.setTaskInitiator(actTaskPageQuery.getTaskInitiator());
            histProcInsQueryEntity.setTaskProcessInstanceName(actTaskPageQuery.getTaskProcessInstanceName());
            this.historicProcessInstanceQuerySet(historicProcessInstanceQuery, histProcInsQueryEntity);
            historicProcessInstance = historicProcessInstanceQuery.singleResult();
            if (null != historicProcessInstance) {
                actHistoricTask = new ActHistoricTaskVO(hi.getStartTime(), hi.getEndTime(), hi.getClaimTime(), hi.getDurationInMillis(), hi.getWorkTimeInMillis(), this.isProcessEnd(hi.getProcessInstanceId()));
                actHistoricTask.setActTaskInfo(this.actTaskGenerate(hi, historicProcessInstance));
                actHistoricTasks.add(actHistoricTask);
            }
        }
        pageTasks.setList(actHistoricTasks);
        return pageTasks;
    }

    @Override
    public List<ActHistoricActivityVO> historicFlowList(ActHistoricFlowQuery actHistoricFlowQuery) {
        String procInsId = actHistoricFlowQuery.getProcInsId();
        String startActId = actHistoricFlowQuery.getStartActId();
        String endActId = actHistoricFlowQuery.getEndActId();
        List<ActHistoricActivityVO> actList = new ArrayList<>();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId).orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
        boolean start = false;
        Map<String, Integer> actMap = new HashMap<>();
        ActHistoricActivityVO historicActivity;
        for (int i = 0; i < list.size(); i++) {
            HistoricActivityInstance histIns = list.get(i);
            // 过滤开始节点前的节点
            if (StringUtils.isNotBlank(startActId) && startActId.equals(histIns.getActivityId())) {
                start = true;
            }
            if (StringUtils.isNotBlank(startActId) && !start) {
                continue;
            }
            // 只显示开始节点和结束节点，并且执行人不为空的任务
            if (StringUtils.isNotBlank(histIns.getAssignee()) || ActCommonActivityType.startEvent.getValue().equals(histIns.getActivityType()) || ActCommonActivityType.endEvent.getValue().equals(histIns.getActivityType())) {
                // 给节点增加一个序号
                Integer actNum = actMap.get(histIns.getActivityId());
                if (actNum == null) {
                    actMap.put(histIns.getActivityId(), actMap.size());
                }
                historicActivity = this.actHistoricActivityGenerate(histIns);
                // 获取流程发起人名称
                if (ActCommonActivityType.startEvent.getValue().equals(histIns.getActivityType())) {
                    HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(histIns.getProcessInstanceId()).singleResult();
                    if (null != hi) {
                        if (StringUtils.isNotBlank(hi.getStartUserId())) {
                            historicActivity.setAssignee(hi.getStartUserId());
                            //historicActivity.setAssigneeName(user.getName());
                        }
                    }
                }
                // 获取意见评论内容
                if (StringUtils.isNotBlank(histIns.getTaskId())) {
                    List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
                    if (commentList.size() > 0) {
                        historicActivity.setAuditType(commentList.get(0).getFullMessage());
                        historicActivity.setComment(commentList.size() >= 2 ? commentList.get(1).getFullMessage() : null);
                    }
                }
                actList.add(historicActivity);
            }
            // 过滤结束节点后的节点
            if (StringUtils.isNotBlank(endActId) && endActId.equals(histIns.getActivityId())) {
                boolean bl = false;
                Integer actNum = actMap.get(histIns.getActivityId());
                // 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
                for (int j = i + 1; j < list.size(); j++) {
                    HistoricActivityInstance hi = list.get(j);
                    Integer actNumA = actMap.get(hi.getActivityId());
                    if ((actNumA != null && actNumA < actNum) || StringUtils.equals(hi.getActivityId(), histIns.getActivityId())) {
                        bl = true;
                    }
                }
                if (!bl) {
                    break;
                }
            }
        }
        return actList;
    }

    @Override
    @Transactional
    public String startProcess(ActProcessStartDTO actProcessStartVo) throws IOException {
        String processStartUserId = actProcessStartVo.getProcessStartUserId();
        String procDefKey = actProcessStartVo.getProcDefKey();
        String processTitle = actProcessStartVo.getProcessTitle();
        Map<String, Object> vars = new HashMap<>();
        String flowParamJsonStr = actProcessStartVo.getFlowParamJsonStr();
        if (!StringUtils.isEmpty(flowParamJsonStr)) {
            ObjectMapper mapper = new ObjectMapper();
            vars = mapper.readValue(flowParamJsonStr, new TypeReference<HashMap<String, Object>>() {
            });
        }

        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(processStartUserId);

        // 启动流程
        ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, vars);
        runtimeService.setProcessInstanceName(procIns.getProcessInstanceId(), processTitle);
        return procIns.getId();
    }

    @Override
    public ActTaskVO getTaskById(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().asc().processInstanceId(task.getProcessInstanceId()).singleResult();
        return this.actTaskGenerate(task, historicProcessInstance);
    }

    @Override
    public Boolean isProcessEnd(String processInstanceId) {
        //正在执行的任务表查询
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return null == processInstance;
    }

    @Override
    @Transactional
    public void deleteTask(ActTaskDeleteDTO actTaskDeleteVo) {
        String taskId = actTaskDeleteVo.getTaskId();
        String deleteReason = actTaskDeleteVo.getDeleteReason();
        taskService.deleteTask(taskId, deleteReason);
    }

    @Override
    @Transactional
    public void claimTask(ActTaskClaimDTO actTaskClaimVo) {
        String taskId = actTaskClaimVo.getTaskId();
        String userId = actTaskClaimVo.getUserId();
        taskService.claim(taskId, userId);
    }

    @Override
    @Transactional
    public void completeTask(ActTaskCompleteDTO actTaskCompleteVo) throws IOException {
        String procInsId = actTaskCompleteVo.getProcInsId();
        String taskId = actTaskCompleteVo.getTaskId();
        String commentType = actTaskCompleteVo.getCommentType();
        String comment = actTaskCompleteVo.getComment();
        String flowParamJsonStr = actTaskCompleteVo.getFlowParamJsonStr();
        Map<String, Object> vars = new HashMap<>();
        if (!StringUtils.isEmpty(flowParamJsonStr)) {
            ObjectMapper mapper = new ObjectMapper();
            vars = mapper.readValue(flowParamJsonStr, new TypeReference<HashMap<String, Object>>() {
            });
        }
        // 添加意见
        if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(commentType) && StringUtils.isNotBlank(comment)) {
            //第一条添加审批结果
            taskService.addComment(taskId, procInsId, commentType);
            //第二条添加审批意见
            taskService.addComment(taskId, procInsId, comment);
        }
        // 提交任务
        taskService.complete(taskId, vars, true);
    }

    /**
     * act 任务查询条件设置
     */
    private void taskQueryConditionSet(TaskInfoQuery taskInfoQuery, ActTaskPageQuery actTaskPageQuery) {
        String procDefKey = actTaskPageQuery.getProcDefKey();
        String procDefName = actTaskPageQuery.getProcDefName();
        String taskNodeName = actTaskPageQuery.getTaskNodeName();
        Date beginDate = actTaskPageQuery.getBeginDate();
        Date endDate = actTaskPageQuery.getEndDate();
        // 设置查询条件
        if (StringUtils.isNotBlank(procDefKey)) {
            taskInfoQuery.processDefinitionKey(procDefKey);
        }
        if (StringUtils.isNotBlank(procDefName)) {
            taskInfoQuery.processDefinitionNameLike(procDefName);
        }
        if (StringUtils.isNotBlank(taskNodeName)) {
            taskInfoQuery.taskNameLike(taskNodeName);
        }
        if (beginDate != null) {
            taskInfoQuery.taskCreatedAfter(beginDate);
        }
        if (endDate != null) {
            taskInfoQuery.taskCreatedBefore(endDate);
        }
    }


    private PageInfo<ActTaskVO> pageFindActTask(TaskQuery taskQuery, ActTaskPageQuery actTaskPageQuery) {
        int pageNum = actTaskPageQuery.getPageNum();
        int pageSize = actTaskPageQuery.getPageSize();
        //设置查询条件
        this.taskQueryConditionSet(taskQuery, actTaskPageQuery);
        PageInfo<ActTaskVO> pageTasks = new PageInfo<>();
        pageTasks.setTotal(taskQuery.count());
        List<Task> tasks = taskQuery.listPage((pageNum - 1) * pageSize, pageSize);
        List<ActTaskVO> actTasks = new ArrayList<>(tasks.size());
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().asc();
        HistProcInsQueryEntity histProcInsQueryEntity = new HistProcInsQueryEntity();
        HistoricProcessInstance historicProcessInstance;
        for (Task task : tasks) {
            histProcInsQueryEntity.setProcessInstanceId(task.getProcessInstanceId());
            histProcInsQueryEntity.setTaskInitiator(actTaskPageQuery.getTaskInitiator());
            histProcInsQueryEntity.setTaskProcessInstanceName(actTaskPageQuery.getTaskProcessInstanceName());
            this.historicProcessInstanceQuerySet(historicProcessInstanceQuery, histProcInsQueryEntity);
            historicProcessInstance = historicProcessInstanceQuery.singleResult();
            if (null != historicProcessInstance) {
                actTasks.add(this.actTaskGenerate(task, historicProcessInstance));
            }
        }
        pageTasks.setList(actTasks);
        return pageTasks;
    }

    private void historicProcessInstanceQuerySet(HistoricProcessInstanceQuery historicProcessInstanceQuery, HistProcInsQueryEntity histProcInsQueryEntity) {
        if (!StringUtils.isEmpty(histProcInsQueryEntity.getProcessInstanceId())) {
            historicProcessInstanceQuery.processInstanceId(histProcInsQueryEntity.getProcessInstanceId());
        }
        if (!StringUtils.isEmpty(histProcInsQueryEntity.getTaskInitiator())) {
            historicProcessInstanceQuery.startedBy(histProcInsQueryEntity.getTaskInitiator());
        }
        if (!StringUtils.isEmpty(histProcInsQueryEntity.getTaskProcessInstanceName())) {
            historicProcessInstanceQuery.processInstanceNameLike(histProcInsQueryEntity.getTaskProcessInstanceName());
        }
    }

    private ActTaskVO actTaskGenerate(TaskInfo task, HistoricProcessInstance hi) {
        ActTaskVO actTask = new ActTaskVO(task.getId(), task.getDescription(), task.getName(), task.getAssignee(), task.getCreateTime(), task.getProcessDefinitionId(), task.getProcessInstanceId(), task.getProcessVariables());
      /*  List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
        if (null != identityLinksForTask && identityLinksForTask.size() > 0) {
            actTask.setNodeAffiliationGroup(identityLinksForTask.get(0).getGroupId());
        }*/
        actTask.setTaskExecutionId(task.getExecutionId());
        actTask.setTaskDefinitionKey(task.getTaskDefinitionKey());
        actTask.setTaskProcessName(hi.getProcessDefinitionName());
        actTask.setTaskName(hi.getName());
        actTask.setTaskInitiator(hi.getStartUserId());
        actTask.setTaskProcDefVersion(hi.getProcessDefinitionVersion());
        return actTask;
    }

    private ActHistoricActivityVO actHistoricActivityGenerate(HistoricActivityInstance histIns) {
        ActHistoricActivityVO actHistoricActivity = new ActHistoricActivityVO();
        actHistoricActivity.setId(histIns.getId());
        actHistoricActivity.setActivityId(histIns.getActivityId());
        actHistoricActivity.setActivityName(histIns.getActivityName());
        actHistoricActivity.setActivityType(histIns.getActivityType());
        actHistoricActivity.setAssignee(histIns.getAssignee());
        actHistoricActivity.setAssigneeName(histIns.getAssignee());
        actHistoricActivity.setProcessDefinitionId(histIns.getProcessDefinitionId());
        actHistoricActivity.setExecutionId(histIns.getExecutionId());
        actHistoricActivity.setProcessInstanceId(histIns.getProcessInstanceId());
        actHistoricActivity.setStartTime(histIns.getStartTime());
        actHistoricActivity.setEndTime(histIns.getEndTime());
        actHistoricActivity.setDurationInMillis(histIns.getDurationInMillis());
        return actHistoricActivity;
    }

    @Data
    private class HistProcInsQueryEntity {
        private String processInstanceId;
        private String taskProcessInstanceName;
        private String taskInitiator;

    }
}
