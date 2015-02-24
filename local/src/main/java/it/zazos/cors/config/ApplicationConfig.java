package it.zazos.cors.config;

import org.ebaysf.web.cors.CORSFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zanutto on 2/24/15.
 */
@Configuration
public class ApplicationConfig {
    @Bean
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean registrationBean =
                new FilterRegistrationBean();
        CORSFilter corsFilter = new CORSFilter();
        Map<String, String> params = new HashMap<>();
        params.put("cors.allowed.methods",CORSFilter.DEFAULT_ALLOWED_HTTP_METHODS + ",DELETE,PUT");
        params.put("cors.allowed.headers",CORSFilter.DEFAULT_ALLOWED_HTTP_HEADERS + ",authorization");
        params.put("cors.logging.enabled", "true");
        registrationBean.setInitParameters(params);
        registrationBean.setFilter(corsFilter);
        registrationBean.setUrlPatterns(Arrays.asList("/rest/*"));
        return registrationBean;
    }
}
