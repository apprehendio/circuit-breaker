package io.apprehend.centextech.circuitbreaker.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import io.apprehend.centextech.circuitbreaker.clients.UnsafeSketchyServiceClient
import io.apprehend.centextech.circuitbreaker.clients.SafeSketchyServiceClient
import io.apprehend.centextech.circuitbreaker.commands.SketchyAdderCommand
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class SketchyService(
        val safeSketchyServiceClient: SafeSketchyServiceClient,
        val unsafeSketchyServiceClient: UnsafeSketchyServiceClient) {

    private val log = LogFactory.getLog(SketchyService::class.java)

    var isSlow : Boolean = false
    var isFailure : Boolean = false
    val random : Random = Random()
    private var safetyMode: SketchyService.SafetyMode = SafetyMode.ANNOTATION

    enum class SafetyMode {
        BUILTIN, COMMAND, ANNOTATION
    }

    fun sketchyAdder(addend1: Int, addend2: Int): Map<String, Int> {
        val delay = random.nextInt(100) * 100L
        val no = if (isFailure) "" else "no"
        log.info("Performing a sketchy add with $no failure and a ${delay}ms delay.")

        if(isFailure) throw Exception("Service is failing")

        if(isSlow) Thread.sleep(delay)

        return mapOf(Pair("sum", addend1 + addend2))
    }

    fun fallbackAdder(addend1: Int, addend2: Int): Map<String, Int> {
        return mapOf(Pair("Annotation-fallback", addend1 + addend2))
    }


    fun setSafetyMode(safetyMode: String) {
        this.safetyMode = SafetyMode.valueOf(safetyMode)
    }

    fun toggleSlowness() {
        isSlow = !isSlow
    }

    fun toggleFailure() {
        isFailure = !isFailure
    }

    fun safeAdd(addend1: Int, addend2: Int): Map<String, Int> {
        return when(this.safetyMode) {
            SafetyMode.ANNOTATION -> safeAddWithHystrixAnnotation(addend1, addend2)
            SafetyMode.COMMAND -> safeAddWithHystrix(addend1, addend2)
            SafetyMode.BUILTIN -> safeAddWithFeignClientBuiltinHystrix(addend1, addend2)
        }
    }

    /*
    Add safely by calling the service via a HystrixCommand descendant
     */
    fun safeAddWithHystrix(addend1: Int, addend2: Int): Map<String, Int> {
       return SketchyAdderCommand(unsafeSketchyServiceClient, addend1, addend2).execute()
    }

    /*
    Add safely using the @HHystrixCommand annotation
     */
    @HystrixCommand(fallbackMethod = "fallbackAdder")
    fun safeAddWithHystrixAnnotation(addend1: Int, addend2: Int): Map<String, Int> {
        return unsafeSketchyServiceClient.add(addend1, addend2)
    }

    /*
    Add safely by calling the service via a HystrixCommand descendant
     */
    fun safeAddWithFeignClientBuiltinHystrix(addend1: Int, addend2: Int): Map<String, Int> {
        return safeSketchyServiceClient.add(addend1, addend2)
    }
}