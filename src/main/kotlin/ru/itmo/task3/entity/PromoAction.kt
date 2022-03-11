package ru.itmo.task3.entity

import javax.persistence.*

@Entity
class PromoAction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String = "",
    var description: String? = null,
    @OneToMany(cascade = [CascadeType.ALL])
    var prizes: MutableList<Prize> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    var participants: MutableList<Participant> = mutableListOf(),
)