package com.bear.crawler.gov.controller;

import com.bear.crawler.gov.service.GovService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/gov")
@RestController
public class GovController {

    @Autowired
    private GovService govService;

    @PostMapping("/syncPersonalAffairsByCategory")
    public String syncPersonalAffairsByCategory() {
        govService.syncPersonalAffairsByCategory();
        return "syncPersonalAffairsByCategory successfully";
    }

    @PostMapping("/filterPersonalAffairs")
    public String filterPersonalAffairs() {
        govService.filterPersonalAffairs();
        return "filterPersonalAffairs successfully";
    }

    @PostMapping("/syncPersonalAffairsByDepartment")
    public String syncPersonalAffairsByDepartment() {
        govService.syncPersonalAffairsByDepartment();
        return "syncPersonalAffairsByDepartment successfully";
    }
}
