@echo on
setlocal

set "SCRIPT_DIR=%~dp0"
set "ACTIVATE_SCRIPT=%SCRIPT_DIR%.venv\Scripts\activate.bat"

echo %ACTIVATE_SCRIPT%

if not exist "%ACTIVATE_SCRIPT%" (
    echo Error: virtual environment activation script not found: "%ACTIVATE_SCRIPT%" 1>&2
    endlocal & exit /b 1
)

call "%ACTIVATE_SCRIPT%"
python "%SCRIPT_DIR%dtsbot_py.py" %*
set "PYTHON_STATUS=%ERRORLEVEL%"

rem endlocal restores PATH and VIRTUAL_ENV to their pre-activation values.
endlocal & exit /b %PYTHON_STATUS%
