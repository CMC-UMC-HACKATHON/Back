package umc.dofarming.domain.memberChallenge.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.dofarming.domain.challenge.Challenge;
import umc.dofarming.domain.challenge.QChallenge;
import umc.dofarming.domain.memberChallenge.QMemberChallenge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberChallengeQueryDSLRepositoryImpl implements MemberChallengeQueryDSLRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Challenge> findChallengesByMemberId(Long memberId, boolean ongoing){
        QMemberChallenge memberChallenge = QMemberChallenge.memberChallenge;
        QChallenge challenge = QChallenge.challenge;

        BooleanBuilder conditions = new BooleanBuilder();
        addCondition(conditions, memberChallenge.member.id.eq(memberId));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();

        if (ongoing) {
            addCondition(conditions, challenge.startDate.loe(startOfDay)
                    .and(challenge.endDate.goe(startOfDay)));
        } else {
            addCondition(conditions, challenge.endDate.lt(startOfDay));
        }

        return jpaQueryFactory
                .select(memberChallenge.challenge)
                .from(memberChallenge)
                .join(memberChallenge.challenge, challenge) // Ensure the join with Challenge
                .where(conditions)
                .fetch();
    }

    private void addCondition(BooleanBuilder builder, BooleanExpression condition) {
        builder.and(condition);
    }

}
