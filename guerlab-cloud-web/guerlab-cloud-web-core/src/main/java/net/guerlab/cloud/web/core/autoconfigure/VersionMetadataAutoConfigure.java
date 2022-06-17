/*
 * Copyright 2018-2022 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.web.core.autoconfigure;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cloud.client.discovery.event.InstancePreRegisteredEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import net.guerlab.cloud.core.domain.Version;

/**
 * 版本号元信息自动配置.
 *
 * @author guer
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class VersionMetadataAutoConfigure {

	private static final String VERSION_METADATA_KEY = "version";

	private BuildProperties buildProperties;

	public VersionMetadataAutoConfigure(ObjectProvider<BuildProperties> buildPropertiesProvider) {
		buildPropertiesProvider.ifAvailable(buildProperties -> this.buildProperties = buildProperties);
	}

	@EventListener(InstancePreRegisteredEvent.class)
	public void instancePreRegistered(InstancePreRegisteredEvent event) {
		if (buildProperties == null) {
			log.debug("buildProperties is null");
			return;
		}

		Map<String, String> metadata = event.getRegistration().getMetadata();
		if (metadata.containsKey(VERSION_METADATA_KEY)) {
			log.debug("metadata has '{}' property", VERSION_METADATA_KEY);
			return;
		}

		String versionString = buildProperties.getVersion();
		if (versionString == null) {
			log.debug("not found 'version' property in build-info.properties");
			return;
		}

		Version version = Version.parse(versionString);
		if (version == null) {
			log.debug("parse 'version' property in build-info.properties fail");
			return;
		}

		metadata.put(VERSION_METADATA_KEY, version.toString());
		log.debug("add metadata {} = {}", VERSION_METADATA_KEY, version);
	}
}
