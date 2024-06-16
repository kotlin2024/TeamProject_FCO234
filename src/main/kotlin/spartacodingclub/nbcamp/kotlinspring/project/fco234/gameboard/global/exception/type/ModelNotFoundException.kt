package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type

data class ModelNotFoundException (

    val modelName: String,
    val id: Long?
) : RuntimeException (
    message = "Model $modelName not found with given id: $id"
)
