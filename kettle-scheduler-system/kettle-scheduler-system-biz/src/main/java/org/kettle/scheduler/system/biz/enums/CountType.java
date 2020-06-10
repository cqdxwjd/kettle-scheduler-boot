package org.kettle.scheduler.system.biz.enums;
public enum CountType {
    ALL(1, "所有"), DETAIL(2, "详细");

    private int key;
    private String remark;
    private CountType(int key, String remark) {
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
