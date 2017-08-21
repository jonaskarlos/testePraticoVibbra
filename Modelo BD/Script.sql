Create table "tb_usuario"
(
	"id_usuario" Bigint NOT NULL,
	"txt_email" Varchar(100) NOT NULL,
	"txt_senha" Varchar(20) NOT NULL,
	"txt_endereco" Varchar(200),
	"txt_cidade" Varchar(100),
	"txt_estado" Varchar(100),
	"txt_pais" Varchar(100),
 primary key ("id_usuario")
) Without Oids;

ALTER TABLE tb_usuario
    OWNER TO avalie; 


Create table "tb_avaliacao"
(
	"id_avaliacao" Bigint NOT NULL,
	"id_usuario" Bigint,
	"txt_velocidade_internet" Varchar(20),
	"flg_internet_aberta" Boolean NOT NULL,
	"txt_senha_internet" Varchar(20),
	"txt_atendimento" Char(1),
	"txt_preco" Char(1),
	"txt_conforto" Char(1),
	"txt_ruido" Char(1),
	"txt_geral" Char(1),
	"id_estabelecimento" Bigint NOT NULL,
 primary key ("id_avaliacao")
) Without Oids;

ALTER TABLE tb_avaliacao
    OWNER TO avalie; 

Create table "tb_tipo_comida"
(
	"id_tipo_comida" Bigint NOT NULL,
	"txt_tipo_comida" Varchar(100) NOT NULL,
 primary key ("id_tipo_comida")
) Without Oids;

ALTER TABLE tb_tipo_comida
    OWNER TO avalie; 

Create table "tb_tipo_bebida"
(
	"id_tipo_bebida" Bigint NOT NULL,
	"txt_tipo_bebida" Varchar(100) NOT NULL,
 primary key ("id_tipo_bebida")
) Without Oids;

ALTER TABLE tb_tipo_bebida
    OWNER TO avalie; 

Create table "tb_avaliacao_comida"
(
	"id_avaliacao_comida" Bigint NOT NULL,
	"id_avaliacao" Bigint NOT NULL,
	"id_tipo_comida" Bigint NOT NULL,
 primary key ("id_avaliacao_comida")
) Without Oids;

ALTER TABLE tb_avaliacao_comida
    OWNER TO avalie; 

Create table "tb_avaliacao_bebida"
(
	"id_avaliacao_bebida" Bigint NOT NULL,
	"id_avaliacao" Bigint NOT NULL,
	"id_tipo_bebida" Bigint NOT NULL,
 primary key ("id_avaliacao_bebida")
) Without Oids;

ALTER TABLE tb_avaliacao_bebida
    OWNER TO avalie; 

Create table "tb_estabelecimento"
(
	"id_estabelecimento" Bigint NOT NULL,
	"txt_nome" Varchar(100) NOT NULL,
	"txt_endereco" Varchar(100),
	"txt_cidade" Varchar(100) NOT NULL,
	"txt_estado" Varchar(100) NOT NULL,
	"txt_pais" Varchar(100) NOT NULL,
	"txt_tipo" Char(1) NOT NULL,
 primary key ("id_estabelecimento")
) Without Oids;

ALTER TABLE tb_estabelecimento
    OWNER TO avalie; 

/* Create Indexes */


/* Create Foreign Keys */

Alter table "tb_avaliacao" add  foreign key ("id_usuario") references "tb_usuario" ("id_usuario") on update restrict on delete restrict;

Alter table "tb_avaliacao_comida" add  foreign key ("id_avaliacao") references "tb_avaliacao" ("id_avaliacao") on update restrict on delete restrict;

Alter table "tb_avaliacao_bebida" add  foreign key ("id_avaliacao") references "tb_avaliacao" ("id_avaliacao") on update restrict on delete restrict;

Alter table "tb_avaliacao_comida" add  foreign key ("id_tipo_comida") references "tb_tipo_comida" ("id_tipo_comida") on update restrict on delete restrict;

Alter table "tb_avaliacao_bebida" add  foreign key ("id_tipo_bebida") references "tb_tipo_bebida" ("id_tipo_bebida") on update restrict on delete restrict;

Alter table "tb_avaliacao" add  foreign key ("id_estabelecimento") references "tb_estabelecimento" ("id_estabelecimento") on update restrict on delete restrict;


CREATE SEQUENCE sq_usuario
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE sq_usuario
    OWNER TO avalie;
    
CREATE SEQUENCE sq_avaliacao
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE sq_avaliacao
    OWNER TO avalie;    
    
CREATE SEQUENCE sq_avaliacao_bebida
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE sq_avaliacao_bebida
    OWNER TO avalie;  
    
CREATE SEQUENCE sq_avaliacao_comida
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE sq_avaliacao_comida
    OWNER TO avalie;        

 
 CREATE SEQUENCE sq_estabelecimento
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE sq_estabelecimento
    OWNER TO avalie;   

 
 CREATE SEQUENCE sq_tipo_bebida
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE sq_tipo_bebida
    OWNER TO avalie;  
    
    

 CREATE SEQUENCE sq_tipo_comida
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE sq_tipo_comida
    OWNER TO avalie; 