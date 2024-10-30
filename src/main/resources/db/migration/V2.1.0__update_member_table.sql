ALTER TABLE member
    CHANGE email subject VARCHAR(255);

ALTER TABLE letmedowith_app.member_term_agree
    ADD CONSTRAINT uc_member_term_agree_member UNIQUE (member_id);