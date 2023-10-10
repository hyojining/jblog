package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public List<PostVo> getPostByCategoryNo(Long categoryNo) {
		return postRepository.findByCategoryNo(categoryNo);
	}

	public PostVo getPostByNo(Long no) {
		return postRepository.findByNo(no);
	}

	public void insertPost(PostVo postVo) {
		postRepository.insert(postVo);
	}
	
}
