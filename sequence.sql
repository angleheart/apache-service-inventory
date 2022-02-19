DROP DATABASE Sequences;
CREATE DATABASE Sequences;
USE Sequences;

CREATE TABLE Sequences
(
    SaveName            varchar(50) PRIMARY KEY,
    CreationTime        TIMESTAMP        NOT NULL,
    CustomerNumber      varchar(10)      NOT NULL,
    CounterPersonNumber tinyint UNSIGNED NOT NULL,
    PurchaseOrder       varchar(15),
    VehicleDescription  varchar(50),
    ShipTo              varchar(50),
    FreightTotal        decimal(10, 2)
);

CREATE TABLE SequenceLines
(
    IndexKey    int UNSIGNED   NOT NULL,
    SaveName    varchar(50)    NOT NULL,
    Quantity    int            NOT NULL,
    LineCode    char(3)        NOT NULL,
    PartNumber  varchar(50)    NOT NULL,
    Description varchar(50)    NOT NULL,
    ListPrice   decimal(10, 3) NOT NULL,
    UnitPrice   decimal(10, 3) NOT NULL,
    TaxCode     char(1)        NOT NULL,
    FOREIGN KEY (SaveName) references Sequences (SaveName),
    CONSTRAINT PK_SequenceLines PRIMARY KEY (IndexKey, SaveName)
);