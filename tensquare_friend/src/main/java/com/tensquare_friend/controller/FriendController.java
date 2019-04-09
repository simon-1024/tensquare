package com.tensquare_friend.controller;

import com.tensquare_friend.client.UserClient;
import com.tensquare_friend.dao.FriendDao;
import com.tensquare_friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友
     */
    @PutMapping(value = "/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){

        Claims claims = (Claims) request.getAttribute("user_claims");
        String userid = claims.getId();
        if(claims==null){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        //如果是喜欢
        if(type.equals("1")){

            int result = friendService.addFriend(userid,friendid);
            if(result==0){
                return new Result(false,StatusCode.REPERROR,"不能重复添加此好友");
            }
            if(result==1){
                userClient.updatefanscountAndfollowcount(userid,friendid,1);
                return  new Result(true, StatusCode.OK,"添加成功");
            }
        }else if (type.equals("2")){
            //不喜欢
           int result= friendService.addNoFriend(claims.getId(),friendid);
           if(result==0){
               return new Result(false,StatusCode.REPERROR,"不能重复添加此非好友");
           }else{
               return  new Result(true, StatusCode.OK,"添加非好友成功");
           }
        }
        return  new Result(true, StatusCode.OK,"添加成功");
    }
}
