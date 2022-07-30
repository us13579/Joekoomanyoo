package com.project.common.controller;

import com.project.common.entity.HeritageEntity;
import com.project.common.service.HeritageServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("HeritageController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/heritage")
public class HeritageController {

    private final HeritageServiceImpl heritageService;

    @ApiOperation(value="문화 유산 상세 정보 불러오기, 문화유산 번호를 통해 상세정보를 불러온다.", response = List.class)
    @GetMapping("/list")
    public ResponseEntity<List<HeritageEntity>> listInfo() throws Exception {
        return new ResponseEntity<List<HeritageEntity>>(heritageService.listInfo(), HttpStatus.OK);
    }


}
