/**
 * @author:稀饭
 * @time:下午12:57:56
 * @filename:Snippet.java
 */
package com.yangyang.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yangyang.service.PersonService;

public class TestAop {
	@Test
	public void testSpringInterceptor() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring.xml");
		PersonService personService = (PersonService) ctx
				.getBean("personService");
		personService.getNameById(1l);
		System.out.println("----------------------------------");
		personService.save("csy");
	}
}
