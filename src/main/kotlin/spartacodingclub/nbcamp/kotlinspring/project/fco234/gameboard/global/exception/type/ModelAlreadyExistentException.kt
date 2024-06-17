package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type

data class ModelAlreadyExistentException (

    val modelName: String
) : IllegalArgumentException("Model already existent in $modelName")