package org.kettle.scheduler.system.biz.enums;

public enum CountMark {
    TODAY(1, "今天新增条数"), OUR(2, "历史总条数");

    private int key;
    private String remark;
    private CountMark(int key, String remark) {
        this.key = key;
        this.remark = remark;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
