package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository;

	public List<CategoryVo> getCategory(String blogId) {
		return categoryRepository.findById(blogId);
	}
	
	public void insertCategory(CategoryVo categoryVo) {
		categoryRepository.insert(categoryVo);
	}

	@Transactional
	public void deleteCategory(Long no) {
		postRepository.delete(no);
		categoryRepository.delete(no);
	}
	
}
