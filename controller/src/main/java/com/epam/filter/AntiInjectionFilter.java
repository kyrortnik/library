package com.epam.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.epam.util.ControllerConstants.MESSAGE;

public class AntiInjectionFilter implements Filter {

    private static final String DOES_NOT_CONTAIN = "^((?!<|>|script).)*$";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();
        for (String [] v : params.values()) {
            sb.append(v[0]);
        }
        if (sb.toString().trim().matches(DOES_NOT_CONTAIN)) {
            chain.doFilter(req, res);
        } else {
            ((HttpServletRequest) request).getSession().setAttribute(MESSAGE,"Injection attempt has been detected");
            request.getRequestDispatcher("frontController?command=go_To_Page&address=antiInjection.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
