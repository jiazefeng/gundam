package com.maxrocky.gundam.common.result;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lizhipeng on 2016/7/13.
 */
public class NoCacheFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        ((HttpServletResponse) response).setHeader("Cache-Control","private, no-cache");
        ((HttpServletResponse) response).setHeader("Pragma","no-cache");
        ((HttpServletResponse) response).setDateHeader ("Expires", -1);
        filterChain.doFilter(request, response);
    }

    public void destroy(){}

    public void init(FilterConfig filterConfig) throws ServletException {}
}
