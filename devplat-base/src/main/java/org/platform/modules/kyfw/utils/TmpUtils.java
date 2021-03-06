package org.platform.modules.kyfw.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.platform.utils.date.DateFormatter;
import org.platform.utils.endecrypt.RSAUtils;
import org.platform.utils.file.DefaultLineHandler;
import org.platform.utils.file.FileUtils;
import org.platform.utils.http.HttpClientUtils;
import org.platform.utils.json.GsonUtils;

public class TmpUtils {
	
	public static void a() {
		String url1 = "http://image.baidu.com/pcdutu/a_upload?fr=html5&target=pcSearchImage&needJson=true";
		String path = "f:\\result\\tmp\\1.png";
		String resp1 = HttpClientUtils.sendPostWithFile(url1, path, "UTF-8");
		System.err.println(resp1);
		Map<String, Object> result1 = GsonUtils.fromJsonToMap(resp1);
		String imgUrl = String.valueOf(result1.get("url"));
		String querySign = String.valueOf(result1.get("querySign"));
		String simid = String.valueOf(result1.get("simid"));
		String url2 = "http://image.baidu.com/pcdutu/a_similar?queryImageUrl=$imgUrl&querySign=$querySign&simid=$simid&word=&querytype=0&t=1514459504295&rn=60&sort=&fr=pc&pn=0";
		url2 = url2.replace("$imgUrl", imgUrl).replace("$querySign", querySign).replace("$simid", simid);
		String resp2 = HttpClientUtils.sendGet(url2, "UTF-8");
		System.err.println(resp2);
		Map<String, Object> result2 = GsonUtils.fromJsonToMap(resp2);
		if (result2.containsKey("result")) {
			Object resultObj = result2.get("result");
//			List<Map<String, Object>> records = GsonUtils.builder().fromJson(String.valueOf(resultObj), List.class);
			List<Map<String, Object>> records = GsonUtils.fromJsonToList(String.valueOf(resultObj));
			for (int i = 0, len = records.size(); i < len; i++) {
				System.err.println(records.get(i));
				System.err.println(records.get(i).getClass());
			}
		}
		
	}
	
	static List<String> filterTokens = Arrays.asList(new String[]{"'train_no':'\\w+'", "'train_date':\\{('\\w+':\\w+,){7}", 
		"'train_location':'\\w+'", "'station_train_code':'\\w+'", "'from_station_telecode':'\\w+'", 
			"'to_station_telecode':'\\w+'", "'key_check_isChange':'\\w+'", "'leftTicketStr':'[\\w%]+'", 
				"globalRepeatSubmitToken = '\\w+';"});

	public static void b() throws Exception {
		StringBuilder sb = new StringBuilder();
		List<String> lines = FileUtils.readFromAbsolute("F:\\result\\tmp\\parse.txt", new DefaultLineHandler());
		for (String line : lines) {
			sb.append(line).append("\n");
		}
		String html = sb.toString();
		for (int i = 0, len = filterTokens.size(); i < len; i++) {
			String token = filterTokens.get(i);
			Pattern pattern = Pattern.compile(token);
			Matcher matcher = pattern.matcher(html);
			while (matcher.find()) {
				String content = matcher.group().replaceAll("'", "").trim();
				System.err.println(content);
				String value = null;
				if (content.startsWith("train_date")) {
					String[] values = content.split(":");
					value = values[values.length - 1].replace(",", "").trim();
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(Long.parseLong(value));
					System.err.println(DateFormatter.TIME.get().format(calendar.getTime()));
				} else if (content.startsWith("globalRepeatSubmitToken")) {
					value = content.split("=")[1].replace(";", "").trim();
				} else {
					value = content.split(":")[1].trim();
				}
				System.err.println(value);
			}
		}
	}
	
	public static void c() {
		String url1 = "http://image.baidu.com/pcdutu/a_upload?fr=html5&target=pcSearchImage&needJson=true";
		String path = "f:\\result\\tmp\\1.png";
		String resp1 = HttpClientUtils.sendPostWithFile(url1, path, "UTF-8");
		System.err.println(resp1);
		Map<String, Object> result1 = GsonUtils.fromJsonToMap(resp1);
		String imgUrl1 = String.valueOf(result1.get("url"));
		System.err.println(imgUrl1);
		String resp2 = HttpClientUtils.sendPostWithFile(url1, path, "UTF-8");
		Map<String, Object> result2 = GsonUtils.fromJsonToMap(resp2);
		String imgUrl2 = String.valueOf(result2.get("url"));
		System.err.println(imgUrl2);
	}
	
