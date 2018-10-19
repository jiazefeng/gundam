package com.maxrocky.gundam.common.result;

/**
 * Created by Tom on 2016/5/9 17:28.
 * Describe:
 */
public class SuccessApiResult extends ApiResult {

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
