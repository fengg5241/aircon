--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.4.4
-- Started on 2015-11-13 10:06:30

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2746 (class 0 OID 19857)
-- Dependencies: 195
-- Data for Name: companies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY companies (id, createdby, creationdate, deploymentid, imagepath, name, updatedate, updatedby, isdel) FROM stdin;
1	RSI	2015-08-13 11:10:00	\N	\N	Panasonic@Bedok	2015-08-13 11:10:00	RSI	\N
2	RSI	2015-08-13 11:10:00	\N	\N	Panasonic@bedok	2015-08-13 11:10:00	RSI	\N
3	RSI	2015-08-13 11:10:00	\N	\N	Panasonic@bedok	2015-08-13 11:10:00	RSI	\N
\.


--
-- TOC entry 2771 (class 0 OID 19944)
-- Dependencies: 222
-- Data for Name: group_level; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY group_level (id, type_level, type_level_name) FROM stdin;
1	Site Group ID_Lvl_1	Country
2	Site Group ID_Lvl_2	State
3	Site Group ID_Lvl_3	Town
4	Site Group ID_Lvl_4	Area
5	Site Group ID_Lvl_5	Street
6	Site ID	Building
7	Logical ID_Lvl_1	Floor
8	Logical ID_Lvl_2	Wing
9	Logical ID_Lvl_3	Central
10	Logical ID_Lvl_4	Cooked
11	Logical ID_Lvl_5	Raw
12	Control Group ID	Department
\.


--
-- TOC entry 2772 (class 0 OID 19947)
-- Dependencies: 223
-- Data for Name: groupcategory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY groupcategory (groupcategoryid, groupcategoryname) FROM stdin;
1	Site Group
2	Site
3	Logical Group
4	Control Group
\.


--
-- TOC entry 2775 (class 0 OID 19961)
-- Dependencies: 226
-- Data for Name: groupscriteria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY groupscriteria (id, is_child, is_sibling, is_unit) FROM stdin;
1	1	0	0
2	1	1	1
3	0	0	1
sad	\N	\N	\N
\.


--
-- TOC entry 2836 (class 0 OID 20176)
-- Dependencies: 287
-- Data for Name: timezonemaster; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY timezonemaster (id, timezone) FROM stdin;
5	(GMT-09:00) Alaska
6	(GMT-08:00) Pacific Time (US &amp; Canada)
7	(GMT-08:00) Tijuana
8	(GMT-07:00) Arizona
9	(GMT-07:00) Chihuahua
10	(GMT-07:00) Mazatlan
11	(GMT-07:00) Mountain Time (US &amp; Canada)
12	(GMT-06:00) Central America
13	(GMT-06:00) Central Time (US &amp; Canada)
14	(GMT-06:00) Guadalajara
15	(GMT-06:00) Mexico City
16	(GMT-06:00) Monterrey
17	(GMT-06:00) Saskatchewan
18	(GMT-05:00) Bogota
19	(GMT-05:00) Eastern Time (US &amp; Canada)
20	(GMT-05:00) Indiana (East)
21	(GMT-05:00) Lima
22	(GMT-05:00) Quito
23	(GMT-04:30) Caracas
24	(GMT-04:00) Atlantic Time (Canada)
25	(GMT-04:00) Georgetown
1	Asia/Kolkata
2	Singapore
3	America/Los_Angeles
4	Pacific/Honolulu
\.


--
-- TOC entry 2773 (class 0 OID 19950)
-- Dependencies: 224
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY groups (id, createdby, creationdate, iconimage, level, name, path, svg_path, updatedate, updatedby, company_id, parentid, groupcriteria_id, groupcategoryid, last_access_groupids, isdel, uniqueid, co2factor, group_level_id, map_latitude, map_longitude, svg_max_latitude, svg_max_longitude, svg_min_latitude, svg_min_longitude, timezone, isunit_exists) FROM stdin;
14	1	2015-08-13 12:31:00	\N	10	Meeting Room 2	|1|2|3|5|6|8|9|10|11|14|	\N	2015-08-13 12:31:00	1	1	11	1	3	\N	\N	LG014	\N	10	\N	\N	\N	\N	\N	\N	1	1
18	1	2015-08-13 12:34:00	\N	12	Personnel Filing Room	|1|2|3|5|6|8|9|10|11|14|16|18|	\N	2015-08-13 12:34:00	1	1	16	1	4	\N	\N	CG018	\N	12	\N	\N	\N	\N	\N	\N	1	1
20	1	2015-08-13 12:34:00	\N	4	Meeting Room 4	|1|2|4|20|	\N	2015-08-13 12:34:00	1	1	4	1	1	\N	\N	SG020	\N	4	1.12340160000000	101.12345600000000	1.12340160000000	101.12345600000000	1.12340060000000	1.12346000000000	\N	1
45	1	2015-08-13 13:17:00	\N	7	Open Office	|25|27|30|39|41|43|45|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	43	3	4	\N	\N	CG045	\N	12	\N	\N	\N	\N	\N	\N	2	1
46	1	2015-08-13 13:17:00	\N	6	Meeting Rooms	|25|27|30|39|42|46|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	42	3	4	\N	\N	CG046	\N	12	\N	\N	\N	\N	\N	\N	2	1
47	1	2015-08-13 13:17:00	\N	5	Other Rooms	|25|27|30|40|47|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	40	3	3	\N	\N	LG047	\N	8	\N	\N	\N	\N	\N	\N	2	1
50	1	2015-08-13 13:01:00	\N	6	Level 3 	|25|27|30|40|48|50|	panasonic_demo.svg	2015-08-13 13:01:00	1	1	48	2	4	\N	\N	CG050	\N	12	\N	\N	\N	\N	\N	\N	2	1
13	1	2015-08-13 12:31:00	\N	10	Meeting Room 1	|1|2|3|5|6|8|9|10|11|13|	\N	2015-08-13 12:31:00	1	1	11	1	4	\N	\N	CG013	\N	12	1.12340150000000	101.12345500000000	1.12340150000000	101.12345500000000	1.12340050000000	1.12345000000000	1	1
1	1	2015-08-13 12:11:00	\N	1	Block A	|1|	\N	2015-08-13 12:11:00	1	1	\N	1	1	\N	\N	SGOO1	\N	1	1.12340110000000	101.12345100000000	1.12340110000000	101.12345100000000	1.12340010000000	1.12345600000000	\N	1
2	1	2015-08-13 12:16:00	\N	2	Level 1	|1|2|	\N	2015-08-13 12:16:00	1	1	1	2	1	\N	\N	SG002	\N	2	\N	\N	\N	\N	\N	\N	\N	1
3	1	2015-08-13 12:17:00	\N	3	Level 2	|1|2|3|	\N	2015-08-13 12:17:00	1	1	2	2	1	\N	\N	SG003	\N	3	1.12340120000000	101.12345200000000	1.12340120000000	101.12345200000000	1.12340020000000	1.12345700000000	\N	1
4	1	2015-08-13 12:17:00	\N	3	Level 3	|1|2|4|	\N	2015-08-13 12:17:00	1	1	2	2	1	\N	\N	SG004	\N	3	1.12340130000000	101.12345300000000	1.12340130000000	101.12345300000000	1.12340030000000	1.12345800000000	\N	1
5	1	2015-08-13 12:19:00	\N	4	Reception	|1|2|3|5|	\N	2015-08-13 12:19:00	1	1	3	1	1	\N	\N	SG005	\N	4	\N	\N	\N	\N	\N	\N	\N	1
6	1	2015-08-13 12:22:00	\N	5	Waiting Area	|1|2|3|5|6|	\N	2015-08-13 12:22:00	1	1	5	1	1	\N	\N	SG006	3.54	5	\N	\N	\N	\N	\N	\N	\N	1
7	1	2015-08-13 12:22:00	\N	5	Meeting Room 1	|1|2|3|5|7|	\N	2015-08-13 12:22:00	1	1	5	1	1	\N	\N	SG007	\N	5	1.12340140000000	101.12345400000000	1.12340140000000	101.12345400000000	1.12340040000000	1.12345900000000	\N	1
8	1	2015-08-13 12:22:00	\N	6	Meeting Room 2	|1|2|3|5|6|8|	\N	2015-08-13 12:22:00	1	1	6	1	2	\N	\N	ST008	1.3100000000000001	6	\N	\N	\N	\N	\N	\N	1	1
9	1	2015-08-13 12:29:00	\N	7	Panasonic Sales	|1|2|3|5|6|8|9|	\N	2015-08-13 12:29:00	1	1	8	1	3	\N	\N	LG009	\N	7	\N	\N	\N	\N	\N	\N	1	1
10	1	2015-08-13 12:29:00	\N	8	Energy Solutions Group	|1|2|3|5|6|8|9|10|	\N	2015-08-13 12:29:00	1	1	9	1	3	\N	\N	LG010	\N	8	\N	\N	\N	\N	\N	\N	1	1
11	1	2015-08-13 12:29:00	\N	9	Panasonic Asia	|1|2|3|5|6|8|9|10|11|	\N	2015-08-13 12:29:00	1	1	10	1	3	\N	\N	LG011	\N	9	\N	\N	\N	\N	\N	\N	1	1
15	1	2015-08-13 12:34:00	\N	10	Open Office	|1|2|3|5|6|8|9|10|11|15|	\N	2015-08-13 12:34:00	1	1	11	1	3	\N	\N	LG015	\N	10	\N	\N	\N	\N	\N	\N	1	1
16	1	2015-08-13 12:34:00	\N	11	Meeting Room 3	|1|2|3|5|6|8|9|10|11|14|16|	\N	2015-08-13 12:34:00	1	1	14	1	3	\N	\N	LG016	\N	11	\N	\N	\N	\N	\N	\N	1	1
17	1	2015-08-13 12:34:00	\N	11	MD Room	|1|2|3|5|6|8|9|10|11|14|17|	\N	2015-08-13 12:34:00	1	1	14	1	4	\N	\N	CG017	\N	12	\N	\N	\N	\N	\N	\N	1	1
21	1	2015-08-13 12:34:00	\N	5	Meeting Room 5	|1|2|4|20|21|	\N	2015-08-13 12:34:00	1	1	20	1	2	\N	\N	ST021	3.21	6	1.12340170000000	101.12345700000000	1.12340170000000	101.12345700000000	1.12340070000000	1.12346100000000	3	1
23	1	2015-08-13 12:37:00	\N	6	Exhibition Room 1	|1|2|4|20|21|23|	\N	2015-08-13 12:37:00	1	1	21	1	3	\N	\N	LG023	\N	7	\N	\N	\N	\N	\N	\N	3	1
25	1	2015-08-13 12:57:00	\N	1	Block B	|25|	\N	2015-08-13 12:57:00	1	1	\N	1	1	\N	\N	SG025	\N	1	1.12340190000000	101.12345900000000	1.12340190000000	101.12345900000000	1.12340090000000	1.12346300000000	\N	1
26	1	2015-08-13 13:01:00	\N	2	Level 1	|25|26|	panasonic_demo.svg	2015-08-13 13:01:00	1	1	25	2	1	\N	\N	SG026	\N	2	\N	\N	\N	\N	\N	\N	\N	1
28	1	2015-08-13 13:03:00	\N	3	EDO Office	|25|26|28|	panasonic_demo.svg	2015-08-13 13:03:00	1	1	26	3	1	\N	\N	SG028	3.3300000000000001	3	\N	\N	\N	\N	\N	\N	\N	1
27	1	2015-08-13 13:01:00	\N	2	Level 2	|25|27|	panasonic_demo.svg	2015-08-13 13:01:00	1	1	25	2	1	\N	\N	SG027	2.4399999999999999	2	\N	\N	\N	\N	\N	\N	\N	1
29	1	2015-08-13 13:03:00	\N	3	PESAP Office	|25|26|29|	panasonic_demo.svg	2015-08-13 13:03:00	1	1	26	3	1	\N	\N	SG029	1.98	3	\N	\N	\N	\N	\N	\N	\N	1
30	1	2015-08-13 13:03:00	\N	3	PRDCSG Office	|25|27|30|	panasonic_demo.svg	2015-08-13 13:03:00	1	1	27	3	2	\N	\N	ST030	1.3100000000000001	6	\N	\N	\N	\N	\N	\N	2	1
35	1	2015-08-13 13:17:00	\N	4	Meeting Room 3	|25|26|29|35|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	29	3	2	\N	\N	ST035	1.3100000000000001	6	\N	\N	\N	\N	\N	\N	3	1
36	1	2015-08-13 13:17:00	\N	5	Open Office	|25|26|29|35|36|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	35	3	3	\N	\N	LG036	\N	7	\N	\N	\N	\N	\N	\N	3	1
37	1	2015-08-13 13:17:00	\N	5	Meeting Room 1	|25|26|29|35|37|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	35	3	3	\N	\N	LG037	\N	7	\N	\N	\N	\N	\N	\N	3	1
39	1	2015-08-13 13:17:00	\N	4	Meeting Room 3	|25|27|30|39|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	30	3	3	\N	\N	LG039	\N	7	\N	\N	\N	\N	\N	\N	2	1
40	1	2015-08-13 13:17:00	\N	4	R&D Open Office	|25|27|30|40|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	30	3	3	\N	\N	LG040	\N	7	\N	\N	\N	\N	\N	\N	2	1
41	1	2015-08-13 13:17:00	\N	5	Meeting Rooms	|25|27|30|39|41|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	39	3	3	\N	\N	LG041	\N	8	\N	\N	\N	\N	\N	\N	2	1
42	1	2015-08-13 13:17:00	\N	5	Test Rooms	|25|27|30|39|42|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	39	3	3	\N	\N	LG042	\N	8	\N	\N	\N	\N	\N	\N	2	1
43	1	2015-08-13 13:17:00	\N	6	Other Rooms	|25|27|30|39|41|43|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	41	3	3	\N	\N	LG043	\N	9	\N	\N	\N	\N	\N	\N	2	1
44	1	2015-08-13 13:17:00	\N	6	Store Rooms	|25|27|30|39|41|44|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	41	3	4	\N	\N	CG044	\N	12	\N	\N	\N	\N	\N	\N	2	1
48	1	2015-08-13 13:17:00	\N	5	AG Meeting Rooms	|25|27|30|40|48|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	40	3	3	\N	\N	LG048	\N	8	\N	\N	\N	\N	\N	\N	2	1
49	1	2015-08-13 13:17:00	\N	6	Meeting Rooms	|25|27|30|40|47|49|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	47	3	4	\N	\N	CG049	\N	12	\N	\N	\N	\N	\N	\N	2	1
12	1	2015-08-13 12:31:00	\N	9	Open Office	|1|2|3|5|6|8|9|10|12|	\N	2015-08-13 12:31:00	1	1	10	1	4	\N	\N	CG012	\N	12	\N	\N	\N	\N	\N	\N	1	1
19	1	2015-08-13 12:34:00	\N	11	Open Office19	|1|2|3|5|6|8|9|10|11|15|19|	\N	2015-08-13 12:34:00	1	1	15	1	4	\N	\N	CG019	\N	12	\N	\N	\N	\N	\N	\N	1	1
22	1	2015-08-13 12:37:00	\N	6	Canteen	|1|2|4|20|21|22|	\N	2015-08-13 12:37:00	1	1	21	1	3	\N	\N	LG022	\N	7	\N	\N	\N	\N	\N	\N	3	1
24	1	2015-08-13 12:37:00	\N	7	Exhibition Room 2	|1|2|4|20|21|22|24|	\N	2015-08-13 12:37:00	1	1	22	1	4	\N	\N	CG024	\N	12	1.12340180000000	101.12345800000000	1.12340180000000	101.12345800000000	1.12340080000000	1.12346200000000	3	1
31	1	2015-08-13 13:03:00	\N	4	CISC-AP Office	|25|26|28|31|	panasonic_demo.svg	2015-08-13 13:03:00	1	1	28	3	1	\N	\N	SG031	1.3100000000000001	5	\N	\N	\N	\N	\N	\N	1	1
32	1	2015-08-13 13:17:00	\N	6	Open Office	|25|26|28|31|51|32|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	51	3	3	\N	\N	LG032	\N	7	\N	\N	\N	\N	\N	\N	1	1
33	1	2015-08-13 13:17:00	\N	6	Meeting Room 1	|25|26|28|31|51|33|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	51	3	3	\N	\N	LG033	\N	7	\N	\N	\N	\N	\N	\N	1	1
34	1	2015-08-13 13:17:00	\N	7	Meeting Room 2	|25|26|28|31|51|32|34|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	32	3	4	\N	\N	CG034	\N	12	\N	\N	\N	\N	\N	\N	1	1
38	1	2015-08-13 13:17:00	\N	6	Meeting Room 2	|25|26|29|35|36|38|	panasonic_demo.svg	2015-08-13 13:17:00	1	1	36	3	4	\N	\N	CG038	\N	12	\N	\N	\N	\N	\N	\N	3	1
51	1	2015-08-13 13:01:00	\N	5	PISC	|25|26|28|31|51|	panasonic_demo.svg	2015-08-13 13:01:00	1	1	31	2	2	\N	\N	ST051	\N	6	\N	\N	\N	\N	\N	\N	1	1
\.


--
-- TOC entry 2742 (class 0 OID 19833)
-- Dependencies: 191
-- Data for Name: adapters; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY adapters (id, activatedate, address, createdby, creationdate, enabled_date, ipnumber, latitude, longitude, notificationrecipients, scanned_date, scanning_date, serialnumber, state, timelinefetched_date, timelinefetchedperiod, updatedate, updatedby, company_id, defualtgroupid, siteid, timezone) FROM stdin;
3	\N	47	\N	2015-08-13 20:14:38	\N	\N	\N	\N	\N	\N	\N	0	\N	\N	\N	\N	\N	\N	\N	\N	3
4	\N	56	\N	2015-08-13 20:14:39	\N	\N	\N	\N	\N	\N	\N	1	\N	\N	\N	\N	\N	\N	\N	\N	4
5	\N	31	\N	2015-08-13 20:14:39	\N	\N	\N	\N	\N	\N	\N	2	\N	\N	\N	\N	\N	\N	\N	\N	3
6	\N	54	\N	2015-08-13 20:14:39	\N	\N	\N	\N	\N	\N	\N	3	\N	\N	\N	\N	\N	\N	\N	\N	1
\.


--
-- TOC entry 2856 (class 0 OID 0)
-- Dependencies: 192
-- Name: adapters_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('adapters_id_seq', 1, false);


--
-- TOC entry 2848 (class 0 OID 20836)
-- Dependencies: 299
-- Data for Name: alarmanotificationmailtemp; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY alarmanotificationmailtemp (deviceid, errorcode, alarmtype, groupname, siteid, maintenance_description, svg_max_longitude, svg_max_latitude, svg_min_longitude, svg_min_latitude, notificationtype_id, groupid, userid, email, notificationtype, devicetype, countermeasure_2way, countermeasure_3way, countermeasure_customer, creationdate, comapany_id) FROM stdin;
\.


--
-- TOC entry 2857 (class 0 OID 0)
-- Dependencies: 194
-- Name: alarmstatistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('alarmstatistics_id_seq', 330, true);


--
-- TOC entry 2858 (class 0 OID 0)
-- Dependencies: 196
-- Name: companies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('companies_id_seq', 1, true);


--
-- TOC entry 2827 (class 0 OID 20121)
-- Dependencies: 278
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY roles (id, createdby, creationdate, name, updatedate, updatedby, isdel) FROM stdin;
1	1	2015-08-13 11:16:00	Super Admin	2015-08-13 11:16:00	1	\N
2	1	2015-08-13 11:16:00	Admin	2015-08-13 11:16:00	1	\N
3	1	2015-08-13 11:16:00	Agent	2015-08-13 11:16:00	1	\N
4	1	2015-08-13 11:16:00	First Level Customer	2015-08-13 11:16:00	1	\N
5	1	2015-08-13 11:16:00	Customer	2015-08-13 11:16:00	1	\N
\.


--
-- TOC entry 2842 (class 0 OID 20194)
-- Dependencies: 293
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (id, confirmationtoken, confirmed_date, confirmedsent_date, country, createdby, creationdate, email, encryptedpassword, failedattempt, firstname, forgottoken, isverified, lastactivity_date, lastname, locked_date, passwordchange_date, rembembercreated_date, resetpassword_sent_date, resetpasswordtoken, resettoken, unlocktoken, updatedate, updatedby, roles_id, timezone_id, isdel, telephone, department, lastvisitedgroups) FROM stdin;
9	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken9@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ9	\N
10	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken10@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	5	1	\N	012-345-6788	HQ10	\N
12	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken12@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ12	[{"entityId":1,"entityType":null,"selected":false,"expanded":false},{"entityId":2,"entityType":null,"selected":false,"expanded":false},{"entityId":3,"entityType":null,"selected":false,"expanded":false}]
11	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken11@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ11	\N
13	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken13@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ13	[{"entityId":1,"entityType":null,"selected":true,"expanded":false},{"entityId":2,"entityType":null,"selected":true,"expanded":false},{"entityId":3,"entityType":null,"selected":true,"expanded":false}]
14	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken14@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ14	[{"entityId":1,"entityType":null,"selected":true,"expanded":false},{"entityId":2,"entityType":null,"selected":true,"expanded":false},{"entityId":3,"entityType":null,"selected":true,"expanded":false}]
1	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	admin@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	2015-09-13 05:59:05	2015-09-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	011-111-6333	HQ	\N
3	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken2@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ	\N
2	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken1@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ1	\N
4	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken3@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	5	1	\N	012-345-6788	HQ	\N
5	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken5@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ	\N
6	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken6@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ6	\N
7	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken7@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ7	\N
8	505aff0c-6052-40ef-bf56-9dba6b66798e	2015-08-13 05:59:05	2015-08-13 05:59:05	Singapore	1	2015-08-13 11:29:05	testtoken8@panasonic.com	4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec	0	William	\N	1	2015-08-13 05:59:05	Wong	\N	2015-08-13 05:59:05	\N	\N	\N	\N	\N	2015-08-13 11:29:05	1	1	1	\N	012-345-6788	HQ8	\N
\.


--
-- TOC entry 2748 (class 0 OID 19862)
-- Dependencies: 197
-- Data for Name: companiesusers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY companiesusers (company_id, user_id, group_id, creationdate, updatedate, createdby, updatedby) FROM stdin;
1	1	8	2015-09-09 00:00:00	2015-09-09 00:00:00	1	1
1	1	21	2015-09-09 00:00:00	2015-09-09 00:00:00	1	1
1	1	30	2015-09-09 00:00:00	2015-09-09 00:00:00	1	1
1	1	31	2015-09-09 00:00:00	2015-09-09 00:00:00	1	1
1	1	33	2015-09-21 19:23:11.459	2015-09-21 19:23:11.459	1	1
3	1	1	2015-09-29 19:07:00.922	\N	\N	\N
3	2	8	2015-09-29 19:07:08.285	\N	\N	\N
3	3	14	2015-09-29 19:08:03.51	\N	\N	\N
3	4	18	2015-09-29 19:08:11.279	\N	\N	\N
1	1	25	2015-09-09 00:00:00	2015-09-09 00:00:00	1	1
1	4	9	2015-09-21 19:23:11.459	2015-09-21 19:23:11.459	1	1
3	5	1	2015-09-30 12:17:59.215	\N	\N	\N
3	5	45	2015-09-30 12:20:01.801	\N	\N	\N
\.


--
-- TOC entry 2859 (class 0 OID 0)
-- Dependencies: 201
-- Name: deviceattribute_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('deviceattribute_master_id_seq', 5, true);


--
-- TOC entry 2860 (class 0 OID 0)
-- Dependencies: 203
-- Name: devicecommand_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('devicecommand_id_seq', 1, false);


--
-- TOC entry 2753 (class 0 OID 19895)
-- Dependencies: 204
-- Data for Name: efficiency_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY efficiency_rating (id, rating, cooling_efficiency, heating_efficiency) FROM stdin;
1	A+++	3.2000000000000002	3.6000000000000001
2	A++	3	3.3999999999999999
3	A+	2.7999999999999998	3.2000000000000002
4	A	2.6000000000000001	3
5	B	2.3999999999999999	2.7999999999999998
6	C	2.2000000000000002	2.6000000000000001
\.


--
-- TOC entry 2861 (class 0 OID 0)
-- Dependencies: 205
-- Name: efficiency_rating_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('efficiency_rating_id_seq', 1, false);


--
-- TOC entry 2801 (class 0 OID 20036)
-- Dependencies: 252
-- Data for Name: notificationtype_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY notificationtype_master (id, typename) FROM stdin;
1	AC Configuration Changes
2	Test 1
3	Test 2
4	isMaster
\.


