package com.epam.filter;

import javax.servlet.*;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

import static com.epam.util.ControllerConstants.*;

public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig config) {
        encoding = nonNull(config.getInitParameter(REQUEST_ENCODING)) ? config.getInitParameter(REQUEST_ENCODING) : UTF8;

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {

        if (isNull(request.getCharacterEncoding())) {
            request.setCharacterEncoding(encoding);
        }

        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(UTF8);

        next.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }


}
