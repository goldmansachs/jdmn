export CI_PROJECT_DIR=.

# mvn clean does not remove them
rm -rf .pytest_cache
rm -rf .tox
rm -rf .venv

$CI_PROJECT_DIR/ci/make_env.sh .venv
status=$?
if [ $status -eq 0 ]
then
  echo "Python venv was created"
else
  echo "Cannot create Python venv"
  exit 1
fi

pwd
ls -la

python -m pytest
status=$?
if [ $status -eq 0 ]
then
  echo "pytest passed"
else
  echo "pytest failed"
  exit 1
fi
tox -e flake8
status=$?
if [ $status -eq 0 ]
then
  echo "flake8 passed"
else
  echo "flake8 failed"
  exit 1
fi
