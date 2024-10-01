CREATE TABLE member_term_agree
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
    CONSTRAINT pk_member_term_agree PRIMARY KEY (id)
);