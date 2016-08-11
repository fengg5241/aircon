-- Date: 20160513
-- Version: v0.4.1

--
-- PostgreSQL database dump
--

--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO groups VALUES (1, '1', now(), NULL, 1, 'Site Group A', '|1|', NULL, now(), '1', 1, null, '1', 1, NULL, false, '1', NULL, 1, 1.12340160000000, 101.12345600000000, 1.12340160000000, 101.12345600000000, 1.12340060000000, 1.12346000000000, 2, 'Site Group A', 1);
INSERT INTO groups VALUES (2, '1', now(), NULL, 2, 'Site A', '|1|2|', NULL, now(), '1', 1, 1, '1', 2, NULL, false, '2', NULL, 6, 1.12340190000000, 101.12345900000000, 1.12340190000000, 101.12345900000000, 1.12340090000000, 1.12346300000000, 2, 'Site Group A, Site A', 1);
INSERT INTO groups VALUES (3, '1', now(), NULL, 3, 'Logical Group A', '|1|2|3|', 'panasonic_demo.svg', now(), '1', 1, 2, '2', 3, NULL, false, '3', 2.4399999999999999, 7, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Site Group A, Site A, Logical Group A', 1);
INSERT INTO groups VALUES (4, '1', now(), NULL, 4, 'Control Group A', '|1|2|3|4|', NULL, now(), '1', 1, 3, '1', 4, NULL, false, '4', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Site Group A, Site A, Logical Group A, Control Group A', 1);

--
-- Data for Name: svg_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO svg_master VALUES (1, 'SVG1.svg', 'a');
INSERT INTO svg_master VALUES (2, 'SVG2', 'b');
INSERT INTO svg_master VALUES (3, 'SVG3', 'c');
INSERT INTO svg_master VALUES (4, 'SVG4', 'd');
INSERT INTO svg_master VALUES (5, 'SVG5', 'e');

UPDATE users SET companyid = 1;

UPDATE users 
SET companyid = companiesusers.company_id
FROM companiesusers 
WHERE companiesusers.user_id = users.id;

--
-- Data for Name: companiesusers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO companiesusers VALUES (1, 1, 1, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 1);
INSERT INTO companiesusers VALUES (1, 2, 1, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 2);
INSERT INTO companiesusers VALUES (1, 3, 1, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 3);
INSERT INTO companiesusers VALUES (1, 4, 1, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 4);

UPDATE groups SET isunit_exists = 1;

INSERT INTO weather_location(id, name, latitude, longitude) VALUES (1, 'Singapore', 1.35797500000000, -9.15003640000000);
INSERT INTO weather_location(id, name, latitude, longitude) VALUES (2, 'Kuala Lumpur', 3.15694860000000, 101.71230300000000);
INSERT INTO weather_location(id, name, latitude, longitude) VALUES (3, 'Xiamen University', 2.83288190000000, 101.70770540000000);

INSERT INTO company_weather_location(company_id, location_id) VALUES (1,1);
INSERT INTO company_weather_location(company_id, location_id) VALUES (1,2);
INSERT INTO company_weather_location(company_id, location_id) VALUES (1,3);

INSERT INTO svg_master(id, svg_file_name, svg_name)VALUES (100, 'MSIA XIAMEN MAP- with Tag-01.svg', 'Xiamen University');
UPDATE companies SET svg_id=100;

--Update groups table timezone to 305 (Singapore) from 2
UPDATE groups SET timezone = 305;
--end of update to groups

--Start of patch(fix userid) seshu
ALTER SEQUENCE companiesuser_seq RESTART WITH 1000;
ALTER SEQUENCE users_id_seq RESTART WITH 400;
--End of patch(Seshu)-----

--start of patch (seshu)
UPDATE users
   SET createdby='4',updatedby='4'
 WHERE id='3';
 --end of patch(seshu)
