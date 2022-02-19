package com.haoshuai.intelligentcommunity.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageEntity {

    private Long current;
    private Long size;
    private Long total;
    private boolean isUpPage;
    private boolean isBelowPage;
    private List<?> list;

    public PageEntity(Page<?> page) {
        this.current = page.getCurrent();
        this.size = page.getSize();
        this.total = page.getTotal();
        this.isUpPage = page.hasPrevious();
        this.isBelowPage = page.hasNext();
        this.list = page.getRecords();
    }
    public PageEntity(IPage<?> page) {
        this.current = page.getCurrent();
        this.size = page.getSize();
        this.total = page.getTotal();
//        this.isUpPage = page.hasPrevious();
//        this.isBelowPage = page.hasNext();
        this.list = page.getRecords();
    }
}
