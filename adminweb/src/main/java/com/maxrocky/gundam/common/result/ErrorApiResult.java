package com.maxrocky.gundam.common.result;

/**
 * Created by Tom on 2016/5/9 17:27.
 * Describe:
 */
public class ErrorApiResult extends ApiResult {

    public ErrorApiResult(String errorKey) {
        this(errorKey, (String) CustomizedPropertyPlaceholderConfigurer.getContextProperty(errorKey), null);
    }

    public ErrorApiResult(String errorKey, Exception exception) {
        this.addAttribute("code", errorKey);
        this.addAttribute("msg", CustomizedPropertyPlaceholderConfigurer.getContextProperty(errorKey));
        this.addAttribute("data", "");
        if (exception != null) {
            this.addAttribute("innerExceptionMessage", exception.getMessage());
            this.addAttribute("innerExceptionStackTrace", exception.getStackTrace());
        }
    }

    public ErrorApiResult(String errorCode, String errorMessage, Exception exception) {
        this.addAttribute("code", errorCode);
        this.addAttribute("msg", errorMessage);
        this.addAttribute("data", "");
        if (exception != null) {
            this.addAttribute("innerExceptionMessage", exception.getMessage());
            this.addAttribute("innerExceptionStackTrace", exception.getStackTrace());
        }
    }
    public ErrorApiResult(String errorCode, String errorMessage) {
        this.addAttribute("code", errorCode);
        this.addAttribute("msg", errorMessage);
    }
}
