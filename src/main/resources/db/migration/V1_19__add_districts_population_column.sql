ALTER TABLE districts ADD COLUMN population INT;

update districts set population = 39378059 where name = 'Центральный федеральный округ';
update districts set population = 29397213 where name = 'Приволжский федеральный округ';
update districts set population = 17173335 where name = 'Сибирский федеральный округ';
update districts set population = 16454550 where name = 'Южный федеральный округ';
update districts set population = 13972070 where name = 'Северо-Западный федеральный округ';
update districts set population = 12350122 where name = 'Уральский федеральный округ';
update districts set population = 9866748 where name = 'Дальневосточный федеральный округ';
update districts set population = 8188623 where name = 'Северо-Кавказский федеральный округ';

alter table districts alter column population set not null;