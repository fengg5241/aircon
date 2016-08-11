--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: postgres_fdw; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgres_fdw WITH SCHEMA public;


--
-- Name: EXTENSION postgres_fdw; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgres_fdw IS 'foreign-data wrapper for remote PostgreSQL servers';


--
-- Name: tablefunc; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS tablefunc WITH SCHEMA public;


--
-- Name: EXTENSION tablefunc; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION tablefunc IS 'functions that manipulate whole tables, including crosstab';


SET search_path = public, pg_catalog;

--
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
-- Name: concat(anyarray); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION concat(VARIADIC anyarray) RETURNS text
    LANGUAGE sql IMMUTABLE
    AS $_$

SELECT array_to_string($1,'');

$_$;


ALTER FUNCTION public.concat(VARIADIC anyarray) OWNER TO postgres;

--
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
-- Name: fn_getpropertvalue(character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION fn_getpropertvalue(property character varying, mode character varying, faclid character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$

declare

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

end

$$;


ALTER FUNCTION public.fn_getpropertvalue(property character varying, mode character varying, faclid character varying) OWNER TO postgres;

--
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
-- Name: usp_getgroupnamelocation_company(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getgroupnamelocation_company(groupids character varying, level integer) RETURNS TABLE(groupname character varying, supplygroupname character varying, groupid character varying, companyname character varying, pathname character varying)
    LANGUAGE plpgsql
    AS $$

--DECLARE x INT DEFAULT 0;

--	DECLARE y INT DEFAULT 0;

--	DECLARE  noOfDELM  INT DEFAULT 0;

	DECLARE  IDs varchar(1024)  DEFAULT '';

  declare

    a text[]; 

    i int;

BEGIN

DROP TABLE IF EXISTS groupslevel_temp;

--DROP TABLE temp_supplygroup;

--

CREATE GLOBAL TEMPORARY  TABLE groupslevel_temp

(

  --id bigint,

  --parentid bigint,

  --groupcategoryid integer,

  --uniqueid character varying(16),

  "name" character varying(1024),

  --path character varying(1024),

  supplygroupname character varying(32),

  groupid_supplylevelgroupid  character varying(64)

);

Select ((array(select distinct id from usp_groupchildtoparentdata_level(groupids,level))) ) into a;

i := 1;

    loop  

        if i > coalesce (array_upper(a, 1),1) then

            exit;

        else

          -- RETURN;

           --RAISE NOTICE 'Calling cs_create_job(%)', array_upper(a, 1);

	    insert into groupslevel_temp  values ((Select string_agg(temp.groupname, ',') from (Select distinct aa.groupname from usp_groupparentchilddata(a[i]) aa) temp) , a[i],a[i]);

           RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];

            i := i + 1;

          end if;

    end loop;

--Update groupslevel_temp t set companyname =   (Select distinct c.name from companies c inner join companiesusers cu

--on c.id =  cu.company_id

--where cu.group_id = cast(t.supplygroupname as bigint) ) ;

Update  groupslevel_temp t set supplygroupname =  (select name from groups where cast(id as varchar(40))  = t.supplygroupname);

RETURN QUERY

--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

select distinct  t.name as groupname,

t.supplygroupname,t.groupid_supplylevelgroupid as groupid,c.name as companyname,g.path_name

from  groupslevel_temp t

join companiesusers cu

on cu.group_id =  cast(t.groupid_supplylevelgroupid as bigint)

join companies c

on id =  cu.company_id

join groups g on g.id = cast(t.groupid_supplylevelgroupid as bigint)

;

--Select * from usp_getindoorunits_supplylevelgroupname('5,20',6)

END;

$$;


ALTER FUNCTION public.usp_getgroupnamelocation_company(groupids character varying, level integer) OWNER TO postgres;

--
-- Name: usp_getgroupnamelocation_company(character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getgroupnamelocation_company(groupids character varying, grouplocation character varying, level integer) RETURNS TABLE(groupname character varying, supplygroupname character varying, groupid character varying, companyname character varying)
    LANGUAGE plpgsql
    AS $$

--DECLARE x INT DEFAULT 0;

--	DECLARE y INT DEFAULT 0;

--	DECLARE  noOfDELM  INT DEFAULT 0;

	DECLARE  IDs varchar(1024)  DEFAULT '';

  declare

    a text[]; 

    i int;

BEGIN

DROP TABLE IF EXISTS groupslevel_temp;

--DROP TABLE temp_supplygroup;

--

CREATE GLOBAL TEMPORARY  TABLE groupslevel_temp

(

  --id bigint,

  --parentid bigint,

  --groupcategoryid integer,

  --uniqueid character varying(16),

  "name" character varying(1024),

  --path character varying(1024),

  supplygroupname character varying(32),

  groupid_supplylevelgroupid  character varying(64)

);

Select ((array(select distinct id from usp_groupchildtoparentdata_level(groupids,level))) ) into a;

i := 1;

    loop  

        if i > coalesce (array_upper(a, 1),1) then

            exit;

        else

          -- RETURN;

           --RAISE NOTICE 'Calling cs_create_job(%)', array_upper(a, 1);

           Select string_agg(temp.groupname, ',') from (Select distinct groupname from usp_groupparentchilddata(a[i]) aa) temp into grouplocation;

	    insert into groupslevel_temp  values (grouplocation , a[i],a[i]);

           RAISE NOTICE 'Calling cs11111_create_job(%)', grouplocation;

            i := i + 1;

          end if;

    end loop;

--Update groupslevel_temp t set companyname =   (Select distinct c.name from companies c inner join companiesusers cu

--on c.id =  cu.company_id

--where cu.group_id = cast(t.supplygroupname as bigint) ) ;

Update  groupslevel_temp t set supplygroupname =  (select name from groups where cast(id as varchar(40))  = t.supplygroupname);

RETURN QUERY

--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

select distinct  t.name as groupname,

t.supplygroupname,t.groupid_supplylevelgroupid as groupid,c.name as companyname

from  groupslevel_temp t

join companiesusers cu

on cu.group_id =  cast(t.groupid_supplylevelgroupid as bigint)

join companies c

on id =  cu.company_id

;

--Select * from usp_getindoorunits_supplylevelgroupname('5,20',6)

END;

$$;


ALTER FUNCTION public.usp_getgroupnamelocation_company(groupids character varying, grouplocation character varying, level integer) OWNER TO postgres;

--
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
-- Name: usp_getindooroutdoorunits_supplylevelgroupname(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindooroutdoorunits_supplylevelgroupname(groupids character varying, level integer) RETURNS TABLE(indoorunitid bigint, groupname character varying, supplygroupname character varying, idname character varying, outdoorunitid bigint, groupid bigint)
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

t.supplygroupname,idu.name as idname,idu.outdoorunit_id,t.id as groupid

from  groupslevel_temp1 t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4

Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 

where t.groupcategoryid in( 4,2)

order by case when idu.id is not null then idu.id else idusite.id END;

--Select * from usp_getindoorunits_supplylevelgroupname('5,20',6)

END;

$$;


ALTER FUNCTION public.usp_getindooroutdoorunits_supplylevelgroupname(groupids character varying, level integer) OWNER TO postgres;

--
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
-- Name: usp_getindoorunits_supplygroupname(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunits_supplygroupname(groupids character varying) RETURNS TABLE(indoorunitid bigint, groupname character varying, supplygroupname character varying, idname character varying, groupid bigint)
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

t.supplygroupname,idu.name as idname,t.id as groupid

from tempgroupsdata t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4

Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 

where t.groupcategoryid in( 4,2)

order by case when idu.id is not null then idu.id else idusite.id END;

END;

$_$;


ALTER FUNCTION public.usp_getindoorunits_supplygroupname(groupids character varying) OWNER TO postgres;

--
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
-- Name: usp_getindoorunits_supplylevelgroupname(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunits_supplylevelgroupname(groupids character varying, level integer) RETURNS TABLE(indoorunitid bigint, groupname character varying, supplygroupname character varying, idname character varying, groupid bigint)
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

t.supplygroupname,idu.name as idname,t.id as groupid

from  groupslevel_temp1 t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4

Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 

where t.groupcategoryid in( 4,2)

order by case when idu.id is not null then idu.id else idusite.id END;

--Select * from usp_getindoorunits_supplylevelgroupname('5,20',6)

END;

$$;


ALTER FUNCTION public.usp_getindoorunits_supplylevelgroupname(groupids character varying, level integer) OWNER TO postgres;

--
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
-- Name: usp_getindoorunitsbyuser(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_getindoorunitsbyuser(userid bigint) RETURNS TABLE(indoorunitid bigint, groupname character varying, idname character varying)
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

END;

$$;


ALTER FUNCTION public.usp_getindoorunitsbyuser(userid bigint) OWNER TO postgres;

--
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
-- Name: usp_groupchildtoparentdata_level(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION usp_groupchildtoparentdata_level(groupids character varying, level integer) RETURNS TABLE(id bigint, parentid bigint, uniqueid character varying, groupname character varying, path character varying)
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

SELECT si.id,	si.parentid,si.groupcategoryid,si.uniqueid,si.name as groupname,sp.path,si.group_level_id

	FROM groups As si

	INNER JOIN ctegroups AS sp

	ON (si.id = sp.parentid)

)

Select c.id,c.parentid,c.uniqueid,c.groupname,c.path from ctegroups  c where group_level_id  = level;	

END;

$$;


ALTER FUNCTION public.usp_groupchildtoparentdata_level(groupids character varying, level integer) OWNER TO postgres;

--
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
-- Name: b2bac; Type: FOREIGN DATA WRAPPER; Schema: -; Owner: postgres
--

CREATE FOREIGN DATA WRAPPER b2bac VALIDATOR postgresql_fdw_validator;


ALTER FOREIGN DATA WRAPPER b2bac OWNER TO postgres;

--
-- Name: b2bacsept; Type: FOREIGN DATA WRAPPER; Schema: -; Owner: postgres
--

CREATE FOREIGN DATA WRAPPER b2bacsept VALIDATOR postgresql_fdw_validator;


ALTER FOREIGN DATA WRAPPER b2bacsept OWNER TO postgres;

--
-- Name: dbrnd; Type: FOREIGN DATA WRAPPER; Schema: -; Owner: postgres
--

CREATE FOREIGN DATA WRAPPER dbrnd VALIDATOR postgresql_fdw_validator;


ALTER FOREIGN DATA WRAPPER dbrnd OWNER TO postgres;

--
-- Name: testdblinkserver; Type: FOREIGN DATA WRAPPER; Schema: -; Owner: postgres
--

CREATE FOREIGN DATA WRAPPER testdblinkserver VALIDATOR postgresql_fdw_validator;


ALTER FOREIGN DATA WRAPPER testdblinkserver OWNER TO postgres;

--
-- Name: b2bacserver; Type: SERVER; Schema: -; Owner: postgres
--

CREATE SERVER b2bacserver FOREIGN DATA WRAPPER testdblinkserver OPTIONS (
    dbname 'platform',
    hostaddr 'localhost'
);


ALTER SERVER b2bacserver OWNER TO postgres;

--
-- Name: USER MAPPING postgres SERVER b2bacserver; Type: USER MAPPING; Schema: -; Owner: postgres
--

CREATE USER MAPPING FOR postgres SERVER b2bacserver OPTIONS (
    password 'postgres',
    "user" 'postgres'
);


--
-- Name: platform_server; Type: SERVER; Schema: -; Owner: postgres
--

CREATE SERVER platform_server FOREIGN DATA WRAPPER postgres_fdw OPTIONS (
    dbname 'platform',
    port '5432'
);


ALTER SERVER platform_server OWNER TO postgres;

--
-- Name: USER MAPPING postgres SERVER platform_server; Type: USER MAPPING; Schema: -; Owner: postgres
--

CREATE USER MAPPING FOR postgres SERVER platform_server OPTIONS (
    password 'postgres',
    "user" 'postgres'
);


--
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
-- Name: adapters; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE adapters (
    id bigint NOT NULL,
    activatedate timestamp without time zone,
    address character varying(255),
    createdby character varying(255),
    creationdate timestamp without time zone DEFAULT now(),
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
    siteid character varying(32),
    timezone smallint,
    name character varying(128),
    oid character varying(225),
    model_id bigint,
    mac_address character varying(225),
    fw_version character varying(225)
);


ALTER TABLE adapters OWNER TO postgres;

--
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
-- Name: adapters_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE adapters_id_seq OWNED BY adapters.id;


--
-- Name: adapters_model; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE adapters_model (
    id bigint DEFAULT nextval('adapters_id_seq'::regclass) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE adapters_model OWNER TO postgres;

--
-- Name: aggr_data; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE aggr_data (
    facl_id character varying(128) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    aggr_item_cd character varying(8) NOT NULL,
    aggr_end_flg integer NOT NULL,
    aggr_val character varying(128) NOT NULL,
    reg_appl_id character varying(255) NOT NULL,
    reg_prgm_id character varying(255) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    reg_usr_id character varying(255),
    upd_appl_id character varying(255) NOT NULL,
    upd_prgm_id character varying(255) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL,
    upd_usr_id character varying(255)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'aggr_data'
);


ALTER FOREIGN TABLE aggr_data OWNER TO postgres;

--
-- Name: distribute_data; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--
CREATE FOREIGN TABLE distribute_data (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    distribute_grp_id character varying(10) NOT NULL,
    facl_id character varying(128) NOT NULL,
    aggr_ctg_cd character varying(8) NOT NULL,
    distribution_type character varying(8) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    distribution_rate double precision,
    distribution_val numeric,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
)
SERVER platform_server
OPTIONS (
	schema_name 'public',
    table_name 'distribute_data'
);


ALTER FOREIGN TABLE distribute_data OWNER TO postgres;
--
-- Name: aircon_indoor_unit; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE aircon_indoor_unit (
    refrig_circuit_grp_no character varying(8) NOT NULL,
    main_idu_flg integer NOT NULL,
    main_idu_addr character varying(128) NOT NULL,
    machine_structure_datetime timestamp without time zone NOT NULL,
    ctrler_logical_id character varying(128) NOT NULL,
    connect_type character varying(8) NOT NULL,
    connect_number character varying(8) NOT NULL,
    connect_idu_addr character varying(8),
    facl_id character varying(128),
    reg_appl_id character varying(255) NOT NULL,
    reg_prgm_id character varying(255) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    reg_usr_id character varying(255),
    upd_appl_id character varying(255) NOT NULL,
    upd_prgm_id character varying(255) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL,
    upd_usr_id character varying(255)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'aircon_indoor_unit'
);


ALTER FOREIGN TABLE aircon_indoor_unit OWNER TO postgres;

--
-- Name: aircon_outdoor_unit; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE aircon_outdoor_unit (
    refrig_circuit_grp_odu_id character varying(8) NOT NULL,
    refrig_circuit_grp_no character varying(8) NOT NULL,
    machine_structure_datetime timestamp without time zone NOT NULL,
    ctrler_logical_id character varying(128) NOT NULL,
    connect_type character varying(8) NOT NULL,
    connect_number character varying(8) NOT NULL,
    facl_id character varying(128),
    reg_appl_id character varying(255) NOT NULL,
    reg_prgm_id character varying(255) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    reg_usr_id character varying(255),
    upd_appl_id character varying(255) NOT NULL,
    upd_prgm_id character varying(255) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL,
    upd_usr_id character varying(255)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'aircon_outdoor_unit'
);


ALTER FOREIGN TABLE aircon_outdoor_unit OWNER TO postgres;

--
-- Name: alarm_data; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE alarm_data (
    occur_datetime timestamp without time zone NOT NULL,
    facl_id character varying(128) NOT NULL,
    alarm_cd character varying(64) NOT NULL,
    device_attr_key_1 character varying(128) NOT NULL,
    device_attr_key_2 character varying(128) NOT NULL,
    device_attr_key_3 character varying(128) NOT NULL,
    device_id character varying(128) NOT NULL,
    pre_alarm_cd character varying(64)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'alarm_data'
);


ALTER FOREIGN TABLE alarm_data OWNER TO postgres;

--
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
-- Name: alarmstatistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE alarmstatistics_id_seq OWNED BY notificationlog.id;


--
-- Name: availabletemp; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE availabletemp (
    id bigint NOT NULL,
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    max numeric(10,0),
    min numeric(10,0),
    mode character varying(45),
    updatedate timestamp without time zone,
    updatedby character varying(45),
    indoorunit_id bigint
);


ALTER TABLE availabletemp OWNER TO postgres;

--
-- Name: availabletemp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE availabletemp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE availabletemp_id_seq OWNER TO postgres;

--
-- Name: availabletemp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE availabletemp_id_seq OWNED BY availabletemp.id;


--
-- Name: status_info; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE status_info (
    property_id character varying(128) NOT NULL,
    facl_id character varying(128) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    measure_val character varying(128) NOT NULL
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'status_info'
);


ALTER FOREIGN TABLE status_info OWNER TO postgres;

--
-- Name: vw_status_info; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_status_info AS
 SELECT status_info.facl_id,
    status_info.property_id,
    status_info.measure_val,
    status_info.data_datetime
   FROM status_info;


ALTER TABLE vw_status_info OWNER TO postgres;

--
-- Name: cl_rw_statusinfo_by_date_id; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW cl_rw_statusinfo_by_date_id AS
 SELECT eav.facl_id,
    eav.data_datetime,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A2_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a2_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A2_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a2_2,
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
            WHEN ((eav.property_id)::text = 'A6_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7_2,
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
            WHEN ((eav.property_id)::text = 'B1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS b1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A3a'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a3a,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A3h'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a3h,
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
            WHEN ((eav.property_id)::text = 'A6a_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6a_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6a_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6a_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6h_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6h_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6h_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6h_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6c_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6c_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6c_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6c_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6d_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6d_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6d_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6d_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6f_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6f_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6f_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6f_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7a_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7a_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7a_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7a_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7h_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7h_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7h_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7h_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7c_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7c_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7c_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7c_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7d_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7d_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7d_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7d_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7f_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7f_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7f_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7f_2,
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
        END)::text) AS a34,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'B20'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS b20
   FROM ( SELECT status_info.facl_id,
            status_info.property_id,
            status_info.measure_val,
            status_info.data_datetime
           FROM vw_status_info status_info) eav
  GROUP BY eav.facl_id, eav.data_datetime
  ORDER BY eav.data_datetime, eav.facl_id;


ALTER TABLE cl_rw_statusinfo_by_date_id OWNER TO postgres;

--
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
    isdel boolean,
    country character varying(225),
    address character varying(225),
    postal_code character varying(225),
    status smallint,
    domain character varying(225)
);


ALTER TABLE companies OWNER TO postgres;

--
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
-- Name: companies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE companies_id_seq OWNED BY companies.id;


--
-- Name: companiesuser_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE companiesuser_seq
    START WITH 9999999
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE companiesuser_seq OWNER TO postgres;

--
-- Name: companiesusers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE companiesusers (
    company_id bigint NOT NULL,
    user_id bigint NOT NULL,
    group_id bigint NOT NULL,
    creationdate timestamp without time zone DEFAULT now() NOT NULL,
    updatedate timestamp without time zone,
    createdby character varying(45) DEFAULT NULL::character varying,
    updatedby character varying(45) DEFAULT NULL::character varying,
    id bigint DEFAULT nextval('companiesuser_seq'::regclass) NOT NULL
);


ALTER TABLE companiesusers OWNER TO postgres;

--
-- Name: companyrole; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE companyrole (
    roleid integer NOT NULL,
    rolename character varying(128),
    createdby character varying(45),
    updatedby character varying(45)
);


ALTER TABLE companyrole OWNER TO postgres;

--
-- Name: companysites_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE companysites_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE companysites_seq OWNER TO postgres;

--
-- Name: cstmer_mst; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE cstmer_mst (
    cstmer_id character varying(255) NOT NULL,
    appl_id character varying(255) NOT NULL,
    del_flg integer NOT NULL,
    reg_appl_id character varying(255) NOT NULL,
    reg_prgm_id character varying(255) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    reg_usr_id character varying(255),
    upd_appl_id character varying(255) NOT NULL,
    upd_prgm_id character varying(255) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL,
    upd_usr_id character varying(255)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'cstmer_mst'
);


ALTER FOREIGN TABLE cstmer_mst OWNER TO postgres;

--
-- Name: period_measure_data; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE period_measure_data (
    property_id character varying(128) NOT NULL,
    facl_id character varying(128) NOT NULL,
    data_collect_interval integer,
    data_datetime timestamp without time zone NOT NULL,
    measure_val character varying(128)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'period_measure_data'
);


ALTER FOREIGN TABLE period_measure_data OWNER TO postgres;

--
-- Name: ct_period_measure_data; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW ct_period_measure_data AS
 SELECT eav.facl_id,
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
            WHEN ((eav.property_id)::text = 'V23'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS v23,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'C2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS c2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'GS1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS gs1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'GS5'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS gs5,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'G44'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS g44
   FROM ( SELECT period_measure_data.facl_id,
            period_measure_data.property_id,
            period_measure_data.measure_val
           FROM period_measure_data) eav
  GROUP BY eav.facl_id;


ALTER TABLE ct_period_measure_data OWNER TO postgres;

--
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
            WHEN ((eav.property_id)::text = 'A2_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a2_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A2_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a2_2,
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
            WHEN ((eav.property_id)::text = 'A6_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7_2,
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
            WHEN ((eav.property_id)::text = 'B1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS b1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A3a'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a3a,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A3h'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a3h,
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
            WHEN ((eav.property_id)::text = 'A6a_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6a_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6a_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6a_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6h_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6h_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6h_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6h_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6c_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6c_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6c_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6c_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6d_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6d_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6d_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6d_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6f_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6f_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A6f_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a6f_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7a_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7a_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7a_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7a_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7h_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7h_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7h_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7h_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7c_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7c_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7c_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7c_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7d_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7d_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7d_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7d_2,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7f_1'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7f_1,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'A7f_2'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS a7f_2,
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
        END)::text) AS a34,
    max((
        CASE
            WHEN ((eav.property_id)::text = 'B20'::text) THEN eav.measure_val
            ELSE NULL::character varying
        END)::text) AS b20
   FROM ( SELECT status_info.facl_id,
            status_info.property_id,
            status_info.measure_val
           FROM status_info) eav
  GROUP BY eav.facl_id;


ALTER TABLE ct_statusinfo OWNER TO postgres;

--
-- Name: rcoperation_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rcoperation_master (
    id bigint NOT NULL,
    name character varying(45)
);


ALTER TABLE rcoperation_master OWNER TO postgres;

--
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
-- Name: deviceattribute_master_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE deviceattribute_master_id_seq OWNED BY rcoperation_master.id;


--
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
-- Name: devicecommand_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE devicecommand_id_seq OWNED BY rcuser_action.id;


--
-- Name: distribution_group; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE distribution_group (
    id integer NOT NULL,
    group_name character varying(225),
    created_time timestamp without time zone DEFAULT ('now'::text)::date,
    customer_id bigint,
    type character varying(100),
    type_measurment character varying(225)
);


ALTER TABLE distribution_group OWNER TO postgres;

--
-- Name: distribution_group_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE distribution_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE distribution_group_id_seq OWNER TO postgres;

--
-- Name: distribution_group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE distribution_group_id_seq OWNED BY distribution_group.id;


--
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
-- Name: efficiency_rating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE efficiency_rating_id_seq OWNED BY efficiency_rating.id;


--
-- Name: errorcode_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE errorcode_master (
    id integer NOT NULL,
    code character varying(64) NOT NULL,
    severity character varying(16),
    customer_description character varying(128),
    maintenance_description character varying(1024),
    notificationtype_id integer,
    createdby integer,
    creationdate timestamp without time zone,
    updatedate timestamp without time zone,
    updatedby integer,
    type character varying(45),
    countermeasure_2way character varying(255),
    countermeasure_3way character varying(255),
    countermeasure_customer character varying(255)
);


ALTER TABLE errorcode_master OWNER TO postgres;

--
-- Name: facl_mst; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE facl_mst (
    site_id character varying(64) NOT NULL,
    facl_id character varying(128) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    appl_id character varying(10) NOT NULL,
    facl_inst_datetime timestamp without time zone,
    facl_remv_datetime timestamp without time zone,
    model_id character varying(128) NOT NULL,
    reg_appl_id character varying(255) NOT NULL,
    reg_prgm_id character varying(255) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    reg_usr_id character varying(255),
    upd_appl_id character varying(255) NOT NULL,
    upd_prgm_id character varying(255) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL,
    upd_usr_id character varying(255)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'facl_mst'
);


ALTER FOREIGN TABLE facl_mst OWNER TO postgres;

--
-- Name: fanspeed_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fanspeed_master (
    id bigint NOT NULL,
    fanspeedname character varying(255)
);


ALTER TABLE fanspeed_master OWNER TO postgres;

--
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
-- Name: fanspeed_master_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE fanspeed_master_id_seq OWNED BY fanspeed_master.id;


--
-- Name: functionalgroup; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE functionalgroup (
    functional_groupid bigint NOT NULL,
    functional_groupname character varying(128),
    roletypeid integer
);


ALTER TABLE functionalgroup OWNER TO postgres;

--
-- Name: functionalgroup_functional_groupid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE functionalgroup_functional_groupid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE functionalgroup_functional_groupid_seq OWNER TO postgres;

--
-- Name: functionalgroup_functional_groupid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE functionalgroup_functional_groupid_seq OWNED BY functionalgroup.functional_groupid;


--
-- Name: functionalgroup_permission; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE functionalgroup_permission (
    id integer NOT NULL,
    functional_groupid integer,
    permissionid integer
);


ALTER TABLE functionalgroup_permission OWNER TO postgres;

--
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
-- Name: gasheatmeter_data_daily_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_daily_id_seq OWNED BY gasheatmeter_data_daily.id;


--
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
-- Name: gasheatmeter_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_id_seq OWNED BY gasheatmeter_data.id;


--
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
-- Name: gasheatmeter_data_monthly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_monthly_id_seq OWNED BY gasheatmeter_data_monthly.id;


--
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
-- Name: gasheatmeter_data_weekly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_weekly_id_seq OWNED BY gasheatmeter_data_weekly.id;


--
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
-- Name: gasheatmeter_data_yearly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE gasheatmeter_data_yearly_id_seq OWNED BY gasheatmeter_data_yearly.id;


--
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
-- Name: ghpparameter_statistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ghpparameter_statistics_id_seq OWNED BY ghpparameter_statistics.id;


--
-- Name: group_level; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE group_level (
    id bigint NOT NULL,
    type_level character varying(128),
    type_level_name character varying(128)
);


ALTER TABLE group_level OWNER TO postgres;

--
-- Name: groupcategory; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE groupcategory (
    groupcategoryid integer NOT NULL,
    groupcategoryname character varying(128) NOT NULL
);


ALTER TABLE groupcategory OWNER TO postgres;

--
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
    path_name character varying(1024),
    isunit_exists smallint DEFAULT 0
);


ALTER TABLE groups OWNER TO postgres;

--
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
-- Name: groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE groups_id_seq OWNED BY groups.id;


--
-- Name: groups_tempdata; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE groups_tempdata (
    id bigint,
    parentid bigint,
    groupcategoryid integer,
    uniqueid character varying(16),
    name character varying(45),
    path character varying(45),
    supplygroupname character varying(45)
);


ALTER TABLE groups_tempdata OWNER TO postgres;

--
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
-- Name: id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE id_seq OWNER TO postgres;

--
-- Name: indoorunits; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indoorunits (
    id bigint NOT NULL,
    centraladdress character varying(45),
    createdby character varying(45),
    creationdate timestamp without time zone DEFAULT now(),
    currenttime timestamp without time zone,
    parent_id integer,
    serialnumber character varying(45),
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
    svg_min_longitude numeric(17,14),
    centralcontroladdress character varying(225),
    connectionnumber character varying(225),
    mainiduaddress character varying(225),
    refrigcircuitno character(225),
    connectiontype character varying(225),
    connectionaddress character varying(225),
    distribution_group_id bigint,
    slinkaddress character varying(128),
    svg_id bigint,
    rc_flag smallint,
    device_model character varying(255)
);


ALTER TABLE indoorunits OWNER TO postgres;

--
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
-- Name: indoorunits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunits_id_seq OWNED BY indoorunits.id;


--
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
-- Name: indoorunitslog_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitslog_history_id_seq OWNED BY indoorunitslog_history.id;


--
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
-- Name: indoorunitslog_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitslog_id_seq OWNED BY indoorunitslog.id;


--
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
-- Name: indoorunitstatistics_daily_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_daily_id_seq OWNED BY indoorunitstatistics_daily.id;


--
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
-- Name: indoorunitstatistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_id_seq OWNED BY indoorunitstatistics.id;


--
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
-- Name: indoorunitstatistics_monthly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_monthly_id_seq OWNED BY indoorunitstatistics_monthly.id;


--
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
-- Name: indoorunitstatistics_weekly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_weekly_id_seq OWNED BY indoorunitstatistics_weekly.id;


--
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
-- Name: indoorunitstatistics_yearly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE indoorunitstatistics_yearly_id_seq OWNED BY indoorunitstatistics_yearly.id;


--
-- Name: inst_ctrler_mst; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE inst_ctrler_mst (
    valid_period_startdate timestamp without time zone NOT NULL,
    site_id character varying(64) NOT NULL,
    ctrler_physical_id character varying(128) NOT NULL,
    ctrler_logical_id character varying(128) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    appl_id character varying(10) NOT NULL,
    del_flg integer NOT NULL,
    reg_appl_id character varying(255) NOT NULL,
    reg_prgm_id character varying(255) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    reg_usr_id character varying(255),
    upd_appl_id character varying(255) NOT NULL,
    upd_prgm_id character varying(255) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL,
    upd_usr_id character varying(255),
    valid_period_enddate timestamp without time zone
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'inst_ctrler_mst'
);


ALTER FOREIGN TABLE inst_ctrler_mst OWNER TO postgres;

--
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
-- Name: maintenance_setting; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE maintenance_setting (
    id bigint NOT NULL,
    maintenance_type_id bigint,
    group_id bigint,
    threshold bigint
);


ALTER TABLE maintenance_setting OWNER TO postgres;

--
-- Name: maintenance_setting_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE maintenance_setting_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE maintenance_setting_id_seq OWNER TO postgres;

--
-- Name: maintenance_status_data; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE maintenance_status_data (
    id bigint NOT NULL,
    logtime timestamp without time zone,
    outdoorunit_id bigint,
    ghp_engine_operation_hours bigint,
    ghp_time_after_oil_change bigint,
    pac_comp_operation_hours bigint,
    vrf_comp_operation_hours_1 bigint,
    vrf_comp_operation_hours_2 bigint,
    vrf_comp_operation_hours_3 bigint
);


ALTER TABLE maintenance_status_data OWNER TO postgres;

--
-- Name: maintenance_status_data_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE maintenance_status_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE maintenance_status_data_id_seq OWNER TO postgres;

--
-- Name: maintenance_status_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE maintenance_status_data_id_seq OWNED BY maintenance_status_data.id;


--
-- Name: maintenance_type_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE maintenance_type_master (
    id bigint NOT NULL,
    description character varying(256)
);


ALTER TABLE maintenance_type_master OWNER TO postgres;

--
-- Name: maintenance_user_list; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE maintenance_user_list (
    id bigint NOT NULL,
    user_mail_id character varying(256),
    issend boolean,
    company_id bigint
);


ALTER TABLE maintenance_user_list OWNER TO postgres;

--
-- Name: maintenance_user_list_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE maintenance_user_list_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE maintenance_user_list_id_seq OWNER TO postgres;

--
-- Name: metaindoorunits; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE metaindoorunits (
    id bigint NOT NULL,
    creationdate timestamp without time zone NOT NULL,
    logo character varying(45),
    modelname character varying(45),
    updatedate timestamp without time zone,
    updatedby character varying(45),
    system character varying(45),
    settabletemp_limit_upper_auto numeric(10,2),
    settabletemp_limit_lower_auto numeric(10,2),
    settabletemp_limit_upper_cool numeric(10,2),
    settabletemp_limit_lower_cool numeric(10,2),
    settabletemp_limit_upper_heat numeric(10,2),
    settabletemp_limit_lower_heat numeric(10,2),
    settabletemp_limit_upper_dry numeric(10,2),
    settabletemp_limit_lower_dry numeric(10,2),
    settablefan_speed_high boolean,
    settablefan_speed_medium boolean,
    settablefan_speed_auto boolean,
    fixedoperation_mode smallint,
    settableswing boolean,
    settableflap boolean,
    is3way_system boolean,
    settablemode_heat boolean,
    settablemode_cool boolean,
    settablemode_dry boolean,
    settablemode_auto boolean,
    settablemode_fan boolean,
    settablefan_speed_low boolean,
    CONSTRAINT metaindoorunits_settabletemp_limit_lower_auto_check CHECK (((settabletemp_limit_lower_auto >= (-35.0)) AND (settabletemp_limit_lower_auto <= 92.5))),
    CONSTRAINT metaindoorunits_settabletemp_limit_lower_cool_check CHECK (((settabletemp_limit_lower_cool >= (-35.0)) AND (settabletemp_limit_lower_cool <= 92.5))),
    CONSTRAINT metaindoorunits_settabletemp_limit_lower_dry_check CHECK (((settabletemp_limit_lower_dry >= (-35.0)) AND (settabletemp_limit_lower_dry <= 92.5))),
    CONSTRAINT metaindoorunits_settabletemp_limit_lower_heat_check CHECK (((settabletemp_limit_lower_heat >= (-35.0)) AND (settabletemp_limit_lower_heat <= 92.5))),
    CONSTRAINT metaindoorunits_settabletemp_limit_upper_auto_check CHECK (((settabletemp_limit_upper_auto >= (-35.0)) AND (settabletemp_limit_upper_auto <= 92.5))),
    CONSTRAINT metaindoorunits_settabletemp_limit_upper_cool_check CHECK (((settabletemp_limit_upper_cool >= (-35.0)) AND (settabletemp_limit_upper_cool <= 92.5))),
    CONSTRAINT metaindoorunits_settabletemp_limit_upper_dry_check CHECK (((settabletemp_limit_upper_dry >= (-35.0)) AND (settabletemp_limit_upper_dry <= 92.5))),
    CONSTRAINT metaindoorunits_settabletemp_limit_upper_heat_check CHECK (((settabletemp_limit_upper_heat >= (-35.0)) AND (settabletemp_limit_upper_heat <= 92.5)))
);


ALTER TABLE metaindoorunits OWNER TO postgres;

--
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
-- Name: metaindoorunits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE metaindoorunits_id_seq OWNED BY metaindoorunits.id;


--
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
-- Name: metaoutdoorunits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE metaoutdoorunits_id_seq OWNED BY metaoutdoorunits.id;


--
-- Name: meter_unit; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE meter_unit (
    ctrler_logical_id character varying(128) NOT NULL,
    machine_structure_datetime timestamp without time zone NOT NULL,
    connect_type character varying(8) NOT NULL,
    connect_number character varying(8) NOT NULL,
    meter_type character varying(8) NOT NULL,
    facl_id character varying(128),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'meter_unit'
);


ALTER FOREIGN TABLE meter_unit OWNER TO postgres;

--
-- Name: modemaster; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE modemaster (
    id bigint NOT NULL,
    modename character varying(25)
);


ALTER TABLE modemaster OWNER TO postgres;

--
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
-- Name: modemaster_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE modemaster_id_seq OWNED BY modemaster.id;


--
-- Name: notificationlog_temp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE notificationlog_temp_id_seq
    START WITH 392
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notificationlog_temp_id_seq OWNER TO postgres;

--
-- Name: notificationlog_temp; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notificationlog_temp (
    id bigint DEFAULT nextval('notificationlog_temp_id_seq'::regclass) NOT NULL,
    indoorunit_id bigint,
    outdoorunit_id bigint,
    adapterid bigint,
    occur_datetime timestamp without time zone,
    alarmcode character(100),
    severity character(100),
    maintenance_description character(255),
    type character(255),
    timezone character(255)
);


ALTER TABLE notificationlog_temp OWNER TO postgres;

--
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
-- Name: notificationsettings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE notificationsettings_id_seq OWNED BY notificationsettings.id;


--
-- Name: notificationtype_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notificationtype_master (
    id integer NOT NULL,
    typename character varying(32)
);


ALTER TABLE notificationtype_master OWNER TO postgres;

--
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
-- Name: notificationtype_master_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE notificationtype_master_id_seq OWNED BY notificationtype_master.id;


--
-- Name: odu_maintenance_status_data_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE odu_maintenance_status_data_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE odu_maintenance_status_data_seq OWNER TO postgres;

--
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
-- Name: outdoorunits; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunits (
    id bigint DEFAULT nextval('id_seq'::regclass) NOT NULL,
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
    siteid character varying(45),
    svg_max_latitude numeric(17,14),
    svg_max_longitude numeric(17,14),
    svg_min_latitude numeric(17,14),
    svg_min_longitude numeric(17,14),
    svg_path character varying(255),
    model_name character varying(255),
    device_model character varying(255),
    refrigcircuitgroupoduid character varying(255),
    refrigcircuitno character varying(255),
    device_name character varying(255),
    connectionnumber character varying(255),
    connectiontype bigint,
    parentid bigint,
    refrigerantid integer,
    slinkaddress character varying(128),
    svg_id bigint
);


ALTER TABLE outdoorunits OWNER TO postgres;

--
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
-- Name: outdoorunitstatistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE outdoorunitstatistics_id_seq OWNED BY outdoorunitslog.id;


--
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
-- Name: outdoorunitstatistics; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunitstatistics (
    id bigint NOT NULL,
    logtime timestamp without time zone NOT NULL,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    outdoorunit_id bigint,
    ghp_oil_change double precision
);


ALTER TABLE outdoorunitstatistics OWNER TO postgres;

--
-- Name: outdoorunitstatistics_daily; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunitstatistics_daily (
    id bigint NOT NULL,
    logtime timestamp without time zone NOT NULL,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    outdoorunit_id bigint
);


ALTER TABLE outdoorunitstatistics_daily OWNER TO postgres;

--
-- Name: outdoorunitstatistics_daily_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE outdoorunitstatistics_daily_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE outdoorunitstatistics_daily_id_seq OWNER TO postgres;

--
-- Name: outdoorunitstatistics_daily_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE outdoorunitstatistics_daily_id_seq OWNED BY outdoorunitstatistics_daily.id;


--
-- Name: outdoorunitstatistics_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE outdoorunitstatistics_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE outdoorunitstatistics_id_seq1 OWNER TO postgres;

--
-- Name: outdoorunitstatistics_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE outdoorunitstatistics_id_seq1 OWNED BY outdoorunitstatistics.id;


--
-- Name: outdoorunitstatistics_monthly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunitstatistics_monthly (
    id bigint NOT NULL,
    logtime timestamp without time zone NOT NULL,
    month smallint NOT NULL,
    year smallint NOT NULL,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    outdoorunit_id bigint
);


ALTER TABLE outdoorunitstatistics_monthly OWNER TO postgres;

--
-- Name: outdoorunitstatistics_monthly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE outdoorunitstatistics_monthly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE outdoorunitstatistics_monthly_id_seq OWNER TO postgres;

--
-- Name: outdoorunitstatistics_monthly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE outdoorunitstatistics_monthly_id_seq OWNED BY outdoorunitstatistics_monthly.id;


--
-- Name: outdoorunitstatistics_weekly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunitstatistics_weekly (
    id bigint NOT NULL,
    logtime timestamp without time zone NOT NULL,
    week smallint NOT NULL,
    year smallint NOT NULL,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    outdoorunit_id bigint
);


ALTER TABLE outdoorunitstatistics_weekly OWNER TO postgres;

--
-- Name: outdoorunitstatistics_weekly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE outdoorunitstatistics_weekly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE outdoorunitstatistics_weekly_id_seq OWNER TO postgres;

--
-- Name: outdoorunitstatistics_weekly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE outdoorunitstatistics_weekly_id_seq OWNED BY outdoorunitstatistics_weekly.id;


--
-- Name: outdoorunitstatistics_yearly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outdoorunitstatistics_yearly (
    id bigint NOT NULL,
    logtime timestamp without time zone NOT NULL,
    year smallint NOT NULL,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    outdoorunit_id bigint
);


ALTER TABLE outdoorunitstatistics_yearly OWNER TO postgres;

--
-- Name: outdoorunitstatistics_yearly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE outdoorunitstatistics_yearly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE outdoorunitstatistics_yearly_id_seq OWNER TO postgres;

--
-- Name: outdoorunitstatistics_yearly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE outdoorunitstatistics_yearly_id_seq OWNED BY outdoorunitstatistics_yearly.id;


--
-- Name: period_measure_data_history; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE period_measure_data_history (
    property_id character varying(128) NOT NULL,
    facl_id character varying(128) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    data_collect_interval integer NOT NULL,
    device_attr_key_1 character varying(128),
    device_attr_key_2 character varying(128),
    device_attr_key_3 character varying(128),
    device_id character varying(128) NOT NULL,
    measure_val character varying(128)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'period_measure_data_history'
);


ALTER FOREIGN TABLE period_measure_data_history OWNER TO postgres;

--
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
    isdel boolean,
    company_id integer
);


ALTER TABLE permissions OWNER TO postgres;

--
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
-- Name: permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE permissions_id_seq OWNED BY permissions.id;


--
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
-- Name: power_consumption_capacity_daily_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_daily_id_seq OWNED BY power_consumption_capacity_daily.id;


--
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
-- Name: power_consumption_capacity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_id_seq OWNED BY power_consumption_capacity.id;


--
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
-- Name: power_consumption_capacity_monthly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_monthly_id_seq OWNED BY power_consumption_capacity_monthly.id;


--
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
-- Name: power_consumption_capacity_weekly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_weekly_id_seq OWNED BY power_consumption_capacity_weekly.id;


--
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
-- Name: power_consumption_capacity_yearly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE power_consumption_capacity_yearly_id_seq OWNED BY power_consumption_capacity_yearly.id;


--
-- Name: pulse_meter; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pulse_meter (
    id integer NOT NULL,
    meter_name character varying(225),
    port_number integer,
    distribution_group_id integer,
    oid character varying(250),
    adapters_id integer,
    device_model character varying(225),
    meter_type character varying(45),
    multi_factor character varying(250),
    creationdate character varying(250) DEFAULT now(),
    connection_type character varying(250)
);


ALTER TABLE pulse_meter OWNER TO postgres;

--
-- Name: pulse_meter_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pulse_meter_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pulse_meter_id_seq OWNER TO postgres;

--
-- Name: pulse_meter_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pulse_meter_id_seq OWNED BY pulse_meter.id;


--
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
-- Name: ratingmaster_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ratingmaster_id_seq OWNED BY ratingmaster.id;


--
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
-- Name: rc_prohibition_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rc_prohibition_id_seq OWNED BY rc_prohibition.id;


--
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
    prohibitionsettemp character varying(16),
    prohibitionenergy_saving character varying(16),
    roomtemp character varying(8)
);


ALTER TABLE rcoperation_log OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE refrigerantcircuit_statistics (
    id bigint NOT NULL,
    refrigerantid integer,
    power_consumption double precision,
    efficiency numeric(5,2),
    ratedcapacity double precision,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    logtime timestamp without time zone,
    currentcapacity real,
    outdoortemp real
);


ALTER TABLE refrigerantcircuit_statistics OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_daily; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE refrigerantcircuit_statistics_daily (
    id bigint NOT NULL,
    refrigerantid integer,
    power_consumption double precision,
    efficiency numeric(5,2),
    ratedcapacity double precision,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    logtime timestamp without time zone,
    currentcapacity real,
    outdoortemp real
);


ALTER TABLE refrigerantcircuit_statistics_daily OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_daily_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE refrigerantcircuit_statistics_daily_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE refrigerantcircuit_statistics_daily_id_seq OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_daily_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE refrigerantcircuit_statistics_daily_id_seq OWNED BY refrigerantcircuit_statistics_daily.id;


--
-- Name: refrigerantcircuit_statistics_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE refrigerantcircuit_statistics_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE refrigerantcircuit_statistics_id_seq OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE refrigerantcircuit_statistics_id_seq OWNED BY refrigerantcircuit_statistics.id;


--
-- Name: refrigerantcircuit_statistics_monthly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE refrigerantcircuit_statistics_monthly (
    id bigint NOT NULL,
    refrigerantid integer,
    power_consumption double precision,
    efficiency numeric(5,2),
    ratedcapacity double precision,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    logtime timestamp without time zone,
    month smallint NOT NULL,
    year smallint NOT NULL,
    currentcapacity real,
    outdoortemp real
);


ALTER TABLE refrigerantcircuit_statistics_monthly OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_monthly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE refrigerantcircuit_statistics_monthly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE refrigerantcircuit_statistics_monthly_id_seq OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_monthly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE refrigerantcircuit_statistics_monthly_id_seq OWNED BY refrigerantcircuit_statistics_monthly.id;


--
-- Name: refrigerantcircuit_statistics_weekly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE refrigerantcircuit_statistics_weekly (
    id bigint NOT NULL,
    refrigerantid integer,
    power_consumption double precision,
    efficiency numeric(5,2),
    ratedcapacity double precision,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    logtime timestamp without time zone,
    week smallint NOT NULL,
    year smallint NOT NULL,
    currentcapacity real,
    outdoortemp real
);


ALTER TABLE refrigerantcircuit_statistics_weekly OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_weekly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE refrigerantcircuit_statistics_weekly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE refrigerantcircuit_statistics_weekly_id_seq OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_weekly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE refrigerantcircuit_statistics_weekly_id_seq OWNED BY refrigerantcircuit_statistics_weekly.id;


--
-- Name: refrigerantcircuit_statistics_yearly; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE refrigerantcircuit_statistics_yearly (
    id bigint NOT NULL,
    refrigerantid integer,
    power_consumption double precision,
    efficiency numeric(5,2),
    ratedcapacity double precision,
    workinghour1 double precision,
    workinghour2 double precision,
    workinghour3 double precision,
    logtime timestamp without time zone,
    week smallint NOT NULL,
    year smallint NOT NULL,
    currentcapacity real,
    outdoortemp real
);


ALTER TABLE refrigerantcircuit_statistics_yearly OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_yearly_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE refrigerantcircuit_statistics_yearly_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE refrigerantcircuit_statistics_yearly_id_seq OWNER TO postgres;

--
-- Name: refrigerantcircuit_statistics_yearly_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE refrigerantcircuit_statistics_yearly_id_seq OWNED BY refrigerantcircuit_statistics_yearly.id;


--
-- Name: refrigerantmaster; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE refrigerantmaster (
    refrigerantid integer NOT NULL,
    refrigerantname character varying(128),
    adapterid bigint,
    linenumber character varying(16),
    svg_id1 bigint,
    svg_max_latitude1 numeric(17,14),
    svg_max_longitude1 numeric(17,14),
    svg_min_latitude1 numeric(17,14),
    svg_min_longitude1 numeric(17,14),
    svg_id2 bigint,
    svg_max_latitude2 numeric(17,14),
    svg_max_longitude2 numeric(17,14),
    svg_min_latitude2 numeric(17,14),
    svg_min_longitude2 numeric(17,14),
    svg_id3 bigint,
    svg_max_latitude3 numeric(17,14),
    svg_max_longitude3 numeric(17,14),
    svg_min_latitude3 numeric(17,14),
    svg_min_longitude3 numeric(17,14),
    siteid character varying(32)
);


ALTER TABLE refrigerantmaster OWNER TO postgres;

--
-- Name: rolefunctionalgrp; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rolefunctionalgrp (
    id bigint NOT NULL,
    roleid integer NOT NULL,
    funcgroupids integer NOT NULL,
    companyid integer,
    updatedby character varying(45),
    createdby character varying(45),
    creationdate timestamp without time zone,
    updatedate timestamp without time zone
);


ALTER TABLE rolefunctionalgrp OWNER TO postgres;

--
-- Name: rolefunctionalgrp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rolefunctionalgrp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rolefunctionalgrp_id_seq OWNER TO postgres;

--
-- Name: rolefunctionalgrp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rolefunctionalgrp_id_seq OWNED BY rolefunctionalgrp.id;


--
-- Name: rolehistory; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rolehistory (
    id bigint NOT NULL,
    role_id integer,
    action character varying(200),
    updateby bigint,
    updateddate timestamp without time zone,
    companyid bigint
);


ALTER TABLE rolehistory OWNER TO postgres;

--
-- Name: rolehistory_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rolehistory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rolehistory_id_seq OWNER TO postgres;

--
-- Name: rolehistory_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rolehistory_id_seq OWNED BY rolehistory.id;


--
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
-- Name: rolepermissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rolepermissions_id_seq OWNED BY rolepermissions.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE roles (
    id bigint NOT NULL,
    createdby character varying(255),
    creationdate timestamp without time zone,
    name character varying(255),
    updatedate timestamp without time zone,
    updatedby character varying(255),
    isdel boolean,
    roletype_id integer,
    company_id integer
);


ALTER TABLE roles OWNER TO postgres;

--
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
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE roles_id_seq OWNED BY roles.id;


--
-- Name: roletype; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE roletype (
    id integer NOT NULL,
    roletypename character varying(128)
);


ALTER TABLE roletype OWNER TO postgres;

--
-- Name: session; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE session (
    id bigint NOT NULL,
    createdby character varying(45),
    creationdate timestamp without time zone NOT NULL,
    email character varying(45),
    status smallint NOT NULL,
    uniquesessionid character varying(256),
    updatedate timestamp without time zone,
    updatedby character varying(45),
    loginid character varying(7) NOT NULL
);


ALTER TABLE session OWNER TO postgres;

--
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
-- Name: session_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE session_id_seq OWNED BY session.id;


--
-- Name: site_mst; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE site_mst (
    site_id character varying(255) NOT NULL,
    cstmer_id character varying(255) NOT NULL,
    appl_id character varying(255) NOT NULL,
    del_flg integer NOT NULL,
    reg_appl_id character varying(255) NOT NULL,
    reg_prgm_id character varying(255) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    reg_usr_id character varying(255),
    upd_appl_id character varying(255) NOT NULL,
    upd_prgm_id character varying(255) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL,
    upd_usr_id character varying(255)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'site_mst'
);


ALTER FOREIGN TABLE site_mst OWNER TO postgres;

--
-- Name: status_info_history; Type: FOREIGN TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE FOREIGN TABLE status_info_history (
    property_id character varying(128) NOT NULL,
    facl_id character varying(128) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    device_attr_key_1 character varying(128),
    device_attr_key_2 character varying(128),
    device_attr_key_3 character varying(128),
    device_id character varying(128) NOT NULL,
    measure_val character varying(128)
)
SERVER platform_server
OPTIONS (
    schema_name 'public',
    table_name 'status_info_history'
);


ALTER FOREIGN TABLE status_info_history OWNER TO postgres;

--
-- Name: svg_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE svg_master (
    id bigint NOT NULL,
    svg_file_name character varying(255),
    svg_name character varying(255)
);


ALTER TABLE svg_master OWNER TO postgres;

--
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
-- Name: temp_groupunitdata_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE temp_groupunitdata_id_seq OWNED BY temp_groupunitdata.id;


--
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
-- Name: timeline_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE timeline_id_seq OWNED BY timeline.id;


--
-- Name: timezonemaster; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE timezonemaster (
    id integer NOT NULL,
    timezone character varying(255)
);


ALTER TABLE timezonemaster OWNER TO postgres;

--
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
-- Name: timezonemaster_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE timezonemaster_id_seq OWNED BY timezonemaster.id;


--
-- Name: user_notification_settings; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_notification_settings (
    id integer NOT NULL,
    notification_settings_id integer,
    user_id integer
);


ALTER TABLE user_notification_settings OWNER TO postgres;

--
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
-- Name: user_notification_settings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_notification_settings_id_seq OWNED BY user_notification_settings.id;


--
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
-- Name: useraudit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE useraudit_id_seq OWNED BY useraudit.id;


--
-- Name: usermangementhistory; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usermangementhistory (
    id bigint NOT NULL,
    auditaction character varying(255),
    createdby bigint,
    creationdate timestamp without time zone,
    updatedate timestamp without time zone,
    updatedby bigint,
    user_id bigint
);


ALTER TABLE usermangementhistory OWNER TO postgres;

--
-- Name: usermangementhistory_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usermangementhistory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usermangementhistory_id_seq OWNER TO postgres;

--
-- Name: usermangementhistory_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE usermangementhistory_id_seq OWNED BY usermangementhistory.id;


--
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
    isemailverified smallint NOT NULL,
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
    lastvisitedgroups text,
    loginid character varying(200) NOT NULL,
    lastlogin_date timestamp without time zone,
    is_locked boolean DEFAULT false,
    is_valid boolean DEFAULT true,
    companyid bigint
);


ALTER TABLE users OWNER TO postgres;

--
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
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
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
-- Name: vrfparameter_statistics_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE vrfparameter_statistics_id_seq OWNED BY vrfparameter_statistics.id;


--
-- Name: vw_aggr_data; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_aggr_data AS
 SELECT aggr_data.facl_id,
    aggr_data.aggr_val,
    aggr_data.data_datetime,
    aggr_data.aggr_item_cd
   FROM aggr_data;


ALTER TABLE vw_aggr_data OWNER TO postgres;

--
-- Name: vw_alarm_data; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_alarm_data AS
 SELECT alarm_data.occur_datetime,
    alarm_data.facl_id,
    alarm_data.alarm_cd,
    alarm_data.pre_alarm_cd
   FROM alarm_data;


ALTER TABLE vw_alarm_data OWNER TO postgres;

--
-- Name: vw_period_measure_data; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_period_measure_data AS
 SELECT period_measure_data.facl_id,
    period_measure_data.property_id,
    period_measure_data.measure_val
   FROM period_measure_data;


ALTER TABLE vw_period_measure_data OWNER TO postgres;

--
-- Name: vw_period_measure_data_history; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_period_measure_data_history AS
 SELECT period_measure_data_history.property_id,
    period_measure_data_history.facl_id,
    period_measure_data_history.data_datetime,
    period_measure_data_history.measure_val
   FROM period_measure_data_history;


ALTER TABLE vw_period_measure_data_history OWNER TO postgres;

--
-- Name: vw_status_info_history; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_status_info_history AS
 SELECT sts.facl_id,
    sts.property_id,
    sts.measure_val,
    sts.data_datetime
   FROM status_info_history sts;


ALTER TABLE vw_status_info_history OWNER TO postgres;

--
-- Name: winddirection_master; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE winddirection_master (
    id bigint NOT NULL,
    winddirectionname character varying(255)
);


ALTER TABLE winddirection_master OWNER TO postgres;

--
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
-- Name: winddirection_master_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE winddirection_master_id_seq OWNED BY winddirection_master.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY adapters ALTER COLUMN id SET DEFAULT nextval('adapters_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY availabletemp ALTER COLUMN id SET DEFAULT nextval('availabletemp_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY companies ALTER COLUMN id SET DEFAULT nextval('companies_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY distribution_group ALTER COLUMN id SET DEFAULT nextval('distribution_group_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY efficiency_rating ALTER COLUMN id SET DEFAULT nextval('efficiency_rating_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fanspeed_master ALTER COLUMN id SET DEFAULT nextval('fanspeed_master_id_seq'::regclass);


--
-- Name: functional_groupid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY functionalgroup ALTER COLUMN functional_groupid SET DEFAULT nextval('functionalgroup_functional_groupid_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_daily ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_daily_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_monthly ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_monthly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_weekly ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_weekly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasheatmeter_data_yearly ALTER COLUMN id SET DEFAULT nextval('gasheatmeter_data_yearly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ghpparameter_statistics ALTER COLUMN id SET DEFAULT nextval('ghpparameter_statistics_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups ALTER COLUMN id SET DEFAULT nextval('groups_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits ALTER COLUMN id SET DEFAULT nextval('indoorunits_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog ALTER COLUMN id SET DEFAULT nextval('indoorunitslog_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history ALTER COLUMN id SET DEFAULT nextval('indoorunitslog_history_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_daily ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_daily_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_monthly ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_monthly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_weekly ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_weekly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_yearly ALTER COLUMN id SET DEFAULT nextval('indoorunitstatistics_yearly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY maintenance_status_data ALTER COLUMN id SET DEFAULT nextval('maintenance_status_data_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY metaindoorunits ALTER COLUMN id SET DEFAULT nextval('metaindoorunits_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY metaoutdoorunits ALTER COLUMN id SET DEFAULT nextval('metaoutdoorunits_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modemaster ALTER COLUMN id SET DEFAULT nextval('modemaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationlog ALTER COLUMN id SET DEFAULT nextval('alarmstatistics_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationsettings ALTER COLUMN id SET DEFAULT nextval('notificationsettings_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationtype_master ALTER COLUMN id SET DEFAULT nextval('notificationtype_master_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunitslog ALTER COLUMN id SET DEFAULT nextval('outdoorunitstatistics_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunitstatistics ALTER COLUMN id SET DEFAULT nextval('outdoorunitstatistics_id_seq1'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunitstatistics_daily ALTER COLUMN id SET DEFAULT nextval('outdoorunitstatistics_daily_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunitstatistics_monthly ALTER COLUMN id SET DEFAULT nextval('outdoorunitstatistics_monthly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunitstatistics_weekly ALTER COLUMN id SET DEFAULT nextval('outdoorunitstatistics_weekly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunitstatistics_yearly ALTER COLUMN id SET DEFAULT nextval('outdoorunitstatistics_yearly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY permissions ALTER COLUMN id SET DEFAULT nextval('permissions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_daily ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_daily_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_monthly ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_monthly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_weekly ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_weekly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_yearly ALTER COLUMN id SET DEFAULT nextval('power_consumption_capacity_yearly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pulse_meter ALTER COLUMN id SET DEFAULT nextval('pulse_meter_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ratingmaster ALTER COLUMN id SET DEFAULT nextval('ratingmaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rc_prohibition ALTER COLUMN id SET DEFAULT nextval('rc_prohibition_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcoperation_master ALTER COLUMN id SET DEFAULT nextval('deviceattribute_master_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcuser_action ALTER COLUMN id SET DEFAULT nextval('devicecommand_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics ALTER COLUMN id SET DEFAULT nextval('refrigerantcircuit_statistics_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics_daily ALTER COLUMN id SET DEFAULT nextval('refrigerantcircuit_statistics_daily_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics_monthly ALTER COLUMN id SET DEFAULT nextval('refrigerantcircuit_statistics_monthly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics_weekly ALTER COLUMN id SET DEFAULT nextval('refrigerantcircuit_statistics_weekly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics_yearly ALTER COLUMN id SET DEFAULT nextval('refrigerantcircuit_statistics_yearly_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolefunctionalgrp ALTER COLUMN id SET DEFAULT nextval('rolefunctionalgrp_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolehistory ALTER COLUMN id SET DEFAULT nextval('rolehistory_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolepermissions ALTER COLUMN id SET DEFAULT nextval('rolepermissions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY roles ALTER COLUMN id SET DEFAULT nextval('roles_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY session ALTER COLUMN id SET DEFAULT nextval('session_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY temp_groupunitdata ALTER COLUMN id SET DEFAULT nextval('temp_groupunitdata_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY timeline ALTER COLUMN id SET DEFAULT nextval('timeline_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY timezonemaster ALTER COLUMN id SET DEFAULT nextval('timezonemaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_notification_settings ALTER COLUMN id SET DEFAULT nextval('user_notification_settings_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraudit ALTER COLUMN id SET DEFAULT nextval('useraudit_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usermangementhistory ALTER COLUMN id SET DEFAULT nextval('usermangementhistory_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vrfparameter_statistics ALTER COLUMN id SET DEFAULT nextval('vrfparameter_statistics_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY winddirection_master ALTER COLUMN id SET DEFAULT nextval('winddirection_master_id_seq'::regclass);


--
-- Data for Name: adapters; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO adapters VALUES (3, NULL, '47', NULL, '2015-08-13 20:14:38', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, 3, 'CA3', NULL, NULL, NULL, NULL);
INSERT INTO adapters VALUES (4, NULL, NULL, '10', '2015-12-11 15:17:24.544', '2015-12-15 00:00:00', NULL, 323232, 323232, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, 1, NULL, '1', 5, 'ada', '[2:{A0000000000F02000000}]', 2, 'A0000000000F', NULL);


--
-- Name: adapters_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('adapters_id_seq', 4, true);


--
-- Data for Name: adapters_model; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO adapters_model VALUES (2, 'Model1');
INSERT INTO adapters_model VALUES (3, 'model2');
INSERT INTO adapters_model VALUES (4, 'model3');
INSERT INTO adapters_model VALUES (5, 'model4');
INSERT INTO adapters_model VALUES (6, 'model5');
INSERT INTO adapters_model VALUES (7, 'model6');
INSERT INTO adapters_model VALUES (8, 'model7');
INSERT INTO adapters_model VALUES (9, 'model8');


--
-- Data for Name: alarmanotificationmailtemp; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: alarmstatistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('alarmstatistics_id_seq', 898, true);


--
-- Data for Name: availabletemp; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: availabletemp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('availabletemp_id_seq', 1, false);


--
-- Data for Name: companies; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO companies VALUES (1, 'RSI', '2015-08-13 11:10:00', NULL, NULL, 'Panasonic@Bedok', '2015-08-13 11:10:00', 'RSI', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO companies VALUES (2, 'RSI', '2015-08-13 11:10:00', NULL, NULL, 'Panasonic@bedok', '2015-08-13 11:10:00', 'RSI', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO companies VALUES (3, 'RSI', '2015-08-13 11:10:00', NULL, NULL, 'Panasonic@bedok', '2015-08-13 11:10:00', 'RSI', NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Name: companies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('companies_id_seq', 1, true);


--
-- Name: companiesuser_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('companiesuser_seq', 9999999, false);


--
-- Data for Name: companiesusers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO companiesusers VALUES (1, 1, 8, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 1);
INSERT INTO companiesusers VALUES (1, 1, 21, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 2);
INSERT INTO companiesusers VALUES (1, 1, 25, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 3);
INSERT INTO companiesusers VALUES (1, 1, 30, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 4);
INSERT INTO companiesusers VALUES (1, 1, 31, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 5);
INSERT INTO companiesusers VALUES (1, 1, 33, '2015-09-21 19:23:11.459', '2015-09-21 19:23:11.459', '1', '1', 6);
INSERT INTO companiesusers VALUES (1, 4, 9, '2015-09-21 19:23:11.459', '2015-09-21 19:23:11.459', '1', '1', 7);
INSERT INTO companiesusers VALUES (1, 5, 45, '2015-09-30 12:20:01.801', NULL, NULL, NULL, 8);
INSERT INTO companiesusers VALUES (2, 13, 53, '2015-09-09 00:00:00', '2015-09-09 00:00:00', '1', '1', 9);
INSERT INTO companiesusers VALUES (3, 1, 1, '2015-09-29 19:07:00.922', NULL, NULL, NULL, 10);
INSERT INTO companiesusers VALUES (3, 2, 8, '2015-09-29 19:07:08.285', NULL, NULL, NULL, 11);
INSERT INTO companiesusers VALUES (3, 3, 14, '2015-09-29 19:08:03.51', NULL, NULL, NULL, 12);
INSERT INTO companiesusers VALUES (3, 4, 18, '2015-09-29 19:08:11.279', NULL, NULL, NULL, 13);
INSERT INTO companiesusers VALUES (3, 5, 1, '2015-09-30 12:17:59.215', NULL, NULL, NULL, 14);


--
-- Data for Name: companyrole; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO companyrole VALUES (1, '11a', NULL, NULL);


--
-- Name: companysites_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('companysites_seq', 1, false);


--
-- Name: deviceattribute_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('deviceattribute_master_id_seq', 6, true);


--
-- Name: devicecommand_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('devicecommand_id_seq', 1, false);


--
-- Data for Name: distribution_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO distribution_group VALUES (1, 'dist1', '2015-12-11 00:00:00', 1, 'VRF', 'Working Time');


--
-- Name: distribution_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('distribution_group_id_seq', 1, true);


--
-- Data for Name: efficiency_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO efficiency_rating VALUES (2, 'A++', 3, 3.3999999999999999);
INSERT INTO efficiency_rating VALUES (3, 'A+', 2.7999999999999998, 3.2000000000000002);
INSERT INTO efficiency_rating VALUES (4, 'A', 2.6000000000000001, 3);
INSERT INTO efficiency_rating VALUES (5, 'B', 2.3999999999999999, 2.7999999999999998);
INSERT INTO efficiency_rating VALUES (6, 'C', 2.2000000000000002, 2.6000000000000001);
INSERT INTO efficiency_rating VALUES (1, 'A+++', 50.200000000000003, 3.6000000000000001);


--
-- Name: efficiency_rating_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('efficiency_rating_id_seq', 1, false);


--
-- Data for Name: errorcode_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO errorcode_master VALUES (6, 'A06', 'Critical', 'AC problem', 'Engine start failure', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (31, 'C01', 'Non Critical', 'AC problem', 'Poor setting on Control Address(duplicated)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (32, 'C02', 'Non Critical', 'AC problem', 'Discord of number of units in central process control', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (33, 'C03', 'Non Critical', 'AC problem', 'Miswiring of central process control', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (34, 'C04', 'NOn Critical', 'AC problem', 'Misconnection of central process control', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (35, 'C05', 'Non Critical', 'AC problem', 'Transmission error of central process control', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (36, 'C06', 'Non Critical', 'AC problem', 'Reception error of central process control', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (37, 'C12', 'Non Critical', 'AC problem', 'Lump/Batch alarm by local adapter', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (38, 'C16', 'Non Critical', 'AC problem', 'Poor transmission of the adapter to the unit', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (39, 'C17', 'NOn Critical', 'AC problem', 'Poor reception of the adapter from the unit', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (40, 'C18', 'Non Critical', 'AC problem', 'Duplicate central address in adaptor', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (41, 'C19', 'Non Critical', 'AC problem', 'Duplicate adaptor address', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (42, 'C20', 'Non Critical', 'AC problem', 'PAC,GHP-type mixed in amy-adapter/communications adapter', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (43, 'C21', 'Non Critical', 'AC problem', 'Non volatile memory in the adapter is abnormal', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (44, 'C22', 'NOn Critical', 'AC problem', 'Poor settingn of  adapter address', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (45, 'C23', 'Non Critical', 'AC problem', 'Host terminal failure(Software)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (46, 'C24', 'Non Critical', 'AC problem', 'Host terminal failure(Hardware)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (47, 'C25', 'Non Critical', 'AC problem', 'Host terminal transaction failure ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (48, 'C26', 'Non Critical', 'AC problem', 'Communication failure in host terminal', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (49, 'C28', 'NOn Critical', 'AC problem', 'Reception error of S-DDC from host terminal', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (30, 'A30', 'Non Critical', 'desc.cust.a30', 'desc.maint.a30', 3, NULL, NULL, NULL, NULL, 'Alarm', 'cm.maint.2way.a30', 'cm.maint.3way.a30', 'cm.cust.a30');
INSERT INTO errorcode_master VALUES (50, 'C29', 'Non Critical', 'AC problem', 'Initialization  failure of S-DDC', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (66, 'E15', 'Non Critical', 'AC problem', 'Alarm for auto address setting.(Number of indoor units is too small.)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (67, 'E16', 'Non Critical', 'AC problem', 'Alarm for auto address setting.(Number of indoor units is too large.)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (68, 'E17', 'Non Critical', 'AC problem', ' Poor transmission from indoor unit to indoor unit. ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (69, 'E18', 'Non Critical', 'AC problem', 'Poor communication of group processing control caused by miswiring', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (71, 'E20', 'Non Critical', 'AC problem', 'No indoor unit in automatic address', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (51, 'C31', 'Non Critical', 'AC problem', 'Configuration change (detected by adaptor)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (52, 'E01', 'Non Critical', 'AC problem', 'Poor reception of the signal on the remote controller / Remote controller is detecting error signal from indoor unit. ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (53, 'E02', 'Non Critical', 'AC problem', 'Poor transmission of the signal on the remote controller.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (72, 'E21', 'Critical', 'AC problem', 'PCB (outdoor control board) trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (73, 'E22', 'Critical', 'AC problem', ' Thermistor (outdoor control board sensor) trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (74, 'E23', 'Non Critical', 'AC problem', 'Poor transmission of CCU to outdoor sub bus.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (75, 'E24', 'Non Critical', 'AC problem', 'Poor reception of CCU from outdoor sub bus .', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (76, 'E25', 'Non Critical', 'AC problem', 'Poor setting of outdoor sub bus address. (duplicated)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (77, 'E26', 'Critical', 'AC problem', 'Discord of number of outdoor sub bus units / Outdoor unit sub bus number mismatch / Mismatch in outdoor unit quantity', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (56, 'E05', 'Non Critical', 'AC problem', 'Poor transmission of the indoor unit to the outdoor unit.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (57, 'E06', 'Non Critical', 'AC problem', 'Poor reception of the outdoor unit from the indoor unit.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (58, 'E07', 'Non Critical', 'AC problem', 'Poor transmission of the outdoor unit to the indoor unit.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (59, 'E08', 'Non Critical', 'AC problem', 'Duplicated setting of indoor units address.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (60, 'E09', 'Non Critical', 'AC problem', 'Setting multiple master remote controllers.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (78, 'E27', 'Critical', 'AC problem', ' Miswiring of outdoor sub bus.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (79, 'E28', 'Non Critical', 'AC problem', 'Misconnection of outdoor units.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (80, 'E29', 'Non Critical', 'AC problem', 'Outdoor unit serial reception failure / Outdoor unit failed to receive communication from relay control unit ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (81, 'E30', 'Non Critical', 'AC problem', 'Poor transmission of outdoor serial / Outdoor unit serial transmission failure', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (82, 'E31', 'Critical', 'AC problem', 'Outdoor unit Unit internal communications failure', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (83, 'F01', 'Critical', 'AC problem', ' Abnormal sensor for the inlet temp. E1 on the heat exchanger of the indoor unit.  ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (84, 'F02', 'Non Critical', 'AC problem', 'Abnormal sensor for mid-point temp. E2 on the heat exchanger of the indoor unit. ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (85, 'F03', 'Non Critical', 'AC problem', ' Abnormal sensor for the outlet temp. E3 on the heat exchanger of the indoor unit ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (61, 'E10', 'Non Critical', 'AC problem', 'Indoor unit - 3-way + signal PCB serial transmission failure', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (62, 'E11', 'Non Critical', 'AC problem', 'Poor reception of the indoor unit from the signal output P.C.B..', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (63, 'E12', 'Non Critical', 'AC problem', 'No auto address setting. (Auto address is in setting) / Inhibit starting automatic address setting ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (64, 'E13', 'Non Critical', 'AC problem', 'Poor transmission of indoor unit to the remote controller.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (65, 'E14', 'Non Critical', 'AC problem', 'Duplicated address of master indoor unit in group processing control.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (95, 'F13', 'Non Critical', 'AC problem', ' Abnormal sensor for cooling water temperature. ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (86, 'F04', 'Non Critical', 'AC problem', 'Abnormal sensor for the outlet temperature of the PC compressor (comp. No.1)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (88, 'F06', 'Non Critical', 'AC problem', ' Abnormal sensor for the inlet temp. on the heat exchanger 1 of the outdoor unit. ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (89, 'F07', 'Non Critical', 'AC problem', 'Abnormal sensor for the outlet temp. on the heat exchanger 1 of the outdoor unit.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (90, 'F08', 'Non Critical', 'AC problem', ' Abnormal sensor for outdoor temperature. ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (87, 'F05', 'Non Critical', 'AC problem', ' Abnormal sensor for the outlet temperature of the AC compressor (comp. No.2)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (91, 'F09', 'Non Critical', 'AC problem', ' Actuation of scroll compressor protective thermostat.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (92, 'F10', 'Non Critical', 'AC problem', ' Abnormal sensor for intake temperature of indoor unit / WHE: Cold/hot water inlet sensor trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (93, 'F11', 'Non Critical', 'AC problem', ' Abnormal sensor for discharge temperature of indoor unit / WHE Cold/hot water outlet sensor trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (94, 'F12', 'Non Critical', 'AC problem', ' Compressor inlet temperature sensor failure ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (96, 'F14', 'Non Critical', 'AC problem', ' Abnormal sensor for engine room temperature.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (97, 'F15', 'Non Critical', 'AC problem', ' Outdoor unit heat exchanger intermediate temperature sensor failure ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (98, 'F16', 'Non Critical', 'AC problem', ' Compressor inlet/outlet pressure sensor failure / High-pressure sensor trouble ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (99, 'F17', 'Non Critical', 'AC problem', ' Low-pressure sensor trouble ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (100, 'F18', 'Non Critical', 'AC problem', ' Abnormal sensor for exhaust gas temperature. ', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (101, 'F19', 'Non Critical', 'AC problem', 'Abnormal sensor for compressor bottom surface temperature.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (102, 'F20', 'Non Critical', 'AC problem', 'ICE sensor / Clutch coil temp. sensor trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (103, 'F21', 'Non Critical', 'AC problem', 'Abnormal sensor for compressor 3 current.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (104, 'F22', 'Non Critical', 'AC problem', 'Abnormal sensor for discharge gas temperature from compressor 3.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (110, 'F28', 'Non Critical', 'AC problem', 'Abnormal sensor for compressor 2 current.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (111, 'F29', 'Non Critical', 'AC problem', 'Abnormal non-volatile memory (EEPROM) in indoor unit.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (112, 'F30', 'Non Critical', 'AC problem', 'Abnormal timer (RTC).', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (113, 'F31', 'Non Critical', 'AC problem', 'Abnormal non-volatile memory (EEPROM) in outdoor unit.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (114, 'H01', 'Non Critical', 'AC problem', 'Incorrect current value for compressor 1 .(over current)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (115, 'H02', 'Non Critical', 'AC problem', 'Incorrect current value for compressor 1 .(locked compressor)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (116, 'H03', 'Non Critical', 'AC problem', 'CT sensor is dislocated from compressor 1, Short circuited / Current is not detected when comp. No. 1 is ON.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (105, 'F23', 'Non Critical', 'AC problem', 'Abnormal sensor for refrigerant gas temperature in the heat exchanger 2 (In).', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (117, 'H04', 'Non Critical', 'AC problem', 'Protective thermostat for scroll compressor 1.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (106, 'F24', 'Non Critical', 'AC problem', 'Abnormal sensor for liquid refrigerant temperature in the heat exchanger 2 (Out).', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (118, 'H05', 'Non Critical', 'AC problem', 'Protective thermostat for compressor 1 is slipped off / not installed', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (107, 'F25', 'Non Critical', 'AC problem', 'Abnormal sensor for heat exchanger coil 1 temperature.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (119, 'H06', 'Non Critical', 'AC problem', 'Activation of low pressure switch.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (108, 'F26', 'Non Critical', 'AC problem', 'Abnormal sensor for heat exchanger coil 2 temperature.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (120, 'H07', 'Non Critical', 'AC problem', 'No oil in compressor 1. Low oil level', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (109, 'F27', 'Non Critical', 'AC problem', 'Abnormal sensor for compressor 1 current.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (121, 'H08', 'Non Critical', 'AC problem', 'Chattering of magnet switch 1 .(Multi)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (122, 'H09', 'Non Critical', 'AC problem', 'Chattering of magnet switch 1 .(Multi)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (123, 'H10', 'Non Critical', 'AC problem', 'Defective sensor for crank case heater 1.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (124, 'H11', 'Non Critical', 'AC problem', 'Incorrect current value for compressor 2 .(over current)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (125, 'H12', 'Non Critical', 'AC problem', 'Incorrect current value for compressor 2 .(locked compressor)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (126, 'H13', 'Non Critical', 'AC problem', 'CT sensor is dislocated from compressor 2, short circuit / Current is not detected when comp. No.2 is ON.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (127, 'H14', 'Non Critical', 'AC problem', 'Protective thermostat for scroll compressor 2.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (128, 'H15', 'Non Critical', 'AC problem', 'Protective thermostat for scroll compressor 2 is dislocated.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (129, 'H16', 'Critical', 'AC problem', 'No oil in compressor 2.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (130, 'H17', 'Non Critical', 'AC problem', 'Sensed unbalanced power voltage.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (131, 'H18', 'Non Critical', 'AC problem', ' Chattering of magnet switch 1 (Espacio).', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (132, 'H19', 'Non Critical', 'AC problem', 'Chattering of magnet switch 2 ..', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (133, 'H20', 'Non Critical', 'AC problem', 'Defective sensor for crank case heater 2.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (134, 'H21', 'Non Critical', 'AC problem', 'Incorrect current value for compressor 3 .(over current)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (135, 'H22', 'Non Critical', 'AC problem', 'Incorrect current value for compressor 3 .(locked compressor)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (136, 'H23', 'Non Critical', 'AC problem', ' CT sensor is dislocated from compressor 3, short circuited.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (137, 'H24', 'Non Critical', 'AC problem', ' Protective thermostat for scroll compressor 3.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (138, 'H25', 'Non Critical', 'AC problem', ' Protective thermostat for scroll compressor 3 is slipped off / Compressor 3 discharge temperature sensor disconnected', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (139, 'H26', 'Critical', 'AC problem', 'No oil in compressor 3.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (140, 'H27', 'Non Critical', 'AC problem', 'Incorrect connection of oil sensor 2.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (141, 'H28', 'Non Critical', 'AC problem', 'Incorrect connection of oil sensor 3.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (142, 'H29', 'Non Critical', 'AC problem', 'Chattering of magnet switch 3.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (143, 'H30', 'Non Critical', 'AC problem', ' Defective sensor for crank case heater 3.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (144, 'H31', 'Critical', 'AC problem', ' HIC trouble alarm / IPM trip (IPM current or temperature)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (145, 'L01', 'Non Critical', 'AC problem', ' Incorrect address for indoor unit. (No master indoor unit.)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (146, 'L02', 'Non Critical', 'AC problem', 'Mismatch of indoor and outdoor units. (Non-GHP device present)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (147, 'L03', 'Non Critical', 'AC problem', 'Setting plural master indoor units for group control.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (148, 'L04', 'Non Critical', 'AC problem', 'Duplicate setting of system address (outdoor unit).', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (149, 'L05', 'Non Critical', 'AC problem', 'Duplicate setting of priority indoor unit. (for priority indoor unit)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (150, 'L06', 'Non Critical', 'AC problem', ' Duplicate setting of priority indoor unit. (for other than priority indoor unit.)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (151, 'L07', 'Non Critical', 'AC problem', ' Group control wiring to individually controlled indoor unit.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (152, 'L08', 'Non Critical', 'AC problem', 'No Setting of indoor unit address.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (153, 'L09', 'Non Critical', 'AC problem', 'No Setting of indoor unit capacity.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (154, 'L10', 'Non Critical', 'AC problem', 'No Setting of outdoor unit capacity.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (155, 'L11', 'Non Critical', 'AC problem', 'Mis-wiring of group control wire. (Espacio)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (156, 'L12', 'Non Critical', 'AC problem', 'Discord of indoor unit s capacity. (Multi)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (157, 'L13', 'Non Critical', 'AC problem', 'Poor setting of indoor unit s type.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (158, 'L14', 'Non Critical', 'AC problem', ' Discord of power frequency. (50,60Hz)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (159, 'L15', 'Non Critical', 'AC problem', 'Defective double duct bearing.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (160, 'L16', 'Non Critical', 'AC problem', 'Water heat exchanger unit setting failure', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (161, 'L17', 'Non Critical', 'AC problem', 'Mis-matched connection of outdoor units that have different kinds of refrigerant.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (162, 'L18', 'Critical', 'AC problem', '4-way valve operation failure.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (163, 'L19', 'Non Critical', 'AC problem', 'Water heat exchanger unit duplicate parallel address', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (164, 'L20', 'Non Critical', 'AC problem', 'Duplicated central control addresses of indoor units at local adaptor', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (165, 'L21', 'Non Critical', 'AC problem', ' Gas type setting failure', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (166, 'P01', 'Critical', 'AC problem', 'Abnormal indoor fan motor / Thermal protector in indoor unit fan motor is activated', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (167, 'P02', 'Non Critical', 'AC problem', 'Actuation of outdoor fan motor, protective thermostats for CM and AC. / Power supply voltage is unusual. (The voltage is more than 260 V or less than 160 V between L and N phase.)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (168, 'P03', 'Non Critical', 'AC problem', 'High discharge temperature of compressor 1.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (169, 'P04', 'Non Critical', 'AC problem', 'Actuation of high refrigerant pressure switch.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (170, 'P05', 'Non Critical', 'AC problem', 'Reversed phase (or missing phase) of the power source detected, capacity mismatch', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (11, 'A11', 'Non Critical', 'desc.cust.a11', 'desc.maint.a11', 3, NULL, NULL, NULL, NULL, 'Alarm', 'cm.maint.2way.a11', 'cm.maint.3way.a11', 'cm.cust.a11');
INSERT INTO errorcode_master VALUES (15, 'A15', 'Critical', 'AC problem', 'Starter power supply output short-circuit', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (21, 'A21', 'Non Critical', 'AC problem', 'Abnormal cooling water level', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (25, 'A25', 'Critical', 'AC problem', 'Clutch trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (54, 'E03', 'NOn Critical', 'AC problem', 'Poor reception of the indoor unit from the remote controller
(central process control)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (55, 'E04', 'Non Critical', 'AC problem', 'Poor reception of indoor unit from the outdoor unit/When turning on the power supply,
', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (196, 'oil', 'Critical', 'AC problem', 'Oil Change Time Alarm', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (1, 'A01', 'Critical', 'desc.cust.a01', 'desc.maint.a01', 3, NULL, '2015-08-13 00:00:00', '2015-08-14 00:00:00', 1, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (2, 'A02', 'Critical', 'desc.cust.a02', 'desc.maint.a02', 3, NULL, '2015-08-14 00:00:00', '2015-08-15 00:00:00', 2, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (28, 'A28', 'Critical', 'AC problem', 'Generator trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (3, 'A03', 'Critical', 'desc.cust.a03', 'desc.maint.a03', 1, NULL, '2015-08-13 00:00:00', '2015-08-13 00:00:00', 1, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (4, 'A04', 'Critical', 'AC problem', 'Engine low speed failure', 2, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (5, 'A05', 'Critical', 'AC problem', 'Ignition power supply failure', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (7, 'A07', 'Critical', 'desc.cust.a07', 'desc.maint.a07', 1, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (8, 'A08', 'Critical', 'AC problem', 'Engine stall/stop', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (9, 'A09', 'Critical', 'desc.cust.a09', 'desc.maint.a09', 2, NULL, NULL, NULL, NULL, 'Alarm', 'cm.maint.2way.a09', 'cm.maint.3way.a09', 'cm.cust.a09');
INSERT INTO errorcode_master VALUES (10, 'A10', 'Critical', 'AC problem', 'High exhaust gas temeprature', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (12, 'A12', 'Critical', 'AC problem', 'Throttle(stepping motor)failure', 1, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (13, 'A13', 'Critical', 'AC problem', 'Fuel gas adjustment valve failure', 2, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (14, 'A14', 'Critical', 'AC problem', 'Engine oil pressure switch failure', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (16, 'A16', 'Critical', 'AC problem', 'Starter locked', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (17, 'A17', 'Critical', 'AC problem', 'CT failure(starter current detection failure)', 1, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (19, 'A19', 'Non Critical', 'AC problem', 'Wax 3 way valve trouble', 2, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (20, 'A20', 'Critical', 'AC problem', 'High cooling water temperature', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (22, 'A22', 'Critical', 'AC problem', 'Cooling water pump overload', 1, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (23, 'A23', 'Non Critical', 'AC problem', 'Crank angle sensor trouble', 2, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (24, 'A24', 'Non Critical', 'AC problem', 'Cam angle sensor trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (27, 'A27', 'Non Critical', 'AC problem', 'Catalyst temp.trouble', 5, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (26, 'A26', 'Non Critical', 'AC problem', 'Misfire', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (29, 'A29', 'Critical', 'AC problem', 'Converter trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (70, 'E19', 'Non Critical', 'AC problem', '3 or more master units (Individual twin multi type) in one system', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (171, 'P06', 'Non Critical', 'AC problem', 'Mismatching of Model type or Capacity in outdoor unit', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (172, 'P07', 'Non Critical', 'AC problem', ' DC-current overload, heat radiator temperature abnormal in outdoor unit', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (173, 'P09', 'Non Critical', 'AC problem', 'Poor connection of ceiling panel connector for indoor unit.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (174, 'P10', 'Non Critical', 'AC problem', 'Actuation of indoor unit float switch.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (175, 'P11', 'Non Critical', 'AC problem', 'Water heat exchanger unit freezing trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (176, 'P12', 'Non Critical', 'AC problem', 'Operation of protective function of fan inverter / Indoor unit DC fan trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (178, 'P14', 'Non Critical', 'AC problem', 'O2 sensor (detects low oxygen level) activated.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (179, 'P15', 'Non Critical', 'AC problem', ' No refrigerant gas.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (180, 'P16', 'Critical', 'AC problem', 'Compressor 1 overcurrent', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (181, 'P17', 'Non Critical', 'AC problem', 'Abnormal discharge gas temperature from compressor 2', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (182, 'P18', 'Non Critical', 'AC problem', 'Abnormal discharge gas temperature from compressor 3 / GHP: Bypass valve trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (183, 'P19', 'Non Critical', 'AC problem', '4 way valve lock trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (184, 'P20', 'Non Critical', 'AC problem', 'Abnormally high refrigerant gas pressure.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (185, 'P21', 'Non Critical', 'AC problem', 'Abnormal pressure difference in compressor oil.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (186, 'P22', 'Non Critical', 'AC problem', 'Fan motor trouble / Outdoor unit fan motor is unusual.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (187, 'P23', 'Critical', 'AC problem', 'Water heat exchanger unit interlock trouble', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (188, 'P24', 'Non Critical', 'AC problem', 'ice storage tank unit abnormaly', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (189, 'P25', 'Non Critical', 'AC problem', 'ice storage tank unit abnormaly', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (190, 'P26', 'Non Critical', 'AC problem', 'Inverter compressor high-frequency overcurrent alarm', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (191, 'P27', 'Non Critical', 'AC problem', 'Protective alarm for freezing of coolant water', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (192, 'P28', 'Non Critical', 'AC problem', 'Alarm: Out of range for coolant water set temperature', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (193, 'P29', 'Non Critical', 'AC problem', 'Inverter compressor missing phase or lock alarm', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (194, 'P30', 'Critical', 'AC problem', 'Abnormal sub indoor units in group control. (Centralized processing control detected the abnormality.)', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (195, 'P31', 'Non Critical', 'AC problem', 'Abnormal group control.', 3, NULL, NULL, NULL, NULL, 'Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (199, 'N03', 'Non Critical', 'Probably window has left open', 'Window left open detected', 3, NULL, NULL, NULL, NULL, 'Alert', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (177, 'P13', 'Critical', 'desc.cust.p13', 'desc.maint.p13', 3, NULL, NULL, NULL, NULL, 'Alarm', 'cm.maint.2way.p13', 'cm.maint.3way.p13', 'cm.cust.p13');
INSERT INTO errorcode_master VALUES (200, 'N04', 'Non Critical', 'AC problem', 'Air short circuit detected in Heating Mode', 3, NULL, NULL, NULL, NULL, 'Alert', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (198, 'N02', 'Non Critical', 'Oil Change required for Compressor', 'Oil Change required for Compressor', 3, NULL, NULL, NULL, NULL, 'Pre-Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (197, 'N01', 'Non Critical', 'Regular Maintenance for Compressor is overdue', 'Regular Maintenance for Compressor is overdue', 3, NULL, NULL, NULL, NULL, 'Pre-Alarm', NULL, NULL, NULL);
INSERT INTO errorcode_master VALUES (201, 'N05', 'Non Critical', 'desc.cust.n05', 'desc.maint.n05', 3, NULL, NULL, NULL, NULL, 'Alert', 'cm.maint.2way.n05', 'cm.maint.3way.n05', 'cm.cust.n05');


--
-- Data for Name: fanspeed_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO fanspeed_master VALUES (1, 'High');
INSERT INTO fanspeed_master VALUES (2, 'Medium');
INSERT INTO fanspeed_master VALUES (3, 'Low');
INSERT INTO fanspeed_master VALUES (4, 'Auto');


--
-- Name: fanspeed_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('fanspeed_master_id_seq', 1, false);


--
-- Data for Name: functionalgroup; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO functionalgroup VALUES (1, 'AC Settings', 2);
INSERT INTO functionalgroup VALUES (2, 'Visualization', 3);
INSERT INTO functionalgroup VALUES (3, 'Notification', 1);
INSERT INTO functionalgroup VALUES (4, 'Schedule', 1);
INSERT INTO functionalgroup VALUES (5, 'System Settings', 3);
INSERT INTO functionalgroup VALUES (6, 'CA Installation & Maintenance', 2);
INSERT INTO functionalgroup VALUES (7, 'Operation Log(Panasonic)', 2);
INSERT INTO functionalgroup VALUES (8, 'Home', 1);


--
-- Name: functionalgroup_functional_groupid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('functionalgroup_functional_groupid_seq', 8, true);


--
-- Data for Name: functionalgroup_permission; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO functionalgroup_permission VALUES (1, 1, 15);
INSERT INTO functionalgroup_permission VALUES (2, 2, 16);
INSERT INTO functionalgroup_permission VALUES (3, 2, 17);
INSERT INTO functionalgroup_permission VALUES (4, 2, 18);
INSERT INTO functionalgroup_permission VALUES (5, 3, 25);
INSERT INTO functionalgroup_permission VALUES (6, 3, 26);
INSERT INTO functionalgroup_permission VALUES (7, 3, 27);
INSERT INTO functionalgroup_permission VALUES (8, 3, 28);
INSERT INTO functionalgroup_permission VALUES (9, 3, 29);
INSERT INTO functionalgroup_permission VALUES (10, 3, 30);
INSERT INTO functionalgroup_permission VALUES (12, 3, 32);
INSERT INTO functionalgroup_permission VALUES (13, 3, 33);
INSERT INTO functionalgroup_permission VALUES (14, 3, 34);
INSERT INTO functionalgroup_permission VALUES (11, 3, 31);


--
-- Data for Name: gasheatmeter_data; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: gasheatmeter_data_daily; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: gasheatmeter_data_daily_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_daily_id_seq', 1, false);


--
-- Name: gasheatmeter_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_id_seq', 1, false);


--
-- Data for Name: gasheatmeter_data_monthly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: gasheatmeter_data_monthly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_monthly_id_seq', 1, false);


--
-- Data for Name: gasheatmeter_data_weekly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: gasheatmeter_data_weekly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_weekly_id_seq', 1, false);


--
-- Data for Name: gasheatmeter_data_yearly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: gasheatmeter_data_yearly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasheatmeter_data_yearly_id_seq', 1, false);


--
-- Data for Name: ghpparameter_statistics; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: ghpparameter_statistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ghpparameter_statistics_id_seq', 1, false);


--
-- Data for Name: group_level; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO group_level VALUES (1, 'Site Group ID_Lvl_1', 'Country');
INSERT INTO group_level VALUES (2, 'Site Group ID_Lvl_2', 'State');
INSERT INTO group_level VALUES (3, 'Site Group ID_Lvl_3', 'Town');
INSERT INTO group_level VALUES (4, 'Site Group ID_Lvl_4', 'Area');
INSERT INTO group_level VALUES (5, 'Site Group ID_Lvl_5', 'Street');
INSERT INTO group_level VALUES (6, 'Site ID', 'Building');
INSERT INTO group_level VALUES (7, 'Logical ID_Lvl_1', 'Floor');
INSERT INTO group_level VALUES (8, 'Logical ID_Lvl_2', 'Wing');
INSERT INTO group_level VALUES (9, 'Logical ID_Lvl_3', 'Central');
INSERT INTO group_level VALUES (10, 'Logical ID_Lvl_4', 'Cooked');
INSERT INTO group_level VALUES (11, 'Logical ID_Lvl_5', 'Raw');
INSERT INTO group_level VALUES (12, 'Control Group ID', 'Department');


--
-- Data for Name: groupcategory; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO groupcategory VALUES (1, 'Site Group');
INSERT INTO groupcategory VALUES (2, 'Site');
INSERT INTO groupcategory VALUES (3, 'Logical Group');
INSERT INTO groupcategory VALUES (4, 'Control Group');


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO groups VALUES (20, '1', '2015-08-13 12:34:00', NULL, 4, 'Meeting Room 4', '|1|2|4|20|', NULL, '2015-08-13 12:34:00', '1', 1, 4, '1', 1, NULL, NULL, 'SG020', NULL, 4, 1.12340160000000, 101.12345600000000, 1.12340160000000, 101.12345600000000, 1.12340060000000, 1.12346000000000, NULL, 'Level 1,Block A,Level 3,Meeting Room 4', 0);
INSERT INTO groups VALUES (25, '1', '2015-08-13 12:57:00', NULL, 1, 'Block B', '|25|', NULL, '2015-08-13 12:57:00', '1', 1, NULL, '1', 1, NULL, NULL, 'SG025', NULL, 1, 1.12340190000000, 101.12345900000000, 1.12340190000000, 101.12345900000000, 1.12340090000000, 1.12346300000000, NULL, 'Block B', 0);
INSERT INTO groups VALUES (27, '1', '2015-08-13 13:01:00', NULL, 2, 'Level 2', '|25|27|', 'panasonic_demo.svg', '2015-08-13 13:01:00', '1', 1, 25, '2', 1, NULL, NULL, 'SG027', 2.4399999999999999, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 2,Block B', 0);
INSERT INTO groups VALUES (18, '1', '2015-08-13 12:34:00', NULL, 12, 'Personnel Filing Room', '|1|2|3|5|6|8|9|10|11|14|16|18|', NULL, '2015-08-13 12:34:00', '1', 1, 16, '1', 4, NULL, NULL, 'CG018', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Meeting Room 3,Meeting Room 2,Panasonic Asia,Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Personnel Filing Room,Energy Solutions Group,Meeting Room 2', 0);
INSERT INTO groups VALUES (15, '1', '2015-08-13 12:34:00', NULL, 10, 'Open Office', '|1|2|3|5|6|8|9|10|11|15|', NULL, '2015-08-13 12:34:00', '1', 1, 11, '1', 3, NULL, NULL, 'LG015', NULL, 10, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Open Office,Panasonic Asia,Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Energy Solutions Group,Meeting Room 2', 0);
INSERT INTO groups VALUES (46, '1', '2015-08-13 13:17:00', NULL, 6, ' Meeting Rooms', '|25|27|30|39|42|46|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 42, '3', 4, NULL, NULL, 'CG046', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2, Meeting Rooms,Block B,PRDCSG Office, Test Rooms,Meeting Room 3', 0);
INSERT INTO groups VALUES (51, '1', '2015-08-13 13:01:00', NULL, 1, 'Level 3 Company 2', '|51|', 'panasonic_demo.svg', '2015-08-13 13:01:00', '1', 2, NULL, '2', 1, NULL, NULL, 'SG051', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 3 Company 2', 0);
INSERT INTO groups VALUES (41, '1', '2015-08-13 13:17:00', NULL, 5, 'Meeting Rooms', '|25|27|30|39|41|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 39, '3', 3, NULL, NULL, 'LG041', NULL, 8, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Meeting Rooms,Block B,PRDCSG Office,Meeting Room 3', 0);
INSERT INTO groups VALUES (44, '1', '2015-08-13 13:17:00', NULL, 6, 'Store Rooms', '|25|27|30|39|41|44|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 41, '3', 4, NULL, NULL, 'CG044', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Store Rooms,Meeting Rooms,Block B,PRDCSG Office,Meeting Room 3', 0);
INSERT INTO groups VALUES (11, '1', '2015-08-13 12:29:00', NULL, 9, 'Panasonic Asia', '|1|2|3|5|6|8|9|10|11|', NULL, '2015-08-13 12:29:00', '1', 1, 10, '1', 3, NULL, NULL, 'LG011', NULL, 9, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Panasonic Asia,Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Energy Solutions Group,Meeting Room 2', 0);
INSERT INTO groups VALUES (29, '1', '2015-08-13 13:03:00', NULL, 3, 'PESAP Office', '|25|26|29|', 'panasonic_demo.svg', '2015-08-13 13:03:00', '1', 1, 26, '3', 1, NULL, NULL, 'SG029', 1.98, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'PESAP Office,Level 1,Block B', 0);
INSERT INTO groups VALUES (13, '1', '2015-08-13 12:31:00', NULL, 10, 'Meeting Room 1', '|1|2|3|5|6|8|9|10|11|13|', NULL, '2015-08-13 12:31:00', '1', 1, 11, '1', 4, NULL, NULL, 'CG013', NULL, 12, 1.12340150000000, 101.12345500000000, 1.12340150000000, 101.12345500000000, 1.12340050000000, 1.12345000000000, 1, 'Meeting Room 1,Panasonic Asia,Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Energy Solutions Group,Meeting Room 2', 0);
INSERT INTO groups VALUES (4, '1', '2015-08-13 12:17:00', NULL, 3, 'Level 3', '|1|2|4|', NULL, '2015-08-13 12:17:00', '1', 1, 2, '2', 1, NULL, NULL, 'SG004', NULL, 3, 1.12340130000000, 101.12345300000000, 1.12340130000000, 101.12345300000000, 1.12340030000000, 1.12345800000000, NULL, 'Level 1,Block A,Level 3', 0);
INSERT INTO groups VALUES (53, '1', '2015-08-13 13:01:00', NULL, 1, 'Level 3 Company 2', '|53|', 'panasonic_demo.svg', '2015-08-13 13:01:00', '1', 2, NULL, '2', 2, NULL, NULL, 'SG053', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 3 Company 2', 0);
INSERT INTO groups VALUES (22, '1', '2015-08-13 12:37:00', NULL, 6, 'Canteen', '|1|2|4|20|21|22|', NULL, '2015-08-13 12:37:00', '1', 1, 21, '1', 3, NULL, NULL, 'LG022', NULL, 7, NULL, NULL, NULL, NULL, NULL, NULL, 3, 'Meeting Room 5,Level 1,Block A,Level 3,Meeting Room 4,Canteen', 0);
INSERT INTO groups VALUES (30, '1', '2015-08-13 13:03:00', NULL, 3, 'PRDCSG Office', '|25|27|30|', 'panasonic_demo.svg', '2015-08-13 13:03:00', '1', 1, 27, '3', 2, NULL, NULL, 'ST030', 1.3100000000000001, 6, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Block B,PRDCSG Office', 0);
INSERT INTO groups VALUES (39, '1', '2015-08-13 13:17:00', NULL, 4, 'Meeting Room 3', '|25|27|30|39|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 30, '3', 3, NULL, NULL, 'LG039', NULL, 7, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Block B,PRDCSG Office,Meeting Room 3', 0);
INSERT INTO groups VALUES (31, '1', '2015-08-13 13:03:00', NULL, 4, 'CISC-AP Office', '|25|26|28|31|', 'panasonic_demo.svg', '2015-08-13 13:03:00', '1', 1, 28, '3', 2, NULL, NULL, 'ST031', 1.3100000000000001, 6, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Level 1,Block B,CISC-AP Office,EDO Office', 0);
INSERT INTO groups VALUES (56, '1', '2015-08-13 13:01:00', NULL, 3, 'Level 3 Company 2', '|53|54|55|56|', 'panasonic_demo.svg', '2015-08-13 13:01:00', '1', 2, 53, '2', 3, NULL, NULL, 'SG056', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 3 Company 2,Level 3 Company 2,Level 3 Company 2,Level 3 Company 2', 0);
INSERT INTO groups VALUES (36, '1', '2015-08-13 13:17:00', NULL, 5, 'Open Office', '|25|26|29|35|36|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 35, '3', 3, NULL, NULL, 'LG036', NULL, 7, NULL, NULL, NULL, NULL, NULL, NULL, 3, 'PESAP Office,Level 1,Block B,Open Office,Meeting Room 3', 0);
INSERT INTO groups VALUES (50, '1', '2015-08-13 13:01:00', NULL, 6, 'Level 3 ', '|25|27|30|40|48|50|', 'panasonic_demo.svg', '2015-08-13 13:01:00', '1', 1, 48, '2', 4, NULL, NULL, 'CG050', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,AG Meeting Rooms,Block B,PRDCSG Office,R&D Open Office,Level 3 ', 0);
INSERT INTO groups VALUES (19, '1', '2015-08-13 12:34:00', NULL, 11, 'Open Office19', '|1|2|3|5|6|8|9|10|11|15|19|', NULL, '2015-08-13 12:34:00', '1', 1, 15, '1', 4, NULL, NULL, 'CG019', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Open Office,Panasonic Asia,Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Energy Solutions Group,Meeting Room 2,Open Office19', 0);
INSERT INTO groups VALUES (38, '1', '2015-08-13 13:17:00', NULL, 6, 'Meeting Room 2', '|25|26|29|35|36|38|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 36, '3', 4, NULL, NULL, 'CG038', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 3, 'PESAP Office,Level 1,Block B,Open Office,Meeting Room 3,Meeting Room 2', 0);
INSERT INTO groups VALUES (17, '1', '2015-08-13 12:34:00', NULL, 11, 'MD Room', '|1|2|3|5|6|8|9|10|11|14|17|', NULL, '2015-08-13 12:34:00', '1', 1, 14, '1', 4, NULL, NULL, 'CG017', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'MD Room,Meeting Room 2,Panasonic Asia,Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Energy Solutions Group,Meeting Room 2', 0);
INSERT INTO groups VALUES (48, '1', '2015-08-13 13:17:00', NULL, 5, 'AG Meeting Rooms', '|25|27|30|40|48|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 40, '3', 3, NULL, NULL, 'LG048', NULL, 8, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,AG Meeting Rooms,Block B,PRDCSG Office,R&D Open Office', 0);
INSERT INTO groups VALUES (23, '1', '2015-08-13 12:37:00', NULL, 6, 'Exhibition Room 1', '|1|2|4|20|21|23|', NULL, '2015-08-13 12:37:00', '1', 1, 21, '1', 3, NULL, NULL, 'LG023', NULL, 7, NULL, NULL, NULL, NULL, NULL, NULL, 3, 'Meeting Room 5,Level 1,Block A,Level 3,Meeting Room 4,Exhibition Room 1', 0);
INSERT INTO groups VALUES (47, '1', '2015-08-13 13:17:00', NULL, 5, 'Other Rooms', '|25|27|30|40|47|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 40, '3', 3, NULL, NULL, 'LG047', NULL, 8, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Block B,PRDCSG Office,R&D Open Office,Other Rooms', 0);
INSERT INTO groups VALUES (45, '1', '2015-08-13 13:17:00', NULL, 7, ' Open Office', '|25|27|30|39|41|43|45|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 43, '3', 4, NULL, NULL, 'CG045', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Meeting Rooms,Block B, Open Office,PRDCSG Office,Other Rooms,Meeting Room 3', 0);
INSERT INTO groups VALUES (40, '1', '2015-08-13 13:17:00', NULL, 4, 'R&D Open Office', '|25|27|30|40|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 30, '3', 3, NULL, NULL, 'LG040', NULL, 7, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Block B,PRDCSG Office,R&D Open Office', 0);
INSERT INTO groups VALUES (34, '1', '2015-08-13 13:17:00', NULL, 6, 'Meeting Room 2', '|25|26|28|31|32|34|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 32, '3', 4, NULL, NULL, 'CG034', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Level 1,Block B,Open Office,CISC-AP Office,EDO Office,Meeting Room 2', 0);
INSERT INTO groups VALUES (9, '1', '2015-08-13 12:29:00', NULL, 7, 'Panasonic Sales', '|1|2|3|5|6|8|9|', NULL, '2015-08-13 12:29:00', '1', 1, 8, '1', 3, NULL, NULL, 'LG009', NULL, 7, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Meeting Room 2', 0);
INSERT INTO groups VALUES (2, '1', '2015-08-13 12:16:00', NULL, 2, 'Level 1', '|1|2|', NULL, '2015-08-13 12:16:00', '1', 1, 1, '2', 2, NULL, NULL, 'SG002', NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 1,Block A', 0);
INSERT INTO groups VALUES (12, '1', '2015-08-13 12:31:00', NULL, 9, 'Open Office', '|1|2|3|5|6|8|9|10|12|', NULL, '2015-08-13 12:31:00', '1', 1, 10, '1', 4, NULL, NULL, 'CG012', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Open Office,Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Energy Solutions Group,Meeting Room 2', 0);
INSERT INTO groups VALUES (16, '1', '2015-08-13 12:34:00', NULL, 11, 'Meeting Room 3', '|1|2|3|5|6|8|9|10|11|14|16|', NULL, '2015-08-13 12:34:00', '1', 1, 14, '1', 3, NULL, NULL, 'LG016', NULL, 11, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Meeting Room 3,Meeting Room 2,Panasonic Asia,Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Energy Solutions Group,Meeting Room 2', 0);
INSERT INTO groups VALUES (21, '1', '2015-08-13 12:34:00', NULL, 5, 'Meeting Room 5', '|1|2|4|20|21|', NULL, '2015-08-13 12:34:00', '1', 1, 20, '1', 2, NULL, NULL, 'ST021', 5.3099999999999996, 6, 1.12340170000000, 101.12345700000000, 1.12340170000000, 101.12345700000000, 1.12340070000000, 1.12346100000000, 3, 'Meeting Room 5,Level 1,Block A,Level 3,Meeting Room 4', 0);
INSERT INTO groups VALUES (8, '1', '2015-08-13 12:22:00', NULL, 6, 'Meeting Room 2', '|1|2|3|5|6|8|', NULL, '2015-08-13 12:22:00', '1', 1, 6, '1', 2, NULL, NULL, 'ST008', 1.3100000000000001, 6, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Reception,Waiting Area,Level 1,Block A,Level 2,Meeting Room 2', 0);
INSERT INTO groups VALUES (49, '1', '2015-08-13 13:17:00', NULL, 6, 'Meeting Rooms', '|25|27|30|40|47|49|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 47, '3', 4, NULL, NULL, 'CG049', NULL, 12, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Meeting Rooms,Block B,PRDCSG Office,R&D Open Office,Other Rooms', 0);
INSERT INTO groups VALUES (6, '1', '2015-08-13 12:22:00', NULL, 5, 'Waiting Area', '|1|2|3|5|6|', NULL, '2015-08-13 12:22:00', '1', 1, 5, '1', 1, NULL, NULL, 'SG006', 3.54, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Reception,Waiting Area,Level 1,Block A,Level 2', 0);
INSERT INTO groups VALUES (52, '1', '2015-08-13 13:01:00', NULL, 1, 'Level 3 Company 2', '|52|', 'panasonic_demo.svg', '2015-08-13 13:01:00', '1', 2, NULL, '2', 1, NULL, NULL, 'SG052', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 3 Company 2', 0);
INSERT INTO groups VALUES (7, '1', '2015-08-13 12:22:00', NULL, 5, 'Meeting Room 1', '|1|2|3|5|7|', NULL, '2015-08-13 12:22:00', '1', 1, 5, '1', 1, NULL, NULL, 'SG007', NULL, 5, 1.12340140000000, 101.12345400000000, 1.12340140000000, 101.12345400000000, 1.12340040000000, 1.12345900000000, NULL, 'Reception,Level 1,Block A,Level 2,Meeting Room 1', 0);
INSERT INTO groups VALUES (14, '1', '2015-08-13 12:31:00', NULL, 10, 'Meeting Room 2', '|1|2|3|5|6|8|9|10|11|14|', NULL, '2015-08-13 12:31:00', '1', 1, 11, '1', 3, NULL, NULL, 'LG014', NULL, 10, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Meeting Room 2,Panasonic Asia,Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Energy Solutions Group,Meeting Room 2', 0);
INSERT INTO groups VALUES (28, '1', '2015-08-13 13:03:00', NULL, 3, 'EDO Office', '|25|26|28|', 'panasonic_demo.svg', '2015-08-13 13:03:00', '1', 1, 26, '3', 1, NULL, NULL, 'SG028', 3.3300000000000001, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 1,Block B,EDO Office', 0);
INSERT INTO groups VALUES (3, '1', '2015-08-13 12:17:00', NULL, 3, 'Level 2', '|1|2|3|', NULL, '2015-08-13 12:17:00', '1', 1, 2, '2', 1, NULL, NULL, 'SG003', NULL, 3, 1.12340120000000, 101.12345200000000, 1.12340120000000, 101.12345200000000, 1.12340020000000, 1.12345700000000, NULL, 'Level 1,Block A,Level 2', 0);
INSERT INTO groups VALUES (37, '1', '2015-08-13 13:17:00', NULL, 5, 'Meeting Room 1', '|25|26|29|35|37|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 35, '3', 3, NULL, NULL, 'LG037', NULL, 7, NULL, NULL, NULL, NULL, NULL, NULL, 3, 'PESAP Office,Level 1,Block B,Meeting Room 1,Meeting Room 3', 0);
INSERT INTO groups VALUES (5, '1', '2015-08-13 12:19:00', NULL, 4, 'Reception', '|1|2|3|5|', NULL, '2015-08-13 12:19:00', '1', 1, 3, '1', 1, NULL, NULL, 'SG005', NULL, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Reception,Level 1,Block A,Level 2', 0);
INSERT INTO groups VALUES (26, '1', '2015-08-13 13:01:00', NULL, 2, 'Level 1', '|25|26|', 'panasonic_demo.svg', '2015-08-13 13:01:00', '1', 1, 25, '2', 1, NULL, NULL, 'SG026', NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 1,Block B', 0);
INSERT INTO groups VALUES (24, '1', '2015-08-13 12:37:00', NULL, 7, 'Exhibition Room 2', '|1|2|4|20|21|22|24|', NULL, '2015-08-13 12:37:00', '1', 1, 22, '1', 4, NULL, NULL, 'CG024', NULL, 12, 1.12340180000000, 101.12345800000000, 1.12340180000000, 101.12345800000000, 1.12340080000000, 1.12346200000000, 3, 'Exhibition Room 2,Meeting Room 5,Level 1,Block A,Level 3,Meeting Room 4,Canteen', 0);
INSERT INTO groups VALUES (54, '1', '2015-08-13 13:01:00', NULL, 2, 'Level 3 Company 2', '|53|54|', 'panasonic_demo.svg', '2015-08-13 13:01:00', '1', 2, 53, '2', 3, NULL, NULL, 'SG054', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 3 Company 2,Level 3 Company 2', 0);
INSERT INTO groups VALUES (32, '1', '2015-08-13 13:17:00', NULL, 5, 'Open Office', '|25|26|28|31|32|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 31, '3', 3, NULL, NULL, 'LG032', NULL, 7, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Level 1,Block B,Open Office,CISC-AP Office,EDO Office', 0);
INSERT INTO groups VALUES (43, '1', '2015-08-13 13:17:00', NULL, 6, 'Other Rooms', '|25|27|30|39|41|43|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 41, '3', 3, NULL, NULL, 'LG043', NULL, 9, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Meeting Rooms,Block B,PRDCSG Office,Other Rooms,Meeting Room 3', 0);
INSERT INTO groups VALUES (55, '1', '2015-08-13 13:01:00', NULL, 3, 'Level 3 Company 2', '|53|54|55|', 'panasonic_demo.svg', '2015-08-13 13:01:00', '1', 2, 53, '2', 3, NULL, NULL, 'SG055', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Level 3 Company 2,Level 3 Company 2,Level 3 Company 2', 0);
INSERT INTO groups VALUES (33, '1', '2015-08-13 13:17:00', NULL, 5, 'Meeting Room 1', '|25|26|28|31|33|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 31, '3', 3, NULL, NULL, 'LG033', NULL, 7, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Meeting Room 1,Level 1,Block B,CISC-AP Office,EDO Office', 0);
INSERT INTO groups VALUES (35, '1', '2015-08-13 13:17:00', NULL, 4, 'Meeting Room 3', '|25|26|29|35|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 29, '3', 2, NULL, NULL, 'ST035', 1.3100000000000001, 6, NULL, NULL, NULL, NULL, NULL, NULL, 3, 'PESAP Office,Level 1,Block B,Meeting Room 3', 0);
INSERT INTO groups VALUES (10, '1', '2015-08-13 12:29:00', NULL, 8, 'Energy Solutions Group', '|1|2|3|5|6|8|9|10|', NULL, '2015-08-13 12:29:00', '1', 1, 9, '1', 3, NULL, NULL, 'LG010', NULL, 8, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'Reception,Waiting Area,Level 1,Block A,Level 2,Panasonic Sales,Energy Solutions Group,Meeting Room 2', 0);
INSERT INTO groups VALUES (42, '1', '2015-08-13 13:17:00', NULL, 5, ' Test Rooms', '|25|27|30|39|42|', 'panasonic_demo.svg', '2015-08-13 13:17:00', '1', 1, 39, '3', 3, NULL, NULL, 'LG042', NULL, 8, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'Level 2,Block B,PRDCSG Office, Test Rooms,Meeting Room 3', 0);
INSERT INTO groups VALUES (1, '1', '2015-08-13 12:11:00', NULL, 1, 'Block A', '|1|', NULL, '2015-08-13 12:11:00', '1', 1, NULL, '1', 2, NULL, NULL, '1', NULL, 1, 1.12340110000000, 101.12345100000000, 1.12340110000000, 101.12345100000000, 1.12340010000000, 1.12345600000000, NULL, 'Block A', 0);


--
-- Name: groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('groups_id_seq', 1, true);


--
-- Data for Name: groups_tempdata; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO groups_tempdata VALUES (34, 32, 4, 'CG034', 'Meeting Room 2', '|25|26|28|31|32|34|', 'Block B');
INSERT INTO groups_tempdata VALUES (34, 32, 4, 'CG034', 'Meeting Room 2', '|25|26|28|31|32|34|', 'Block B');
INSERT INTO groups_tempdata VALUES (34, 32, 4, 'CG034', 'Meeting Room 2', '|25|26|28|31|32|34|', 'Block B');


--
-- Data for Name: groupscriteria; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO groupscriteria VALUES ('1', 1, 0, 0);
INSERT INTO groupscriteria VALUES ('2', 1, 1, 1);
INSERT INTO groupscriteria VALUES ('3', 0, 0, 1);
INSERT INTO groupscriteria VALUES ('sad', NULL, NULL, NULL);


--
-- Name: id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('id_seq', 8, true);


--
-- Data for Name: indoorunits; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO indoorunits VALUES (6, '0', NULL, '2015-12-11 15:37:04.328', NULL, NULL, NULL, 'AC-VRF', NULL, NULL, 4, NULL, 5, NULL, NULL, 'sds', '[2:{A0000000000F02000003}]', NULL, NULL, NULL, NULL, NULL, NULL, 'null', '1', '1                                                                                                                                                                                                                                ', '1', 'null', NULL, '0-1-1', NULL, NULL, 'AC-model-A');
INSERT INTO indoorunits VALUES (7, '0', NULL, '2015-12-11 15:37:04.333', NULL, 6, NULL, 'AC-VRF', NULL, NULL, 4, NULL, 5, NULL, NULL, 'dsds', '[2:{A0000000000F02000004}]', NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2', '1                                                                                                                                                                                                                                ', '1', 'null', NULL, '0-1-2', NULL, NULL, 'AC-model-B');
INSERT INTO indoorunits VALUES (8, '0', NULL, '2015-12-11 15:37:04.338', NULL, NULL, NULL, 'AC-VRF', NULL, NULL, 4, NULL, 7, NULL, NULL, 'dsd', '[2:{A0000000000F02000007}]', NULL, NULL, NULL, NULL, NULL, NULL, 'null', '1', '2                                                                                                                                                                                                                                ', '2', 'null', NULL, '1-2-1', NULL, NULL, 'AC-model-C');
INSERT INTO indoorunits VALUES (9, '0', NULL, '2015-12-11 15:37:04.343', NULL, 8, NULL, 'AC-VRF', NULL, NULL, 4, NULL, 7, NULL, NULL, 'sds', '[2:{A0000000000F02000008}]', NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2', '2                                                                                                                                                                                                                                ', '2', 'null', NULL, '1-2-2', NULL, NULL, 'AC-model-D');


--
-- Name: indoorunits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunits_id_seq', 9, true);


--
-- Data for Name: indoorunitslog; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: indoorunitslog_history; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: indoorunitslog_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitslog_history_id_seq', 1, false);


--
-- Name: indoorunitslog_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitslog_id_seq', 1, false);


--
-- Data for Name: indoorunitstatistics; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: indoorunitstatistics_daily; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: indoorunitstatistics_daily_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_daily_id_seq', 100, false);


--
-- Name: indoorunitstatistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_id_seq', 1, false);


--
-- Data for Name: indoorunitstatistics_monthly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: indoorunitstatistics_monthly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_monthly_id_seq', 100, false);


--
-- Data for Name: indoorunitstatistics_weekly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: indoorunitstatistics_weekly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_weekly_id_seq', 100, false);


--
-- Data for Name: indoorunitstatistics_yearly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: indoorunitstatistics_yearly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indoorunitstatistics_yearly_id_seq', 100, false);


--
-- Data for Name: job_tracker; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO job_tracker VALUES ('ETL-GROUPS-ALARAM-NOTIFICATION', 'Success', '2015-12-09 20:39:12', '2015-12-09 20:39:12', 'RSI', NULL);
INSERT INTO job_tracker VALUES ('ETL-GROUPS-ALARAM-NOTIFICATION', 'Success', '2015-12-09 20:53:40', '2015-12-09 20:53:40', 'RSI', NULL);
INSERT INTO job_tracker VALUES ('ETL-PF-RC-OPERATION-LOG', 'SUCCESS', '2015-12-09 21:08:45.252', '2015-12-09 21:08:45.252', 'ETL', '2015-12-09 21:08:45.584');
INSERT INTO job_tracker VALUES ('ETL-PF-RC-OPERATION-LOG', 'SUCCESS', '2015-12-09 21:22:15.479', '2015-12-09 21:22:15.479', 'ETL', '2015-12-09 21:22:15.902');


--
-- Data for Name: maintenance_setting; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO maintenance_setting VALUES (1, 1, 8, 234);
INSERT INTO maintenance_setting VALUES (2, 2, 8, 2234);
INSERT INTO maintenance_setting VALUES (3, 3, 8, 3334);
INSERT INTO maintenance_setting VALUES (4, 4, 8, 2444);
INSERT INTO maintenance_setting VALUES (5, 5, 8, 5544);
INSERT INTO maintenance_setting VALUES (6, 6, 8, 234666);


--
-- Name: maintenance_setting_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('maintenance_setting_id_seq', 1, false);


--
-- Data for Name: maintenance_status_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO maintenance_status_data VALUES (18, '2015-01-01 06:30:00', 16, NULL, NULL, NULL, -1926, 74, 1174);
INSERT INTO maintenance_status_data VALUES (17, '2015-01-01 06:30:00', 1, NULL, NULL, NULL, -1926, 74, 1174);
INSERT INTO maintenance_status_data VALUES (14, '2015-01-01 06:30:00', 27, -5544, 234666, NULL, NULL, NULL, NULL);
INSERT INTO maintenance_status_data VALUES (11, '2015-01-01 06:30:00', 15, NULL, NULL, NULL, 234, -1126, -26);


--
-- Name: maintenance_status_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('maintenance_status_data_id_seq', 18, true);


--
-- Data for Name: maintenance_type_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO maintenance_type_master VALUES (1, 'VRF Compressor #1 Operation Hours');
INSERT INTO maintenance_type_master VALUES (2, 'VRF Compressor #2 Operation Hours');
INSERT INTO maintenance_type_master VALUES (3, 'VRF Compressor #3 Operation Hours');
INSERT INTO maintenance_type_master VALUES (4, 'PAC Compressor Operation Hours ');
INSERT INTO maintenance_type_master VALUES (5, 'GHP Engine Operation Hours ');
INSERT INTO maintenance_type_master VALUES (6, 'GHP Time After Oil Change');


--
-- Data for Name: maintenance_user_list; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO maintenance_user_list VALUES (51, 'xyz@aa2.com', false, 2);
INSERT INTO maintenance_user_list VALUES (55, 'amitesh.arya@rsystems.com', true, 1);
INSERT INTO maintenance_user_list VALUES (44, 'xyz@aa1.com', NULL, 2);
INSERT INTO maintenance_user_list VALUES (49, 'xyz@aa.com', NULL, 2);
INSERT INTO maintenance_user_list VALUES (50, 'xyz@aa1.com', NULL, 2);
INSERT INTO maintenance_user_list VALUES (52, 'xyz@aa.com', true, 2);
INSERT INTO maintenance_user_list VALUES (53, 'xyz@aa1.com', true, 2);
INSERT INTO maintenance_user_list VALUES (54, 'xyz@aa2.com', true, 2);
INSERT INTO maintenance_user_list VALUES (45, 'xyz@aa2.com', NULL, 2);
INSERT INTO maintenance_user_list VALUES (46, 'xyz@aa.com', NULL, 2);
INSERT INTO maintenance_user_list VALUES (47, 'xyz@aa1.com', false, 2);
INSERT INTO maintenance_user_list VALUES (48, 'xyz@aa2.com', NULL, 2);
INSERT INTO maintenance_user_list VALUES (56, 'xyz@aa1.com', true, 2);
INSERT INTO maintenance_user_list VALUES (57, 'xyz@aa2.com', true, 2);
INSERT INTO maintenance_user_list VALUES (80, 'xyz@aa1.com', true, 2);
INSERT INTO maintenance_user_list VALUES (81, 'xyz@aa2.com', true, 2);
INSERT INTO maintenance_user_list VALUES (84, 'xyz@aa2.com', true, 2);
INSERT INTO maintenance_user_list VALUES (83, 'xyz@aa1.com', true, 2);
INSERT INTO maintenance_user_list VALUES (85, 'xyz@aa.com', true, 2);
INSERT INTO maintenance_user_list VALUES (86, 'xyz@aa1.com', true, 2);
INSERT INTO maintenance_user_list VALUES (87, 'xyz@aa2.com', true, 2);
INSERT INTO maintenance_user_list VALUES (79, 'amitesh.arya1@rsystems.com', false, 1);
INSERT INTO maintenance_user_list VALUES (82, 'amitesh.arya2@rsystems.com', false, 1);
INSERT INTO maintenance_user_list VALUES (88, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (89, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (90, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (91, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (92, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (93, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (94, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (95, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (96, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (97, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (98, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (99, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (100, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (101, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (102, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (103, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (104, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (105, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (106, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (107, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (108, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (109, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (110, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (111, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (112, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (113, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (114, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (115, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (116, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (117, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (118, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (119, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (120, 'xyz@aa2.com', true, 1);
INSERT INTO maintenance_user_list VALUES (121, 'xyz@aa.com', true, 1);
INSERT INTO maintenance_user_list VALUES (122, 'xyz@aa1.com', true, 1);
INSERT INTO maintenance_user_list VALUES (123, 'xyz@aa2.com', true, 1);


--
-- Name: maintenance_user_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('maintenance_user_list_id_seq', 123, true);


--
-- Data for Name: metaindoorunits; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO metaindoorunits VALUES (1, '2015-07-03 15:45:30', 'device.png', 'model1', '2015-07-03 17:22:53', 'RSI', '2 way', 30.00, 20.00, -35.00, 16.00, 92.50, 30.00, 30.00, 35.00, true, true, false, 1, true, true, true, true, true, true, true, false, false);
INSERT INTO metaindoorunits VALUES (2, '2015-07-03 15:45:33', 'device.png', 'model2', '2015-07-03 17:22:53', 'RSI', '3 way', 30.00, 15.00, -10.00, 16.00, 55.50, 25.00, 25.00, 20.00, false, false, true, 0, false, false, true, false, false, true, true, true, true);


--
-- Name: metaindoorunits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('metaindoorunits_id_seq', 1, false);


--
-- Data for Name: metaoutdoorunits; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO metaoutdoorunits VALUES (1, '1', '2015-03-16 18:29:44', 12.300000000000001, 11.5, 'fused', 33, 33, 33, 33, 33, 33, 'Kind1', 'logo1', 'Model1', 21.100000000000001, 11.1, 1, 33, 33, 33, 33, 33, 33, 11, 11, 11, '2015-07-03 17:24:48', NULL, '2 way');
INSERT INTO metaoutdoorunits VALUES (2, '1', '2015-04-21 14:16:12', 13.1, 21, 'fused', 31, 21, 22, 23, 13, 34, 'Kiind2', 'logo2', 'Model2', 11.199999999999999, 33.399999999999999, 2, 32, 45, 42, 45, 24, 21, 14, 10, 12, '2015-06-04 13:25:45', NULL, '3 way');


--
-- Name: metaoutdoorunits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('metaoutdoorunits_id_seq', 1, false);


--
-- Data for Name: modemaster; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO modemaster VALUES (3, 'Fan');
INSERT INTO modemaster VALUES (4, 'Dry');
INSERT INTO modemaster VALUES (5, 'Auto');
INSERT INTO modemaster VALUES (1, 'Heat');
INSERT INTO modemaster VALUES (2, 'Cool');


--
-- Name: modemaster_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('modemaster_id_seq', 1, false);


--
-- Data for Name: notificationlog; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: notificationlog_temp; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: notificationlog_temp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('notificationlog_temp_id_seq', 676, true);


--
-- Data for Name: notificationsettings; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO notificationsettings VALUES (38, true, 4, 13);
INSERT INTO notificationsettings VALUES (37, false, 2, 13);
INSERT INTO notificationsettings VALUES (1950, false, 1, 13);
INSERT INTO notificationsettings VALUES (1951, true, 3, 13);
INSERT INTO notificationsettings VALUES (46, true, 1, 20);
INSERT INTO notificationsettings VALUES (47, false, 2, 20);
INSERT INTO notificationsettings VALUES (40, false, 1, 3);
INSERT INTO notificationsettings VALUES (41, true, 3, 3);
INSERT INTO notificationsettings VALUES (48, false, 1, 5);
INSERT INTO notificationsettings VALUES (49, true, 3, 6);


--
-- Name: notificationsettings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('notificationsettings_id_seq', 49, true);


--
-- Data for Name: notificationtype_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO notificationtype_master VALUES (1, 'AC Configuration Changes');
INSERT INTO notificationtype_master VALUES (2, 'Test 1');
INSERT INTO notificationtype_master VALUES (3, 'Test 2');
INSERT INTO notificationtype_master VALUES (4, 'isMaster');
INSERT INTO notificationtype_master VALUES (5, 'Test 3');


--
-- Name: notificationtype_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('notificationtype_master_id_seq', 1, false);


--
-- Name: odu_maintenance_status_data_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('odu_maintenance_status_data_seq', 1, false);


--
-- Data for Name: outdoorunitparameters; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO outdoorunitparameters VALUES (4, 'Outdoor unit status', 'V1', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (67, 'Bypass valve opening', 'G29', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (68, 'Liquid valve opening', 'G30', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (87, 'Oil level temperature', 'G49', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (90, 'Compressor oil level', 'G52', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (35, 'Instant gas', 'Instantgas', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (36, 'Instant heat', 'Instantheat', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (37, 'Engine operation time', 'Engineoperationtime', 'Hours', 'GHP');
INSERT INTO outdoorunitparameters VALUES (38, 'Time after changing engine oil', 'Timeafterchangingengine oil', 'Hours', 'GHP');
INSERT INTO outdoorunitparameters VALUES (85, 'Clutch coil temperature', 'Clutchcoiltemperature', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (91, 'Super heat level of compressor unit', 'Superheatlevelofcompressorunit', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (66, 'Expansion valve opening 2', 'G28', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (96, '3 way valve for hot water', 'G33', '', 'GHP');
INSERT INTO outdoorunitparameters VALUES (43, 'Starter motor', 'G5', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (39, 'GHP oil sign', 'G1', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (42, 'Starter motor power', 'G4', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (41, 'Fuel gas shut_off valve 2', 'G3', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (40, 'Fuel gas shut_off valve 1', 'G2', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (44, 'Oil pump', 'G6', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (45, 'Heater for cold region', 'G7', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (46, 'Clutch', 'G8', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (47, 'Clutch 2', 'G9', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (48, 'Oil recovery valve', 'G10', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (49, 'Balance valve', 'G11', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (54, 'Discharge solenoid valve 1', 'G16', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (5, 'INV comp. target Hz', 'V2', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (6, 'INV comp. actual Hz', 'V3', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (7, 'Fixed speed comp. 1', 'V4', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (8, 'Fixed speed comp. 2', 'V5', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (9, 'Liquid temp. of outdoor coil 1', 'V6', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (11, 'Gas temp. of outdoor coil 1', 'V7', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (10, 'Liquid temp. of outdoor coil 2', 'V8', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (12, 'Gas temp. of outdoor coil 2', 'V9', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (13, 'SCG', 'V10', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (14, 'Temp. oil 1', 'V11', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (15, 'Temp. oil 2', 'V12', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (16, 'Temp. oil 3', 'V13', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (17, 'Temp. compress or discharge 1', 'V14', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (19, 'Temp. compress or discharge 3', 'V15', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (18, 'Temp. compress or discharge 2', 'V16', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (20, 'Inlet temp. outdoor unit', 'V17', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (21, 'High pressure', 'V18', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (22, 'Low pressure', 'V19', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (1, 'Compressor 1 working time', 'V20', 'Hours', 'VRF');
INSERT INTO outdoorunitparameters VALUES (2, 'Compressor 2 working time', 'V21', 'Hours', 'VRF');
INSERT INTO outdoorunitparameters VALUES (50, 'Flushing valve', 'G12', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (3, 'Compressor 3 working time', 'V22', 'Hours', 'VRF');
INSERT INTO outdoorunitparameters VALUES (51, 'Gas refrigerant shut_off valve', 'G13', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (53, 'Pump for hot water', 'G15', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (52, 'Compressor heater', 'G14', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (55, 'Discharge solenoid valve 2', 'G17', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (56, 'Suction solenoid valve 1', 'G18', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (57, 'Suction solenoid valve 2', 'G19', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (58, 'Drain filter heater 1', 'G20', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (59, 'Drain filter heater 2', 'G21', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (60, 'Receiver tank valve 1', 'G22', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (61, 'Receiver tank valve 2', 'G23', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (62, 'Engine revolution', 'G24', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (63, 'Fuel gas regulating valve opening', 'G25', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (92, 'thottle', 'G26', '', 'GHP');
INSERT INTO outdoorunitparameters VALUES (93, 'Expansion valve opening', 'G27', '', 'GHP');
INSERT INTO outdoorunitparameters VALUES (72, '3 way cooler valve', 'G34', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (70, '3 way valve for coolant', 'G35', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (74, 'Compressor inlet pressure', 'G36', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (75, 'Compressor outlet pressure', 'G37', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (76, 'Compressor inlet temperature', 'G38', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (29, 'Current 3', 'V29', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (77, 'Compressor outlet temperature', 'G39', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (78, 'Heat exchanger inlet temperature', 'G40', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (28, 'Current 2', 'V28', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (27, 'INV secondary current', 'V27', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (34, 'Mov. 4 pulse', 'V34', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (33, 'Mov. 2 pulse', 'V33', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (32, 'Mov. 1 pulse', 'V32', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (31, 'Fan rotation', 'V31', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (30, 'Fan mode', 'V30', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (26, 'INV primary current', 'V26', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (25, 'Saturated temp. low press.', 'V25', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (24, 'Saturated temp. high press.', 'V24', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (23, 'Demand', 'V23', NULL, 'VRF');
INSERT INTO outdoorunitparameters VALUES (95, 'Exhaust heat recovery valve opening', 'G31', '', 'GHP');
INSERT INTO outdoorunitparameters VALUES (80, 'Exhaust gas temperature', 'G42', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (79, 'Heat exchanger inlet temperature 2', 'G41', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (81, 'Outdoor unit fan output', 'G43', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (82, 'Generation power', 'G44', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (83, 'Starter motor current', 'G45', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (84, 'Ignition timing', 'G46', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (97, 'Clutch coil temperature 1', 'G47', '', 'GHP');
INSERT INTO outdoorunitparameters VALUES (86, 'Clutch coil temperature 2', 'G48', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (88, 'Catalyzer temperature', 'G50', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (89, 'Hot water temperature', 'G51', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (99, 'Superheat level of compressor inlet', 'G53', '', 'GHP');
INSERT INTO outdoorunitparameters VALUES (65, 'Expansion valve opening', 'Expansionvalveopening', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (94, 'liquid valve opening', 'liquid valve opening', '', 'GHP');
INSERT INTO outdoorunitparameters VALUES (73, 'Coolant temperature', 'Coolant temperature', NULL, 'GHP');
INSERT INTO outdoorunitparameters VALUES (98, 'Comperssor oil level', 'Comperssor oil level', '', 'GHP');
INSERT INTO outdoorunitparameters VALUES (100, 'Oil Level Temperature', 'OilLevelTemperature', '', 'GHP');


--
-- Data for Name: outdoorunits; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO outdoorunits VALUES (5, NULL, NULL, NULL, NULL, NULL, 'AC-VRF', NULL, NULL, 4, NULL, 'dsds', '[2:{A0000000000F02000001}]', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC-OU-model-A', '0', '1', NULL, '0', 0, NULL, NULL, '0-0-0', NULL);
INSERT INTO outdoorunits VALUES (6, NULL, NULL, NULL, NULL, NULL, 'AC-VRF', NULL, NULL, 4, NULL, 'dsd', '[2:{A0000000000F02000002}]', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC-OU-model-A', '1', '1', NULL, '0', 0, 5, NULL, '0-1-1', NULL);
INSERT INTO outdoorunits VALUES (7, NULL, NULL, NULL, NULL, NULL, 'AC-VRF', NULL, NULL, 4, NULL, 'sd', '[2:{A0000000000F02000005}]', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC-OU-model-A', '0', '2', NULL, '1', 0, NULL, NULL, '1-0-0', NULL);
INSERT INTO outdoorunits VALUES (8, NULL, NULL, NULL, NULL, NULL, 'AC-VRF', NULL, NULL, 4, NULL, 'sdsds', '[2:{A0000000000F02000006}]', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'AC-OU-model-A', '1', '2', NULL, '1', 0, 7, NULL, '1-1-1', NULL);


--
-- Name: outdoorunits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outdoorunits_id_seq', 1, true);


--
-- Data for Name: outdoorunitslog; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO outdoorunitslog VALUES (28, NULL, NULL, NULL, NULL, 3.5, NULL, NULL, NULL, NULL, NULL, NULL, 31, NULL, NULL, 43.100000000000001, NULL, NULL, NULL, NULL, 2, 1435, 15.00, 16.00, 17.00, 16, NULL);
INSERT INTO outdoorunitslog VALUES (29, NULL, NULL, NULL, NULL, 16.3999996, NULL, NULL, NULL, NULL, NULL, NULL, 32, NULL, NULL, 56.700000000000003, NULL, NULL, NULL, NULL, 3, 2124, 11.00, 34.00, 16.00, 17, NULL);
INSERT INTO outdoorunitslog VALUES (30, NULL, NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL, NULL, 35, NULL, NULL, 20.100000000000001, NULL, NULL, NULL, NULL, 4, 1253, 12.00, 35.00, 14.00, 18, NULL);
INSERT INTO outdoorunitslog VALUES (31, NULL, NULL, NULL, NULL, 4.80000019, NULL, NULL, NULL, NULL, NULL, NULL, 33.5, NULL, NULL, 20.100000000000001, NULL, NULL, NULL, NULL, 5, 2906, 13.00, 36.00, 18.00, 19, NULL);
INSERT INTO outdoorunitslog VALUES (32, NULL, NULL, NULL, NULL, 5.19999981, NULL, NULL, NULL, NULL, NULL, NULL, 33, NULL, NULL, 22.300000000000001, NULL, NULL, NULL, NULL, 6, 926, 14.00, 37.00, 19.00, 20, NULL);
INSERT INTO outdoorunitslog VALUES (33, NULL, NULL, NULL, NULL, 5.0999999, NULL, NULL, NULL, NULL, NULL, NULL, 34, NULL, NULL, 25.199999999999999, NULL, NULL, NULL, NULL, 7, 1420, 15.00, 38.00, 20.00, 21, NULL);
INSERT INTO outdoorunitslog VALUES (34, NULL, NULL, NULL, NULL, 7.0999999, NULL, NULL, NULL, NULL, NULL, NULL, 33, NULL, NULL, 40.200000000000003, NULL, NULL, NULL, NULL, 8, 1383, 16.00, 39.00, 21.00, 22, NULL);
INSERT INTO outdoorunitslog VALUES (35, NULL, NULL, NULL, NULL, 18.6000004, NULL, NULL, NULL, NULL, NULL, NULL, 33, NULL, NULL, 40.200000000000003, NULL, NULL, NULL, NULL, 9, 1668, 17.00, 40.00, 22.00, 23, NULL);
INSERT INTO outdoorunitslog VALUES (36, NULL, NULL, NULL, NULL, 11.1999998, NULL, NULL, NULL, NULL, NULL, NULL, 32.5, NULL, NULL, 40.200000000000003, NULL, NULL, NULL, NULL, 10, 1590, 18.00, 41.00, 13.00, 24, NULL);
INSERT INTO outdoorunitslog VALUES (37, NULL, NULL, NULL, NULL, 37.2000008, NULL, NULL, NULL, NULL, NULL, NULL, 32.5, NULL, NULL, 40.200000000000003, NULL, NULL, NULL, NULL, 11, 2064, 19.00, 42.00, 23.00, 25, NULL);
INSERT INTO outdoorunitslog VALUES (38, NULL, NULL, NULL, NULL, 15.5, NULL, NULL, NULL, NULL, NULL, NULL, 33.5, NULL, NULL, 40.200000000000003, NULL, NULL, NULL, NULL, 12, 1468, 20.00, 43.00, 24.00, 26, NULL);
INSERT INTO outdoorunitslog VALUES (39, NULL, NULL, NULL, NULL, 16.2000008, NULL, NULL, NULL, NULL, NULL, NULL, 33.5, NULL, NULL, 43.100000000000001, NULL, NULL, NULL, NULL, 13, 1420, 21.00, 44.00, 24.00, 27, NULL);
INSERT INTO outdoorunitslog VALUES (40, NULL, NULL, NULL, NULL, 5.5, NULL, NULL, NULL, NULL, NULL, NULL, 31.5, NULL, NULL, 20.100000000000001, NULL, NULL, NULL, NULL, 14, 1242, 22.00, 45.00, 26.00, 28, NULL);
INSERT INTO outdoorunitslog VALUES (41, NULL, NULL, NULL, NULL, 8.69999981, NULL, NULL, NULL, NULL, NULL, NULL, 31, NULL, NULL, 29.699999999999999, NULL, NULL, NULL, NULL, 15, 1663, 23.00, 5.00, 27.00, 29, NULL);
INSERT INTO outdoorunitslog VALUES (42, NULL, NULL, NULL, NULL, 0.400000006, NULL, NULL, NULL, NULL, NULL, NULL, 32, NULL, NULL, 26.800000000000001, NULL, NULL, NULL, NULL, 16, 890, 24.00, 6.00, 28.00, 30, NULL);
INSERT INTO outdoorunitslog VALUES (43, NULL, NULL, NULL, NULL, 29.8999996, NULL, NULL, NULL, NULL, NULL, NULL, 34.5, NULL, NULL, 48.600000000000001, NULL, NULL, NULL, NULL, 17, 1841, 25.00, 7.00, 29.00, 31, NULL);
INSERT INTO outdoorunitslog VALUES (44, NULL, NULL, NULL, NULL, 6.9000001, NULL, NULL, NULL, NULL, NULL, NULL, 31.5, NULL, NULL, 18.899999999999999, NULL, NULL, NULL, NULL, 18, 1809, 26.00, 8.00, 30.00, 32, NULL);
INSERT INTO outdoorunitslog VALUES (45, NULL, NULL, NULL, NULL, 20, NULL, NULL, NULL, NULL, NULL, NULL, 32.5, NULL, NULL, 43.100000000000001, NULL, NULL, NULL, NULL, 19, 1527, 27.00, 9.00, 31.00, 33, NULL);
INSERT INTO outdoorunitslog VALUES (46, NULL, NULL, NULL, NULL, 12.8999996, NULL, NULL, NULL, NULL, NULL, NULL, 33, NULL, NULL, 32.299999999999997, NULL, NULL, NULL, NULL, 20, 1634, 27.00, 10.00, 32.00, 34, NULL);
INSERT INTO outdoorunitslog VALUES (47, NULL, NULL, NULL, NULL, 4, NULL, NULL, NULL, NULL, NULL, NULL, 35.5, NULL, NULL, 35.200000000000003, NULL, NULL, NULL, NULL, 21, 1386, 29.00, 11.00, 33.00, 35, NULL);
INSERT INTO outdoorunitslog VALUES (48, NULL, NULL, NULL, NULL, 12.3000002, NULL, NULL, NULL, NULL, NULL, NULL, 35, NULL, NULL, 35.200000000000003, NULL, NULL, NULL, NULL, 22, 1200, 30.00, 12.00, 34.00, 36, NULL);
INSERT INTO outdoorunitslog VALUES (49, NULL, NULL, NULL, NULL, 6.0999999, NULL, NULL, NULL, NULL, NULL, NULL, 34, NULL, NULL, 35.200000000000003, NULL, NULL, NULL, NULL, 23, 1328, 31.00, 13.00, 35.00, 37, NULL);
INSERT INTO outdoorunitslog VALUES (50, NULL, NULL, NULL, NULL, 6.80000019, NULL, NULL, NULL, NULL, NULL, NULL, 32.5, NULL, NULL, 22.300000000000001, NULL, NULL, NULL, NULL, 24, 1479, 32.00, 14.00, 36.00, 38, NULL);
INSERT INTO outdoorunitslog VALUES (51, NULL, NULL, NULL, NULL, 6.80000019, NULL, NULL, NULL, NULL, NULL, NULL, 32.5, NULL, NULL, 22.300000000000001, NULL, NULL, NULL, NULL, 24, 1479, 32.00, 14.00, 36.00, 15, NULL);
INSERT INTO outdoorunitslog VALUES (27, NULL, NULL, NULL, NULL, 22.2000008, NULL, NULL, NULL, NULL, NULL, NULL, 33, NULL, NULL, 76.200000000000003, NULL, NULL, NULL, NULL, 1, 1787, 20.00, 33.00, 15.00, 1, NULL);


--
-- Data for Name: outdoorunitslog_history; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: outdoorunitstatistics; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO outdoorunitstatistics VALUES (1, '2015-11-16 00:00:00', 12, 12, NULL, 15, NULL);
INSERT INTO outdoorunitstatistics VALUES (2, '2015-11-16 00:00:00', 12, 12, NULL, 16, NULL);
INSERT INTO outdoorunitstatistics VALUES (5, '2015-11-20 00:00:00', 12, 12, NULL, 27, NULL);
INSERT INTO outdoorunitstatistics VALUES (4, '2015-12-02 00:00:00', 12, 12, NULL, 18, NULL);
INSERT INTO outdoorunitstatistics VALUES (3, '2015-12-02 00:00:00', 12, 12, NULL, 17, NULL);


--
-- Data for Name: outdoorunitstatistics_daily; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO outdoorunitstatistics_daily VALUES (2, '2014-11-12 00:00:00', 213, 12, 12, 15);
INSERT INTO outdoorunitstatistics_daily VALUES (3, '2014-11-12 00:00:00', 213, 12, 12, 15);
INSERT INTO outdoorunitstatistics_daily VALUES (4, '2014-11-12 00:00:00', 213, 12, 12, 15);
INSERT INTO outdoorunitstatistics_daily VALUES (5, '2014-11-12 00:00:00', 213, 12, 12, 15);


--
-- Name: outdoorunitstatistics_daily_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outdoorunitstatistics_daily_id_seq', 5, true);


--
-- Name: outdoorunitstatistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outdoorunitstatistics_id_seq', 1, true);


--
-- Name: outdoorunitstatistics_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outdoorunitstatistics_id_seq1', 1, true);


--
-- Data for Name: outdoorunitstatistics_monthly; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO outdoorunitstatistics_monthly VALUES (1, '2015-11-16 00:00:00', 1, 2015, 12, 12, 12, 15);
INSERT INTO outdoorunitstatistics_monthly VALUES (4, '2015-11-16 00:00:00', 1, 2015, 12, 12, 12, 17);
INSERT INTO outdoorunitstatistics_monthly VALUES (5, '2015-11-19 00:00:00', 10, 2015, 12, 12, 12, 18);
INSERT INTO outdoorunitstatistics_monthly VALUES (2, '2015-11-16 00:00:00', 2, 2015, 12, 12, 12, 16);
INSERT INTO outdoorunitstatistics_monthly VALUES (3, '2015-11-16 00:00:00', 3, 2015, 12, 12, 12, 16);


--
-- Name: outdoorunitstatistics_monthly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outdoorunitstatistics_monthly_id_seq', 1, false);


--
-- Data for Name: outdoorunitstatistics_weekly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: outdoorunitstatistics_weekly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outdoorunitstatistics_weekly_id_seq', 1, false);


--
-- Data for Name: outdoorunitstatistics_yearly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: outdoorunitstatistics_yearly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outdoorunitstatistics_yearly_id_seq', 1, false);


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO permissions VALUES (15, 'System', '2015-11-06 12:17:43', 'AC Settings', '2015-11-06 12:17:43', NULL, '/group/getIDUs.htm', NULL, NULL, 1);
INSERT INTO permissions VALUES (16, 'System', '2015-11-06 12:17:43', 'Visualization', '2015-11-06 12:17:43', NULL, '/stats/statisticsByGroup.htm', NULL, NULL, 1);
INSERT INTO permissions VALUES (17, 'System', '2015-11-06 12:17:43', 'Visualization', '2015-11-06 12:17:43', NULL, '/stats/statisticsByAircon.htm', NULL, NULL, 1);
INSERT INTO permissions VALUES (18, 'System', '2015-11-06 12:17:43', 'Visualization', '2015-11-06 12:17:43', NULL, '/stats/efficiencyRanking.htm ', NULL, NULL, 1);
INSERT INTO permissions VALUES (19, 'System', '2015-11-06 12:17:43', 'Home', '2015-11-06 12:17:43', NULL, '/home/homeScreen.htm', NULL, NULL, 1);
INSERT INTO permissions VALUES (20, 'System', '2015-11-06 12:17:43', 'Home', '2015-11-06 12:17:43', NULL, '/home/saveLastGroupSelection.htm ', NULL, NULL, 1);
INSERT INTO permissions VALUES (21, 'System', '2015-11-06 12:17:43', 'Home', '2015-11-06 12:17:43', NULL, '/home/getLastGroupSelection.htm ', NULL, NULL, 2);
INSERT INTO permissions VALUES (22, 'System', '2015-11-06 12:17:43', 'Home', '2015-11-06 12:17:43', NULL, '/notification/getNotificationCount.htm', NULL, NULL, 2);
INSERT INTO permissions VALUES (23, 'System', '2015-11-06 12:17:43', 'Home', '2015-11-06 12:17:43', NULL, '/dashboard/getEfficiencyRating.htm', NULL, NULL, 2);
INSERT INTO permissions VALUES (24, 'System', '2015-11-06 12:17:43', 'Home', '2015-11-06 12:17:43', NULL, '/stats/eneryConsumption.htm', NULL, NULL, 2);
INSERT INTO permissions VALUES (25, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/getNotificationDetails.htm', NULL, NULL, 2);
INSERT INTO permissions VALUES (26, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/getNotificationSetting.htm', NULL, NULL, 2);
INSERT INTO permissions VALUES (27, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/saveNotificationSetting.htm', NULL, NULL, 2);
INSERT INTO permissions VALUES (28, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/saveNotificationUserSetting.htm', NULL, NULL, 3);
INSERT INTO permissions VALUES (29, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/getNotificationUserSetting.htm', NULL, NULL, 3);
INSERT INTO permissions VALUES (30, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/getNotificationOverView.htm', NULL, NULL, 3);
INSERT INTO permissions VALUES (31, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/downalodNotificationDetails.htm', NULL, NULL, 3);
INSERT INTO permissions VALUES (32, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/downloadNotificationOverView.htm', NULL, NULL, 3);
INSERT INTO permissions VALUES (33, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/getAlarmType.htm', NULL, NULL, 3);
INSERT INTO permissions VALUES (34, 'System', '2015-11-06 12:17:43', 'Notification', '2015-11-06 12:17:43', NULL, '/notification/getAlarmType.htm', NULL, NULL, 3);
INSERT INTO permissions VALUES (35, 'System', '2015-11-06 12:17:43', 'System Settings', '2015-11-06 12:17:43', NULL, '/co2Factor/saveCO2Factor.htm', NULL, NULL, 3);


--
-- Name: permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('permissions_id_seq', 35, true);


--
-- Data for Name: power_consumption_capacity; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: power_consumption_capacity_daily; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: power_consumption_capacity_daily_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_daily_id_seq', 200, false);


--
-- Name: power_consumption_capacity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_id_seq', 5, true);


--
-- Data for Name: power_consumption_capacity_monthly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: power_consumption_capacity_monthly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_monthly_id_seq', 100, false);


--
-- Data for Name: power_consumption_capacity_weekly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: power_consumption_capacity_weekly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_weekly_id_seq', 100, false);


--
-- Data for Name: power_consumption_capacity_yearly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: power_consumption_capacity_yearly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('power_consumption_capacity_yearly_id_seq', 100, false);


--
-- Data for Name: pulse_meter; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO pulse_meter VALUES (2, 'dsds', 0, NULL, '[2:{A0000000000F02000009}]', 4, 'null', '1', '1', '2015-12-11 15:37:04.372+08', '0');


--
-- Name: pulse_meter_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pulse_meter_id_seq', 2, true);


--
-- Data for Name: ratingmaster; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO ratingmaster VALUES (1, '#008500', 'A', '3.6-10');
INSERT INTO ratingmaster VALUES (2, '#008501', 'B', '3.4-3.6');
INSERT INTO ratingmaster VALUES (3, '#008502', 'C', '3.2-3.4');
INSERT INTO ratingmaster VALUES (4, '#008502', 'D', '2.8-3.2');


--
-- Name: ratingmaster_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ratingmaster_id_seq', 1, false);


--
-- Data for Name: rc_prohibition; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: rc_prohibition_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rc_prohibition_id_seq', 1, false);


--
-- Data for Name: rcoperation_log; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: rcoperation_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rcoperation_log_id_seq', 52, true);


--
-- Data for Name: rcoperation_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO rcoperation_master VALUES (1, 'MODE');
INSERT INTO rcoperation_master VALUES (2, 'TEMPERATURE');
INSERT INTO rcoperation_master VALUES (3, 'FANSPEED');
INSERT INTO rcoperation_master VALUES (4, 'WINDDIRECTION');
INSERT INTO rcoperation_master VALUES (5, 'POWERSTATUS');
INSERT INTO rcoperation_master VALUES (6, 'ENERGY_SAVING');
INSERT INTO rcoperation_master VALUES (7, 'PROHIBITION_POWERSTATUS');
INSERT INTO rcoperation_master VALUES (8, 'PROHIBITON_MODE');
INSERT INTO rcoperation_master VALUES (9, 'PROHIBITION_FANSPEED');
INSERT INTO rcoperation_master VALUES (10, 'PROHIBITION_WINDRIECTION');
INSERT INTO rcoperation_master VALUES (11, 'PROHIBITION_SET_TEMP');


--
-- Data for Name: rcuser_action; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: refrigerantcircuit_statistics; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO refrigerantcircuit_statistics VALUES (1, 1, 20, 30.00, 20, 10, 20, 30, '2015-12-08 08:26:03', NULL, NULL);
INSERT INTO refrigerantcircuit_statistics VALUES (2, 1, 20, 30.00, 20, 10, 20, 30, '2015-12-08 08:26:03', NULL, NULL);


--
-- Data for Name: refrigerantcircuit_statistics_daily; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: refrigerantcircuit_statistics_daily_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('refrigerantcircuit_statistics_daily_id_seq', 1, false);


--
-- Name: refrigerantcircuit_statistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('refrigerantcircuit_statistics_id_seq', 1, false);


--
-- Data for Name: refrigerantcircuit_statistics_monthly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: refrigerantcircuit_statistics_monthly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('refrigerantcircuit_statistics_monthly_id_seq', 1, false);


--
-- Data for Name: refrigerantcircuit_statistics_weekly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: refrigerantcircuit_statistics_weekly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('refrigerantcircuit_statistics_weekly_id_seq', 1, false);


--
-- Data for Name: refrigerantcircuit_statistics_yearly; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: refrigerantcircuit_statistics_yearly_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('refrigerantcircuit_statistics_yearly_id_seq', 1, false);


--
-- Data for Name: refrigerantmaster; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO refrigerantmaster VALUES (1, 'RC1', 3, 'link1', 1, 111.00000000000000, 112.00000000000000, 113.00000000000000, 114.00000000000000, 5, 221.00023000000000, 222.00000000000000, 223.00000000000000, 224.00000000000000, NULL, NULL, NULL, NULL, NULL, 'ST030');
INSERT INTO refrigerantmaster VALUES (2, 'RC2', 3, 'link2', NULL, NULL, NULL, NULL, NULL, 2, 225.02340000000000, 226.00000000000000, 227.00000000000000, 228.00000000000000, NULL, NULL, NULL, NULL, NULL, 'ST030');
INSERT INTO refrigerantmaster VALUES (3, 'RC3', 3, 'link3', 4, 115.00000000000000, 116.00000000000000, 117.00000000000000, 118.02340000000000, NULL, NULL, NULL, NULL, NULL, 3, 331.12300000000000, 332.00120000000000, 333.00000000000000, 334.00000000000000, 'ST030');
INSERT INTO refrigerantmaster VALUES (4, 'RC4', 3, 'link4', 4, 115.00000000000000, 116.00000000000000, 117.00000000000000, 118.02300000000000, NULL, NULL, NULL, NULL, NULL, 3, 332.00123000000000, 332.00000000000000, 333.00000000000000, 334.00000000000000, 'ST008');


--
-- Data for Name: rolefunctionalgrp; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO rolefunctionalgrp VALUES (9, 23, 1, 2, NULL, NULL, '2015-11-25 19:10:29.235', NULL);
INSERT INTO rolefunctionalgrp VALUES (10, 23, 2, 2, NULL, NULL, '2015-11-25 19:10:29.235', NULL);
INSERT INTO rolefunctionalgrp VALUES (11, 23, 3, 2, NULL, NULL, '2015-11-25 19:10:29.235', NULL);
INSERT INTO rolefunctionalgrp VALUES (12, 23, 4, 2, NULL, NULL, '2015-11-25 19:10:29.235', NULL);
INSERT INTO rolefunctionalgrp VALUES (13, 1, 1, 2, NULL, NULL, '2015-11-25 19:46:13.097', NULL);
INSERT INTO rolefunctionalgrp VALUES (14, 1, 2, 2, NULL, NULL, '2015-11-25 19:46:18.677', NULL);
INSERT INTO rolefunctionalgrp VALUES (15, 1, 3, 2, NULL, NULL, '2015-11-25 19:46:20.775', NULL);
INSERT INTO rolefunctionalgrp VALUES (16, 1, 4, 2, NULL, NULL, '2015-11-25 19:46:22.671', NULL);
INSERT INTO rolefunctionalgrp VALUES (17, 1, 1, 2, NULL, NULL, '2015-11-25 19:48:31.126', NULL);
INSERT INTO rolefunctionalgrp VALUES (18, 1, 2, 2, NULL, NULL, '2015-11-25 19:48:31.126', NULL);
INSERT INTO rolefunctionalgrp VALUES (19, 1, 3, 2, NULL, NULL, '2015-11-25 19:48:31.126', NULL);
INSERT INTO rolefunctionalgrp VALUES (20, 1, 4, 2, NULL, NULL, '2015-11-25 19:48:31.126', NULL);
INSERT INTO rolefunctionalgrp VALUES (21, 1, 1, 2, NULL, NULL, '2015-11-25 19:56:17.383', NULL);
INSERT INTO rolefunctionalgrp VALUES (22, 1, 2, 2, NULL, NULL, '2015-11-25 19:56:17.383', NULL);
INSERT INTO rolefunctionalgrp VALUES (23, 1, 3, 2, NULL, NULL, '2015-11-25 19:56:17.383', NULL);
INSERT INTO rolefunctionalgrp VALUES (24, 1, 4, 2, NULL, NULL, '2015-11-25 19:56:17.383', NULL);
INSERT INTO rolefunctionalgrp VALUES (25, 1, 1, 2, NULL, NULL, '2015-11-26 10:54:38.531', NULL);
INSERT INTO rolefunctionalgrp VALUES (26, 1, 2, 2, NULL, NULL, '2015-11-26 10:54:43.2', NULL);
INSERT INTO rolefunctionalgrp VALUES (27, 1, 3, 2, NULL, NULL, '2015-11-26 10:54:44.628', NULL);
INSERT INTO rolefunctionalgrp VALUES (28, 1, 4, 2, NULL, NULL, '2015-11-26 10:54:46.167', NULL);
INSERT INTO rolefunctionalgrp VALUES (29, 29, 1, 2, NULL, NULL, '2015-11-26 15:24:36.944', NULL);
INSERT INTO rolefunctionalgrp VALUES (30, 29, 2, 2, NULL, NULL, '2015-11-26 15:24:36.944', NULL);
INSERT INTO rolefunctionalgrp VALUES (31, 29, 3, 2, NULL, NULL, '2015-11-26 15:24:36.944', NULL);
INSERT INTO rolefunctionalgrp VALUES (32, 29, 4, 2, NULL, NULL, '2015-11-26 15:24:36.944', NULL);
INSERT INTO rolefunctionalgrp VALUES (33, 30, 1, 2, NULL, NULL, '2015-11-26 15:28:01.8', NULL);
INSERT INTO rolefunctionalgrp VALUES (34, 30, 2, 2, NULL, NULL, '2015-11-26 15:28:01.8', NULL);
INSERT INTO rolefunctionalgrp VALUES (35, 30, 3, 2, NULL, NULL, '2015-11-26 15:28:01.8', NULL);
INSERT INTO rolefunctionalgrp VALUES (36, 30, 4, 2, NULL, NULL, '2015-11-26 15:28:01.8', NULL);
INSERT INTO rolefunctionalgrp VALUES (37, 31, 1, 2, NULL, NULL, '2015-11-26 18:48:54.717', NULL);
INSERT INTO rolefunctionalgrp VALUES (38, 31, 2, 2, NULL, NULL, '2015-11-26 18:48:56.264', NULL);
INSERT INTO rolefunctionalgrp VALUES (39, 31, 3, 2, NULL, NULL, '2015-11-26 18:48:59.797', NULL);
INSERT INTO rolefunctionalgrp VALUES (40, 31, 4, 2, NULL, NULL, '2015-11-26 18:49:02.504', NULL);
INSERT INTO rolefunctionalgrp VALUES (41, 1, 1, 2, NULL, NULL, '2015-11-26 19:32:46.473', NULL);
INSERT INTO rolefunctionalgrp VALUES (42, 1, 2, 2, NULL, NULL, '2015-11-26 19:32:46.473', NULL);
INSERT INTO rolefunctionalgrp VALUES (43, 1, 3, 2, NULL, NULL, '2015-11-26 19:32:46.473', NULL);
INSERT INTO rolefunctionalgrp VALUES (44, 1, 4, 2, NULL, NULL, '2015-11-26 19:32:46.473', NULL);
INSERT INTO rolefunctionalgrp VALUES (45, 1, 1, 2, NULL, NULL, '2015-11-26 19:34:17.705', NULL);
INSERT INTO rolefunctionalgrp VALUES (46, 1, 2, 2, NULL, NULL, '2015-11-26 19:34:20.063', NULL);
INSERT INTO rolefunctionalgrp VALUES (47, 1, 3, 2, NULL, NULL, '2015-11-26 19:34:21.313', NULL);
INSERT INTO rolefunctionalgrp VALUES (48, 1, 4, 2, NULL, NULL, '2015-11-26 19:34:23.093', NULL);
INSERT INTO rolefunctionalgrp VALUES (49, 32, 1, 3, NULL, NULL, '2015-11-27 12:28:51.747', NULL);
INSERT INTO rolefunctionalgrp VALUES (50, 32, 2, 3, NULL, NULL, '2015-11-27 12:29:05.423', NULL);
INSERT INTO rolefunctionalgrp VALUES (51, 32, 3, 3, NULL, NULL, '2015-11-27 12:29:07.704', NULL);
INSERT INTO rolefunctionalgrp VALUES (52, 32, 4, 3, NULL, NULL, '2015-11-27 12:29:13.987', NULL);
INSERT INTO rolefunctionalgrp VALUES (53, 35, 1, 3, NULL, NULL, '2015-11-27 12:55:47.982', NULL);
INSERT INTO rolefunctionalgrp VALUES (54, 35, 2, 3, NULL, NULL, '2015-11-27 12:55:47.982', NULL);
INSERT INTO rolefunctionalgrp VALUES (55, 36, 1, 3, NULL, NULL, '2015-11-27 13:05:34.644', NULL);
INSERT INTO rolefunctionalgrp VALUES (56, 36, 2, 3, NULL, NULL, '2015-11-27 13:05:34.644', NULL);
INSERT INTO rolefunctionalgrp VALUES (57, 44, 1, 3, NULL, NULL, '2015-11-27 16:06:31.137', NULL);
INSERT INTO rolefunctionalgrp VALUES (58, 44, 2, 3, NULL, NULL, '2015-11-27 16:06:31.137', NULL);
INSERT INTO rolefunctionalgrp VALUES (59, 1, 1, 3, NULL, NULL, '2015-11-27 16:35:52.166', NULL);
INSERT INTO rolefunctionalgrp VALUES (60, 1, 2, 3, NULL, NULL, '2015-11-27 16:35:52.166', NULL);
INSERT INTO rolefunctionalgrp VALUES (61, 1, 3, 3, NULL, NULL, '2015-11-27 16:35:52.166', NULL);
INSERT INTO rolefunctionalgrp VALUES (62, 1, 4, 3, NULL, NULL, '2015-11-27 16:35:52.166', NULL);
INSERT INTO rolefunctionalgrp VALUES (63, 49, 1, 2, NULL, NULL, '2015-11-27 20:17:00.014', NULL);
INSERT INTO rolefunctionalgrp VALUES (64, 49, 2, 2, NULL, NULL, '2015-11-27 20:17:00.014', NULL);
INSERT INTO rolefunctionalgrp VALUES (65, 2, 1, 2, NULL, NULL, '2015-11-27 20:30:14.681', NULL);
INSERT INTO rolefunctionalgrp VALUES (66, 2, 2, 2, NULL, NULL, '2015-11-27 20:30:17.322', NULL);
INSERT INTO rolefunctionalgrp VALUES (67, 51, 1, 2, NULL, NULL, '2015-11-28 15:23:16.633', NULL);
INSERT INTO rolefunctionalgrp VALUES (68, 51, 2, 2, NULL, NULL, '2015-11-28 15:23:16.633', NULL);
INSERT INTO rolefunctionalgrp VALUES (69, 52, 1, 2, NULL, NULL, '2015-11-28 15:37:11.13', NULL);
INSERT INTO rolefunctionalgrp VALUES (70, 52, 2, 2, NULL, NULL, '2015-11-28 15:37:11.13', NULL);
INSERT INTO rolefunctionalgrp VALUES (71, 53, 1, 2, NULL, NULL, '2015-11-28 15:38:39.847', NULL);
INSERT INTO rolefunctionalgrp VALUES (72, 53, 2, 2, NULL, NULL, '2015-11-28 15:38:39.847', NULL);
INSERT INTO rolefunctionalgrp VALUES (73, 54, 1, 2, NULL, NULL, '2015-11-28 15:45:01.22', NULL);
INSERT INTO rolefunctionalgrp VALUES (74, 54, 2, 2, NULL, NULL, '2015-11-28 15:45:01.22', NULL);
INSERT INTO rolefunctionalgrp VALUES (85, 68, 1, 2, NULL, NULL, '2015-11-30 12:44:43.258', NULL);
INSERT INTO rolefunctionalgrp VALUES (86, 68, 2, 2, NULL, NULL, '2015-11-30 12:44:43.258', NULL);
INSERT INTO rolefunctionalgrp VALUES (87, 3, 1, 2, NULL, NULL, '2015-11-30 12:44:43.271', NULL);
INSERT INTO rolefunctionalgrp VALUES (88, 3, 2, 2, NULL, NULL, '2015-11-30 12:44:43.271', NULL);
INSERT INTO rolefunctionalgrp VALUES (89, 3, 1, 2, NULL, NULL, '2015-11-30 13:34:23.905', NULL);
INSERT INTO rolefunctionalgrp VALUES (90, 3, 2, 2, NULL, NULL, '2015-11-30 13:34:23.905', NULL);
INSERT INTO rolefunctionalgrp VALUES (95, 3, 1, 2, NULL, NULL, '2015-11-30 16:04:15.072', NULL);
INSERT INTO rolefunctionalgrp VALUES (96, 3, 2, 2, NULL, NULL, '2015-11-30 16:04:15.072', NULL);
INSERT INTO rolefunctionalgrp VALUES (97, 3, 1, 2, NULL, NULL, '2015-11-30 16:16:13.319', NULL);
INSERT INTO rolefunctionalgrp VALUES (98, 3, 2, 2, NULL, NULL, '2015-11-30 16:16:13.319', NULL);


--
-- Name: rolefunctionalgrp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rolefunctionalgrp_id_seq', 170, true);


--
-- Data for Name: rolehistory; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO rolehistory VALUES (1, 1, 'edit', 1, '2015-11-27 13:26:45.481', 1);
INSERT INTO rolehistory VALUES (2, 1, 'delete', 2, '2015-11-27 13:26:47.166', 1);
INSERT INTO rolehistory VALUES (5, 44, 'created', 10, '2015-11-27 16:06:31.137', 3);
INSERT INTO rolehistory VALUES (6, 1, 'edit', 10, '2015-11-27 16:35:47.239', 3);
INSERT INTO rolehistory VALUES (7, 3, 'delete', 1, '2015-11-27 16:54:19.674', 2);
INSERT INTO rolehistory VALUES (8, 3, 'delete', 1, '2015-11-27 17:04:24.14', 2);
INSERT INTO rolehistory VALUES (9, 3, 'delete', 1, '2015-11-27 17:09:42.174', 2);
INSERT INTO rolehistory VALUES (10, 3, 'delete', 1, '2015-11-27 17:26:41.06', 2);
INSERT INTO rolehistory VALUES (11, 3, 'delete', 1, '2015-11-27 17:28:31.875', 2);
INSERT INTO rolehistory VALUES (12, 3, 'delete', 1, '2015-11-27 17:35:55.122', 2);
INSERT INTO rolehistory VALUES (17, 49, 'created', 1, '2015-11-27 20:16:40.537', 2);
INSERT INTO rolehistory VALUES (18, 2, 'edit', 1, '2015-11-27 20:29:38.326', 2);
INSERT INTO rolehistory VALUES (19, 2, 'delete', 1, '2015-11-27 20:45:42.818', 2);
INSERT INTO rolehistory VALUES (20, 3, 'edit', 1, '2015-11-28 12:26:44.6', 2);
INSERT INTO rolehistory VALUES (21, 3, 'edit', 1, '2015-11-28 12:29:47.83', 2);
INSERT INTO rolehistory VALUES (23, 51, 'created', 1, '2015-11-28 15:23:13.929', 2);
INSERT INTO rolehistory VALUES (24, 52, 'created', 1, '2015-11-28 15:37:11.125', 2);
INSERT INTO rolehistory VALUES (25, 53, 'created', 1, '2015-11-28 15:38:33.783', 2);
INSERT INTO rolehistory VALUES (26, 54, 'created', 1, '2015-11-28 15:45:01.213', 2);
INSERT INTO rolehistory VALUES (31, 2, 'delete', 1, '2015-11-29 21:48:15.427', 2);
INSERT INTO rolehistory VALUES (33, 2, 'delete', 1, '2015-11-30 12:28:29.638', 2);
INSERT INTO rolehistory VALUES (34, 68, 'created', 1, '2015-11-30 12:44:43.254', 2);
INSERT INTO rolehistory VALUES (35, 3, 'edit', 1, '2015-11-30 12:44:43.269', 2);
INSERT INTO rolehistory VALUES (36, 3, 'edit', 1, '2015-11-30 13:34:23.9', 2);
INSERT INTO rolehistory VALUES (39, 3, 'edit', 1, '2015-11-30 16:04:15.068', 2);
INSERT INTO rolehistory VALUES (40, 2, 'delete', 1, '2015-11-30 16:04:15.089', 2);
INSERT INTO rolehistory VALUES (41, 3, 'edit', 1, '2015-11-30 16:16:13.317', 2);
INSERT INTO rolehistory VALUES (42, 2, 'delete', 1, '2015-11-30 16:16:13.334', 2);


--
-- Name: rolehistory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rolehistory_id_seq', 90, true);


--
-- Data for Name: rolepermissions; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: rolepermissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rolepermissions_id_seq', 1, false);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO roles VALUES (35, NULL, '2015-11-30 12:36:35.013', 'User1', NULL, NULL, true, 1, 2);
INSERT INTO roles VALUES (4, '1', '2015-08-13 11:16:00', 'First Level Customer', '2015-08-13 11:16:00', '1', NULL, 3, 2);
INSERT INTO roles VALUES (68, '1', '2015-11-30 12:44:43.249', 'customer_10', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (24, NULL, '2015-12-03 17:33:05.657', 'panasonic7', NULL, NULL, true, 1, 2);
INSERT INTO roles VALUES (1, '10', '2015-11-27 16:35:41.509', 'customer', NULL, NULL, NULL, 3, 1);
INSERT INTO roles VALUES (3, '1', '2015-11-30 16:16:13.315', 'panasonic', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (2, '1', '2015-11-30 16:16:13.334', 'panasonic1', NULL, NULL, true, 1, 2);
INSERT INTO roles VALUES (5, '1', '2015-08-13 11:16:00', 'Customer1', '2015-08-13 11:16:00', '1', NULL, 3, 2);
INSERT INTO roles VALUES (23, NULL, NULL, 'panasonic3', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (25, NULL, NULL, 'customer2', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (29, NULL, '2015-11-26 15:24:32.067', 'panasonic4', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (31, NULL, '2015-11-27 16:47:27.713', 'panasonic5', NULL, NULL, true, 1, 3);
INSERT INTO roles VALUES (44, '10', '2015-11-27 16:06:31.095', 'User2', NULL, NULL, NULL, 1, 3);
INSERT INTO roles VALUES (51, '1', '2015-11-28 15:22:57.96', 'customer_11', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (52, '1', '2015-11-28 15:37:11.089', 'customer_12', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (53, '1', '2015-11-28 15:38:00.409', 'customer_13', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (36, NULL, '2015-11-27 13:05:34.165', 'User', NULL, NULL, NULL, 1, 3);
INSERT INTO roles VALUES (22, NULL, NULL, 'panasonic6', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (30, NULL, '2015-11-26 15:27:53.644', 'panasonic8', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (32, NULL, '2015-11-27 12:21:40.064', 'panasonic9', NULL, NULL, NULL, 1, 3);
INSERT INTO roles VALUES (49, '1', '2015-11-27 20:16:27.87', 'panasonic10', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (26, '1', '2015-11-25 19:56:17.337', 'customer8', NULL, NULL, NULL, 1, 2);
INSERT INTO roles VALUES (54, '1', '2015-11-28 15:44:59.704', 'customer_1', NULL, NULL, NULL, 1, 2);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('roles_id_seq', 95, true);


--
-- Data for Name: roletype; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO roletype VALUES (1, 'Panasonic');
INSERT INTO roletype VALUES (2, 'Installer AC/CA');
INSERT INTO roletype VALUES (3, 'Customer');


--
-- Data for Name: session; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO session VALUES (3045, 'RSI', '2015-09-01 20:12:00', 'testtoken@rsystems.com', 0, 'EF0C425256996FDDF274E39B159918CD', '2015-09-01 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3047, 'RSI', '2015-09-03 20:12:00', 'testtoken@rsystems.com', 0, 'BEB5F241FBAFFC4BC29DAABA9FF8D64B', '2015-09-03 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (93, NULL, '2015-10-07 15:45:40.47', 'testtoken@rsystems.com', 0, 'FD2D0D8BDAAD91E2CDCE915D741571DA', '2015-10-07 15:53:39.558', NULL, 'AAAAAAA');
INSERT INTO session VALUES (96, NULL, '2015-10-07 16:01:54.16', 'testtoken@rsystems.com', 0, 'E7F08C7723F71BA3EC7644E8F19CF938', '2015-10-07 16:07:44.133', NULL, 'AAAAAAA');
INSERT INTO session VALUES (100, NULL, '2015-10-08 18:44:36.429', 'testtoken@rsystems.com', 0, '2A09F2005C3861EAD7AB7A311EAB865F', '2015-10-08 18:45:27.23', NULL, 'AAAAAAA');
INSERT INTO session VALUES (3028, 'RSI', '2015-08-15 20:12:00', 'testtoken@rsystems.com', 0, '8FB230197571A7866E748EB81ECBBFA7', '2015-08-15 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (94, NULL, '2015-10-07 15:53:40.61', 'testtoken@rsystems.com', 0, '1DD0E81197729748C65A1BAF7ADAA3E3', '2015-10-07 15:57:17.046', NULL, 'AAAAAAA');
INSERT INTO session VALUES (99, NULL, '2015-10-08 18:39:30.543', 'testtoken@rsystems.com', 0, '2799DE357575EA64A94EC7AB4DAE0D45', '2015-10-08 18:44:36.419', NULL, 'AAAAAAA');
INSERT INTO session VALUES (3027, 'RSI', '2015-08-14 20:12:00', 'testtoken@rsystems.com', 0, 'D0C709CE16F171B0386AD2D0C9BDE05A', '2015-08-14 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (95, NULL, '2015-10-07 15:57:18.939', 'testtoken@rsystems.com', 0, '007589044591194CF356A97B40C4ED17', '2015-10-07 16:01:54.131', NULL, 'AAAAAAA');
INSERT INTO session VALUES (3029, 'RSI', '2015-08-16 20:12:00', 'testtoken@rsystems.com', 0, '5679F660BE3E4B43F35E1BB05DC5C49B', '2015-08-16 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3030, 'RSI', '2015-08-17 20:12:00', 'testtoken@rsystems.com', 0, '4DD9D831CDA959F477F8A56A75E847DB', '2015-08-17 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3032, 'RSI', '2015-08-19 20:12:00', 'testtoken@rsystems.com', 0, 'D27FF17BFA92F2F7E9DF7A4F2D416FCE', '2015-08-19 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3035, 'RSI', '2015-08-22 20:12:00', 'testtoken@rsystems.com', 0, '55640BB526927FA42086BAAD40045C9F', '2015-08-22 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3033, 'RSI', '2015-08-20 20:12:00', 'testtoken@rsystems.com', 0, 'C04544FFBF90E2AEE006AD8705A724C7', '2015-08-20 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3031, 'RSI', '2015-08-18 20:12:00', 'testtoken@rsystems.com', 0, '3B231150BE01A602AB939ECA5D01F383', '2015-08-18 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3034, 'RSI', '2015-08-21 20:12:00', 'testtoken@rsystems.com', 0, 'B26DE3013A9798A5FEFA4A461AE871B7', '2015-08-21 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3036, 'RSI', '2015-08-23 20:12:00', 'testtoken@rsystems.com', 0, '189C70C128A8639FC02D9531AF206E56', '2015-08-23 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3038, 'RSI', '2015-08-25 20:12:00', 'testtoken@rsystems.com', 0, '701426B63E31777FF1A049B95E771E33', '2015-08-25 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3041, 'RSI', '2015-08-28 20:12:00', 'testtoken@rsystems.com', 0, '4392C604A7784751BC671183B0583D15', '2015-08-28 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3043, 'RSI', '2015-08-30 20:12:00', 'testtoken@rsystems.com', 0, '1044EB2DE0AECAC07C4F01B5E0DA200A', '2015-08-30 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3049, 'RSI', '2015-09-05 20:12:00', 'testtoken@rsystems.com', 0, 'B9FE54C68D39E596F4B8C1C3D19DCB40', '2015-09-05 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3051, 'RSI', '2015-09-07 20:12:00', 'testtoken@rsystems.com', 0, '23303F4F4FE45223C450196A69EAB095', '2015-10-07 15:45:40.445', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3050, 'RSI', '2015-09-06 20:12:00', 'testtoken@rsystems.com', 0, '3AF0B0109C852916589CDA9527EC0EE2', '2015-09-06 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3048, 'RSI', '2015-09-04 20:12:00', 'testtoken@rsystems.com', 0, '6A6C5856B3332CAAAF2CD51C8C48BB1E', '2015-09-04 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3046, 'RSI', '2015-09-02 20:12:00', 'testtoken@rsystems.com', 0, '2EB6227CD92320FC9E61ECF2EC0C7E16', '2015-09-02 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3044, 'RSI', '2015-08-31 20:12:00', 'testtoken@rsystems.com', 0, 'E358BA83C5659DCA609D7BC22132FB74', '2015-08-31 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3042, 'RSI', '2015-08-29 20:12:00', 'testtoken@rsystems.com', 0, 'ECEC45AFFA341FC5913C026D25786EB2', '2015-08-29 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3040, 'RSI', '2015-08-27 20:12:00', 'testtoken@rsystems.com', 0, '62810887F870F589156D64AE5E267E12', '2015-08-27 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3039, 'RSI', '2015-08-26 20:12:00', 'testtoken@rsystems.com', 0, '9ADA1905A8E0F8049F0E13B7C3F603D8', '2015-08-26 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (3037, 'RSI', '2015-08-24 20:12:00', 'testtoken@rsystems.com', 0, '471E9C35780E01EED5C2F5C31ADFA307', '2015-08-24 20:12:00', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (182, NULL, '2015-11-25 18:54:49.969', NULL, 0, '2', '2015-11-25 18:56:52.364', NULL, 'BBBBBBB');
INSERT INTO session VALUES (183, NULL, '2015-11-25 18:57:01.367', NULL, 0, '2', '2015-11-25 19:14:57.863', NULL, 'BBBBBBB');
INSERT INTO session VALUES (184, NULL, '2015-11-25 19:14:58.309', NULL, 0, '2', '2015-11-27 12:25:22.561', NULL, 'BBBBBBB');
INSERT INTO session VALUES (185, NULL, '2015-11-27 12:25:22.585', NULL, 0, '2', '2015-11-27 12:26:24.526', NULL, 'BBBBBBB');
INSERT INTO session VALUES (186, NULL, '2015-11-27 12:26:27.531', NULL, 0, '2', '2015-11-27 12:30:34.125', NULL, 'BBBBBBB');
INSERT INTO session VALUES (187, NULL, '2015-11-27 12:30:34.846', NULL, 0, '2', '2015-11-27 12:32:02.803', NULL, 'BBBBBBB');
INSERT INTO session VALUES (188, NULL, '2015-11-27 12:32:03.4', NULL, 0, '2', '2015-11-27 12:34:55.785', NULL, 'BBBBBBB');
INSERT INTO session VALUES (189, NULL, '2015-11-27 12:34:55.813', NULL, 0, '2', '2015-11-27 12:37:46.907', NULL, 'BBBBBBB');
INSERT INTO session VALUES (190, NULL, '2015-11-27 12:37:46.925', NULL, 0, '2', '2015-11-27 12:39:55.908', NULL, 'BBBBBBB');
INSERT INTO session VALUES (191, NULL, '2015-11-27 12:39:55.929', NULL, 0, '2', '2015-11-27 12:47:20.277', NULL, 'BBBBBBB');
INSERT INTO session VALUES (192, NULL, '2015-11-27 12:47:20.333', NULL, 0, '2', '2015-11-27 12:48:10.45', NULL, 'BBBBBBB');
INSERT INTO session VALUES (193, NULL, '2015-11-27 12:48:10.468', NULL, 0, '2', '2015-11-27 12:50:25.687', NULL, 'BBBBBBB');
INSERT INTO session VALUES (194, NULL, '2015-11-27 12:50:25.691', NULL, 0, '38', '2015-11-27 13:00:03.54', NULL, 'BBBBBBB');
INSERT INTO session VALUES (195, NULL, '2015-11-27 13:00:04.34', NULL, 0, '2', '2015-11-27 13:03:55.729', NULL, 'BBBBBBB');
INSERT INTO session VALUES (196, NULL, '2015-11-27 13:03:57.209', NULL, 0, '2', '2015-11-27 13:06:33.361', NULL, 'BBBBBBB');
INSERT INTO session VALUES (197, NULL, '2015-11-27 13:06:33.377', NULL, 0, '2', '2015-11-27 13:15:09.161', NULL, 'BBBBBBB');
INSERT INTO session VALUES (198, NULL, '2015-11-27 13:15:21.375', NULL, 0, '2', '2015-11-27 13:29:37.681', NULL, 'BBBBBBB');
INSERT INTO session VALUES (199, NULL, '2015-11-27 13:29:37.692', NULL, 0, '36', '2015-11-27 13:30:38.376', NULL, 'BBBBBBB');
INSERT INTO session VALUES (200, NULL, '2015-11-27 13:30:38.396', NULL, 0, '2', '2015-11-27 13:33:02.05', NULL, 'BBBBBBB');
INSERT INTO session VALUES (201, NULL, '2015-11-27 13:33:02.069', NULL, 0, '2', '2015-11-27 13:34:52.165', NULL, 'BBBBBBB');
INSERT INTO session VALUES (202, NULL, '2015-11-27 13:34:54.066', NULL, 0, '2', '2015-11-27 13:38:44.802', NULL, 'BBBBBBB');
INSERT INTO session VALUES (203, NULL, '2015-11-27 13:38:47.287', NULL, 0, '2', '2015-11-27 13:40:39.526', NULL, 'BBBBBBB');
INSERT INTO session VALUES (204, NULL, '2015-11-27 13:40:40.543', NULL, 0, '2', '2015-11-27 13:44:28.811', NULL, 'BBBBBBB');
INSERT INTO session VALUES (205, NULL, '2015-11-27 13:44:28.827', NULL, 0, '2', '2015-11-27 13:47:31.106', NULL, 'BBBBBBB');
INSERT INTO session VALUES (206, NULL, '2015-11-27 13:47:31.115', NULL, 0, '2', '2015-11-27 14:49:17.823', NULL, 'BBBBBBB');
INSERT INTO session VALUES (207, NULL, '2015-11-27 14:49:17.833', NULL, 0, '36', '2015-11-27 14:50:19.734', NULL, 'BBBBBBB');
INSERT INTO session VALUES (208, NULL, '2015-11-27 14:50:19.747', NULL, 0, '2', '2015-11-27 14:55:16.533', NULL, 'BBBBBBB');
INSERT INTO session VALUES (209, NULL, '2015-11-27 14:55:16.548', NULL, 0, '2', '2015-11-27 15:03:25.859', NULL, 'BBBBBBB');
INSERT INTO session VALUES (210, NULL, '2015-11-27 15:03:25.869', NULL, 0, '2', '2015-11-27 15:08:29.803', NULL, 'BBBBBBB');
INSERT INTO session VALUES (211, NULL, '2015-11-27 15:08:29.815', NULL, 0, '2', '2015-11-27 15:16:06.876', NULL, 'BBBBBBB');
INSERT INTO session VALUES (212, NULL, '2015-11-27 15:16:06.891', NULL, 0, '2', '2015-11-27 15:17:46.991', NULL, 'BBBBBBB');
INSERT INTO session VALUES (213, NULL, '2015-11-27 15:17:47.002', NULL, 0, '2', '2015-11-27 15:35:31.196', NULL, 'BBBBBBB');
INSERT INTO session VALUES (214, NULL, '2015-11-27 15:35:31.205', NULL, 0, '2', '2015-11-27 15:38:29.825', NULL, 'BBBBBBB');
INSERT INTO session VALUES (215, NULL, '2015-11-27 15:38:29.839', NULL, 0, '2', '2015-11-27 15:41:12.713', NULL, 'BBBBBBB');
INSERT INTO session VALUES (217, NULL, '2015-11-27 15:41:12.729', NULL, 0, '2', '2015-11-27 15:42:09.241', NULL, 'BBBBBBB');
INSERT INTO session VALUES (218, NULL, '2015-11-27 15:42:09.256', NULL, 0, '2', '2015-11-27 15:42:37.061', NULL, 'BBBBBBB');
INSERT INTO session VALUES (219, NULL, '2015-11-27 15:42:37.077', NULL, 0, '2', '2015-11-27 15:43:08.876', NULL, 'BBBBBBB');
INSERT INTO session VALUES (221, NULL, '2015-11-27 15:43:25.789', NULL, 0, '2', '2015-11-27 15:47:19.565', NULL, 'BBBBBBB');
INSERT INTO session VALUES (3026, 'RSI', '2015-08-13 20:12:00', 'admin@panasonic.com', 0, '6AC7EEE40403FBBA9EA651413EA9AB93', '2015-11-27 19:21:05.139', 'RSI', 'AAAAAAA');
INSERT INTO session VALUES (101, NULL, '2015-10-08 18:47:54.543', 'testtoken@rsystems.com', 0, '39E773927B607FD0F92D75D941BFAB8D', '2015-11-27 19:21:05.141', NULL, 'AAAAAAA');
INSERT INTO session VALUES (220, NULL, '2015-11-27 15:43:08.891', NULL, 0, '2', '2015-11-27 15:43:25.773', NULL, 'BBBBBBB');
INSERT INTO session VALUES (222, NULL, '2015-11-27 15:47:19.565', NULL, 0, '2', '2015-11-27 15:49:02.65', NULL, 'BBBBBBB');
INSERT INTO session VALUES (223, NULL, '2015-11-27 15:49:02.65', NULL, 0, '2', '2015-11-27 15:51:53.602', NULL, 'BBBBBBB');
INSERT INTO session VALUES (224, NULL, '2015-11-27 15:51:53.602', NULL, 0, '2', '2015-11-27 15:52:46.479', NULL, 'BBBBBBB');
INSERT INTO session VALUES (225, NULL, '2015-11-27 15:52:46.495', NULL, 0, '2', '2015-11-27 15:58:18.555', NULL, 'BBBBBBB');
INSERT INTO session VALUES (226, NULL, '2015-11-27 15:58:20.006', NULL, 0, '2', '2015-11-27 16:02:38.941', NULL, 'BBBBBBB');
INSERT INTO session VALUES (227, NULL, '2015-11-27 16:02:38.943', NULL, 0, '2', '2015-11-27 16:33:17.451', NULL, 'BBBBBBB');
INSERT INTO session VALUES (228, NULL, '2015-11-27 17:06:42.031', NULL, 0, '2', '2015-11-27 17:08:13.605', NULL, 'BBBBBBB');
INSERT INTO session VALUES (229, NULL, '2015-11-27 17:08:13.621', NULL, 0, '2', '2015-11-27 17:54:14.361', NULL, 'BBBBBBB');
INSERT INTO session VALUES (230, NULL, '2015-11-27 17:54:14.373', NULL, 0, '3', '2015-11-27 17:54:14.475', NULL, 'BBBBBBB');
INSERT INTO session VALUES (231, NULL, '2015-11-27 17:54:14.482', NULL, 0, '5', '2015-11-27 17:54:14.524', NULL, 'BBBBBBB');
INSERT INTO session VALUES (232, NULL, '2015-11-27 17:54:14.577', NULL, 0, '8', '2015-11-27 18:22:33.451', NULL, 'BBBBBBB');
INSERT INTO session VALUES (233, NULL, '2015-11-28 11:25:47.775', NULL, 0, '2', '2015-11-28 11:33:20.224', NULL, 'DDDDDDD');
INSERT INTO session VALUES (236, NULL, '2015-11-28 12:26:48.346', NULL, 0, '2', '2015-11-28 12:29:23.698', NULL, 'CCCCCCC');
INSERT INTO session VALUES (237, NULL, '2015-11-28 12:29:23.724', NULL, 0, '2', '2015-11-29 21:48:02.202', NULL, 'CCCCCCC');
INSERT INTO session VALUES (235, NULL, '2015-11-28 11:51:34.407', NULL, 0, '2', '2015-11-29 21:48:02.302', NULL, 'EEEEEEE');
INSERT INTO session VALUES (238, NULL, '2015-11-29 21:48:02.202', NULL, 0, '34', '2015-11-30 12:28:15.371', NULL, 'CCCCCCC');
INSERT INTO session VALUES (239, NULL, '2015-11-29 21:48:02.302', NULL, 0, '36', '2015-11-30 12:28:15.485', NULL, 'EEEEEEE');
INSERT INTO session VALUES (240, NULL, '2015-11-30 12:28:15.377', NULL, 0, '34', '2015-11-30 12:44:29.552', NULL, 'CCCCCCC');
INSERT INTO session VALUES (243, NULL, '2015-11-30 12:44:29.616', NULL, 1, '37', NULL, NULL, 'EEEEEEE');
INSERT INTO session VALUES (241, NULL, '2015-11-30 12:28:15.493', NULL, 0, '36', '2015-11-30 12:44:29.61', NULL, 'EEEEEEE');
INSERT INTO session VALUES (245, NULL, '2015-11-30 13:01:53.036', NULL, 1, '2', NULL, NULL, 'HHHHHHH');
INSERT INTO session VALUES (244, NULL, '2015-11-30 13:01:11.453', NULL, 0, '2', '2015-11-30 13:01:53.024', NULL, 'HHHHHHH');
INSERT INTO session VALUES (291, NULL, '2015-12-09 16:02:09.302', NULL, 1, '2', NULL, NULL, 'DDDDDDD');
INSERT INTO session VALUES (242, NULL, '2015-11-30 12:44:29.559', NULL, 0, '35', '2015-12-09 16:49:41.026', NULL, 'CCCCCCC');
INSERT INTO session VALUES (293, NULL, '2015-12-11 11:28:02.253', NULL, 0, '6C98F6669F4ED9C2A41944A2441B2DCC', '2015-12-11 11:28:46.49', NULL, 'admin');
INSERT INTO session VALUES (294, NULL, '2015-12-11 11:28:59.421', NULL, 0, '77C5C6FB658D389B6ED1BC874EA3EC62', '2015-12-11 11:29:13.239', NULL, 'srini');
INSERT INTO session VALUES (295, NULL, '2015-12-11 11:29:19.606', NULL, 0, 'B779AD28A48FC8DAA6A22278F4F8E62B', '2015-12-11 11:34:31.653', NULL, 'srini');
INSERT INTO session VALUES (296, NULL, '2015-12-11 11:34:31.669', NULL, 0, '66089D26AEA8959524E0EF4BBBE65E98', '2015-12-11 12:03:25.341', NULL, 'srini');
INSERT INTO session VALUES (297, NULL, '2015-12-11 12:03:33.828', NULL, 0, '05C46CC54735C28AD24A5A30BD58210D', '2015-12-11 12:23:44.813', NULL, 'srini');
INSERT INTO session VALUES (298, NULL, '2015-12-11 12:23:44.842', NULL, 0, 'C4A5EF5475AF59440126E2A8124F46BD', '2015-12-11 12:25:45.782', NULL, 'srini');
INSERT INTO session VALUES (299, NULL, '2015-12-11 12:25:45.809', NULL, 0, 'A8E6EA5A4B36389DE05F8BD6FD0F7FE7', '2015-12-11 12:26:44.677', NULL, 'srini');
INSERT INTO session VALUES (300, NULL, '2015-12-11 12:26:44.7', NULL, 0, '9787D8B181D6C5F0AD16BA545F3F756D', '2015-12-11 12:33:50.596', NULL, 'srini');
INSERT INTO session VALUES (301, NULL, '2015-12-11 12:33:50.621', NULL, 0, '6D76A2F0B954B91F32D9715E5888369B', '2015-12-11 12:35:26.818', NULL, 'srini');
INSERT INTO session VALUES (302, NULL, '2015-12-11 12:35:34.981', NULL, 0, 'E460F9D91F7F82573359FF30C0FB0A3A', '2015-12-11 12:42:44.56', NULL, 'srini');
INSERT INTO session VALUES (303, NULL, '2015-12-11 12:42:44.588', NULL, 0, 'BDF8B0B2B18BAFB623A4D497D3A71E90', '2015-12-11 12:44:22.172', NULL, 'srini');
INSERT INTO session VALUES (304, NULL, '2015-12-11 12:44:35.474', NULL, 0, 'DB5225CB96A49903D6E8A8567DD66EA8', '2015-12-11 12:56:32.995', NULL, 'srini');
INSERT INTO session VALUES (305, NULL, '2015-12-11 12:56:33.05', NULL, 0, '5A48CF20A9A79003831CDE09E3A7CDDB', '2015-12-11 13:07:37.514', NULL, 'srini');
INSERT INTO session VALUES (306, NULL, '2015-12-11 13:07:45.293', NULL, 0, 'BCB81A94F82B8EABF1180B8A74D6CC76', '2015-12-11 13:15:06.787', NULL, 'srini');
INSERT INTO session VALUES (307, NULL, '2015-12-11 13:15:06.818', NULL, 0, 'A011677DB7F831AB4FBE4F72A97C325D', '2015-12-11 13:35:55.608', NULL, 'srini');
INSERT INTO session VALUES (308, NULL, '2015-12-11 13:35:55.68', NULL, 0, '6AC2C8FB09B76F8235D6AACA4766721D', '2015-12-11 14:12:10.486', NULL, 'srini');
INSERT INTO session VALUES (309, NULL, '2015-12-11 14:12:10.55', NULL, 0, 'E8209E49C45D383DD53CFAB809228673', '2015-12-11 14:18:28.669', NULL, 'srini');
INSERT INTO session VALUES (310, NULL, '2015-12-11 14:18:28.697', NULL, 0, 'E94F68475A2E0AA25A1F2C7AAB25B1D5', '2015-12-11 14:21:40.594', NULL, 'srini');
INSERT INTO session VALUES (311, NULL, '2015-12-11 14:21:40.623', NULL, 0, 'A48CFA0DC88ED9EF60F5DA5D87D782B8', '2015-12-11 14:26:07.688', NULL, 'srini');
INSERT INTO session VALUES (312, NULL, '2015-12-11 14:26:07.718', NULL, 0, '15EACD7BC7B998755BC5C7D37D71FC9D', '2015-12-11 14:28:46.154', NULL, 'srini');
INSERT INTO session VALUES (313, NULL, '2015-12-11 14:28:46.202', NULL, 0, 'FF91A51376FCEC9BAFF87159158F4433', '2015-12-11 14:30:33.046', NULL, 'srini');
INSERT INTO session VALUES (314, NULL, '2015-12-11 14:30:33.082', NULL, 0, 'C291AD3E0221252A5A8E836D32974D9A', '2015-12-11 14:36:02.08', NULL, 'srini');
INSERT INTO session VALUES (315, NULL, '2015-12-11 14:36:02.107', NULL, 0, 'B8A401D25AA29528EA806EFDD147E6A4', '2015-12-11 14:40:55.186', NULL, 'srini');
INSERT INTO session VALUES (316, NULL, '2015-12-11 14:40:55.214', NULL, 0, '7039D112868A5CE1DC717003EF65EDF9', '2015-12-11 14:53:57.065', NULL, 'srini');
INSERT INTO session VALUES (317, NULL, '2015-12-11 14:53:57.091', NULL, 0, '60FCB0146E92A3EB1A2F694B06A9CE9F', '2015-12-11 14:56:34.89', NULL, 'srini');
INSERT INTO session VALUES (318, NULL, '2015-12-11 14:56:34.915', NULL, 0, '763E9EB994050C9C2583B0593B9B69AE', '2015-12-11 14:57:43.679', NULL, 'srini');
INSERT INTO session VALUES (319, NULL, '2015-12-11 14:57:43.708', NULL, 0, '2688461D495E9117E946BCFB5E0FC65A', '2015-12-11 14:59:27.003', NULL, 'srini');
INSERT INTO session VALUES (320, NULL, '2015-12-11 14:59:27.028', NULL, 0, '75677552E4EA11CEEEB2F8B4E21BD48C', '2015-12-11 15:00:52.173', NULL, 'srini');
INSERT INTO session VALUES (321, NULL, '2015-12-11 15:00:52.201', NULL, 0, '3FFEAD3E08774E375B8E1936CDD9F8BE', '2015-12-11 15:01:57.718', NULL, 'srini');
INSERT INTO session VALUES (322, NULL, '2015-12-11 15:01:57.746', NULL, 0, '796FDC65211ED48A97FEB5CDF9001C45', '2015-12-11 15:04:43.963', NULL, 'srini');
INSERT INTO session VALUES (323, NULL, '2015-12-11 15:04:51.71', NULL, 0, 'CCB5D775DB1C00E23A3B132D77C3BB4E', '2015-12-11 15:14:46.436', NULL, 'srini');
INSERT INTO session VALUES (324, NULL, '2015-12-11 15:14:46.466', NULL, 0, '579E4B02325C845059D802079547827B', '2015-12-11 15:35:03.734', NULL, 'srini');
INSERT INTO session VALUES (325, NULL, '2015-12-11 15:35:03.765', NULL, 1, '4647C609DC1BA4589C9C6282F3E9766C', NULL, NULL, 'srini');


--
-- Name: session_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('session_id_seq', 325, true);


--
-- Data for Name: svg_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO svg_master VALUES (1, 'SVG1.svg', 'a');
INSERT INTO svg_master VALUES (2, 'SVG2', 'b');
INSERT INTO svg_master VALUES (3, 'SVG3', 'c');
INSERT INTO svg_master VALUES (4, 'SVG4', 'd');
INSERT INTO svg_master VALUES (5, 'SVG5', 'e');


--
-- Data for Name: temp_groupunitdata; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: temp_groupunitdata_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('temp_groupunitdata_id_seq', 1, false);


--
-- Data for Name: tempgroupdata; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 26, 0, 26, 0, 0, 'NONCRITICAL', '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 27, 0, 27, 0, 0, 'CRITICAL', '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 28, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 32, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 33, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 34, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 35, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 36, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 37, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 45, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 46, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 47, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 48, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 49, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 50, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 51, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 52, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 53, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 54, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 55, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 56, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 57, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 58, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 59, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 67, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 76, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 79, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 106, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 119, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 120, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 121, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 122, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 26, 0, 26, 0, 0, 'NONCRITICAL', '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 27, 0, 27, 0, 0, 'CRITICAL', '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 28, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 32, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 33, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 34, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 35, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 36, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 37, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 45, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 46, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 47, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 48, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 49, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 50, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 51, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 52, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 53, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 54, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 55, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 56, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 57, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 58, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 59, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 67, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 76, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 79, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 106, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 119, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 120, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 121, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 122, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 26, 0, 26, 0, 0, 'NONCRITICAL', '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 27, 0, 27, 0, 0, 'CRITICAL', '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 28, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 32, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 33, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 34, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 35, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 36, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 37, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 45, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 46, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 47, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 48, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 49, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 50, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 51, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 52, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 53, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 54, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 55, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 56, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 57, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 58, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 59, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 67, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 76, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 79, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 106, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 119, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 120, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 121, 0, 0, NULL, '', 0, 0);
INSERT INTO tempgroupdata VALUES ('|25|27|31|45', 45, 0, 0, 122, 0, 0, NULL, '', 0, 0);


--
-- Data for Name: timeline; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: timeline_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('timeline_id_seq', 1, false);


--
-- Data for Name: timezonemaster; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO timezonemaster VALUES (5, '(GMT-09:00) Alaska');
INSERT INTO timezonemaster VALUES (6, '(GMT-08:00) Pacific Time (US &amp; Canada)');
INSERT INTO timezonemaster VALUES (7, '(GMT-08:00) Tijuana');
INSERT INTO timezonemaster VALUES (8, '(GMT-07:00) Arizona');
INSERT INTO timezonemaster VALUES (9, '(GMT-07:00) Chihuahua');
INSERT INTO timezonemaster VALUES (10, '(GMT-07:00) Mazatlan');
INSERT INTO timezonemaster VALUES (11, '(GMT-07:00) Mountain Time (US &amp; Canada)');
INSERT INTO timezonemaster VALUES (12, '(GMT-06:00) Central America');
INSERT INTO timezonemaster VALUES (13, '(GMT-06:00) Central Time (US &amp; Canada)');
INSERT INTO timezonemaster VALUES (14, '(GMT-06:00) Guadalajara');
INSERT INTO timezonemaster VALUES (15, '(GMT-06:00) Mexico City');
INSERT INTO timezonemaster VALUES (16, '(GMT-06:00) Monterrey');
INSERT INTO timezonemaster VALUES (17, '(GMT-06:00) Saskatchewan');
INSERT INTO timezonemaster VALUES (18, '(GMT-05:00) Bogota');
INSERT INTO timezonemaster VALUES (19, '(GMT-05:00) Eastern Time (US &amp; Canada)');
INSERT INTO timezonemaster VALUES (20, '(GMT-05:00) Indiana (East)');
INSERT INTO timezonemaster VALUES (21, '(GMT-05:00) Lima');
INSERT INTO timezonemaster VALUES (22, '(GMT-05:00) Quito');
INSERT INTO timezonemaster VALUES (23, '(GMT-04:30) Caracas');
INSERT INTO timezonemaster VALUES (24, '(GMT-04:00) Atlantic Time (Canada)');
INSERT INTO timezonemaster VALUES (25, '(GMT-04:00) Georgetown');
INSERT INTO timezonemaster VALUES (1, 'Asia/Kolkata');
INSERT INTO timezonemaster VALUES (2, 'Singapore');
INSERT INTO timezonemaster VALUES (3, 'America/Los_Angeles');
INSERT INTO timezonemaster VALUES (4, 'Pacific/Honolulu');


--
-- Name: timezonemaster_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('timezonemaster_id_seq', 1, false);


--
-- Data for Name: user_notification_settings; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO user_notification_settings VALUES (22, 1950, 1);
INSERT INTO user_notification_settings VALUES (23, 1950, 2);
INSERT INTO user_notification_settings VALUES (24, 1951, 1);
INSERT INTO user_notification_settings VALUES (25, 1951, 2);
INSERT INTO user_notification_settings VALUES (431, 38, 1);
INSERT INTO user_notification_settings VALUES (432, 38, 2);
INSERT INTO user_notification_settings VALUES (439, 38, 9);
INSERT INTO user_notification_settings VALUES (440, 38, 10);
INSERT INTO user_notification_settings VALUES (413, 40, 8);
INSERT INTO user_notification_settings VALUES (579, 38, 3);
INSERT INTO user_notification_settings VALUES (580, 38, 4);
INSERT INTO user_notification_settings VALUES (581, 38, 5);
INSERT INTO user_notification_settings VALUES (582, 38, 6);
INSERT INTO user_notification_settings VALUES (583, 38, 7);
INSERT INTO user_notification_settings VALUES (584, 38, 8);


--
-- Name: user_notification_settings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_notification_settings_id_seq', 584, true);


--
-- Data for Name: useraudit; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO useraudit VALUES (305, 'failed.signed.in', NULL, '2015-08-13 20:12:00', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (306, 'failed.signed.in', NULL, '2015-08-13 20:12:01', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (307, 'successfully.signed.in', NULL, '2015-08-13 20:12:46', NULL, NULL, 1);
INSERT INTO useraudit VALUES (308, 'failed.signed.in', NULL, '2015-08-13 20:41:14', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (309, 'failed.signed.in', NULL, '2015-08-13 20:41:23', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (310, 'failed.signed.in', NULL, '2015-08-14 01:15:44', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (311, 'failed.signed.in', NULL, '2015-08-14 01:15:45', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (312, 'failed.signed.in', NULL, '2015-08-14 14:48:37', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (313, 'failed.signed.in', NULL, '2015-08-14 14:48:39', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (314, 'failed.signed.in', NULL, '2015-08-14 14:48:40', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (315, 'failed.signed.in', NULL, '2015-08-14 13:55:11', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (316, 'successfully.signed.in', NULL, '2015-08-14 13:55:46', NULL, NULL, 1);
INSERT INTO useraudit VALUES (317, 'successfully.signed.in', NULL, '2015-08-14 14:47:08', NULL, NULL, 1);
INSERT INTO useraudit VALUES (319, 'successfully.signed.in', NULL, '2015-08-14 15:28:38', NULL, NULL, 1);
INSERT INTO useraudit VALUES (320, 'User.logged.out.successfully', NULL, '2015-08-14 15:28:38', NULL, NULL, 1);
INSERT INTO useraudit VALUES (321, 'successfully.signed.in', NULL, '2015-08-14 15:29:29', NULL, NULL, 1);
INSERT INTO useraudit VALUES (322, 'User.logged.out.successfully', NULL, '2015-08-14 15:29:29', NULL, NULL, 1);
INSERT INTO useraudit VALUES (323, 'successfully.signed.in', NULL, '2015-08-14 15:31:20', NULL, NULL, 1);
INSERT INTO useraudit VALUES (324, 'User.logged.out.successfully', NULL, '2015-08-14 15:31:20', NULL, NULL, 1);
INSERT INTO useraudit VALUES (325, 'successfully.signed.in', NULL, '2015-08-17 06:11:26', NULL, NULL, 1);
INSERT INTO useraudit VALUES (326, 'User.logged.out.successfully', NULL, '2015-08-17 06:11:27', NULL, NULL, 1);
INSERT INTO useraudit VALUES (327, 'successfully.signed.in', NULL, '2015-08-17 06:43:38', NULL, NULL, 1);
INSERT INTO useraudit VALUES (328, 'User.logged.out.successfully', NULL, '2015-08-17 06:43:38', NULL, NULL, 1);
INSERT INTO useraudit VALUES (329, 'successfully.signed.in', NULL, '2015-08-17 07:36:39', NULL, NULL, 1);
INSERT INTO useraudit VALUES (330, 'User.logged.out.successfully', NULL, '2015-08-17 07:36:39', NULL, NULL, 1);
INSERT INTO useraudit VALUES (331, 'successfully.signed.in', NULL, '2015-08-17 07:38:34', NULL, NULL, 1);
INSERT INTO useraudit VALUES (332, 'User.logged.out.successfully', NULL, '2015-08-17 07:38:34', NULL, NULL, 1);
INSERT INTO useraudit VALUES (333, 'successfully.signed.in', NULL, '2015-08-17 07:46:17', NULL, NULL, 1);
INSERT INTO useraudit VALUES (334, 'User.logged.out.successfully', NULL, '2015-08-17 07:46:17', NULL, NULL, 1);
INSERT INTO useraudit VALUES (335, 'successfully.signed.in', NULL, '2015-08-17 07:50:08', NULL, NULL, 1);
INSERT INTO useraudit VALUES (336, 'User.logged.out.successfully', NULL, '2015-08-17 07:50:08', NULL, NULL, 1);
INSERT INTO useraudit VALUES (337, 'successfully.signed.in', NULL, '2015-08-17 08:32:36', NULL, NULL, 1);
INSERT INTO useraudit VALUES (338, 'User.logged.out.successfully', NULL, '2015-08-17 08:32:36', NULL, NULL, 1);
INSERT INTO useraudit VALUES (339, 'successfully.signed.in', NULL, '2015-08-17 10:33:39', NULL, NULL, 1);
INSERT INTO useraudit VALUES (340, 'successfully.signed.in', NULL, '2015-08-17 11:14:30', NULL, NULL, 1);
INSERT INTO useraudit VALUES (341, 'User.logged.out.successfully', NULL, '2015-08-17 11:14:30', NULL, NULL, 1);
INSERT INTO useraudit VALUES (342, 'successfully.signed.in', NULL, '2015-08-17 11:29:50', NULL, NULL, 1);
INSERT INTO useraudit VALUES (343, 'User.logged.out.successfully', NULL, '2015-08-17 11:29:50', NULL, NULL, 1);
INSERT INTO useraudit VALUES (344, 'successfully.signed.in', NULL, '2015-08-17 13:16:34', NULL, NULL, 1);
INSERT INTO useraudit VALUES (345, 'User.logged.out.successfully', NULL, '2015-08-17 13:16:34', NULL, NULL, 1);
INSERT INTO useraudit VALUES (346, 'successfully.signed.in', NULL, '2015-08-17 14:54:53', NULL, NULL, 1);
INSERT INTO useraudit VALUES (347, 'User.logged.out.successfully', NULL, '2015-08-17 14:54:53', NULL, NULL, 1);
INSERT INTO useraudit VALUES (348, 'successfully.signed.in', NULL, '2015-08-18 06:48:30', NULL, NULL, 1);
INSERT INTO useraudit VALUES (349, 'User.logged.out.successfully', NULL, '2015-08-18 06:48:30', NULL, NULL, 1);
INSERT INTO useraudit VALUES (350, 'successfully.signed.in', NULL, '2015-08-18 06:49:44', NULL, NULL, 1);
INSERT INTO useraudit VALUES (351, 'User.logged.out.successfully', NULL, '2015-08-18 06:49:44', NULL, NULL, 1);
INSERT INTO useraudit VALUES (352, 'successfully.signed.in', NULL, '2015-08-18 07:24:28', NULL, NULL, 1);
INSERT INTO useraudit VALUES (353, 'User.logged.out.successfully', NULL, '2015-08-18 07:24:28', NULL, NULL, 1);
INSERT INTO useraudit VALUES (354, 'successfully.signed.in', NULL, '2015-08-18 07:43:25', NULL, NULL, 1);
INSERT INTO useraudit VALUES (355, 'User.logged.out.successfully', NULL, '2015-08-18 07:43:25', NULL, NULL, 1);
INSERT INTO useraudit VALUES (356, 'successfully.signed.in', NULL, '2015-08-18 07:44:01', NULL, NULL, 1);
INSERT INTO useraudit VALUES (357, 'User.logged.out.successfully', NULL, '2015-08-18 07:44:01', NULL, NULL, 1);
INSERT INTO useraudit VALUES (358, 'successfully.signed.in', NULL, '2015-08-18 07:47:22', NULL, NULL, 1);
INSERT INTO useraudit VALUES (359, 'User.logged.out.successfully', NULL, '2015-08-18 07:47:22', NULL, NULL, 1);
INSERT INTO useraudit VALUES (360, 'successfully.signed.in', NULL, '2015-08-18 07:50:45', NULL, NULL, 1);
INSERT INTO useraudit VALUES (361, 'User.logged.out.successfully', NULL, '2015-08-18 07:50:45', NULL, NULL, 1);
INSERT INTO useraudit VALUES (362, 'successfully.signed.in', NULL, '2015-08-18 08:57:27', NULL, NULL, 1);
INSERT INTO useraudit VALUES (363, 'User.logged.out.successfully', NULL, '2015-08-18 08:57:27', NULL, NULL, 1);
INSERT INTO useraudit VALUES (500, 'successfully.signed.in', NULL, '2015-10-07 15:45:40.4', NULL, NULL, 1);
INSERT INTO useraudit VALUES (501, 'User.logged.out.successfully', NULL, '2015-10-07 15:45:40.465', NULL, NULL, 1);
INSERT INTO useraudit VALUES (502, 'successfully.signed.in', NULL, '2015-10-07 15:53:36.641', NULL, NULL, 1);
INSERT INTO useraudit VALUES (503, 'User.logged.out.successfully', NULL, '2015-10-07 15:53:39.665', NULL, NULL, 1);
INSERT INTO useraudit VALUES (504, 'successfully.signed.in', NULL, '2015-10-07 15:57:13.384', NULL, NULL, 1);
INSERT INTO useraudit VALUES (505, 'User.logged.out.successfully', NULL, '2015-10-07 15:57:17.151', NULL, NULL, 1);
INSERT INTO useraudit VALUES (506, 'successfully.signed.in', NULL, '2015-10-07 16:01:52.143', NULL, NULL, 1);
INSERT INTO useraudit VALUES (507, 'User.logged.out.successfully', NULL, '2015-10-07 16:01:54.155', NULL, NULL, 1);
INSERT INTO useraudit VALUES (508, 'failed.signed.in', NULL, '2015-10-07 16:07:44.103', NULL, NULL, 1);
INSERT INTO useraudit VALUES (509, 'User.logged.out.successfully', NULL, '2015-10-07 16:07:44.173', NULL, NULL, 1);
INSERT INTO useraudit VALUES (510, 'failed.signed.in', NULL, '2015-10-07 16:08:36.364', NULL, NULL, 1);
INSERT INTO useraudit VALUES (511, 'failed.signed.in', NULL, '2015-10-07 16:09:32.66', NULL, NULL, 1);
INSERT INTO useraudit VALUES (512, 'failed.signed.in', NULL, '2015-10-07 16:13:34.618', NULL, NULL, 1);
INSERT INTO useraudit VALUES (513, 'failed.signed.in', NULL, '2015-10-07 16:14:09.832', NULL, NULL, 1);
INSERT INTO useraudit VALUES (516, 'failed.signed.in', NULL, '2015-10-07 16:30:23.595', NULL, NULL, 1);
INSERT INTO useraudit VALUES (517, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 16:30:23.762', NULL, NULL, 1);
INSERT INTO useraudit VALUES (518, 'failed.signed.in', NULL, '2015-10-07 16:31:52.163', NULL, NULL, 1);
INSERT INTO useraudit VALUES (519, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 16:31:52.234', NULL, NULL, 1);
INSERT INTO useraudit VALUES (520, 'failed.signed.in', NULL, '2015-10-07 16:35:35.511', NULL, NULL, 1);
INSERT INTO useraudit VALUES (521, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 16:35:35.531', NULL, NULL, 1);
INSERT INTO useraudit VALUES (522, 'failed.signed.in', NULL, '2015-10-07 16:37:14.414', NULL, NULL, 1);
INSERT INTO useraudit VALUES (523, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 16:37:14.451', NULL, NULL, 1);
INSERT INTO useraudit VALUES (524, 'failed.signed.in', NULL, '2015-10-07 16:37:23.318', NULL, NULL, 1);
INSERT INTO useraudit VALUES (525, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 16:37:23.362', NULL, NULL, 1);
INSERT INTO useraudit VALUES (526, 'failed.signed.in', NULL, '2015-10-07 17:09:27.459', NULL, NULL, 1);
INSERT INTO useraudit VALUES (527, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 17:09:27.486', NULL, NULL, 1);
INSERT INTO useraudit VALUES (528, 'failed.signed.in', NULL, '2015-10-07 17:11:24.239', NULL, NULL, 1);
INSERT INTO useraudit VALUES (529, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 17:11:24.257', NULL, NULL, 1);
INSERT INTO useraudit VALUES (530, 'failed.signed.in', NULL, '2015-10-07 17:15:53.644', NULL, NULL, 1);
INSERT INTO useraudit VALUES (531, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 17:15:53.66', NULL, NULL, 1);
INSERT INTO useraudit VALUES (532, 'failed.signed.in', NULL, '2015-10-07 17:16:04.822', NULL, NULL, 1);
INSERT INTO useraudit VALUES (533, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 17:16:04.842', NULL, NULL, 1);
INSERT INTO useraudit VALUES (534, 'failed.signed.in', NULL, '2015-10-07 17:37:49.9', NULL, NULL, 1);
INSERT INTO useraudit VALUES (535, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 17:37:50.989', NULL, NULL, 1);
INSERT INTO useraudit VALUES (536, 'failed.signed.in', NULL, '2015-10-07 17:38:13.68', NULL, NULL, 1);
INSERT INTO useraudit VALUES (537, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 17:38:13.699', NULL, NULL, 1);
INSERT INTO useraudit VALUES (538, 'failed.signed.in', NULL, '2015-10-07 17:38:23.914', NULL, NULL, 1);
INSERT INTO useraudit VALUES (539, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 17:38:23.927', NULL, NULL, 1);
INSERT INTO useraudit VALUES (540, 'failed.signed.in', NULL, '2015-10-07 18:10:45.155', NULL, NULL, 1);
INSERT INTO useraudit VALUES (541, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 18:10:45.324', NULL, NULL, 1);
INSERT INTO useraudit VALUES (542, 'failed.signed.in', NULL, '2015-10-07 18:11:23.021', NULL, NULL, 1);
INSERT INTO useraudit VALUES (543, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-10-07 18:11:23.039', NULL, NULL, 1);
INSERT INTO useraudit VALUES (602, 'failed.signed.in', NULL, '2015-10-08 17:41:50.919', NULL, NULL, 1);
INSERT INTO useraudit VALUES (608, 'successfully.signed.in', NULL, '2015-10-08 18:39:30.503', NULL, NULL, 1);
INSERT INTO useraudit VALUES (609, 'successfully.signed.in', NULL, '2015-10-08 18:44:36.409', NULL, NULL, 1);
INSERT INTO useraudit VALUES (610, 'User.logged.out.successfully', NULL, '2015-10-08 18:44:36.429', NULL, NULL, 1);
INSERT INTO useraudit VALUES (611, 'failed.signed.in', NULL, '2015-10-08 18:45:27.22', NULL, NULL, 1);
INSERT INTO useraudit VALUES (612, 'User.logged.out.successfully', NULL, '2015-10-08 18:45:27.24', NULL, NULL, 1);
INSERT INTO useraudit VALUES (613, 'successfully.signed.in', NULL, '2015-10-08 18:47:54.513', NULL, NULL, 1);
INSERT INTO useraudit VALUES (1076, 'passwordreset', '5', NULL, NULL, '5', 1);
INSERT INTO useraudit VALUES (1079, 'passwordreset', '5', NULL, NULL, '5', 1);
INSERT INTO useraudit VALUES (1082, 'passwordreset', '5', NULL, NULL, '5', 1);
INSERT INTO useraudit VALUES (1083, 'passwordreset', '5', NULL, NULL, '5', 1);
INSERT INTO useraudit VALUES (845, 'successfully.signed.in', NULL, '2015-11-25 18:54:42.736', NULL, NULL, 2);
INSERT INTO useraudit VALUES (846, 'successfully.signed.in', NULL, '2015-11-25 18:56:46.577', NULL, NULL, 2);
INSERT INTO useraudit VALUES (847, 'User.logged.out.successfully', NULL, '2015-11-25 18:56:52.427', NULL, NULL, 2);
INSERT INTO useraudit VALUES (848, 'successfully.signed.in', NULL, '2015-11-25 19:14:57.406', NULL, NULL, 2);
INSERT INTO useraudit VALUES (849, 'User.logged.out.successfully', NULL, '2015-11-25 19:14:57.878', NULL, NULL, 2);
INSERT INTO useraudit VALUES (850, 'successfully.signed.in', NULL, '2015-11-27 12:25:22.426', NULL, NULL, 2);
INSERT INTO useraudit VALUES (851, 'User.logged.out.successfully', NULL, '2015-11-27 12:25:22.579', NULL, NULL, 2);
INSERT INTO useraudit VALUES (852, 'successfully.signed.in', NULL, '2015-11-27 12:26:15.118', NULL, NULL, 2);
INSERT INTO useraudit VALUES (853, 'User.logged.out.successfully', NULL, '2015-11-27 12:26:24.562', NULL, NULL, 2);
INSERT INTO useraudit VALUES (854, 'successfully.signed.in', NULL, '2015-11-27 12:30:33.444', NULL, NULL, 2);
INSERT INTO useraudit VALUES (855, 'User.logged.out.successfully', NULL, '2015-11-27 12:30:34.167', NULL, NULL, 2);
INSERT INTO useraudit VALUES (856, 'successfully.signed.in', NULL, '2015-11-27 12:32:02.244', NULL, NULL, 2);
INSERT INTO useraudit VALUES (857, 'User.logged.out.successfully', NULL, '2015-11-27 12:32:02.815', NULL, NULL, 2);
INSERT INTO useraudit VALUES (858, 'successfully.signed.in', NULL, '2015-11-27 12:34:55.715', NULL, NULL, 2);
INSERT INTO useraudit VALUES (859, 'User.logged.out.successfully', NULL, '2015-11-27 12:34:55.805', NULL, NULL, 2);
INSERT INTO useraudit VALUES (860, 'successfully.signed.in', NULL, '2015-11-27 12:37:46.861', NULL, NULL, 2);
INSERT INTO useraudit VALUES (861, 'User.logged.out.successfully', NULL, '2015-11-27 12:37:46.921', NULL, NULL, 2);
INSERT INTO useraudit VALUES (862, 'successfully.signed.in', NULL, '2015-11-27 12:39:55.855', NULL, NULL, 2);
INSERT INTO useraudit VALUES (863, 'User.logged.out.successfully', NULL, '2015-11-27 12:39:55.924', NULL, NULL, 2);
INSERT INTO useraudit VALUES (864, 'successfully.signed.in', NULL, '2015-11-27 12:47:20.227', NULL, NULL, 2);
INSERT INTO useraudit VALUES (865, 'User.logged.out.successfully', NULL, '2015-11-27 12:47:20.322', NULL, NULL, 2);
INSERT INTO useraudit VALUES (866, 'successfully.signed.in', NULL, '2015-11-27 12:48:10.414', NULL, NULL, 2);
INSERT INTO useraudit VALUES (867, 'User.logged.out.successfully', NULL, '2015-11-27 12:48:10.463', NULL, NULL, 2);
INSERT INTO useraudit VALUES (868, 'successfully.signed.in', NULL, '2015-11-27 12:50:25.665', NULL, NULL, 2);
INSERT INTO useraudit VALUES (869, 'User.logged.out.successfully', NULL, '2015-11-27 12:50:25.69', NULL, NULL, 2);
INSERT INTO useraudit VALUES (870, 'successfully.signed.in', NULL, '2015-11-27 13:00:02.876', NULL, NULL, 2);
INSERT INTO useraudit VALUES (871, 'User.logged.out.successfully', NULL, '2015-11-27 13:00:03.594', NULL, NULL, 2);
INSERT INTO useraudit VALUES (872, 'successfully.signed.in', NULL, '2015-11-27 13:03:54.454', NULL, NULL, 2);
INSERT INTO useraudit VALUES (873, 'User.logged.out.successfully', NULL, '2015-11-27 13:03:55.746', NULL, NULL, 2);
INSERT INTO useraudit VALUES (874, 'successfully.signed.in', NULL, '2015-11-27 13:06:33.32', NULL, NULL, 2);
INSERT INTO useraudit VALUES (875, 'User.logged.out.successfully', NULL, '2015-11-27 13:06:33.374', NULL, NULL, 2);
INSERT INTO useraudit VALUES (876, 'successfully.signed.in', NULL, '2015-11-27 13:14:37.478', NULL, NULL, 2);
INSERT INTO useraudit VALUES (877, 'User.logged.out.successfully', NULL, '2015-11-27 13:15:09.195', NULL, NULL, 2);
INSERT INTO useraudit VALUES (878, 'successfully.signed.in', NULL, '2015-11-27 13:29:37.656', NULL, NULL, 2);
INSERT INTO useraudit VALUES (879, 'User.logged.out.successfully', NULL, '2015-11-27 13:29:37.689', NULL, NULL, 2);
INSERT INTO useraudit VALUES (880, 'successfully.signed.in', NULL, '2015-11-27 13:30:38.328', NULL, NULL, 2);
INSERT INTO useraudit VALUES (881, 'User.logged.out.successfully', NULL, '2015-11-27 13:30:38.392', NULL, NULL, 2);
INSERT INTO useraudit VALUES (882, 'successfully.signed.in', NULL, '2015-11-27 13:33:02.004', NULL, NULL, 2);
INSERT INTO useraudit VALUES (883, 'User.logged.out.successfully', NULL, '2015-11-27 13:33:02.065', NULL, NULL, 2);
INSERT INTO useraudit VALUES (884, 'successfully.signed.in', NULL, '2015-11-27 13:34:48.15', NULL, NULL, 2);
INSERT INTO useraudit VALUES (885, 'User.logged.out.successfully', NULL, '2015-11-27 13:34:52.209', NULL, NULL, 2);
INSERT INTO useraudit VALUES (886, 'successfully.signed.in', NULL, '2015-11-27 13:38:42.015', NULL, NULL, 2);
INSERT INTO useraudit VALUES (887, 'User.logged.out.successfully', NULL, '2015-11-27 13:38:44.844', NULL, NULL, 2);
INSERT INTO useraudit VALUES (888, 'successfully.signed.in', NULL, '2015-11-27 13:40:39.333', NULL, NULL, 2);
INSERT INTO useraudit VALUES (889, 'User.logged.out.successfully', NULL, '2015-11-27 13:40:39.542', NULL, NULL, 2);
INSERT INTO useraudit VALUES (890, 'successfully.signed.in', NULL, '2015-11-27 13:44:28.768', NULL, NULL, 2);
INSERT INTO useraudit VALUES (891, 'User.logged.out.successfully', NULL, '2015-11-27 13:44:28.824', NULL, NULL, 2);
INSERT INTO useraudit VALUES (892, 'successfully.signed.in', NULL, '2015-11-27 13:47:31.086', NULL, NULL, 2);
INSERT INTO useraudit VALUES (893, 'User.logged.out.successfully', NULL, '2015-11-27 13:47:31.113', NULL, NULL, 2);
INSERT INTO useraudit VALUES (894, 'successfully.signed.in', NULL, '2015-11-27 14:49:17.801', NULL, NULL, 2);
INSERT INTO useraudit VALUES (895, 'User.logged.out.successfully', NULL, '2015-11-27 14:49:17.83', NULL, NULL, 2);
INSERT INTO useraudit VALUES (896, 'successfully.signed.in', NULL, '2015-11-27 14:50:19.706', NULL, NULL, 2);
INSERT INTO useraudit VALUES (897, 'User.logged.out.successfully', NULL, '2015-11-27 14:50:19.745', NULL, NULL, 2);
INSERT INTO useraudit VALUES (898, 'successfully.signed.in', NULL, '2015-11-27 14:55:16.494', NULL, NULL, 2);
INSERT INTO useraudit VALUES (899, 'User.logged.out.successfully', NULL, '2015-11-27 14:55:16.545', NULL, NULL, 2);
INSERT INTO useraudit VALUES (900, 'successfully.signed.in', NULL, '2015-11-27 15:03:25.829', NULL, NULL, 2);
INSERT INTO useraudit VALUES (901, 'User.logged.out.successfully', NULL, '2015-11-27 15:03:25.867', NULL, NULL, 2);
INSERT INTO useraudit VALUES (902, 'successfully.signed.in', NULL, '2015-11-27 15:08:29.778', NULL, NULL, 2);
INSERT INTO useraudit VALUES (903, 'User.logged.out.successfully', NULL, '2015-11-27 15:08:29.811', NULL, NULL, 2);
INSERT INTO useraudit VALUES (904, 'successfully.signed.in', NULL, '2015-11-27 15:16:06.829', NULL, NULL, 2);
INSERT INTO useraudit VALUES (905, 'User.logged.out.successfully', NULL, '2015-11-27 15:16:06.888', NULL, NULL, 2);
INSERT INTO useraudit VALUES (906, 'successfully.signed.in', NULL, '2015-11-27 15:17:46.968', NULL, NULL, 2);
INSERT INTO useraudit VALUES (907, 'User.logged.out.successfully', NULL, '2015-11-27 15:17:46.999', NULL, NULL, 2);
INSERT INTO useraudit VALUES (908, 'successfully.signed.in', NULL, '2015-11-27 15:35:31.175', NULL, NULL, 2);
INSERT INTO useraudit VALUES (909, 'User.logged.out.successfully', NULL, '2015-11-27 15:35:31.203', NULL, NULL, 2);
INSERT INTO useraudit VALUES (910, 'successfully.signed.in', NULL, '2015-11-27 15:38:29.801', NULL, NULL, 2);
INSERT INTO useraudit VALUES (911, 'User.logged.out.successfully', NULL, '2015-11-27 15:38:29.835', NULL, NULL, 2);
INSERT INTO useraudit VALUES (1077, 'passwordreset', '5', NULL, NULL, '5', 1);
INSERT INTO useraudit VALUES (1080, 'passwordreset', '5', NULL, NULL, '5', 1);
INSERT INTO useraudit VALUES (914, 'successfully.signed.in', NULL, '2015-11-27 15:41:12.697', NULL, NULL, 2);
INSERT INTO useraudit VALUES (915, 'User.logged.out.successfully', NULL, '2015-11-27 15:41:12.729', NULL, NULL, 2);
INSERT INTO useraudit VALUES (916, 'successfully.signed.in', NULL, '2015-11-27 15:42:09.225', NULL, NULL, 2);
INSERT INTO useraudit VALUES (917, 'User.logged.out.successfully', NULL, '2015-11-27 15:42:09.241', NULL, NULL, 2);
INSERT INTO useraudit VALUES (918, 'successfully.signed.in', NULL, '2015-11-27 15:42:37.046', NULL, NULL, 2);
INSERT INTO useraudit VALUES (919, 'User.logged.out.successfully', NULL, '2015-11-27 15:42:37.077', NULL, NULL, 2);
INSERT INTO useraudit VALUES (920, 'successfully.signed.in', NULL, '2015-11-27 15:43:08.845', NULL, NULL, 2);
INSERT INTO useraudit VALUES (921, 'User.logged.out.successfully', NULL, '2015-11-27 15:43:08.891', NULL, NULL, 2);
INSERT INTO useraudit VALUES (922, 'successfully.signed.in', NULL, '2015-11-27 15:43:25.758', NULL, NULL, 2);
INSERT INTO useraudit VALUES (923, 'User.logged.out.successfully', NULL, '2015-11-27 15:43:25.789', NULL, NULL, 2);
INSERT INTO useraudit VALUES (924, 'successfully.signed.in', NULL, '2015-11-27 15:47:19.534', NULL, NULL, 2);
INSERT INTO useraudit VALUES (925, 'User.logged.out.successfully', NULL, '2015-11-27 15:47:19.565', NULL, NULL, 2);
INSERT INTO useraudit VALUES (926, 'successfully.signed.in', NULL, '2015-11-27 15:49:02.619', NULL, NULL, 2);
INSERT INTO useraudit VALUES (927, 'User.logged.out.successfully', NULL, '2015-11-27 15:49:02.65', NULL, NULL, 2);
INSERT INTO useraudit VALUES (928, 'successfully.signed.in', NULL, '2015-11-27 15:51:53.571', NULL, NULL, 2);
INSERT INTO useraudit VALUES (929, 'User.logged.out.successfully', NULL, '2015-11-27 15:51:53.602', NULL, NULL, 2);
INSERT INTO useraudit VALUES (930, 'successfully.signed.in', NULL, '2015-11-27 15:52:46.448', NULL, NULL, 2);
INSERT INTO useraudit VALUES (931, 'User.logged.out.successfully', NULL, '2015-11-27 15:52:46.495', NULL, NULL, 2);
INSERT INTO useraudit VALUES (932, 'successfully.signed.in', NULL, '2015-11-27 15:58:08.273', NULL, NULL, 2);
INSERT INTO useraudit VALUES (933, 'User.logged.out.successfully', NULL, '2015-11-27 15:58:18.618', NULL, NULL, 2);
INSERT INTO useraudit VALUES (934, 'successfully.signed.in', NULL, '2015-11-27 16:02:36.398', NULL, NULL, 2);
INSERT INTO useraudit VALUES (935, 'User.logged.out.successfully', NULL, '2015-11-27 16:02:38.942', NULL, NULL, 2);
INSERT INTO useraudit VALUES (952, 'failed.signed.in', NULL, '2015-11-27 16:33:16.718', NULL, NULL, 2);
INSERT INTO useraudit VALUES (953, 'User.logged.out.successfully', NULL, '2015-11-27 16:33:17.482', NULL, NULL, 2);
INSERT INTO useraudit VALUES (954, 'failed.signed.in', NULL, '2015-11-27 16:41:23.924', NULL, NULL, 2);
INSERT INTO useraudit VALUES (955, 'failed.signed.in', NULL, '2015-11-27 16:41:38.552', NULL, NULL, 2);
INSERT INTO useraudit VALUES (956, 'failed.signed.in', NULL, '2015-11-27 16:41:54.814', NULL, NULL, 2);
INSERT INTO useraudit VALUES (957, 'failed.signed.in', NULL, '2015-11-27 16:42:05.22', NULL, NULL, 2);
INSERT INTO useraudit VALUES (958, 'failed.signed.in', NULL, '2015-11-27 16:42:25.133', NULL, NULL, 2);
INSERT INTO useraudit VALUES (959, 'failed.signed.in', NULL, '2015-11-27 16:42:36.652', NULL, NULL, 2);
INSERT INTO useraudit VALUES (960, 'failed.signed.in', NULL, '2015-11-27 16:43:24.688', NULL, NULL, 2);
INSERT INTO useraudit VALUES (961, 'failed.signed.in', NULL, '2015-11-27 16:45:13.198', NULL, NULL, 2);
INSERT INTO useraudit VALUES (962, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-11-27 16:45:50.264', NULL, NULL, 2);
INSERT INTO useraudit VALUES (963, 'failed.signed.in', NULL, '2015-11-27 16:46:32.613', NULL, NULL, 2);
INSERT INTO useraudit VALUES (964, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-11-27 16:46:32.754', NULL, NULL, 2);
INSERT INTO useraudit VALUES (965, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-11-27 16:50:51.435', NULL, NULL, 2);
INSERT INTO useraudit VALUES (966, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-11-27 16:51:19.629', NULL, NULL, 2);
INSERT INTO useraudit VALUES (967, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-11-27 16:56:35.63', NULL, NULL, 2);
INSERT INTO useraudit VALUES (968, 'successfully.signed.in', NULL, '2015-11-27 17:06:33.746', NULL, NULL, 2);
INSERT INTO useraudit VALUES (969, 'successfully.signed.in', NULL, '2015-11-27 17:08:10.688', NULL, NULL, 2);
INSERT INTO useraudit VALUES (970, 'User.logged.out.successfully', NULL, '2015-11-27 17:08:13.621', NULL, NULL, 2);
INSERT INTO useraudit VALUES (971, 'successfully.signed.in', NULL, '2015-11-27 17:54:14.337', NULL, NULL, 2);
INSERT INTO useraudit VALUES (972, 'User.logged.out.successfully', NULL, '2015-11-27 17:54:14.37', NULL, NULL, 2);
INSERT INTO useraudit VALUES (973, 'successfully.signed.in', NULL, '2015-11-27 17:54:14.466', NULL, NULL, 2);
INSERT INTO useraudit VALUES (974, 'User.logged.out.successfully', NULL, '2015-11-27 17:54:14.48', NULL, NULL, 2);
INSERT INTO useraudit VALUES (975, 'failed.signed.in', NULL, '2015-11-27 17:54:14.516', NULL, NULL, 2);
INSERT INTO useraudit VALUES (976, 'User.logged.out.successfully', NULL, '2015-11-27 17:54:14.528', NULL, NULL, 2);
INSERT INTO useraudit VALUES (977, 'successfully.signed.in', NULL, '2015-11-27 17:54:14.571', NULL, NULL, 2);
INSERT INTO useraudit VALUES (978, 'failed.signed.in', NULL, '2015-11-27 18:22:33.428', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (979, 'User.logged.out.successfully', NULL, '2015-11-27 18:22:33.456', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (980, 'failed.signed.in', NULL, '2015-11-27 18:22:33.497', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (981, 'failed.signed.in', NULL, '2015-11-27 18:22:33.53', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (982, 'failed.signed.in', NULL, '2015-11-27 18:22:33.566', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (983, 'failed.signed.in', NULL, '2015-11-27 18:46:53.699', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (984, 'failed.signed.in', NULL, '2015-11-27 18:46:53.761', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (985, 'failed.signed.in', NULL, '2015-11-27 18:46:53.792', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (986, 'failed.signed.in', NULL, '2015-11-27 18:46:53.87', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (987, 'User.logged.out.successfully', NULL, '2015-11-27 19:21:05.148', NULL, NULL, 1);
INSERT INTO useraudit VALUES (988, 'successfully.signed.in', NULL, '2015-11-28 11:25:47.745', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1078, 'passwordreset', '5', NULL, NULL, '5', 1);
INSERT INTO useraudit VALUES (991, 'failed.signed.in', NULL, '2015-11-28 11:33:20.193', NULL, NULL, 4);
INSERT INTO useraudit VALUES (992, 'User.logged.out.successfully', NULL, '2015-11-28 11:33:20.233', NULL, NULL, 4);
INSERT INTO useraudit VALUES (993, 'failed.signed.in', NULL, '2015-11-28 11:36:50.707', NULL, NULL, 4);
INSERT INTO useraudit VALUES (994, 'failed.signed.in', NULL, '2015-11-28 11:37:16.122', NULL, NULL, 4);
INSERT INTO useraudit VALUES (995, 'failed.signed.in', NULL, '2015-11-28 11:37:29.709', NULL, NULL, 4);
INSERT INTO useraudit VALUES (996, 'failed.signed.in', NULL, '2015-11-28 11:37:44.748', NULL, NULL, 4);
INSERT INTO useraudit VALUES (997, 'failed.signed.in', NULL, '2015-11-28 11:38:01.161', NULL, NULL, 4);
INSERT INTO useraudit VALUES (998, 'failed.signed.in', NULL, '2015-11-28 11:38:22.45', NULL, NULL, 4);
INSERT INTO useraudit VALUES (999, 'failed.signed.in', NULL, '2015-11-28 11:38:40.28', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1000, 'failed.signed.in', NULL, '2015-11-28 11:38:54.724', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1001, 'failed.signed.in', NULL, '2015-11-28 11:39:10.145', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1002, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-11-28 11:39:10.206', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1003, 'failed.signed.in', NULL, '2015-11-28 11:39:25.408', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1004, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-11-28 11:39:25.474', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1005, 'failed.signed.in', NULL, '2015-11-28 11:45:58.972', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1006, 'User Account is locked due to six or more successive failed attempt for login', NULL, '2015-11-28 11:45:59.034', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1007, 'successfully.signed.in', NULL, '2015-11-28 11:51:34.387', NULL, NULL, 5);
INSERT INTO useraudit VALUES (1008, 'successfully.signed.in', NULL, '2015-11-28 12:26:48.319', NULL, NULL, 3);
INSERT INTO useraudit VALUES (1009, 'successfully.signed.in', NULL, '2015-11-28 12:29:23.668', NULL, NULL, 3);
INSERT INTO useraudit VALUES (1010, 'User.logged.out.successfully', NULL, '2015-11-28 12:29:23.72', NULL, NULL, 3);
INSERT INTO useraudit VALUES (1011, 'successfully.signed.in', NULL, '2015-11-29 21:48:02.192', NULL, NULL, 3);
INSERT INTO useraudit VALUES (1012, 'User.logged.out.successfully', NULL, '2015-11-29 21:48:02.202', NULL, NULL, 3);
INSERT INTO useraudit VALUES (1013, 'successfully.signed.in', NULL, '2015-11-29 21:48:02.272', NULL, NULL, 5);
INSERT INTO useraudit VALUES (1014, 'User.logged.out.successfully', NULL, '2015-11-29 21:48:02.302', NULL, NULL, 5);
INSERT INTO useraudit VALUES (1015, 'failed.signed.in', NULL, '2015-11-29 21:48:02.322', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1016, 'failed.signed.in', NULL, '2015-11-29 21:48:02.342', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1017, 'successfully.signed.in', NULL, '2015-11-30 12:28:15.358', NULL, NULL, 3);
INSERT INTO useraudit VALUES (1018, 'User.logged.out.successfully', NULL, '2015-11-30 12:28:15.375', NULL, NULL, 3);
INSERT INTO useraudit VALUES (1019, 'successfully.signed.in', NULL, '2015-11-30 12:28:15.477', NULL, NULL, 5);
INSERT INTO useraudit VALUES (1020, 'User.logged.out.successfully', NULL, '2015-11-30 12:28:15.49', NULL, NULL, 5);
INSERT INTO useraudit VALUES (1021, 'failed.signed.in', NULL, '2015-11-30 12:28:15.524', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1022, 'failed.signed.in', NULL, '2015-11-30 12:28:15.555', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1023, 'successfully.signed.in', NULL, '2015-11-30 12:44:29.54', NULL, NULL, 3);
INSERT INTO useraudit VALUES (1024, 'User.logged.out.successfully', NULL, '2015-11-30 12:44:29.556', NULL, NULL, 3);
INSERT INTO useraudit VALUES (1025, 'successfully.signed.in', NULL, '2015-11-30 12:44:29.602', NULL, NULL, 5);
INSERT INTO useraudit VALUES (1026, 'User.logged.out.successfully', NULL, '2015-11-30 12:44:29.614', NULL, NULL, 5);
INSERT INTO useraudit VALUES (1027, 'failed.signed.in', NULL, '2015-11-30 12:44:29.646', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1028, 'failed.signed.in', NULL, '2015-11-30 12:44:29.672', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1029, 'successfully.signed.in', NULL, '2015-11-30 13:01:11.433', NULL, NULL, 8);
INSERT INTO useraudit VALUES (1030, 'successfully.signed.in', NULL, '2015-11-30 13:01:52.996', NULL, NULL, 8);
INSERT INTO useraudit VALUES (1031, 'User.logged.out.successfully', NULL, '2015-11-30 13:01:53.033', NULL, NULL, 8);
INSERT INTO useraudit VALUES (1081, 'passwordreset', '5', NULL, NULL, '5', 1);
INSERT INTO useraudit VALUES (1052, 'failed.signed.in', NULL, '2015-11-30 16:04:03.106', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1053, 'failed.signed.in', NULL, '2015-11-30 16:04:03.138', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1054, 'failed.signed.in', NULL, '2015-11-30 16:04:03.164', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1055, 'failed.signed.in', NULL, '2015-11-30 16:04:03.193', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1056, 'failed.signed.in', NULL, '2015-11-30 16:16:03.07', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1057, 'failed.signed.in', NULL, '2015-11-30 16:16:03.102', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1058, 'failed.signed.in', NULL, '2015-11-30 16:16:03.125', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1059, 'failed.signed.in', NULL, '2015-11-30 16:16:03.153', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1070, 'passwordreset', '5', NULL, NULL, '5', 1);
INSERT INTO useraudit VALUES (1155, 'successfully.signed.in', NULL, '2015-12-09 16:02:09.267', NULL, NULL, 4);
INSERT INTO useraudit VALUES (1156, 'User.logged.out.successfully', NULL, '2015-12-09 16:49:41.1', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1159, 'failed.signed.in', NULL, '2015-12-11 11:23:59.694', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1160, 'successfully.signed.in', NULL, '2015-12-11 11:28:02.246', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1161, 'User.logged.out.successfully', NULL, '2015-12-11 11:28:46.494', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1162, 'successfully.signed.in', NULL, '2015-12-11 11:28:59.411', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1163, 'User.logged.out.successfully', NULL, '2015-12-11 11:29:13.248', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1164, 'successfully.signed.in', NULL, '2015-12-11 11:29:19.6', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1165, 'failed.signed.in', NULL, '2015-12-11 11:34:10.952', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1166, 'successfully.signed.in', NULL, '2015-12-11 11:34:31.644', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1167, 'User.logged.out.successfully', NULL, '2015-12-11 11:34:31.666', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1168, 'failed.signed.in', NULL, '2015-12-11 12:03:24.959', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1169, 'User.logged.out.successfully', NULL, '2015-12-11 12:03:25.36', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1170, 'successfully.signed.in', NULL, '2015-12-11 12:03:33.82', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1171, 'successfully.signed.in', NULL, '2015-12-11 12:23:44.734', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1172, 'User.logged.out.successfully', NULL, '2015-12-11 12:23:44.837', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1173, 'successfully.signed.in', NULL, '2015-12-11 12:25:45.746', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1174, 'User.logged.out.successfully', NULL, '2015-12-11 12:25:45.804', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1175, 'failed.signed.in', NULL, '2015-12-11 12:26:26.03', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1176, 'failed.signed.in', NULL, '2015-12-11 12:26:36.206', NULL, NULL, NULL);
INSERT INTO useraudit VALUES (1177, 'successfully.signed.in', NULL, '2015-12-11 12:26:44.668', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1178, 'User.logged.out.successfully', NULL, '2015-12-11 12:26:44.695', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1179, 'successfully.signed.in', NULL, '2015-12-11 12:33:50.557', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1180, 'User.logged.out.successfully', NULL, '2015-12-11 12:33:50.616', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1181, 'failed.signed.in', NULL, '2015-12-11 12:35:26.706', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1182, 'User.logged.out.successfully', NULL, '2015-12-11 12:35:26.836', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1183, 'successfully.signed.in', NULL, '2015-12-11 12:35:34.973', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1184, 'successfully.signed.in', NULL, '2015-12-11 12:42:44.524', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1185, 'User.logged.out.successfully', NULL, '2015-12-11 12:42:44.583', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1186, 'failed.signed.in', NULL, '2015-12-11 12:44:22.131', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1187, 'User.logged.out.successfully', NULL, '2015-12-11 12:44:22.192', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1188, 'successfully.signed.in', NULL, '2015-12-11 12:44:35.465', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1189, 'successfully.signed.in', NULL, '2015-12-11 12:56:32.881', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1190, 'User.logged.out.successfully', NULL, '2015-12-11 12:56:33.023', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1191, 'failed.signed.in', NULL, '2015-12-11 13:07:37.476', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1192, 'User.logged.out.successfully', NULL, '2015-12-11 13:07:37.54', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1193, 'successfully.signed.in', NULL, '2015-12-11 13:07:45.282', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1194, 'successfully.signed.in', NULL, '2015-12-11 13:15:06.749', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1195, 'User.logged.out.successfully', NULL, '2015-12-11 13:15:06.812', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1196, 'successfully.signed.in', NULL, '2015-12-11 13:35:55.555', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1197, 'User.logged.out.successfully', NULL, '2015-12-11 13:35:55.661', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1198, 'successfully.signed.in', NULL, '2015-12-11 14:12:10.296', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1199, 'User.logged.out.successfully', NULL, '2015-12-11 14:12:10.54', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1200, 'successfully.signed.in', NULL, '2015-12-11 14:18:28.552', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1201, 'User.logged.out.successfully', NULL, '2015-12-11 14:18:28.691', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1202, 'successfully.signed.in', NULL, '2015-12-11 14:21:40.535', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1203, 'User.logged.out.successfully', NULL, '2015-12-11 14:21:40.617', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1204, 'successfully.signed.in', NULL, '2015-12-11 14:26:07.652', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1205, 'User.logged.out.successfully', NULL, '2015-12-11 14:26:07.713', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1206, 'successfully.signed.in', NULL, '2015-12-11 14:28:46.102', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1207, 'User.logged.out.successfully', NULL, '2015-12-11 14:28:46.175', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1208, 'successfully.signed.in', NULL, '2015-12-11 14:30:32.962', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1209, 'User.logged.out.successfully', NULL, '2015-12-11 14:30:33.075', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1210, 'successfully.signed.in', NULL, '2015-12-11 14:36:02.041', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1211, 'User.logged.out.successfully', NULL, '2015-12-11 14:36:02.101', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1212, 'successfully.signed.in', NULL, '2015-12-11 14:40:55.134', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1213, 'User.logged.out.successfully', NULL, '2015-12-11 14:40:55.207', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1214, 'successfully.signed.in', NULL, '2015-12-11 14:53:57.028', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1215, 'User.logged.out.successfully', NULL, '2015-12-11 14:53:57.086', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1216, 'successfully.signed.in', NULL, '2015-12-11 14:56:34.837', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1217, 'User.logged.out.successfully', NULL, '2015-12-11 14:56:34.91', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1218, 'successfully.signed.in', NULL, '2015-12-11 14:57:43.645', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1219, 'User.logged.out.successfully', NULL, '2015-12-11 14:57:43.702', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1220, 'successfully.signed.in', NULL, '2015-12-11 14:59:26.972', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1221, 'User.logged.out.successfully', NULL, '2015-12-11 14:59:27.023', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1222, 'successfully.signed.in', NULL, '2015-12-11 15:00:52.14', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1223, 'User.logged.out.successfully', NULL, '2015-12-11 15:00:52.196', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1224, 'successfully.signed.in', NULL, '2015-12-11 15:01:57.628', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1225, 'User.logged.out.successfully', NULL, '2015-12-11 15:01:57.741', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1226, 'failed.signed.in', NULL, '2015-12-11 15:04:43.923', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1227, 'User.logged.out.successfully', NULL, '2015-12-11 15:04:43.987', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1228, 'successfully.signed.in', NULL, '2015-12-11 15:04:51.702', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1229, 'successfully.signed.in', NULL, '2015-12-11 15:14:46.4', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1230, 'User.logged.out.successfully', NULL, '2015-12-11 15:14:46.459', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1231, 'successfully.signed.in', NULL, '2015-12-11 15:35:03.683', NULL, NULL, 10);
INSERT INTO useraudit VALUES (1232, 'User.logged.out.successfully', NULL, '2015-12-11 15:35:03.758', NULL, NULL, 10);


--
-- Name: useraudit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('useraudit_id_seq', 1232, true);


--
-- Data for Name: usermangementhistory; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usermangementhistory VALUES (8, 'User updated', NULL, NULL, '2015-12-03 17:37:09.706', 1, 2);
INSERT INTO usermangementhistory VALUES (9, 'passwordreset', 5, NULL, NULL, 5, 1);
INSERT INTO usermangementhistory VALUES (10, 'passwordreset', 4, NULL, NULL, 4, 1);
INSERT INTO usermangementhistory VALUES (11, 'passwordreset', 4, NULL, NULL, 4, 1);
INSERT INTO usermangementhistory VALUES (12, 'passwordreset', 4, NULL, NULL, 4, 1);
INSERT INTO usermangementhistory VALUES (13, 'passwordreset', 4, NULL, NULL, 4, 1);


--
-- Name: usermangementhistory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usermangementhistory_id_seq', 25, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users VALUES (7, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken7@rsystems.com', 'f3c3ae5e33123777336e4d0f3727bcf873198bb22e61398a2b4e891abc82e2b2', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-08-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 1, 1, NULL, '012-345-6788', 'HQ7', NULL, 'GGGGGGG', NULL, false, true, NULL);
INSERT INTO users VALUES (9, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken9@rsystems.com', 'f3c3ae5e33123777336e4d0f3727bcf873198bb22e61398a2b4e891abc82e2b2', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-08-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 1, 1, NULL, '012-345-6788', 'HQ9', NULL, 'IIIIIII', NULL, false, true, NULL);
INSERT INTO users VALUES (11, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken11@rsystems.com', 'f3c3ae5e33123777336e4d0f3727bcf873198bb22e61398a2b4e891abc82e2b2', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-08-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 1, 1, NULL, '012-345-6788', 'HQ11', NULL, 'KKKKKKK', NULL, false, true, NULL);
INSERT INTO users VALUES (13, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken13@rsystems.com', 'f3c3ae5e33123777336e4d0f3727bcf873198bb22e61398a2b4e891abc82e2b2', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-08-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 5, 1, NULL, '012-345-6788', 'HQ13', '[{"entityId":1,"entityType":null,"selected":true,"expanded":false},{"entityId":2,"entityType":null,"selected":true,"expanded":false},{"entityId":3,"entityType":null,"selected":true,"expanded":false}]', 'MMMMMMM', NULL, false, true, NULL);
INSERT INTO users VALUES (14, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken14@rsystems.com', 'f3c3ae5e33123777336e4d0f3727bcf873198bb22e61398a2b4e891abc82e2b2', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-08-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 1, 1, NULL, '012-345-6788', 'HQ14', '[{"entityId":1,"entityType":null,"selected":true,"expanded":false},{"entityId":2,"entityType":null,"selected":true,"expanded":false},{"entityId":3,"entityType":null,"selected":true,"expanded":false}]', 'NNNNNNN', NULL, false, true, NULL);
INSERT INTO users VALUES (12, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken12@rsystems.com', 'f3c3ae5e33123777336e4d0f3727bcf873198bb22e61398a2b4e891abc82e2b2', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-08-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 1, 1, NULL, '012-345-6788', 'HQ12', '[{"entityId":1,"entityType":null,"selected":false,"expanded":false},{"entityId":2,"entityType":null,"selected":false,"expanded":false},{"entityId":3,"entityType":null,"selected":false,"expanded":false}]', 'LLLLLLL', NULL, false, true, NULL);
INSERT INTO users VALUES (8, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken8@rsystems.com', NULL, 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-08-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 1, 1, NULL, '012-345-6788', 'HQ8', NULL, 'HHHHHHH', '2015-11-30 13:01:53', false, true, NULL);
INSERT INTO users VALUES (5, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', '', 'f3c3ae5e33123777336e4d0f3727bcf873198bb22e61398a2b4e891abc82e2b2', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-11-28 12:08:15', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 1, 1, NULL, '123445', 'HQ', NULL, 'EEEEEEE', NULL, false, true, NULL);
INSERT INTO users VALUES (2, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken@rsystems.com', 'd4a642fae8fb326923ddf079dfa10bae1906b01dedfc63bc7300c1256576c5ca', 0, 'William', NULL, 0, '2015-08-13 05:59:05', 'Wong', NULL, '2015-11-27 18:22:33', NULL, NULL, NULL, NULL, NULL, '2015-12-03 17:37:09.706', '1', 1, 1, NULL, '123445', 'HQ1', NULL, 'ZZZZZZZ', '2015-11-27 17:54:14', false, true, NULL);
INSERT INTO users VALUES (1, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'nitin.verma1@rsystems.com', '1bcf8ea41ba0e73daf3fea97b4f466dcd64e6e20b35e0ea02c906d581819265a', 0, 'Nitin', NULL, 1, '2015-08-13 05:59:05', 'Verma', '2015-09-13 05:59:05', '2015-09-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '4', 1, 1, NULL, '011-111-6333', 'HQ', NULL, 'AAAAAAA', NULL, false, true, NULL);
INSERT INTO users VALUES (6, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken6@rsystems.com', 'f3c3ae5e33123777336e4d0f3727bcf873198bb22e61398a2b4e891abc82e2b2', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-08-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 1, 1, NULL, '012-345-6788', 'HQ6', NULL, 'FFFFFFF', NULL, false, true, NULL);
INSERT INTO users VALUES (3, '3874af08-e96f-4a76-ab75-c82cb7217d18', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'nitin.verma1@rsystems.com', '435dd4dfcc7822dd489b63f63fd23357061ba575d593ca8e4ca6f5a80d495021', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-12-09 16:49:40', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 1, 1, NULL, '9990967637', 'HQ', NULL, 'NitinVerma', '2015-12-09 16:49:40', false, true, NULL);
INSERT INTO users VALUES (4, '505aff0c-6052-40ef-bf56-9dba6b66798e', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', 'testtoken3@rsystems.com', 'f3c3ae5e33123777336e4d0f3727bcf873198bb22e61398a2b4e891abc82e2b2', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-08-13 05:59:05', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 5, 1, NULL, '012-345-6788', 'HQ', NULL, 'DDDDDDD', '2015-12-09 16:02:09', false, true, NULL);
INSERT INTO users VALUES (10, '466a23cb-32f2-42c1-9e9f-b3b80814175f', '2015-08-13 05:59:05', '2015-08-13 05:59:05', 'Singapore', '1', '2015-08-13 11:29:05', NULL, '4d3b8e367916edb39a7267ae0f75cdce07968adf25b5c7594b64dff4bd44f2ec', 0, 'William', NULL, 1, '2015-08-13 05:59:05', 'Wong', NULL, '2015-12-11 08:58:46', NULL, NULL, NULL, NULL, NULL, '2015-08-13 11:29:05', '1', 5, 1, NULL, NULL, 'HQ10', NULL, 'srini', '2015-12-11 15:35:03', false, true, NULL);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 35, true);


--
-- Data for Name: vrfparameter_statistics; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO vrfparameter_statistics VALUES (1, 3535.00, 45646.00, 36346.00, 'RSI', '2015-08-07 10:13:06', 236, 235, 46, 235, 25, 46, 346, 21, 124, 23, 312, 56, 68, 57, 14, 346, 87, 6, 25, 25, 25, 56, 25, 2, 64, 235, 756, 123, 3463, 235, 234, NULL, 'RSI', 15, NULL);
INSERT INTO vrfparameter_statistics VALUES (2, 47.00, 47.00, 47.00, 'RSI', '2015-08-07 10:13:50', 236, 25, 236, 35, 5634, 678, 67, 346, 23, 236, 26, 346, 457, 236, 23, 36, 34, 26, 46, 3, 234, 457, 26, 346, 46, 8678, 235, 235, 23, 8, 235, NULL, 'RSI', 16, NULL);
INSERT INTO vrfparameter_statistics VALUES (3, 568.00, 8568.00, 346.00, 'RSI', '2015-08-07 10:14:12', 37, 37, 37, 37, 36, 37, 37, 37, 37, 37, 7, 347, 346, 37, 37, 37, 37, 37, 36, 79, 12, 458, 37, 37, 37, 37, 37, 37, 73, 37, 37, NULL, 'RSI', 17, NULL);
INSERT INTO vrfparameter_statistics VALUES (4, 346.00, 346.00, 36.00, 'RSI', '2015-08-07 10:16:02', 22, 876, 87, 252, 25, 36, 36, 7, 8, 75, 875, 36, 36, 785, 8, 667, 3, 27, 54, 45, 765, 346, 2, 721, 52, 82, 757, 75, 87, 41, 78, NULL, 'RSI', 18, NULL);


--
-- Name: vrfparameter_statistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('vrfparameter_statistics_id_seq', 1, false);


--
-- Data for Name: winddirection_master; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO winddirection_master VALUES (1, 'Swing');
INSERT INTO winddirection_master VALUES (2, 'F1');
INSERT INTO winddirection_master VALUES (3, 'F2');
INSERT INTO winddirection_master VALUES (4, 'F3');
INSERT INTO winddirection_master VALUES (5, 'F4');
INSERT INTO winddirection_master VALUES (6, 'F5');
INSERT INTO winddirection_master VALUES (7, 'Unset');


--
-- Name: winddirection_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('winddirection_master_id_seq', 1, false);


--
-- Name: adapters_model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY adapters_model
    ADD CONSTRAINT adapters_model_pkey PRIMARY KEY (id);


--
-- Name: adapters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY adapters
    ADD CONSTRAINT adapters_pkey PRIMARY KEY (id);


--
-- Name: alarmstatistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notificationlog
    ADD CONSTRAINT alarmstatistics_pkey PRIMARY KEY (id);


--
-- Name: availabletemp_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY availabletemp
    ADD CONSTRAINT availabletemp_pkey PRIMARY KEY (id);


--
-- Name: companies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY companies
    ADD CONSTRAINT companies_pkey PRIMARY KEY (id);


--
-- Name: companiesusers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY companiesusers
    ADD CONSTRAINT companiesusers_pkey PRIMARY KEY (id);


--
-- Name: deviceattribute_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rcoperation_master
    ADD CONSTRAINT deviceattribute_master_pkey PRIMARY KEY (id);


--
-- Name: devicecommand_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rcuser_action
    ADD CONSTRAINT devicecommand_pkey PRIMARY KEY (id);


--
-- Name: dist_id_groups_uniqueid_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT dist_id_groups_uniqueid_key UNIQUE (uniqueid);


--
-- Name: distribution_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY distribution_group
    ADD CONSTRAINT distribution_group_pkey PRIMARY KEY (id);


--
-- Name: efficiencyrating_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY efficiency_rating
    ADD CONSTRAINT efficiencyrating_pk PRIMARY KEY (id);


--
-- Name: fanspeed_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fanspeed_master
    ADD CONSTRAINT fanspeed_master_pkey PRIMARY KEY (id);


--
-- Name: gasheatmeter_data_daily_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data_daily
    ADD CONSTRAINT gasheatmeter_data_daily_pkey PRIMARY KEY (id);


--
-- Name: gasheatmeter_data_monthly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data_monthly
    ADD CONSTRAINT gasheatmeter_data_monthly_pkey PRIMARY KEY (id);


--
-- Name: gasheatmeter_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data
    ADD CONSTRAINT gasheatmeter_data_pkey PRIMARY KEY (id);


--
-- Name: gasheatmeter_data_weekly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data_weekly
    ADD CONSTRAINT gasheatmeter_data_weekly_pkey PRIMARY KEY (id);


--
-- Name: gasheatmeter_data_yearly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasheatmeter_data_yearly
    ADD CONSTRAINT gasheatmeter_data_yearly_pkey PRIMARY KEY (id);


--
-- Name: ghpparameter_statistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ghpparameter_statistics
    ADD CONSTRAINT ghpparameter_statistics_pkey PRIMARY KEY (id);


--
-- Name: group_level_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY group_level
    ADD CONSTRAINT group_level_pk PRIMARY KEY (id);


--
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- Name: groupscriteria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groupscriteria
    ADD CONSTRAINT groupscriteria_pkey PRIMARY KEY (id);


--
-- Name: idx_indoorunits; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT idx_indoorunits UNIQUE (parent_id);


--
-- Name: indoorunits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT indoorunits_pkey PRIMARY KEY (id);


--
-- Name: indoorunitslog_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT indoorunitslog_history_pkey PRIMARY KEY (id);


--
-- Name: indoorunitslog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT indoorunitslog_pkey PRIMARY KEY (id);


--
-- Name: indoorunitstatistics_daily_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics_daily
    ADD CONSTRAINT indoorunitstatistics_daily_pkey PRIMARY KEY (id);


--
-- Name: indoorunitstatistics_monthly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics_monthly
    ADD CONSTRAINT indoorunitstatistics_monthly_pkey PRIMARY KEY (id);


--
-- Name: indoorunitstatistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics
    ADD CONSTRAINT indoorunitstatistics_pkey PRIMARY KEY (id);


--
-- Name: indoorunitstatistics_weekly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics_weekly
    ADD CONSTRAINT indoorunitstatistics_weekly_pkey PRIMARY KEY (id);


--
-- Name: indoorunitstatistics_yearly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indoorunitstatistics_yearly
    ADD CONSTRAINT indoorunitstatistics_yearly_pkey PRIMARY KEY (id);


--
-- Name: job_tracker_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY job_tracker
    ADD CONSTRAINT job_tracker_pkey PRIMARY KEY (jobname, lastexecutiontime);


--
-- Name: maintenance_setting_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY maintenance_setting
    ADD CONSTRAINT maintenance_setting_pk PRIMARY KEY (id);


--
-- Name: maintenance_type_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY maintenance_type_master
    ADD CONSTRAINT maintenance_type_pk PRIMARY KEY (id);


--
-- Name: maintenance_user_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY maintenance_user_list
    ADD CONSTRAINT maintenance_user_pk PRIMARY KEY (id);


--
-- Name: metaindoorunits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY metaindoorunits
    ADD CONSTRAINT metaindoorunits_pkey PRIMARY KEY (id);


--
-- Name: metaoutdoorunits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY metaoutdoorunits
    ADD CONSTRAINT metaoutdoorunits_pkey PRIMARY KEY (id);


--
-- Name: modemaster_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY modemaster
    ADD CONSTRAINT modemaster_pkey PRIMARY KEY (id);


--
-- Name: myprimarykey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notificationlog_temp
    ADD CONSTRAINT myprimarykey PRIMARY KEY (id);


--
-- Name: outdoorunitparameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitparameters
    ADD CONSTRAINT outdoorunitparameters_pkey PRIMARY KEY (id);


--
-- Name: outdoorunits_parentid_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT outdoorunits_parentid_key UNIQUE (parentid);


--
-- Name: outdoorunits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT outdoorunits_pkey PRIMARY KEY (id);


--
-- Name: outdoorunitstatistics_daily_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitstatistics_daily
    ADD CONSTRAINT outdoorunitstatistics_daily_pkey1 PRIMARY KEY (id);


--
-- Name: outdoorunitstatistics_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitslog_history
    ADD CONSTRAINT outdoorunitstatistics_history_pkey PRIMARY KEY (id);


--
-- Name: outdoorunitstatistics_monthly_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitstatistics_monthly
    ADD CONSTRAINT outdoorunitstatistics_monthly_pkey1 PRIMARY KEY (id);


--
-- Name: outdoorunitstatistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitslog
    ADD CONSTRAINT outdoorunitstatistics_pkey PRIMARY KEY (id);


--
-- Name: outdoorunitstatistics_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitstatistics
    ADD CONSTRAINT outdoorunitstatistics_pkey1 PRIMARY KEY (id);


--
-- Name: outdoorunitstatistics_weekly_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitstatistics_weekly
    ADD CONSTRAINT outdoorunitstatistics_weekly_pkey1 PRIMARY KEY (id);


--
-- Name: outdoorunitstatistics_yearly_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outdoorunitstatistics_yearly
    ADD CONSTRAINT outdoorunitstatistics_yearly_pkey1 PRIMARY KEY (id);


--
-- Name: permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (id);


--
-- Name: pk_companyrole; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY companyrole
    ADD CONSTRAINT pk_companyrole PRIMARY KEY (roleid);


--
-- Name: pk_errorcode_master; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY errorcode_master
    ADD CONSTRAINT pk_errorcode_master PRIMARY KEY (id);


--
-- Name: pk_functionalgroup; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY functionalgroup
    ADD CONSTRAINT pk_functionalgroup PRIMARY KEY (functional_groupid);


--
-- Name: pk_functionalgroup_permission; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY functionalgroup_permission
    ADD CONSTRAINT pk_functionalgroup_permission PRIMARY KEY (id);


--
-- Name: pk_groupcategory; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groupcategory
    ADD CONSTRAINT pk_groupcategory PRIMARY KEY (groupcategoryid);


--
-- Name: pk_maintenance_status_data; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY maintenance_status_data
    ADD CONSTRAINT pk_maintenance_status_data PRIMARY KEY (id);


--
-- Name: pk_notification_type_master; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notificationtype_master
    ADD CONSTRAINT pk_notification_type_master PRIMARY KEY (id);


--
-- Name: pk_notificationsettings; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notificationsettings
    ADD CONSTRAINT pk_notificationsettings PRIMARY KEY (id);


--
-- Name: pk_refrigerantcircuit_statistics; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refrigerantcircuit_statistics
    ADD CONSTRAINT pk_refrigerantcircuit_statistics PRIMARY KEY (id);


--
-- Name: pk_refrigerantcircuit_statistics_daily; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refrigerantcircuit_statistics_daily
    ADD CONSTRAINT pk_refrigerantcircuit_statistics_daily PRIMARY KEY (id);


--
-- Name: pk_refrigerantcircuit_statistics_monthly; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refrigerantcircuit_statistics_monthly
    ADD CONSTRAINT pk_refrigerantcircuit_statistics_monthly PRIMARY KEY (id);


--
-- Name: pk_refrigerantcircuit_statistics_weekly; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refrigerantcircuit_statistics_weekly
    ADD CONSTRAINT pk_refrigerantcircuit_statistics_weekly PRIMARY KEY (id);


--
-- Name: pk_refrigerantcircuit_statistics_yearly; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refrigerantcircuit_statistics_yearly
    ADD CONSTRAINT pk_refrigerantcircuit_statistics_yearly PRIMARY KEY (id);


--
-- Name: pk_refrigerantmaster; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refrigerantmaster
    ADD CONSTRAINT pk_refrigerantmaster PRIMARY KEY (refrigerantid);


--
-- Name: pk_rolefunctionalgrp; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rolefunctionalgrp
    ADD CONSTRAINT pk_rolefunctionalgrp PRIMARY KEY (id);


--
-- Name: pk_roletype; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY roletype
    ADD CONSTRAINT pk_roletype PRIMARY KEY (id);


--
-- Name: pk_user_notification_settings; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_notification_settings
    ADD CONSTRAINT pk_user_notification_settings PRIMARY KEY (id);


--
-- Name: power_consumption_capacity_daily_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity_daily
    ADD CONSTRAINT power_consumption_capacity_daily_pkey PRIMARY KEY (id);


--
-- Name: power_consumption_capacity_monthly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity_monthly
    ADD CONSTRAINT power_consumption_capacity_monthly_pkey PRIMARY KEY (id);


--
-- Name: power_consumption_capacity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity
    ADD CONSTRAINT power_consumption_capacity_pkey PRIMARY KEY (id);


--
-- Name: power_consumption_capacity_weekly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity_weekly
    ADD CONSTRAINT power_consumption_capacity_weekly_pkey PRIMARY KEY (id);


--
-- Name: power_consumption_capacity_yearly_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY power_consumption_capacity_yearly
    ADD CONSTRAINT power_consumption_capacity_yearly_pkey PRIMARY KEY (id);


--
-- Name: pulse_meter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pulse_meter
    ADD CONSTRAINT pulse_meter_pkey PRIMARY KEY (id);


--
-- Name: ratingmaster_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ratingmaster
    ADD CONSTRAINT ratingmaster_pkey PRIMARY KEY (id);


--
-- Name: rc_prohibition_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rc_prohibition
    ADD CONSTRAINT rc_prohibition_pkey PRIMARY KEY (id);


--
-- Name: rcoperation_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rcoperation_log
    ADD CONSTRAINT rcoperation_log_pkey PRIMARY KEY (id);


--
-- Name: rolehistory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rolehistory
    ADD CONSTRAINT rolehistory_pkey PRIMARY KEY (id);


--
-- Name: rolepermissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rolepermissions
    ADD CONSTRAINT rolepermissions_pkey PRIMARY KEY (id);


--
-- Name: roles_name; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_name UNIQUE (name);


--
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: session_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY session
    ADD CONSTRAINT session_pkey PRIMARY KEY (id);


--
-- Name: svg_master_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY svg_master
    ADD CONSTRAINT svg_master_pk PRIMARY KEY (id);


--
-- Name: temp_groupunitdata_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY temp_groupunitdata
    ADD CONSTRAINT temp_groupunitdata_pkey PRIMARY KEY (id);


--
-- Name: timeline_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY timeline
    ADD CONSTRAINT timeline_pkey PRIMARY KEY (id);


--
-- Name: timezonemaster_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY timezonemaster
    ADD CONSTRAINT timezonemaster_pkey PRIMARY KEY (id);


--
-- Name: unique_users_loginid; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT unique_users_loginid UNIQUE (loginid);


--
-- Name: useraudit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY useraudit
    ADD CONSTRAINT useraudit_pkey PRIMARY KEY (id);


--
-- Name: usermanagementhistory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usermangementhistory
    ADD CONSTRAINT usermanagementhistory_pkey PRIMARY KEY (id);


--
-- Name: users_loginid; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_loginid UNIQUE (loginid);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: vrfparameter_statistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vrfparameter_statistics
    ADD CONSTRAINT vrfparameter_statistics_pkey PRIMARY KEY (id);


--
-- Name: winddirection_master_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY winddirection_master
    ADD CONSTRAINT winddirection_master_pkey PRIMARY KEY (id);


--
-- Name: fki_group_level_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_group_level_fk ON groups USING btree (group_level_id);


--
-- Name: fki_svg_master_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_svg_master_fk ON indoorunits USING btree (svg_id);


--
-- Name: idx_errorcode_master; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_errorcode_master ON errorcode_master USING btree (code);


--
-- Name: idx_groups; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_groups ON groups USING btree (groupcategoryid);


--
-- Name: idx_indoorunits_0; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_indoorunits_0 ON indoorunits USING btree (group_id);


--
-- Name: idx_user_notification_settings; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_user_notification_settings ON user_notification_settings USING btree (notification_settings_id);


--
-- Name: index_maintenance_setting; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX index_maintenance_setting ON maintenance_setting USING btree (group_id);


--
-- Name: adapters_siteid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY adapters
    ADD CONSTRAINT adapters_siteid_fkey FOREIGN KEY (siteid) REFERENCES groups(uniqueid);


--
-- Name: fk_1lgod7u59j9xc5ucnup1cv3j5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_daily
    ADD CONSTRAINT fk_1lgod7u59j9xc5ucnup1cv3j5 FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_3es4q0x8gw88peunitrgnf0tr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rc_prohibition
    ADD CONSTRAINT fk_3es4q0x8gw88peunitrgnf0tr FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_3yiv897i9xx5e2s1k21j80xa3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolepermissions
    ADD CONSTRAINT fk_3yiv897i9xx5e2s1k21j80xa3 FOREIGN KEY (permission_id) REFERENCES permissions(id);


--
-- Name: fk_423uw67rudujfavow17g85m5d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_423uw67rudujfavow17g85m5d FOREIGN KEY (adapters_id) REFERENCES adapters(id);


--
-- Name: fk_4r8rxmkm95idra6k5cyq3y5en; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_weekly
    ADD CONSTRAINT fk_4r8rxmkm95idra6k5cyq3y5en FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_5jfxxr4mpxn07h1wcb441f7k8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_yearly
    ADD CONSTRAINT fk_5jfxxr4mpxn07h1wcb441f7k8 FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_5kapln94acqioo2tk1sxgcaqu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT fk_5kapln94acqioo2tk1sxgcaqu FOREIGN KEY (acmode) REFERENCES modemaster(id);


--
-- Name: fk_67jfffsbqh10lqnkun0vewesi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_67jfffsbqh10lqnkun0vewesi FOREIGN KEY (groupcriteria_id) REFERENCES groupscriteria(id);


--
-- Name: fk_6dw2cqm1k46b3o92x56kwok81; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraudit
    ADD CONSTRAINT fk_6dw2cqm1k46b3o92x56kwok81 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk_7tpdq1b851rijkpcoci2cawqq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_weekly
    ADD CONSTRAINT fk_7tpdq1b851rijkpcoci2cawqq FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_9mri5y8e3b5qh2kr2iu3xccq1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_9mri5y8e3b5qh2kr2iu3xccq1 FOREIGN KEY (parentid) REFERENCES groups(id);


--
-- Name: fk_9pk8fu4ar85q9krtvn9605ddv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT fk_9pk8fu4ar85q9krtvn9605ddv FOREIGN KEY (flapmode) REFERENCES winddirection_master(id);


--
-- Name: fk_adapid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pulse_meter
    ADD CONSTRAINT fk_adapid FOREIGN KEY (adapters_id) REFERENCES adapters(id);


--
-- Name: fk_adpt_tzm1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY adapters
    ADD CONSTRAINT fk_adpt_tzm1 FOREIGN KEY (timezone) REFERENCES timezonemaster(id);


--
-- Name: fk_aut17b77fbvtfyos5s6f5x5k4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY timeline
    ADD CONSTRAINT fk_aut17b77fbvtfyos5s6f5x5k4 FOREIGN KEY (adapter_id) REFERENCES adapters(id);


--
-- Name: fk_bro92n1nho9v4hgw877ajnegg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_daily
    ADD CONSTRAINT fk_bro92n1nho9v4hgw877ajnegg FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_c4q4c6bo7rv2fg34h2pto84n6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT fk_c4q4c6bo7rv2fg34h2pto84n6 FOREIGN KEY (fanspeed) REFERENCES fanspeed_master(id);


--
-- Name: fk_companies_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY maintenance_user_list
    ADD CONSTRAINT fk_companies_id FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- Name: fk_companies_users_company; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY companiesusers
    ADD CONSTRAINT fk_companies_users_company FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- Name: fk_companies_users_groups; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY companiesusers
    ADD CONSTRAINT fk_companies_users_groups FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- Name: fk_companies_users_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY companiesusers
    ADD CONSTRAINT fk_companies_users_users FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk_companyrole_companies; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolefunctionalgrp
    ADD CONSTRAINT fk_companyrole_companies FOREIGN KEY (companyid) REFERENCES companies(id) MATCH FULL;


--
-- Name: fk_distid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pulse_meter
    ADD CONSTRAINT fk_distid FOREIGN KEY (distribution_group_id) REFERENCES distribution_group(id);


--
-- Name: fk_distid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_distid FOREIGN KEY (distribution_group_id) REFERENCES distribution_group(id);


--
-- Name: fk_distid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY distribution_group
    ADD CONSTRAINT fk_distid FOREIGN KEY (customer_id) REFERENCES companies(id);


--
-- Name: fk_e862s3rospv9agg9cqyat87oq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationlog
    ADD CONSTRAINT fk_e862s3rospv9agg9cqyat87oq FOREIGN KEY (adapterid) REFERENCES adapters(id);


--
-- Name: fk_errorcode_master_notificationtype_master; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY errorcode_master
    ADD CONSTRAINT fk_errorcode_master_notificationtype_master FOREIGN KEY (notificationtype_id) REFERENCES notificationtype_master(id);


--
-- Name: fk_f3ju22jh3fbrdrp5buut13ipr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_f3ju22jh3fbrdrp5buut13ipr FOREIGN KEY (metaindoorunit_id) REFERENCES metaindoorunits(id);


--
-- Name: fk_functionalgroup_permission_functionalgroup; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY functionalgroup_permission
    ADD CONSTRAINT fk_functionalgroup_permission_functionalgroup FOREIGN KEY (functional_groupid) REFERENCES functionalgroup(functional_groupid) MATCH FULL;


--
-- Name: fk_functionalgroup_permission_permissions; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY functionalgroup_permission
    ADD CONSTRAINT fk_functionalgroup_permission_permissions FOREIGN KEY (permissionid) REFERENCES permissions(id) MATCH FULL;


--
-- Name: fk_functionalgroup_roletype; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY functionalgroup
    ADD CONSTRAINT fk_functionalgroup_roletype FOREIGN KEY (roletypeid) REFERENCES roletype(id) MATCH FULL;


--
-- Name: fk_groups_groupcategory; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_groups_groupcategory FOREIGN KEY (groupcategoryid) REFERENCES groupcategory(groupcategoryid);


--
-- Name: fk_groups_refrigerantmaster; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantmaster
    ADD CONSTRAINT fk_groups_refrigerantmaster FOREIGN KEY (siteid) REFERENCES groups(uniqueid) MATCH FULL;


--
-- Name: fk_groups_timezone; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_groups_timezone FOREIGN KEY (timezone) REFERENCES timezonemaster(id);


--
-- Name: fk_gt1fw7ibe3d50auci4i8fbukv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY availabletemp
    ADD CONSTRAINT fk_gt1fw7ibe3d50auci4i8fbukv FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_indoorunits_groups; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_indoorunits_groups FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- Name: fk_indoorunits_indoorunits; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_indoorunits_indoorunits FOREIGN KEY (parent_id) REFERENCES indoorunits(id);


--
-- Name: fk_indoorunitslog_history_fanspeed_master; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT fk_indoorunitslog_history_fanspeed_master FOREIGN KEY (fanspeed) REFERENCES fanspeed_master(id);


--
-- Name: fk_indoorunitslog_history_indoorunits; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT fk_indoorunitslog_history_indoorunits FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_indoorunitslog_history_modemaster; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT fk_indoorunitslog_history_modemaster FOREIGN KEY (acmode) REFERENCES modemaster(id);


--
-- Name: fk_indoorunitslog_history_winddirection_master; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog_history
    ADD CONSTRAINT fk_indoorunitslog_history_winddirection_master FOREIGN KEY (flapmode) REFERENCES winddirection_master(id);


--
-- Name: fk_j2c76jbbj0cqtob3mhh9lxsig; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationlog
    ADD CONSTRAINT fk_j2c76jbbj0cqtob3mhh9lxsig FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_lgf4pero1bp62p5vtk59jj6hx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY adapters
    ADD CONSTRAINT fk_lgf4pero1bp62p5vtk59jj6hx FOREIGN KEY (defualtgroupid) REFERENCES groups(id);


--
-- Name: fk_ne4tox3pdanigmj202avfn2ti; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_ne4tox3pdanigmj202avfn2ti FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- Name: fk_notificationsettings_groups; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationsettings
    ADD CONSTRAINT fk_notificationsettings_groups FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- Name: fk_notificationsettings_notificationtype_master; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificationsettings
    ADD CONSTRAINT fk_notificationsettings_notificationtype_master FOREIGN KEY (notificationtype_id) REFERENCES notificationtype_master(id);


--
-- Name: fk_o4vmavf9wetq0mimpe28jb40e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_o4vmavf9wetq0mimpe28jb40e FOREIGN KEY (timezone_id) REFERENCES timezonemaster(id);


--
-- Name: fk_omii8qay0i55ud0h1pynki901; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_omii8qay0i55ud0h1pynki901 FOREIGN KEY (roles_id) REFERENCES roles(id);


--
-- Name: fk_pa5sra7ew4xoxtlhob4go2e81; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_yearly
    ADD CONSTRAINT fk_pa5sra7ew4xoxtlhob4go2e81 FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_pbxx7flic11vic1lfnr44ywim; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity_monthly
    ADD CONSTRAINT fk_pbxx7flic11vic1lfnr44ywim FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_qck4ee66xd9endai8qthbtpa3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY adapters
    ADD CONSTRAINT fk_qck4ee66xd9endai8qthbtpa3 FOREIGN KEY (company_id) REFERENCES companies(id);


--
-- Name: fk_qcvu0enpsmd4aatd61cvi6hd8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcuser_action
    ADD CONSTRAINT fk_qcvu0enpsmd4aatd61cvi6hd8 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk_rcoperation_log_indoorunits; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcoperation_log
    ADD CONSTRAINT fk_rcoperation_log_indoorunits FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_rcoperation_log_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rcoperation_log
    ADD CONSTRAINT fk_rcoperation_log_users FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk_refrigerantmaster_adapters; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantmaster
    ADD CONSTRAINT fk_refrigerantmaster_adapters FOREIGN KEY (adapterid) REFERENCES adapters(id) MATCH FULL;


--
-- Name: fk_refrigerantstat_refrigerantmaster; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics
    ADD CONSTRAINT fk_refrigerantstat_refrigerantmaster FOREIGN KEY (refrigerantid) REFERENCES refrigerantmaster(refrigerantid);


--
-- Name: fk_refrigerantstat_refrigerantmaster_daily; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics_daily
    ADD CONSTRAINT fk_refrigerantstat_refrigerantmaster_daily FOREIGN KEY (refrigerantid) REFERENCES refrigerantmaster(refrigerantid);


--
-- Name: fk_refrigerantstat_refrigerantmaster_monthly; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics_monthly
    ADD CONSTRAINT fk_refrigerantstat_refrigerantmaster_monthly FOREIGN KEY (refrigerantid) REFERENCES refrigerantmaster(refrigerantid);


--
-- Name: fk_refrigerantstat_refrigerantmaster_weekly; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics_weekly
    ADD CONSTRAINT fk_refrigerantstat_refrigerantmaster_weekly FOREIGN KEY (refrigerantid) REFERENCES refrigerantmaster(refrigerantid);


--
-- Name: fk_refrigerantstat_refrigerantmaster_yearly; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantcircuit_statistics_yearly
    ADD CONSTRAINT fk_refrigerantstat_refrigerantmaster_yearly FOREIGN KEY (refrigerantid) REFERENCES refrigerantmaster(refrigerantid);


--
-- Name: fk_rolefunctionalgrp_functionalgroup; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolefunctionalgrp
    ADD CONSTRAINT fk_rolefunctionalgrp_functionalgroup FOREIGN KEY (funcgroupids) REFERENCES functionalgroup(functional_groupid) MATCH FULL;


--
-- Name: fk_rolefunctionalgrp_roles; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolefunctionalgrp
    ADD CONSTRAINT fk_rolefunctionalgrp_roles FOREIGN KEY (roleid) REFERENCES roles(id) MATCH FULL;


--
-- Name: fk_rolehostory_companies; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolehistory
    ADD CONSTRAINT fk_rolehostory_companies FOREIGN KEY (companyid) REFERENCES companies(id) MATCH FULL;


--
-- Name: fk_rolehostory_roles; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolehistory
    ADD CONSTRAINT fk_rolehostory_roles FOREIGN KEY (role_id) REFERENCES roles(id) MATCH FULL;


--
-- Name: fk_rolehostory_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolehistory
    ADD CONSTRAINT fk_rolehostory_users FOREIGN KEY (updateby) REFERENCES users(id) MATCH FULL;


--
-- Name: fk_roles_companies; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT fk_roles_companies FOREIGN KEY (company_id) REFERENCES companies(id) MATCH FULL;


--
-- Name: fk_roles_roletype; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT fk_roles_roletype FOREIGN KEY (roletype_id) REFERENCES roletype(id) MATCH FULL;


--
-- Name: fk_sfvu9rencl782arq0o5bul0a0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rolepermissions
    ADD CONSTRAINT fk_sfvu9rencl782arq0o5bul0a0 FOREIGN KEY (roles_id) REFERENCES roles(id);


--
-- Name: fk_svg_id1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantmaster
    ADD CONSTRAINT fk_svg_id1 FOREIGN KEY (svg_id1) REFERENCES svg_master(id);


--
-- Name: fk_svg_id2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantmaster
    ADD CONSTRAINT fk_svg_id2 FOREIGN KEY (svg_id2) REFERENCES svg_master(id);


--
-- Name: fk_svg_id3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refrigerantmaster
    ADD CONSTRAINT fk_svg_id3 FOREIGN KEY (svg_id3) REFERENCES svg_master(id);


--
-- Name: fk_t05r4etofjmnj48wrb7h6px8g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics
    ADD CONSTRAINT fk_t05r4etofjmnj48wrb7h6px8g FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_t2timx87f1dr9ov9qj9i8dyxo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY power_consumption_capacity
    ADD CONSTRAINT fk_t2timx87f1dr9ov9qj9i8dyxo FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_tma6d08qssl6gxt7tmd9ckit6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitslog
    ADD CONSTRAINT fk_tma6d08qssl6gxt7tmd9ckit6 FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_totyjayw4r5fj6m3es75uts6k; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunitstatistics_monthly
    ADD CONSTRAINT fk_totyjayw4r5fj6m3es75uts6k FOREIGN KEY (indoorunit_id) REFERENCES indoorunits(id);


--
-- Name: fk_unique_site_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_unique_site_id FOREIGN KEY (siteid) REFERENCES groups(uniqueid);


--
-- Name: fk_user_notification_setting_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_notification_settings
    ADD CONSTRAINT fk_user_notification_setting_user FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk_user_notification_settings; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_notification_settings
    ADD CONSTRAINT fk_user_notification_settings FOREIGN KEY (notification_settings_id) REFERENCES notificationsettings(id);


--
-- Name: fk_usermanagementhistory_createdby_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usermangementhistory
    ADD CONSTRAINT fk_usermanagementhistory_createdby_user FOREIGN KEY (createdby) REFERENCES users(id);


--
-- Name: fk_usermanagementhistory_updatedby_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usermangementhistory
    ADD CONSTRAINT fk_usermanagementhistory_updatedby_user FOREIGN KEY (updatedby) REFERENCES users(id);


--
-- Name: fk_usermanagementhistory_userid_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usermangementhistory
    ADD CONSTRAINT fk_usermanagementhistory_userid_user FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk_users_comapany; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_comapany FOREIGN KEY (companyid) REFERENCES companies(id) MATCH FULL;


--
-- Name: group_level_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT group_level_fk FOREIGN KEY (group_level_id) REFERENCES group_level(id);


--
-- Name: groups_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY maintenance_setting
    ADD CONSTRAINT groups_id_fk FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- Name: indoorunits_outdoorunit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT indoorunits_outdoorunit_id_fkey FOREIGN KEY (outdoorunit_id) REFERENCES outdoorunits(id);


--
-- Name: maintenance_type_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY maintenance_setting
    ADD CONSTRAINT maintenance_type_id_fk FOREIGN KEY (maintenance_type_id) REFERENCES maintenance_type_master(id);


--
-- Name: outdoorunits_adapters_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT outdoorunits_adapters_id_fkey FOREIGN KEY (adapters_id) REFERENCES adapters(id);


--
-- Name: outdoorunits_metaoutdoorunit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT outdoorunits_metaoutdoorunit_id_fkey FOREIGN KEY (metaoutdoorunit_id) REFERENCES metaoutdoorunits(id);


--
-- Name: outdoorunits_parentid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT outdoorunits_parentid_fkey FOREIGN KEY (parentid) REFERENCES outdoorunits(id) MATCH FULL;


--
-- Name: outdoorunits_siteid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outdoorunits
    ADD CONSTRAINT outdoorunits_siteid_fkey FOREIGN KEY (siteid) REFERENCES groups(uniqueid);


--
-- Name: svg_master_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT svg_master_fk FOREIGN KEY (svg_id) REFERENCES svg_master(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: b2bacserver; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON FOREIGN SERVER b2bacserver FROM PUBLIC;
REVOKE ALL ON FOREIGN SERVER b2bacserver FROM postgres;
GRANT ALL ON FOREIGN SERVER b2bacserver TO postgres;


--
-- Name: alarmanotificationmailtemp; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE alarmanotificationmailtemp FROM PUBLIC;
REVOKE ALL ON TABLE alarmanotificationmailtemp FROM postgres;
GRANT ALL ON TABLE alarmanotificationmailtemp TO postgres;


--
-- PostgreSQL database dump complete
--

-- Patch from V5.1.0 (RSI)
Alter table users drop column timezone_id;

UPDATE users SET companyid = 1;

UPDATE users 
SET companyid = companiesusers.company_id
FROM companiesusers 
WHERE companiesusers.user_id = users.id;

Alter table users alter column companyid set not  null;
--END of Patch

--Start Adding to display all groups by default
UPDATE groups SET isunit_exists = 1;
--end

-- Patch for login issue if login id more than 7
ALTER TABLE session ALTER COLUMN loginid TYPE character varying(200);
-- End of Patch for login issue if login id more than 7

--patch for V5.4.0 
-- Function: usp_getrefrigerant_suplylevelgroupname(character varying, integer)

-- DROP FUNCTION usp_getrefrigerant_suplylevelgroupname(character varying, integer);

CREATE OR REPLACE FUNCTION usp_getrefrigerant_suplylevelgroupname(
    IN groupids character varying,
    IN level integer)
  RETURNS TABLE(groupname character varying, supplygroupname character varying, groupid bigint, refrigerantid integer, supplygroupid bigint) AS
$BODY$

DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
  declare
    a text[]; 
    i int;

	
BEGIN

DROP TABLE IF EXISTS groupslevel_temp;


--
CREATE GLOBAL TEMPORARY  TABLE groupslevel_temp
(
  id bigint,
  parentid bigint,
  groupcategoryid integer,
  uniqueid character varying(16),
  "name" character varying(45),
  path character varying(45),
  supplygroupname character varying(45),
  refrigerant integer,
  supplygroupid bigint

);

Select ((array(select distinct id from usp_groupparentchilddata_level(groupids,level))) ) into a;

i := 1;
    loop  
        if i > coalesce (array_upper(a, 1),1) then
            exit;
        else
          -- RETURN;
           --RAISE NOTICE 'Calling cs_create_job(%)', array_upper(a, 1);
	    insert into groupslevel_temp          Select *,a[i],null,a[i]::bigint from usp_groupparentchilddata(a[i]) aa where  aa.groupcategoryid  = 2 ;
          --  RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
          -- commit;
           -- RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
            i := i + 1;
          end if;
    end loop;      
Update  groupslevel_temp t set supplygroupname =  (select name from groups where cast(id as varchar(40))  = t.supplygroupname);
RETURN QUERY
--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
select distinct  t.name as groupname,
t.supplygroupname,t.id as groupid,rm.refrigerantid,t.supplygroupid
from  groupslevel_temp t 
Left outer join refrigerantmaster rm   on (t.uniqueid = rm.siteid) and t.groupcategoryid  = 2
--where t.groupcategoryid in( 2)
;


--Select * from usp_getindoorunits_supplylevelgroupname('5,20',6)

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION usp_getrefrigerant_suplylevelgroupname(character varying, integer)
  OWNER TO postgres;
--End of patch V5.4.0
  
--Data patch for role management
 --
-- permissions
--
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (101, 'System', '2015-12-23', 'navi-home', '2015-12-23', '', '/home/homeScreen.htm');
				
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (102, 'System', '2015-12-23', 'navi-AC Settings', '2015-12-23', '', '/acconfig/viewAcConfig.htm');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (103, 'System', '2015-12-23', 'tab-AC Settings/IDU Details', '2015-12-23', '', '');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (104, 'System', '2015-12-23', 'tab-AC Settings/ODU Details', '2015-12-23', '', '');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (105, 'System', '2015-12-23', 'tab-AC Settings/Cloud Adapter Details', '2015-12-23', '', '');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (106, 'System', '2015-12-23', 'tab-AC Settings/Ac Maintenance', '2015-12-23', '', '');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (107, 'System', '2015-12-23', 'tab-AC Settings/Map View', '2015-12-23', '', '');
				
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (108, 'System', '2015-12-23', 'navi-Visualisation', '2015-12-23', '', '/stats/viewVisualization.htm');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (109, 'System', '2015-12-23', 'tab-Visualisation/Statistics(Group)', '2015-12-23', '', '');  
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (110, 'System', '2015-12-23', 'tab-Visualisation/Statistics(Refrigerant)', '2015-12-23', '', '');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (111, 'System', '2015-12-23', 'tab-Visualisation/Statistics(Air Con)', '2015-12-23', '', '');  
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (112, 'System', '2015-12-23', 'tab-Visualisation/Eiiiciency Ranking', '2015-12-23', '', '');				


INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (113, 'System', '2015-12-23', 'navi-Notification', '2015-12-23', '', '/notification/viewNotification.htm');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (114, 'System', '2015-12-23', 'tab-Notification/Notification Details', '2015-12-23', '', '');  
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (115, 'System', '2015-12-23', 'tab-Notification/Notification Overview', '2015-12-23', '', '');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (116, 'System', '2015-12-23', 'tab-Notification/Mainenance Settings', '2015-12-23', '', '');  
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (117, 'System', '2015-12-23', 'tab-Notification/Map View', '2015-12-23', '', '');	
				
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (118, 'System', '2015-12-23', 'navi-System Settings', '2015-12-23', '', '/co2Factor/viewSettings.htm');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (119, 'System', '2015-12-23', 'tab-System Settings/System Configuration', '2015-12-23', '', '');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (120, 'System', '2015-12-23', 'tab-System Settings/Area Allocation', '2015-12-23', '', '');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (121, 'System', '2015-12-23', 'tab-System Settings/Cut Off Request', '2015-12-23', '', '');		
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (122, 'System', '2015-12-23', 'tab-System Settings/Customer Registration', '2015-12-23', '', '');					
				
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (123, 'System', '2015-12-23', 'navi-User Account', '2015-12-23', '', '/usermanagement/viewAccount.htm');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (124, 'System', '2015-12-23', 'tab-User Account/new User Registration', '2015-12-23', '', '');  
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (125, 'System', '2015-12-23', 'tab-User Account/Update User', '2015-12-23', '', '');
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (126, 'System', '2015-12-23', 'tab-User Account/User list', '2015-12-23', '', '');  
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (127, 'System', '2015-12-23', 'tab-User Account/Role list', '2015-12-23', '', '');	
				
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (128, 'System', '2015-12-23', 'navi-System Op.', '2015-12-23', '', '/cust_data/view_cust_data.htm');		
INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (129, 'System', '2015-12-23', 'navi-CA Installation', '2015-12-23', '', '/ca_data/viewCa.htm');

INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (130, 'System', '2015-12-23', 'component-User Account/Update User/customerIdTree ', '2015-12-23', '', '');
				
--
--function group
--
INSERT INTO functionalgroup(
            functional_groupid, functional_groupname, roletypeid)
    VALUES (11, 'operator', 1);
INSERT INTO functionalgroup(
            functional_groupid, functional_groupname, roletypeid)
    VALUES (12, 'Installer/Maintainer', 2);
INSERT INTO functionalgroup(
            functional_groupid, functional_groupname, roletypeid)
    VALUES (13, 'customer', 3);
INSERT INTO functionalgroup(
            functional_groupid, functional_groupname, roletypeid)
    VALUES (14, 'superadmin', 1);
--
--function group permissions
--
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (101, 11, 101);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (102, 11, 102);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (103, 11, 103);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (104, 11, 104);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (105, 11, 105);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (106, 11, 107);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (107, 11, 108);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (108, 11, 109);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (109, 11, 110);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (110, 11, 111);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (111, 11, 112);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (112, 11, 113);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (113, 11, 114);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (114, 11, 115);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (115, 11, 116);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (116, 11, 117);


INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (117, 11, 123);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (118, 11, 124);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (119, 11, 125);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (120, 11, 126);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (121, 11, 127);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (122, 11, 128);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (190, 11, 130);

--
--Installer
--
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (123, 12, 101);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (124, 12, 102);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (125, 12, 103);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (126, 12, 104);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (127, 12, 105);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (128, 12, 106);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (129, 12, 107);


INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (130, 12, 123);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (131, 12, 124);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (132, 12, 125);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (133, 12, 126);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (134, 12, 127);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (135, 12, 129);

--
--customer
--
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (136, 13, 101);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (137, 13, 102);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (138, 13, 103);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (139, 13, 104);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (140, 13, 105);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (141, 13, 107);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (142, 13, 108);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (143, 13, 109);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (144, 13, 110);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (145, 13, 111);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (146, 13, 112);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (147, 13, 113);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (148, 13, 114);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (149, 13, 115);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (150, 13, 116);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (151, 13, 117);


INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (152, 13, 118);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (153, 13, 119);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (154, 13, 120);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (155, 13, 121);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (156, 13, 123);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (157, 13, 124);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (158, 13, 125);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (159, 13, 126);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (160, 13, 127);

--
--superadmin
--
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (161, 14, 101);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (162, 14, 102);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (163, 14, 103);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (164, 14, 104);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (165, 14, 105);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (166, 14, 106);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (167, 14, 107);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (168, 14, 108);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (169, 14, 109);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (170, 14, 110);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (171, 14, 111);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (172, 14, 112);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (173, 14, 113);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (174, 14, 114);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (175, 14, 115);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (176, 14, 116);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (177, 14, 117);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (178, 14, 118);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (179, 14, 119);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (180, 14, 120);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (181, 14, 121);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (182, 14, 122);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (183, 14, 123);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (184, 14, 124);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (185, 14, 125);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (186, 14, 126);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (187, 14, 127);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (188, 14, 128);
INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (189, 14, 129);

INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (191, 14, 130);

--
--role
--
INSERT INTO roles( id,name,roletype_id, company_id) VALUES (101, 'panasonic_101', 1, 3);
INSERT INTO roles( id,name,roletype_id, company_id) VALUES (102, 'installer_102', 2, 3);
INSERT INTO roles( id,name,roletype_id, company_id) VALUES (103, 'customer_103', 3, 3);

--
--rolefunctionalgrp
--
INSERT INTO rolefunctionalgrp(id, roleid, funcgroupids, companyid) VALUES (101, 101, 11, 3);
INSERT INTO rolefunctionalgrp(id, roleid, funcgroupids, companyid) VALUES (102, 102, 12, 3);
INSERT INTO rolefunctionalgrp(id, roleid, funcgroupids, companyid) VALUES (103, 103, 13, 3);
INSERT INTO rolefunctionalgrp(id, roleid, funcgroupids, companyid) VALUES (104, 1, 14, 3);

--End data patch for role management
				 