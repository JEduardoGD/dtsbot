# Repository notes

- This repository has two independent tools: a Java 21/Spring Boot batch app at the root and a standalone Python DOCX generator in `dtsbot_py/` (not part of Maven). For Python changes, follow `dtsbot_py/AGENTS.md`.

## Java batch app

- Run commands from the repository root: `./mvnw compile` checks production code; `./mvnw spring-boot:run` runs the batch. `./mvnw test` currently fails because `src/test` imports JUnit but `pom.xml` declares no test dependency.
- `DtsbotApplication` is the `CommandLineRunner`. It reads `context.txt` and workbook files in `insumos/`, then writes reports under `results/`. These runtime files/directories are gitignored. The optional root `.env` is imported by Spring; relevant settings are `OPEN_AI_API_KEY`, `USE_FAKE_IA`, `SO`, and `DST_TEMPLATE_LOCATION`.
- The loader reads the first worksheet starting at row index 1 and maps zero-based columns A/C/E/K to incident/application/request/original text. It filters `.xls` and `.xlsx` names but uses `XSSFWorkbook`, so inputs must be `.xlsx`.
- `insumos.loation` is misspelled in both `application.properties` and `LoadInsumosServiceImpl`; update both if correcting it.
