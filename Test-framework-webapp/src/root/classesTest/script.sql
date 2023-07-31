create table empmodel(
    id serial primary key,
    nom varchar(30),
    prenom varchar(30)
);
insert into empmodel values(default, 'NomEmp1', 'PrenomEmp1');
insert into empmodel values(default, 'NomEmp2', 'PrenomEmp2');
insert into empmodel values(default, 'NomEmp3', 'PrenomEmp3');

create table plat(
    id serial primary key,
    libelle varchar(30)
);
insert into plat values(default, 'Ravitoto');
insert into plat values(default, 'Anana');
insert into plat values(default, 'Henomby');


create table empmodel_plat(
    id serial primary key,
    idempmodel int,
    idplat int,
    date date default now(),
    nomfichier varchar(100),
    FOREIGN KEY (idempmodel) references empmodel(id),
    foreign key (idplat) references plat(id)
);

insert into empmodel_plat values(default, 1, 1, default, 'plat1.txt');
insert into empmodel_plat values(default, 2, 2, default, 'plat2.txt');
insert into empmodel_plat values(default, 2, 1, default, 'plat3.txt');


create view v_empmodel_plat as
select empmodel_plat.id, empmodel_plat.date, empmodel.nom, plat.libelle, empmodel_plat.nomfichier
from empmodel_plat
join empmodel on idempmodel = empmodel.id
join plat on plat.id = empmodel_plat.idplat;