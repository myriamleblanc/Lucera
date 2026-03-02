@ECHO OFF
REM ----------------------------------------------------------------------------
REM Maven Wrapper startup script for Windows
REM Simplified version: downloads maven-wrapper.jar on first run if necessary.
REM ----------------------------------------------------------------------------

SETLOCAL

SET "BASEDIR=%~dp0"
SET "WRAPPER_JAR=%BASEDIR%.mvn\wrapper\maven-wrapper.jar"
SET "WRAPPER_MAIN=org.apache.maven.wrapper.MavenWrapperMain"

IF NOT EXIST "%BASEDIR%.mvn\wrapper" (
  MKDIR "%BASEDIR%.mvn\wrapper"
)

IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Downloading Maven Wrapper (maven-wrapper.jar)...
  SET "WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

  REM Try PowerShell first
  POWERSHELL -Command "Invoke-WebRequest -UseBasicParsing -Uri '%WRAPPER_URL%' -OutFile '%WRAPPER_JAR%'" 2>NUL
  IF ERRORLEVEL 1 (
    ECHO Failed to download maven-wrapper.jar. Please download it manually from:
    ECHO   %WRAPPER_URL%
    EXIT /B 1
  )
)

SET "JAVA_EXE=java"
IF NOT "%JAVA_HOME%"=="" (
  IF EXIST "%JAVA_HOME%\bin\java.exe" (
    SET "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
  )
)

WHERE "%JAVA_EXE%" >NUL 2>&1
IF ERRORLEVEL 1 (
  ECHO ERROR: Java executable not found. Please install Java 21 and/or set JAVA_HOME.
  EXIT /B 1
)

IF "%MAVEN_PROJECTBASEDIR%"=="" (
  SET "MAVEN_PROJECTBASEDIR=%BASEDIR%"
)

"%JAVA_EXE%" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -cp "%WRAPPER_JAR%" %WRAPPER_MAIN% %*

ENDLOCAL

