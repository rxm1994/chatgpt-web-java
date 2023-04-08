package com.hncboy.chatgpt.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hncboy.chatgpt.base.domain.entity.UserSecretDO;

/**
 * @author hncboy
 * @date 2023/3/25 16:30
 * 聊天记录相关业务接口
 */
public interface UserSecretService extends IService<UserSecretDO> {

    UserSecretDO queryBySecret(String secret);

    void updateBalance(Long balance, Long id);

    void checkSecret();
}
