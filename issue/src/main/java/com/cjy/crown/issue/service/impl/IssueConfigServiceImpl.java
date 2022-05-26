package com.cjy.crown.issue.service.impl;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.common.dto.enums.UniversalStateEnum;
import com.cjy.crown.issue.entity.Issue;
import com.cjy.crown.issue.entity.IssueConfig;
import com.cjy.crown.issue.entity.IssueManager;
import com.cjy.crown.issue.repository.IssueConfigRepository;
import com.cjy.crown.issue.repository.IssueManagerRepository;
import com.cjy.crown.issue.repository.IssueRepository;
import com.cjy.crown.issue.service.IssueConfigService;
import com.cjy.crown.issue.service.rest.MessageRestService;
import com.cjy.crown.security.repository.UserRepository;
import com.cjy.crown.security.utils.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/20 14:51
 * @description：
 */
@Service
public class IssueConfigServiceImpl implements IssueConfigService {

    @Autowired
    IssueConfigRepository issueConfigRepository;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    MessageRestService messageRestService;

    @Autowired
    IssueManagerRepository issueManagerRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * 全量更细IssueConfig
     * @param issueConfig
     * @return
     */
    @Override
    public Result update(IssueConfig issueConfig) {
        Result result = new Result();
        if (StringUtils.equalsAny(WebUtil.getUserId(), issueRepository.findById(issueConfig.getIssueId()).get().getHolder())) {
            result.addData("issueConfig", issueConfigRepository.save(issueConfig));
        }
        return result;
    }

    /**
     * 获取议题对应的管理员和议题持有者的好友信息
     * @param issueId
     * @return
     */
    @Override
    public Result getManagerAndFriend(String issueId) {
        String userId = WebUtil.getUserId();
        Result result = messageRestService.getFriendIdAndUsernameByUserId(userId, new HashMap() {{
            put("Authorization", WebUtil.getToken());
        }});
        Map<String, String> friendsIdAndName = (Map<String, String>) result.getData().get("friendsIdAndName");
        Map<String, String> managersIdAndName = new HashMap<>();
        Set<String> managerIds = new HashSet<>();
        issueManagerRepository.findByIssueIdAndState(issueId, UniversalStateEnum.INUSE.getCode()).forEach(x -> {
            managerIds.add(x.getManager());
        });
        if (managerIds.size() != 0) {
            userRepository.findAllById(managerIds).forEach(x -> {
                managersIdAndName.put(x.getId(), x.getUsername());
                friendsIdAndName.remove(x.getId());
            });
        }
        result.addData("managers", managersIdAndName);
        result.addData("friends", friendsIdAndName);
        return result;
    }

    /**
     * 根据传来的用户ID去更新议题的管理员
     * @param issueId
     * @param managers
     * @return
     */
    @Override
    public Result setManagers(String issueId, List<String> managers) {
        Issue issue = issueRepository.findById(issueId).get();
        Result result = new Result();
        String userId = WebUtil.getUserId();
        if (!userId.equals(issue.getHolder())) {
            return result;
        }
        Date now = new Date();
        List<IssueManager> issueManagerList = issueManagerRepository.findByIssueId(issueId);
        issueManagerList.forEach(x -> {
            if (x.getState() == UniversalStateEnum.INUSE.getCode()) {
                if (!managers.contains(x.getManager())) {
                    x.setState(UniversalStateEnum.FORBIDDEN.getCode());
                    x.setUpdateTime(now);
                }
            } else if (x.getState() == UniversalStateEnum.FORBIDDEN.getCode()) {
                if (managers.contains(x.getManager())) {
                    x.setState(UniversalStateEnum.INUSE.getCode());
                    x.setUpdateTime(now);
                }
            }
            managers.remove(x.getManager());
        });
        managers.forEach(x->{
            IssueManager issueManager = new IssueManager();
            issueManager.setManager(x);
            issueManager.setIssueId(issueId);
            issueManager.setCreateTime(now);
            issueManager.setState(UniversalStateEnum.INUSE.getCode());
            issueManagerList.add(issueManager);
        });
        issueManagerRepository.saveAll(issueManagerList);
        return result;
    }

    @Override
    public Result setIssueState(String issueId, int state) {
        Issue issue = issueRepository.findById(issueId).get();
        issue.setState(state);
        issue.setUpdateTime(new Date());
        issue.setUpdater(WebUtil.getUserId());
        issueRepository.save(issue);
        return new Result();
    }
}
