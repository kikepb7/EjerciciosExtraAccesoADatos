-- Tabla albumes
INSERT INTO albumes VALUES (7000, 'Death Magnetic','Metallica','Rock','2008-09-12',10);
INSERT INTO albumes VALUES (7001, 'Devil Came to Me','Dover','Rock','1997-04-21',14);
INSERT INTO albumes VALUES (7002, 'Epica','Kamelot','Metal epico','2003-01-13',13);
INSERT INTO albumes VALUES (7003, 'Animal Instinct','The Cranberries','Pop-Rock','1998-01-03',11);
INSERT INTO albumes VALUES (7004, 'Behind the wheel','Depeche Mode','Electronica','1988-04-23',5);
INSERT INTO albumes VALUES (7005, 'Listen','David Guetta','Electronica','2014-01-01',25);
-- Tabla canciones
INSERT INTO canciones VALUES (8000, 'Death',3,1,7000);
INSERT INTO canciones VALUES (8001, 'undefined',4,5,7000);
INSERT INTO canciones VALUES (8002, 'Serenade',8,2,7001);
INSERT INTO canciones VALUES (8003, 'Devil Came to me',6,4,7001);
INSERT INTO canciones VALUES (8004, 'Black Halo',3,2,7002);
INSERT INTO canciones VALUES (8005, 'Karma',3,6,7002);
INSERT INTO canciones VALUES (8006, 'Listen',7,1,7005);
INSERT INTO canciones VALUES (8007, 'Dangerous',4,6,7005);
INSERT INTO canciones VALUES (8008, 'Zombie',3,1,7003);
INSERT INTO canciones VALUES (8009, 'Decide',5,2,7003);
INSERT INTO canciones VALUES (8010, 'Precious',2,1,7004);
INSERT INTO canciones VALUES (8011, 'Wrong',3,5,7004);
INSERT INTO canciones VALUES (8012, 'Frozen',6,3,7004);
INSERT INTO canciones VALUES (8013, 'One',10,9,7000);
INSERT INTO canciones VALUES (8014, 'Sorrow',7,11,7000);
