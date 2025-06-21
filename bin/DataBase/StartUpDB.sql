CREATE DATABASE IF NOT EXISTS your_database_name;
USE your_database_name;

CREATE TABLE IF NOT EXISTS admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone_no VARCHAR(20),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS founder (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone_no VARCHAR(20),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS mentor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone_no VARCHAR(20),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS investor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone_no VARCHAR(20),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

Use startup_db;
CREATE TABLE startups (
    id INT AUTO_INCREMENT PRIMARY KEY,
    founder_name VARCHAR(100) NOT NULL,
    startup_name VARCHAR(100) NOT NULL UNIQUE,
    industry VARCHAR(100),
    business_model VARCHAR(100),
    description TEXT,
    website VARCHAR(150),
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);
CREATE TABLE startups (
    id INT AUTO_INCREMENT PRIMARY KEY,
    founder_name VARCHAR(100),
    startup_name VARCHAR(100),
    industry VARCHAR(100),
    model VARCHAR(100),
    description TEXT,
    website VARCHAR(255),
    email VARCHAR(100),
    password VARCHAR(100)
);

CREATE TABLE team (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_name VARCHAR(255) NOT NULL,
    role VARCHAR(100) NOT NULL,
    contact VARCHAR(100),
    FOREIGN KEY (startup_id) REFERENCES startups(id)
);


USE startup_db; 
CREATE TABLE services (
    id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    description TEXT,
    startup_id INT,
    FOREIGN KEY (startup_id) REFERENCES startups(id) 
);
