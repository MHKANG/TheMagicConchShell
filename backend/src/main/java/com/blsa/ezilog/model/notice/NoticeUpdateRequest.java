package com.blsa.ezilog.model.notice;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@Valid
@ToString
public class NoticeUpdateRequest {
    @ApiModelProperty(required = true)
    @NotNull
    private Long nid;

    @ApiModelProperty(required = true)
    @NotNull
    String title;

    @ApiModelProperty(required = true)
    @NotNull
    String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getNid() {
        return nid;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

}
