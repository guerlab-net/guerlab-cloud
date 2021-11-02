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
package net.guerlab.cloud.loadbalancer;

import net.guerlab.cloud.loadbalancer.utils.VersionCompareUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 版本控制测试
 *
 * @author guer
 */
public class VersionCompareUtilsTest {

    // @formatter:off
    private final String[] origins = new String[] {
            "1.0", "1.0.1", "1.1", "1.1.1", "1.2", "1.2.1", "str"
    };
    private final String[] ranges = new String[] {
            "1.0.0", "1.0.1", "[1.0.0, 1.2.1]", "(1.0.0, 1.2.1)", "[1.0.0, 1.2.1)", "(1.0.0, 1.2.1]", "string", "[str, str]"
    };
    private final Boolean[][] expect = new Boolean[][] {
            new Boolean[] { true, false, true, false, true, false, false, false },
            new Boolean[] { false, true, true, true, true, true, false, false },
            new Boolean[] { false, false, true, true, true, true, false, false },
            new Boolean[] { false, false, true, true, true, true, false, false },
            new Boolean[] { false, false, true, true, true, true, false, false },
            new Boolean[] { false, false, true, false, false, true, false, false },
            new Boolean[] { false, false, false, false, false, false, true, true },
    };
    // @formatter:on

    /**
     * 匹配测试
     */
    @Test
    public void match() {
        for (int o = 0; o < origins.length; o++) {
            for (int r = 0; r < ranges.length; r++) {
                Assertions.assertEquals(expect[o][r], VersionCompareUtils.match(origins[o], ranges[r]), String.format("%s and %s match result not equals %s", origins[o], ranges[r], expect[o][r]));
            }
        }

    }
}
