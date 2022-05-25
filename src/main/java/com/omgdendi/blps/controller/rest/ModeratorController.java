package com.omgdendi.blps.controller.rest;

import com.omgdendi.blps.dto.req.CategoryReqDto;
import com.omgdendi.blps.dto.req.EssayReqDto;
import com.omgdendi.blps.dto.res.EssayResDto;
import com.omgdendi.blps.service.CategoryService;
import com.omgdendi.blps.service.EssayService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/moderator")
@Transactional
public class ModeratorController {

    private final EssayService essayService;
    private final CategoryService categoryService;

    @Autowired
    public ModeratorController(EssayService essayService, CategoryService categoryService) {
        this.essayService = essayService;
        this.categoryService = categoryService;
    }

    @Operation(summary = "Создать письменный материал")
    @PostMapping("/essay/create")
    public ResponseEntity<EssayResDto> createEssay(@RequestBody EssayReqDto essay) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(essayService.createApprovedEssay(essay, principal));
    }

    @Operation(summary = "Создать категорию")
    @PostMapping("category/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryReqDto category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok("Категория была успешно создана");
    }

}
