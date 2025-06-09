package com.homefit.homefit.notification.persistence.po;

import java.util.List;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPagePo {
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private List<NotificationPo> notifications;
}