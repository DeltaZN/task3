package ru.itmo.task3.repository

import org.springframework.data.repository.CrudRepository
import ru.itmo.task3.entity.PromoAction

interface PromoActionRepository : CrudRepository<PromoAction, Long>