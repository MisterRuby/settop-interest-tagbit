package ruby.settopinteresttagbit.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ruby.settopinteresttagbit.repository.SetTopBoxRepository

@RestController
@RequestMapping("/setTopBox")
class SetTopBoxController(private val setTopBoxRepository: SetTopBoxRepository) {

   @GetMapping("/count/bitsum")
   fun getUserCountByBitSum(@RequestParam interestId: Long): String {
       // 측정 시작 시간
       val startTime = System.currentTimeMillis()

       // interestId 를 통해 취미에 해당하는 비트 값과 OR 연산하여 사용자 수를 구한다.
       val count = setTopBoxRepository.countByInterestIdAndInterestBitSum(interestId)

       // 측정 종료 시간
       val endTime = System.currentTimeMillis()

       return "개수 : $count / 조회 시간 : ${(endTime - startTime) / 1000} ms"
   }

    @GetMapping("/count")
    fun getUserCount(@RequestParam interestId: Long): String {
        // 측정 시작 시간
        val startTime = System.currentTimeMillis()

        // 1 : N 의 관계로 관심사 id 를 가진 사용자의 수를 구한다.
        val count = setTopBoxRepository.countByInterestId(interestId)

        // 측정 종료 시간
        val endTime = System.currentTimeMillis()

        return "개수 : $count / 조회 시간 : ${(endTime - startTime) / 1000} ms"
    }
}
