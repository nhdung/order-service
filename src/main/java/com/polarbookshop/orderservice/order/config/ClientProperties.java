package com.polarbookshop.orderservice.order.config;

import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
public class ClientProperties {

    private URI catalogServiceUri;

    public ClientProperties(URI catalogServiceUri) {
        this.catalogServiceUri = catalogServiceUri;
    }

    public URI getCatalogServiceUri() {
        return catalogServiceUri;
    }
}
