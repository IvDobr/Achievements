# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table achievement (
  ach_id                    integer not null,
  ach_user_id               integer,
  ach_title                 varchar(255),
  ach_date                  timestamp,
  ach_cat                   varchar(255),
  ach_long_cat              varchar(255),
  ach_dop                   VARCHAR(4095),
  ach_comment               VARCHAR(4095),
  ach_prem                  integer,
  ach_stip                  integer,
  constraint pk_achievement primary key (ach_id))
;

create table faculty (
  fcl_id                    integer not null,
  fcl_title                 varchar(255),
  fcl_top_moder             integer,
  fcl_moder                 integer,
  fcl_under_moder           integer,
  fcl_adress                VARCHAR(2047),
  constraint uq_faculty_fcl_title unique (fcl_title),
  constraint pk_faculty primary key (fcl_id))
;

create table long_cat (
  long_id                   varchar(255) not null,
  long_title                VARCHAR(4095),
  parent_stip               integer,
  constraint pk_long_cat primary key (long_id))
;

create table stip (
  stip_id                   integer not null,
  stip_title                varchar(255),
  constraint uq_stip_stip_title unique (stip_title),
  constraint pk_stip primary key (stip_id))
;

create table user (
  user_id                   integer not null,
  user_login                varchar(255),
  user_first_name           varchar(255),
  user_last_name            varchar(255),
  user_pass                 varchar(255),
  user_faculty              integer,
  user_reg                  timestamp,
  user_stip                 integer,
  user_status               boolean,
  user_group                varchar(255),
  constraint uq_user_user_login unique (user_login),
  constraint pk_user primary key (user_id))
;

create sequence achievement_seq;

create sequence faculty_seq;

create sequence long_cat_seq;

create sequence stip_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists achievement;

drop table if exists faculty;

drop table if exists long_cat;

drop table if exists stip;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists achievement_seq;

drop sequence if exists faculty_seq;

drop sequence if exists long_cat_seq;

drop sequence if exists stip_seq;

drop sequence if exists user_seq;

