-- a1의 팔로잉 더미 데이터
insert into member_follow (create_at, updated_at, created_by, updated_by, follower_id, following_id)
values (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a1'), (select id from member where nickname = 'a2')),
       (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a1'), (select id from member where nickname = 'a3')),
       (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a1'), (select id from member where nickname = 'a4')),
       (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a1'), (select id from member where nickname = 'a5')),
       (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a1'), (select id from member where nickname = 'a6'));

-- a1의 팔로워 더미 데이터
insert into member_follow (create_at, updated_at, created_by, updated_by, follower_id, following_id)
values (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a7'), (select id from member where nickname = 'a1')),
       (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a8'), (select id from member where nickname = 'a1')),
       (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a9'), (select id from member where nickname = 'a1')),
       (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a10'), (select id from member where nickname = 'a1')),
       (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a11'), (select id from member where nickname = 'a1')),
       (now(), now(), 'admin', 'admin', (select id from member where nickname = 'a12'), (select id from member where nickname = 'a1'));