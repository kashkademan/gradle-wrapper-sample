#!/usr/bin/env bash
set -e

# Первый аргумент — режим, по умолчанию "server" (чтобы запуск без параметров работал)
MODE="${1:-server}"

# Запускаем приложение с classpath на libs/* (jar + зависимости лежат рядом)
java -cp "libs/*" com.example.readlater.app.ReadLaterApplication "$MODE"
