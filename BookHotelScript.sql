USE [master]
GO
/****** Object:  Database [J3LP0003]    Script Date: 12/18/2020 9:20:24 PM ******/
CREATE DATABASE [J3LP0003]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'J3LP0003', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\J3LP0003.mdf' , SIZE = 3264KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'J3LP0003_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\J3LP0003_log.ldf' , SIZE = 816KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [J3LP0003] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [J3LP0003].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [J3LP0003] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [J3LP0003] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [J3LP0003] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [J3LP0003] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [J3LP0003] SET ARITHABORT OFF 
GO
ALTER DATABASE [J3LP0003] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [J3LP0003] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [J3LP0003] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [J3LP0003] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [J3LP0003] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [J3LP0003] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [J3LP0003] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [J3LP0003] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [J3LP0003] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [J3LP0003] SET  ENABLE_BROKER 
GO
ALTER DATABASE [J3LP0003] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [J3LP0003] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [J3LP0003] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [J3LP0003] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [J3LP0003] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [J3LP0003] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [J3LP0003] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [J3LP0003] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [J3LP0003] SET  MULTI_USER 
GO
ALTER DATABASE [J3LP0003] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [J3LP0003] SET DB_CHAINING OFF 
GO
ALTER DATABASE [J3LP0003] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [J3LP0003] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [J3LP0003] SET DELAYED_DURABILITY = DISABLED 
GO
USE [J3LP0003]
GO
/****** Object:  Table [dbo].[tblAccount]    Script Date: 12/18/2020 9:20:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblAccount](
	[username] [varchar](50) NOT NULL,
	[password] [char](50) NOT NULL,
	[fullName] [nvarchar](100) NOT NULL,
	[address] [nvarchar](200) NULL,
	[phone] [nvarchar](10) NULL,
	[createdDate] [date] NOT NULL DEFAULT (getdate()),
	[roleId] [varchar](5) NULL DEFAULT ('user'),
	[statusId] [int] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblDiscount]    Script Date: 12/18/2020 9:20:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblDiscount](
	[discountId] [int] IDENTITY(1,1) NOT NULL,
	[discountPercent] [int] NOT NULL,
	[date] [date] NOT NULL DEFAULT (getdate()),
	[status] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[discountId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblOrder]    Script Date: 12/18/2020 9:20:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblOrder](
	[orderId] [varchar](36) NOT NULL DEFAULT (newid()),
	[username] [varchar](50) NULL,
	[name] [nvarchar](50) NULL,
	[address] [nvarchar](200) NULL,
	[phoneNumber] [varchar](10) NULL,
	[discountId] [int] NULL,
	[totalPrice] [decimal](18, 0) NOT NULL,
	[discountPrice] [decimal](18, 0) NOT NULL,
	[bookingDate] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[orderId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblOrderDetail]    Script Date: 12/18/2020 9:20:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblOrderDetail](
	[detailId] [int] IDENTITY(1,1) NOT NULL,
	[orderId] [varchar](36) NULL,
	[roomId] [int] NULL,
	[totalPrice] [decimal](18, 0) NOT NULL,
	[checkinDate] [date] NOT NULL,
	[checkoutDate] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[detailId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRole]    Script Date: 12/18/2020 9:20:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRole](
	[roleId] [varchar](5) NOT NULL,
	[roleName] [varchar](5) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[roleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRoom]    Script Date: 12/18/2020 9:20:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRoom](
	[roomId] [int] IDENTITY(1,1) NOT NULL,
	[typeId] [nvarchar](50) NOT NULL,
	[image] [varchar](100) NULL,
	[price] [decimal](18, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[roomId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRoomType]    Script Date: 12/18/2020 9:20:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRoomType](
	[typeId] [nvarchar](50) NOT NULL,
	[typeName] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[typeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblStatus]    Script Date: 12/18/2020 9:20:24 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblStatus](
	[statusId] [int] NOT NULL,
	[statusName] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[statusId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblAccount] ([username], [password], [fullName], [address], [phone], [createdDate], [roleId], [statusId]) VALUES (N'admin', N'admin                                             ', N'admin', NULL, NULL, CAST(N'2020-12-15' AS Date), N'admin', 1)
INSERT [dbo].[tblAccount] ([username], [password], [fullName], [address], [phone], [createdDate], [roleId], [statusId]) VALUES (N'hau', N'hau                                               ', N'hau', NULL, NULL, CAST(N'2020-12-15' AS Date), N'user', 1)
INSERT [dbo].[tblAccount] ([username], [password], [fullName], [address], [phone], [createdDate], [roleId], [statusId]) VALUES (N'long', N'12345678                                          ', N'long', N'123ThichQuangDuc', N'0123456789', CAST(N'2020-12-15' AS Date), N'user', 1)
INSERT [dbo].[tblAccount] ([username], [password], [fullName], [address], [phone], [createdDate], [roleId], [statusId]) VALUES (N'longpt', N'12345678                                          ', N'longpt', N'233PPHOUTO', N'0123414361', CAST(N'2020-12-18' AS Date), N'user', 1)
INSERT [dbo].[tblAccount] ([username], [password], [fullName], [address], [phone], [createdDate], [roleId], [statusId]) VALUES (N'redore0808@gmail.com', N'12345678                                          ', N'redore0808@gmail.com', NULL, NULL, CAST(N'2020-12-18' AS Date), N'user', 1)
INSERT [dbo].[tblAccount] ([username], [password], [fullName], [address], [phone], [createdDate], [roleId], [statusId]) VALUES (N'redore0908@gmail.com', N'12345678                                          ', N'redore0908@gmail.com', NULL, NULL, CAST(N'2020-12-18' AS Date), N'user', 1)
INSERT [dbo].[tblAccount] ([username], [password], [fullName], [address], [phone], [createdDate], [roleId], [statusId]) VALUES (N'redore137@gmail.com', N'12345678                                          ', N'redore137@gmail.com', NULL, NULL, CAST(N'2020-12-16' AS Date), N'user', 1)
INSERT [dbo].[tblAccount] ([username], [password], [fullName], [address], [phone], [createdDate], [roleId], [statusId]) VALUES (N'tinder', N'tinder                                            ', N'tinder', NULL, NULL, CAST(N'2020-12-15' AS Date), N'user', 2)
SET IDENTITY_INSERT [dbo].[tblDiscount] ON 

INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (1, 5, CAST(N'2020-12-16' AS Date), 0)
INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (2, 5, CAST(N'2020-12-16' AS Date), 0)
INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (3, 10, CAST(N'2020-12-16' AS Date), 1)
INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (4, 10, CAST(N'2020-12-17' AS Date), 1)
INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (5, 5, CAST(N'2020-12-17' AS Date), 1)
INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (6, 5, CAST(N'2020-12-17' AS Date), 1)
INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (7, 10, CAST(N'2020-12-17' AS Date), 1)
INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (8, 5, CAST(N'2020-12-17' AS Date), 1)
INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (9, 2, CAST(N'2020-12-17' AS Date), 1)
INSERT [dbo].[tblDiscount] ([discountId], [discountPercent], [date], [status]) VALUES (10, 3, CAST(N'2020-12-17' AS Date), 1)
SET IDENTITY_INSERT [dbo].[tblDiscount] OFF
INSERT [dbo].[tblOrder] ([orderId], [username], [name], [address], [phoneNumber], [discountId], [totalPrice], [discountPrice], [bookingDate]) VALUES (N'4C0C8DCD-22EF-4DC3-AF95-3E7C05453039', N'redore0808@gmail.com', N'redore0808@gmail.com', N'123PXL', N'2222222222', 2, CAST(2500000 AS Decimal(18, 0)), CAST(2375000 AS Decimal(18, 0)), CAST(N'2020-12-18 21:17:13.280' AS DateTime))
INSERT [dbo].[tblOrder] ([orderId], [username], [name], [address], [phoneNumber], [discountId], [totalPrice], [discountPrice], [bookingDate]) VALUES (N'6D672DE4-23D7-431C-A796-B96533D16E90', N'redore0908@gmail.com', N'redore0908@gmail.com', N'232PXL', N'1111111111', 1, CAST(2000000 AS Decimal(18, 0)), CAST(1900000 AS Decimal(18, 0)), CAST(N'2020-12-18 21:16:01.120' AS DateTime))
SET IDENTITY_INSERT [dbo].[tblOrderDetail] ON 

INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [roomId], [totalPrice], [checkinDate], [checkoutDate]) VALUES (1, N'6D672DE4-23D7-431C-A796-B96533D16E90', 1, CAST(2000000 AS Decimal(18, 0)), CAST(N'2020-12-19' AS Date), CAST(N'2020-12-19' AS Date))
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [roomId], [totalPrice], [checkinDate], [checkoutDate]) VALUES (2, N'4C0C8DCD-22EF-4DC3-AF95-3E7C05453039', 2, CAST(2500000 AS Decimal(18, 0)), CAST(N'2020-12-19' AS Date), CAST(N'2020-12-19' AS Date))
SET IDENTITY_INSERT [dbo].[tblOrderDetail] OFF
INSERT [dbo].[tblRole] ([roleId], [roleName]) VALUES (N'admin', N'admin')
INSERT [dbo].[tblRole] ([roleId], [roleName]) VALUES (N'user', N'user')
SET IDENTITY_INSERT [dbo].[tblRoom] ON 

INSERT [dbo].[tblRoom] ([roomId], [typeId], [image], [price]) VALUES (1, N'Single', N'D:\CN5\LAB231_BLOCK3W\J3.L.P0003\Images\Single.PNG', CAST(2000000 AS Decimal(18, 0)))
INSERT [dbo].[tblRoom] ([roomId], [typeId], [image], [price]) VALUES (2, N'Twin', N'D:\CN5\LAB231_BLOCK3W\J3.L.P0003\Images\Twin.PNG', CAST(2500000 AS Decimal(18, 0)))
INSERT [dbo].[tblRoom] ([roomId], [typeId], [image], [price]) VALUES (3, N'Superior', N'D:\CN5\LAB231_BLOCK3W\J3.L.P0003\Images\Superior.PNG', CAST(3000000 AS Decimal(18, 0)))
INSERT [dbo].[tblRoom] ([roomId], [typeId], [image], [price]) VALUES (4, N'Deluxe', N'D:\CN5\LAB231_BLOCK3W\J3.L.P0003\Images\Deluxe.PNG', CAST(3500000 AS Decimal(18, 0)))
SET IDENTITY_INSERT [dbo].[tblRoom] OFF
INSERT [dbo].[tblRoomType] ([typeId], [typeName]) VALUES (N'Deluxe', N'Deluxe')
INSERT [dbo].[tblRoomType] ([typeId], [typeName]) VALUES (N'Double', N'Double')
INSERT [dbo].[tblRoomType] ([typeId], [typeName]) VALUES (N'Extra bed', N'Extra bed')
INSERT [dbo].[tblRoomType] ([typeId], [typeName]) VALUES (N'Single', N'Single')
INSERT [dbo].[tblRoomType] ([typeId], [typeName]) VALUES (N'Superior', N'Superior')
INSERT [dbo].[tblRoomType] ([typeId], [typeName]) VALUES (N'Triple', N'Triple')
INSERT [dbo].[tblRoomType] ([typeId], [typeName]) VALUES (N'Twin', N'Twin')
INSERT [dbo].[tblStatus] ([statusId], [statusName]) VALUES (1, N'Active')
INSERT [dbo].[tblStatus] ([statusId], [statusName]) VALUES (2, N'Inactive')
ALTER TABLE [dbo].[tblAccount]  WITH CHECK ADD FOREIGN KEY([roleId])
REFERENCES [dbo].[tblRole] ([roleId])
GO
ALTER TABLE [dbo].[tblAccount]  WITH CHECK ADD FOREIGN KEY([statusId])
REFERENCES [dbo].[tblStatus] ([statusId])
GO
ALTER TABLE [dbo].[tblOrder]  WITH CHECK ADD FOREIGN KEY([discountId])
REFERENCES [dbo].[tblDiscount] ([discountId])
GO
ALTER TABLE [dbo].[tblOrder]  WITH CHECK ADD FOREIGN KEY([username])
REFERENCES [dbo].[tblAccount] ([username])
GO
ALTER TABLE [dbo].[tblOrderDetail]  WITH CHECK ADD FOREIGN KEY([orderId])
REFERENCES [dbo].[tblOrder] ([orderId])
GO
ALTER TABLE [dbo].[tblOrderDetail]  WITH CHECK ADD FOREIGN KEY([roomId])
REFERENCES [dbo].[tblRoom] ([roomId])
GO
ALTER TABLE [dbo].[tblRoom]  WITH CHECK ADD FOREIGN KEY([typeId])
REFERENCES [dbo].[tblRoomType] ([typeId])
GO
USE [master]
GO
ALTER DATABASE [J3LP0003] SET  READ_WRITE 
GO
