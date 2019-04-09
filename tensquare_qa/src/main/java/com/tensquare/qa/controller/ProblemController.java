package com.tensquare.qa.controller;
import java.awt.*;
import java.util.List;
import java.util.Map;

import com.tensquare.qa.client.LableClient;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private LableClient lableClient;

	@Autowired
	private HttpServletRequest request;

	@GetMapping("/label/{id}")
	public Result findByLableId(@PathVariable("id") String id){
		Result result = lableClient.findById(id);
		return result;
	}

	@GetMapping("/newlist/{label}/{page}/{size}")
    public Result newList( @PathVariable String label,@PathVariable Integer page,@PathVariable Integer size){
		Page<Problem> newList = problemService.newList(label, page, size);
		return new Result(true,StatusCode.OK,"查询成功",newList);

	}

	@GetMapping("/hotlist/{label}/{page}/{size}")
	public Result hotList(@PathVariable String label,@PathVariable Integer page,@PathVariable Integer size){
		Page<Problem> hotList = problemService.hotList(label, page, size);
		return new Result(true,StatusCode.OK,"查询成功",hotList);
	}

	@GetMapping("/waitlist/{label}/{page}/{size}")
	public Result waitList(@PathVariable String label,@PathVariable Integer page, @PathVariable Integer size){

		Page<Problem> waitList = problemService.waitList(label, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",waitList);
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		Claims token = (Claims) request.getAttribute("user_claims");
		if(token==null||"".equals(token)){
			return new Result(true,StatusCode.ACCESSERROR,"权限不足");
		}
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
