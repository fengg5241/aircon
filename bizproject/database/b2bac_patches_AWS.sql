-- Date: 20160513
-- Version: v0.4.1

--
-- PostgreSQL database dump
--

-- Adding datatbase patch for V9.5.0
DROP VIEW cl_rw_statusinfo_by_date_id;

CREATE OR REPLACE VIEW cl_rw_statusinfo_by_date_id AS 
 SELECT eav.facl_id,
    date_trunc('minutes'::text, eav.data_datetime) AS data_datetime,
    max(
        CASE
            WHEN eav.property_id::text = 'A1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a1,
    max(
        CASE
            WHEN eav.property_id::text = 'A2_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a2_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A2_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a2_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A3a'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a3a,
    max(
        CASE
            WHEN eav.property_id::text = 'A3h'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a3h,
    max(
        CASE
            WHEN eav.property_id::text = 'A3c'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a3c,
    max(
        CASE
            WHEN eav.property_id::text = 'A3d'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a3d,
    max(
        CASE
            WHEN eav.property_id::text = 'A6a_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6a_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A6a_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6a_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A6h_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6h_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A6h_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6h_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A6c_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6c_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A6c_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6c_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A6d_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6d_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A6d_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6d_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A6f_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6f_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A6f_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a6f_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A7a_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7a_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A7a_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7a_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A7h_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7h_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A7h_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7h_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A7c_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7c_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A7c_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7c_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A7d_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7d_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A7d_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7d_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A7f_1'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7f_1,
    max(
        CASE
            WHEN eav.property_id::text = 'A7f_2'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a7f_2,
    max(
        CASE
            WHEN eav.property_id::text = 'A10'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a10,
    max(
        CASE
            WHEN eav.property_id::text = 'A11'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a11,
    max(
        CASE
            WHEN eav.property_id::text = 'A12'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a12,
    max(
        CASE
            WHEN eav.property_id::text = 'A13'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a13,
    max(
        CASE
            WHEN eav.property_id::text = 'A14'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a14,
    max(
        CASE
            WHEN eav.property_id::text = 'A15'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a15,
    max(
        CASE
            WHEN eav.property_id::text = 'A34'::text THEN eav.measure_val
            ELSE NULL::character varying
        END::text) AS a34
   FROM ( SELECT status_info.facl_id,
            status_info.property_id,
            status_info.measure_val,
            status_info.data_datetime
           FROM vw_status_info_history status_info) eav
  GROUP BY eav.facl_id, date_trunc('minutes'::text, eav.data_datetime)
  ORDER BY date_trunc('minutes'::text, eav.data_datetime), eav.facl_id;

ALTER TABLE cl_rw_statusinfo_by_date_id
  OWNER TO appgroup;
GRANT ALL ON TABLE cl_rw_statusinfo_by_date_id TO appgroup;

-- Function: usp_getrolessbyuserid_roletype(bigint)

-- DROP FUNCTION usp_getrolessbyuserid_roletype(bigint);

CREATE OR REPLACE FUNCTION usp_getrolessbyuserid_roletype(IN in_userid bigint)
  RETURNS TABLE(rolelistid bigint, rolelistname character varying, functionalgroupid text, functionalgroupname text, roletypeid integer, roletypename character varying) AS
$BODY$

declare
    in_createdby bigint;
    supplied_roletypeid integer;
    creadedby_roletypeid integer;
    in_companyid bigint; 

 BEGIN

	Select r.roletype_id as supplied_roletypeid,rcr.roletype_id as creadedby_roletypeid,temp.supplied_companyid,temp.crby from 
		(
			(
			Select u.id as userid, cast(u.createdby as bigint) as crby,roles_id as suppliedrolesid,u.companyid as supplied_companyid from users u where u.id =  in_userid
			) m
			left join users u1 on u1.id =  cast(m.crby as bigint)
		) temp 
		left join roles r on r.id = temp.suppliedrolesid
		left join roles rcr on rcr.id = temp.roles_id
		into supplied_roletypeid, creadedby_roletypeid,	in_companyid,in_createdby ;


--Customer Admin 
-- first condition supplied_roletypeid =  3 and creadedby_roletypeid  =1
-- Second condition supplied_roletypeid =  3     Input userid = 9
 if (supplied_roletypeid = 3 and creadedby_roletypeid =  1) then 
	RETURN QUERY
	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 	(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id = 3 
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
						where cast(u.createdby as bigint)<> 1
					) tmp join 
                                roles r on r.id = tmp.roleid
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                                where   company_id = in_companyid and (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
--Panasonic admin
-- first condition supplied_roletypeid =  1 
-- Second condition supplied_roletypeid =  1  and creadedby_roletypeid =  1 and createdby =  1   Input userid = 2
elseif (supplied_roletypeid = 1 and creadedby_roletypeid =  1 and in_createdby  = 1)  then 
	RETURN QUERY
				
	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 	(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id in(1,2,3)  and cast(createdby as integer)<> 1
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
						where (roledata.role_roletypeid = 3 and u1.id =  1)
						OR (roledata.role_roletypeid = 1 and u.id <>  1)
						) tmp 
				left join roles r on r.id = tmp.roleid 
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                               where    (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
				
--Super Admin (Role Type ID  = 1 i.e. Panasonic and user id = 1 and created by = null)
elseif (supplied_roletypeid = 1 and in_userid =  1 )  then 
	RETURN QUERY

	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 
					(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id  = 1  and cast(createdby as integer) = 1
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
					) tmp
                                left join roles r on r.id = tmp.roleid
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                                where   cast(r.createdby as bigint) = 1 and
                                (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
				
	

end if;	
	

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION usp_getrolessbyuserid_roletype(bigint)
  OWNER TO appgroup;
-- End of adding database patch for V9.5.0
--start of patch (seshu)
UPDATE users
   SET createdby='4',updatedby='4'
 WHERE id='3';
 --end of patch(seshu)
---Start of patch (seshu -updating RSI one)---


CREATE OR REPLACE FUNCTION usp_getrolessbyuserid_roletype(IN in_userid bigint)
  RETURNS TABLE(rolelistid bigint, rolelistname character varying, functionalgroupid text, functionalgroupname text, roletypeid integer, roletypename character varying) AS
$BODY$

declare
    in_createdby bigint;
    supplied_roletypeid integer;
    creadedby_roletypeid integer;
    in_companyid bigint; 

 BEGIN

	Select r.roletype_id as supplied_roletypeid,rcr.roletype_id as creadedby_roletypeid,temp.supplied_companyid,temp.crby from 
		(
			(
			Select u.id as userid, cast(u.createdby as bigint) as crby,roles_id as suppliedrolesid,u.companyid as supplied_companyid from users u where u.id =  in_userid
			) m
			left join users u1 on u1.id =  cast(m.crby as bigint)
		) temp 
		left join roles r on r.id = temp.suppliedrolesid
		left join roles rcr on rcr.id = temp.roles_id
		into supplied_roletypeid, creadedby_roletypeid,	in_companyid,in_createdby ;


--Customer Admin 
-- first condition supplied_roletypeid =  3 and creadedby_roletypeid  =1
-- Second condition supplied_roletypeid =  3     Input userid = 9
 if (supplied_roletypeid = 3 and creadedby_roletypeid =  1) then 
	RETURN QUERY
	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 	(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id = 3 
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
						where cast(u.createdby as bigint)<> 1
					) tmp join 
                                roles r on r.id = tmp.roleid
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                                where   company_id = in_companyid and (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
--Panasonic admin
-- first condition supplied_roletypeid =  1 
-- Second condition supplied_roletypeid =  1  and creadedby_roletypeid =  1 and createdby =  1   Input userid = 2
elseif (supplied_roletypeid = 1 and creadedby_roletypeid =  1 and in_createdby  = 1)  then 
	RETURN QUERY
				
	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 	(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id in(1,2,3)  and cast(createdby as integer)<> 1
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
						where (roledata.role_roletypeid = 3 and u1.id =  1)
						OR (roledata.role_roletypeid = 1 and u.id <>  1)
						OR (roledata.role_roletypeid = 2 and u.id <>  1)
						) tmp 
				left join roles r on r.id = tmp.roleid 
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                               where    (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
				
--Super Admin (Role Type ID  = 1 i.e. Panasonic and user id = 1 and created by = null)
elseif (supplied_roletypeid = 1 and in_userid =  1 )  then 
	RETURN QUERY

	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 
					(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id  = 1  and cast(createdby as integer) = 1
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
					) tmp
                                left join roles r on r.id = tmp.roleid
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                                where   cast(r.createdby as bigint) = 1 and
                                (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
				
	

end if;	
	

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION usp_getrolessbyuserid_roletype(bigint)
  OWNER TO appgroup;
  
  ---End of patch (Seshu updating RSI one)
  
-- Adding V9.6.0 patch
  
-- Function: usp_getrolessbyuserid_roletype(bigint)

-- DROP FUNCTION usp_getrolessbyuserid_roletype(bigint);

CREATE OR REPLACE FUNCTION usp_getrolessbyuserid_roletype(IN in_userid bigint)
  RETURNS TABLE(rolelistid bigint, rolelistname character varying, functionalgroupid text, functionalgroupname text, roletypeid integer, roletypename character varying) AS
$BODY$

declare
    in_createdby bigint;
    supplied_roletypeid integer;
    creadedby_roletypeid integer;
    in_companyid bigint; 

 BEGIN

	Select r.roletype_id as supplied_roletypeid,rcr.roletype_id as creadedby_roletypeid,temp.supplied_companyid,temp.crby from 
		(
			(
			Select u.id as userid, cast(u.createdby as bigint) as crby,roles_id as suppliedrolesid,u.companyid as supplied_companyid from users u where u.id =  in_userid
			) m
			left join users u1 on u1.id =  cast(m.crby as bigint)
		) temp 
		left join roles r on r.id = temp.suppliedrolesid
		left join roles rcr on rcr.id = temp.roles_id
		into supplied_roletypeid, creadedby_roletypeid,	in_companyid,in_createdby ;


--Customer Admin 
-- first condition supplied_roletypeid =  3 and creadedby_roletypeid  =1
-- Second condition supplied_roletypeid =  3     Input userid = 9
 if (supplied_roletypeid = 3 and creadedby_roletypeid =  1) then 
	RETURN QUERY
	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 	(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id = 3 
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
						where cast(u.createdby as bigint)<> 1
					) tmp join 
                                roles r on r.id = tmp.roleid
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                                where   company_id = in_companyid and (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
--Panasonic admin
-- first condition supplied_roletypeid =  1 
-- Second condition supplied_roletypeid =  1  and creadedby_roletypeid =  1 and createdby =  1   Input userid = 2
elseif (supplied_roletypeid = 1 and creadedby_roletypeid =  1 and in_createdby  = 1)  then 
	RETURN QUERY
				
	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 	(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id in(1,2,3)  and cast(createdby as integer)<> 1
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
						where (roledata.role_roletypeid = 3 and u1.id =  1)
						OR (roledata.role_roletypeid in (1,2) and u.id <>  1)
						) tmp 
				left join roles r on r.id = tmp.roleid 
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                               where    (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
				
--Super Admin (Role Type ID  = 1 i.e. Panasonic and user id = 1 and created by = null)
elseif (supplied_roletypeid = 1 and in_userid =  1 )  then 
	RETURN QUERY

	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 
					(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id  = 1  and cast(createdby as integer) = 1
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
					) tmp
                                left join roles r on r.id = tmp.roleid
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                                where   cast(r.createdby as bigint) = 1 and
                                (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
				
	

end if;	
	

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION usp_getrolessbyuserid_roletype(bigint)
  OWNER TO appgroup;

drop table if exists distribution_detail_data;

CREATE TABLE distribution_detail_data
(
  cutoffreq_id bigint not null,
  device_oid character varying(128) not null,
  indoorunit_id bigint,
  ratedcapacity_kw numeric,
  workinghours_tstat_onhigh_fan numeric,
  workinghours_tstat_on_med_fan numeric,
  workinghours_tstat_on_low_fan numeric,
  workinghours_tstat_off_high_fan numeric,
  workinghours_tstat_off_med_fan numeric,
  workinghours_tstat_off_low_fan numeric,
  powerusage_kwh numeric,
  cutoffstart_actual_time timestamp without time zone,
  cutoffend_actual_time timestamp without time zone,
  pulsemeter_power_usage numeric,
  pulsemeter_id bigint,
  CONSTRAINT powerdistribution_detail_report_pkey PRIMARY KEY (cutoffreq_id,device_oid),
  CONSTRAINT fk_distribution_detail_data_pulsemeter FOREIGN KEY (pulsemeter_id)
      REFERENCES pulse_meter (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_powerdistribution_detail_report_cutoff_request FOREIGN KEY (cutoffreq_id)
      REFERENCES cutoff_request (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_powerdistribution_detail_report_indoorunits FOREIGN KEY (indoorunit_id)
      REFERENCES indoorunits (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE distribution_detail_data
  OWNER TO appgroup;

drop table if exists distribution_ratio_data;

CREATE TABLE distribution_ratio_data
(
  cutoffreq_id bigint not null,
  device_oid character varying(128) not null,
  indoorunit_id bigint,
  powerdistribution_ratio double precision,
  powerusage_kwh numeric,
  cutoffstart_actual_time timestamp without time zone,
  cutoffend_actual_time timestamp without time zone,
  pulsemeter_id bigint,
  CONSTRAINT powerdistribution_ratio_report_pkey PRIMARY KEY (cutoffreq_id, device_oid),
  CONSTRAINT fk_distribution_ratio_data_pulsemeter FOREIGN KEY (pulsemeter_id)
      REFERENCES pulse_meter (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_powerdistribution_ratio_indoorunits FOREIGN KEY (indoorunit_id)
      REFERENCES indoorunits (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_powerdistribution_ratio_report_cutoff_request FOREIGN KEY (cutoffreq_id)
      REFERENCES cutoff_request (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE distribution_ratio_data
  OWNER TO appgroup;
  
-- End of adding V9.6.0 patch
  
-- Patch by nandha --
ALTER TABLE metaindoorunits ALTER COLUMN settablefan_speed_high DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settablefan_speed_medium DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settablefan_speed_auto DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settablemode_heat DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settablemode_cool DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settablemode_dry DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settablemode_auto DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settablemode_fan DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settablefan_speed_low DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settablefan_speed_manual DROP NOT NULL;
ALTER TABLE metaindoorunits ALTER COLUMN settableauto_mode DROP NOT NULL;
-- end of patch by nandha --

--Start of patch --(Seshu)
UPDATE users
   SET  createdby='4',updatedby='4'
 WHERE id='2';
UPDATE roles
   SET createdby='1'
 WHERE id='101';
UPDATE roles
   SET createdby='4'
 WHERE id='102';
 UPDATE roles
   SET createdby='4'
 WHERE id='103';
 ----End of pacth -----(seshu)
 ---Start of patch ---(seshu)
 INSERT INTO permissions( id, createdby, creationdate, name, updatedate, updatedby, url)
				VALUES (140, 'System', '2015-12-23', 'component-User Account/Update User/restrictlist ', '2015-12-23', '', '');

 INSERT INTO functionalgroup_permission( id, functional_groupid, permissionid) VALUES (210, 11, 140);
 ----End of patch---(seshu)
 
 --Datababase patch start from RSI[v9.9.0]--
 -- Function: usp_getindooroutdoorunits_supplylevelgroupname(character varying, integer)

DROP FUNCTION usp_getindooroutdoorunits_supplylevelgroupname(character varying, integer);

CREATE OR REPLACE FUNCTION usp_getindooroutdoorunits_supplylevelgroupname(
    IN groupids character varying,
    IN level integer)
  RETURNS TABLE(indoorunitid bigint, groupname character varying, supplygroupname character varying, idname character varying, outdoorunitid bigint, groupid bigint, supplygroupid bigint) AS
$BODY$

DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
  declare
    a text[]; 
    i int;
BEGIN
	DROP TABLE IF EXISTS groupslevel_temp1;

	CREATE GLOBAL TEMPORARY  TABLE groupslevel_temp1
	(

	id bigint,
	parentid bigint,
	groupcategoryid integer,
	uniqueid character varying(16),
	"name" character varying(45),
	path character varying(45),
	supplygroupname character varying(45),
	outdoorunitid bigint,
	supplygroupid bigint
	);

	Select ((array(select distinct id from usp_groupparentchilddata_level(groupids,level))) ) into a;

	i := 1;
	loop  
		if i > coalesce (array_upper(a, 1),1) then
            exit;
        else
	    insert into groupslevel_temp1          Select *,a[i],a[i]::bigint,a[i]::bigint from usp_groupparentchilddata(a[i]) ;
	   -- Update  groupslevel_temp1 t set supplygroupid =  10060;
		   --RAISE NOTICE 'Calling cs11111_create_job(%)', a[i];
            i := i + 1;

         end if;
    end loop;      
	Update  groupslevel_temp1 t set supplygroupname =  (select name from groups where cast(id as varchar(40))  = t.supplygroupname);
	RETURN QUERY

	select distinct  case when idu.id is not null then idu.id else idusite.id END indootunitid, t.name as groupname,
	t.supplygroupname,idu.name as idname,idu.outdoorunit_id,t.id as groupid,t.supplygroupid
	from  groupslevel_temp1 t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4
	Left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
	where t.groupcategoryid in( 4,2)
	order by case when idu.id is not null then idu.id else idusite.id END;
END;


$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION usp_getindooroutdoorunits_supplylevelgroupname(character varying, integer)
  OWNER TO appgroup;
--Datababase patch end from RSI[v9.9.0]--
  
--Database patch start added by ravi [#3543]

CREATE OR REPLACE FUNCTION usp_getadapterid_supplygroupid(IN groupids character varying)
  RETURNS TABLE(adapters_id bigint) AS
$BODY$

DECLARE x INT DEFAULT 0;
	DECLARE y INT DEFAULT 0;
	DECLARE  noOfDELM  INT DEFAULT 0;
	DECLARE  IDs varchar(1024)  DEFAULT '';
  declare
    a text[]; 
    i int;

	
BEGIN
	
if groupids is null or groupids = '' then
                exit;
                return;
end if;


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
  supplygroupname character varying(45),
  supplygroupid bigint
);
  
  
Insert into tempgroupsdata select * from usp_groupparentchilddata(groupids);

select string_to_array(groupids, ',') into a; 

i := 1;
    loop  
        if i > array_upper(a, 1) then
            exit;
        else
            --insert into test(i, t) values($1, a[i]);
           Update tempgroupsdata set supplygroupid = a[i]::bigint, supplygroupname = (SELECT g.name FROM groups g WHERE cast(g.id as varchar) = a[i]) where tempgroupsdata.path LIKE ('%|'||a[i]||'|%') ;
            i := i + 1;
        end if;
    end loop;      

RETURN QUERY
select distinct (CASE WHEN idu.adapters_id is null THEN idusite.adapters_id ELSE idu.adapters_id END) adapters_id
from tempgroupsdata t left outer join indoorunits idu on t.id = idu.group_id and groupcategoryid  = 4
left outer join indoorunits idusite on (t.uniqueid = idusite.siteid)and groupcategoryid  = 2 and idusite.group_id is null 
where t.groupcategoryid in( 4,2) and (idu.adapters_id is not null or idusite.adapters_id is not null);
  
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION usp_getindoorunits_supplygroupname(character varying)
  OWNER TO appgroup;
 --Database patch end added by ravi [#3543]
 
-- Start of v9.11.0 patch from RSI
  
-- Function: usp_getrolessbyuserid_roletype(bigint)

-- DROP FUNCTION usp_getrolessbyuserid_roletype(bigint);

CREATE OR REPLACE FUNCTION usp_getrolessbyuserid_roletype(IN in_userid bigint)
  RETURNS TABLE(rolelistid bigint, rolelistname character varying, functionalgroupid text, functionalgroupname text, roletypeid integer, roletypename character varying) AS
$BODY$

declare
    in_createdby bigint;
    supplied_roletypeid integer;
    creadedby_roletypeid integer;
    in_companyid bigint; 

 BEGIN

	Select r.roletype_id as supplied_roletypeid,rcr.roletype_id as creadedby_roletypeid,temp.supplied_companyid,temp.crby from 
		(
			(
			Select u.id as userid, cast(u.createdby as bigint) as crby,roles_id as suppliedrolesid,u.companyid as supplied_companyid from users u where u.id =  in_userid
			) m
			left join users u1 on u1.id =  cast(m.crby as bigint)
		) temp 
		left join roles r on r.id = temp.suppliedrolesid
		left join roles rcr on rcr.id = temp.roles_id
		into supplied_roletypeid, creadedby_roletypeid,	in_companyid,in_createdby ;


--Customer Admin 
-- first condition supplied_roletypeid =  3 and creadedby_roletypeid  =1
-- Second condition supplied_roletypeid =  3     Input userid = 9
 if (supplied_roletypeid = 3 ) then 
	RETURN QUERY
	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 	(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id = 3 
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
						--where cast(u.createdby as bigint)<> 1
					) tmp join 
                                roles r on r.id = tmp.roleid
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                                where   company_id = in_companyid and (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
--Panasonic admin
-- first condition supplied_roletypeid =  1 
-- Second condition supplied_roletypeid =  1  and creadedby_roletypeid =  1 and createdby =  1   Input userid = 2
elseif (supplied_roletypeid = 1 and creadedby_roletypeid =  1 and in_createdby  = 1)  then 
	RETURN QUERY
				
	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 	(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id in(1,2,3)  and cast(createdby as integer)<> 1
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
						where ((roledata.role_roletypeid in (2,3)  and u1.id =  1)
						OR (roledata.role_roletypeid = 1 and u.id <>  1)
							)
						) tmp 
				left join roles r on r.id = tmp.roleid 
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                               where    (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by r.id;
				
--Super Admin (Role Type ID  = 1 i.e. Panasonic and user id = 1 and created by = null)
elseif (supplied_roletypeid = 1 and in_userid =  1 )  then 
	RETURN QUERY

	select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 
					(
						Select roledata.*, u.createdby as usercreatedby,u.roles_id as userroleid, u1.roles_id,u1.id as createdbyuserid from
						(
						select r.id as roleid,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid from roles r
						where r.roletype_id  = 1  and cast(createdby as integer) = 1
						)roledata
						join users u on u.id = cast(roledata.rolecreatedby as bigint)
						left join users u1 on u1.id = cast(u.createdby as bigint)
					) tmp
                                left join roles r on r.id = tmp.roleid
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                                where   cast(r.createdby as bigint) = 1 and
                                (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
   union all
   select r.id as rolelistId , r.name as rolelistName, string_agg(distinct cast(functional_groupid as varchar),',') functionalgroupid, string_agg(distinct functional_groupname,',') functionalgroupname,
                                rt.id as roletypeid, rt.roletypename as roletypename 
                                from 
					(
						Select rr.id as roleid from
						(
							select r.id as roleid --,r.name, r.createdby as rolecreatedby,r.roletype_id as role_roletypeid 
							from roles r
							where r.roletype_id  = 1  and cast(createdby as integer) = 1
							)roledata
							join users ur on roledata.roleid = ur.roles_id
							join roles rr on ur.id =  cast(rr.createdby as bigint)
					) tmp
                                left join roles r on r.id = tmp.roleid
                                left outer join   rolefunctionalgrp rfg on rfg.roleid = r.id 
                                left join functionalgroup fg on rfg.funcgroupids =  fg.functional_groupid 
                                left join roletype rt  on  r.roletype_id  =  rt.id 
                                where  (isdel = false or isdel is null) 
				group by  r.id , r.name ,rt.roletypename, rt.id , r.id 
				order by rolelistId;					
				
				
	

end if;	
	

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION usp_getrolessbyuserid_roletype(bigint)
  OWNER TO appgroup;


-- Function: usp_getusersbyuserid_roletype(bigint)

-- DROP FUNCTION usp_getusersbyuserid_roletype(bigint);

CREATE OR REPLACE FUNCTION usp_getusersbyuserid_roletype(IN userid bigint)
  RETURNS TABLE(id bigint, createdby bigint, supplied_roletypeid integer, creadedby_roletypeid integer) AS
$BODY$

declare
    in_createdby bigint;
    supplied_roletypeid integer;
    creadedby_roletypeid integer;
    in_companyid bigint; 

 BEGIN

	Select r.roletype_id as supplied_roletypeid,rcr.roletype_id as creadedby_roletypeid,temp.supplied_companyid,temp.crby from 
		
		(
		(Select u.id as userid, cast(u.createdby as bigint) as crby,roles_id as suppliedrolesid,u.companyid as supplied_companyid from users u where u.id =  userid) m
		left join users u1 on u1.id =  cast(m.crby as bigint)
		) temp 
		left join roles r on r.id = temp.suppliedrolesid
		left join roles rcr on rcr.id = temp.roles_id
		into supplied_roletypeid, creadedby_roletypeid,	in_companyid,in_createdby ;


--Customer Admin 
-- first condition supplied_roletypeid =  3 and creadedby_roletypeid  =1
-- Second condition supplied_roletypeid =  3  and creadedby_roletypeid =  3   Input userid = 9
 if (supplied_roletypeid = 3 ) then 
	RETURN QUERY
	Select temp.userid,cast(temp.crby as bigint),temp.supplied_roletypeid,rcr.roletype_id as created_roletypeid from
	(
	(select u.id  as userid,u.createdby as crby,r.roletype_id as supplied_roletypeid ,roles_id as suppliedrolesid from users u inner 
		join roles r on r.id =  u.roles_id 
		inner join roletype rt on rt.id =  r.roletype_id 
		where  u.companyid =  in_companyid) m
		left join users u1 on u1.id =  cast(m.crby as bigint)
	) temp 
	left join roles r on r.id = temp.suppliedrolesid
	left join roles rcr on rcr.id = temp.roles_id
	where temp.supplied_roletypeid = 3;

/*
--Panasonic operator
-- first condition supplied_roletypeid =  1 
-- Second condition supplied_roletypeid =  1  and creadedby_roletypeid =  1 and createdby <> 1   Input userid = 4
elseif (supplied_roletypeid = 1 and creadedby_roletypeid =  1 and in_createdby <> 1)  then 

	RETURN QUERY
	Select temp.userid,cast(temp.crby as bigint),temp.supplied_roletypeid,rcr.roletype_id as created_roletypeid from
	(
	(select u.id  as userid,u.createdby as crby,r.roletype_id as supplied_roletypeid ,roles_id as suppliedrolesid from users u inner join roles r on r.id =  u.roles_id inner join roletype rt on rt.id =  r.roletype_id where rt.id =  3) m
	 left join users u1 on u1.id =  cast(m.crby as bigint)

	) temp 
	left join roles r on r.id = temp.suppliedrolesid
	left join roles rcr on rcr.id = temp.roles_id
	where rcr.roletype_id  = 1; */
--Panasonic admin
-- first condition supplied_roletypeid =  1 
-- Second condition supplied_roletypeid =  1  and creadedby_roletypeid =  1 and createdby =  1   Input userid = 2
elseif (supplied_roletypeid = 1 and creadedby_roletypeid =  1 and in_createdby  = 1)  then 
	RETURN QUERY
	Select temp.userid,cast(temp.crby as bigint),temp.supplied_roletypeid,rcr.roletype_id as created_roletypeid from
	(
	(select u.id  as userid,u.createdby as crby,r.roletype_id as supplied_roletypeid ,roles_id as suppliedrolesid from users u 
		inner join roles r on r.id =  u.roles_id inner join roletype rt on rt.id =  r.roletype_id --where rt.id =  1
	) m
	 left join users u1 on u1.id =  cast(m.crby as bigint)

	) temp 
	left join roles r on r.id = temp.suppliedrolesid
	left join roles rcr on rcr.id = temp.roles_id
	where rcr.roletype_id  = 1 and cast(crby as bigint) <> 1;
	
elseif (supplied_roletypeid = 1 and userid =  1 )  then 
	RETURN QUERY

	Select temp.userid,cast(temp.crby as bigint),temp.supplied_roletypeid,rcr.roletype_id as created_roletypeid from
	(
	(select u.id  as userid,u.createdby as crby,r.roletype_id as supplied_roletypeid ,roles_id as suppliedrolesid from users u 
		inner join roles r on r.id =  u.roles_id inner join roletype rt on rt.id =  r.roletype_id --where rt.id =  1
	) m
	 left join users u1 on u1.id =  cast(m.crby as bigint)

	) temp 
	left join roles r on r.id = temp.suppliedrolesid
	left join roles rcr on rcr.id = temp.roles_id
	where  rcr.roletype_id <> 3 order by temp.userid;
	

end if;	
	

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION usp_getusersbyuserid_roletype(bigint)
  OWNER TO appgroup;


ALTER TABLE users
DROP CONSTRAINT unique_users_loginid;

-- End of database patch from RSI [v9.11.0]

--Database patch added by ravi[v9.13.0 from RSI]--
ALTER TABLE refrigerantcircuit_statistics
   ALTER COLUMN efficiency TYPE numeric(6,2);
ALTER TABLE refrigerantcircuit_statistics_daily
   ALTER COLUMN efficiency TYPE numeric(6,2);
ALTER TABLE refrigerantcircuit_statistics_monthly
   ALTER COLUMN efficiency TYPE numeric(6,2);
ALTER TABLE refrigerantcircuit_statistics_weekly
   ALTER COLUMN efficiency TYPE numeric(6,2);
ALTER TABLE refrigerantcircuit_statistics_yearly
   ALTER COLUMN efficiency TYPE numeric(6,2);
--Database patch ended by ravi[v9.13.0 from RSI]--
--Added by srinivas --to avoid oid duplication---

ALTER TABLE indoorunits ADD UNIQUE (oid);
ALTER TABLE outdoorunits ADD UNIQUE (oid);
ALTER TABLE pulse_meter ADD UNIQUE (oid);
--patch ends-----

-- Start of patch from RSI 9.20.1
-- Function: usp_check_insertmissingdatatracker(character varying, character varying, bigint[], character varying, character varying, character varying, bigint, character varying, character varying, character varying, character varying, character varying)

-- DROP FUNCTION usp_check_insertmissingdatatracker(character varying, character varying, bigint[], character varying, character varying, character varying, bigint, character varying, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION usp_check_insertmissingdatatracker(
    in_job_id character varying,
    in_app_data_entity_type character varying,
    in_device_id bigint[],
    in_device_facl_id character varying,
    in_device_type character varying,
    in_createdate character varying,
    in_companyid bigint,
    in_status character varying,
    in_timezone character varying,
    in_property_id character varying,
    in_starttime character varying,
    in_endtime character varying)
  RETURNS void AS
$BODY$
declare
		a text[]; 
		i int;
BEGIN


	BEGIN
	
	--select string_to_array(in_device_id, ',') into a; 
	i := 1;

	loop  
		--if i > array_upper(a, 1) then
		if i > coalesce (array_upper(in_device_id, 1),1) then
			exit;
		else
			BEGIN
				--RAISE NOTICE ' found record id=%', a[i];  
				PERFORM 1 FROM missing_data_tracker WHERE device_id = in_device_id[i]::bigint and device_type = in_device_type and starttime = in_starttime::timestamp and  endtime = in_endtime::timestamp LIMIT 1;	
				
				IF FOUND THEN
					RAISE NOTICE ' found record id=%', in_device_id[i];  
				ELSE

				INSERT INTO missing_data_tracker
				( job_id, app_data_entity_type, device_id, device_facl_id, device_type,  createdate,  companyid,  status, timezone, property_id, starttime, endtime) 
				VALUES (in_job_id, in_app_data_entity_type, in_device_id[i]::bigint, in_device_facl_id, in_device_type, in_createdate::timestamp, in_companyid, in_status, in_timezone, in_property_id, in_starttime::timestamp, in_endtime::timestamp);
            
				
				END IF;
				i := i + 1;
			END;
		end if;
	End loop;  
		
	
		
        END;
  	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION usp_check_insertmissingdatatracker(character varying, character varying, bigint[], character varying, character varying, character varying, bigint, character varying, character varying, character varying, character varying, character varying)
  OWNER TO appgroup;

--- composite uqnique constratint
CREATE UNIQUE INDEX unq_indx_devicetype_faclid_starttime
  ON missing_data_tracker
  USING btree
  (device_type COLLATE pg_catalog."default", device_id, starttime, endtime);
--End of patch from RSI 9.20.1---