package com.jamesobin.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jamesobin.memo.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	private PostService postService;
	
	public PostRestController(PostService postService) {
		
		this.postService = postService;
	}
	
	@PostMapping("/create")
	public Map<String, String> createMemo(
			@RequestParam("title") String title
			, @RequestParam("contents") String contents
			, @RequestParam(value="imageFile", required=false) MultipartFile file
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		Map<String, String> resultMap = new HashMap<>();
		
		if(postService.addPost(userId, title, contents, file)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	@PutMapping("/update")
	public Map<String, String> updateMemo(
			@RequestParam("id") int id
			, @RequestParam("title") String title
			, @RequestParam("contents") String contents) {
		Map<String, String> resultMap = new HashMap<>();
		if(postService.updatePost(id, title, contents)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	@DeleteMapping("/delete")
	public Map<String, String> deleteMemo(@RequestParam("id") int id) {
		Map<String, String> resultMap = new HashMap<>();
		
		if(postService.deletePost(id)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
}
