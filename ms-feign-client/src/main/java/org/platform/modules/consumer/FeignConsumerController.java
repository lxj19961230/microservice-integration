package org.platform.modules.consumer;

import org.platform.modules.consumer.feign.FeignConsumerClient;
import org.platform.modules.consumer.feign.ICacheService;
import org.platform.modules.consumer.feign.IElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope //允许动态刷新配置 
@RestController
public class FeignConsumerController {

	@Autowired
	private FeignConsumerClient feignConsumerClient = null;
	
	@Autowired
	private IElasticService elasticService = null;
	
	@Autowired
	private ICacheService cacheService = null;
	
	@Value(value = "${eureka.client.serviceUrl.defaultZone:None}")
	private String eurekaServiceUrl = null;
	
	@RequestMapping(value = "/eurekaServiceUrl", method = RequestMethod.GET)
	public String getEurekaServiceUrl() {
		return eurekaServiceUrl;
	}
	
	@ResponseBody
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public int doInvokeRedisSet(String key, @RequestParam Object value) {
		try {
			cacheService.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Object doInvokeRedisGet(String key) {
		try {
			return cacheService.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Object search(String i, String t, String f, String q, Integer hl, Integer pn, Integer rn) {
		return elasticService.search(i, t, f, q, hl, pn, rn);
	}
	
	@HystrixCommand(fallbackMethod = "doConsumeFallback")
	@RequestMapping(value = "/feign/consume/{operation}", method = RequestMethod.GET)
	public int doConsume(@PathVariable String operation, int a, int b) {
		System.out.println("a: " + a + " b: " + b);
		System.out.println("feignConsumerClient: " + feignConsumerClient);
		return "add".equals(operation) ? feignConsumerClient.add(a, b) : feignConsumerClient.minus(a, b);
	}
	
	public int doConsumeFallback(String operation, int a, int b) {
		return -9999;
	}
	
}
