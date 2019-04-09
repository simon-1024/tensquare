package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService service;

    @PostMapping()
    public Result save(@RequestBody Article article){
        service.save(article);
        return new Result(true, StatusCode.OK,"插入成功");
    }

    @GetMapping("/{keywords}/{page}/{size}")
    public Result findByTitleOrContentLisk(@PathVariable String keywords,@PathVariable int page,@PathVariable int size){
        Page<Article> pageData = service.findByTitleOrContentLike(keywords, page, size);
        return new Result(true,StatusCode.OK,"查询成功", new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
    }
}
