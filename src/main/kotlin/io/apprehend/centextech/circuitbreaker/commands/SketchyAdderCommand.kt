package io.apprehend.centextech.circuitbreaker.commands

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import io.apprehend.centextech.circuitbreaker.clients.UnsafeSketchyServiceClient


class SketchyAdderCommand(val sketchyServiceClient: UnsafeSketchyServiceClient,
                          val addend1: Int,
                          val addend2: Int):
        HystrixCommand<Map<String,Int>>(HystrixCommandGroupKey.Factory.asKey("sketchyAdder")) {
    override fun run(): Map<String, Int> {
        try {
            return sketchyServiceClient.add(addend1, addend2)
        } catch (ex : Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun getFallback(): Map<String, Int> {
        return mapOf(Pair("Fallback-sum", addend1 + addend2))
    }
}