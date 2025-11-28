Cambios importantes en: 

--Errores de Casting: ahora GameObjectContainer es el que aisla a Mario de gameObjects, para evitar errores
(antes teniamos una mala implementacion que de casualidad funcionaba, teniamos varios Marios) 

--Errores en visibilidad: FileGameConfig ahora no tiene acceso al tipo de valores iniciales que maneja Game (cree una clase Initial values que 
aplica directamente en game)

--Errores de Multiples marios: Ahora Game no tiene atributo mario, todo se hace desde el contenedor , por lo que he eliminado varios metodos como setAsMainCharacter, etc.

--Refactorizacion para hacer el codigo mas legible y breve. Sobre todo en los metodos de clonar create filegameconfig etc.

--Finalmente aplique los cambios que propuso inicialmente el profe en la correccion de PR2


Falta: 
 --solo comportamiento opcional de mario (test opcional) .
