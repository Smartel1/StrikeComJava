alter table news
	add sent_push_ru boolean default false not null;
alter table news
	add sent_push_en boolean default false not null;
alter table news
	add sent_push_es boolean default false not null;
alter table news
	add sent_push_de boolean default false not null;

alter table events
	add sent_push_ru boolean default false not null;
alter table events
	add sent_push_en boolean default false not null;
alter table events
	add sent_push_es boolean default false not null;
alter table events
	add sent_push_de boolean default false not null;

update news set sent_push_ru = true where title_ru is not null and published = true;
update news set sent_push_en = true where title_en is not null and published = true;
update news set sent_push_es = true where title_es is not null and published = true;
update news set sent_push_de = true where title_de is not null and published = true;

update events set sent_push_ru = true where title_ru is not null and published = true;
update events set sent_push_en = true where title_en is not null and published = true;
update events set sent_push_es = true where title_es is not null and published = true;
update events set sent_push_de = true where title_de is not null and published = true;