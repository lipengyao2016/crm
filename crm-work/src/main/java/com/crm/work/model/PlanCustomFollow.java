package com.crm.work.model;

public class PlanCustomFollow {
    private Long id;

    private Long pid;

    private Long followId;

    private Short customStage;

    private String notFinishedCause;

    public String getNotFinishedCause() {
        return notFinishedCause;
    }

    public void setNotFinishedCause(String notFinishedCause) {
        this.notFinishedCause = notFinishedCause;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public Short getCustomStage() {
        return customStage;
    }

    public void setCustomStage(Short customStage) {
        this.customStage = customStage;
    }
}