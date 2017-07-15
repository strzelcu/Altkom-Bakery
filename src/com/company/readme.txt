Klasa utilities.Configuration pozwala na dostosowanie opóźnień czasowych operacji odbioru i
realizacji zamówień w celu przetestowania działania wielowątkowości, poziomu logowania
oraz kilku innych parametrów. Wszystkie parametry opisane są w pliku.

Zakładam, że aplikacja będzie symulacją pracy cukierni więc napiszę aplikację tak,
aby zaprezentować nabyte umiejętności ;-) (Zbyt ambitne podejście mnie zablokowało...)

Zakładam, że cukiernia jest w stanie wykonać zdefiniowaną ilość zamówień na dobę.
W razie rozrostu cukierni próg zamówień można zwiększyć. Po osiągnięciu progu
piekarnia zamyka dzień i rozpoczyna wyrób wypieków. Po wykonaniu wszystkich
wypieków piekarnia otwiera kolejny dzień a klienci odbierają wykonane zamówienia,
o których zostali poinformowani.

Zakładam, że klient przy zamówieniu deklaruje metodę odbioru dla całego zamówienia,
ponieważ zapłata za zamówienie połowy kwoty na miejscu i połowy kwoty przy dowozie
jest problematyczna przy rozliczaniu zapłaty. Rozwiązanie jest rozwojowe, ponieważ
można osobo zintegrować system drukowania zamówień odbieranych na miejscu i zamówień
dowożonych przez kierowców jak również prowadzić statystyki zamówień dowożonych
i odbieranych na miejscu w celu rozwinięcia sieci dowozu.

W związku z tym, że firma została kupiona przez fundusz typu Venture Capital,
zakładam, że piekarnia produkuje wypieki na skalę przemysłową przez cały tydzień i tylko
dla klientów biznesowych, którzy są zobowiązani odebrać osobiście zamówienie lub
ich zamówienie zostanie dowiezione transportem cukierni. Zakładam również,
że klienci biznesowi udostępniają numer telefonu i email w celu poinformowania
o zrealizowanym zamówieniu.

Zakładam, że aplikacja ma obsługiwać tylko system zamówień. W przyszłości aplikacja
może zostać rozszerzona o funkcjonalności pobierające i sprawdzające płatności od klientów.
Fundusz Venture Capital będzie otrzymywał przed otwarciem kolejnego dnia szczegółowy raport
działań cukierni wraz z podsumowaniem dziennych szacowanych przychodów.