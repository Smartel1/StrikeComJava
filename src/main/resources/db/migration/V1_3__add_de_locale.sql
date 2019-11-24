ALTER TABLE public.client_versions
    ADD COLUMN description_de VARCHAR(500) NOT NULL DEFAULT '';
ALTER TABLE public.conflict_reasons
    ADD COLUMN name_de VARCHAR(255);
ALTER TABLE public.conflict_results
    ADD COLUMN name_de VARCHAR(255);
ALTER TABLE public.conflicts
    ADD COLUMN title_de VARCHAR(255);
ALTER TABLE public.countries
    ADD COLUMN name_de VARCHAR(255);
ALTER TABLE public.event_statuses
    ADD COLUMN name_de VARCHAR(255);
ALTER TABLE public.event_types
    ADD COLUMN name_de VARCHAR(255);
ALTER TABLE public.events
    ADD COLUMN title_de VARCHAR(255);
ALTER TABLE public.events
    ADD COLUMN content_de text;
ALTER TABLE public.industries
    ADD COLUMN name_de VARCHAR(255);
ALTER TABLE public.news
    ADD COLUMN title_de VARCHAR(255);
ALTER TABLE public.news
    ADD COLUMN content_de TEXT;

ALTER TABLE public.client_versions ALTER COLUMN description_de DROP DEFAULT;