--
-- TOC entry 2755 (class 0 OID 19900)
-- Dependencies: 206
-- Data for Name: errorcode_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY errorcode_master (id, code, severity, customer_description, maintenance_description, createdby, creationdate, updatedate, updatedby, type, countermeasure_2way, countermeasure_3way, countermeasure_customer, notificationtype_id) FROM stdin;
196	oil	Critical	AC problem	Oil Change Time Alarm	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
3	A03	Critical	desc.cust.a03	desc.maint.a03	\N	2015-08-13 00:00:00	2015-08-13 00:00:00	1	Alarm	\N	\N	\N	\N
1	A01	Critical	desc.cust.a01	desc.maint.a01	\N	2015-08-13 00:00:00	2015-08-14 00:00:00	1	Alarm	\N	\N	\N	\N
2	A02	Critical	desc.cust.a02	desc.maint.a02	\N	2015-08-14 00:00:00	2015-08-15 00:00:00	2	Alarm	\N	\N	\N	\N
4	A04	Critical	AC problem	Engine low speed failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
5	A05	Critical	AC problem	Ignition power supply failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
6	A06	Critical	AC problem	Engine start failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
8	A08	Critical	AC problem	Engine stall/stop	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
10	A10	Critical	AC problem	High exhaust gas temeprature	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
12	A12	Critical	AC problem	Throttle(stepping motor)failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
13	A13	Critical	AC problem	Fuel gas adjustment valve failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
14	A14	Critical	AC problem	Engine oil pressure switch failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
15	A15	Critical	AC problem	Starter power supply output short-circuit	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
16	A16	Critical	AC problem	Starter locked	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
17	A17	Critical	AC problem	CT failure(starter current detection failure)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
19	A19	Non Critical	AC problem	Wax 3 way valve trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
20	A20	Critical	AC problem	High cooling water temperature	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
21	A21	Non Critical	AC problem	Abnormal cooling water level	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
22	A22	Critical	AC problem	Cooling water pump overload	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
23	A23	Non Critical	AC problem	Crank angle sensor trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
24	A24	Non Critical	AC problem	Cam angle sensor trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
25	A25	Critical	AC problem	Clutch trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
26	A26	Non Critical	AC problem	Misfire	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
27	A27	Non Critical	AC problem	Catalyst temp.trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
28	A28	Critical	AC problem	Generator trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
29	A29	Critical	AC problem	Converter trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
31	C01	Non Critical	AC problem	Poor setting on Control Address(duplicated)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
32	C02	Non Critical	AC problem	Discord of number of units in central process control	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
33	C03	Non Critical	AC problem	Miswiring of central process control	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
34	C04	NOn Critical	AC problem	Misconnection of central process control	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
35	C05	Non Critical	AC problem	Transmission error of central process control	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
36	C06	Non Critical	AC problem	Reception error of central process control	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
37	C12	Non Critical	AC problem	Lump/Batch alarm by local adapter	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
38	C16	Non Critical	AC problem	Poor transmission of the adapter to the unit	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
39	C17	NOn Critical	AC problem	Poor reception of the adapter from the unit	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
40	C18	Non Critical	AC problem	Duplicate central address in adaptor	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
41	C19	Non Critical	AC problem	Duplicate adaptor address	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
42	C20	Non Critical	AC problem	PAC,GHP-type mixed in amy-adapter/communications adapter	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
43	C21	Non Critical	AC problem	Non volatile memory in the adapter is abnormal	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
44	C22	NOn Critical	AC problem	Poor settingn of  adapter address	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
45	C23	Non Critical	AC problem	Host terminal failure(Software)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
46	C24	Non Critical	AC problem	Host terminal failure(Hardware)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
47	C25	Non Critical	AC problem	Host terminal transaction failure 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
48	C26	Non Critical	AC problem	Communication failure in host terminal	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
49	C28	NOn Critical	AC problem	Reception error of S-DDC from host terminal	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
7	A07	Critical	desc.cust.a07	desc.maint.a07	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
30	A30	Non Critical	desc.cust.a30	desc.maint.a30	\N	\N	\N	\N	Alarm	cm.maint.2way.a30	cm.maint.3way.a30	cm.cust.a30	\N
11	A11	Non Critical	desc.cust.a11	desc.maint.a11	\N	\N	\N	\N	Alarm	cm.maint.2way.a11	cm.maint.3way.a11	cm.cust.a11	\N
9	A09	Critical	desc.cust.a09	desc.maint.a09	\N	\N	\N	\N	Alarm	cm.maint.2way.a09	cm.maint.3way.a09	cm.cust.a09	\N
50	C29	Non Critical	AC problem	Initialization  failure of S-DDC	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
66	E15	Non Critical	AC problem	Alarm for auto address setting.(Number of indoor units is too small.)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
67	E16	Non Critical	AC problem	Alarm for auto address setting.(Number of indoor units is too large.)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
68	E17	Non Critical	AC problem	 Poor transmission from indoor unit to indoor unit. 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
69	E18	Non Critical	AC problem	Poor communication of group processing control caused by miswiring	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
70	E19	Non Critical	AC problem	3 or more master units (Individual twin multi type) in one system	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
71	E20	Non Critical	AC problem	No indoor unit in automatic address	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
51	C31	Non Critical	AC problem	Configuration change (detected by adaptor)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
52	E01	Non Critical	AC problem	Poor reception of the signal on the remote controller / Remote controller is detecting error signal from indoor unit. 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
53	E02	Non Critical	AC problem	Poor transmission of the signal on the remote controller.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
54	E03	NOn Critical	AC problem	Poor reception of the indoor unit from the remote controller\n(central process control)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
72	E21	Critical	AC problem	PCB (outdoor control board) trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
55	E04	Non Critical	AC problem	Poor reception of indoor unit from the outdoor unit/When turning on the power supply,\n	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
73	E22	Critical	AC problem	 Thermistor (outdoor control board sensor) trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
74	E23	Non Critical	AC problem	Poor transmission of CCU to outdoor sub bus.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
75	E24	Non Critical	AC problem	Poor reception of CCU from outdoor sub bus .	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
76	E25	Non Critical	AC problem	Poor setting of outdoor sub bus address. (duplicated)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
77	E26	Critical	AC problem	Discord of number of outdoor sub bus units / Outdoor unit sub bus number mismatch / Mismatch in outdoor unit quantity	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
56	E05	Non Critical	AC problem	Poor transmission of the indoor unit to the outdoor unit.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
57	E06	Non Critical	AC problem	Poor reception of the outdoor unit from the indoor unit.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
58	E07	Non Critical	AC problem	Poor transmission of the outdoor unit to the indoor unit.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
59	E08	Non Critical	AC problem	Duplicated setting of indoor units address.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
60	E09	Non Critical	AC problem	Setting multiple master remote controllers.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
78	E27	Critical	AC problem	 Miswiring of outdoor sub bus.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
79	E28	Non Critical	AC problem	Misconnection of outdoor units.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
80	E29	Non Critical	AC problem	Outdoor unit serial reception failure / Outdoor unit failed to receive communication from relay control unit 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
81	E30	Non Critical	AC problem	Poor transmission of outdoor serial / Outdoor unit serial transmission failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
82	E31	Critical	AC problem	Outdoor unit Unit internal communications failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
83	F01	Critical	AC problem	 Abnormal sensor for the inlet temp. E1 on the heat exchanger of the indoor unit.  	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
84	F02	Non Critical	AC problem	Abnormal sensor for mid-point temp. E2 on the heat exchanger of the indoor unit. 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
85	F03	Non Critical	AC problem	 Abnormal sensor for the outlet temp. E3 on the heat exchanger of the indoor unit 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
61	E10	Non Critical	AC problem	Indoor unit - 3-way + signal PCB serial transmission failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
62	E11	Non Critical	AC problem	Poor reception of the indoor unit from the signal output P.C.B..	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
63	E12	Non Critical	AC problem	No auto address setting. (Auto address is in setting) / Inhibit starting automatic address setting 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
64	E13	Non Critical	AC problem	Poor transmission of indoor unit to the remote controller.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
65	E14	Non Critical	AC problem	Duplicated address of master indoor unit in group processing control.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
95	F13	Non Critical	AC problem	 Abnormal sensor for cooling water temperature. 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
86	F04	Non Critical	AC problem	Abnormal sensor for the outlet temperature of the PC compressor (comp. No.1)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
88	F06	Non Critical	AC problem	 Abnormal sensor for the inlet temp. on the heat exchanger 1 of the outdoor unit. 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
89	F07	Non Critical	AC problem	Abnormal sensor for the outlet temp. on the heat exchanger 1 of the outdoor unit.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
90	F08	Non Critical	AC problem	 Abnormal sensor for outdoor temperature. 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
87	F05	Non Critical	AC problem	 Abnormal sensor for the outlet temperature of the AC compressor (comp. No.2)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
91	F09	Non Critical	AC problem	 Actuation of scroll compressor protective thermostat.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
92	F10	Non Critical	AC problem	 Abnormal sensor for intake temperature of indoor unit / WHE: Cold/hot water inlet sensor trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
93	F11	Non Critical	AC problem	 Abnormal sensor for discharge temperature of indoor unit / WHE Cold/hot water outlet sensor trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
94	F12	Non Critical	AC problem	 Compressor inlet temperature sensor failure 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
96	F14	Non Critical	AC problem	 Abnormal sensor for engine room temperature.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
97	F15	Non Critical	AC problem	 Outdoor unit heat exchanger intermediate temperature sensor failure 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
98	F16	Non Critical	AC problem	 Compressor inlet/outlet pressure sensor failure / High-pressure sensor trouble 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
99	F17	Non Critical	AC problem	 Low-pressure sensor trouble 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
100	F18	Non Critical	AC problem	 Abnormal sensor for exhaust gas temperature. 	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
101	F19	Non Critical	AC problem	Abnormal sensor for compressor bottom surface temperature.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
102	F20	Non Critical	AC problem	ICE sensor / Clutch coil temp. sensor trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
103	F21	Non Critical	AC problem	Abnormal sensor for compressor 3 current.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
104	F22	Non Critical	AC problem	Abnormal sensor for discharge gas temperature from compressor 3.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
110	F28	Non Critical	AC problem	Abnormal sensor for compressor 2 current.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
111	F29	Non Critical	AC problem	Abnormal non-volatile memory (EEPROM) in indoor unit.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
112	F30	Non Critical	AC problem	Abnormal timer (RTC).	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
113	F31	Non Critical	AC problem	Abnormal non-volatile memory (EEPROM) in outdoor unit.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
114	H01	Non Critical	AC problem	Incorrect current value for compressor 1 .(over current)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
115	H02	Non Critical	AC problem	Incorrect current value for compressor 1 .(locked compressor)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
116	H03	Non Critical	AC problem	CT sensor is dislocated from compressor 1, Short circuited / Current is not detected when comp. No. 1 is ON.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
105	F23	Non Critical	AC problem	Abnormal sensor for refrigerant gas temperature in the heat exchanger 2 (In).	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
117	H04	Non Critical	AC problem	Protective thermostat for scroll compressor 1.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
106	F24	Non Critical	AC problem	Abnormal sensor for liquid refrigerant temperature in the heat exchanger 2 (Out).	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
118	H05	Non Critical	AC problem	Protective thermostat for compressor 1 is slipped off / not installed	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
107	F25	Non Critical	AC problem	Abnormal sensor for heat exchanger coil 1 temperature.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
119	H06	Non Critical	AC problem	Activation of low pressure switch.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
108	F26	Non Critical	AC problem	Abnormal sensor for heat exchanger coil 2 temperature.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
120	H07	Non Critical	AC problem	No oil in compressor 1. Low oil level	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
109	F27	Non Critical	AC problem	Abnormal sensor for compressor 1 current.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
121	H08	Non Critical	AC problem	Chattering of magnet switch 1 .(Multi)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
122	H09	Non Critical	AC problem	Chattering of magnet switch 1 .(Multi)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
123	H10	Non Critical	AC problem	Defective sensor for crank case heater 1.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
124	H11	Non Critical	AC problem	Incorrect current value for compressor 2 .(over current)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
125	H12	Non Critical	AC problem	Incorrect current value for compressor 2 .(locked compressor)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
126	H13	Non Critical	AC problem	CT sensor is dislocated from compressor 2, short circuit / Current is not detected when comp. No.2 is ON.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
127	H14	Non Critical	AC problem	Protective thermostat for scroll compressor 2.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
128	H15	Non Critical	AC problem	Protective thermostat for scroll compressor 2 is dislocated.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
129	H16	Critical	AC problem	No oil in compressor 2.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
130	H17	Non Critical	AC problem	Sensed unbalanced power voltage.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
131	H18	Non Critical	AC problem	 Chattering of magnet switch 1 (Espacio).	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
132	H19	Non Critical	AC problem	Chattering of magnet switch 2 ..	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
133	H20	Non Critical	AC problem	Defective sensor for crank case heater 2.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
134	H21	Non Critical	AC problem	Incorrect current value for compressor 3 .(over current)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
135	H22	Non Critical	AC problem	Incorrect current value for compressor 3 .(locked compressor)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
136	H23	Non Critical	AC problem	 CT sensor is dislocated from compressor 3, short circuited.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
137	H24	Non Critical	AC problem	 Protective thermostat for scroll compressor 3.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
138	H25	Non Critical	AC problem	 Protective thermostat for scroll compressor 3 is slipped off / Compressor 3 discharge temperature sensor disconnected	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
139	H26	Critical	AC problem	No oil in compressor 3.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
140	H27	Non Critical	AC problem	Incorrect connection of oil sensor 2.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
141	H28	Non Critical	AC problem	Incorrect connection of oil sensor 3.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
142	H29	Non Critical	AC problem	Chattering of magnet switch 3.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
143	H30	Non Critical	AC problem	 Defective sensor for crank case heater 3.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
144	H31	Critical	AC problem	 HIC trouble alarm / IPM trip (IPM current or temperature)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
145	L01	Non Critical	AC problem	 Incorrect address for indoor unit. (No master indoor unit.)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
146	L02	Non Critical	AC problem	Mismatch of indoor and outdoor units. (Non-GHP device present)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
147	L03	Non Critical	AC problem	Setting plural master indoor units for group control.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
148	L04	Non Critical	AC problem	Duplicate setting of system address (outdoor unit).	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
149	L05	Non Critical	AC problem	Duplicate setting of priority indoor unit. (for priority indoor unit)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
150	L06	Non Critical	AC problem	 Duplicate setting of priority indoor unit. (for other than priority indoor unit.)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
151	L07	Non Critical	AC problem	 Group control wiring to individually controlled indoor unit.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
152	L08	Non Critical	AC problem	No Setting of indoor unit address.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
153	L09	Non Critical	AC problem	No Setting of indoor unit capacity.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
154	L10	Non Critical	AC problem	No Setting of outdoor unit capacity.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
155	L11	Non Critical	AC problem	Mis-wiring of group control wire. (Espacio)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
156	L12	Non Critical	AC problem	Discord of indoor unit s capacity. (Multi)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
157	L13	Non Critical	AC problem	Poor setting of indoor unit s type.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
158	L14	Non Critical	AC problem	 Discord of power frequency. (50,60Hz)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
159	L15	Non Critical	AC problem	Defective double duct bearing.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
160	L16	Non Critical	AC problem	Water heat exchanger unit setting failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
161	L17	Non Critical	AC problem	Mis-matched connection of outdoor units that have different kinds of refrigerant.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
162	L18	Critical	AC problem	4-way valve operation failure.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
163	L19	Non Critical	AC problem	Water heat exchanger unit duplicate parallel address	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
164	L20	Non Critical	AC problem	Duplicated central control addresses of indoor units at local adaptor	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
165	L21	Non Critical	AC problem	 Gas type setting failure	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
166	P01	Critical	AC problem	Abnormal indoor fan motor / Thermal protector in indoor unit fan motor is activated	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
167	P02	Non Critical	AC problem	Actuation of outdoor fan motor, protective thermostats for CM and AC. / Power supply voltage is unusual. (The voltage is more than 260 V or less than 160 V between L and N phase.)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
168	P03	Non Critical	AC problem	High discharge temperature of compressor 1.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
169	P04	Non Critical	AC problem	Actuation of high refrigerant pressure switch.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
170	P05	Non Critical	AC problem	Reversed phase (or missing phase) of the power source detected, capacity mismatch	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
171	P06	Non Critical	AC problem	Mismatching of Model type or Capacity in outdoor unit	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
172	P07	Non Critical	AC problem	 DC-current overload, heat radiator temperature abnormal in outdoor unit	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
173	P09	Non Critical	AC problem	Poor connection of ceiling panel connector for indoor unit.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
174	P10	Non Critical	AC problem	Actuation of indoor unit float switch.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
175	P11	Non Critical	AC problem	Water heat exchanger unit freezing trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
176	P12	Non Critical	AC problem	Operation of protective function of fan inverter / Indoor unit DC fan trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
178	P14	Non Critical	AC problem	O2 sensor (detects low oxygen level) activated.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
179	P15	Non Critical	AC problem	 No refrigerant gas.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
180	P16	Critical	AC problem	Compressor 1 overcurrent	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
181	P17	Non Critical	AC problem	Abnormal discharge gas temperature from compressor 2	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
182	P18	Non Critical	AC problem	Abnormal discharge gas temperature from compressor 3 / GHP: Bypass valve trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
183	P19	Non Critical	AC problem	4 way valve lock trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
184	P20	Non Critical	AC problem	Abnormally high refrigerant gas pressure.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
185	P21	Non Critical	AC problem	Abnormal pressure difference in compressor oil.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
186	P22	Non Critical	AC problem	Fan motor trouble / Outdoor unit fan motor is unusual.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
187	P23	Critical	AC problem	Water heat exchanger unit interlock trouble	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
188	P24	Non Critical	AC problem	ice storage tank unit abnormaly	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
189	P25	Non Critical	AC problem	ice storage tank unit abnormaly	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
190	P26	Non Critical	AC problem	Inverter compressor high-frequency overcurrent alarm	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
191	P27	Non Critical	AC problem	Protective alarm for freezing of coolant water	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
192	P28	Non Critical	AC problem	Alarm: Out of range for coolant water set temperature	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
193	P29	Non Critical	AC problem	Inverter compressor missing phase or lock alarm	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
194	P30	Critical	AC problem	Abnormal sub indoor units in group control. (Centralized processing control detected the abnormality.)	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
195	P31	Non Critical	AC problem	Abnormal group control.	\N	\N	\N	\N	Alarm	\N	\N	\N	\N
199	N03	Non Critical	Probably window has left open	Window left open detected	\N	\N	\N	\N	Alert	\N	\N	\N	\N
177	P13	Critical	desc.cust.p13	desc.maint.p13	\N	\N	\N	\N	Alarm	cm.maint.2way.p13	cm.maint.3way.p13	cm.cust.p13	\N
200	N04	Non Critical	AC problem	Air short circuit detected in Heating Mode	\N	\N	\N	\N	Alert	\N	\N	\N	\N
198	N02	Non Critical	Oil Change required for Compressor	Oil Change required for Compressor	\N	\N	\N	\N	Pre-Alarm	\N	\N	\N	\N
197	N01	Non Critical	Regular Maintenance for Compressor is overdue	Regular Maintenance for Compressor is overdue	\N	\N	\N	\N	Pre-Alarm	\N	\N	\N	\N
201	N05	Non Critical	desc.cust.n05	desc.maint.n05	\N	\N	\N	\N	Alert	cm.maint.2way.n05	cm.maint.3way.n05	cm.cust.n05	\N
\.


--
-- TOC entry 2756 (class 0 OID 19906)
-- Dependencies: 207
-- Data for Name: errorgroup; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY errorgroup (errorgroupid, errorgroup_category) FROM stdin;
\.


--
-- TOC entry 2757 (class 0 OID 19909)
-- Dependencies: 208
-- Data for Name: fanspeed_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY fanspeed_master (id, fanspeedname) FROM stdin;
1	High
2	Medium
3	Low
4	Auto
\.


--
-- TOC entry 2862 (class 0 OID 0)
-- Dependencies: 209
-- Name: fanspeed_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('fanspeed_master_id_seq', 1, false);


--
-- TOC entry 2794 (class 0 OID 20012)
-- Dependencies: 245
-- Data for Name: metaoutdoorunits; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY metaoutdoorunits (id, createdby, creationdate, fuelcounsumptioncooling, fuelcounsumptionheating, fusedisolatora, inputpower220vw, inputpower230vw, inputpower240vw, inputpower380vw, inputpower400vw, inputpower415vw, kind, logo, modelname, nominalcoolingkw, nominalheatingkw, phase, runningcurrent220va, runningcurrent230va, runningcurrent240va, runningcurrent380va, runningcurrent400va, runningcurrent415va, unitdepthm, unitheightm, unitwidthtm, updatedate, updatedby, system) FROM stdin;
1	1	2015-03-16 18:29:44	12.300000000000001	11.5	fused	33	33	33	33	33	33	Kind1	logo1	Model1	21.100000000000001	11.1	1	33	33	33	33	33	33	11	11	11	2015-07-03 17:24:48	\N	2 way
2	1	2015-04-21 14:16:12	13.1	21	fused	31	21	22	23	13	34	Kiind2	logo2	Model2	11.199999999999999	33.399999999999999	2	32	45	42	45	24	21	14	10	12	2015-06-04 13:25:45	\N	3 way
\.


--
-- TOC entry 2804 (class 0 OID 20047)
-- Dependencies: 255
-- Data for Name: outdoorunits; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY outdoorunits (id, centraladdress, createdby, creationdate, iswaterheatexchanger, serialnumber, type, updatedate, updatedby, adapters_id, metaoutdoorunit_id, name, oid, svg_path, siteid, svg_max_latitude, svg_max_longitude, svg_min_latitude, svg_min_longitude) FROM stdin;
1	\n"ODU2"\n	\N	\N	\N	\N	VRF	\N	\N	\N	1	ODU1	1	a_outdoor.svg	ST008	\N	\N	\N	\N
15	0-0-01	1	2015-08-13 20:14:45	0	0	VRF	2015-08-14 08:26:03	1	3	1	ODU1	[2:{AABBCCDDEEFF02000040}]	b_outdoor.svg	ST008	1.32200745980500	103.93104637027100	1.32199782318208	103.93103664726300
16	0-0-12	1	2015-08-13 20:14:46	0	0	VRF	2015-08-14 08:26:03	1	3	2	ODU2	2	c_outdoor.svg	ST051	1.32200729221156	103.93106464282000	1.32199765558864	103.93105483599300
17	0-0-13	1	2015-08-13 20:14:46	0	0	VRF	2015-08-14 08:26:03	1	3	1	ODU3	2	d_outdoor.svg	ST021	1.32202614647369	103.93110563032600	1.32201650985084	103.93109582350000
18	0-0-15	1	2015-08-13 20:14:46	0	0	VRF	2015-08-14 08:26:03	1	3	1	ODU4	2	e_outdoor.svg	\N	1.32202631406713	103.93111853845700	1.32201650985084	103.93110889926900
19	0-0-16	1	2015-08-13 20:14:46	0	0	VRF	2015-08-14 08:26:03	1	3	2	ODU5	2	f_outdoor.svg	\N	1.32202631406713	103.93117746323600	1.32201659364755	103.93116782404800
20	1-1-17	1	2015-08-13 20:14:46	0	1	VRF	2015-08-14 08:26:03	1	4	2	ODU6	2	g_outdoor.svg	\N	1.32202589508352	103.93118995227200	1.32201634225740	103.93118031308400
21	1-1-18	1	2015-08-13 20:14:46	0	1	VRF	2015-08-14 08:26:03	1	4	1	ODU7	2	h_outdoor.svg	\N	1.32202698444089	103.93123881876800	1.32201743161476	103.93122917957900
22	1-1-19	1	2015-08-13 20:14:46	0	1	VRF	2015-08-14 08:26:03	1	4	2	ODU8	2	i_outdoor.svg	\N	1.32202698444089	103.93125164308000	1.32201743161476	103.93124200389100
23	1-1-20	1	2015-08-13 20:14:46	0	1	VRF	2015-08-14 08:26:03	1	4	1	\N	1	j_outdoor.svg	\N	1.32202715203433	103.93128969692000	1.32201734781804	103.93127997391200
24	1-1-21	1	2015-08-13 20:14:47	0	1	VRF	2015-08-14 08:26:03	1	4	1	\N	1	k_outdoor.svg	\N	1.32202690064417	103.93130151540300	1.32201734781804	103.93129187621500
25	1-1-22	1	2015-08-13 20:14:47	0	1	VRF	2015-08-14 08:26:03	1	4	1	ODU25	2	l_outdoor.svg	\N	1.32202648166057	103.93134535275700	1.32201676124100	103.93133554593000
26	1-1-23	1	2015-08-13 20:14:47	0	1	VRF	2015-08-14 08:26:03	1	4	2	ODU26	3	m_outdoor.svg	\N	1.32202631406713	103.93135717124000	1.32201642605411	103.93134719677600
27	1-1-24	1	2015-08-13 20:14:47	0	1	GHP	2015-08-14 08:26:03	1	4	2	ODU27	4	n_outdoor.svg	\N	1.32202555989663	103.93140980959200	1.32201592327378	103.93140000276600
28	2-2-01	1	2015-08-13 20:14:47	0	2	VRF	2015-08-14 08:26:03	1	5	2	ODU28	5	o_outdoor.svg	\N	1.32202572749009	103.93142607048500	1.32201592327378	103.93141617983900
29	2-2-02	1	2015-08-13 20:14:47	0	2	VRF	2015-08-14 08:26:03	1	5	1	\N	1	p_outdoor.svg	\N	1.32202564369337	103.93145280875600	1.32201600707052	103.93144308574800
30	2-2-03	1	2015-08-13 20:14:47	0	2	VRF	2015-08-14 08:26:03	1	5	2	\N	1	q_outdoor.svg	\N	1.32202572749009	103.93146353759200	1.32201600707052	103.93145389840300
31	2-2-04	1	2015-08-13 20:14:47	0	2	VRF	2015-08-14 08:26:03	1	5	1	\N	1	r_outdoor.svg	\N	1.32202564369337	103.93152422257100	1.32201592327378	103.93151449956300
32	2-2-11	1	2015-08-13 20:14:47	0	2	VRF	2015-08-14 08:26:03	1	5	1	\N	1	s_outdoor.svg	\N	1.32202606267696	103.93153629251100	1.32201642605411	103.93152648568500
33	3-3-05	1	2015-08-13 20:14:47	0	3	VRF	2015-08-14 08:26:03	1	6	1	\N	1	t_outdoor.svg	\N	1.32202581128680	103.93157308906600	1.32201617466395	103.93156336605800
34	3-3-06	1	2015-08-13 20:14:47	0	3	VRF	2015-08-14 08:26:03	1	6	2	ODU13	1	u_outdoor.svg	\N	1.32202564369337	103.93158423699700	1.32201592327378	103.93157451399000
35	3-3-07	1	2015-08-13 20:14:47	0	3	VRF	2015-08-14 08:26:03	1	6	2	ODU12	1	v_outdoor.svg	\N	1.32202589508352	103.93164441906200	1.32201634225740	103.93163477987400
36	3-3-08	1	2015-08-13 20:14:47	0	3	VRF	2015-08-14 08:26:03	1	6	2	ODU11	1	w_outdoor.svg	\N	1.32202581128680	103.93165590227000	1.32201592327378	103.93164626308100
37	3-3-09	1	2015-08-13 20:14:47	0	3	VRF	2015-08-14 08:26:03	1	6	1	ODU10	1	x_outdoor.svg	\N	1.32202589508352	103.93169403992900	1.32201634225740	103.93168440074000
38	3-3-10	1	2015-08-13 20:14:47	0	3	VRF	2015-08-14 08:26:03	1	6	\N	ODU9	1	y_outdoor.svg	\N	1.32202631406713	103.93170510404100	1.32201642605411	103.93169571631000
\.


--
-- TOC entry 2759 (class 0 OID 19914)
-- Dependencies: 210
-- Data for Name: gasheatmeter_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gasheatmeter_data (id, creationdate, logtime, totalenergy_consumed, updatedate, outdoorunit_id) FROM stdin;
\.


--
-- TOC entry 2760 (class 0 OID 19917)
-- Dependencies: 211
-- Data for Name: gasheatmeter_data_daily; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gasheatmeter_data_daily (id, totalenergy_consumed, outdoorunit_id, logtime, vrfwater_heat_exchanger, ghpwater_heat_exchanger) FROM stdin;
\.


--
-- TOC entry 2863 (class 0 OID 0)
-- Dependencies: 212
-- Name: gasheatmeter_data_daily_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_daily_id_seq', 1, false);


--
-- TOC entry 2864 (class 0 OID 0)
-- Dependencies: 213
-- Name: gasheatmeter_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_id_seq', 1, false);


--
-- TOC entry 2763 (class 0 OID 19924)
-- Dependencies: 214
-- Data for Name: gasheatmeter_data_monthly; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gasheatmeter_data_monthly (id, month, totalenergy_consumed, year, outdoorunit_id, logtime, vrfwater_heat_exchanger, ghpwater_heat_exchanger) FROM stdin;
\.


--
-- TOC entry 2865 (class 0 OID 0)
-- Dependencies: 215
-- Name: gasheatmeter_data_monthly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_monthly_id_seq', 1, false);


--
-- TOC entry 2765 (class 0 OID 19929)
-- Dependencies: 216
-- Data for Name: gasheatmeter_data_weekly; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gasheatmeter_data_weekly (id, creationdate, totalenergy_consumed, updatedate, week, year, outdoorunit_id, ghpwater_heat_exchanger, vrfwater_heat_exchanger, logtime) FROM stdin;
\.


--
-- TOC entry 2866 (class 0 OID 0)
-- Dependencies: 217
-- Name: gasheatmeter_data_weekly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_weekly_id_seq', 1, false);


--
-- TOC entry 2767 (class 0 OID 19934)
-- Dependencies: 218
-- Data for Name: gasheatmeter_data_yearly; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gasheatmeter_data_yearly (id, totalenergy_consumed, year, outdoorunit_id, logtime, vrfwater_heat_exchanger, ghpwater_heat_exchanger) FROM stdin;
\.


--
-- TOC entry 2867 (class 0 OID 0)
-- Dependencies: 219
-- Name: gasheatmeter_data_yearly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_yearly_id_seq', 1, false);


--
-- TOC entry 2769 (class 0 OID 19939)
-- Dependencies: 220
-- Data for Name: ghpparameter_statistics; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ghpparameter_statistics (id, threewaycoolervalve, threewayvalveforcoolant, threewayvalveforhotwater, balancevalve, bypassvalve_opening, catalyzertemperature, clutch, clutch2, clutchcoiltemperature, clutchcoiltemperature2, comperssoroillevel, compressorheater, compressorinlet_pressure, compressorinlet_temperature, compressoroutlet_pressure, compressoroutlet_temperature, coolanttemperature, createdby, creationdate, dischargesolenoidvalve1, dischargesolenoidvalve2, drainfilterheater1, drainfilterheater2, engineoperation_time, enginerevolution, exhaustgastemperature, exhaustheat_recovery_valve_opening, expansionvalve_opening, expansionvalve_opening2, flushingvalve, fuelgas_regulating_valve_opening, fuelgasshut_offvalve1, fuelgasshut_offvalve2, gasrefrigerantshut_offvalve, generationpower, ghpoilsign, heaterfor_cold_region, heatexchangerinlet_temperature, heatexchangerinlet_temperature2, hotwatertemperature, ignitiontiming, instantgas, instantheat, liquidvalve_opening, oilleveltemperature, oilpump, oilrecoveryvalve, outdoorunitfanoutput, pumpforhotwater, receivertankvalve1, receivertankvalve2, startermotor, startermotorcurrent, statermotor_power, suctionsolenoidevalve1, suctionsolenoidevalve2, superheat_level_of_compressor_unit, thtottle, timeafter_changing_engine_oil, updatedate, updatedby, outdoorunit_id, logtime) FROM stdin;
\.


--
-- TOC entry 2868 (class 0 OID 0)
-- Dependencies: 221
-- Name: ghpparameter_statistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ghpparameter_statistics_id_seq', 1, false);


--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 225
-- Name: groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('groups_id_seq', 6, true);


--
-- TOC entry 2792 (class 0 OID 20007)
-- Dependencies: 243
-- Data for Name: metaindoorunits; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY metaindoorunits (id, airflowahigh, airflowlow, airflowmedium, airflowultrahigh, createdby, creationdate, fusedisolatora, horsepower, inputpower220vw, inputpower230vw, inputpower240vw, kind, logo, modelname, nominalcoolingkw, nominalheatingkw, runningcurrent220va, runningcurrent230va, runningcurrent240va, unitdepthm, unitheightm, unitwidthtm, updatedate, updatedby, weight, system) FROM stdin;
1	9	5	15	38	RSI	2015-07-03 15:45:30	fused	22.5	10	20	15	Kind1	device.png	model1	17	15	14.5	18.5	11.5	23.5	16.5	18.5	2015-07-03 17:22:53	RSI	15.10	2 way
2	12	4	8	20	RSI	2015-07-03 15:45:33	fused2	44.5	13	13	13	Kind2	device.png	model2	22	22	12.5	12.5	12.5	12.5	12.5	12.5	2015-07-03 17:22:53	RSI	12.40	3 way
\.


