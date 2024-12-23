CREATE TABLE IF NOT EXISTS member
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    create_at           datetime              NULL,
    updated_at          datetime              NULL,
    created_by          VARCHAR(255)          NULL,
    updated_by          VARCHAR(255)          NULL,
    subject             VARCHAR(255)          NULL,
    status              VARCHAR(255)          NOT NULL,
    task_complete_level VARCHAR(10)           NOT NULL,
    nickname            VARCHAR(255)          NULL,
    self_description    VARCHAR(255)          NULL,
    gender              VARCHAR(2)            NULL,
    date_of_birth       date                  NULL,
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

CREATE TABLE IF NOT EXISTS badge
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

CREATE TABLE IF NOT EXISTS member_badge
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

CREATE TABLE IF NOT EXISTS member_term_agree
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    create_at            datetime              NULL,
    updated_at           datetime              NULL,
    created_by           VARCHAR(255)          NULL,
    updated_by           VARCHAR(255)          NULL,
    member_id            BIGINT                NOT NULL,
    terms_of_agree       BIT(1)                NOT NULL,
    privacy              BIT(1)                NOT NULL,
    advertisement        BIT(1)                NOT NULL,
    CONSTRAINT pk_member_term_agree PRIMARY KEY (id),
    CONSTRAINT uc_member_term_agree_member UNIQUE (member_id)
);

CREATE TABLE IF NOT EXISTS letmedowith_app.member_alarm_setting
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    create_at     datetime              NULL,
    updated_at    datetime              NULL,
    created_by    VARCHAR(255)          NULL,
    updated_by    VARCHAR(255)          NULL,
    member_id     BIGINT                NOT NULL,
    base_alarm_yn BIT(1)                NOT NULL,
    todo_bot_yn   BIT(1)                NOT NULL,
    feedback_yn   BIT(1)                NOT NULL,
    marketing_yn  BIT(1)                NOT NULL,
    CONSTRAINT pk_member_alarm_setting PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS letmedowith_app.dev_refresh_token
(
    token         VARCHAR(255)          NOT NULL,
    access_token  VARCHAR(255)          NOT NULL,
    member_id     bigint                NOT NULL,
    user_agent    VARCHAR(100)          NOT NULL,
    expire_at     datetime              NOT NULL,
    CONSTRAINT pk_dev_refresh_token PRIMARY KEY (token)
);