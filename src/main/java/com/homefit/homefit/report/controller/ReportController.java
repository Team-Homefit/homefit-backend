package com.homefit.homefit.report.controller;

import com.homefit.homefit.report.application.ReportService;
import com.homefit.homefit.report.application.dto.BanDto;
import com.homefit.homefit.report.application.dto.ReportStatisticDto;
import com.homefit.homefit.report.controller.request.ApologyRequest;
import com.homefit.homefit.report.controller.request.BanRequest;
import com.homefit.homefit.report.controller.request.ReportRequest;
import com.homefit.homefit.report.controller.response.BanResponse;
import com.homefit.homefit.report.controller.response.ReportsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/report")
@Controller
public class ReportController implements ReportApiSpecification {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Void> report(@RequestBody @Valid ReportRequest request) {
        log.info("신고 요청: {}", request);

        reportService.report(request);

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<ReportsResponse> getReports() {
        log.info("신고 조회 요청");

        List<ReportStatisticDto> reportStatisticDtos = reportService.searchReports();

        return ResponseEntity.ok(ReportsResponse.of(reportStatisticDtos));
    }
    
    @PostMapping("/ban")
    public ResponseEntity<Void> ban(@RequestBody @Valid BanRequest banRequest) {
        log.info("사용자 정지 요청: {}", banRequest);
        
        reportService.banMember(banRequest);
        
		return ResponseEntity.ok(null);
    }

    @GetMapping("/ban")
    public ResponseEntity<BanResponse> getMyBan() {
        log.info("정지 조회 요청");

        BanDto banDto = reportService.getBan();

        return ResponseEntity.ok(BanResponse.from(banDto));
    }
    
    @PostMapping("/ban/apology")
    public ResponseEntity<Void> apology(@RequestBody @Valid ApologyRequest request) {
        log.info("반성문 제출 요청: {}", request);
        
        reportService.apology(request);
        
		return ResponseEntity.ok(null);
    }
}
