package com.tensquare_friend.dao;

import com.tensquare_friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String> {

    public NoFriend findByUseridAndFriendid(String userid, String friendid);

}
