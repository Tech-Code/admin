package com.techapi.bus.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techapi.bus.dao.AnalysisCityDao;
import com.techapi.bus.dao.AnalysisGroupDao;
import com.techapi.bus.dao.AnalysisTypeDao;

/***
 * 日志分析的服务
 * @author jiayusun
 *
 */
@Service
public class AnalysisService {
	@Resource
	private AnalysisCityDao analysisCityDao;
	@Resource
	private AnalysisTypeDao analysisTypeDao;
	@Resource
	private AnalysisGroupDao analysisGroupDao;
	
	
	
}
