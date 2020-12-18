CREATE DATABASE J3LP0003
USE J3LP0003

CREATE TABLE tblRole (
	roleId VARCHAR(5) PRIMARY KEY NOT NULL,
	roleName VARCHAR(5) NOT NULL
)
GO

CREATE TABLE tblStatus (
	statusId INT PRIMARY KEY NOT NULL,
	statusName VARCHAR(20) NOT NULL
)
GO 

CREATE TABLE tblAccount (
	username VARCHAR(50) PRIMARY KEY NOT NULL,
	password char(50) NOT NULL,
	fullName NVARCHAR(100) NOT NULL,
	address NVARCHAR(200),
	phone NVARCHAR(10),
	createdDate DATE DEFAULT GETDATE() NOT NULL,
	roleId VARCHAR(5) DEFAULT 'user' FOREIGN KEY REFERENCES dbo.tblRole(roleId),
	statusId INT DEFAULT 1  FOREIGN KEY REFERENCES tblStatus(statusId)
)
GO 

CREATE TABLE tblRoomType (
	typeId NVARCHAR(50) PRIMARY KEY,
	typeName NVARCHAR(50) NOT NULL,
)
GO 

CREATE TABLE tblRoom (
	roomId INT IDENTITY(1,1) PRIMARY KEY,
	typeId NVARCHAR(50) FOREIGN KEY REFERENCES dbo.tblRoomType(typeId) NOT NULL,
	image VARCHAR(100), 
	price DECIMAL NOT NULL,
)
GO 


CREATE TABLE tblDiscount (
	discountId INT IDENTITY(1,1) PRIMARY KEY,
	discountPercent INT NOT NULL,
	date DATE DEFAULT GETDATE() NOT NULL,
	status BIT DEFAULT 1 NOT NULL,
)
GO 

CREATE TABLE tblOrder (
	orderId VARCHAR(36) DEFAULT NEWID() PRIMARY KEY ,
	username VARCHAR(50) FOREIGN KEY REFERENCES dbo.tblAccount(username),
	name NVARCHAR(50),
	address NVARCHAR(200),
	phoneNumber VARCHAR(10),
	discountId INT FOREIGN KEY REFERENCES dbo.tblDiscount(discountId),
	totalPrice DECIMAL NOT NULL,
	discountPrice DECIMAL NOT NULL,
	bookingDate DATETIME DEFAULT GETDATE(), 
) 
GO

CREATE TABLE tblOrderDetail (
	detailId INT IDENTITY(1,1) PRIMARY KEY,
	orderId VARCHAR(36) FOREIGN KEY REFERENCES dbo.tblOrder(orderId),
	roomId INT FOREIGN KEY REFERENCES dbo.tblRoom(roomId),
	totalPrice DECIMAL NOT NULL,
	checkinDate DATE NOT NULL,
	checkoutDate DATE NOT NULL,
)
GO

DROP TABLE dbo.tblRole
DROP TABLE dbo.tblStatus
DROP TABLE dbo.tblAccount
DROP TABLE dbo.tblRoom
DROP TABLE dbo.tblRoomType
DROP TABLE dbo.tblDiscount
DROP TABLE dbo.tblOrder
DROP TABLE dbo.tblOrderDetail