package com.homefit.homefit.notification.application;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.homefit.homefit.notification.application.command.CreateNotificationCommand;
import com.homefit.homefit.notification.application.command.UpdateNotificationCommand;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homefit.homefit.notification.application.dto.NotificationDto;
import com.homefit.homefit.notification.application.dto.NotificationPageDto;
import com.homefit.homefit.notification.controller.request.CreateNotificationRequest;
import com.homefit.homefit.notification.controller.request.UpdateNotificationRequest;
import com.homefit.homefit.notification.domain.Notification;
import com.homefit.homefit.notification.persistence.NotificationRepository;
import com.homefit.homefit.notification.persistence.po.NotificationPagePo;
import com.homefit.homefit.notification.persistence.po.NotificationPo;
import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.security.util.UserPrincipalUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    // 공지사항 생성
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public NotificationDto createNotification(CreateNotificationCommand command) {
        Long writerId = UserPrincipalUtil.getId().orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED,
                "로그인 후 이용해주세요"));

        Notification notification = Notification.of(
                command.getTitle(),
                command.getContent(),
                writerId);

        int result = notificationRepository.insert(notification);

        if (result == 0) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "공지사항 생성 후 조회에 실패했습니다");
        }

        NotificationPo po = notificationRepository.selectById(notification.getId());
        return NotificationDto.from(po);
    }

    // 공지사항 수정
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public NotificationDto updateNotification(UpdateNotificationCommand command) {
        // 기존 공지사항 조회
        NotificationPo existingPo = notificationRepository.selectById(command.getId());

        if (existingPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "공지사항 조회에 실패했습니다");
        }

        // 로그인한 유저의 id 조회
        Long loginId = UserPrincipalUtil.getId().orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED,
                "로그인 후 이용해주세요"));

        // 공지사항 작성자와 로그인한 유저의 id가 다르면 수정 불가
        if (!existingPo.getNotificationWriterId().equals(loginId)) {
            throw new HomefitException(HttpStatus.FORBIDDEN, "등록한 어드민만 수정할 수 있습니다");
        }

        // Po를 도메인 객체로 변환
        Notification notification = existingPo.toDomain();

        // 공지사항 수정
        notification.update(command.getTitle(), command.getContent());

        int result = notificationRepository.update(notification);
        if (result == 0) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "공지사항 수정에 실패했습니다");
        }

        // 수정된 공지사항을 다시 조회해서 DTO로 반환
        NotificationPo updatedPo = notificationRepository.selectById(command.getId());
        if (updatedPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "공지사항 수정 후 조회에 실패했습니다");
        }
        return NotificationDto.from(updatedPo);
    }

    // 공지사항 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteNotification(Long id) {
        NotificationPo existingPo = notificationRepository.selectById(id);
        if (existingPo == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "공지사항 조회에 실패했습니다");
        }

        // 로그인한 유저의 id 조회
        Long loginId = UserPrincipalUtil.getId().orElseThrow(() -> new HomefitException(HttpStatus.UNAUTHORIZED,
                "로그인 후 이용해주세요"));

        // 공지사항 작성자와 로그인한 유저의 id가 다르면 삭제 불가
        if (!existingPo.getNotificationWriterId().equals(loginId)) {
            throw new HomefitException(HttpStatus.FORBIDDEN, "등록한 어드민만 삭제할 수 있습니다");
        }

        int result = notificationRepository.deleteById(id);
        if (result == 0) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "공지사항 삭제에 실패했습니다");
        }
    }

    // 공지사항 단건 조회
    @PreAuthorize("hasAnyRole('BASIC', 'ADMIN')")
    public NotificationDto getNotification(Long id) {
        // 조회 후 DTO로 변환
        NotificationPo po = notificationRepository.selectById(id);
        if (po == null) {
            throw new HomefitException(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다");
        }
        return NotificationDto.from(po);
    }

    // 공지사항 목록 조회 (페이지네이션)
    @PreAuthorize("hasAnyRole('BASIC', 'ADMIN')")
    public NotificationPageDto getNotifications(int page, int size) {
        // offset 계산 (page는 1부터 시작하므로 -1)
        int offset = (page - 1) * size;

        // 페이지 정보와 목록을 한 번에 조회
        NotificationPagePo pageInfo = notificationRepository.selectPageWithList(page, size, offset);
        if (pageInfo == null) {
            return NotificationPageDto.of(
                Collections.emptyList(),
                page,
                size,
                0,
                0
            );
        }
        // DTO 리스트로 변환
        List<NotificationPageDto.Notification> notifications = pageInfo.getNotifications().stream()
                .map(NotificationPageDto.Notification::from)
                .collect(Collectors.toList());

        return NotificationPageDto.of(
                notifications,
                pageInfo.getCurrentPage(),
                pageInfo.getPageSize(),
                pageInfo.getTotalElements(),
                pageInfo.getTotalPages());
    }
}