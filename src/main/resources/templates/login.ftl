<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
<div class="alert alert-danger" role="alert">
    ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
</div>
</#if>
<@c.page>
<#if message??>
<div class="alert alert-${messageType}" role="alert">
    ${message}
</div>
</#if>
<@l.login "/login" false />
</@c.page>