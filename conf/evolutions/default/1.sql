# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  uid                       bigint auto_increment not null,
  email                     varchar(255),
  password                  varchar(255),
  create_time               datetime,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (uid))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

