CREATE TABLE events
(
    id              SERIAL,
    author_id       INT                                     DEFAULT NULL,
    conflict_id     INT                                     DEFAULT NULL,
    event_status_id INT                                     DEFAULT NULL,
    event_type_id   INT                                     DEFAULT NULL,
    locality_id     INT                                     DEFAULT NULL,
    date            TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    views           INT                            NOT NULL DEFAULT 0,
    source_link     VARCHAR(500)                            DEFAULT NULL,
    published       BOOLEAN                        NOT NULL DEFAULT false,
    title_ru        VARCHAR(255)                            DEFAULT NULL,
    title_en        VARCHAR(255)                            DEFAULT NULL,
    title_es        VARCHAR(255)                            DEFAULT NULL,
    content_ru      TEXT                                    DEFAULT NULL,
    content_en      TEXT                                    DEFAULT NULL,
    content_es      TEXT                                    DEFAULT NULL,
    created_at      TIMESTAMP(0) WITHOUT TIME ZONE          DEFAULT NULL,
    updated_at      TIMESTAMP(0) WITHOUT TIME ZONE          DEFAULT NULL,
    latitude        DOUBLE PRECISION               NOT NULL,
    longitude       DOUBLE PRECISION               NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX IDX_5387574AF675F31B ON events (author_id);
CREATE INDEX IDX_5387574AC05AB355 ON events (conflict_id);
CREATE INDEX IDX_5387574AED623E80 ON events (event_status_id);
CREATE INDEX IDX_5387574A401B253C ON events (event_type_id);
CREATE INDEX IDX_5387574A88823A92 ON events (locality_id);
CREATE TABLE event_photo
(
    event_id INT NOT NULL,
    photo_id INT NOT NULL,
    PRIMARY KEY (event_id, photo_id)
);
CREATE INDEX IDX_55AC353471F7E88B ON event_photo (event_id);
CREATE INDEX IDX_55AC35347E9E4C8C ON event_photo (photo_id);
CREATE TABLE event_video
(
    event_id INT NOT NULL,
    video_id INT NOT NULL,
    PRIMARY KEY (event_id, video_id)
);
CREATE INDEX IDX_3DDC6B0071F7E88B ON event_video (event_id);
CREATE INDEX IDX_3DDC6B0029C1004E ON event_video (video_id);
CREATE TABLE event_tag
(
    event_id INT NOT NULL,
    tag_id   INT NOT NULL,
    PRIMARY KEY (event_id, tag_id)
);
CREATE INDEX IDX_1246725071F7E88B ON event_tag (event_id);
CREATE INDEX IDX_12467250BAD26311 ON event_tag (tag_id);

CREATE TABLE client_versions
(
    id             SERIAL,
    version        VARCHAR(255) NOT NULL,
    client_id      VARCHAR(255) NOT NULL,
    required       BOOLEAN      NOT NULL,
    description_ru VARCHAR(500) NOT NULL,
    description_en VARCHAR(500) NOT NULL,
    description_es VARCHAR(500) NOT NULL,
    created_at     TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    updated_at     TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE tags
(
    id   SERIAL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE news
(
    id          SERIAL,
    author_id   INT                                     DEFAULT NULL,
    date        TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    views       INT                            NOT NULL DEFAULT 0,
    source_link VARCHAR(500)                            DEFAULT NULL,
    published   BOOLEAN                        NOT NULL DEFAULT false,
    title_ru    VARCHAR(255)                            DEFAULT NULL,
    title_en    VARCHAR(255)                            DEFAULT NULL,
    title_es    VARCHAR(255)                            DEFAULT NULL,
    content_ru  TEXT                                    DEFAULT NULL,
    content_en  TEXT                                    DEFAULT NULL,
    content_es  TEXT                                    DEFAULT NULL,
    created_at  TIMESTAMP(0) WITHOUT TIME ZONE          DEFAULT NULL,
    updated_at  TIMESTAMP(0) WITHOUT TIME ZONE          DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX IDX_1DD39950F675F31B ON news (author_id);
CREATE TABLE news_photo
(
    news_id  INT NOT NULL,
    photo_id INT NOT NULL,
    PRIMARY KEY (news_id, photo_id)
);
CREATE INDEX IDX_6E080346B5A459A0 ON news_photo (news_id);
CREATE INDEX IDX_6E0803467E9E4C8C ON news_photo (photo_id);
CREATE TABLE news_video
(
    news_id  INT NOT NULL,
    video_id INT NOT NULL,
    PRIMARY KEY (news_id, video_id)
);
CREATE INDEX IDX_6785D72B5A459A0 ON news_video (news_id);
CREATE INDEX IDX_6785D7229C1004E ON news_video (video_id);
CREATE TABLE news_tag
(
    news_id INT NOT NULL,
    tag_id  INT NOT NULL,
    PRIMARY KEY (news_id, tag_id)
);
CREATE INDEX IDX_BE3ED8A1B5A459A0 ON news_tag (news_id);
CREATE INDEX IDX_BE3ED8A1BAD26311 ON news_tag (tag_id);

CREATE TABLE conflict_reasons
(
    id      SERIAL,
    name_ru VARCHAR(255) DEFAULT NULL,
    name_en VARCHAR(255) DEFAULT NULL,
    name_es VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE conflict_results
(
    id      SERIAL,
    name_ru VARCHAR(255) DEFAULT NULL,
    name_en VARCHAR(255) DEFAULT NULL,
    name_es VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE localities
(
    id        SERIAL,
    region_id INT          NOT NULL,
    name      VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX IDX_41E780E998260155 ON localities (region_id);
CREATE TABLE video_types
(
    id   SERIAL,
    code VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE industries
(
    id      SERIAL,
    name_ru VARCHAR(255) DEFAULT NULL,
    name_en VARCHAR(255) DEFAULT NULL,
    name_es VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE countries
(
    id      SERIAL,
    name_ru VARCHAR(255) DEFAULT NULL,
    name_en VARCHAR(255) DEFAULT NULL,
    name_es VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE event_statuses
(
    id      SERIAL,
    name_ru VARCHAR(255) DEFAULT NULL,
    name_en VARCHAR(255) DEFAULT NULL,
    name_es VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE regions
(
    id         SERIAL,
    country_id INT          NOT NULL,
    name       VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX IDX_A26779F3F92F3E70 ON regions (country_id);
CREATE TABLE event_types
(
    id      SERIAL,
    name_ru VARCHAR(255) DEFAULT NULL,
    name_en VARCHAR(255) DEFAULT NULL,
    name_es VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE photos
(
    id         SERIAL,
    url        VARCHAR(500) NOT NULL,
    created_at TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    updated_at TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE users
(
    id         SERIAL,
    uuid       VARCHAR(255)                                NOT NULL,
    name       VARCHAR(255)                   DEFAULT NULL,
    email      VARCHAR(255)                   DEFAULT NULL,
    roles      JSON                           DEFAULT '[]' NOT NULL,
    fcm        VARCHAR(255)                   DEFAULT NULL,
    image_url  VARCHAR(500)                   DEFAULT NULL,
    created_at TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    updated_at TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX UNIQ_1483A5E9D17F50A6 ON users (uuid);
COMMENT ON COLUMN users.roles IS '(DC2Type:json)';
CREATE TABLE favourite_events
(
    user_id  INT NOT NULL,
    event_id INT NOT NULL,
    PRIMARY KEY (user_id, event_id)
);
CREATE INDEX IDX_7349A778A76ED395 ON favourite_events (user_id);
CREATE INDEX IDX_7349A77871F7E88B ON favourite_events (event_id);
CREATE TABLE favourite_news
(
    user_id INT NOT NULL,
    news_id INT NOT NULL,
    PRIMARY KEY (user_id, news_id)
);
CREATE INDEX IDX_B9C192F1A76ED395 ON favourite_news (user_id);
CREATE INDEX IDX_B9C192F1B5A459A0 ON favourite_news (news_id);
CREATE TABLE videos
(
    id            SERIAL,
    video_type_id INT          NOT NULL,
    url           VARCHAR(500) NOT NULL,
    preview_url   VARCHAR(500)                   DEFAULT NULL,
    created_at    TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    updated_at    TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX IDX_29AA643220A1653E ON videos (video_type_id);
CREATE TABLE conflicts
(
    id                 SERIAL,
    conflict_reason_id INT                            DEFAULT NULL,
    conflict_result_id INT                            DEFAULT NULL,
    industry_id        INT                            DEFAULT NULL,
    parent_event_id    INT                            DEFAULT NULL,
    company_name       VARCHAR(500)                   DEFAULT NULL,
    date_from          TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    date_to            TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    title_ru           VARCHAR(255)                   DEFAULT NULL,
    title_en           VARCHAR(255)                   DEFAULT NULL,
    title_es           VARCHAR(255)                   DEFAULT NULL,
    created_at         TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    updated_at         TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT NULL,
    latitude           DOUBLE PRECISION NOT NULL,
    longitude          DOUBLE PRECISION NOT NULL,
    parent_id          INT,
    lft                INT              NOT NULL,
    rgt                INT              NOT NULL,
    lvl                INT              NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX IDX_5D2A0BEF6FE2D95B ON conflicts (conflict_reason_id);
CREATE INDEX IDX_5D2A0BEF31FE7A8A ON conflicts (conflict_result_id);
CREATE INDEX IDX_5D2A0BEF2B19A734 ON conflicts (industry_id);
CREATE INDEX IDX_5D2A0BEFEE3A445A ON conflicts (parent_event_id);
CREATE INDEX IDX_5D2A0BEF727ACA70 ON conflicts (parent_id);
ALTER TABLE events
    ADD CONSTRAINT FK_5387574AF675F31B FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE events
    ADD CONSTRAINT FK_5387574AC05AB355 FOREIGN KEY (conflict_id) REFERENCES conflicts (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE events
    ADD CONSTRAINT FK_5387574AED623E80 FOREIGN KEY (event_status_id) REFERENCES event_statuses (id) NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE events
    ADD CONSTRAINT FK_5387574A401B253C FOREIGN KEY (event_type_id) REFERENCES event_types (id) NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE events
    ADD CONSTRAINT FK_5387574A88823A92 FOREIGN KEY (locality_id) REFERENCES localities (id) ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE event_photo
    ADD CONSTRAINT FK_55AC353471F7E88B FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE event_photo
    ADD CONSTRAINT FK_55AC35347E9E4C8C FOREIGN KEY (photo_id) REFERENCES photos (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE event_video
    ADD CONSTRAINT FK_3DDC6B0071F7E88B FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE event_video
    ADD CONSTRAINT FK_3DDC6B0029C1004E FOREIGN KEY (video_id) REFERENCES videos (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE event_tag
    ADD CONSTRAINT FK_1246725071F7E88B FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE event_tag
    ADD CONSTRAINT FK_12467250BAD26311 FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE news
    ADD CONSTRAINT FK_1DD39950F675F31B FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE news_photo
    ADD CONSTRAINT FK_6E080346B5A459A0 FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE news_photo
    ADD CONSTRAINT FK_6E0803467E9E4C8C FOREIGN KEY (photo_id) REFERENCES photos (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE news_video
    ADD CONSTRAINT FK_6785D72B5A459A0 FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE news_video
    ADD CONSTRAINT FK_6785D7229C1004E FOREIGN KEY (video_id) REFERENCES videos (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE news_tag
    ADD CONSTRAINT FK_BE3ED8A1B5A459A0 FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE news_tag
    ADD CONSTRAINT FK_BE3ED8A1BAD26311 FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE localities
    ADD CONSTRAINT FK_41E780E998260155 FOREIGN KEY (region_id) REFERENCES regions (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE regions
    ADD CONSTRAINT FK_A26779F3F92F3E70 FOREIGN KEY (country_id) REFERENCES countries (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE favourite_events
    ADD CONSTRAINT FK_7349A778A76ED395 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE favourite_events
    ADD CONSTRAINT FK_7349A77871F7E88B FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE favourite_news
    ADD CONSTRAINT FK_B9C192F1A76ED395 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE favourite_news
    ADD CONSTRAINT FK_B9C192F1B5A459A0 FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE videos
    ADD CONSTRAINT FK_29AA643220A1653E FOREIGN KEY (video_type_id) REFERENCES video_types (id) NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE conflicts
    ADD CONSTRAINT FK_5D2A0BEF6FE2D95B FOREIGN KEY (conflict_reason_id) REFERENCES conflict_reasons (id) NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE conflicts
    ADD CONSTRAINT FK_5D2A0BEF31FE7A8A FOREIGN KEY (conflict_result_id) REFERENCES conflict_results (id) NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE conflicts
    ADD CONSTRAINT FK_5D2A0BEF2B19A734 FOREIGN KEY (industry_id) REFERENCES industries (id) NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE conflicts
    ADD CONSTRAINT FK_5D2A0BEFEE3A445A FOREIGN KEY (parent_event_id) REFERENCES events (id) NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE conflicts
    ADD CONSTRAINT FK_5D2A0BEF727ACA70 FOREIGN KEY (parent_id) REFERENCES conflicts (id) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE