package com.kh.coreflow.department.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.department.model.dto.DepartmentDto;
import com.kh.coreflow.department.model.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDto.DepartmentLite>> findAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }
	
}
