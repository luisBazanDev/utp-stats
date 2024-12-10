# Proyecto: Análisis Académico

## Descripción de la Solución
Este proyecto consiste en una aplicación web desarrollada en Java, diseñada para gestionar y analizar datos de evaluaciones académicas. La solución está orientada a ayudar a los docentes a realizar un análisis detallado del desempeño de sus alumnos, identificando áreas de oportunidad basadas en los resultados por pregunta.

### Características principales:
- **Registro de calificaciones**: Permite ingresar las notas de los estudiantes manualmente o mediante importación de archivos.
- **Generación de reportes detallados con gráficos**:
  - Gráfico de pastel para aprobados y desaprobados.
  - Gráfico de barras con distribución de calificaciones (0 a 20).
  - Podio para los tres primeros puestos, considerando empates.
  - Gráfico de barras para rendimiento por pregunta.
- **Soporte multilingüe**: Disponible en inglés y español.
- **Almacenamiento centralizado**: Base de datos para consultas y análisis futuros.

Esta herramienta busca mejorar la comprensión del desempeño académico y facilitar la toma de decisiones pedagógicas basadas en datos.

---

## Objetivo y Alcance

### **Objetivo**
Diseñar e implementar una aplicación web que permita gestionar y analizar las notas académicas, proporcionando reportes y gráficos que simplifiquen la evaluación del rendimiento de los estudiantes.

### **Alcance**
- **Ingreso de calificaciones**:
  - Manual o mediante importación de archivos.
- **Reportes y gráficos**:
  - Representaciones detalladas del rendimiento de los estudiantes.
- **Multilingüe**:
  - Disponible en inglés y español.
- **Base de datos**:
  - Almacenamiento seguro y eficiente de la información.
- **Interfaz de usuario**:
  - Amigable, utilizando JSP/JSF y Facelets Tags.
- **Servicios REST**:
  - Para garantizar interoperabilidad.

---

## Estructura del Proyecto

### **Directorio principal**
- `.mvn`: Configuración de Maven para la gestión de dependencias y compilación.
- `assets`: Recursos estáticos como íconos e imágenes.
- Archivos SQL:
  - `CREATE-DATABASE.sql`, `PROCEDURES.sql`, `SEEDER.sql`: Scripts para creación de la base de datos, procedimientos y datos iniciales.
- `test-1.xlsx`: Archivo de prueba en formato Excel.

### **`src/main`**
- **`java/pe/edu/utp/dwi/hackathon/hackathondwi`**:
  - **`beans`**: Clases que manejan datos para la capa de presentación o lógica intermedia.
  - **`controllers`**: Controladores que gestionan la lógica de negocio.
  - **`dao`**: Clases de acceso a datos (Data Access Object) para interactuar con la base de datos.
  - **`domain/upload`**: Clases que representan estructuras de datos.
  - **`dto`**: Objetos de transferencia de datos (Data Transfer Objects).
  - **`singleton`**: Implementaciones del patrón Singleton para ciertas clases.
- **`resources/webapp`**:
  - `WEB-INF`: Configuraciones protegidas.
  - `icons`, `images`: Recursos gráficos.
  - `scripts`, `styles`: Scripts y estilos para la interfaz.
  - `template`: Páginas XHTML y JSP para la interfaz.

---

## Instalación y Configuración

1. **Requisitos previos**:
   - Java (JDK 21 como mínimo).
   - Maven.
   - Servidor de aplicaciones compatible con JSP/JSF (por ejemplo, Apache Tomcat).
   - Base de datos MySQL.

2. **Configuración del entorno**:
   - Clona este repositorio: `git clone "https://github.com/luisBazanDev/utp-stats"`.
   - Configura el archivo `application.properties` con las credenciales de la base de datos.
   - Ejecuta los scripts SQL en el servidor de base de datos.

3. **Compilación e inicio**:
   - Compila el proyecto con Maven: `mvn clean install`.
   - Despliega la aplicación en el servidor de aplicaciones.

---

## Uso
1. **Inicio de sesión**:
   - Accede a la aplicación desde el navegador.
2. **Carga de calificaciones**:
   - Ingresa manualmente o importa un archivo con los datos de evaluación.
3. **Generación de reportes**:
   - Explora las representaciones gráficas y tabulares del rendimiento académico.

---

## Contribuciones
Si deseas contribuir a este proyecto:
- Realiza un *fork* del repositorio.
- Crea una rama para tus cambios: `git checkout -b feature/nueva-funcionalidad`.
- Envía un *pull request* con tus mejoras.

---

## Licencia
Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

---

## Contacto
Para dudas o soporte, contacta al equipo de desarrollo en: [https://mtocommunity.com/#contactus].

