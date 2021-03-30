CREATE TABLE public.film_session
(
    id integer NOT NULL DEFAULT nextval('film_session_id_seq'::regclass),
    description text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT film_session_pkey PRIMARY KEY (id)
)