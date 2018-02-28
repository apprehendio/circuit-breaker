package io.apprehend.centextech.circuitbreaker.clients

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(value = "circuit-breaker", fallback = SafeSketchyServiceClientFallback::class)
interface SafeSketchyServiceClient {
    @RequestMapping("/add",method = [RequestMethod.GET])
    fun add(@RequestParam("addend1") addend1: Int, @RequestParam("addend2") addend2: Int) : Map<String, Int>
}

@Component
class SafeSketchyServiceClientFallback: SafeSketchyServiceClient {
    override fun add(addend1: Int, addend2: Int): Map<String, Int> {
        return mapOf(Pair("SafeClientFallback-Sum",addend1 + addend2))
    }

}


