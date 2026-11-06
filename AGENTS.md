# Repository notes

- This repository has two independent tools: a Java 21/Spring Boot CLI at the root and the Python DOCX generator in `dtsbot_py/` (not part of Maven). For Python changes, follow `dtsbot_py/AGENTS.md`; update its README history for relevant changes.

## Java

- From the repository root, run `./mvnw compile` for production-source verification or `./mvnw spring-boot:run` to execute the batch. `./mvnw test` currently cannot compile `src/test`: the test imports JUnit, but `pom.xml` declares no test dependency.
- Runtime inputs are root `context.txt` and `.xlsx` workbooks in `insumos/`; these and `results/` are gitignored. The optional root `.env` is imported by Spring configuration; configured environment values include `OPEN_AI_API_KEY`, `USE_FAKE_IA`, and `SO`.
- `insumos.loation` is intentionally spelled this way in both `application.properties` and `LoadInsumosServiceImpl`; change both together if correcting it.
- The loader reads the first worksheet from row index 1 and maps columns A/C/E/K to incident/application/request/original text. Although it filters `.xls` files, it opens workbooks with `XSSFWorkbook`, so provide `.xlsx` files.
- `DtsbotApplication` is the `CommandLineRunner`: it processes each loaded incident. `USE_FAKE_IA=true` uses a hard-coded reasoning and random probability; otherwise it calls the OpenAI service. The runner loads root `context.txt` for the AI context.
