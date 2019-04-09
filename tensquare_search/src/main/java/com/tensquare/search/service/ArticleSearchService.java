package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    /**
     *添加文章
     */
    public void save(Article article){
        articleSearchDao.save(article);
    }

    /**
     * 文章检索
     */
    public Page<Article> findByTitleOrContentLike(String keywords,int page,int size){

        Pageable pageable = PageRequest.of(page-1,size);
        return articleSearchDao.findByTitleOrContentLike(keywords,keywords, pageable);
    }
}
