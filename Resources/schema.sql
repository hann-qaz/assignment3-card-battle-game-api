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
    hp int, -- для Warrior и Building карт
    damage int, -- для Warrior и Spell карт
    lifetime int, -- для Building карт
    radius int -- для Spell карт
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
insert into cards (name, card_type, rarity, elixir_cost, level, hp, damage, lifetime, radius) values
('Knight', 'Warrior', 'Common', 3, 5, 1400, 125, NULL, NULL),
('Fireball', 'Spell', 'Rare', 4, 3, NULL, 572, NULL, 3),
('Cannon', 'Building', 'Common', 3, 6, 824, NULL, 30, NULL),
('Archer', 'Warrior', 'Common', 3, 7, 252, 102, NULL, NULL),
('Giant', 'Warrior', 'Epic', 5, 4, 3275, 211, NULL, NULL),
('Zap', 'Spell', 'Common', 2, 8, NULL, 159, NULL, 3),
('Tesla', 'Building', 'Rare', 4, 2, 799, NULL, 35, NULL),
('Wizard', 'Warrior', 'Epic', 5, 5, 598, 307, NULL, NULL);


