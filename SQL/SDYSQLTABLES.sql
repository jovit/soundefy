create table SDYUser(
	sdyuser_ID int identity primary key,
	sdyuser_name varchar(100), 
	sdyuser_password varchar(100),
	sdyuser_email varchar(100),
	sdyuser_birthdate date
)

create table SDYMusicGenre(
	sdymusicgenre_ID int identity primary key,
	sdymusicgenre_name varchar(30)
)

create table SDYMusicArtist(
	sdymusicartist_ID int identity primary key,
	sdymusicartist_name varchar(50)
)

create table SDYSong(
	sdysong_ID int identity primary key,
	sdysong_name varchar(50),
	sdysong_year int,
	sdysong_artistID int,
	sdysong_genreID int,
	constraint fk_sdysong_artistID foreign key (sdysong_artistID) references SDYMusicArtist(sdymusicartist_ID),
	constraint fk_sdysong_genreID foreign key (sdysong_genreID) references SDYMusicGenre(sdymusicgenre_ID)
)

create table SDYMusicTab(
	sdymusictab_ID int identity primary key,
	sdymusictab_url varchar(255),
	sdymusictab_songID int,
	constraint fk_sdymusictab_songID foreign key (sdymusictab_songID) references SDYSong(sdysong_ID) 
)

select SDYMusicTab.sdymusictab_ID, SDYSong.sdysong_name from SDYMusicTab, SDYSong where 
SDYMusicTab.sdymusictab_songID = SDYSong.sdysong_ID

insert into SDYMusicGenre values ('Indie')
insert into SDYMusicGenre values ('Rock')
insert into SDYMusicGenre values ('Pop')
insert into SDYMusicGenre values ('Samba')
insert into SDYMusicGenre values ('Funk')
insert into SDYMusicGenre values ('Metal')
insert into SDYMusicGenre values ('Eletronica')
insert into SDYMusicGenre values ('Instrumental')

insert into SDYMusicArtist values ('Pink Floyd')
insert into SDYMusicArtist values ('MC Dudu')

insert into SDYSong values ('Shine on you crazy diamond', 1988, 1, 2)
insert into SDYSong values ('Os pika do verão', 2015, 2, 5)

delete from SDYSong where sdysong_ID = 2

select * from SDYMusicGenre
select * from SDYMusicArtist
select * from SDYSong

insert into SDYMusicTab values ('batatamc', 4)

