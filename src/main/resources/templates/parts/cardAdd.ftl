<#include "security.ftl">
<#if userChannel?? && isCurrentUser?? && !isCurrentUser>
    <div class="form-group mt-3">
        <form method="post" action="/ask">
            <div class="form-group">
                <input type="text"
                       class="form-control "
                       name="question"
                       placeholder="Your question"/>
                <#if questionError??>
                    <div class="invalid-feedback">
                        ${questionError}
                    </div>
                </#if>
            </div>
            <input type="hidden" name="reciever" value="${userChannel.id}">
            <input type="hidden" name="channel" value="${userChannel.id}">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Ask question</button>
        </form>
    </div>
</#if>
