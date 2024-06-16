package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.user.aop

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidBirthdayValidator::class])
annotation class ValidBirthday(
    val message: String = "생일은 현재 날짜보다 이전이어야 합니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)