--
-- TOC entry 2776 (class 0 OID 19964)
-- Dependencies: 227
-- Data for Name: indoorunits; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indoorunits (id, centraladdress, createdby, creationdate, currenttime, parent_id, serialnumber, type, updatedate, updatedby, adapters_id, metaindoorunit_id, outdoorunit_id, siteid, group_id, name, oid, svg_path, svg_max_latitude, svg_max_longitude, svg_min_latitude, svg_min_longitude) FROM stdin;
25	0-01-01	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP meeting room AC25	\N	2015-08-13 13:09:43	\N	3	1	15	ST008	12	IDU100	Logical-IDU-AA	a_indoor.svg	1.32173486905704	103.93136739716200	1.32172933847284	103.93136152983000
161	3-05-04	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	22	ST030	46	\N	[2:{AABBCCDDEEFF02000137}]	\N	\N	\N	\N	\N
162	3-05-06	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	23	ST030	46	\N	[2:{AABBCCDDEEFF02000138}]	\N	\N	\N	\N	\N
163	3-05-07	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	24	ST030	46	\N	[2:{AABBCCDDEEFF02000139}]	\N	\N	\N	\N	\N
169	3-05-22	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	30	ST030	49	IDU36	[2:{AABBCCDDEEFF02000145}]	\N	\N	\N	\N	\N
165	3-05-12	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	26	ST030	46	\N	[2:{AABBCCDDEEFF02000141}]	\N	\N	\N	\N	\N
148	2-04-03	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	33	ST030	44	IDU34	[2:{AABBCCDDEEFF02000124}]	\N	\N	\N	\N	\N
147	2-04-02	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	32	ST030	44	IDU33	[2:{AABBCCDDEEFF02000123}]	\N	\N	\N	\N	\N
146	2-04-01	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	31	ST030	44	IDU32	[2:{AABBCCDDEEFF02000122}]	\N	\N	\N	\N	\N
164	3-05-09	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	25	ST030	46	\N	[2:{AABBCCDDEEFF02000140}]	\N	\N	\N	\N	\N
179	3-06-10	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	16	ST030	50	IDU35	[2:{AABBCCDDEEFF02000155}]	\N	\N	\N	\N	\N
132	2-01-05	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	1	17	ST035	38	IDU20	[2:{AABBCCDDEEFF02000108}]	\N	\N	\N	\N	\N
160	3-05-02	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	21	ST030	46	\N	[2:{AABBCCDDEEFF02000136}]	\N	\N	\N	\N	\N
176	3-06-07	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	37	ST030	49	IDU27	[2:{AABBCCDDEEFF02000152}]	\N	\N	\N	\N	\N
190	3-07-24	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	27	ST008	13	\N	[2:{AABBCCDDEEFF02000166}]	\N	\N	\N	\N	\N
134	2-02-02	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	19	ST035	38	IDU22	[2:{AABBCCDDEEFF02000110}]	\N	\N	\N	\N	\N
177	3-06-08	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	38	ST030	49	IDU28	[2:{AABBCCDDEEFF02000153}]	\N	\N	\N	\N	\N
178	3-06-09	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	15	ST030	49	IDU29	[2:{AABBCCDDEEFF02000154}]	\N	\N	\N	\N	\N
133	2-02-01	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	18	ST035	38	IDU21	[2:{AABBCCDDEEFF02000109}]	\N	\N	\N	\N	\N
151	2-04-06	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	36	ST030	45	\N	[2:{AABBCCDDEEFF02000127}]	\N	\N	\N	\N	\N
155	2-04-10	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	16	ST030	45	\N	[2:{AABBCCDDEEFF02000131}]	\N	\N	\N	\N	\N
135	2-02-03	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	1	20	ST035	38	IDU23	[2:{AABBCCDDEEFF02000111}]	\N	\N	\N	\N	\N
136	2-02-04	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	21	ST035	38	IDU24	[2:{AABBCCDDEEFF02000112}]	\N	\N	\N	\N	\N
130	2-01-03	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	1	15	ST035	38	\N	[2:{AABBCCDDEEFF02000106}]	\N	\N	\N	\N	\N
200	3-09-04	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	37	ST008	13	\N	[2:{AABBCCDDEEFF02000176}]	\N	\N	\N	\N	\N
199	3-09-01	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	36	ST008	13	\N	[2:{AABBCCDDEEFF02000175}]	\N	\N	\N	\N	\N
198	3-08-27	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	35	ST021	24	\N	[2:{AABBCCDDEEFF02000174}]	\N	\N	\N	\N	\N
197	3-08-21	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	34	ST008	19	\N	[2:{AABBCCDDEEFF02000173}]	\N	\N	\N	\N	\N
196	3-08-18	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	33	ST008	18	\N	[2:{AABBCCDDEEFF02000172}]	\N	\N	\N	\N	\N
195	3-08-14	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	32	ST051	34	\N	[2:{AABBCCDDEEFF02000171}]	\N	\N	\N	\N	\N
191	3-07-25	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	28	ST008	17	\N	[2:{AABBCCDDEEFF02000167}]	\N	\N	\N	\N	\N
175	3-06-05	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	36	ST030	49	\N	[2:{AABBCCDDEEFF02000151}]	\N	\N	\N	\N	\N
174	3-06-04	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	35	ST030	49	\N	[2:{AABBCCDDEEFF02000150}]	\N	\N	\N	\N	\N
173	3-06-03	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	34	ST030	49	\N	[2:{AABBCCDDEEFF02000149}]	\N	\N	\N	\N	\N
131	2-01-04	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	16	ST035	38	\N	[2:{AABBCCDDEEFF02000107}]	\N	\N	\N	\N	\N
153	2-04-08	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	38	ST030	45	\N	[2:{AABBCCDDEEFF02000129}]	\N	\N	\N	\N	\N
152	2-04-07	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	37	ST030	45	\N	[2:{AABBCCDDEEFF02000128}]	\N	\N	\N	\N	\N
150	2-04-05	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	35	ST030	45	\N	[2:{AABBCCDDEEFF02000126}]	\N	\N	\N	\N	\N
149	2-04-04	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	34	ST030	45	\N	[2:{AABBCCDDEEFF02000125}]	\N	\N	\N	\N	\N
140	2-03-01	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	1	25	ST030	44	\N	[2:{AABBCCDDEEFF02000116}]	\N	\N	\N	\N	\N
157	2-11-02	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	18	ST030	45	IDU30	[2:{AABBCCDDEEFF02000133}]	\N	\N	\N	\N	\N
154	2-04-09	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	15	ST030	45	\N	[2:{AABBCCDDEEFF02000130}]	\N	\N	\N	\N	\N
138	2-02-06	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	1	23	ST035	38	IDU26	[2:{AABBCCDDEEFF02000114}]	\N	\N	\N	\N	\N
137	2-02-05	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	22	ST035	38	IDU25	[2:{AABBCCDDEEFF02000113}]	\N	\N	\N	\N	\N
158	2-11-03	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	19	ST030	45	IDU31	[2:{AABBCCDDEEFF02000134}]	\N	\N	\N	\N	\N
139	2-02-07	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	2	24	ST030	44	\N	[2:{AABBCCDDEEFF02000115}]	\N	\N	\N	\N	\N
203	3-09-16	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	16	ST030	44	\N	[2:{AABBCCDDEEFF02000179}]	\N	\N	\N	\N	\N
201	3-09-05	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	38	ST008	12	\N	[2:{AABBCCDDEEFF02000177}]	\N	\N	\N	\N	\N
189	3-07-23	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	26	ST008	12	\N	[2:{AABBCCDDEEFF02000165}]	\N	\N	\N	\N	\N
202	3-09-06	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	15	ST008	12	\N	[2:{AABBCCDDEEFF02000178}]	\N	\N	\N	\N	\N
187	3-07-13	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	24	ST030	50	\N	[2:{AABBCCDDEEFF02000163}]	\N	\N	\N	\N	\N
211	3-10-07	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	24	ST030	44	\N	[2:{AABBCCDDEEFF02000187}]	\N	\N	\N	\N	\N
185	3-07-09	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	22	ST030	50	\N	[2:{AABBCCDDEEFF02000161}]	\N	\N	\N	\N	\N
159	3-05-01	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	20	ST030	46	\N	[2:{AABBCCDDEEFF02000135}]	\N	\N	\N	\N	\N
183	3-07-05	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	20	ST030	50	\N	[2:{AABBCCDDEEFF02000159}]	\N	\N	\N	\N	\N
182	3-07-04	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	19	ST030	50	\N	[2:{AABBCCDDEEFF02000158}]	\N	\N	\N	\N	\N
181	3-07-03	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	18	ST030	50	\N	[2:{AABBCCDDEEFF02000157}]	\N	\N	\N	\N	\N
180	3-06-11	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	17	ST030	50	\N	[2:{AABBCCDDEEFF02000156}]	\N	\N	\N	\N	\N
194	3-08-08	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	31	ST030	46	\N	[2:{AABBCCDDEEFF02000170}]	\N	\N	\N	\N	\N
193	3-08-06	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	30	ST030	45	\N	[2:{AABBCCDDEEFF02000169}]	\N	\N	\N	\N	\N
192	3-07-29	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	29	ST030	50	\N	[2:{AABBCCDDEEFF02000168}]	\N	\N	\N	\N	\N
212	3-10-08	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	25	ST030	44	\N	[2:{AABBCCDDEEFF02000188}]	\N	\N	\N	\N	\N
188	3-07-20	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	25	ST030	50	\N	[2:{AABBCCDDEEFF02000164}]	\N	\N	\N	\N	\N
210	3-10-06	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	23	ST030	44	\N	[2:{AABBCCDDEEFF02000186}]	\N	\N	\N	\N	\N
186	3-07-12	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	23	ST030	50	\N	[2:{AABBCCDDEEFF02000162}]	\N	\N	\N	\N	\N
209	3-10-05	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	22	ST030	44	\N	[2:{AABBCCDDEEFF02000185}]	\N	\N	\N	\N	\N
208	3-10-04	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	21	ST030	44	\N	[2:{AABBCCDDEEFF02000184}]	\N	\N	\N	\N	\N
207	3-10-03	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	1	20	ST030	44	\N	[2:{AABBCCDDEEFF02000183}]	\N	\N	\N	\N	\N
184	3-07-07	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	21	ST030	50	\N	[2:{AABBCCDDEEFF02000160}]	\N	\N	\N	\N	\N
206	3-10-02	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	19	ST030	44	\N	[2:{AABBCCDDEEFF02000182}]	\N	\N	\N	\N	\N
172	3-06-02	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	33	ST030	49	\N	[2:{AABBCCDDEEFF02000148}]	\N	\N	\N	\N	\N
171	3-06-01	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	32	ST030	49	\N	[2:{AABBCCDDEEFF02000147}]	\N	\N	\N	\N	\N
170	3-05-30	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	31	ST030	49	\N	[2:{AABBCCDDEEFF02000146}]	\N	\N	\N	\N	\N
168	3-05-19	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	29	ST030	46	\N	[2:{AABBCCDDEEFF02000144}]	\N	\N	\N	\N	\N
167	3-05-17	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	28	ST030	46	\N	[2:{AABBCCDDEEFF02000143}]	\N	\N	\N	\N	\N
166	3-05-15	\N	2015-08-13 20:14:44	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	\N	27	ST030	46	\N	[2:{AABBCCDDEEFF02000142}]	\N	\N	\N	\N	\N
205	3-10-01	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	18	ST030	44	\N	[2:{AABBCCDDEEFF02000181}]	\N	\N	\N	\N	\N
145	2-03-06	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	30	ST030	44	\N	[2:{AABBCCDDEEFF02000121}]	\N	\N	\N	\N	\N
144	2-03-05	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	29	ST030	44	\N	[2:{AABBCCDDEEFF02000120}]	\N	\N	\N	\N	\N
143	2-03-04	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	28	ST030	44	\N	[2:{AABBCCDDEEFF02000119}]	\N	\N	\N	\N	\N
142	2-03-03	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	27	ST030	44	\N	[2:{AABBCCDDEEFF02000118}]	\N	\N	\N	\N	\N
141	2-03-02	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	\N	26	ST030	44	\N	[2:{AABBCCDDEEFF02000117}]	\N	\N	\N	\N	\N
204	3-09-26	\N	2015-08-13 20:14:45	2015-08-13 07:39:43	\N	3	\N	2015-08-13 13:09:43	\N	6	2	17	ST030	44	\N	[2:{AABBCCDDEEFF02000180}]	\N	\N	\N	\N	\N
156	2-11-01	\N	2015-08-13 20:14:43	2015-08-13 07:39:43	\N	2	\N	2015-08-13 13:09:43	\N	5	1	17	ST030	45	\N	[2:{AABBCCDDEEFF02000132}]	\N	\N	\N	\N	\N
26	0-01-02	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	25	AP open office AC26	\N	2015-08-13 13:09:43	\N	3	2	16	ST008	12	IDU26	[2:{AABBCCDDEEFF02000002}]	b_indoor.svg	1.32193623259124	103.93143487148300	1.32193070200749	103.93142908797000
27	0-01-03	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP open office AC27	\N	2015-08-13 13:09:43	\N	3	1	17	ST008	12	IDU27	[2:{AABBCCDDEEFF02000003}]	c_indoor.svg	1.32182520193039	103.93143512294000	1.32181967134641	103.93142925560800
28	0-01-04	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP open office AC28	\N	2015-08-13 13:09:43	\N	3	2	18	ST008	12	\N	[2:{AABBCCDDEEFF02000004}]	d_indoor.svg	1.32176763357828	103.93143336274000	1.32176210299416	103.93142757922700
29	0-01-05	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	28	AP other room AC29	\N	2015-08-13 13:09:43	\N	3	1	19	ST008	12	\N	[2:{AABBCCDDEEFF02000005}]	\N	1.32197142721476	103.93145238966000	1.32196589663110	103.93144652232800
30	0-01-06	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	27	AP meeting room AC30	\N	2015-08-13 13:09:43	\N	3	2	20	ST008	12	\N	[2:{AABBCCDDEEFF02000006}]	\N	1.32173956167392	103.93145347930800	1.32173411488646	103.93144777961400
31	0-01-07	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	34	AP meeting room AC31	\N	2015-08-13 13:09:43	\N	3	1	21	ST008	12	\N	[2:{AABBCCDDEEFF02000007}]	\N	1.32174014825102	103.93149840630900	1.32173461766684	103.93149262279600
32	0-01-08	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP open office AC32	\N	2015-08-13 13:09:43	\N	3	2	22	ST008	12	\N	[2:{AABBCCDDEEFF02000008}]	\N	1.32193648398140	103.93148868330100	1.32193095339766	103.93148289978800
33	0-01-09	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP open office AC33	\N	2015-08-13 13:09:43	\N	3	1	23	ST008	12	\N	[2:{AABBCCDDEEFF02000009}]	\N	1.32182536952386	103.93148918621500	1.32181992273661	103.93148340270200
34	0-01-10	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP open office AC34	\N	2015-08-13 13:09:43	\N	3	2	24	ST008	12	\N	[2:{AABBCCDDEEFF02000010}]	\N	1.32176754978154	103.93148700692100	1.32176201919743	103.93148122340700
35	0-12-01	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP open office AC35	\N	2015-08-13 13:09:43	\N	3	1	25	ST008	12	\N	[2:{AABBCCDDEEFF02000011}]	\N	1.32193447286004	103.93154333331000	1.32192902607303	103.93153746597800
36	0-13-01	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP open office AC36	\N	2015-08-13 13:09:43	\N	3	2	26	ST008	12	\N	[2:{AABBCCDDEEFF02000012}]	\N	1.32182587230422	103.93154299803400	1.32182025792350	103.93153746597800
37	0-12-02	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP open office AC37	\N	2015-08-13 13:09:43	\N	3	1	27	ST008	12	\N	[2:{AABBCCDDEEFF02000013}]	\N	1.32176754978154	103.93153880708200	1.32176201919743	103.93153293975000
38	0-13-02	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP meeting room AC38	\N	2015-08-13 13:09:43	\N	3	2	28	ST008	12	\N	[2:{AABBCCDDEEFF02000014}]	\N	1.32173553943087	103.93154266275800	1.32173009264341	103.93153687924400
39	0-12-03	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP meeting room AC39	\N	2015-08-13 13:09:43	\N	3	1	29	ST008	12	\N	[2:{AABBCCDDEEFF02000015}]	\N	1.32173746675566	103.93157887257900	1.32173193617148	103.93157308906600
40	0-13-03	\N	2015-08-13 20:14:39	2015-08-13 07:39:43	\N	AP other room AC40	\N	2015-08-13 13:09:43	\N	3	2	30	ST008	13	IDU37	[2:{AABBCCDDEEFF02000016}]	\N	1.32174324873002	103.93160887979300	1.32173771814585	103.93160301246000
41	0-13-04	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP meeting room AC41	\N	2015-08-13 13:09:43	\N	3	2	31	ST008	13	IDU38	[2:{AABBCCDDEEFF02000017}]	\N	1.32174450568097	103.93163000218900	1.32173889130007	103.93162413485600
42	0-13-05	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP other room AC42	\N	2015-08-13 13:09:43	\N	3	1	32	ST008	13	IDU39	[2:{AABBCCDDEEFF02000018}]	\N	1.32174031584449	103.93167526446600	1.32173478526030	103.93166956477200
43	0-13-06	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP meeting room AC43	\N	2015-08-13 13:09:43	\N	3	2	33	ST008	13	IDU40	[2:{AABBCCDDEEFF02000019}]	\N	1.32193464045350	103.93159639075700	1.32192919366646	103.93159069106300
44	0-13-07	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP meeting room AC44	\N	2015-08-13 13:09:43	\N	3	1	34	ST008	13	IDU41	[2:{AABBCCDDEEFF02000020}]	\N	1.32176754978154	103.93159303799600	1.32176201919743	103.93158717066300
45	0-13-08	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC45	\N	2015-08-13 13:09:43	\N	3	2	35	ST008	13	IDU42	[2:{AABBCCDDEEFF02000021}]	\N	1.32176788496847	103.93164584398600	1.32176243818106	103.93164014429200
46	0-13-09	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC46	\N	2015-08-13 13:09:43	\N	3	1	36	ST008	13	\N	[2:{AABBCCDDEEFF02000022}]	\N	1.32176788496847	103.93168825641600	1.32176227058761	103.93168238908400
47	0-13-10	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC47	\N	2015-08-13 13:09:43	\N	3	2	37	ST008	13	\N	[2:{AABBCCDDEEFF02000023}]	\N	1.32186592713941	103.93159965969900	1.32186048035223	103.93159379236700
48	0-13-11	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC48	\N	2015-08-13 13:09:43	\N	3	1	38	ST008	13	\N	[2:{AABBCCDDEEFF02000024}]	\N	1.32183785523616	103.93164693363300	1.32183240844891	103.93164106630100
49	0-13-12	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC49	\N	2015-08-13 13:09:43	\N	3	2	15	ST008	13	\N	[2:{AABBCCDDEEFF02000025}]	\N	1.32184606731534	103.93167601883700	1.32184062052814	103.93167015150500
50	0-15-01	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC50	\N	2015-08-13 13:09:43	\N	3	1	16	ST008	13	\N	[2:{AABBCCDDEEFF02000026}]	\N	1.32194997525381	103.93156931721000	1.32194444467010	103.93156344987800
51	0-15-02	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC51	\N	2015-08-13 13:09:43	\N	3	2	17	ST008	13	IDU3	[2:{AABBCCDDEEFF02000027}]	\N	1.32197720918857	103.93164835855700	1.32197176240166	103.93164249122400
52	0-15-03	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC52	\N	2015-08-13 13:09:43	\N	3	1	18	ST008	13	IDU4	[2:{AABBCCDDEEFF02000028}]	\N	1.32193195895835	103.93164659835700	1.32192642837458	103.93164081484400
53	0-16-01	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC53	\N	2015-08-13 13:09:43	\N	3	2	19	ST008	13	IDU5	[2:{AABBCCDDEEFF02000029}]	\N	1.32193212655179	103.93168607712100	1.32192651217132	103.93168029360800
54	0-16-02	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC54	\N	2015-08-13 13:09:43	\N	3	1	20	ST008	13	IDU6	[2:{AABBCCDDEEFF02000030}]	\N	1.32193690296502	103.93176184952600	1.32193137238128	103.93175606601200
55	0-16-03	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC55	\N	2015-08-13 13:09:43	\N	3	2	21	ST008	13	\N	[2:{AABBCCDDEEFF02000031}]	\N	1.32183090010784	103.93176201716400	1.32182536952386	103.93175623365000
56	0-12-04	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC56	\N	2015-08-13 13:09:43	\N	3	2	22	ST008	17	\N	[2:{AABBCCDDEEFF02000032}]	\N	1.32177299656892	103.93176025696400	1.32176754978154	103.93175447345100
57	0-12-05	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC57	\N	2015-08-13 13:09:43	\N	3	1	23	ST008	17	\N	[2:{AABBCCDDEEFF02000033}]	\N	1.32193137238128	103.93182060666700	1.32192592559425	103.93181473933500
58	0-12-06	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC58	\N	2015-08-13 13:09:43	\N	3	2	24	ST008	17	\N	[2:{AABBCCDDEEFF02000034}]	\N	1.32186969799206	103.93180937491700	1.32186416740817	103.93180359140300
59	0-12-07	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC59	\N	2015-08-13 13:09:43	\N	3	1	25	ST008	17	\N	[2:{AABBCCDDEEFF02000035}]	\N	1.32178405773709	103.93180501632700	1.32177861094974	103.93179923281400
60	0-12-08	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AG meeting room AC60	\N	2015-08-13 13:09:43	\N	3	\N	26	ST008	17	\N	[2:{AABBCCDDEEFF02000036}]	\N	1.32173486905704	103.93173393778800	1.32172933847284	103.93172815427500
61	0-12-09	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AG meeting room AC61	\N	2015-08-13 13:09:43	\N	3	\N	27	ST008	17	\N	[2:{AABBCCDDEEFF02000037}]	\N	1.32173470146356	103.93176905796200	1.32172925467612	103.93176319063000
62	0-12-10	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP meeting room AC62	\N	2015-08-13 13:09:43	\N	3	\N	28	ST008	17	\N	[2:{AABBCCDDEEFF02000038}]	\N	1.32185319003707	103.93189864218500	1.32184765945314	103.93189285867200
63	0-12-11	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP meeting room AC63	\N	2015-08-13 13:09:43	\N	3	\N	29	ST008	17	\N	[2:{AABBCCDDEEFF02000039}]	\N	1.32193413767315	103.93189839072800	1.32192860708941	103.93189260721500
64	0-12-12	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP meeting room AC64	\N	2015-08-13 13:09:43	\N	3	\N	30	ST008	17	\N	[2:{AABBCCDDEEFF02000041}]	\N	1.32176092983996	103.93186209708800	1.32175539925581	103.93185631357400
65	0-12-13	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP meeting room AC65	\N	2015-08-13 13:09:43	\N	3	\N	31	ST008	17	\N	[2:{AABBCCDDEEFF02000041}]	\N	1.32181866578569	103.93189637907100	1.32181321899841	103.93189059555800
66	0-12-14	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AG meeting room AC66	\N	2015-08-13 13:09:43	\N	3	\N	32	ST008	18	\N	[2:{AABBCCDDEEFF02000042}]	\N	1.32173436627665	103.93179588005200	1.32172875189572	103.93179009653900
67	0-12-15	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP open office AC67	\N	2015-08-13 13:09:43	\N	3	\N	33	ST008	18	\N	[2:{AABBCCDDEEFF02000043}]	\N	1.32176218679088	103.93190660499300	1.32175674000349	103.93190090529900
68	0-12-16	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP other room AC68	\N	2015-08-13 13:09:43	\N	3	\N	34	ST008	18	\N	[2:{AABBCCDDEEFF02000044}]	\N	1.32196589663110	103.93132154815200	1.32196044984415	103.93131576463900
69	0-12-17	\N	2015-08-13 20:14:40	2015-08-13 07:39:43	\N	AP other room AC69	\N	2015-08-13 13:09:43	\N	3	\N	35	ST008	18	\N	[2:{AABBCCDDEEFF02000045}]	\N	1.32192835569923	103.93131978795200	1.32192299270891	103.93131392062000
70	0-12-18	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD test room AC70	\N	2015-08-13 13:09:43	\N	3	\N	36	ST008	18	\N	[2:{AABBCCDDEEFF02000046}]	\N	1.32198902452634	103.93107931115000	1.32198341014598	103.93107344381800
71	0-12-19	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD test room AC71	\N	2015-08-13 13:09:43	\N	3	\N	37	ST008	18	\N	[2:{AABBCCDDEEFF02000055}]	\N	1.32201022509678	103.93107922733100	1.32200469451320	103.93107335999900
72	1-17-01	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD test room AC72	\N	2015-08-13 13:09:43	\N	4	\N	38	ST008	18	\N	[2:{AABBCCDDEEFF02000047}]	\N	1.32199824216569	103.93111719735300	1.32199254398864	103.93111141383900
73	1-17-02	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD test room AC73	\N	2015-08-13 13:09:43	\N	4	1	15	ST008	18	\N	[2:{AABBCCDDEEFF02000048}]	\N	1.32199572826404	103.93115784958300	1.32199019768044	103.93115206607000
74	1-17-03	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD test room AC74	\N	2015-08-13 13:09:43	\N	4	2	16	ST008	18	\N	[2:{AABBCCDDEEFF02000049}]	\N	1.32201265520169	103.93121685818100	1.32200695702468	103.93121099084900
75	1-17-04	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD test room AC75	\N	2015-08-13 13:09:43	\N	4	1	17	ST008	18	\N	[2:{AABBCCDDEEFF02000050}]	\N	1.32198835415256	103.93121601999100	1.32198282356894	103.93121015265900
76	1-17-05	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	AP open office AC76	\N	2015-08-13 13:09:43	\N	4	1	18	ST008	19	\N	[2:{AABBCCDDEEFF02000051}]	\N	1.32194863450625	103.93104008384300	1.32194302012581	103.93103421651100
77	1-17-06	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD other room AC77	\N	2015-08-13 13:09:43	\N	4	2	19	ST008	19	\N	[2:{AABBCCDDEEFF02000052}]	\N	1.32194067381757	103.93100814879200	1.32193522703054	103.93100219764100
78	1-17-07	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD other room AC78	\N	2015-08-13 13:09:43	\N	4	1	20	ST008	19	\N	[2:{AABBCCDDEEFF02000053}]	\N	1.32195374610633	103.93110361867000	1.32194821552264	103.93109783515600
79	1-17-08	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	AP open office AC79	\N	2015-08-13 13:09:43	\N	4	1	21	ST008	19	\N	[2:{AABBCCDDEEFF02000054}]	\N	1.32194838311609	103.93114519290900	1.32194285253236	103.93113932557700
80	1-18-01	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC80	\N	2015-08-13 13:09:43	\N	4	2	22	ST008	19	\N	[2:{AABBCCDDEEFF02000056}]	\N	1.32187028456914	103.93102709189400	1.32186475398525	103.93102130838000
81	1-18-02	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC81	\N	2015-08-13 13:09:43	\N	4	1	23	ST008	19	\N	[2:{AABBCCDDEEFF02000057}]	\N	1.32187036836587	103.93106254734400	1.32186483778196	103.93105668001200
82	1-18-03	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC82	\N	2015-08-13 13:09:43	\N	4	2	24	ST008	19	\N	[2:{AABBCCDDEEFF02000058}]	\N	1.32187078734950	103.93109917626100	1.32186525676560	103.93109339274800
83	1-18-04	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC83	\N	2015-08-13 13:09:43	\N	4	1	25	ST008	19	\N	[2:{AABBCCDDEEFF02000059}]	\N	1.32187045216259	103.93113505080600	1.32186492157870	103.93112918347400
84	1-18-05	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC84	\N	2015-08-13 13:09:43	\N	4	2	26	ST008	19	\N	[2:{AABBCCDDEEFF02000060}]	\N	1.32187061975605	103.93116849460000	1.32186500537543	103.93116254344900
85	1-18-06	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC85	\N	2015-08-13 13:09:43	\N	4	\N	27	ST008	19	\N	[2:{AABBCCDDEEFF02000061}]	\N	1.32187087114622	103.93120679989800	1.32186525676560	103.93120093256500
86	1-18-07	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC86	\N	2015-08-13 13:09:43	\N	4	1	28	ST021	24	IDU43	[2:{AABBCCDDEEFF02000062}]	\N	1.32187036836587	103.93124225534800	1.32186492157870	103.93123647183500
87	1-18-08	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD other room AC87	\N	2015-08-13 13:09:43	\N	4	\N	29	ST021	24	IDU44	[2:{AABBCCDDEEFF02000063}]	\N	1.32182662647475	103.93099716849900	1.32182117968750	103.93099138498600
88	1-18-09	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD meeting room AC88	\N	2015-08-13 13:09:43	\N	4	\N	30	ST021	24	IDU45	[2:{AABBCCDDEEFF02000064}]	\N	1.32184573212844	103.93104670554700	1.32184020154450	103.93104092203400
89	1-19-01	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD meeting room AC89	\N	2015-08-13 13:09:43	\N	4	\N	31	ST021	24	IDU46	[2:{AABBCCDDEEFF02000065}]	\N	1.32184564833172	103.93108467556800	1.32184020154450	103.93107880823600
90	1-19-02	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD meeting room AC90	\N	2015-08-13 13:09:43	\N	4	\N	32	ST021	24		[2:{AABBCCDDEEFF02000066}]	\N	1.32184506175462	103.93112046629500	1.32183961496742	103.93111443132500
91	1-19-03	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD meeting room AC91	\N	2015-08-13 13:09:43	\N	4	\N	33	ST021	24	\N	[2:{AABBCCDDEEFF02000067}]	\N	1.32184422378737	103.93114527672800	1.32183886079688	103.93113949321500
92	1-19-04	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD meeting room AC92	\N	2015-08-13 13:09:43	\N	4	\N	34	ST021	24	\N	[2:{AABBCCDDEEFF02000068}]	\N	1.32183986635760	103.93117553539900	1.32183433577363	103.93116966806700
93	1-19-05	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD other room AC93	\N	2015-08-13 13:09:43	\N	4	\N	35	ST021	24	\N	[2:{AABBCCDDEEFF02000069}]	\N	1.32183659828526	103.93121233195400	1.32183098390456	103.93120654844000
94	1-19-06	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD other room AC94	\N	2015-08-13 13:09:43	\N	4	\N	36	ST021	24	\N	[2:{AABBCCDDEEFF02000070}]	\N	1.32185285485017	103.93121199667800	1.32184740806297	103.93120621316400
95	1-20-01	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC95	\N	2015-08-13 13:09:43	\N	4	\N	37	ST021	24	\N	[2:{AABBCCDDEEFF02000071}]	\N	1.32178020308759	103.93107637748400	1.32177467250350	103.93107059397100
96	1-20-02	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC96	\N	2015-08-13 13:09:43	\N	4	\N	38	ST021	24	\N	[2:{AABBCCDDEEFF02000072}]	\N	1.32178531468801	103.93111049183000	1.32177986790067	103.93110470831700
97	1-20-03	\N	2015-08-13 20:14:41	2015-08-13 07:39:43	\N	PRD open office AC97	\N	2015-08-13 13:09:43	\N	4	1	15	ST021	24	IDU7	[2:{AABBCCDDEEFF02000073}]	\N	1.32178539848476	103.93115189843200	1.32177995169740	103.93114611491900
98	1-20-04	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD open office AC98	\N	2015-08-13 13:09:43	\N	4	2	16	ST021	24	IDU8	[2:{AABBCCDDEEFF02000074}]	\N	1.32177978410394	103.93120227367000	1.32177416972312	103.93119640633800
99	1-20-05	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD open office AC99	\N	2015-08-13 13:09:43	\N	4	1	17	ST021	24	IDU9	[2:{AABBCCDDEEFF02000075}]	\N	1.32176612523715	103.93104000002400	1.32176067844976	103.93103413269200
100	1-20-06	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD open office AC100	\N	2015-08-13 13:09:43	\N	4	2	18	ST021	24	IDU10	[2:{AABBCCDDEEFF02000076}]	\N	1.32176947710631	103.93111065946800	1.32176403031892	103.93110479213600
101	1-21-01	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD open office AC101	\N	2015-08-13 13:09:43	\N	4	2	19	ST021	24	IDU11	[2:{AABBCCDDEEFF02000077}]	\N	1.32176972849649	103.93115223370800	1.32176411411566	103.93114645019500
102	1-21-02	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD store room AC102	\N	2015-08-13 13:09:43	\N	4	1	20	ST021	24	IDU12	[2:{AABBCCDDEEFF02000078}]	\N	1.32175087423242	103.93119456231900	1.32174534364827	103.93119179629100
103	1-21-03	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD other room AC103	\N	2015-08-13 13:09:43	\N	4	2	21	ST021	24	IDU13	[2:{AABBCCDDEEFF02000079}]	\N	1.32176051085629	103.93122507244600	1.32175506406890	103.93121928893300
104	1-21-04	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD other room AC104	\N	2015-08-13 13:09:43	\N	4	2	22	ST021	24	IDU14	[2:{AABBCCDDEEFF02000080}]	\N	1.32173201996820	103.93122465335100	1.32172657318073	103.93121895365700
105	1-21-05	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD other room AC105	\N	2015-08-13 13:09:43	\N	4	1	23	ST021	24	IDU15	[2:{AABBCCDDEEFF02000081}]	\N	1.32173931028373	103.93099322900500	1.32173377969954	103.93098736167300
106	1-21-06	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP open office AC106	\N	2015-08-13 13:09:43	\N	4	2	24	ST051	34	\N	[2:{AABBCCDDEEFF02000082}]	\N	1.32200285098535	103.93128676325400	1.32199732040175	103.93128097974100
107	1-22-01	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD test room AC107	\N	2015-08-13 13:09:43	\N	4	1	25	ST051	34	\N	[2:{AABBCCDDEEFF02000083}]	\N	1.32194553402751	103.93127829753200	1.32193991964705	103.93127243019900
108	1-22-02	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD test room AC108	\N	2015-08-13 13:09:43	\N	4	2	26	ST051	34	IDU108	[2:{AABBCCDDEEFF02000084}]	\N	1.32192064640065	103.93128718234900	1.32191528341031	103.93128131501700
109	1-22-03	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD store room AC109	\N	2015-08-13 13:09:43	\N	4	1	27	ST051	34	IDU109	[2:{AABBCCDDEEFF02000085}]	\N	1.32189793748841	103.93127921954100	1.32189249070131	103.93127343602800
110	1-22-04	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD meeting room AC110	\N	2015-08-13 13:09:43	\N	4	1	28	ST051	34	\N	[2:{AABBCCDDEEFF02000086}]	\N	1.32184120710522	103.93128776908200	1.32183550892780	103.93128190175000
111	1-22-05	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD meeting room AC111	\N	2015-08-13 13:09:43	\N	4	1	29	ST051	34	\N	[2:{AABBCCDDEEFF02000087}]	\N	1.32181849819224	103.93128693089200	1.32181296760822	103.93128123119800
112	1-22-06	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD store room AC112	\N	2015-08-13 13:09:43	\N	4	2	30	ST051	34	\N	[2:{AABBCCDDEEFF02000088}]	\N	1.32177760538900	103.93128751762500	1.32177199100819	103.93128173411200
113	1-23-01	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD meeting room AC113	\N	2015-08-13 13:09:43	\N	4	2	31	ST051	34	\N	[2:{AABBCCDDEEFF02000089}]	\N	1.32175221498010	103.93128827199600	1.32174676819268	103.93128240466400
114	1-23-02	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP meeting room AC114	\N	2015-08-13 13:09:43	\N	4	2	32	ST051	34	\N	[2:{AABBCCDDEEFF02000090}]	\N	1.32189500460304	103.93132045850500	1.32188947401921	103.93131467499100
115	1-23-03	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP meeting room AC115	\N	2015-08-13 13:09:43	\N	4	1	33	ST051	34	\N	[2:{AABBCCDDEEFF02000091}]	\N	1.32186693270012	103.93131945267600	1.32186156970966	103.93131358534400
116	1-23-04	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP meeting room AC116	\N	2015-08-13 13:09:43	\N	4	1	34	ST051	34	\N	[2:{AABBCCDDEEFF02000092}]	\N	1.32181196204750	103.93133454010200	1.32180643146348	103.93132875658900
117	1-23-05	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP meeting room AC117	\N	2015-08-13 13:09:43	\N	4	1	35	ST008	\N	IDU1	[2:{AABBCCDDEEFF02000093}]	z_indoor.svg	1.32173981306409	103.93131953649500	1.32173436627665	103.93131366916300
118	1-23-06	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP meeting room AC118	\N	2015-08-13 13:09:43	\N	4	2	36	ST021	\N	IDU2	[2:{AABBCCDDEEFF02000094}]	\N	1.32196799154915	103.93136479877200	1.32196246096549	103.93135909907800
119	1-24-01	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP open office AC119	\N	2015-08-13 13:09:43	\N	4	2	37	ST051	34	IDU16	[2:{AABBCCDDEEFF02000095}]	\N	1.32193665157484	103.93138105966500	1.32193103719439	103.93137527615100
120	1-24-02	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP open office AC120	\N	2015-08-13 13:09:43	\N	4	2	38	ST051	34	IDU17	[2:{AABBCCDDEEFF02000096}]	\N	1.32183969876413	103.93136932500000	1.32183416818019	103.93136345766800
121	1-24-03	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP open office AC121	\N	2015-08-13 13:09:43	\N	4	2	15	ST051	34	IDU18	[2:{AABBCCDDEEFF02000097}]	\N	1.32176662801753	103.93137720398900	1.32176101363667	103.93137133665700
122	1-24-04	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP open office AC122	\N	2015-08-13 13:09:43	\N	4	2	16	ST051	34	IDU19	[2:{AABBCCDDEEFF02000098}]	\N	1.32171618238615	103.93193487689100	1.32171065180193	103.93192909337800
123	1-24-05	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD other room AC123	\N	2015-08-13 13:09:43	\N	4	1	17	ST051	34	IDU18	[2:{AABBCCDDEEFF02000099}]	\N	1.32203544790961	103.93095618099300	1.32202991732610	103.93095031366100
124	1-24-06	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD other room AC124	\N	2015-08-13 13:09:43	\N	4	2	18	ST051	34	IDU19	[2:{AABBCCDDEEFF02000100}]	\N	1.32196438829011	103.93123471163500	1.32195885770642	103.93122884430300
125	1-24-07	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD meeting room AC125	\N	2015-08-13 13:09:43	\N	4	2	19	ST035	38	\N	[2:{AABBCCDDEEFF02000101}]	\N	1.32188997679955	103.93098752931100	1.32188444621571	103.93098166197800
126	1-24-08	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD other room AC126	\N	2015-08-13 13:09:43	\N	4	1	20	ST035	38	\N	[2:{AABBCCDDEEFF02000102}]	\N	1.32184229646267	103.93125114016500	1.32183684967544	103.93124535665200
127	1-24-09	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD other room AC127	\N	2015-08-13 13:09:43	\N	4	2	21	ST035	38	\N	[2:{AABBCCDDEEFF02000103}]	\N	1.32180567729293	103.93123286761600	1.32180014670891	103.93122716792200
128	2-01-01	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	PRD meeting room AC128	\N	2015-08-13 13:09:43	\N	5	2	22	ST035	38	\N	[2:{AABBCCDDEEFF02000104}]	\N	1.32186911141498	103.93128709853000	1.32186349703437	103.93128131501700
129	2-01-02	\N	2015-08-13 20:14:42	2015-08-13 07:39:43	\N	AP meeting room AC129	\N	2015-08-13 13:09:43	\N	5	2	23	ST035	38	\N	[2:{AABBCCDDEEFF02000105}]	\N	1.32197729298531	103.93148549817800	1.32197167860494	103.93147971466500
\.


