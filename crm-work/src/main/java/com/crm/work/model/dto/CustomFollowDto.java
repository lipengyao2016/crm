package com.crm.work.model.dto;

import com.crm.work.model.CustomFollow;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

@ApiModel("客户跟进编辑类")
public class CustomFollowDto extends CustomFollow {

    @ApiModelProperty("下次跟进信息")
    private CustomFollow nextFollow;

    @ApiModelProperty("任务跟进模块:未完成原因")
    private String notFinishedCause;

    public CustomFollow getNextFollow() {
        return nextFollow;
    }

    public void setNextFollow(CustomFollow nextFollow) {
        this.nextFollow = nextFollow;
    }

    public String getNotFinishedCause() {
        return notFinishedCause;
    }

    public void setNotFinishedCause(String notFinishedCause) {
        this.notFinishedCause = notFinishedCause;
    }
}
