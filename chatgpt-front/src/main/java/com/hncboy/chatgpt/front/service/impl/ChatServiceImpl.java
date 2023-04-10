package com.hncboy.chatgpt.front.service.impl;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.hncboy.chatgpt.base.config.ChatConfig;
import com.hncboy.chatgpt.base.domain.entity.UserSecretDO;
import com.hncboy.chatgpt.base.enums.ApiTypeEnum;
import com.hncboy.chatgpt.base.service.UserSecretService;
import com.hncboy.chatgpt.base.util.ObjectMapperUtil;
import com.hncboy.chatgpt.base.util.StringUtil;
import com.hncboy.chatgpt.base.util.WebUtil;
import com.hncboy.chatgpt.front.domain.request.ChatProcessRequest;
import com.hncboy.chatgpt.front.domain.vo.ChatConfigVO;
import com.hncboy.chatgpt.front.handler.emitter.ChatMessageEmitterChain;
import com.hncboy.chatgpt.front.handler.emitter.IpRateLimiterEmitterChain;
import com.hncboy.chatgpt.front.handler.emitter.ResponseEmitterChain;
import com.hncboy.chatgpt.front.handler.emitter.SensitiveWordEmitterChain;
import com.hncboy.chatgpt.front.service.ChatService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author hncboy
 * @date 2023/3/22 19:41
 * 聊天相关业务实现类
 */
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatConfig chatConfig;

    @Resource
    private UserSecretService userSecretService;

    @Override
    public ChatConfigVO getChatConfig() {
        ChatConfigVO chatConfigVO = new ChatConfigVO();
        chatConfigVO.setApiModel(chatConfig.getApiTypeEnum());
        UserSecretDO userSecretDO = userSecretService.queryBySecret(WebUtil.getSecret());
        if (chatConfig.getApiTypeEnum() == ApiTypeEnum.ACCESS_TOKEN || BooleanUtil.isFalse(chatConfig.getIsShowBalance())) {
            chatConfigVO.setBalance(StrUtil.DASHED);
        }
        chatConfigVO.setHttpsProxy(StrUtil.isAllNotEmpty(chatConfig.getHttpProxyHost(), String.valueOf(chatConfig.getHttpProxyPort()))
                ? String.format("%s:%s", chatConfig.getHttpProxyHost(), chatConfig.getHttpProxyPort())
                : StrPool.DASHED);
        chatConfigVO.setReverseProxy(chatConfig.getApiReverseProxy());
        chatConfigVO.setSocksProxy(StrPool.DASHED);
        chatConfigVO.setTimeoutMs(chatConfig.getTimeoutMs());
        chatConfigVO.setBalance(userSecretDO.getBalance() + "");
        chatConfigVO.setInitValue(userSecretDO.getInitValue());
        return chatConfigVO;
    }

    @Override
    public ResponseBodyEmitter chatProcess(ChatProcessRequest chatProcessRequest) {
        String secret = JakartaServletUtil.getHeader(WebUtil.getRequest(), "Authorization", StandardCharsets.UTF_8).replace("Bearer ", "").trim();

        // 超时时间设置 3 分钟
        ResponseBodyEmitter emitter = new ResponseBodyEmitter(3 * 60 * 1000L);
        emitter.onCompletion(() -> {
            log.info("请求参数：{}，Front-end closed the emitter connection.", ObjectMapperUtil.toJson(chatProcessRequest));
            UserSecretService userSecretService = SpringUtil.getBean(UserSecretService.class);
            UserSecretDO userSecretDo = userSecretService.queryBySecret(secret);
            int promptLength = StringUtil.countChineseAndEnglish(chatProcessRequest.getPrompt());
            log.info("last balance:{},spends words:{}", userSecretDo.getBalance(), promptLength);
            long newBalance = userSecretDo.getBalance() - promptLength;
            userSecretService.updateBalance(newBalance, userSecretDo.getId());
        });
        emitter.onTimeout(() -> log.error("请求参数：{}，Back-end closed the emitter connection.", ObjectMapperUtil.toJson(chatProcessRequest)));

        try {
            emitter.send("This is the end of the response stream.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 构建 emitter 处理链路
        ResponseEmitterChain ipRateLimiterEmitterChain = new IpRateLimiterEmitterChain();
        ResponseEmitterChain sensitiveWordEmitterChain = new SensitiveWordEmitterChain();
        sensitiveWordEmitterChain.setNext(new ChatMessageEmitterChain());
        ipRateLimiterEmitterChain.setNext(sensitiveWordEmitterChain);
        ipRateLimiterEmitterChain.doChain(chatProcessRequest, emitter);
        return emitter;
    }
}
