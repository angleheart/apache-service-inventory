CREATE DATABASE Vehicles;
USE Vehicles;

CREATE TABLE Vehicles
(
    Year   varchar(4)  NOT NULL,
    Make   varchar(50) NOT NULL,
    Model  varchar(50) NOT NULL,
    Engine varchar(50) NOT NULL,
    CONSTRAINT Pk_Vehicles PRIMARY KEY (Year, Make, Model, Engine)
);
