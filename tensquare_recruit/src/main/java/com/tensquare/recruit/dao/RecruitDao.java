package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    /**
     * 热门职位:查询状态码为2 的
     */
    public List<Recruit> findTop6ByStateOrderByCreatetime(String state);//where state= ? order by creatime

    /**
     * 最新职位：
     * 查询状态码不为0 的  按照日期排序
     */
    public List<Recruit> findTop6ByStateNotOrderByCreatetime(String state);//where state= ? order by creatime


}
