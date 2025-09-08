package com.kh.coreflow.ai.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.ai.model.dto.AiDto.AiChatSession;
import com.kh.coreflow.ai.model.service.AiService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
@Tag(name="CoreFlow AI API", description="CoreFlow AI API")
public class AiController {
	private final AiService service;
	
	@GetMapping("/sessions")
	public ResponseEntity<List<AiChatSession>> getSessions(
			Authentication auth
			) {
		Long userNo = Long.parseLong(auth.getPrincipal().toString());
		
		List<AiChatSession> list = service.getSessions(userNo);
		
		if (list != null) {
			return ResponseEntity.ok(list);			
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/checkUsedBefore")
	public ResponseEntity<Boolean> checkUsedBefore(
			Authentication auth
			) {
		Long userNo = Long.parseLong(auth.getPrincipal().toString());
		
		Integer count = service.countAiUsage(userNo);
		System.out.println(count);
		
		if (count != null) {
			if (count > 0) {
				return ResponseEntity.ok(true);				
			} else {
				return ResponseEntity.ok(false);
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/insertAiUsage")
	public ResponseEntity<Void> insertAiUsage(
			Authentication auth,
			Long tokensUsed
			) {
		Long userNo = Long.parseLong(auth.getPrincipal().toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put("userNo", userNo);
		map.put("tokensUsed", tokensUsed);
		
		int result = service.insertAiUsage(map);
		
		if (result > 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PatchMapping("/updateAiUsage")
	public ResponseEntity<Void> updateAiUsage(
			Authentication auth,
			Long tokensUsed
			) {
		Long userNo = Long.parseLong(auth.getPrincipal().toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put("userNo", userNo);
		map.put("tokensUsed", tokensUsed);
		
		int result = service.updateAiUsage(map);
		
		if (result > 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/sessions")
	public ResponseEntity<Long> insertSession(
			Authentication auth,
			String title
			) {
		Long userNo = Long.parseLong(auth.getPrincipal().toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put("userNo", userNo);
		map.put("sessionNo", 0L);
		map.put("title", title);
		
		int result = service.insertSession(map);
		System.out.println(map.get("sessionNo"));
		
		if (result > 0) {
			return ResponseEntity.ok(Long.parseLong(map.get("sessionNo").toString()));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PatchMapping("/sessions/{sessionId}")
	public ResponseEntity<Void> updateSession(
			@PathVariable Long sessionId
			) {
		int result = service.updateSession(sessionId);
		
		if (result > 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}









