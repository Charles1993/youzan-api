package com.youzan.data.service.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youzan.cloud.open.sdk.api.AbstractAPI;
import com.youzan.cloud.open.sdk.api.ApiParams;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.core.HttpConfig;
import com.youzan.cloud.open.sdk.core.client.auth.Token;
import com.youzan.cloud.open.sdk.core.client.core.DefaultYZClient;
import com.youzan.cloud.open.sdk.core.oauth.model.OAuthToken;
import com.youzan.cloud.open.sdk.core.oauth.token.TokenParameter;
import com.youzan.common.exception.ServiceException;
import com.youzan.data.api.HttpService;
import com.youzan.data.service.conf.HttpClientConfig;

import okhttp3.OkHttpClient;

@Service
public class HttpServiceImpl extends BaseServiceImpl implements HttpService{
	
	@Autowired
	private HttpClientConfig httpClientConfig;
	
	private HttpConfig httpConfig;
	
	private TokenParameter tokenParameter;
	
	private String accessToken = null;
	
	private long expires = 1;
	
	private Lock lock = new ReentrantLock();    //注意这个地方
	
	private void initHttpConfig() throws SDKException {
		if(null == httpConfig) {
			OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.connectTimeout(httpClientConfig.getConnectTimeout(), TimeUnit.SECONDS)//设置连接超时时间
		        .readTimeout(httpClientConfig.getReadTimeout(), TimeUnit.SECONDS);//设置读取超时时间

			httpConfig = HttpConfig.builder().OkHttpClientBuilder(builder).build();
			
			tokenParameter = TokenParameter.self()
					.clientId(httpClientConfig.getClientId())
					.clientSecret(httpClientConfig.getClientSecret())
					.grantId(httpClientConfig.getGrantId())
					.build();
		}
	}
	
	/**
	 * 获取token
	 * @throws SDKException
	 */
	private void getToken() throws SDKException {
		long curTime = System.currentTimeMillis();
		
		if(StringUtils.isNotEmpty(this.accessToken)) {
			if((expires - 10000) > curTime) {
				return;
			}
		}
		
		lock.lock();
		try {
			this.initHttpConfig();
			
			if((expires - 10000) <= curTime) {
				DefaultYZClient youZanClient = new DefaultYZClient(httpConfig);
				OAuthToken oAuthToken = youZanClient.getOAuthToken(tokenParameter);
				
				accessToken = oAuthToken.getAccessToken();
				expires = oAuthToken.getExpires();
			}
		}finally {
            lock.unlock();
        }
	}
	
	/**
	 * 通用请求方法
	 * @param api
	 * @param apiParams
	 * @param clazz
	 * @return
	 * @throws ServiceException
	 * @throws SDKException
	 */
	public <T> T get(AbstractAPI api,ApiParams apiParams,Class<T> clazz) throws ServiceException, SDKException{
		DefaultYZClient youZanClient = new DefaultYZClient(httpConfig);
		
		api.setAPIParams(apiParams);
		return youZanClient.invoke(api, new Token(this.getAccessToken()), clazz);
	}
	
	public String getAccessToken() throws ServiceException{
		try {
			this.getToken();
		} catch (SDKException e) {
			throw new ServiceException(e);
		}
		return accessToken;
	}
}
