# Sistema de riego
### Problemática a resolver
En los campos de la Patagonia, la utilización del agua para el riego de terrenos es económicamente costosa y de muy poco acceso, con lo cual ante la necesidad de realizar el riego de las plantaciones (cultivos) en los terrenos se necesita optimizar al máximo este recurso.

### Alcance
El proyecto a desarrollar consistirá en un sistema que le permitirá al usuario saber la cantidad de agua de riego para su parcela y la misma será determinada en función de factores climáticos y terrestres. Los factores climáticos serán obtenidos de los informes climáticos publicados por un servicio de meteorología, mientras que los terrestres serán provistos por el usuario del sistema. Los primeros son la radiación solar, la temperatura ambiental y la humedad del aire entre otros, mientras que los segundos son el tipo de tierra, el tipo de cultivo, la dimensión, ubicación geográfica y riego de las parcelas. El sistema le sugerirá al usuario la cantidad de agua de riego y el usuario tendrá que confirmar la cantidad de agua que utilizó para el riego de sus parcelas.

La cantidad de agua de riego será determinada utilizando la formula de Penman Monteith.

Se le proveerá al usuario un mapa para que seleccione su ubicación geográfica, la cual es necesaria para obtener los datos climáticos a los cuales se encuentran sometidas sus parcelas.

Se permitirá la administración de parcelas (ABMC), tipos de cultivo y tipos de suelo, lo cual le permitirá al usuario registrar sus respectivos datos. Esto hará posible que el sistema tenga un registro histórico de las plantaciones realizadas en las parcelas durante las temporadas.

A su vez, se tendrá un registro histórico del riego realizado, el cual será cargado por parte del usuario. Este registro es importante porque para determinar la cantidad de agua del nuevo riego se debe tener en cuenta el riego previo y los datos climáticos.

Se proveerá al usuario un informe estadístico de las parcelas, el cual le indicará en función de los nutrientes de la tierra, la cantidad de agua registrada y los tipos de suelo, los cultivos que mejor se desarrollan en ella.

El sistema contará con funcionalidad para un administrador que será el encargado de cargar los datos paramétricos de los tipos de cultivo y tipos de tierra.

Por último, tendrá un registro e ingreso de usuarios (login). Cada usuario podrá gestionar su información relacionada con sus parcelas con sólo registrarse en el sistema de forma gratuita.

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

- Será utilizado mediante un navegador web como Mozilla Firefox o Google Chrome, lo cual permitirá que sea utilizado por medio de un dispositivo móvil (celular inteligente, tablet, etc.) y no sólo mediante una computadora.  

- Será gratuito.
