package ru.itmo.task3.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itmo.task3.service.ParticipantDTO
import ru.itmo.task3.service.ParticipantRemoveStatus
import ru.itmo.task3.service.ParticipantService
import javax.persistence.EntityNotFoundException

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/promo")
class ParticipantController(
    val participantService: ParticipantService,
) {
    @PostMapping("/{id}/participant")
    fun addParticipant(@PathVariable id: Long, @RequestBody body: ParticipantDTO): ResponseEntity<Long> {
        val result = participantService.addParticipant(id, body)
        return if (result.isPresent) {
            ResponseEntity.ok(result.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}/participant/{participantId}")
    fun deleteParticipant(@PathVariable id: Long, @PathVariable participantId: Long): ResponseEntity<Unit> {
        return when (participantService.removeParticipant(id, participantId)) {
            ParticipantRemoveStatus.SUCCESS -> ResponseEntity.ok().build()
            ParticipantRemoveStatus.PARTICIPANT_NOT_FOUND -> throw EntityNotFoundException("Participant with $participantId wasn't found.")
            ParticipantRemoveStatus.PROMO_NOT_FOUND -> throw EntityNotFoundException("Promo action with $id wasn't found.")
        }
    }
}