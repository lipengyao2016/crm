package com.crm.work.model;

import com.crm.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("任务基类")
public class PlanInfo extends BaseDto{
    private Long id;

    @ApiModelProperty(value = "任务标题",example = "测试任务")
    private String title;

    @ApiModelProperty(value = "任务内容",example = "测试任务内容")
    private String content;

    @ApiModelProperty(value = "任务级别",example = "1")
    private Short reportType;

    @ApiModelProperty(value = "任务状态",example = "Y")
    private String status;

    @ApiModelProperty(value = "任务类型(1:日计划,2:周计划,3:月计划)",example = "2")
    private Short type;

    @ApiModelProperty(value = "任务模式(1:单人指派,2:多人指标)",example = "1")
    private Short pattern;

    @ApiModelProperty("客户编码")
    private Long customId;

    public Long getCustomId() {
        return customId;
    }

    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Short getReportType() {
        return reportType;
    }

    public void setReportType(Short reportType) {
        this.reportType = reportType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getPattern() {
        return pattern;
    }

    public void setPattern(Short pattern) {
        this.pattern = pattern;
    }
}