-- auto-generated definition
CREATE TABLE address
(
  id      SERIAL NOT NULL
    CONSTRAINT address_id_pk
    PRIMARY KEY,
  user_id VARCHAR,
  city    VARCHAR,
  street  VARCHAR,
  house   VARCHAR,
  flat    VARCHAR
);

-- auto-generated definition
CREATE TABLE booking
(
  id           SERIAL NOT NULL
    CONSTRAINT booking_pkey
    PRIMARY KEY,
  user_info_id INTEGER,
  address_id   INTEGER,
  created      TIMESTAMP DEFAULT now(),
  updated      TIMESTAMP DEFAULT now(),
  status       VARCHAR
);

CREATE UNIQUE INDEX booking_id_uindex
  ON booking (id);

-- auto-generated definition
CREATE TABLE booking_products
(
  id          SERIAL NOT NULL
    CONSTRAINT order_info_id_pk
    PRIMARY KEY,
  order_id    INTEGER,
  product_id  INTEGER,
  quantity    INTEGER,
  price       DOUBLE PRECISION,
  total_price DOUBLE PRECISION
);

CREATE UNIQUE INDEX order_info_id_uindex
  ON booking_products (id);

-- auto-generated definition
CREATE TABLE "order"
(
  id           SERIAL NOT NULL
    CONSTRAINT order_pkey
    PRIMARY KEY,
  user_info_id INTEGER,
  address_id   INTEGER,
  total_price  DOUBLE PRECISION DEFAULT 0,
  created      TIMESTAMP        DEFAULT now(),
  updated      TIMESTAMP        DEFAULT now(),
  status       VARCHAR
);

-- auto-generated definition
CREATE TABLE phones
(
  bigint INTEGER
);

-- auto-generated definition
CREATE TABLE post
(
  id      SERIAL NOT NULL
    CONSTRAINT post_pkey
    PRIMARY KEY,
  title   VARCHAR,
  content TEXT
);

CREATE UNIQUE INDEX post_id_uindex
  ON post (id);

-- auto-generated definition
CREATE TABLE product
(
  id       SERIAL NOT NULL
    CONSTRAINT products_pkey
    PRIMARY KEY,
  name     VARCHAR,
  category VARCHAR,
  price    DOUBLE PRECISION DEFAULT 0
);

-- auto-generated definition
CREATE TABLE user_entity
(
  id         VARCHAR NOT NULL
    CONSTRAINT user_entity_pkey
    PRIMARY KEY,
  email      VARCHAR,
  password   VARCHAR,
  role       VARCHAR DEFAULT 'USER' :: CHARACTER VARYING,
  last_login TIMESTAMP
);

-- auto-generated definition
CREATE TABLE user_info
(
  id          SERIAL NOT NULL
    CONSTRAINT user_info_pkey
    PRIMARY KEY,
  user_id     VARCHAR,
  first_name  VARCHAR,
  second_name VARCHAR,
  sex         VARCHAR,
  birthday    DATE,
  phone       BIGINT
);

CREATE UNIQUE INDEX user_info_id_uindex
  ON user_info (id);


INSERT INTO user_entity (id, email, password, role) VALUES ('21232f297a57a5a743894a0e4a801fc3','admin', '21232f297a57a5a743894a0e4a801fc3', 'ADMIN')
