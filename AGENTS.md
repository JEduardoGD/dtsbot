# Repository notes

- The root `pom.xml` is a Java 21 Spring Boot CLI; `DtsbotApplication` runs on startup as a `CommandLineRunner`. `dtsbot_py/` is a separate Python document generator, not part of Maven.

## Java

- Run from the repository root: `./mvnw compile` for production-source verification; `./mvnw spring-boot:run` starts the batch runner. `pom.xml` has no test dependency although a test imports JUnit, so `./mvnw test` is not configured to compile the test source.
- `src/main/resources/application.properties` optionally imports root `.env`, expects `OPEN_AI_API_KEY`, and sets the input directory using the misspelled key `insumos.loation`. The runner loads root `context.txt` and workbooks from `insumos/`; the loader opens files as `XSSFWorkbook`, so provide `.xlsx` files with headers on row 1 and application/text values in columns A/B of sheet 1. These inputs and report outputs under `results/` are gitignored.
- `DtsbotApplication.run` currently submits a hard-coded response with a random probability; the `dtsCallService.proceessCall(...)` call is commented out. Running it does not test live OpenAI classification.

## Python

- For changes under `dtsbot_py/`, follow `dtsbot_py/AGENTS.md` and `dtsbot_py/README.md`; the latter requires a history entry for each relevant change. Run from `dtsbot_py/`: `python -m pip install -r requirements.txt`, then `python dtsbot_py.py INCS<number>/INCS<number>.txt`. The generated `<number>_DTS.docx` is written beside the input; successful runs overwrite an existing output.
