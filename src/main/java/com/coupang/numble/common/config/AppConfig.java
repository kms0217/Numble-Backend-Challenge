package com.coupang.numble.common.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class AppConfig {

    @Profile("dev")
    @Configuration
    public class LocalMvcConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                .addResourceHandler("/file/**")
                .addResourceLocations("file:///")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
        }
    }

    @Profile("prod")
    @Configuration
    public class ProdMvcConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                .addResourceHandler("/file/**")
                .addResourceLocations("file:/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
        }
    }

    @Bean
    public ModelMapper modelmapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setSkipNullEnabled(true)
            .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
