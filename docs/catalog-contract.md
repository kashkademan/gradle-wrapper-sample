<!-- Карта фиксирует не все данные провайдера, а только то, что реально нужно клиенту. -->

## Сценарий поиска

<!-- Для поиска важно разделить шаблон пути и конкретный пример запроса. -->
- Method: `GET`
- Template path: `/books`
- Query-параметры:
  - `search` — строка поиска
- Success status: `200`
- Content-Type: содержит `application/json`
- Верхнеуровневая форма body: JSON object с пагинацией и массивом книг в `results`

Форма ответа:
    {
      "count": number,
      "next": string | null,
      "previous": string | null,
      "results": [
        {
          "id": number,
          "title": string,
          "authors": [
            {
              "name": string,
              "birth_year": number | null,
              "death_year": number | null
            }
          ],
          "languages": [string],
          "media_type": string,
          "formats": { "<mime-type>": "<url>" },
          "download_count": number
        }
      ]
    }

<!-- Здесь перечислены только поля, которые понадобятся клиенту на следующем шаге. -->
Поля, которые понадобятся клиенту:
- `count`
- `next`
- `previous`
- `results[].id`
- `results[].title`
- `results[].authors[].name`
- `results[].authors[].birth_year`
- `results[].authors[].death_year`
- `results[].languages`
- `results[].media_type`
- `results[].formats` — объект ссылок по MIME type
- `results[].download_count`

## Сценарий деталей

<!-- В details идентификатор приходит в path, а не в query. -->
- Method: `GET`
- Template path: `/books/{id}`
- Path parameter:
  - `id` — идентификатор книги
- Success status: `200`
- Content-Type: содержит `application/json`
- Верхнеуровневая форма body: JSON object одной книги

Форма ответа:
    {
      "id": number,
      "title": string,
      "authors": [
        {
          "name": string,
          "birth_year": number | null,
          "death_year": number | null
        }
      ],
      "summaries": [string],
      "subjects": [string],
      "bookshelves": [string],
      "languages": [string],
      "copyright": boolean,
      "media_type": string,
      "formats": { "<mime-type>": "<url>" },
      "download_count": number
    }

<!-- Для клиентской логики важны не все поля ответа, а только используемые данные карточки книги. -->
Поля, которые понадобятся клиенту:
- `id`
- `title`
- `authors[].name`
- `authors[].birth_year`
- `authors[].death_year`
- `summaries`
- `subjects`
- `bookshelves`
- `languages`
- `media_type`
- `formats` — объект ссылок по MIME type
- `download_count`

## Smoke-набор

<!-- Smoke нужен как быстрый ручной чек: статус, тип ответа и форма тела. -->
1. Поиск: `GET /books?search=pride%20and%20prejudice` → ожидаемый статус `200`, форма ответа — JSON object с `count`, `next`, `previous` и массивом `results`.
2. Детали: `GET /books/1342` → ожидаемый статус `200`, форма ответа — JSON object одной книги с `id`, `title`, `authors`, `summaries`, `languages`, `formats`.
