insert into conflict_results (name_ru, name_en, name_es, name_de)
values ('В прогрессе', 'In progress', 'En progreso', 'Im Gange');

update conflicts set conflict_result_id = (select max(id) from conflict_results)
                 where conflict_result_id is null;

alter table conflicts alter column conflict_result_id set not null;