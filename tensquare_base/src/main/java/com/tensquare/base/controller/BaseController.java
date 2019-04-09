package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/label")
@RefreshScope
public class BaseController {

    @Autowired
    private LabelService service;

    @Value("${sms}")
    private String author;

    /**
     * 查询全部
     */
    @GetMapping()
    public Result findAll(){
        System.out.println("author = "+author);
        return new Result(true, StatusCode.OK,"查询成功",service.findAll()) ;
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",service.findById(id));
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        service.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 更新标签
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody Label label){
        label.setId(id);
        service.updateLabel(label);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    /**
     * 插入标签
     */
    @PostMapping()
    public Result add(@RequestBody Label label){
        service.addLable(label);
        return  new Result(true,StatusCode.OK,"插入成功");
    }

    /**
     * 根据条件查询
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Label label){
        List<Label> list = service.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 根据条件查询 含分页信息
     */
    @PostMapping("/search/{page}/{size}")
    public Result queryPage(@RequestBody Label label,@PathVariable int page,@PathVariable int size){
        Page pageData = service.queryPage(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));

    }

}
