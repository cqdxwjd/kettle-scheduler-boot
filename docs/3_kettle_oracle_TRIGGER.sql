CREATE OR REPLACE TRIGGER category_trigger
before insert on K_category for each row
begin
select  SEQ_category.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER DATABASE_TYPE_trigger
before insert on K_DATABASE_TYPE for each row
begin
select  SEQ_DATABASE_TYPE.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER JOB_MONITOR_trigger
before insert on K_JOB_MONITOR for each row
begin
select  SEQ_JOB_MONITOR.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER JOB_RECORD_trigger
before insert on K_JOB_RECORD for each row
begin
select  SEQ_JOB_RECORD.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER JOB_trigger
before insert on K_JOB for each row
begin
select  SEQ_JOB.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER QUARTZ_trigger
before insert on K_QUARTZ for each row
begin
select  SEQ_QUARTZ.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER REPOSITORY_trigger
before insert on K_REPOSITORY for each row
begin
select  SEQ_REPOSITORY.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER TRANS_MONITOR_trigger
before insert on K_TRANS_MONITOR for each row
begin
select  SEQ_TRANS_MONITOR.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER TRANS_RECORD_trigger
before insert on K_TRANS_RECORD for each row
begin
select  SEQ_TRANS_RECORD.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER TRANS_trigger
before insert on K_TRANS for each row
begin
select  SEQ_TRANS.Nextval into:new.id from dual;
end;

CREATE OR REPLACE TRIGGER USER_trigger
before insert on K_USER for each row
begin
select  SEQ_USER.Nextval into:new.id from dual;
end;
