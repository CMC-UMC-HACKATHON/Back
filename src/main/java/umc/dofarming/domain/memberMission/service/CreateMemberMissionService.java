package umc.dofarming.domain.memberMission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.dofarming.api_response.exception.GeneralException;
import umc.dofarming.api_response.status.ErrorStatus;
import umc.dofarming.domain.challengeMission.ChallengeMission;
import umc.dofarming.domain.challengeMission.controller.request.ProofMissionRequest;
import umc.dofarming.domain.challengeMission.repository.ChallengeMissionRepository;
import umc.dofarming.domain.enums.MissionStatus;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.member.repository.MemberRepository;
import umc.dofarming.domain.memberMission.MemberMission;
import umc.dofarming.domain.memberMission.repository.MemberMissionRepository;
import umc.dofarming.util.SecurityUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateMemberMissionService {

  private final ChallengeMissionRepository challengeMissionRepository;
  private final MemberMissionRepository memberMissionRepository;
  private final MemberRepository memberRepository;

  public void proofMission(Long missionId, ProofMissionRequest request) {
    ChallengeMission challengeMission = challengeMissionRepository.findById(missionId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 미션 ID입니다."));

    Member member = memberRepository.findByLoginId(SecurityUtils.getCurrentMemberLoginId())
      .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."));
    MemberMission memberMission = memberMissionRepository.findByChallengeMissionAndMember(challengeMission, member)
      .orElseThrow(() -> new GeneralException(ErrorStatus.INTERNAL_ERROR));
    if (memberMission.getMissionStatus().equals(MissionStatus.COMPLETE)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "이미 인증한 미션입니다.");
    }
    memberMission.proofMission(request.getProofUrl(), request.getProofText());

  }
}
