package org.cisiondata.modules.bootstrap;

import org.cisiondata.modules.filter.CustomFilterProcessor;
import org.cisiondata.modules.filter.ErrorExtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@EnableOAuth2Sso
@EnableZuulProxy
@EnableEurekaClient
@SpringCloudApplication  
@EnableAutoConfiguration  
@ComponentScan(basePackages={"org.cisiondata"})
public class BootstrapApplication {

	private static Logger LOG = LoggerFactory.getLogger(BootstrapApplication.class);
	
	@Bean
	public CustomFilterProcessor CustomFilterProcessor() {
		return new CustomFilterProcessor();
	}
	
	@Bean
	public ErrorExtFilter errorExtFilter() {
		return new ErrorExtFilter();
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {    
	    return new EmbeddedServletContainerCustomizer(){        
	        @Override        
	         public void customize(ConfigurableEmbeddedServletContainer container) {            
	            container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400.html"));            
	            container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401.html"));            
	            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html"));        
	            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html"));            
	        }    
	    };
	}

	public static void main(String[] args) {
		SpringApplication.run(BootstrapApplication.class, args);
		LOG.info("API Gateway Server Bootstrap");
	}
	
	
}
