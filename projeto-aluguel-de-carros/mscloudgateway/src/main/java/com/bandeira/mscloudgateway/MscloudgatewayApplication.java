package com.bandeira.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableDiscoveryClient
public class MscloudgatewayApplication {

    public static void main(String[] args) throws Exception{
		SpringApplication.run(MscloudgatewayApplication.class, args);
	}


	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder
				.routes()
				.route(r -> r.path("/users/**").uri("lb://ms-users"))
				.route(r -> r.path("/cars/**").uri("lb://ms-cars"))
				.route(r -> r.path("/store/**").uri("lb://ms-reservation"))
				.route(r -> r.path("/emails/**").uri("lb://ms-emails"))
				.build();
	}

}
