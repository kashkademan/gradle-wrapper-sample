@echo off

rem Первый аргумент — режим, если не задан, используем server
set MODE=%1
if "%MODE%"=="" set MODE=server

rem Запуск через classpath на libs/* (jar + зависимости в одной папке)
java -cp "libs/*" com.example.readlater.app.ReadLaterApplication %MODE%
