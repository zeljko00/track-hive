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

                <h2>Welcome back to TrackHive</h2>

                <form id="kc-form-login" action="${url.loginAction}" method="post">

                    <div class="field">
                        <input type="text" name="username" placeholder="Email" autofocus />
                    </div>

                    <div class="field password-wrapper">
                        <input type="password" name="password" placeholder="Password" id="password" />
                        <span class="toggle" onclick="togglePassword()">👁</span>
                    </div>

                    <div class="row">
                        <label>
                            <input type="checkbox" name="rememberMe" /> Remember me
                        </label>
                        <a href="${url.loginResetCredentialsUrl}" class="link">Forgot password?</a>
                    </div>

                    <button type="submit">Sign in</button>

                    <#if message?has_content>
                        <div class="error">${message.summary}</div>
                    </#if>

                </form>
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

    <script>
        function togglePassword() {
            const input = document.getElementById('password');
            input.type = input.type === 'password' ? 'text' : 'password';
        }
    </script>

</body>

</html>