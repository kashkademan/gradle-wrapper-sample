# ReadLater Starter

Учебный проект курса `Java Server`.

## Requirements
- JDK 25

## Build & Run (macOS / Linux)
./gradlew build
./gradlew run

## Build & Run (Windows)
gradlew.bat build
gradlew.bat run

## Smoke-check
1) GET {{baseUrl}}/health -> 200
2) POST /api/v1/reading-list -> 201 + Location
3) GET /api/v1/reading-list -> 200 + count

## Артефакты
- Postman collection: postman/readlater.postman_collection.json
- Конфиг: src/main/resources/application.properties

## Structure of postmam collection
ReadLater Starter — Local API
├─ Health
│  └─ GET /health
├─ Reading List
│  ├─ GET /api/v1/reading-list
│  ├─ GET /api/v1/reading-list/{id}
│  ├─ POST /api/v1/reading-list
│  ├─ PUT /api/v1/reading-list/{id}
│  ├─ PATCH /api/v1/reading-list/{id}/status
│  └─ DELETE /api/v1/reading-list/{id}
└─ Negative
   ├─ 400 Validation error
   ├─ 404 Not found
   ├─ 405 Method not allowed (+Allow)
   ├─ 409 Conflict (externalId)
   └─ 500 Internal error (формат ответа)

Обычный jar и runtime classpath

Когда вы делаете ./gradlew jar, Gradle собирает обычный jar. В нём есть ваш код, но нет зависимостей (Jackson, SLF4J, Logback). И это нормально: мы в этом курсе не подключаем Shadow plugin и не делаем fat-jar, потому что это отдельная тема и отдельные нюансы.

Поэтому если вы попытаетесь “честно” запустить только jar, то рано или поздно увидите знакомое грустное:

NoClassDefFoundError: com/fasterxml/jackson/...
Решение на уровне “простого дистрибутива” такое: в ZIP кладём jar и кладём рядом все runtime-зависимости, а запуск делаем через classpath wildcard. Команда получается понятная:

java -cp "libs/*" com.example.readlater.app.ReadLaterApplication server
И это выглядит почти как “мини-установщик”: есть папка libs/, есть команда запуска, есть README и Postman.


# ReadLater Starter

Учебный проект с локальным HTTP API и режимом запуска `server`.

## Требования

- JDK 25
- все команды выполняются из корня проекта через Gradle Wrapper `./gradlew`
- запуск через IDE не нужен

## Сборка

```bash
# Сборка через Gradle Wrapper, без привязки к IDE
./gradlew clean build
```

После сборки jar-файл находится в `build/libs/readlater-starter.jar`.

## Запуск в режиме `server`

Оставьте сервер запущенным в отдельном терминале:

```bash
# Сервер должен быть запущен отдельно, чтобы curl мог его проверить
./gradlew run --args="server"
```

По умолчанию сервер поднимается на порту из файла `src/main/resources/application.properties`.

## Ключевые файлы

- `build.gradle.kts` — сборка проекта и задача `packageDist`
- `src/main/resources/application.properties` — основной конфиг приложения
- `postman/readlater-starter.postman_collection.json` — экспортированная Postman collection

## Smoke-проверка через `curl`

При запущенном сервере выполните:

```bash
# Проверка health endpoint
curl http://localhost:8080/health
# Базовый запрос к списку чтения
curl http://localhost:8080/api/v1/reading-list
```

Опционально можно проверить фильтр по статусу:

```bash
# Дополнительный фильтр помогает быстро проверить query-параметры
curl "http://localhost:8080/api/v1/reading-list?status=TO_READ"
```

## Сборка дистрибутива

```bash
./gradlew packageDist
```

Готовый архив:

```text
build/dist/readlater-starter.zip
```

Внутри архива лежат:

- `README.md`
- директория `postman/`
- jar-файл приложения в `libs/`
- runtime-зависимости в `libs/`
