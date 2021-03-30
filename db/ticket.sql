CREATE TABLE public.ticket
(
    id integer NOT NULL DEFAULT nextval('ticket_id_seq'::regclass),
    session_id integer NOT NULL,
    account_id integer NOT NULL,
    "row" integer NOT NULL,
    seat integer NOT NULL,
    CONSTRAINT ticket_pkey PRIMARY KEY (id),
    CONSTRAINT row_seat_session_unique UNIQUE ("row", seat, session_id),
    CONSTRAINT account_fk FOREIGN KEY (account_id)
        REFERENCES public.account (id) MATCH SIMPLE,
    CONSTRAINT session_fk FOREIGN KEY (session_id)
        REFERENCES public.film_session (id) MATCH SIMPLE
)