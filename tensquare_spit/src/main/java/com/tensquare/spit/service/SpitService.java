package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.ConfigFile;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao dao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate template;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<Spit> findAll(){
        return dao.findAll();
    }

    public Spit findById(String id){

        //findOne由于springboot版本限制已经不再使用,使用如下方法
        return dao.findById(id).get();
    }

    public void deleteById(String id){
          dao.deleteById(id);
    }

    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //如果当前添加的吐槽，有父节点，那么父节点的吐槽数要+1
        if(spit.getParentid()!=null &&!"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            template.updateFirst(query,update,"spit");
        }
        dao.save(spit);
    }

    public void update(Spit spit){
        dao.save(spit);
    }

    public Page<Spit> findByParentId(String parentid,int page,int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return  dao.findByParentid(parentid,pageable);
    }

    public void thumbup(String id){

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup",1);
        template.updateFirst(query,update,"spit");
    }

}
