package com.bb.web.framework.servlet;

import com.bb.web.framework.bean.Data;
import com.bb.web.framework.bean.Handler;
import com.bb.web.framework.bean.Param;
import com.bb.web.framework.bean.View;
import com.bb.web.framework.helper.*;
import com.bb.web.framework.util.JsonUtil;
import com.bb.web.framework.util.ReflectionUtil;
import com.bb.web.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @ClassName DispatchServlet
 * @Description: TODO
 * @Author zzy
 * @Date 2020/10/29
 **/
@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class DispatchServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        HelperLoader.init();

        ServletContext servletContext = servletConfig.getServletContext();

        //注册处理JSP的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        //处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

        //处理上传的初始化
        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求方法与路径
        ServletHelper.init(request, response);
        try {
            String requestMethod = request.getMethod().toLowerCase();
            String requestPath = request.getPathInfo();

            if (requestPath.equals("/favicon.ico")) {
                return;
            }

            //获取Action处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                Param param;
                //TODO
                if (UploadHelper.isMultipart(request)) {
                    param = UploadHelper.createParam(request);
                } else {
                    param = RequestHelper.createParam(request);
                }

                Object result;
                //调用action方法
                Method actionMethod = handler.getActionMethod();
                if (param.isEmpty()) {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }
                //处理Action方法返回值
                if (result instanceof View) {
                    //返回jsp页面
                    handlerViewResult((View) result, request, response);
                } else if (result instanceof Data) {
                    handlerDataResult((Data) result, response);
                }
            }
        } finally {
            ServletHelper.destroy();
        }
    }


    private void handlerViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
    }

    private void handlerDataResult(Data data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

}
