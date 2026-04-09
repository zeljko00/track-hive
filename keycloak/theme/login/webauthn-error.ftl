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

                <h2>Passkey sign-in cancelled</h2>
                <p class="subtitle">The passkey prompt was dismissed or failed.</p>

                <#if message?has_content>
                    <div class="error">${message.summary}</div>
                </#if>

                <form action="${url.loginAction}" method="post">
                    <button type="submit" class="btn-passkey">🔑 Try passkey again</button>
                </form>

                <#if auth.showTryAnotherWayLink()>
                    <div class="divider"><span>or</span></div>
                    <form action="${url.loginAction}" method="post">
                        <input type="hidden" name="tryAnotherWay" value="on"/>
                        <button type="submit" class="btn-secondary">Use password instead</button>
                    </form>
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
