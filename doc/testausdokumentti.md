##Kattavuus

Testasin automaattisesti suurimman osan kaikesta mikä ei liittinyt jotenkin graafiseen käyttöliittymään. Lisäksi GameTest-luokassa on kommentuituna testi joka nostaa pitissä game ja util pakettien yhteisen rivikattavuuden 97 prosenttiin ja mutaatiokattavuuden 94 prosenttiin. Kuitenkin kun tämä testi on päällä, kestää pit raportin teko n. 3 minuuttia. Tämä testi siis testaa pelin alustamista.

Normaalit testit antavat game- ja utilpakkauksien yhteisrivikattavuudeksi 90% ja mutaatiokattavuudeksi 93% ja tämä kattaa käytännössä kaiken pelilogiikan ja tiedostojen luvun.

##Testaus
Näin jälkeenpäin mietittynä aika iso osa ohjelmakehityksen testeistä oli enemmänkin integraatiotestejä kuin yksikkötestejä. Kun sain peruslogiikan toimimaan aloin pyörittämään mukana testiä joka pelasi aina yhden tason läpi. En aluksi ehkä tajunnut mennä tarpeeksi pienelle tasolle yksikkötesteissä ja tämä johti enemmäkin integraatiotesteihin. 


Graafinen käyttöliittymä on testattu käsin pelaamalla peliä.

##Bugit

###Frame = null bugi (korjattu(?))

GUI-luokan addMenuPanel() metodissa tapahtuu outoja ainakin joillain koneilla. Jostain syystä on mahdollista että frame on null kun frame.getContentPane().add() kutsutaan. En ole keksinyt syytä tähän. En myöskään tajua miksi Thread.sleep() toimii ainakin workaroundina bugiin. Kuitenkin ohjelma toimii sleepin kanssa koneella millä tuli aina null pointer exception ilman sleeppiä.

