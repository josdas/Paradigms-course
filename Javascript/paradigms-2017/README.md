Тесты к курсу «Парадигмы программирования»
====

[Условия домашних заданий](http://www.kgeorgiy.info/courses/paradigms/homeworks.html)

Домашнее задание 10. Объектные выражения на JavaScript
---
 * *Базовая*
    * Код должен находиться в файле `objectExpression.js`.
    * [Исходный код тестов](javascript/test/ObjectExpressionTest.java)
        * Запускать c аргументом `easy`, `hard` или `bonus`.


Домашнее задание 9. Функциональные выражения на JavaScript
---
 * *Усложненная*. Дополнительное реализовать поддержку:
    * констант:
        * `pi` — π;
        * `e` — основание натурального логарифма;
    * переменных `x`, `y`, `z`;
    * операций:
        * `min3` — минимальный из трех элементов, `3 1 4 min3` равно 1;
        * `max5` — максимальный из пяти элементов, `3 1 4 0 2 max5` равно 4.
    * [Исходный код тестов](javascript/test/FunctionalMinMaxTest.java)
        * Запускать c аргументом `hard` или `easy`
 * *Простая 2*. Дополнительное реализовать поддержку:
    * констант:
        * `pi` — π;
        * `e` — основание натурального логарифма;
    * переменных-литералов `x`, `y`, `z`;
    * [Исходный код тестов](javascript/test/FunctionalPieTest.java)
        * Запускать c аргументом `hard` или `easy`
 * *Простая*. Дополнительное реализовать поддержку:
    * переменных `y`, `z`, `u`, `v`, `w`;
    * [Исходный код тестов](javascript/test/FunctionalVariablesTest.java)
        * Запускать c аргументом `hard` или `easy`.
 * *Базовая*
    * Код должен находиться в файле `functionalExpression.js`.
    * [Исходный код тестов](javascript/test/FunctionalExpressionTest.java)
        * Запускать c аргументом `hard` или `easy`;

Исходный код к лекции по JavaScript
----
* [Скрипт с примерами](javascript/script.js)
* [Запуск в браузере](javascript/RunJS.html)
* [Запуск из консоли](javascript/RunJS.java) (на Java)
* [Запуск из консоли](javascript/RunJS.js) (на node.js)

Обратите внимание на реализацию функции `mCurry` в разделе про 
[функции высшего порядка](javascript/examples/functions-hi.js).

Домашнее задание 8. Вычисление в различных типах
---
 * *Усложненная*
    * Реализовать операции из простого варианта.
    * Дополнительно реализовать поддержку режимов:
        * `u` — вычисления в `int` без проверки на переполнение;
        * `b` — вычисления в `byte` без проверки на переполнение;
        * `f` — вычисления в `float` без проверки на переполнение.
    * [Исходный код тестов](java/expression/generic/GenericHardTest.java)
 * *Простая*
    * Дополнительно реализовать унарные операции:
        * `abs` — модуль числа, `abs -5` равно 5;
        * `square` — возведение в квадрат, `square 5` равно 25.
    * Дополнительно реализовать бинарную операцию (максимальный приоритет):
        * `mod` — взятие по модулю, приоритет как у умножения (`1 + 5 mod 3` равно `1 + (5 mod 3)` равно `3`).
    * [Исходный код тестов](java/expression/generic/GenericEasyTest.java)
 * *Базовая*
    * Класс `GenericTabulator` должен реализовывать интерфейс 
      [Tabulator](java/expression/generic/Tabulator.java) и
      сроить трехмерную таблицу значений заданного выражения.
        * `mode` — режим вычислений:
           * `i` — вычисления в `int` с проверкой на переполнение;
           * `d` — вычисления в `double` без проверки на переполнение;
           * `bi` — вычисления в `BigInteger`.
        * `expression` — выражение, для которого надо построить таблицу;
        * `x1`, `x2` — минимальное и максимальное значения переменной `x` (включительно)
        * `y1`, `y2`, `z1`, `z2` — аналогично для `y` и `z`.
        * Результат: элемент `result[i][j][k]` должен содержать
          значение выражения для `x = x1 + i`, `y = y1 + j`, `z = z1 + k`.
          Если значение не определено (например, по причине переполнения),
          то соответствующий элемент должен быть равен `null`.
    * [Исходный код тестов](java/expression/generic/GenericTest.java)

Домашнее задание 7. Обработка ошибок
---
 * *Усложненная*
    * Реализовать операции из второго простого варианта.
    * Дополнительно реализовать бинарные операции (минимальный приоритет):
        * `min` — минимум, `2 min 3` равно 2;
        * `max` — максимум, `2 max 3` равно 3.
    * [Исходный код тестов](java/expression/exceptions/ExceptionsMinMaxTest.java)
 * *Простая*
    * Дополнительно реализовать унарные операции:
        * `log2` — логарифм по уснованию 2, `log2 10` равно 3;
        * `pow2` — два в степени, `pow2 4` равно 16.
    * [Исходный код тестов](java/expression/exceptions/ExceptionsLog2Test.java)
 * *Простая 2* 
    * Дополнительно реализовать унарные операции:
        * `abs` — модуль числа, `abs -5` равно 5;
        * `sqrt` — квадратный корень, `sqrt 24` равно 4.
    * [Исходный код тестов](java/expression/exceptions/ExceptionsAbsTest.java)
 * *Базовая*
    * Класс `ExpressionParser` должен реализовывать интерфейс 
        [Parser](java/expression/exceptions/Parser.java)
    * Классы `CheckedAdd`, `CheckedSubtract`, `CheckedMultiply`, 
        `CheckedDivide` и `CheckedNegate` должны реализовывать интерфейс 
        [TripleExpression](java/expression/TripleExpression.java)
    * Нельзя использовать типы `long` и `double`
    * Нельзя использовать методы классов `Math` и `StrictMath`
    * [Исходный код тестов](java/expression/exceptions/ExceptionsTest.java)


Домашнее задание 6. Разбор выражений
---
 * *Простая*
    * Дополнительно реализовать бинарные операции:
        * `<<` — сдвиг влево, минимальный приоритет (`1 << 5 + 3` равно `1 << (5 + 3)` равно 256);
        * `>>` — сдвиг вправо, минимальный приоритет (`1024 >> 5 + 3` равно `1024 >> (5 + 3)` равно 4);
    * [Исходный код тестов](java/expression/parser/ParserShiftsTest.java)
 * *Простая 2*
    * Дополнительно реализовать бинарные операции:
        * `&` — побитное И, приоритет меньше чем у `+` (`6 & 1 + 2` равно `6 & (1 + 2)` равно 2);
        * `^` — побитный XOR, приоритет меньше чем у `&` (`6 ^ 1 + 2` равно `6 ^ (1 + 2)` равно 5);
        * `|` — побитное ИЛИ, приоритет меньше чем у `^` (`6 | 1 + 2` равно `6 | (1 + 2)` равно 7);
    * [Исходный код тестов](java/expression/parser/ParserBitwiseTest.java)
 * *Усложненная*
    * Реализовать операции из простого варианта.
    * Дополнительно реализовать унарные операции (приоритет как у унарного минуса):
        * `abs` — модуль числа, `abs -5` равно 5;
        * `square` — возведение в квадрат, `square -5` равно 25.
    * [Исходный код тестов](java/expression/parser/ParserHardTest.java)
 * *Базовая*
    * Класс `ExpressionParser` должен реализовывать интерфейс 
        [Parser](java/expression/parser/Parser.java)
    * Результат разбора должен реализовывать интерфейс 
        [TripleExpression](java/expression/TripleExpression.java)
    * [Исходный код тестов](java/expression/parser/ParserTest.java)

Домашнее задание 5. Вычисление выражений
---
 * *Базовая*
    * Реализовать интерфейс [Expression](java/expression/Expression.java)
    * [Исходный код тестов](java/expression/ExpressionTest.java)
 * *Простая*
    * Реализовать интерфейс [DoubleExpression](java/expression/DoubleExpression.java)
    * [Исходный код тестов](java/expression/DoubleExpressionTest.java)
 * *Усложненная*
    * Реализовать интерфейсы [DoubleExpression](java/expression/DoubleExpression.java) и [TripleExpression](java/expression/TripleExpression.java)
    * [Исходный код тестов](java/expression/TripleExpressionTest.java)

Домашнее задание 4. Очередь на связном списке
---
 * *Базовая*
    * [Исходный код тестов](java/queue/QueueTest.java)
    * [Откомпилированные тесты](artifacts/queue/QueueTest.jar)
 * *Простая*
    * Добавить в интерфейс очереди и реализовать метод
      `toArray`, возвращающий массив,
      содержащий элементы, лежащие в очереди в порядке
      от головы к хвосту
    * Исходная очередь должна оставаться неизменной
    * Дублирования кода быть не должно
    * [Исходный код тестов](java/queue/QueueToArrayTest.java)
    * [Откомпилированные тесты](artifacts/queue/QueueToArrayTest.jar)
 * *Усложненная*
    * Добавить в интерфейс очереди и реализовать методы
        * `filter(predicate)` – создать очередь, содержащую элементы, удовлетворяющие 
            [предикату](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)
        * `map(function)` – создать очередь, содержащую результаты применения 
            [функции](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html)
    * Исходная очередь должна остаться неизменной
    * Тип возвращаемой очереди должен соответствовать типу исходной очереди
    * Взаимный порядок элементов должен сохраняться
    * Дублирования кода быть не должно
    * [Исходный код тестов](java/queue/QueueFunctions.java)
    * [Откомпилированные тесты](artifacts/queue/QueueFunctionsTest.jar)

Домашнее задание 3. Очередь на массиве
---
Модификации
 * *Базовая*
    * [Исходный код тестов](java/queue/ArrayQueueTest.java)
    * [Откомпилированные тесты](artifacts/queue/ArrayQueueTest.jar)
 * *Простая*
    * Реализовать метод `toArray`, возвращающий массив,
      содержащий элементы, лежащие в очереди в порядке
      от головы к хвосту.
    * Исходная очередь должна остаться неизменной
    * Дублирования кода быть не должно
    * [Исходный код тестов](java/queue/ArrayQueueToArrayTest.java)
    * [Откомпилированные тесты](artifacts/queue/ArrayQueueToArrayTest.jar)
 * *Простая 2*
    * Реализовать метод `toStr`, возвращающий сроковое представление
      очереди в виде '`[`' _голова_ '`, `' ... '`, `' _хвост_ '`]`'
    * Для получения строкового представления элементов используйте
      метод `toString`.
    * [Исходный код тестов](java/queue/ArrayQueueToStrTest.java)
    * [Откомпилированные тесты](artifacts/queue/ArrayQueueToStrTest.jar)
 * *Усложненная*
    * Реализовать методы
        * `push` – добавить элемент в начало очереди
        * `peek` – вернуть последний элемент в очереди
        * `remove` – вернуть и удалить последний элемент из очереди
    * [Исходный код тестов](java/queue/ArrayQueueDequeTest.java)
    * [Откомпилированные тесты](artifacts/queue/ArrayQueueDequeTest.jar)


Домашнее задание 2. Бинарный поиск
----

Модификации
 * *Базовая*
    * [Исходный код тестов](java/search/BinarySearchTest.java)
    * [Откомпилированные тесты](artifacts/search/BinarySearchTest.jar)
 * *Простая*
    * Если в массиве `a` отсутствует элемент, равный `x`, то требуется
      вывести индекс вставки в формате, определенном в 
      [`Arrays.binarySearch`](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#binarySearch-int:A-int-).
    * Класс должен иметь имя `BinarySearchMissing`
    * [Исходный код тестов](java/search/BinarySearchMissingTest.java)
    * [Откомпилированные тесты](artifacts/search/BinarySearchMissingTest.jar)
 * *Усложненная*
    * Требуется вывести два числа: начало и длину диапазона элементов,
      равных `x`. Если таких элементов нет, то следует вывести
      пустой диапазон, у которого левая граница совпадает с местом
      вставки элемента `x`.
    * Не допускается использование типов `long` и `BigInteger`.
    * Класс должен иметь имя `BinarySearchSpan`
    * [Исходный код тестов](java/search/BinarySearchSpanTest.java)
    * [Откомпилированные тесты](artifacts/search/BinarySearchSpanTest.jar)


Домашнее задание 1. Сумма чисел
----

Модификации
 * *Простая*
    * Входные данные помещаются в тип `long`
    * Класс должен иметь имя `SumLong`
    * [Исходный код тестов](java/sum/SumLongTest.java)
    * [Откомпилированные тесты](artifacts/sum/SumLongTest.jar)
 * *Усложненная*
    * Входные данные помещаются в тип `long`
    * На вход подаются десятичные и шестнадцатеричные числа
    * Шестнадцатеричные числа имеют префикс `0x`
    * Ввод регистронезависим
    * Класс должен иметь имя `SumLongHex`
    * [Исходный код тестов](java/sum/SumLongHexTest.java)
    * [Откомпилированные тесты](artifacts/sum/SumLongHexTest.jar)

Для того, чтобы протестировать исходную программу:

 1. Скачайте откомпилированные тесты ([SumTest.jar](artifacts/sum/SumTest.jar))
 * Откомпилируйте `Sum.java`
 * Проверьте, что создался `Sum.class`
 * В каталоге, в котором находится `Sum.class` выполните команду 
    ```
       java -jar <путь к SumTest.jar>
    ```
    * Например, если `SumTest.jar` находится в текущем каталоге, выполните команду 
    ```
        java -jar SumTest.jar
    ```
    
Исходный код тестов: 

* [SumTest.java](java/sum/SumTest.java), 
* [SumChecker.java](java/sum/SumChecker.java)
