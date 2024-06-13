package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.exception

data class InvalidCredentialException(
    override val message: String? = "The credentials is invalid"

): RuntimeException(message)
