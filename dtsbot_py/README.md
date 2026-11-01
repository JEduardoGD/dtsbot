# dtsbot_py

El objetivo del proyecto es generar archivos DTS en formato Word a partir de un archivo de propiedades TXT y un archivo de ejemplo. El script `dtsbot_py.py` requiere la ruta del archivo de propiedades:

```text
python dtsbot_py.py propiedades.txt
```

Instala la dependencia antes de ejecutar el script:

```text
python -m pip install -r requirements.txt
```

El archivo debe contener una propiedad por línea, separada por `=` o `:`. El directorio y el nombre del archivo deben ser `INCS<número>` (por ejemplo, `INCS00003799504/INCS00003799504.txt`), y la propiedad `número` debe coincidir con ese número. También son obligatorias, con valor no vacío y sin duplicados, las propiedades `número`, `IDENTIFICACION_DEL_PROBLEMA` y `FECHA_ANALISIS`; esta última debe contener una fecha válida con formato `DD/MM/AAAA`. Si la fecha no coincide con el día de ejecución, el script muestra una advertencia y continúa. Si una validación falla, muestra un error y termina con código `1`. Si todo es correcto, copia `examples/NNNNNNN_DTS.docx` como `<número>_DTS.docx`, reemplaza todas las apariciones de los marcadores de las propiedades en el cuerpo, tablas, encabezados y pies de página, y falla si alguno de los marcadores no está en el documento.

Actualmente también contiene documentos de ejemplo en `examples/` y un archivo de referencia en `INCS00003799504/`.

## Historial

- 2026-11-01: Se crean este README y `AGENTS.md` para documentar el proyecto y orientar futuras actualizaciones.
- 2026-11-01: Se añade Git al proyecto.
- 2026-11-01: Se define como objetivo generar archivos DTS de Word a partir de un archivo de propiedades y un archivo de ejemplo.
- 2026-11-01: Se añade `dtsbot_py.py` como punto de entrada mínimo.
- 2026-11-01: Se requiere un archivo TXT de propiedades como argumento del script.
- 2026-11-01: El script lee propiedades con formato `NOMBRE= valor` y las muestra en pantalla.
- 2026-11-01: Se valida que el número de incidente coincida entre directorio, nombre de archivo y propiedad `número`.
- 2026-11-01: Tras validar las propiedades, se copia la plantilla DTS al directorio del incidente con su número en el nombre.
- 2026-11-01: Se exigen las propiedades `número`, `IDENTIFICACION_DEL_PROBLEMA` y `FECHA_ANALISIS` con valores no vacíos.
- 2026-11-01: Se valida que `FECHA_ANALISIS` sea una fecha existente con formato `DD/MM/AAAA`.
- 2026-11-01: Se advierte si `FECHA_ANALISIS` no coincide con la fecha de ejecución, sin interrumpir el proceso.
- 2026-11-01: Se añade `python-docx` para reemplazar todas las apariciones de las propiedades en el documento DTS generado.
