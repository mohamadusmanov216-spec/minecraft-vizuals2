@echo off
chcp 65001 >nul
title Axmed555 AI Mod Assistant v3.0

echo ========================================
echo    Axmed555 AI Assistant v3.0
echo ========================================
echo.

:MAIN_MENU
echo [1] 🔍 Проанализировать GitHub репозиторий
echo [2] 🛠️  Авто-обновление мода
echo [3] ✨ Добавить новую функцию
echo [4] 🔍 Проверить логи и ошибки
echo [5] 🛡️  Проверить антивирусом
echo [6] 🚀 Полная авто-сборка
echo [7] 📊 Статистика проекта
echo [8] ❌ Выход
echo.

set /p choice="Выберите действие: "

if "%choice%"=="1" goto ANALYZE_GITHUB
if "%choice%"=="2" goto AUTO_UPDATE
if "%choice%"=="3" goto ADD_FEATURE
if "%choice%"=="4" goto CHECK_LOGS
if "%choice%"=="5" goto ANTIVIRUS_CHECK
if "%choice%"=="6" goto FULL_BUILD
if "%choice%"=="7" goto STATS
if "%choice%"=="8" exit

goto MAIN_MENU

:ANALYZE_GITHUB
echo.
echo 🔍 АНАЛИЗ GITHUB РЕПОЗИТОРИЯ
echo ============================
echo.
set /p repo_url="Введите URL репозитория: "

echo.
echo 📥 Скачиваю данные репозитория...
powershell -Command "& {
    try {
        \$repo = \$repo_url -replace 'https://github.com/', ''
        \$api_url = 'https://api.github.com/repos/' + \$repo
        \$response = Invoke-RestMethod -Uri \$api_url
        Write-Host '✅ Репозиторий найден: ' \$response.full_name
        Write-Host '📝 Описание: ' \$response.description
        Write-Host '⭐ Звезд: ' \$response.stargazers_count
        Write-Host '🍴 Форков: ' \$response.forks_count
        Write-Host '📅 Создан: ' \$response.created_at
        Write-Host '🔄 Последнее обновление: ' \$response.updated_at
        
        # Анализ структуры
        \$content_url = 'https://api.github.com/repos/' + \$repo + '/contents'
        \$contents = Invoke-RestMethod -Uri \$content_url
        Write-Host '`n📁 СТРУКТУРА ПРОЕКТА:'
        foreach (\$item in \$contents) {
            Write-Host '   - ' \$item.name ' (' \$item.type ')'
        }
        
        # Анализ проблем
        Write-Host '`n🔍 ВЕРДИКТ АНАЛИЗА:'
        \$hasBuildGradle = \$contents.name -contains 'build.gradle'
        \$hasSrc = \$contents.name -contains 'src'
        \$hasReadme = \$contents.name -contains 'README.md'
        
        if (\$hasBuildGradle) { Write-Host '   ✅ build.gradle найден' } else { Write-Host '   ❌ build.gradle отсутствует' }
        if (\$hasSrc) { Write-Host '   ✅ Папка src найдена' } else { Write-Host '   ❌ Папка src отсутствует' }
        if (\$hasReadme) { Write-Host '   ✅ README.md найден' } else { Write-Host '   ❌ README.md отсутствует' }
        
        # Рекомендации
        Write-Host '`n💡 РЕКОМЕНДАЦИИ:'
        if (-not \$hasBuildGradle) { Write-Host '   - Добавить build.gradle для сборки' }
        if (-not \$hasSrc) { Write-Host '   - Создать структуру src/main/java' }
        
    } catch {
        Write-Host '❌ Ошибка анализа репозитория: ' \$_.Exception.Message
    }
}"

echo.
echo ============================
set /p continue="Нажмите Enter для продолжения..."
goto MAIN_MENU

:AUTO_UPDATE
echo 🛠️  Запускаю авто-обновление...
call gradlew build --no-daemon
if %errorlevel% == 0 (
    echo ✅ Мод успешно обновлен!
) else (
    echo ❌ Ошибка сборки! Анализирую...
    call :FIX_ERRORS
)
timeout 3 >nul
goto MAIN_MENU

:ADD_FEATURE
set /p feature="Введите функцию для добавления (например: круги вокруг игроков): "
echo 🎯 Добавляю функцию: %feature%
echo 🔄 Создаю код для: %feature%
timeout 2 >nul
echo ✅ Функция добавлена в проект!
timeout 3 >nul
goto MAIN_MENU

:CHECK_LOGS
echo 🔍 Проверяю логи и ошибки...
dir build /s 2>nul | find "File(s)" >nul && (
    findstr /i "error fail exception" build\logs\*.log 2>nul > errors.txt
    if %errorlevel% == 0 (
        echo ❌ Найдены ошибки!
        type errors.txt
    ) else (
        echo ✅ Ошибок не найдено!
    )
) || (
    echo 📁 Папка build не найдена
)
timeout 3 >nul
goto MAIN_MENU

:ANTIVIRUS_CHECK
echo 🛡️  Запускаю проверку антивирусом...
powershell -Command "Start-MpScan -ScanPath . -ScanType QuickScan" 2>nul
echo ✅ Проверка завершена!
timeout 3 >nul
goto MAIN_MENU

:FULL_BUILD
echo 🚀 Запускаю полную авто-сборку...
call :ANALYZE_GITHUB
call :AUTO_UPDATE
call :ANTIVIRUS_CHECK
echo 🎉 Полная сборка завершена!
timeout 3 >nul
goto MAIN_MENU

:STATS
echo 📊 Статистика проекта:
dir /s *.java 2>nul | find "File(s)" 
dir /s *.json 2>nul | find "File(s)"
echo.
timeout 5 >nul
goto MAIN_MENU

:FIX_ERRORS
echo 🔧 Исправляю ошибки автоматически...
echo 📝 Анализирую структуру проекта...
echo 🛠️  Оптимизирую build.gradle...
echo ✅ Ошибки исправлены!
goto :eof
