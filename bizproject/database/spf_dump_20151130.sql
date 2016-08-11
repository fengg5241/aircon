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


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: aggr_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE aggr_data (
    facl_id character varying(128) NOT NULL,
    aggr_item_cd character varying(8) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    aggr_val double precision,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE aggr_data OWNER TO pfbiz;

--
-- Name: TABLE aggr_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE aggr_data IS 'Aggregate data
(集計データ)';


--
-- Name: COLUMN aggr_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.facl_id IS '設備ID';


--
-- Name: COLUMN aggr_data.aggr_item_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.aggr_item_cd IS '集計項目コード';


--
-- Name: COLUMN aggr_data.data_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.data_datetime IS '計測日時';


--
-- Name: COLUMN aggr_data.aggr_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.aggr_val IS '集計値';


--
-- Name: COLUMN aggr_data.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN aggr_data.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN aggr_data.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN aggr_data.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN aggr_data.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN aggr_data.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN aggr_data.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN aggr_data.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_data.upd_tstp IS '更新タイムスタンプ';


--
-- Name: aggr_item_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE aggr_item_mst (
    model_id character varying(128) NOT NULL,
    aggr_item_cd character varying(8) NOT NULL,
    measure_class character varying(4) NOT NULL,
    property_id character varying(128) NOT NULL,
    target_aggr_item_cd character varying(8) NOT NULL,
    aggr_ctg_cd character varying(8) NOT NULL,
    aggr_pattern character varying(8) NOT NULL,
    aggr_attr_cd character varying(8),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE aggr_item_mst OWNER TO pfbiz;

--
-- Name: TABLE aggr_item_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE aggr_item_mst IS 'Aggregate item master
(集計項目マスタ)';


--
-- Name: COLUMN aggr_item_mst.model_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.model_id IS '機種ID';


--
-- Name: COLUMN aggr_item_mst.aggr_item_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.aggr_item_cd IS '集計項目コード';


--
-- Name: COLUMN aggr_item_mst.measure_class; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.measure_class IS '計測データ抽出区分';


--
-- Name: COLUMN aggr_item_mst.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.property_id IS 'プロパティID';


--
-- Name: COLUMN aggr_item_mst.target_aggr_item_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.target_aggr_item_cd IS '対象集計項目コード';


--
-- Name: COLUMN aggr_item_mst.aggr_ctg_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.aggr_ctg_cd IS '集計種別';


--
-- Name: COLUMN aggr_item_mst.aggr_pattern; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.aggr_pattern IS '集計条件';


--
-- Name: COLUMN aggr_item_mst.aggr_attr_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.aggr_attr_cd IS '集計属性コード';


--
-- Name: COLUMN aggr_item_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN aggr_item_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN aggr_item_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN aggr_item_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN aggr_item_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN aggr_item_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN aggr_item_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN aggr_item_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_item_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: aggr_mng_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE aggr_mng_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    aggr_item_cd character varying(8) NOT NULL,
    keep_amount_type character varying(4) NOT NULL,
    keep_amount integer NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE aggr_mng_mst OWNER TO pfbiz;

--
-- Name: TABLE aggr_mng_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE aggr_mng_mst IS 'Aggregate manage master
(集計データ管理マスタ)';


--
-- Name: COLUMN aggr_mng_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN aggr_mng_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN aggr_mng_mst.aggr_item_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.aggr_item_cd IS '集計項目コード';


--
-- Name: COLUMN aggr_mng_mst.keep_amount_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.keep_amount_type IS '保持種別';


--
-- Name: COLUMN aggr_mng_mst.keep_amount; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.keep_amount IS '保持量';


--
-- Name: COLUMN aggr_mng_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN aggr_mng_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN aggr_mng_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN aggr_mng_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN aggr_mng_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN aggr_mng_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN aggr_mng_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN aggr_mng_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_mng_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: aggr_queue_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE aggr_queue_data (
    facl_id character varying(128) NOT NULL,
    property_id character varying(128) NOT NULL,
    aggr_item_cd character varying(8) NOT NULL,
    target_aggr_ctg_cd character varying(8) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    aggr_val character varying(128),
    aggr_ctg_cd character varying(8) NOT NULL,
    aggr_status character varying(4) NOT NULL,
    distribute_status character varying(4) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE aggr_queue_data OWNER TO pfbiz;

--
-- Name: TABLE aggr_queue_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE aggr_queue_data IS 'Aggregate queue data
(集計キューデータ)';


--
-- Name: COLUMN aggr_queue_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.facl_id IS '設備ID';


--
-- Name: COLUMN aggr_queue_data.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.property_id IS 'プロパティID';


--
-- Name: COLUMN aggr_queue_data.aggr_item_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.aggr_item_cd IS '集計項目コード';


--
-- Name: COLUMN aggr_queue_data.target_aggr_ctg_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.target_aggr_ctg_cd IS '集計先集計種別';


--
-- Name: COLUMN aggr_queue_data.data_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.data_datetime IS '計測日時';


--
-- Name: COLUMN aggr_queue_data.aggr_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.aggr_val IS '集計値';


--
-- Name: COLUMN aggr_queue_data.aggr_ctg_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.aggr_ctg_cd IS '集計種別';


--
-- Name: COLUMN aggr_queue_data.aggr_status; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.aggr_status IS '集計ステータス';


--
-- Name: COLUMN aggr_queue_data.distribute_status; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.distribute_status IS '按分ステータス';


--
-- Name: COLUMN aggr_queue_data.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN aggr_queue_data.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN aggr_queue_data.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN aggr_queue_data.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN aggr_queue_data.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN aggr_queue_data.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN aggr_queue_data.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN aggr_queue_data.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aggr_queue_data.upd_tstp IS '更新タイムスタンプ';


--
-- Name: aircon_indoor_unit; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE aircon_indoor_unit (
    ctrler_logical_id character varying(128) NOT NULL,
    machine_structure_datetime timestamp without time zone NOT NULL,
    connect_type character varying(8) NOT NULL,
    connect_number character varying(8) NOT NULL,
    connect_idu_addr character varying(128) NOT NULL,
    refrig_circuit_grp_no character varying(8) NOT NULL,
    main_idu_flg integer,
    main_idu_addr character varying(128),
    cntrl_ctr_addr character varying(8),
    facl_id character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE aircon_indoor_unit OWNER TO pfbiz;

--
-- Name: TABLE aircon_indoor_unit; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE aircon_indoor_unit IS 'Aircon indoor unit
(空調室内機)';


--
-- Name: COLUMN aircon_indoor_unit.ctrler_logical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.ctrler_logical_id IS 'コントローラ論理ID';


--
-- Name: COLUMN aircon_indoor_unit.machine_structure_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.machine_structure_datetime IS '構成情報取得日時';


--
-- Name: COLUMN aircon_indoor_unit.connect_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.connect_type IS '接続種別';


--
-- Name: COLUMN aircon_indoor_unit.connect_number; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.connect_number IS '接続番号';


--
-- Name: COLUMN aircon_indoor_unit.connect_idu_addr; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.connect_idu_addr IS '接続番号内室内機アドレス';


--
-- Name: COLUMN aircon_indoor_unit.refrig_circuit_grp_no; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.refrig_circuit_grp_no IS '冷媒系統番号';


--
-- Name: COLUMN aircon_indoor_unit.main_idu_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.main_idu_flg IS '室内機親フラグ';


--
-- Name: COLUMN aircon_indoor_unit.main_idu_addr; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.main_idu_addr IS '親室内機アドレス';


--
-- Name: COLUMN aircon_indoor_unit.cntrl_ctr_addr; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.cntrl_ctr_addr IS '集中制御アドレス';


--
-- Name: COLUMN aircon_indoor_unit.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.facl_id IS '設備ID';


--
-- Name: COLUMN aircon_indoor_unit.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN aircon_indoor_unit.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN aircon_indoor_unit.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN aircon_indoor_unit.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN aircon_indoor_unit.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN aircon_indoor_unit.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN aircon_indoor_unit.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN aircon_indoor_unit.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_indoor_unit.upd_tstp IS '更新タイムスタンプ';


--
-- Name: aircon_outdoor_unit; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE aircon_outdoor_unit (
    ctrler_logical_id character varying(128) NOT NULL,
    machine_structure_datetime timestamp without time zone NOT NULL,
    connect_type character varying(8) NOT NULL,
    connect_number character varying(8) NOT NULL,
    refrig_circuit_grp_no character varying(8) NOT NULL,
    refrig_circuit_grp_odu_id character varying(8) NOT NULL,
    facl_id character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE aircon_outdoor_unit OWNER TO pfbiz;

--
-- Name: TABLE aircon_outdoor_unit; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE aircon_outdoor_unit IS 'Aircon outdoor unit
(空調室外機)';


--
-- Name: COLUMN aircon_outdoor_unit.ctrler_logical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.ctrler_logical_id IS 'コントローラ論理ID';


--
-- Name: COLUMN aircon_outdoor_unit.machine_structure_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.machine_structure_datetime IS '構成情報取得日時';


--
-- Name: COLUMN aircon_outdoor_unit.connect_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.connect_type IS '接続種別';


--
-- Name: COLUMN aircon_outdoor_unit.connect_number; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.connect_number IS '接続番号';


--
-- Name: COLUMN aircon_outdoor_unit.refrig_circuit_grp_no; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.refrig_circuit_grp_no IS '冷媒系統番号';


--
-- Name: COLUMN aircon_outdoor_unit.refrig_circuit_grp_odu_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.refrig_circuit_grp_odu_id IS '冷媒系統内室外機親子番号';


--
-- Name: COLUMN aircon_outdoor_unit.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.facl_id IS '設備ID';


--
-- Name: COLUMN aircon_outdoor_unit.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN aircon_outdoor_unit.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN aircon_outdoor_unit.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN aircon_outdoor_unit.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN aircon_outdoor_unit.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN aircon_outdoor_unit.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN aircon_outdoor_unit.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN aircon_outdoor_unit.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN aircon_outdoor_unit.upd_tstp IS '更新タイムスタンプ';


--
-- Name: alarm_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE alarm_data (
    facl_id character varying(128) NOT NULL,
    occur_datetime timestamp without time zone NOT NULL,
    alarm_cd character varying(64) NOT NULL,
    pre_alarm_cd character varying(64),
    device_id character varying(128) NOT NULL,
    device_attr_key_1 character varying(128) NOT NULL,
    device_attr_key_2 character varying(128) NOT NULL,
    device_attr_key_3 character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE alarm_data OWNER TO pfbiz;

--
-- Name: TABLE alarm_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE alarm_data IS 'Alarm Data
(警報データ)';


--
-- Name: COLUMN alarm_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.facl_id IS '設備ID';


--
-- Name: COLUMN alarm_data.occur_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.occur_datetime IS '発生日時';


--
-- Name: COLUMN alarm_data.alarm_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.alarm_cd IS '警報コード';


--
-- Name: COLUMN alarm_data.pre_alarm_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.pre_alarm_cd IS 'プレトリップコード';


--
-- Name: COLUMN alarm_data.device_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.device_id IS '機器ID';


--
-- Name: COLUMN alarm_data.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN alarm_data.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN alarm_data.device_attr_key_3; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.device_attr_key_3 IS '機器属性キー3';


--
-- Name: COLUMN alarm_data.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN alarm_data.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN alarm_data.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN alarm_data.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN alarm_data.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN alarm_data.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN alarm_data.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN alarm_data.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_data.upd_tstp IS '更新タイムスタンプ';


--
-- Name: alarm_mng_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE alarm_mng_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    keep_amount_type character varying(4) NOT NULL,
    keep_amount integer NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE alarm_mng_mst OWNER TO pfbiz;

--
-- Name: TABLE alarm_mng_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE alarm_mng_mst IS 'Alarm manage master
(警報データ管理マスタ)';


--
-- Name: COLUMN alarm_mng_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN alarm_mng_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN alarm_mng_mst.keep_amount_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.keep_amount_type IS '保持種別';


--
-- Name: COLUMN alarm_mng_mst.keep_amount; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.keep_amount IS '保持量';


--
-- Name: COLUMN alarm_mng_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN alarm_mng_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN alarm_mng_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN alarm_mng_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN alarm_mng_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN alarm_mng_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN alarm_mng_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN alarm_mng_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN alarm_mng_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: appl_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE appl_mst (
    appl_id character varying(10) NOT NULL,
    appl_nm character varying(32),
    del_flg integer DEFAULT 0 NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE appl_mst OWNER TO pfbiz;

--
-- Name: TABLE appl_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE appl_mst IS 'Application master
(アプリマスタ)';


--
-- Name: COLUMN appl_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN appl_mst.appl_nm; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.appl_nm IS 'アプリ名';


--
-- Name: COLUMN appl_mst.del_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.del_flg IS '削除フラグ';


--
-- Name: COLUMN appl_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN appl_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN appl_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN appl_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN appl_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN appl_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN appl_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN appl_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN appl_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: cd_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE cd_mst (
    cd_id character varying(10) NOT NULL,
    cd_nm character varying(64) NOT NULL,
    cd character varying(10) NOT NULL,
    disp_order bigint NOT NULL,
    nm character varying(128),
    set_string_01 character varying(32),
    set_string_02 character varying(32),
    set_string_03 character varying(32),
    set_string_04 character varying(32),
    set_string_05 character varying(32),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE cd_mst OWNER TO pfbiz;

--
-- Name: TABLE cd_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE cd_mst IS 'Code master
(コードマスタ)';


--
-- Name: COLUMN cd_mst.cd_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.cd_id IS 'コードID';


--
-- Name: COLUMN cd_mst.cd_nm; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.cd_nm IS 'コード名';


--
-- Name: COLUMN cd_mst.cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.cd IS 'コード';


--
-- Name: COLUMN cd_mst.disp_order; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.disp_order IS '表示順';


--
-- Name: COLUMN cd_mst.nm; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.nm IS '名称';


--
-- Name: COLUMN cd_mst.set_string_01; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.set_string_01 IS '設定文字１';


--
-- Name: COLUMN cd_mst.set_string_02; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.set_string_02 IS '設定文字２';


--
-- Name: COLUMN cd_mst.set_string_03; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.set_string_03 IS '設定文字３';


--
-- Name: COLUMN cd_mst.set_string_04; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.set_string_04 IS '設定文字４';


--
-- Name: COLUMN cd_mst.set_string_05; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.set_string_05 IS '設定文字５';


--
-- Name: COLUMN cd_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN cd_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN cd_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN cd_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN cd_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN cd_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN cd_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN cd_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cd_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: characteristic_process_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE characteristic_process_mst (
    device_attr_key_1 character varying(128) NOT NULL,
    device_attr_key_2 character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE characteristic_process_mst OWNER TO pfbiz;

--
-- Name: TABLE characteristic_process_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE characteristic_process_mst IS 'Characteristic processing master
(固有処理マスタ)';


--
-- Name: COLUMN characteristic_process_mst.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN characteristic_process_mst.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN characteristic_process_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN characteristic_process_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN characteristic_process_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN characteristic_process_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN characteristic_process_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN characteristic_process_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN characteristic_process_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN characteristic_process_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN characteristic_process_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: cstmer_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE cstmer_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    del_flg integer DEFAULT 0 NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE cstmer_mst OWNER TO pfbiz;

--
-- Name: TABLE cstmer_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE cstmer_mst IS 'Customer master
(顧客マスタ)';


--
-- Name: COLUMN cstmer_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN cstmer_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN cstmer_mst.del_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.del_flg IS '削除フラグ';


--
-- Name: COLUMN cstmer_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN cstmer_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN cstmer_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN cstmer_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN cstmer_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN cstmer_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN cstmer_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN cstmer_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN cstmer_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: ctr_grp_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE ctr_grp_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    site_id character varying(64) NOT NULL,
    ctr_group_id character varying(64) NOT NULL,
    facl_id character varying(128) NOT NULL,
    del_flg integer DEFAULT 0 NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE ctr_grp_mst OWNER TO pfbiz;

--
-- Name: TABLE ctr_grp_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE ctr_grp_mst IS 'Control group
(制御グループ)';


--
-- Name: COLUMN ctr_grp_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN ctr_grp_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN ctr_grp_mst.site_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.site_id IS '施設ID';


--
-- Name: COLUMN ctr_grp_mst.ctr_group_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.ctr_group_id IS '制御グループID';


--
-- Name: COLUMN ctr_grp_mst.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.facl_id IS '設備ID';


--
-- Name: COLUMN ctr_grp_mst.del_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.del_flg IS '削除フラグ';


--
-- Name: COLUMN ctr_grp_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN ctr_grp_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN ctr_grp_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN ctr_grp_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN ctr_grp_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN ctr_grp_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN ctr_grp_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN ctr_grp_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_grp_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: ctr_item_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE ctr_item_mst (
    ctr_property_id character varying(32) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE ctr_item_mst OWNER TO pfbiz;

--
-- Name: TABLE ctr_item_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE ctr_item_mst IS 'Control item master
(制御項目マスタ)';


--
-- Name: COLUMN ctr_item_mst.ctr_property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_mst.ctr_property_id IS '制御プロパティID';


--
-- Name: COLUMN ctr_item_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN ctr_item_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN ctr_item_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN ctr_item_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN ctr_item_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN ctr_item_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN ctr_item_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN ctr_item_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: ctr_item_nm_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE ctr_item_nm_mst (
    ctr_property_id character varying(32) NOT NULL,
    lang_cd character varying(4) NOT NULL,
    ctr_property_nm character varying(128),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE ctr_item_nm_mst OWNER TO pfbiz;

--
-- Name: TABLE ctr_item_nm_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE ctr_item_nm_mst IS 'Control item name master
(制御項目名称マスタ)';


--
-- Name: COLUMN ctr_item_nm_mst.ctr_property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.ctr_property_id IS '制御プロパティID';


--
-- Name: COLUMN ctr_item_nm_mst.lang_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.lang_cd IS '言語コード';


--
-- Name: COLUMN ctr_item_nm_mst.ctr_property_nm; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.ctr_property_nm IS '制御プロパティ名';


--
-- Name: COLUMN ctr_item_nm_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN ctr_item_nm_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN ctr_item_nm_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN ctr_item_nm_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN ctr_item_nm_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN ctr_item_nm_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN ctr_item_nm_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN ctr_item_nm_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_item_nm_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: ctr_schedule_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE ctr_schedule_data (
    facl_id character varying(128) NOT NULL,
    proc_datetime timestamp without time zone NOT NULL,
    ctr_property_id character varying(32) NOT NULL,
    ctr_set_value character varying(128),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE ctr_schedule_data OWNER TO pfbiz;

--
-- Name: TABLE ctr_schedule_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE ctr_schedule_data IS 'Control schedule
(制御スケジュール)';


--
-- Name: COLUMN ctr_schedule_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.facl_id IS '設備ID';


--
-- Name: COLUMN ctr_schedule_data.proc_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.proc_datetime IS '処理日時';


--
-- Name: COLUMN ctr_schedule_data.ctr_property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.ctr_property_id IS '制御プロパティID';


--
-- Name: COLUMN ctr_schedule_data.ctr_set_value; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.ctr_set_value IS '制御設定値';


--
-- Name: COLUMN ctr_schedule_data.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN ctr_schedule_data.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN ctr_schedule_data.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN ctr_schedule_data.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN ctr_schedule_data.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN ctr_schedule_data.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN ctr_schedule_data.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN ctr_schedule_data.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctr_schedule_data.upd_tstp IS '更新タイムスタンプ';


--
-- Name: ctrler_auth_info; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE ctrler_auth_info (
    ctrler_physical_id character varying(128) NOT NULL,
    m2mpf_cd character varying(4) NOT NULL,
    m2mpf_auth_info_1 character varying(512),
    m2mpf_auth_info_2 character varying(512),
    m2mpf_auth_info_3 character varying(512),
    m2mpf_auth_info_4 character varying(512),
    m2mpf_auth_info_5 character varying(512),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE ctrler_auth_info OWNER TO pfbiz;

--
-- Name: TABLE ctrler_auth_info; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE ctrler_auth_info IS 'Controller Communication Authentication info
(コントローラ通信認証情報）';


--
-- Name: COLUMN ctrler_auth_info.ctrler_physical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.ctrler_physical_id IS 'コントローラ物理ID';


--
-- Name: COLUMN ctrler_auth_info.m2mpf_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.m2mpf_cd IS 'M2MPFコード';


--
-- Name: COLUMN ctrler_auth_info.m2mpf_auth_info_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.m2mpf_auth_info_1 IS 'M2M認証情報1';


--
-- Name: COLUMN ctrler_auth_info.m2mpf_auth_info_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.m2mpf_auth_info_2 IS 'M2M認証情報2';


--
-- Name: COLUMN ctrler_auth_info.m2mpf_auth_info_3; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.m2mpf_auth_info_3 IS 'M2M認証情報3';


--
-- Name: COLUMN ctrler_auth_info.m2mpf_auth_info_4; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.m2mpf_auth_info_4 IS 'M2M認証情報4';


--
-- Name: COLUMN ctrler_auth_info.m2mpf_auth_info_5; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.m2mpf_auth_info_5 IS 'M2M認証情報5';


--
-- Name: COLUMN ctrler_auth_info.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN ctrler_auth_info.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN ctrler_auth_info.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN ctrler_auth_info.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN ctrler_auth_info.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN ctrler_auth_info.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN ctrler_auth_info.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN ctrler_auth_info.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_auth_info.upd_tstp IS '更新タイムスタンプ';


--
-- Name: ctrler_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE ctrler_mst (
    ctrler_physical_id character varying(128) NOT NULL,
    ctrler_type character varying(128) NOT NULL,
    ctrler_physical_addr character varying(128) NOT NULL,
    ctrler_logical_id character varying(128) NOT NULL,
    service_status character varying(8),
    del_flg integer DEFAULT 0 NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE ctrler_mst OWNER TO pfbiz;

--
-- Name: TABLE ctrler_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE ctrler_mst IS 'Controller master
(コントローラマスタ)';


--
-- Name: COLUMN ctrler_mst.ctrler_physical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.ctrler_physical_id IS 'コントローラ物理ID';


--
-- Name: COLUMN ctrler_mst.ctrler_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.ctrler_type IS 'コントローラ種別';


--
-- Name: COLUMN ctrler_mst.ctrler_physical_addr; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.ctrler_physical_addr IS 'コントローラ物理アドレス';


--
-- Name: COLUMN ctrler_mst.ctrler_logical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.ctrler_logical_id IS 'コントローラ論理ID';


--
-- Name: COLUMN ctrler_mst.service_status; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.service_status IS 'サービス状態';


--
-- Name: COLUMN ctrler_mst.del_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.del_flg IS '削除フラグ';


--
-- Name: COLUMN ctrler_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN ctrler_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN ctrler_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN ctrler_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN ctrler_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN ctrler_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN ctrler_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN ctrler_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN ctrler_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: device_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE device_mst (
    device_id character varying(128) NOT NULL,
    ctrler_physical_id character varying(128) NOT NULL,
    model_id character varying(128),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE device_mst OWNER TO pfbiz;

--
-- Name: TABLE device_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE device_mst IS 'Device master
(機器マスタ)';


--
-- Name: COLUMN device_mst.device_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.device_id IS '機器ID';


--
-- Name: COLUMN device_mst.ctrler_physical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.ctrler_physical_id IS 'コントローラ物理ID';


--
-- Name: COLUMN device_mst.model_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.model_id IS '機種ID';


--
-- Name: COLUMN device_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN device_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN device_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN device_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN device_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN device_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN device_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN device_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: device_structure; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE device_structure (
    ctrler_logical_id character varying(128) NOT NULL,
    machine_structure_datetime timestamp without time zone NOT NULL,
    machine_structure_approve_datetime timestamp without time zone,
    current_flg integer NOT NULL,
    machine_structure_reg_status character varying(4) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE device_structure OWNER TO pfbiz;

--
-- Name: TABLE device_structure; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE device_structure IS 'Device chain structure
(構成基本情報)';


--
-- Name: COLUMN device_structure.ctrler_logical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.ctrler_logical_id IS 'コントローラ論理ID';


--
-- Name: COLUMN device_structure.machine_structure_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.machine_structure_datetime IS '構成情報取得日時';


--
-- Name: COLUMN device_structure.machine_structure_approve_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.machine_structure_approve_datetime IS '構成情報承認日時';


--
-- Name: COLUMN device_structure.current_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.current_flg IS '最新フラグ';


--
-- Name: COLUMN device_structure.machine_structure_reg_status; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.machine_structure_reg_status IS '構成情報取得結果コード';


--
-- Name: COLUMN device_structure.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN device_structure.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN device_structure.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN device_structure.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN device_structure.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN device_structure.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN device_structure.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN device_structure.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN device_structure.upd_tstp IS '更新タイムスタンプ';


--
-- Name: distribute_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE distribute_data (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    distribute_grp_id character varying(10) NOT NULL,
    facl_id character varying(128) NOT NULL,
    aggr_ctg_cd character varying(8) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    power_distribution_rate double precision,
    gas_distribution_rate double precision,
    power_distribution_val double precision,
    gas_distribution_val double precision,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE distribute_data OWNER TO pfbiz;

--
-- Name: TABLE distribute_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE distribute_data IS 'Distribution data
(按分データ)';


--
-- Name: COLUMN distribute_data.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.appl_id IS 'アプリID';


--
-- Name: COLUMN distribute_data.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.cstmer_id IS '顧客ID';


--
-- Name: COLUMN distribute_data.distribute_grp_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.distribute_grp_id IS '按分グループID';


--
-- Name: COLUMN distribute_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.facl_id IS '設備ID';


--
-- Name: COLUMN distribute_data.aggr_ctg_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.aggr_ctg_cd IS '集計種別';


--
-- Name: COLUMN distribute_data.data_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.data_datetime IS '計測日時';


--
-- Name: COLUMN distribute_data.power_distribution_rate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.power_distribution_rate IS '電力量按分率';


--
-- Name: COLUMN distribute_data.gas_distribution_rate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.gas_distribution_rate IS 'ガス按分率';


--
-- Name: COLUMN distribute_data.power_distribution_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.power_distribution_val IS '按分消費電力量';


--
-- Name: COLUMN distribute_data.gas_distribution_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.gas_distribution_val IS '按分ガス消費量';


--
-- Name: COLUMN distribute_data.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN distribute_data.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN distribute_data.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN distribute_data.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN distribute_data.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN distribute_data.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN distribute_data.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN distribute_data.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_data.upd_tstp IS '更新タイムスタンプ';


--
-- Name: distribute_grp_detail_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE distribute_grp_detail_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    distribute_grp_id character varying(10) NOT NULL,
    valid_period_startdate timestamp without time zone NOT NULL,
    facl_id character varying(128) NOT NULL,
    model_ctg_cd character varying(64) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE distribute_grp_detail_mst OWNER TO pfbiz;

--
-- Name: TABLE distribute_grp_detail_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE distribute_grp_detail_mst IS 'Distribution group detail
(按分グループ設備明細)';


--
-- Name: COLUMN distribute_grp_detail_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN distribute_grp_detail_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN distribute_grp_detail_mst.distribute_grp_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.distribute_grp_id IS '按分グループID';


--
-- Name: COLUMN distribute_grp_detail_mst.valid_period_startdate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.valid_period_startdate IS '有効期間開始日時';


--
-- Name: COLUMN distribute_grp_detail_mst.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.facl_id IS '設備ID';


--
-- Name: COLUMN distribute_grp_detail_mst.model_ctg_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.model_ctg_cd IS '機種カテゴリコード';


--
-- Name: COLUMN distribute_grp_detail_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN distribute_grp_detail_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN distribute_grp_detail_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN distribute_grp_detail_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN distribute_grp_detail_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN distribute_grp_detail_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN distribute_grp_detail_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN distribute_grp_detail_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_detail_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: distribute_grp_meter_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE distribute_grp_meter_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    distribute_grp_id character varying(10) NOT NULL,
    valid_period_startdate timestamp without time zone NOT NULL,
    seq_no bigint NOT NULL,
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
);


ALTER TABLE distribute_grp_meter_mst OWNER TO pfbiz;

--
-- Name: TABLE distribute_grp_meter_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE distribute_grp_meter_mst IS 'Distribution group''s meter
(按分グループ所属メータ)';


--
-- Name: COLUMN distribute_grp_meter_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN distribute_grp_meter_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN distribute_grp_meter_mst.distribute_grp_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.distribute_grp_id IS '按分グループID';


--
-- Name: COLUMN distribute_grp_meter_mst.valid_period_startdate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.valid_period_startdate IS '有効期間開始日時';


--
-- Name: COLUMN distribute_grp_meter_mst.seq_no; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.seq_no IS '連番';


--
-- Name: COLUMN distribute_grp_meter_mst.meter_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.meter_type IS 'メータ種別';


--
-- Name: COLUMN distribute_grp_meter_mst.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.facl_id IS '設備ID';


--
-- Name: COLUMN distribute_grp_meter_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN distribute_grp_meter_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN distribute_grp_meter_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN distribute_grp_meter_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN distribute_grp_meter_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN distribute_grp_meter_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN distribute_grp_meter_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN distribute_grp_meter_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_meter_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: distribute_grp_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE distribute_grp_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    distribute_grp_id character varying(10) NOT NULL,
    valid_period_startdate timestamp without time zone NOT NULL,
    valid_period_enddate timestamp without time zone DEFAULT 'infinity'::timestamp without time zone NOT NULL,
    distribute_calc_cd character varying(4),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE distribute_grp_mst OWNER TO pfbiz;

--
-- Name: TABLE distribute_grp_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE distribute_grp_mst IS 'Distribution group
(按分グループ)';


--
-- Name: COLUMN distribute_grp_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN distribute_grp_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN distribute_grp_mst.distribute_grp_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.distribute_grp_id IS '按分グループID';


--
-- Name: COLUMN distribute_grp_mst.valid_period_startdate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.valid_period_startdate IS '有効期間開始日時';


--
-- Name: COLUMN distribute_grp_mst.valid_period_enddate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.valid_period_enddate IS '有効期間終了日時';


--
-- Name: COLUMN distribute_grp_mst.distribute_calc_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.distribute_calc_cd IS '按分処理方式コード';


--
-- Name: COLUMN distribute_grp_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN distribute_grp_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN distribute_grp_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN distribute_grp_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN distribute_grp_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN distribute_grp_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN distribute_grp_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN distribute_grp_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN distribute_grp_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: event_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE event_data (
    facl_id character varying(128) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    property_id character varying(128) NOT NULL,
    measure_val character varying(128),
    device_id character varying(128) NOT NULL,
    device_attr_key_1 character varying(128),
    device_attr_key_2 character varying(128),
    device_attr_key_3 character varying(128)
);


ALTER TABLE event_data OWNER TO pfbiz;

--
-- Name: TABLE event_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE event_data IS 'Event data
(イベントデータ)';


--
-- Name: COLUMN event_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN event_data.facl_id IS '設備ID';


--
-- Name: COLUMN event_data.data_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN event_data.data_datetime IS '計測日時';


--
-- Name: COLUMN event_data.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN event_data.property_id IS 'プロパティID';


--
-- Name: COLUMN event_data.measure_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN event_data.measure_val IS '計測値';


--
-- Name: COLUMN event_data.device_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN event_data.device_id IS '機器ID';


--
-- Name: COLUMN event_data.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN event_data.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN event_data.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN event_data.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN event_data.device_attr_key_3; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN event_data.device_attr_key_3 IS '機器属性キー3';


--
-- Name: facl_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE facl_mst (
    facl_id character varying(128) NOT NULL,
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    site_id character varying(64) NOT NULL,
    model_id character varying(128),
    facl_inst_datetime timestamp without time zone,
    facl_remv_datetime timestamp without time zone,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE facl_mst OWNER TO pfbiz;

--
-- Name: TABLE facl_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE facl_mst IS 'Facility master
(設備マスタ)';


--
-- Name: COLUMN facl_mst.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.facl_id IS '設備ID';


--
-- Name: COLUMN facl_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN facl_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN facl_mst.site_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.site_id IS '施設ID';


--
-- Name: COLUMN facl_mst.model_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.model_id IS '機種ID';


--
-- Name: COLUMN facl_mst.facl_inst_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.facl_inst_datetime IS '設備導入日';


--
-- Name: COLUMN facl_mst.facl_remv_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.facl_remv_datetime IS '設備撤去日';


--
-- Name: COLUMN facl_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN facl_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN facl_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN facl_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN facl_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN facl_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN facl_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN facl_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: facl_property_info; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE facl_property_info (
    ctrler_logical_id character varying(128) NOT NULL,
    machine_structure_datetime timestamp without time zone NOT NULL,
    facl_id character varying(128) NOT NULL,
    structure_property_id character varying(128) NOT NULL,
    set_value character varying(128),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE facl_property_info OWNER TO pfbiz;

--
-- Name: TABLE facl_property_info; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE facl_property_info IS 'Facility property information
(設備詳細属性情報)';


--
-- Name: COLUMN facl_property_info.ctrler_logical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.ctrler_logical_id IS 'コントローラ論理ID';


--
-- Name: COLUMN facl_property_info.machine_structure_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.machine_structure_datetime IS '構成情報取得日時';


--
-- Name: COLUMN facl_property_info.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.facl_id IS '設備ID';


--
-- Name: COLUMN facl_property_info.structure_property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.structure_property_id IS '構成情報プロパティID';


--
-- Name: COLUMN facl_property_info.set_value; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.set_value IS '設定値';


--
-- Name: COLUMN facl_property_info.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN facl_property_info.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN facl_property_info.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN facl_property_info.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN facl_property_info.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN facl_property_info.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN facl_property_info.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN facl_property_info.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_info.upd_tstp IS '更新タイムスタンプ';


--
-- Name: facl_property_item_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE facl_property_item_mst (
    project_ctg_cd character varying(4) NOT NULL,
    device_attr_key_1 character varying(128) NOT NULL,
    device_attr_key_2 character varying(128) NOT NULL,
    device_attr_key_3 character varying(128) NOT NULL,
    structure_property_id character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE facl_property_item_mst OWNER TO pfbiz;

--
-- Name: TABLE facl_property_item_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE facl_property_item_mst IS 'Facility property item master
(設備属性項目マスタ)';


--
-- Name: COLUMN facl_property_item_mst.project_ctg_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.project_ctg_cd IS '案件種別コード';


--
-- Name: COLUMN facl_property_item_mst.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN facl_property_item_mst.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN facl_property_item_mst.device_attr_key_3; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.device_attr_key_3 IS '機器属性キー3';


--
-- Name: COLUMN facl_property_item_mst.structure_property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.structure_property_id IS '構成情報プロパティID';


--
-- Name: COLUMN facl_property_item_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN facl_property_item_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN facl_property_item_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN facl_property_item_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN facl_property_item_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN facl_property_item_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN facl_property_item_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN facl_property_item_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN facl_property_item_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: fault_diagnosis_result; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE fault_diagnosis_result (
    facl_id character varying(128) NOT NULL,
    occur_datetime timestamp without time zone NOT NULL,
    fail_diag_cd character varying(10) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE fault_diagnosis_result OWNER TO pfbiz;

--
-- Name: TABLE fault_diagnosis_result; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE fault_diagnosis_result IS 'Device fail diagnosis data
(故障診断データ)';


--
-- Name: COLUMN fault_diagnosis_result.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.facl_id IS '設備ID';


--
-- Name: COLUMN fault_diagnosis_result.occur_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.occur_datetime IS '発生日時';


--
-- Name: COLUMN fault_diagnosis_result.fail_diag_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.fail_diag_cd IS '故障診断コード';


--
-- Name: COLUMN fault_diagnosis_result.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN fault_diagnosis_result.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN fault_diagnosis_result.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN fault_diagnosis_result.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN fault_diagnosis_result.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN fault_diagnosis_result.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN fault_diagnosis_result.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN fault_diagnosis_result.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN fault_diagnosis_result.upd_tstp IS '更新タイムスタンプ';


--
-- Name: inst_ctrler_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE inst_ctrler_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    site_id character varying(64) NOT NULL,
    ctrler_logical_id character varying(128) NOT NULL,
    ctrler_physical_id character varying(128) NOT NULL,
    valid_period_startdate timestamp without time zone NOT NULL,
    valid_period_enddate timestamp without time zone DEFAULT 'infinity'::timestamp without time zone,
    del_flg integer DEFAULT 0 NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE inst_ctrler_mst OWNER TO pfbiz;

--
-- Name: TABLE inst_ctrler_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE inst_ctrler_mst IS 'Installed controller master
(施設設置コントローラマスタ)';


--
-- Name: COLUMN inst_ctrler_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN inst_ctrler_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN inst_ctrler_mst.site_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.site_id IS '施設ID';


--
-- Name: COLUMN inst_ctrler_mst.ctrler_logical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.ctrler_logical_id IS 'コントローラ論理ID';


--
-- Name: COLUMN inst_ctrler_mst.ctrler_physical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.ctrler_physical_id IS 'コントローラ物理ID';


--
-- Name: COLUMN inst_ctrler_mst.valid_period_startdate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.valid_period_startdate IS '有効期間開始日時';


--
-- Name: COLUMN inst_ctrler_mst.valid_period_enddate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.valid_period_enddate IS '有効期間終了日時';


--
-- Name: COLUMN inst_ctrler_mst.del_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.del_flg IS '削除フラグ';


--
-- Name: COLUMN inst_ctrler_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN inst_ctrler_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN inst_ctrler_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN inst_ctrler_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN inst_ctrler_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN inst_ctrler_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN inst_ctrler_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN inst_ctrler_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_ctrler_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: inst_facl_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE inst_facl_mst (
    facl_id character varying(128) NOT NULL,
    device_id character varying(128) NOT NULL,
    valid_period_startdate timestamp without time zone NOT NULL,
    valid_period_enddate timestamp without time zone DEFAULT 'infinity'::timestamp without time zone,
    del_flg integer DEFAULT 0 NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE inst_facl_mst OWNER TO pfbiz;

--
-- Name: TABLE inst_facl_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE inst_facl_mst IS 'Installed facility master
(設備機器連携マスタ)';


--
-- Name: COLUMN inst_facl_mst.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.facl_id IS '設備ID';


--
-- Name: COLUMN inst_facl_mst.device_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.device_id IS '機器ID';


--
-- Name: COLUMN inst_facl_mst.valid_period_startdate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.valid_period_startdate IS '有効期間開始日時';


--
-- Name: COLUMN inst_facl_mst.valid_period_enddate; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.valid_period_enddate IS '有効期間終了日時';


--
-- Name: COLUMN inst_facl_mst.del_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.del_flg IS '削除フラグ';


--
-- Name: COLUMN inst_facl_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN inst_facl_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN inst_facl_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN inst_facl_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN inst_facl_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN inst_facl_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN inst_facl_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN inst_facl_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: inst_facl_temp_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE inst_facl_temp_mst (
    ctrler_logical_id character varying(128) NOT NULL,
    machine_structure_datetime timestamp without time zone NOT NULL,
    physical_oid character varying(128) NOT NULL,
    logical_oid character varying(128) NOT NULL,
    structure_device_type character varying(4) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE inst_facl_temp_mst OWNER TO pfbiz;

--
-- Name: TABLE inst_facl_temp_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE inst_facl_temp_mst IS 'Installed facility temporary master
(構成情報取得用設備機器連携マスタ)';


--
-- Name: COLUMN inst_facl_temp_mst.ctrler_logical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.ctrler_logical_id IS 'コントローラ論理ID';


--
-- Name: COLUMN inst_facl_temp_mst.machine_structure_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.machine_structure_datetime IS '構成情報取得日時';


--
-- Name: COLUMN inst_facl_temp_mst.physical_oid; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.physical_oid IS '物理OID';


--
-- Name: COLUMN inst_facl_temp_mst.logical_oid; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.logical_oid IS '論理OID';


--
-- Name: COLUMN inst_facl_temp_mst.structure_device_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.structure_device_type IS '構成機器種別';


--
-- Name: COLUMN inst_facl_temp_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN inst_facl_temp_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN inst_facl_temp_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN inst_facl_temp_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN inst_facl_temp_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN inst_facl_temp_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN inst_facl_temp_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN inst_facl_temp_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN inst_facl_temp_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: m2mdata_convert_class_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE m2mdata_convert_class_mst (
    m2mpf_cd character varying(4) NOT NULL,
    data_category_code character varying(4) NOT NULL,
    device_attr_key_1 character varying(128) NOT NULL,
    device_attr_key_2 character varying(128) NOT NULL,
    m2m_data_convert_class character varying(128),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE m2mdata_convert_class_mst OWNER TO pfbiz;

--
-- Name: TABLE m2mdata_convert_class_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE m2mdata_convert_class_mst IS 'M2M Data conversion class master
(M2Mデータ変換クラスマスタ)';


--
-- Name: COLUMN m2mdata_convert_class_mst.m2mpf_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.m2mpf_cd IS 'M2MPFコード';


--
-- Name: COLUMN m2mdata_convert_class_mst.data_category_code; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.data_category_code IS 'データ種別コード';


--
-- Name: COLUMN m2mdata_convert_class_mst.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN m2mdata_convert_class_mst.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN m2mdata_convert_class_mst.m2m_data_convert_class; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.m2m_data_convert_class IS 'M2Mデータ変換クラス名';


--
-- Name: COLUMN m2mdata_convert_class_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN m2mdata_convert_class_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN m2mdata_convert_class_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN m2mdata_convert_class_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN m2mdata_convert_class_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN m2mdata_convert_class_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN m2mdata_convert_class_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN m2mdata_convert_class_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN m2mdata_convert_class_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: measure_item_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE measure_item_mst (
    property_id character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE measure_item_mst OWNER TO pfbiz;

--
-- Name: TABLE measure_item_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE measure_item_mst IS 'Measurement item master
(計測項目マスタ)';


--
-- Name: COLUMN measure_item_mst.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_mst.property_id IS 'プロパティID';


--
-- Name: COLUMN measure_item_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN measure_item_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN measure_item_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN measure_item_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN measure_item_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN measure_item_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN measure_item_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN measure_item_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: measure_item_nm_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE measure_item_nm_mst (
    property_id character varying(128) NOT NULL,
    lang_cd character varying(4) NOT NULL,
    property_nm character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE measure_item_nm_mst OWNER TO pfbiz;

--
-- Name: TABLE measure_item_nm_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE measure_item_nm_mst IS 'Measurement item name master
(計測項目名称マスタ)';


--
-- Name: COLUMN measure_item_nm_mst.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.property_id IS 'プロパティID';


--
-- Name: COLUMN measure_item_nm_mst.lang_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.lang_cd IS '言語コード';


--
-- Name: COLUMN measure_item_nm_mst.property_nm; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.property_nm IS 'プロパティ名';


--
-- Name: COLUMN measure_item_nm_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN measure_item_nm_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN measure_item_nm_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN measure_item_nm_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN measure_item_nm_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN measure_item_nm_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN measure_item_nm_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN measure_item_nm_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_item_nm_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: measure_mng_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE measure_mng_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    property_id character varying(128) NOT NULL,
    keep_amount_type character varying(4) NOT NULL,
    keep_amount integer NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE measure_mng_mst OWNER TO pfbiz;

--
-- Name: TABLE measure_mng_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE measure_mng_mst IS 'Measurement manage master
(計測データ管理マスタ)';


--
-- Name: COLUMN measure_mng_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN measure_mng_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN measure_mng_mst.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.property_id IS 'プロパティID';


--
-- Name: COLUMN measure_mng_mst.keep_amount_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.keep_amount_type IS '保持種別';


--
-- Name: COLUMN measure_mng_mst.keep_amount; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.keep_amount IS '保持量';


--
-- Name: COLUMN measure_mng_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN measure_mng_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN measure_mng_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN measure_mng_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN measure_mng_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN measure_mng_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN measure_mng_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN measure_mng_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_mng_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: measure_val_convert_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE measure_val_convert_mst (
    model_id character varying(128) NOT NULL,
    property_id character varying(128) NOT NULL,
    physical_val character varying(128) NOT NULL,
    logical_val character varying(128) NOT NULL,
    remark character varying(256),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE measure_val_convert_mst OWNER TO pfbiz;

--
-- Name: TABLE measure_val_convert_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE measure_val_convert_mst IS 'Measured value convert master
(計測データ変換マスタ)';


--
-- Name: COLUMN measure_val_convert_mst.model_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.model_id IS '機種ID';


--
-- Name: COLUMN measure_val_convert_mst.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.property_id IS 'プロパティID';


--
-- Name: COLUMN measure_val_convert_mst.physical_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.physical_val IS '物理値';


--
-- Name: COLUMN measure_val_convert_mst.logical_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.logical_val IS '論理値';


--
-- Name: COLUMN measure_val_convert_mst.remark; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.remark IS '備考';


--
-- Name: COLUMN measure_val_convert_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN measure_val_convert_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN measure_val_convert_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN measure_val_convert_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN measure_val_convert_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN measure_val_convert_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN measure_val_convert_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN measure_val_convert_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN measure_val_convert_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: meter_unit; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE meter_unit (
    ctrler_logical_id character varying(128) NOT NULL,
    machine_structure_datetime timestamp without time zone NOT NULL,
    connect_type character varying(8) NOT NULL,
    adapter_number character varying(8) NOT NULL,
    port_number character varying(8) NOT NULL,
    meter_type character varying(8),
    facl_id character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE meter_unit OWNER TO pfbiz;

--
-- Name: TABLE meter_unit; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE meter_unit IS 'Meter unit
(計測メータ)';


--
-- Name: COLUMN meter_unit.ctrler_logical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.ctrler_logical_id IS 'コントローラ論理ID';


--
-- Name: COLUMN meter_unit.machine_structure_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.machine_structure_datetime IS '構成情報取得日時';


--
-- Name: COLUMN meter_unit.connect_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.connect_type IS '接続種別';


--
-- Name: COLUMN meter_unit.adapter_number; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.adapter_number IS 'アダプタ番号';


--
-- Name: COLUMN meter_unit.port_number; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.port_number IS 'ポート番号';


--
-- Name: COLUMN meter_unit.meter_type; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.meter_type IS 'メータ種別';


--
-- Name: COLUMN meter_unit.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.facl_id IS '設備ID';


--
-- Name: COLUMN meter_unit.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN meter_unit.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN meter_unit.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN meter_unit.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN meter_unit.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN meter_unit.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN meter_unit.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN meter_unit.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN meter_unit.upd_tstp IS '更新タイムスタンプ';


--
-- Name: model_ctg_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE model_ctg_mst (
    model_ctg_cd character varying(64) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE model_ctg_mst OWNER TO pfbiz;

--
-- Name: TABLE model_ctg_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE model_ctg_mst IS 'Model category master
(機種カテゴリマスタ)';


--
-- Name: COLUMN model_ctg_mst.model_ctg_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_mst.model_ctg_cd IS '機種カテゴリコード';


--
-- Name: COLUMN model_ctg_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN model_ctg_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN model_ctg_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN model_ctg_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN model_ctg_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN model_ctg_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN model_ctg_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN model_ctg_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: model_ctg_nm_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE model_ctg_nm_mst (
    model_ctg_cd character varying(64) NOT NULL,
    lang_cd character varying(4) NOT NULL,
    model_ctg_nm character varying(64),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE model_ctg_nm_mst OWNER TO pfbiz;

--
-- Name: TABLE model_ctg_nm_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE model_ctg_nm_mst IS 'Model category name master
(機種カテゴリ名称マスタ)';


--
-- Name: COLUMN model_ctg_nm_mst.model_ctg_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.model_ctg_cd IS '機種カテゴリコード';


--
-- Name: COLUMN model_ctg_nm_mst.lang_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.lang_cd IS '言語コード';


--
-- Name: COLUMN model_ctg_nm_mst.model_ctg_nm; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.model_ctg_nm IS '機種カテゴリ名';


--
-- Name: COLUMN model_ctg_nm_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN model_ctg_nm_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN model_ctg_nm_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN model_ctg_nm_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN model_ctg_nm_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN model_ctg_nm_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN model_ctg_nm_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN model_ctg_nm_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_ctg_nm_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: model_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE model_mst (
    model_id character varying(128) NOT NULL,
    model_ctg_cd character varying(64) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE model_mst OWNER TO pfbiz;

--
-- Name: TABLE model_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE model_mst IS 'Model master
(機種マスタ)';


--
-- Name: COLUMN model_mst.model_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.model_id IS '機種ID';


--
-- Name: COLUMN model_mst.model_ctg_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.model_ctg_cd IS '機種カテゴリコード';


--
-- Name: COLUMN model_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN model_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN model_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN model_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN model_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN model_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN model_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN model_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: model_nm_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE model_nm_mst (
    model_id character varying(128) NOT NULL,
    lang_cd character varying(4) NOT NULL,
    model_nm character varying(64),
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE model_nm_mst OWNER TO pfbiz;

--
-- Name: TABLE model_nm_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE model_nm_mst IS 'Model name master
(機種名称マスタ)';


--
-- Name: COLUMN model_nm_mst.model_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.model_id IS '機種ID';


--
-- Name: COLUMN model_nm_mst.lang_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.lang_cd IS '言語コード';


--
-- Name: COLUMN model_nm_mst.model_nm; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.model_nm IS '機種名';


--
-- Name: COLUMN model_nm_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN model_nm_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN model_nm_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN model_nm_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN model_nm_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN model_nm_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN model_nm_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN model_nm_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_nm_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: model_spcf_ctr_item_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE model_spcf_ctr_item_mst (
    model_id character varying(128) NOT NULL,
    ctr_property_id character varying(32) NOT NULL,
    device_attr_key_1 character varying(128) NOT NULL,
    device_attr_key_2 character varying(128) NOT NULL,
    device_attr_key_3 character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE model_spcf_ctr_item_mst OWNER TO pfbiz;

--
-- Name: TABLE model_spcf_ctr_item_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE model_spcf_ctr_item_mst IS 'Model-specific control item master
(機種別制御項目マスタ)';


--
-- Name: COLUMN model_spcf_ctr_item_mst.model_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.model_id IS '機種ID';


--
-- Name: COLUMN model_spcf_ctr_item_mst.ctr_property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.ctr_property_id IS '制御プロパティID';


--
-- Name: COLUMN model_spcf_ctr_item_mst.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN model_spcf_ctr_item_mst.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN model_spcf_ctr_item_mst.device_attr_key_3; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.device_attr_key_3 IS '機器属性キー3';


--
-- Name: COLUMN model_spcf_ctr_item_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN model_spcf_ctr_item_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN model_spcf_ctr_item_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN model_spcf_ctr_item_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN model_spcf_ctr_item_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN model_spcf_ctr_item_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN model_spcf_ctr_item_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN model_spcf_ctr_item_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_ctr_item_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: model_spcf_measure_item_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE model_spcf_measure_item_mst (
    model_id character varying(128) NOT NULL,
    property_id character varying(128) NOT NULL,
    measure_class character varying(4) NOT NULL,
    device_attr_key_1 character varying(128) NOT NULL,
    device_attr_key_2 character varying(128) NOT NULL,
    device_attr_key_3 character varying(128) NOT NULL,
    type_cd character varying(8) NOT NULL,
    offset_set_flg integer NOT NULL,
    data_collect_interval integer NOT NULL,
    data_transfer_interval integer NOT NULL,
    correct_magnification double precision NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE model_spcf_measure_item_mst OWNER TO pfbiz;

--
-- Name: TABLE model_spcf_measure_item_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE model_spcf_measure_item_mst IS 'Model-specific measurement item master
(機種別計測項目マスタ)';


--
-- Name: COLUMN model_spcf_measure_item_mst.model_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.model_id IS '機種ID';


--
-- Name: COLUMN model_spcf_measure_item_mst.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.property_id IS 'プロパティID';


--
-- Name: COLUMN model_spcf_measure_item_mst.measure_class; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.measure_class IS '計測データ抽出区分';


--
-- Name: COLUMN model_spcf_measure_item_mst.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN model_spcf_measure_item_mst.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN model_spcf_measure_item_mst.device_attr_key_3; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.device_attr_key_3 IS '機器属性キー3';


--
-- Name: COLUMN model_spcf_measure_item_mst.type_cd; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.type_cd IS '型コード';


--
-- Name: COLUMN model_spcf_measure_item_mst.offset_set_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.offset_set_flg IS 'オフセット取得フラグ';


--
-- Name: COLUMN model_spcf_measure_item_mst.data_collect_interval; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.data_collect_interval IS 'データ収集間隔';


--
-- Name: COLUMN model_spcf_measure_item_mst.data_transfer_interval; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.data_transfer_interval IS 'データ送信間隔';


--
-- Name: COLUMN model_spcf_measure_item_mst.correct_magnification; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.correct_magnification IS '補正率';


--
-- Name: COLUMN model_spcf_measure_item_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN model_spcf_measure_item_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN model_spcf_measure_item_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN model_spcf_measure_item_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN model_spcf_measure_item_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN model_spcf_measure_item_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN model_spcf_measure_item_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN model_spcf_measure_item_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN model_spcf_measure_item_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: offset_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE offset_data (
    facl_id character varying(128) NOT NULL,
    offset_datetime timestamp without time zone NOT NULL,
    property_id character varying(128) NOT NULL,
    offset_val character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE offset_data OWNER TO pfbiz;

--
-- Name: TABLE offset_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE offset_data IS 'Offset data
(オフセットデータ)';


--
-- Name: COLUMN offset_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.facl_id IS '設備ID';


--
-- Name: COLUMN offset_data.offset_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.offset_datetime IS 'オフセット日時';


--
-- Name: COLUMN offset_data.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.property_id IS 'プロパティID';


--
-- Name: COLUMN offset_data.offset_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.offset_val IS 'オフセット値';


--
-- Name: COLUMN offset_data.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN offset_data.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN offset_data.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN offset_data.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN offset_data.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN offset_data.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN offset_data.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN offset_data.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN offset_data.upd_tstp IS '更新タイムスタンプ';


--
-- Name: oid_mng_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE oid_mng_mst (
    ctrler_physical_id character varying(128) NOT NULL,
    oid_generate_mode character varying(4) NOT NULL,
    numbered_oid character varying(128) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE oid_mng_mst OWNER TO pfbiz;

--
-- Name: TABLE oid_mng_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE oid_mng_mst IS 'Object ID manage master
(OID管理マスタ)';


--
-- Name: COLUMN oid_mng_mst.ctrler_physical_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.ctrler_physical_id IS 'コントローラ物理ID';


--
-- Name: COLUMN oid_mng_mst.oid_generate_mode; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.oid_generate_mode IS 'ID生成モード';


--
-- Name: COLUMN oid_mng_mst.numbered_oid; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.numbered_oid IS '発行済OID';


--
-- Name: COLUMN oid_mng_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN oid_mng_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN oid_mng_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN oid_mng_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN oid_mng_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN oid_mng_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN oid_mng_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN oid_mng_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN oid_mng_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: period_measure_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE period_measure_data (
    facl_id character varying(128) NOT NULL,
    property_id character varying(128) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    data_collect_interval integer,
    measure_val character varying(128)
);


ALTER TABLE period_measure_data OWNER TO pfbiz;

--
-- Name: TABLE period_measure_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE period_measure_data IS 'Latest periodical collection measurement data
(定期収集最新データ)';


--
-- Name: COLUMN period_measure_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data.facl_id IS '設備ID';


--
-- Name: COLUMN period_measure_data.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data.property_id IS 'プロパティID';


--
-- Name: COLUMN period_measure_data.data_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data.data_datetime IS '計測日時';


--
-- Name: COLUMN period_measure_data.data_collect_interval; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data.data_collect_interval IS 'データ収集間隔';


--
-- Name: COLUMN period_measure_data.measure_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data.measure_val IS '計測値';


--
-- Name: period_measure_data_history; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE period_measure_data_history (
    facl_id character varying(128) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    property_id character varying(128) NOT NULL,
    data_collect_interval integer NOT NULL,
    measure_val character varying(128),
    device_id character varying(128) NOT NULL,
    device_attr_key_1 character varying(128),
    device_attr_key_2 character varying(128),
    device_attr_key_3 character varying(128),
    device_attr_key_4 character varying(128),
    device_attr_key_5 character varying(128),
    measure_val_physical character varying(128)
);


ALTER TABLE period_measure_data_history OWNER TO pfbiz;

--
-- Name: TABLE period_measure_data_history; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE period_measure_data_history IS 'Periodical collection measurement data
(定期収集データ)';


--
-- Name: COLUMN period_measure_data_history.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.facl_id IS '設備ID';


--
-- Name: COLUMN period_measure_data_history.data_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.data_datetime IS '計測日時';


--
-- Name: COLUMN period_measure_data_history.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.property_id IS 'プロパティID';


--
-- Name: COLUMN period_measure_data_history.data_collect_interval; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.data_collect_interval IS 'データ収集間隔';


--
-- Name: COLUMN period_measure_data_history.measure_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.measure_val IS '計測値';


--
-- Name: COLUMN period_measure_data_history.device_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.device_id IS '機器ID';


--
-- Name: COLUMN period_measure_data_history.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN period_measure_data_history.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN period_measure_data_history.device_attr_key_3; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.device_attr_key_3 IS '機器属性キー3';


--
-- Name: COLUMN period_measure_data_history.device_attr_key_4; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.device_attr_key_4 IS '機器属性キー4';


--
-- Name: COLUMN period_measure_data_history.device_attr_key_5; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.device_attr_key_5 IS '機器属性キー5';


--
-- Name: COLUMN period_measure_data_history.measure_val_physical; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN period_measure_data_history.measure_val_physical IS '計測値(物理)';


--
-- Name: pre_alarm_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE pre_alarm_data (
    facl_id character varying(128) NOT NULL,
    occur_datetime timestamp without time zone NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    property_id character varying(128),
    measure_val character varying(128) NOT NULL,
    device_id character varying(128) NOT NULL,
    device_attr_key_1 character varying(128) NOT NULL,
    device_attr_key_2 character varying(128) NOT NULL,
    device_attr_key_3 character varying(128)
);


ALTER TABLE pre_alarm_data OWNER TO pfbiz;

--
-- Name: TABLE pre_alarm_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE pre_alarm_data IS 'Pre-alarm bind Data
(プレトリップ付加情報)';


--
-- Name: COLUMN pre_alarm_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN pre_alarm_data.facl_id IS '設備ID';


--
-- Name: COLUMN pre_alarm_data.occur_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN pre_alarm_data.occur_datetime IS '発生日時';


--
-- Name: COLUMN pre_alarm_data.data_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN pre_alarm_data.data_datetime IS '計測日時';


--
-- Name: COLUMN pre_alarm_data.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN pre_alarm_data.property_id IS 'プロパティID';


--
-- Name: COLUMN pre_alarm_data.measure_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN pre_alarm_data.measure_val IS '計測値';


--
-- Name: COLUMN pre_alarm_data.device_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN pre_alarm_data.device_id IS '機器ID';


--
-- Name: COLUMN pre_alarm_data.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN pre_alarm_data.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN pre_alarm_data.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN pre_alarm_data.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN pre_alarm_data.device_attr_key_3; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN pre_alarm_data.device_attr_key_3 IS '機器属性キー3';


--
-- Name: remote_ctr_data; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE remote_ctr_data (
    seq_no integer NOT NULL,
    facl_id character varying(128) NOT NULL,
    ctr_property_id character varying(32),
    proc_datetime timestamp without time zone NOT NULL,
    appl_id character varying(10) NOT NULL,
    exec_class_nm character varying(60) NOT NULL,
    exec_method_nm character varying(60) NOT NULL,
    process_section character varying(4) NOT NULL,
    ctr_set_status character varying(4) NOT NULL,
    ctr_set_value character varying(128),
    err_message character varying(128)
);


ALTER TABLE remote_ctr_data OWNER TO pfbiz;

--
-- Name: TABLE remote_ctr_data; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE remote_ctr_data IS 'Remote control data
(遠隔制御データ)';


--
-- Name: COLUMN remote_ctr_data.seq_no; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.seq_no IS '連番';


--
-- Name: COLUMN remote_ctr_data.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.facl_id IS '設備ID';


--
-- Name: COLUMN remote_ctr_data.ctr_property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.ctr_property_id IS '制御プロパティID';


--
-- Name: COLUMN remote_ctr_data.proc_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.proc_datetime IS '処理日時';


--
-- Name: COLUMN remote_ctr_data.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.appl_id IS 'アプリID';


--
-- Name: COLUMN remote_ctr_data.exec_class_nm; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.exec_class_nm IS '実行クラス名';


--
-- Name: COLUMN remote_ctr_data.exec_method_nm; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.exec_method_nm IS '実行メソッド名';


--
-- Name: COLUMN remote_ctr_data.process_section; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.process_section IS '処理区分';


--
-- Name: COLUMN remote_ctr_data.ctr_set_status; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.ctr_set_status IS '制御設定ステータス';


--
-- Name: COLUMN remote_ctr_data.ctr_set_value; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.ctr_set_value IS '制御設定値';


--
-- Name: COLUMN remote_ctr_data.err_message; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN remote_ctr_data.err_message IS 'エラーメッセージ';


--
-- Name: remote_ctr_data_seq_no_seq; Type: SEQUENCE; Schema: public; Owner: pfbiz
--

CREATE SEQUENCE remote_ctr_data_seq_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE remote_ctr_data_seq_no_seq OWNER TO pfbiz;

--
-- Name: remote_ctr_data_seq_no_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pfbiz
--

ALTER SEQUENCE remote_ctr_data_seq_no_seq OWNED BY remote_ctr_data.seq_no;


--
-- Name: site_mst; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE site_mst (
    appl_id character varying(10) NOT NULL,
    cstmer_id character varying(64) NOT NULL,
    site_id character varying(64) NOT NULL,
    del_flg integer DEFAULT 0 NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE site_mst OWNER TO pfbiz;

--
-- Name: TABLE site_mst; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE site_mst IS 'Site master
(施設マスタ)';


--
-- Name: COLUMN site_mst.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.appl_id IS 'アプリID';


--
-- Name: COLUMN site_mst.cstmer_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.cstmer_id IS '顧客ID';


--
-- Name: COLUMN site_mst.site_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.site_id IS '施設ID';


--
-- Name: COLUMN site_mst.del_flg; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.del_flg IS '削除フラグ';


--
-- Name: COLUMN site_mst.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN site_mst.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN site_mst.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN site_mst.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN site_mst.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN site_mst.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN site_mst.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN site_mst.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN site_mst.upd_tstp IS '更新タイムスタンプ';


--
-- Name: status_info; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE status_info (
    facl_id character varying(128) NOT NULL,
    property_id character varying(128) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    measure_val character varying(128)
);


ALTER TABLE status_info OWNER TO pfbiz;

--
-- Name: TABLE status_info; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE status_info IS 'Latest Status information data
(状態情報データ)';


--
-- Name: COLUMN status_info.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info.facl_id IS '設備ID';


--
-- Name: COLUMN status_info.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info.property_id IS 'プロパティID';


--
-- Name: COLUMN status_info.data_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info.data_datetime IS '計測日時';


--
-- Name: COLUMN status_info.measure_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info.measure_val IS '計測値';


--
-- Name: status_info_history; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE status_info_history (
    facl_id character varying(128) NOT NULL,
    data_datetime timestamp without time zone NOT NULL,
    property_id character varying(128) NOT NULL,
    measure_val character varying(128),
    device_id character varying(128) NOT NULL,
    device_attr_key_1 character varying(128),
    device_attr_key_2 character varying(128),
    device_attr_key_3 character varying(128),
    measure_val_physical character varying(128)
);


ALTER TABLE status_info_history OWNER TO pfbiz;

--
-- Name: TABLE status_info_history; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE status_info_history IS 'Status information history data
(状態情報履歴データ)';


--
-- Name: COLUMN status_info_history.facl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info_history.facl_id IS '設備ID';


--
-- Name: COLUMN status_info_history.data_datetime; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info_history.data_datetime IS '計測日時';


--
-- Name: COLUMN status_info_history.property_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info_history.property_id IS 'プロパティID';


--
-- Name: COLUMN status_info_history.measure_val; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info_history.measure_val IS '計測値';


--
-- Name: COLUMN status_info_history.device_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info_history.device_id IS '機器ID';


--
-- Name: COLUMN status_info_history.device_attr_key_1; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info_history.device_attr_key_1 IS '機器属性キー1';


--
-- Name: COLUMN status_info_history.device_attr_key_2; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info_history.device_attr_key_2 IS '機器属性キー2';


--
-- Name: COLUMN status_info_history.device_attr_key_3; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info_history.device_attr_key_3 IS '機器属性キー3';


--
-- Name: COLUMN status_info_history.measure_val_physical; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN status_info_history.measure_val_physical IS '計測値(物理)';


--
-- Name: sys_param; Type: TABLE; Schema: public; Owner: pfbiz; Tablespace: 
--

CREATE TABLE sys_param (
    appl_id character varying(10) NOT NULL,
    param_id character varying(128) NOT NULL,
    param_value character varying(256) NOT NULL,
    reg_appl_id character varying(10) NOT NULL,
    reg_usr_id character varying(32),
    reg_prgm_id character varying(128) NOT NULL,
    reg_tstp timestamp without time zone NOT NULL,
    upd_appl_id character varying(10) NOT NULL,
    upd_usr_id character varying(32),
    upd_prgm_id character varying(128) NOT NULL,
    upd_tstp timestamp without time zone NOT NULL
);


ALTER TABLE sys_param OWNER TO pfbiz;

--
-- Name: TABLE sys_param; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON TABLE sys_param IS 'System Parameter
(システムパラメータ)';


--
-- Name: COLUMN sys_param.appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.appl_id IS 'アプリID';


--
-- Name: COLUMN sys_param.param_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.param_id IS 'パラメータID';


--
-- Name: COLUMN sys_param.param_value; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.param_value IS 'パラメータ設定値';


--
-- Name: COLUMN sys_param.reg_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.reg_appl_id IS '登録アプリID';


--
-- Name: COLUMN sys_param.reg_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.reg_usr_id IS '登録ユーザID';


--
-- Name: COLUMN sys_param.reg_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.reg_prgm_id IS '登録プログラムID';


--
-- Name: COLUMN sys_param.reg_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.reg_tstp IS '登録タイムスタンプ';


--
-- Name: COLUMN sys_param.upd_appl_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.upd_appl_id IS '更新アプリID';


--
-- Name: COLUMN sys_param.upd_usr_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.upd_usr_id IS '更新ユーザID';


--
-- Name: COLUMN sys_param.upd_prgm_id; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.upd_prgm_id IS '更新プログラムID';


--
-- Name: COLUMN sys_param.upd_tstp; Type: COMMENT; Schema: public; Owner: pfbiz
--

COMMENT ON COLUMN sys_param.upd_tstp IS '更新タイムスタンプ';


--
-- Name: seq_no; Type: DEFAULT; Schema: public; Owner: pfbiz
--

ALTER TABLE ONLY remote_ctr_data ALTER COLUMN seq_no SET DEFAULT nextval('remote_ctr_data_seq_no_seq'::regclass);


--
-- Name: aggr_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY aggr_data
    ADD CONSTRAINT aggr_data_pkey PRIMARY KEY (facl_id, aggr_item_cd, data_datetime);


--
-- Name: aggr_item_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY aggr_item_mst
    ADD CONSTRAINT aggr_item_mst_pkey PRIMARY KEY (model_id, aggr_item_cd);


--
-- Name: aggr_mng_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY aggr_mng_mst
    ADD CONSTRAINT aggr_mng_mst_pkey PRIMARY KEY (appl_id, cstmer_id, aggr_item_cd);


--
-- Name: aggr_queue_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY aggr_queue_data
    ADD CONSTRAINT aggr_queue_data_pkey PRIMARY KEY (facl_id, property_id, aggr_item_cd, target_aggr_ctg_cd, data_datetime);


--
-- Name: aircon_indoor_unit_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY aircon_indoor_unit
    ADD CONSTRAINT aircon_indoor_unit_pkey PRIMARY KEY (ctrler_logical_id, machine_structure_datetime, connect_type, connect_number, connect_idu_addr, refrig_circuit_grp_no);


--
-- Name: aircon_outdoor_unit_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY aircon_outdoor_unit
    ADD CONSTRAINT aircon_outdoor_unit_pkey PRIMARY KEY (ctrler_logical_id, machine_structure_datetime, connect_type, connect_number, refrig_circuit_grp_no, refrig_circuit_grp_odu_id);


--
-- Name: alarm_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY alarm_data
    ADD CONSTRAINT alarm_data_pkey PRIMARY KEY (facl_id, occur_datetime);


--
-- Name: alarm_mng_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY alarm_mng_mst
    ADD CONSTRAINT alarm_mng_mst_pkey PRIMARY KEY (appl_id, cstmer_id);


--
-- Name: appl_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY appl_mst
    ADD CONSTRAINT appl_mst_pkey PRIMARY KEY (appl_id);


--
-- Name: cd_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY cd_mst
    ADD CONSTRAINT cd_mst_pkey PRIMARY KEY (cd_id, cd);


--
-- Name: characteristic_process_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY characteristic_process_mst
    ADD CONSTRAINT characteristic_process_mst_pkey PRIMARY KEY (device_attr_key_1, device_attr_key_2);


--
-- Name: cstmer_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY cstmer_mst
    ADD CONSTRAINT cstmer_mst_pkey PRIMARY KEY (appl_id, cstmer_id);


--
-- Name: ctr_grp_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY ctr_grp_mst
    ADD CONSTRAINT ctr_grp_mst_pkey PRIMARY KEY (appl_id, cstmer_id, site_id, ctr_group_id, facl_id);


--
-- Name: ctr_item_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY ctr_item_mst
    ADD CONSTRAINT ctr_item_mst_pkey PRIMARY KEY (ctr_property_id);


--
-- Name: ctr_item_nm_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY ctr_item_nm_mst
    ADD CONSTRAINT ctr_item_nm_mst_pkey PRIMARY KEY (ctr_property_id, lang_cd);


--
-- Name: ctr_schedule_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY ctr_schedule_data
    ADD CONSTRAINT ctr_schedule_data_pkey PRIMARY KEY (facl_id, proc_datetime, ctr_property_id);


--
-- Name: ctrler_auth_info_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY ctrler_auth_info
    ADD CONSTRAINT ctrler_auth_info_pkey PRIMARY KEY (ctrler_physical_id);


--
-- Name: ctrler_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY ctrler_mst
    ADD CONSTRAINT ctrler_mst_pkey PRIMARY KEY (ctrler_physical_id);


--
-- Name: device_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY device_mst
    ADD CONSTRAINT device_mst_pkey PRIMARY KEY (device_id);


--
-- Name: device_structure_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY device_structure
    ADD CONSTRAINT device_structure_pkey PRIMARY KEY (ctrler_logical_id, machine_structure_datetime);


--
-- Name: distribute_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY distribute_data
    ADD CONSTRAINT distribute_data_pkey PRIMARY KEY (appl_id, cstmer_id, distribute_grp_id, facl_id, aggr_ctg_cd, data_datetime);


--
-- Name: distribute_grp_detail_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY distribute_grp_detail_mst
    ADD CONSTRAINT distribute_grp_detail_mst_pkey PRIMARY KEY (appl_id, cstmer_id, distribute_grp_id, valid_period_startdate, facl_id);


--
-- Name: distribute_grp_meter_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY distribute_grp_meter_mst
    ADD CONSTRAINT distribute_grp_meter_mst_pkey PRIMARY KEY (appl_id, cstmer_id, distribute_grp_id, valid_period_startdate, seq_no);


--
-- Name: distribute_grp_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY distribute_grp_mst
    ADD CONSTRAINT distribute_grp_mst_pkey PRIMARY KEY (appl_id, cstmer_id, distribute_grp_id, valid_period_startdate);


--
-- Name: event_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY event_data
    ADD CONSTRAINT event_data_pkey PRIMARY KEY (facl_id, data_datetime, property_id);


--
-- Name: facl_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY facl_mst
    ADD CONSTRAINT facl_mst_pkey PRIMARY KEY (facl_id);


--
-- Name: facl_property_info_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY facl_property_info
    ADD CONSTRAINT facl_property_info_pkey PRIMARY KEY (ctrler_logical_id, machine_structure_datetime, facl_id, structure_property_id);


--
-- Name: facl_property_item_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY facl_property_item_mst
    ADD CONSTRAINT facl_property_item_mst_pkey PRIMARY KEY (project_ctg_cd, device_attr_key_1, device_attr_key_2, device_attr_key_3);


--
-- Name: fault_diagnosis_result_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY fault_diagnosis_result
    ADD CONSTRAINT fault_diagnosis_result_pkey PRIMARY KEY (facl_id, occur_datetime);


--
-- Name: inst_ctrler_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY inst_ctrler_mst
    ADD CONSTRAINT inst_ctrler_mst_pkey PRIMARY KEY (appl_id, cstmer_id, site_id, ctrler_logical_id, ctrler_physical_id, valid_period_startdate);


--
-- Name: inst_facl_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY inst_facl_mst
    ADD CONSTRAINT inst_facl_mst_pkey PRIMARY KEY (facl_id, device_id, valid_period_startdate);


--
-- Name: inst_facl_temp_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY inst_facl_temp_mst
    ADD CONSTRAINT inst_facl_temp_mst_pkey PRIMARY KEY (ctrler_logical_id, machine_structure_datetime, physical_oid);


--
-- Name: m2mdata_convert_class_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY m2mdata_convert_class_mst
    ADD CONSTRAINT m2mdata_convert_class_mst_pkey PRIMARY KEY (m2mpf_cd, data_category_code, device_attr_key_1, device_attr_key_2);


--
-- Name: measure_item_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY measure_item_mst
    ADD CONSTRAINT measure_item_mst_pkey PRIMARY KEY (property_id);


--
-- Name: measure_item_nm_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY measure_item_nm_mst
    ADD CONSTRAINT measure_item_nm_mst_pkey PRIMARY KEY (property_id, lang_cd);


--
-- Name: measure_mng_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY measure_mng_mst
    ADD CONSTRAINT measure_mng_mst_pkey PRIMARY KEY (appl_id, cstmer_id, property_id);


--
-- Name: measure_val_convert_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY measure_val_convert_mst
    ADD CONSTRAINT measure_val_convert_mst_pkey PRIMARY KEY (model_id, property_id);


--
-- Name: meter_unit_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY meter_unit
    ADD CONSTRAINT meter_unit_pkey PRIMARY KEY (ctrler_logical_id, machine_structure_datetime, connect_type, adapter_number, port_number);


--
-- Name: model_ctg_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY model_ctg_mst
    ADD CONSTRAINT model_ctg_mst_pkey PRIMARY KEY (model_ctg_cd);


--
-- Name: model_ctg_nm_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY model_ctg_nm_mst
    ADD CONSTRAINT model_ctg_nm_mst_pkey PRIMARY KEY (model_ctg_cd, lang_cd);


--
-- Name: model_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY model_mst
    ADD CONSTRAINT model_mst_pkey PRIMARY KEY (model_id);


--
-- Name: model_nm_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY model_nm_mst
    ADD CONSTRAINT model_nm_mst_pkey PRIMARY KEY (model_id, lang_cd);


--
-- Name: model_spcf_ctr_item_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY model_spcf_ctr_item_mst
    ADD CONSTRAINT model_spcf_ctr_item_mst_pkey PRIMARY KEY (model_id, ctr_property_id);


--
-- Name: model_spcf_measure_item_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY model_spcf_measure_item_mst
    ADD CONSTRAINT model_spcf_measure_item_mst_pkey PRIMARY KEY (model_id, property_id);


--
-- Name: offset_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY offset_data
    ADD CONSTRAINT offset_data_pkey PRIMARY KEY (facl_id, offset_datetime, property_id);


--
-- Name: oid_mng_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY oid_mng_mst
    ADD CONSTRAINT oid_mng_mst_pkey PRIMARY KEY (ctrler_physical_id, oid_generate_mode);


--
-- Name: period_measure_data_history_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY period_measure_data_history
    ADD CONSTRAINT period_measure_data_history_pkey PRIMARY KEY (facl_id, data_datetime, property_id, data_collect_interval);


--
-- Name: period_measure_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY period_measure_data
    ADD CONSTRAINT period_measure_data_pkey PRIMARY KEY (facl_id, property_id);


--
-- Name: pre_alarm_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY pre_alarm_data
    ADD CONSTRAINT pre_alarm_data_pkey PRIMARY KEY (facl_id, occur_datetime);


--
-- Name: remote_ctr_data_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY remote_ctr_data
    ADD CONSTRAINT remote_ctr_data_pkey PRIMARY KEY (seq_no);


--
-- Name: site_mst_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY site_mst
    ADD CONSTRAINT site_mst_pkey PRIMARY KEY (appl_id, cstmer_id, site_id);


--
-- Name: status_info_history_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY status_info_history
    ADD CONSTRAINT status_info_history_pkey PRIMARY KEY (facl_id, data_datetime, property_id);


--
-- Name: status_info_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY status_info
    ADD CONSTRAINT status_info_pkey PRIMARY KEY (facl_id, property_id);


--
-- Name: sys_param_pkey; Type: CONSTRAINT; Schema: public; Owner: pfbiz; Tablespace: 
--

ALTER TABLE ONLY sys_param
    ADD CONSTRAINT sys_param_pkey PRIMARY KEY (appl_id, param_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: root
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM root;
GRANT ALL ON SCHEMA public TO root;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

