package net.guerlab.smart.platform.server.autoconfigure;

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
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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
