# merimas

Acceso por linea de comandos del diccionario de rimas de http://www.cronopista.com/

La idea principal es usarlo en otros programas como editores de texto.
Y gracias a Eduardo Rodríguez Lorenzo por compartir su trabajo el cual me baso.

![terminal - xerron nemesis -documentos-diccionarios-merimas_008](https://cloud.githubusercontent.com/assets/1724033/6048607/7e151da6-ac7f-11e4-9ec5-611d59ccbef8.png)

## Uso

Obtener rimas con los parametros por defecto:

    java -jar merimas.jar -w <palabra>

Mostrar la ayuda:

    java -jar merimas.jar -h

![seleccion_007](https://cloud.githubusercontent.com/assets/1724033/6047597/b4799d92-ac78-11e4-9422-f6fd020a502d.png)

Configuración manual en el archivo **merimas.properties**


## Notas para noveles

La estructura de la aplicación debe ser la siguiente:

     -DDBB
     -merimas.properties
     -merimas.jar (ejecutable creado)

Descargar binario [aquí](https://github.com/xerron/merimas/releases/latest).

## TODO

- [ ] Interfaz para configurar (merimas.properties)

## Licencia

MIT.

