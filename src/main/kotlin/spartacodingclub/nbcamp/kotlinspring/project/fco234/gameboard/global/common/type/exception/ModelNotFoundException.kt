package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.exception

data class ModelNotFoundException(val modelName: String, val id: Long?) : RuntimeException(
    "Model $modelName not found with given id: $id"
)
