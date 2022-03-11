package ru.itmo.task3.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itmo.task3.controller.advice.RaffleException
import ru.itmo.task3.service.RaffleResultDTO
import ru.itmo.task3.service.RaffleService
import ru.itmo.task3.service.RaffleStatus
import javax.persistence.EntityNotFoundException


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/promo")
class RaffleController(
    private val raffleService: RaffleService,
) {
    @PostMapping("/{id}/raffle")
    fun raffle(@PathVariable id: Long): ResponseEntity<List<RaffleResultDTO>> {
        val (status, raffleResults) = raffleService.performRaffle(id)
        return when (status) {
            RaffleStatus.SUCCESS -> ResponseEntity.ok(raffleResults)
            RaffleStatus.ACTION_NOT_FOUND -> throw EntityNotFoundException("Promo action with $id wasn't found.")
            RaffleStatus.CONFLICT_PARTICIPANTS_PRIZES -> throw RaffleException("Number of prizes doesn't match participants number.")
        }
    }
}