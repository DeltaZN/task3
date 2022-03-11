package ru.itmo.task3.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itmo.task3.service.PrizeDTO
import ru.itmo.task3.service.PrizeService

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/promo")
class PrizeController(
    val prizeService: PrizeService,
) {
    @PostMapping("/{id}/prize")
    fun addPrize(@PathVariable id: Long, @RequestBody body: PrizeDTO): ResponseEntity<Long> {
        val result = prizeService.addPrize(id, body)
        return if (result.isPresent) {
            ResponseEntity.ok(result.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}/prize/{prizeId}")
    fun deleteParticipant(@PathVariable id: Long, @PathVariable prizeId: Long): ResponseEntity<Unit> {
        val status = prizeService.removePrize(id, prizeId)
        return if (status) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}