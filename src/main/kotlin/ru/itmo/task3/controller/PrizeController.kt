package ru.itmo.task3.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itmo.task3.service.PrizeDTO
import ru.itmo.task3.service.PrizeRemoveStatus
import ru.itmo.task3.service.PrizeService
import javax.persistence.EntityNotFoundException

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
            throw EntityNotFoundException("Promo action with $id wasn't found.")
        }
    }

    @DeleteMapping("/{id}/prize/{prizeId}")
    fun deleteParticipant(@PathVariable id: Long, @PathVariable prizeId: Long): ResponseEntity<Unit> {
        return when (prizeService.removePrize(id, prizeId)) {
            PrizeRemoveStatus.SUCCESS -> ResponseEntity.ok().build()
            PrizeRemoveStatus.PRIZE_NOT_FOUND -> throw EntityNotFoundException("Prize with $prizeId wasn't found.")
            PrizeRemoveStatus.PROMO_NOT_FOUND -> throw EntityNotFoundException("Promo action with $id wasn't found.")
        }
    }
}