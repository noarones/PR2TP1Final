Cambios importantes en: 

--Errores de Casting: ahora GameObjectContainer es el que aisla a Mario de gameObjects, para evitar errores
(antes teniamos una mala implementacion que de casualidad funcionaba, teniamos varios Marios) 

--Errores en visibilidad: FileGameConfig ahora no tiene acceso al tipo de valores iniciales que maneja Game (cree una clase Initial values que 
aplica directamente en game)

--Errores de Multiples marios: Ahora Game no tiene atributo mario, todo se hace desde el contenedor , por lo que he eliminado varios metodos como setAsMainCharacter, etc.

--Refactorizacion para hacer el codigo mas legible y breve. Sobre todo en los metodos de clonar create filegameconfig etc.

--Finalmente aplique los cambios que propuso inicialmente el profe en la correccion de PR2

--Clase Mario reorganizada , quitando logica innecesaria

--IMPORTANTE: Se ha reducido Game y se ha utilizado una factoria de niveles para inicializar los mapas. Por si en el examen piden crear un mapa nuevo o algo asi.

--Ahora se utiliza una interfaz para el Mario que se ve desde container MarioPlayer.

--NuevaInterfaz GameStatusWriter que hereda de GameStatus para poder aplicar cambios en los atributos de game. 

Falta: 
 --solo comportamiento opcional de mario (test opcional) .
