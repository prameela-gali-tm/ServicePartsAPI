package com.toyota.scs.serviceparts;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;


import com.toyota.scs.serviceparts.*;

public class TrustedSSLContext {

	public static CloseableHttpClient buildHttpClient() {
	        return buildHttpClient(120);
	    }

	    /**
	     * Build a HttpClient that associates an overridden SSL Context
	     *
	     * @return httpClient that will accept all SSL connections
	     */
	    public static CloseableHttpClient buildHttpClient(int timeout) {
	        
	        

	        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
	        
	        SSLContext sslContext = null;
	        try {
	            
	            sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
	                @Override
	                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	                    return true;
	                }
	            }).build();
	        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
	            throw new WebServiceException("Failed to create custom SSL Context", e);
	        }

	        
	        
	        clientBuilder.setSSLContext(sslContext);

	        
	        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

	        
	        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
	                .register("http", PlainConnectionSocketFactory.getSocketFactory())
	                .register("https", sslSocketFactory)
	                .build();

	        
	        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
	        clientBuilder.setConnectionManager(connMgr);

	        
	        RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(timeout * 1000)
	                .setConnectionRequestTimeout(timeout * 1000)
	                .setSocketTimeout(timeout * 1000)
	                .build();

	        
	        CloseableHttpClient client = clientBuilder.setDefaultRequestConfig(requestConfig).build();
	        
	        return client;
	    }

		

}
