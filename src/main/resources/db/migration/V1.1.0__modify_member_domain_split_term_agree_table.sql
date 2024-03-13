CREATE TABLE member_term_agree
(
    member_term_agree_id              BIGINT AUTO_INCREMENT NOT NULL,
    create_at                         datetime              NULL,
    updated_at                        datetime              NULL,
    created_by                        VARCHAR(255)          NULL,
    updated_by                        VARCHAR(255)          NULL,
    member_id                         BIGINT                NOT NULL,
    advertising_info_receive_agree_yn BIT(1)                NOT NULL COMMENT '광고성 정보 수신동의 여부',
    CONSTRAINT pk_membertermagree PRIMARY KEY (member_term_agree_id)
);

ALTER TABLE member_term_agree
    ADD CONSTRAINT FK_MEMBERTERMAGREE_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE member
    DROP COLUMN marketing_term_agree_yn;