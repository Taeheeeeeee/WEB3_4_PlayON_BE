package com.ll.playon.domain.guild.guildJoinRequest.controller;

import com.ll.playon.domain.guild.guildJoinRequest.dto.response.GuildJoinRequestResponse;
import com.ll.playon.domain.guild.guildJoinRequest.service.GuildJoinRequestService;
import com.ll.playon.domain.member.entity.Member;
import com.ll.playon.global.response.RsData;
import com.ll.playon.global.security.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Guild Join Request", description = "길드 가입 요청 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guilds")
public class GuildJoinRequestController {

    private final GuildJoinRequestService guildJoinRequestService;
    private final UserContext userContext;

    @PostMapping("/{guildId}/join")
    @Operation(summary = "길드 가입 요청")
    public RsData<String> joinRequest(@PathVariable Long guildId) {
        Member actor = userContext.getActor();
        guildJoinRequestService.requestToJoinGuild(guildId, actor);
        return RsData.success(HttpStatus.OK, "가입 요청 완료");
    }

    @PostMapping("/{guildId}/join/{requestId}/approve")
    @Operation(summary = "가입 요청 승인")
    public RsData<String> approveRequest(
            @PathVariable Long guildId,
            @PathVariable Long requestId
    ) {
        Member actor = userContext.getActor();
        guildJoinRequestService.approveJoinRequest(guildId, requestId, actor);
        return RsData.success(HttpStatus.OK, "가입이 승인되었습니다.");
    }

    @PostMapping("/{guildId}/join/{requestId}/reject")
    @Operation(summary = "가입 요청 거절")
    public RsData<String> rejectRequest(
            @PathVariable Long guildId,
            @PathVariable Long requestId
    ) {
        Member actor = userContext.getActor();
        guildJoinRequestService.rejectJoinRequest(guildId, requestId, actor);
        return RsData.success(HttpStatus.OK, "가입 요청이 거절되었습니다.");
    }

    @GetMapping("/{guildId}/join/requests")
    @Operation(summary = "가입 요청 목록 조회")
    public RsData<List<GuildJoinRequestResponse>> getJoinRequests(@PathVariable Long guildId) {
        Member actor = userContext.getActor();
        List<GuildJoinRequestResponse> responses = guildJoinRequestService.getPendingJoinRequests(guildId, actor);
        return RsData.success(HttpStatus.OK, responses);
    }
}
