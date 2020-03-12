<#import "parts/common.ftl" as c>

<@c.page>
<#if userChannel??>
    <h5>${userChannel.username}</h5>
</#if>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="filter" value="${filter?ifExists}" class="form-control" placeholder="Search by question"/>
            <button type="submit" class="btn btn-primary ml-2" disabled="disabled">Search</button>
        </form>
    </div>
</div>

<#include "parts/cardAdd.ftl" />

<#include "parts/cardList.ftl" />

</@c.page>