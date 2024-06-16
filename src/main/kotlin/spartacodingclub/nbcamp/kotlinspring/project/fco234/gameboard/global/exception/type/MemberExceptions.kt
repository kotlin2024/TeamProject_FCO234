package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type

class UnauthorizedAccessException : IllegalAccessException("Unauthorized Access")

class MemberNotVerifiedException : IllegalAccessException("Member Not Verified")

class IncorrectPasswordException : IllegalArgumentException("Incorrect Password")

class EmailAlreadyOccupiedException : IllegalArgumentException("Email Already Occupied By Another Member")
class UsernameAlreadyOccupiedException : IllegalArgumentException("Username Already Occupied By Another Member")