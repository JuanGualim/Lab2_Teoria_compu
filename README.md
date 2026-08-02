# Laboratorio 2 - Teoría de la Computación

**Universidad:** Universidad del Valle de Guatemala  
**Curso:** Teoría de la Computación  
**Laboratorio:** 2  
**Estudiante:** Juan Gabriel Gualim Molina

---

# Descripción

Este repositorio contiene las soluciones desarrolladas para el **Laboratorio 2** del curso de Teoría de la Computación.

Cada problema se encuentra desarrollado en una rama (branch) diferente del repositorio, tal como fue solicitado en el laboratorio.

---

# Problema 2 - Balanceo de Expresiones

## Descripción

Este programa implementa un algoritmo que verifica si una expresión se encuentra correctamente balanceada utilizando una **pila (Stack)**.

El programa:

- Lee un archivo de texto.
- Procesa una expresión por línea.
- Utiliza una pila para verificar los símbolos de apertura y cierre.
- Muestra paso a paso las operaciones realizadas sobre la pila.
- Indica si cada expresión está balanceada o no.

### Funcionalidades

- Lectura de archivo de texto.
- Validación de:
  - ()
  - []
  - {}
- Uso de la estructura de datos **Stack**.
- Visualización del proceso de validación.
- Resumen de resultados al finalizar.

---

# Problema 3 - Algoritmo de Shunting Yard

## Descripción

Este programa implementa el algoritmo **Shunting Yard**, el cual convierte expresiones regulares del formato **Infix** al formato **Postfix**.

El programa:

- Lee un archivo de texto.
- Procesa una expresión regular por línea.
- Inserta automáticamente los operadores de concatenación cuando es necesario.
- Utiliza una pila para manejar los operadores según su precedencia.
- Muestra el proceso completo de conversión.
- Genera la expresión equivalente en formato **Postfix**.

### Operadores soportados

- Unión (`|`)
- Concatenación (`.`)
- Cerradura de Kleene (`*`)
- Uno o más (`+`)
- Opcional (`?`)
- Paréntesis (`()`)

---

# Estructura del proyecto

## Problema 2

- Main.java
- Archivo.java
- Balanceador.java
- Resultado.java
- Utilidades.java
- expresiones.txt

---

## Problema 3

- Main.java
- Archivo.java
- ShuntingYard.java
- expresiones.txt

---

# Requisitos

- Java 17 o superior
- JDK instalado
- Consola o IDE compatible (IntelliJ IDEA, Eclipse, NetBeans o VS Code)

---

# Compilación

Compilar:

```bash
javac *.java
```

Ejecutar:

```bash
java Main
```

---

# Video de demostración

Enlace al video de YouTube (No listado):

**Link:**  
```
https://youtu.be/gG1-wc7aCBQ
```

---

# Autor

**Juan Gabriel Gualim Molina**

Laboratorio 2  
Teoría de la Computación