package com.yangyang.service.impl;

import com.yangyang.service.PersonService;

public class PersonServiceImpl implements PersonService {

	@Override
	public String getNameById(Long id) {
		System.out.println("我是getNameById()方法");
		return "csy";
	}

	@Override
	public void save(String name) {
		throw new RuntimeException("故意抛出了异常,仅供测试");
		// System.out.println("我是save()方法");
	}

	@Override
	public void update(String name, Long id) {
		System.out.println("我是update()方法");
	}

}