package com.hncboy.chatgpt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncboy.chatgpt.base.domain.entity.UserSecretDO;
import com.hncboy.chatgpt.base.mapper.UserSecretMapper;
import com.hncboy.chatgpt.base.service.UserSecretService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hncboy
 * @date 2023/3/25 16:33
 * 聊天记录相关业务实现类
 */
@Slf4j
@Service("FrontUserSecretServiceImpl")
public class UserSecretServiceImpl extends ServiceImpl<UserSecretMapper, UserSecretDO> implements UserSecretService {


    @Override
    public UserSecretDO queryBySecret(String secret) {
        QueryWrapper<UserSecretDO> wrapper = new QueryWrapper<>();
        wrapper.eq("secret", secret);
        return getOne(wrapper);
    }

    @Override
    public void updateBalance(Long balance, Long id) {
        log.info("balance:{},id:{}", balance, id);
        UpdateWrapper<UserSecretDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("balance", balance)
                .eq("id", id);
        update(updateWrapper);
    }

    @Override
    public UserSecretDO queryByUserName(String userName) {
        QueryWrapper<UserSecretDO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return getOne(wrapper);    }

    @Override
    public void checkSecret() {
    }
}