--
-- TOC entry 2870 (class 0 OID 0)
-- Dependencies: 228
-- Name: indoorunits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunits_id_seq', 1, true);


--
-- TOC entry 2796 (class 0 OID 20020)
-- Dependencies: 247
-- Data for Name: modemaster; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY modemaster (id, modename) FROM stdin;
3	Fan
4	Dry
5	Auto
1	Heat
2	Cool
\.


--
-- TOC entry 2846 (class 0 OID 20222)
-- Dependencies: 297
-- Data for Name: winddirection_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY winddirection_master (id, winddirectionname) FROM stdin;
1	Swing
2	F1
3	F2
4	F3
5	F4
6	F5
7	Unset
\.


--
-- TOC entry 2778 (class 0 OID 19972)
-- Dependencies: 229
-- Data for Name: indoorunitslog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indoorunitslog (id, roomtemp, setpointtemperature, iseconavi, powerstatus, modeltype, rcprohibitfanspeed, rcprohibitmode, rcprohibitpwr, rcprohibitsettemp, rcprohibitvanepos, updateeconavi, fanspeed, indoorunit_id, acmode, flapmode, lastfilter_cleaning_date, status) FROM stdin;
232968	\N	20	\N	0	\N	\N	\N	\N	\N	\N	\N	2	168	2	1	\N	\N
232978	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	178	2	3	\N	\N
232979	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	179	2	3	\N	\N
232980	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	180	4	1	\N	\N
232981	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	4	181	2	1	\N	\N
232982	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	4	182	2	2	\N	\N
232983	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	183	4	3	\N	\N
232984	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	184	2	2	\N	\N
232948	\N	26	\N	0	\N	\N	\N	\N	\N	\N	\N	2	148	2	2	\N	\N
232949	\N	28	\N	0	\N	\N	\N	\N	\N	\N	\N	3	149	4	3	\N	\N
232950	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	150	2	3	\N	\N
232931	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	131	2	3	\N	\N
232938	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	138	2	7	\N	\N
232939	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	4	139	2	3	\N	\N
232940	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	140	2	3	\N	\N
232941	\N	26	\N	0	\N	\N	\N	\N	\N	\N	\N	4	141	2	3	\N	\N
232951	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	2	151	2	1	\N	\N
232952	\N	26	\N	0	\N	\N	\N	\N	\N	\N	\N	1	152	2	3	\N	\N
232953	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	153	2	3	\N	\N
232954	\N	21	\N	0	\N	\N	\N	\N	\N	\N	\N	1	154	2	3	\N	\N
232976	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	176	2	2	\N	\N
232942	\N	26	\N	0	\N	\N	\N	\N	\N	\N	\N	4	142	2	3	\N	\N
232943	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	3	143	2	1	\N	\N
232944	\N	26	\N	0	\N	\N	\N	\N	\N	\N	\N	2	144	2	1	\N	\N
232889	18	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	89	2	3	\N	\N
232890	90	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	90	2	3	\N	\N
232891	20	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	91	2	3	\N	\N
232892	21	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	92	2	3	\N	\N
232893	22	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	93	2	3	\N	\N
232894	23	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	94	2	3	\N	\N
232895	24	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	95	2	3	\N	\N
232896	25	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	96	2	3	\N	\N
232897	26	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	97	2	3	\N	\N
232898	27	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	98	2	3	\N	\N
232899	28	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	99	2	3	\N	\N
232900	29	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	100	2	3	\N	\N
232901	30	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	101	2	3	\N	\N
232902	31	24	\N	0	\N	\N	\N	\N	\N	\N	\N	4	102	2	3	\N	\N
232903	32	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	103	2	3	\N	\N
232904	33	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	104	2	3	\N	\N
232905	34	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	105	2	3	\N	\N
232907	36	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	107	2	3	\N	\N
232831	10	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	31	5	3	\N	\N
232834	13	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	34	1	6	\N	\N
232835	14	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	35	1	6	\N	\N
232836	15	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	36	1	6	\N	\N
232862	17	26	\N	0	\N	\N	\N	\N	\N	\N	\N	3	62	2	1	\N	\N
232863	1	30	\N	0	\N	\N	\N	\N	\N	\N	\N	1	63	4	1	\N	\N
232864	2	24	\N	0	\N	\N	\N	\N	\N	\N	\N	3	64	2	1	\N	\N
232865	3	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	65	2	1	\N	\N
232969	\N	18	\N	0	\N	\N	\N	\N	\N	\N	\N	1	169	2	1	\N	active
232971	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	3	171	2	2	\N	active
232972	\N	20	\N	0	\N	\N	\N	\N	\N	\N	\N	1	172	2	1	\N	active
232970	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	4	170	2	7	\N	inactive
232973	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	173	2	3	\N	inactive
232975	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	3	175	2	1	\N	inactive
232977	\N	18	\N	0	\N	\N	\N	\N	\N	\N	\N	1	177	2	1	\N	inactive
232945	\N	27	\N	0	\N	\N	\N	\N	\N	\N	\N	4	145	2	2	\N	\N
232962	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	162	2	3	\N	\N
232963	\N	20	\N	0	\N	\N	\N	\N	\N	\N	\N	2	163	2	1	\N	\N
232964	\N	18	\N	0	\N	\N	\N	\N	\N	\N	\N	1	164	2	1	\N	\N
232965	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	165	2	3	\N	\N
232966	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	3	166	2	2	\N	\N
232967	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	3	167	2	7	\N	\N
232988	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	4	188	2	1	\N	\N
232989	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	189	4	7	\N	\N
232932	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	132	2	3	\N	\N
232933	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	133	2	3	\N	\N
232934	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	134	2	1	\N	\N
232935	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	4	135	2	3	\N	\N
232936	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	4	136	2	3	\N	\N
232937	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	4	137	2	3	\N	\N
232990	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	3	190	2	2	\N	\N
232992	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	192	2	3	\N	\N
232993	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	2	193	2	3	\N	\N
232999	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	199	2	3	\N	\N
233000	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	200	2	3	\N	\N
233001	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	201	2	3	\N	\N
233002	\N	26	\N	0	\N	\N	\N	\N	\N	\N	\N	1	202	2	1	\N	\N
233003	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	203	2	3	\N	\N
233004	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	204	2	3	\N	\N
233005	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	205	2	3	\N	\N
233006	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	206	2	3	\N	\N
233007	\N	21	\N	0	\N	\N	\N	\N	\N	\N	\N	1	207	2	3	\N	\N
233008	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	3	208	2	2	\N	\N
233009	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	209	2	3	\N	\N
233010	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	210	2	3	\N	\N
233011	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	4	211	2	2	\N	\N
233012	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	212	2	3	\N	\N
232994	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	194	2	2	\N	\N
232906	35	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	106	1	6	\N	\N
232908	37	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	108	2	3	\N	\N
232909	38	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	109	2	3	\N	\N
232910	39	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	110	2	3	\N	\N
232911	40	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	111	2	3	\N	\N
232832	11	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	32	1	6	\N	\N
232833	12	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	33	1	6	\N	\N
232848	3	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	48	1	6	\N	\N
232849	4	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	49	1	6	\N	\N
232866	4	19	\N	0	\N	\N	\N	\N	\N	\N	\N	3	66	2	1	\N	\N
232867	5	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	67	1	6	\N	\N
232868	6	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	68	2	1	\N	\N
232869	7	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	69	2	1	\N	\N
232870	8	19	\N	0	\N	\N	\N	\N	\N	\N	\N	3	70	4	1	\N	\N
232871	9	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	71	2	1	\N	\N
232872	10	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	72	2	2	\N	\N
232873	11	19	\N	0	\N	\N	\N	\N	\N	\N	\N	3	73	2	2	\N	\N
232874	12	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	74	2	2	\N	\N
232875	13	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	75	2	3	\N	\N
232876	14	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	76	1	6	\N	\N
232877	15	19	\N	0	\N	\N	\N	\N	\N	\N	\N	2	77	2	2	\N	\N
232878	16	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	78	2	1	\N	\N
232879	17	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	79	1	6	\N	\N
232880	18	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	80	2	1	\N	\N
232881	19	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	81	2	2	\N	\N
232882	20	19	\N	0	\N	\N	\N	\N	\N	\N	\N	2	82	2	3	\N	\N
232883	21	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	83	2	1	\N	\N
232884	23	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	84	2	2	\N	\N
232885	22	22	\N	0	\N	\N	\N	\N	\N	\N	\N	4	85	2	2	\N	\N
232912	1	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	112	2	3	\N	\N
232919	8	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	119	1	6	\N	\N
232920	9	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	120	1	6	\N	\N
232921	10	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	121	1	6	\N	\N
232922	11	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	122	1	6	\N	\N
232991	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	3	191	2	3	\N	inactive
232926	\N	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	126	2	7	\N	\N
232927	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	127	2	3	\N	\N
232928	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	128	2	3	\N	\N
232985	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	3	185	2	1	\N	\N
232986	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	3	186	2	1	\N	\N
232987	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	187	2	3	\N	\N
232929	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	129	2	1	\N	\N
232930	\N	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	130	2	1	\N	\N
232995	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	195	2	1	\N	\N
232996	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	2	196	2	3	\N	\N
232997	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	197	2	3	\N	\N
232998	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	198	2	3	\N	\N
232946	\N	27	\N	0	\N	\N	\N	\N	\N	\N	\N	4	146	4	2	\N	\N
232947	\N	22	\N	0	\N	\N	\N	\N	\N	\N	\N	1	147	2	3	\N	\N
232955	\N	21	\N	0	\N	\N	\N	\N	\N	\N	\N	1	155	2	3	\N	\N
232956	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	156	2	3	\N	\N
232957	\N	22	\N	0	\N	\N	\N	\N	\N	\N	\N	1	157	2	3	\N	\N
232958	\N	23	\N	0	\N	\N	\N	\N	\N	\N	\N	1	158	2	3	\N	\N
232959	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	159	2	3	\N	\N
232960	\N	22	\N	0	\N	\N	\N	\N	\N	\N	\N	3	160	2	2	\N	\N
232961	\N	20	\N	0	\N	\N	\N	\N	\N	\N	\N	1	161	2	3	\N	\N
232925	14	25	\N	0	\N	\N	\N	\N	\N	\N	\N	3	125	2	3	\N	\N
232887	15	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	87	2	2	\N	\N
232888	16	19	\N	0	\N	\N	\N	\N	\N	\N	\N	4	88	2	7	\N	\N
232826	6	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	29	1	6	\N	\N
232827	7	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	30	1	6	\N	\N
232828	8	25.800000000000001	\N	1	\N	\N	\N	\N	\N	\N	\N	4	28	4	6	\N	active
232837	16	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	37	1	6	\N	\N
232838	17	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	38	2	3	\N	\N
232839	18	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	39	2	1	\N	\N
232840	19	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	40	2	3	\N	\N
232841	20	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	41	2	3	\N	\N
232842	21	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	42	4	3	\N	\N
232843	22	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	43	2	3	\N	\N
232974	\N	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	174	2	3	\N	active
232857	12	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	57	1	6	\N	\N
232913	2	25	\N	0	\N	\N	\N	\N	\N	\N	\N	2	113	2	3	\N	\N
232844	23	19	\N	0	\N	\N	\N	\N	\N	\N	\N	2	44	2	3	\N	\N
232845	24	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	45	1	6	\N	\N
232846	1	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	46	1	6	\N	\N
232847	2	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	47	1	6	\N	\N
232850	5	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	50	1	6	\N	\N
232851	6	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	51	1	6	\N	\N
232914	3	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	114	2	3	\N	\N
232915	4	25	\N	0	\N	\N	\N	\N	\N	\N	\N	1	115	2	3	\N	\N
232858	13	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	58	1	6	\N	\N
232852	7	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	52	1	6	\N	\N
232853	8	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	53	1	6	\N	\N
232854	9	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	54	1	6	\N	\N
232855	10	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	55	1	6	\N	\N
232856	11	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	56	1	6	\N	\N
232859	14	29.899999999999999	\N	0	\N	\N	\N	\N	\N	\N	\N	4	59	1	6	\N	\N
232860	15	20	\N	0	\N	\N	\N	\N	\N	\N	\N	4	60	4	1	\N	\N
232861	16	26	\N	0	\N	\N	\N	\N	\N	\N	\N	2	61	2	1	\N	\N
232916	5	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	116	2	3	\N	\N
232917	6	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	117	2	3	\N	active
232918	7	19	\N	0	\N	\N	\N	\N	\N	\N	\N	1	118	2	3	\N	\N
232923	12	24	\N	0	\N	\N	\N	\N	\N	\N	\N	1	123	2	2	\N	\N
232924	13	24	\N	0	\N	\N	\N	\N	\N	\N	\N	4	124	2	7	\N	\N
232825	5	25.800000000000001	1	1	\N	\N	\N	\N	\N	\N	\N	1	25	4	3	\N	active
232830	9	25.800000000000001	1	1	\N	\N	\N	\N	\N	\N	\N	1	27	4	3	\N	active
232886	17	22	1	1	\N	\N	\N	\N	\N	\N	\N	4	86	2	2	\N	\N
233013	\N	26.5	1	1	\N	\N	\N	\N	\N	\N	\N	2	26	2	6	\N	active
\.


--
-- TOC entry 2779 (class 0 OID 19975)
-- Dependencies: 230
-- Data for Name: indoorunitslog_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indoorunitslog_history (id, roomtemp, setpointtempreture, iseconavi, powerstatus, modeltype, rcprohibitfanspeed, rcprohibitmode, rcprohibitpwr, rcprohibitsettemp, rcprohibitvanepos, updateeconavi, fanspeed, indoorunit_id, acmode, flapmode, lastfilter_cleaning_date, status) FROM stdin;
\.


--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 231
-- Name: indoorunitslog_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitslog_history_id_seq', 1, false);


--
-- TOC entry 2872 (class 0 OID 0)
-- Dependencies: 232
-- Name: indoorunitslog_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitslog_id_seq', 1, false);


--
-- TOC entry 2782 (class 0 OID 19982)
-- Dependencies: 233
-- Data for Name: indoorunitstatistics; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indoorunitstatistics (id, logtime, filtersignclear, instantpower, otelectricheater, othighfscooling, othighfsheating, othighfsthermoff, othighfsthermon, otlowfscooling, otlowfsheating, otlowfsthermoff, otlowfsthermon, otmediumfscooling, otmediumfsheating, otmediumfsthermoff, otmediumfsthermon, settablefanspeed, settablemode, updatefiltersign, ventilation, indoorunit_id) FROM stdin;
\.


--
-- TOC entry 2783 (class 0 OID 19985)
-- Dependencies: 234
-- Data for Name: indoorunitstatistics_daily; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indoorunitstatistics_daily (id, logtime, filtersignclear, instantpower, otelectricheater, othighfscooling, othighfsheating, othighfsthermoff, othighfsthermon, otlowfscooling, otlowfsheating, otlowfsthermoff, otlowfsthermon, otmediumfscooling, otmediumfsheating, otmediumfsthermoff, otmediumfsthermon, settablefanspeed, settablemode, updatefiltersign, ventilation, indoorunit_id) FROM stdin;
1	2015-07-24 23:08:00	\N	\N	\N	17	119	10	13	24	59	14	17	31	56	35	25	\N	\N	\N	\N	26
2	2015-07-25 23:08:00	\N	\N	\N	18	120	11	23	25	60	15	18	32	57	3	1	\N	\N	\N	\N	26
3	2015-07-26 23:08:00	\N	\N	\N	19	121	12	25	26	61	16	19	33	58	3	4	\N	\N	\N	\N	26
4	2015-07-27 23:08:00	\N	\N	\N	20	122	13	26	27	62	17	20	34	59	18960	23280	\N	\N	\N	\N	26
5	2015-07-28 23:08:00	\N	\N	\N	21	123	14	27	28	63	18	21	35	60	20400	21900	\N	\N	\N	\N	26
6	2015-07-29 23:08:00	\N	\N	\N	22	124	15	28	29	64	19	22	36	61	21120	21180	\N	\N	\N	\N	26
7	2015-07-30 23:08:00	\N	\N	\N	23	125	16	29	30	65	20	23	37	62	20700	21600	\N	\N	\N	\N	26
8	2015-07-31 23:08:00	\N	\N	\N	24	126	17	30	31	66	21	24	38	63	10140	10080	\N	\N	\N	\N	26
9	2015-08-01 23:08:00	\N	\N	\N	25	127	18	31	32	67	22	25	39	64	5580	5880	\N	\N	\N	\N	26
10	2015-08-02 23:08:00	\N	\N	\N	26	128	19	32	33	68	23	26	40	65	19440	22860	\N	\N	\N	\N	26
11	2015-08-03 23:08:00	\N	\N	\N	27	129	20	33	34	69	24	27	41	66	21540	20760	\N	\N	\N	\N	26
12	2015-08-04 23:08:00	\N	\N	\N	28	130	21	34	35	70	25	28	42	67	19800	22500	\N	\N	\N	\N	26
13	2015-08-05 23:08:00	\N	\N	\N	29	131	22	35	36	71	26	29	43	68	21600	20640	\N	\N	\N	\N	26
14	2015-08-06 23:08:00	\N	\N	\N	30	132	23	36	37	72	27	30	44	69	0	344	\N	\N	\N	\N	26
15	2015-08-07 23:08:00	\N	\N	\N	31	133	24	37	38	73	28	31	45	70	23940	18300	\N	\N	\N	\N	26
16	2015-08-08 23:08:00	\N	\N	\N	32	134	25	23	39	74	29	32	46	71	18780	20100	\N	\N	\N	\N	26
17	2015-08-09 23:08:00	\N	\N	\N	17	119	10	13	24	59	14	17	31	56	35	25	\N	\N	\N	\N	26
18	2015-08-10 23:08:00	\N	\N	\N	18	120	11	23	25	60	15	18	32	57	3	1	\N	\N	\N	\N	26
19	2015-08-11 23:08:00	\N	\N	\N	19	121	12	25	26	61	16	19	33	58	3	4	\N	\N	\N	\N	26
20	2015-08-12 23:08:00	\N	\N	\N	20	122	13	26	27	62	17	20	34	59	18960	23280	\N	\N	\N	\N	26
21	2015-08-13 23:08:00	\N	\N	\N	21	123	14	27	28	63	18	21	35	60	20400	21900	\N	\N	\N	\N	26
22	2015-08-14 23:08:00	\N	\N	\N	22	124	15	28	29	64	19	22	36	61	21120	21180	\N	\N	\N	\N	26
23	2015-08-15 23:08:00	\N	\N	\N	23	125	16	29	30	65	20	23	37	62	20700	21600	\N	\N	\N	\N	26
24	2015-08-16 23:08:00	\N	\N	\N	24	126	17	30	31	66	21	24	38	63	10140	10080	\N	\N	\N	\N	26
25	2015-07-24 23:08:00	\N	\N	\N	17	119	10	13	24	59	14	17	31	56	35	25	\N	\N	\N	\N	27
26	2015-07-25 23:08:00	\N	\N	\N	18	120	11	23	25	60	15	18	32	57	3	1	\N	\N	\N	\N	27
27	2015-07-26 23:08:00	\N	\N	\N	19	121	12	25	26	61	16	19	33	58	3	4	\N	\N	\N	\N	27
28	2015-07-27 23:08:00	\N	\N	\N	20	122	13	26	27	62	17	20	34	59	18960	23280	\N	\N	\N	\N	27
29	2015-07-28 23:08:00	\N	\N	\N	21	123	14	27	28	63	18	21	35	60	20400	21900	\N	\N	\N	\N	27
30	2015-07-29 23:08:00	\N	\N	\N	22	124	15	28	29	64	19	22	36	61	21120	21180	\N	\N	\N	\N	27
31	2015-07-30 23:08:00	\N	\N	\N	23	125	16	29	30	65	20	23	37	62	20700	21600	\N	\N	\N	\N	27
32	2015-07-31 23:08:00	\N	\N	\N	24	126	17	30	31	66	21	24	38	63	10140	10080	\N	\N	\N	\N	27
33	2015-08-01 23:08:00	\N	\N	\N	25	127	18	31	32	67	22	25	39	64	5580	5880	\N	\N	\N	\N	27
34	2015-08-02 23:08:00	\N	\N	\N	26	128	19	32	33	68	23	26	40	65	19440	22860	\N	\N	\N	\N	27
35	2015-08-03 23:08:00	\N	\N	\N	27	129	20	33	34	69	24	27	41	66	21540	20760	\N	\N	\N	\N	27
36	2015-08-04 23:08:00	\N	\N	\N	28	130	21	34	35	70	25	28	42	67	19800	22500	\N	\N	\N	\N	27
37	2015-08-05 23:08:00	\N	\N	\N	29	131	22	35	36	71	26	29	43	68	21600	20640	\N	\N	\N	\N	27
38	2015-08-06 23:08:00	\N	\N	\N	30	132	23	36	37	72	27	30	44	69	0	344	\N	\N	\N	\N	27
39	2015-08-07 23:08:00	\N	\N	\N	31	133	24	37	38	73	28	31	45	70	23940	18300	\N	\N	\N	\N	27
40	2015-08-08 23:08:00	\N	\N	\N	32	134	25	23	39	74	29	32	46	71	18780	20100	\N	\N	\N	\N	27
41	2015-08-09 23:08:00	\N	\N	\N	17	119	10	13	24	59	14	17	31	56	35	25	\N	\N	\N	\N	27
42	2015-08-10 23:08:00	\N	\N	\N	18	120	11	23	25	60	15	18	32	57	3	1	\N	\N	\N	\N	27
43	2015-08-11 23:08:00	\N	\N	\N	19	121	12	25	26	61	16	19	33	58	3	4	\N	\N	\N	\N	27
44	2015-08-12 23:08:00	\N	\N	\N	20	122	13	26	27	62	17	20	34	59	18960	23280	\N	\N	\N	\N	27
45	2015-08-13 23:08:00	\N	\N	\N	21	123	14	27	28	63	18	21	35	60	20400	21900	\N	\N	\N	\N	27
46	2015-08-14 23:08:00	\N	\N	\N	22	124	15	28	29	64	19	22	36	61	21120	21180	\N	\N	\N	\N	27
47	2015-08-15 23:08:00	\N	\N	\N	23	125	16	29	30	65	20	23	37	62	20700	21600	\N	\N	\N	\N	27
48	2015-08-16 23:08:00	\N	\N	\N	24	126	17	30	31	66	21	24	38	63	10140	10080	\N	\N	\N	\N	27
49	2015-07-24 23:08:00	\N	\N	\N	17	119	10	13	24	59	14	17	31	56	35	25	\N	\N	\N	\N	108
50	2015-07-25 23:08:00	\N	\N	\N	18	120	11	23	25	60	15	18	32	57	3	1	\N	\N	\N	\N	108
51	2015-07-26 23:08:00	\N	\N	\N	19	121	12	25	26	61	16	19	33	58	3	4	\N	\N	\N	\N	108
52	2015-07-27 23:08:00	\N	\N	\N	20	122	13	26	27	62	17	20	34	59	18960	23280	\N	\N	\N	\N	108
53	2015-07-28 23:08:00	\N	\N	\N	21	123	14	27	28	63	18	21	35	60	20400	21900	\N	\N	\N	\N	108
54	2015-07-29 23:08:00	\N	\N	\N	22	124	15	28	29	64	19	22	36	61	21120	21180	\N	\N	\N	\N	108
55	2015-07-30 23:08:00	\N	\N	\N	23	125	16	29	30	65	20	23	37	62	20700	21600	\N	\N	\N	\N	108
56	2015-07-31 23:08:00	\N	\N	\N	24	126	17	30	31	66	21	24	38	63	10140	10080	\N	\N	\N	\N	108
57	2015-08-01 23:08:00	\N	\N	\N	25	127	18	31	32	67	22	25	39	64	5580	5880	\N	\N	\N	\N	108
58	2015-08-02 23:08:00	\N	\N	\N	26	128	19	32	33	68	23	26	40	65	19440	22860	\N	\N	\N	\N	108
59	2015-08-03 23:08:00	\N	\N	\N	27	129	20	33	34	69	24	27	41	66	21540	20760	\N	\N	\N	\N	108
60	2015-08-04 23:08:00	\N	\N	\N	28	130	21	34	35	70	25	28	42	67	19800	22500	\N	\N	\N	\N	108
61	2015-08-05 23:08:00	\N	\N	\N	29	131	22	35	36	71	26	29	43	68	21600	20640	\N	\N	\N	\N	108
62	2015-08-06 23:08:00	\N	\N	\N	30	132	23	36	37	72	27	30	44	69	0	344	\N	\N	\N	\N	108
63	2015-08-07 23:08:00	\N	\N	\N	31	133	24	37	38	73	28	31	45	70	23940	18300	\N	\N	\N	\N	108
64	2015-08-08 23:08:00	\N	\N	\N	32	134	25	23	39	74	29	32	46	71	18780	20100	\N	\N	\N	\N	108
65	2015-08-09 23:08:00	\N	\N	\N	17	119	10	13	24	59	14	17	31	56	35	25	\N	\N	\N	\N	108
66	2015-08-10 23:08:00	\N	\N	\N	18	120	11	23	25	60	15	18	32	57	3	1	\N	\N	\N	\N	108
67	2015-08-11 23:08:00	\N	\N	\N	19	121	12	25	26	61	16	19	33	58	3	4	\N	\N	\N	\N	108
68	2015-08-12 23:08:00	\N	\N	\N	20	122	13	26	27	62	17	20	34	59	18960	23280	\N	\N	\N	\N	108
69	2015-08-13 23:08:00	\N	\N	\N	21	123	14	27	28	63	18	21	35	60	20400	21900	\N	\N	\N	\N	108
70	2015-08-14 23:08:00	\N	\N	\N	22	124	15	28	29	64	19	22	36	61	21120	21180	\N	\N	\N	\N	108
71	2015-08-15 23:08:00	\N	\N	\N	23	125	16	29	30	65	20	23	37	62	20700	21600	\N	\N	\N	\N	108
72	2015-08-16 23:08:00	\N	\N	\N	24	126	17	30	31	66	21	24	38	63	10140	10080	\N	\N	\N	\N	108
73	2015-07-24 23:08:00	\N	\N	\N	17	119	10	13	24	59	14	17	31	56	35	25	\N	\N	\N	\N	109
74	2015-07-25 23:08:00	\N	\N	\N	18	120	11	23	25	60	15	18	32	57	3	1	\N	\N	\N	\N	109
75	2015-07-26 23:08:00	\N	\N	\N	19	121	12	25	26	61	16	19	33	58	3	4	\N	\N	\N	\N	109
76	2015-07-27 23:08:00	\N	\N	\N	20	122	13	26	27	62	17	20	34	59	18960	23280	\N	\N	\N	\N	109
77	2015-07-28 23:08:00	\N	\N	\N	21	123	14	27	28	63	18	21	35	60	20400	21900	\N	\N	\N	\N	109
78	2015-07-29 23:08:00	\N	\N	\N	22	124	15	28	29	64	19	22	36	61	21120	21180	\N	\N	\N	\N	109
79	2015-07-30 23:08:00	\N	\N	\N	23	125	16	29	30	65	20	23	37	62	20700	21600	\N	\N	\N	\N	109
80	2015-07-31 23:08:00	\N	\N	\N	24	126	17	30	31	66	21	24	38	63	10140	10080	\N	\N	\N	\N	109
81	2015-08-01 23:08:00	\N	\N	\N	25	127	18	31	32	67	22	25	39	64	5580	5880	\N	\N	\N	\N	109
82	2015-08-02 23:08:00	\N	\N	\N	26	128	19	32	33	68	23	26	40	65	19440	22860	\N	\N	\N	\N	109
83	2015-08-03 23:08:00	\N	\N	\N	27	129	20	33	34	69	24	27	41	66	21540	20760	\N	\N	\N	\N	109
84	2015-08-04 23:08:00	\N	\N	\N	28	130	21	34	35	70	25	28	42	67	19800	22500	\N	\N	\N	\N	109
85	2015-08-05 23:08:00	\N	\N	\N	29	131	22	35	36	71	26	29	43	68	21600	20640	\N	\N	\N	\N	109
86	2015-08-06 23:08:00	\N	\N	\N	30	132	23	36	37	72	27	30	44	69	0	344	\N	\N	\N	\N	109
87	2015-08-07 23:08:00	\N	\N	\N	31	133	24	37	38	73	28	31	45	70	23940	18300	\N	\N	\N	\N	109
88	2015-08-08 23:08:00	\N	\N	\N	32	134	25	23	39	74	29	32	46	71	18780	20100	\N	\N	\N	\N	109
89	2015-08-09 23:08:00	\N	\N	\N	17	119	10	13	24	59	14	17	31	56	35	25	\N	\N	\N	\N	109
90	2015-08-10 23:08:00	\N	\N	\N	18	120	11	23	25	60	15	18	32	57	3	1	\N	\N	\N	\N	109
91	2015-08-11 23:08:00	\N	\N	\N	19	121	12	25	26	61	16	19	33	58	3	4	\N	\N	\N	\N	109
92	2015-08-12 23:08:00	\N	\N	\N	20	122	13	26	27	62	17	20	34	59	18960	23280	\N	\N	\N	\N	109
93	2015-08-13 23:08:00	\N	\N	\N	21	123	14	27	28	63	18	21	35	60	20400	21900	\N	\N	\N	\N	109
94	2015-08-14 23:08:00	\N	\N	\N	22	124	15	28	29	64	19	22	36	61	21120	21180	\N	\N	\N	\N	109
95	2015-08-15 23:08:00	\N	\N	\N	23	125	16	29	30	65	20	23	37	62	20700	21600	\N	\N	\N	\N	109
96	2015-08-16 23:08:00	\N	\N	\N	24	126	17	30	31	66	21	24	38	63	10140	10080	\N	\N	\N	\N	109
\.


