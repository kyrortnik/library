package com.epam.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.epam.util.ControllerConstants.ANTI_INJECTION_MESSAGE;
import static com.epam.util.ControllerConstants.MESSAGE;

public class AntiInjectionFilter implements Filter {

    private static final String JS_INJECTION_PATTERN = ".*<.*script>.*";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();
        for (String[] v : params.values()) {
            sb.append(v[0]);
        }
        String toMatch = sb.toString().trim();
        if (!toMatch.matches(JS_INJECTION_PATTERN)) {
            chain.doFilter(req, res);
        } else {
            ((HttpServletRequest) request).getSession().setAttribute(ANTI_INJECTION_MESSAGE, "Injection attempt has been detected");
            ((HttpServletRequest) request).getSession().setAttribute(MESSAGE, null);
            request.getRequestDispatcher("frontController?command=go_To_Page&address=antiInjection.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
