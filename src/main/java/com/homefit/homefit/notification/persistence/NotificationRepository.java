package com.homefit.homefit.notification.persistence;

import com.homefit.homefit.notification.domain.Notification;
import com.homefit.homefit.notification.persistence.po.NotificationPagePo;
import com.homefit.homefit.notification.persistence.po.NotificationPo;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationRepository {
    int insert(Notification notification);

    NotificationPo selectById(Long id);

    List<NotificationPo> selectAll(int offset, int size);

    int update(Notification notification);

    int deleteById(Long id);

    long count();

    NotificationPagePo selectPageWithList(int page, int size, int offset);
}