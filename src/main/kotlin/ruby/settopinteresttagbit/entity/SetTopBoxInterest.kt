package ruby.settopinteresttagbit.entity

import jakarta.persistence.*

@Entity
@Table(name = "SETTOPBOX_INTEREST")
data class SetTopBoxInterest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id", nullable = false)
    val setTopBox: SetTopBox,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id", nullable = false)
    val interest: Interest
)
