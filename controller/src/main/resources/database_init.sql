DROP DATABASE library;
CREATE DATABASE library;

CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS users (
  user_id serial PRIMARY KEY,
  login VARCHAR ( 50 ) UNIQUE NOT NULL,
  password VARCHAR ( 50 ) NOT NULL,
  role VARCHAR ( 255 ) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
  product_id serial PRIMARY KEY,
  title VARCHAR ( 50 ) UNIQUE NOT NULL,
  author VARCHAR ( 50 ) NOT NULL,
  isReserved boolean,
  publishYear INT,
  publisher VARCHAR (255),
  genre VARCHAR (255),
  number_of_pages INT,
  is_hard_cover boolean 
);

CREATE TABLE IF NOT EXISTS orders (
  order_id serial PRIMARY KEY,
  product_id int not null,
  user_id int not null,
    FOREIGN KEY (product_id)
      REFERENCES products (product_id),
  FOREIGN KEY (user_id)
      REFERENCES users (user_id)
);
