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
                <p class="subtitle">Use your passkey (fingerprint, face, or security key) to login.</p>

                <form id="webauth" action="${url.loginAction}" method="post">
                    <input type="hidden" id="clientDataJSON"   name="clientDataJSON"/>
                    <input type="hidden" id="authenticatorData" name="authenticatorData"/>
                    <input type="hidden" id="signature"        name="signature"/>
                    <input type="hidden" id="credentialId"     name="credentialId"/>
                    <input type="hidden" id="userHandle"       name="userHandle"/>
                    <input type="hidden" id="error"            name="error"/>

                    <button type="button" id="passkey-btn" onclick="authenticate()" class="btn-passkey">
                        🔑 Authenticate with Passkey
                    </button>

                    <#if message?has_content>
                        <div class="error">${message.summary}</div>
                    </#if>
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

    <script>
        function base64UrlDecode(str) {
            const base64 = str.replace(/-/g, '+').replace(/_/g, '/');
            const pad = '='.repeat((4 - base64.length % 4) % 4);
            return Uint8Array.from(atob(base64 + pad), c => c.charCodeAt(0));
        }

        function base64UrlEncode(buffer) {
            return btoa(String.fromCharCode(...new Uint8Array(buffer)))
                .replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
        }

        async function authenticate() {
            const btn = document.getElementById('passkey-btn');
            btn.disabled = true;
            btn.textContent = '⏳ Waiting for passkey…';

            // Keycloak-provided variables
            const challenge = base64UrlDecode("${challenge}");
            const rpId = "${rpId}";
            const userVerification = "${userVerification!'preferred'}";
            const timeout = ${createTimeout!60000};

            <#assign authList = authenticators![]>
            const allowCredentials = [<#list authList as cred>{
                id: base64UrlDecode("${cred.credentialId}"),
                type: 'public-key'
            }<#sep>,</#sep></#list>];

            try {
                const assertion = await navigator.credentials.get({
                    publicKey: {
                        challenge,
                        rpId,
                        userVerification,
                        allowCredentials,
                        timeout
                    }
                });

                document.getElementById('clientDataJSON').value    = base64UrlEncode(assertion.response.clientDataJSON);
                document.getElementById('authenticatorData').value = base64UrlEncode(assertion.response.authenticatorData);
                document.getElementById('signature').value         = base64UrlEncode(assertion.response.signature);
                document.getElementById('credentialId').value      = base64UrlEncode(assertion.rawId);

                if (assertion.response.userHandle) {
                    document.getElementById('userHandle').value = base64UrlEncode(assertion.response.userHandle);
                }

                document.getElementById('webauth').submit();
            } catch (err) {
                btn.disabled = false;
                btn.textContent = '🔑 Authenticate with Passkey';
                document.getElementById('error').value = err.toString();
                document.getElementById('webauth').submit();
            }
        }
    </script>

</body>

</html>
