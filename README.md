# Practica DEVOPS
## Resolución del ejercicio practico

Para la resolución de este ejercicio se creó el proyecto https://github.com/bpgtoapanta/retodevops

Se usó las siguientes herramientas para la resolución

- JAVA / SpringBoot 3.3.4 con maven
- docker (Construcción de imágenes) 
- docker-compose (para definir y ejecutar aplicaciones multicontenedor de Docker)
- Github Actions (pipeline)

## Puntos de resolución

## Microservicio

Se crea un proyecto a partir del uso de SpringBoot 3.3.4

## Github Actions

Se usa Github Actions para elaborar el pipeline el cual abarca las siguientes etapas:
- build - Ejecuta la construcción de la aplicación de aquí se obtiene el artefacto JAR el cual con tiene la distribución de la aplicación
- Test - Ejecuta los test unitarios que contiene el proyecto.
- dockerize - Ejecuta la construcción y la subida de la imagen a DockerHub, para su posterior uso.

## Docker

Se realizó un archivo dockerfile, el cual contiene la definición para dockerizar la imagen con el artefacto de distribución del proyecto y los comandos necesarios para la ejecución.

## Docker-compose 

Se realizó un archivo docker-compose.yml, el cual contiene la definición para ejecutar las aplicaciones muticontenedor y que nos da la posibilidad de simular el balanceador para esto se usa nginx, aquí también haremos uso de la imagen creada por parte del pipeline a través de la definición de Dockerfile.

## Ejecución de la solución

Importante tener instalado Docker para la ejecución

Para la actualización de la imagen que contiene el aplicativo se requiere ejecutar el pipeline gestionado por Github actions.

Para esto se debe crear los siguiente secretos y variables en Github

# Secretos 
```
DOCKER_HUB_USERNAME (username de DockerHub)
DOCKER_HUB_ACCESS_TOKEN (Token que se debe crear en DockerHub)
```

# Variables 
```
DOCKER_IMAGE (nombre de una imagen de Docker)
```

Una vez se tenga esto configurado se puede ejecutar el pipeline

# Subir el ambiente docker-compose  
Pre requisitos 
Tener docker y docker compose instalado en el ambiente donde se ejecutará 

Declarar la variable de ambiente 
En Linux
```
export IMAGE_NAME=<nombre de una imagen de Docker>
```

En Windows
```
set IMAGE_NAME=<nombre de una imagen de Docker>
```

Nos ubicamos en la raíz del proyecto y se ejecuta lo siguiente
```
docker-compose up -d
```
Para bajar el ambiente 
```
docker-compose down
```

Para esta práctica el HOST será localhost.

Una vez el docker-compose levante todo los servicios y contenedores se podrá acceder a la petición, para esto se creó dos endpoints:

1. Genera el token JWT 

http://localhost/devops/generate-token

Request a enviar 
```
{
    "username": "Rita Asturia"
}
```

1. Envió de mensaje

http://localhost/devops

Request a enviar 
```
{
    "message": "This is a test",
    "to": "Juan Perez",
    "from": "Rita Asturia",
    "timeToLifeSec": 45
}
```
Envió de cabeceras 
```
X-Parse-REST-API-Key=2f5ae96c-b558-4c7b-a590-a501ae1c3f6c
X-JWT-KWY= <JWT GENERADO >
```

# Prueba ejecución con CURL 
```
curl -X POST \
-H "X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c" \
-H "X-JWT-KWY: $JWT" \
-H "Content-Type: application/json" \
-d '{ "message" : "This is a test", "to": "Juan Perez", "from": "Rita Asturia", "timeToLifeSec" : 45 }' \
http://localhost/devops
```

