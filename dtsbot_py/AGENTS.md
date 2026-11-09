# Instrucciones para agentes

- Mantén `README.md` actualizado cuando cambien el propósito, la estructura o el uso del proyecto.
- Añade al historial de `README.md` una nota breve por cada cambio relevante.
- No describas funcionalidades que no estén implementadas o confirmadas.
- Para generar un ejecutable `.exe` de un solo archivo, ejecuta desde la raíz del repositorio `python -m PyInstaller --onefile .\dtsbot_py\dtsbot_py.py`; PyInstaller lo coloca en `dist/`. El `.exe` recibe los mismos tres argumentos posicionales (`propiedades`, `plantilla` y `salida`); las rutas deben ser absolutas y la carpeta de salida debe existir.
