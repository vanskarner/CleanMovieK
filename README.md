# CleanMovieK
Catálogo de peliculas con enfoque [Clean Arquitecture](https://github.com/vanskarner/CleanMovie/wiki)

https://github.com/user-attachments/assets/0305894a-9688-4699-98dd-028810d87a4d

## Arquitectura
Diagrama de componentes simplificado
| d1 | d2 |
| --- | --- |
| ![276390303-6d542b6d-1042-4447-be61-e38b3778539b](https://github.com/user-attachments/assets/b3cb07ea-7cda-4fae-959b-128bd0aa3bab) | ![276388571-b564c5b1-8308-4a17-b9b8-6f080f927248](https://github.com/user-attachments/assets/a6fa0019-3c77-4ea5-af87-2743efaef69b) |

## Pruebas
Las pruebas al componente películas estan completas, pero aún me falta las pruebas para la UI por el momento.

| Componente movie: persistencia local | Componente movie: dominio y persistencia remota |
| --- | --- |
| ![test1](https://github.com/user-attachments/assets/a8f2028e-26e8-4b1d-80b7-a8be793d18fb) | ![test2](https://github.com/user-attachments/assets/ad3d99c3-e576-45d4-862d-f97dd8a1177a) |

## Consideraciones
- Si quieres utilizar la aplicación, primero tienes que generar tu clave api de desarrollador en [Themoviedb API](https://www.themoviedb.org/settings/api). Luego, una vez generado, debe poner esa clave en el archivo `data.properties` que se encuentra en la carpeta features:
```properties
#Project properties
themoviedbApiKey=HERE_YOUR_KEY
```
- Para ejecutar cualquier tipo de prueba, no es necesaria una clave API.
