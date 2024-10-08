CREATE TABLE IF NOT EXISTS member
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    create_at           datetime              NULL,
    updated_at          datetime              NULL,
    created_by          VARCHAR(255)          NULL,
    updated_by          VARCHAR(255)          NULL,
    email               VARCHAR(255)          NULL,
    status              VARCHAR(255)          NOT NULL,
    task_complete_level VARCHAR(10)           NOT NULL,
    nickname            VARCHAR(255)          NULL,
    self_description    VARCHAR(255)          NULL,
    type                VARCHAR(255)          NOT NULL,
    profile_image_url   VARCHAR(255)          NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member_follow
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    create_at    datetime              NULL,
    updated_at   datetime              NULL,
    created_by   VARCHAR(255)          NULL,
    updated_by   VARCHAR(255)          NULL,
    follower_id  BIGINT                NOT NULL,
    following_id BIGINT                NOT NULL,
    CONSTRAINT pk_member_follow PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS member_social_account
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    create_at  datetime              NULL,
    updated_at datetime              NULL,
    created_by VARCHAR(255)          NULL,
    updated_by VARCHAR(255)          NULL,
    member_id  BIGINT                NOT NULL,
    provider   VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_member_social_account PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member_status_history
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    create_at         datetime              NULL,
    updated_at        datetime              NULL,
    created_by        VARCHAR(255)          NULL,
    updated_by        VARCHAR(255)          NULL,
    member_id         BIGINT                NOT NULL,
    status            VARCHAR(255)          NOT NULL,
    status_changed_at datetime              NOT NULL,
    status_end_at     datetime              NOT NULL,
    CONSTRAINT pk_member_status_history PRIMARY KEY (id)
);

create table badge
(
    sort_order   int          null,
    create_at    datetime(6)  null,
    id           bigint auto_increment
        primary key,
    updated_at   datetime(6)  null,
    acquire_hint varchar(255) null,
    created_by   varchar(255) null,
    description  varchar(255) null,
    image_url    varchar(255) null,
    name         varchar(255) not null,
    status       varchar(255) not null,
    updated_by   varchar(255) null
);

create table member_badge
(
    badge_id   bigint       not null,
    create_at  datetime(6)  null,
    id         bigint auto_increment
        primary key,
    member_id  bigint       null,
    updated_at datetime(6)  null,
    created_by varchar(255) null,
    main_yn    varchar(255) not null,
    updated_by varchar(255) null
);


-- ALTER TABLE member_follow
--     ADD CONSTRAINT FK_MEMBER_FOLLOW_ON_FOLLOWED FOREIGN KEY (followed_id) REFERENCES member (member_id);
--
-- ALTER TABLE member_follow
--     ADD CONSTRAINT FK_MEMBER_FOLLOW_ON_FOLLOWER FOREIGN KEY (follower_id) REFERENCES member (member_id);
--
-- ALTER TABLE member_push_setting
--     ADD CONSTRAINT FK_MEMBER_PUSH_SETTING_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);
--
-- ALTER TABLE member_social_account
--     ADD CONSTRAINT FK_MEMBER_SOCIAL_ACCOUNT_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);
--
-- ALTER TABLE member_status_history
--     ADD CONSTRAINT FK_MEMBER_STATUS_HISTORY_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);