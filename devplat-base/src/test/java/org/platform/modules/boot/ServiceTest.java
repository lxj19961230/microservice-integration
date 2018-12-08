package org.platform.modules.boot;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.platform.modules.bootstrap.BaseBootstrapApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseBootstrapApplication.class)
@WebAppConfiguration 
public class ServiceTest {
	
	@Resource(name = "templateEngine")
	protected SpringTemplateEngine templateEngine = null;
	
	@Test
	public void test1() {
		Context context = new Context();
		context.setVariable("module", "user");
		context.setVariable("entity", "User");
		String template = "StandardDAO.txt";
		String content = templateEngine.process(template, context);
		System.err.println(content);
	}
	
}