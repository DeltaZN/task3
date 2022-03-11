package ru.itmo.task3.service

import org.springframework.stereotype.Service
import ru.itmo.task3.controller.PromoReqDTO
import ru.itmo.task3.controller.PromoRespDTO
import ru.itmo.task3.entity.PromoAction
import ru.itmo.task3.repository.PromoActionRepository
import java.util.*

@Service
class PromoActionService(
    val promoActionRepository: PromoActionRepository
) {
    fun createPromo(data: PromoReqDTO): Long {
        val action = PromoAction(name = data.name, description = data.description)
        promoActionRepository.save(action)
        return action.id
    }

    fun getAllPromos(): List<PromoRespDTO> {
        return promoActionRepository.findAll().map { a -> PromoRespDTO(a.id, a.name, a.description) }.toList()
    }

    fun getPromo(id: Long): Optional<PromoAction> {
        return promoActionRepository.findById(id)
    }

    fun editPromo(id: Long, data: PromoReqDTO): Boolean {
        val maybeAction = promoActionRepository.findById(id)
        if (maybeAction.isPresent) {
            val action = maybeAction.get()
            action.name = data.name
            action.description = data.description
            promoActionRepository.save(action)
            return true
        }
        return false
    }

    fun deletePromo(id: Long): Boolean {
        val action = promoActionRepository.findById(id)
        if (action.isPresent) {
            promoActionRepository.delete(action.get())
            return true
        }
        return false
    }
}