package com.anxi.activiti.web.workflow.controller;

import com.anxi.activiti.service.api.ActIdentityService;
import com.anxi.activiti.vo.ActGroupQuery;
import com.anxi.activiti.vo.ActGroupSaveDTO;
import com.anxi.activiti.vo.ActGroupVO;
import com.anxi.activiti.vo.ActMembershipOperateDTO;
import com.anxi.activiti.vo.ActUserQuery;
import com.anxi.activiti.vo.ActUserSaveDTO;
import com.anxi.activiti.vo.ActUserVO;
import com.anxi.activiti.web.conf.FormPostParam;
import com.anxi.activiti.web.workflow.util.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by LJ on 2018/5/8
 */
@Controller
@RequestMapping(value = "/workflow/identity")
public class ActIdentityController {

    @Reference(version = "${activiti.service.version}")
    private ActIdentityService actIdentityService;

    @RequestMapping(value = "actUserList")
    public String actUserList(@FormPostParam ActUserQuery actUserQuery, Model model) {
        PageInfo<ActUserVO> actUserVOPageInfo = actIdentityService.queryPageActUser(actUserQuery);
        if (!actUserVOPageInfo.getList().isEmpty()) {
            ActGroupQuery actGroupQuery = new ActGroupQuery();
            for (ActUserVO actGroupVO : actUserVOPageInfo.getList()) {
                actGroupQuery.setUserId(actGroupVO.getUserId());
                actGroupVO.setActGroupVOs(actIdentityService.queryAllActGroup(actGroupQuery));
            }
        }
        Page<ActUserVO> page = new Page<>(actUserQuery.getPageNum(), actUserQuery.getPageSize(), actUserVOPageInfo.getTotal(), actUserVOPageInfo.getList());
        model.addAttribute("queryParam", actUserQuery);
        model.addAttribute("page", page);
        model.addAttribute("actGroups", actIdentityService.queryAllActGroup(new ActGroupQuery()));
        return "/modules/act/actUserList";
    }

    @RequestMapping(value = "actGroupList")
    public String actGroupList(@FormPostParam ActGroupQuery actGroupQuery, Model model) {
        PageInfo<ActGroupVO> actGroupVOPageInfo = actIdentityService.queryPageActGroup(actGroupQuery);
        if (!actGroupVOPageInfo.getList().isEmpty()) {
            ActUserQuery actUserQuery = new ActUserQuery();
            for (ActGroupVO actGroupVO : actGroupVOPageInfo.getList()) {
                actUserQuery.setGroupId(actGroupVO.getGroupId());
                actGroupVO.setActUserVOs(actIdentityService.queryAllActUser(actUserQuery));
            }
        }
        Page<ActGroupVO> page = new Page<>(actGroupQuery.getPageNum(), actGroupQuery.getPageSize(), actGroupVOPageInfo.getTotal(), actGroupVOPageInfo.getList());
        model.addAttribute("queryParam", actGroupQuery);
        model.addAttribute("page", page);
        return "/modules/act/actGroupList";
    }

    @RequestMapping(value = "actUserPacketManaged")
    public String actUserPacketManaged(String userId, Model model) {
        ActUserVO actUser = actIdentityService.getActUserById(userId);
        ActGroupQuery actGroupQuery = new ActGroupQuery();
        model.addAttribute("actUser", actUser);
        model.addAttribute("allActGroup", actIdentityService.queryAllActGroup(actGroupQuery));
        actGroupQuery.setUserId(userId);
        model.addAttribute("actUserPackets", actIdentityService.queryAllActGroup(actGroupQuery));
        return "/modules/act/acUserPacketManaged";
    }

    @RequestMapping(value = "createActUser")
    public String createActUser(Model model) {
        model.addAttribute("actUser", new ActUserVO());
        return "/modules/act/actUserCreate";
    }

    @RequestMapping(value = "createActGroup")
    public String createActGroup(Model model) {
        model.addAttribute("actGroup", new ActGroupVO());
        return "/modules/act/acGroupCreate";
    }

    @RequestMapping(value = "saveActUser")
    public String saveActUser(@FormPostParam ActUserSaveDTO userInfo) {
        actIdentityService.saveActUser(userInfo);
        return "redirect:/workflow/identity/actUserList";
    }

    @RequestMapping(value = "saveActGroup")
    public String saveActGroup(@FormPostParam ActGroupSaveDTO groupInfo) {
        actIdentityService.saveActGroup(groupInfo);
        return "redirect:/workflow/identity/actGroupList";
    }

    @RequestMapping(value = "updateActUser")
    public String updateActUser(String userId, Model model) {
        model.addAttribute("actUser", actIdentityService.getActUserById(userId));
        return "/modules/act/actUserCreate";
    }

    @RequestMapping(value = "updateActGroup")
    public String updateActGroup(String groupId, Model model) {
        model.addAttribute("actGroup", actIdentityService.getActGroupById(groupId));
        return "/modules/act/acGroupCreate";
    }

    @RequestMapping(value = "deleteActUser")
    public String deleteActUser(String userId) {
        actIdentityService.deleteActUser(userId);
        return "redirect:/workflow/identity/actUserList";
    }

    @RequestMapping(value = "deleteActGroup")
    public String deleteActGroup(String groupId) {
        actIdentityService.deleteActGroup(groupId);
        return "redirect:/workflow/identity/actGroupList";
    }

    @RequestMapping(value = "updateMembership")
    public String updateMembership(ActMembershipOperateDTO actMembershipOperateDTO) {
        actIdentityService.createMembership(actMembershipOperateDTO);
        return "redirect:/workflow/identity/actUserList";
    }

    @ResponseBody
    @RequestMapping(value = "/userIdSet")
    public String userIdSet(String userId, HttpServletRequest request) {
        ActUserVO actUserVO = actIdentityService.getActUserById(userId);
        if (null == actUserVO) {
            return "false";
        }
        request.getSession().setAttribute("actUser", actUserVO);
        return "true";
    }

    @ResponseBody
    @RequestMapping(value = "/userIdEmpty")
    public String userIdEmpty(HttpServletRequest request) {
        request.getSession().removeAttribute("actUser");
        return "true";
    }

}
