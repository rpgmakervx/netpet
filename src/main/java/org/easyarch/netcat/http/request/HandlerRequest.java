package org.easyarch.netcat.http.request;

import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.cookie.HttpCookie;
import org.easyarch.netcat.http.session.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by xingtianyu on 17-3-9
 * 下午3:29
 * description:
 */

public interface HandlerRequest {
    public HandlerContext getContext();

    public Set<HttpCookie> getCookies();


    public String getHeader(String name);

    public Collection<String> getHeaderNames();


    public Long getDateHeader(String name);


    public String getMethod();

    public String getContextPath();

    public<T> T body(Class<T> cls);

    public String getQueryString();

    public String getSessionId();

    public SocketAddress getRemoteAddress();

    public String getRequestURI();

    public HttpSession getSession();

    public String getCharacterEncoding();


    public void setCharacterEncoding(String env) throws UnsupportedEncodingException;


    public int getContentLength();


    public String getContentType();


    public String getParameter(String name);

    public Collection<String> getParameterNames();

    public Collection<String> getParameterValues(String name);

    public Map getParameterMap();


    public String getProtocol();

    public String getRemoteAddr();

}
