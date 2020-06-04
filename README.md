# Sistema web para el cálculo del agua de riego
### Problemática a resolver
En los campos de la Patagonia, la utilización del agua para el riego de terrenos es económicamente costosa y de muy poco acceso, con lo cual ante la necesidad de realizar el riego de las plantaciones (cultivos) se necesita optimizar al máximo este recurso.

### Alcance
El proyecto a desarrollar consistirá en un sistema que le permitirá al usuario saber la cantidad de agua de riego para su parcela y la misma será determinada en función de factores climáticos y terrestres. Los factores climáticos serán obtenidos de los informes climáticos publicados por un servicio de meteorología, mientras que los terrestres serán provistos por el usuario del sistema. Los primeros son la radiación solar, la temperatura ambiental y la humedad del aire entre otros, mientras que los segundos son el tipo de tierra, el cultivo, la dimensión, la ubicación geográfica y el riego de las parcelas. El sistema le sugerirá al usuario la cantidad de agua de riego y el usuario tendrá que confirmar la cantidad de agua que utilizó para el riego de sus parcelas.

La cantidad de agua de riego será determinada utilizando la formula FAO (Organización de las Naciones Unidas para la Alimentación y la Agricultura, ONUAA, o más conocida como FAO) Penman Monteith.

El sistema contará con funcionalidad de administrador que será el encargado de cargar los datos paramétricos de los cultivos y tipos de tierra.

Se le proveerá al usuario cliente un mapa para que seleccione su ubicación geográfica, la cual es necesaria para obtener los datos climáticos a los cuales se encuentran sometidas sus parcelas.

Al usuario cliente se le proveerá la administración (ABMC) de sus parcelas, lo cual le permitirá registrar y modificar los respectivos de sus parcelas. Esto hará posible que el sistema tenga un registro histórico de las plantaciones realizadas en las parcelas. En cambio, al usuario administrador se le proveerá la administración (ABMC) de cultivos y tipos de suelo, lo cual le permitirá registrar y modificar los respectivos datos de estas dos entidades.

A su vez, se tendrá un registro histórico del riego realizado, el cual podrá ser modificado únicamente por el usuario cliente. Este registro es importante porque para determinar la cantidad de agua del nuevo riego se debe tener en cuenta el riego previo y los datos climáticos.

Se proveerá al usuario cliente la función de generar un informe estadístico de sus parcelas, el cual le indicará los siguientes datos con respecto a una persona en particular: Cultivos más plantados, cultivos menos plantados, cultivos que más tiempo estuvieron plantados, cultivos que menos tiempo estuvieron plantados y el tiempo en el que una parcela estuvo sin cultivos plantados.

Por último, tendrá un registro e ingreso de usuarios cliente (login), con lo cual cada usuario podrá administrar los datos de sus parcelas, además de también administrar sus datos personales.

### Características funcionales del sistema
- Determinar la cantidad de agua para riego.

- Extracción automática de datos meteorológicos publicados por un servicio de meteorología.

- Mapa para la elección de la ubicación geográfica.

- Registro de parcelas.

- Registro de tipos de cultivo.

- Registro de tipos de tierra.

- Proveer un registro histórico de lo que se ha hecho con las parcelas.

- Registro de riego realizado.

- Informes estadísticos de las parcelas.

- Ingreso y uso del sistema como administrador.

- Registro e ingreso de usuarios.

### Requerimientos no funcionales
El sistema:  
- Será multi usuario.  

- Será utilizado mediante un navegador web como Mozilla Firefox o Google Chrome, lo cual permitirá que sea utilizado por medio de un dispositivo móvil (celular inteligente, tableta, etc.) y no sólo mediante una computadora.  

- Será gratuito.
