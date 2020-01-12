ALTER TABLE event_statuses ADD COLUMN slug VARCHAR(15);

UPDATE event_statuses SET slug = 'new' WHERE name_ru = 'Новый';
UPDATE event_statuses SET slug = 'intermediate' WHERE name_ru = 'Развитие';
UPDATE event_statuses SET slug = 'final' WHERE name_ru = 'Завершение';

ALTER TABLE event_statuses ALTER COLUMN slug SET NOT NULL;
ALTER TABLE event_statuses ADD CONSTRAINT unique_slug UNIQUE(slug);
