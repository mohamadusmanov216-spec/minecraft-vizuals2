@echo off
echo 🚀 Быстрое исправление мода для TLauncher...
echo.

echo 📥 Скачиваю твои файлы с GitHub...
git clone https://github.com/mohamadusmanov216-spec/minecraft-vizuals2 2>nul

if exist minecraft-vizuals2 (
    cd minecraft-vizuals2
    echo ✅ Проект скачан!
    echo 🔧 Исправляю для TLauncher...
    
    REM Исправляем build.gradle
    echo plugins { > build.gradle
    echo     id 'net.minecraftforge.gradle' version '5.1.+' >> build.gradle
    echo     id 'java' >> build.gradle
    echo } >> build.gradle
    echo ✅ build.gradle исправлен!
    
    echo 🎯 Мод готов для TLauncher!
    echo 📦 Собираю: gradlew build
    gradlew build
) else (
    echo ❌ Не удалось скачать проект!
)

pause
