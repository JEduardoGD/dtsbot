@echo off
setlocal

set "EXE=%~dp0dist\dtsbot_py.exe"
if not exist "%EXE%" (
    echo Error: no se encontro "%EXE%". Genera primero el ejecutable con PyInstaller.
    exit /b 1
)

"%EXE%" %*
exit /b %ERRORLEVEL%
