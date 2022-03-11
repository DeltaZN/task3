package ru.itmo.task3.entity

import javax.persistence.*

@Entity
class RaffleWinner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @OneToOne
    var winner: Participant = Participant(),
    @OneToOne
    var prize: Prize = Prize(),
)