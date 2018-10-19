package com.maxrocky.gundam.domain.demo.dto.DO0005;

/**
 * Created by Tom on 2016/5/27 15:03.
 * Describe:Return DO0005、DO0006、DO0007、DO0011 api data.
 */
public class DemoDetailJsonDTO {

    public DemoDetailJsonDTO(){
        this.demoId = "";
        this.demoName = "";
    }

    private String demoId;
    private String demoName;

    public String getDemoId() {
        return demoId;
    }

    public void setDemoId(String demoId) {
        this.demoId = demoId;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }
}
