CREATE TABLE user
(
    user_id                 BIGINT       NOT NULL,
    create_at               datetime NULL,
    updated_at              datetime NULL,
    created_by              VARCHAR(255) NULL,
    updated_by              VARCHAR(255) NULL,
    email                   VARCHAR(255) NULL,
    status                  VARCHAR(255) NOT NULL,
    nickname                VARCHAR(255) NOT NULL,
    self_description        VARCHAR(255) NULL,
    type                    VARCHAR(255) NOT NULL,
    user_profile_image_url  VARCHAR(255) NULL,
    marketing_term_agree_yn BIT(1)       NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);