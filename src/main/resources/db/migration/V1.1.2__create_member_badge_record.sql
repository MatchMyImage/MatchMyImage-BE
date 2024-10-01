insert into badge (name, description, status, acquire_hint, image_url,
                   sort_order, create_at, created_by, updated_at, updated_by)
values ('뱃지1', '뱃지1 설명설명입니다. 뱃지는 설명이 필요하죠', 'ACTIVE', '뱃지1 획득 힌트 입니다. 뱃지2를 획득하려면 힌트가 필요하죠', 'https://contents.sixshop.com/uploadedFiles/84218/default/image_1547035192141.jpg'
       ,1, now(), 'admin', now(), 'admin'),
       ('뱃지2', '뱃지2 설명설명입니다. 뱃지는 설명이 필요하죠', 'ACTIVE', '뱃지2 획득 힌트 입니다. 뱃지2를 획득하려면 힌트가 필요하죠', 'https://contents.sixshop.com/uploadedFiles/84218/default/image_1547035192141.jpg'
       ,2, now(), 'admin', now(), 'admin'),
       ('뱃지3', '뱃지3 설명설명입니다. 뱃지는 설명이 필요하죠', 'ACTIVE', '뱃지3 획득 힌트 입니다. 뱃지2를 획득하려면 힌트가 필요하죠', 'https://contents.sixshop.com/uploadedFiles/84218/default/image_1547035192141.jpg'
       ,3, now(), 'admin', now(), 'admin'),
       ('뱃지4', '뱃지4 설명설명입니다. 뱃지는 설명이 필요하죠', 'ACTIVE', '뱃지4 획득 힌트 입니다. 뱃지2를 획득하려면 힌트가 필요하죠', 'https://contents.sixshop.com/uploadedFiles/84218/default/image_1547035192141.jpg'
       ,4, now(), 'admin', now(), 'admin'),
       ('뱃지5', '뱃지5 설명설명입니다. 뱃지는 설명이 필요하죠', 'ACTIVE', '뱃지5 획득 힌트 입니다. 뱃지2를 획득하려면 힌트가 필요하죠', 'https://contents.sixshop.com/uploadedFiles/84218/default/image_1547035192141.jpg'
       ,5, now(), 'admin', now(), 'admin'),
       ('뱃지6', '뱃지6 설명설명입니다. 뱃지는 설명이 필요하죠', 'ACTIVE', '뱃지6 획득 힌트 입니다. 뱃지2를 획득하려면 힌트가 필요하죠', 'https://contents.sixshop.com/uploadedFiles/84218/default/image_1547035192141.jpg'
       ,6, now(), 'admin', now(), 'admin'),
       ('뱃지7', '뱃지7 설명설명입니다. 뱃지는 설명이 필요하죠', 'ACTIVE', '뱃지7 획득 힌트 입니다. 뱃지2를 획득하려면 힌트가 필요하죠', 'https://contents.sixshop.com/uploadedFiles/84218/default/image_1547035192141.jpg'
       ,7, now(), 'admin', now(), 'admin'),
       ('뱃지8', '뱃지8 설명설명입니다. 뱃지는 설명이 필요하죠', 'ACTIVE', '뱃지8 획득 힌트 입니다. 뱃지2를 획득하려면 힌트가 필요하죠', 'https://contents.sixshop.com/uploadedFiles/84218/default/image_1547035192141.jpg'
       ,8, now(), 'admin', now(), 'admin'),
       ('뱃지9', '뱃지9 설명설명입니다. 뱃지는 설명이 필요하죠', 'ACTIVE', '뱃지9 획득 힌트 입니다. 뱃지2를 획득하려면 힌트가 필요하죠', 'https://contents.sixshop.com/uploadedFiles/84218/default/image_1547035192141.jpg'
       ,9, now(), 'admin', now(), 'admin');

insert into member_badge (badge_id, main_yn, member_id, create_at, updated_at, created_by, updated_by)
values ((select id from badge where name = '뱃지1'), 'Y', (select id from member where nickname = 'a1'), now(), now(), 'admin', 'admin'),
       ((select id from badge where name = '뱃지2'), 'N', (select id from member where nickname = 'a1'), now(), now(), 'admin', 'admin'),
       ((select id from badge where name = '뱃지4'), 'N', (select id from member where nickname = 'a1'), now(), now(), 'admin', 'admin'),
       ((select id from badge where name = '뱃지2'), 'N', (select id from member where nickname = 'a2'), now(), now(), 'admin', 'admin'),
       ((select id from badge where name = '뱃지5'), 'N', (select id from member where nickname = 'a2'), now(), now(), 'admin', 'admin'),
       ((select id from badge where name = '뱃지9'), 'M', (select id from member where nickname = 'a2'), now(), now(), 'admin', 'admin');