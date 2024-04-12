ALTER TABLE member_social_account
    ADD provider VARCHAR(255) NULL;

ALTER TABLE member_social_account
    MODIFY provider VARCHAR(255) NOT NULL;

ALTER TABLE member_term_agree
    DROP FOREIGN KEY FK_MEMBERTERMAGREE_ON_MEMBER;

DROP TABLE member_term_agree;

ALTER TABLE member_social_account
    DROP COLUMN `column`;