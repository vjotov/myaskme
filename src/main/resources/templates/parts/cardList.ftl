<#include "security.ftl">
<div class="card-columns" id="cards-list">
<#list page as card>
    <div class="card my-3" data-id="${card.id}">
        <div class="card-body">
            <h5 class="card-title">question: ${card.question}</h5>
            <div class="row">
                <span><i>author: ${card.author.username}</i></span><br/>
                <span>to: <a href="/channel/${card.receiver.id}"> ${card.receiver.username}</a></span><br/>
                answer: <#if card.answer??>${card.answer}</#if>
            </div>
            <div class="card-footer text-muted container">
                <div class="row" >
                    <a href="#">
                        <i class="fas fa-heart"></i>
                    </a>
                </div>
                <#if card.receiver.id == currentUserId && !card.answer??>
                    <div class="row">
                        <form action="answer/${card.id}" method="post">
                            <input type="text" name="answer_text"  />
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-primary my-2" type="submit">Answer</button>
                        </form>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#list>
</div>