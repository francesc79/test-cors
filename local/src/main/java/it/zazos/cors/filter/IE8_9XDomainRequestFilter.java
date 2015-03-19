package it.zazos.cors.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by zanutto on 3/11/15.
 */
public class IE8_9XDomainRequestFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        chain.doFilter(new HttpServletRequestWrapper (request), res);
    }

    @Override
    public void destroy() {
    }

    private static class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {
        private final Logger logger = LoggerFactory.getLogger(getClass());
        private Map<String, String[]> params = new HashMap<String, String[]>();

        public HttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            super (request);
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                if (super.getContentType() == null || "text/plain".equals(super.getContentType())) {
                    if ("application/x-www-form-urlencoded".equals(getContentType())) {

                        // read content
                        StringBuilder textBuilder = new StringBuilder();
                        try (Reader reader = new BufferedReader(new InputStreamReader (getInputStream(), Charset.forName(StandardCharsets.UTF_8.name())))) {
                            int c = 0;
                            while ((c = reader.read()) != -1) {
                                textBuilder.append((char) c);
                            }
                        }

                        // parse content
                        Map<String, List<String>> map = new HashMap<String, List<String>>();
                        final String[] pairs = textBuilder.toString().split("&");
                        for (String pair : pairs) {
                            final int idx = pair.indexOf("=");
                            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8.name()) : pair;
                            if (!map.containsKey(key)) {
                                map.put(key, new LinkedList<String>());
                            }
                            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8.name()) : null;
                            map.get(key).add(value);
                        }

                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                            params.put(entry.getKey(), entry.getValue().toArray(new String[0]));
                        }
                    }
                }
            }
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            if (value == null) {
                String []values = params.get(name);
                if (values != null && values.length > 0) {
                    value = values[0];
                }
            }
            return value;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> map = new HashMap<String, String[]>(super.getParameterMap());
            map.putAll(params);
            return map;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            final Iterator<String> iterator = getParameterMap().keySet().iterator();
            return new Enumeration<String> () {

                @Override
                public boolean hasMoreElements() {
                    return iterator.hasNext();
                }

                @Override
                public String nextElement() {
                    return iterator.next();
                }
            };
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values == null) {
                values = params.get(name);
            }
            return values;
        }

        @Override
        public String getHeader (String name) {
            String value = super.getHeader(name);
            if (value == null && "Authorization".equals(name)) {
                value = this.getParameter(name);
            }
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("get header key:%s value:%s", name, value));
            }
            return value;
        }

        @Override
        public String getContentType() {
            String contentType = super.getContentType();
            if (contentType == null) {
                contentType = this.getParameter("Content-Type");
            }
            return contentType;
        }
    }
}
