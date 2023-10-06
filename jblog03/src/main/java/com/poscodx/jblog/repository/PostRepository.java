package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<PostVo> findByCategoryNo(Long categoryNo) {
		return sqlSession.selectList("post.findByCategoryNo", categoryNo);
	}

	public PostVo findByNo(Long no) {
		return sqlSession.selectOne("post.findByNo", no);
	}

	public void insert(PostVo postVo) {
		sqlSession.insert("post.insert", postVo);
	}

	public void delete(Long categoryNo) {
		sqlSession.delete("post.delete", categoryNo);
	}
	
}
