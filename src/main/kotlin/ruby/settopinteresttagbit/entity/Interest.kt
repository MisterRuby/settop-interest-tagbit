package ruby.settopinteresttagbit.entity

import jakarta.persistence.*

@Entity
@Table(name = "INTEREST_TAG")
data class Interest(

    // 아이디 값만큼 2의 제곱수가 비트 값이 된다.
    // Long 의 범위를 초과할 수 없으므로 id는 최대 63까지 허용
    // ex) id 가 3일 경우 비트 값은 1000. i가 6일 경우 1000000
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val interestName: String,

    @Column(nullable = false)
    val bitValue: Long = 0,

    @OneToMany(mappedBy = "interest", cascade = [CascadeType.ALL], orphanRemoval = true)
    val setTopBoxInterests: List<SetTopBoxInterest> = mutableListOf()
)
