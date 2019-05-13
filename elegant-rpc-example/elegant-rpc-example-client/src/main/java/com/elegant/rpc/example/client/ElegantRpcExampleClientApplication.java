package com.elegant.rpc.example.client;

import com.elegant.rpc.core.annotation.EnableRpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author aman
 */
@EnableRpcClient
@SpringBootApplication
public class ElegantRpcExampleClientApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ElegantRpcExampleClientApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ElegantRpcExampleClientApplication.class);
    }
}

