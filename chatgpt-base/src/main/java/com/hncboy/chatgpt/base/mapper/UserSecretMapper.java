package com.hncboy.chatgpt.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hncboy.chatgpt.base.domain.entity.UserSecretDO;
import org.springframework.stereotype.Repository;

/**
 * @author xm
 * @date 2023/3/25 16:28
 * 聊天记录数据访问层
 */
@Repository("FrontUserSecretMapper")
public interface UserSecretMapper extends BaseMapper<UserSecretDO> {
}
