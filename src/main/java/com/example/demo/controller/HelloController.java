package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@Qualifier("redisSessionTemplate")
	@Autowired
	RedisTemplate<String,Object> redisTemplate;
	
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

	@GetMapping("/listmap")
    public List<Map<String, String>> getListMap() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = null;
        for (int i = 1; i <= 5; i++) {
            map = new HashMap<>();
            map.put("name", "Shanhy-" + i);
            list.add(map);
        }
        return list;
    }
}
