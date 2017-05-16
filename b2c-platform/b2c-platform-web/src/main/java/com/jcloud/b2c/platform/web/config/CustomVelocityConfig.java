package com.jcloud.b2c.platform.web.config;

import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

@Configuration
public class CustomVelocityConfig {
    @Bean(name = "velocityViewResolver")
    public VelocityViewResolver velocityViewResolver(VelocityProperties properties) {
        VelocityLayoutViewResolver viewResolver = new VelocityLayoutViewResolver();
        viewResolver.setExposeRequestAttributes(true);
//        viewResolver.setSuffix(".vm");
//        viewResolver.setPrefix("");
        properties.applyToViewResolver(viewResolver);
        return viewResolver;
    }

}
