package com.atguigu.gmall.test.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {

	@Value("${myName}")
	private String myName;

	@Autowired
	RedissonClient redissonClient;

	@Autowired
	RedisTemplate redisTemplate;

	@RequestMapping("test")
	@ResponseBody
	public String test(){

		Integer ticket = (Integer)redisTemplate.opsForValue().get("ticket");

		ticket--;

//		System.out.println(request.getRemoteAddr()+"剩余票数："+ticket);
		System.out.println("剩余票数："+ticket);
		redisTemplate.opsForValue().set("ticket",ticket);

		return ticket+"";
//		RLock lock = redissonClient.getLock("lock");
//
//		lock.lock();
//
//		lock.unlock();

//		return myName;
	}
	@RequestMapping("testLock")
	@ResponseBody
	public String testLock(HttpServletRequest request){

		RLock lock = redissonClient.getLock("lock");
		Integer ticket = 0;
		try {

			lock.lock();// lock是一个类似于sync的通用锁
			ticket = (Integer) redisTemplate.opsForValue().get("ticket");

			ticket--;

			System.out.println(request.getRemoteAddr() + "剩余票数：" + ticket);

			redisTemplate.opsForValue().set("ticket", ticket);

		}finally {
			lock.unlock();
		}

		return ticket+"";
	}
}
