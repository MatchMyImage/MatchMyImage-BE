package com.LetMeDoWith.LetMeDoWith.infrastructure.task.jpaRepository;

import com.LetMeDoWith.LetMeDoWith.domain.task.DowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.QDowithTask;
import com.LetMeDoWith.LetMeDoWith.domain.task.QDowithTaskConfirm;
import com.LetMeDoWith.LetMeDoWith.domain.task.QDowithTaskRoutine;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QDowithTaskRepositoryImpl implements QDowithTaskRepository{

  private final JPAQueryFactory jpaQueryFactory;

private QDowithTask qDowithTask = QDowithTask.dowithTask;
private QDowithTaskConfirm qDowithTaskConfirm = QDowithTaskConfirm.dowithTaskConfirm;
private QDowithTaskRoutine qDowithTaskRoutine = QDowithTaskRoutine.dowithTaskRoutine;


  @Override
  public Optional<DowithTask> findJoinRoutineAndConfirm(Long id) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(qDowithTask)
        .leftJoin(qDowithTask.confirms, qDowithTaskConfirm)
        .leftJoin(qDowithTask.routine, qDowithTaskRoutine)
        .where(qDowithTask.id.eq(id))
        .fetchJoin()
        .fetchOne());
  }

  @Override
  public List<DowithTask> findJoinRoutineAndConfirm(Long memberId, LocalDate date) {
    Date targetDate = Date.valueOf(date);
    return jpaQueryFactory.selectFrom(qDowithTask)
        .leftJoin(qDowithTask.confirms, qDowithTaskConfirm)
        .leftJoin(qDowithTask.routine, qDowithTaskRoutine)
        .where(Expressions.dateTemplate(java.sql.Date.class, "DATE({0})", qDowithTask.startDateTime).eq(targetDate).and(qDowithTask.memberId.eq(memberId)))
        .fetchJoin()
        .fetch();
  }

  @Override
  public Optional<DowithTask> findJoinRoutineAndConfirm(Long id, Long memberId) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(qDowithTask)
        .leftJoin(qDowithTask.confirms, qDowithTaskConfirm)
        .leftJoin(qDowithTask.routine, qDowithTaskRoutine)
        .where(qDowithTask.id.eq(id).and(qDowithTask.memberId.eq(memberId)))
        .fetchJoin()
        .fetchOne());
  }

}
