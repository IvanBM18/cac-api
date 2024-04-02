# CAC API
Proyecto de backend para el proyecto de asistencias del CAC

## Dependencias
- [JDK-17](https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_windows-x64_bin.zip)

## Como correr el proyecto
Esta es una aplicacion de Spring Boot basada en maven, por lo que para obtener todas las dependencias es necesario correr el siguiente comando:
```
./mvnw clean install
```
Ya con las dependencias correctamente instaladas, solo basta con correr:
```
./mvnw spring-boot:run
```

## Variables de Entorno
Para correr este proyecto es necesario agregar el archivo de ```application.properties``` en la siguiente direccion:
**src/main/resources**
