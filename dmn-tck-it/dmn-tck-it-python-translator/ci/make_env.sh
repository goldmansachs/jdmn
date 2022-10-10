if [ -z "$1" ]
then
    echo "Usage make_env.bat PYTHON_ENV_DIR"
    exit 1
fi

# export PIP_INDEX_URL='https://pypi.python.org/pypi'
# export PIP_TRUSTED_HOST='pypi.python.org'
# export PIP_EXTRA_INDEX_URL='https://test.pypi.org/pypi'

export PYTHON_ENV_DIR=$1
python -m venv $PYTHON_ENV_DIR
source $PYTHON_ENV_DIR/bin/activate

python --version
pip --version
python -m pip install -U pip
pip install -r $CI_PROJECT_DIR/ci/ci_requirements.txt
