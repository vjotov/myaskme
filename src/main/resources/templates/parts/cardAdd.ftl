<#include "security.ftl">

<form method="post" action="ask">
    <input type="text" name="question">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary">Ask question</button>
</form>
