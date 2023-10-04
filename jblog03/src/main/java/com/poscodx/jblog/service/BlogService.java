package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public BlogVo getBlog(String blogId) {
		return blogRepository.findById(blogId);
	}
	
	public void insertBlog(UserVo userVo) {
		BlogVo blogVo = new BlogVo();
		blogVo.setBlogId(userVo.getId());
		blogVo.setImage("/assets/images/spring-logo.jpg");
		blogVo.setTitle(userVo.getName()+"님의 블로그");
		blogRepository.insert(blogVo);
	}

	public void updateAdminBasic(BlogVo vo) {
		blogRepository.update(vo);
	}
}