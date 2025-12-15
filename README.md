Cambios importantes en: 

--Movimiento de objetos: 
Los objetos(en especial Mario) antes de verificar si pueden moverse hacen un TryHit, 
es decir se mueven provisionalmente checkean interacciones y vuelven a su sitio para de nuevo comprobar si pueden moverse, si es asi se moveran sino no. 
De esta forma se interactua con objetos sin atravesarlos(Caso box, tienes que activarlo sin atravesarlo, goomba mismo comportamiento) 

--Movimiento general automatico: Se ha anadido metodo canMove y su sobrecarga canMove(action, pos) para verificar si un objeto dada una posicion puede moverse en una direccion. 


