package com.tensquare_friend.service;

import com.tensquare_friend.client.UserClient;
import com.tensquare_friend.dao.FriendDao;
import com.tensquare_friend.dao.NoFriendDao;
import com.tensquare_friend.pojo.Friend;
import com.tensquare_friend.pojo.NoFriend;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {

        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        // 判断：如果用户已经添加了这个好友，则不进行任何操作
        if (friend != null) {
            return 0;
        }

        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        if (friendDao.findByUseridAndFriendid(friendid, userid) != null) {
            friendDao.update(friendid, userid);
            friendDao.update(userid, friendid);
        }
        return 1;
    }

    public int addNoFriend(String userid, String friendid) {

        if (noFriendDao.findByUseridAndFriendid(userid,friendid)!=null){
            return 0;
        }
        friendDao.delete(userid, friendid);
        NoFriend  nofriend = new NoFriend();
        nofriend.setUserid(userid);
        nofriend.setFriendid(friendid);
        noFriendDao.save(nofriend);
        return 1;
    }
}