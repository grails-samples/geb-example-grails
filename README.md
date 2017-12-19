# Example of Spock & Grails integration 

The Spock tests [RoomCRUDSpec](https://github.com/grails-samples/geb-example-grails/blob/master/src/integration-test/groovy/com/test/RoomCRUDSpec.groovy#L17),
[ExtraCRUDSpec](https://github.com/grails-samples/geb-example-grails/blob/master/src/integration-test/groovy/com/test/ExtraCRUDSpec.groovy#L15),
[BookingCRUDSpec](https://github.com/grails-samples/geb-example-grails/blob/master/src/integration-test/groovy/com/test/BookingCRUDSpec.groovy#L15), 
used in this project extend from `geb.spock.GebReportingSpec`.

This superclass is shipped by Geb Spock integration. This superclass automatically takes 
reports at the end of test methods with the label “end”. They also set the report group 
to the name of the test class (substituting “.” with “/”).

The build reports directory is [defined in build.gradle](https://github.com/grails-samples/geb-example-grails/blob/master/build.gradle#L81)

To allow integration tests to be run without Geb Specifications, `RoomCRUDSpec`, `ExtraCRUDSpec` and `BookingCRUDSpec` are annotated with: 

`@IgnoreIf({ !System.getProperty('geb.env') })`
 
The application uses [Geb Pages and Modules](https://github.com/grails-samples/geb-example-grails/tree/master/src/integration-test/groovy/com/test/pages) to faciliate test maintainability.

> The Page Object Pattern allows us to apply the same principles of modularity, reuse and encapsulation that we use in other aspects of programming to avoid such issues in browser automation code. 