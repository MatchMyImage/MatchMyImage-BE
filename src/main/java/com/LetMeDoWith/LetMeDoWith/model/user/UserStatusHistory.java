package com.LetMeDoWith.LetMeDoWith.model.user;

import com.LetMeDoWith.LetMeDoWith.enums.converter.user.UserStatusConverter;
import com.LetMeDoWith.LetMeDoWith.enums.user.UserStatus;
import com.LetMeDoWith.LetMeDoWith.model.BaseAuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "user_status_history")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatusHistory extends BaseAuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_status_history_id", nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Convert(converter = UserStatusConverter.class)
    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false)
    private LocalDateTime statusChangedAt;

    @Column(nullable = false)
    private LocalDateTime statusEndAt;

}
