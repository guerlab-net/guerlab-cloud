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

package net.guerlab.cloud.api.core.feign;

import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.AbstractSearchParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author guer
 */
public class DynamicProxyFallbackTest {

    TestApi proxy;

    @BeforeEach
    void init() {
        TestFallbackFactory factory = new TestFallbackFactory();
        proxy = factory.create(new Exception());
    }

    @Test
    void selectById() {
        Assertions.assertNull(proxy.selectById(""));
    }

    @Test
    void selectOne() {
        Assertions.assertNull(proxy.selectOne(new AbstractSearchParams() {}));
    }

    @Test
    void selectList() {
        Assertions.assertEquals(Collections.emptyList(), proxy.selectList(new AbstractSearchParams() {}));
    }

    @Test
    void selectPage() {
        Assertions.assertEquals(Pageable.empty(), proxy.selectPage(new AbstractSearchParams() {}));
    }

    @Test
    void selectCount() {
        Assertions.assertEquals(0, proxy.selectCount(new AbstractSearchParams() {}));
    }

    @Test
    void selectMap() {
        Assertions.assertEquals(Collections.emptyMap(), proxy.selectMap());
    }

    public interface TestApi {

        @Nullable
        Object selectOne(AbstractSearchParams searchParams);

        @Nullable
        Object selectById(String id);

        Collection<Object> selectList(AbstractSearchParams searchParams);

        Pageable<Object> selectPage(AbstractSearchParams searchParams);

        int selectCount(AbstractSearchParams searchParams);

        Map<String, String> selectMap();
    }

    public static class TestFallbackFactory extends AbstractFallbackFactory<TestApi> {}
}
