ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = cac;


CREATE TABLE cac.stat_graphic(
    id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0 START WITH 0 CACHE 20 CONSTRAINT stat_graphic_pk PRIMARY KEY,
    name varchar2(64) NOT NULL,
    type varchar2(64) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cac.stat_line(
    id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0 START WITH 0 CACHE 20 CONSTRAINT stat_line_pk PRIMARY KEY,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    table_id NUMBER CONSTRAINT stat_line_fk REFERENCES cac.stat_graphic(id)
);

CREATE TABLE cac.stat_point(
    id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0 START WITH 0 CACHE 20 CONSTRAINT stat_point_pk PRIMARY KEY,
    x NUMBER NOT NULL,
    y NUMBER NOT NULL,
    z NUMBER NOT NULL,
    table_id NUMBER CONSTRAINT stat_point_table_fk REFERENCES cac.stat_graphic(id),
    line_id NUMBER CONSTRAINT stat_point_line_fk REFERENCES cac.stat_line(id)
);

CREATE TABLE cac.stat_general(
    id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0 START WITH 0 CACHE 20 CONSTRAINT stat_general_pk PRIMARY KEY,
    name VARCHAR2(64) NOT NULL,
    value NUMBER DEFAULT 0 NOT NULL
);

INSERT INTO cac.stat_general VALUES(DEFAULT,'totalAlumnos',5);
INSERT INTO cac.stat_general VALUES(DEFAULT,'totalBasicos',3);
INSERT INTO cac.stat_general VALUES(DEFAULT,'totalIntermedios',1);
INSERT INTO cac.stat_general VALUES(DEFAULT,'totalAsistencias',5);
INSERT INTO cac.stat_general VALUES(DEFAULT,'totalSubmissions',0);
INSERT INTO cac.stat_general VALUES(DEFAULT,'totalSubmissionsCorrectas',0);

INSERT INTO cac.stat_graphic VALUES(0,'IvanRegression','Regresion',DEFAULT);
INSERT INTO cac.stat_line VALUES(0,DEFAULT,0);

INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (0.0, 0.0, 6, 0, NULL);
INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (1707.0, 6.0, 0, 0, NULL);
INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (1936.0, 3.0, 1, 0, NULL);
INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (1718.3333333333333, 2.3333333333333335, 1, 0, NULL);
INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (1726.75, 2.0, 5, 0, NULL);
INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (1515.4, 2.6, 5, 0, NULL);
INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (1461.3333333333333, 3.0, 0, 0, NULL);
INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (1476.4285714285713, 2.5714285714285716, 1, 0, NULL);
INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (1518.125, 2.375, 0, 0, NULL);
INSERT INTO cac.stat_point (x, y, z, table_id, line_id) VALUES (1600.2222222222222, 2.111111111111111, 1, 0, NULL);

COMMIT;