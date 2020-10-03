package com.sugo.seckill.web.order;

import com.sugo.seckill.http.HttpResult;
import com.sugo.seckill.http.HttpStatus;
import com.sugo.seckill.order.service.SeckillOrderService;
import com.sugo.seckill.pojo.FrontUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seckill")
public class SeckillOrderController {

	@Autowired
	private SeckillOrderService seckillOrderService;






	/**
	 * @Description: 方式零：秒杀下单，不使用任何锁，会出现超卖的现象
	 * @Author: hubin
	 * @CreateDate: 2020/6/10 17:31
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/6/10 17:31
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping(value = "/startSubmitOrder/{seckillId}",method = RequestMethod.GET)
	public HttpResult submitOrder(@PathVariable Long seckillId,HttpServletRequest request){

		String token = request.getHeader("Authorization");

		//判断
		if(StringUtils.isBlank(token)){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		//单体架构，用户数据从当前session中获取
		FrontUser frontUser = seckillOrderService.getUserInfoFromRedis(token);

		//判断用户是否登录
		if(frontUser == null){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		HttpResult result = seckillOrderService.startSubmitOrder(seckillId, frontUser.getId() + "");
		return result;

	}


	/**
	 * @Description: 方式零：秒杀下单，不使用任何锁，会出现超卖的现象
	 * @Author: hubin
	 * @CreateDate: 2020/6/10 17:31
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/6/10 17:31
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping(value = "/submitOrderMul/{seckillId}",method = RequestMethod.GET)
	public HttpResult submitOrderMultiThread(@PathVariable Long seckillId,HttpServletRequest request){

		String token = request.getHeader("Authorization");

		//判断
		if(StringUtils.isBlank(token)){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		//单体架构，用户数据从当前session中获取
		FrontUser frontUser = seckillOrderService.getUserInfoFromRedis(token);

		//判断用户是否登录
		if(frontUser == null){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		HttpResult result = seckillOrderService.startSubmitOrderMultiThread(seckillId, frontUser.getId() + "");
		return result;

	}



	/**
	 * @Description: 方式一：程序锁秒杀下单
	 * @Author: hubin
	 * @CreateDate: 2020/6/10 17:31
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/6/10 17:31
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping(value = "/submitOrderLock/{seckillId}",method = RequestMethod.GET)
	public HttpResult submitOrderLock(@PathVariable Long seckillId,HttpServletRequest request){

		String token = request.getHeader("Authorization");

		//判断
		if(StringUtils.isBlank(token)){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		//单体架构，用户数据从当前session中获取
		FrontUser frontUser = seckillOrderService.getUserInfoFromRedis(token);

		//判断用户是否登录
		if(frontUser == null){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		HttpResult result = seckillOrderService.startSubmitOrderLock(seckillId, frontUser.getId() + "");
		return result;

	}

	/**
	 * @Description: 方式一：程序锁秒杀下单,多线程方式
	 * @Author: hubin
	 * @CreateDate: 2020/6/10 17:31
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/6/10 17:31
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping(value = "/submitOrderLockMultiThread/{seckillId}",method = RequestMethod.GET)
	public HttpResult submitOrderLockMultiThread(@PathVariable Long seckillId,HttpServletRequest request){

		String token = request.getHeader("Authorization");

		//判断
		if(StringUtils.isBlank(token)){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		//单体架构，用户数据从当前session中获取
		FrontUser frontUser = seckillOrderService.getUserInfoFromRedis(token);

		//判断用户是否登录
		if(frontUser == null){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		HttpResult result = seckillOrderService.startSubmitOrderLockMultiThread(seckillId, frontUser.getId() + "");
		return result;

	}


	/**
	 * @Description: 方式二：AOP锁秒杀下单
	 * @Author: hubin
	 * @CreateDate: 2020/6/10 17:31
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/6/10 17:31
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping(value = "/submitOrderAopLock/{seckillId}",method = RequestMethod.GET)
	public HttpResult submitOrderAopLock(@PathVariable Long seckillId,HttpServletRequest request){

		String token = request.getHeader("Authorization");

		//判断
		if(StringUtils.isBlank(token)){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		//单体架构，用户数据从当前session中获取
		FrontUser frontUser = seckillOrderService.getUserInfoFromRedis(token);

		//判断用户是否登录
		if(frontUser == null){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		HttpResult result = seckillOrderService.startSubmitOrderAopLock(seckillId, frontUser.getId() + "");
		return result;

	}



	/**
	 * @Description: 方式二：AOP锁秒杀下单,多线程的方式
	 * @Author: hubin
	 * @CreateDate: 2020/6/10 17:31
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/6/10 17:31
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping(value = "/submitOrderAopLockWithMultiThread/{seckillId}",method = RequestMethod.GET)
	public HttpResult submitOrderAopLockWithMultiThread(@PathVariable Long seckillId,HttpServletRequest request){

		String token = request.getHeader("Authorization");

		//判断
		if(StringUtils.isBlank(token)){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		//单体架构，用户数据从当前session中获取
		FrontUser frontUser = seckillOrderService.getUserInfoFromRedis(token);

		//判断用户是否登录
		if(frontUser == null){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		HttpResult result = seckillOrderService.startsubmitOrderAopLockWithMultiThread(seckillId, frontUser.getId() + "");
		return result;

	}


	/**
	 * @Description: 方式三：数据库悲观锁
	 * @Author: hubin
	 * @CreateDate: 2020/6/10 17:31
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/6/10 17:31
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping(value = "/submitOrderDataBaseLockForUpdate/{seckillId}",method = RequestMethod.GET)
	public HttpResult submitOrderDataBaseLockForUpdate(@PathVariable Long seckillId,HttpServletRequest request){

		String token = request.getHeader("Authorization");

		//判断
		if(StringUtils.isBlank(token)){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		//单体架构，用户数据从当前session中获取
		FrontUser frontUser = seckillOrderService.getUserInfoFromRedis(token);

		//判断用户是否登录
		if(frontUser == null){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		HttpResult result = seckillOrderService.submitOrderDataBaseLockForUpdateOne(seckillId, frontUser.getId() + "");
		return result;

	}

	/**
	 * @Description: 方式四：数据库悲观锁第二种方式，解放锁资源
	 * @Author: hubin
	 * @CreateDate: 2020/6/10 17:31
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/6/10 17:31
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping(value = "/submitOrderDataBaseLockVersion/{seckillId}",method = RequestMethod.GET)
	public HttpResult submitOrderDataBaseLockVersion(@PathVariable Long seckillId,HttpServletRequest request){

		String token = request.getHeader("Authorization");
		//判断
		if(StringUtils.isBlank(token)){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}
		//单体架构，用户数据从当前session中获取
		FrontUser frontUser = seckillOrderService.getUserInfoFromRedis(token);
		//判断用户是否登录
		if(frontUser == null){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}
		HttpResult result = seckillOrderService.submitOrderDataBaseLockForUpdateTwo(seckillId, frontUser.getId() + "");
		return result;

	}
	/**
	 * @Description: 方式五：阻塞队列BlockingQueue方式，调用秒杀下单接口
	 * @Author: hubin
	 * @CreateDate: 2020/7/15 10:34
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/7/15 10:34
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping(value = "/startSeckillByBlockingQueue/{seckillId}",method = RequestMethod.GET)
	public HttpResult startSeckillByBlockingQueue(@PathVariable Long seckillId,HttpServletRequest request){
		String token = request.getHeader("Authorization");
		//判断
		if(StringUtils.isBlank(token)){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}
		//单体架构，用户数据从当前session中获取
		FrontUser frontUser = seckillOrderService.getUserInfoFromRedis(token);
		//判断用户是否登录
		if(frontUser == null){
			return HttpResult.error(HttpStatus.SC_FORBIDDEN,"先登录，才能抢购哦!");
		}

		HttpResult httpResult = seckillOrderService.startSeckilByQueue(seckillId, frontUser.getId());

		return httpResult;

	}




	/**
	 * @Description: 获取时间
	 * @Author: hubin
	 * @CreateDate: 2020/6/10 16:19
	 * @UpdateUser: hubin
	 * @UpdateDate: 2020/6/10 16:19
	 * @UpdateRemark: 修改内容
	 * @Version: 1.0
	 */
	@RequestMapping("/submitOrder/times")
	public HttpResult getConcurrentTime(){
		return HttpResult.ok(System.currentTimeMillis()+"");
	}
	
}
