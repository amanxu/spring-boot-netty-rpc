package com.elegant.rpc.example.server;

import com.elegant.rpc.core.annotation.EnableRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author aman
 */
@EnableRpcServer
@SpringBootApplication
public class ElegantRpcExampleServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ElegantRpcExampleServerApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ElegantRpcExampleServerApplication.class);
    }

}

