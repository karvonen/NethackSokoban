##Kattavuus

Testasin automaattisesti suurimman osan kaikesta mik� ei liittinyt jotenkin graafiseen k�ytt�liittym��n. Lis�ksi GameTest-luokassa on kommentuituna testi joka nostaa pitiss� game ja util pakettien yhteisen rivikattavuuden 95 prosenttiin ja mutaatiokattavuuden 93 prosenttiin. Kuitenkin kun t�m� testi on p��ll�, kest�� pit raportin teko n. 3 minuuttia. T�m� testi siis testaa pelin alustamista.

Normaalit testit antavat game- ja utilpakkauksien yhteisrivikattavuudeksi 89% ja mutaatiokattavuudeksi 92% ja t�m� kattaa k�yt�nn�ss� kaiken pelilogiikan ja tiedostojen luvun.

##Testaus

N�in j�lkik�teen mietittyn� aika iso osa ohjelmakehityksen testeist� oli enemm�nkin integraatiotestej� kuin yksikk�testej�. Kun sain peruslogiikan toimimaan aloin py�ritt�m��n mukana my�s paria testi� jotka pelasivat aina yhden tason l�pi. En aluksi ehk� tajunnut menn� tarpeeksi pienelle tasolle yksikk�testeiss� ja t�m� johti enemm�kin integraatiotesteihin. 

Graafinen k�ytt�liittym� on testattu k�sin pelaamalla peli�.

##Bugit

###Frame = null bugi (korjattu(?))

GUI-luokan addMenuPanel() metodissa tapahtuu outoja ainakin joillain koneilla. Jostain syyst� on mahdollista ett� frame on null kun frame.getContentPane().add() kutsutaan. En ole keksinyt syyt� t�h�n. En my�sk��n tajua miksi Thread.sleep() toimii ainakin workaroundina bugiin. Kuitenkin ohjelma toimii sleepin kanssa koneella mill� tuli aina null pointer exception ilman sleeppi�.

