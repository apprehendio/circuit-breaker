package io.apprehend.centextech.circuitbreaker.controller

import io.apprehend.centextech.circuitbreaker.service.SketchyService
import org.springframework.web.bind.annotation.*

@RestController
class SketchyController(val sketchyService: SketchyService) {
    @GetMapping("/add")
    fun mightFail(@RequestParam("addend1") addend1: Int,
                  @RequestParam("addend2") addend2: Int): Map<String, Int> {
        return sketchyService.sketchyAdder(addend1, addend2)
    }

    @PostMapping("/set-safety-mode")
    fun setSafetyMode(@RequestParam("mode") mode: String) {
        sketchyService.setSafetyMode(mode.toUpperCase())
    }

    @PostMapping("/toggle-failure")
    fun toggleFailure() {
        sketchyService.toggleFailure()
    }

    @PostMapping("/toggle-slowness")
    fun toggleSlowness() {
        sketchyService.toggleSlowness()
    }

    @GetMapping("/safe-add")
    fun safeMightFail(@RequestParam("addend1") addend1: Int,
                      @RequestParam("addend2") addend2: Int): Map<String, Int> {
        return sketchyService.safeAdd(addend1, addend2)
    }
}