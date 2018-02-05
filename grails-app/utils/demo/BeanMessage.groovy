package demo

import groovy.transform.CompileStatic
import org.springframework.context.MessageSource
import org.springframework.validation.ObjectError
import grails.validation.ValidationException

@CompileStatic
trait BeanMessage {

    List<String> beanMessage(ValidationException bean, MessageSource messageSource, Locale locale = Locale.getDefault()) {
        List<String> errorMsgs = []

            for (ObjectError error in bean.errors.allErrors) {
                errorMsgs << messageSource.getMessage(error, locale)
            }

        errorMsgs
    }
}
