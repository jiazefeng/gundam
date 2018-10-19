package com.maxrocky.gundam.coreservice.common;

import org.springframework.ui.ModelMap;

/**
 * Created by yuer5 on 16/6/21.
 */
public class ErrorApiResult extends ModelMap {

    public ErrorApiResult(String errorCode, String errorMessage, Exception exception) {
        this.addAttribute("code", errorCode);
        this.addAttribute("msg", errorMessage);
        this.addAttribute("data", "");
        if (exception != null) {
            this.addAttribute("innerExceptionMessage", exception.getMessage());
            this.addAttribute("innerExceptionStackTrace", exception.getStackTrace());
        }
    }
}