--
-- TOC entry 2873 (class 0 OID 0)
-- Dependencies: 235
-- Name: indoorunitstatistics_daily_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_daily_id_seq', 1000, false);


--
-- TOC entry 2874 (class 0 OID 0)
-- Dependencies: 236
-- Name: indoorunitstatistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_id_seq', 1, false);


--
-- TOC entry 2786 (class 0 OID 19992)
-- Dependencies: 237
-- Data for Name: indoorunitstatistics_monthly; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indoorunitstatistics_monthly (id, filtersignclear, instantpower, month, otelectricheater, othighfscooling, othighfsheating, othighfsthermoff, othighfsthermon, otlowfscooling, otlowfsheating, otlowfsthermoff, otlowfsthermon, otmediumfscooling, otmediumfsheating, otmediumfsthermoff, otmediumfsthermon, settablefanspeed, settablemode, updatefiltersign, ventilation, year, indoorunit_id, logtime) FROM stdin;
1	1	1	5	30	578	222	10	10	385	247	30	30	247	112	20	20	2	1	2015-03-21 00:57:00	1	2015	26	\N
2	2	2	6	40	521	235	20	20	297	385	40	40	414	534	30	30	2	1	2015-03-22 00:57:00	2	2015	26	\N
3	3	3	7	50	457	451	30	30	419	295	50	50	295	429	40	40	2	1	2015-03-23 00:57:00	3	2015	26	\N
4	4	4	5	60	396	325	40	45	455	321	60	60	288	541	50	55	2	1	2015-03-24 00:57:00	4	2015	27	\N
5	4	4	6	22	458	145	18	55	314	412	22	14	385	352	20	12	2	1	2015-03-25 00:57:00	3	2015	27	\N
6	4	4	7	22	521	415	18	12	352	351	22	16	412	524	28	12	2	1	2015-03-26 00:57:00	4	2015	27	\N
7	1	1	5	0	578	222	90	210	385	247	0	0	247	112	124680	124650	2	1	2015-03-27 00:57:00	1	2015	108	\N
8	2	2	6	124155	521	235	0	0	297	385	124155	125475	414	534	0	0	2	1	2015-03-28 00:57:00	2	2015	108	\N
9	3	3	7	0	457	451	0	0	419	295	0	0	295	429	0	0	2	1	2015-03-29 00:57:00	3	2015	108	\N
10	4	4	5	0	396	325	0	0	455	321	0	0	288	541	0	0	2	1	2015-03-30 00:57:00	4	2015	109	\N
11	4	4	6	0	458	145	122760	126870	314	412	0	0	385	352	0	0	2	1	2015-03-31 00:57:00	3	2015	109	\N
12	4	4	7	0	521	415	0	0	352	351	0	0	412	524	127395	122235	2	1	2015-04-01 00:57:00	4	2015	109	\N
\.


--
-- TOC entry 2875 (class 0 OID 0)
-- Dependencies: 238
-- Name: indoorunitstatistics_monthly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_monthly_id_seq', 1000, false);


--
-- TOC entry 2788 (class 0 OID 19997)
-- Dependencies: 239
-- Data for Name: indoorunitstatistics_weekly; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indoorunitstatistics_weekly (id, filtersignclear, instantpower, otelectricheater, othighfscooling, othighfsheating, othighfsthermoff, othighfsthermon, otlowfscooling, otlowfsheating, otlowfsthermoff, otlowfsthermon, otmediumfscooling, otmediumfsheating, otmediumfsthermoff, otmediumfsthermon, settablefanspeed, settablemode, updatefiltersign, ventilation, week, year, indoorunit_id, logtime) FROM stdin;
1	1	1	1	578	222	10	10	385	247	30	30	247	112	20	20	2	1	2015-03-21 02:46:00	1	28	2015	26	\N
2	2	2	2	521	235	20	20	297	385	40	40	414	534	30	30	2	1	2015-03-22 02:46:00	2	29	2015	26	\N
3	3	3	3	457	451	30	30	419	295	50	50	295	429	40	40	2	1	2015-03-23 02:46:00	3	30	2015	26	\N
4	4	4	4	396	325	40	30	455	321	60	60	288	541	50	50	2	1	2015-03-24 02:46:00	4	31	2015	26	\N
5	4	4	1	458	145	90	210	314	412	0	0	385	352	124680	124650	2	1	2015-03-25 02:46:00	3	28	2015	27	\N
6	4	4	2	521	415	0	0	352	351	124155	125475	412	524	0	0	2	1	2015-03-26 02:46:00	4	29	2015	27	\N
7	1	1	3	578	222	0	0	385	247	0	0	247	112	0	0	2	1	2015-03-27 02:46:00	1	30	2015	27	\N
8	2	2	4	521	235	0	0	297	385	0	0	414	534	0	0	2	1	2015-03-28 02:46:00	2	31	2015	27	\N
9	3	3	5	457	451	122760	126870	419	295	0	0	295	429	0	0	2	1	2015-03-29 02:46:00	3	28	2015	108	\N
10	4	4	6	396	325	0	0	455	321	0	0	288	541	127395	122235	2	1	2015-03-30 02:46:00	4	29	2015	108	\N
11	4	4	7	458	145	0	0	314	412	126405	123225	385	352	0	0	2	1	2015-03-31 02:46:00	3	30	2015	108	\N
12	4	4	8	521	415	0	0	352	351	0	0	412	524	0	0	2	1	2015-04-01 02:46:00	4	31	2015	108	\N
13	1	1	1	578	222	10	10	385	247	30	30	247	112	20	20	2	1	2015-03-21 02:46:00	1	28	2015	109	\N
14	2	2	2	521	235	20	20	297	385	40	40	414	534	30	30	2	1	2015-03-22 02:46:00	2	29	2015	109	\N
15	3	3	3	457	451	30	30	419	295	50	50	295	429	40	40	2	1	2015-03-23 02:46:00	3	30	2015	109	\N
16	4	4	4	396	325	40	30	455	321	60	60	288	541	50	50	2	1	2015-03-24 02:46:00	4	31	2015	109	\N
\.


--
-- TOC entry 2876 (class 0 OID 0)
-- Dependencies: 240
-- Name: indoorunitstatistics_weekly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_weekly_id_seq', 1000, false);


--
-- TOC entry 2790 (class 0 OID 20002)
-- Dependencies: 241
-- Data for Name: indoorunitstatistics_yearly; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indoorunitstatistics_yearly (id, filtersignclear, instantpower, otelectricheater, othighfscooling, othighfsheating, othighfsthermoff, othighfsthermon, otlowfscooling, otlowfsheating, otlowfsthermoff, otlowfsthermon, otmediumfscooling, otmediumfsheating, otmediumfsthermoff, otmediumfsthermon, settablefanspeed, settablemode, updatefiltersign, ventilation, year, indoorunit_id, logtime) FROM stdin;
1	\N	\N	\N	578	222	10	10	385	247	30	30	247	112	20	20	\N	\N	\N	\N	2013	26	\N
2	\N	\N	\N	521	235	20	20	297	385	40	40	414	534	30	30	\N	\N	\N	\N	2014	26	\N
3	\N	\N	\N	457	451	30	30	419	295	50	50	295	429	40	40	\N	\N	\N	\N	2015	26	\N
4	\N	\N	\N	396	325	40	40	455	321	60	60	288	541	50	50	\N	\N	\N	\N	2013	27	\N
5	\N	\N	\N	458	145	346	346	314	412	36	346	385	352	36	346	\N	\N	\N	\N	2014	27	\N
6	\N	\N	\N	521	415	25	24	352	351	789	235	412	524	436	25	\N	\N	\N	\N	2015	27	\N
7	\N	\N	\N	578	222	10	10	385	247	30	30	247	112	20	20	\N	\N	\N	\N	2013	108	\N
8	\N	\N	\N	521	235	20	20	297	385	40	40	414	534	30	30	\N	\N	\N	\N	2014	108	\N
9	\N	\N	\N	457	451	30	30	419	295	50	50	295	429	40	40	\N	\N	\N	\N	2015	108	\N
10	\N	\N	\N	396	325	40	40	455	321	60	60	288	541	50	50	\N	\N	\N	\N	2013	109	\N
11	\N	\N	\N	458	145	346	346	314	412	36	346	385	352	36	346	\N	\N	\N	\N	2014	109	\N
12	\N	\N	\N	521	415	25	24	352	351	789	235	412	524	436	25	\N	\N	\N	\N	2015	109	\N
\.


--
-- TOC entry 2877 (class 0 OID 0)
-- Dependencies: 242
-- Name: indoorunitstatistics_yearly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_yearly_id_seq', 1000, false);


--
-- TOC entry 2849 (class 0 OID 21047)
-- Dependencies: 300
-- Data for Name: job_tracker; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY job_tracker (jobname, status, lastexecutiontime, createdate, createdby, updatedate) FROM stdin;
\.


--
-- TOC entry 2878 (class 0 OID 0)
-- Dependencies: 244
-- Name: metaindoorunits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('metaindoorunits_id_seq', 1, false);


--
-- TOC entry 2879 (class 0 OID 0)
-- Dependencies: 246
-- Name: metaoutdoorunits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('metaoutdoorunits_id_seq', 1, false);


--
-- TOC entry 2880 (class 0 OID 0)
-- Dependencies: 248
-- Name: modemaster_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('modemaster_id_seq', 1, false);


--
-- TOC entry 2744 (class 0 OID 19841)
-- Dependencies: 193
-- Data for Name: notificationlog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY notificationlog (id, alarm_type, createdby, creationdate, description, severity, status, "time", updatedate, updatedby, adapterid, code, indoorunit_id, outdoorunit_id, fixed_time) FROM stdin;
9	Alram	RSI	2015-07-03 14:47:00	Suspected Window Is Left Open! CounterMeasure: Please check the wondow and close it!	CRITICAL	new	2015-08-16 10:05:00	2015-07-21 00:39:00	RSI	3	P01	27	\N	\N
11	Alram	RSI	2015-04-17 10:32:00	Abnormal indoor fan motor! CounterMeasure: Please call the service center	NONCRITICAL	new	2015-06-20 19:08:00	2015-03-21 00:39:00	RSI	5	P01	29	\N	\N
13	Alram	RSI	2015-04-17 10:32:00	Air Filter is dirty! CounterMeasure: Please clean it.	CRITICAL	new	2015-07-20 19:08:00	2015-03-21 00:39:00	RSI	3	A07	30	\N	\N
14	pre Alram	RSI	2015-07-03 14:47:00	Abnormal indoor fan motor! CounterMeasure: Please call the service center	NONCRITICAL	fixed	2015-07-20 19:08:00	2015-03-21 00:39:00	RSI	3	A09	30	\N	\N
15	pre Alram	RSI	2015-04-17 10:32:00	Suspected Window Is Left Open! CounterMeasure: Please check the wondow and close it!	CRITICAL	on-hold	2015-07-21 19:08:00	2015-03-21 00:39:00	RSI	3	A07	30	\N	\N
23	Alram	RSI	2015-07-03 14:48:00	Fuel gas pressure low	CRITICAL	on-hold	2015-04-17 18:05:00	2015-04-17 23:35:00	RSI	6	A30	\N	16	\N
24	Alram	RSI	2015-04-17 10:35:00	Fuel gas valve failure! CounterMeasure: Please call the service center!	NONCRITICAL	on-hold	2015-06-15 12:08:00	2015-03-21 00:39:00	RSI	6	A07	\N	17	\N
25	Alram	RSI	2015-04-17 10:35:00	Fuel gas valve failure! CounterMeasure: Please call the service center!	NONCRITICAL	on-hold	2015-06-15 12:08:00	2015-03-21 00:39:00	RSI	6	A07	\N	17	\N
27	Alram	RSI	2015-04-17 10:35:00	Fuel gas valve failure! CounterMeasure: Please call the service center!	CRITICAL	new	2015-06-15 12:08:00	2015-03-21 00:39:00	RSI	6	P01	\N	18	\N
298	Alarm	ETL	2015-10-15 13:49:52.669	\N	Critical	New	2015-07-07 07:30:00	\N	\N	\N	A01	54	\N	\N
29	Alram	RSI	2015-04-17 10:35:00	Detected Air Con Short Circuit! CounterMeasure: Check air flow and call our service center! 	CRITICAL	new	2015-06-15 12:08:00	2015-03-21 00:39:00	RSI	3	A07	\N	21	\N
30	Alram	RSI	2015-04-17 10:35:00	Detected Air Con Short Circuit! CounterMeasure: Check air flow and call our service center! 	CRITICAL	on-hold	2015-06-15 12:08:00	2015-03-21 00:39:00	RSI	3	P01	\N	22	\N
301	Alarm	ETL	2015-10-15 13:49:52.673	\N	Critical	New	2015-08-07 07:30:00	\N	\N	\N	A01	54	\N	\N
302	Alarm	ETL	2015-10-15 13:49:52.674	\N	Critical	New	2015-09-07 07:30:00	\N	\N	\N	A01	54	\N	\N
5	Alram	RSI	2015-07-10 00:39:00	Engine Maintenance due 20150817. CounterMeasure: Please call the service center to maintain it. 	CRITICAL	new	2015-08-03 19:08:00	2015-07-10 00:39:00	RSI	3	P01	170	\N	\N
8	Alram	RSI	2015-07-21 00:39:00	Fuel gas pressure low	CRITICAL	on-hold	2015-08-02 19:08:00	2015-07-21 00:39:00	RSI	5	P01	26	\N	\N
4	pre Alram	RSI	2015-07-10 00:39:00	Fuel gas pressure low	NONCRITICAL	on-hold	2015-08-04 19:08:00	2015-07-10 00:39:00	RSI	3	N02	25	\N	\N
6	Alram	RSI	2015-07-10 00:39:00	Abnormal indoor fan motor! CounterMeasure: Please call the service center	CRITICAL	on-hold	2015-08-06 19:08:00	2015-07-10 00:39:00	RSI	3	P15	25	\N	\N
7	pre Alram	RSI	2015-07-21 00:39:00	Suspected Window Is Left Open! CounterMeasure: Please check the wondow and close it!	NONCRITICAL	fixed	2015-07-17 10:05:00	2015-07-21 00:39:00	RSI	5	P14	26	\N	\N
2	Alram	RSI	2015-07-10 00:39:00	Abnormal indoor fan motor! CounterMeasure: Please call the service center	NONCRITICAL	new	2015-08-02 19:08:00	2015-07-10 00:39:00	RSI	3	A07	25	\N	\N
1	pre Alram	RSI	2015-07-10 00:39:00	Air Filter is dirty! CounterMeasure: Please clean it.	CRITICAL	new	2015-08-01 19:08:00	2015-07-10 00:39:00	RSI	3	P15	25	\N	\N
304	Alarm	ETL	2015-10-15 13:49:52.677	\N	Critical	New	2015-09-10 07:30:00	\N	\N	\N	A03	53	\N	\N
305	Alarm	ETL	2015-10-15 13:49:52.685	\N	Critical	New	2015-09-10 07:30:00	\N	\N	\N	A01	54	\N	\N
10	Alram	RSI	2015-07-03 14:47:00	Air Filter is dirty! CounterMeasure: Please clean it.	NONCRITICAL	on-hold	2015-08-17 10:05:00	2015-07-21 00:39:00	RSI	3	N05	27	\N	\N
12	Alram	RSI	2015-04-17 10:32:00	Suspected Window Is Left Open! CounterMeasure: Please check the wondow and close it!	CRITICAL	fixed	2015-06-21 19:08:00	2015-03-21 00:39:00	RSI	5	P13	29	\N	\N
16	pre Alram	RSI	2015-07-03 14:47:00	Fuel gas pressure low	NONCRITICAL	new	2015-07-21 19:08:00	2015-03-21 00:39:00	RSI	3	P14	172	\N	\N
17	Alram	RSI	2015-04-17 10:28:00	Suspected Window Is Left Open! CounterMeasure: Please check the wondow and close it!	NONCRITICAL	on-hold	2015-08-17 10:05:00	2015-03-21 00:39:00	RSI	4	P13	32	\N	\N
18	Alram	RSI	2015-04-17 10:28:00	Suspected Window Is Left Open! CounterMeasure: Please check the wondow and close it!	NONCRITICAL	on-hold	2015-08-12 10:05:00	2015-03-21 00:39:00	RSI	4	P13	32	\N	\N
19	Alram	RSI	2015-07-03 14:48:00	Fuel gas pressure low	CRITICAL	on-hold	2015-07-17 13:42:00	2015-04-17 19:12:00	RSI	4	N04	40	\N	\N
20	Alram	RSI	2015-07-03 14:48:00	Fuel gas pressure low	CRITICAL	new	2015-07-11 13:42:00	2015-04-17 19:12:00	RSI	4	N03	40	\N	\N
26	Alram	RSI	2015-04-17 10:35:00	Fuel gas valve failure! CounterMeasure: Please call the service center!	NONCRITICAL	new	2015-06-15 12:08:00	2015-03-21 00:39:00	RSI	6	P15	\N	16	\N
28	Alram	RSI	2015-04-17 10:35:00	Detected Air Con Short Circuit! CounterMeasure: Check air flow and call our service center! 	NONCRITICAL	new	2015-06-15 12:08:00	2015-03-21 00:39:00	RSI	3	N05	\N	20	\N
31	Alram	RSI	2015-04-17 14:50:00	Engine Maintenance due 20150417. CounterMeasure: Please call the service center to maintain it. 	CRITICAL	new	2015-04-17 12:19:00	2015-03-21 00:39:00	RSI	3	P14	\N	22	\N
32	Alram	RSI	2015-04-17 14:50:00	Engine Maintenance due 20150417. CounterMeasure: Please call the service center to maintain it. 	CRITICAL	new	2015-04-17 12:19:00	2015-03-21 00:39:00	RSI	3	P15	\N	23	\N
33	pre Alram	RSI	2015-07-10 00:39:00	Air Filter is dirty! CounterMeasure: Please clean it.	NONCRITICAL	new	2015-08-01 19:08:00	2015-07-10 00:39:00	RSI	3	P15	108	\N	\N
34	pre Alram	RSI	2015-07-10 00:39:00	Air Filter is dirty! CounterMeasure: Please clean it.	CRITICAL	new	2015-08-01 19:08:00	2015-07-10 00:39:00	RSI	3	P15	108	\N	\N
35	Alram	RSI	2015-04-17 10:32:00	Suspected Window Is Left Open! CounterMeasure: Please check the wondow and close it!	CRITICAL	fixed	2015-06-21 19:08:00	2015-03-21 00:39:00	RSI	5	P13	109	\N	\N
37	pre Alarm	RSI	2015-07-03 14:48:00	Fuel gas pressure low	CRITICAL	on-hold	2015-10-12 01:05:00	2015-04-17 23:35:00	RSI	6	A30	\N	16	\N
36	Alarm	RSI	2015-04-17 10:32:00	Suspected Window Is Left Open! CounterMeasure: Please check the wondow and close it!	CRITICAL	new	2015-10-13 19:08:00	2015-10-13 00:39:00	RSI	5	P13	26	\N	2015-10-13 19:08:00
300	Alarm	ETL	2015-10-15 13:49:52.671	\N	NonCritical	New	2015-07-11 07:30:00	\N	\N	\N	A11	52	\N	\N
303	Alarm	ETL	2015-10-15 13:49:52.677	\N	NonCritical	New	2015-09-10 07:30:00	\N	\N	\N	A11	52	\N	\N
307	Alarm	ETL	2015-10-15 13:49:52.676	\N	NONCRITICAL	New	2015-09-09 11:30:00	\N	\N	\N	A23	\N	15	\N
306	Alarm	ETL	2015-10-15 13:49:52.675	\N	NONCRITICAL	New	2015-09-09 07:30:00	\N	\N	\N	A24	\N	15	\N
308	Alarm	ETL	2015-10-15 13:49:52.686	\N	NONCRITICAL	New	2015-09-10 07:30:00	\N	\N	\N	A23	\N	15	\N
38	Alert	RSI	2015-07-03 14:48:00	Fuel gas pressure low	NONCRITICAL	fixed	2015-10-11 18:55:00	2015-04-17 23:35:00	RSI	6	N05	\N	17	2015-10-13 19:08:00
3	Alram	RSI	2015-07-10 00:39:00	Suspected Window Is Left Open! CounterMeasure: Please check the wondow and close it!	CRITICAL	new	2015-07-15 19:08:00	2015-07-10 00:39:00	RSI	3	P13	25	\N	\N
299	Alarm	ETL	2015-10-15 13:49:52.67	\N	NonCritical	New	2015-07-10 07:30:00	\N	\N	\N	A11	52	\N	\N
\.


--
-- TOC entry 2798 (class 0 OID 20025)
-- Dependencies: 249
-- Data for Name: notificationlog_temp; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY notificationlog_temp (id, indoorunit_id, outdoorunit_id, adapterid, occur_datetime, alarmcode, severity, maintenance_description, type, timezone) FROM stdin;
\.


--
-- TOC entry 2799 (class 0 OID 20031)
-- Dependencies: 250
-- Data for Name: notificationsettings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY notificationsettings (id, on_off, notificationtype_id, group_id) FROM stdin;
38	t	4	13
37	f	2	13
1950	f	1	13
1951	t	3	13
40	f	1	3
41	t	3	3
46	t	1	20
47	f	2	20
\.


--
-- TOC entry 2881 (class 0 OID 0)
-- Dependencies: 251
-- Name: notificationsettings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('notificationsettings_id_seq', 47, true);


--
-- TOC entry 2882 (class 0 OID 0)
-- Dependencies: 253
-- Name: notificationtype_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('notificationtype_master_id_seq', 1, false);


--
-- TOC entry 2803 (class 0 OID 20041)
-- Dependencies: 254
-- Data for Name: outdoorunitparameters; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY outdoorunitparameters (id, display_name, parameter_name, parameter_unit, type) FROM stdin;
16	Temp. oil 3	temppil3	\N	VRF
17	Temp. compress or discharge 1	tempcompressordischarge1	\N	VRF
18	Temp. compress or discharge 2	tempcompressordischarge2	\N	VRF
19	Temp. compress or discharge 3	tempcompressordischarge3	\N	VRF
20	Inlet temp. outdoor unit	inlettempoutdoorunit	\N	VRF
21	High pressure	highpresure	\N	VRF
22	Low pressure	lowpresure	\N	VRF
23	Demand	demand	\N	VRF
24	Saturated temp. high press.	saturatedtemphighpress	\N	VRF
25	Saturated temp. low press.	saturatedtemplowpress	\N	VRF
26	INV primary current	invprimarycurrent	\N	VRF
27	INV secondary current	invsecondarycurrent	\N	VRF
28	Current 2	current2	\N	VRF
29	Current 3	current3	\N	VRF
30	Fan mode	fanmode	\N	VRF
31	Fan rotation	fanrotation	\N	VRF
32	Mov. 1 pulse	mov1pulse	\N	VRF
33	Mov. 2 pulse	mov2pulse	\N	VRF
34	Mov. 4 pulse	mov4pulse	\N	VRF
35	Instant gas	instantgas	\N	GHP
36	Instant heat	instantheat	\N	GHP
37	Engine operation time	engineoperation_time	Hours	GHP
38	Time after changing engine oil	timeafter_changing_engine_oil	Hours	GHP
54	Discharge solenoid valve 1	dischargesolenoidvalve1	\N	GHP
55	Discharge solenoid valve 2	dischargesolenoidvalve2	\N	GHP
56	Suction solenoid valve 1	suctionsolenoidevalve1	\N	GHP
57	Suction solenoid valve 2	suctionsolenoidevalve2	\N	GHP
58	Drain filter heater 1	drainfilterheater1	\N	GHP
59	Drain filter heater 2	drainfilterheater2	\N	GHP
60	Receiver tank valve 1	receivertankvalve1	\N	GHP
61	Receiver tank valve 2	receivertankvalve2	\N	GHP
62	Engine revolution	enginerevolution	\N	GHP
63	Fuel gas regulating valve opening	fuelgas_regulating_valve_opening	\N	GHP
64	Throttle	thtottle	\N	GHP
65	Expansion valve opening	expansionvalve_opening	\N	GHP
66	Expansion valve opening 2	expansionvalve_opening2	\N	GHP
67	Bypass valve opening	bypassvalve_opening	\N	GHP
68	Liquid valve opening	liquidvalve_opening	\N	GHP
69	Exhaust heat recovery valve opening	exhaustheat_recovery_valve_opening	\N	GHP
70	3 way valve for coolant	3wayvalveforcoolant	\N	GHP
71	3 way valve for hot water	3wayvalveforhotwater	\N	GHP
72	3 way cooler valve	3waycoolervalve	\N	GHP
73	Coolant temperature	coolanttemperature	\N	GHP
74	Compressor inlet pressure	compressorinlet_pressure	\N	GHP
75	Compressor outlet pressure	compressoroutlet_pressure	\N	GHP
76	Compressor inlet temperature	compressorinlet_temperature	\N	GHP
77	Compressor outlet temperature	compressoroutlet_temperature	\N	GHP
78	Heat exchanger inlet temperature	heatexchangerinlet_temperature	\N	GHP
79	Heat exchanger inlet temperature 2	heatexchangerinlet_temperature2	\N	GHP
80	Exhaust gas temperature	exhaustgastemperature	\N	GHP
81	Outdoor unit fan output	outdoorunitfanoutput	\N	GHP
82	Generation power	generationpower	\N	GHP
83	Starter motor current	startermotorcurrent	\N	GHP
84	Ignition timing	ignitiontiming	\N	GHP
85	Clutch coil temperature	clutchcoiltemperature	\N	GHP
86	Clutch coil temperature 2	clutchcoiltemperature2	\N	GHP
87	Oil level temperature	oilleveltemperature	\N	GHP
88	Catalyzer temperature	catalyzertemperature	\N	GHP
89	Hot water temperature	hotwatertemperature	\N	GHP
90	Compressor oil level	comperssoroillevel	\N	GHP
91	Super heat level of compressor unit	superheat_level_of_compressor_unit	\N	GHP
2	Compressor 2 working time	V21	Hours	VRF
1	Compressor 1 working time	V20	Hours	VRF
3	Compressor 3 working time	V22	Hours	VRF
4	Outdoor unit status	V1	\N	VRF
5	INV comp. target Hz	V2	\N	VRF
6	INV comp. actual Hz	V3	\N	VRF
7	Fixed speed comp. 1	V4	\N	VRF
8	Fixed speed comp. 2	V5	\N	VRF
9	Liquid temp. of outdoor coil 1	V6	\N	VRF
10	Liquid temp. of outdoor coil 2	V8	\N	VRF
11	Gas temp. of outdoor coil 1	V7	\N	VRF
12	Gas temp. of outdoor coil 2	V9	\N	VRF
13	SCG	V10	\N	VRF
14	Temp. oil 1	V11	\N	VRF
15	Temp. oil 2	V12	\N	VRF
39	GHP oil sign	G1	\N	GHP
40	Fuel gas shut_off valve 1	G2	\N	GHP
41	Fuel gas shut_off valve 2	G3	\N	GHP
42	Starter motor power	G4	\N	GHP
43	Starter motor	G5	\N	GHP
44	Oil pump	G6	\N	GHP
45	Heater for cold region	G7	\N	GHP
46	Clutch	G8	\N	GHP
47	Clutch 2	G9	\N	GHP
48	Oil recovery valve	G10	\N	GHP
49	Balance valve	G11	\N	GHP
50	Flushing valve	G12	\N	GHP
51	Gas refrigerant shut_off valve	G13	\N	GHP
52	Compressor heater	G14	\N	GHP
53	Pump for hot water	G15	\N	GHP
\.


--
-- TOC entry 2883 (class 0 OID 0)
-- Dependencies: 256
-- Name: outdoorunits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outdoorunits_id_seq', 1, true);


