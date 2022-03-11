package ru.itmo.task3.service

import org.springframework.stereotype.Service
import ru.itmo.task3.entity.Participant
import ru.itmo.task3.repository.PromoActionRepository
import java.util.*

data class ParticipantDTO(
    val name: String,
)

@Service
class ParticipantService(
    val promoActionRepository: PromoActionRepository
) {
    fun addParticipant(promoId: Long, data: ParticipantDTO): Optional<Long> {
        val maybeAction = promoActionRepository.findById(promoId)
        return if (maybeAction.isPresent) {
            val action = maybeAction.get()
            val participant = Participant(name = data.name)
            action.participants.add(participant)
            promoActionRepository.save(action)
            Optional.of(action.participants.last().id)
        } else {
            Optional.empty()
        }
    }

    fun removeParticipant(promoId: Long, participantId: Long): Boolean {
        val maybeAction = promoActionRepository.findById(promoId)
        return if (maybeAction.isPresent) {
            val action = maybeAction.get()
            val removed = action.participants.removeIf { p -> p.id == participantId }
            promoActionRepository.save(action)
            removed
        } else {
            false
        }
    }
}