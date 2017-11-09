package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * SpringSession配置类，用来启用RedisHttpSession功能，并向Spring容器中注册一个RedisConnectionFactory
 * RedisHttpSessionConfiguration的配置文件 引入RedisHttpSessionConfiguration.class
 * maxInactiveIntervalInSeconds设置session在redis里的超时
 * 
 * @EnableRedisHttpSession注解通过Import，引入了RedisHttpSessionConfiguration配置类
 * 该配置类通过@Bean注解，向Spring容器中注册了一个SessionRepositoryFilter
 * SessionRepositoryFilter的依赖关系：SessionRepositoryFilter --> SessionRepository --> RedisTemplate --> RedisConnectionFactory
* 
* 项目名称:  SpringCloudSession
* 包:       com.example.demo.config   
* 类名称:    SpringRedisClusterConfigFactory.java
* 类描述:    
* 创建人:    yzx 
* 创建时间:  2017年11月8日
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=7200,redisFlushMode=RedisFlushMode.IMMEDIATE)
public class SpringRedisClusterConfigFactory {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("10.5.2.242");
		connectionFactory.setPort(6379);
		return connectionFactory;
	}
	
	@Bean(name="redisSessionTemplate")
	public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory factory){
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
		redisTemplate.setConnectionFactory(factory);
		RedisSerializer<String> serializer1 = new StringRedisSerializer();
		RedisSerializer<Object> serializer2 = new JdkSerializationRedisSerializer();
		redisTemplate.setKeySerializer(serializer1);
		redisTemplate.setValueSerializer(serializer2);
		redisTemplate.setHashKeySerializer(serializer1);
		redisTemplate.setHashValueSerializer(serializer2);
		return redisTemplate;
	}
	
}
