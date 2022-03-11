package ru.itmo.task3.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itmo.task3.service.ParticipantService

data class ParticipantDTO(
    val name: String,
)

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
        val status = participantService.removeParticipant(id, participantId)
        return if (status) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}