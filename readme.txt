SpringBoot与SpringSession集成
1) pom.xml增加依赖spring-session、spring-boot-starter-data-redis
2) SpringSession配置类，用来启用RedisHttpSession功能，并向Spring容器中注册一个RedisConnectionFactory
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
3) 加载RedisHttpSessionConfiguration配置文件 
定义springSessionRepositoryFilter拦截所有的请求将session封装为spring session
public class SpringSessionInitializer extends AbstractHttpSessionApplicationInitializer{

	public SpringSessionInitializer() {
		super(SpringRedisClusterConfigFactory.class);
	}
}
4) Spring Session配置完成之后，我们就可以使用标准的Servlet API与之交互了
	@GetMapping
    public String hello(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute("hello", "world");
        return "Hello Spring-Boot";
    }
	
	@GetMapping("/info")
    public Map<String, Object> getInfo(@RequestParam String name,HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        map.put("springsessionId", session.getId());
        map.put("hello", session.getAttribute("hello"));
        map.put("createTime", session.getCreationTime());
        map.put("name", name);
        return map;
    }
5) 测试接口，关注Redis数据
http://localhost:8080/hello
http://localhost:8080/hello/info?name=123