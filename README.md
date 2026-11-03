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

The runner expects `.xlsx` workbooks in `insumos/` and a `context.txt` file in the repository root. It reads the first worksheet, starting at row 2, using columns A (application) and B (original text). Workbook and context inputs are local files and are not tracked by Git; reports are written under `results/`.

The optional root `.env` is imported by Spring configuration. `OPEN_AI_API_KEY` is referenced in `src/main/resources/application.properties`. Although the app includes an OpenAI call service, the call from the runner is currently commented out: incident processing uses a hard-coded response and random probability, so a run does not perform live classification.

The Maven project has no test dependency configured, despite a JUnit-based test source; `./mvnw test` cannot compile that source as currently configured. Use `./mvnw compile` to check production code.

## Python document generator

Run from `dtsbot_py/`:

```sh
python -m pip install -r requirements.txt
python dtsbot_py.py INCS00003799504/INCS00003799504.txt
```

The script uses its `examples/NNNNNNN_DTS.docx` template and writes `<number>_DTS.docx` beside the input file. A successful run overwrites an existing output. See the [Python README](dtsbot_py/README.md) for required properties and validation details.
