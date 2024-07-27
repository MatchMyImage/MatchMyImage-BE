package com.LetMeDoWith.LetMeDoWith.infrastructure.member.jpaRepository;

import com.LetMeDoWith.LetMeDoWith.application.member.domain.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.QMember;
import com.LetMeDoWith.LetMeDoWith.entity.member.QMemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.common.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class QMemberJpaRepositoryImpl implements QMemberJpaRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QMember qMember = QMember.member;
    private final QMemberSocialAccount qMemberSocialAccount = QMemberSocialAccount.memberSocialAccount;
    
    @Override
    public Optional<Member> findByProviderAndSubject(SocialProvider provider, String subject) {
        return Optional.ofNullable(
            jpaQueryFactory.selectFrom(qMember)
                           .leftJoin(qMemberSocialAccount)
                           .on(qMember.eq(qMemberSocialAccount.member))
                           .fetchJoin()
                           .where(qMember.subject.eq(subject)
                                                 .and(qMemberSocialAccount.provider.eq(provider)))
                           .fetchOne());
    }
    
    @Override
    public Optional<Member> findByProviderAndSubjectAndStatus(SocialProvider provider,
                                                              String subject,
                                                              MemberStatus status) {
        return Optional.ofNullable(
            jpaQueryFactory.selectFrom(qMember)
                           .leftJoin(qMemberSocialAccount)
                           .on(qMember.eq(qMemberSocialAccount.member))
                           .fetchJoin()
                           .where(qMember.subject.eq(subject)
                                                 .and(qMemberSocialAccount.provider.eq(provider))
                                                 .and(qMember.status.eq(status)))
                           .fetchOne());
    }
}