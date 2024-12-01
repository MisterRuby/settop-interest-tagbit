package ruby.settopinteresttagbit.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ruby.settopinteresttagbit.entity.SetTopBox

interface SetTopBoxRepository : JpaRepository<SetTopBox, Long> {
    @Query("SELECT COUNT(s) FROM SetTopBox s WHERE mod(s.interestBitSum / cast(power(2, :interestId) as long), 2) = 1")
    fun countByInterestIdAndInterestBitSum(interestId: Long): Long

    @Query("SELECT COUNT(s) FROM SetTopBox s INNER JOIN s.setTopBoxInterests ui WHERE ui.interest.id = :interestId")
    fun countByInterestId(interestId: Long): Long
}
