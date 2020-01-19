ALTER TABLE client_versions ALTER COLUMN description_ru TYPE TEXT USING description_ru::TEXT;
ALTER TABLE client_versions ALTER COLUMN description_en TYPE TEXT USING description_en::TEXT;
ALTER TABLE client_versions ALTER COLUMN description_es TYPE TEXT USING description_es::TEXT;
ALTER TABLE client_versions ALTER COLUMN description_de TYPE TEXT USING description_de::TEXT;