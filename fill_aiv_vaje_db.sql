DROP TABLE IF EXISTS regija;
DROP TABLE IF EXISTS dnevni_podatek;

CREATE TABLE regija (
	regija_id SERIAL PRIMARY KEY,
	naziv VARCHAR (90) UNIQUE NOT NULL,
	ime_skrbnika VARCHAR (90) NOT NULL,
	priimek_skrbnika VARCHAR (90) NOT NULL,
	email_skrbnika VARCHAR (90) UNIQUE NOT NULL,
	st_prebivalcev INT NOT NULL
);

CREATE TABLE dnevni_podatek (
	dnevni_podatek_id SERIAL PRIMARY KEY,
	datum TIMESTAMP NOT NULL,
	hospitalizirani VARCHAR (90) NOT NULL,
	testirani VARCHAR (90) NOT NULL,
	okuženi VARCHAR (90) NOT NULL,
	st_prebivalcev INT NOT NULL,
	regija_id INT NOT NULL,
	CONSTRAINT fk_regija
   		FOREIGN KEY(regija_id) 
      		REFERENCES regija(regija_id)
);


INSERT INTO regija(naziv, ime_skrbnika, priimek_skrbnika, email_skrbnika, st_prebivalcev)
VALUES 	('Gorenjska','Nina', 'Cvetko', 'nc@email.com', 30000),
		('Podravska','Eva', 'Leben', 'el@email.com', 40000),
		('Savinjska','Hana', 'Kljun', 'hk@email.com', 50000);
		
INSERT INTO dnevni_podatek (regija_id, datum, hospitalizirani, okuzeni, testirani)
VALUES 	(1, '2021-06-22', 2, 2, 2),
		(1, '2021-06-23', 3, 2, 3),
		(1, '2021-06-21', 4, 4, 4),
		(1, '2021-06-20', 2, 2, 2),
		(2, '2021-06-22', 3, 1, 4),
		(2, '2021-06-20', 1, 1, 1),
		(3, '2021-06-23', 4, 6, 4),
		(3, '2021-06-20', 5, 2, 2);
