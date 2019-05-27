package com.directory.abe.Emailer;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import org.apache.http.conn.ssl.SSLSocketFactory;

public final class JSSEProvider extends Provider {

    /* renamed from: com.directory.abe.Emailer.JSSEProvider$1 */
    class C02451 implements PrivilegedAction<Void> {
        C02451() {
        }

        public Void run() {
            JSSEProvider.this.put("SSLContext.TLS", "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
            JSSEProvider.this.put("Alg.Alias.SSLContext.TLSv1", SSLSocketFactory.TLS);
            JSSEProvider.this.put("KeyManagerFactory.X509", "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
            JSSEProvider.this.put("TrustManagerFactory.X509", "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
            return null;
        }
    }

    public JSSEProvider() {
        super("HarmonyJSSE", 1.0d, "Harmony JSSE Provider");
        AccessController.doPrivileged(new C02451());
    }
}
