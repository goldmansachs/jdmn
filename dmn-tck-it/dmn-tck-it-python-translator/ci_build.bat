@ECHO OFF

@REM mvn clean does not remove them
if exist .pytest_cache del /F /Q .pytest_cache
if exist .tox del /F /Q .tox
if exist .venv del /F /Q .venv

call ci/make_env.bat .venv && (
  echo Python venv was created
) || (
  echo Cannot create Python venv
  exit /b 1
)

python -m pytest && (
  echo pytest passed
) || (
  echo pytest failed
  exit /b 1
)
tox -e flake8 && (
  echo flake8 passed
) || (
  echo flake8 failed
  exit /b 1
)
