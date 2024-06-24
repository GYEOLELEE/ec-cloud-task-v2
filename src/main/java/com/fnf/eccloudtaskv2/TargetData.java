package com.fnf.eccloudtaskv2;


import java.time.LocalDateTime;

public class TargetData {
    private final int id;
    private final String text;
    private LocalDateTime regDttm;

    public TargetData(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }
    public String getText() {
        return text;
    }

    public LocalDateTime getRegDttm() {
        return regDttm;
    }

}
