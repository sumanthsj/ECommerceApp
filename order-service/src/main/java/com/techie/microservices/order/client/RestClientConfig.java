package com.techie.microservices.order.client;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestClientConfig {

    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    @Value("${inventory.connect-timeout:5000}")
    private long connectTimeout;

    @Value("${inventory.read-timeout:10000}")
    private long readTimeout;

    @Bean
    public InventoryClient inventoryClient() {
        // Configure request config with timeouts
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.of(connectTimeout, TimeUnit.MILLISECONDS))
                .setResponseTimeout(Timeout.of(readTimeout, TimeUnit.MILLISECONDS))
                .build();

        // Create HttpClient with the request config
        var httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        // Create request factory using HttpClient
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        // Build RestClient with the request factory
        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .requestFactory(requestFactory)
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(InventoryClient.class);
    }
}