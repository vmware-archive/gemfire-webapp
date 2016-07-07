package io.pivotal.wealthfrontier;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import com.gemstone.gemfire.pdx.ReflectionBasedAutoSerializer;
import io.pivotal.wealthfrontier.domain.Customer;
import io.pivotal.wealthfrontier.domain.Alert;
import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;

/**
 * Created by vupadhya on 2/24/16.
 */
@Configuration
public class GemFireConfiguration extends AbstractCloudConfig {

    public ServiceConnectorConfig createGemfireConnectorConfig() {
        // Create a custom service connector config object which sets specific properties
        // for the ClientCache as exposed by the GemfireServiceConnectorConfig.
        GemfireServiceConnectorConfig gemfireConfig = new GemfireServiceConnectorConfig();
        gemfireConfig.setPoolIdleTimeout(7777L);
        gemfireConfig.setPdxReadSerialized(false);
        ReflectionBasedAutoSerializer serializer = new ReflectionBasedAutoSerializer();
        serializer.reconfigure("io.pivotal.wealthfrontier.domain.*");
        gemfireConfig.setPdxSerializer(serializer);

        return gemfireConfig;
    }

    @Bean(name = "gemfireCache")
    public ClientCache getGemfireClientCache() {
        CloudFactory cloudFactory = new CloudFactory();

        // Obtain the Cloud object for the environment in which the application is running.
        // Note that you must have a CloudConnector suitable for your deployment environment on your classpath.
        // For example, if you are deploying the application to Cloud Foundry, you must add the Cloud Foundry
        // Connector to your classpath. If no suitable CloudConnector is found, the getCloud() method will throw
        // a CloudException. Use the Cloud instance to access application and service information and to create
        // service connectors.
        Cloud cloud = cloudFactory.getCloud();

        // Let Spring Cloud create a service connector for you.
        ClientCache cache = cloud.getServiceConnector("gemfireinstance", ClientCache.class,
                createGemfireConnectorConfig());

        return cache;
    }

    @Bean(name = "Customer")
    ClientRegionFactoryBean<String,Customer> customerRegion(ClientCache gemfireCache){
        ClientRegionFactoryBean<String,Customer> customerRegion = new ClientRegionFactoryBean<String, Customer>();
        customerRegion.setCache(gemfireCache);
        customerRegion.setName("Customer");
        customerRegion.setShortcut(ClientRegionShortcut.PROXY);
        return customerRegion;

    }

    @Bean(name = "Alert")
    ClientRegionFactoryBean<String,Alert> alertRegion(ClientCache gemfireCache){
        ClientRegionFactoryBean<String,Alert> alertRegion = new ClientRegionFactoryBean<String, Alert>();
        alertRegion.setCache(gemfireCache);
        alertRegion.setName("Alert");
        alertRegion.setShortcut(ClientRegionShortcut.PROXY);
        return alertRegion;

    }
}
