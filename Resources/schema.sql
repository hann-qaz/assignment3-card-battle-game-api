DROP TABLE IF EXISTS deck_cards;
DROP TABLE IF EXISTS decks;
DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS players;

---таблица игроков
create table players (
    id serial primary key,
    name varchar(50) unique not null,
    level int default 1,
    trophies int default 0
);

---таблица карт
create table cards (
    id serial primary key,
    name varchar (50) not null,
    card_type varchar(20), --воин, заклинание, здание
    rarity varchar (20), --обычная, редкая, эпик, легендарка
    elixir_cost int  check (elixir_cost > 0 and elixir_cost <= 10),
    level int default 1 check ( level > 0 and level <= 16),
    damage int default 0,
    hp int default 0,
    radius int default 0,
    lifetime int default 0
);

--- таблица колод
create table decks (
    id serial primary key,
    player_id int not null references players(id) on delete cascade,
    deck_name varchar(50)
);

-- таблица для связи карт в колоде ( по 8 карт в одной колоде) многие ко многим
create table deck_cards (
deck_id int not null references decks(id) on delete cascade,
card_id int not null references cards(id) on delete cascade,
position int not null check (position >= 1 and position <=8),
primary key (deck_id, card_id),
unique (deck_id, position)
);
