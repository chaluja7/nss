/*Users*/
INSERT INTO persons(username, password) VALUES ('admin', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb');

/*regions*/
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
ALTER SEQUENCE regions_id_seq RESTART WITH 15;

/*stations*/
INSERT INTO stations(id, title, name, region_id) VALUES (1, 'Praha hl. n.', 'Praha hlavní nádraží', 1);
INSERT INTO stations(id, title, name, region_id) VALUES (2, 'Olomouc hl. n.', 'Olomouc hlavní nádraží', 12);
INSERT INTO stations(id, title, name, region_id) VALUES (3, 'Ostrava hl. n.', 'Ostrava hlavní nádraží', 13);
INSERT INTO stations(id, title, name, region_id) VALUES (4, 'Ostrava Svinov', 'Ostrava Svinov', 13);
INSERT INTO stations(id, title, name, region_id) VALUES (5, 'Bohumín', 'Bohumín', 13);
INSERT INTO stations(id, title, name, region_id) VALUES (6, 'Dětmarovice', 'Dětmarovice', 13);
INSERT INTO stations(id, title, name, region_id) VALUES (7, 'Karviná hl. n.', 'Karviná hlavní nádraží', 13);
INSERT INTO stations(id, title, name, region_id) VALUES (8, 'Studénka', 'Studénka', 13);
INSERT INTO stations(id, title, name, region_id) VALUES (9, 'Přerov', 'Přerov', 12);
INSERT INTO stations(id, title, name, region_id) VALUES (10, 'Hulín', 'Hulín', 14);
INSERT INTO stations(id, title, name, region_id) VALUES (11, 'Otrokovice', 'Otrokovice', 14);
INSERT INTO stations(id, title, name, region_id) VALUES (12, 'Staré Město u Uh. Hr.', 'Staré Město u Uherského Hradiště', 14);
INSERT INTO stations(id, title, name, region_id) VALUES (13, 'Suchdol nad Odrou', 'Suchdol nad Odrou', 13);
INSERT INTO stations(id, title, name, region_id) VALUES (14, 'Pardubice', 'Pardubice', 9);
INSERT INTO stations(id, title, name, region_id) VALUES (15, 'Ostrava střed', 'Ostava střed', 13);
ALTER SEQUENCE stations_id_seq RESTART WITH 16;

/*routes*/
INSERT INTO routes(id, name) VALUES (1, 'Praha - Bohumín');
INSERT INTO routes(id, name) VALUES (2, 'Bohumín - Praha');
INSERT INTO routes(id, name) VALUES (3, 'Praha - Karviná');
INSERT INTO routes(id, name) VALUES (4, 'Karviná - Praha');
INSERT INTO routes(id, name) VALUES (5, 'Praha - Staré Město u Uh. Hr.');
INSERT INTO routes(id, name) VALUES (6, 'Staré Město u Uh. Hr. - Praha');
ALTER SEQUENCE routes_id_seq RESTART WITH 7;


/*route stops*/
/*route1*/
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (1, 0, 1, 1);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (2, 250, 1, 2);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (3, 272, 1, 9);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (4, 322, 1, 13);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (5, 334, 1, 8);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (6, 351, 1, 4);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (7, 356, 1, 3);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (8, 364, 1, 5);
/*route2*/
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (9, 0, 2, 5);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (10, 8, 2, 3);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (11, 13, 2, 4);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (12, 30, 2, 8);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (13, 42, 2, 13);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (14, 92, 2, 9);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (15, 114, 2, 2);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (16, 364, 2, 1);
/*route3*/
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (17, 0, 3, 1);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (18, 250, 3, 2);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (19, 272, 3, 9);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (20, 322, 3, 13);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (21, 334, 3, 8);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (22, 351, 3, 4);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (23, 356, 3, 3);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (24, 364, 3, 5);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (25, 372, 3, 6);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (26, 379, 3, 7);
/*route4*/
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (27, 0, 4, 7);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (28, 7, 4, 6);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (29, 15, 4, 5);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (30, 23, 4, 3);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (31, 28, 4, 4);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (32, 45, 4, 8);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (33, 57, 4, 13);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (34, 107, 4, 9);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (35, 129, 4, 2);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (36, 379, 4, 1);
/*route5*/
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (37, 0, 5, 1);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (38, 250, 5, 2);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (39, 272, 5, 9);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (40, 287, 5, 10);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (41, 300, 5, 11);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (42, 318, 5, 12);
/*route6*/
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (43, 0, 6, 12);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (44, 18, 6, 11);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (45, 31, 6, 10);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (46, 46, 6, 9);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (47, 68, 6, 2);
INSERT INTO route_stops(id, distance, route_id, station_id) VALUES (48, 318, 6, 1);

ALTER SEQUENCE route_stops_id_seq RESTART WITH 49;

/*lines*/
/*line 1*/
INSERT INTO lines(id, linetype, name, route_id) VALUES (1, 'TRAIN', 'SP 1351', 1);
INSERT INTO lines(id, linetype, name, route_id) VALUES (2, 'TRAIN', 'SP 1353', 1);
INSERT INTO lines(id, linetype, name, route_id) VALUES (3, 'TRAIN', 'SP 1355', 1);
INSERT INTO lines(id, linetype, name, route_id) VALUES (4, 'TRAIN', 'SP 1357', 1);
INSERT INTO lines(id, linetype, name, route_id) VALUES (5, 'TRAIN', 'SP 1359', 1);
INSERT INTO lines(id, linetype, name, route_id) VALUES (6, 'TRAIN', 'SP 1367', 1);
/*line 2*/
INSERT INTO lines(id, linetype, name, route_id) VALUES (7, 'TRAIN', 'SP 1361', 3);
INSERT INTO lines(id, linetype, name, route_id) VALUES (8, 'TRAIN', 'SP 1365', 3);
/*line 3*/
INSERT INTO lines(id, linetype, name, route_id) VALUES (9, 'TRAIN', 'SP 1363', 5);
/*line 4*/
INSERT INTO lines(id, linetype, name, route_id) VALUES (10, 'TRAIN', 'SP 1356', 2);
INSERT INTO lines(id, linetype, name, route_id) VALUES (11, 'TRAIN', 'SP 1360', 2);
INSERT INTO lines(id, linetype, name, route_id) VALUES (12, 'TRAIN', 'SP 1362', 2);
INSERT INTO lines(id, linetype, name, route_id) VALUES (13, 'TRAIN', 'SP 1364', 2);
INSERT INTO lines(id, linetype, name, route_id) VALUES (14, 'TRAIN', 'SP 1366', 2);
/*line 5*/
INSERT INTO lines(id, linetype, name, route_id) VALUES (15, 'TRAIN', 'SP 1352', 4);
INSERT INTO lines(id, linetype, name, route_id) VALUES (16, 'TRAIN', 'SP 1354', 4);
INSERT INTO lines(id, linetype, name, route_id) VALUES (17, 'TRAIN', 'SP 1358', 4);
/*line 6*/
INSERT INTO lines(id, linetype, name, route_id) VALUES (18, 'TRAIN', 'SP 1350', 6);

ALTER SEQUENCE lines_id_seq RESTART WITH 19;

/*rides and stops*/
INSERT INTO rides(id, line_id) VALUES (1, 1);
INSERT INTO rides(id, line_id) VALUES (2, 1);
INSERT INTO rides(id, line_id) VALUES (3, 1);
INSERT INTO rides(id, line_id) VALUES (4, 1);
INSERT INTO rides(id, line_id) VALUES (5, 1);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (1, 1, null, '2014-12-08 05:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (1, 2, '2014-12-08 07:19:00', '2014-12-08 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (1, 9, '2014-12-08 07:35:00', '2014-12-08 07:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (1, 13, '2014-12-08 08:05:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (2, 1, null, '2014-12-09 05:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (2, 2, '2014-12-09 07:19:00', '2014-12-09 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (2, 9, '2014-12-09 07:35:00', '2014-12-09 07:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (2, 13, '2014-12-09 08:05:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (3, 1, null, '2014-12-10 05:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (3, 2, '2014-12-10 07:19:00', '2014-12-10 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (3, 9, '2014-12-10 07:35:00', '2014-12-10 07:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (3, 13, '2014-12-10 08:05:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (4, 1, null, '2014-12-11 05:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (4, 2, '2014-12-11 07:19:00', '2014-12-11 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (4, 9, '2014-12-11 07:35:00', '2014-12-11 07:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (4, 13, '2014-12-11 08:05:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (5, 1, null, '2014-12-12 05:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (5, 2, '2014-12-12 07:19:00', '2014-12-12 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (5, 9, '2014-12-12 07:35:00', '2014-12-12 07:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (5, 13, '2014-12-12 08:05:00', null);

INSERT INTO rides(id, line_id) VALUES (6, 2);
INSERT INTO rides(id, line_id) VALUES (7, 2);
INSERT INTO rides(id, line_id) VALUES (8, 2);
INSERT INTO rides(id, line_id) VALUES (9, 2);
INSERT INTO rides(id, line_id) VALUES (10, 2);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (6, 13, null, '2014-12-08 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (6, 8, '2014-12-08 10:12:00', '2014-12-08 10:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (6, 4, '2014-12-08 10:22:00', '2014-12-08 10:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (6, 3, '2014-12-08 10:31:00', '2014-12-08 10:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (6, 5, '2014-12-08 10:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (7, 13, null, '2014-12-09 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (7, 8, '2014-12-09 10:12:00', '2014-12-09 10:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (7, 4, '2014-12-09 10:22:00', '2014-12-09 10:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (7, 3, '2014-12-09 10:31:00', '2014-12-09 10:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (7, 5, '2014-12-09 10:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (8, 13, null, '2014-12-10 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (8, 8, '2014-12-10 10:12:00', '2014-12-10 10:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (8, 4, '2014-12-10 10:22:00', '2014-12-10 10:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (8, 3, '2014-12-10 10:31:00', '2014-12-10 10:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (8, 5, '2014-12-10 10:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (9, 13, null, '2014-12-11 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (9, 8, '2014-12-11 10:12:00', '2014-12-11 10:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (9, 4, '2014-12-11 10:22:00', '2014-12-11 10:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (9, 3, '2014-12-11 10:31:00', '2014-12-11 10:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (9, 5, '2014-12-11 10:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (10, 13, null, '2014-12-12 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (10, 8, '2014-12-12 10:12:00', '2014-12-12 10:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (10, 4, '2014-12-12 10:22:00', '2014-12-12 10:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (10, 3, '2014-12-12 10:31:00', '2014-12-12 10:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (10, 5, '2014-12-12 10:39:00', null);

INSERT INTO rides(id, line_id) VALUES (11, 3);
INSERT INTO rides(id, line_id) VALUES (12, 3);
INSERT INTO rides(id, line_id) VALUES (13, 3);
INSERT INTO rides(id, line_id) VALUES (14, 3);
INSERT INTO rides(id, line_id) VALUES (15, 3);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (11, 1, null, '2014-12-08 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (11, 2, '2014-12-08 12:19:00', '2014-12-08 12:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (11, 9, '2014-12-08 12:35:00', '2014-12-08 12:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (11, 13, '2014-12-08 13:05:00', '2014-12-08 13:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (11, 8, '2014-12-08 13:12:00', '2014-12-08 13:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (11, 4, '2014-12-08 13:22:00', '2014-12-08 13:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (11, 3, '2014-12-08 13:31:00', '2014-12-08 13:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (11, 5, '2014-12-08 13:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (12, 1, null, '2014-12-09 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (12, 2, '2014-12-09 12:19:00', '2014-12-09 12:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (12, 9, '2014-12-09 12:35:00', '2014-12-09 12:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (12, 13, '2014-12-09 13:05:00', '2014-12-09 13:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (12, 8, '2014-12-09 13:12:00', '2014-12-09 13:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (12, 4, '2014-12-09 13:22:00', '2014-12-09 13:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (12, 3, '2014-12-09 13:31:00', '2014-12-09 13:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (12, 5, '2014-12-09 13:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (13, 1, null, '2014-12-10 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (13, 2, '2014-12-10 12:19:00', '2014-12-10 12:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (13, 9, '2014-12-10 12:35:00', '2014-12-10 12:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (13, 13, '2014-12-10 13:05:00', '2014-12-10 13:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (13, 8, '2014-12-10 13:12:00', '2014-12-10 13:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (13, 4, '2014-12-10 13:22:00', '2014-12-10 13:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (13, 3, '2014-12-10 13:31:00', '2014-12-10 13:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (13, 5, '2014-12-10 13:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (14, 1, null, '2014-12-11 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (14, 2, '2014-12-11 12:19:00', '2014-12-11 12:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (14, 9, '2014-12-11 12:35:00', '2014-12-11 12:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (14, 13, '2014-12-11 13:05:00', '2014-12-11 13:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (14, 8, '2014-12-11 13:12:00', '2014-12-11 13:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (14, 4, '2014-12-11 13:22:00', '2014-12-11 13:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (14, 3, '2014-12-11 13:31:00', '2014-12-11 13:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (14, 5, '2014-12-11 13:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (15, 1, null, '2014-12-12 10:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (15, 2, '2014-12-12 12:19:00', '2014-12-12 12:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (15, 9, '2014-12-12 12:35:00', '2014-12-12 12:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (15, 13, '2014-12-12 13:05:00', '2014-12-12 13:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (15, 8, '2014-12-12 13:12:00', '2014-12-12 13:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (15, 4, '2014-12-12 13:22:00', '2014-12-12 13:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (15, 3, '2014-12-12 13:31:00', '2014-12-12 13:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (15, 5, '2014-12-12 13:39:00', null);

INSERT INTO rides(id, line_id) VALUES (16, 4);
INSERT INTO rides(id, line_id) VALUES (17, 4);
INSERT INTO rides(id, line_id) VALUES (18, 4);
INSERT INTO rides(id, line_id) VALUES (19, 4);
INSERT INTO rides(id, line_id) VALUES (20, 4);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (16, 1, null, '2014-12-08 12:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (16, 2, '2014-12-08 14:19:00', '2014-12-08 14:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (16, 9, '2014-12-08 14:35:00', '2014-12-08 14:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (16, 13, '2014-12-08 15:05:00', '2014-12-08 15:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (16, 8, '2014-12-08 15:12:00', '2014-12-08 15:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (16, 4, '2014-12-08 15:22:00', '2014-12-08 15:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (16, 3, '2014-12-08 15:31:00', '2014-12-08 15:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (16, 5, '2014-12-08 15:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (17, 1, null, '2014-12-09 12:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (17, 2, '2014-12-09 14:19:00', '2014-12-09 14:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (17, 9, '2014-12-09 14:35:00', '2014-12-09 14:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (17, 13, '2014-12-09 15:05:00', '2014-12-09 15:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (17, 8, '2014-12-09 15:12:00', '2014-12-09 15:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (17, 4, '2014-12-09 15:22:00', '2014-12-09 15:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (17, 3, '2014-12-09 15:31:00', '2014-12-09 15:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (17, 5, '2014-12-09 15:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (18, 1, null, '2014-12-10 12:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (18, 2, '2014-12-10 14:19:00', '2014-12-10 14:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (18, 9, '2014-12-10 14:35:00', '2014-12-10 14:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (18, 13, '2014-12-10 15:05:00', '2014-12-10 15:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (18, 8, '2014-12-10 15:12:00', '2014-12-10 15:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (18, 4, '2014-12-10 15:22:00', '2014-12-10 15:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (18, 3, '2014-12-10 15:31:00', '2014-12-10 15:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (18, 5, '2014-12-10 15:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (19, 1, null, '2014-12-11 12:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (19, 2, '2014-12-11 14:19:00', '2014-12-11 14:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (19, 9, '2014-12-11 14:35:00', '2014-12-11 14:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (19, 13, '2014-12-11 15:05:00', '2014-12-11 15:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (19, 8, '2014-12-11 15:12:00', '2014-12-11 15:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (19, 4, '2014-12-11 15:22:00', '2014-12-11 15:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (19, 3, '2014-12-11 15:31:00', '2014-12-11 15:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (19, 5, '2014-12-11 15:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (20, 1, null, '2014-12-12 12:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (20, 2, '2014-12-12 14:19:00', '2014-12-12 14:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (20, 9, '2014-12-12 14:35:00', '2014-12-12 14:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (20, 13, '2014-12-12 15:05:00', '2014-12-12 15:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (20, 8, '2014-12-12 15:12:00', '2014-12-12 15:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (20, 4, '2014-12-12 15:22:00', '2014-12-12 15:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (20, 3, '2014-12-12 15:31:00', '2014-12-12 15:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (20, 5, '2014-12-12 15:39:00', null);

INSERT INTO rides(id, line_id) VALUES (21, 5);
INSERT INTO rides(id, line_id) VALUES (22, 5);
INSERT INTO rides(id, line_id) VALUES (23, 5);
INSERT INTO rides(id, line_id) VALUES (24, 5);
INSERT INTO rides(id, line_id) VALUES (25, 5);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (21, 1, null, '2014-12-08 14:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (21, 2, '2014-12-08 16:19:00', '2014-12-08 16:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (21, 9, '2014-12-08 16:35:00', '2014-12-08 16:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (21, 13, '2014-12-08 17:05:00', '2014-12-08 17:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (21, 8, '2014-12-08 17:12:00', '2014-12-08 17:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (21, 4, '2014-12-08 17:22:00', '2014-12-08 17:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (21, 3, '2014-12-08 17:31:00', '2014-12-08 17:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (21, 5, '2014-12-08 17:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (22, 1, null, '2014-12-09 14:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (22, 2, '2014-12-09 16:19:00', '2014-12-09 16:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (22, 9, '2014-12-09 16:35:00', '2014-12-09 16:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (22, 13, '2014-12-09 17:05:00', '2014-12-09 17:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (22, 8, '2014-12-09 17:12:00', '2014-12-09 17:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (22, 4, '2014-12-09 17:22:00', '2014-12-09 17:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (22, 3, '2014-12-09 17:31:00', '2014-12-09 17:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (22, 5, '2014-12-09 17:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (23, 1, null, '2014-12-10 14:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (23, 2, '2014-12-10 16:19:00', '2014-12-10 16:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (23, 9, '2014-12-10 16:35:00', '2014-12-10 16:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (23, 13, '2014-12-10 17:05:00', '2014-12-10 17:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (23, 8, '2014-12-10 17:12:00', '2014-12-10 17:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (23, 4, '2014-12-10 17:22:00', '2014-12-10 17:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (23, 3, '2014-12-10 17:31:00', '2014-12-10 17:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (23, 5, '2014-12-10 17:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (24, 1, null, '2014-12-11 14:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (24, 2, '2014-12-11 16:19:00', '2014-12-11 16:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (24, 9, '2014-12-11 16:35:00', '2014-12-11 16:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (24, 13, '2014-12-11 17:05:00', '2014-12-11 17:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (24, 8, '2014-12-11 17:12:00', '2014-12-11 17:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (24, 4, '2014-12-11 17:22:00', '2014-12-11 17:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (24, 3, '2014-12-11 17:31:00', '2014-12-11 17:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (24, 5, '2014-12-11 17:39:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (25, 1, null, '2014-12-12 14:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (25, 2, '2014-12-12 16:19:00', '2014-12-12 16:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (25, 9, '2014-12-12 16:35:00', '2014-12-12 16:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (25, 13, '2014-12-12 17:05:00', '2014-12-12 17:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (25, 8, '2014-12-12 17:12:00', '2014-12-12 17:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (25, 4, '2014-12-12 17:22:00', '2014-12-12 17:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (25, 3, '2014-12-12 17:31:00', '2014-12-12 17:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (25, 5, '2014-12-12 17:39:00', null);

INSERT INTO rides(id, line_id) VALUES (26, 6);
INSERT INTO rides(id, line_id) VALUES (27, 6);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (26, 1, null, '2014-12-13 20:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (26, 2, '2014-12-13 22:51:00', '2014-12-13 22:53:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (26, 9, '2014-12-13 23:06:00', '2014-12-13 23:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (26, 13, '2014-12-13 23:36:00', '2014-12-13 23:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (26, 8, '2014-12-13 23:43:00', '2014-12-13 23:44:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (26, 4, '2014-12-13 23:53:00', '2014-12-13 23:55:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (26, 3, '2014-12-14 00:02:00', '2014-12-14 00:04:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (26, 5, '2014-12-14 00:10:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (27,  1, null, '2014-12-14  20:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (27,  2, '2014-12-14  22:51:00', '2014-12-14  22:53:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (27,  9, '2014-12-14  23:06:00', '2014-12-14  23:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (27,  13, '2014-12-14  23:36:00', '2014-12-14  23:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (27,  8, '2014-12-14  23:43:00', '2014-12-14  23:44:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (27,  4, '2014-12-14  23:53:00', '2014-12-14  23:55:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (27,  3, '2014-12-15  00:02:00', '2014-12-15  00:04:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (27,  5, '2014-12-15  00:10:00', null);

INSERT INTO rides(id, line_id) VALUES (28, 7);
INSERT INTO rides(id, line_id) VALUES (29, 7);
INSERT INTO rides(id, line_id) VALUES (30, 7);
INSERT INTO rides(id, line_id) VALUES (31, 7);
INSERT INTO rides(id, line_id) VALUES (32, 7);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 1, null, '2014-12-08 16:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 2, '2014-12-08 18:19:00', '2014-12-08 18:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 9, '2014-12-08 18:35:00', '2014-12-08 18:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 13, '2014-12-08 19:05:00', '2014-12-08 19:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 8, '2014-12-08 19:12:00', '2014-12-08 19:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 4, '2014-12-08 19:22:00', '2014-12-08 19:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 3, '2014-12-08 19:31:00', '2014-12-08 19:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 5, '2014-12-08 19:39:00', '2014-12-08 19:42:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 6, '2014-12-08 19:49:00', '2014-12-08 19:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (28, 7, '2014-12-08 19:59:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 1, null, '2014-12-09 16:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 2, '2014-12-09 18:19:00', '2014-12-09 18:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 9, '2014-12-09 18:35:00', '2014-12-09 18:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 13, '2014-12-09 19:05:00', '2014-12-09 19:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 8, '2014-12-09 19:12:00', '2014-12-09 19:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 4, '2014-12-09 19:22:00', '2014-12-09 19:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 3, '2014-12-09 19:31:00', '2014-12-09 19:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 5, '2014-12-09 19:39:00', '2014-12-09 19:42:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 6, '2014-12-09 19:49:00', '2014-12-09 19:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (29, 7, '2014-12-09 19:59:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 1, null, '2014-12-10 16:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 2, '2014-12-10 18:19:00', '2014-12-10 18:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 9, '2014-12-10 18:35:00', '2014-12-10 18:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 13, '2014-12-10 19:05:00', '2014-12-10 19:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 8, '2014-12-10 19:12:00', '2014-12-10 19:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 4, '2014-12-10 19:22:00', '2014-12-10 19:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 3, '2014-12-10 19:31:00', '2014-12-10 19:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 5, '2014-12-10 19:39:00', '2014-12-10 19:42:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 6, '2014-12-10 19:49:00', '2014-12-10 19:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (30, 7, '2014-12-10 19:59:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 1, null, '2014-12-11 16:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 2, '2014-12-11 18:19:00', '2014-12-11 18:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 9, '2014-12-11 18:35:00', '2014-12-11 18:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 13, '2014-12-11 19:05:00', '2014-12-11 19:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 8, '2014-12-11 19:12:00', '2014-12-11 19:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 4, '2014-12-11 19:22:00', '2014-12-11 19:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 3, '2014-12-11 19:31:00', '2014-12-11 19:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 5, '2014-12-11 19:39:00', '2014-12-11 19:42:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 6, '2014-12-11 19:49:00', '2014-12-11 19:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (31, 7, '2014-12-11 19:59:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 1, null, '2014-12-12 16:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 2, '2014-12-12 18:19:00', '2014-12-12 18:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 9, '2014-12-12 18:35:00', '2014-12-12 18:39:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 13, '2014-12-12 19:05:00', '2014-12-12 19:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 8, '2014-12-12 19:12:00', '2014-12-12 19:13:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 4, '2014-12-12 19:22:00', '2014-12-12 19:24:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 3, '2014-12-12 19:31:00', '2014-12-12 19:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 5, '2014-12-12 19:39:00', '2014-12-12 19:42:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 6, '2014-12-12 19:49:00', '2014-12-12 19:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (32, 7, '2014-12-12 19:59:00', null);

INSERT INTO rides(id, line_id) VALUES (33, 8);
INSERT INTO rides(id, line_id) VALUES (34, 8);
INSERT INTO rides(id, line_id) VALUES (35, 8);
INSERT INTO rides(id, line_id) VALUES (36, 8);
INSERT INTO rides(id, line_id) VALUES (37, 8);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 1, null, '2014-12-08 18:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 2, '2014-12-08 20:49:00', '2014-12-08 20:51:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 9, '2014-12-08 21:05:00', '2014-12-08 21:09:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 13, '2014-12-08 21:35:00', '2014-12-08 21:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 8, '2014-12-08 21:42:00', '2014-12-08 21:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 4, '2014-12-08 21:52:00', '2014-12-08 21:54:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 3, '2014-12-08 22:01:00', '2014-12-08 22:03:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 5, '2014-12-08 22:09:00', '2014-12-08 22:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 6, '2014-12-08 22:16:00', '2014-12-08 22:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (33, 7, '2014-12-08 22:26:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 1, null, '2014-12-09 18:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 2, '2014-12-09 20:49:00', '2014-12-09 20:51:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 9, '2014-12-09 21:05:00', '2014-12-09 21:09:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 13, '2014-12-09 21:35:00', '2014-12-09 21:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 8, '2014-12-09 21:42:00', '2014-12-09 21:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 4, '2014-12-09 21:52:00', '2014-12-09 21:54:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 3, '2014-12-09 22:01:00', '2014-12-09 22:03:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 5, '2014-12-09 22:09:00', '2014-12-09 22:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 6, '2014-12-09 22:16:00', '2014-12-09 22:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (34, 7, '2014-12-09 22:26:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 1, null, '2014-12-10 18:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 2, '2014-12-10 20:49:00', '2014-12-10 20:51:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 9, '2014-12-10 21:05:00', '2014-12-10 21:09:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 13, '2014-12-10 21:35:00', '2014-12-10 21:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 8, '2014-12-10 21:42:00', '2014-12-10 21:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 4, '2014-12-10 21:52:00', '2014-12-10 21:54:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 3, '2014-12-10 22:01:00', '2014-12-10 22:03:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 5, '2014-12-10 22:09:00', '2014-12-10 22:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 6, '2014-12-10 22:16:00', '2014-12-10 22:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (35, 7, '2014-12-10 22:26:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 1, null, '2014-12-11 18:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 2, '2014-12-11 20:49:00', '2014-12-11 20:51:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 9, '2014-12-11 21:05:00', '2014-12-11 21:09:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 13, '2014-12-11 21:35:00', '2014-12-11 21:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 8, '2014-12-11 21:42:00', '2014-12-11 21:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 4, '2014-12-11 21:52:00', '2014-12-11 21:54:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 3, '2014-12-11 22:01:00', '2014-12-11 22:03:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 5, '2014-12-11 22:09:00', '2014-12-11 22:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 6, '2014-12-11 22:16:00', '2014-12-11 22:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (36, 7, '2014-12-11 22:26:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 1, null, '2014-12-12 18:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 2, '2014-12-12 20:49:00', '2014-12-12 20:51:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 9, '2014-12-12 21:05:00', '2014-12-12 21:09:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 13, '2014-12-12 21:35:00', '2014-12-12 21:36:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 8, '2014-12-12 21:42:00', '2014-12-12 21:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 4, '2014-12-12 21:52:00', '2014-12-12 21:54:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 3, '2014-12-12 22:01:00', '2014-12-12 22:03:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 5, '2014-12-12 22:09:00', '2014-12-12 22:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 6, '2014-12-12 22:16:00', '2014-12-12 22:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (37, 7, '2014-12-12 22:26:00', null);

INSERT INTO rides(id, line_id) VALUES (38, 9);
INSERT INTO rides(id, line_id) VALUES (39, 9);
INSERT INTO rides(id, line_id) VALUES (40, 9);
INSERT INTO rides(id, line_id) VALUES (41, 9);
INSERT INTO rides(id, line_id) VALUES (42, 9);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (38, 1, null, '2014-12-08 16:28:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (38, 2, '2014-12-08 18:44:00', '2014-12-08 18:46:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (38, 9, '2014-12-08 19:00:00', '2014-12-08 19:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (38, 10, '2014-12-08 19:11:00', '2014-12-08 19:12:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (38, 11, '2014-12-08 19:19:00', '2014-12-08 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (38, 12, '2014-12-08 19:30:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (39, 1, null, '2014-12-09 16:28:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (39, 2, '2014-12-09 18:44:00', '2014-12-09 18:46:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (39, 9, '2014-12-09 19:00:00', '2014-12-09 19:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (39, 10, '2014-12-09 19:11:00', '2014-12-09 19:12:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (39, 11, '2014-12-09 19:19:00', '2014-12-09 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (39, 12, '2014-12-09 19:30:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (40, 1, null, '2014-12-10 16:28:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (40, 2, '2014-12-10 18:44:00', '2014-12-10 18:46:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (40, 9, '2014-12-10 19:00:00', '2014-12-10 19:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (40, 10, '2014-12-10 19:11:00', '2014-12-10 19:12:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (40, 11, '2014-12-10 19:19:00', '2014-12-10 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (40, 12, '2014-12-10 19:30:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (41, 1, null, '2014-12-11 16:28:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (41, 2, '2014-12-11 18:44:00', '2014-12-11 18:46:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (41, 9, '2014-12-11 19:00:00', '2014-12-11 19:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (41, 10, '2014-12-11 19:11:00', '2014-12-11 19:12:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (41, 11, '2014-12-11 19:19:00', '2014-12-11 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (41, 12, '2014-12-11 19:30:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (42, 1, null, '2014-12-12 16:28:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (42, 2, '2014-12-12 18:44:00', '2014-12-12 18:46:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (42, 9, '2014-12-12 19:00:00', '2014-12-12 19:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (42, 10, '2014-12-12 19:11:00', '2014-12-12 19:12:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (42, 11, '2014-12-12 19:19:00', '2014-12-12 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (42, 12, '2014-12-12 19:30:00', null);


/*DO Prahy*/
INSERT INTO rides(id, line_id) VALUES (43, 10);
INSERT INTO rides(id, line_id) VALUES (44, 10);
INSERT INTO rides(id, line_id) VALUES (45, 10);
INSERT INTO rides(id, line_id) VALUES (46, 10);
INSERT INTO rides(id, line_id) VALUES (47, 10);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (43, 5, null, '2014-12-08 09:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (43, 3, '2014-12-08 09:23:00', '2014-12-08 09:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (43, 4, '2014-12-08 09:31:00', '2014-12-08 09:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (43, 8, '2014-12-08 09:42:00', '2014-12-08 09:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (43, 13, '2014-12-08 09:48:00', '2014-12-08 09:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (43, 9, '2014-12-08 10:17:00', '2014-12-08 10:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (43, 2, '2014-12-08 10:35:00', '2014-12-08 10:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (43, 1, '2014-12-08 12:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (44, 5, null, '2014-12-09 09:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (44, 3, '2014-12-09 09:23:00', '2014-12-09 09:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (44, 4, '2014-12-09 09:31:00', '2014-12-09 09:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (44, 8, '2014-12-09 09:42:00', '2014-12-09 09:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (44, 13, '2014-12-09 09:48:00', '2014-12-09 09:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (44, 9, '2014-12-09 10:17:00', '2014-12-09 10:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (44, 2, '2014-12-09 10:35:00', '2014-12-09 10:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (44, 1, '2014-12-09 12:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (45, 5, null, '2014-12-10 09:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (45, 3, '2014-12-10 09:23:00', '2014-12-10 09:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (45, 4, '2014-12-10 09:31:00', '2014-12-10 09:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (45, 8, '2014-12-10 09:42:00', '2014-12-10 09:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (45, 13, '2014-12-10 09:48:00', '2014-12-10 09:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (45, 9, '2014-12-10 10:17:00', '2014-12-10 10:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (45, 2, '2014-12-10 10:35:00', '2014-12-10 10:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (45, 1, '2014-12-10 12:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (46, 5, null, '2014-12-11 09:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (46, 3, '2014-12-11 09:23:00', '2014-12-11 09:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (46, 4, '2014-12-11 09:31:00', '2014-12-11 09:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (46, 8, '2014-12-11 09:42:00', '2014-12-11 09:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (46, 13, '2014-12-11 09:48:00', '2014-12-11 09:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (46, 9, '2014-12-11 10:17:00', '2014-12-11 10:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (46, 2, '2014-12-11 10:35:00', '2014-12-11 10:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (46, 1, '2014-12-11 12:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (47, 5, null, '2014-12-12 09:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (47, 3, '2014-12-12 09:23:00', '2014-12-12 09:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (47, 4, '2014-12-12 09:31:00', '2014-12-12 09:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (47, 8, '2014-12-12 09:42:00', '2014-12-12 09:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (47, 13, '2014-12-12 09:48:00', '2014-12-12 09:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (47, 9, '2014-12-12 10:17:00', '2014-12-12 10:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (47, 2, '2014-12-12 10:35:00', '2014-12-12 10:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (47, 1, '2014-12-12 12:56:00', null);

INSERT INTO rides(id, line_id) VALUES (48, 11);
INSERT INTO rides(id, line_id) VALUES (49, 11);
INSERT INTO rides(id, line_id) VALUES (50, 11);
INSERT INTO rides(id, line_id) VALUES (51, 11);
INSERT INTO rides(id, line_id) VALUES (52, 11);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (48, 5, null, '2014-12-08 12:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (48, 3, '2014-12-08 12:23:00', '2014-12-08 12:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (48, 4, '2014-12-08 12:31:00', '2014-12-08 12:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (48, 8, '2014-12-08 12:42:00', '2014-12-08 12:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (48, 13, '2014-12-08 12:48:00', '2014-12-08 12:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (48, 9, '2014-12-08 13:17:00', '2014-12-08 13:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (48, 2, '2014-12-08 13:35:00', '2014-12-08 13:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (48, 1, '2014-12-08 15:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (49, 5, null, '2014-12-09 12:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (49, 3, '2014-12-09 12:23:00', '2014-12-09 12:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (49, 4, '2014-12-09 12:31:00', '2014-12-09 12:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (49, 8, '2014-12-09 12:42:00', '2014-12-09 12:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (49, 13, '2014-12-09 12:48:00', '2014-12-09 12:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (49, 9, '2014-12-09 13:17:00', '2014-12-09 13:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (49, 2, '2014-12-09 13:35:00', '2014-12-09 13:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (49, 1, '2014-12-09 15:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (50, 5, null, '2014-12-10 12:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (50, 3, '2014-12-10 12:23:00', '2014-12-10 12:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (50, 4, '2014-12-10 12:31:00', '2014-12-10 12:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (50, 8, '2014-12-10 12:42:00', '2014-12-10 12:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (50, 13, '2014-12-10 12:48:00', '2014-12-10 12:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (50, 9, '2014-12-10 13:17:00', '2014-12-10 13:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (50, 2, '2014-12-10 13:35:00', '2014-12-10 13:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (50, 1, '2014-12-10 15:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (51, 5, null, '2014-12-11 12:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (51, 3, '2014-12-11 12:23:00', '2014-12-11 12:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (51, 4, '2014-12-11 12:31:00', '2014-12-11 12:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (51, 8, '2014-12-11 12:42:00', '2014-12-11 12:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (51, 13, '2014-12-11 12:48:00', '2014-12-11 12:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (51, 9, '2014-12-11 13:17:00', '2014-12-11 13:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (51, 2, '2014-12-11 13:35:00', '2014-12-11 13:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (51, 1, '2014-12-11 15:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (52, 5, null, '2014-12-12 12:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (52, 3, '2014-12-12 12:23:00', '2014-12-12 12:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (52, 4, '2014-12-12 12:31:00', '2014-12-12 12:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (52, 8, '2014-12-12 12:42:00', '2014-12-12 12:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (52, 13, '2014-12-12 12:48:00', '2014-12-12 12:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (52, 9, '2014-12-12 13:17:00', '2014-12-12 13:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (52, 2, '2014-12-12 13:35:00', '2014-12-12 13:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (52, 1, '2014-12-12 15:56:00', null);

INSERT INTO rides(id, line_id) VALUES (53, 12);
INSERT INTO rides(id, line_id) VALUES (54, 12);
INSERT INTO rides(id, line_id) VALUES (55, 12);
INSERT INTO rides(id, line_id) VALUES (56, 12);
INSERT INTO rides(id, line_id) VALUES (57, 12);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (53, 5, null, '2014-12-08 14:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (53, 3, '2014-12-08 14:23:00', '2014-12-08 14:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (53, 4, '2014-12-08 14:31:00', '2014-12-08 14:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (53, 8, '2014-12-08 14:42:00', '2014-12-08 14:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (53, 13, '2014-12-08 14:48:00', '2014-12-08 14:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (53, 9, '2014-12-08 15:17:00', '2014-12-08 15:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (53, 2, '2014-12-08 15:35:00', '2014-12-08 15:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (53, 1, '2014-12-08 17:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (54, 5, null, '2014-12-09 14:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (54, 3, '2014-12-09 14:23:00', '2014-12-09 14:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (54, 4, '2014-12-09 14:31:00', '2014-12-09 14:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (54, 8, '2014-12-09 14:42:00', '2014-12-09 14:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (54, 13, '2014-12-09 14:48:00', '2014-12-09 14:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (54, 9, '2014-12-09 15:17:00', '2014-12-09 15:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (54, 2, '2014-12-09 15:35:00', '2014-12-09 15:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (54, 1, '2014-12-09 17:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (55, 5, null, '2014-12-10 14:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (55, 3, '2014-12-10 14:23:00', '2014-12-10 14:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (55, 4, '2014-12-10 14:31:00', '2014-12-10 14:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (55, 8, '2014-12-10 14:42:00', '2014-12-10 14:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (55, 13, '2014-12-10 14:48:00', '2014-12-10 14:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (55, 9, '2014-12-10 15:17:00', '2014-12-10 15:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (55, 2, '2014-12-10 15:35:00', '2014-12-10 15:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (55, 1, '2014-12-10 17:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (56, 5, null, '2014-12-11 14:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (56, 3, '2014-12-11 14:23:00', '2014-12-11 14:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (56, 4, '2014-12-11 14:31:00', '2014-12-11 14:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (56, 8, '2014-12-11 14:42:00', '2014-12-11 14:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (56, 13, '2014-12-11 14:48:00', '2014-12-11 14:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (56, 9, '2014-12-11 15:17:00', '2014-12-11 15:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (56, 2, '2014-12-11 15:35:00', '2014-12-11 15:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (56, 1, '2014-12-11 17:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (57, 5, null, '2014-12-12 14:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (57, 3, '2014-12-12 14:23:00', '2014-12-12 14:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (57, 4, '2014-12-12 14:31:00', '2014-12-12 14:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (57, 8, '2014-12-12 14:42:00', '2014-12-12 14:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (57, 13, '2014-12-12 14:48:00', '2014-12-12 14:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (57, 9, '2014-12-12 15:17:00', '2014-12-12 15:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (57, 2, '2014-12-12 15:35:00', '2014-12-12 15:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (57, 1, '2014-12-12 17:56:00', null);

INSERT INTO rides(id, line_id) VALUES (58, 13);
INSERT INTO rides(id, line_id) VALUES (59, 13);
INSERT INTO rides(id, line_id) VALUES (60, 13);
INSERT INTO rides(id, line_id) VALUES (61, 13);
INSERT INTO rides(id, line_id) VALUES (62, 13);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (58, 5, null, '2014-12-08 16:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (58, 3, '2014-12-08 16:23:00', '2014-12-08 16:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (58, 4, '2014-12-08 16:31:00', '2014-12-08 16:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (58, 8, '2014-12-08 16:42:00', '2014-12-08 16:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (58, 13, '2014-12-08 16:48:00', '2014-12-08 16:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (58, 9, '2014-12-08 17:17:00', '2014-12-08 17:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (58, 2, '2014-12-08 17:35:00', '2014-12-08 17:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (58, 1, '2014-12-08 19:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (59, 5, null, '2014-12-09 16:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (59, 3, '2014-12-09 16:23:00', '2014-12-09 16:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (59, 4, '2014-12-09 16:31:00', '2014-12-09 16:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (59, 8, '2014-12-09 16:42:00', '2014-12-09 16:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (59, 13, '2014-12-09 16:48:00', '2014-12-09 16:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (59, 9, '2014-12-09 17:17:00', '2014-12-09 17:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (59, 2, '2014-12-09 17:35:00', '2014-12-09 17:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (59, 1, '2014-12-09 19:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (60, 5, null, '2014-12-10 16:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (60, 3, '2014-12-10 16:23:00', '2014-12-10 16:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (60, 4, '2014-12-10 16:31:00', '2014-12-10 16:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (60, 8, '2014-12-10 16:42:00', '2014-12-10 16:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (60, 13, '2014-12-10 16:48:00', '2014-12-10 16:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (60, 9, '2014-12-10 17:17:00', '2014-12-10 17:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (60, 2, '2014-12-10 17:35:00', '2014-12-10 17:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (60, 1, '2014-12-10 19:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (61, 5, null, '2014-12-11 16:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (61, 3, '2014-12-11 16:23:00', '2014-12-11 16:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (61, 4, '2014-12-11 16:31:00', '2014-12-11 16:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (61, 8, '2014-12-11 16:42:00', '2014-12-11 16:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (61, 13, '2014-12-11 16:48:00', '2014-12-11 16:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (61, 9, '2014-12-11 17:17:00', '2014-12-11 17:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (61, 2, '2014-12-11 17:35:00', '2014-12-11 17:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (61, 1, '2014-12-11 19:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (62, 5, null, '2014-12-12 16:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (62, 3, '2014-12-12 16:23:00', '2014-12-12 16:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (62, 4, '2014-12-12 16:31:00', '2014-12-12 16:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (62, 8, '2014-12-12 16:42:00', '2014-12-12 16:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (62, 13, '2014-12-12 16:48:00', '2014-12-12 16:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (62, 9, '2014-12-12 17:17:00', '2014-12-12 17:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (62, 2, '2014-12-12 17:35:00', '2014-12-12 17:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (62, 1, '2014-12-12 19:56:00', null);

INSERT INTO rides(id, line_id) VALUES (63, 14);
INSERT INTO rides(id, line_id) VALUES (64, 14);
INSERT INTO rides(id, line_id) VALUES (65, 14);
INSERT INTO rides(id, line_id) VALUES (66, 14);
INSERT INTO rides(id, line_id) VALUES (67, 14);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (63, 5, null, '2014-12-08 18:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (63, 3, '2014-12-08 18:23:00', '2014-12-08 18:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (63, 4, '2014-12-08 18:31:00', '2014-12-08 18:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (63, 8, '2014-12-08 18:42:00', '2014-12-08 18:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (63, 13, '2014-12-08 18:48:00', '2014-12-08 18:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (63, 9, '2014-12-08 19:17:00', '2014-12-08 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (63, 2, '2014-12-08 19:35:00', '2014-12-08 19:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (63, 1, '2014-12-08 21:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (64, 5, null, '2014-12-09 18:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (64, 3, '2014-12-09 18:23:00', '2014-12-09 18:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (64, 4, '2014-12-09 18:31:00', '2014-12-09 18:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (64, 8, '2014-12-09 18:42:00', '2014-12-09 18:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (64, 13, '2014-12-09 18:48:00', '2014-12-09 18:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (64, 9, '2014-12-09 19:17:00', '2014-12-09 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (64, 2, '2014-12-09 19:35:00', '2014-12-09 19:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (64, 1, '2014-12-09 21:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (65, 5, null, '2014-12-10 18:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (65, 3, '2014-12-10 18:23:00', '2014-12-10 18:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (65, 4, '2014-12-10 18:31:00', '2014-12-10 18:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (65, 8, '2014-12-10 18:42:00', '2014-12-10 18:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (65, 13, '2014-12-10 18:48:00', '2014-12-10 18:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (65, 9, '2014-12-10 19:17:00', '2014-12-10 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (65, 2, '2014-12-10 19:35:00', '2014-12-10 19:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (65, 1, '2014-12-10 21:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (66, 5, null, '2014-12-11 18:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (66, 3, '2014-12-11 18:23:00', '2014-12-11 18:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (66, 4, '2014-12-11 18:31:00', '2014-12-11 18:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (66, 8, '2014-12-11 18:42:00', '2014-12-11 18:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (66, 13, '2014-12-11 18:48:00', '2014-12-11 18:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (66, 9, '2014-12-11 19:17:00', '2014-12-11 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (66, 2, '2014-12-11 19:35:00', '2014-12-11 19:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (66, 1, '2014-12-11 21:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (67, 5, null, '2014-12-12 18:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (67, 3, '2014-12-12 18:23:00', '2014-12-12 18:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (67, 4, '2014-12-12 18:31:00', '2014-12-12 18:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (67, 8, '2014-12-12 18:42:00', '2014-12-12 18:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (67, 13, '2014-12-12 18:48:00', '2014-12-12 18:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (67, 9, '2014-12-12 19:17:00', '2014-12-12 19:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (67, 2, '2014-12-12 19:35:00', '2014-12-12 19:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (67, 1, '2014-12-12 21:56:00', null);

INSERT INTO rides(id, line_id) VALUES (68, 15);
INSERT INTO rides(id, line_id) VALUES (69, 15);
INSERT INTO rides(id, line_id) VALUES (70, 15);
INSERT INTO rides(id, line_id) VALUES (71, 15);
INSERT INTO rides(id, line_id) VALUES (72, 15);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 7, null, '2014-12-08 06:01:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 6, '2014-12-08 06:10:00', '2014-12-08 06:11:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 5, '2014-12-08 06:16:00', '2014-12-08 06:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 3, '2014-12-08 06:23:00', '2014-12-08 06:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 4, '2014-12-08 06:31:00', '2014-12-08 06:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 8, '2014-12-08 06:42:00', '2014-12-08 06:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 13, '2014-12-08 06:48:00', '2014-12-08 06:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 9, '2014-12-08 07:17:00', '2014-12-08 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 2, '2014-12-08 07:35:00', '2014-12-08 07:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (68, 1, '2014-12-08 09:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 7, null, '2014-12-09 06:01:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 6, '2014-12-09 06:10:00', '2014-12-09 06:11:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 5, '2014-12-09 06:16:00', '2014-12-09 06:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 3, '2014-12-09 06:23:00', '2014-12-09 06:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 4, '2014-12-09 06:31:00', '2014-12-09 06:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 8, '2014-12-09 06:42:00', '2014-12-09 06:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 13, '2014-12-09 06:48:00', '2014-12-09 06:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 9, '2014-12-09 07:17:00', '2014-12-09 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 2, '2014-12-09 07:35:00', '2014-12-09 07:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (69, 1, '2014-12-09 09:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 7, null, '2014-12-10 06:01:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 6, '2014-12-10 06:10:00', '2014-12-10 06:11:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 5, '2014-12-10 06:16:00', '2014-12-10 06:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 3, '2014-12-10 06:23:00', '2014-12-10 06:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 4, '2014-12-10 06:31:00', '2014-12-10 06:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 8, '2014-12-10 06:42:00', '2014-12-10 06:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 13, '2014-12-10 06:48:00', '2014-12-10 06:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 9, '2014-12-10 07:17:00', '2014-12-10 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 2, '2014-12-10 07:35:00', '2014-12-10 07:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (70, 1, '2014-12-10 09:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 7, null, '2014-12-11 06:01:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 6, '2014-12-11 06:10:00', '2014-12-11 06:11:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 5, '2014-12-11 06:16:00', '2014-12-11 06:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 3, '2014-12-11 06:23:00', '2014-12-11 06:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 4, '2014-12-11 06:31:00', '2014-12-11 06:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 8, '2014-12-11 06:42:00', '2014-12-11 06:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 13, '2014-12-11 06:48:00', '2014-12-11 06:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 9, '2014-12-11 07:17:00', '2014-12-11 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 2, '2014-12-11 07:35:00', '2014-12-11 07:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (71, 1, '2014-12-11 09:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 7, null, '2014-12-12 06:01:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 6, '2014-12-12 06:10:00', '2014-12-12 06:11:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 5, '2014-12-12 06:16:00', '2014-12-12 06:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 3, '2014-12-12 06:23:00', '2014-12-12 06:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 4, '2014-12-12 06:31:00', '2014-12-12 06:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 8, '2014-12-12 06:42:00', '2014-12-12 06:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 13, '2014-12-12 06:48:00', '2014-12-12 06:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 9, '2014-12-12 07:17:00', '2014-12-12 07:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 2, '2014-12-12 07:35:00', '2014-12-12 07:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (72, 1, '2014-12-12 09:56:00', null);

INSERT INTO rides(id, line_id) VALUES (73, 16);
INSERT INTO rides(id, line_id) VALUES (74, 16);
INSERT INTO rides(id, line_id) VALUES (75, 16);
INSERT INTO rides(id, line_id) VALUES (76, 16);
INSERT INTO rides(id, line_id) VALUES (77, 16);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 7, null, '2014-12-08 06:56:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 6, '2014-12-08 07:05:00', '2014-12-08 07:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 5, '2014-12-08 07:11:00', '2014-12-08 07:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 3, '2014-12-08 07:23:00', '2014-12-08 07:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 4, '2014-12-08 07:31:00', '2014-12-08 07:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 8, '2014-12-08 07:42:00', '2014-12-08 07:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 13, '2014-12-08 07:48:00', '2014-12-08 07:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 9, '2014-12-08 08:17:00', '2014-12-08 08:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 2, '2014-12-08 08:35:00', '2014-12-08 08:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (73, 1, '2014-12-08 10:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 7, null, '2014-12-09 06:56:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 6, '2014-12-09 07:05:00', '2014-12-09 07:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 5, '2014-12-09 07:11:00', '2014-12-09 07:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 3, '2014-12-09 07:23:00', '2014-12-09 07:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 4, '2014-12-09 07:31:00', '2014-12-09 07:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 8, '2014-12-09 07:42:00', '2014-12-09 07:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 13, '2014-12-09 07:48:00', '2014-12-09 07:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 9, '2014-12-09 08:17:00', '2014-12-09 08:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 2, '2014-12-09 08:35:00', '2014-12-09 08:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (74, 1, '2014-12-09 10:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 7, null, '2014-12-10 06:56:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 6, '2014-12-10 07:05:00', '2014-12-10 07:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 5, '2014-12-10 07:11:00', '2014-12-10 07:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 3, '2014-12-10 07:23:00', '2014-12-10 07:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 4, '2014-12-10 07:31:00', '2014-12-10 07:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 8, '2014-12-10 07:42:00', '2014-12-10 07:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 13, '2014-12-10 07:48:00', '2014-12-10 07:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 9, '2014-12-10 08:17:00', '2014-12-10 08:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 2, '2014-12-10 08:35:00', '2014-12-10 08:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (75, 1, '2014-12-10 10:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 7, null, '2014-12-11 06:56:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 6, '2014-12-11 07:05:00', '2014-12-11 07:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 5, '2014-12-11 07:11:00', '2014-12-11 07:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 3, '2014-12-11 07:23:00', '2014-12-11 07:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 4, '2014-12-11 07:31:00', '2014-12-11 07:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 8, '2014-12-11 07:42:00', '2014-12-11 07:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 13, '2014-12-11 07:48:00', '2014-12-11 07:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 9, '2014-12-11 08:17:00', '2014-12-11 08:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 2, '2014-12-11 08:35:00', '2014-12-11 08:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (76, 1, '2014-12-11 10:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 7, null, '2014-12-12 06:56:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 6, '2014-12-12 07:05:00', '2014-12-12 07:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 5, '2014-12-12 07:11:00', '2014-12-12 07:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 3, '2014-12-12 07:23:00', '2014-12-12 07:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 4, '2014-12-12 07:31:00', '2014-12-12 07:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 8, '2014-12-12 07:42:00', '2014-12-12 07:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 13, '2014-12-12 07:48:00', '2014-12-12 07:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 9, '2014-12-12 08:17:00', '2014-12-12 08:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 2, '2014-12-12 08:35:00', '2014-12-12 08:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (77, 1, '2014-12-12 10:56:00', null);

INSERT INTO rides(id, line_id) VALUES (78, 17);
INSERT INTO rides(id, line_id) VALUES (79, 17);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 7, null, '2014-12-13 08:56:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 6, '2014-12-13 09:05:00', '2014-12-13 09:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 5, '2014-12-13 09:11:00', '2014-12-13 09:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 3, '2014-12-13 09:23:00', '2014-12-13 09:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 4, '2014-12-13 09:31:00', '2014-12-13 09:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 8, '2014-12-13 09:42:00', '2014-12-13 09:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 13, '2014-12-13 09:48:00', '2014-12-13 09:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 9, '2014-12-13 12:17:00', '2014-12-13 12:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 2, '2014-12-13 12:35:00', '2014-12-13 12:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (78, 1, '2014-12-13 14:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 7, null, '2014-12-14 08:56:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 6, '2014-12-14 09:05:00', '2014-12-14 09:06:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 5, '2014-12-14 09:11:00', '2014-12-14 09:17:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 3, '2014-12-14 09:23:00', '2014-12-14 09:25:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 4, '2014-12-14 09:31:00', '2014-12-14 09:33:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 8, '2014-12-14 09:42:00', '2014-12-14 09:43:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 13, '2014-12-14 09:48:00', '2014-12-14 09:49:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 9, '2014-12-14 12:17:00', '2014-12-14 12:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 2, '2014-12-14 12:35:00', '2014-12-14 12:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (79, 1, '2014-12-14 14:56:00', null);

INSERT INTO rides(id, line_id) VALUES (80, 18);
INSERT INTO rides(id, line_id) VALUES (81, 18);
INSERT INTO rides(id, line_id) VALUES (82, 18);
INSERT INTO rides(id, line_id) VALUES (83, 18);
INSERT INTO rides(id, line_id) VALUES (84, 18);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (80, 12, null, '2014-12-08 05:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (80, 11, '2014-12-08 06:00:00', '2014-12-08 06:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (80, 10, '2014-12-08 06:09:00', '2014-12-08 06:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (80, 9, '2014-12-08 06:19:00', '2014-12-08 06:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (80, 2, '2014-12-08 06:35:00', '2014-12-08 06:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (80, 1, '2014-12-08 08:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (81, 12, null, '2014-12-09 05:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (81, 11, '2014-12-09 06:00:00', '2014-12-09 06:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (81, 10, '2014-12-09 06:09:00', '2014-12-09 06:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (81, 9, '2014-12-09 06:19:00', '2014-12-09 06:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (81, 2, '2014-12-09 06:35:00', '2014-12-09 06:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (81, 1, '2014-12-09 08:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (82, 12, null, '2014-12-10 05:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (82, 11, '2014-12-10 06:00:00', '2014-12-10 06:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (82, 10, '2014-12-10 06:09:00', '2014-12-10 06:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (82, 9, '2014-12-10 06:19:00', '2014-12-10 06:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (82, 2, '2014-12-10 06:35:00', '2014-12-10 06:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (82, 1, '2014-12-10 08:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (83, 12, null, '2014-12-11 05:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (83, 11, '2014-12-11 06:00:00', '2014-12-11 06:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (83, 10, '2014-12-11 06:09:00', '2014-12-11 06:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (83, 9, '2014-12-11 06:19:00', '2014-12-11 06:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (83, 2, '2014-12-11 06:35:00', '2014-12-11 06:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (83, 1, '2014-12-11 08:56:00', null);

INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (84, 12, null, '2014-12-12 05:50:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (84, 11, '2014-12-12 06:00:00', '2014-12-12 06:02:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (84, 10, '2014-12-12 06:09:00', '2014-12-12 06:10:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (84, 9, '2014-12-12 06:19:00', '2014-12-12 06:21:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (84, 2, '2014-12-12 06:35:00', '2014-12-12 06:37:00');
INSERT INTO stops(ride_id, station_id, arrival, departure) VALUES (84, 1, '2014-12-12 08:56:00', null);

ALTER SEQUENCE rides_id_seq RESTART WITH 85;