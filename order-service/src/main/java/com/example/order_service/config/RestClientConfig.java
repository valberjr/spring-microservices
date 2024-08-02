package com.example.order_service.config;

import com.example.order_service.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    @Bean
    public InventoryClient inventoryClient() {
        var restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .build();

        var restClientAdapter = RestClientAdapter.create(restClient);

        var httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(restClientAdapter)
                .build();

        return httpServiceProxyFactory.createClient(InventoryClient.class);
    }
}
