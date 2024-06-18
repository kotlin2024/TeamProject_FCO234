package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type

data class ModelNotFoundException (

    val modelName: String
) : RuntimeException ("Model $modelName not found")