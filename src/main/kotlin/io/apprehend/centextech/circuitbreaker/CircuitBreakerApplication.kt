package io.apprehend.centextech.circuitbreaker

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer
import org.springframework.context.annotation.Configuration
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard

@Configuration
@EnableConfigServer
@EnableEurekaServer
@EnableHystrixDashboard
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@SpringBootApplication
class CircuitBreakerApplication

fun main(args: Array<String>) {
    runApplication<CircuitBreakerApplication>(*args)
}
