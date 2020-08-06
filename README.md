# JAVA FUNCTIONAL FEATURES

## Expresiones lambda

Algunos conceptos previos:

**First class functions**

Cuando un lenguaje tiene funciones como elementos de primer nivel. También implica que una
función se puede asignar a una variable y se puede pasar como parametro a otras funciones o 
ser devuelta cdomo el resultado de una función.

Java es un lenguaje donde los elementos de primer nivel son las clases. Para introducir en java
algo similar a las funciones de primer nivel se han echo algunos truquillos usando interfaces
"funcionales" con un sólo método. Luego lo desarrollamos más cuando veamos las lambdas.

**High order functions**

Se dice que una función es de alto orden cuando alguno de sus parametros de entrada es una
función o bien cuando el valor de retorno es una función.

**Lambda expressions**

Son funciones anonimas con la siguiente sintaxis:

    (parametros) -> { cuerpo funcion }
    
***Parametros***

No es necesario usar parentesis cuando la funcion tenga un solo parametro
Si la funcion no recibe ningún parametro la sintaxis sera () -> { cuerpo funcion }
No es necesario indicar los tipos de los parametros ya que son inferidos por el compilador

***Cuerpo***

Si el cuerpo de la función tiene una única instrucción no es necesario usar llaves y tampoco
es necesario especificar la clausula "return"  

## Predefined functional interfaces

Por convenicencia, para no tener que declarar una interface cada vez que queramos escribir
un método que reciba una lambda, en java existen multitud de interfaces predefinidas que podemos
usar directamente:

BiConsumer<T,U>	        It represents an operation that accepts two input arguments and returns no result.

Consumer<T>	            It represents an operation that accepts a single argument and returns no result.

Function<T,R>	        It represents a function that accepts one argument and returns a result.

Predicate<T>	        It represents a predicate (boolean-valued function) of one argument.

BiFunction<T,U,R>	    It represents a function that accepts two arguments and returns a a result.

BinaryOperator<T>	    It represents an operation upon two operands of the same data type.
 It returns a result of the same type as the operands.
 
BiPredicate<T,U>	    It represents a predicate (boolean-valued function) of two arguments.

BooleanSupplier	        It represents a supplier of boolean-valued results.

DoubleBinaryOperator	It represents an operation upon two double type operands and returns a double type value.

DoubleConsumer	        It represents an operation that accepts a single double type argument and returns no result.

DoubleFunction<R>	    It represents a function that accepts a double type argument and produces a result.

DoublePredicate	        It represents a predicate (boolean-valued function) of one double type argument.

DoubleSupplier	        It represents a supplier of double type results.

DoubleToIntFunction	    It represents a function that accepts a double type argument and produces an int type result.

DoubleToLongFunction	It represents a function that accepts a double type argument and produces a long type result.

DoubleUnaryOperator	    It represents an operation on a single double type operand that produces a double type result.

IntBinaryOperator	    It represents an operation upon two int type operands and returns an int type result.

IntConsumer	            It represents an operation that accepts a single integer argument and returns no result.

IntFunction<R>	        It represents a function that accepts an integer argument and returns a result.

IntPredicate	        It represents a predicate (boolean-valued function) of one integer argument.

IntSupplier	            It represents a supplier of integer type.

IntToDoubleFunction	    It represents a function that accepts an integer argument and returns a double.

IntToLongFunction	    It represents a function that accepts an integer argument and returns a long.

IntUnaryOperator	    It represents an operation on a single integer operand that produces an integer result.

LongBinaryOperator	    It represents an operation upon two long type operands and returns a long type result.

LongConsumer	        It represents an operation that accepts a single long type argument and returns no result.

LongFunction<R>	        It represents a function that accepts a long type argument and returns a result.

LongPredicate	        It represents a predicate (boolean-valued function) of one long type argument.

LongSupplier	        It represents a supplier of long type results.

LongToDoubleFunction	It represents a function that accepts a long type argument and returns a result of double type.

LongToIntFunction	    It represents a function that accepts a long type argument and returns an integer result.

LongUnaryOperator	    It represents an operation on a single long type operand that returns a long type result.

ObjDoubleConsumer<T>	It represents an operation that accepts an object and a double argument, and returns no result.

ObjIntConsumer<T>	    It represents an operation that accepts an object and an integer argument.
 It does not return result.
 
ObjLongConsumer<T>	    It represents an operation that accepts an object and a long argument, it returns no result.

Supplier<T>	            It represents a supplier of results.

ToDoubleBiFunction<T,U>	It represents a function that accepts two arguments and produces a double type result.

ToDoubleFunction<T>	    It represents a function that returns a double type result.

ToIntBiFunction<T,U>	It represents a function that accepts two arguments and returns an integer.

ToIntFunction<T>	    It represents a function that returns an integer.

ToLongBiFunction<T,U>	It represents a function that accepts two arguments and returns a result of long type.

ToLongFunction<T>	    It represents a function that returns a result of long type.

UnaryOperator<T>	    It represents an operation on a single operand that returnsa a result of the same type as its operand.


## Referencias a métodos

Estamos acostumbrados a utilizar referencias a objetos. En java 8 es posible referenciar
directamente métodos, existen referencias de cuatro tipos:

referencias a metodos estáticos:

    Class::methodName
    
referencias a métodos de instancias existentes

    InstanceName::methodName

referencias a métodos de instancia

    Class:methodName

referencias a constructores

    Class:new
   
## Optional

En palabras del creador del concepto de nulo:

`I call it my billion-dollar mistake. It was the invention of the null reference in 1965. At that time, I was designing the first comprehensive type system for references in an object oriented language (ALGOL W). My goal was to ensure that all use of references should be absolutely safe, with checking performed automatically by the compiler. But I couldn't resist the temptation to put in a null reference, simply because it was so easy to implement. This has led to innumerable errors, vulnerabilities, and system crashes, which have probably caused a billion dollars of pain and damage in the last forty years.`

java 8 introduce la clase Optional como alternativa para lididar con los null, inspirado en lenguajes como haskell o scala que ya incluian este concepto.

Un Optional es simplemente un contenedor de un valor, una caja donde guardarlo, la gracia esta en que la caja
puede estar contener un valor o estar vacia y en que el optional incluye métodos para ejecutar cosas sólo 
cuando la caja esta llena. De esta forma evitas mucha lógica condicional y muchos riesgos de NullPointerException.

## Streams

El API de stream esta diseñado para permitir ejecutar una serie de operaciones sobre un cojunto de elementos y agregar sus resultados al estilo funcional.

Los streams no son colecciones para almacenar datos.

Una operación con un stream se compone de 3 partes:

    fuente de datos -> operaciones intermedias de modificación -> operation de terminacion

Una vez invocada una operación de terminación no se podrán hacer mas invocaciones sobre ese stream.

### Operaciones Intermedias

filter()
map()
flatMap()
distinct()
sorted()
peek()
limit()
skip()

### Operaciones Finales

toArray()
collect()
count()
reduce()
forEach()
forEachOrdered()
min()
max()
anyMatch()
allMatch()
noneMatch()
findAny()
findFirst()
    





  
