package net.guerlab.smart.platform.server.autoconfigure;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import net.guerlab.smart.platform.server.mybatis.plus.ReplaceInsertSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus自动配置
 *
 * @author guer
 */
@Configuration
public class MybatisPlusAutoconfigure {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 自定义 SqlInjector
     * 里面包含自定义的全局方法
     *
     * @return replace模式插入
     */
    @Bean
    public ReplaceInsertSqlInjector replaceInsertSqlInjector() {
        return new ReplaceInsertSqlInjector();
    }
}
