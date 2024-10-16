package com.LetMeDoWith.LetMeDoWith.infrastructure.task.jpaRepository;

import com.LetMeDoWith.LetMeDoWith.domain.task.DowithTask;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QDowithTaskRepositoryImpl implements QDowithTaskRepository{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<DowithTask> findJoinRoutineAndConfirm(Long id) {
    // TODO - 구현
    return null;
  }

  @Override
  public Optional<DowithTask> findJoinRoutineAndConfirm(Long id, Long memberId) {
    // TODO - 구현
    return null;
  }
}
