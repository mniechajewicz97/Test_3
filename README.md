Zadanie 01:

Dane są trzy pliki tekstowe o nazwach: lekarze.txt, pacjenci.txt, wizyty.txt.
Zawierają one informacje na temat lekarzy, pacjentów i odbytych wizyt domowych.
W każdym z plików dane w wierszu oddzielone są znakami tabulacji.
Plik o nazwie lekarze.txt zawiera informacje na temat lekarzy: numer identyfikacyjny
lekarza, jego nazwisko, imię, specjalność, datę urodzenia, numer NIP i numer PESEL.
Przykład:
23 Kadaj Monika pediatra 1965-03-16 879-122-69-94 65031687654
34 Nowak Anna nefrolog 1965-03-16 879-122-69-94 65031687654
Plik o nazwie pacjenci.txt zawiera dane na temat pacjentów: numer identyfikacyjny
pacjenta, jego nazwisko, imię, numer PESEL i datę urodzenia.
Przykład:
122 Nowakowska Joanna 73050512356 1973-05-05
124 Witkowski Hubert 88030422345 1988-03-04
Plik o nazwie wizyty.txt zawiera informacje na temat domowych wizyt lekarskich
przeprowadzonych przez lekarzy u pacjentów: numer identyfikacyjny lekarza, numer
identyfikacyjny pacjenta oraz datę wizyty lekarskiej przeprowadzonej przez lekarza
u pacjenta.
Przykład:
23 124 2006-12-13
34 122 2007-02-20
Wykorzystując informacje zawarte w plikach wykonaj następujące polecenia:
- znajdź lekarza ktory miał najwięcej wizyt
- znajdź pacjenta który miał najwięcej wizyt
- która specalizacja cieszy się największym powodzeniem?
- którego roku było najwięcej wizyt?
- wypisz top 5 najstarszych lekarzy
- zwroc pacientow ktorzy byli u minumum 5ciu roznych lekarzy
- zwroc lekarzy exclusive - czyli takich ktorzy przyjmowali tylko jednego pacjenta
  rozwiązania zrób w formie metod i klas, oceniane będzie podejście do problemu, poprawność rozwiażania, wytestowanie swojej aplikacji itp.
  powodzenia :)


Zadanie 02:
napisz kod tak aby fragment ponizej dzialal i sie kompilowal. - ale nie mozesz uzywac tablic, list, setow, zadnych kolekcji czy kolejek, ani konkatenowac to w Stringi czy appendowac
w string buildery
//tworzymy klaske String container ktora bedzie mogla przyjmowac tylko Stringi z okreslonym Patternem podanym jako argument.
//podczas tworzenia obiektu nalezy zdefinowac poprawnosc patternu i jesli pattern bedzie "zly- czyli taki ktory sie nie kompiluje" to ma zostac rzucony wyjatek InvalidStringContainerPatternException(badPattern)
//wszystkie wyjatki w programie maja dziedziczyc RuntimeException.
//tu w przykladzie dodajemy kody pocztowe
StringContainer st = new StringContainer("\\d{2}[-]\\d{3}");
st.add("02-495");//git
st.add("01-120");//git
st.add("05-123");//git
st.add("00-000");//git
//st.add("ala ma kota"); //powinno sie wywalic wyjatkiem InvalidStringContainerValueException(badValue)
for(int i=0; i<st.size(); i++){
System.out.println(st.get(i)); //powinno wypisac dodane kody pocztowe
}
st.remove(0);  //usuwa "02-495"
st.remove("00-000"); // usuwa "00-000"
System.out.println("po usunieciu");
for(int i=0; i<st.size(); i++){
System.out.println(st.get(i)); //powinno wypisac dodane kody pocztowe
}
nasza liste mozna tez parametryzowac tak aby nie dalo sie wrzucac powtorzen np:
StringContainer st = new StringContainer("\\d{2}[-]\\d{3}", true); //jakis parametr np: duplicatedNotAllowed - domyslnie false
wtedy np:
st.add("02-495");//git
st.add("02-495");//powinno rzucic wyjatkiem DuplicatedElementOnListException
//kazdy element w StringContainer powinien miec date+czas dodania elementu do niego,
//nalezy zaimplementowac metode:
StringContainer stBetween = st.getDataBetween(dateFrom, dateTo);
//ktora zwroci elementy dodane pomiedzy dateFrom a dateTo
//gdzie dateFrom i dateTo to obiekty LocalDateTime i moga byc nullami.
//dodatkowo pomysl o persystencji StringContainer tj:
st.storeToFile("postalCodes.txt"); // powinno zapisac zawartosc
StringContainer fromFile = StringContainer.fromFile("postalCodes.txt"); // powinno wczytac zawartosc z pliku i "fromFile" musi miec te same dane co "st"


Zadanie 03:
dana jest tablica intow o rozmiarze 200 mln elementow, elementy maja losowe wartosci z pelnego zakresu inta.
- policz ile czasu potrwa liczenie sumy wszystkich elementow uzywajac 1 watku
- policz ile czasu potrwa liczenie sumy wszystkich elementow 2 watkow
- policz ile czasu potrwa liczenie sumy wszystkich elementow 4 watkow
- policz ile czasu potrwa liczenie sumy wszystkich elementow 8 watkow