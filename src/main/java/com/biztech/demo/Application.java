package com.biztech.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Properties;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
//public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(Application.class);
//    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
//        return springApplicationBuilder
//                .sources(Application.class)
//                .properties(getProperties());
//    }
//
//    public static void main(String[] args) {
//
//        SpringApplicationBuilder springApplicationBuilder = (SpringApplicationBuilder) new SpringApplicationBuilder(Application.class)
//                .sources(Application.class)
//                .properties(getProperties())
//                .run(args);
//    }
//
//    static Properties getProperties() {
//        Properties props = new Properties();
//        props.put("spring.config.location", "classpath:demo-restful/");
//        return props;
//    }
}
