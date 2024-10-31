CREATE SCHEMA IF NOT EXISTS entities_management;

CREATE SEQUENCE IF NOT EXISTS entities_management.model_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS entity (
    id BIGINT,
    CONSTRAINT pk_entity PRIMARY KEY (id)
);
