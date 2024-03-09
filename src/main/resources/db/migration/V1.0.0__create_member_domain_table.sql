CREATE TABLE member
(
    member_id               BIGINT AUTO_INCREMENT NOT NULL,
    create_at               datetime              NULL,
    updated_at              datetime              NULL,
    created_by              VARCHAR(255)          NULL,
    updated_by              VARCHAR(255)          NULL,
    email                   VARCHAR(255)          NULL,
    status                  VARCHAR(255)          NOT NULL,
    nickname                VARCHAR(255)          NOT NULL,
    self_description        VARCHAR(255)          NULL,
    type                    VARCHAR(255)          NOT NULL,
    profile_image_url       VARCHAR(255)          NULL,
    marketing_term_agree_yn BIT(1)                NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (member_id)
);

CREATE TABLE member_follow
(
    member_follow_id BIGINT AUTO_INCREMENT NOT NULL,
    create_at        datetime              NULL,
    updated_at       datetime              NULL,
    created_by       VARCHAR(255)          NULL,
    updated_by       VARCHAR(255)          NULL,
    follower_id      BIGINT                NOT NULL,
    followed_id      BIGINT                NOT NULL,
    CONSTRAINT pk_member_follow PRIMARY KEY (member_follow_id)
);

CREATE TABLE member_push_setting
(
    member_push_setting_id BIGINT AUTO_INCREMENT NOT NULL,
    create_at              datetime              NULL,
    updated_at             datetime              NULL,
    created_by             VARCHAR(255)          NULL,
    updated_by             VARCHAR(255)          NULL,
    member_id              BIGINT                NOT NULL,
    base_push_yn           BIT(1)                NOT NULL,
    todo_bot_yn            BIT(1)                NOT NULL,
    feedback_yn            BIT(1)                NOT NULL,
    marketing_yn           BIT(1)                NOT NULL,
    CONSTRAINT pk_member_push_setting PRIMARY KEY (member_push_setting_id)
);

CREATE TABLE member_social_account
(
    member_social_account_id BIGINT AUTO_INCREMENT NOT NULL,
    create_at                datetime              NULL,
    updated_at               datetime              NULL,
    created_by               VARCHAR(255)          NULL,
    updated_by               VARCHAR(255)          NULL,
    member_id                BIGINT                NOT NULL,
    `column`                 VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_member_social_account PRIMARY KEY (member_social_account_id)
);

CREATE TABLE member_status_history
(
    member_status_history_id BIGINT AUTO_INCREMENT NOT NULL,
    create_at                datetime              NULL,
    updated_at               datetime              NULL,
    created_by               VARCHAR(255)          NULL,
    updated_by               VARCHAR(255)          NULL,
    member_id                BIGINT                NOT NULL,
    status                   VARCHAR(255)          NOT NULL,
    status_changed_at        datetime              NOT NULL,
    status_end_at            datetime              NOT NULL,
    CONSTRAINT pk_member_status_history PRIMARY KEY (member_status_history_id)
);

ALTER TABLE member_follow
    ADD CONSTRAINT FK_MEMBER_FOLLOW_ON_FOLLOWED FOREIGN KEY (followed_id) REFERENCES member (member_id);

ALTER TABLE member_follow
    ADD CONSTRAINT FK_MEMBER_FOLLOW_ON_FOLLOWER FOREIGN KEY (follower_id) REFERENCES member (member_id);

ALTER TABLE member_push_setting
    ADD CONSTRAINT FK_MEMBER_PUSH_SETTING_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE member_social_account
    ADD CONSTRAINT FK_MEMBER_SOCIAL_ACCOUNT_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE member_status_history
    ADD CONSTRAINT FK_MEMBER_STATUS_HISTORY_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);