package com.haoshuai.intelligentcommunity.service.impl;

import com.haoshuai.intelligentcommunity.entity.Message;
import com.haoshuai.intelligentcommunity.mapper.MessageMapper;
import com.haoshuai.intelligentcommunity.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-14
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
