package ru.itmo.task3.service

import org.springframework.stereotype.Service
import ru.itmo.task3.entity.Participant
import ru.itmo.task3.entity.Prize
import ru.itmo.task3.repository.PromoActionRepository

enum class RaffleStatus {
    ACTION_NOT_FOUND,
    CONFLICT_PARTICIPANTS_PRIZES,
    SUCCESS,
}

data class RaffleResponse(
    val status: RaffleStatus,
    val raffleResults: List<RaffleResultDTO>,
)

data class RaffleResultDTO(
    var winner: Participant,
    var prize: Prize,
)

@Service
class RaffleService(
    val promoActionRepository: PromoActionRepository
) {
    fun performRaffle(promoId: Long): RaffleResponse {
        val maybeAction = promoActionRepository.findById(promoId)
        if (maybeAction.isPresent) {
            val action = maybeAction.get()
            if (action.participants.size != action.prizes.size) {
                return RaffleResponse(RaffleStatus.CONFLICT_PARTICIPANTS_PRIZES, emptyList())
            }
            action.prizes.shuffle()
            val result = action.participants.mapIndexed { idx, participant -> RaffleResultDTO(participant, action.prizes[idx]) }
            return RaffleResponse(RaffleStatus.SUCCESS, result)
        } else {
            return RaffleResponse(RaffleStatus.ACTION_NOT_FOUND, emptyList())
        }
    }
}