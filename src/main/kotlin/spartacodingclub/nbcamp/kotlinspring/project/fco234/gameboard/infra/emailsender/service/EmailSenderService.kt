package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.emailsender.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service


@Service
class EmailSenderService @Autowired constructor (

    private val mailSender: JavaMailSender
) {

    fun sendEmail(
        to: String,
        subject: String,
        text: String
    ) {

        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text, true) // true로 설정하여 HTML 내용을 허용

        mailSender.send(message)
    }

}