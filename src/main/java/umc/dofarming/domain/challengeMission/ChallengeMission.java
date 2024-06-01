package umc.dofarming.domain.challengeMission;

import jakarta.persistence.*;
import lombok.*;
import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.enums.Mission;
import umc.dofarming.domain.member.Member;
import umc.dofarming.domain.memberChallenge.MemberChallenge;
import umc.dofarming.domain.memberMission.MemberMission;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime missionDate;

    @Enumerated(EnumType.STRING)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mission_id")
    private MemberMission memberMission;
}
