##Kattavuus

Testasin automaattisesti suurimman osan kaikesta mikä ei liittinyt jotenkin graafiseen käyttöliittymään. Lisäksi GameTest-luokassa on kommentuituna testi joka nostaa pitissä game ja util pakettien yhteisen rivikattavuuden 95 prosenttiin ja mutaatiokattavuuden 93 prosenttiin. Kuitenkin kun tämä testi on päällä, kestää pit raportin teko n. 3 minuuttia. Tämä testi siis testaa pelin alustamista.

Normaalit testit antavat game- ja utilpakkauksien yhteisrivikattavuudeksi 89% ja mutaatiokattavuudeksi 92% ja tämä kattaa käytännössä kaiken pelilogiikan ja tiedostojen luvun.

##Testaus

Näin jälkikäteen mietittynä aika iso osa ohjelmakehityksen testeistä oli enemmänkin integraatiotestejä kuin yksikkötestejä. Kun sain peruslogiikan toimimaan aloin pyörittämään mukana myös paria testiä jotka pelasivat aina yhden tason läpi. En aluksi ehkä tajunnut mennä tarpeeksi pienelle tasolle yksikkötesteissä ja tämä johti enemmäkin integraatiotesteihin. 

Graafinen käyttöliittymä on testattu käsin pelaamalla peliä.

##Bugit

###Frame = null bugi (korjattu(?))

GUI-luokan addMenuPanel() metodissa tapahtuu outoja ainakin joillain koneilla. Jostain syystä on mahdollista että frame on null kun frame.getContentPane().add() kutsutaan. En ole keksinyt syytä tähän. En myöskään tajua miksi Thread.sleep() toimii ainakin workaroundina bugiin. Kuitenkin ohjelma toimii sleepin kanssa koneella millä tuli aina null pointer exception ilman sleeppiä.

