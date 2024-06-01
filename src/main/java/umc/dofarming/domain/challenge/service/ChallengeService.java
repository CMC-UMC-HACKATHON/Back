package umc.dofarming.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.dofarming.api_response.exception.GeneralException;
import umc.dofarming.api_response.status.ErrorStatus;
import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.challenge.dto.ChallengeResponse;
import umc.dofarming.domain.challenge.mapper.ChallengeMapper;
import umc.dofarming.domain.challenge.repository.ChallengeRepository;
import umc.dofarming.domain.enums.SortBy;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.member.service.DetailMemberService;
import umc.dofarming.domain.memberChallenge.MemberChallenge;
import umc.dofarming.domain.memberChallenge.repository.MemberChallengeRepository;
import umc.dofarming.util.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    private final DetailMemberService detailMemberService;

    //현재 진행중인 챌린지 리스트
    @Transactional(readOnly = true)
    public List<ChallengeResponse.JoinChallenge> joinChallengeList(SortBy sortBy) {
        //sortBy 검증
        if(sortBy == null){
            throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "SortBy 값 오류");
        }

        //최신순
        if(SortBy.LATEST.equals(sortBy)){
            List<Challenge> challenges =challengeRepository.findByStartDateAfterOrderByStartDateDesc(LocalDateTime.now());

            return challenges.stream()
                            .map(challenge ->
                                    ChallengeMapper.toJoinChallenge //response 매핑
                                            (challenge, memberChallengeRepository.countByChallenge(challenge))
                            )
                                    .collect(Collectors.toList());


        } else if (SortBy.POPULAR.equals(sortBy)) {
            //인기순 정렬 (기준-> 참여하는 사람의 수)
            List<Challenge> challenges = challengeRepository.findByStartDateAfter(LocalDateTime.now());
            return challenges.stream()
                    .sorted((c1, c2) -> {
                        long count1 = memberChallengeRepository.countByChallenge(c1);
                        long count2 = memberChallengeRepository.countByChallenge(c2);
                        return Long.compare(count2, count1); // 내림차순 정렬
                    })
                    .map(challenge ->
                            ChallengeMapper.toJoinChallenge //response 매핑
                                    (challenge, memberChallengeRepository.countByChallenge(challenge))
                    )
                    .collect(Collectors.toList());
        }else{
            throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "SortBy 값 오류");
        }
    }

    //챌린지 상세보기
    @Transactional(readOnly = true)
    public ChallengeResponse.JoinChallengeDetail joinChallengeDetail(Long challengeId){
        Optional<Challenge> challenge = challengeRepository.findById(challengeId);

        //유효한 id 값이 아니면
        if(challenge.isEmpty()){
            throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "id 값에 해당하는 챌린지 존재하지 않음");
        }

        //시작일이 지난 챌린지인 경우
        if(challenge.get().getStartDate().isBefore(LocalDateTime.now())){
            throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "시작 기한이 지난 챌린지");
        }

        return ChallengeMapper.toJoinChallengeDatail(
                challenge.get(), memberChallengeRepository.countByChallenge(challenge.get())
        );
    }

    //챌린지 참여하기
    @Transactional
    public Long postChallengeMission(Long challengeId){

        Optional<Challenge> challenge=challengeRepository.findById(challengeId);
        //존재하는 챌린지 인가
        if(challenge.isEmpty()){
            throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "id 값에 해당하는 챌린지 존재하지 않음");
        }
        Member member = detailMemberService.findByLoginId(SecurityUtils.getCurrentMemberLoginId());
        //이미 참여한 챌린지인가
        if(memberChallengeRepository.existsByMemberAndChallenge(
                member
                , challenge.get())
        ){
            throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "이미 참여한 챌린지입니다.");
        }

        //시작 전인 챌린지인가
        if(challenge.get().getStartDate().isBefore(now())){
            throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "시작 기한이 지난 챌린지");
        }

        return memberChallengeRepository.save(MemberChallenge.builder()
                .challenge(challenge.get())
                .member(member)
                .build()).getId();
    }

}
