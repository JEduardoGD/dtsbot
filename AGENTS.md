# Repository notes

- The root is a Java 21 Spring Boot CLI; `DtsbotApplication` is a startup `CommandLineRunner`. `dtsbot_py/` is a separate Python generator, outside Maven; follow its `AGENTS.md` and `README.md` when changing it (including the README history requirement).

## Java

- From the repository root, use `./mvnw compile` to verify production sources and `./mvnw spring-boot:run` to run the batch. Maven has no test dependency configured, though `src/test` imports JUnit; `./mvnw test` is not a usable check without fixing that configuration.
- Runtime inputs are root `context.txt` and `.xlsx` files in `insumos/` (both gitignored). `application.properties` imports optional root `.env`, references `OPEN_AI_API_KEY` and `DEBUG`, and configures the directory with the misspelled property `insumos.loation`—keep the spelling aligned with `LoadInsumosServiceImpl` unless deliberately renaming both.
- The workbook loader uses sheet 1, starts at row 2 (row index 1), and reads incident/application/request/original text from columns A/C/E/K. It lists `.xls` as well as `.xlsx`, but opens with `XSSFWorkbook`; use `.xlsx` inputs.
- `DEBUG=true` selects a hard-coded reasoning and random probability. With debug off, the runner calls OpenAI. It currently breaks after processing the first incident in each workbook, so a run does not process all rows. Reports are written under gitignored `results/`.