--
-- TOC entry 2806 (class 0 OID 20055)
-- Dependencies: 257
-- Data for Name: outdoorunitslog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY outdoorunitslog (id, accumulatedpower_consumption, checkoil, clutch, compressorheater, instaneouscurrent, nextoilchange, nextrefreshmaint, oilpump, oiltimeafterchange, outdoorhp, outdoormodel_info, outsideairtemperature, prealarminformation, ratedcooling_refrigerant_circulating, ratedcurrent, ratedheating_refrigerant_circulating, ratedsystem_current, refrigerantcirculating, "time", utilizationrate, workingtime, workingtime1, workingtime2, workingtime3, outdoorunit_id, status) FROM stdin;
28	\N	\N	\N	\N	3.5	\N	\N	\N	\N	\N	\N	31	\N	\N	43.100000000000001	\N	\N	\N	\N	2	1435	15.00	16.00	17.00	16	\N
29	\N	\N	\N	\N	16.3999996	\N	\N	\N	\N	\N	\N	32	\N	\N	56.700000000000003	\N	\N	\N	\N	3	2124	11.00	34.00	16.00	17	\N
30	\N	\N	\N	\N	3	\N	\N	\N	\N	\N	\N	35	\N	\N	20.100000000000001	\N	\N	\N	\N	4	1253	12.00	35.00	14.00	18	\N
31	\N	\N	\N	\N	4.80000019	\N	\N	\N	\N	\N	\N	33.5	\N	\N	20.100000000000001	\N	\N	\N	\N	5	2906	13.00	36.00	18.00	19	\N
32	\N	\N	\N	\N	5.19999981	\N	\N	\N	\N	\N	\N	33	\N	\N	22.300000000000001	\N	\N	\N	\N	6	926	14.00	37.00	19.00	20	\N
33	\N	\N	\N	\N	5.0999999	\N	\N	\N	\N	\N	\N	34	\N	\N	25.199999999999999	\N	\N	\N	\N	7	1420	15.00	38.00	20.00	21	\N
34	\N	\N	\N	\N	7.0999999	\N	\N	\N	\N	\N	\N	33	\N	\N	40.200000000000003	\N	\N	\N	\N	8	1383	16.00	39.00	21.00	22	\N
35	\N	\N	\N	\N	18.6000004	\N	\N	\N	\N	\N	\N	33	\N	\N	40.200000000000003	\N	\N	\N	\N	9	1668	17.00	40.00	22.00	23	\N
36	\N	\N	\N	\N	11.1999998	\N	\N	\N	\N	\N	\N	32.5	\N	\N	40.200000000000003	\N	\N	\N	\N	10	1590	18.00	41.00	13.00	24	\N
37	\N	\N	\N	\N	37.2000008	\N	\N	\N	\N	\N	\N	32.5	\N	\N	40.200000000000003	\N	\N	\N	\N	11	2064	19.00	42.00	23.00	25	\N
38	\N	\N	\N	\N	15.5	\N	\N	\N	\N	\N	\N	33.5	\N	\N	40.200000000000003	\N	\N	\N	\N	12	1468	20.00	43.00	24.00	26	\N
39	\N	\N	\N	\N	16.2000008	\N	\N	\N	\N	\N	\N	33.5	\N	\N	43.100000000000001	\N	\N	\N	\N	13	1420	21.00	44.00	24.00	27	\N
40	\N	\N	\N	\N	5.5	\N	\N	\N	\N	\N	\N	31.5	\N	\N	20.100000000000001	\N	\N	\N	\N	14	1242	22.00	45.00	26.00	28	\N
41	\N	\N	\N	\N	8.69999981	\N	\N	\N	\N	\N	\N	31	\N	\N	29.699999999999999	\N	\N	\N	\N	15	1663	23.00	5.00	27.00	29	\N
42	\N	\N	\N	\N	0.400000006	\N	\N	\N	\N	\N	\N	32	\N	\N	26.800000000000001	\N	\N	\N	\N	16	890	24.00	6.00	28.00	30	\N
43	\N	\N	\N	\N	29.8999996	\N	\N	\N	\N	\N	\N	34.5	\N	\N	48.600000000000001	\N	\N	\N	\N	17	1841	25.00	7.00	29.00	31	\N
44	\N	\N	\N	\N	6.9000001	\N	\N	\N	\N	\N	\N	31.5	\N	\N	18.899999999999999	\N	\N	\N	\N	18	1809	26.00	8.00	30.00	32	\N
45	\N	\N	\N	\N	20	\N	\N	\N	\N	\N	\N	32.5	\N	\N	43.100000000000001	\N	\N	\N	\N	19	1527	27.00	9.00	31.00	33	\N
46	\N	\N	\N	\N	12.8999996	\N	\N	\N	\N	\N	\N	33	\N	\N	32.299999999999997	\N	\N	\N	\N	20	1634	27.00	10.00	32.00	34	\N
47	\N	\N	\N	\N	4	\N	\N	\N	\N	\N	\N	35.5	\N	\N	35.200000000000003	\N	\N	\N	\N	21	1386	29.00	11.00	33.00	35	\N
48	\N	\N	\N	\N	12.3000002	\N	\N	\N	\N	\N	\N	35	\N	\N	35.200000000000003	\N	\N	\N	\N	22	1200	30.00	12.00	34.00	36	\N
49	\N	\N	\N	\N	6.0999999	\N	\N	\N	\N	\N	\N	34	\N	\N	35.200000000000003	\N	\N	\N	\N	23	1328	31.00	13.00	35.00	37	\N
50	\N	\N	\N	\N	6.80000019	\N	\N	\N	\N	\N	\N	32.5	\N	\N	22.300000000000001	\N	\N	\N	\N	24	1479	32.00	14.00	36.00	38	\N
51	\N	\N	\N	\N	6.80000019	\N	\N	\N	\N	\N	\N	32.5	\N	\N	22.300000000000001	\N	\N	\N	\N	24	1479	32.00	14.00	36.00	15	\N
27	\N	\N	\N	\N	22.2000008	\N	\N	\N	\N	\N	\N	33	\N	\N	76.200000000000003	\N	\N	\N	\N	1	1787	10.00	33.00	15.00	1	\N
\.


--
-- TOC entry 2808 (class 0 OID 20060)
-- Dependencies: 259
-- Data for Name: outdoorunitslog_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY outdoorunitslog_history (id, accumulatedpower_consumption, checkoil, clutch, compressorheater, instaneouscurrent, nextoilchange, nextrefreshmaint, oilpump, oiltimeafterchange, outdoorhp, outdoormodel_info, outsideairtemperature, prealarminformation, ratedcooling_refrigerant_circulating, ratedcurrent, ratedheating_refrigerant_circulating, ratedsystem_current, refrigerantcirculating, "time", utilizationrate, workingtime, workingtime1, workingtime2, workingtime3, outdoorunit_id) FROM stdin;
\.


--
-- TOC entry 2884 (class 0 OID 0)
-- Dependencies: 258
-- Name: outdoorunitstatistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outdoorunitstatistics_id_seq', 1, false);


--
-- TOC entry 2809 (class 0 OID 20064)
-- Dependencies: 260
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY permissions (id, createdby, creationdate, name, updatedate, updatedby, url, isleftmenu, isdel) FROM stdin;
1	\N	2015-02-23 19:17:43	Companies	2015-07-03 14:50:13	\N	\N	\N	\N
2	\N	2015-02-23 19:17:47	Group	2015-07-03 14:50:13	\N	\N	\N	\N
3	\N	2015-02-23 19:17:50	User	2015-07-03 14:50:13	\N	\N	\N	\N
4	\N	2015-02-23 19:17:53	Adaptor	2015-07-03 14:50:13	\N	\N	\N	\N
5	\N	2015-02-23 19:17:53	NotificationMessageCustomer	\N	\N	\N	\N	\N
6	\N	2015-02-23 19:17:53	NotificationMessageMaintenance	\N	\N	\N	\N	\N
\.


--
-- TOC entry 2885 (class 0 OID 0)
-- Dependencies: 261
-- Name: permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('permissions_id_seq', 1, false);


--
-- TOC entry 2811 (class 0 OID 20072)
-- Dependencies: 262
-- Data for Name: power_consumption_capacity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY power_consumption_capacity (id, co2emission, outdoortemp, roomtemp, setpointtemp, total_power_consumption, indoorunit_id, outdoorunit_id, logtime, efficiency_cop, efficiency_seer, currentcapacity_heating, currentcapacity_cooling, totalcapacity_cooling, totalcapacity_heating) FROM stdin;
24	21	22	23	24	25	27	\N	2015-10-06 00:00:00	28	29	30	31	33	32
11	10	11	12	13	14	26	\N	2015-09-11 00:00:00	15	16	17	18	19	20
10	19	28	15	17	16	26	\N	2015-09-10 02:00:00	21	23	22	25	28	31
25	33.299999999999997	22	18.5	22	19	108	\N	2015-10-06 00:00:00	50	21	17	33	20	11
12	21	22	23	24	25	27	\N	2015-09-12 00:00:00	28	29	30	31	33	32
13	21	22	23	24	25	117	\N	2015-09-12 00:00:00	28	29	30	31	33	32
14	22	25	13	15	11	26	\N	2015-10-01 02:00:00	21	23	22	25	28	31
15	10	11	12	13	14	26	\N	2015-10-01 00:00:00	15	16	17	18	19	20
16	21	22	23	24	25	27	\N	2015-10-01 00:00:00	28	29	30	31	33	32
2	45.5	23	12	10	17	109	\N	2015-08-26 00:00:00	74	33	2	58	36	3
3	101	25	23	15	35	109	\N	2015-08-27 00:00:00	104	12	32	54	95	6
17	23	19	18	20	11	27	\N	2015-10-01 00:00:00	21	20	26	24	29	23
26	33.299999999999997	22	18.5	22	20	108	\N	2014-12-19 00:00:00	50	21	17	33	20	11
18	45.5	23	12	10	17	108	\N	2015-10-01 00:00:00	74	33	2	58	36	3
19	33.299999999999997	22	18.5	22	19	108	\N	2015-10-01 00:00:00	50	21	17	33	20	11
5	27.5	17	25.5	18	30	140	\N	2015-09-22 02:00:00	41	16	29	45	42	9
20	20.199999999999999	1	24	12	58	109	\N	2015-10-01 00:00:00	22	22	2	22	2	2
6	33.299999999999997	22	18.5	22	19	141	\N	2015-09-22 02:00:00	50	21	17	33	20	11
27	33.299999999999997	22	18.5	22	30	108	\N	2014-12-25 00:00:00	50	21	17	33	20	11
8	17.5	19	20	25	20	87	\N	2015-09-22 02:00:00	40	32	18	21	33	25
9	20	15	17.5	21	15	88	\N	2015-09-22 02:00:00	35	35	15	23	20	30
28	33.299999999999997	22	18.5	22	40	108	\N	2015-01-02 00:00:00	50	21	17	33	20	11
7	22.100000000000001	15	21.7000008	30	21	86	\N	2015-09-22 02:00:00	45	28	24	19	45	17
21	101	25	23	15	35	109	\N	2015-07-20 00:00:00	104	12	32	54	95	6
22	101	25	23	15	35	109	\N	2015-10-06 00:00:00	104	12	32	54	95	6
23	10	11	12	13	14	26	\N	2015-10-06 00:00:00	15	16	17	18	19	20
1	20.199999999999999	1	24	12	58	109	\N	2015-09-15 00:00:00	22	22	2	22	2	2
4	55	1	30.8999996	22	33	109	\N	2015-09-14 02:00:00	55	12	22	22	33	56
\.


--
-- TOC entry 2812 (class 0 OID 20075)
-- Dependencies: 263
-- Data for Name: power_consumption_capacity_daily; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY power_consumption_capacity_daily (id, co2emission, outdoortemp, roomtemp, setpointtemp, total_power_consumption, indoorunit_id, outdoorunit_id, efficiency_cop, efficiency_seer, currentcapacity_heating, currentcapacity_cooling, totalcapacity_heating, totalcapacity_cooling, logtime) FROM stdin;
13	2	11.3999996	11.3999996	11.3999996	20	\N	16	147.199997	137.199997	157	147	197	192	\N
14	4	11.6000004	11.6000004	11.6000004	22	\N	16	124.199997	114.199997	134	124	174	169	\N
15	6	11.8000002	11.8000002	11.8000002	24	\N	16	125.199997	115.199997	135	125	175	170	\N
16	2	12	12	12	26	\N	16	126.199997	116.199997	136	126	176	171	\N
17	4	10.1999998	10.1999998	10.1999998	28	\N	16	127.199997	117.199997	137	127	177	172	\N
18	6	10.3999996	10.3999996	10.3999996	30	\N	16	128.199997	118.199997	138	128	178	173	\N
19	2	10.6000004	10.6000004	10.6000004	20	\N	16	129.199997	119.199997	139	129	179	174	\N
20	4	10.8000002	10.8000002	10.8000002	22	\N	16	130.199997	120.199997	140	130	180	175	\N
21	6	11	11	11	24	\N	16	131.199997	121.199997	141	131	181	176	\N
22	2	11.1999998	11.1999998	11.1999998	26	\N	16	132.199997	122.199997	142	132	182	177	\N
23	4	11.3999996	11.3999996	11.3999996	28	\N	16	133.199997	123.199997	143	133	183	178	\N
24	6	11.6000004	11.6000004	11.6000004	30	\N	16	134.199997	124.199997	144	134	184	179	\N
25	2	11	11	11	20	\N	15	135.199997	125.199997	145	135	185	180	\N
28	2	11.6000004	11.6000004	11.6000004	26	\N	15	138.199997	128.199997	148	138	188	183	\N
29	4	11.8000002	11.8000002	11.8000002	28	\N	15	139.199997	129.199997	149	139	189	184	\N
30	6	12	12	12	30	\N	15	140.199997	130.199997	150	140	190	185	\N
31	2	10.1999998	10.1999998	10.1999998	20	\N	15	141.199997	131.199997	151	141	191	186	\N
32	4	10.3999996	10.3999996	10.3999996	22	\N	15	142.199997	132.199997	152	142	192	187	\N
33	6	10.6000004	10.6000004	10.6000004	24	\N	15	143.199997	133.199997	153	143	193	188	\N
34	2	10.8000002	10.8000002	10.8000002	26	\N	15	144.199997	134.199997	154	144	194	189	\N
35	4	11	11	11	28	\N	15	145.199997	135.199997	155	145	195	190	\N
36	6	11.1999998	11.1999998	11.1999998	30	\N	15	146.199997	136.199997	156	146	196	191	\N
37	2	11.3999996	11.3999996	11.3999996	20	\N	15	147.199997	137.199997	157	147	197	192	\N
38	4	11.6000004	11.6000004	11.6000004	22	\N	15	135.199997	125.199997	145	135	185	180	\N
39	6	11.8000002	11.8000002	11.8000002	24	\N	15	136.199997	126.199997	146	136	186	181	\N
40	2	12	12	12	26	\N	15	137.199997	127.199997	147	137	187	182	\N
41	4	10.1999998	10.1999998	10.1999998	28	\N	15	138.199997	128.199997	148	138	188	183	\N
42	6	10.3999996	10.3999996	10.3999996	30	\N	15	139.199997	129.199997	149	139	189	184	\N
43	2	10.6000004	10.6000004	10.6000004	20	\N	15	140.199997	130.199997	150	140	190	185	\N
44	4	10.8000002	10.8000002	10.8000002	22	\N	15	141.199997	131.199997	151	141	191	186	\N
45	6	11	11	11	24	\N	15	142.199997	132.199997	152	142	192	187	\N
46	2	11.1999998	11.1999998	11.1999998	26	\N	15	143.199997	133.199997	153	143	193	188	\N
47	4	11.3999996	11.3999996	11.3999996	28	\N	15	144.199997	134.199997	154	144	194	189	\N
48	6	11.6000004	11.6000004	11.6000004	30	\N	15	145.199997	135.199997	155	145	195	190	\N
26	4	11.1999998	11.1999998	11.1999998	22	\N	15	136.199997	126.199997	146	136	186	181	2015-09-30 00:00:00
27	6	11.3999996	11.3999996	11.3999996	24	\N	15	137.199997	127.199997	147	137	187	182	2015-09-29 00:00:00
49	2	11	11	11	20	26	\N	146.199997	136.199997	156	146	196	191	2015-09-29 00:00:00
61	2	11.3999996	11.3999996	11.3999996	20	26	\N	134.199997	124.199997	144	134	184	179	\N
62	4	11.6000004	11.6000004	11.6000004	22	26	\N	135.199997	125.199997	145	135	185	180	\N
63	6	11.8000002	11.8000002	11.8000002	24	26	\N	136.199997	126.199997	146	136	186	181	\N
64	2	12	12	12	26	26	\N	137.199997	127.199997	147	137	187	182	\N
65	4	10.1999998	10.1999998	10.1999998	28	26	\N	138.199997	128.199997	148	138	188	183	\N
66	6	10.3999996	10.3999996	10.3999996	30	26	\N	139.199997	129.199997	149	139	189	184	\N
67	2	10.6000004	10.6000004	10.6000004	20	26	\N	140.199997	130.199997	150	140	190	185	\N
68	4	10.8000002	10.8000002	10.8000002	22	26	\N	141.199997	131.199997	151	141	191	186	\N
69	6	11	11	11	24	26	\N	142.199997	132.199997	152	142	192	187	\N
70	2	11.1999998	11.1999998	11.1999998	26	26	\N	143.199997	133.199997	153	143	193	188	\N
71	4	11.3999996	11.3999996	11.3999996	28	26	\N	144.199997	134.199997	154	144	194	189	\N
72	6	11.6000004	11.6000004	11.6000004	30	26	\N	145.199997	135.199997	155	145	195	190	\N
73	2	11	11	11	20	27	\N	146.199997	136.199997	156	146	196	191	\N
74	4	11.1999998	11.1999998	11.1999998	22	27	\N	147.199997	137.199997	157	147	197	192	\N
75	6	11.3999996	11.3999996	11.3999996	24	27	\N	126.199997	116.199997	136	126	176	171	\N
76	2	11.6000004	11.6000004	11.6000004	26	27	\N	127.199997	117.199997	137	127	177	172	\N
77	4	11.8000002	11.8000002	11.8000002	28	27	\N	128.199997	118.199997	138	128	178	173	\N
85	2	11.3999996	11.3999996	11.3999996	20	27	\N	136.199997	126.199997	146	136	186	181	\N
86	4	11.6000004	11.6000004	11.6000004	22	27	\N	137.199997	127.199997	147	137	187	182	\N
87	6	11.8000002	11.8000002	11.8000002	24	27	\N	138.199997	128.199997	148	138	188	183	\N
88	2	12	12	12	26	27	\N	139.199997	129.199997	149	139	189	184	\N
89	4	10.1999998	10.1999998	10.1999998	28	27	\N	140.199997	130.199997	150	140	190	185	\N
90	6	10.3999996	10.3999996	10.3999996	30	27	\N	141.199997	131.199997	151	141	191	186	\N
91	2	10.6000004	10.6000004	10.6000004	20	27	\N	142.199997	132.199997	152	142	192	187	\N
92	4	10.8000002	10.8000002	10.8000002	22	27	\N	143.199997	133.199997	153	143	193	188	\N
93	6	11	11	11	24	27	\N	144.199997	134.199997	154	144	194	189	\N
94	2	11.1999998	11.1999998	11.1999998	26	27	\N	145.199997	135.199997	155	145	195	190	\N
95	4	11.3999996	11.3999996	11.3999996	28	27	\N	146.199997	136.199997	156	146	196	191	\N
96	6	11.6000004	11.6000004	11.6000004	30	27	\N	147.199997	137.199997	157	147	197	192	\N
97	2	11	11	11	20	108	\N	124.199997	114.199997	134	124	174	169	\N
98	4	11.1999998	11.1999998	11.1999998	22	108	\N	125.199997	115.199997	135	125	175	170	\N
59	4	11	11	11	28	26	\N	132.199997	122.199997	142	132	182	177	2015-09-11 00:00:00
83	4	11	11	11	28	27	\N	134.199997	124.199997	144	134	184	179	2015-09-11 00:00:00
60	6	11.1999998	11.1999998	11.1999998	30	26	\N	133.199997	123.199997	143	133	183	178	2015-09-12 00:00:00
84	6	11.1999998	11.1999998	11.1999998	30	27	\N	135.199997	125.199997	145	135	185	180	2015-09-12 00:00:00
58	2	10.8000002	10.8000002	10.8000002	26	109	\N	131.199997	121.199997	141	131	181	176	2015-09-10 00:00:00
82	2	10.8000002	10.8000002	10.8000002	26	109	\N	133.199997	123.199997	143	133	183	178	2015-09-10 00:00:00
101	4	11.8000002	11.8000002	11.8000002	28	108	\N	128.199997	118.199997	138	128	178	173	\N
109	2	11.3999996	11.3999996	11.3999996	20	108	\N	136.199997	126.199997	146	136	186	181	\N
110	4	11.6000004	11.6000004	11.6000004	22	108	\N	137.199997	127.199997	147	137	187	182	\N
111	6	11.8000002	11.8000002	11.8000002	24	108	\N	138.199997	128.199997	148	138	188	183	\N
112	2	12	12	12	26	108	\N	139.199997	129.199997	149	139	189	184	\N
113	4	10.1999998	10.1999998	10.1999998	28	108	\N	140.199997	130.199997	150	140	190	185	\N
114	6	10.3999996	10.3999996	10.3999996	30	108	\N	141.199997	131.199997	151	141	191	186	\N
115	2	10.6000004	10.6000004	10.6000004	20	108	\N	142.199997	132.199997	152	142	192	187	\N
116	4	10.8000002	10.8000002	10.8000002	22	108	\N	143.199997	133.199997	153	143	193	188	\N
117	6	11	11	11	24	108	\N	144.199997	134.199997	154	144	194	189	\N
118	2	11.1999998	11.1999998	11.1999998	26	108	\N	145.199997	135.199997	155	145	195	190	\N
119	4	11.3999996	11.3999996	11.3999996	28	108	\N	146.199997	136.199997	156	146	196	191	\N
120	6	11.6000004	11.6000004	11.6000004	30	108	\N	147.199997	137.199997	157	147	197	192	\N
121	2	11	11	11	20	109	\N	124.199997	114.199997	134	124	174	169	\N
122	4	11.1999998	11.1999998	11.1999998	22	109	\N	125.199997	115.199997	135	125	175	170	\N
123	6	11.3999996	11.3999996	11.3999996	24	109	\N	126.199997	116.199997	136	126	176	171	\N
124	2	11.6000004	11.6000004	11.6000004	26	109	\N	127.199997	117.199997	137	127	177	172	\N
125	4	11.8000002	11.8000002	11.8000002	28	109	\N	128.199997	118.199997	138	128	178	173	\N
133	2	11.3999996	11.3999996	11.3999996	20	109	\N	136.199997	126.199997	146	136	186	181	\N
134	4	11.6000004	11.6000004	11.6000004	22	109	\N	137.199997	127.199997	147	137	187	182	\N
135	6	11.8000002	11.8000002	11.8000002	24	109	\N	138.199997	128.199997	148	138	188	183	\N
136	2	12	12	12	26	109	\N	139.199997	129.199997	149	139	189	184	\N
137	4	10.1999998	10.1999998	10.1999998	28	109	\N	140.199997	130.199997	150	140	190	185	\N
138	6	10.3999996	10.3999996	10.3999996	30	109	\N	141.199997	131.199997	151	141	191	186	\N
139	2	10.6000004	10.6000004	10.6000004	20	109	\N	142.199997	132.199997	152	142	192	187	\N
140	4	10.8000002	10.8000002	10.8000002	22	109	\N	143.199997	133.199997	153	143	193	188	\N
141	6	11	11	11	24	109	\N	144.199997	134.199997	154	144	194	189	\N
142	2	11.1999998	11.1999998	11.1999998	26	109	\N	145.199997	135.199997	155	145	195	190	\N
143	4	11.3999996	11.3999996	11.3999996	28	109	\N	146.199997	136.199997	156	146	196	191	\N
144	6	11.6000004	11.6000004	11.6000004	30	109	\N	147.199997	137.199997	157	147	197	192	\N
102	6	12	12	12	30	108	\N	129.199997	119.199997	139	129	179	174	2015-09-06 00:00:00
126	6	12	12	12	30	109	\N	129.199997	119.199997	139	129	179	174	2015-09-06 00:00:00
103	2	10.1999998	10.1999998	10.1999998	20	108	\N	130.199997	120.199997	140	130	180	175	2015-09-07 00:00:00
127	2	10.1999998	10.1999998	10.1999998	20	109	\N	130.199997	120.199997	140	130	180	175	2015-09-07 00:00:00
104	4	10.3999996	10.3999996	10.3999996	22	108	\N	131.199997	121.199997	141	131	181	176	2015-09-08 00:00:00
128	4	10.3999996	10.3999996	10.3999996	22	109	\N	131.199997	121.199997	141	131	181	176	2015-09-08 00:00:00
105	6	10.6000004	10.6000004	10.6000004	24	108	\N	132.199997	122.199997	142	132	182	177	2015-09-09 00:00:00
129	6	10.6000004	10.6000004	10.6000004	24	109	\N	132.199997	122.199997	142	132	182	177	2015-09-09 00:00:00
106	2	10.8000002	10.8000002	10.8000002	26	108	\N	133.199997	123.199997	143	133	183	178	2015-09-10 00:00:00
130	2	10.8000002	10.8000002	10.8000002	26	109	\N	133.199997	123.199997	143	133	183	178	2015-09-10 00:00:00
107	4	11	11	11	28	108	\N	134.199997	124.199997	144	134	184	179	2015-09-11 00:00:00
131	4	11	11	11	28	109	\N	134.199997	124.199997	144	134	184	179	2015-09-11 00:00:00
108	6	11.1999998	11.1999998	11.1999998	30	108	\N	135.199997	125.199997	145	135	185	180	2015-09-12 00:00:00
132	6	11.1999998	11.1999998	11.1999998	30	109	\N	135.199997	125.199997	145	135	185	180	2015-09-12 00:00:00
99	6	11.3999996	11.3999996	11.3999996	24	117	\N	126.199997	116.199997	136	126	176	171	2015-09-08 00:00:00
100	2	11.6000004	11.6000004	11.6000004	26	118	\N	127.199997	117.199997	137	127	177	172	2015-09-08 00:00:00
1	2	11	11	11	20	176	\N	135.199997	125.199997	145	135	185	180	2015-09-01 00:00:00
2	4	11.1999998	11.1999998	11.1999998	22	176	\N	136.199997	126.199997	146	136	186	181	2015-09-02 00:00:00
3	6	11.3999996	11.3999996	11.3999996	24	176	\N	137.199997	127.199997	147	137	187	182	2015-09-03 00:00:00
4	2	11.6000004	11.6000004	11.6000004	26	176	\N	138.199997	128.199997	148	138	188	183	2015-09-04 00:00:00
5	4	11.8000002	11.8000002	11.8000002	28	176	\N	139.199997	129.199997	149	139	189	184	2015-09-05 00:00:00
6	6	12	12	12	30	176	\N	140.199997	130.199997	150	140	190	185	2015-09-06 00:00:00
7	2	10.1999998	10.1999998	10.1999998	20	176	\N	141.199997	131.199997	151	141	191	186	2015-09-07 00:00:00
8	4	10.3999996	10.3999996	10.3999996	22	176	\N	142.199997	132.199997	152	142	192	187	2015-09-08 00:00:00
9	6	10.6000004	10.6000004	10.6000004	24	176	\N	143.199997	133.199997	153	143	193	188	2015-09-09 00:00:00
10	2	10.8000002	10.8000002	10.8000002	26	176	\N	144.199997	134.199997	154	144	194	189	2015-09-10 00:00:00
11	4	11	11	11	28	176	\N	145.199997	135.199997	155	145	195	190	2015-09-11 00:00:00
12	6	11.1999998	11.1999998	11.1999998	30	176	\N	146.199997	136.199997	156	146	196	191	2015-09-12 00:00:00
50	4	11.1999998	11.1999998	11.1999998	22	26	\N	147.199997	137.199997	157	147	197	192	2015-09-02 00:00:00
51	6	11.3999996	11.3999996	11.3999996	24	26	\N	124.199997	114.199997	134	124	174	169	2015-09-03 00:00:00
52	2	11.6000004	11.6000004	11.6000004	26	26	\N	125.199997	115.199997	135	125	175	170	2015-09-04 00:00:00
53	4	11.8000002	11.8000002	11.8000002	28	26	\N	126.199997	116.199997	136	126	176	171	2015-09-05 00:00:00
54	6	12	12	12	30	26	\N	127.199997	117.199997	137	127	177	172	2015-09-06 00:00:00
55	2	10.1999998	10.1999998	10.1999998	20	26	\N	128.199997	118.199997	138	128	178	173	2015-09-07 00:00:00
56	4	10.3999996	10.3999996	10.3999996	22	26	\N	129.199997	119.199997	139	129	179	174	2015-09-08 00:00:00
57	6	10.6000004	10.6000004	10.6000004	24	26	\N	130.199997	120.199997	140	130	180	175	2015-09-09 00:00:00
81	6	10.6000004	10.6000004	10.6000004	24	27	\N	132.199997	122.199997	142	132	182	177	2015-09-09 00:00:00
80	4	10.3999996	10.3999996	10.3999996	22	27	\N	131.199997	121.199997	141	131	181	176	2015-09-08 00:00:00
79	2	10.1999998	10.1999998	10.1999998	20	27	\N	130.199997	120.199997	140	130	180	175	2015-09-07 00:00:00
78	6	12	12	12	30	27	\N	129.199997	119.199997	139	129	179	174	2015-09-06 00:00:00
146	6	11.1999998	11.1999998	11.1999998	30	109	\N	135.199997	125.199997	145	135	185	180	2014-12-30 00:00:00
147	6	11.1999998	11.1999998	11.1999998	40	109	\N	135.199997	125.199997	145	135	185	180	2015-01-02 00:00:00
145	6	5.30000019	6.30000019	9.5	22	109	\N	135.199997	125.199997	145	135	185	180	2015-10-05 00:00:00
148	6	17.5	22.6000004	26.1000004	30	26	\N	135.199997	125.199997	145	135	185	180	2015-10-09 00:00:00
\.


--
-- TOC entry 2886 (class 0 OID 0)
-- Dependencies: 264
-- Name: power_consumption_capacity_daily_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_daily_id_seq', 1000, false);


--
-- TOC entry 2887 (class 0 OID 0)
-- Dependencies: 265
-- Name: power_consumption_capacity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_id_seq', 5, true);


--
-- TOC entry 2815 (class 0 OID 20082)
-- Dependencies: 266
-- Data for Name: power_consumption_capacity_monthly; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY power_consumption_capacity_monthly (id, co2emission, month, outdoortemp, roomtemp, setpointtemp, total_power_consumption, year, indoorunit_id, outdoorunit_id, efficiency_cop, efficiency_seer, currentcapacity_heating, currentcapacity_cooling, totalcapacity_heating, totalcapacity_cooling, logtime) FROM stdin;
21	158	10	11	11	11	992	2015	27	\N	40.2000008	40.2000008	65	70	90	85	\N
2	162	6	11.1000004	22.2000008	10.1999998	996	2015	26	\N	40.2000008	30.2000008	65	70	90	85	\N
3	158	7	12.1999998	15.5	12.3000002	1004	2015	26	\N	12.1999998	32.2000008	65	70	90	85	\N
4	192	5	13.1999998	17.8999996	14	1200	2015	27	\N	13.3000002	25.2999992	65	70	90	85	\N
5	158	6	11	11	2	992	2015	27	\N	14.3999996	36.5	65	70	90	85	\N
6	192	7	11.1999998	11.1999998	15.1999998	1200	2015	27	\N	15.5	15.3999996	65	70	90	85	\N
7	158	5	14.5	30.2000008	16.1000004	1004	2015	108	\N	16.6000004	40.2000008	65	70	90	85	\N
8	160	6	16.2000008	17.2000008	20.1000004	1000	2015	108	\N	17.7000008	44.4000015	65	70	90	85	\N
1	160	5	10.1000004	11.1000004	11.1000004	1000	2015	26	\N	10.1000004	40.2000008	65	70	90	85	\N
18	158	7	26	11.1000004	11.1000004	992	2015	\N	16	30.2000008	36.2000008	65	70	90	85	\N
19	158	7	100	150	100	100	2015	117	\N	\N	\N	\N	\N	\N	\N	\N
20	158	7	100	80	100	100	2015	26	\N	\N	\N	\N	\N	\N	\N	\N
9	192	7	18.8999996	10.1999998	22.2000008	1200	2015	108	\N	18.7999992	20.2000008	65	70	90	85	\N
10	160	5	9	9.5	19.8999996	1000	2015	109	\N	19.8999996	21.1000004	65	70	90	85	\N
11	162	6	8	10.1999998	23.6000004	1008	2015	109	\N	20.2000008	24.8999996	65	70	90	85	\N
12	158	7	10	11.8999996	31.2000008	992	2015	109	\N	21.2000008	30.2999992	65	70	90	85	\N
13	192	5	20	17.5	17.5	1200	2015	\N	15	23.2999992	18.3999996	65	70	90	85	\N
14	158	6	21	23.2999992	9	992	2015	\N	15	22.3999996	44.5	65	70	90	85	\N
15	192	7	22	24.2000008	8	1200	2015	\N	15	25.6000004	28.3999996	65	70	90	85	\N
16	158	5	23	11.1000004	10.5	992	2015	\N	16	27.7999992	32.5	65	70	90	85	\N
17	192	6	34	11.1000004	24	1200	2015	\N	16	29.5	18.5	65	70	90	85	\N
\.


--
-- TOC entry 2888 (class 0 OID 0)
-- Dependencies: 267
-- Name: power_consumption_capacity_monthly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_monthly_id_seq', 1000, false);


