package com.crm.work.model.pojo;

import com.crm.common.SearchCondition;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel("汇报检索模型")
public class ReportSearchCondition extends SearchCondition {
     private String title;

     private int reportType;

     private int type;

     private Long uid;

     private List<Long> rids;

    public List<Long> getRids() {
        return rids;
    }

    public void setRids(List<Long> rids) {
        this.rids = rids;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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
