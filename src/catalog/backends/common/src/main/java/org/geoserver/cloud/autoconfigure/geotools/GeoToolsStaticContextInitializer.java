/*
 * (c) 2022 Open Source Geospatial Foundation - all rights reserved This code is licensed under the
 * GPL 2.0 license, available at the root application directory.
 */
package org.geoserver.cloud.autoconfigure.geotools;

import org.geoserver.GeoserverInitStartupListener;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@link ApplicationContextInitializer} replacing upstream's {@link GeoserverInitStartupListener},
 * which is a servlet context listener instead, and hence runs too late in spring-boot.
 *
 * <p>With an {@code ApplicationContextInitializer} we make sure required initializations run before
 * even loading the spring beans.
 *
 * @since 1.0
 */
public class GeoToolsStaticContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.setProperty("org.geotools.referencing.forceXY", "true");

        Boolean useEnvAwareHttpClient =
                applicationContext
                        .getEnvironment()
                        .getProperty(
                                "geotools.httpclient.proxy.enabled", Boolean.class, Boolean.TRUE);
        if (useEnvAwareHttpClient) {
            // factoryName matches the one in
            // src/main/resources/META-INF/services/org.geotools.http.HTTPClientFactory
            String factoryName =
                    SpringEnvironmentAwareGeoToolsHttpClientFactory.class.getCanonicalName();
            System.setProperty("HTTP_CLIENT_FACTORY", factoryName);
        }
    }
}
