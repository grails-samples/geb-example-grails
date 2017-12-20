<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'booking.label', default: 'Booking')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-booking" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <g:render template="/templates/nav"/>
        <div id="create-booking" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.booking}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.booking}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.booking}" method="POST">
                <fieldset class="form">
                    <f:all bean="booking"/>

                    <g:if test="${roomList}">
                        <div class="fieldcontain">
                            <label for="rooms"><g:message code="booking.rooms.label" default="Rooms" /></label>
                            <ol class="property-list">
                                <g:each var="room" in="${roomList}">
                                    <li class="fieldcontain">
                                        <span class="property-label">${room.name}</span>
                                        <span>&nbsp;</span>
                                        <g:checkBox name="rooms" value="${room.id}" checked="false" />
                                    </li>
                                </g:each>
                            </ol>
                        </div>
                    </g:if>

                    <g:if test="${extraList}">
                        <div class="fieldcontain">
                            <label for="extras"><g:message code="booking.extras.label" default="Extras" /></label>
                            <ol class="property-list">
                                <g:each var="extra" in="${extraList}">
                                    <li class="fieldcontain">
                                        <span class="property-label">${extra.name}</span>
                                        <span>&nbsp;</span>
                                        <g:checkBox name="extras" value="${extra.id}" checked="false" />
                                    </li>
                                </g:each>
                            </ol>
                        </div>
                    </g:if>

                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