--
-- TOC entry 2817 (class 0 OID 20087)
-- Dependencies: 268
-- Data for Name: power_consumption_capacity_weekly; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY power_consumption_capacity_weekly (id, co2emission, outdoortemp, roomtemp, setpointtemp, total_power_consumption, week, year, indoorunit_id, outdoorunit_id, efficiency_cop, efficiency_seer, currentcapacity_heating, currentcapacity_cooling, totalcapacity_heating, totalcapacity_cooling, logtime) FROM stdin;
3	32	11.1999998	11.1999998	11.1999998	200	30	2015	26	\N	40.2000008	40.2000008	65	70	90	85	\N
4	30	11.1000004	11.1000004	11.1000004	204	31	2015	26	\N	40.2000008	40.2000008	65	70	90	85	\N
5	34	11	11	11	208	28	2015	27	\N	40.2000008	40.2000008	65	70	90	85	\N
6	32	11.1999998	11.1999998	11.1999998	200	29	2015	27	\N	40.2000008	40.2000008	65	70	90	85	\N
7	30	11.3000002	11.3000002	11.3000002	192	30	2015	27	\N	40.2000008	40.2000008	65	70	90	85	\N
8	34	10.8999996	10.8999996	10.8999996	196	31	2015	27	\N	40.2000008	40.2000008	65	70	90	85	\N
9	32	11.1000004	11.1000004	11.1000004	200	28	2015	108	\N	40.2000008	40.2000008	65	70	90	85	\N
10	30	11.1999998	11.1999998	11.1999998	204	29	2015	108	\N	40.2000008	40.2000008	65	70	90	85	\N
13	192	11.1999998	11.1999998	11.1999998	192	28	2015	109	\N	40.2000008	40.2000008	65	70	90	85	\N
17	34	11.3000002	11.3000002	11.3000002	208	28	2015	\N	16	40.2000008	40.2000008	65	70	90	85	\N
18	32	10.8999996	10.8999996	10.8999996	200	29	2015	\N	16	40.2000008	40.2000008	65	70	90	85	\N
19	30	11.1000004	11.1000004	11.1000004	192	30	2015	\N	16	40.2000008	40.2000008	65	70	90	85	\N
20	196	11.1000004	11.1000004	11.1000004	196	31	2015	\N	16	40.2000008	40.2000008	65	70	90	85	\N
21	192	11.1999998	11.1999998	11.1999998	192	28	2015	\N	15	40.2000008	40.2000008	65	70	90	85	\N
22	196	11.1000004	11.1000004	11.1000004	196	29	2015	\N	15	40.2000008	40.2000008	65	70	90	85	\N
23	200	11	11	11	200	30	2015	\N	15	40.2000008	40.2000008	65	70	90	85	\N
24	30	11.1999998	11.1999998	11.1999998	204	31	2015	\N	15	40.2000008	40.2000008	65	70	90	85	\N
28	32	11.1999998	11.1999998	11.1999998	200	29	2015	27	\N	40.2000008	40.2000008	65	70	90	85	\N
29	30	10.8999996	10.8999996	10.8999996	192	1	2015	26	\N	40.2000008	40.2000008	65	70	90	85	\N
1	30	10.8999996	10.8999996	10.8999996	192	28	2015	26	\N	40.2000008	40.2000008	65	70	90	85	\N
30	30	10.8999996	10.8999996	10.8999996	100	52	2014	26	\N	40.2000008	40.2000008	65	70	90	85	\N
14	196	11.1000004	11.1000004	11.1000004	196	36	2015	109	\N	40.2000008	40.2000008	65	70	90	85	\N
31	30	10.8999996	10.8999996	10.8999996	175	52	2015	26	\N	40.2000008	40.2000008	65	70	90	85	\N
11	208	10.8999996	10.8999996	10.8999996	208	34	2015	108	\N	40.2000008	40.2000008	65	70	90	85	\N
12	200	11.1000004	11.1000004	11.1000004	200	35	2015	108	\N	40.2000008	40.2000008	65	70	90	85	\N
15	200	11	11	11	200	37	2015	109	\N	40.2000008	40.2000008	65	70	90	85	\N
2	34	11.1000004	11.1000004	11.1000004	196	38	2015	26	\N	40.2000008	40.2000008	65	70	90	85	\N
32	30	10.8999996	10.8999996	10.8999996	300	52	2011	26	\N	40.2000008	40.2000008	65	70	90	85	\N
16	30	11.1999998	11.1999998	11.1999998	204	39	2015	109	\N	40.2000008	40.2000008	65	70	90	85	\N
25	30	10.8999996	10.8999996	10.8999996	192	41	2015	26	\N	40.2000008	40.2000008	65	70	90	85	\N
26	34	11.1000004	11.1000004	11.1000004	196	41	2015	26	\N	40.2000008	40.2000008	65	70	90	85	\N
27	34	11	11	11	208	41	2015	27	\N	40.2000008	40.2000008	65	70	90	85	\N
\.


--
-- TOC entry 2889 (class 0 OID 0)
-- Dependencies: 269
-- Name: power_consumption_capacity_weekly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_weekly_id_seq', 100, false);


--
-- TOC entry 2819 (class 0 OID 20092)
-- Dependencies: 270
-- Data for Name: power_consumption_capacity_yearly; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY power_consumption_capacity_yearly (id, co2emission, outdoortemp, roomtemp, setpointtemp, total_power_consumption, year, indoorunit_id, outdoorunit_id, efficiency_cop, efficiency_seer, currentcapacity_heating, currentcapacity_cooling, totalcapacity_heating, totalcapacity_cooling, logtime) FROM stdin;
1	60.399999999999999	9	10	25	1001	2013	26	\N	40.0999985	40.2999992	61	65	88	88	\N
2	60.5	10	11	26	1002	2014	26	\N	40.2000008	40.4000015	62	66	89	89	\N
3	60.600000000000001	11	12	27	1003	2015	26	\N	40.2999992	40.5	63	67	90	90	\N
4	60.700000000000003	12	13	28	1004	2013	27	\N	40.4000015	40.5999985	64	68	91	91	\N
5	60.799999999999997	13	14	29	1005	2014	27	\N	40.5	40.7000008	65	69	92	92	\N
6	60.899999999999999	14	15	30	1006	2015	27	\N	40.5999985	40.7999992	66	70	93	93	\N
7	61.100000000000001	15	22	10.1000004	1007	2013	\N	15	40.7000008	40.9000015	67	71	94	94	\N
8	61.200000000000003	16	22	10.1000004	1008	2014	\N	15	40.7999992	41	68	72	95	95	\N
9	61.299999999999997	17	22	10.1000004	1009	2015	\N	15	40.9000015	41.0999985	69	73	96	96	\N
10	61.399999999999999	18	22	10.1000004	1010	2013	\N	16	41	41.2000008	70	74	97	97	\N
11	61.5	19	22	10.1000004	1011	2014	\N	16	41.0999985	41.2999992	71	75	98	98	\N
12	61.600000000000001	20	22	10.1000004	1012	2015	\N	16	41.2000008	41.4000015	72	76	99	99	\N
13	60.399999999999999	9	10	25	1001	2013	108	\N	40.0999985	40.2999992	61	65	88	88	\N
14	60.5	10	11	26	1002	2014	108	\N	40.2000008	40.4000015	62	66	89	89	\N
15	60.600000000000001	11	12	27	1003	2015	108	\N	40.2999992	40.5	63	67	90	90	\N
16	60.700000000000003	12	13	28	1004	2013	109	\N	40.4000015	40.5999985	64	68	91	91	\N
17	60.799999999999997	13	14	29	1005	2014	109	\N	40.5	40.7000008	65	69	92	92	\N
18	60.899999999999999	14	15	30	1006	2015	109	\N	40.5999985	40.7999992	66	70	93	93	\N
\.


--
-- TOC entry 2890 (class 0 OID 0)
-- Dependencies: 271
-- Name: power_consumption_capacity_yearly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_yearly_id_seq', 1000, false);


--
-- TOC entry 2821 (class 0 OID 20097)
-- Dependencies: 272
-- Data for Name: ratingmaster; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ratingmaster (id, colorcode, rating, rating_range) FROM stdin;
1	#008500	A	3.6-10
2	#008501	B	3.4-3.6
3	#008502	C	3.2-3.4
4	#008502	D	2.8-3.2
\.


--
-- TOC entry 2891 (class 0 OID 0)
-- Dependencies: 273
-- Name: ratingmaster_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ratingmaster_id_seq', 1, false);


--
-- TOC entry 2823 (class 0 OID 20105)
-- Dependencies: 274
-- Data for Name: rc_prohibition; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rc_prohibition (id, createdby, creationdate, ison, rcprohibitfanspeed, rcprohibitmode, rcprohibitpwr, rcprohibitsettemp, rcprohibitvanepos, updatedate, updatedby, indoorunit_id) FROM stdin;
8	\N	2015-08-13 20:14:46	\N	0	0	0	1	0	2015-08-14 21:35:52	\N	29
9	\N	2015-08-13 20:14:46	\N	0	0	0	1	0	2015-08-14 21:35:52	\N	30
10	\N	2015-08-13 20:14:46	\N	0	0	0	1	0	2015-08-14 21:35:52	\N	31
17	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:53	\N	38
18	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:53	\N	39
19	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:53	\N	40
20	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:53	\N	41
21	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:53	\N	42
22	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:53	\N	43
23	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:53	\N	44
39	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:55	\N	60
40	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:55	\N	61
41	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:55	\N	62
42	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:55	\N	63
43	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:55	\N	64
44	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:55	\N	65
45	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:55	\N	66
47	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:55	\N	68
48	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:56	\N	69
49	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:56	\N	70
50	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:56	\N	71
51	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:46	\N	72
52	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:46	\N	73
53	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:46	\N	74
54	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:46	\N	75
56	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:46	\N	77
57	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:46	\N	78
59	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:46	\N	80
92	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	113
93	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	114
110	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	131
111	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	132
112	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	133
113	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	134
11	\N	2015-08-13 20:14:46	1	1	1	0	1	1	2015-08-14 21:35:52	\N	32
12	\N	2015-08-13 20:14:46	1	1	1	0	1	1	2015-08-14 21:35:52	\N	33
13	\N	2015-08-13 20:14:46	1	1	1	0	1	1	2015-08-14 21:35:52	\N	34
14	\N	2015-08-13 20:14:46	1	1	1	0	1	1	2015-08-14 21:35:53	\N	35
15	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:53	\N	36
16	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:53	\N	37
24	\N	2015-08-13 20:14:47	1	1	1	0	0	1	2015-08-14 21:35:53	\N	45
25	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	46
26	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	47
27	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	48
28	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	49
6	\N	2015-08-13 20:14:46	1	1	1	0	1	1	2015-08-14 21:35:52	\N	27
7	\N	2015-08-13 20:14:46	1	1	1	0	1	1	2015-08-14 21:35:52	\N	28
102	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:50	\N	123
103	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:50	\N	124
104	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:50	\N	125
105	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:50	\N	126
106	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:50	\N	127
114	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	135
115	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	136
116	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	137
117	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	138
118	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	139
119	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	140
120	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	141
121	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	142
122	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	143
123	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	144
124	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	145
125	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	146
126	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	147
127	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	148
128	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:38	\N	149
129	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:39	\N	150
130	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:39	\N	151
131	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:39	\N	152
132	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:39	\N	153
133	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:39	\N	154
134	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:39	\N	155
135	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:39	\N	156
136	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:39	\N	157
137	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:39	\N	158
138	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:40	\N	159
139	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:40	\N	160
140	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:40	\N	161
141	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:40	\N	162
142	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:40	\N	163
143	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:41	\N	164
144	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:41	\N	165
145	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:41	\N	166
146	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:41	\N	167
147	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:41	\N	168
148	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:41	\N	169
149	\N	2015-08-13 20:14:49	\N	0	0	1	1	0	2015-08-14 21:35:41	\N	170
150	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:41	\N	171
151	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:41	\N	172
152	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:41	\N	173
153	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	174
154	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	175
155	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	176
156	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	177
157	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	178
158	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	179
159	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	180
160	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	181
161	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	182
162	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	183
163	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	184
164	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	185
165	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:42	\N	186
166	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	187
167	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	188
168	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	189
169	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	190
170	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	191
171	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	192
172	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	193
173	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	194
174	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	195
175	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	196
176	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:43	\N	197
177	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	198
178	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	199
179	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	200
180	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	201
181	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	202
182	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	203
183	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	204
184	\N	2015-08-13 20:14:49	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	205
185	\N	2015-08-13 20:14:50	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	206
186	\N	2015-08-13 20:14:50	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	207
187	\N	2015-08-13 20:14:50	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	208
188	\N	2015-08-13 20:14:50	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	209
189	\N	2015-08-13 20:14:50	\N	0	0	0	1	0	2015-08-14 21:35:44	\N	210
190	\N	2015-08-13 20:14:50	\N	0	0	0	1	0	2015-08-14 21:35:45	\N	211
191	\N	2015-08-13 20:14:50	\N	0	0	0	1	0	2015-08-14 21:35:45	\N	212
46	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:55	\N	67
55	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:46	\N	76
58	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:46	\N	79
5	\N	2015-08-13 20:14:46	1	1	1	0	1	1	2015-08-14 21:35:52	\N	26
29	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	50
30	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	51
60	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:46	\N	81
61	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:46	\N	82
62	\N	2015-08-13 20:14:47	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	83
63	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	84
64	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	85
65	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	86
66	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	87
67	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	88
68	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	89
69	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	90
70	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	91
71	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	92
72	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:47	\N	93
73	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	94
74	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	95
75	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	96
76	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	97
77	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	98
78	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	99
79	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	100
80	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	101
81	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	102
82	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	103
83	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:48	\N	104
84	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	105
86	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	107
87	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	108
88	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	109
89	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	110
90	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	111
91	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	112
31	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	52
32	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	53
33	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	54
34	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	55
35	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:54	\N	56
36	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:55	\N	57
37	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:55	\N	58
38	\N	2015-08-13 20:14:47	1	1	1	0	1	1	2015-08-14 21:35:55	\N	59
85	\N	2015-08-13 20:14:48	1	1	1	0	1	1	2015-08-14 21:35:49	\N	106
98	\N	2015-08-13 20:14:48	1	1	1	0	1	1	2015-08-14 21:35:50	\N	119
99	\N	2015-08-13 20:14:48	1	1	1	0	1	1	2015-08-14 21:35:50	\N	120
100	\N	2015-08-13 20:14:48	1	1	1	0	1	1	2015-08-14 21:35:50	\N	121
101	\N	2015-08-13 20:14:48	1	1	1	0	1	1	2015-08-14 21:35:50	\N	122
94	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:49	\N	115
95	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:50	\N	116
96	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:50	\N	117
97	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:50	\N	118
107	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:36	\N	128
108	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:36	\N	129
109	\N	2015-08-13 20:14:48	\N	0	0	0	1	0	2015-08-14 21:35:37	\N	130
4	\N	2015-08-13 20:14:46	\N	0	0	1	1	0	2015-08-14 21:35:52	\N	25
\.


--
-- TOC entry 2892 (class 0 OID 0)
-- Dependencies: 275
-- Name: rc_prohibition_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rc_prohibition_id_seq', 1, false);


--
-- TOC entry 2851 (class 0 OID 23121)
-- Dependencies: 302
-- Data for Name: rcoperation_log; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rcoperation_log (id, creationdate, requestedtime, indoorunit_id, user_id, success, airconmode, temperature, fanspeed, flapmode, powerstatus, energysaving, prohibitionpowerstatus, prohibitonmode, prohibitionfanspeed, prohibitionwindriection, prohibitionsettemp) FROM stdin;
1	2015-11-20 22:58:05.924	2015-11-20 22:58:05.924	29	2	t	COOL	30.0	LOW	F1	ON	0	0	0	0	0	0
2	2015-11-24 13:57:12.558	2015-11-24 13:57:12.558	26	1	f	heat	25	\N	\N	ON	\N	\N	\N	\N	\N	\N
3	2015-11-24 13:57:12.558	2015-11-24 13:57:12.558	25	1	f	heat	25	\N	\N	ON	\N	\N	\N	\N	\N	\N
\.


--
-- TOC entry 2893 (class 0 OID 0)
-- Dependencies: 301
-- Name: rcoperation_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rcoperation_log_id_seq', 1, false);


--
-- TOC entry 2749 (class 0 OID 19877)
-- Dependencies: 200
-- Data for Name: rcoperation_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rcoperation_master (id, name) FROM stdin;
1	Mode
2	Tempreture
3	Fanspeed
4	Winddirection
5	PowerStatus
\.


--
-- TOC entry 2751 (class 0 OID 19882)
-- Dependencies: 202
-- Data for Name: rcuser_action; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rcuser_action (id, command, createdby, creationdate, device_id, devicetype, updatedate, updatedby, value, user_id) FROM stdin;
\.


--
-- TOC entry 2825 (class 0 OID 20113)
-- Dependencies: 276
-- Data for Name: rolepermissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY rolepermissions (id, createdby, creationdate, permission_value, updatedate, updatedby, permission_id, roles_id) FROM stdin;
1	\N	2015-02-23 19:18:09	7	2015-07-03 14:50:38	\N	1	1
2	\N	2015-02-23 19:18:09	7	2015-07-03 14:50:38	\N	2	1
3	\N	2015-02-23 19:18:09	7	2015-07-03 14:50:38	\N	3	1
4	\N	2015-02-23 19:18:09	7	2015-07-03 14:50:38	\N	4	1
6	\N	2015-02-23 19:18:10	1	2015-07-03 14:50:38	\N	2	5
7	\N	2015-02-23 19:18:10	0	2015-07-03 14:50:38	\N	3	5
\.


--
-- TOC entry 2894 (class 0 OID 0)
-- Dependencies: 277
-- Name: rolepermissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rolepermissions_id_seq', 1, false);


--
-- TOC entry 2895 (class 0 OID 0)
-- Dependencies: 279
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('roles_id_seq', 1, false);


--
-- TOC entry 2829 (class 0 OID 20129)
-- Dependencies: 280
-- Data for Name: session; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY session (id, createdby, creationdate, email, status, uniquesessionid, updatedate, updatedby) FROM stdin;
3027	RSI	2015-08-14 20:12:00	testtoken@rsystems.com	0	D0C709CE16F171B0386AD2D0C9BDE05A	2015-08-14 20:12:00	RSI
3028	RSI	2015-08-15 20:12:00	testtoken@rsystems.com	0	8FB230197571A7866E748EB81ECBBFA7	2015-08-15 20:12:00	RSI
3029	RSI	2015-08-16 20:12:00	testtoken@rsystems.com	0	5679F660BE3E4B43F35E1BB05DC5C49B	2015-08-16 20:12:00	RSI
3030	RSI	2015-08-17 20:12:00	testtoken@rsystems.com	0	4DD9D831CDA959F477F8A56A75E847DB	2015-08-17 20:12:00	RSI
3031	RSI	2015-08-18 20:12:00	testtoken@rsystems.com	0	3B231150BE01A602AB939ECA5D01F383	2015-08-18 20:12:00	RSI
3032	RSI	2015-08-19 20:12:00	testtoken@rsystems.com	0	D27FF17BFA92F2F7E9DF7A4F2D416FCE	2015-08-19 20:12:00	RSI
3033	RSI	2015-08-20 20:12:00	testtoken@rsystems.com	0	C04544FFBF90E2AEE006AD8705A724C7	2015-08-20 20:12:00	RSI
3034	RSI	2015-08-21 20:12:00	testtoken@rsystems.com	0	B26DE3013A9798A5FEFA4A461AE871B7	2015-08-21 20:12:00	RSI
3035	RSI	2015-08-22 20:12:00	testtoken@rsystems.com	0	55640BB526927FA42086BAAD40045C9F	2015-08-22 20:12:00	RSI
3036	RSI	2015-08-23 20:12:00	testtoken@rsystems.com	0	189C70C128A8639FC02D9531AF206E56	2015-08-23 20:12:00	RSI
3037	RSI	2015-08-24 20:12:00	testtoken@rsystems.com	0	471E9C35780E01EED5C2F5C31ADFA307	2015-08-24 20:12:00	RSI
3038	RSI	2015-08-25 20:12:00	testtoken@rsystems.com	0	701426B63E31777FF1A049B95E771E33	2015-08-25 20:12:00	RSI
3039	RSI	2015-08-26 20:12:00	testtoken@rsystems.com	0	9ADA1905A8E0F8049F0E13B7C3F603D8	2015-08-26 20:12:00	RSI
3040	RSI	2015-08-27 20:12:00	testtoken@rsystems.com	0	62810887F870F589156D64AE5E267E12	2015-08-27 20:12:00	RSI
3041	RSI	2015-08-28 20:12:00	testtoken@rsystems.com	0	4392C604A7784751BC671183B0583D15	2015-08-28 20:12:00	RSI
3042	RSI	2015-08-29 20:12:00	testtoken@rsystems.com	0	ECEC45AFFA341FC5913C026D25786EB2	2015-08-29 20:12:00	RSI
3043	RSI	2015-08-30 20:12:00	testtoken@rsystems.com	0	1044EB2DE0AECAC07C4F01B5E0DA200A	2015-08-30 20:12:00	RSI
3044	RSI	2015-08-31 20:12:00	testtoken@rsystems.com	0	E358BA83C5659DCA609D7BC22132FB74	2015-08-31 20:12:00	RSI
3045	RSI	2015-09-01 20:12:00	testtoken@rsystems.com	0	EF0C425256996FDDF274E39B159918CD	2015-09-01 20:12:00	RSI
3046	RSI	2015-09-02 20:12:00	testtoken@rsystems.com	0	2EB6227CD92320FC9E61ECF2EC0C7E16	2015-09-02 20:12:00	RSI
3047	RSI	2015-09-03 20:12:00	testtoken@rsystems.com	0	BEB5F241FBAFFC4BC29DAABA9FF8D64B	2015-09-03 20:12:00	RSI
3048	RSI	2015-09-04 20:12:00	testtoken@rsystems.com	0	6A6C5856B3332CAAAF2CD51C8C48BB1E	2015-09-04 20:12:00	RSI
3049	RSI	2015-09-05 20:12:00	testtoken@rsystems.com	0	B9FE54C68D39E596F4B8C1C3D19DCB40	2015-09-05 20:12:00	RSI
3050	RSI	2015-09-06 20:12:00	testtoken@rsystems.com	0	3AF0B0109C852916589CDA9527EC0EE2	2015-09-06 20:12:00	RSI
3051	RSI	2015-09-07 20:12:00	testtoken@rsystems.com	0	23303F4F4FE45223C450196A69EAB095	2015-10-07 15:45:40.445	RSI
93	\N	2015-10-07 15:45:40.47	testtoken@rsystems.com	0	FD2D0D8BDAAD91E2CDCE915D741571DA	2015-10-07 15:53:39.558	\N
94	\N	2015-10-07 15:53:40.61	testtoken@rsystems.com	0	1DD0E81197729748C65A1BAF7ADAA3E3	2015-10-07 15:57:17.046	\N
95	\N	2015-10-07 15:57:18.939	testtoken@rsystems.com	0	007589044591194CF356A97B40C4ED17	2015-10-07 16:01:54.131	\N
96	\N	2015-10-07 16:01:54.16	testtoken@rsystems.com	0	E7F08C7723F71BA3EC7644E8F19CF938	2015-10-07 16:07:44.133	\N
99	\N	2015-10-08 18:39:30.543	testtoken@rsystems.com	0	2799DE357575EA64A94EC7AB4DAE0D45	2015-10-08 18:44:36.419	\N
100	\N	2015-10-08 18:44:36.429	testtoken@rsystems.com	0	2A09F2005C3861EAD7AB7A311EAB865F	2015-10-08 18:45:27.23	\N
101	\N	2015-10-08 18:47:54.543	testtoken@rsystems.com	0	39E773927B607FD0F92D75D941BFAB8D	2015-10-27 15:15:31.389	\N
125	\N	2015-10-27 15:16:15.176	testtoken1@rsystems.com	0	8E328334AF5CCF558C58101AF36E504C	2015-10-28 10:23:37.281	\N
126	\N	2015-10-28 10:23:37.321	testtoken1@rsystems.com	0	FB81F88AD72414CD603E6EF645F4D30D	2015-10-28 13:46:42.13	\N
127	\N	2015-10-28 13:46:42.18	testtoken1@rsystems.com	0	1341A2D72711F084048FA4C340A610F1	2015-10-28 15:08:30.909	\N
128	\N	2015-10-28 15:08:30.923	testtoken1@rsystems.com	0	7BD968253EAF654EAC91EC3C4C547F78	2015-10-28 15:11:01.554	\N
129	\N	2015-10-28 15:11:01.567	testtoken1@rsystems.com	0	1F8C40790E694CCA00EF6CF089B8CA48	2015-10-28 15:24:03.859	\N
131	\N	2015-10-28 15:47:59.428	testtoken2@rsystems.com	1	E6FFDE08577B264C887B54EE657A133E	\N	\N
130	\N	2015-10-28 15:24:03.867	testtoken1@rsystems.com	0	2509150B621822482B0A15ECDBDBE9C8	2015-10-29 15:14:28.395	\N
132	\N	2015-10-28 18:32:52.305	testtoken1@rsystems.com	0	0170B5B0A4A4547F2605480D712F183D	2015-10-29 15:14:28.405	\N
133	\N	2015-10-29 15:14:28.683	testtoken1@rsystems.com	0	B51E1482B430EDED87779A46D40BF715	2015-11-01 09:30:57.466	\N
134	\N	2015-11-01 09:30:57.506	testtoken1@rsystems.com	0	ACEC3568F8AF942D38F3530FF16D26FF	2015-11-01 22:02:25.409	\N
135	\N	2015-11-01 22:02:25.443	testtoken1@rsystems.com	0	D61153C3CC7CBB63E037E7F16969A068	2015-11-01 22:51:21.487	\N
3026	RSI	2015-08-13 20:12:00	admin@panasonic.com	0	6AC7EEE40403FBBA9EA651413EA9AB93	2015-11-01 22:51:28.545	RSI
136	\N	2015-11-01 22:51:28.557	admin@panasonic.com	0	70FCD412A92F047A303F401177009A8F	2015-11-02 10:31:40.341	\N
137	\N	2015-11-02 10:17:53.535	admin@panasonic.com	0	960373FA91642E5A8F867D608ACBD8B8	2015-11-02 10:31:40.346	\N
138	\N	2015-11-02 10:31:40.403	admin@panasonic.com	0	5EACA2545FC8965B038A25FAB9C96BCF	2015-11-02 11:07:28.8	\N
139	\N	2015-11-02 11:07:28.837	admin@panasonic.com	0	0B55D6C445042AE82F0F9D359E78B644	2015-11-02 11:12:26.786	\N
140	\N	2015-11-02 11:12:26.817	admin@panasonic.com	0	508F20B218AE4A0BD67683FF3381E8C4	2015-11-02 11:16:06.949	\N
141	\N	2015-11-02 11:16:06.987	admin@panasonic.com	0	CE47075F7BC9775244FF96B26E4DA441	2015-11-02 11:17:03.934	\N
142	\N	2015-11-02 11:17:03.957	admin@panasonic.com	0	31336AB0E875DAF4E393ED609DD79C2E	2015-11-02 12:04:56.172	\N
143	\N	2015-11-02 12:04:56.222	admin@panasonic.com	0	48DB5CB8EC797B8718EEA3D0E9714CB7	2015-11-02 14:04:31.481	\N
144	\N	2015-11-02 14:04:31.516	admin@panasonic.com	0	6CFE817FF42297EFD50EBD2382BBEEDB	2015-11-02 14:18:42.941	\N
145	\N	2015-11-02 14:18:42.982	admin@panasonic.com	0	1308D278D68AEAB11F29B103D28DCF60	2015-11-02 14:32:32.782	\N
146	\N	2015-11-02 14:32:32.841	admin@panasonic.com	0	EAC7A6441ED63DB131D6BB8BF8F6B9AC	2015-11-02 18:32:08.91	\N
148	\N	2015-11-02 18:32:08.98	admin@panasonic.com	0	B05949C3F16BAA5165BFA660F242D562	2015-11-02 19:24:49.094	\N
149	\N	2015-11-04 17:51:20.321	admin@panasonic.com	0	7843F5CF7AE75B2C4AAFAAB9FF1D6B48	2015-11-04 22:32:42.26	\N
150	\N	2015-11-04 21:44:43.879	admin@panasonic.com	0	E6AC50FE2334ABA2B2C71BA06D4A640E	2015-11-04 22:32:42.265	\N
151	\N	2015-11-04 22:32:42.299	admin@panasonic.com	0	2882775DF8DD327A163C17A8E8492373	2015-11-05 16:24:50.662	\N
152	\N	2015-11-05 16:24:50.714	admin@panasonic.com	0	179CB29EFC123CAC6D028DFB47640A1B	2015-11-06 10:08:30.975	\N
153	\N	2015-11-06 10:08:31.002	admin@panasonic.com	0	6CC2AA1160F71040ED64D9B95A5E5978	2015-11-06 13:10:01.606	\N
154	\N	2015-11-06 13:10:01.654	admin@panasonic.com	0	D2B0DC5A02F677F01A7A103E4CFEB046	2015-11-06 14:09:11.5	\N
155	\N	2015-11-06 14:11:20.608	admin@panasonic.com	0	9471506FF987D0062B14CEF4950A6D03	2015-11-09 10:32:45.596	\N
156	\N	2015-11-09 10:32:45.641	admin@panasonic.com	0	BFA9449572DB225F592C3FE74C70F27A	2015-11-09 10:32:51.839	\N
157	\N	2015-11-09 10:32:58.666	admin@panasonic.com	0	9517B48F3CEDD631FD1E92207A746028	2015-11-09 10:33:09.499	\N
147	\N	2015-11-02 14:36:39.215	testtoken1@panasonic.com	0	22F3E64B6C8BA29AB3C35B867C976C77	2015-11-09 11:37:41.83	\N
158	\N	2015-11-09 10:33:30.939	admin@panasonic.com	0	32EC6B35FCB88222396A38C373129FB1	2015-11-09 10:33:34.411	\N
159	\N	2015-11-09 10:33:39.942	admin@panasonic.com	0	30E83052F3BC5500BC54F5913F2D00B7	2015-11-09 10:34:03.673	\N
160	\N	2015-11-09 10:34:13.642	admin@panasonic.com	0	C3268EC4C0EDE24C236E1B3FCFC94B91	2015-11-11 11:20:50.624	\N
161	\N	2015-11-09 11:36:39.731	admin@panasonic.com	0	C2FCC156D4A7B5D7BB7F4F6CFFBF4B7D	2015-11-11 11:20:50.633	\N
163	\N	2015-11-09 14:13:50.936	admin@panasonic.com	0	7EA9C55C09E60209BB92A96362AAF86C	2015-11-11 11:20:50.633	\N
164	\N	2015-11-09 16:02:51.639	admin@panasonic.com	0	C42AE9272534E50AC4B65C927BFA68B3	2015-11-11 11:20:50.634	\N
165	\N	2015-11-09 20:01:30.509	admin@panasonic.com	0	18CAA909085B12FE680D95DF2A1483C0	2015-11-11 11:20:50.634	\N
166	\N	2015-11-11 11:20:50.681	admin@panasonic.com	0	5E277CACC9D42CFF3C519A110DB4221F	2015-11-11 13:43:59.617	\N
167	\N	2015-11-11 13:44:10.694	admin@panasonic.com	0	2FE5FF7025E5639D69A105501D821BD9	2015-11-11 15:59:34.338	\N
168	\N	2015-11-11 15:59:34.414	admin@panasonic.com	0	CB9B55925033F968C94733099DF45F95	2015-11-11 21:08:55.891	\N
169	\N	2015-11-11 21:08:55.926	admin@panasonic.com	0	A7DDDB791309F98E00EFED084887C04E	2015-11-11 22:23:59.56	\N
162	\N	2015-11-09 11:37:41.865	testtoken1@panasonic.com	0	484BA295D1F3E9FD3ECEB9B0876D4CD9	2015-11-11 22:26:27.044	\N
170	\N	2015-11-11 22:23:59.608	admin@panasonic.com	0	1B87D4DC139B45FE6DB994B148AB494B	2015-11-12 10:13:49.715	\N
172	\N	2015-11-12 10:13:49.799	admin@panasonic.com	1	B83226271A2B29262F02468193127590	\N	\N
173	\N	2015-11-12 12:23:06.073	admin@panasonic.com	1	9B3CC916BFB9BB1849E754E850C897A2	\N	\N
174	\N	2015-11-12 18:30:41.477	admin@panasonic.com	1	BA880B670A7B2711ADDD13064D1BA801	\N	\N
175	\N	2015-11-12 20:53:06.324	admin@panasonic.com	1	06D74260CC432B712E73B2E20877638B	\N	\N
171	\N	2015-11-11 22:26:27.156	testtoken1@panasonic.com	0	3496C2527B67ACE7DA4DA6697F09C703	2015-11-12 21:22:52.81	\N
176	\N	2015-11-12 21:22:52.826	testtoken1@panasonic.com	1	DCC0F2B5F20CDCD704C1466DA9373617	\N	\N
177	\N	2015-11-13 10:03:41.577	admin@panasonic.com	1	5CF01412037349BA0653E27500883313	\N	\N
\.


