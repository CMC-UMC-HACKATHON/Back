package umc.dofarming.domain.challengeMission;

import jakarta.persistence.*;
import lombok.*;
import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.enums.Mission;
import umc.dofarming.domain.memberMission.MemberMission;

import umc.dofarming.domain.enums.MissionType;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime missionDate;

    @Enumerated(EnumType.STRING)
    private Mission mission;

    @Enumerated(EnumType.STRING)
    private MissionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mission_id")
    private MemberMission memberMission;
}
