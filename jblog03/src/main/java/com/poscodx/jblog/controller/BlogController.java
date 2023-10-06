package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileUploadService fileuploadService;
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
		@PathVariable("id") String blogId, 
		@PathVariable("categoryNo") Optional<Long> categoryNo, 
		@PathVariable("postNo") Optional<Long> postNo,
		Model model) {
		
	    BlogVo blogVo = blogService.getBlog(blogId);
	    if(blogVo == null) {
			return "error/404"; // 블로그 존재하지 않는 경우
		}
	    
	    List<CategoryVo> categoryList = categoryService.getCategory(blogId);
	    
	    // 카테고리 번호가 없는 경우, 첫 번째 카테고리 번호로 설정
	    Long selectedCategoryNo = categoryNo.orElse(categoryList.isEmpty() ? null : categoryList.get(0).getNo());
	    
	    List<PostVo> postList = postService.getPostByCategoryNo(selectedCategoryNo);
	    
	    // 포스트 번호가 없는 경우, 첫 번째 포스트 번호로 설정
	    Long selectedPostNo = postNo.orElse(postList.isEmpty() ? null : postList.get(0).getNo());
	    
	    PostVo post = postService.getPostByNo(selectedPostNo);
	    
	    model.addAttribute("blog", blogVo);
	    model.addAttribute("categoryNo", selectedCategoryNo);
	    model.addAttribute("categoryList", categoryList);
	    model.addAttribute("postList", postList);
	    model.addAttribute("post", post);
		
		return "blog/main";
	}
	
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blog", blogVo);
		return "blog/admin-basic";
	}
	
	@RequestMapping(value="/admin/basic/update", method=RequestMethod.POST)
	public String update(@PathVariable("id") String blogId, BlogVo blogVo, @RequestParam("file") MultipartFile file) {
		
		String url = fileuploadService.restore(file);
		
	    if (url != null) {
	    	blogVo.setImage(url);
	    } else {
	    	// 이미지를 업로드하지 않은 경우, 기존 이미지 URL을 유지
	    	BlogVo originalBlog = blogService.getBlog(blogId);
	    	blogVo.setImage(originalBlog.getImage());
	    }

	    blogVo.setBlogId(blogId);
		
		blogService.updateAdminBasic(blogVo); // 블로그 정보 업데이트
		
		return "redirect:/" + blogId + "/admin/basic"; 
	}
	
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		List<CategoryVo> categoryList = categoryService.getCategory(blogId);

		model.addAttribute("blog", blogVo);
		model.addAttribute("category", categoryList);
		
		return "blog/admin-category";
	}
	
	@RequestMapping(value="/admin/category", method=RequestMethod.POST)
	public String adminCategory(@PathVariable("id") String blogId, CategoryVo categoryVo) {
		categoryVo.setBlogId(blogId);
		categoryService.insertCategory(categoryVo); // 카테고리 추가
		
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping("/admin/category/delete/{no}")
	public String delete(@PathVariable("id") String blogId,	@PathVariable("no") Long no) {
		postService.deletePost(no);
		categoryService.deleteCategory(no);
		
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		List<CategoryVo> categoryList = categoryService.getCategory(blogId);

		model.addAttribute("blog", blogVo);
		model.addAttribute("category", categoryList);
		
		return "blog/admin-write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String adminWrite(@PathVariable("id") String blogId, @RequestParam(value="category", required=true, defaultValue="") Long categoryNo, PostVo postVo) {
		postVo.setCategoryNo(categoryNo);
		postService.insertPost(postVo); // 게시글 추가
		
		return "redirect:/" + blogId;
	}
}
