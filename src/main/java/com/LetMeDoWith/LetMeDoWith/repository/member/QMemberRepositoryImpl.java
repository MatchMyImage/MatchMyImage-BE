package com.LetMeDoWith.LetMeDoWith.repository.member;

import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.entity.member.QMember;
import com.LetMeDoWith.LetMeDoWith.entity.member.QMemberSocialAccount;
import com.LetMeDoWith.LetMeDoWith.enums.SocialProvider;
import com.LetMeDoWith.LetMeDoWith.enums.member.MemberStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class QMemberRepositoryImpl implements QMemberRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QMember qMember = QMember.member;
    private final QMemberSocialAccount qMemberSocialAccount = QMemberSocialAccount.memberSocialAccount;
    
    @Override
    public Optional<Member> findByProviderAndEmail(SocialProvider provider, String email) {
        return Optional.ofNullable(
            jpaQueryFactory.select(qMember)
                           .from(qMember)
                           .leftJoin(qMemberSocialAccount)
                           .on(qMember.eq(qMemberSocialAccount.member))
                           .where(qMember.email.eq(email)
                                               .and(qMemberSocialAccount.provider.eq(provider)))
                           .fetchOne());
    }
    
    @Override
    public Optional<Member> findByProviderAndEmailAndStatus(SocialProvider provider,
        String email,
        MemberStatus status) {
        return Optional.ofNullable(
            jpaQueryFactory.select(qMember)
                           .from(qMember)
                           .leftJoin(qMemberSocialAccount)
                           .on(qMember.eq(qMemberSocialAccount.member))
                           .where(qMember.email.eq(email)
                                               .and(qMemberSocialAccount.provider.eq(provider))
                                               .and(qMember.status.eq(status)))
                           .fetchOne());
    }
}