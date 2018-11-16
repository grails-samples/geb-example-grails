package demo.controllers

import spock.lang.Specification

class ExtraControllerSpec extends Specification {

    void 'Test the ExtraController.save redirects to /extra/show and contains flash.message'() {
        expect:
        false
    }

}
