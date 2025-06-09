package com.homefit.homefit.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homefit.homefit.member.application.InterestedRegionService;
import com.homefit.homefit.member.application.dto.InterestedRegionDto;
import com.homefit.homefit.member.controller.request.AddInterestedRegionsRequest;
import com.homefit.homefit.member.controller.request.DeleteInterestedRegionRequest;
import com.homefit.homefit.member.controller.response.AddInterestedRegionsResponse;
import com.homefit.homefit.member.controller.response.DeleteInterestedRegionsResponse;
import com.homefit.homefit.member.controller.response.SearchInterestedRegionResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/region")
@RestController
public class InterestedRegionController implements InterestedRegionApiSpecification {
	private final InterestedRegionService interestedRegionService;
	
	@PostMapping
	public ResponseEntity<AddInterestedRegionsResponse> addRegions(@RequestBody @Valid AddInterestedRegionsRequest request) {
		log.info("관심지역 추가 요청: 코드={}", request);
		
		Integer numberOfAdd = interestedRegionService.addInterestedRegions(request);
		
		return ResponseEntity.ok(AddInterestedRegionsResponse.of(numberOfAdd));
	}
	
	@GetMapping
	public ResponseEntity<SearchInterestedRegionResponse> searchRegions() {
		log.info("관심지역 추가 요청");
		
		InterestedRegionDto interestedRegionDto = interestedRegionService.searchInterestedRegions();
		
		return ResponseEntity.ok(SearchInterestedRegionResponse.from(interestedRegionDto));
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteInterestedRegionsResponse> deleteRegions(@RequestBody @Valid DeleteInterestedRegionRequest request) {
		log.info("관심지역 삭제 요청: 코드={}", request);
		
		Integer numberOfDelete = interestedRegionService.deleteInterestedRegions(request);
		
		return ResponseEntity.ok(DeleteInterestedRegionsResponse.of(numberOfDelete));
	}
}
