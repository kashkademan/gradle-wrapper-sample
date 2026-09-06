plugins {
    // Компилируем Java-код
    java
    // Добавляем задачу `run` для запуска приложения
    application
}

repositories {
    // Отсюда Gradle будет скачивать зависимости
    mavenCentral()
}

java {
    toolchain {
        // Используем Java 25 во всех окружениях
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    // Точка входа приложения
    mainClass = "com.example.readlater.ReadLaterApplication"
}
