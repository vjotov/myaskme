<#include "security.ftl">
<div class="card-columns" id="cards-list">
<#list page as card>
    <div class="card my-3" data-id="${card.id}">
        <div class="card-body">
            <h5 class="card-title">question: ${card.question}</h5>
            <div class="">
                <span><i>author: ${card.author.username}</i></span><br/>
                <span>to: ${card.receiver.username}</span><br/>
                answer: <#if card.answer??>${card.answer}</#if>
            </div>
            <#if card.receiver.id == currentUserId && !card.answer??>
                <div class="">
                    <form action="answer/${card.id}" method="post">
                        <input type="text" name="answer_text"  />
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button class="btn btn-primary my-2" type="submit">Answer</button>
                    </form>
                </div>
            </#if>
        </div>
    </div>
</#list>
</div>