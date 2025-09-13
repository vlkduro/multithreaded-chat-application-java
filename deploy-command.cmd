@echo off
setlocal enabledelayedexpansion

REM --- Réglages ---
set "SCRIPT_DIR=%~dp0"
REM enlever le \ final
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"
set "PROJECT_ROOT=%SCRIPT_DIR%"
set "SRC_DIR=%PROJECT_ROOT%\src"
set "OUT_DIR=%PROJECT_ROOT%\out"
set "DIST_DIR=%PROJECT_ROOT%\dist"

set "SERVER_MAIN=server.ServeurSocket"
set "CLIENT_MAIN=client.ClientSocket"

echo == Nettoyage ==
if exist "%OUT_DIR%" rd /s /q "%OUT_DIR%"
if exist "%DIST_DIR%" rd /s /q "%DIST_DIR%"
mkdir "%OUT_DIR%" "%DIST_DIR%"

echo == Compilation ==
REM Liste des sources
(for /r "%SRC_DIR%" %%f in (*.java) do @echo %%f) > "%OUT_DIR%\sources.txt"
for %%A in ("%OUT_DIR%\sources.txt") do if %%~zA==0 (
  echo Erreur: aucun fichier .java trouve sous %SRC_DIR%
  exit /b 1
)

REM Compile en UTF-8 vers out/
javac -encoding UTF-8 -d "%OUT_DIR%" @"%OUT_DIR%\sources.txt"
if errorlevel 1 exit /b 1

REM Verifie la presence des classes main
set "SERVER_CLASS_FILE=%OUT_DIR%\%SERVER_MAIN:.=\%.class"
set "CLIENT_CLASS_FILE=%OUT_DIR%\%CLIENT_MAIN:.=\%.class"
if not exist "%SERVER_CLASS_FILE%" (
  echo Erreur: classe principale introuvable: %SERVER_MAIN% (attendu: %SERVER_CLASS_FILE%)
  exit /b 1
)
if not exist "%CLIENT_CLASS_FILE%" (
  echo Erreur: classe principale introuvable: %CLIENT_MAIN% (attendu: %CLIENT_CLASS_FILE%)
  exit /b 1
)

echo == Packaging JAR ==
jar cfe "%DIST_DIR%\server.jar" "%SERVER_MAIN" -C "%OUT_DIR%" .
if errorlevel 1 exit /b 1
jar cfe "%DIST_DIR%\client.jar" "%CLIENT_MAIN" -C "%OUT_DIR%" .
if errorlevel 1 exit /b 1

REM Lanceurs Windows
> "%DIST_DIR%\run-server.bat" (
  echo @echo off
  echo set "SCRIPT_DIR=%%~dp0"
  echo set "JAVA_BIN=%%SCRIPT_DIR%%runtime\bin\java.exe"
  echo if not exist "%%JAVA_BIN%%" set "JAVA_BIN=java"
  echo "%%JAVA_BIN%%" -jar "%%SCRIPT_DIR%%server.jar" %%*
)

> "%DIST_DIR%\run-client.bat" (
  echo @echo off
  echo set "SCRIPT_DIR=%%~dp0"
  echo set "JAVA_BIN=%%SCRIPT_DIR%%runtime\bin\java.exe"
  echo if not exist "%%JAVA_BIN%%" set "JAVA_BIN=java"
  echo set "HOST=127.0.0.1"
  echo set "PORT=10080"
  echo if not "%%~1"=="" set "HOST=%%~1"
  echo if not "%%~2"=="" set "PORT=%%~2"
  echo "%%JAVA_BIN%%" -jar "%%SCRIPT_DIR%%client.jar" %%HOST%% %%PORT%%
)

echo.
echo == OK ==
echo Serveur: %DIST_DIR%\server.jar  ^(double-clic: run-server.bat^)
echo Client : %DIST_DIR%\client.jar  ^(double-clic: run-client.bat^)

endlocal
