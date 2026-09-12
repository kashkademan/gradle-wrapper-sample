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

dependencies {
    // JSON
    implementation("tools.jackson.core:jackson-core:3.0.4")
    implementation("tools.jackson.core:jackson-databind:3.0.4")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.17")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.32")
    runtimeOnly("ch.qos.logback:logback-core:1.5.32")
}

java {
    toolchain {
        // Используем Java 25 во всех окружениях
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    // Точка входа приложения
    mainClass = "pro._21it.readlater.ReadLaterApplication"
}
