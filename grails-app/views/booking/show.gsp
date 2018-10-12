<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'booking.label', default: 'Booking')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-booking" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <g:render template="/templates/nav"/>
        <div id="show-booking" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="booking" />
            <ol class="property-list booking">
                <g:if test="${roomList}">
                    <li class="fieldcontain">
                        <span id="rooms-label" class="property-label"><g:message code="booking.rooms.label" default="Rooms"/></span>
                        <div class="property-value" aria-labelledby="name-label">${roomList.join(',')}</div>
                    </li>
                </g:if>
                <g:if test="${extraList}">
                    <li class="fieldcontain">
                        <span id="extras-label" class="property-label"><g:message code="booking.extras.label" default="Extras"/></span>
                        <div class="property-value" aria-labelledby="name-label">${extraList.join(',')}</div>
                    </li>
                </g:if>
            </ol>
            <g:form resource="${this.booking}" method="DELETE">
                <fieldset class="buttons">
                    <g:link controller="booking" class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
                    <g:link class="edit" action="edit" resource="${this.booking}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
