<#import "parts/common.ftl" as c>

<@c.page>
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