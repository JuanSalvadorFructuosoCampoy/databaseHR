GESTIÓN DE LA BASE DE DATOS HR
-------------------------------

El presente proyecto consiste en la parte backend de una aplicación que permite gestionar la base de datos HR (una base de datos gratuita que proporciona Oracle con fines académicos).

Dicho proyecto permite hacer el CRUD de todas las tablas de la base de datos: Employees, Departments, Locations, Countries, Regions, Jobs y Job History. En la siguiente enlace podemos ver el esquema de la base de datos HR: 

![hr_schema](https://github.com/user-attachments/assets/ada19b76-d04a-4550-82f6-4188e3820642)

Al cambiar el trabajo de un empleado se cambia automáticamente la tabla de Job_History, y cuando borramos un registro de una tabla, se borran en cascada todos los elementos vinculados a ese registro.

El programa también tiene una gestión de excepciones, de tal manera que al intentar introducir valores erróneos o que no existen en la base de datos (por ejemplo, introducimos el nombre de un país no registrado), nos devolverá un mensaje de error.

Finalmente, se han elaborado tests de la aplicación usando Mockito. Dichos tests proporcionan una cobertura casi del 100% (y con el mínimo número de fallos de SonarLint).

Para la elaboración del proyecto, se han comprobado los resultados del mismo con Postman y Swagger.

Acceso a la base de datos HR:
https://github.com/bbrumm/databasestar/tree/main/sample_databases/oracle_hr

