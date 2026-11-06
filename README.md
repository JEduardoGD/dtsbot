# dtsbot

This repository contains two separate tools:

- A Java 21 / Spring Boot command-line app that reads incident workbooks and writes reports.
- `dtsbot_py/`, a standalone Python script that generates DTS Word documents from incident property files. See [`dtsbot_py/README.md`](dtsbot_py/README.md) for its input format and detailed usage.

## Java app

Run commands from the repository root. Java 21 is required.

```sh
./mvnw compile
./mvnw spring-boot:run
```

The runner expects `.xlsx` workbooks in `insumos/` and a `context.txt` file in the repository root. It reads the first worksheet from row 2, mapping columns A/C/E/K to incident/application/request/original text. Workbook and context inputs are local files and are not tracked by Git; reports are written under `results/`.

The optional root `.env` is imported by Spring configuration. Configure `OPEN_AI_API_KEY`; `USE_FAKE_IA=true` selects a hard-coded reasoning and random probability, while the default path calls the OpenAI service.

The Maven project has no test dependency configured, despite a JUnit-based test source; `./mvnw test` cannot compile that source as currently configured. Use `./mvnw compile` to check production code.

## Python document generator

The standalone Python generator takes three positional arguments: an absolute properties TXT path, an absolute `NNNNNNN_DTS.docx` template path, and an existing absolute output directory. Install dependencies from `dtsbot_py/` with `python -m pip install -r requirements.txt`. See the [Python README](dtsbot_py/README.md) for input validation and examples.

To run the script directly, invoke it from `dtsbot_py/` with those three arguments. Alternatively, from the repository root:

```sh
./run_dtsbot_py.sh "/absolute/path/INCS00003799504/INCS00003799504.txt" "/absolute/path/examples/NNNNNNN_DTS.docx" "/absolute/path/output"
```

On Linux, grant the launcher execute permission once with `chmod +x run_dtsbot_py.sh`. On Windows, build the one-file executable from the repository root with `python -m PyInstaller --onefile .\dtsbot_py\dtsbot_py.py`; it is written to `dist\dtsbot_py.exe` and can be called through `run_dtsbot_exe.bat` with the same three arguments.
