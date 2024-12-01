package ruby.settopinteresttagbit.entity

import jakarta.persistence.*

@Entity
@Table(name = "SETTOPBOX")
data class SetTopBox(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val interestBitSum: Long = 0,

    @OneToMany(mappedBy = "setTopBox", cascade = [CascadeType.ALL], orphanRemoval = true)
    val setTopBoxInterests: List<SetTopBoxInterest> = mutableListOf()
)
