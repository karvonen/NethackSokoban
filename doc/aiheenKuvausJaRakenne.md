# Aiheen kuvaus ja rakenne

## Aihe
Toteutetaan [Nethackin Sokoban](http://nethack.wikia.com/wiki/Sokoban)-tasot omaksi pieneksi peliksi. Pelissä on tarkoitus manipuloida liikkuvia kivijärkäleitä (tässä laatikoita) ja täyttää niillä kuoppia jotta pelaaja voi edetä tason loppuun. Sovellukseen kuuluu vain tämän ongelmaratkaisuaspektin tekeminen, ei niinkään Nethackin ominaispiirteiden, kuten pelissä huijaamisen tai hirviöiden toteuttaminen.

Tehty peli seuraa Nethackin sääntöjä niissä määrin kuin on tarve. Vinottainen liikkuminen ei ole mahdollista kahden laatikoiden/seinän välistä, eikä laatikoita voi työntää vinottain.



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

