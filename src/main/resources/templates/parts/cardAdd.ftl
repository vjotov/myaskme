<#include "security.ftl">
<#if userChannel?? && isCurrentUser?? && !isCurrentUser>
    <div class="form-group mt-3">
        <form method="post" action="/ask">
            <div class="form-group">
                <input type="text" class="form-control" name="question">
            </div>

            <input type="hidden" name="receiver_id" value="${userChannel.id}">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Ask question</button>
        </form>
    </div>
</#if>
