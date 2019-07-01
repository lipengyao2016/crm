package com.crm.work.model;

public class PlanProperty {
    private Long id;

    private Long pid;

    private Integer propertySeqId;

    private String target;

    private String propertyName;

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

    public Integer getPropertySeqId() {
        return propertySeqId;
    }

    public void setPropertySeqId(Integer propertySeqId) {
        this.propertySeqId = propertySeqId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }
}