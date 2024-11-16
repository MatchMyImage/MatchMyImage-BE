CREATE TABLE IF NOT EXISTS dowith_task
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    member_id               BIGINT                NOT NULL,
    dowith_task_routine_id  BIGINT                NULL,
    task_category_id        BIGINT                NOT NULL,
    title                   VARCHAR(255)          NOT NULL,
    status                  VARCHAR(20)           NOT NULL,
    date                    date                  NOT NULL,
    start_time              time                  NOT NULL,
    success_at              datetime              NULL,
    complete_at             datetime              NULL,
    create_at               datetime              NULL,
    updated_at              datetime              NULL,
    created_by              VARCHAR(255)          NULL,
    updated_by              VARCHAR(255)          NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dowith_task_routine
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    dates                   TEXT                  NOT NULL,
    create_at               datetime              NULL,
    updated_at              datetime              NULL,
    created_by              VARCHAR(255)          NULL,
    updated_by              VARCHAR(255)          NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dowith_task_confirm
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    dowith_task_id          BIGINT                NOT NULL,
    image_url               varchar(2083)         NOT NULL,
    create_at               datetime              NULL,
    updated_at              datetime              NULL,
    created_by              VARCHAR(255)          NULL,
    updated_by              VARCHAR(255)          NULL,
    PRIMARY KEY (id)
)