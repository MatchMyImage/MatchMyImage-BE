CREATE TABLE letmedowith_app.member_alarm_setting
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

ALTER TABLE letmedowith_app.member_alarm_setting
    ADD CONSTRAINT uc_member_alarm_setting_member UNIQUE (member_id);

ALTER TABLE letmedowith_app.member_alarm_setting
    ADD CONSTRAINT FK_MEMBER_ALARM_SETTING_ON_MEMBER FOREIGN KEY (member_id) REFERENCES letmedowith_app.member (member_id);

DROP TABLE letmedowith_app.member_push_setting;