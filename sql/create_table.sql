create table if not exists public.region
(
    region_id    bigint not null
        primary key,
    created_date timestamp,
    updated_date timestamp,
    city         varchar(255),
    country      varchar(255)
);

alter table public.region
    owner to fcprovin;

create table if not exists public.sns
(
    sns_id       bigint not null
        primary key,
    created_date timestamp,
    updated_date timestamp,
    type         varchar(255),
    uuid         varchar(255)
        constraint uk_7cf9gqrd04ky0wtjhrwfi4nbt
            unique
);

alter table public.sns
    owner to fcprovin;

create table if not exists public.member
(
    member_id    bigint not null
        primary key,
    created_date timestamp,
    updated_date timestamp,
    birth_date   date,
    email        varchar(255)
        constraint uk_mbmcqelty0fbrvxp1q58dn57t
            unique,
    name         varchar(255),
    sns_id       bigint
        constraint fk7da8ocrq7kyre6u4vqd9xddsa
            references public.sns
);

alter table public.member
    owner to fcprovin;

create table if not exists public.stadium
(
    stadium_id   bigint           not null
        primary key,
    created_date timestamp,
    updated_date timestamp,
    address      varchar(255),
    latitude     double precision not null,
    longitude    double precision not null,
    name         varchar(255)
);

alter table public.stadium
    owner to fcprovin;

create table if not exists public.team
(
    team_id      bigint not null
        primary key,
    created_date timestamp,
    updated_date timestamp,
    name         varchar(255),
    status       varchar(255),
    region_id    bigint
        constraint fk1mvpu5m4jl4llnakyltahxvq9
            references public.region
);

alter table public.team
    owner to fcprovin;

create table if not exists public.match
(
    match_id             bigint  not null
        primary key,
    created_date         timestamp,
    updated_date         timestamp,
    attend_deadline_date timestamp,
    home                 boolean not null,
    end_date             timestamp,
    start_date           timestamp,
    notice               varchar(255),
    other_team_name      varchar(255),
    status               varchar(255),
    vote_end_date        timestamp,
    vote_start_date      timestamp,
    stadium_id           bigint
        constraint fkrts3eov4pp1qbwx2e2vtbgwls
            references public.stadium,
    team_id              bigint
        constraint fk7oi14pd4sn24w1q63konp77cx
            references public.team
);

alter table public.match
    owner to fcprovin;

create table if not exists public.player
(
    player_id      bigint  not null
        primary key,
    created_date   timestamp,
    updated_date   timestamp,
    authority      varchar(255),
    position       varchar(255),
    status         varchar(255),
    uniform_number integer not null,
    member_id      bigint
        constraint fktky0mr5eq3kww4rqiscxivebo
            references public.member,
    team_id        bigint
        constraint fkdvd6ljes11r44igawmpm1mc5s
            references public.team
);

alter table public.player
    owner to fcprovin;

create table if not exists public.attend
(
    attend_id    bigint not null
        primary key,
    created_date timestamp,
    updated_date timestamp,
    status       varchar(255),
    match_id     bigint
        constraint fksuvcyn7pqqgbo4fblocvw5tc4
            references public.match,
    player_id    bigint
        constraint fk3a6j8ffgyqyhmtpiaby4d01c5
            references public.player
);

alter table public.attend
    owner to fcprovin;

create table if not exists public.vote
(
    vote_id      bigint not null
        primary key,
    created_date timestamp,
    updated_date timestamp,
    status       varchar(255),
    match_id     bigint
        constraint fkdi4j14msoissmti1vyatas7yg
            references public.match,
    player_id    bigint
        constraint fk5cxvn2h3fuqpbgvedhp3nip49
            references public.player
);

alter table public.vote
    owner to fcprovin;

