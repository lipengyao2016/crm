package com.crm.work.model.vo;

import com.crm.work.model.CustomFollow;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("跟进阶段展示")
public class CustomFollowVo extends CustomFollow {

    @ApiModelProperty("下一个计划跟进信息")
    CustomFollow nextFollow;

    @ApiModelProperty("客户信息详情")
    private CustomInfoVo customInfoVo;

    public CustomFollow getNextFollow() {
        return nextFollow;
    }

    public void setNextFollow(CustomFollow nextFollow) {
        this.nextFollow = nextFollow;
    }

    public CustomInfoVo getCustomInfoVo() {
        return customInfoVo;
    }

    public void setCustomInfoVo(CustomInfoVo customInfoVo) {
        this.customInfoVo = customInfoVo;
    }
}
