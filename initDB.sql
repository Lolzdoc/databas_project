set foreign_key_checks = 0;

drop table if exists RawMaterials;
drop table if exists Recipes;
drop table if exists Ingredients;
drop table if exists Pallets;
drop table if exists Orders;
drop table if exists Customers;
drop table if exists RecipesInOrder;

set foreign_key_checks = 1;

-- Create Tables:

CREATE TABLE RawMaterials(
  materialName varchar(40),
  measurementUnit varchar(5),
  currentAmount double default 0,
  lastDelivered Date,
  amountDelivered double,
  primary key(materialName)
);

CREATE TABLE Recipes(
  recipeName varchar(40),
  primary key(recipeName)
);

CREATE TABLE Ingredients(
  materialName varchar(40),
  recipeName varchar(40),
  amount double,
  primary key(materialName, recipeName),
  foreign key(materialName) references RawMaterials(materialName),
  foreign key(recipeName) references Recipes(recipeName)
);  

CREATE TABLE Orders(
  orderId int auto_increment,
  amount int,
  deliveryDate Date,
  primary key(orderId)
);

CREATE TABLE RecipesInOrder(
  recipeName varchar(40),
  orderID int,
  primary key(recipeName, orderID),
  foreign key(recipeName) references Recipes(recipeName),
  foreign key(orderId) references Orders(orderID)
);

CREATE TABLE Customers(
  customerID int auto_increment,
  name varchar(40),
  adress varchar(40),
  primary key(customerID)
);

CREATE TABLE Pallets(
  palletID int auto_increment,
  customerID int,
  recipeName varchar(40),
  location varchar(40),
  timestampBaking Date,
  blockForDelivery boolean,
  timestampDelivery Date,
  primary key(palletID),
  foreign key(recipeName) references Recipes(recipeName),
  foreign key(customerID) references Customers(customerID)
);

-- Insert data

-- Customers
INSERT INTO Customers(name, adress)
VALUES ('Finkakor AB','Helsingborg');

INSERT INTO Customers(name, adress)
VALUES ('Småbröd AB', 'Malmö');

INSERT INTO Customers(name, adress)
VALUES ('Kaffebröd AB', 'Landskrona');

INSERT INTO Customers(name, adress)
VALUES ('Bjudkakor AB', 'Ystad');

INSERT INTO Customers(name, adress)
VALUES ('Kalasbröd AB', 'Trelleborg');

INSERT INTO Customers(name, adress)
VALUES ('Partykakor AB', 'Kristianstad');

INSERT INTO Customers(name, adress)
VALUES('Gästkakor AB', 'Hässleholm');

INSERT INTO Customers(name, adress)
VALUES ('Skånekakor AB', 'Perstorp');

-- Recipes
INSERT INTO Recipes(recipeName)
VALUES ('Nut ring');

INSERT INTO Recipes(recipeName)
VALUES ('Nut cookie');

INSERT INTO Recipes(recipeName)
VALUES ('Amneris');

INSERT INTO Recipes(recipeName)
VALUES ('Tango');

INSERT INTO Recipes(recipeName)
VALUES ('Almond Delight');

INSERT INTO Recipes(recipeName)
VALUES ('Berliner');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Flour','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Butter','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Icing sugar','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Roated,chopped nuts','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('fine-ground nuts','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Ground, roasted nuts','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Bread crumbs','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Sugar','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Egg whites','dl');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Chocolate','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Marzipan','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Eggs','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Potato starch','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Wheat flour','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Sodium bicarbonate','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Vanilla','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Chopped almonds','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Cinnamon','g');

INSERT INTO RawMaterials(materialName, measurementUnit)
VALUES ('Vanilla sugar','g');

-- Ingredients

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Flour','Nut ring', 450);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Butter','Nut ring', 450);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Icing sugar','Nut ring', 190);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Roasted, chopped nuts','Nut ring', 225);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Fine-ground nuts','Nut cookie', 750);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Ground, roasted nuts','Nut cookie', 625);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Bread crumbs','Nut cookie', 125);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Sugar','Nut cookie', 375);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Egg whites','Nut cookie', 3.5);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Chocolate','Nut cookie', 50);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Marzipan','Amneris', 750);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Butter','Amneris', 250);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Eggs','Amneris', 250);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Potato starch','Amneris', 25);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Wheat flour','Amneris', 25);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Butter','Tango', 200);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Sugar','Tango', 250);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Flour','Tango', 300);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Sodium bicarbonate','Tango', 4);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Vanilla','Tango', 2);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Butter','Almond delight', 400);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Sugar','Almond delight', 270);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Chopped almonds','Almond delight', 279);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Flour','Almond delight', 400);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Cinnamon','Almond delight', 10);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Flour','Berliner', 350);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Butter','Berliner', 250);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Icing sugar','Berliner', 100);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Eggs','Berliner', 50);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Vanilla sugar','Berliner', 5);

INSERT INTO Ingredients(materialName, recipeName, amount)
VALUES ('Chocolate','Berliner', 50);


