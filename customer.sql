CREATE DATABASE Customers;
USE Customers;

CREATE TABLE Customers
(
    CustomerNumber  varchar(10) PRIMARY KEY,
    Name            varchar(30)   NOT NULL,
    Address         varchar(50)   NOT NULL,
    City            varchar(30)   NOT NULL,
    State           char(2)       NOT NULL,
    Zip             char(5)       NOT NULL,
    PhoneNumber     varchar(15)   NOT NULL,
    Taxable         boolean DEFAULT true
);