//package com.hncboy.chatgpt.base.handler.aspect;
//
//import cn.hutool.core.util.BooleanUtil;
//import cn.hutool.extra.servlet.JakartaServletUtil;
//import com.hncboy.chatgpt.base.config.ChatConfig;
//import com.hncboy.chatgpt.base.domain.entity.UserSecretDO;
//import com.hncboy.chatgpt.base.exception.AuthException;
//import com.hncboy.chatgpt.base.service.UserSecretService;
//import com.hncboy.chatgpt.base.util.WebUtil;
//import jakarta.annotation.Resource;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * @author hncboy
// * @date 2023/3/23 00:19
// * FrontPreAuth 用户端切面
// */
//@Aspect
//@Component
//public class FrontPreAuthAspect {
//
//    @Resource
//    private ChatConfig chatConfig;
//
//    @Resource
//    private UserSecretService userSecretService;
//
//    @Pointcut("@annotation(com.hncboy.chatgpt.base.annotation.FrontPreAuth) || @within(com.hncboy.chatgpt.base.annotation.FrontPreAuth)")
//    public void pointcut() {
//
//    }
//
//    /**
//     * 切 方法 和 类上的 @PreAuth 注解
//     *
//     * @param point 切点
//     * @return Object
//     * @throws Throwable 没有权限的异常
//     */
//    @Around("pointcut()")
//    public Object checkAuth(ProceedingJoinPoint point) throws Throwable {
//        // 没有设置鉴权
//        if (BooleanUtil.isFalse(chatConfig.hasAuth())) {
//            return point.proceed();
//        }
//
//        String authorization = JakartaServletUtil.getHeader(WebUtil.getRequest(), "Authorization", StandardCharsets.UTF_8);
//        String secret = authorization.replace("Bearer ", "").trim();
//        UserSecretDO userSecretDO = userSecretService.queryBySecret(secret);
//        if (userSecretDO == null) {
//            throw new AuthException("Error: 无访问权限,请去微信公众号 AI小薪 申请秘钥 | No access rights");
//        }
//        if (userSecretDO.getBalance() < 0) {
//            throw new AuthException("秘钥额度为0，请去微信公众号 AI小薪 申请额度");
//        }
//        return point.proceed();
//    }
//}
