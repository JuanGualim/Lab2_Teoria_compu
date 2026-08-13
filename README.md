# Laboratorio de Teoría de la Computación - Thompson y AFN

## Objetivo

El programa lee expresiones regulares en formato infix, reutiliza Shunting Yard
y el árbol sintáctico del laboratorio anterior, aplica el algoritmo de
Thompson, muestra el AFN resultante y simula una cadena `w` para decidir si
pertenece al lenguaje de la expresión.

## Funcionamiento general

```text
expresiones.txt
       ↓
expresión infix
       ↓
Shunting Yard
       ↓
expresión postfix
       ↓
árbol sintáctico con una pila
       ↓
algoritmo de Thompson
       ↓
AFN con transiciones epsilon
       ↓
visualización y simulación
       ↓
si / no
```

La concatenación implícita se hace explícita con el operador `.`. Por ejemplo,
`ab` se convierte primero en `a.b`.

La precedencia utilizada es:

1. `*`, `+` y `?` (mayor precedencia)
2. `.`
3. `|` (menor precedencia)

Los operadores `+` (uno o más) y `?` (cero o uno) se mantienen como nodos
unarios. Thompson los procesa directamente, sin modificar ni duplicar el árbol:

- `R+` obliga a recorrer `R` una vez y después permite regresar a su inicio.
- `R?` permite recorrer `R` o saltarlo mediante una transición `ε`.

## Estructura del proyecto

- `Main.java`: coordina la ejecución completa.
- `Archivo.java`: lee una expresión por línea.
- `ShuntingYard.java`: agrega concatenaciones y convierte de infix a postfix.
- `Nodo.java`: representa un nodo del árbol.
- `ArbolSintactico.java`: construye el árbol desde postfix con `Stack<Nodo>`.
- `VisualizadorArbol.java`: dibuja el árbol con Java Swing.
- `Estado.java`: representa un estado `q0`, `q1`, etc.
- `Transicion.java`: guarda origen, destino y símbolo.
- `AFN.java`: almacena los estados y transiciones de un AFN.
- `Thompson.java`: recorre el árbol y construye el AFN recursivamente.
- `SimuladorAFN.java`: calcula cierres epsilon y simula una cadena.
- `VisualizadorAFN.java`: dibuja estados, flechas y etiquetas con Swing.
- `expresiones.txt`: contiene las expresiones que se procesarán.

## Conversión con Shunting Yard

Los operandos pasan directamente a la salida postfix. Los operadores se
guardan temporalmente en una pila y salen de acuerdo con su precedencia. Los
paréntesis solamente controlan la agrupación y no aparecen en el postfix.

## Construcción del árbol

Se recorre el postfix de izquierda a derecha:

- Un operando crea una hoja y se coloca en la pila.
- Un operador unario (`*`, `+`, `?`) extrae un nodo y crea su padre.
- Un operador binario (`.`, `|`) extrae primero el hijo derecho y después el
  izquierdo, crea el operador padre y lo coloca en la pila.

Al terminar debe quedar exactamente un nodo en la pila: la raíz del árbol.

## Construcción y simulación del AFN

Thompson crea fragmentos pequeños para operandos, concatenación, unión,
cerradura de Kleene, cerradura positiva y operador opcional. Cada expresión
reinicia la numeración de sus estados en `q0`.

La simulación comienza con el cierre epsilon del estado inicial. Para cada
símbolo de `w`, sigue las transiciones coincidentes y vuelve a calcular el
cierre epsilon. La cadena es aceptada si al terminar se alcanza el estado de
aceptación.

## Expresiones utilizadas

```text
(a*|b*)+
((ε|a)|b*)*
(a|b)*abb(a|b)*
0?(1?)?0*
```

## Requisitos

- JDK 17 o superior.
- No se requieren librerías externas; la visualización utiliza Java Swing.

## Compilación y ejecución

Desde la carpeta del proyecto:

```bash
javac *.java
java Main
```

También se puede indicar otro archivo:

```bash
java Main otro_archivo.txt
```

Después de construir cada AFN, el programa muestra:

```text
Ingrese una cadena w para probar:
```

Se escribe la cadena y se presiona Enter. Para probar la cadena vacía solamente
se presiona Enter; las transiciones `ε` no consumen ningún carácter.

Java Swing se utiliza para visualizar tanto el árbol como el AFN. En un entorno
sin interfaz gráfica se mantienen disponibles todas las representaciones de
consola y solamente se omiten las ventanas.

## Video

VIDEO: https://youtu.be/UkO3AJwDAuk 
