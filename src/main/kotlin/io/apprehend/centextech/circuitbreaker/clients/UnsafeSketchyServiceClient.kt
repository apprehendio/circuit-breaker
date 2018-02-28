package io.apprehend.centextech.circuitbreaker.clients

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import feign.Feign

@FeignClient(name = "unsafe-circuit-breaker", url = "http://localhost:8761", configuration = [UnsafeFeignConfig::class])
interface UnsafeSketchyServiceClient {
    @RequestMapping("/add",method = [RequestMethod.GET])
    fun add(@RequestParam("addend1") addend1: Int, @RequestParam("addend2") addend2: Int) : Map<String, Int>
}

class UnsafeFeignConfig {
    @Bean
    @Scope("prototype")
    fun feignBuilder(): Feign.Builder {
        return Feign.builder()
    }
}