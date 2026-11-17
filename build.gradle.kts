import org.gradle.api.tasks.bundling.Zip

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

    // if libs toml is aplied
    // implementation(libs.jackson.databind)
    // implementation(libs.jackson.core)

    // implementation(libs.slf4j.api)
    // classic had transit depedency to core
    // runtimeOnly(libs.logback.classic)
}

java {
    toolchain {
        // Используем Java 25 во всех окружениях
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    // Точка входа приложения
    mainClass = "pro._21it.readlater.app.ReadLaterApplication"
}

tasks.register<Zip>("packageDist") {
    // Кладём собранный jar в папку libs внутри архива
    from(tasks.jar) { into("ligs") }

    // Кладём runtime-зависимости рядом, чтобы запускать через -cp "libs/*"
    from(configurations.runtimeClasspath) { into("libs") }

    // Добавляем инструкцию по запуску рядом — иначе ZIP превращается в "угадай как"
    from("README.md")

    // Добавляем smoke-инструмент (Postman collection) как часть артефакта
    from("postman") { into("postman") }

    // Явно задаём, куда складывать ZIP и как его назвать (чтобы путь был предсказуемым)
    destinationDirectory.set(layout.buildDirectory.dir("dist"))
    archiveFileName.set("readlater-starter.zip")
}
