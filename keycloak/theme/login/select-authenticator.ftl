<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${realm.displayName!"TrackHive"}</title>
    <link rel="stylesheet" href="${url.resourcesPath}/css/style-common.css">
    <script>
        var selected_theme = localStorage.getItem('theme');
        var valid_themes = ['style-dark', 'style-light'];
        var os_theme = window.matchMedia('(prefers-color-scheme: dark)').matches ? 'style-dark' : 'style-light';
        var theme = valid_themes.includes(selected_theme) ? selected_theme : os_theme;
        document.write('<link rel="stylesheet" href="${url.resourcesPath}/css/' + theme + '.css">');
    </script>
</head>

<body>

    <div class="layout">

        <div class="left-panel">
            <div class="card">

                <h2>Choose how to sign in</h2>
                <p class="subtitle">Select your preferred authentication method</p>

                <div class="auth-options">
                    <#list auth.authenticationSelections as selection>
                        <form action="${url.loginAction}" method="post">
                            <input type="hidden" name="authenticationExecution" value="${selection.authExecId}"/>
                            <button type="submit" class="auth-option-btn">
                                <span class="auth-option-icon">
                                    <#if selection.iconCssClass?contains("webauthn") || selection.displayName?lower_case?contains("passkey") || selection.displayName?lower_case?contains("security key")>
                                        🔑
                                    <#elseif selection.iconCssClass?contains("password") || selection.displayName?lower_case?contains("password")>
                                        🔒
                                    <#else>
                                        🔐
                                    </#if>
                                </span>
                                <span class="auth-option-text">
                                    <strong>${selection.displayName}</strong>
                                    <#if selection.helpText?has_content>
                                        <small>${selection.helpText}</small>
                                    </#if>
                                </span>
                                <span class="auth-option-arrow">›</span>
                            </button>
                        </form>
                    </#list>
                </div>

                <#if message?has_content>
                    <div class="error">${message.summary}</div>
                </#if>

            </div>
        </div>

        <div class="right-panel">
            <div class="branding">
                <img src="${url.resourcesPath}/img/logo_cropped.png" class="logo" />
                <h1>TrackHive</h1>
                <p>Track every order in real time.</p>
            </div>
        </div>

    </div>

</body>

</html>
