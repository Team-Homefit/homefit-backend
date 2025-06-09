package com.homefit.homefit.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.homefit.homefit.notification.controller.request.CreateNotificationRequest;
import com.homefit.homefit.notification.controller.request.UpdateNotificationRequest;
import com.homefit.homefit.notification.controller.response.NotificationDetailResponse;
import com.homefit.homefit.notification.controller.response.NotificationPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Tag(name = "공지사항", description = "공지사항 API")
public interface NotificationApiSpecification {
    @Operation(summary = "공지사항 생성", description = "새로운 공지사항을 생성합니다. (어드민만)")
    @PostMapping
    ResponseEntity<NotificationDetailResponse> create(@RequestBody @Valid CreateNotificationRequest request);

    @Operation(summary = "공지사항 수정", description = "기존 공지사항을 수정합니다. (어드민만)")
    @PatchMapping
    ResponseEntity<NotificationDetailResponse> update(@RequestBody @Valid UpdateNotificationRequest request);

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다. (어드민만)")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@Parameter(description = "공지사항 ID") @PathVariable Long id);

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 페이지네이션하여 조회합니다.")
    @GetMapping
    ResponseEntity<NotificationPageResponse> getList(
            @Parameter(description = "페이지 번호 (1부터 시작)") @RequestParam @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다") Integer page,
            @Parameter(description = "페이지 크기") @RequestParam @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다") Integer size);

    @Operation(summary = "공지사항 단건 조회", description = "특정 공지사항의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    ResponseEntity<NotificationDetailResponse> get(@Parameter(description = "공지사항 ID") @PathVariable Long id);
}