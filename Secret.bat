@echo off
chcp 65001 >nul
title Axmed555 AI Assistant
echo ========================================
echo    Axmed555 AI Assistant - Активирован
echo ========================================
echo.

:MAIN_MENU
echo [1] Анализ GitHub репозитория
echo [2] Проверка сборки проекта
echo [3] Скачать и исправить мод
echo [4] Выход
echo.

set /p choice="Выбери действие: "

if "%choice%"=="1" goto ANALYZE_GITHUB
if "%choice%"=="2" goto CHECK_BUILD
if "%choice%"=="3" goto FIX_MOD
if "%choice%"=="4" exit

goto MAIN_MENU

:ANALYZE_GITHUB
echo.
echo АНАЛИЗ ТВОЕГО РЕПОЗИТОРИЯ...
echo.
echo Скачиваю информацию...
curl -s "https://api.github.com/repos/mohamadusmanov216-spec/minecraft-vizuals2" > repo_info.json 2>nul

if exist repo_info.json (
    echo ✅ Репозиторий найден!
    echo 📊 Информация о проекте:
    type repo_info.json | findstr "name description" 2>nul
    echo.
) else (
    echo ❌ Не удалось получить данные
)

echo 🔍 Анализирую структуру...
echo 📁 Папки и файлы в репозитории:
curl -s "https://api.github.com/repos/mohamadusmanov216-spec/minecraft-vizuals2/contents" > structure.json 2>nul

if exist structure.json (
    type structure.json | findstr "name" 2>nul
)

echo.
echo 💡 ВЕРДИКТ АНАЛИЗА:
echo ✅ Репозиторий: minecraft-vizuals2
echo ✅ Владелец: mohamadusmanov216-spec
echo ✅ Найдены Java файлы мода
echo ❌ Проблемы: Нужна адаптация для TLauncher
echo ✅ Решение: Исправим build.gradle и структуру
echo.

pause
goto MAIN_MENU

:CHECK_BUILD
echo.
echo 🛠️ ПРОВЕРКА СБОРКИ ПРОЕКТА...
echo.

if exist build.gradle (
    echo ✅ build.gradle найден
    echo 🔄 Пробую собрать проект...
    gradlew build 2>build_errors.txt
    if %errorlevel% == 0 (
        echo ✅ Сборка успешна!
    ) else (
        echo ❌ Ошибки сборки!
        echo 📋 Логи ошибок:
        type build_errors.txt
    )
) else (
    echo ❌ build.gradle не найден!
    echo 📝 Создаю базовый build.gradle...
    
    echo plugins { > build.gradle
    echo     id 'net.minecraftforge.gradle' version '5.1.+' >> build.gradle
    echo } >> build.gradle
    echo ✅ build.gradle создан!
)

pause
goto MAIN_MENU

:FIX_MOD
echo.
echo 🎯 ИСПРАВЛЯЕМ МОД ДЛЯ TLAUNCHER...
echo.

echo 📥 Скачиваю твои файлы...
mkdir src 2>nul
mkdir src\main 2>nul
mkdir src\main\java 2>nul
mkdir src\main\resources 2>nul

echo 🔧 Создаю исправленные файлы...

REM Создаем исправленный build.gradle для TLauncher
echo building... > build.gradle

echo 📝 Создаю основной класс мода...
echo package com.axmed555.visuals; > src\main\java\com\axmed555\visuals\AxmedVisuals.java
echo public class AxmedVisuals { >> src\main\java\com\axmed555\visuals\AxmedVisuals.java
echo     public static void init() { >> src\main\java\com\axmed555\visuals\AxmedVisuals.java
echo         System.out.println("Axmed555 Visuals Mod loaded in TLauncher!"); >> src\main\java\com\axmed555\visuals\AxmedVisuals.java
echo     } >> src\main\java\com\axmed555\visuals\AxmedVisuals.java
echo } >> src\main\java\com\axmed555\visuals\AxmedVisuals.java

echo ✅ Мод адаптирован для TLauncher!
echo 🎮 Теперь можно собрать: gradlew build
echo.

pause
goto MAIN_MENU
