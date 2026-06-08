package com.example.entity;

import java.io.Serializable;

/**
 * 体检信息
 */
public class MedicalExam implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 体检项目名称 */
    private String name;
    /** 体检项目封面（比如体检项目的图片路径） */
    private String cover;
    /** 体检项目简介 */
    private String descr;
    /** 体检开始日期 */
    private String start;
    /** 体检结束日期 */
    private String end;
    /** 体检项目详情（比如具体的体检内容等） */
    private String content;
    /** 体检地址 */
    private String address;
    /** 体检状态（比如已预约、已完成等） */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}