package com.maxrocky.gundam.coreservice.common;

import org.springframework.ui.ModelMap;

/**
 * Created by yuer5 on 16/6/21.
 */
public class SuccessApiResult extends ModelMap {

    public SuccessApiResult() {
        this.init();
        this.addAttribute("data", "");
    }

    public SuccessApiResult(Object data) {
        this.init();
        this.addAttribute("data", data);
    }

    private void init() {
        this.addAttribute("code", 0);
        this.addAttribute("msg", "OK");
    }

    public Object getData() {
        return this.getOrDefault("data", null);
    }

}