package com.crm.work.model.pojo;

import com.crm.common.SearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("任务检索模型")
public class PlanSearchCondition extends SearchCondition {
    @ApiModelProperty("任务标题")
     private String title;
    @ApiModelProperty("任务等级")
     private int reportType;
    @ApiModelProperty("任务类型(1:日计划,2:周计划,3:月计划)")
     private int type;
    @ApiModelProperty("任务类型(1:日计划,2:周计划,3:月计划)")
     private List<Long> rids;

    public List<Long> getRids() {
        return rids;
    }

    public void setRids(List<Long> rids) {
        this.rids = rids;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public int getReportType() {
          return reportType;
     }

     public void setReportType(int reportType) {
          this.reportType = reportType;
     }
}
