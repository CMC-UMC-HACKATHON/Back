package umc.dofarming.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.challenge.controller.response.ChallengeResultDto;
import umc.dofarming.domain.challenge.controller.response.ChallengeResultResponse;
import umc.dofarming.domain.challenge.controller.response.CheckChallengeLogResponse;
import umc.dofarming.domain.challenge.dto.ChallengeResponseDTO;
import umc.dofarming.domain.challenge.converter.ChallengeConverter;
import umc.dofarming.domain.challenge.repository.ChallengeRepository;
import umc.dofarming.domain.challengeMission.ChallengeMission;
import umc.dofarming.domain.challengeMission.repository.ChallengeMissionRepository;
import umc.dofarming.domain.challenge_log.ChallengeLog;
import umc.dofarming.domain.challenge_log.repository.ChallengeLogRepository;
import umc.dofarming.domain.enums.Mission;
import umc.dofarming.domain.enums.MissionStatus;
import umc.dofarming.domain.enums.MissionType;
import umc.dofarming.domain.member.repository.MemberRepository;
import umc.dofarming.domain.memberChallenge.repository.MemberChallengeRepository;
import umc.dofarming.domain.memberMission.MemberMission;
import umc.dofarming.domain.memberMission.repository.ListMemberMissionRepository;
import umc.dofarming.domain.memberMission.repository.MemberMissionRepository;
import umc.dofarming.util.SecurityUtils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import umc.dofarming.api_response.exception.GeneralException;
import umc.dofarming.api_response.status.ErrorStatus;
import umc.dofarming.domain.challenge.dto.ChallengeResponse;
import umc.dofarming.domain.challenge.mapper.ChallengeMapper;
import umc.dofarming.domain.enums.Category;
import umc.dofarming.domain.enums.RewardType;
import umc.dofarming.domain.enums.SortBy;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.member.service.DetailMemberService;
import umc.dofarming.domain.memberChallenge.MemberChallenge;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static umc.dofarming.domain.enums.Mission.COMMON1;
import static umc.dofarming.domain.enums.Mission.COMMON2;
import static umc.dofarming.domain.enums.Mission.COMMON4;
import static umc.dofarming.domain.enums.Mission.CULTURE1;
import static umc.dofarming.domain.enums.Mission.CULTURE2;
import static umc.dofarming.domain.enums.Mission.CULTURE3;
import static umc.dofarming.domain.enums.Mission.DAILY1;
import static umc.dofarming.domain.enums.Mission.DAILY2;
import static umc.dofarming.domain.enums.Mission.DAILY3;
import static umc.dofarming.domain.enums.Mission.EXERCISE1;
import static umc.dofarming.domain.enums.Mission.EXERCISE2;
import static umc.dofarming.domain.enums.Mission.EXERCISE3;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeService {

  private final MemberRepository memberRepository;
  private final MemberChallengeRepository memberChallengeRepository;
  private final ChallengeRepository challengeRepository;
  private final DetailMemberService detailMemberService;
  private final ChallengeLogRepository challengeLogRepository;
  private final ListMemberMissionRepository listMemberMissionRepository;
  private final ChallengeMissionRepository challengeMissionRepository;
  private final MemberMissionRepository memberMissionRepository;

  public List<ChallengeResponseDTO.GetMyChallengeInfoResult> findMyChallengeInfo(boolean ongoing) {
    String loginId = SecurityUtils.getCurrentMemberLoginId();
    Member member = memberRepository.findByLoginId(loginId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));
    List<Challenge> challengeList = memberChallengeRepository.findChallengesByMemberId(member.getId(), ongoing);
    List<ChallengeResponseDTO.GetMyChallengeInfoResult> getMyChallengeInfoResultList = new ArrayList<>();
    for (Challenge challenge : challengeList) {
      int count = memberChallengeRepository.findAllByChallenge(challenge).size();
      ChallengeResponseDTO.GetMyChallengeInfoResult getMyChallengeInfoResult = ChallengeConverter.toChallengeResponseDTO(challenge, count);
      getMyChallengeInfoResultList.add(getMyChallengeInfoResult);
    }
    return getMyChallengeInfoResultList;
  }

  //현재 진행중인 챌린지 리스트
  public List<ChallengeResponse.JoinChallenge> joinChallengeList(SortBy sortBy) {

    //최신순
    if (SortBy.LATEST.equals(sortBy)) {
      List<Challenge> challenges = challengeRepository.findByStartDateAfterOrderByStartDateDesc(now());

      List<ChallengeResponse.JoinChallenge> dto = challenges.stream()
        .map(challenge ->
          ChallengeMapper.toJoinChallenge //response 매핑
            (challenge, memberChallengeRepository.countByChallenge(challenge))
        )
        .collect(Collectors.toList());
      return validateChallengeCreation(dto, 0);

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

    } else {
      throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "SortBy 값 오류");
    }
  }

  //챌린지 상세보기
  @Transactional(readOnly = true)
  public ChallengeResponse.JoinChallengeDetail joinChallengeDetail(Long challengeId) {
    Optional<Challenge> challenge = challengeRepository.findById(challengeId);

    //유효한 id 값이 아니면
    if (challenge.isEmpty()) {
      throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "id 값에 해당하는 챌린지 존재하지 않음");
    }

    //시작일이 지난 챌린지인 경우
    if (challenge.get().getStartDate().isBefore(now())) {
      throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "시작 기한이 지난 챌린지");
    }

    return ChallengeMapper.toJoinChallengeDatail(
      challenge.get(), memberChallengeRepository.countByChallenge(challenge.get())
    );
  }

  //챌린지 참여하기
  @Transactional
  public Long postChallengeMission(Long challengeId) {

    Optional<Challenge> challenge = challengeRepository.findById(challengeId);
    //존재하는 챌린지 인가
    if (challenge.isEmpty()) {
      throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "id 값에 해당하는 챌린지 존재하지 않음");
    }
    Member member = detailMemberService.findByLoginId(SecurityUtils.getCurrentMemberLoginId());
    //이미 참여한 챌린지인가
    if (memberChallengeRepository.existsByMemberAndChallenge(
      member, challenge.get())
    ) {
      throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "이미 참여한 챌린지입니다.");
    }

    //시작 전인 챌린지인가
    if (challenge.get().getStartDate().isBefore(now())) {
      throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "시작 기한이 지난 챌린지");
    }

    Long savedId = memberChallengeRepository.save(MemberChallenge.builder()
      .challenge(challenge.get())
      .member(member)
      .build()).getId();

    List<ChallengeMission> missionList = challengeMissionRepository.findAllByChallengeId(challengeId);
    for (ChallengeMission mission : missionList) {
      memberMissionRepository.save(
        MemberMission.builder()
          .missionStatus(MissionStatus.IN_PROGRESS)
          .member(member)
          .challengeMission(mission)
          .build()
      );
    }

    return savedId;
  }

  public ChallengeResultResponse getChallengeResult(Long challengeId) {
    List<ChallengeResultDto> resultList = listMemberMissionRepository.getChallengeResult(challengeId);

    int myRanking = 0, myReward = 0;
    for (int i = 1; i <= resultList.size(); i++) {
      ChallengeResultDto dto = resultList.get(i - 1);
      int reward = 0;
      if (i == 1) {
        reward = dto.getMoney() / 2;
      } else if (i == 2) {
        reward = 3 * dto.getMoney() / 10;
      } else if (i == 3) {
        reward = 2 * dto.getMoney() / 10;
      }
      dto.update(1, Integer.toString(reward));

      if (dto.getLoginId().equals(SecurityUtils.getCurrentMemberLoginId())) {
        myRanking = i;
        myReward = reward;
      }
    }

    return ChallengeResultResponse.builder()
      .ranking(myRanking)
      .reward(Integer.toString(myReward))
      .rankingList(resultList.subList(0, Math.min(resultList.size(), 3)))
      .build();
  }

  @Transactional //챌린지 랜덤 생성
  public ChallengeResponse.JoinChallenge createChallenge() {
    Random random = new Random();

    Category category = Category.values()[random.nextInt(Category.values().length)];
    RewardType rewardType = RewardType.values()[random.nextInt(RewardType.values().length)];

    Integer money = (random.nextInt(5) + 1) * 10000; //1~5만원 랜덤

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

    challengeMissionRepository.save(
      ChallengeMission.builder()
        .challenge(challenge)
        .missionDate(LocalDateTime.now())
        .mission(Mission.COMMON1)
        .type(MissionType.REQUIRE)
        .build()
    );
    if (category.equals(Category.EXERCISE)) {
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(EXERCISE2)
          .type(MissionType.OPTION)
          .build()
      );
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(EXERCISE3)
          .type(MissionType.OPTION)
          .build()
      );
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(EXERCISE1)
          .type(MissionType.OPTION)
          .build()
      );
    } else if (category.equals(Category.COMMON)) {
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(COMMON4)
          .type(MissionType.OPTION)
          .build()
      );
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(COMMON1)
          .type(MissionType.OPTION)
          .build()
      );
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(COMMON2)
          .type(MissionType.OPTION)
          .build()
      );
    } else if (category.equals(Category.DAILY)) {
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(DAILY3)
          .type(MissionType.OPTION)
          .build()
      );
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(DAILY1)
          .type(MissionType.OPTION)
          .build()
      );
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(DAILY2)
          .type(MissionType.OPTION)
          .build()
      );
    } else if (category.equals(Category.CULTURE)) {
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(CULTURE1)
          .type(MissionType.OPTION)
          .build()
      );
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(CULTURE2)
          .type(MissionType.OPTION)
          .build()
      );
      challengeMissionRepository.save(
        ChallengeMission.builder()
          .challenge(challenge)
          .missionDate(LocalDateTime.now())
          .mission(CULTURE3)
          .type(MissionType.OPTION)
          .build()
      );
    }

    return ChallengeMapper.toJoinChallenge(challenge, 0);
  }

  //챌린지 생성 조건 검증
  @Transactional
  public List<ChallengeResponse.JoinChallenge> validateChallengeCreation(List<ChallengeResponse.JoinChallenge> dto, int index) {
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
  private LocalDateTime dateTimeFomater(LocalDateTime localDateTime) {
    localDateTime.withHour(0);
    localDateTime.withMinute(0);
    localDateTime.withSecond(0);
    localDateTime.withNano(0);

    return localDateTime;
  }

  public void saveChallengeLog(Long challengeId) {
    Member member = memberRepository.findByLoginId(SecurityUtils.getCurrentMemberLoginId())
      .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."));
    Challenge challenge = challengeRepository.findById(challengeId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 챌린저 ID입니다."));
    MemberChallenge memberChallenge = memberChallengeRepository.findByMemberAndChallenge(member, challenge)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));
    challengeLogRepository.save(
      ChallengeLog.builder()
        .memberChallenge(memberChallenge)
        .build()
    );
  }

  public CheckChallengeLogResponse checkChallengeLog(Long challengeId) {
    Member member = memberRepository.findByLoginId(SecurityUtils.getCurrentMemberLoginId())
      .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."));
    Challenge challenge = challengeRepository.findById(challengeId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 챌린저 ID입니다."));
    MemberChallenge memberChallenge = memberChallengeRepository.findByMemberAndChallenge(member, challenge)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));
    return CheckChallengeLogResponse.builder()
      .checkYN(challengeLogRepository.findByMemberChallenge(memberChallenge).isPresent())
      .build();
  }
}
