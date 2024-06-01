package umc.dofarming.domain.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.dofarming.domain.challenge.Challenge;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> findByStartDateAfterOrderByStartDateDesc(LocalDateTime date); // 최신순 정렬
    List<Challenge> findByStartDateAfter(LocalDateTime date); //시작 전인 챌린지만 뽑아오기
    Optional<Challenge> findById(Long id);
}
