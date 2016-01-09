# Aiheen kuvaus ja rakenne

## Aihe
Toteutetaan [Nethackin Sokoban](http://nethack.wikia.com/wiki/Sokoban)-tasot omaksi pieneksi peliksi. Pelissä on tarkoitus manipuloida liikkuvia kivijärkäleitä (tässä laatikoita) ja täyttää niillä kuoppia jotta pelaaja voi edetä tason loppuun. Sovellukseen kuuluu vain tämän ongelmaratkaisuaspektin tekeminen, ei niinkään Nethackin ominaispiirteiden, kuten pelissä huijaamisen tai hirviöiden toteuttaminen.

Tehty peli seuraa Nethackin sääntöjä niissä määrin kuin on tarve. Vinottainen liikkuminen ei ole mahdollista kahden laatikon/seinän välistä, eikä laatikoita voi työntää vinottain.



## Käyttäjät
* Pelaaja

### Pelaajan toiminnot
* Uudet pelin aloitus
  * Pelaaja voi valita haluamansa tason menu-valikosta
* Pelihahmon siirtäminen
* Laatikoiden työntäminen siirtämällä pelihahmoa niitä päin
* Kuoppien täyttäminen työntämällä laatikoita niiden päälle
* Voittaminen pääsemällä maaliin
* Ennenaikainen pelin lopetus
* Tason aloittaminen alusta


## Rakenne

Kun koodissa ja dokumentaatiossa puhutaan mapista tarkoitetaan sillä charactertaulukkoa josta Level muodostetaan. Kun puhutaan levelistä tarkoitetaan Level-oliota joka sisältää taulukon tasolle, mutta siitä on poistettu pelaaja ja laatikot ja niistä on luotu omat oliot.



Game sisältää suurimman osan pelilogiikasta. Game tuntee kerrallaan yhden pelattavan tason (Level). Kun taso luodaan, luodaan myös yksi Player ja tarvittaessa monta Box oliota ja ne liitetään tasoon. Player ja Box käytännössä "elävät" tason päällä. Location-oliota käytetään apuna suuressa osassa koodia joka käyttää taulukkoja koodin luettavuutta varten ja helpottamaan kaksiulottoisen taulukon manipulointia.

Game käyttää myös FileScanneria joka lukee tiedostoista pelattavat mapit. FileScanner taas kutsuu MapValidatoria joka tarkistaa että mapista voidaan muodostaa pelattava taso. Tämä ei takaa että taso olisi mahdollista pelata läpi, vaan ainoastaan että se käynnistyy.

Graafinen käyttöliittymä liitetään peliin. Käyttöliittymä lähettää luetut näppäinpainallukset pelille KeyboardListenerin avulla joka on liitetty käyttöliittymään. Käyttöliittymän näkyvät osat koostuvat Boardista ja MenuPanelista. Boardiin hakee Gamelta tarvittavat tiedot pelikentän piirtämiseen. MenuPanel saa Gamelta tiedot kuinka monta tasoa on ladattuna ja montako siirtoa on käytetty sen hetkisen tason pelaamiseen. MenuPanel kutsuu käyttöliittymän kautta uuden pelin aloittamisen kun tasonvalinta tehdään.
