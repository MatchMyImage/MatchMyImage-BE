package com.LetMeDoWith.LetMeDoWith.presentation.member.controller;

import com.LetMeDoWith.LetMeDoWith.application.member.dto.GetBadgesInfoResult;
import com.LetMeDoWith.LetMeDoWith.application.member.dto.MemberBadgeVO;
import com.LetMeDoWith.LetMeDoWith.application.member.service.BadgeService;
import com.LetMeDoWith.LetMeDoWith.common.enums.common.Yn;
import com.LetMeDoWith.LetMeDoWith.common.util.AuthUtil;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import com.LetMeDoWith.LetMeDoWith.presentation.member.dto.RetrieveBadgesInfoResDto;
import com.LetMeDoWith.LetMeDoWith.presentation.member.dto.UpdateMainBadgeReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member/badge")
@RequiredArgsConstructor
public class BadgeController {

  private final BadgeService badgeService;

  @GetMapping("")
  public ResponseEntity retrieveBadgesInfo() {

    Long memberId = AuthUtil.getMemberId();
    GetBadgesInfoResult result = badgeService.getBadgesInfo(memberId);

    MemberBadgeVO mainBadge = result.getBadges().stream().filter(e -> Yn.TRUE.equals(e.getIsMain())).findFirst().orElse(null);

    return ResponseUtil.createSuccessResponse(
        RetrieveBadgesInfoResDto.of(memberId, result.isMemberLazy(), mainBadge, result.getBadges()));
  }

  @PutMapping("/main")
  public ResponseEntity updateMainBadge(@RequestBody UpdateMainBadgeReqDto request) {

    Long memberId = AuthUtil.getMemberId();
    badgeService.updateMainBadge(memberId, request.badgeId());

    return ResponseUtil.createSuccessResponse();
  }

}
