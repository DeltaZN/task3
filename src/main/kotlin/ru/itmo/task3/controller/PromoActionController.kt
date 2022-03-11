package ru.itmo.task3.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itmo.task3.entity.PromoAction
import ru.itmo.task3.service.PromoActionService
import ru.itmo.task3.service.PromoReqDTO
import ru.itmo.task3.service.PromoRespDTO
import javax.persistence.EntityNotFoundException

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
            throw EntityNotFoundException("Promo action with $id wasn't found.")
        }
    }

    @PutMapping("/{id}")
    fun editPromo(@PathVariable id: Long, @RequestBody body: PromoReqDTO): ResponseEntity<PromoAction> {
        val status = promoActionService.editPromo(id, body)
        return if (status) {
            ResponseEntity.ok().build()
        } else {
            throw EntityNotFoundException("Promo action with $id wasn't found.")
        }
    }

    @DeleteMapping("/{id}")
    fun deletePromo(@PathVariable id: Long): ResponseEntity<Unit> {
        val status = promoActionService.deletePromo(id)
        return if (status) {
            ResponseEntity.ok().build()
        } else {
            throw EntityNotFoundException("Promo action with $id wasn't found.")
        }
    }
}