package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type

data class InvalidCredentialException (

    override val message: String? = "The credentials is invalid"
) : RuntimeException (
    message = message
)
