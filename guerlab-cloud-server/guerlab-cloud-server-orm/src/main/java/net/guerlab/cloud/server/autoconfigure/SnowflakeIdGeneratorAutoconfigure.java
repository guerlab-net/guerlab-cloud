/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.server.autoconfigure;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import net.guerlab.spring.commons.sequence.Sequence;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 雪花ID构造器自动配置
 *
 * @author guer
 */
@Configuration
public class SnowflakeIdGeneratorAutoconfigure {

    /**
     * 构造雪花ID生成器
     *
     * @param sequence
     *         雪花ID序列
     * @return 雪花ID生成器
     */
    @Bean
    @ConditionalOnBean(Sequence.class)
    public SnowflakeIdGenerator snowflakeIdGenerator(Sequence sequence) {
        return new SnowflakeIdGenerator(sequence);
    }

    /**
     * 雪花ID构造器
     */
    public static class SnowflakeIdGenerator implements IdentifierGenerator {

        private final Sequence sequence;

        public SnowflakeIdGenerator(Sequence sequence) {
            this.sequence = sequence;
        }

        @Override
        public Long nextId(Object entity) {
            return sequence.nextId();
        }
    }

}
