package com.fnf.eccloudtaskv2;


public class SourceData {
    private int id;
    private String text;
    private boolean flag;

    // 기본 생성자
    public SourceData() {
    }
    public SourceData(int id, String text, boolean flag) {
        this.id = id;
        this.text = text;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SourceData{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", flag=" + flag +
                '}';
    }
}
