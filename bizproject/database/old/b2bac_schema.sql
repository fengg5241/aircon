--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.4.4
-- Started on 2015-11-13 10:05:30

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 311 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2749 (class 0 OID 0)
-- Dependencies: 311
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 312 (class 3079 OID 19729)
-- Name: tablefunc; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS tablefunc WITH SCHEMA public;


--
-- TOC entry 2750 (class 0 OID 0)
-- Dependencies: 312
-- Name: EXTENSION tablefunc; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION tablefunc IS 'functions that manipulate whole tables, including crosstab';


SET search_path = public, pg_catalog;

--
-- TOC entry 748 (class 1247 OID 19752)
-- Name: dblink_pkey_results; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE dblink_pkey_results AS (
	"position" integer,
	colname text
);


ALTER TYPE dblink_pkey_results OWNER TO postgres;

--
-- TOC entry 336 (class 1255 OID 19753)
-- Name: c_crosstab(character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION c_crosstab(eavsql_inarg character varying, resview character varying, rowid character varying, colid character varying, val character varying, agr character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
    casesql varchar;
    dynsql varchar;    
    r record;
BEGIN   
 dynsql='';

 for r in 
      select * from pg_views where lower(viewname) = lower(resview)
  loop
      execute 'DROP VIEW ' || resview;
  end loop;   

 casesql='SELECT DISTINCT ' || colid || ' AS v from (' || eavsql_inarg || ') eav ORDER BY ' || colid;
 FOR r IN EXECUTE casesql Loop
    dynsql = dynsql || ', ' || agr || '(CASE WHEN ' || colid || '=''' || r.v || ''' THEN ' || val || ' ELSE NULL END) AS ' || replace(r.v , '-', '_');
 END LOOP;
 dynsql = 'CREATE VIEW ' || resview || ' AS SELECT ' || rowid || dynsql || ' from (' || eavsql_inarg || ') eav GROUP BY ' || rowid ;
 RAISE NOTICE 'dynsql %', dynsql; 
 EXECUTE dynsql;
END

$$;


ALTER FUNCTION public.c_crosstab(eavsql_inarg character varying, resview character varying, rowid character varying, colid character varying, val character varying, agr character varying) OWNER TO postgres;

--
-- TOC entry 337 (class 1255 OID 19754)
-- Name: concat(anyarray); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION concat(VARIADIC anyarray) RETURNS text
    LANGUAGE sql IMMUTABLE
    AS $_$
SELECT array_to_string($1,'');
$_$;


ALTER FUNCTION public.concat(VARIADIC anyarray) OWNER TO postgres;

--
-- TOC entry 338 (class 1255 OID 19755)
-- Name: dblink(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink(text) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_record';


ALTER FUNCTION public.dblink(text) OWNER TO postgres;

--
-- TOC entry 339 (class 1255 OID 19756)
-- Name: dblink(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink(text, boolean) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_record';


ALTER FUNCTION public.dblink(text, boolean) OWNER TO postgres;

--
-- TOC entry 340 (class 1255 OID 19757)
-- Name: dblink(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink(text, text) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_record';


ALTER FUNCTION public.dblink(text, text) OWNER TO postgres;

--
-- TOC entry 341 (class 1255 OID 19758)
-- Name: dblink(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink(text, text, boolean) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_record';


ALTER FUNCTION public.dblink(text, text, boolean) OWNER TO postgres;

--
-- TOC entry 342 (class 1255 OID 19759)
-- Name: dblink_build_sql_delete(text, int2vector, integer, text[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_build_sql_delete(text, int2vector, integer, text[]) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_build_sql_delete';


ALTER FUNCTION public.dblink_build_sql_delete(text, int2vector, integer, text[]) OWNER TO postgres;

--
-- TOC entry 343 (class 1255 OID 19760)
-- Name: dblink_build_sql_insert(text, int2vector, integer, text[], text[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_build_sql_insert(text, int2vector, integer, text[], text[]) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_build_sql_insert';


ALTER FUNCTION public.dblink_build_sql_insert(text, int2vector, integer, text[], text[]) OWNER TO postgres;

--
-- TOC entry 344 (class 1255 OID 19761)
-- Name: dblink_build_sql_update(text, int2vector, integer, text[], text[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_build_sql_update(text, int2vector, integer, text[], text[]) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_build_sql_update';


ALTER FUNCTION public.dblink_build_sql_update(text, int2vector, integer, text[], text[]) OWNER TO postgres;

--
-- TOC entry 345 (class 1255 OID 19762)
-- Name: dblink_cancel_query(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_cancel_query(text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_cancel_query';


ALTER FUNCTION public.dblink_cancel_query(text) OWNER TO postgres;

--
-- TOC entry 346 (class 1255 OID 19763)
-- Name: dblink_close(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_close(text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_close';


ALTER FUNCTION public.dblink_close(text) OWNER TO postgres;

--
-- TOC entry 347 (class 1255 OID 19764)
-- Name: dblink_close(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_close(text, boolean) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_close';


ALTER FUNCTION public.dblink_close(text, boolean) OWNER TO postgres;

--
-- TOC entry 348 (class 1255 OID 19765)
-- Name: dblink_close(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_close(text, text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_close';


ALTER FUNCTION public.dblink_close(text, text) OWNER TO postgres;

--
-- TOC entry 349 (class 1255 OID 19766)
-- Name: dblink_close(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_close(text, text, boolean) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_close';


ALTER FUNCTION public.dblink_close(text, text, boolean) OWNER TO postgres;

--
-- TOC entry 350 (class 1255 OID 19767)
-- Name: dblink_connect(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_connect(text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_connect';


ALTER FUNCTION public.dblink_connect(text) OWNER TO postgres;

--
-- TOC entry 351 (class 1255 OID 19768)
-- Name: dblink_connect(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_connect(text, text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_connect';


ALTER FUNCTION public.dblink_connect(text, text) OWNER TO postgres;

--
-- TOC entry 394 (class 1255 OID 19769)
-- Name: dblink_connect_u(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_connect_u(text) RETURNS text
    LANGUAGE c STRICT SECURITY DEFINER
    AS '$libdir/dblink', 'dblink_connect';


ALTER FUNCTION public.dblink_connect_u(text) OWNER TO postgres;

--
-- TOC entry 395 (class 1255 OID 19770)
-- Name: dblink_connect_u(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_connect_u(text, text) RETURNS text
    LANGUAGE c STRICT SECURITY DEFINER
    AS '$libdir/dblink', 'dblink_connect';


ALTER FUNCTION public.dblink_connect_u(text, text) OWNER TO postgres;

--
-- TOC entry 352 (class 1255 OID 19771)
-- Name: dblink_current_query(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_current_query() RETURNS text
    LANGUAGE c
    AS '$libdir/dblink', 'dblink_current_query';


ALTER FUNCTION public.dblink_current_query() OWNER TO postgres;

--
-- TOC entry 353 (class 1255 OID 19772)
-- Name: dblink_disconnect(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_disconnect() RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_disconnect';


ALTER FUNCTION public.dblink_disconnect() OWNER TO postgres;

--
-- TOC entry 354 (class 1255 OID 19773)
-- Name: dblink_disconnect(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_disconnect(text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_disconnect';


ALTER FUNCTION public.dblink_disconnect(text) OWNER TO postgres;

--
-- TOC entry 355 (class 1255 OID 19774)
-- Name: dblink_error_message(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_error_message(text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_error_message';


ALTER FUNCTION public.dblink_error_message(text) OWNER TO postgres;

--
-- TOC entry 356 (class 1255 OID 19775)
-- Name: dblink_exec(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_exec(text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_exec';


ALTER FUNCTION public.dblink_exec(text) OWNER TO postgres;

--
-- TOC entry 357 (class 1255 OID 19776)
-- Name: dblink_exec(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_exec(text, boolean) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_exec';


ALTER FUNCTION public.dblink_exec(text, boolean) OWNER TO postgres;

--
-- TOC entry 358 (class 1255 OID 19777)
-- Name: dblink_exec(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_exec(text, text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_exec';


ALTER FUNCTION public.dblink_exec(text, text) OWNER TO postgres;

--
-- TOC entry 359 (class 1255 OID 19778)
-- Name: dblink_exec(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_exec(text, text, boolean) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_exec';


ALTER FUNCTION public.dblink_exec(text, text, boolean) OWNER TO postgres;

--
-- TOC entry 360 (class 1255 OID 19779)
-- Name: dblink_fetch(text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_fetch(text, integer) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_fetch';


ALTER FUNCTION public.dblink_fetch(text, integer) OWNER TO postgres;

--
-- TOC entry 361 (class 1255 OID 19780)
-- Name: dblink_fetch(text, integer, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_fetch(text, integer, boolean) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_fetch';


ALTER FUNCTION public.dblink_fetch(text, integer, boolean) OWNER TO postgres;

--
-- TOC entry 362 (class 1255 OID 19781)
-- Name: dblink_fetch(text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_fetch(text, text, integer) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_fetch';


ALTER FUNCTION public.dblink_fetch(text, text, integer) OWNER TO postgres;

--
-- TOC entry 363 (class 1255 OID 19782)
-- Name: dblink_fetch(text, text, integer, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_fetch(text, text, integer, boolean) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_fetch';


ALTER FUNCTION public.dblink_fetch(text, text, integer, boolean) OWNER TO postgres;

--
-- TOC entry 364 (class 1255 OID 19783)
-- Name: dblink_get_connections(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_get_connections() RETURNS text[]
    LANGUAGE c
    AS '$libdir/dblink', 'dblink_get_connections';


ALTER FUNCTION public.dblink_get_connections() OWNER TO postgres;

--
-- TOC entry 365 (class 1255 OID 19784)
-- Name: dblink_get_notify(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_get_notify(OUT notify_name text, OUT be_pid integer, OUT extra text) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_get_notify';


ALTER FUNCTION public.dblink_get_notify(OUT notify_name text, OUT be_pid integer, OUT extra text) OWNER TO postgres;

--
-- TOC entry 366 (class 1255 OID 19785)
-- Name: dblink_get_notify(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_get_notify(conname text, OUT notify_name text, OUT be_pid integer, OUT extra text) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_get_notify';


ALTER FUNCTION public.dblink_get_notify(conname text, OUT notify_name text, OUT be_pid integer, OUT extra text) OWNER TO postgres;

--
-- TOC entry 367 (class 1255 OID 19786)
-- Name: dblink_get_pkey(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_get_pkey(text) RETURNS SETOF dblink_pkey_results
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_get_pkey';


ALTER FUNCTION public.dblink_get_pkey(text) OWNER TO postgres;

--
-- TOC entry 368 (class 1255 OID 19787)
-- Name: dblink_get_result(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_get_result(text) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_get_result';


ALTER FUNCTION public.dblink_get_result(text) OWNER TO postgres;

--
-- TOC entry 369 (class 1255 OID 19788)
-- Name: dblink_get_result(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_get_result(text, boolean) RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_get_result';


ALTER FUNCTION public.dblink_get_result(text, boolean) OWNER TO postgres;

--
-- TOC entry 370 (class 1255 OID 19789)
-- Name: dblink_is_busy(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_is_busy(text) RETURNS integer
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_is_busy';


ALTER FUNCTION public.dblink_is_busy(text) OWNER TO postgres;

--
-- TOC entry 371 (class 1255 OID 19790)
-- Name: dblink_open(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_open(text, text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_open';


ALTER FUNCTION public.dblink_open(text, text) OWNER TO postgres;

--
-- TOC entry 372 (class 1255 OID 19791)
-- Name: dblink_open(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_open(text, text, boolean) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_open';


ALTER FUNCTION public.dblink_open(text, text, boolean) OWNER TO postgres;

--
-- TOC entry 373 (class 1255 OID 19792)
-- Name: dblink_open(text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_open(text, text, text) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_open';


ALTER FUNCTION public.dblink_open(text, text, text) OWNER TO postgres;

--
-- TOC entry 374 (class 1255 OID 19793)
-- Name: dblink_open(text, text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_open(text, text, text, boolean) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_open';


ALTER FUNCTION public.dblink_open(text, text, text, boolean) OWNER TO postgres;

--
-- TOC entry 375 (class 1255 OID 19794)
-- Name: dblink_send_query(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_send_query(text, text) RETURNS integer
    LANGUAGE c STRICT
    AS '$libdir/dblink', 'dblink_send_query';


ALTER FUNCTION public.dblink_send_query(text, text) OWNER TO postgres;

--
-- TOC entry 376 (class 1255 OID 19795)
-- Name: ff(integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION ff(integer, text) RETURNS integer
    LANGUAGE plpgsql
    AS $_$
declare
    a text[]; 
    i int;
begin
    select string_to_array($2, ',') into a; 
    i := 1;
    loop  
        if i > array_upper(a, 1) then
            exit;
        else
            insert into test(i, t) values($1, a[i]);
            i := i + 1;
        end if;
    end loop;                                 
    return 0;       
end
$_$;


ALTER FUNCTION public.ff(integer, text) OWNER TO postgres;

--
-- TOC entry 396 (class 1255 OID 19796)
-- Name: fn_getpropertvalue(character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION fn_getpropertvalue(property character varying, mode character varying, faclid character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$declare
  --  a text[]; 
    --i int;
    prop_val varchar(128);
begin
if mode = 'settemp' then
	BEGIN
--	RETURN QUERY
	Select  Case 	when	property = '1' then a3a 
			when	property = '2' then a3b
			--when	property = '3' then a3
			when	property = '4' then a3c
			when	property = '5' then a3d
			when	property in ('0','7')then a3
			
		END 
	from ct_statusinfo where a2 = property and facl_id = faclid into prop_val;
	--RAISE NOTICE 'Calling csww_create_job(%)', prop_val;
	END;
END IF;
if mode = 'fanspeed' then
	BEGIN
	Select  Case 	when	property = '1' then a6a 
			when	property = '2' then a6b
			when	property = '3' then a6e
			when	property = '4' then a6c
			when	property = '5' then a6d
			when	property in ('0','7')then a6
					
		END 
	from ct_statusinfo where a2 = property and facl_id = faclid into prop_val;
	--RAISE NOTICE 'Calling csww_create_job(%)', prop_val;
	END;
END if;

if mode = 'winddirection' then
	BEGIN
	Select  Case 	when	property = '1' then a7a 
			when	property = '2' then a7b
			when	property = '3' then a7e
			when	property = '4' then a7c
			when	property = '5' then a7d
			when	property in ('0','7')then a7
					
		END 
	from ct_statusinfo where a2 = property and facl_id = faclid into prop_val;
	--RAISE NOTICE 'Calling csww_create_job(%)', prop_val;
	END;
END if;

      return prop_val  as propval;       
end$$;


ALTER FUNCTION public.fn_getpropertvalue(property character varying, mode character varying, faclid character varying) OWNER TO postgres;

--
-- TOC entry 377 (class 1255 OID 19797)
-- Name: fun_array(integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION fun_array(integer, text) RETURNS integer
    LANGUAGE plpgsql
    AS $_$
begin
    insert into test(i, t)
    select $1, s 
    from unnest(string_to_array($2, ',')) as dt(s);
    return 0;
end
$_$;


ALTER FUNCTION public.fun_array(integer, text) OWNER TO postgres;

--
-- TOC entry 378 (class 1255 OID 19798)
-- Name: string_to_rows(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION string_to_rows(text) RETURNS SETOF integer
    LANGUAGE plpgsql
    AS $_$ 
  DECLARE
    elems integer[];      
  BEGIN
    elems := string_to_array($1, '|');
    FOR i IN array_lower(elems, 1) .. array_upper(elems, 1) LOOP
      RETURN NEXT elems[i];
    END LOOP;
    RETURN;
  END
$_$;


ALTER FUNCTION public.string_to_rows(text) OWNER TO postgres;

--
-- TOC entry 379 (class 1255 OID 19799)
-- Name: usp_getco2factor(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getco2factor(groupids character varying) RETURNS TABLE(co2factor double precision)
    LANGUAGE plpgsql
    AS $$

	
BEGIN


RETURN QUERY
  WITH RECURSIVE ctegroupsot AS
(SELECT g.id, g.parentid, g.groupcategoryid,g.uniqueid,cast(g.name as varchar(45)) as groupname,g.path,g.co2factor
FROM groups g
WHERE g.id = ANY(string_to_array('8', ',')::int[])
UNION ALL
SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,cast(si.name as varchar(45))as groupname,sp.path,si.co2factor
	FROM groups As si
	INNER JOIN ctegroupsot AS sp
	ON (si.id = sp.parentid)
)
select distinct g.co2factor from ctegroupsot g where groupcategoryid = 2;

END;
$$;


ALTER FUNCTION public.usp_getco2factor(groupids character varying) OWNER TO postgres;

--
-- TOC entry 381 (class 1255 OID 19800)
-- Name: usp_getgroupbyuserid(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getgroupbyuserid(userid bigint) RETURNS TABLE(id integer, path character varying, group_id bigint, alaramcount bigint, indoorunit bigint, indoorunitcount bigint, outdoorunit bigint, outdoorunitcount bigint)
    LANGUAGE plpgsql
    AS $$


DECLARE use_ VARCHAR(100);

 v_fin_ INTEGER DEFAULT 0;

        v_gro_ VARCHAR(100) DEFAULT '';

 

 DECLARE grp_ CURSOR FOR

 SELECT group_id FROM companiesusers WHERE user_id =  userid;
BEGIN

 



SELECT  r.name INTO use_ FROM roles r INNER JOIN users u ON r.id = u.roles_id WHERE u.id  = userid;



IF  use_ = 'Customer' THEN

BEGIN

TRUNCATE TABLE temp_groupunitdata;

 OPEN grp_;

 LOOP

 FETCH grp_ INTO v_gro_;



INSERT INTO temp_groupunitdata (path,group_id,alaramcount,indoorunitcount,outdoorunitcount,indoorunit,outdoorunit)

SELECT t.path, t.group_id,COUNT(ast.indoorunit_id)  + COUNT(ast1.outdoorunit_id) AS alaramcount, COUNT(ast.indoorunit_id) , COUNT(ast1.outdoorunit_id) ,ast.indoorunit_id ,

 ast1.outdoorunit_id FROM (

SELECT gu.group_id,gu.indoorunit_id ,gu.outdoorunit_id,gmain.path  FROM groupsunits gu

INNER JOIN (SELECT id,path FROM groups g WHERE g.path LIKE CONCAT('%|',v_gro_,'|%')) gmain

ON gu.group_id = gmain.id

) t

LEFT OUTER JOIN alarmstatistics ast ON t.indoorunit_id =  ast.indoorunit_id

AND (ast.isdel = 0)

LEFT OUTER JOIN alarmstatistics ast1 ON t.outdoorunit_id =  ast1.outdoorunit_id

AND (ast1.isdel = 0)

WHERE (ast.indoorunit_id IS NOT NULL AND ast1.outdoorunit_id IS NULL) OR (ast.indoorunit_id IS NULL AND ast1.outdoorunit_id IS NOT NULL)

GROUP BY t.group_id,ast.indoorunit_id, ast1.outdoorunit_id

ORDER BY t.group_id;

END LOOP ;


SELECT DISTINCT path,group_id,alaramcount,indoorunitcount,outdoorunitcount,indoorunit,outdoorunit FROM temp_groupunitdata;

 CLOSE grp_;

 END;

 END IF;

  IF  use_ = 'Super Admin' THEN

BEGIN
	RETURN QUERY
	SELECT DISTINCT 1,t.path, t.group_id,COUNT(ast.indoorunit_id)  + COUNT(ast1.outdoorunit_id) AS alaramcount, COUNT(ast.indoorunit_id)  as indoorunitcount, COUNT(ast1.outdoorunit_id) as outdoorunitcount ,ast.indoorunit_id as indoorunit,

	 ast1.outdoorunit_id as outdoorunit FROM (

	SELECT gu.group_id,gu.indoorunit_id ,gu.outdoorunit_id,g.path  FROM groupsunits gu

	LEFT OUTER JOIN groups g ON gu.group_id =  g.id

	) t

	LEFT OUTER JOIN alarmstatistics ast ON t.indoorunit_id =  ast.indoorunit_id

	AND (ast.isdel = 0 )

	LEFT OUTER JOIN alarmstatistics ast1 ON t.outdoorunit_id =  ast1.outdoorunit_id

	AND (ast1.isdel = 0)

	WHERE (ast.indoorunit_id IS NOT NULL AND ast1.outdoorunit_id IS NULL) OR (ast.indoorunit_id IS NULL AND ast1.outdoorunit_id IS NOT NULL)

	GROUP BY t.group_id,ast.indoorunit_id, ast1.outdoorunit_id,t.path

	ORDER BY t.group_id;

END;

END IF;

END;
$$;


ALTER FUNCTION public.usp_getgroupbyuserid(userid bigint) OWNER TO postgres;

--
-- TOC entry 382 (class 1255 OID 19801)
-- Name: usp_getgroupdetailbygroupid(bigint, smallint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getgroupdetailbygroupid(groupid bigint, isefficiency smallint) RETURNS TABLE(path character varying, group_id bigint, alarmindoorunitcount integer, alarmoutdoorunitcount integer, indoorunitcount integer, outdoorunitcount integer, totalalarmcount integer, severity character varying, groupname character varying, co2emission double precision, efficiency_seer double precision)
    LANGUAGE plpgsql
    AS $$

 DECLARE usertype VARCHAR(100);
 DECLARE v_groupid BIGINT DEFAULT 0;
 DECLARE v_groupname VARCHAR(200);
 DECLARE v_level INTEGER DEFAULT 0;

--Declare isefficiency smallint default 0;


DECLARE grp_cursor 
CURSOR FOR select g.id,g.name from groups g where g.parentid = groupid  and level =  (select gi.level from groups gi where gi.id = groupid)+1;


BEGIN
truncate table tempgroupdata;
if isefficiency = 0 then
	BEGIN
		
		INSERT INTO tempgroupdata
		SELECT SUBSTRING(t.path,position(t.path in('|'||groupid||'|')),length(t.path)), t.group_id,COALESCE(ast.indoorunit_id ,0)as alarmindoorunitcount,COALESCE(ast1.outdoorunit_id ,0)as alarmoutdoorunitcount,COALESCE(t.indoorunit_id,0) as indoorunitcount,
		COALESCE(t.outdoorunit_id,0) as outdoorunitcount,0 as todalcount,
		case when ast.severity is null then ast1.severity else ast.severity end severity,''  as groupname,0,0
		  FROM (
			SELECT  gu.group_id,gu.indoorunit_id ,gu.outdoorunit_id,gmain.path  FROM groupsunits gu
			  INNER JOIN (SELECT g.id,g.path FROM groups g WHERE g.path LIKE ('%|'||groupid||'|%')) gmain
			  ON gu.group_id = gmain.id
		  ) t
			LEFT OUTER JOIN
			(Select (a.indoorunit_id) as indoorunit_id,a.isdel,a.severity from alarmstatistics a where isdel = 0 )  ast on   ast.indoorunit_id  = t.indoorunit_id
			LEFT OUTER JOIN
			(Select (a.outdoorunit_id) as outdoorunit_id,a.isdel,a.severity from alarmstatistics  a where isdel = 0 )  ast1 on   ast1.outdoorunit_id  = t.outdoorunit_id
			WHERE t.path is not null;



		OPEN grp_cursor;
 
		FETCH grp_cursor INTO v_groupid,v_groupname;
	

		Update tempgroupdata tg set groupname = v_groupname where tg.path LIKE ('%|'||v_groupid||'|%');

		RETURN QUERY
		SELECT * FROM tempgroupdata;

		CLOSE grp_cursor;
	END;
 END IF;
 

if isefficiency = 1 then




INSERT INTO tempgroupdata

      SELECT t.path, t.group_id,COALESCE(ast.indoorunit_id,0) as alarmindoorunitcount,COALESCE(ast1.outdoorunit_id , 0)as alarmoutdoorunitcount,COALESCE(t.indoorunit_id,0) as indoorunitcount,

COALESCE(t.outdoorunit_id,0) as outdoorunitcount,0 as todalcount,

case when ast.severity is null then ast1.severity else ast.severity end severity,''  as groupname,pcd.co2emission,pcd.efficiency_seer

  FROM (

    SELECT  gu.group_id,gu.indoorunit_id ,gu.outdoorunit_id,gmain.path  FROM groupsunits gu

      INNER JOIN (SELECT g.id,g.path FROM groups g WHERE g.path LIKE ('%|'||groupid||'|%')) gmain

      ON gu.group_id = gmain.id

  ) t



LEFT OUTER JOIN

(Select (a.indoorunit_id) as indoorunit_id,a.isdel,a.severity from alarmstatistics a where a.isdel = 0  and a.status= 'new')  ast on   ast.indoorunit_id  = t.indoorunit_id

LEFT OUTER JOIN

(Select (a.outdoorunit_id) as outdoorunit_id,a.isdel,a.severity from alarmstatistics a where a.isdel = 0 and a.status = 'new')  ast1 on   ast1.outdoorunit_id  = t.outdoorunit_id

LEFT OUTER JOIN

(Select p.indoorunit_id,SUM(p.co2emission) as co2emission,avg(p.efficiency_seer) efficiency_seer from power_consumption_capacity_daily p

group by p.indoorunit_id)  pcd on   pcd.indoorunit_id  = t.indoorunit_id

WHERE t.path is not null;



OPEN grp_cursor;


 FETCH grp_cursor INTO v_groupid,v_groupname;

Update tempgroupdata set groupname = v_groupname from tempgroupdata t where t.path LIKE ('%|'||v_groupid||'|%');

RETURN QUERY SELECT * FROM tempgroupdata;

 CLOSE grp_cursor;
 END IF;
END;

$$;


ALTER FUNCTION public.usp_getgroupdetailbygroupid(groupid bigint, isefficiency smallint) OWNER TO postgres;

--
-- TOC entry 393 (class 1255 OID 20820)
-- Name: usp_getgroupschildtoparent(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getgroupschildtoparent(groupids bigint) RETURNS TABLE(groupid bigint, parentgroupid bigint, groupname character varying, path character varying, supplygroupid bigint)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
WITH RECURSIVE ctegroupchildtoparent AS
(SELECT id, parentid, groupcategoryid,uniqueid,name as groupname,groups.path
FROM groups
WHERE id = groupids
--WHERE id = ANY(string_to_array(groupids, ',')::int[])
UNION ALL
SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,name as groupname,sp.path
	FROM groups As si
	INNER JOIN ctegroupchildtoparent AS sp
	ON (si.id = sp.parentid)
)
--Select distinct case when idu.id is not null then idu.id else idusite.id END indootunitid, case when groupcategoryid =  2 then null else main.groupname  end groupname
Select distinct id, parentid,g.groupname,g.path,groupids from ctegroupchildtoparent g order by id;
--left outer join indoorunits idu on Main.id = idu.group_id and groupcategoryid  = 4
--Left outer join indoorunits idusite on (Main.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
--where Main.groupcategoryid in( 4,2)
--order by case when idu.id is not null then idu.id else idusite.id END;

END;
$$;


ALTER FUNCTION public.usp_getgroupschildtoparent(groupids bigint) OWNER TO postgres;

--
-- TOC entry 398 (class 1255 OID 20835)
-- Name: usp_getindooroutdoorunits_supplylevelgroupname(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindooroutdoorunits_supplylevelgroupname(groupids character varying, level integer) RETURNS TABLE(indoorunitid bigint, groupname character varying, supplygroupname character varying, idname character varying, outdoorunitid bigint)
    LANGUAGE plpgsql
    AS $$

DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
  declare
    a text[]; 
    i int;

	
BEGIN

DROP TABLE IF EXISTS groupslevel_temp1;
--DROP TABLE temp_supplygroup;

--
CREATE GLOBAL TEMPORARY  TABLE groupslevel_temp1
(
  id bigint,
  parentid bigint,
  groupcategoryid integer,
  uniqueid character varying(16),
  "name" character varying(45),
  path character varying(45),
  supplygroupname character varying(45),
	outdoorunitid bigint
);
--ON COMMIT DROP;
Select ((array(select distinct id from usp_groupparentchilddata_level(groupids,level))) ) into a;

i := 1;
    loop  
        if i > coalesce (array_upper(a, 1),1) then
            exit;
        else
          -- RETURN;
           --RAISE NOTICE 'Calling cs_create_job(%)', array_upper(a, 1);
	    insert into groupslevel_temp1          Select *,a[i] from usp_groupparentchilddata(a[i]) ;
          --  RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
          -- commit;
           -- RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
            i := i + 1;
          end if;
    end loop;      
Update  groupslevel_temp1 t set supplygroupname =  (select name from groups where cast(id as varchar(40))  = t.supplygroupname);
RETURN QUERY
--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
select distinct  case when idu.id is not null then idu.id else idusite.id END indootunitid, t.name as groupname,
t.supplygroupname,idu.name as idname,idu.outdoorunit_id
from  groupslevel_temp1 t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4
Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
where t.groupcategoryid in( 4,2)
order by case when idu.id is not null then idu.id else idusite.id END;

--Select * from usp_getindoorunits_supplylevelgroupname('5,20',6)

END;
$$;


ALTER FUNCTION public.usp_getindooroutdoorunits_supplylevelgroupname(groupids character varying, level integer) OWNER TO postgres;

--
-- TOC entry 383 (class 1255 OID 19802)
-- Name: usp_getindoorunits(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunits(indoorunits character varying) RETURNS TABLE(indoorunitid bigint, groupname character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
WITH RECURSIVE ctegroupsot AS
(SELECT id, parentid, groupcategoryid,uniqueid,name as groupname,groups.path
FROM groups
WHERE id = ANY(string_to_array(indoorunits, ',')::int[])
UNION ALL
SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,name as groupname,sp.path
	FROM groups As si
	INNER JOIN ctegroupsot AS sp
	ON (si.id = sp.parentid)
)
,
ctegroups AS
(SELECT id, parentid, groupcategoryid,uniqueid,name as groupname,groups.path
FROM groups
WHERE --parentid in (9,20) OR (ID in  (9,20))
(parentid = ANY(string_to_array(indoorunits, ',')::int[]) OR (id = ANY(string_to_array(indoorunits, ',')::int[])))
UNION ALL
SELECT si.id,	si.parentid,si.groupcategoryid,si.uniqueid,name as groupname,sp.path
	FROM groups As si
	INNER JOIN ctegroups AS sp
	ON (si.parentid = sp.id)
)
Select distinct case when idu.id is not null then idu.id else idusite.id END indootunitid, case when groupcategoryid =  2 then null else main.groupname  end groupname--,main.path--,(select distinct name from groups where (string_to_array(indoorunits, ',')::varchar[]) = ANY(string_to_array(main.path, '|')::varchar[])) 
from(
Select * from ctegroups Union ALL
Select * from ctegroupsot where groupcategoryid =  2) main
left outer join indoorunits idu on Main.id = idu.group_id and groupcategoryid  = 4
Left outer join indoorunits idusite on (Main.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
where Main.groupcategoryid in( 4,2)
order by case when idu.id is not null then idu.id else idusite.id END;

END;
$$;


ALTER FUNCTION public.usp_getindoorunits(indoorunits character varying) OWNER TO postgres;

--
-- TOC entry 384 (class 1255 OID 19803)
-- Name: usp_getindoorunits_supplygroupname(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunits_supplygroupname(groupids character varying) RETURNS TABLE(indoorunitid bigint, groupname character varying, supplygroupname character varying, idname character varying)
    LANGUAGE plpgsql
    AS $_$

DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
  declare
    a text[]; 
    i int;

	
BEGIN


DROP TABLE IF EXISTS tempgroupsdata;
--DROP TABLE temp_supplygroup;

--
CREATE GLOBAL TEMPORARY  TABLE tempgroupsdata
(
 id bigint,
  parentid bigint,
  groupcategoryid integer,
  uniqueid character varying(16),
  "name" character varying(45),
  path character varying(45),
  supplygroupname character varying(45)

);
  
  
Insert into tempgroupsdata select * from usp_groupparentchilddata(groupids);

select string_to_array(groupids, ',') into a; 

i := 1;
    loop  
        if i > array_upper(a, 1) then
            exit;
        else
            --insert into test(i, t) values($1, a[i]);
           Update tempgroupsdata set supplygroupname = (SELECT g.name FROM groups g WHERE cast(g.id as varchar) = a[i]) where tempgroupsdata.path LIKE ('%|'||a[i]||'|%') ;
            i := i + 1;
        end if;
    end loop;      

RETURN QUERY
select distinct  case when idu.id is not null then idu.id else idusite.id END indootunitid, t.name as groupname,
t.supplygroupname,idu.name as idname
from tempgroupsdata t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4
Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
where t.groupcategoryid in( 4,2)
order by case when idu.id is not null then idu.id else idusite.id END;
  
END;
$_$;


ALTER FUNCTION public.usp_getindoorunits_supplygroupname(groupids character varying) OWNER TO postgres;

--
-- TOC entry 385 (class 1255 OID 19804)
-- Name: usp_getindoorunits_supplylevelgroupname(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunits_supplylevelgroupname(groupids character varying) RETURNS TABLE(indoorunitid bigint, groupname character varying, supplygroupname character varying, idname character varying)
    LANGUAGE plpgsql
    AS $_$

DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
  declare
    a text[]; 
    i int;

	
BEGIN

--


DROP TABLE IF EXISTS groupslevel_temp;
--DROP TABLE temp_supplygroup;

--
CREATE GLOBAL TEMPORARY  TABLE groupsleveltempdata
(
  id bigint,
  parentid bigint,
  groupcategoryid integer,
  uniqueid character varying(16),
  "name" character varying(45),
  path character varying(45),
  supplygroupname character varying(45)
  );
Select (array_to_string(array(select distinct id from usp_groupparentchilddata_level(groupids)),',') ) into a;
--Select array_to_string(array(* from usp_groupparentchilddata_level('5,21',6)
--Select distinct id,groupname,path,groupcategoryid from usp_groupparentchilddata(array_to_string(array(select distinct id from ctegroups where group_level_id  = 6),',') ) 
 
Insert into groupsleveltempdata select * from usp_groupparentchilddata(groupids);

select string_to_array(groupids, ',') into a; 

i := 1;
    loop  
        if i > array_upper(a, 1) then
            exit;
        else
            --insert into test(i, t) values($1, a[i]);
           Update groupsleveltempdata set supplygroupname = (SELECT g.name FROM groups g WHERE cast(g.id as varchar) = a[i]) where groups_tempdata.path LIKE ('%|'||a[i]||'|%') ;
            i := i + 1;
        end if;
    end loop;      

RETURN QUERY
select distinct  case when idu.id is not null then idu.id else idusite.id END indootunitid, t.name as groupname,
t.supplygroupname,idu.name as idname
from groupsleveltempdata t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4
Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
where t.groupcategoryid in( 4,2)
order by case when idu.id is not null then idu.id else idusite.id END;
  
END;
$_$;


ALTER FUNCTION public.usp_getindoorunits_supplylevelgroupname(groupids character varying) OWNER TO postgres;

--
-- TOC entry 386 (class 1255 OID 19805)
-- Name: usp_getindoorunits_supplylevelgroupname(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunits_supplylevelgroupname(groupids character varying, level integer) RETURNS TABLE(indoorunitid bigint, groupname character varying, supplygroupname character varying, idname character varying)
    LANGUAGE plpgsql
    AS $$

DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
  declare
    a text[]; 
    i int;

	
BEGIN

DROP TABLE IF EXISTS groupslevel_temp1;
--DROP TABLE temp_supplygroup;

--
CREATE GLOBAL TEMPORARY  TABLE groupslevel_temp1
(
  id bigint,
  parentid bigint,
  groupcategoryid integer,
  uniqueid character varying(16),
  "name" character varying(45),
  path character varying(45),
  supplygroupname character varying(45)

);
--ON COMMIT DROP;
Select ((array(select distinct id from usp_groupparentchilddata_level(groupids,level))) ) into a;

i := 1;
    loop  
        if i > coalesce (array_upper(a, 1),1) then
            exit;
        else
          -- RETURN;
           --RAISE NOTICE 'Calling cs_create_job(%)', array_upper(a, 1);
	    insert into groupslevel_temp1          Select *,a[i] from usp_groupparentchilddata(a[i]) ;
          --  RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
          -- commit;
           -- RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
            i := i + 1;
          end if;
    end loop;      
Update  groupslevel_temp1 t set supplygroupname =  (select name from groups where cast(id as varchar(40))  = t.supplygroupname);
RETURN QUERY
--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
select distinct  case when idu.id is not null then idu.id else idusite.id END indootunitid, t.name as groupname,
t.supplygroupname,idu.name as idname
from  groupslevel_temp1 t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4
Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
where t.groupcategoryid in( 4,2)
order by case when idu.id is not null then idu.id else idusite.id END;

--Select * from usp_getindoorunits_supplylevelgroupname('5,20',6)

END;
$$;


ALTER FUNCTION public.usp_getindoorunits_supplylevelgroupname(groupids character varying, level integer) OWNER TO postgres;

--
-- TOC entry 387 (class 1255 OID 19806)
-- Name: usp_getindoorunits_supplylevelgroupname22(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunits_supplylevelgroupname22(groupids character varying, level integer) RETURNS TABLE(id bigint, parentid bigint, uniqueid character varying, name character varying, supplygroupname character varying)
    LANGUAGE plpgsql
    AS $$
--a.id,a.parentid,a.uniqueid,a.name,a.supplygroupname 
DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
  declare
    a text[]; 
    i int;

	
BEGIN

--
Select ((array(select distinct fn.id from usp_groupparentchilddata_level('5,21',6) fn)) ) into a;

--Select (array(select distinct id from groups)) 
--Select (array(Select id from (select distinct id::int from usp_groupparentchilddata('5,20')) t)) 
--select array('5,20',',')
--Select array_to_string(array(select distinct id  from usp_groupparentchilddata_level('5,21',6))
--Select * from usp_groupparentchilddata('21') 
--Select distinct id,groupname,path,groupcategoryid from usp_groupparentchilddata(array_to_string(array(select distinct id from ctegroups where group_level_id  = 6),',') ) 
  truncate table groupslevel_tempdata;
--Insert into groupslevel_tempdata select * from usp_groupparentchilddata(groupids);

--select string_to_array(groupids, ',') into a; 

i := 1;
    loop  
        if i > array_upper(a, 1) then
            exit;
        else
          
            Insert into groupslevel_tempdata Select *,a[i] from usp_groupparentchilddata(a[i]) ;
           --Update groups_tempdata set supplygroupname = (SELECT g.name FROM groups g WHERE cast(g.id as varchar) = a[i]) where groups_tempdata.path LIKE ('%|'||a[i]||'|%') ;
            i := i + 1;
        end if;
    end loop;      
Update groupslevel_tempdata t set supplygroupname =  (select g.name from groups g where cast(g.id as varchar(40))  = t.supplygroupname);



RETURN QUERY
Select distinct a.id,a.parentid,a.uniqueid,a.name,a.supplygroupname from groupslevel_tempdata a where a.groupcategoryid in (4,2);
--select * from groupslevel_tempdata;
/*
select distinct  case when idu.id is not null then idu.id else idusite.id END indootunitid, t.name as groupname,
t.supplygroupname,idu.name as idname
from groupslevel_tempdata t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4
Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
where t.groupcategoryid in( 4,2)
order by case when idu.id is not null then idu.id else idusite.id END;

--Select * from usp_getindoorunits_supplylevelgroupname('5,20',6)
*/
END;
$$;


ALTER FUNCTION public.usp_getindoorunits_supplylevelgroupname22(groupids character varying, level integer) OWNER TO postgres;

--
-- TOC entry 388 (class 1255 OID 19807)
-- Name: usp_getindoorunits_supplylevelgroupname_rating(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunits_supplylevelgroupname_rating(groupids character varying, level integer) RETURNS TABLE(indoorunitid bigint, groupname character varying, supplygroupname character varying, idname character varying, groupid bigint, path character varying, co2factor double precision)
    LANGUAGE plpgsql
    AS $$

DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
	DECLARE co2factor double precision ;
  declare
    a text[]; 
    i int;

	
BEGIN

DROP TABLE IF EXISTS groupslevel_temp1;
--DROP TABLE temp_supplygroup;

--
CREATE GLOBAL TEMPORARY  TABLE groupslevel_temp1
(
  id bigint,
  parentid bigint,
  groupcategoryid integer,
  uniqueid character varying(16),
  "name" character varying(45),
  path character varying(45),
  supplygroupid bigint,
  supplygroupname character varying(45),
  co2factor double precision

);
--ON COMMIT DROP;
Select ((array(select distinct id from usp_groupparentchilddata_level(groupids,level))) ) into a;

i := 1;
    loop  
        if i > coalesce (array_upper(a, 1),1) then
            exit;
        else
          -- RETURN;
		Select fn.co2factor into co2factor from usp_getco2factor(a[i]) fn;
          
           --RAISE NOTICE 'Calling cs_create_job(%)', array_upper(a, 1);
	    insert into groupslevel_temp1          Select *,cast(a[i] as bigint),a[i],co2factor from usp_groupparentchilddata(a[i]) ;
          --  RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
          -- commit;
           -- RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
            i := i + 1;
          end if;
    end loop;      
Update  groupslevel_temp1 t set supplygroupname =  (select name from groups where id   = t.supplygroupid),path = (select g.path from groups  g where id   = t.supplygroupid) 
--,supplygroupid = (select g.id from groups  g where id   = t.supplygroupid) 
;

RETURN QUERY
--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
select distinct  case when idu.id is not null then idu.id else idusite.id END indootunitid, t.name as groupname,
t.supplygroupname,idu.name as idname,t.supplygroupid,t.path,t.co2factor
from  groupslevel_temp1 t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4
Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
where t.groupcategoryid in( 4,2)
order by case when idu.id is not null then idu.id else idusite.id END;

--Select * from usp_getindoorunits_supplylevelgroupname_rating('5,20',6)

END;
$$;


ALTER FUNCTION public.usp_getindoorunits_supplylevelgroupname_rating(groupids character varying, level integer) OWNER TO postgres;

--
-- TOC entry 397 (class 1255 OID 19808)
-- Name: usp_getindoorunitsbyuser(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunitsbyuser(userid bigint) RETURNS TABLE(indoorunitid bigint, groupname character varying, idname character varying)
    LANGUAGE plpgsql
    AS $$DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
  declare
    a text[]; 
    i int;

	
BEGIN

DROP TABLE IF EXISTS groupslevel_temp1;
--DROP TABLE temp_supplygroup;

--
CREATE GLOBAL TEMPORARY  TABLE groupslevel_temp1
(
  id bigint,
  parentid bigint,
  groupcategoryid integer,
  uniqueid character varying(16),
  "name" character varying(45),
  path character varying(45),
  supplygroupname character varying(45)

);
--ON COMMIT DROP;
Select array_to_string(array(select (group_id) from companiesusers where user_id = userid),',') into IDs;

Select ((array(select group_id from companiesusers where user_id = 2)) ) into a;
--Select * from usp_groupparentchilddata('8,21,30,31,33,1,25') ;

/*
i := 1;
    loop  
        if i > coalesce (array_upper(a, 1),1) then
            exit;
        else
          --RAISE NOTICE 'Calling cs_create_job(%)', array_upper(a, 1);
	    insert into groupslevel_temp1          Select *,a[i] from usp_groupparentchilddata(a[i]) ;
          --  RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
            i := i + 1;
          end if;
    end loop;      
Update  groupslevel_temp1 t set supplygroupname =  (select name from groups where cast(id as varchar(40))  = t.supplygroupname);
*/
RETURN QUERY

select distinct  case when idu.id is not null then idu.id else idusite.id END indootunitid, t.groupname,
idu.name as idname
from  usp_groupparentchilddata(IDs) t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4
Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
where t.groupcategoryid in( 4,2)
order by case when idu.id is not null then idu.id else idusite.id END;

END;$$;


ALTER FUNCTION public.usp_getindoorunitsbyuser(userid bigint) OWNER TO postgres;

--
-- TOC entry 389 (class 1255 OID 19809)
-- Name: usp_getsites(bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getsites(userid bigint, companyid bigint) RETURNS TABLE(id bigint, groupcategoryid integer, path character varying, co2factor double precision)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
WITH RECURSIVE cte1 AS
(select g.id ,g.groupcategoryid ,g.path,g.co2factor
	from companiesusers cu 
	inner join groups g 
	on cu.group_id =  g.id
	where cu.user_id =  userid and cu.company_id = companyid and g.groupcategoryid In (1,2)
Union
SELECT g.id ,g.groupcategoryid ,g.path,g.co2factor
FROM groups g 
INNER JOIN cte1 AS sp
ON (g.parentid = sp.id )
and sp.groupcategoryid= 1
)

Select * from cte1 where cte1.groupcategoryid =2;

END;
$$;


ALTER FUNCTION public.usp_getsites(userid bigint, companyid bigint) OWNER TO postgres;

--
-- TOC entry 380 (class 1255 OID 19810)
-- Name: usp_getsites_test(bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getsites_test(userid bigint, companyid bigint) RETURNS TABLE(id bigint, groupcategoryid integer, path character varying, co2factor double precision)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
WITH RECURSIVE cte1 AS
(select g.id ,g.groupcategoryid ,g.path,g.co2factor
	from companiesusers cu 
	inner join groups g 
	on cu.group_id =  g.id
	where cu.user_id =  userid and cu.company_id = companyid and g.groupcategoryid In (1,2)
Union
SELECT g.id ,g.groupcategoryid ,g.path,g.co2factor
FROM groups g 
INNER JOIN cte1 AS sp
ON (g.parentid = sp.id )
and sp.groupcategoryid= 1
)

Select * from cte1 where cte1.groupcategoryid =2;

END;
$$;


ALTER FUNCTION public.usp_getsites_test(userid bigint, companyid bigint) OWNER TO postgres;

--
-- TOC entry 390 (class 1255 OID 19811)
-- Name: usp_groupparentchilddata(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_groupparentchilddata(groupids character varying) RETURNS TABLE(id bigint, parentid bigint, groupcategoryid integer, uniqueid character varying, groupname character varying, path character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
/*
WITH RECURSIVE ctegroupsot AS
(SELECT g.id, g.parentid, g.groupcategoryid,g.uniqueid,cast(g.name as varchar(45)) as groupname,g.path
FROM groups g
WHERE g.id = ANY(string_to_array(groupids, ',')::int[])
UNION ALL
SELECT si.id,si.parentid,si.groupcategoryid,si.uniqueid,cast(si.name as varchar(45))as groupname,sp.path
	FROM groups As si
	INNER JOIN ctegroupsot AS sp
	ON (si.id = sp.parentid)
)
,
*/
WITH RECURSIVE ctegroups AS
(SELECT g.id, g.parentid, g.groupcategoryid,g.uniqueid,cast(g.name as varchar(45)) as groupname,g.path
FROM groups g
WHERE 
(g.parentid = ANY(string_to_array(groupids, ',')::int[]) OR (g.id = ANY(string_to_array(groupids, ',')::int[])))
UNION ALL
SELECT si.id,	si.parentid,si.groupcategoryid,si.uniqueid,cast(si.name as varchar(45))as groupname,si.path
	FROM groups As si
	INNER JOIN ctegroups AS sp
	ON (si.parentid = sp.id)
)
Select * from ctegroups;
/*from(
Select * from ctegroups Union ALL
Select * from ctegroupsot where ctegroupsot.groupcategoryid =  2) main;
*/
END;
$$;


ALTER FUNCTION public.usp_groupparentchilddata(groupids character varying) OWNER TO postgres;

--
-- TOC entry 391 (class 1255 OID 19812)
-- Name: usp_groupparentchilddata_level(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_groupparentchilddata_level(groupids character varying, level integer) RETURNS TABLE(id bigint, parentid bigint, uniqueid character varying, groupname character varying, path character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY

WITH RECURSIVE ctegroups AS
(SELECT g.id, g.parentid, g.groupcategoryid,g.uniqueid,cast(g.name as varchar(45)) as groupname,g.path,g.group_level_id
FROM groups g
WHERE 
(g.parentid = ANY(string_to_array(groupids, ',')::int[]) OR (g.id = ANY(string_to_array(groupids, ',')::int[])))
UNION ALL
SELECT si.id,	si.parentid,si.groupcategoryid,si.uniqueid,cast(si.name as varchar(45))as groupname,sp.path,si.group_level_id
	FROM groups As si
	INNER JOIN ctegroups AS sp
	ON (si.parentid = sp.id)
)
Select c.id,c.parentid,c.uniqueid,c.groupname,c.path from ctegroups  c where group_level_id  = level;	

END;
$$;


ALTER FUNCTION public.usp_groupparentchilddata_level(groupids character varying, level integer) OWNER TO postgres;

--
-- TOC entry 392 (class 1255 OID 19813)
-- Name: usp_svgunitlevelinfo(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_svgunitlevelinfo(groupid character varying) RETURNS TABLE(path character varying, group_id integer, iduid bigint, oduid bigint, currenttemp double precision, modename character varying, indoorunit_id bigint, alarmindoorunitcount bigint, idumodelname character varying, modelname character varying, iducentraladdress character varying, centraladdress character varying, idusvg_min_latitude numeric, idusvg_max_latitude numeric, idusvg_min_longitude numeric, idusvg_max_longitude numeric, svg_min_latitude numeric, svg_max_latitude numeric, svg_min_longitude numeric, svg_max_longitude numeric, workingtime1 numeric, workingtime2 numeric, workingtime3 numeric, utilizationrate real, odugroupid integer, ison smallint, serialnumber character varying, fanspeedname character varying, winddirectionname character varying, iseconavi smallint, roomtemp real)
    LANGUAGE plpgsql
    AS $$

DECLARE v_incount BIGINT DEFAULT 0;
DECLARE v_outcount BIGINT DEFAULT 0;
BEGIN



/*
if v_incount =  0 then 
begin

	RETURN QUERY
     SELECT t.path, t.group_id,coalesce(t.indoorunit_id,0) as iduid,
coalesce(t.outdoorunit_id,0) as oduid,idul.currenttemp as temperature,mm.modename as mode,coalesce(ast.indoorunit_id ,0)as alarmindoorunitcount,coalesce(ast1.outdoorunit_id ,0)as alarmoutdoorunitcount,midu.modelname as idumodelname,modu.modelname as odumodelname,idu.centraladdress as iducentraladdress,odu.centraladdress as oducentraladdress,
idu.svg_min_latitude as idu_svg_min_latitude,idu.svg_max_latitude as idu_svg_max_latitude,idu.svg_min_longitude as idu_svg_min_longitude,
idu.svg_max_longitude as idu_svg_max_longitude,odu.svg_min_latitude as odu_svg_min_latitude,odu.svg_max_latitude as odu_svg_max_latitude
,odu.svg_min_longitude as odu_svg_min_longitude,odu.svg_max_longitude as odu_svg_max_longitude,odus.workingtime1,odus.workingtime2,odus.workingtime3,odus.utilizationrate,gpu.group_id as odugroupid,idul.ison as idu_ison
,idu.serialnumber,idul.iseconavi,pccd_1.roomtemp
FROM (
    SELECT gu.group_id,gu.indoorunit_id ,gu.outdoorunit_id,gmain.path
    FROM groupsunits gu
      INNER JOIN (SELECT id, g.path FROM groups g WHERE g.path LIKE ('%|'||groupid||'|%')) gmain
      ON gu.group_id = gmain.id
  ) t
LEFT OUTER JOIN indoorunitslog idul on t.indoorunit_id = idul.indoorunit_id
LEFT OUTER JOIN indoorunits idu on t.indoorunit_id = idu.id
LEFT OUTER JOIN metaindoorunits midu on idu.metaindoorunit_id = midu.id
LEFT OUTER JOIN outdoorunits odu on t.outdoorunit_id = odu.id
LEFT OUTER JOIN metaoutdoorunits modu on modu.id = odu.metaoutdoorunit_id
LEFT OUTER JOIN outdoorunitstatistics odus on odus.outdoorunit_id = odu.id
LEFT OUTER JOIN modemaster mm on idul.currentmode = mm.id
LEFT OUTER JOIN groupsunits gpu on gpu.outdoorunit_id = odu.id
LEFT OUTER JOIN
(select max(p.id) id,p.indoorunit_id from power_consumption_capacity_daily p group by p.indoorunit_id)  pccd on   pccd.indoorunit_id  = idu.id
LEFT OUTER JOIN power_consumption_capacity_daily pccd_1 on   pccd.id  = pccd_1.id
LEFT OUTER JOIN
(Select (indoorunit_id) as indoorunit_id,isdel,severity from alarmstatistics   where isdel = 0 )  ast on   ast.indoorunit_id  = t.indoorunit_id
LEFT OUTER JOIN
(Select (outdoorunit_id) as outdoorunit_id,isdel,severity from alarmstatistics where isdel = 0 )  ast1 on   ast1.outdoorunit_id  = t.outdoorunit_id;

end;
end if;
*/



RETURN QUERY
SELECT g.path, idu.group_id,coalesce(idu.id,0) as iduid, idu.outdoorunit_id as linkedoutdoorunit_id
,idul.setpointtemperature as temperature,mm.modename as mode,countindoorunitid,countoutdoorunitid
,midu.modelname as idumodelname,modu.modelname as odumodelname,idu.centraladdress as iducentraladdress,odu.centraladdress as oducentraladdress,
idu.svg_min_latitude as idu_svg_min_latitude,idu.svg_max_latitude as idu_svg_max_latitude,idu.svg_min_longitude as idu_svg_min_longitude,idu.svg_max_longitude as idu_svg_max_longitude,odu.svg_min_latitude as odu_svg_min_latitude,odu.svg_max_latitude as odu_svg_max_latitude,odu.svg_min_longitude as odu_svg_min_longitude,odu.svg_max_longitude as odu_svg_max_longitude,odus.workingtime1,odus.workingtime2,odus.workingtime3,odus.utilizationrate,0 as odugroupid,idul.powerstatus as idu_ison,
idu.serialnumber,fm.fanspeedname,wm.winddirectionname,idul.iseconavi,pccd_1.roomtemp
  FROM usp_getindoorunits_supplygroupname(groupid) t
	JOIN indoorunits idu on t.indoorunitid = idu.id
	LEFT OUTER JOIN indoorunitslog idul on idu.id = idul.indoorunit_id
	LEFT OUTER JOIN metaindoorunits midu on idu.metaindoorunit_id = midu.id
	LEFT OUTER JOIN outdoorunits odu on idu.outdoorunit_id = odu.id
	LEFT OUTER JOIN metaoutdoorunits modu on modu.id = odu.metaoutdoorunit_id
	LEFT OUTER JOIN outdoorunitslog odus on odus.outdoorunit_id = odu.id
	inner join modemaster mm on idul.acmode = mm.id
	LEFT OUTER join fanspeed_master fm on idul.fanspeed = fm.id
	LEFT OUTER join winddirection_master wm on idul.flapmode = wm.id
	--LEFT OUTER JOIN groupsunits gpu on gpu.outdoorunit_id = odu.id
	LEFT OUTER JOIN
	(select max(p.id) id,p.indoorunit_id from power_consumption_capacity p group by p.indoorunit_id)  pccd on   pccd.indoorunit_id  = idu.id
	LEFT OUTER JOIN power_consumption_capacity pccd_1 on   pccd.id  = pccd_1.id
	LEFT OUTER JOIN
	(Select count(a.indoorunit_id) as countindoorunitid, a.indoorunit_id from notificationlog a  group by a.indoorunit_id)  ast on   ast.indoorunit_id  = idu.id
	LEFT OUTER JOIN
	(Select count(a.outdoorunit_id) as countoutdoorunitid,a.outdoorunit_id from notificationlog a group by a.outdoorunit_id)  ast1 on   ast1.outdoorunit_id  = idu.outdoorunit_id
	Left Outer Join groups g on g.id = idu.group_id;


END
$$;


ALTER FUNCTION public.usp_svgunitlevelinfo(groupid character varying) OWNER TO postgres;

--
-- TOC entry 2044 (class 2328 OID 19815)
-- Name: b2bac; Type: FOREIGN DATA WRAPPER; Schema: -; Owner: postgres
--

CREATE FOREIGN DATA WRAPPER b2bac VALIDATOR postgresql_fdw_validator;


ALTER FOREIGN DATA WRAPPER b2bac OWNER TO postgres;

--
-- TOC entry 2045 (class 2328 OID 19816)
-- Name: dbrnd; Type: FOREIGN DATA WRAPPER; Schema: -; Owner: postgres
--

CREATE FOREIGN DATA WRAPPER dbrnd VALIDATOR postgresql_fdw_validator;


ALTER FOREIGN DATA WRAPPER dbrnd OWNER TO postgres;

--
-- TOC entry 2046 (class 2328 OID 19817)
-- Name: testdblinkserver; Type: FOREIGN DATA WRAPPER; Schema: -; Owner: postgres
--

CREATE FOREIGN DATA WRAPPER testdblinkserver VALIDATOR postgresql_fdw_validator;


ALTER FOREIGN DATA WRAPPER testdblinkserver OWNER TO postgres;

--
-- TOC entry 2048 (class 1417 OID 22701)
-- Name: b2bac; Type: SERVER; Schema: -; Owner: postgres
--

CREATE SERVER b2bac FOREIGN DATA WRAPPER b2bac OPTIONS (
    dbname 'platform',
    host 'localhost',
    hostaddr '127.0.0.1'
);


ALTER SERVER b2bac OWNER TO postgres;

--
-- TOC entry 2753 (class 0 OID 0)
-- Dependencies: 2048
-- Name: USER MAPPING postgres SERVER b2bac; Type: USER MAPPING; Schema: -; Owner: postgres
--

CREATE USER MAPPING FOR postgres SERVER b2bac OPTIONS (
    password 'postgres',
    "user" 'postgres'
);


--
-- TOC entry 2047 (class 1417 OID 19820)
-- Name: b2bacserver; Type: SERVER; Schema: -; Owner: postgres
--

CREATE SERVER b2bacserver FOREIGN DATA WRAPPER testdblinkserver OPTIONS (
    dbname 'platform',
    hostaddr '10.131.10.6'
);


ALTER SERVER b2bacserver OWNER TO postgres;

--
-- TOC entry 2755 (class 0 OID 0)
-- Dependencies: 2047
-- Name: USER MAPPING postgres SERVER b2bacserver; Type: USER MAPPING; Schema: -; Owner: postgres
--

CREATE USER MAPPING FOR postgres SERVER b2bacserver OPTIONS (
    password 'postgres',
    "user" 'postgres'
);


--
-- TOC entry 190 (class 1259 OID 19828)
-- Name: active_locks; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW active_locks AS
 SELECT t.schemaname,
    t.relname,
    l.locktype,
    l.page,
    l.virtualtransaction,
    l.pid,
    l.mode,
    l.granted
   FROM (pg_locks l
     JOIN pg_stat_all_tables t ON ((l.relation = t.relid)))
  WHERE ((t.schemaname <> 'pg_toast'::name) AND (t.schemaname <> 'pg_catalog'::name))
  ORDER BY t.schemaname, t.relname;


ALTER TABLE active_locks OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 191 (class 1259 OID 19833)
-- Name: adapters; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE adapters (
    id bigint NOT NULL,
    activatedate timestamp without time zone,
    address character varying(255),
    createdby character varying(255),
    creationdate timestamp without time zone NOT NULL,
    enabled_date timestamp without time zone,
    ipnumber character varying(255),
    latitude real,
    longitude real,
    notificationrecipients numeric(19,2),
    scanned_date timestamp without time zone,
    scanning_date timestamp without time zone,
    serialnumber character varying(255),
    state character varying(255),
    timelinefetched_date timestamp without time zone,
    timelinefetchedperiod character varying(255),
    updatedate timestamp without time zone,
    updatedby character varying(255),
    company_id bigint,
    defualtgroupid bigint,
    siteid bigint,
    timezone smallint
);


ALTER TABLE adapters OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 19839)
-- Name: adapters_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE adapters_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adapters_id_seq OWNER TO postgres;

--
-- TOC entry 2756 (class 0 OID 0)
-- Dependencies: 192
-- Name: adapters_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE adapters_id_seq OWNED BY adapters.id;


--
-- TOC entry 299 (class 1259 OID 20836)
-- Name: alarmanotificationmailtemp; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE alarmanotificationmailtemp (
    deviceid bigint,
    errorcode character varying(256),
    alarmtype character varying(128),
    groupname character varying(128),
    siteid character varying(128),
    maintenance_description character varying(512),
    svg_max_longitude numeric(17,14),
    svg_max_latitude numeric(17,14),
    svg_min_longitude numeric(17,14),
    svg_min_latitude numeric(17,14),
    notificationtype_id integer,
    groupid bigint,
    userid bigint,
    email character varying(256),
    notificationtype character varying(256),
    devicetype character varying(16),
    countermeasure_2way character varying(256),
    countermeasure_3way character varying(256),
    countermeasure_customer character varying(256),
    creationdate timestamp without time zone,
    comapany_id bigint
);


ALTER TABLE alarmanotificationmailtemp OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 19841)
-- Name: notificationlog; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notificationlog (
    id bigint NOT NULL,
    alarm_type character varying(255),
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    description character varying(500),
    severity character varying(45),
    status character varying(45),
    "time" timestamp without time zone,
    updatedate timestamp without time zone,
    updatedby character varying(45),
    adapterid bigint,
    code character varying(255),
    indoorunit_id bigint,
    outdoorunit_id bigint,
    fixed_time timestamp without time zone
);


ALTER TABLE notificationlog OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 19847)
-- Name: alarmstatistics_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE alarmstatistics_id_seq
    START WITH 39
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE alarmstatistics_id_seq OWNER TO postgres;

--
-- TOC entry 2757 (class 0 OID 0)
-- Dependencies: 194
-- Name: alarmstatistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE alarmstatistics_id_seq OWNED BY notificationlog.id;


--
-- TOC entry 195 (class 1259 OID 19857)
-- Name: companies; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE companies (
    id bigint NOT NULL,
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    deploymentid numeric(10,0),
    imagepath character varying(255),
    name character varying(45) NOT NULL,
    updatedate timestamp without time zone,
    updatedby character varying(45),
    isdel boolean
);


ALTER TABLE companies OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 19860)
-- Name: companies_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE companies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE companies_id_seq OWNER TO postgres;

--
-- TOC entry 2758 (class 0 OID 0)
-- Dependencies: 196
-- Name: companies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE companies_id_seq OWNED BY companies.id;


--
-- TOC entry 197 (class 1259 OID 19862)
-- Name: companiesusers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE companiesusers (
    company_id bigint NOT NULL,
    user_id bigint NOT NULL,
    group_id bigint NOT NULL,
    creationdate timestamp without time zone DEFAULT now() NOT NULL,
    updatedate timestamp without time zone,
    createdby character varying(45) DEFAULT NULL::character varying,
    updatedby character varying(45) DEFAULT NULL::character varying
);


ALTER TABLE companiesusers OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 19868)
-- Name: status_info; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW status_info AS
 SELECT sts.facl_id,
    sts.property_id,
    sts.measure_val
   FROM dblink('host=localhost user=postgres password=postgres dbname=platform'::text, 'SELECT facl_id,property_id,measure_val FROM public.status_info'::text) sts(facl_id character varying(64), property_id character varying(64), measure_val character varying(64));


ALTER TABLE status_info OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 19872)
-- Name: ct_statusinfo; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW ct_statusinfo AS
 SELECT eav.facl_id,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A28'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a28,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A3'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a3,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A4'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a4,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'B17-1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS b17_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'B17-2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS b17_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'B17-3'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS b17_3,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'V20'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS v20,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'V21'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS v21,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'V22'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS v22,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'B1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS b1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'GS1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS gs1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A3a'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a3a,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A3b'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a3b,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A3c'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a3c,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A3d'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a3d,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6a'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6a,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6b'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6b,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6c'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6c,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6d'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6d,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6e'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6e,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7a'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7a,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7b'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7b,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7c'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7c,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7d'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7d,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7e'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7e,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A10'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a10,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A11'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a11,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A12'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a12,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A13'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a13,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A14'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a14,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A15'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a15,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A34'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a34
   FROM ( SELECT status_info.facl_id,
            status_info.property_id,
            status_info.measure_val
           FROM status_info) eav
  GROUP BY eav.facl_id;


ALTER TABLE ct_statusinfo OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 19877)
-- Name: rcoperation_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rcoperation_master (
    id bigint NOT NULL,
    name character varying(45)
);


ALTER TABLE rcoperation_master OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 19880)
-- Name: deviceattribute_master_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE deviceattribute_master_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE deviceattribute_master_id_seq OWNER TO postgres;

--
-- TOC entry 2759 (class 0 OID 0)
-- Dependencies: 201
-- Name: deviceattribute_master_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE deviceattribute_master_id_seq OWNED BY rcoperation_master.id;


--
-- TOC entry 202 (class 1259 OID 19882)
-- Name: rcuser_action; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rcuser_action (
    id bigint NOT NULL,
    command character varying(45),
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    device_id bigint NOT NULL,
    devicetype character varying(45),
    updatedate timestamp without time zone,
    updatedby character varying(45),
    value integer NOT NULL,
    user_id bigint
);


ALTER TABLE rcuser_action OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 19885)
-- Name: devicecommand_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE devicecommand_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE devicecommand_id_seq OWNER TO postgres;

--
-- TOC entry 2760 (class 0 OID 0)
-- Dependencies: 203
-- Name: devicecommand_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE devicecommand_id_seq OWNED BY rcuser_action.id;


--
-- TOC entry 204 (class 1259 OID 19895)
-- Name: efficiency_rating; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE efficiency_rating (
    id bigint NOT NULL,
    rating character varying(45),
    cooling_efficiency double precision,
    heating_efficiency double precision
);


ALTER TABLE efficiency_rating OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 19898)
-- Name: efficiency_rating_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE efficiency_rating_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE efficiency_rating_id_seq OWNER TO postgres;

--
-- TOC entry 2761 (class 0 OID 0)
-- Dependencies: 205
-- Name: efficiency_rating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE efficiency_rating_id_seq OWNED BY efficiency_rating.id;


--
-- TOC entry 206 (class 1259 OID 19900)
-- Name: errorcode_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE errorcode_master (
    id integer NOT NULL,
    code character varying(64) NOT NULL,
    severity character varying(16),
    customer_description character varying(128),
    maintenance_description character varying(1024),
    createdby integer,
    creationdate timestamp without time zone,
    updatedate timestamp without time zone,
    updatedby integer,
    type character varying(45),
    countermeasure_2way character varying(255),
    countermeasure_3way character varying(255),
    countermeasure_customer character varying(255),
    notificationtype_id integer
);


ALTER TABLE errorcode_master OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 19906)
-- Name: errorgroup; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE errorgroup (
    errorgroupid integer NOT NULL,
    errorgroup_category character varying(256)
);


ALTER TABLE errorgroup OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 19909)
-- Name: fanspeed_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fanspeed_master (
    id bigint NOT NULL,
    fanspeedname character varying(255)
);


ALTER TABLE fanspeed_master OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 19912)
-- Name: fanspeed_master_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fanspeed_master_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fanspeed_master_id_seq OWNER TO postgres;

--
-- TOC entry 2762 (class 0 OID 0)
-- Dependencies: 209
-- Name: fanspeed_master_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE fanspeed_master_id_seq OWNED BY fanspeed_master.id;


--
-- TOC entry 210 (class 1259 OID 19914)
-- Name: gasheatmeter_data; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE gasheatmeter_data (
    id bigint NOT NULL,
    creationdate timestamp without time zone,
    logtime timestamp without time zone,
    totalenergy_consumed double precision,
    updatedate timestamp without time zone,
    outdoorunit_id bigint
);


ALTER TABLE gasheatmeter_data OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 19917)
-- Name: gasheatmeter_data_daily; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE gasheatmeter_data_daily (
    id bigint NOT NULL,
    totalenergy_consumed double precision,
    outdoorunit_id bigint,
    logtime timestamp without time zone,
    vrfwater_heat_exchanger integer,
    ghpwater_heat_exchanger integer
);


ALTER TABLE gasheatmeter_data_daily OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 19920)
-- Name: gasheatmeter_data_daily_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE gasheatmeter_data_daily_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gasheatmeter_data_daily_id_seq OWNER TO postgres;

--
-- TOC entry 2763 (class 0 OID 0)
-- Dependencies: 212
-- Name: gasheatmeter_data_daily_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_daily_id_seq OWNED BY gasheatmeter_data_daily.id;


--
-- TOC entry 213 (class 1259 OID 19922)
-- Name: gasheatmeter_data_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE gasheatmeter_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gasheatmeter_data_id_seq OWNER TO postgres;

--
-- TOC entry 2764 (class 0 OID 0)
-- Dependencies: 213
-- Name: gasheatmeter_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_id_seq OWNED BY gasheatmeter_data.id;


--
-- TOC entry 214 (class 1259 OID 19924)
-- Name: gasheatmeter_data_monthly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE gasheatmeter_data_monthly (
    id bigint NOT NULL,
    month smallint NOT NULL,
    totalenergy_consumed double precision,
    year smallint NOT NULL,
    outdoorunit_id bigint,
    logtime timestamp without time zone,
    vrfwater_heat_exchanger integer,
    ghpwater_heat_exchanger integer
);


ALTER TABLE gasheatmeter_data_monthly OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 19927)
-- Name: gasheatmeter_data_monthly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE gasheatmeter_data_monthly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gasheatmeter_data_monthly_id_seq OWNER TO postgres;

--
-- TOC entry 2765 (class 0 OID 0)
-- Dependencies: 215
-- Name: gasheatmeter_data_monthly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_monthly_id_seq OWNED BY gasheatmeter_data_monthly.id;


--
-- TOC entry 216 (class 1259 OID 19929)
-- Name: gasheatmeter_data_weekly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE gasheatmeter_data_weekly (
    id bigint NOT NULL,
    creationdate timestamp without time zone,
    totalenergy_consumed double precision,
    updatedate timestamp without time zone,
    week smallint NOT NULL,
    year smallint NOT NULL,
    outdoorunit_id bigint,
    ghpwater_heat_exchanger integer,
    vrfwater_heat_exchanger integer,
    logtime timestamp without time zone
);


ALTER TABLE gasheatmeter_data_weekly OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 19932)
-- Name: gasheatmeter_data_weekly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE gasheatmeter_data_weekly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gasheatmeter_data_weekly_id_seq OWNER TO postgres;

--
-- TOC entry 2766 (class 0 OID 0)
-- Dependencies: 217
-- Name: gasheatmeter_data_weekly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_weekly_id_seq OWNED BY gasheatmeter_data_weekly.id;


--
-- TOC entry 218 (class 1259 OID 19934)
-- Name: gasheatmeter_data_yearly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE gasheatmeter_data_yearly (
    id bigint NOT NULL,
    totalenergy_consumed double precision,
    year smallint NOT NULL,
    outdoorunit_id bigint,
    logtime timestamp without time zone,
    vrfwater_heat_exchanger integer,
    ghpwater_heat_exchanger integer
);


ALTER TABLE gasheatmeter_data_yearly OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 19937)
-- Name: gasheatmeter_data_yearly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE gasheatmeter_data_yearly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gasheatmeter_data_yearly_id_seq OWNER TO postgres;

--
-- TOC entry 2767 (class 0 OID 0)
-- Dependencies: 219
-- Name: gasheatmeter_data_yearly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_yearly_id_seq OWNED BY gasheatmeter_data_yearly.id;


--
-- TOC entry 220 (class 1259 OID 19939)
-- Name: ghpparameter_statistics; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ghpparameter_statistics (
    id bigint NOT NULL,
    threewaycoolervalve integer,
    threewayvalveforcoolant integer,
    threewayvalveforhotwater integer,
    balancevalve smallint NOT NULL,
    bypassvalve_opening integer,
    catalyzertemperature integer NOT NULL,
    clutch smallint NOT NULL,
    clutch2 smallint NOT NULL,
    clutchcoiltemperature integer NOT NULL,
    clutchcoiltemperature2 integer NOT NULL,
    comperssoroillevel integer NOT NULL,
    compressorheater smallint NOT NULL,
    compressorinlet_pressure integer,
    compressorinlet_temperature integer,
    compressoroutlet_pressure integer,
    compressoroutlet_temperature integer,
    coolanttemperature integer NOT NULL,
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    dischargesolenoidvalve1 smallint NOT NULL,
    dischargesolenoidvalve2 smallint NOT NULL,
    drainfilterheater1 smallint NOT NULL,
    drainfilterheater2 smallint NOT NULL,
    engineoperation_time numeric(19,2),
    enginerevolution integer NOT NULL,
    exhaustgastemperature integer NOT NULL,
    exhaustheat_recovery_valve_opening integer,
    expansionvalve_opening integer,
    expansionvalve_opening2 integer,
    flushingvalve smallint NOT NULL,
    fuelgas_regulating_valve_opening integer,
    fuelgasshut_offvalve1 smallint,
    fuelgasshut_offvalve2 smallint,
    gasrefrigerantshut_offvalve smallint,
    generationpower integer NOT NULL,
    ghpoilsign smallint NOT NULL,
    heaterfor_cold_region smallint,
    heatexchangerinlet_temperature integer,
    heatexchangerinlet_temperature2 integer,
    hotwatertemperature integer NOT NULL,
    ignitiontiming integer NOT NULL,
    instantgas double precision NOT NULL,
    instantheat double precision NOT NULL,
    liquidvalve_opening integer,
    oilleveltemperature integer NOT NULL,
    oilpump smallint NOT NULL,
    oilrecoveryvalve smallint NOT NULL,
    outdoorunitfanoutput integer NOT NULL,
    pumpforhotwater smallint NOT NULL,
    receivertankvalve1 smallint NOT NULL,
    receivertankvalve2 smallint NOT NULL,
    startermotor smallint NOT NULL,
    startermotorcurrent integer NOT NULL,
    statermotor_power smallint,
    suctionsolenoidevalve1 smallint NOT NULL,
    suctionsolenoidevalve2 smallint NOT NULL,
    superheat_level_of_compressor_unit integer,
    thtottle integer NOT NULL,
    timeafter_changing_engine_oil numeric(19,2),
    updatedate timestamp without time zone,
    updatedby character varying(255),
    outdoorunit_id bigint,
    logtime timestamp without time zone
);


ALTER TABLE ghpparameter_statistics OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 19942)
-- Name: ghpparameter_statistics_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ghpparameter_statistics_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ghpparameter_statistics_id_seq OWNER TO postgres;

--
-- TOC entry 2768 (class 0 OID 0)
-- Dependencies: 221
-- Name: ghpparameter_statistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ghpparameter_statistics_id_seq OWNED BY ghpparameter_statistics.id;


--
-- TOC entry 222 (class 1259 OID 19944)
-- Name: group_level; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE group_level (
    id bigint NOT NULL,
    type_level character varying(128),
    type_level_name character varying(128)
);


ALTER TABLE group_level OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 19947)
-- Name: groupcategory; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE groupcategory (
    groupcategoryid integer NOT NULL,
    groupcategoryname character varying(128) NOT NULL
);


ALTER TABLE groupcategory OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 19950)
-- Name: groups; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE groups (
    id bigint NOT NULL,
    createdby character varying(45),
    creationdate timestamp without time zone,
    iconimage character varying(45),
    level integer NOT NULL,
    name character varying(45) NOT NULL,
    path character varying(45),
    svg_path character varying(255),
    updatedate timestamp without time zone,
    updatedby character varying(45),
    company_id bigint,
    parentid bigint,
    groupcriteria_id character varying(255),
    groupcategoryid integer,
    last_access_groupids character varying(1024),
    isdel boolean,
    uniqueid character varying(16) NOT NULL,
    co2factor double precision,
    group_level_id bigint,
    map_latitude numeric(17,14),
    map_longitude numeric(17,14),
    svg_max_latitude numeric(17,14),
    svg_max_longitude numeric(17,14),
    svg_min_latitude numeric(17,14),
    svg_min_longitude numeric(17,14),
    timezone bigint,
    isunit_exists bit(1) DEFAULT (0)::bit(1) NOT NULL
);


ALTER TABLE groups OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 19956)
-- Name: groups_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE groups_id_seq OWNER TO postgres;

--
-- TOC entry 2769 (class 0 OID 0)
-- Dependencies: 225
-- Name: groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE groups_id_seq OWNED BY groups.id;


--
-- TOC entry 226 (class 1259 OID 19961)
-- Name: groupscriteria; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE groupscriteria (
    id character varying(255) NOT NULL,
    is_child smallint,
    is_sibling smallint,
    is_unit smallint
);


ALTER TABLE groupscriteria OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 19964)
-- Name: indoorunits; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indoorunits (
    id bigint NOT NULL,
    centraladdress character varying(45),
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    currenttime timestamp without time zone,
    parent_id integer,
    serialnumber character varying(45) NOT NULL,
    type character varying(45),
    updatedate timestamp without time zone,
    updatedby character varying(45),
    adapters_id bigint,
    metaindoorunit_id bigint,
    outdoorunit_id bigint,
    siteid character varying(32),
    group_id integer,
    name character varying(32),
    oid character varying(128),
    svg_path character varying(255),
    svg_max_latitude numeric(17,14),
    svg_max_longitude numeric(17,14),
    svg_min_latitude numeric(17,14),
    svg_min_longitude numeric(17,14)
);


ALTER TABLE indoorunits OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 19970)
-- Name: indoorunits_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indoorunits_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE indoorunits_id_seq OWNER TO postgres;

--
-- TOC entry 2770 (class 0 OID 0)
-- Dependencies: 228
-- Name: indoorunits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunits_id_seq OWNED BY indoorunits.id;


--
-- TOC entry 229 (class 1259 OID 19972)
-- Name: indoorunitslog; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indoorunitslog (
    id bigint NOT NULL,
    roomtemp double precision,
    setpointtemperature double precision,
    iseconavi smallint,
    powerstatus smallint,
    modeltype integer,
    rcprohibitfanspeed smallint,
    rcprohibitmode smallint,
    rcprohibitpwr smallint,
    rcprohibitsettemp smallint,
    rcprohibitvanepos smallint,
    updateeconavi timestamp without time zone,
    fanspeed bigint,
    indoorunit_id bigint,
    acmode bigint,
    flapmode bigint,
    lastfilter_cleaning_date timestamp without time zone,
    status character varying(24)
);


ALTER TABLE indoorunitslog OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 19975)
-- Name: indoorunitslog_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indoorunitslog_history (
    id bigint NOT NULL,
    roomtemp double precision,
    setpointtempreture double precision,
    iseconavi smallint,
    powerstatus smallint,
    modeltype integer,
    rcprohibitfanspeed smallint,
    rcprohibitmode smallint,
    rcprohibitpwr smallint,
    rcprohibitsettemp smallint,
    rcprohibitvanepos smallint,
    updateeconavi timestamp without time zone,
    fanspeed bigint,
    indoorunit_id bigint,
    acmode bigint,
    flapmode bigint,
    lastfilter_cleaning_date timestamp without time zone,
    status character varying(24)
);


ALTER TABLE indoorunitslog_history OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 19978)
-- Name: indoorunitslog_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indoorunitslog_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE indoorunitslog_history_id_seq OWNER TO postgres;

--
-- TOC entry 2771 (class 0 OID 0)
-- Dependencies: 231
-- Name: indoorunitslog_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitslog_history_id_seq OWNED BY indoorunitslog_history.id;


--
-- TOC entry 232 (class 1259 OID 19980)
-- Name: indoorunitslog_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indoorunitslog_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE indoorunitslog_id_seq OWNER TO postgres;

--
-- TOC entry 2772 (class 0 OID 0)
-- Dependencies: 232
-- Name: indoorunitslog_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitslog_id_seq OWNED BY indoorunitslog.id;


--
-- TOC entry 233 (class 1259 OID 19982)
-- Name: indoorunitstatistics; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indoorunitstatistics (
    id bigint NOT NULL,
    logtime timestamp without time zone,
    filtersignclear smallint,
    instantpower integer,
    otelectricheater integer,
    othighfscooling integer,
    othighfsheating integer,
    othighfsthermoff integer,
    othighfsthermon integer,
    otlowfscooling integer,
    otlowfsheating integer,
    otlowfsthermoff integer,
    otlowfsthermon integer,
    otmediumfscooling integer,
    otmediumfsheating integer,
    otmediumfsthermoff integer,
    otmediumfsthermon integer,
    settablefanspeed smallint,
    settablemode smallint,
    updatefiltersign timestamp without time zone,
    ventilation integer,
    indoorunit_id bigint
);


ALTER TABLE indoorunitstatistics OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 19985)
-- Name: indoorunitstatistics_daily; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indoorunitstatistics_daily (
    id bigint NOT NULL,
    logtime timestamp without time zone,
    filtersignclear smallint,
    instantpower integer,
    otelectricheater integer,
    othighfscooling integer,
    othighfsheating integer,
    othighfsthermoff integer,
    othighfsthermon integer,
    otlowfscooling integer,
    otlowfsheating integer,
    otlowfsthermoff integer,
    otlowfsthermon integer,
    otmediumfscooling integer,
    otmediumfsheating integer,
    otmediumfsthermoff integer,
    otmediumfsthermon integer,
    settablefanspeed smallint,
    settablemode smallint,
    updatefiltersign timestamp without time zone,
    ventilation integer,
    indoorunit_id bigint
);


ALTER TABLE indoorunitstatistics_daily OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 19988)
-- Name: indoorunitstatistics_daily_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indoorunitstatistics_daily_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE indoorunitstatistics_daily_id_seq OWNER TO postgres;

--
-- TOC entry 2773 (class 0 OID 0)
-- Dependencies: 235
-- Name: indoorunitstatistics_daily_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_daily_id_seq OWNED BY indoorunitstatistics_daily.id;


--
-- TOC entry 236 (class 1259 OID 19990)
-- Name: indoorunitstatistics_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indoorunitstatistics_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE indoorunitstatistics_id_seq OWNER TO postgres;

--
-- TOC entry 2774 (class 0 OID 0)
-- Dependencies: 236
-- Name: indoorunitstatistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_id_seq OWNED BY indoorunitstatistics.id;


--
-- TOC entry 237 (class 1259 OID 19992)
-- Name: indoorunitstatistics_monthly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indoorunitstatistics_monthly (
    id bigint NOT NULL,
    filtersignclear smallint,
    instantpower integer,
    month smallint NOT NULL,
    otelectricheater integer,
    othighfscooling integer,
    othighfsheating integer,
    othighfsthermoff integer,
    othighfsthermon integer,
    otlowfscooling integer,
    otlowfsheating integer,
    otlowfsthermoff integer,
    otlowfsthermon integer,
    otmediumfscooling integer,
    otmediumfsheating integer,
    otmediumfsthermoff integer,
    otmediumfsthermon integer,
    settablefanspeed smallint,
    settablemode smallint,
    updatefiltersign timestamp without time zone,
    ventilation integer,
    year smallint NOT NULL,
    indoorunit_id bigint,
    logtime timestamp without time zone
);


ALTER TABLE indoorunitstatistics_monthly OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 19995)
-- Name: indoorunitstatistics_monthly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indoorunitstatistics_monthly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE indoorunitstatistics_monthly_id_seq OWNER TO postgres;

--
-- TOC entry 2775 (class 0 OID 0)
-- Dependencies: 238
-- Name: indoorunitstatistics_monthly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_monthly_id_seq OWNED BY indoorunitstatistics_monthly.id;


--
-- TOC entry 239 (class 1259 OID 19997)
-- Name: indoorunitstatistics_weekly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indoorunitstatistics_weekly (
    id bigint NOT NULL,
    filtersignclear smallint,
    instantpower integer,
    otelectricheater integer,
    othighfscooling integer,
    othighfsheating integer,
    othighfsthermoff integer,
    othighfsthermon integer,
    otlowfscooling integer,
    otlowfsheating integer,
    otlowfsthermoff integer,
    otlowfsthermon integer,
    otmediumfscooling integer,
    otmediumfsheating integer,
    otmediumfsthermoff integer,
    otmediumfsthermon integer,
    settablefanspeed smallint,
    settablemode smallint,
    updatefiltersign timestamp without time zone,
    ventilation integer,
    week smallint NOT NULL,
    year smallint NOT NULL,
    indoorunit_id bigint,
    logtime timestamp without time zone
);


ALTER TABLE indoorunitstatistics_weekly OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 20000)
-- Name: indoorunitstatistics_weekly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indoorunitstatistics_weekly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE indoorunitstatistics_weekly_id_seq OWNER TO postgres;

--
-- TOC entry 2776 (class 0 OID 0)
-- Dependencies: 240
-- Name: indoorunitstatistics_weekly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_weekly_id_seq OWNED BY indoorunitstatistics_weekly.id;


--
-- TOC entry 241 (class 1259 OID 20002)
-- Name: indoorunitstatistics_yearly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indoorunitstatistics_yearly (
    id bigint NOT NULL,
    filtersignclear smallint,
    instantpower integer,
    otelectricheater integer,
    othighfscooling integer,
    othighfsheating integer,
    othighfsthermoff integer,
    othighfsthermon integer,
    otlowfscooling integer,
    otlowfsheating integer,
    otlowfsthermoff integer,
    otlowfsthermon integer,
    otmediumfscooling integer,
    otmediumfsheating integer,
    otmediumfsthermoff integer,
    otmediumfsthermon integer,
    settablefanspeed smallint,
    settablemode smallint,
    updatefiltersign timestamp without time zone,
    ventilation integer,
    year smallint NOT NULL,
    indoorunit_id bigint,
    logtime timestamp without time zone
);


ALTER TABLE indoorunitstatistics_yearly OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 20005)
-- Name: indoorunitstatistics_yearly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indoorunitstatistics_yearly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE indoorunitstatistics_yearly_id_seq OWNER TO postgres;

--
-- TOC entry 2777 (class 0 OID 0)
-- Dependencies: 242
-- Name: indoorunitstatistics_yearly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_yearly_id_seq OWNED BY indoorunitstatistics_yearly.id;


--
-- TOC entry 300 (class 1259 OID 21047)
-- Name: job_tracker; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE job_tracker (
    jobname character varying(60) NOT NULL,
    status character varying(60),
    lastexecutiontime timestamp without time zone NOT NULL,
    createdate timestamp without time zone,
    createdby character varying(100),
    updatedate timestamp without time zone
);


ALTER TABLE job_tracker OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 20007)
-- Name: metaindoorunits; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE metaindoorunits (
    id bigint NOT NULL,
    airflowahigh integer NOT NULL,
    airflowlow integer NOT NULL,
    airflowmedium integer NOT NULL,
    airflowultrahigh integer NOT NULL,
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    fusedisolatora character varying(45),
    horsepower double precision NOT NULL,
    inputpower220vw integer NOT NULL,
    inputpower230vw integer NOT NULL,
    inputpower240vw integer NOT NULL,
    kind character varying(45),
    logo character varying(45),
    modelname character varying(45),
    nominalcoolingkw double precision NOT NULL,
    nominalheatingkw double precision NOT NULL,
    runningcurrent220va double precision NOT NULL,
    runningcurrent230va double precision NOT NULL,
    runningcurrent240va double precision NOT NULL,
    unitdepthm double precision NOT NULL,
    unitheightm double precision NOT NULL,
    unitwidthtm double precision NOT NULL,
    updatedate timestamp without time zone,
    updatedby character varying(45),
    weight numeric(19,2),
    system character varying(45)
);


ALTER TABLE metaindoorunits OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 20010)
-- Name: metaindoorunits_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE metaindoorunits_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE metaindoorunits_id_seq OWNER TO postgres;

--
-- TOC entry 2778 (class 0 OID 0)
-- Dependencies: 244
-- Name: metaindoorunits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE metaindoorunits_id_seq OWNED BY metaindoorunits.id;


--
-- TOC entry 245 (class 1259 OID 20012)
-- Name: metaoutdoorunits; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE metaoutdoorunits (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp without time zone,
    fuelcounsumptioncooling double precision NOT NULL,
    fuelcounsumptionheating double precision NOT NULL,
    fusedisolatora character varying(255),
    inputpower220vw double precision NOT NULL,
    inputpower230vw double precision NOT NULL,
    inputpower240vw double precision NOT NULL,
    inputpower380vw integer NOT NULL,
    inputpower400vw integer NOT NULL,
    inputpower415vw integer NOT NULL,
    kind character varying(255),
    logo character varying(255),
    modelname character varying(255),
    nominalcoolingkw double precision NOT NULL,
    nominalheatingkw double precision NOT NULL,
    phase integer NOT NULL,
    runningcurrent220va double precision NOT NULL,
    runningcurrent230va double precision NOT NULL,
    runningcurrent240va double precision NOT NULL,
    runningcurrent380va double precision NOT NULL,
    runningcurrent400va double precision NOT NULL,
    runningcurrent415va double precision NOT NULL,
    unitdepthm double precision NOT NULL,
    unitheightm double precision NOT NULL,
    unitwidthtm double precision NOT NULL,
    updatedate timestamp without time zone,
    updatedby character varying(255),
    system character varying(45)
);


ALTER TABLE metaoutdoorunits OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 20018)
-- Name: metaoutdoorunits_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE metaoutdoorunits_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE metaoutdoorunits_id_seq OWNER TO postgres;

--
-- TOC entry 2779 (class 0 OID 0)
-- Dependencies: 246
-- Name: metaoutdoorunits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE metaoutdoorunits_id_seq OWNED BY metaoutdoorunits.id;


--
-- TOC entry 247 (class 1259 OID 20020)
-- Name: modemaster; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE modemaster (
    id bigint NOT NULL,
    modename character varying(25)
);


ALTER TABLE modemaster OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 20023)
-- Name: modemaster_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE modemaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE modemaster_id_seq OWNER TO postgres;

--
-- TOC entry 2780 (class 0 OID 0)
-- Dependencies: 248
-- Name: modemaster_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE modemaster_id_seq OWNED BY modemaster.id;


--
-- TOC entry 249 (class 1259 OID 20025)
-- Name: notificationlog_temp; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notificationlog_temp (
    id bigint,
    indoorunit_id bigint,
    outdoorunit_id bigint,
    adapterid bigint,
    occur_datetime date,
    alarmcode character(100),
    severity character(100),
    maintenance_description character(255),
    type character(255),
    timezone character(255)
);


ALTER TABLE notificationlog_temp OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 20031)
-- Name: notificationsettings; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notificationsettings (
    id integer NOT NULL,
    on_off boolean,
    notificationtype_id integer,
    group_id bigint
);


ALTER TABLE notificationsettings OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 20034)
-- Name: notificationsettings_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE notificationsettings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notificationsettings_id_seq OWNER TO postgres;

--
-- TOC entry 2781 (class 0 OID 0)
-- Dependencies: 251
-- Name: notificationsettings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE notificationsettings_id_seq OWNED BY notificationsettings.id;


--
-- TOC entry 252 (class 1259 OID 20036)
-- Name: notificationtype_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notificationtype_master (
    id integer NOT NULL,
    typename character varying(32)
);


ALTER TABLE notificationtype_master OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 20039)
-- Name: notificationtype_master_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE notificationtype_master_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notificationtype_master_id_seq OWNER TO postgres;

--
-- TOC entry 2782 (class 0 OID 0)
-- Dependencies: 253
-- Name: notificationtype_master_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE notificationtype_master_id_seq OWNED BY notificationtype_master.id;


--
-- TOC entry 254 (class 1259 OID 20041)
-- Name: outdoorunitparameters; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunitparameters (
    id bigint NOT NULL,
    display_name character varying(255),
    parameter_name character varying(255),
    parameter_unit character varying(255),
    type character varying(255)
);


ALTER TABLE outdoorunitparameters OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 20047)
-- Name: outdoorunits; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunits (
    id bigint NOT NULL,
    centraladdress character varying(255),
    createdby character varying(255),
    creationdate timestamp without time zone,
    iswaterheatexchanger smallint,
    serialnumber character varying(255),
    type character varying(255),
    updatedate timestamp without time zone,
    updatedby character varying(255),
    adapters_id bigint,
    metaoutdoorunit_id bigint,
    name character varying(32),
    oid character varying(128),
    svg_path character varying(255),
    siteid character varying(45),
    svg_max_latitude numeric(17,14),
    svg_max_longitude numeric(17,14),
    svg_min_latitude numeric(17,14),
    svg_min_longitude numeric(17,14)
);


ALTER TABLE outdoorunits OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 20053)
-- Name: outdoorunits_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE outdoorunits_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE outdoorunits_id_seq OWNER TO postgres;

--
-- TOC entry 2783 (class 0 OID 0)
-- Dependencies: 256
-- Name: outdoorunits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE outdoorunits_id_seq OWNED BY outdoorunits.id;


--
-- TOC entry 257 (class 1259 OID 20055)
-- Name: outdoorunitslog; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunitslog (
    id bigint NOT NULL,
    accumulatedpower_consumption integer,
    checkoil smallint,
    clutch smallint,
    compressorheater smallint,
    instaneouscurrent real NOT NULL,
    nextoilchange timestamp without time zone,
    nextrefreshmaint timestamp without time zone,
    oilpump smallint,
    oiltimeafterchange double precision,
    outdoorhp smallint,
    outdoormodel_info integer,
    outsideairtemperature real,
    prealarminformation integer,
    ratedcooling_refrigerant_circulating integer,
    ratedcurrent double precision NOT NULL,
    ratedheating_refrigerant_circulating integer,
    ratedsystem_current integer,
    refrigerantcirculating integer,
    "time" timestamp without time zone,
    utilizationrate real,
    workingtime double precision NOT NULL,
    workingtime1 numeric(19,2),
    workingtime2 numeric(19,2),
    workingtime3 numeric(19,2),
    outdoorunit_id bigint,
    status character varying(24)
);


ALTER TABLE outdoorunitslog OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 20058)
-- Name: outdoorunitstatistics_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE outdoorunitstatistics_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE outdoorunitstatistics_id_seq OWNER TO postgres;

--
-- TOC entry 2784 (class 0 OID 0)
-- Dependencies: 258
-- Name: outdoorunitstatistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE outdoorunitstatistics_id_seq OWNED BY outdoorunitslog.id;


--
-- TOC entry 259 (class 1259 OID 20060)
-- Name: outdoorunitslog_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunitslog_history (
    id bigint DEFAULT nextval('outdoorunitstatistics_id_seq'::regclass) NOT NULL,
    accumulatedpower_consumption integer,
    checkoil smallint,
    clutch smallint,
    compressorheater smallint,
    instaneouscurrent real NOT NULL,
    nextoilchange timestamp without time zone,
    nextrefreshmaint timestamp without time zone,
    oilpump smallint,
    oiltimeafterchange double precision,
    outdoorhp smallint,
    outdoormodel_info integer,
    outsideairtemperature real,
    prealarminformation integer,
    ratedcooling_refrigerant_circulating integer,
    ratedcurrent double precision NOT NULL,
    ratedheating_refrigerant_circulating integer,
    ratedsystem_current integer,
    refrigerantcirculating integer,
    "time" timestamp without time zone,
    utilizationrate real,
    workingtime double precision NOT NULL,
    workingtime1 numeric(19,2),
    workingtime2 numeric(19,2),
    workingtime3 numeric(19,2),
    outdoorunit_id bigint
);


ALTER TABLE outdoorunitslog_history OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 20064)
-- Name: permissions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permissions (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp without time zone,
    name character varying(255),
    updatedate timestamp without time zone,
    updatedby character varying(255),
    url character varying(1024),
    isleftmenu boolean,
    isdel boolean
);


ALTER TABLE permissions OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 20070)
-- Name: permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permissions_id_seq OWNER TO postgres;

--
-- TOC entry 2785 (class 0 OID 0)
-- Dependencies: 261
-- Name: permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE permissions_id_seq OWNED BY permissions.id;


--
-- TOC entry 262 (class 1259 OID 20072)
-- Name: power_consumption_capacity; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE power_consumption_capacity (
    id bigint NOT NULL,
    co2emission double precision,
    outdoortemp real,
    roomtemp real,
    setpointtemp real,
    total_power_consumption double precision,
    indoorunit_id bigint,
    outdoorunit_id bigint,
    logtime timestamp without time zone,
    efficiency_cop double precision,
    efficiency_seer double precision,
    currentcapacity_heating double precision,
    currentcapacity_cooling double precision,
    totalcapacity_cooling double precision,
    totalcapacity_heating double precision
);


ALTER TABLE power_consumption_capacity OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 20075)
-- Name: power_consumption_capacity_daily; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE power_consumption_capacity_daily (
    id bigint NOT NULL,
    co2emission double precision,
    outdoortemp real,
    roomtemp real,
    setpointtemp real,
    total_power_consumption double precision,
    indoorunit_id bigint,
    outdoorunit_id bigint,
    efficiency_cop real,
    efficiency_seer real,
    currentcapacity_heating double precision,
    currentcapacity_cooling double precision,
    totalcapacity_heating double precision,
    totalcapacity_cooling double precision,
    logtime timestamp without time zone
);


ALTER TABLE power_consumption_capacity_daily OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 20078)
-- Name: power_consumption_capacity_daily_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE power_consumption_capacity_daily_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE power_consumption_capacity_daily_id_seq OWNER TO postgres;

--
-- TOC entry 2786 (class 0 OID 0)
-- Dependencies: 264
-- Name: power_consumption_capacity_daily_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_daily_id_seq OWNED BY power_consumption_capacity_daily.id;


--
-- TOC entry 265 (class 1259 OID 20080)
-- Name: power_consumption_capacity_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE power_consumption_capacity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE power_consumption_capacity_id_seq OWNER TO postgres;

--
-- TOC entry 2787 (class 0 OID 0)
-- Dependencies: 265
-- Name: power_consumption_capacity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_id_seq OWNED BY power_consumption_capacity.id;


--
-- TOC entry 266 (class 1259 OID 20082)
-- Name: power_consumption_capacity_monthly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE power_consumption_capacity_monthly (
    id bigint NOT NULL,
    co2emission double precision,
    month smallint NOT NULL,
    outdoortemp real,
    roomtemp real,
    setpointtemp real,
    total_power_consumption double precision,
    year smallint NOT NULL,
    indoorunit_id bigint,
    outdoorunit_id bigint,
    efficiency_cop real,
    efficiency_seer real,
    currentcapacity_heating double precision,
    currentcapacity_cooling double precision,
    totalcapacity_heating double precision,
    totalcapacity_cooling double precision,
    logtime timestamp without time zone
);


ALTER TABLE power_consumption_capacity_monthly OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 20085)
-- Name: power_consumption_capacity_monthly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE power_consumption_capacity_monthly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE power_consumption_capacity_monthly_id_seq OWNER TO postgres;

--
-- TOC entry 2788 (class 0 OID 0)
-- Dependencies: 267
-- Name: power_consumption_capacity_monthly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_monthly_id_seq OWNED BY power_consumption_capacity_monthly.id;


--
-- TOC entry 268 (class 1259 OID 20087)
-- Name: power_consumption_capacity_weekly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE power_consumption_capacity_weekly (
    id bigint NOT NULL,
    co2emission double precision,
    outdoortemp real,
    roomtemp real,
    setpointtemp real,
    total_power_consumption double precision,
    week smallint NOT NULL,
    year smallint NOT NULL,
    indoorunit_id bigint,
    outdoorunit_id bigint,
    efficiency_cop real,
    efficiency_seer real,
    currentcapacity_heating double precision,
    currentcapacity_cooling double precision,
    totalcapacity_heating double precision,
    totalcapacity_cooling double precision,
    logtime timestamp without time zone
);


ALTER TABLE power_consumption_capacity_weekly OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 20090)
-- Name: power_consumption_capacity_weekly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE power_consumption_capacity_weekly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE power_consumption_capacity_weekly_id_seq OWNER TO postgres;

--
-- TOC entry 2789 (class 0 OID 0)
-- Dependencies: 269
-- Name: power_consumption_capacity_weekly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_weekly_id_seq OWNED BY power_consumption_capacity_weekly.id;


--
-- TOC entry 270 (class 1259 OID 20092)
-- Name: power_consumption_capacity_yearly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE power_consumption_capacity_yearly (
    id bigint NOT NULL,
    co2emission double precision,
    outdoortemp real,
    roomtemp real,
    setpointtemp real,
    total_power_consumption double precision,
    year smallint NOT NULL,
    indoorunit_id bigint,
    outdoorunit_id bigint,
    efficiency_cop real,
    efficiency_seer real,
    currentcapacity_heating double precision,
    currentcapacity_cooling double precision,
    totalcapacity_heating double precision,
    totalcapacity_cooling double precision,
    logtime timestamp without time zone
);


ALTER TABLE power_consumption_capacity_yearly OWNER TO postgres;

--
-- TOC entry 271 (class 1259 OID 20095)
-- Name: power_consumption_capacity_yearly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE power_consumption_capacity_yearly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE power_consumption_capacity_yearly_id_seq OWNER TO postgres;

--
-- TOC entry 2790 (class 0 OID 0)
-- Dependencies: 271
-- Name: power_consumption_capacity_yearly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_yearly_id_seq OWNED BY power_consumption_capacity_yearly.id;


--
-- TOC entry 272 (class 1259 OID 20097)
-- Name: ratingmaster; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ratingmaster (
    id bigint NOT NULL,
    colorcode character varying(255),
    rating character varying(255),
    rating_range character varying(255)
);


ALTER TABLE ratingmaster OWNER TO postgres;

--
-- TOC entry 273 (class 1259 OID 20103)
-- Name: ratingmaster_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ratingmaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ratingmaster_id_seq OWNER TO postgres;

--
-- TOC entry 2791 (class 0 OID 0)
-- Dependencies: 273
-- Name: ratingmaster_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ratingmaster_id_seq OWNED BY ratingmaster.id;


--
-- TOC entry 274 (class 1259 OID 20105)
-- Name: rc_prohibition; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rc_prohibition (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp without time zone,
    ison smallint,
    rcprohibitfanspeed smallint NOT NULL,
    rcprohibitmode smallint NOT NULL,
    rcprohibitpwr smallint NOT NULL,
    rcprohibitsettemp smallint NOT NULL,
    rcprohibitvanepos smallint NOT NULL,
    updatedate timestamp without time zone,
    updatedby character varying(255),
    indoorunit_id bigint
);


ALTER TABLE rc_prohibition OWNER TO postgres;

--
-- TOC entry 275 (class 1259 OID 20111)
-- Name: rc_prohibition_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rc_prohibition_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rc_prohibition_id_seq OWNER TO postgres;

--
-- TOC entry 2792 (class 0 OID 0)
-- Dependencies: 275
-- Name: rc_prohibition_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rc_prohibition_id_seq OWNED BY rc_prohibition.id;


--
-- TOC entry 301 (class 1259 OID 23119)
-- Name: rcoperation_log_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rcoperation_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rcoperation_log_id_seq OWNER TO postgres;

--
-- TOC entry 302 (class 1259 OID 23121)
-- Name: rcoperation_log; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rcoperation_log (
    id bigint DEFAULT nextval('rcoperation_log_id_seq'::regclass) NOT NULL,
    creationdate timestamp without time zone,
    requestedtime timestamp without time zone,
    indoorunit_id bigint,
    user_id bigint,
    success boolean,
    airconmode character varying(16),
    temperature character varying(16),
    fanspeed character varying(16),
    flapmode character varying(16),
    powerstatus character varying(16),
    energysaving character varying(16),
    prohibitionpowerstatus character varying(16),
    prohibitonmode character varying(16),
    prohibitionfanspeed character varying(16),
    prohibitionwindriection character varying(16),
    prohibitionsettemp character varying(16)
);


ALTER TABLE rcoperation_log OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 20113)
-- Name: rolepermissions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rolepermissions (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp without time zone,
    permission_value integer,
    updatedate timestamp without time zone,
    updatedby character varying(255),
    permission_id bigint,
    roles_id bigint
);


ALTER TABLE rolepermissions OWNER TO postgres;

--
-- TOC entry 277 (class 1259 OID 20119)
-- Name: rolepermissions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rolepermissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rolepermissions_id_seq OWNER TO postgres;

--
-- TOC entry 2793 (class 0 OID 0)
-- Dependencies: 277
-- Name: rolepermissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rolepermissions_id_seq OWNED BY rolepermissions.id;


--
-- TOC entry 278 (class 1259 OID 20121)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE roles (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp without time zone,
    name character varying(255),
    updatedate timestamp without time zone,
    updatedby character varying(255),
    isdel boolean
);


ALTER TABLE roles OWNER TO postgres;

--
-- TOC entry 279 (class 1259 OID 20127)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE roles_id_seq OWNER TO postgres;

--
-- TOC entry 2794 (class 0 OID 0)
-- Dependencies: 279
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE roles_id_seq OWNED BY roles.id;


--
-- TOC entry 280 (class 1259 OID 20129)
-- Name: session; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE session (
    id bigint NOT NULL,
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    email character varying(45) NOT NULL,
    status smallint NOT NULL,
    uniquesessionid character varying(256),
    updatedate timestamp without time zone,
    updatedby character varying(45)
);


ALTER TABLE session OWNER TO postgres;

--
-- TOC entry 281 (class 1259 OID 20132)
-- Name: session_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE session_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE session_id_seq OWNER TO postgres;

--
-- TOC entry 2795 (class 0 OID 0)
-- Dependencies: 281
-- Name: session_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE session_id_seq OWNED BY session.id;


--
-- TOC entry 282 (class 1259 OID 20146)
-- Name: temp_groupunitdata; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE temp_groupunitdata (
    id bigint NOT NULL,
    alaramcount numeric(19,2),
    group_id numeric(19,2),
    indoorunit numeric(19,2),
    indoorunitcount numeric(19,2),
    outdoorunit numeric(19,2),
    outdoorunitcount numeric(19,2),
    path character varying(255)
);


ALTER TABLE temp_groupunitdata OWNER TO postgres;

--
-- TOC entry 283 (class 1259 OID 20149)
-- Name: temp_groupunitdata_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE temp_groupunitdata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE temp_groupunitdata_id_seq OWNER TO postgres;

--
-- TOC entry 2796 (class 0 OID 0)
-- Dependencies: 283
-- Name: temp_groupunitdata_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE temp_groupunitdata_id_seq OWNED BY temp_groupunitdata.id;


--
-- TOC entry 284 (class 1259 OID 20157)
-- Name: tempgroupdata; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tempgroupdata (
    path character varying(45) DEFAULT NULL::character varying,
    group_id bigint,
    alarmindoorunitcount integer,
    alarmoutdoorunitcount integer,
    indoorunitcount integer,
    outdoorunitcount integer,
    totalalarmcount integer,
    severity character varying,
    groupname character varying(200) DEFAULT NULL::character varying,
    co2emission double precision,
    efficiency_seer double precision
);


ALTER TABLE tempgroupdata OWNER TO postgres;

--
-- TOC entry 285 (class 1259 OID 20171)
-- Name: timeline; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE timeline (
    id bigint NOT NULL,
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    state character varying(45),
    updatedate timestamp without time zone,
    updatedby character varying(45),
    adapter_id bigint
);


ALTER TABLE timeline OWNER TO postgres;

--
-- TOC entry 286 (class 1259 OID 20174)
-- Name: timeline_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE timeline_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE timeline_id_seq OWNER TO postgres;

--
-- TOC entry 2797 (class 0 OID 0)
-- Dependencies: 286
-- Name: timeline_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE timeline_id_seq OWNED BY timeline.id;


--
-- TOC entry 287 (class 1259 OID 20176)
-- Name: timezonemaster; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE timezonemaster (
    id integer NOT NULL,
    timezone character varying(255)
);


ALTER TABLE timezonemaster OWNER TO postgres;

--
-- TOC entry 288 (class 1259 OID 20179)
-- Name: timezonemaster_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE timezonemaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE timezonemaster_id_seq OWNER TO postgres;

--
-- TOC entry 2798 (class 0 OID 0)
-- Dependencies: 288
-- Name: timezonemaster_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE timezonemaster_id_seq OWNED BY timezonemaster.id;


--
-- TOC entry 289 (class 1259 OID 20181)
-- Name: user_notification_settings; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_notification_settings (
    id integer NOT NULL,
    notification_settings_id integer,
    user_id integer
);


ALTER TABLE user_notification_settings OWNER TO postgres;

--
-- TOC entry 290 (class 1259 OID 20184)
-- Name: user_notification_settings_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_notification_settings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_notification_settings_id_seq OWNER TO postgres;

--
-- TOC entry 2799 (class 0 OID 0)
-- Dependencies: 290
-- Name: user_notification_settings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_notification_settings_id_seq OWNED BY user_notification_settings.id;


--
-- TOC entry 291 (class 1259 OID 20186)
-- Name: useraudit; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE useraudit (
    id bigint NOT NULL,
    auditaction character varying(255),
    createdby character varying(255),
    creationdate timestamp without time zone,
    updatedate timestamp without time zone,
    updatedby character varying(255),
    user_id bigint
);


ALTER TABLE useraudit OWNER TO postgres;

--
-- TOC entry 292 (class 1259 OID 20192)
-- Name: useraudit_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE useraudit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE useraudit_id_seq OWNER TO postgres;

--
-- TOC entry 2800 (class 0 OID 0)
-- Dependencies: 292
-- Name: useraudit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE useraudit_id_seq OWNED BY useraudit.id;


--
-- TOC entry 293 (class 1259 OID 20194)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    id bigint NOT NULL,
    confirmationtoken character varying(255),
    confirmed_date timestamp without time zone,
    confirmedsent_date timestamp without time zone,
    country character varying(255),
    createdby character varying(255),
    creationdate timestamp without time zone,
    email character varying(255),
    encryptedpassword character varying(255),
    failedattempt integer NOT NULL,
    firstname character varying(255),
    forgottoken character varying(255),
    isverified smallint NOT NULL,
    lastactivity_date timestamp without time zone,
    lastname character varying(255),
    locked_date timestamp without time zone,
    passwordchange_date timestamp without time zone,
    rembembercreated_date timestamp without time zone,
    resetpassword_sent_date timestamp without time zone,
    resetpasswordtoken character varying(255),
    resettoken character varying(255),
    unlocktoken character varying(255),
    updatedate timestamp without time zone,
    updatedby character varying(255),
    roles_id bigint,
    timezone_id integer,
    isdel boolean,
    telephone character varying(16),
    department character varying(8),
    lastvisitedgroups text
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 294 (class 1259 OID 20200)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- TOC entry 2801 (class 0 OID 0)
-- Dependencies: 294
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 295 (class 1259 OID 20214)
-- Name: vrfparameter_statistics; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vrfparameter_statistics (
    id bigint NOT NULL,
    compressor1workingtime numeric(19,2),
    compressor2workingtime numeric(19,2),
    compressor3workingtime numeric(19,2),
    createdby character varying(255),
    creationdate timestamp without time zone,
    current2 integer NOT NULL,
    current3 integer NOT NULL,
    demand integer NOT NULL,
    fanmode integer NOT NULL,
    fanrotation integer NOT NULL,
    fixedspeedcomp1 integer NOT NULL,
    fixedspeedcomp2 integer NOT NULL,
    gastempofoutdoorcoil1 integer NOT NULL,
    gastempofoutdoorcoil2 integer NOT NULL,
    highpresure integer NOT NULL,
    inlettempoutdoorunit integer NOT NULL,
    invcompactualhz integer NOT NULL,
    invcomptargethz integer NOT NULL,
    invprimarycurrent integer NOT NULL,
    invsecondarycurrent integer NOT NULL,
    liquidtempofoutdoorcoil1 integer NOT NULL,
    liquidtempofoutdoorcoil2 integer NOT NULL,
    lowpresure integer NOT NULL,
    mov1pulse integer NOT NULL,
    mov2pulse integer NOT NULL,
    mov4pulse integer NOT NULL,
    outdoorunitstatus integer NOT NULL,
    saturatedtemphighpress integer NOT NULL,
    saturatedtemplowpress integer NOT NULL,
    scg integer NOT NULL,
    tempcompressordischarge1 integer NOT NULL,
    tempcompressordischarge2 integer NOT NULL,
    tempcompressordischarge3 integer NOT NULL,
    tempoil1 integer NOT NULL,
    tempoil2 integer NOT NULL,
    temppil3 integer NOT NULL,
    updatedate timestamp without time zone,
    updatedby character varying(255),
    outdoorunit_id bigint,
    logtime timestamp without time zone
);


ALTER TABLE vrfparameter_statistics OWNER TO postgres;

--
-- TOC entry 296 (class 1259 OID 20220)
-- Name: vrfparameter_statistics_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vrfparameter_statistics_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vrfparameter_statistics_id_seq OWNER TO postgres;

--
-- TOC entry 2802 (class 0 OID 0)
-- Dependencies: 296
-- Name: vrfparameter_statistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE vrfparameter_statistics_id_seq OWNED BY vrfparameter_statistics.id;


--
-- TOC entry 297 (class 1259 OID 20222)
-- Name: winddirection_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE winddirection_master (
    id bigint NOT NULL,
    winddirectionname character varying(255)
);


ALTER TABLE winddirection_master OWNER TO postgres;

--
-- TOC entry 298 (class 1259 OID 20225)
-- Name: winddirection_master_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE winddirection_master_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE winddirection_master_id_seq OWNER TO postgres;

--
-- TOC entry 2803 (class 0 OID 0)
-- Dependencies: 298
-- Name: winddirection_master_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE winddirection_master_id_seq OWNED BY winddirection_master.id;


--
-- TOC entry 2382 (class 2604 OID 20227)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY adapters ALTER COLUMN id SET DEFAULT nextval('adapters_id_seq'::regclass);


--
-- TOC entry 2384 (class 2604 OID 20229)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY companies ALTER COLUMN id SET DEFAULT nextval('companies_id_seq'::regclass);


--
-- TOC entry 2390 (class 2604 OID 20230)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY efficiency_rating ALTER COLUMN id SET DEFAULT nextval('efficiency_rating_id_seq'::regclass);


--
-- TOC entry 2391 (class 2604 OID 20231)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fanspeed_master ALTER COLUMN id SET DEFAULT nextval('fanspeed_master_id_seq'::regclass);


--
-- TOC entry 2392 (class 2604 OID 20232)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_id_seq'::regclass);


--
-- TOC entry 2393 (class 2604 OID 20233)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_daily ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_daily_id_seq'::regclass);


--
-- TOC entry 2394 (class 2604 OID 20234)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_monthly ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_monthly_id_seq'::regclass);


--
-- TOC entry 2395 (class 2604 OID 20235)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_weekly ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_weekly_id_seq'::regclass);


--
-- TOC entry 2396 (class 2604 OID 20236)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_yearly ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_yearly_id_seq'::regclass);


--
-- TOC entry 2397 (class 2604 OID 20237)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ghpparameter_statistics ALTER COLUMN id SET DEFAULT nextval('ghpparameter_statistics_id_seq'::regclass);


--
-- TOC entry 2398 (class 2604 OID 20238)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups ALTER COLUMN id SET DEFAULT nextval('groups_id_seq'::regclass);


--
-- TOC entry 2399 (class 2604 OID 20239)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits ALTER COLUMN id SET DEFAULT nextval('indoorunits_id_seq'::regclass);


--
-- TOC entry 2400 (class 2604 OID 20240)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog ALTER COLUMN id SET DEFAULT nextval('indoorunitslog_id_seq'::regclass);


--
-- TOC entry 2401 (class 2604 OID 20241)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history ALTER COLUMN id SET DEFAULT nextval('indoorunitslog_history_id_seq'::regclass);


--
-- TOC entry 2402 (class 2604 OID 20242)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_id_seq'::regclass);


--
-- TOC entry 2403 (class 2604 OID 20243)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_daily ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_daily_id_seq'::regclass);


--
-- TOC entry 2404 (class 2604 OID 20244)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_monthly ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_monthly_id_seq'::regclass);


--
-- TOC entry 2405 (class 2604 OID 20245)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_weekly ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_weekly_id_seq'::regclass);


--
-- TOC entry 2406 (class 2604 OID 20246)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_yearly ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_yearly_id_seq'::regclass);


--
-- TOC entry 2407 (class 2604 OID 20247)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY metaindoorunits ALTER COLUMN id SET DEFAULT nextval('metaindoorunits_id_seq'::regclass);


--
-- TOC entry 2408 (class 2604 OID 20248)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY metaoutdoorunits ALTER COLUMN id SET DEFAULT nextval('metaoutdoorunits_id_seq'::regclass);


--
-- TOC entry 2409 (class 2604 OID 20249)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modemaster ALTER COLUMN id SET DEFAULT nextval('modemaster_id_seq'::regclass);


--
-- TOC entry 2383 (class 2604 OID 20250)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationlog ALTER COLUMN id SET DEFAULT nextval('alarmstatistics_id_seq'::regclass);


--
-- TOC entry 2410 (class 2604 OID 20251)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationsettings ALTER COLUMN id SET DEFAULT nextval('notificationsettings_id_seq'::regclass);


--
-- TOC entry 2411 (class 2604 OID 20252)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationtype_master ALTER COLUMN id SET DEFAULT nextval('notificationtype_master_id_seq'::regclass);


--
-- TOC entry 2412 (class 2604 OID 20253)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunits ALTER COLUMN id SET DEFAULT nextval('outdoorunits_id_seq'::regclass);


--
-- TOC entry 2413 (class 2604 OID 20254)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunitslog ALTER COLUMN id SET DEFAULT nextval('outdoorunitstatistics_id_seq'::regclass);


--
-- TOC entry 2415 (class 2604 OID 20255)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY permissions ALTER COLUMN id SET DEFAULT nextval('permissions_id_seq'::regclass);


--
-- TOC entry 2416 (class 2604 OID 20256)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_id_seq'::regclass);


--
-- TOC entry 2417 (class 2604 OID 20257)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_daily ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_daily_id_seq'::regclass);


--
-- TOC entry 2418 (class 2604 OID 20258)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_monthly ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_monthly_id_seq'::regclass);


--
-- TOC entry 2419 (class 2604 OID 20259)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_weekly ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_weekly_id_seq'::regclass);


--
-- TOC entry 2420 (class 2604 OID 20260)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_yearly ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_yearly_id_seq'::regclass);


--
-- TOC entry 2421 (class 2604 OID 20261)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ratingmaster ALTER COLUMN id SET DEFAULT nextval('ratingmaster_id_seq'::regclass);


--
-- TOC entry 2422 (class 2604 OID 20262)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rc_prohibition ALTER COLUMN id SET DEFAULT nextval('rc_prohibition_id_seq'::regclass);


--
-- TOC entry 2388 (class 2604 OID 20264)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcoperation_master ALTER COLUMN id SET DEFAULT nextval('deviceattribute_master_id_seq'::regclass);


--
-- TOC entry 2389 (class 2604 OID 20265)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcuser_action ALTER COLUMN id SET DEFAULT nextval('devicecommand_id_seq'::regclass);


--
-- TOC entry 2423 (class 2604 OID 20266)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolepermissions ALTER COLUMN id SET DEFAULT nextval('rolepermissions_id_seq'::regclass);


--
-- TOC entry 2424 (class 2604 OID 20267)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY roles ALTER COLUMN id SET DEFAULT nextval('roles_id_seq'::regclass);


--
-- TOC entry 2425 (class 2604 OID 20268)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY session ALTER COLUMN id SET DEFAULT nextval('session_id_seq'::regclass);


--
-- TOC entry 2426 (class 2604 OID 20269)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY temp_groupunitdata ALTER COLUMN id SET DEFAULT nextval('temp_groupunitdata_id_seq'::regclass);


--
-- TOC entry 2429 (class 2604 OID 20270)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY timeline ALTER COLUMN id SET DEFAULT nextval('timeline_id_seq'::regclass);


--
-- TOC entry 2430 (class 2604 OID 20271)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY timezonemaster ALTER COLUMN id SET DEFAULT nextval('timezonemaster_id_seq'::regclass);


--
-- TOC entry 2431 (class 2604 OID 20272)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_notification_settings ALTER COLUMN id SET DEFAULT nextval('user_notification_settings_id_seq'::regclass);


--
-- TOC entry 2432 (class 2604 OID 20273)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraudit ALTER COLUMN id SET DEFAULT nextval('useraudit_id_seq'::regclass);


--
-- TOC entry 2433 (class 2604 OID 20274)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 2434 (class 2604 OID 20275)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vrfparameter_statistics ALTER COLUMN id SET DEFAULT nextval('vrfparameter_statistics_id_seq'::regclass);


--
-- TOC entry 2435 (class 2604 OID 20276)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY winddirection_master ALTER COLUMN id SET DEFAULT nextval('winddirection_master_id_seq'::regclass);


--
-- TOC entry 2438 (class 2606 OID 20278)
-- Name: adapters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY adapters
    ADD CONSTRAINT adapters_pkey PRIMARY KEY (id);


--
-- TOC entry 2440 (class 2606 OID 20280)
-- Name: alarmstatistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notificationlog
    ADD CONSTRAINT alarmstatistics_pkey PRIMARY KEY (id);


--
-- TOC entry 2442 (class 2606 OID 20284)
-- Name: companies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY companies
    ADD CONSTRAINT companies_pkey PRIMARY KEY (id);


--
-- TOC entry 2444 (class 2606 OID 20286)
-- Name: companiesusers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY companiesusers
    ADD CONSTRAINT companiesusers_pkey PRIMARY KEY (company_id, user_id, group_id);


--
-- TOC entry 2446 (class 2606 OID 20288)
-- Name: deviceattribute_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rcoperation_master
    ADD CONSTRAINT deviceattribute_master_pkey PRIMARY KEY (id);


--
-- TOC entry 2448 (class 2606 OID 20290)
-- Name: devicecommand_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rcuser_action
    ADD CONSTRAINT devicecommand_pkey PRIMARY KEY (id);


--
-- TOC entry 2475 (class 2606 OID 20294)
-- Name: dist_id_groups_uniqueid_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT dist_id_groups_uniqueid_key UNIQUE (uniqueid);


--
-- TOC entry 2450 (class 2606 OID 20296)
-- Name: efficiencyrating_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY efficiency_rating
    ADD CONSTRAINT efficiencyrating_pk PRIMARY KEY (id);


--
-- TOC entry 2457 (class 2606 OID 20298)
-- Name: fanspeed_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fanspeed_master
    ADD CONSTRAINT fanspeed_master_pkey PRIMARY KEY (id);


--
-- TOC entry 2461 (class 2606 OID 20300)
-- Name: gasheatmeter_data_daily_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data_daily
    ADD CONSTRAINT gasheatmeter_data_daily_pkey PRIMARY KEY (id);


--
-- TOC entry 2463 (class 2606 OID 20302)
-- Name: gasheatmeter_data_monthly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data_monthly
    ADD CONSTRAINT gasheatmeter_data_monthly_pkey PRIMARY KEY (id);


--
-- TOC entry 2459 (class 2606 OID 20304)
-- Name: gasheatmeter_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data
    ADD CONSTRAINT gasheatmeter_data_pkey PRIMARY KEY (id);


--
-- TOC entry 2465 (class 2606 OID 20306)
-- Name: gasheatmeter_data_weekly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data_weekly
    ADD CONSTRAINT gasheatmeter_data_weekly_pkey PRIMARY KEY (id);


--
-- TOC entry 2467 (class 2606 OID 20308)
-- Name: gasheatmeter_data_yearly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data_yearly
    ADD CONSTRAINT gasheatmeter_data_yearly_pkey PRIMARY KEY (id);


--
-- TOC entry 2469 (class 2606 OID 20310)
-- Name: ghpparameter_statistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ghpparameter_statistics
    ADD CONSTRAINT ghpparameter_statistics_pkey PRIMARY KEY (id);


--
-- TOC entry 2471 (class 2606 OID 20312)
-- Name: group_level_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY group_level
    ADD CONSTRAINT group_level_pk PRIMARY KEY (id);


--
-- TOC entry 2478 (class 2606 OID 20314)
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2481 (class 2606 OID 20316)
-- Name: groupscriteria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groupscriteria
    ADD CONSTRAINT groupscriteria_pkey PRIMARY KEY (id);


--
-- TOC entry 2483 (class 2606 OID 20318)
-- Name: idx_indoorunits; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT idx_indoorunits UNIQUE (parent_id);


--
-- TOC entry 2486 (class 2606 OID 20320)
-- Name: indoorunits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT indoorunits_pkey PRIMARY KEY (id);


--
-- TOC entry 2490 (class 2606 OID 20322)
-- Name: indoorunitslog_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT indoorunitslog_history_pkey PRIMARY KEY (id);


--
-- TOC entry 2488 (class 2606 OID 20324)
-- Name: indoorunitslog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT indoorunitslog_pkey PRIMARY KEY (id);


--
-- TOC entry 2494 (class 2606 OID 20326)
-- Name: indoorunitstatistics_daily_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics_daily
    ADD CONSTRAINT indoorunitstatistics_daily_pkey PRIMARY KEY (id);


--
-- TOC entry 2496 (class 2606 OID 20328)
-- Name: indoorunitstatistics_monthly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics_monthly
    ADD CONSTRAINT indoorunitstatistics_monthly_pkey PRIMARY KEY (id);


--
-- TOC entry 2492 (class 2606 OID 20330)
-- Name: indoorunitstatistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics
    ADD CONSTRAINT indoorunitstatistics_pkey PRIMARY KEY (id);


--
-- TOC entry 2498 (class 2606 OID 20332)
-- Name: indoorunitstatistics_weekly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics_weekly
    ADD CONSTRAINT indoorunitstatistics_weekly_pkey PRIMARY KEY (id);


--
-- TOC entry 2500 (class 2606 OID 20334)
-- Name: indoorunitstatistics_yearly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics_yearly
    ADD CONSTRAINT indoorunitstatistics_yearly_pkey PRIMARY KEY (id);


--
-- TOC entry 2559 (class 2606 OID 21051)
-- Name: job_tracker_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY job_tracker
    ADD CONSTRAINT job_tracker_pkey PRIMARY KEY (jobname, lastexecutiontime);


--
-- TOC entry 2502 (class 2606 OID 20336)
-- Name: metaindoorunits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY metaindoorunits
    ADD CONSTRAINT metaindoorunits_pkey PRIMARY KEY (id);


--
-- TOC entry 2504 (class 2606 OID 20338)
-- Name: metaoutdoorunits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY metaoutdoorunits
    ADD CONSTRAINT metaoutdoorunits_pkey PRIMARY KEY (id);


--
-- TOC entry 2506 (class 2606 OID 20340)
-- Name: modemaster_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY modemaster
    ADD CONSTRAINT modemaster_pkey PRIMARY KEY (id);


--
-- TOC entry 2512 (class 2606 OID 20342)
-- Name: outdoorunitparameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitparameters
    ADD CONSTRAINT outdoorunitparameters_pkey PRIMARY KEY (id);


--
-- TOC entry 2514 (class 2606 OID 20344)
-- Name: outdoorunits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT outdoorunits_pkey PRIMARY KEY (id);


--
-- TOC entry 2518 (class 2606 OID 20346)
-- Name: outdoorunitstatistics_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitslog_history
    ADD CONSTRAINT outdoorunitstatistics_history_pkey PRIMARY KEY (id);


--
-- TOC entry 2516 (class 2606 OID 20348)
-- Name: outdoorunitstatistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitslog
    ADD CONSTRAINT outdoorunitstatistics_pkey PRIMARY KEY (id);


--
-- TOC entry 2520 (class 2606 OID 20350)
-- Name: permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (id);


--
-- TOC entry 2453 (class 2606 OID 20352)
-- Name: pk_errorcode_master; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY errorcode_master
    ADD CONSTRAINT pk_errorcode_master PRIMARY KEY (id);


--
-- TOC entry 2455 (class 2606 OID 20354)
-- Name: pk_errorgroup ; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY errorgroup
    ADD CONSTRAINT "pk_errorgroup " PRIMARY KEY (errorgroupid);


--
-- TOC entry 2473 (class 2606 OID 20356)
-- Name: pk_groupcategory; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groupcategory
    ADD CONSTRAINT pk_groupcategory PRIMARY KEY (groupcategoryid);


--
-- TOC entry 2510 (class 2606 OID 20358)
-- Name: pk_notification_type_master; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notificationtype_master
    ADD CONSTRAINT pk_notification_type_master PRIMARY KEY (id);


--
-- TOC entry 2508 (class 2606 OID 20360)
-- Name: pk_notificationsettings; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notificationsettings
    ADD CONSTRAINT pk_notificationsettings PRIMARY KEY (id);


--
-- TOC entry 2549 (class 2606 OID 20362)
-- Name: pk_user_notification_settings; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_notification_settings
    ADD CONSTRAINT pk_user_notification_settings PRIMARY KEY (id);


--
-- TOC entry 2524 (class 2606 OID 20364)
-- Name: power_consumption_capacity_daily_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity_daily
    ADD CONSTRAINT power_consumption_capacity_daily_pkey PRIMARY KEY (id);


--
-- TOC entry 2526 (class 2606 OID 20366)
-- Name: power_consumption_capacity_monthly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity_monthly
    ADD CONSTRAINT power_consumption_capacity_monthly_pkey PRIMARY KEY (id);


--
-- TOC entry 2522 (class 2606 OID 20368)
-- Name: power_consumption_capacity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity
    ADD CONSTRAINT power_consumption_capacity_pkey PRIMARY KEY (id);


--
-- TOC entry 2528 (class 2606 OID 20370)
-- Name: power_consumption_capacity_weekly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity_weekly
    ADD CONSTRAINT power_consumption_capacity_weekly_pkey PRIMARY KEY (id);


--
-- TOC entry 2530 (class 2606 OID 20372)
-- Name: power_consumption_capacity_yearly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity_yearly
    ADD CONSTRAINT power_consumption_capacity_yearly_pkey PRIMARY KEY (id);


--
-- TOC entry 2532 (class 2606 OID 20374)
-- Name: ratingmaster_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ratingmaster
    ADD CONSTRAINT ratingmaster_pkey PRIMARY KEY (id);


--
-- TOC entry 2534 (class 2606 OID 20376)
-- Name: rc_prohibition_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rc_prohibition
    ADD CONSTRAINT rc_prohibition_pkey PRIMARY KEY (id);


--
-- TOC entry 2561 (class 2606 OID 23126)
-- Name: rcoperation_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rcoperation_log
    ADD CONSTRAINT rcoperation_log_pkey PRIMARY KEY (id);


--
-- TOC entry 2536 (class 2606 OID 20378)
-- Name: rolepermissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rolepermissions
    ADD CONSTRAINT rolepermissions_pkey PRIMARY KEY (id);


--
-- TOC entry 2538 (class 2606 OID 20380)
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 2540 (class 2606 OID 20382)
-- Name: session_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY session
    ADD CONSTRAINT session_pkey PRIMARY KEY (id);


--
-- TOC entry 2542 (class 2606 OID 20386)
-- Name: temp_groupunitdata_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY temp_groupunitdata
    ADD CONSTRAINT temp_groupunitdata_pkey PRIMARY KEY (id);


--
-- TOC entry 2544 (class 2606 OID 20388)
-- Name: timeline_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY timeline
    ADD CONSTRAINT timeline_pkey PRIMARY KEY (id);


--
-- TOC entry 2546 (class 2606 OID 20390)
-- Name: timezonemaster_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY timezonemaster
    ADD CONSTRAINT timezonemaster_pkey PRIMARY KEY (id);


--
-- TOC entry 2551 (class 2606 OID 20392)
-- Name: useraudit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY useraudit
    ADD CONSTRAINT useraudit_pkey PRIMARY KEY (id);


--
-- TOC entry 2553 (class 2606 OID 20394)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2555 (class 2606 OID 20396)
-- Name: vrfparameter_statistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vrfparameter_statistics
    ADD CONSTRAINT vrfparameter_statistics_pkey PRIMARY KEY (id);


--
-- TOC entry 2557 (class 2606 OID 20398)
-- Name: winddirection_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY winddirection_master
    ADD CONSTRAINT winddirection_master_pkey PRIMARY KEY (id);


--
-- TOC entry 2476 (class 1259 OID 20399)
-- Name: fki_group_level_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_group_level_fk ON groups USING btree (group_level_id);


--
-- TOC entry 2451 (class 1259 OID 20400)
-- Name: idx_errorcode_master; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_errorcode_master ON errorcode_master USING btree (code);


--
-- TOC entry 2479 (class 1259 OID 20401)
-- Name: idx_groups; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_groups ON groups USING btree (groupcategoryid);


--
-- TOC entry 2484 (class 1259 OID 20402)
-- Name: idx_indoorunits_0; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_indoorunits_0 ON indoorunits USING btree (group_id);


--
-- TOC entry 2547 (class 1259 OID 20404)
-- Name: idx_user_notification_settings; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_user_notification_settings ON user_notification_settings USING btree (notification_settings_id);


--
-- TOC entry 2598 (class 2606 OID 20405)
-- Name: fk_1lgod7u59j9xc5ucnup1cv3j5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_daily
    ADD CONSTRAINT fk_1lgod7u59j9xc5ucnup1cv3j5 FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2604 (class 2606 OID 20410)
-- Name: fk_35mp1eagi1b27br70uuwtsm3n; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT fk_35mp1eagi1b27br70uuwtsm3n FOREIGN KEY (adapters_id) REFERENCES adapters(id);


--
-- TOC entry 2618 (class 2606 OID 20415)
-- Name: fk_3es4q0x8gw88peunitrgnf0tr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rc_prohibition
    ADD CONSTRAINT fk_3es4q0x8gw88peunitrgnf0tr FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2619 (class 2606 OID 20420)
-- Name: fk_3yiv897i9xx5e2s1k21j80xa3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolepermissions
    ADD CONSTRAINT fk_3yiv897i9xx5e2s1k21j80xa3 FOREIGN KEY (permission_id) REFERENCES permissions(id);


--
-- TOC entry 2584 (class 2606 OID 20425)
-- Name: fk_423uw67rudujfavow17g85m5d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_423uw67rudujfavow17g85m5d FOREIGN KEY (adapters_id) REFERENCES adapters(id);


--
-- TOC entry 2600 (class 2606 OID 20430)
-- Name: fk_4r8rxmkm95idra6k5cyq3y5en; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_weekly
    ADD CONSTRAINT fk_4r8rxmkm95idra6k5cyq3y5en FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2616 (class 2606 OID 20435)
-- Name: fk_5jfxxr4mpxn07h1wcb441f7k8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_yearly
    ADD CONSTRAINT fk_5jfxxr4mpxn07h1wcb441f7k8 FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2589 (class 2606 OID 20440)
-- Name: fk_5kapln94acqioo2tk1sxgcaqu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT fk_5kapln94acqioo2tk1sxgcaqu FOREIGN KEY (acmode) REFERENCES modemaster(id);


--
-- TOC entry 2578 (class 2606 OID 20445)
-- Name: fk_67jfffsbqh10lqnkun0vewesi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_67jfffsbqh10lqnkun0vewesi FOREIGN KEY (groupcriteria_id) REFERENCES groupscriteria(id);


--
-- TOC entry 2624 (class 2606 OID 20450)
-- Name: fk_6dw2cqm1k46b3o92x56kwok81; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraudit
    ADD CONSTRAINT fk_6dw2cqm1k46b3o92x56kwok81 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2614 (class 2606 OID 20455)
-- Name: fk_7tpdq1b851rijkpcoci2cawqq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_weekly
    ADD CONSTRAINT fk_7tpdq1b851rijkpcoci2cawqq FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2617 (class 2606 OID 20460)
-- Name: fk_8wj808ce87n1ut37t73j2g7ig; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_yearly
    ADD CONSTRAINT fk_8wj808ce87n1ut37t73j2g7ig FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2579 (class 2606 OID 20465)
-- Name: fk_9mri5y8e3b5qh2kr2iu3xccq1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_9mri5y8e3b5qh2kr2iu3xccq1 FOREIGN KEY (parentid) REFERENCES groups(id);


--
-- TOC entry 2590 (class 2606 OID 20470)
-- Name: fk_9pk8fu4ar85q9krtvn9605ddv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT fk_9pk8fu4ar85q9krtvn9605ddv FOREIGN KEY (flapmode) REFERENCES winddirection_master(id);


--
-- TOC entry 2605 (class 2606 OID 20475)
-- Name: fk_9sba3v0r96cp2hi05uxsliicc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT fk_9sba3v0r96cp2hi05uxsliicc FOREIGN KEY (metaoutdoorunit_id) REFERENCES metaoutdoorunits(id);


--
-- TOC entry 2627 (class 2606 OID 20480)
-- Name: fk_a013uhfyflp56cwi6skf065yt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vrfparameter_statistics
    ADD CONSTRAINT fk_a013uhfyflp56cwi6skf065yt FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2621 (class 2606 OID 20485)
-- Name: fk_aut17b77fbvtfyos5s6f5x5k4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY timeline
    ADD CONSTRAINT fk_aut17b77fbvtfyos5s6f5x5k4 FOREIGN KEY (adapter_id) REFERENCES adapters(id);


--
-- TOC entry 2610 (class 2606 OID 20490)
-- Name: fk_bro92n1nho9v4hgw877ajnegg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_daily
    ADD CONSTRAINT fk_bro92n1nho9v4hgw877ajnegg FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2591 (class 2606 OID 20495)
-- Name: fk_c4q4c6bo7rv2fg34h2pto84n6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT fk_c4q4c6bo7rv2fg34h2pto84n6 FOREIGN KEY (fanspeed) REFERENCES fanspeed_master(id);


--
-- TOC entry 2606 (class 2606 OID 20500)
-- Name: fk_c8b26rxe135l1kl48g3gk46f0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunitslog
    ADD CONSTRAINT fk_c8b26rxe135l1kl48g3gk46f0 FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2567 (class 2606 OID 20505)
-- Name: fk_companies_users_company; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY companiesusers
    ADD CONSTRAINT fk_companies_users_company FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- TOC entry 2568 (class 2606 OID 20510)
-- Name: fk_companies_users_groups; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY companiesusers
    ADD CONSTRAINT fk_companies_users_groups FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- TOC entry 2569 (class 2606 OID 20515)
-- Name: fk_companies_users_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY companiesusers
    ADD CONSTRAINT fk_companies_users_users FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2612 (class 2606 OID 20520)
-- Name: fk_dir1mg91h73bg90akwkx3c0g1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_monthly
    ADD CONSTRAINT fk_dir1mg91h73bg90akwkx3c0g1 FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2564 (class 2606 OID 20525)
-- Name: fk_e862s3rospv9agg9cqyat87oq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationlog
    ADD CONSTRAINT fk_e862s3rospv9agg9cqyat87oq FOREIGN KEY (adapterid) REFERENCES adapters(id);


--
-- TOC entry 2571 (class 2606 OID 20842)
-- Name: fk_errorcode_master_notificationtype_master; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY errorcode_master
    ADD CONSTRAINT fk_errorcode_master_notificationtype_master FOREIGN KEY (notificationtype_id) REFERENCES notificationtype_master(id);


--
-- TOC entry 2585 (class 2606 OID 20535)
-- Name: fk_f3ju22jh3fbrdrp5buut13ipr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_f3ju22jh3fbrdrp5buut13ipr FOREIGN KEY (metaindoorunit_id) REFERENCES metaindoorunits(id);


--
-- TOC entry 2575 (class 2606 OID 20540)
-- Name: fk_fn2ygk9pxp8opvuy0s25cn70l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_weekly
    ADD CONSTRAINT fk_fn2ygk9pxp8opvuy0s25cn70l FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2572 (class 2606 OID 20545)
-- Name: fk_gasheatmeter_data_outdoorunits; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data
    ADD CONSTRAINT fk_gasheatmeter_data_outdoorunits FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2573 (class 2606 OID 20550)
-- Name: fk_gl9jjp4lsertxldusofyp2hm3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_daily
    ADD CONSTRAINT fk_gl9jjp4lsertxldusofyp2hm3 FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2580 (class 2606 OID 20555)
-- Name: fk_groups_groupcategory; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_groups_groupcategory FOREIGN KEY (groupcategoryid) REFERENCES groupcategory(groupcategoryid);


--
-- TOC entry 2583 (class 2606 OID 21042)
-- Name: fk_groups_timezone; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_groups_timezone FOREIGN KEY (timezone) REFERENCES timezonemaster(id);


--
-- TOC entry 2565 (class 2606 OID 20565)
-- Name: fk_hblhmlh8ng9r401pahdgad4sf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationlog
    ADD CONSTRAINT fk_hblhmlh8ng9r401pahdgad4sf FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2608 (class 2606 OID 20570)
-- Name: fk_igwh550k5bs32dahnx05i310j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity
    ADD CONSTRAINT fk_igwh550k5bs32dahnx05i310j FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2586 (class 2606 OID 20575)
-- Name: fk_indoorunits_groups; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_indoorunits_groups FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- TOC entry 2587 (class 2606 OID 20580)
-- Name: fk_indoorunits_indoorunits; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_indoorunits_indoorunits FOREIGN KEY (parent_id) REFERENCES indoorunits(id);


--
-- TOC entry 2593 (class 2606 OID 20585)
-- Name: fk_indoorunitslog_history_fanspeed_master; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT fk_indoorunitslog_history_fanspeed_master FOREIGN KEY (fanspeed) REFERENCES fanspeed_master(id);


--
-- TOC entry 2594 (class 2606 OID 20590)
-- Name: fk_indoorunitslog_history_indoorunits; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT fk_indoorunitslog_history_indoorunits FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2595 (class 2606 OID 20595)
-- Name: fk_indoorunitslog_history_modemaster; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT fk_indoorunitslog_history_modemaster FOREIGN KEY (acmode) REFERENCES modemaster(id);


--
-- TOC entry 2596 (class 2606 OID 20600)
-- Name: fk_indoorunitslog_history_winddirection_master; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT fk_indoorunitslog_history_winddirection_master FOREIGN KEY (flapmode) REFERENCES winddirection_master(id);


--
-- TOC entry 2566 (class 2606 OID 20605)
-- Name: fk_j2c76jbbj0cqtob3mhh9lxsig; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationlog
    ADD CONSTRAINT fk_j2c76jbbj0cqtob3mhh9lxsig FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2611 (class 2606 OID 20610)
-- Name: fk_k4au4jdf5xyp95qndgjbilo2o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_daily
    ADD CONSTRAINT fk_k4au4jdf5xyp95qndgjbilo2o FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2562 (class 2606 OID 20615)
-- Name: fk_lgf4pero1bp62p5vtk59jj6hx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY adapters
    ADD CONSTRAINT fk_lgf4pero1bp62p5vtk59jj6hx FOREIGN KEY (defualtgroupid) REFERENCES groups(id);


--
-- TOC entry 2576 (class 2606 OID 20620)
-- Name: fk_n3o58nmvlsqw9phlrwlt3dept; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_yearly
    ADD CONSTRAINT fk_n3o58nmvlsqw9phlrwlt3dept FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2577 (class 2606 OID 20625)
-- Name: fk_ne25rry8s5203cm06k5ojwxwp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ghpparameter_statistics
    ADD CONSTRAINT fk_ne25rry8s5203cm06k5ojwxwp FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2581 (class 2606 OID 20630)
-- Name: fk_ne4tox3pdanigmj202avfn2ti; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_ne4tox3pdanigmj202avfn2ti FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- TOC entry 2602 (class 2606 OID 20635)
-- Name: fk_notificationsettings_groups; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationsettings
    ADD CONSTRAINT fk_notificationsettings_groups FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- TOC entry 2603 (class 2606 OID 20640)
-- Name: fk_notificationsettings_notificationtype_master; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationsettings
    ADD CONSTRAINT fk_notificationsettings_notificationtype_master FOREIGN KEY (notificationtype_id) REFERENCES notificationtype_master(id);


--
-- TOC entry 2625 (class 2606 OID 20645)
-- Name: fk_o4vmavf9wetq0mimpe28jb40e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_o4vmavf9wetq0mimpe28jb40e FOREIGN KEY (timezone_id) REFERENCES timezonemaster(id);


--
-- TOC entry 2626 (class 2606 OID 20650)
-- Name: fk_omii8qay0i55ud0h1pynki901; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_omii8qay0i55ud0h1pynki901 FOREIGN KEY (roles_id) REFERENCES roles(id);


--
-- TOC entry 2607 (class 2606 OID 20655)
-- Name: fk_outdoorunitstatistics_history_outdoorunits; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunitslog_history
    ADD CONSTRAINT fk_outdoorunitstatistics_history_outdoorunits FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2601 (class 2606 OID 20660)
-- Name: fk_pa5sra7ew4xoxtlhob4go2e81; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_yearly
    ADD CONSTRAINT fk_pa5sra7ew4xoxtlhob4go2e81 FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2613 (class 2606 OID 20665)
-- Name: fk_pbxx7flic11vic1lfnr44ywim; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_monthly
    ADD CONSTRAINT fk_pbxx7flic11vic1lfnr44ywim FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2574 (class 2606 OID 20670)
-- Name: fk_pve81yfj6q5kh6gwhvo2ypg6b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_monthly
    ADD CONSTRAINT fk_pve81yfj6q5kh6gwhvo2ypg6b FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2563 (class 2606 OID 20675)
-- Name: fk_qck4ee66xd9endai8qthbtpa3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY adapters
    ADD CONSTRAINT fk_qck4ee66xd9endai8qthbtpa3 FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- TOC entry 2570 (class 2606 OID 20680)
-- Name: fk_qcvu0enpsmd4aatd61cvi6hd8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcuser_action
    ADD CONSTRAINT fk_qcvu0enpsmd4aatd61cvi6hd8 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2628 (class 2606 OID 23127)
-- Name: fk_rcoperation_log_indoorunits; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcoperation_log
    ADD CONSTRAINT fk_rcoperation_log_indoorunits FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2629 (class 2606 OID 23132)
-- Name: fk_rcoperation_log_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcoperation_log
    ADD CONSTRAINT fk_rcoperation_log_users FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2615 (class 2606 OID 20690)
-- Name: fk_rs7eqsy5hk04nvpvakmm1mx3r; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_weekly
    ADD CONSTRAINT fk_rs7eqsy5hk04nvpvakmm1mx3r FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2620 (class 2606 OID 20695)
-- Name: fk_sfvu9rencl782arq0o5bul0a0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolepermissions
    ADD CONSTRAINT fk_sfvu9rencl782arq0o5bul0a0 FOREIGN KEY (roles_id) REFERENCES roles(id);


--
-- TOC entry 2597 (class 2606 OID 20700)
-- Name: fk_t05r4etofjmnj48wrb7h6px8g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics
    ADD CONSTRAINT fk_t05r4etofjmnj48wrb7h6px8g FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2609 (class 2606 OID 20705)
-- Name: fk_t2timx87f1dr9ov9qj9i8dyxo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity
    ADD CONSTRAINT fk_t2timx87f1dr9ov9qj9i8dyxo FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2592 (class 2606 OID 20710)
-- Name: fk_tma6d08qssl6gxt7tmd9ckit6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT fk_tma6d08qssl6gxt7tmd9ckit6 FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2588 (class 2606 OID 20715)
-- Name: fk_tmbjmv5m00tnd6nk43orvbbip; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_tmbjmv5m00tnd6nk43orvbbip FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- TOC entry 2599 (class 2606 OID 20720)
-- Name: fk_totyjayw4r5fj6m3es75uts6k; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_monthly
    ADD CONSTRAINT fk_totyjayw4r5fj6m3es75uts6k FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- TOC entry 2596 (class 2606 OID 34698)
-- Name: fk_unique_site_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_unique_site_id FOREIGN KEY (siteid) REFERENCES groups(uniqueid);


--
-- TOC entry 2614 (class 2606 OID 34703)
-- Name: fk_unique_site_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT fk_unique_site_id FOREIGN KEY (siteid) REFERENCES groups(uniqueid);


--
-- TOC entry 2631 (class 2606 OID 20725)
-- Name: fk_user_notification_setting_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_notification_settings
    ADD CONSTRAINT fk_user_notification_setting_user FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2623 (class 2606 OID 20730)
-- Name: fk_user_notification_settings; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_notification_settings
    ADD CONSTRAINT fk_user_notification_settings FOREIGN KEY (notification_settings_id) REFERENCES notificationsettings(id);


--
-- TOC entry 2582 (class 2606 OID 20740)
-- Name: group_level_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT group_level_fk FOREIGN KEY (group_level_id) REFERENCES group_level(id);


--
-- TOC entry 2748 (class 0 OID 0)
-- Dependencies: 8
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- TOC entry 2751 (class 0 OID 0)
-- Dependencies: 394
-- Name: dblink_connect_u(text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION dblink_connect_u(text) FROM PUBLIC;
REVOKE ALL ON FUNCTION dblink_connect_u(text) FROM postgres;
GRANT ALL ON FUNCTION dblink_connect_u(text) TO postgres;


--
-- TOC entry 2752 (class 0 OID 0)
-- Dependencies: 395
-- Name: dblink_connect_u(text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION dblink_connect_u(text, text) FROM PUBLIC;
REVOKE ALL ON FUNCTION dblink_connect_u(text, text) FROM postgres;
GRANT ALL ON FUNCTION dblink_connect_u(text, text) TO postgres;


--
-- TOC entry 2754 (class 0 OID 0)
-- Dependencies: 2047
-- Name: b2bacserver; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON FOREIGN SERVER b2bacserver FROM PUBLIC;
REVOKE ALL ON FOREIGN SERVER b2bacserver FROM postgres;
GRANT ALL ON FOREIGN SERVER b2bacserver TO postgres;


-- Completed on 2015-11-13 10:05:31

--
-- PostgreSQL database dump complete
--

--Ca Installation and Customer Data

CREATE TABLE adapters_model (
    id bigint DEFAULT nextval('adapters_id_seq'::regclass) NOT NULL,
    name character varying(255) NOT NULL
);

ALTER TABLE ONLY adapters_model
    ADD CONSTRAINT adapters_model_pkey PRIMARY KEY (id);

ALTER TABLE adapters ADD fw_version character varying(500);
ALTER TABLE adapters ADD mac_address character varying(255);
ALTER TABLE adapters ADD facility_id character varying(255);
ALTER TABLE adapters ADD name character varying(255);
ALTER TABLE adapters ADD model_id bigint;

ALTER TABLE ONLY adapters
    ADD CONSTRAINT adapters_model_id_fkey FOREIGN KEY (model_id) REFERENCES adapters_model(id);

ALTER TABLE ONLY adapters
    ADD CONSTRAINT adapters_siteid_fkey FOREIGN KEY (siteid) REFERENCES groups(id);

ALTER TABLE companies ADD country character varying(255);
ALTER TABLE companies ADD address character varying(255);
ALTER TABLE companies ADD postal_code character varying(255);
ALTER TABLE companies ADD status smallint;

--Added By Ravi for location info
ALTER TABLE groups
  ADD COLUMN path_name character varying(1024);
COMMENT ON COLUMN groups.path_name IS 'Added easy retrival of location information';




--Schema By Pramodh

ALTER TABLE ONLY pulse_meter
    ADD CONSTRAINT pulse_meter_pkey PRIMARY KEY (id);
ALTER TABLE ONLY pulse_meter
    ADD CONSTRAINT pulse_meter_adapter_id_fkey FOREIGN KEY (adapters_id) REFERENCES adapters(id);
ALTER TABLE ONLY pulse_meter
    ADD CONSTRAINT pulse_meter_distribution_group_id_fkey FOREIGN KEY (distribution_group_id) REFERENCES distribution_group(id);


ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT idx_indoorunits UNIQUE (parent_id);


ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT indoorunits_pkey PRIMARY KEY (id);


    ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_423uw67rudujfavow17g85m5d FOREIGN KEY (adapters_id) REFERENCES adapters(id);

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_indoorunits_groups FOREIGN KEY (group_id) REFERENCES groups(id);



ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_tmbjmv5m00tnd6nk43orvbbip FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT indoorunits_distribution_group_id_fkey FOREIGN KEY (distribution_group_id) REFERENCES distribution_group(id);



ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT fk_35mp1eagi1b27br70uuwtsm3n FOREIGN KEY (adapters_id) REFERENCES adapters(id);


ALTER TABLE ONLY adapters
    ADD CONSTRAINT adapters_site_id_fkey FOREIGN KEY (site_id) REFERENCES groups(id);



CREATE TABLE pulse_meter (
    id bigint DEFAULT nextval('adapters_id_seq'::regclass) NOT NULL,
    meter_name character varying(225),
    port_number integer,
    distribution_group_id integer,
    logical_oid character varying(225),
    adapters_id integer,
    device_modal character varying(225),
    meter_type character varying(45)
);


CREATE TABLE distribution_group (
    id bigint DEFAULT nextval('companies_id_seq'::regclass) NOT NULL,
    group_name character varying(225),
    created_time timestamp without time zone DEFAULT ('now'::text)::date,
    costomer_id bigint,
    type character varying(100),
    type_measurment character varying(225)
);


ALTER TABLE outdoorunits ADD Logical_oid character varying(255);
ALTER TABLE outdoorunits ADD s_link character varying(255) NOT NULL;
ALTER TABLE outdoorunits ADD fecility_id character varying(255);
ALTER TABLE outdoorunits ADD model_name character varying(255);
ALTER TABLE outdoorunits ADD device_modal character varying(255);
ALTER TABLE outdoorunits ADD refrigcircuitgroupoduid character varying(255);
ALTER TABLE outdoorunits ADD refrigcircuitno character varying(255);
ALTER TABLE outdoorunits ADD device_name character varying(255);
ALTER TABLE outdoorunits ADD connectionnumber character varying(255);


ALTER TABLE indoorunits ADD Logical_oid character varying(255);
ALTER TABLE indoorunits ADD distribution_group_id bigint;
ALTER TABLE indoorunits ADD s_link character varying(225);
ALTER TABLE indoorunits ADD device_modal character varying(225);
ALTER TABLE indoorunits ADD fecility_id character varying(225);
ALTER TABLE indoorunits ADD centralcontroladdress character varying(225);
ALTER TABLE indoorunits ADD connectionnumber character varying(225);
ALTER TABLE indoorunits ADD  mainiduaddress character varying(225);
ALTER TABLE indoorunits ADD refrigcircuitno character(225);
ALTER TABLE indoorunits ADD connectiontype character varying(225);
ALTER TABLE indoorunits ADD connectionaddress character varying(225);

-- corrected queries of Distribution group and pulse meter

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_adapterid FOREIGN KEY (adapters_id) REFERENCES adapters(id);
	
	
	ALTER TABLE ONLY outdoorunits
   ADD  CONSTRAINT  fk_adapterid FOREIGN KEY (adapters_id) REFERENCES adapters(id) ;
   
   
ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT  fk_adapterid FOREIGN KEY (adapters_id) REFERENCES adapters(id);
	
	
ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT indoorunits_distribution_group_id_fkey FOREIGN KEY (distribution_group_id) REFERENCES distribution_group(id);
	
	
ALTER TABLE ONLY pulse_meter
    ADD CONSTRAINT pulse_meter_adapter_id_fkey FOREIGN KEY (adapters_id) REFERENCES adapters(id);
ALTER TABLE ONLY pulse_meter
    ADD CONSTRAINT pulse_meter_distribution_group_id_fkey FOREIGN KEY (distribution_group_id) REFERENCES distribution_group(id);


