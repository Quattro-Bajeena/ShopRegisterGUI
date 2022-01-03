# Aplikacja kasy sklepowej

*Autor:* Mateusz Oleszek

## Funkcjonalność

Aplikacja posiada pewien stan produktów, które można dodać do koszyka aktualnej transakcji. Cały czas jest widoczna obecna wartość wszystkich produktów w koszyku.
Po zakończeniu transakcji zostanie ona dodana do listy zakończonych. Wszystkie przeprowadzone transakcje moża zobaczyć. Można też dodać nowe produkty do katalogu.

## Implementacja

Produkty oraz transakcje są przechowywane w lokalnej bazie SQLite, w pliku shop.db. 

W ramach obiektowegej zasady rozdzielenia obowiązków aplikacja jest podzielona na kilka warstw. W wartswie prezentacji są klasy przechowywujące obiekty okienkowego frameworku Swing które obsługują interakcje użytwonika z aplikacją. Klasa odpowiadająca za główne okno, ShopApp, posiada instancję klasy ShopManager.

Należy ona do wartwy logiki biznesowej. Wszyskie kluczowe operacje są wyabstrachowane jako jej metody. Za to ona posiada obiekt klasy DbRepository. Odpowiada on w całości za interakcje z bazą danych.

## Diagram UML

![diagram](/diagram.png)
