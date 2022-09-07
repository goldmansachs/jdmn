@ECHO OFF

set CI_PROJECT_DIR=%cd%

if "%1"=="" (
    echo Usage make_env.bat PYTHON_ENV_DIR
    exit /b 1
)

@REM set PIP_INDEX_URL=https://pypi.python.org/pypi
@REM set PIP_TRUSTED_HOST=pypi.python.org
set PIP_EXTRA_INDEX_URL=https://test.pypi.org/pypi

set PYTHON_ENV_DIR=%1
python -m venv %PYTHON_ENV_DIR%
call %PYTHON_ENV_DIR%/Scripts/activate

python --version
pip --version
python -m pip install -U pip
pip install -r %CI_PROJECT_DIR%/ci/ci_requirements.txt
