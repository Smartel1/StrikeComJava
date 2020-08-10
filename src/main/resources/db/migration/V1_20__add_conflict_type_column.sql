alter table conflicts add column main_type_id int;
alter table conflicts
	add constraint fk_5d2a0bef2b19a755
		foreign key (main_type_id) references event_types ("id");

alter table event_types add column priority int not null default 0;
update event_types set priority = 15 where name_ru = 'Забастовка';
update event_types set priority = 14 where name_ru = 'ст. 142 ТК РФ';
update event_types set priority = 13 where name_ru = 'Забастовка частичная';
update event_types set priority = 12 where name_ru = 'Демонстрация';
update event_types set priority = 11 where name_ru = 'Голодовка';
update event_types set priority = 10 where name_ru = 'Обращение';