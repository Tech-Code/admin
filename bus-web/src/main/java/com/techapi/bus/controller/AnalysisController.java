package com.techapi.bus.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techapi.bus.service.AnalysisService;
@Controller
@RequestMapping("/analysis")
public class AnalysisController {
	@Resource
	public AnalysisService analysisService;
	
	@RequestMapping("/list")
    @ResponseBody
	public Map<String, Object> list() throws Exception {
        return null;
	}
}
