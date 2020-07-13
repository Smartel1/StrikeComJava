CREATE TABLE districts
(
    id         SERIAL,
    name       VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE regions ADD COLUMN district_id INT;

ALTER TABLE regions
    ADD CONSTRAINT FK_2DDC1B0071F7E88B FOREIGN KEY (district_id) REFERENCES districts (id) ON DELETE SET NULL;

insert into districts (name) values
 ('Центральный федеральный округ'),
 ('Северо-Западный федеральный округ'),
 ('Приволжский федеральный округ'),
 ('Уральский федеральный округ'),
 ('Сибирский федеральный округ'),
 ('Южный федеральный округ'),
 ('Дальневосточный федеральный округ'),
 ('Северо-Кавказский федеральный округ')
