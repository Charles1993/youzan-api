package com.youzan.data.web.aop;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.youzan.common.bean.Result;
import com.youzan.common.exception.ErrCode;
import com.youzan.common.exception.ServiceException;

@Aspect
@Component
public class MethodAspect {
	
	private static final Result SUCCESS = new Result();
	
	// Controller层切点
    @Pointcut("execution(* com.youzan.data.web.controller..*.*(..))")
    public void controllerAspect() {
    	
    }
	
	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void handlerException(Exception e) throws Exception{
		Result ret = null;
		if(e instanceof ServiceException){
			ServiceException se = (ServiceException)e;
			ret = new Result(se.getCode(), se.getMessage());
		}else{
			ret = new Result(ErrCode.FAILURE,e.getMessage());
		}
		this.writeContent(JSON.toJSONString(ret));
	}
	
	/**
     * 将内容输出到浏览器
     *
     * @param content 输出内容
     */
    private void writeContent(String content) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        response.setHeader("icop-content-type", "exception");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.print(content);
        writer.flush();
        writer.close();
    }
	
	/**
	 * 所有执行方法后执行(判断返回对象是否为Result，如果不是添加到Result实体类)
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable 
	 */
	@Around("controllerAspect()")
	public Object doAction(ProceedingJoinPoint joinPoint) throws Throwable{
		Object obj = joinPoint.proceed();
		
		//如果为url 请求，并且为Object时，自动组装返回对象为Result
		if(this.isAspect(joinPoint) && this.retTypeIsObjectClazz(joinPoint)){
			obj = new Result(ErrCode.SUCCESS,obj);
		}
		return null == obj ? SUCCESS : obj;
	}
	
	/**
	 * 判断返回类型是否为Object
	 * @author  Evan.zhu
	 * @param joinPoint
	 * @return [Parameters description]
	 * @return boolean [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	@SuppressWarnings("rawtypes")
	private boolean retTypeIsObjectClazz(ProceedingJoinPoint joinPoint){
		Signature signature =  joinPoint.getSignature(); 
		Class returnType = ((MethodSignature) signature).getReturnType();
		if(null != returnType && returnType == Object.class){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断哪些方法需要拦截
	 * @param joinPoint
	 * @return
	 */
	private boolean isAspect(JoinPoint joinPoint){
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();  
		Method method = methodSignature.getMethod();  
		
		if(null != method){
			Annotation[] an = method.getAnnotations();
			for(int i=0;i<an.length;i++){
				if(an[i] instanceof PostMapping){
					return true;
				}else if(an[i] instanceof GetMapping){
					return true;
				}else if(an[i] instanceof RequestMapping){
					return true;
				}else if(an[i] instanceof PutMapping){
					return true;
				}else if(an[i] instanceof DeleteMapping){
					return true;
				}else if(an[i] instanceof PatchMapping){
					return true;
				}
			}
		}
		return false;
	}
	
}
