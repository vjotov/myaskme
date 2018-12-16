<#include "security.ftl">

<#list cards as card>
id: ${card.id} <br/>
question: ${card.question}<br/>
answer: ${card.answer}
<hr/>
</#list>