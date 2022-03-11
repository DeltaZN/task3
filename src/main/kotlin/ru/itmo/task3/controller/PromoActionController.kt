package ru.itmo.task3.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itmo.task3.entity.PromoAction
import ru.itmo.task3.service.PromoActionService

data class PromoReqDTO(
    val name: String,
    val description: String?,
)

data class PromoRespDTO(
    val id: Long,
    val name: String,
    val description: String?,
)

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/promo")
class PromoActionController(
    val promoActionService: PromoActionService,
) {
    @PostMapping
    fun createPromo(@RequestBody body: PromoReqDTO): ResponseEntity<Long> {
        val id = promoActionService.createPromo(body)
        return ResponseEntity(id, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllPromos(): ResponseEntity<List<PromoRespDTO>> {
        return ResponseEntity.ok(promoActionService.getAllPromos())
    }

    @GetMapping("/{id}")
    fun getPromo(@PathVariable id: Long): ResponseEntity<PromoAction> {
        val result = promoActionService.getPromo(id)
        return if (result.isPresent) {
            ResponseEntity.ok(result.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun editPromo(@PathVariable id: Long, @RequestBody body: PromoReqDTO): ResponseEntity<PromoAction> {
        val status = promoActionService.editPromo(id, body)
        return if (status) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletePromo(@PathVariable id: Long): ResponseEntity<Unit> {
        val status = promoActionService.deletePromo(id)
        return if (status) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
//
//    @PostMapping("/{id}/participant")
//    fun addParticipant(@PathVariable id: Long, @RequestBody body: PromoReqDTO): ResponseEntity<Long> {
//        println(body)
//        return ResponseEntity.ok(1)
//    }
//
//    @DeleteMapping("/{id}/participant/{participantId}")
//    fun deleteParticipant(@PathVariable id: Long, @PathVariable participantId: Long): ResponseEntity<PromoAction> {
//        return ResponseEntity.ok(emptyList())
//    }
//
//    @PostMapping("/{id}/prize")
//    fun addPrize(@PathVariable id: Long, @RequestBody body: PromoReqDTO): ResponseEntity<Long> {
//        println(body)
//        return ResponseEntity.ok(1)
//    }
//
//    @DeleteMapping("/{id}/prize/{prizeId}")
//    fun deletePrize(@PathVariable id: Long, @PathVariable prizeId: Long): ResponseEntity<PromoAction> {
//        return ResponseEntity.ok(emptyList())
//    }
//
//    @PostMapping("/{id}/raffle")
//    fun raffle(@PathVariable id: Long): ResponseEntity<Long> {
//        println(body)
//        return ResponseEntity.ok(1)
//    }
}