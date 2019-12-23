package com.youzan.data.service.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpClientConfig {
 
    @Value("${http.connectTimeout}")
    private Integer connectTimeout;
 
    @Value("${http.readTimeout}")
    private Integer readTimeout;
    
    @Value("${http.clientId}")
    private String clientId;
    
    @Value("${http.clientSecret}")
    private String clientSecret;
    
    @Value("${http.grantId}")
    private String grantId;
    
	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getGrantId() {
		return grantId;
	}

	public void setGrantId(String grantId) {
		this.grantId = grantId;
	}
}
