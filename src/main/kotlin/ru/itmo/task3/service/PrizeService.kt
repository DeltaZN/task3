package ru.itmo.task3.service

import org.springframework.stereotype.Service
import ru.itmo.task3.controller.ParticipantDTO
import ru.itmo.task3.controller.PrizeDTO
import ru.itmo.task3.entity.Participant
import ru.itmo.task3.entity.Prize
import ru.itmo.task3.repository.PromoActionRepository
import java.util.*

@Service
class PrizeService(
    val promoActionRepository: PromoActionRepository
) {
    fun addPrize(promoId: Long, data: PrizeDTO): Optional<Long> {
        val maybeAction = promoActionRepository.findById(promoId)
        return if (maybeAction.isPresent) {
            val action = maybeAction.get()
            val prize = Prize(description = data.description)
            action.prizes.add(prize)
            promoActionRepository.save(action)
            Optional.of(action.prizes.last().id)
        } else {
            Optional.empty()
        }
    }

    fun removePrize(promoId: Long, prizeId: Long): Boolean {
        val maybeAction = promoActionRepository.findById(promoId)
        return if (maybeAction.isPresent) {
            val action = maybeAction.get()
            val removed = action.prizes.removeIf { p -> p.id == prizeId }
            promoActionRepository.save(action)
            removed
        } else {
            false
        }
    }
}