package com.kh.coreflow.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.member.model.dto.MemberDto;
import com.kh.coreflow.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberDto.MemberLite>> search(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(required = false, defaultValue = "30") Integer limit,
            @RequestParam(required = false) Long depId
    ) {
        return ResponseEntity.ok(memberService.search(query, limit, depId));
    }
}
