/*
 * (c) 2020 Open Source Geospatial Foundation - all rights reserved This code is licensed under the
 * GPL 2.0 license, available at the root application directory.
 */
package org.geoserver.cloud.event.info;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.NonNull;

import org.geoserver.catalog.Info;
import org.geoserver.cloud.event.catalog.CatalogInfoRemoved;
import org.geoserver.cloud.event.config.ConfigInfoRemoved;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CatalogInfoRemoved.class, name = "CatalogInfoRemoved"),
    @JsonSubTypes.Type(value = ConfigInfoRemoved.class, name = "ConfigInfoRemoved"),
})
public abstract class InfoRemoved<SELF, INFO extends Info> extends InfoEvent<SELF, INFO> {

    protected InfoRemoved() {}

    protected InfoRemoved(
            @NonNull Long updateSequence, @NonNull String objectId, @NonNull ConfigInfoType type) {
        super(updateSequence, objectId, type);
    }
}
