package com.blsa.ezilog.model.reply;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@Valid
@ToString
public class ReplyUpdateRequest {
    
    @ApiModelProperty(required = true)
    @NotNull
    BigInteger id;
    
    @ApiModelProperty(required = true)
    @NotNull
    BigInteger postNo;
    
    @ApiModelProperty(required = true)
    @NotNull 
    String writer;
    
    @ApiModelProperty(required = true)
    @NotNull
    String content;
    
    
    @ApiModelProperty(required = true)
    @NotNull
    boolean selected;
    
    @ApiModelProperty(required = true)
    @NotNull
    boolean secret;

    public BigInteger getPostNo() {
        return postNo;
    }

    public void setPostNo(BigInteger postNo) {
        this.postNo = postNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
    
}
