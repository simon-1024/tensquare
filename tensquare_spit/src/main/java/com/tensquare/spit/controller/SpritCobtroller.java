package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpritCobtroller {

    @Autowired
    private SpitService service;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",service.findAll());
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",service.findById(id));
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id){
        service.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @PostMapping()
    public Result save(@RequestBody Spit spit){

        service.save(spit);
        return new Result(true,StatusCode.OK,"插入成功");
    }

    @PutMapping("/{id}")
    public Result updateById(@PathVariable String id,@RequestBody Spit spit){
        spit.set_id(id);
        service.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentid,@PathVariable Integer page,@PathVariable Integer size){
        Page<Spit> pageData = service.findByParentId(parentid, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageData.getTotalElements(),pageData.getContent()));
    }

    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId){

        //判断当前用户是否已经点赞，由于没有认证，暂时把uid写死
        String uid = "111";
        //判断当前用户是否已经点赞
        if((redisTemplate.opsForValue().get("thumbup_"+uid))!=null){
            return new Result(false,StatusCode.REPERROR,"不能重复点赞");
        }
        service.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+uid,111);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
