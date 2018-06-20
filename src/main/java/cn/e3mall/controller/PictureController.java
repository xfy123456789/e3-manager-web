package cn.e3mall.controller;

import java.awt.PageAttributes.MediaType;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.util.FastDFSClient;
import cn.e3mall.common.util.JsonUtils;

@Controller
public class PictureController {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping(value= "/pic/upload",produces=org.springframework.http.MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		try{
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:conf/client.conf");
			String originalFileName=uploadFile.getOriginalFilename();
			String extName=originalFileName.substring(originalFileName.lastIndexOf(".")+1);
			String url=fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
			url=IMAGE_SERVER_URL+url;
			Map result=new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		}
		catch(Exception e) {
			e.printStackTrace();
			Map result=new HashMap<>();
			result.put("error", 1);
			result.put("url", "图片上传失败");
			return JsonUtils.objectToJson(result); 
		}
	}
}
