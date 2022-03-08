package com.haoshuai.intelligentcommunity.service.impl;

import com.haoshuai.intelligentcommunity.entity.Comment;
import com.haoshuai.intelligentcommunity.mapper.CommentMapper;
import com.haoshuai.intelligentcommunity.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
