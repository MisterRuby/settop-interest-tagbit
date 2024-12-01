package ruby.settopinteresttagbit.repository

import org.springframework.data.jpa.repository.JpaRepository
import ruby.settopinteresttagbit.entity.SetTopBoxInterest

interface SetTopBoxInterestRepository : JpaRepository<SetTopBoxInterest, Long>
