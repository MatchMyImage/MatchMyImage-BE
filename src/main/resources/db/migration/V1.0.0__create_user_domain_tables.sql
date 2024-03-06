CREATE TABLE user
(
    user_id                 BIGINT AUTO_INCREMENT NOT NULL,
    create_at               datetime              NULL,
    updated_at              datetime              NULL,
    created_by              VARCHAR(255)          NULL,
    updated_by              VARCHAR(255)          NULL,
    email                   VARCHAR(255)          NULL,
    status                  VARCHAR(255)          NOT NULL,
    nickname                VARCHAR(255)          NOT NULL,
    self_description        VARCHAR(255)          NULL,
    type                    VARCHAR(255)          NOT NULL,
    user_profile_image_url  VARCHAR(255)          NULL,
    marketing_term_agree_yn BIT(1)                NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);

CREATE TABLE user_follow
(
    user_follow_id BIGINT AUTO_INCREMENT NOT NULL,
    create_at      datetime              NULL,
    updated_at     datetime              NULL,
    created_by     VARCHAR(255)          NULL,
    updated_by     VARCHAR(255)          NULL,
    follower_id    BIGINT                NOT NULL,
    followed_id    BIGINT                NOT NULL,
    CONSTRAINT pk_user_follow PRIMARY KEY (user_follow_id)
);

CREATE TABLE user_push_setting
(
    user_push_setting_id BIGINT AUTO_INCREMENT NOT NULL,
    create_at            datetime              NULL,
    updated_at           datetime              NULL,
    created_by           VARCHAR(255)          NULL,
    updated_by           VARCHAR(255)          NULL,
    user_id              BIGINT                NOT NULL,
    base_push_yn         BIT(1)                NOT NULL,
    todo_bot_yn          BIT(1)                NOT NULL,
    feedback_yn          BIT(1)                NOT NULL,
    marketing_yn         BIT(1)                NOT NULL,
    CONSTRAINT pk_user_push_setting PRIMARY KEY (user_push_setting_id)
);

CREATE TABLE user_social_account
(
    user_social_account_id BIGINT AUTO_INCREMENT NOT NULL,
    create_at              datetime              NULL,
    updated_at             datetime              NULL,
    created_by             VARCHAR(255)          NULL,
    updated_by             VARCHAR(255)          NULL,
    user_id                BIGINT                NOT NULL,
    `column`               VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_user_social_account PRIMARY KEY (user_social_account_id)
);

CREATE TABLE user_status_history
(
    user_status_history_id BIGINT AUTO_INCREMENT NOT NULL,
    create_at              datetime              NULL,
    updated_at             datetime              NULL,
    created_by             VARCHAR(255)          NULL,
    updated_by             VARCHAR(255)          NULL,
    user_id                BIGINT                NOT NULL,
    status                 VARCHAR(255)          NOT NULL,
    status_changed_at      datetime              NOT NULL,
    status_end_at          datetime              NOT NULL,
    CONSTRAINT pk_user_status_history PRIMARY KEY (user_status_history_id)
);

ALTER TABLE user_follow
    ADD CONSTRAINT FK_USER_FOLLOW_ON_FOLLOWED FOREIGN KEY (followed_id) REFERENCES user (user_id);

ALTER TABLE user_follow
    ADD CONSTRAINT FK_USER_FOLLOW_ON_FOLLOWER FOREIGN KEY (follower_id) REFERENCES user (user_id);

ALTER TABLE user_push_setting
    ADD CONSTRAINT FK_USER_PUSH_SETTING_ON_USER FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE user_social_account
    ADD CONSTRAINT FK_USER_SOCIAL_ACCOUNT_ON_USER FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE user_status_history
    ADD CONSTRAINT FK_USER_STATUS_HISTORY_ON_USER FOREIGN KEY (user_id) REFERENCES user (user_id);