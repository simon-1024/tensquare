package com.tensquare_friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 增加粉丝数和关注数
     * @param userid   自己的ID
     * @param friendid  好友的ID
     * @param x   粉丝增加或者减少
     */
    @PutMapping("/user/{userid}/{friendid}/{x}")
    public void updatefanscountAndfollowcount(@PathVariable("userid") String userid,@PathVariable("friendid") String friendid,@PathVariable("x") int x);

}
