package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public BlogVo getBlog(String blogId) {
		return blogRepository.findById(blogId);
	}

	public void updateAdminBasic(BlogVo blogVo) {
		blogRepository.update(blogVo);
	}
	
}
