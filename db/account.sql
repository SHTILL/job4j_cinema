CREATE TABLE public.account
(
    id integer NOT NULL DEFAULT nextval('account_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    phone text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT name_email_phone_unique UNIQUE (name, email, phone)
)