--
-- TOC entry 2896 (class 0 OID 0)
-- Dependencies: 281
-- Name: session_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('session_id_seq', 177, true);


--
-- TOC entry 2831 (class 0 OID 20146)
-- Dependencies: 282
-- Data for Name: temp_groupunitdata; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY temp_groupunitdata (id, alaramcount, group_id, indoorunit, indoorunitcount, outdoorunit, outdoorunitcount, path) FROM stdin;
\.


--
-- TOC entry 2897 (class 0 OID 0)
-- Dependencies: 283
-- Name: temp_groupunitdata_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('temp_groupunitdata_id_seq', 1, false);


--
-- TOC entry 2833 (class 0 OID 20157)
-- Dependencies: 284
-- Data for Name: tempgroupdata; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tempgroupdata (path, group_id, alarmindoorunitcount, alarmoutdoorunitcount, indoorunitcount, outdoorunitcount, totalalarmcount, severity, groupname, co2emission, efficiency_seer) FROM stdin;
|25|27|31|45	45	26	0	26	0	0	NONCRITICAL		0	0
|25|27|31|45	45	27	0	27	0	0	CRITICAL		0	0
|25|27|31|45	45	0	0	28	0	0	\N		0	0
|25|27|31|45	45	0	0	32	0	0	\N		0	0
|25|27|31|45	45	0	0	33	0	0	\N		0	0
|25|27|31|45	45	0	0	34	0	0	\N		0	0
|25|27|31|45	45	0	0	35	0	0	\N		0	0
|25|27|31|45	45	0	0	36	0	0	\N		0	0
|25|27|31|45	45	0	0	37	0	0	\N		0	0
|25|27|31|45	45	0	0	45	0	0	\N		0	0
|25|27|31|45	45	0	0	46	0	0	\N		0	0
|25|27|31|45	45	0	0	47	0	0	\N		0	0
|25|27|31|45	45	0	0	48	0	0	\N		0	0
|25|27|31|45	45	0	0	49	0	0	\N		0	0
|25|27|31|45	45	0	0	50	0	0	\N		0	0
|25|27|31|45	45	0	0	51	0	0	\N		0	0
|25|27|31|45	45	0	0	52	0	0	\N		0	0
|25|27|31|45	45	0	0	53	0	0	\N		0	0
|25|27|31|45	45	0	0	54	0	0	\N		0	0
|25|27|31|45	45	0	0	55	0	0	\N		0	0
|25|27|31|45	45	0	0	56	0	0	\N		0	0
|25|27|31|45	45	0	0	57	0	0	\N		0	0
|25|27|31|45	45	0	0	58	0	0	\N		0	0
|25|27|31|45	45	0	0	59	0	0	\N		0	0
|25|27|31|45	45	0	0	67	0	0	\N		0	0
|25|27|31|45	45	0	0	76	0	0	\N		0	0
|25|27|31|45	45	0	0	79	0	0	\N		0	0
|25|27|31|45	45	0	0	106	0	0	\N		0	0
|25|27|31|45	45	0	0	119	0	0	\N		0	0
|25|27|31|45	45	0	0	120	0	0	\N		0	0
|25|27|31|45	45	0	0	121	0	0	\N		0	0
|25|27|31|45	45	0	0	122	0	0	\N		0	0
|25|27|31|45	45	26	0	26	0	0	NONCRITICAL		0	0
|25|27|31|45	45	27	0	27	0	0	CRITICAL		0	0
|25|27|31|45	45	0	0	28	0	0	\N		0	0
|25|27|31|45	45	0	0	32	0	0	\N		0	0
|25|27|31|45	45	0	0	33	0	0	\N		0	0
|25|27|31|45	45	0	0	34	0	0	\N		0	0
|25|27|31|45	45	0	0	35	0	0	\N		0	0
|25|27|31|45	45	0	0	36	0	0	\N		0	0
|25|27|31|45	45	0	0	37	0	0	\N		0	0
|25|27|31|45	45	0	0	45	0	0	\N		0	0
|25|27|31|45	45	0	0	46	0	0	\N		0	0
|25|27|31|45	45	0	0	47	0	0	\N		0	0
|25|27|31|45	45	0	0	48	0	0	\N		0	0
|25|27|31|45	45	0	0	49	0	0	\N		0	0
|25|27|31|45	45	0	0	50	0	0	\N		0	0
|25|27|31|45	45	0	0	51	0	0	\N		0	0
|25|27|31|45	45	0	0	52	0	0	\N		0	0
|25|27|31|45	45	0	0	53	0	0	\N		0	0
|25|27|31|45	45	0	0	54	0	0	\N		0	0
|25|27|31|45	45	0	0	55	0	0	\N		0	0
|25|27|31|45	45	0	0	56	0	0	\N		0	0
|25|27|31|45	45	0	0	57	0	0	\N		0	0
|25|27|31|45	45	0	0	58	0	0	\N		0	0
|25|27|31|45	45	0	0	59	0	0	\N		0	0
|25|27|31|45	45	0	0	67	0	0	\N		0	0
|25|27|31|45	45	0	0	76	0	0	\N		0	0
|25|27|31|45	45	0	0	79	0	0	\N		0	0
|25|27|31|45	45	0	0	106	0	0	\N		0	0
|25|27|31|45	45	0	0	119	0	0	\N		0	0
|25|27|31|45	45	0	0	120	0	0	\N		0	0
|25|27|31|45	45	0	0	121	0	0	\N		0	0
|25|27|31|45	45	0	0	122	0	0	\N		0	0
|25|27|31|45	45	26	0	26	0	0	NONCRITICAL		0	0
|25|27|31|45	45	27	0	27	0	0	CRITICAL		0	0
|25|27|31|45	45	0	0	28	0	0	\N		0	0
|25|27|31|45	45	0	0	32	0	0	\N		0	0
|25|27|31|45	45	0	0	33	0	0	\N		0	0
|25|27|31|45	45	0	0	34	0	0	\N		0	0
|25|27|31|45	45	0	0	35	0	0	\N		0	0
|25|27|31|45	45	0	0	36	0	0	\N		0	0
|25|27|31|45	45	0	0	37	0	0	\N		0	0
|25|27|31|45	45	0	0	45	0	0	\N		0	0
|25|27|31|45	45	0	0	46	0	0	\N		0	0
|25|27|31|45	45	0	0	47	0	0	\N		0	0
|25|27|31|45	45	0	0	48	0	0	\N		0	0
|25|27|31|45	45	0	0	49	0	0	\N		0	0
|25|27|31|45	45	0	0	50	0	0	\N		0	0
|25|27|31|45	45	0	0	51	0	0	\N		0	0
|25|27|31|45	45	0	0	52	0	0	\N		0	0
|25|27|31|45	45	0	0	53	0	0	\N		0	0
|25|27|31|45	45	0	0	54	0	0	\N		0	0
|25|27|31|45	45	0	0	55	0	0	\N		0	0
|25|27|31|45	45	0	0	56	0	0	\N		0	0
|25|27|31|45	45	0	0	57	0	0	\N		0	0
|25|27|31|45	45	0	0	58	0	0	\N		0	0
|25|27|31|45	45	0	0	59	0	0	\N		0	0
|25|27|31|45	45	0	0	67	0	0	\N		0	0
|25|27|31|45	45	0	0	76	0	0	\N		0	0
|25|27|31|45	45	0	0	79	0	0	\N		0	0
|25|27|31|45	45	0	0	106	0	0	\N		0	0
|25|27|31|45	45	0	0	119	0	0	\N		0	0
|25|27|31|45	45	0	0	120	0	0	\N		0	0
|25|27|31|45	45	0	0	121	0	0	\N		0	0
|25|27|31|45	45	0	0	122	0	0	\N		0	0
\.


--
-- TOC entry 2834 (class 0 OID 20171)
-- Dependencies: 285
-- Data for Name: timeline; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY timeline (id, createdby, creationdate, state, updatedate, updatedby, adapter_id) FROM stdin;
\.


--
-- TOC entry 2898 (class 0 OID 0)
-- Dependencies: 286
-- Name: timeline_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('timeline_id_seq', 1, false);


--
-- TOC entry 2899 (class 0 OID 0)
-- Dependencies: 288
-- Name: timezonemaster_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('timezonemaster_id_seq', 1, false);


--
-- TOC entry 2838 (class 0 OID 20181)
-- Dependencies: 289
-- Data for Name: user_notification_settings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY user_notification_settings (id, notification_settings_id, user_id) FROM stdin;
22	1950	1
23	1950	2
24	1951	1
25	1951	2
26	38	1
27	38	2
372	38	9
373	38	10
380	38	3
381	38	4
382	38	5
383	38	6
384	38	7
385	38	8
\.


--
-- TOC entry 2900 (class 0 OID 0)
-- Dependencies: 290
-- Name: user_notification_settings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_notification_settings_id_seq', 385, true);


--
-- TOC entry 2840 (class 0 OID 20186)
-- Dependencies: 291
-- Data for Name: useraudit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY useraudit (id, auditaction, createdby, creationdate, updatedate, updatedby, user_id) FROM stdin;
305	failed.signed.in	\N	2015-08-13 20:12:00	\N	\N	\N
306	failed.signed.in	\N	2015-08-13 20:12:01	\N	\N	\N
307	successfully.signed.in	\N	2015-08-13 20:12:46	\N	\N	1
308	failed.signed.in	\N	2015-08-13 20:41:14	\N	\N	\N
309	failed.signed.in	\N	2015-08-13 20:41:23	\N	\N	\N
310	failed.signed.in	\N	2015-08-14 01:15:44	\N	\N	\N
311	failed.signed.in	\N	2015-08-14 01:15:45	\N	\N	\N
312	failed.signed.in	\N	2015-08-14 14:48:37	\N	\N	\N
313	failed.signed.in	\N	2015-08-14 14:48:39	\N	\N	\N
314	failed.signed.in	\N	2015-08-14 14:48:40	\N	\N	\N
315	failed.signed.in	\N	2015-08-14 13:55:11	\N	\N	\N
316	successfully.signed.in	\N	2015-08-14 13:55:46	\N	\N	1
317	successfully.signed.in	\N	2015-08-14 14:47:08	\N	\N	1
319	successfully.signed.in	\N	2015-08-14 15:28:38	\N	\N	1
320	User.logged.out.successfully	\N	2015-08-14 15:28:38	\N	\N	1
321	successfully.signed.in	\N	2015-08-14 15:29:29	\N	\N	1
322	User.logged.out.successfully	\N	2015-08-14 15:29:29	\N	\N	1
323	successfully.signed.in	\N	2015-08-14 15:31:20	\N	\N	1
324	User.logged.out.successfully	\N	2015-08-14 15:31:20	\N	\N	1
325	successfully.signed.in	\N	2015-08-17 06:11:26	\N	\N	1
326	User.logged.out.successfully	\N	2015-08-17 06:11:27	\N	\N	1
327	successfully.signed.in	\N	2015-08-17 06:43:38	\N	\N	1
328	User.logged.out.successfully	\N	2015-08-17 06:43:38	\N	\N	1
329	successfully.signed.in	\N	2015-08-17 07:36:39	\N	\N	1
330	User.logged.out.successfully	\N	2015-08-17 07:36:39	\N	\N	1
331	successfully.signed.in	\N	2015-08-17 07:38:34	\N	\N	1
332	User.logged.out.successfully	\N	2015-08-17 07:38:34	\N	\N	1
333	successfully.signed.in	\N	2015-08-17 07:46:17	\N	\N	1
334	User.logged.out.successfully	\N	2015-08-17 07:46:17	\N	\N	1
335	successfully.signed.in	\N	2015-08-17 07:50:08	\N	\N	1
336	User.logged.out.successfully	\N	2015-08-17 07:50:08	\N	\N	1
337	successfully.signed.in	\N	2015-08-17 08:32:36	\N	\N	1
338	User.logged.out.successfully	\N	2015-08-17 08:32:36	\N	\N	1
339	successfully.signed.in	\N	2015-08-17 10:33:39	\N	\N	1
340	successfully.signed.in	\N	2015-08-17 11:14:30	\N	\N	1
341	User.logged.out.successfully	\N	2015-08-17 11:14:30	\N	\N	1
342	successfully.signed.in	\N	2015-08-17 11:29:50	\N	\N	1
343	User.logged.out.successfully	\N	2015-08-17 11:29:50	\N	\N	1
344	successfully.signed.in	\N	2015-08-17 13:16:34	\N	\N	1
345	User.logged.out.successfully	\N	2015-08-17 13:16:34	\N	\N	1
346	successfully.signed.in	\N	2015-08-17 14:54:53	\N	\N	1
347	User.logged.out.successfully	\N	2015-08-17 14:54:53	\N	\N	1
348	successfully.signed.in	\N	2015-08-18 06:48:30	\N	\N	1
349	User.logged.out.successfully	\N	2015-08-18 06:48:30	\N	\N	1
350	successfully.signed.in	\N	2015-08-18 06:49:44	\N	\N	1
351	User.logged.out.successfully	\N	2015-08-18 06:49:44	\N	\N	1
352	successfully.signed.in	\N	2015-08-18 07:24:28	\N	\N	1
353	User.logged.out.successfully	\N	2015-08-18 07:24:28	\N	\N	1
354	successfully.signed.in	\N	2015-08-18 07:43:25	\N	\N	1
355	User.logged.out.successfully	\N	2015-08-18 07:43:25	\N	\N	1
356	successfully.signed.in	\N	2015-08-18 07:44:01	\N	\N	1
357	User.logged.out.successfully	\N	2015-08-18 07:44:01	\N	\N	1
358	successfully.signed.in	\N	2015-08-18 07:47:22	\N	\N	1
359	User.logged.out.successfully	\N	2015-08-18 07:47:22	\N	\N	1
360	successfully.signed.in	\N	2015-08-18 07:50:45	\N	\N	1
361	User.logged.out.successfully	\N	2015-08-18 07:50:45	\N	\N	1
362	successfully.signed.in	\N	2015-08-18 08:57:27	\N	\N	1
363	User.logged.out.successfully	\N	2015-08-18 08:57:27	\N	\N	1
500	successfully.signed.in	\N	2015-10-07 15:45:40.4	\N	\N	1
501	User.logged.out.successfully	\N	2015-10-07 15:45:40.465	\N	\N	1
502	successfully.signed.in	\N	2015-10-07 15:53:36.641	\N	\N	1
503	User.logged.out.successfully	\N	2015-10-07 15:53:39.665	\N	\N	1
504	successfully.signed.in	\N	2015-10-07 15:57:13.384	\N	\N	1
505	User.logged.out.successfully	\N	2015-10-07 15:57:17.151	\N	\N	1
506	successfully.signed.in	\N	2015-10-07 16:01:52.143	\N	\N	1
507	User.logged.out.successfully	\N	2015-10-07 16:01:54.155	\N	\N	1
508	failed.signed.in	\N	2015-10-07 16:07:44.103	\N	\N	1
509	User.logged.out.successfully	\N	2015-10-07 16:07:44.173	\N	\N	1
510	failed.signed.in	\N	2015-10-07 16:08:36.364	\N	\N	1
511	failed.signed.in	\N	2015-10-07 16:09:32.66	\N	\N	1
512	failed.signed.in	\N	2015-10-07 16:13:34.618	\N	\N	1
513	failed.signed.in	\N	2015-10-07 16:14:09.832	\N	\N	1
516	failed.signed.in	\N	2015-10-07 16:30:23.595	\N	\N	1
517	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 16:30:23.762	\N	\N	1
518	failed.signed.in	\N	2015-10-07 16:31:52.163	\N	\N	1
519	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 16:31:52.234	\N	\N	1
520	failed.signed.in	\N	2015-10-07 16:35:35.511	\N	\N	1
521	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 16:35:35.531	\N	\N	1
522	failed.signed.in	\N	2015-10-07 16:37:14.414	\N	\N	1
523	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 16:37:14.451	\N	\N	1
524	failed.signed.in	\N	2015-10-07 16:37:23.318	\N	\N	1
525	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 16:37:23.362	\N	\N	1
526	failed.signed.in	\N	2015-10-07 17:09:27.459	\N	\N	1
527	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 17:09:27.486	\N	\N	1
528	failed.signed.in	\N	2015-10-07 17:11:24.239	\N	\N	1
529	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 17:11:24.257	\N	\N	1
530	failed.signed.in	\N	2015-10-07 17:15:53.644	\N	\N	1
531	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 17:15:53.66	\N	\N	1
532	failed.signed.in	\N	2015-10-07 17:16:04.822	\N	\N	1
533	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 17:16:04.842	\N	\N	1
534	failed.signed.in	\N	2015-10-07 17:37:49.9	\N	\N	1
535	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 17:37:50.989	\N	\N	1
536	failed.signed.in	\N	2015-10-07 17:38:13.68	\N	\N	1
537	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 17:38:13.699	\N	\N	1
538	failed.signed.in	\N	2015-10-07 17:38:23.914	\N	\N	1
539	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 17:38:23.927	\N	\N	1
540	failed.signed.in	\N	2015-10-07 18:10:45.155	\N	\N	1
541	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 18:10:45.324	\N	\N	1
542	failed.signed.in	\N	2015-10-07 18:11:23.021	\N	\N	1
543	User Account is locked due to six or more successive failed attempt for login	\N	2015-10-07 18:11:23.039	\N	\N	1
602	failed.signed.in	\N	2015-10-08 17:41:50.919	\N	\N	1
608	successfully.signed.in	\N	2015-10-08 18:39:30.503	\N	\N	1
609	successfully.signed.in	\N	2015-10-08 18:44:36.409	\N	\N	1
610	User.logged.out.successfully	\N	2015-10-08 18:44:36.429	\N	\N	1
611	failed.signed.in	\N	2015-10-08 18:45:27.22	\N	\N	1
612	User.logged.out.successfully	\N	2015-10-08 18:45:27.24	\N	\N	1
613	successfully.signed.in	\N	2015-10-08 18:47:54.513	\N	\N	1
732	failed.signed.in	\N	2015-10-27 15:15:31.198	\N	\N	\N
733	User.logged.out.successfully	\N	2015-10-27 15:15:31.432	\N	\N	\N
734	successfully.signed.in	\N	2015-10-27 15:16:15.165	\N	\N	2
735	successfully.signed.in	\N	2015-10-28 10:23:37.167	\N	\N	2
736	User.logged.out.successfully	\N	2015-10-28 10:23:37.316	\N	\N	2
737	successfully.signed.in	\N	2015-10-28 13:46:42.047	\N	\N	2
738	User.logged.out.successfully	\N	2015-10-28 13:46:42.167	\N	\N	2
739	failed.signed.in	\N	2015-10-28 15:08:13.918	\N	\N	\N
740	failed.signed.in	\N	2015-10-28 15:08:21.598	\N	\N	\N
741	successfully.signed.in	\N	2015-10-28 15:08:30.875	\N	\N	2
742	User.logged.out.successfully	\N	2015-10-28 15:08:30.92	\N	\N	2
743	successfully.signed.in	\N	2015-10-28 15:11:01.543	\N	\N	2
744	User.logged.out.successfully	\N	2015-10-28 15:11:01.564	\N	\N	2
745	successfully.signed.in	\N	2015-10-28 15:24:03.849	\N	\N	2
746	User.logged.out.successfully	\N	2015-10-28 15:24:03.864	\N	\N	2
747	successfully.signed.in	\N	2015-10-28 15:47:59.419	\N	\N	3
748	successfully.signed.in	\N	2015-10-28 18:32:52.022	\N	\N	2
749	successfully.signed.in	\N	2015-10-29 15:14:27.189	\N	\N	2
750	User.logged.out.successfully	\N	2015-10-29 15:14:28.638	\N	\N	2
751	successfully.signed.in	\N	2015-11-01 09:30:57.341	\N	\N	2
752	User.logged.out.successfully	\N	2015-11-01 09:30:57.501	\N	\N	2
753	successfully.signed.in	\N	2015-11-01 22:02:25.362	\N	\N	2
754	User.logged.out.successfully	\N	2015-11-01 22:02:25.438	\N	\N	2
755	User.logged.out.successfully	\N	2015-11-01 22:51:21.494	\N	\N	\N
756	successfully.signed.in	\N	2015-11-01 22:51:28.531	\N	\N	1
757	User.logged.out.successfully	\N	2015-11-01 22:51:28.552	\N	\N	1
758	successfully.signed.in	\N	2015-11-02 10:17:53.508	\N	\N	1
759	successfully.signed.in	\N	2015-11-02 10:31:40.252	\N	\N	1
760	User.logged.out.successfully	\N	2015-11-02 10:31:40.396	\N	\N	1
761	successfully.signed.in	\N	2015-11-02 11:07:28.744	\N	\N	1
762	User.logged.out.successfully	\N	2015-11-02 11:07:28.833	\N	\N	1
763	successfully.signed.in	\N	2015-11-02 11:12:26.724	\N	\N	1
764	User.logged.out.successfully	\N	2015-11-02 11:12:26.814	\N	\N	1
765	successfully.signed.in	\N	2015-11-02 11:16:06.909	\N	\N	1
766	User.logged.out.successfully	\N	2015-11-02 11:16:06.983	\N	\N	1
767	successfully.signed.in	\N	2015-11-02 11:17:03.9	\N	\N	1
768	User.logged.out.successfully	\N	2015-11-02 11:17:03.954	\N	\N	1
769	successfully.signed.in	\N	2015-11-02 12:04:56.117	\N	\N	1
770	User.logged.out.successfully	\N	2015-11-02 12:04:56.212	\N	\N	1
771	successfully.signed.in	\N	2015-11-02 14:04:31.399	\N	\N	1
772	User.logged.out.successfully	\N	2015-11-02 14:04:31.511	\N	\N	1
773	successfully.signed.in	\N	2015-11-02 14:18:42.878	\N	\N	1
774	User.logged.out.successfully	\N	2015-11-02 14:18:42.975	\N	\N	1
775	successfully.signed.in	\N	2015-11-02 14:32:32.659	\N	\N	1
776	User.logged.out.successfully	\N	2015-11-02 14:32:32.837	\N	\N	1
777	successfully.signed.in	\N	2015-11-02 14:36:39.159	\N	\N	2
778	successfully.signed.in	\N	2015-11-02 18:32:08.819	\N	\N	1
779	User.logged.out.successfully	\N	2015-11-02 18:32:08.953	\N	\N	1
780	User.logged.out.successfully	\N	2015-11-02 19:24:49.111	\N	\N	1
781	failed.signed.in	\N	2015-11-04 17:41:10.123	\N	\N	\N
782	failed.signed.in	\N	2015-11-04 17:41:17.923	\N	\N	\N
783	failed.signed.in	\N	2015-11-04 17:42:11.107	\N	\N	\N
784	failed.signed.in	\N	2015-11-04 17:47:29.854	\N	\N	\N
785	successfully.signed.in	\N	2015-11-04 17:51:20.308	\N	\N	1
786	successfully.signed.in	\N	2015-11-04 21:44:43.65	\N	\N	1
787	successfully.signed.in	\N	2015-11-04 22:32:42.195	\N	\N	1
788	User.logged.out.successfully	\N	2015-11-04 22:32:42.295	\N	\N	1
789	successfully.signed.in	\N	2015-11-05 16:24:50.562	\N	\N	1
790	User.logged.out.successfully	\N	2015-11-05 16:24:50.702	\N	\N	1
791	successfully.signed.in	\N	2015-11-06 10:08:30.927	\N	\N	1
792	User.logged.out.successfully	\N	2015-11-06 10:08:30.997	\N	\N	1
795	successfully.signed.in	\N	2015-11-06 13:10:01.533	\N	\N	1
796	User.logged.out.successfully	\N	2015-11-06 13:10:01.645	\N	\N	1
797	User.logged.out.successfully	\N	2015-11-06 14:09:11.517	\N	\N	1
798	successfully.signed.in	\N	2015-11-06 14:11:20.493	\N	\N	1
799	successfully.signed.in	\N	2015-11-09 10:32:45.511	\N	\N	1
800	User.logged.out.successfully	\N	2015-11-09 10:32:45.634	\N	\N	1
801	User.logged.out.successfully	\N	2015-11-09 10:32:51.856	\N	\N	1
802	successfully.signed.in	\N	2015-11-09 10:32:58.639	\N	\N	1
803	User.logged.out.successfully	\N	2015-11-09 10:33:09.514	\N	\N	1
804	successfully.signed.in	\N	2015-11-09 10:33:30.926	\N	\N	1
805	User.logged.out.successfully	\N	2015-11-09 10:33:34.469	\N	\N	1
806	successfully.signed.in	\N	2015-11-09 10:33:39.929	\N	\N	1
807	User.logged.out.successfully	\N	2015-11-09 10:34:03.677	\N	\N	1
808	successfully.signed.in	\N	2015-11-09 10:34:13.641	\N	\N	1
809	successfully.signed.in	\N	2015-11-09 11:36:39.486	\N	\N	1
810	successfully.signed.in	\N	2015-11-09 11:37:41.814	\N	\N	2
811	User.logged.out.successfully	\N	2015-11-09 11:37:41.863	\N	\N	2
812	successfully.signed.in	\N	2015-11-09 14:13:50.868	\N	\N	1
813	successfully.signed.in	\N	2015-11-09 16:02:51.49	\N	\N	1
814	successfully.signed.in	\N	2015-11-09 20:01:30.41	\N	\N	1
815	successfully.signed.in	\N	2015-11-11 11:20:50.521	\N	\N	1
816	User.logged.out.successfully	\N	2015-11-11 11:20:50.675	\N	\N	1
817	failed.signed.in	\N	2015-11-11 13:43:59.398	\N	\N	1
818	User.logged.out.successfully	\N	2015-11-11 13:43:59.682	\N	\N	1
819	successfully.signed.in	\N	2015-11-11 13:44:10.664	\N	\N	1
820	failed.signed.in	\N	2015-11-11 15:20:07.537	\N	\N	1
821	failed.signed.in	\N	2015-11-11 15:20:17.27	\N	\N	1
822	successfully.signed.in	\N	2015-11-11 15:59:34.262	\N	\N	1
823	User.logged.out.successfully	\N	2015-11-11 15:59:34.408	\N	\N	1
824	successfully.signed.in	\N	2015-11-11 21:08:55.823	\N	\N	1
825	User.logged.out.successfully	\N	2015-11-11 21:08:55.918	\N	\N	1
826	successfully.signed.in	\N	2015-11-11 22:23:59.221	\N	\N	1
827	User.logged.out.successfully	\N	2015-11-11 22:23:59.603	\N	\N	1
828	successfully.signed.in	\N	2015-11-11 22:26:26.992	\N	\N	2
829	User.logged.out.successfully	\N	2015-11-11 22:26:27.123	\N	\N	2
830	successfully.signed.in	\N	2015-11-12 10:13:49.605	\N	\N	1
831	User.logged.out.successfully	\N	2015-11-12 10:13:49.788	\N	\N	1
832	successfully.signed.in	\N	2015-11-12 12:23:05.73	\N	\N	1
833	failed.signed.in	\N	2015-11-12 18:21:11.356	\N	\N	1
834	successfully.signed.in	\N	2015-11-12 18:30:41.465	\N	\N	1
835	successfully.signed.in	\N	2015-11-12 20:53:06.128	\N	\N	1
836	successfully.signed.in	\N	2015-11-12 21:22:52.795	\N	\N	2
837	User.logged.out.successfully	\N	2015-11-12 21:22:52.81	\N	\N	2
838	successfully.signed.in	\N	2015-11-13 10:03:41.399	\N	\N	1
\.


--
-- TOC entry 2901 (class 0 OID 0)
-- Dependencies: 292
-- Name: useraudit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('useraudit_id_seq', 838, true);


--
-- TOC entry 2902 (class 0 OID 0)
-- Dependencies: 294
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


--
-- TOC entry 2844 (class 0 OID 20214)
-- Dependencies: 295
-- Data for Name: vrfparameter_statistics; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY vrfparameter_statistics (id, compressor1workingtime, compressor2workingtime, compressor3workingtime, createdby, creationdate, current2, current3, demand, fanmode, fanrotation, fixedspeedcomp1, fixedspeedcomp2, gastempofoutdoorcoil1, gastempofoutdoorcoil2, highpresure, inlettempoutdoorunit, invcompactualhz, invcomptargethz, invprimarycurrent, invsecondarycurrent, liquidtempofoutdoorcoil1, liquidtempofoutdoorcoil2, lowpresure, mov1pulse, mov2pulse, mov4pulse, outdoorunitstatus, saturatedtemphighpress, saturatedtemplowpress, scg, tempcompressordischarge1, tempcompressordischarge2, tempcompressordischarge3, tempoil1, tempoil2, temppil3, updatedate, updatedby, outdoorunit_id, logtime) FROM stdin;
1	3535.00	45646.00	36346.00	RSI	2015-08-07 10:13:06	236	235	46	235	25	46	346	21	124	23	312	56	68	57	14	346	87	6	25	25	25	56	25	2	64	235	756	123	3463	235	234	\N	RSI	15	\N
2	47.00	47.00	47.00	RSI	2015-08-07 10:13:50	236	25	236	35	5634	678	67	346	23	236	26	346	457	236	23	36	34	26	46	3	234	457	26	346	46	8678	235	235	23	8	235	\N	RSI	16	\N
3	568.00	8568.00	346.00	RSI	2015-08-07 10:14:12	37	37	37	37	36	37	37	37	37	37	7	347	346	37	37	37	37	37	36	79	12	458	37	37	37	37	37	37	73	37	37	\N	RSI	17	\N
4	346.00	346.00	36.00	RSI	2015-08-07 10:16:02	22	876	87	252	25	36	36	7	8	75	875	36	36	785	8	667	3	27	54	45	765	346	2	721	52	82	757	75	87	41	78	\N	RSI	18	\N
\.


--
-- TOC entry 2903 (class 0 OID 0)
-- Dependencies: 296
-- Name: vrfparameter_statistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('vrfparameter_statistics_id_seq', 1, false);


--
-- TOC entry 2904 (class 0 OID 0)
-- Dependencies: 298
-- Name: winddirection_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('winddirection_master_id_seq', 1, false);


-- Completed on 2015-11-13 10:06:31

--
-- PostgreSQL database dump complete
--
--Ca Installation and Customer Data

INSERT INTO adapters_model VALUES (2, 'Model1');
INSERT INTO adapters_model VALUES (3, 'model2');
INSERT INTO adapters_model VALUES (4, 'model3');
INSERT INTO adapters_model VALUES (5, 'model4');
INSERT INTO adapters_model VALUES (6, 'model5');
INSERT INTO adapters_model VALUES (7, 'model6');
INSERT INTO adapters_model VALUES (8, 'model7');
INSERT INTO adapters_model VALUES (9, 'model8');
