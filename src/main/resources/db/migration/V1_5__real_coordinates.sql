ALTER TABLE conflicts ALTER COLUMN longitude TYPE REAL USING longitude::REAL;
ALTER TABLE conflicts ALTER COLUMN latitude TYPE REAL USING latitude::REAL;
ALTER TABLE strike.public.events ALTER COLUMN longitude TYPE REAL USING longitude::REAL;
ALTER TABLE strike.public.events ALTER COLUMN latitude TYPE REAL USING latitude::REAL;