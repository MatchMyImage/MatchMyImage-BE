package com.LetMeDoWith.LetMeDoWith.application.member.service;

import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.BADGE_NOT_EXIST;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.LAZY_NOT_AVAIL_UPDATE_MAIN_BADGE;
import static com.LetMeDoWith.LetMeDoWith.common.exception.status.FailResponseStatus.MEMBER_NOT_EXIST_BADGE;

import com.LetMeDoWith.LetMeDoWith.application.member.dto.GetBadgesInfoResult;
import com.LetMeDoWith.LetMeDoWith.presentation.member.dto.RetrieveBadgesInfoResDto;
import com.LetMeDoWith.LetMeDoWith.application.member.repository.BadgeRepository;
import com.LetMeDoWith.LetMeDoWith.application.member.repository.MemberRepository;
import com.LetMeDoWith.LetMeDoWith.common.enums.member.MemberStatus;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.domain.member.Badge;
import com.LetMeDoWith.LetMeDoWith.domain.member.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BadgeService {

  private final BadgeRepository badgeRepository;
  private final MemberRepository memberRepository;

  public GetBadgesInfoResult getBadgesInfo(Long memberId) {

    Member member = memberRepository.getMember(memberId, MemberStatus.NORMAL)
        .orElseThrow(() -> new RestApiException(MEMBER_NOT_EXIST_BADGE));

    List<Badge> badges = badgeRepository.getBadges(memberId);

    return GetBadgesInfoResult.of(member.isLazyBadgeAcquireLevel(), badges);

  }

  @Transactional
  public void updateMainBadge(Long memberId, Long badgeId) {

    Member member = memberRepository.getMember(memberId, MemberStatus.NORMAL)
        .orElseThrow(() -> new RestApiException(MEMBER_NOT_EXIST_BADGE));

    if(member.isLazyBadgeAcquireLevel()) throw new RestApiException(LAZY_NOT_AVAIL_UPDATE_MAIN_BADGE);

    List<Badge> badges = badgeRepository.getBadges(memberId);
    Badge oldMainBadge = badges.stream().filter(Badge::isMainBadge).findFirst().orElse(null);
    Badge newMainBadge = badges.stream().filter(e -> e.getId().equals(badgeId)).findFirst()
        .orElseThrow(() -> new RestApiException(BADGE_NOT_EXIST));

    if(oldMainBadge != null) {
      oldMainBadge.cancelMain();
    }

    newMainBadge.registerToMain();

    badgeRepository.save(badges);

  }



}
