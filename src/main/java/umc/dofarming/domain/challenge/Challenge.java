package umc.dofarming.domain.challenge;

import jakarta.persistence.*;
import lombok.*;
import umc.dofarming.domain.challengeMission.ChallengeMission;
import umc.dofarming.domain.enums.Category;
import umc.dofarming.domain.enums.RewardType;
import umc.dofarming.domain.memberChallenge.MemberChallenge;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private RewardType rewardType;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer money;

    @Builder.Default
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<MemberChallenge> memberChallengeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<ChallengeMission> challengeMissionArrayList = new ArrayList<>();
}
