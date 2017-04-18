package testHttp;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final String encoding = "UTF-8";

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        request.setCharacterEncoding(encoding);

        try {
            Object f = response;
            f = new GZIPResponseWrapper((HttpServletResponse)f);
            chain.doFilter(request, (ServletResponse)f);
            ((GZIPResponseWrapper)f).finishResponse();

        } catch (IOException e1) {

        } catch (ServletException e2) {

        } catch (Exception e3) {

        }

    }

    public void init(FilterConfig arg0) throws ServletException {

    }


}