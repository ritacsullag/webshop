package com.csullag.shippingservice.config;

import com.csullag.shippingservice.xmlws.ShippingXmlWs;
import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {

    private final Bus bus;
    private final ShippingXmlWs shippingXmlWs;

    @Bean
    public Endpoint endpoint() {
        Endpoint endpoint = new EndpointImpl(bus, shippingXmlWs);
        //this will be the url of the web service (http://localhost:8084/services/shipment?wsdl)
        endpoint.publish("/shipment");

        return endpoint;
    }
}
