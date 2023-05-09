//package com.hncboy.chatgpt.front.service.impl;
//
//import cn.hutool.core.util.BooleanUtil;
//import cn.hutool.core.util.StrUtil;
//import com.hncboy.chatgpt.base.config.ChatConfig;
//import com.hncboy.chatgpt.base.domain.entity.UserSecretDO;
//import com.hncboy.chatgpt.base.exception.ServiceException;
//import com.hncboy.chatgpt.base.service.UserSecretService;
//import com.hncboy.chatgpt.front.domain.request.VerifySecretRequest;
//import com.hncboy.chatgpt.front.domain.vo.ApiModelVO;
//import com.hncboy.chatgpt.front.service.AuthService;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
///**
// * @author hncboy
// * @date 2023/3/22 20:05
// * 鉴权相关业务实现类
// */
//@Slf4j
//@Service
//public class AuthServiceImpl implements AuthService {
//
//    @Resource
//    private ChatConfig chatConfig;
//
//    @Resource
//    private UserSecretService userSecretService;
//
//    @Override
//    public String verifySecretKey(VerifySecretRequest verifySecretRequest) {
//        if (BooleanUtil.isFalse(chatConfig.hasAuth())) {
//            return "未设置密码";
//        }
//
//        if (StrUtil.isEmpty(verifySecretRequest.getToken())) {
//            throw new ServiceException("Secret key is empty");
//        }
//
//        UserSecretDO userSecretDO = userSecretService.queryBySecret(verifySecretRequest.getToken());
//        if (userSecretDO != null) {
//            if (userSecretDO.getBalance() <= 0) {
//                log.error("secret 余额为0，请去公众号  进行申请！");
//                throw new ServiceException("secret 余额为0，请去公众号 AI小薪 进行申请！");
//            }
//            log.info("userSecretDo:{}", userSecretDO);
//            return "Verify successfully";
//        }
//
//        throw new ServiceException("密钥无效 | Secret key is invalid");
//    }
//
//    @Override
//    public ApiModelVO getApiModel() {
//        ApiModelVO apiModelVO = new ApiModelVO();
//        apiModelVO.setAuth(chatConfig.hasAuth());
//        apiModelVO.setModel(chatConfig.getApiTypeEnum());
//        return apiModelVO;
//    }
//}
