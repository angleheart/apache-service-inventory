CREATE DATABASE Inventory;
USE Inventory;

CREATE TABLE Parts
(
    LineCode          char(3)     NOT NULL,
    PartNumber        varchar(50) NOT NULL,
    Description       varchar(50),
    Cost              decimal(10, 2),
    StockQuantity     int         NOT NULL,
    AvailableQuantity int         NOT NULL,
    CONSTRAINT Pk_Parts PRIMARY KEY (LineCode, PartNumber)
);