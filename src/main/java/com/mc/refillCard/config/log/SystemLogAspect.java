package com.mc.refillCard.config.log;


import com.mc.refillCard.annotation.ControllerDetailLog;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.annotation.SystemServiceLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.entity.LogInformation;
import com.mc.refillCard.entity.User;
import com.mc.refillCard.service.LogService;
import com.mc.refillCard.service.UserService;
import com.mc.refillCard.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Aspect
@Component
@SuppressWarnings("all")
public class SystemLogAspect {

    @Resource
    LogService logService;
    @Resource
    UserService userService;

    @Pointcut("@annotation(com.mc.refillCard.annotation.SystemServiceLog)")
    public void serviceAspect(){

    }

    //包含所有加了注释的方法
    @Pointcut("@annotation(com.mc.refillCard.annotation.SystemControllerLog)")
    public void controllerAspect(){

    }

    @Pointcut("@annotation(com.mc.refillCard.annotation.ControllerDetailLog)")
    public void controllerDetailAspect(){

    }

    @Pointcut("@annotation(com.mc.refillCard.annotation.MethodTimeLog)")
    public void methodTimeAspect(){

    }


    //后置通知
    @AfterReturning(value = "controllerAspect()",returning="result")
    public void doAfter(JoinPoint joinPoint,Object result){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserVo userVo = (UserVo) SecurityUtils.getSubject().getPrincipal();
        //针对登录接口无法获取到用户信息，则从登录返回信息获取用户信息
        if("/login".equals(request.getRequestURI())){
            Result r = (Result) result;
            userVo = (UserVo) r.getData();
            //退出登录接口
        }else if("/login/logout".equals(request.getRequestURI())){
            Result r = (Result) result;
            userVo = (UserVo) r.getData();
        }

        String ip = getIpAddr(request);
        try {
            LogInformation log = new LogInformation();
            if (userVo != null) {
                log.setUserId(userVo.getId());
                log.setUserName(userVo.getUserName());
                log.setCompanyId(Long.valueOf(0));
            } else {
                log.setUserName("admin");
                log.setCompanyId(Long.valueOf(0));
            }
            String[] msgs = getControllerMethodDescription(joinPoint).split(":");
            log.setAction(msgs[0]);
            log.setResult(msgs[1]);
            log.setCreateTime(new Date());
            log.setIp(ip);
            logService.add(log);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     *  获取真实ip
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {

                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * @Description  获取注解中对方法的描述信息 用于Controller层注解
     * @date 2019年11月7日 上午12:01
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();//请求的类名
        String methodName = joinPoint.getSignature().getName();//目标方法名
        Object[] arguments = joinPoint.getArgs();//请求的方法参数值
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        String remarks = "";
        for (Method method:methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length==arguments.length){
                    if (method.getAnnotation(SystemControllerLog.class)!=null){
                        remarks = method.getAnnotation(SystemControllerLog.class).remarks();
                        description = method.getAnnotation(SystemControllerLog.class).description()+remarks;
                    }else {
                        description = method.getAnnotation(ControllerDetailLog.class).description();
                    }
                    break;
                }
            }
        }
        return description;
    }


    /**
     * @Description  获取注解中对方法的描述信息 用于service层注解
     * @date 2019年11月7日 下午5:05
     */
    public static String getServiceMethodDescription(JoinPoint joinPoint)throws Exception{
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method:methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length==arguments.length){
                    description = method.getAnnotation(SystemServiceLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * @Description  异常通知 用于拦截service层记录异常日志
     * @date 2019年11月7日 下午5:43
     */
    @AfterThrowing(pointcut = "serviceAspect()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        User user = (User) session.getAttribute("user");
        //获取请求ip
        String ip = request.getRemoteAddr();
        //获取用户请求方法的参数并序列化为JSON格式字符串
        String params = "";
        if (joinPoint.getArgs()!=null&&joinPoint.getArgs().length>0){
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
//                System.out.println(joinPoint.getArgs()[i]+"joinPoint.getArgs()[i]----------------------");
//                params+= JsonUtils.objectToJson(joinPoint.getArgs()[i])+";";
            }
        }
        try{
            /*========控制台输出=========*/
            System.out.println("=====异常通知开始=====");
            System.out.println("异常代码:" + e.getClass().getName());
            System.out.println("异常信息:" + e.getMessage());
            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            System.out.println("方法描述:" + getServiceMethodDescription(joinPoint));
            System.out.println("请求人:" + user.getUserName());
            System.out.println("请求IP:" + ip);
            System.out.println("请求参数:" + params);
            /*==========数据库日志=========*/
//            Action action = new Action();
//            action.setActionDes(getServiceMethodDescription(joinPoint));
//            action.setActionType("1");
//            action.setUserId(user.getId());
//            action.setActionIp(ip);
//            action.setActionTime(new Date());
//            //保存到数据库
//            actionService.add(action);
        }catch (Exception ex){
            //记录本地异常日志
//            logger.error("==异常通知异常==");
//            logger.error("异常信息:{}", ex.getMessage());
        }
    }


    @Around("methodTimeAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            System.out.println("执行了" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()
                    + "方法,耗时:" + (end - start) + " ms!");
            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            System.out.println(joinPoint + ",耗时:" + (end - start) + " ms,抛出异常 :" + e.getMessage());
            throw e;
        }
    }
}
