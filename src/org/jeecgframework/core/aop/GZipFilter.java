package org.jeecgframework.core.aop;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.util.FileCopyUtils;

public class GZipFilter implements Filter {
    private static final Logger logger = Logger.getLogger(GZipFilter.class);
    private static final String DESIGNER_PLUG_IN = "plug-in/designer";

    public void destroy() {
    }

    private static boolean isGZipEncoding(HttpServletRequest request) {
        boolean flag = false;
        String encoding = request.getHeader("Accept-Encoding");
        String gzipFlag = request.getParameter("gzip");
        if ("0".equals(gzipFlag)) {//如果gzip标志为0，则不使用gzip
            flag = false;
            return flag;
        }
        if ((encoding != null) && (encoding.indexOf("gzip") != -1)) {
            flag = true;
        }
        return flag;
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void CacheResource(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        uri = uri.substring(uri.lastIndexOf(".") + 1);
        System.out.println(uri);

        long date = 0L;

        if (uri.equalsIgnoreCase("jpg")) {
            date = System.currentTimeMillis() + 18000000L;
        }

        if (uri.equalsIgnoreCase("gif")) {
            date = System.currentTimeMillis() + 18000000L;
        }

        if (uri.equalsIgnoreCase("css")) {
            date = System.currentTimeMillis() + 18000000L;
        }

        if (uri.equalsIgnoreCase("js")) {
            date = System.currentTimeMillis() + 18000000L;
        }

        res.setDateHeader("Expires", date);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURI();
        String path = req.getContextPath();
        String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path;

        if (!JavaMUtil.lic_flag) {
            String licence = ResourceUtil.getConfigByName("licence");
            if ((url.indexOf("licence") == -1) && (url.indexOf("licController.do") == -1)
                    && (JavaMUtil.getssss() != null) && (!StringUtil.contains(licence.split(","), JavaMUtil.getssss()))) {
                HttpSession session = req.getSession(true);
                resp.setHeader("Cache-Control", "no-store");
                resp.setDateHeader("Expires", 0L);
                resp.setHeader("Prama", "no-cache");
                resp.sendRedirect(basePath + "/webpage/common/licence.jsp");
            } else if ((url.indexOf("licence") == -1) && (url.indexOf("licController.do") == -1)
                    && (StringUtil.contains(licence.split(","), JavaMUtil.getssss()))) {
                JavaMUtil.lic_flag = Boolean.TRUE.booleanValue();
            }

        }

        if (req.getRequestURI().indexOf("plug-in/designer") != -1) {
            url = url.replaceFirst(req.getContextPath(), "");
            response.reset();
            String s = ResMime.get(url.substring(url.lastIndexOf(".")).replace(".", ""));
            if (s != null)
                response.setContentType(s);

            OutputStream os = null;
            InputStream is = null;
            try {
                url = url.replaceFirst(req.getContextPath(), "");
                is = getClass().getResourceAsStream(url);
                if (is != null) {
                    os = response.getOutputStream();
                    FileCopyUtils.copy(is, os);
                } else {
                    logger.warn("\tdosen't find resource : " + url);
                }
            } catch (IOException e) {
                e.printStackTrace();

                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (is == null)
                    return;
                try {
                    is.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (req.getRequestURI().indexOf("ReportServer") != -1) {
            chain.doFilter(request, response);
        } else if (isGZipEncoding(req)) {
            Wrapper wrapper = new Wrapper(resp);
            chain.doFilter(request, wrapper);
            byte[] gzipData = gzip(wrapper.getResponseData());
            resp.addHeader("Content-Encoding", "gzip");
            resp.setContentLength(gzipData.length);

            ServletOutputStream output = response.getOutputStream();
            output.write(gzipData);
            output.flush();
        } else {
            chain.doFilter(request, response);
        }
    }

    private byte[] gzip(byte[] data) {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
        GZIPOutputStream output = null;
        try {
            output = new GZIPOutputStream(byteOutput);
            output.write(data);
        } catch (IOException localIOException) {
            try {
                output.close();
            } catch (IOException localIOException1) {
            }
        } finally {
            try {
                output.close();
            } catch (IOException localIOException2) {
            }
        }
        return byteOutput.toByteArray();
    }
}