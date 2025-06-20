package com.homefit.homefit.notification.controller;

import com.homefit.homefit.notification.application.command.CreateNotificationCommand;
import com.homefit.homefit.notification.application.command.UpdateNotificationCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homefit.homefit.notification.application.NotificationService;
import com.homefit.homefit.notification.application.dto.NotificationDto;
import com.homefit.homefit.notification.application.dto.NotificationPageDto;
import com.homefit.homefit.notification.controller.request.CreateNotificationRequest;
import com.homefit.homefit.notification.controller.request.UpdateNotificationRequest;
import com.homefit.homefit.notification.controller.response.NotificationDetailResponse;
import com.homefit.homefit.notification.controller.response.NotificationPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Tag(name = "공지사항", description = "공지사항 관련 API")
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController implements NotificationApiSpecification {
    private final NotificationService notificationService;

    @Override
    @Operation(summary = "공지사항 생성", description = "새로운 공지사항을 생성합니다. (어드민만)")
    @PostMapping
    public ResponseEntity<NotificationDetailResponse> create(@RequestBody @Valid CreateNotificationRequest request) {
        CreateNotificationCommand command = CreateNotificationCommand.of(request.getTitle(), request.getContent());
        NotificationDto notificationDto = notificationService.createNotification(command);
        return ResponseEntity.ok(NotificationDetailResponse.from(notificationDto));
    }

    @Override
    @Operation(summary = "공지사항 수정", description = "기존 공지사항을 수정합니다. (어드민만)")
    @PatchMapping
    public ResponseEntity<NotificationDetailResponse> update(@RequestBody @Valid UpdateNotificationRequest request) {
        UpdateNotificationCommand command = UpdateNotificationCommand.of(request.getId(),  request.getTitle(), request.getContent());
        NotificationDto notificationDto = notificationService.updateNotification(command);
        return ResponseEntity.ok(NotificationDetailResponse.from(notificationDto));
    }

    @Override
    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다. (어드민만)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Operation(summary = "공지사항 단건 조회", description = "공지사항을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDetailResponse> get(@PathVariable Long id) {
        NotificationDto notificationDto = notificationService.getNotification(id);
        return ResponseEntity.ok(NotificationDetailResponse.from(notificationDto));
    }

    @Override
    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<NotificationPageResponse> getList(
            @RequestParam @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다") Integer page,
            @RequestParam @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다") Integer size) {
        NotificationPageDto notificationPageDto = notificationService.getNotifications(page, size);
        return ResponseEntity.ok(NotificationPageResponse.from(notificationPageDto));
    }
}