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
import umc.dofarming.domain.enums.Category;
import umc.dofarming.domain.enums.RewardType;
import umc.dofarming.domain.enums.SortBy;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.member.service.DetailMemberService;
import umc.dofarming.domain.memberChallenge.MemberChallenge;
import umc.dofarming.domain.memberChallenge.repository.MemberChallengeRepository;
import umc.dofarming.util.SecurityUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    private final DetailMemberService detailMemberService;

    //현재 진행중인 챌린지 리스트
    @Transactional
    public List<ChallengeResponse.JoinChallenge> joinChallengeList(SortBy sortBy) {
        //sortBy 검증
        if(sortBy == null){
            throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "SortBy 값 오류");
        }

        //최신순
        if(SortBy.LATEST.equals(sortBy)){
            List<Challenge> challenges =challengeRepository.findByStartDateAfterOrderByStartDateDesc(now());

            List<ChallengeResponse.JoinChallenge> dto = challenges.stream()
                            .map(challenge ->
                                    ChallengeMapper.toJoinChallenge //response 매핑
                                            (challenge, memberChallengeRepository.countByChallenge(challenge))
                            )
                                    .collect(Collectors.toList());
            return validateChallengeCreation(dto,0);

        } else if (SortBy.POPULAR.equals(sortBy)) {
            //인기순 정렬 (기준-> 참여하는 사람의 수)
            List<Challenge> challenges = challengeRepository.findByStartDateAfter(now());
            List<ChallengeResponse.JoinChallenge> dto = challenges.stream()
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
            return validateChallengeCreation(dto, -1);

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
        if(challenge.get().getStartDate().isBefore(now())){
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

    @Transactional //챌린지 랜덤 생성
    public ChallengeResponse.JoinChallenge createChallenge(){
        Random random = new Random();

        Category category = Category.values()[random.nextInt(Category.values().length)];
        RewardType rewardType = RewardType.values()[random.nextInt(RewardType.values().length)];


        Integer money = (random.nextInt(5) + 1)*10000; //1~5만원 랜덤

        LocalDateTime currentDate = LocalDateTime.now(); // 오늘 시간
        int startDayOffset = random.nextInt(16) + 10; // 10~25일 후에 시작
        LocalDateTime startDate = dateTimeFomater(currentDate.plusDays(startDayOffset));

        int weeksOffset = random.nextInt(4) + 1; // 마감일은 1~4주
        LocalDateTime endDate = dateTimeFomater(startDate.plusWeeks(weeksOffset));

        Challenge challenge = challengeRepository.save(
                Challenge.builder()
                        .money(money)
                        .title(category.getChallengeTitle())
                        .startDate(startDate)
                        .endDate(endDate)
                        .profileUrl(category.getS3Url())
                        .category(category)
                        .rewardType(rewardType)
                        .build()
        );

        return ChallengeMapper.toJoinChallenge(challenge, 0);
    }

    //챌린지 생성 조건 검증
    @Transactional
    public List<ChallengeResponse.JoinChallenge> validateChallengeCreation (List<ChallengeResponse.JoinChallenge> dto, int index){
        if (dto.size() < 4) { //챌린지 생성 조건 검증
            do {
                if (index == -1) {
                    dto.add(createChallenge()); //맨 뒤로 추가
                } else {
                    dto.add(index, createChallenge());
                }
            } while (dto.size() < 4);
        }
        return dto;
    }

    //00시로 맞춤
    private LocalDateTime dateTimeFomater(LocalDateTime localDateTime){
        localDateTime.withHour(0);
        localDateTime.withMinute(0);
        localDateTime.withSecond(0);
        localDateTime.withNano(0);

        return localDateTime;
    }

}
