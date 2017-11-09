package com.example.demo.init;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

import com.example.demo.config.SpringRedisClusterConfigFactory;

/**
 * 用于向Servlet容器中添加springSessionRepositoryFilter
 * 一个AbstractHttpSessionApplicationInitializer实现类
 * 
 * 加载RedisHttpSessionConfiguration配置文件 
 * 定义springSessionRepositoryFilter拦截所有的请求将session封装为spring session
* 
* 项目名称:  SpringCloudSession
* 包:       com.example.demo.init   
* 类名称:    SpringSessionInitializer.java
* 类描述:    
* 创建人:    yzx 
* 创建时间:  2017年11月8日
 */
public class SpringSessionInitializer extends AbstractHttpSessionApplicationInitializer{

	public SpringSessionInitializer() {
		super(SpringRedisClusterConfigFactory.class);
	}
}
