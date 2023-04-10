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

package net.guerlab.cloud.stream;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

/**
 * 动态方法注册.
 *
 * @author guer
 */
@Slf4j
public class DynamicsFunctionRegistry implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private Environment environment;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		DynamicsFunctionProperties properties = Binder.get(environment)
				.bind(DynamicsFunctionProperties.PROPERTIES_PREFIX, DynamicsFunctionProperties.class).get();
		for (DynamicsFunctionDefinition definition : properties.getDefinitions()) {
			registerDynamicsFunctionBeanDefinition(definition, registry);
		}
	}

	private void registerDynamicsFunctionBeanDefinition(DynamicsFunctionDefinition definition, BeanDefinitionRegistry registry) {
		String beanName = String.format("DynamicsFunction.%s", definition.getFunctionName());
		Assert.notNull(definition.getInputClass(), () -> String.format("DynamicsFunction[%s] input class cannot be null", beanName));
		Assert.notNull(definition.getEventClass(), () -> String.format("DynamicsFunction[%s] event class cannot be null", beanName));
		Assert.isTrue(!registry.containsBeanDefinition(beanName), () -> String.format("bean definition has exist, name is %s", beanName));

		ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
		constructorArgumentValues.addIndexedArgumentValue(0, definition.getFunctionName());
		constructorArgumentValues.addIndexedArgumentValue(1, definition.getInputClass());
		constructorArgumentValues.addIndexedArgumentValue(2, definition.getEventClass());

		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(DynamicsFunction.class);
		beanDefinition.setConstructorArgumentValues(constructorArgumentValues);

		registry.registerBeanDefinition(beanName, beanDefinition);
		log.debug("registerDynamicsFunctionBeanDefinition[{}]", beanName);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
