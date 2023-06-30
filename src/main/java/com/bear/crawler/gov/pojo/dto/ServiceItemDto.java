package com.bear.crawler.gov.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ServiceItemDto {

    @JsonAlias("uni")
    private String uni; // "D395CD074491675AEDB296AF9FD302EE",

    @JsonAlias("addtyp")
    private String addtyp; // "行政许可",

    @JsonAlias("infoproji")
    private String infoproji; // "003916081XK01302",

    @JsonAlias("checkMateria")
    private String checkMateria; // "S",

    @JsonAlias("deptAlia")
    private String deptAlia; // "东山县教育局",

    @JsonAlias("promiseda")
    private String promiseda; // "14",

    @JsonAlias("promisetyp")
    private String promisetyp; // "1",

    @JsonAlias("servicenam")
    private String servicenam; // "设立学前教育阶段民办学校的审批",

    @JsonAlias("caChec")
    private String caChec; // false,

    @JsonAlias("ydjFla")
    private boolean ydjFla; // "N",

    @JsonAlias("parentuni")
    private String parentuni; // "7366B95A5B645BA51A2BD73A1BAA8F9E",

    @JsonAlias("wsnu")
    private String wsnu; // "0",

    @JsonAlias("starpropert")
    private int starpropert; // 5,

    @JsonAlias("inpropert")
    private String inpropert; // "in04",

    @JsonAlias("deptnam")
    private String deptnam; // "东山县教育局",

    @JsonAlias("deptuni")
    private String deptuni; // "E4A6D93E4484D3CC68CF6ED86CFFA443",

    @JsonAlias("cknu")
    private String cknu; // "1"
}
