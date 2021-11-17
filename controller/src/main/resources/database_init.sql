DROP DATABASE library;
CREATE DATABASE library;

CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS users (
  user_id serial PRIMARY KEY,
  login VARCHAR ( 50 ) UNIQUE NOT NULL,
  password VARCHAR ( 100 ) NOT NULL,
  role VARCHAR ( 255 ) NOT NULL,
  salt VARCHAR (100)
);

CREATE TABLE IF NOT EXISTS books (
  book_id serial PRIMARY KEY,
  title VARCHAR ( 50 ) UNIQUE NOT NULL,
  author VARCHAR ( 50 ) NOT NULL,
  isReserved boolean,
  publishYear INT,
  publisher VARCHAR (255),
  genre VARCHAR (255),
  number_of_pages INT,
  is_hard_cover boolean,
  description VARCHAR (255)
);

CREATE TABLE IF NOT EXISTS orders (
  order_id serial PRIMARY KEY,
  product_id VARCHAR (255) NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS reserves (
  reserve_id serial PRIMARY KEY,
  user_id INT NOT NULL,
  product_id INT NOT NULL
);
