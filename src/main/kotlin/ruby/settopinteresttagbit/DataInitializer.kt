package ruby.settopinteresttagbit

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ruby.settopinteresttagbit.entity.Interest
import ruby.settopinteresttagbit.entity.SetTopBox
import ruby.settopinteresttagbit.entity.SetTopBoxInterest
import ruby.settopinteresttagbit.repository.InterestRepository
import ruby.settopinteresttagbit.repository.SetTopBoxInterestRepository
import ruby.settopinteresttagbit.repository.SetTopBoxRepository
import kotlin.math.pow

@Configuration
class DataInitializer (
    private val setTopBoxRepository: SetTopBoxRepository,
    private val interestRepository: InterestRepository,
    private val setTopBoxInterestRepository: SetTopBoxInterestRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun initDatabase(): CommandLineRunner {
        return CommandLineRunner {
            if (setTopBoxRepository.count() == 0L) {
                log.info("=================initDatabase insert start===================")
                interestRepository.deleteAll()
                setTopBoxRepository.deleteAll()

                generateInterests()
                generateUsers()
                log.info("=================initDatabase insert end===================")
            }
        }
    }

    private fun generateInterests() {
        val interestNames = listOf(
            "드라마", "코미디", "스릴러", "호러", "액션", "판타지", "SF(공상 과학)", "로맨스", "미스터리", "범죄", "역사", "음악",
            "다큐멘터리", "리얼리티", "애니메이션", "토크쇼", "게임쇼", "스포츠", "뉴스", "교육", "여행", "요리", "건강", "자연", "패션",
            "가족", "법정", "정치", "스탠드업 코미디", "오컬트", "무협", "서부극", "청춘", "군사", "초자연적", "탐험", "생존", "모험",
            "민속", "문학", "시사", "생명 공학", "기술", "종교", "예술", "인터뷰", "인디", "스파이", "파헤치기", "애국가"
        )

        var index = 0L
        val interests = interestNames
            .map { interestName -> Interest(interestName = interestName, bitValue = 2.0.pow(++index * 1.0).toLong()) }
            .toList()

        interestRepository.saveAll(interests)
    }

    private fun generateUsers() {
        val interests = interestRepository.findAll()
        val interestSize = interests.size
        val batchSize = 1000 // 한 번에 저장할 배치 크기

        val setTopBoxBatch = mutableListOf<SetTopBox>()
        val setTopBoxInterestsBatch = mutableListOf<SetTopBoxInterest>()
        var setTopBoxInsertRow = 0L
        var setTopBoxInterestInsertRow = 0L

        (1..10_000_000).asSequence().forEach { _ ->
            val selectInterestCount = (3..10).random()
            val randomIndices = getRandomNumbers(interestSize, selectInterestCount)
            val interestBitSum = randomIndices.sumOf { index -> interests[index].bitValue }

            val setTopBox = SetTopBox(interestBitSum = interestBitSum)
            setTopBoxBatch.add(setTopBox) // UserInfo 객체 추가

            randomIndices.forEach { index ->
                val setTopBoxInterest = SetTopBoxInterest(setTopBox = setTopBox, interest = interests[index])
                setTopBoxInterestsBatch.add(setTopBoxInterest) // UserInterest 객체 추가
            }

            // 지정된 배치 크기에 도달하면 한 번에 저장
            if (setTopBoxBatch.size >= batchSize) {
                setTopBoxRepository.saveAll(setTopBoxBatch)
                setTopBoxRepository.flush() // 변경 사항 즉시 반영

                setTopBoxInterestRepository.saveAll(setTopBoxInterestsBatch)
                setTopBoxInterestRepository.flush()

                setTopBoxInsertRow += setTopBoxBatch.size
                setTopBoxInterestInsertRow += setTopBoxInterestsBatch.size
                log.info("setTopBoxInsertRow : $setTopBoxInsertRow row")

                setTopBoxBatch.clear()
                setTopBoxInterestsBatch.clear()
            }
        }

        // 남아있는 데이터 저장
        if (setTopBoxBatch.isNotEmpty()) {
            setTopBoxRepository.saveAll(setTopBoxBatch)
            setTopBoxInterestRepository.saveAll(setTopBoxInterestsBatch)

            setTopBoxInsertRow += setTopBoxBatch.size
            setTopBoxInterestInsertRow += setTopBoxInterestsBatch.size
        }
    }

    private fun getRandomNumbers(rangeSize: Int, count: Int): List<Int> {
        return (0 until rangeSize).shuffled().take(count)
    }
}
