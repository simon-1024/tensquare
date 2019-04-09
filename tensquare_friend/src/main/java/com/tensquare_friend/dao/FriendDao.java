package com.tensquare_friend.dao;

import com.tensquare_friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface FriendDao extends JpaRepository<Friend,String>{

    public Friend findByUseridAndFriendid(String userid,String friendid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_friend SET islike='1' WHERE userid=? AND friendid =?;",nativeQuery = true)
    public void update(String userid,String friendid);

    @Modifying
    @Query(value = "DELETE FROM tb_friend WHERE userid=? AND friendid=?;",nativeQuery = true)
    public void delete(String userid, String friendid);
}