	@SuppressWarnings("unchecked")
	public static void d() throws Exception {
		String json = (String) FileUtils.readFromAbsolute("F:\\result\\tmp\\parse.txt", new DefaultLineHandler()).get(0);
		Map<String, Object> result2 = GsonUtils.fromJsonToMap(json);
		if (result2.containsKey("result")) {
			Object resultObj = result2.get("result");
//			List<Map<String, Object>> records = GsonUtils.builder().fromJson(String.valueOf(resultObj), List.class);
			List<Map<String, Object>> records = GsonUtils.fromJsonToList(String.valueOf(resultObj));
			for (int i = 0, len = records.size(); i < len; i++) {
				Map<String, Object> record = records.get(i);
				if (!record.containsKey("simid_info")) continue;
				Map<String, Object> simidInfo = (Map<String, Object>) record.get("simid_info");
				if (!simidInfo.containsKey("tags")) continue;
				Map<String, Object> tags = (Map<String, Object>) simidInfo.get("tags");
				/**
				if (tags.containsKey("keyword-cont")) {
					List<Map<String, Object>> keywordCont1s = (List<Map<String, Object>>) tags.get("keyword-cont");
					for (Map<String, Object> kc1 : keywordCont1s) {
						String keyword = (String) kc1.get("keyword");
					}
				}
				*/
				if (tags.containsKey("keyword-cont2")) {
					List<Map<String, Object>> keywordCont2s = (List<Map<String, Object>>) tags.get("keyword-cont2");
					for (Map<String, Object> kc2 : keywordCont2s) {
						System.err.println("k2k: " + kc2.get("keyword"));
						System.err.println("k2m: " + kc2.get("maxword"));
					}
				}
				
			}
		}
	}
	
	public static void e() throws Exception {
//		String encryptTxt = RSAUtils.encryptNoPaddingAndBase64("王仕恩");
//		String encryptTxt = "a4+lwJCfdmHBVpbr6kVB4GwNOtsPpco9mnqUNwpkCENKGCU6/aSVg+nH8wrvinEg17cGfqeEtF72NkALGeHu9IZPMP3HxUo4skufkAQEk5SsB5PoRpg8p1gYnyAh+jQP6Shf72eA8FOc1LkBx7u6lldD5NnBUmdHwCBxdUMGNl4=";
//		System.err.println(encryptTxt.length());
//		System.err.println(new String(RSAUtils.decryptNoPaddingAndBase64(encryptTxt)));
		String txt = "AQu+G9jT/4rm5ZvaRr1TIpePu9Oau7JAno8Nm+MtniAQE6k7uXlLv/7WyiqjigoF5f7D7wfJAtKNufNo/XP8S0xt8uP8CdgLX8DStZteE/rBI8agVHNBzvg66ngO7W7CCBYpGGsMNx8vc2odX6dhhZwqzC0KW0seX7Wqxy/DA+0=";
		String realTxt = new String(RSAUtils.decryptNoPaddingAndBase64(txt));
		System.err.println(realTxt);
		String encryptTxt = RSAUtils.encryptNoPaddingAndBase64(realTxt);
		System.err.println(encryptTxt);
		System.err.println(new String(RSAUtils.decryptNoPaddingAndBase64("JlaQ/k2O3h7jOQDnympUl8HZMjM0G5cMm4m3qpgg04jCrA/shl3kLcz9/6/F8vVZCeP4EagBJh2nBu2P+iI+XTeXpZo05TgiGjFOhLa1J3DD3QOI75HfGcAv6tVlc3uJVILryyZo8rU8I2jcw+TqOHFua1fD2qTNt6Br4VRFyGY=")));
		System.err.println(new String(RSAUtils.decryptNoPaddingAndBase64("WfI9iDbVV9utWIcExC9qCSusjzrvje/MSvUnR5GOJUAnNalSqrxzVdOfjXr9Hw1zbw6VOzevKf4XYuRQaQl9wtEGN1wmHLCpX8Jhy2J6ikpEUI6OAAwhNaiCLRZVMmYDBnGxZ1auqy5CLw47BD5W1w9htrThwcqazkTOEg7zYA8=")));
	}
	
	public static void main(String[] args) throws Exception {
		e();
		System.exit(0);
	}

}
