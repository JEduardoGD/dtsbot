# Repository notes

- The root `pom.xml` builds a Java 21 Spring Boot command-line app (`DtsbotApplication` implements `CommandLineRunner`), not a web server. `dtsbot_py/` is a separate Python Word-document generator, not a Maven module; follow its `AGENTS.md` and `README.md` for Python changes (including the README history requirement).

## Java (run from repository root)

- `./mvnw compile` checks production sources; `./mvnw spring-boot:run` starts the runner. The existing `@SpringBootTest` source imports JUnit, but `pom.xml` has no test dependency, so `./mvnw test` cannot compile tests as configured.
- `application.properties` optionally loads root `.env` and references `OPEN_AI_API_KEY`; `.env.example` lists the expected variable names.
- The runner reads workbooks from root `insumos/` (the property key is misspelled `insumos.loation`) and root `context.txt`, then writes reports under root `results/`. These input/output files are gitignored. The loader selects `.xls`/`.xlsx` names but opens them with `XSSFWorkbook`; use `.xlsx` inputs, with headers in row 1 and data in columns A/B of the first sheet.
- Incident scoring in `DtsbotApplication.run` is currently a hard-coded response with a random probability; the real `dtsCallService.proceessCall(...)` invocation is commented out. Do not treat a run as an OpenAI classification test. The Postgres/Chroma services in `docker-compose.yml` are not used by this runner.

## Python

- Run from `dtsbot_py/`: `python -m pip install -r requirements.txt`, then `python dtsbot_py.py INCS<number>/INCS<number>.txt`. The script uses `examples/NNNNNNN_DTS.docx` relative to itself and writes `<number>_DTS.docx` beside the input TXT; a successful run overwrites that output file.
