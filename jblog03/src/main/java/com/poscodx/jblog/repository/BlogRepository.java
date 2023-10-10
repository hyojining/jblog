package com.poscodx.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public BlogVo findById(String blogId) {
		return sqlSession.selectOne("blog.findById", blogId);
	}

	public void insert(UserVo userVo) {
		BlogVo blogVo = new BlogVo();
		blogVo.setBlogId(userVo.getId());
		blogVo.setImage("/assets/images/spring-logo.jpg");
		blogVo.setTitle(userVo.getName()+" 블로그");
		
		sqlSession.insert("blog.insert", blogVo);
	}

	public void update(BlogVo blogVo) {
		sqlSession.update("blog.update", blogVo);
	}
	
}
