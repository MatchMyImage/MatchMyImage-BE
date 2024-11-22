INSERT INTO letmedowith_app.task_category (create_at, updated_at, created_by, updated_by, title, active_yn,
                                           creation_type, emoji, category_holder_id)
VALUES (NOW(), NOW(), 'admin', 'admin', '약속', 'Y', 'COMMON', '🙋‍♀️', NULL),
       (NOW(), NOW(), 'admin', 'admin', '시험', 'Y', 'COMMON', '🗓️', NULL),
       (NOW(), NOW(), 'admin', 'admin', '운동', 'Y', 'COMMON', '🦺', NULL),
       (NOW(), NOW(), 'admin', 'admin', '일상', 'Y', 'COMMON', '🏙️', NULL),
       (NOW(), NOW(), 'admin', 'admin', '공부', 'Y', 'COMMON', '📝', NULL),
       (NOW(), NOW(), 'admin', 'admin', '독서', 'Y', 'COMMON', '📘', NULL),
       (NOW(), NOW(), 'admin', 'admin', '작업', 'Y', 'COMMON', '👩‍💻', NULL),
       (NOW(), NOW(), 'admin', 'admin', '기타', 'Y', 'COMMON', '⏰', NULL);