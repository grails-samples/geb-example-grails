package demo.controllers.allowedMethods


import spock.lang.Specification

class ExtraControllerAllowedMethodsSpec extends Specification  {

    def "test ExtraController.create accepts GET requests"() {
        expect:
        false
    }
}
