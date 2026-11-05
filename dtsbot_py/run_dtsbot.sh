#!/usr/bin/env bash

set -u

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
ACTIVATE_SCRIPT="$SCRIPT_DIR/.venv/bin/activate"

if [[ ! -f "$ACTIVATE_SCRIPT" ]]; then
    printf 'Error: virtual environment activation script not found: %s\n' "$ACTIVATE_SCRIPT" >&2
    exit 1
fi

source "$ACTIVATE_SCRIPT"
python "$SCRIPT_DIR/dtsbot_py.py" "$@"
status=$?
deactivate
exit "$status"
