# Gestor de ficheros

Programa que aplica lo aprendido en el primer trimestre de la asignatura *Acceso a Datos*, del segundo curso del Grado Superior en Desarrollo de Aplicaciones Multiplataforma impartido en 2026-2027 en el IES Albarregas de Mérida, Badajoz.

El programa sirve para leer:

- Ficheros CSV (de golpe, no linea por linea).
- Ficheros de registros (tanto directamente como registros arbitrarios).

## Documentación

He subido la [documentación del proyecto](https://merida.party/dam/datos/ficheros) para que todo resulte un poquito más fácil de seguir.

Gran parte del código está comentado, así que sigue mereciendo la pena pasarse por los archivos *.java* para ver el funcionamiento interno. Los archivos de código están en la carpeta *gestorficheros/src/main/java/es/cesargp/gestorficheros*.

## Cómo ejecutar

Puedes clonar el repositorio y abrir la carpeta desde cualquier IDE. Es un proyecto de Gradle normal y no debería dar ningún problema.

Si quieres hacerlo sin IDE, simplemente:

1. Clona el repositorio (`git clone https://github.com/cesar-gp/gestorficheros.git`).
2. Abre la carpeta en el terminal (`cd gestorficheros`).
3. Ejecútalo con Gradle (`./gradlew run`).