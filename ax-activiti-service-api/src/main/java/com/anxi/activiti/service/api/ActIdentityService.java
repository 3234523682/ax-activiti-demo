package com.anxi.activiti.service.api;

import com.anxi.activiti.vo.ActGroupQuery;
import com.anxi.activiti.vo.ActGroupSaveDTO;
import com.anxi.activiti.vo.ActGroupVO;
import com.anxi.activiti.vo.ActMembershipOperateDTO;
import com.anxi.activiti.vo.ActUserQuery;
import com.anxi.activiti.vo.ActUserSaveDTO;
import com.anxi.activiti.vo.ActUserVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by LJ on 2018/3/26
 */
public interface ActIdentityService {


    ActUserVO getActUserById(String userId);

    PageInfo<ActUserVO> queryPageActUser(ActUserQuery actUserQuery);

    List<ActUserVO> queryAllActUser(ActUserQuery actUserQuery);

    ActGroupVO getActGroupById(String groupId);

    PageInfo<ActGroupVO> queryPageActGroup(ActGroupQuery actGroupQuery);

    List<ActGroupVO> queryAllActGroup(ActGroupQuery actGroupQuery);

    void saveActUser(ActUserSaveDTO userInfo);

    void saveActGroup(ActGroupSaveDTO groupInfo);

    void deleteActUser(String userId);

    void deleteActGroup(String groupId);

    void createMembership(ActMembershipOperateDTO actMembershipOperateDTO);

    void deleteMembership(ActMembershipOperateDTO actMembershipOperateDTO);
}
