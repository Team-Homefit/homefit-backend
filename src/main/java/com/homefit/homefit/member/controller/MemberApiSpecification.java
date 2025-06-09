package com.homefit.homefit.member.controller;

import com.homefit.homefit.member.controller.request.ModifyMemberRequest;
import com.homefit.homefit.member.controller.request.ModifyPasswordRequest;
import com.homefit.homefit.member.controller.request.ModifyRoleRequest;
import com.homefit.homefit.member.controller.request.SignUpRequest;
import com.homefit.homefit.member.controller.response.MemberResponse;
import com.homefit.homefit.member.controller.response.MemberRoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

@Tag(name = "Member", description = "회원 정보 관리에 대한 API")
public interface MemberApiSpecification {

    @Operation(
        summary = "회원가입",
        description = "이메일, 비밀번호, 닉네임과 그 외 선택정보를 입력하여 회원가입을 진행합니다.",
        requestBody = @RequestBody(
                required = true,
                description = """
                        회원가입 정보<br>
                        비밀번호와 닉네임은 필수이며, 각 양식에 따라야 합니다.<br>
                        성별에 값이 있다면 "M"과 "F" 중 하나여야 합니다.
                        """,
                content = @Content(mediaType = "application/json")
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "회원가입 성공",
                        content = @Content(schema = @Schema(implementation = MemberResponse.class), mediaType = "application/json")
                ),
                @ApiResponse(responseCode = "403", description = "인증되지 않은 이메일", content = @Content()),
                @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content())
        }
    )
    ResponseEntity<MemberResponse> signUp(SignUpRequest request, HttpSession session);

    @Operation(
            summary = "사용자 조회",
            description = """
                    member-id로 사용자를 조회합니다.<br>
                    본인인 경우 모든 정보가 반환됩니다.<br>
                    본인이 아닌 경우 마스킹 된 uername과 nickname이 반환됩니다.
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = MemberResponse.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content())
            }
    )
    ResponseEntity<MemberResponse> getMember(Long memberId);

    @Operation(
            summary = "사용자 정보 수정",
            description = """
                    로그인한 사용자 정보를 수정합니다.<br>
                    양식을 만족하는 요청에 대해 받은 데이터를 덮어 씌웁니다. (null이 오면 null로 수정됩니다.)
                    """,
            requestBody = @RequestBody(
                    required = true,
                    description = """
                        수정할 사용자 정보<br>
                        닉네임은 필수이며, 각 양식에 따라야 합니다.<br>
                        성별에 값이 있다면 "M"과 "F" 중 하나여야 합니다.
                        """,
                    content = @Content(mediaType = "application/json")
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "수정 성공",
                            content = @Content(schema = @Schema(implementation = MemberResponse.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "403", description = "권한 없음 (로그인 되어 있지 않은 경우)", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content())
            }
    )
    ResponseEntity<MemberResponse> modifyMember(ModifyMemberRequest request);

    @Operation(
            summary = "비밀번호 수정",
            description = """
                    로그인한 사용자의 비밀번호를 수정합니다.<br>
                    새로운 비밀빈호는 양식을 준수해야 합니다.
                    """,
            requestBody = @RequestBody(
                    required = true,
                    description = """
                        새로운 비밀번호
                        """,
                    content = @Content(mediaType = "application/json")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "권한 없음 (로그인 되지 않음)", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content())
            }
    )
    ResponseEntity<Void> modifyPassword(ModifyPasswordRequest request, HttpSession httpSession);

    @Operation(
            summary = "권한 변경 (관리자용)",
            description = "대상 사용자에 대한 권한을 수정합니다.",
            requestBody = @RequestBody(
                    required = true,
                    description = "대상 사용자의 memberId와 변경될 권한",
                    content = @Content(mediaType = "application/json")
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "권한 수정 성공",
                            content = @Content(schema = @Schema(implementation = MemberRoleResponse.class), mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "403", description = "권한 없음 (요청한 사용자가 관리자가 아님)", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content())
            }
    )
    ResponseEntity<MemberRoleResponse> modifyRole(ModifyRoleRequest request);

    @Operation(
            summary = "회원 탈퇴",
            description = "현재 로그인한 사용자를 탈퇴시킵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "탈퇴 성공", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content())
            }
    )
    ResponseEntity<Void> deleteMe();
}
