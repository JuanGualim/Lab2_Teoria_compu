# Laboratorio de Teoría de la Computación - Árbol sintáctico

## Objetivo

El programa lee expresiones regulares en formato infix, las convierte a
postfix con el algoritmo de Shunting Yard y construye un árbol sintáctico para
cada una. El árbol se imprime en la consola y también se dibuja en una ventana
de Java Swing.

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
representación textual y gráfica
```

La concatenación implícita se hace explícita con el operador `.`. Por ejemplo,
`ab` se convierte primero en `a.b`.

La precedencia utilizada es:

1. `*`, `+` y `?` (mayor precedencia)
2. `.`
3. `|` (menor precedencia)

Los operadores `+` (uno o más) y `?` (cero o uno) se mantienen como nodos
unarios. No se expanden, para conservar sencilla la construcción del árbol.

## Estructura del proyecto

- `Main.java`: coordina la ejecución completa.
- `Archivo.java`: lee una expresión por línea.
- `ShuntingYard.java`: agrega concatenaciones y convierte de infix a postfix.
- `Nodo.java`: representa un nodo del árbol.
- `ArbolSintactico.java`: construye el árbol desde postfix con `Stack<Nodo>`.
- `VisualizadorArbol.java`: dibuja el árbol con Java Swing.
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


## Video

VIDEO: https://youtu.be/fga5GMPrHn8 
