# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table t_option (
  id                        integer auto_increment not null,
  content                   varchar(255),
  count                     bigint,
  question_id               integer,
  constraint pk_t_option primary key (id))
;

create table t_question (
  id                        integer auto_increment not null,
  title                     varchar(255),
  content                   varchar(255),
  constraint pk_t_question primary key (id))
;

alter table t_option add constraint fk_t_option_question_1 foreign key (question_id) references t_question (id) on delete restrict on update restrict;
create index ix_t_option_question_1 on t_option (question_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table t_option;

drop table t_question;

SET FOREIGN_KEY_CHECKS=1;

