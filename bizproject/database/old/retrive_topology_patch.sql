
CREATE TABLE distribution_group (
    id bigint DEFAULT nextval('companies_id_seq'::regclass) NOT NULL,
    group_name character varying(225),
    created_time timestamp without time zone DEFAULT ('now'::text)::date,
    customer_id bigint,
    type character varying(100),
    type_measurment character varying(225)
);


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
    logical_oid character varying(255),
    distribution_group_id bigint,
    s_link character varying(225),
    device_model character varying(225),
    facility_id character varying(225),
    centralcontroladdress character varying(225),
    connectionnumber character varying(225),
    mainiduaddress character varying(225),
    refrigcircuitno character(225),
    connectiontype character varying(225),
    connectionaddress character varying(225),
    svg_id bigint
);

CREATE TABLE outdoorunits (
    id bigint NOT NULL,
    centraladdress character varying(255),
    createdby character varying(255),
    creationdate timestamp without time zone DEFAULT now(),
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
    svg_min_longitude numeric(17,14),
    logical_oid character varying(255),
    facility_id character varying(255),
    model_name character varying(255),
    device_model character varying(255),
    refrigcircuitgroupoduid character varying(255),
    refrigcircuitno character varying(255),
    device_name character varying(255),
    connectionnumber character varying(255),
    s_link character varying(255),
    connectiontype bigint,
    parentid bigint,
    refrigerantid integer,
    slinkaddress character varying(128),
    svg_id bigint
);



CREATE TABLE pulse_meter (
    id bigint DEFAULT nextval('adapters_id_seq'::regclass) NOT NULL,
    meter_name character varying(225),
    port_number integer,
    distribution_group_id integer,
    facility_id character varying(250),
    adapters_id integer,
    device_model character varying(225),
    meter_type character varying(45),
    multi_factor character varying(250),
    creationdate character varying(250) DEFAULT now(),
    connection_type character varying(250)
);

ALTER TABLE ONLY indoorunits
    ADD CONSTRAINT fk_distid FOREIGN KEY (distribution_group_id) REFERENCES distribution_group(id);


ALTER TABLE ONLY pulse_meter
    ADD CONSTRAINT fk_distid FOREIGN KEY (distribution_group_id) REFERENCES distribution_group(id); 


ALTER TABLE ONLY distribution_group
    ADD CONSTRAINT distribution_group_pkey PRIMARY KEY (id);
	
ALTER TABLE companies ADD domain character varying(225);


