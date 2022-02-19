DROP DATABASE Accounting;
CREATE DATABASE Accounting;
USE Accounting;


CREATE TABLE Invoices
(
    InvoiceNumber       int UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    CustomerNumber      varchar(10)       NOT NULL,
    CounterPersonNumber tinyint UNSIGNED  NOT NULL,
    PurchaseOrder       varchar(15),
    VehicleDescription  varchar(50),
    ShipTo              varchar(50),
    ReleaseTime         TIMESTAMP         NOT NULL,
    TransCode           varchar(3)        NOT NULL,
    ReleaseCode         tinyint           NOT NULL,
    Balance             decimal(10, 2)    NOT NULL,

    FreightTotal        decimal(10, 2)    NOT NULL,
    TaxRate             decimal(3, 3)     NOT NULL,

    AccountingPeriod    smallint UNSIGNED NOT NULL
);

CREATE TABLE InvoiceLines
(
    IndexKey      int UNSIGNED   NOT NULL,
    InvoiceNumber int UNSIGNED   NOT NULL,
    Quantity      int            NOT NULL,
    LineCode      char(3)        NOT NULL,
    PartNumber    varchar(50)    NOT NULL,
    Description   varchar(50)    NOT NULL,
    ListPrice     decimal(10, 3) NOT NULL,
    Price         decimal(10, 3) NOT NULL,
    TaxCode       char(1)        NOT NULL,

    FOREIGN KEY (InvoiceNumber) references Invoices (InvoiceNumber),
    CONSTRAINT PK_InvoiceLines PRIMARY KEY (IndexKey, InvoiceNumber)
);


CREATE TABLE Payments
(
    PaymentID        int UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    CustomerNumber   varchar(10)       NOT NULL,
    PaymentAmount    decimal(10, 2)    NOT NULL,
    ReleaseCode      tinyint UNSIGNED  NOT NULL,
    AccountingPeriod smallint UNSIGNED NOT NULL,
    DocumentDetail   varchar(25)       NOT NULL,
    ReleaseTime      timestamp
);

CREATE TABLE PerInvoicePayments
(
    PaymentID     int UNSIGNED   NOT NULL,
    InvoiceNumber int UNSIGNED   NOT NULL,
    AmountApplied decimal(10, 2) NOT NULL,
    FOREIGN KEY (PaymentID) REFERENCES Payments (PaymentID),
    FOREIGN KEY (InvoiceNumber) REFERENCES Invoices (InvoiceNumber)
);