package ruby.settopinteresttagbit.repository

import org.springframework.data.jpa.repository.JpaRepository
import ruby.settopinteresttagbit.entity.Interest

interface InterestRepository : JpaRepository<Interest, Long>
