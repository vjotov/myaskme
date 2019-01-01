<#include "security.ftl">


<div class="card-columns" id="cards-list">
    <#list page.content as card>
        <div class="card my-3" data-id="${card.id}">
            <div class="card-body">
                <h5 class="card-title">question: ${card.question}</h5>
                <div class="row">
                </div>
                <div class="card-footer text-muted container">
                    <div class="row" >
                        <a href="/channel/${card.receiver.id}"> ${card.receiver.username}</a>
                        <a href="/cards/${card.id}/like">
                            <#if card.meLiked>
                                <i class="fas fa-heart"></i>
                            <#else>
                                <i class="far fa-heart"></i>
                            </#if>
                            ${card.likes}
                        </a>
                    </div>
                    <#if card.receiver.id == currentUserId && !card.answer??>
                        <div class="row">
                            <form action="answer/${card.id}" method="post">
                                <input type="text" name="answer_text"  />
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                <button class="btn btn-primary my-2" type="submit">Answer</button>
                            </form>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        NO QUESTIONS
    </#list>
</div>