package umc.dofarming.domain.memberMission;

import jakarta.persistence.*;
import lombok.*;
import umc.dofarming.domain.challengeMission.ChallengeMission;
import umc.dofarming.domain.enums.MissionStatus;
import umc.dofarming.domain.member.Member;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String proofUrl;

    private String proofText;

    @Enumerated(EnumType.STRING)
    private MissionStatus missionStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_mission_id")
    private ChallengeMission challengeMission;


}
