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
  ach_dop                   varchar(255),
  ach_comment               varchar(255),
  ach_prem                  integer,
  ach_stip                  integer,
  constraint pk_achievement primary key (ach_id))
;

create table user (
  user_id                   integer not null,
  user_login                varchar(255),
  user_first_name           varchar(255),
  user_last_name            varchar(255),
  user_pass                 varchar(255),
  user_faculty              varchar(255),
  user_reg                  timestamp,
  user_stip                 varchar(255),
  user_status               boolean,
  user_group                varchar(255),
  constraint uq_user_user_login unique (user_login),
  constraint pk_user primary key (user_id))
;

create sequence achievement_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists achievement;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists achievement_seq;

drop sequence if exists user_seq;

