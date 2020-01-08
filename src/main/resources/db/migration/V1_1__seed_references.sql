INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Оплата труда', 'Salary', 'remuneración');
INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Задержка ЗП', 'Delay salary', 'Salarios Atrasados');
INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Сокращения', 'Contraction', 'Contracción');
INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Ликвидация предприятия', 'Liquidation of an enterprise', 'Liquidación de la empresa');
INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Прочее', 'Other', 'Otros');
INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Увольнение', 'Dismissal', 'Despido');
INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Политика руководства', 'Management course', 'Política de liderazgo');
INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Условия труда', 'Working conditions', 'Condiciones de trabajo');
INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Рабочее время', 'Working time', 'Horas de trabajo');
INSERT INTO public.conflict_reasons (name_ru, name_en, name_es) VALUES ('Коллективный договор', 'Collective agreement', 'Convenio colectivo');

INSERT INTO public.conflict_results (name_ru, name_en, name_es) VALUES ('Удовлетворены полностью', 'Completely satisfied', 'Completamente satisfecho');
INSERT INTO public.conflict_results (name_ru, name_en, name_es) VALUES ('Удовлетворены частично', 'Partially satisfied', 'Parcialmente satisfecho');
INSERT INTO public.conflict_results (name_ru, name_en, name_es) VALUES ('Не удовлетворены', 'Not satisfied', 'No satisfecho');

INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Россия', 'Russia', 'Rusia');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Азербайджан', 'Azerbaijan', 'Azerbaiyán');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Армения', 'Armenia', 'Armenia');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Беларусь', 'Belarus', 'Bielorrusia');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Казахстан', 'Kazakhstan', 'Kazajistán');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Молдова', 'Moldova', 'Moldavia');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Таджикистан', 'Tajikistan', 'Tadjikistán');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Узбекистан', 'Uzbekistan', 'Uzbekistán');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Грузия', 'Georgia', 'Georgia');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Украина', 'Ukraine', 'Ucrania');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Туркменистан', 'Turkmenistan', 'Turkmenistán');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Литва', 'Lithuania', 'Lituania');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Латвия', 'Latvia', 'Letonia');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Эстония', 'Estonia', 'Estonia');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Республика Корея', 'The Republic of Corea', 'República de Corea');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Киргизия', 'Kyrgyzstan', 'Kirguizstán');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Монголия', 'Mongolia', 'Mongolia');
INSERT INTO public.countries (name_ru, name_en, name_es) VALUES ('Турция', 'Turkey', 'Pavo');

INSERT INTO public.event_statuses (name_ru, name_en, name_es) VALUES ('Развитие', 'Development', 'Desarrollo');
INSERT INTO public.event_statuses (name_ru, name_en, name_es) VALUES ('Завершение', 'Completion', 'Finalización');
INSERT INTO public.event_statuses (name_ru, name_en, name_es) VALUES ('Новый', 'New', 'Nuevo');

INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Обращение', 'Appeal', 'Reclamación');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Демонстрация', 'Demonstration', 'Demostración');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Голодовка', 'Hunger strike', 'Huelga de hambre');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Забастовка', 'Strike', 'Huelga');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Столкновение', 'Clash', 'Choque');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Угроза', 'Threat', 'Amenaza');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('ст. 142 ТК РФ', 'st. 142', 'Art.142');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Переговоры', 'Conversation', 'Negociación');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Забастовка частичная', 'Partial strike', 'Huelga parcial');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Перекрытие магистралей', 'Highway overlap', 'Vía solapada');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Суд', 'Court', 'La corte');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Расследование', 'Investigation', 'Investigación');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Отказ от переработок', 'Refusal of processing', 'Negativa de tramitación');
INSERT INTO public.event_types (name_ru, name_en, name_es) VALUES ('Массовое увольнение', 'Mass layoff', 'Despido masivo');

INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Строительство', 'Building', 'Edificio');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Транспорт', 'Transport', 'Transporte');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Торговля', 'Trading', 'Comercio');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Обрабатывающие производства', 'Manufacturing industries', 'Industrias manufactureras');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Госслужба', 'State service', 'Servicio estatal');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('ЖКХ', 'Housing and communal services', 'Vivienda y servicios comunitarios.');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Культура и спорт', 'Culture and sport', 'Cultura y deporte');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Сельское хозяйство', 'Agriculture', 'Agricultura');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Добывающая промышленность', 'Mining industry', 'Industria minera');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Электроэнергетика', 'Power industry', 'Industria de la energía');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Здравоохранение', 'Health care', 'Cuidado de la salud');
INSERT INTO public.industries (name_ru, name_en, name_es) VALUES ('Образование и наука', 'Education and science', 'Educación y ciencia');

INSERT INTO public.video_types (code) VALUES ('youtube_link');
INSERT INTO public.video_types (code) VALUES ('vk_link');
INSERT INTO public.video_types (code) VALUES ('other');