

import kotlin.io.readLine
import kotlin.math.PI
import kotlin.text.toDoubleOrNull
import kotlin.text.toDouble

class WrongOperationTypeException(message: String) : Exception(message)
class BadPropertyException() : Exception("Некорректное property")

abstract class Figure {
    abstract var property: Double
}
interface ConsoleService {
    fun work()
}
interface FigureService {
    fun addSquare(property: Double)
    fun addCircle(property: Double)
    fun getPerimeter()
    fun getArea()
}

internal data class Square(override var property : Double): Figure() {
    constructor() : this(0.0){
        println("Figure.constructor()->")
    }
    fun getPerimeter() : Double {
        return 4*property
    }
    fun getArea() : Double {
        return property*property
    }
}
internal data class Circle(override var property : Double): Figure() {
    constructor() : this(0.0){
        println("Figure.constructor()->")
    }
    fun getPerimeter() : Double {
        var perimeter = 2 * PI * property
        return perimeter
    }
    fun getArea() : Double {
        var area = PI * property * property
        return area
    }
}
enum class Operation {
    INSERT, GET_AREA, GET_PERIMETER, EXIT
}

object ConsoleServiceImpl : ConsoleService {
    val figures = FigureServiceImpl
    override fun work() {
        while(true) {
            try {
                println("Введите тип операции, которую хотите исполнить:\n1) добавить фигуру\n2) получить площадь всех фигур\n3) получить периметр всех фигур\n4) завершить выполнение")
                val operation = getOperation(readln())
                when (operation) {
                    Operation.INSERT -> addFigure()
                    Operation.GET_AREA -> getArea()
                    Operation.GET_PERIMETER -> getPerimeter()
                    Operation.EXIT -> break
                }
            } catch (e: WrongOperationTypeException){
                println("Непраильная команда")
            }
        }
    }
    private fun getOperation(operation: String) : Operation {
        return when (operation) {
            "1" -> Operation.INSERT
            "2" -> Operation.GET_AREA
            "3" -> Operation.GET_PERIMETER
            "4" -> Operation.EXIT
            else -> {
                throw WrongOperationTypeException("Wrong exception: $operation")
            }
        }
    }
    private fun addFigure() {
        println("Какую фигуру хотите добавить: \n 1) квадрат\n 2) круг")
        val figure = readLine()
        val parameter = readLine()?.toDoubleOrNull() ?: throw BadPropertyException()
        // val parameter : Double?
        // try {
        //     parameter = readline()?.toDoubleOrNull()
        // } catch (e BadPropertyException) {
        //     print(e.message)
        // }
        when (figure) {
            "1" -> figures.addSquare(parameter)
            "2" -> figures.addCircle(parameter)
            else -> {
                throw WrongOperationTypeException("Wrong exception: $figure")
            }
        }
        // val parameter = try {
        //      readLine()?.toDoubleOrNull()
        // } catch (e: BadPropertyException) {
        //     null
        // }
    }
    private fun getPerimeter() {
        figures.getPerimeter()
    }
    private fun getArea() {
        figures.getArea()
    }
}

object FigureServiceImpl : FigureService {
    private var figuresCircle = mutableListOf<Circle>()
    private var figuresSquare = mutableListOf<Square>()
    override fun addSquare(property: Double) {
        figuresSquare.add(Square(property))
    }
    override fun addCircle(property: Double) {
        figuresCircle.add(Circle(property))
    }
    override fun getPerimeter() {
        if (figuresCircle.isNotEmpty()) {
            println("Периметры окружностей: ")
            for (figure in figuresCircle) {
                print(figure.getPerimeter())
            }
        }
        if (figuresCircle.isNotEmpty()) {
            println("Периметры квадратов: ")
            for (figure in figuresSquare) {
                print(figure.getPerimeter())
            }
        }
    }
    override fun getArea() {
        if (figuresCircle.isNotEmpty()) {
            println("Площади кругов: ")
            for (figure in figuresCircle) {
                print(figure.getArea())
            }
        }
        if (figuresCircle.isNotEmpty()) {
            println("Площади квадратов: ")
            for (figure in figuresSquare) {
                print(figure.getArea())
            }
        }
    }
}
fun main(){
    val sq = Square(5.0)
    print(sq)
    val console = ConsoleServiceImpl.work()
}

