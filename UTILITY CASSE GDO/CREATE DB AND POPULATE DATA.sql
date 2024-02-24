create table type_of_measures(
 tom_id serial PRIMARY KEY,
 tom_description varchar(100) UNIQUE
);


create table departments(
 department_id serial PRIMARY KEY,
 department_description varchar(100) UNIQUE
);

create table products(
	pro_id serial PRIMARY KEY,
	pro_description varchar(100) NOT NULL,
	pro_weight integer NOT NULL,
	pro_type_of_measure_id integer,
	pro_department_id integer,
	CONSTRAINT fk_type_of_measure_product
		FOREIGN KEY (pro_type_of_measure_id) 
			REFERENCES type_of_measures(tom_id) 
				ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT fk_department_product 
		FOREIGN KEY (pro_department_id) 
			REFERENCES departments(department_id) 
				ON UPDATE CASCADE ON DELETE SET NULL
);


create table barcodes(
	barcode_id serial PRIMARY KEY,
	barcode_code varchar(50) NOT NULL UNIQUE,
	barcode_start_validity Date NOT NULL,
	barcode_end_validity Date NOT NULL,
	barcode_valid boolean,
	barcode_pro_id integer,
	CONSTRAINT fk_product_barcode 
		FOREIGN KEY (barcode_pro_id) 
			REFERENCES products(pro_id) 
				ON UPDATE CASCADE ON DELETE SET NULL
);

create table prices(
	price_id serial PRIMARY KEY,
	price_value real,
	price_product_id integer UNIQUE,
	CONSTRAINT fk_barcode_prices 
		FOREIGN KEY (price_product_id) 
			REFERENCES products(pro_id) 
				ON UPDATE CASCADE ON DELETE CASCADE
);

create table stocks(
	stock_id serial PRIMARY KEY,
	stock_num integer,
	stock_date Date,
	stock_pro_id integer,
	CONSTRAINT fk_product_stock 
		FOREIGN KEY (stock_pro_id) 
			REFERENCES products(pro_id) 
				ON UPDATE CASCADE ON DELETE SET NULL
);

create table receipts(
	receipt_id serial PRIMARY KEY,
	receipt_date date NOT NULL,
	receipt_total real
);

create table sells(
	sell_id serial PRIMARY KEY,
	sell_date date NOT NULL,
	sell_price real NOT NULL,
	sell_quantity integer,
	sell_description varchar(100),
	sell_pro_id integer,
	sell_receipt_id integer,
	sell_department_id integer,
	CONSTRAINT fk_product_sell 
		FOREIGN KEY (sell_pro_id) 
			REFERENCES products(pro_id) 
				ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT fk_receipit_sell 
		FOREIGN KEY (sell_receipt_id) 
			REFERENCES receipts(receipt_id) 
				ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT fk_department_sell 
		FOREIGN KEY (sell_department_id) 
			REFERENCES departments(department_id) 
				ON UPDATE CASCADE ON DELETE SET NULL
	
);

INSERT INTO public.type_of_measures (tom_description) VALUES('g'),('kg'),('pz');
INSERT INTO public.departments (department_description) VALUES('Casa'),('Ortofrutta'),('Salumeria'),('Pescheria'),('Giardino');

INSERT INTO public.products(pro_description, pro_weight, pro_type_of_measure_id, pro_department_id) VALUES 
('Mensola',2,2,1),
('Tavolo',40,2,1),
('Sedia',20,2,1),
('Mela',1,2,2),
('Carota',1,2,2),
('Innaffiatoio',1,3,5),
('Paletto Legno',1,3,5),
('Mortadella',1,2,3),
('Speck',1,2,3),
('Orata',1,1,4),
('Cozze',1,1,4),
('Spigola',1,1,4);

INSERT INTO public.barcodes(barcode_code, barcode_start_validity, barcode_end_validity, barcode_valid, barcode_pro_id) VALUES 
('12345','2024-01-01','2024-12-31',true,1),
('12345-Bis','2025-01-01','2025-12-31',false,1),
('12345-0','2024-01-01','2024-12-31',true,2),
('12345-0bis','2024-01-01','2024-12-31',true,2),
('12345-1','2024-01-01','2024-12-31',true,3),
('12345-2','2024-01-01','2024-12-31',true,4),
('12345-3','2024-01-01','2024-12-31',true,5),
('12345-3-bis','2024-01-01','2024-12-31',true,5),
('12345-4','2024-01-01','2024-12-31',true,5),
('12345-5','2024-01-01','2024-12-31',true,6),
('12345-6','2024-01-01','2024-12-31',true,7),
('12345-6-bis','2024-01-01','2024-12-31',true,7),
('12345-7','2024-01-01','2024-12-31',true,8),
('12345-8','2024-01-01','2024-12-31',true,9),
('12345-9','2024-01-01','2024-12-31',true,10),
('12345-10','2024-01-01','2024-12-31',true,11),
('12345-10-bis','2024-01-01','2024-12-31',true,11),
('12345-11','2024-01-01','2024-12-31',true,12),
('12345-11-bis','2024-01-01','2024-12-31',true,12);

INSERT INTO public.prices( price_value, price_product_id) VALUES 
(50.5,2),
(150.5,1),
(15.5,3),
(1.5,4),
(1.5,5),
(4.5,6),
(6,7),
(16,8),
(14.68,9),
(20,10),
(9,11),
(14.6,12);



INSERT INTO public.stocks( stock_num, stock_date, stock_pro_id)	VALUES 
(20,'2024-02-23',8),
(20,'2024-02-23',9),
(10,'2024-02-23',10),
(10,'2024-02-23',11),
(10,'2024-02-23',12),
(90,'2024-02-23',1),
(80,'2024-02-23',2),
(180,'2024-02-23',3),
(100,'2024-02-23',4),
(80,'2024-02-23',5),
(50,'2024-02-23',6),
(50,'2024-02-23',7);