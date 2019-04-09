package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private HttpServletRequest request ;

	@Autowired
	private JwtUtil jwtUtil;

	//更新好友的粉丝数和自己的关注数
	@PutMapping("/{userid}/{friendid}/{x}")
	public void updatefanscountAndfollowcount(@PathVariable String userid,@PathVariable String friendid,@PathVariable int x){

		userService.updatefanscountAndfollowcount(x,userid,friendid);

	}

	@PostMapping("/register/{code}")
	public Result regist(@PathVariable String code,@RequestBody User user){

		String checkcode = (String) redisTemplate.opsForValue().get("checkcode_" + user.getMobile());
		if(checkcode==null){
			return  new Result(false,StatusCode.ERROR,"请先获取验证码");
		}
		if(!checkcode.equals(code)){
			return  new Result(false,StatusCode.ERROR,"验证码输入错误");
		}
		userService.add(user);
		return new Result(true,StatusCode.OK,"注册成功");
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Result login(@RequestBody User user){
		User user1 = userService.findByMobileAndPassword(user.getMobile(), user.getPassword());
		if(user1!=null){
			//生成token
			String token = jwtUtil.createJWT(user1.getId(), user1.getMobile(), "user");
			Map<String,String> map = new HashMap();
			map.put("token",token);
			map.put("roles","user");
			return new Result(true,StatusCode.OK,"登录成功",map);
		}else{
			return new Result(true,StatusCode.LOGINERROR,"登录失败");
		}
	}

	/**
	 * 发送手机注册验证码
	 */
	@PostMapping("/sendsms/{mobile}")
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");

	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}

	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		Claims claims = (Claims) request.getAttribute("admin_claims");
		if(claims==null){
			return new Result(true,StatusCode.ERROR,"无权访问");
		}
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
}
