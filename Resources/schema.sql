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
    level int default 1 check ( level > 0 and level <= 16)
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

---- данны
insert into players (name, level, trophies) values
('Player1', 10, 4500),
('Player2', 8, 3200);
insert into cards (name, card_type, rarity, elixir_cost, level) values
('Knight', 'Warrior', 'Common', 3, 5),
('Fireball', 'Spell', 'Rare', 4, 3),
('Cannon', 'Building', 'Common', 3, 6),
('Archer', 'Warrior', 'Common', 3, 7),
('Giant', 'Warrior', 'Epic', 5, 4),
('Zap', 'Spell', 'Common', 2, 8),
('Tesla', 'Building', 'Rare', 4, 2),
('Wizard', 'Warrior', 'Epic', 5, 5);


