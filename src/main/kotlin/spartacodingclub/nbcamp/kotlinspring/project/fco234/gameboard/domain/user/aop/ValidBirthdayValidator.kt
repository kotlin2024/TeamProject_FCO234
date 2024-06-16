package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.aop

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ValidBirthdayValidator : ConstraintValidator<ValidBirthday, String> {

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        return try {
            val date = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            !date.isAfter(LocalDate.now())
        } catch (e: Exception) {
            false
        }
    }
}