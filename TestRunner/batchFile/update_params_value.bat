@echo off
setlocal EnableExtensions EnableDelayedExpansion

REM Set project directory
cd /d "E:\CS_AutomationFramwork\amazon-selenium-framework" || (
    echo ERROR: Project directory not found.
    exit /b 1
)

set "configFile=src\test\resources\configurations\config.properties"
set "pomFile=pom.xml"

if not exist "%configFile%" (
    echo ERROR: config.properties not found.
    exit /b 1
)

if not exist "%pomFile%" (
    echo ERROR: pom.xml not found.
    exit /b 1
)

REM Update config.properties
(
    for /f "usebackq tokens=1,* delims==" %%A in ("%configFile%") do (
        if /I "%%A"=="BROWSER" (
            echo BROWSER=!BROWSER!
        ) else if /I "%%A"=="ENV" (
            echo ENV=!SERVER!
        ) else if /I "%%A"=="PARALLEL_MODE" (
            echo PARALLEL_MODE=!PARALLEL_MODE!
        ) else if /I "%%A"=="MAX_PARALLEL_TESTS" (
            echo MAX_PARALLEL_TESTS=!MAX_PARALLEL_TESTS!
        ) else if /I "%%A"=="MAX_RETRIES" (
            echo MAX_RETRIES=!MAX_RETRIES!
        ) else (
            echo %%A=%%B
        )
    )
) > "%configFile%.tmp"

if errorlevel 1 exit /b 1

move /Y "%configFile%.tmp" "%configFile%" >nul
if errorlevel 1 exit /b 1

REM Update suiteXmlFile in pom.xml using PowerShell (Safe from XML syntax bugs)
if not "!SUITE!"=="" (
    powershell -Command "(Get-Content '%pomFile%') -replace '<suiteXmlFile>.*?</suiteXmlFile>', '<suiteXmlFile>!SUITE!</suiteXmlFile>' | Set-Content '%pomFile%'"
)

echo Batch file completed successfully.
exit /b 0