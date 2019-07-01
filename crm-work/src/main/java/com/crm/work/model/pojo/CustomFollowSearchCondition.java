package com.crm.work.model.pojo;

import com.crm.common.SearchCondition;

public class CustomFollowSearchCondition extends SearchCondition {
    private Long followUserId;

    private Long customId;

    private int followStage;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public Long getCustomId() {
        return customId;
    }

    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    public int getFollowStage() {
        return followStage;
    }

    public void setFollowStage(int followStage) {
        this.followStage = followStage;
    }
}
