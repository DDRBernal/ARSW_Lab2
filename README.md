### Escuela Colombiana de Ingeniería
### Arquitecturas de Software - ARSW
David Otalora Bernal

## Parte I

1)	En la primera parte al ejecutar el programa con un hilo Podemos observar que utiliza los todos los núcleos que tenga, en un principio llega al 70% en general.

![image](https://user-images.githubusercontent.com/46855679/185171795-c59e8148-95ab-4eac-aa10-a48ee0a5bd4c.png)

2)	Cuando dividimos la tarea en 3 hilos notamos un comportamiento similar, se usarán todos los núcleos, en general con un uso (al inicio) de más del 50%. El comportamiento es similar posiblemente porque la tarea es bastante simple.

![image](https://user-images.githubusercontent.com/46855679/185171841-3658bb3c-894e-4e96-87ff-f86259199d9b.png)

![image](https://user-images.githubusercontent.com/46855679/185171932-f054f3be-3b31-4d44-8bcd-999ba1c9ce50.png)

 
3)	.

## Parte III

1. Para eso agregamos el metodo join a la clase Thread para sincronizar el hilo que inicia la carrera con los demas hilos de los galgos.

![image](https://user-images.githubusercontent.com/46855679/185807792-9ff97e69-72d8-48bb-8231-f2efb12d8785.png)

2. La región crítica que encontramos en el programa está en la clase Galgo, en el metodo corra, cuando llamamos al metodo *setUltimaPosicionAlcanzada* de la clase RegistroLlegada, varios hilos estan estan modificando el valor de la variable *ultimaPosicionAlcanzada*, al suceder esto el valor final de la variable puede ser diferente al esperado.

![image](https://user-images.githubusercontent.com/46855679/185807798-0b282618-8f70-4ec1-b8ca-fb9469825527.png)

3. Para permitir que un solo hilo acceda a la vez a la región crítica usaremos la palabra reservada *synchronized* en donde usemos el metodo *setUltimaPosicionAlcanzada* (la imagen arriba).

El resultado se muestra de la siguiente manera:

![image](https://user-images.githubusercontent.com/46855679/185807839-b96acde7-50b0-4fee-a3ce-e257a0798849.png)

Gracias a esto el orden de llegada de los galgos se muestra de manera sincronizada

![image](https://user-images.githubusercontent.com/46855679/185807859-16edf04e-de9c-4103-ac42-13697c98bf6c.png)

Ademas muestra el mensaje final con el numero de galgos correcto.

![image](https://user-images.githubusercontent.com/46855679/185807928-041755e3-b768-4b6d-896a-d3dcfe429e80.png)

Antes se mostraba de la siguiente manera algunas veces (sin *synchronized*), cuando el numero total de galgos deberia ser el mismo siempre (en este caso 17).

![image](https://user-images.githubusercontent.com/46855679/185807864-88d21dc3-acea-4e03-ac49-c4c117c43a14.png)
