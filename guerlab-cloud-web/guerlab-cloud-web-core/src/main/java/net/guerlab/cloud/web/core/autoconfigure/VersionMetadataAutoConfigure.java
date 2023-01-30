/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cloud.client.discovery.event.InstancePreRegisteredEvent;
import org.springframework.context.event.EventListener;

import net.guerlab.cloud.core.domain.Version;

/**
 * 版本号元信息自动配置.
 *
 * @author guer
 */
@Slf4j
@AutoConfiguration
public class VersionMetadataAutoConfigure {

	private static final String COMPLETE_VERSION_METADATA_KEY = "version.complete";

	private BuildProperties buildProperties;

	/**
	 * 版本元信息Key.
	 */
	@Value("${spring.cloud.discovery.version-metadata-key:version}")
	private String versionMetadataKey;

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
		if (metadata.containsKey(versionMetadataKey)) {
			log.debug("metadata has '{}' property", versionMetadataKey);
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

		metadata.put(versionMetadataKey, version.toString());
		metadata.put(COMPLETE_VERSION_METADATA_KEY, versionString);
		log.debug("add metadata {} = {}", versionMetadataKey, version);
		log.debug("add metadata {} = {}", COMPLETE_VERSION_METADATA_KEY, versionString);
	}
}
