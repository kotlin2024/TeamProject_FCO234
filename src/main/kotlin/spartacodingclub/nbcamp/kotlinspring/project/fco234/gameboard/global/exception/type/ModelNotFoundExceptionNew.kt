package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type

data class ModelNotFoundExceptionNew (

    val modelName: String
) : RuntimeException ("Model $modelName not found")