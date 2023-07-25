--create database basket;
--\c basket

create table equipe(
    id  varchar(20) primary key,
    nom varchar(20) not null
);
create sequence seqequ;

insert into equipe values(nextval('seqequ'), 'Equipe1');
insert into equipe values(nextval('seqequ'), 'Equipe2');

----Joueur
create table joueur(
    id varchar(20) primary key,
    idEq varchar(20) not null,
    nom varchar(20) not null,
    foreign key (idEq) references equipe(id)
);
create sequence seqjou;

insert into joueur values(nextval('seqjou'), 1, 'J1');
insert into joueur values(nextval('seqjou'), 1, 'J2');
insert into joueur values(nextval('seqjou'), 1, 'J3');
insert into joueur values(nextval('seqjou'), 1, 'J4');
insert into joueur values(nextval('seqjou'), 1, 'J5');


insert into joueur values(nextval('seqjou'), 2, 'Ja1');
insert into joueur values(nextval('seqjou'), 2, 'Ja2');
insert into joueur values(nextval('seqjou'), 2, 'Ja3');
insert into joueur values(nextval('seqjou'), 2, 'Ja4');
insert into joueur values(nextval('seqjou'), 2, 'Ja5');



----Matchs
create table matchs(
    id varchar(20) primary key,
    idEq1 varchar(20),
    idEq2 varchar(20),
    foreign key (idEq1) references equipe(id),
    foreign key (idEq2) references equipe(id)
);
create sequence seqmat;


----Actions
create table typeAction(
    id numeric(1) primary key,
    nomAction varchar(20) not null
);

insert into typeAction
values(1,'tir');
-- 0 : tsy maty, 1, 2, 3

insert into typeAction
values(2,'rebond');
-- 0 : defensif, 1 : offensif



insert into typeAction
values(3,'passe');
-- 0 : normal, 1 : passe de

create table actions(
    id varchar(20) primary key,
    idMatch varchar(20) not null,
    idEq varchar(20) not null,
    idJoueur varchar(20) not null,
    idTypeaction numeric(1) not null,
    valeur numeric(1) not null,
    foreign key (idMatch) references matchs(id),
    foreign key (idEq) references equipe(id),
    foreign key (idJoueur) references joueur(id),
    foreign key (idTypeaction) references typeAction(id)
);
create sequence seqact;

-- Stats générales
-- Pour Stats Equipes
-- Pour Stats Joueur
-- Pour possession
create or replace view StatGen as
	select actions.id, actions.idmatch, actions.ideq, actions.idjoueur, joueur.nom, typeaction.nomaction, actions.valeur
		from actions
		join typeaction on actions.idtypeaction = typeaction.id
		join joueur on actions.idjoueur = joueur.id
	group by actions.idmatch, actions.ideq, actions.id, actions.idjoueur, joueur.nom, typeaction.id, actions.valeur
	order by actions.ideq;

-- Pour connaître nb tir 0 d'un joueur
-- select * from statgen
--      where idjoueur = '...'
--      and nomaction = 'tir'
--      and valeur = '0';
-- A l'aide de select de BDDgeneral et return listStatgen.length;

-- Idem pour toutes les stats
-- Inserer dans Statjoueurs dans joueurs


-- Boucler pour tous les joueurs d'une equipe !

--REBOND
-- nb rebonds totaux
-- nb rebonds offensifs
-- nb rebonds defensifs

--PASSE
-- nb passes decisives

-- POSSESSION DU BALLON
create table possessionjoueur(
    id varchar(20) primary key,
    idMatch varchar(20) not null,
    idEq varchar(20) not null,
    idJoueur varchar(20) not null,
    daterecu timestamp not null,
    datenvoi timestamp,
    foreign key (idMatch) references matchs(id),
    foreign key (idEq) references equipe(id),
    foreign key (idJoueur) references joueur(id)
);
create sequence seqpos;



--Requetes poss
create or replace view possGen as
    select id, idmatch, idjoueur, ideq, (datenvoi - daterecu) as possession
        from possessionjoueur
        group by idMatch, idEq, idJoueur, datenvoi, daterecu, id
        order by idMatch, idEq;

--Temps de jeu des dernieres equipes par matchs
create or replace view TempsEq as
    select idmatch, ideq, sum(possession) as tempstotal
       from possGen
       group by ideq, idMatch
       order by idMatch, idEq;

-- Memes chose en secondes
create or replace view TempsEqSec as
    select idmatch, ideq, extract(hours from tempstotal)*3600+extract(minutes from tempstotal)*60+extract(seconds from tempstotal) as tempsSec
           from TempsEq
           group by idMatch, idEq, tempstotal
           order by idMatch, idEq;


-- Reinit
delete from possessionjoueur;
delete from actions;
delete from matchs;

drop sequence seqmat;
drop sequence seqact;
drop sequence seqpos;

create sequence seqmat;
create sequence seqact;
create sequence seqpos;


















