INSERT INTO persons(username, password) VALUES ('admin', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb');

INSERT INTO regions(id, name) VALUES (1, 'Hlavní město praha');
INSERT INTO regions(id, name) VALUES (2, 'Středočeský kraj');
INSERT INTO regions(id, name) VALUES (3, 'Jihočeský kraj');
INSERT INTO regions(id, name) VALUES (4, 'Plzeňský kraj');
INSERT INTO regions(id, name) VALUES (5, 'Karlovarský kraj');
INSERT INTO regions(id, name) VALUES (6, 'Ústecký kraj');
INSERT INTO regions(id, name) VALUES (7, 'Liberecký kraj');
INSERT INTO regions(id, name) VALUES (8, 'Královéhradecký kraj');
INSERT INTO regions(id, name) VALUES (9, 'Pardubický kraj');
INSERT INTO regions(id, name) VALUES (10, 'Kraj Vysočina');
INSERT INTO regions(id, name) VALUES (11, 'Jihomoravský kraj');
INSERT INTO regions(id, name) VALUES (12, 'Olomoucký kraj');
INSERT INTO regions(id, name) VALUES (13, 'Moravskoslezský kraj');
INSERT INTO regions(id, name) VALUES (14, 'Zlínský kraj');

INSERT INTO stations(title, name, region_id) VALUES ('Praha hl. n.', 'Praha hlavní nádraží', 1);
INSERT INTO stations(title, name, region_id) VALUES ('Olomouc hl. n.', 'Olomouc hlavní nádraží', 12);
INSERT INTO stations(title, name, region_id) VALUES ('Ostrava hl. n.', 'Ostrava hlavní nádraží', 13);
INSERT INTO stations(title, name, region_id) VALUES ('Ostrava Svinov', 'Ostrava Svinov', 13);
INSERT INTO stations(title, name, region_id) VALUES ('Bohumín', 'Bohumín', 13);
INSERT INTO stations(title, name, region_id) VALUES ('Dětmarovice', 'Dětmarovice', 13);
INSERT INTO stations(title, name, region_id) VALUES ('Karviná hl. n.', 'Karviná hlavní nádraží', 13);
INSERT INTO stations(title, name, region_id) VALUES ('Studénka', 'Studénka', 13);
INSERT INTO stations(title, name, region_id) VALUES ('Přerov', 'Přerov', 12);
INSERT INTO stations(title, name, region_id) VALUES ('Hulín', 'Hulín', 14);
INSERT INTO stations(title, name, region_id) VALUES ('Otrokovice', 'Otrokovice', 14);
INSERT INTO stations(title, name, region_id) VALUES ('Staré Město u Uh. Hr.', 'Staré Město u Uherského Hradiště', 14);
INSERT INTO stations(title, name, region_id) VALUES ('Suchdol nad Odrou', 'Suchdol nad Odrou', 13);
INSERT INTO stations(title, name, region_id) VALUES ('Pardubice', 'Pardubice', 9);
INSERT INTO stations(title, name, region_id) VALUES ('Ostrava střed', 'Ostava střed', 13);
