package it.zazos.cors.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.ebaysf.web.cors.CORSFilter;
import it.zazos.cors.filter.IE8_9XDomainRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    /*
    * https://thoughtfulsoftware.wordpress.com/2014/01/05/setting-up-https-for-spring-boot/
    * */
    @Bean
    public EmbeddedServletContainerFactory servletContainer() throws IOException {
        // keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addAdditionalTomcatConnectors(createSslConnector());
        return factory;
    }

    private File getKeyStoreFile() throws IOException {
        ClassPathResource resource = new ClassPathResource("/keystore.p12");
        try {
            return resource.getFile();
        }
        catch (Exception ex) {
            File temp = File.createTempFile("keystore", ".tmp");
            FileCopyUtils.copy(resource.getInputStream(), new FileOutputStream(temp));
            return temp;
        }
    }

    private Connector createSslConnector() throws IOException {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("https");
        connector.setSecure(true);
        connector.setPort(8090);

        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        protocol.setSSLEnabled(true);
        protocol.setKeystoreFile(getKeyStoreFile().getAbsolutePath());
        protocol.setKeystorePass("mypassword");
        protocol.setKeystoreType("PKCS12");
        protocol.setProperty("keystoreProvider", "SunJSSE");
        protocol.setKeyAlias("tomcat");
        return connector;
    }
}
