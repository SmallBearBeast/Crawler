package com.bear.crawler.wechat.pojo.dto.resp;

import cn.hutool.core.collection.CollectionUtil;
import com.bear.crawler.wechat.pojo.dto.GroupInfoDto;
import com.bear.crawler.wechat.pojo.dto.WUserInfoDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfosRespDto extends BaseRespDto {

    @JsonAlias("group_info")
    private Map<String, List<GroupInfoDto>> groupInfo;

    @JsonAlias("user_list")
    private Map<String, List<WUserInfoDto>> userList;

    public @NonNull List<GroupInfoDto> getGroupInfos() {
        if (CollectionUtil.isEmpty(groupInfo)) {
            return new ArrayList<>();
        }
        List<GroupInfoDto> groupInfoDtos = groupInfo.get("group_info_list");
        return groupInfoDtos == null ? new ArrayList<>() : groupInfoDtos;
    }

    public @NonNull List<WUserInfoDto> getUserInfos() {
        if (CollectionUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        List<WUserInfoDto> WUserInfoDtos = userList.get("user_info_list");;
        return WUserInfoDtos == null ? new ArrayList<>() : WUserInfoDtos;
    }
}
