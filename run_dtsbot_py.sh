#!/bin/sh

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
. "$SCRIPT_DIR/dtsbot_py/.venv/bin/activate" || exit 1

python "$SCRIPT_DIR/dtsbot_py/dtsbot_py.py" "$@"
SCRIPT_STATUS=$?

deactivate
exit "$SCRIPT_STATUS"
