El presente proyecto permite gestionar la base de datos HR usando Java con Spring Boot.
Se puede obtener una copia de la base de datos HR con los scripts SQL que se encuentran en este enlace:
https://github.com/bbrumm/databasestar/tree/main/sample_databases/oracle_hr

Este proyecto permite la gestión de las siguientes tablas:
- Regions.
- Countries.
- Locations.
- Departments.
- Employees.
- Jobs.

También actualiza la tabla job_history cuando un empleado cambia de trabajo.

La vista emp_details_view puede visualizarse mediante este proyecto, pero no se realizan cambios directamente en la vista, ya que las vistas simplemente muestran los datos de la base de datos y son elementos de sólo lectura.

El proyecto convierte los datos de las tablas en un JSON, siendo por tanto una API que sirve de intermediario entre la base de datos y un frontend que le dé una interfaz a la aplicación.

Cabe destacar que el proyecto está habilitado para poder visualizarse tanto con Swagger como con Postman. En la elaboración del mismo se ha utilizado mayormente Swagger.

Se han mapeado las entidades del proyecto, usando DTOs, de tal manera que al visualizar las entidades en el JSON, en lugar de encontrarnos en algunos casos con IDs de otras entidades, en su lugar, podemos ver el nombre, haciendo más clara la lectura de los JSON, para facilitar su interpretación por parte del equipo de frontend.

Hay cobertura de excepciones, permitiendo así introducir valores erróneos y obtener mensajes personalizados que indiquen el error cometido.

Cobertura de errores del 97% de línea de código del proyecto, usando un total de 191 tests de Mockito.
