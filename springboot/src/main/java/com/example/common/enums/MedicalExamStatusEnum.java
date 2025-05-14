package com.example.common.enums;

public enum MedicalExamStatusEnum {
    NOT_SCHEDULED("未预约"),
    SCHEDULED("已预约"),
    NOT_DONE("未体检"),
    DONE("已体检");

    private String value;

    public String getValue() {
        return value;
    }

    MedicalExamStatusEnum(String value) {
        this.value = value;
    }
}