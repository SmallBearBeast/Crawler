package com.bear.crawler.webmagic.basic.http;

import com.bear.crawler.webmagic.pojo.WechatConfig;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfiguration {

    private final OkHttpProperties okHttpProperties;

    @Autowired
    private WechatConfig wechatConfig;

    public OkHttpConfiguration(OkHttpProperties okHttpProperties) {
        this.okHttpProperties = okHttpProperties;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
//                .sslSocketFactory(sslServerSocketFactory(), x509TrustManager())
                .retryOnConnectionFailure(true)
                .connectionPool(pool())
                .connectTimeout(okHttpProperties.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(okHttpProperties.getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(okHttpProperties.getWriteTimeout(), TimeUnit.SECONDS)
//                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(new WAuthInterceptor(wechatConfig))
                .addInterceptor(new WSleepInterceptor())
                .build();
    }

    @Bean
    public X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    @Bean
    public SSLSocketFactory sslServerSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public ConnectionPool pool() {
        return new ConnectionPool(okHttpProperties.getMaxIdleConnections(), okHttpProperties.getKeepAliveDuration(), TimeUnit.SECONDS);
    }
}
