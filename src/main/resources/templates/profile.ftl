<#import "parts/common.ftl" as c>
<@c.page>
${message?ifExists}
<h5>${username}</h5>
<form method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Current Password:</label>
        <div class="col-sm-6">
            <input type="password" name="current_password" class="form-control ${(currentPassword??)?string('is-invalid', '')}" placeholder="Current Password"/>
            <#if currentPassword??>
                <div class="invalid-feedback">
                    ${currentPassword}
                </div>
            </#if>
        </div>

    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">New Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="New Password"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Retype New Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}" placeholder="Retype New Password"/>
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}" placeholder="some@some.com" value="${email!''}"/>
            <#if emailError??>
            <div class="invalid-feedback">
                ${emailError}
            </div>
        </#if>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit"  class="btn btn-primary">Save</button>
</form>
</@c.page>
