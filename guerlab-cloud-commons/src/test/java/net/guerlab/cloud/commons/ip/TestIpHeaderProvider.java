package net.guerlab.cloud.commons.ip;

import java.util.List;

/**
 * @author guer
 */
public class TestIpHeaderProvider implements IpHeaderProvider {

	@Override
	public List<String> get() {
		return List.of("CustomerHeader");
	}
}
