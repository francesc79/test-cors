package it.zazos.cors.config;

import org.ebaysf.web.cors.CORSFilter;
import it.zazos.cors.filter.IE8_9XDomainRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zanutto on 2/24/15.
 */
@Configuration
public class ApplicationConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean createIE8_9XDomainRequestFilter() {
        FilterRegistrationBean registrationBean =
                new FilterRegistrationBean();
        registrationBean.setFilter(new IE8_9XDomainRequestFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/rest/*"));
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean createCORSFilter() {
        FilterRegistrationBean registrationBean =
                new FilterRegistrationBean();
        CORSFilter corsFilter = new CORSFilter();
        Map<String, String> params = new HashMap<>();
        params.put("cors.allowed.origins","*");
        params.put("cors.support.credentials","false");
        params.put("cors.allowed.methods",CORSFilter.DEFAULT_ALLOWED_HTTP_METHODS + ",DELETE,PUT");
        params.put("cors.allowed.headers",CORSFilter.DEFAULT_ALLOWED_HTTP_HEADERS + ",authorization");
        params.put("cors.logging.enabled", "true");
        registrationBean.setInitParameters(params);
        registrationBean.setFilter(corsFilter);
        registrationBean.setUrlPatterns(Arrays.asList("/rest/*"));
        return registrationBean;
    }
}
