package com.anxi.activiti.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anxi.activiti.service.api.ActIdentityService;
import com.anxi.activiti.vo.ActGroupQuery;
import com.anxi.activiti.vo.ActGroupSaveDTO;
import com.anxi.activiti.vo.ActGroupVO;
import com.anxi.activiti.vo.ActMembershipOperateDTO;
import com.anxi.activiti.vo.ActUserQuery;
import com.anxi.activiti.vo.ActUserSaveDTO;
import com.anxi.activiti.vo.ActUserVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJ on 2018/5/8
 */
@Slf4j
@Service(version = "${activiti.service.version}")
public class ActIdentityServiceImpl implements ActIdentityService {

    @Resource
    private IdentityService identityService;

    @Override
    public ActUserVO getActUserById(String userId) {
        User user = identityService.createUserQuery().userId(userId).singleResult();
        return null != user ? new ActUserVO(user.getId(), user.getFirstName(), user.getEmail()) : null;
    }

    @Override
    public PageInfo<ActUserVO> queryPageActUser(ActUserQuery actUserQuery) {
        UserQuery userQuery = this.generateUserQuery(actUserQuery);
        PageInfo<ActUserVO> actUserPage = new PageInfo<>();
        actUserPage.setTotal(userQuery.count());
        List<User> users = userQuery.listPage((actUserQuery.getPageNum() - 1) * actUserQuery.getPageSize(), actUserQuery.getPageSize());
        List<ActUserVO> actUsers = new ArrayList<>(users.size());
        ActUserVO actUserVO;
        for (User user : users) {
            actUserVO = new ActUserVO(user.getId(), user.getFirstName(), user.getEmail());
            actUsers.add(actUserVO);
        }
        actUserPage.setList(actUsers);
        return actUserPage;
    }

    @Override
    public List<ActUserVO> queryAllActUser(ActUserQuery actUserQuery) {
        UserQuery userQuery = this.generateUserQuery(actUserQuery);
        List<ActUserVO> list = new ArrayList<>(userQuery.list().size());
        ActUserVO actUserVO;
        for (User user : userQuery.list()) {
            actUserVO = new ActUserVO(user.getId(), user.getFirstName(), user.getEmail());
            list.add(actUserVO);
        }
        return list;
    }

    @Override
    public ActGroupVO getActGroupById(String groupId) {
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        return new ActGroupVO(group.getId(), group.getName(), group.getType());
    }

    @Override
    public PageInfo<ActGroupVO> queryPageActGroup(ActGroupQuery actGroupQuery) {
        GroupQuery groupQuery = this.generateGroupQuery(actGroupQuery);
        PageInfo<ActGroupVO> actUserPage = new PageInfo<>();
        actUserPage.setTotal(groupQuery.count());
        List<Group> groups = groupQuery.listPage((actGroupQuery.getPageNum() - 1) * actGroupQuery.getPageSize(), actGroupQuery.getPageSize());
        List<ActGroupVO> actUsers = new ArrayList<>(groups.size());
        ActGroupVO actGroupVO;
        for (Group group : groups) {
            actGroupVO = new ActGroupVO(group.getId(), group.getName(), group.getType());
            actUsers.add(actGroupVO);
        }
        actUserPage.setList(actUsers);
        return actUserPage;
    }

    @Override
    public List<ActGroupVO> queryAllActGroup(ActGroupQuery actGroupQuery) {
        GroupQuery groupQuery = this.generateGroupQuery(actGroupQuery);
        List<ActGroupVO> list = new ArrayList<>(groupQuery.list().size());
        ActGroupVO actGroupVO;
        for (Group group : groupQuery.list()) {
            actGroupVO = new ActGroupVO(group.getId(), group.getName(), group.getType());
            list.add(actGroupVO);
        }
        return list;
    }

    @Override
    @Transactional
    public void saveActUser(ActUserSaveDTO userInfo) {
        identityService.deleteUser(userInfo.getUserId());
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userInfo.getUserId());
        userEntity.setFirstName(userInfo.getUserName());
        userEntity.setEmail(userInfo.getUserEmail());
        identityService.saveUser(userEntity);
    }

    @Override
    @Transactional
    public void deleteActUser(String userId) {
        identityService.deleteUser(userId);
    }

    @Override
    @Transactional
    public void saveActGroup(ActGroupSaveDTO groupInfo) {
        identityService.deleteGroup(groupInfo.getGroupId());
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(groupInfo.getGroupId());
        groupEntity.setName(groupInfo.getGroupName());
        groupEntity.setType(groupInfo.getGroupType());
        identityService.saveGroup(groupEntity);
    }

    @Override
    @Transactional
    public void deleteActGroup(String groupId) {
        identityService.deleteGroup(groupId);
    }

    @Override
    @Transactional
    public void createMembership(ActMembershipOperateDTO actMembershipOperateDTO) {
        for (String groupId : actMembershipOperateDTO.getGroupIds()) {
            identityService.deleteMembership(actMembershipOperateDTO.getUserId(), groupId);
            identityService.createMembership(actMembershipOperateDTO.getUserId(), groupId);
        }
    }

    @Override
    @Transactional
    public void deleteMembership(ActMembershipOperateDTO actMembershipOperateDTO) {
        for (String groupId : actMembershipOperateDTO.getGroupIds()) {
            identityService.deleteMembership(actMembershipOperateDTO.getUserId(), groupId);
        }
    }

    private UserQuery generateUserQuery(ActUserQuery actUserQuery) {
        UserQuery userQuery = identityService.createUserQuery();
        String userId = actUserQuery.getUserId();
        String groupId = actUserQuery.getGroupId();
        String userName = actUserQuery.getUserName();
        String userEmail = actUserQuery.getUserEmail();
        if (!StringUtils.isEmpty(userId)) {
            userQuery.userId(userId);
        }
        if (!StringUtils.isEmpty(userName)) {
            userQuery.userFirstNameLike("%" + userName + "%");
        }
        if (!StringUtils.isEmpty(userEmail)) {
            userQuery.userEmailLike("%" + userEmail + "%");
        }
        if (!StringUtils.isEmpty(groupId)) {
            userQuery.memberOfGroup(groupId);
        }
        return userQuery;
    }

    private GroupQuery generateGroupQuery(ActGroupQuery actGroupQuery) {
        GroupQuery groupQuery = identityService.createGroupQuery();
        String groupId = actGroupQuery.getGroupId();
        String groupName = actGroupQuery.getGroupName();
        String groupType = actGroupQuery.getGroupType();
        String userId = actGroupQuery.getUserId();
        if (!StringUtils.isEmpty(groupId)) {
            groupQuery.groupId(groupId);
        }
        if (!StringUtils.isEmpty(groupName)) {
            groupQuery.groupNameLike("%" + groupName + "%");
        }
        if (!StringUtils.isEmpty(groupType)) {
            groupQuery.groupType(groupType);
        }
        if (!StringUtils.isEmpty(userId)) {
            groupQuery.groupMember(userId);
        }
        return groupQuery;
    }
}
