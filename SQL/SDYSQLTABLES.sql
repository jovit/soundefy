create table SDYUser(
	sdyuser_ID int identity primary key,
	sdyuser_name varchar(100), 
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