package com.henu.ocr.api.model;

import lombok.Data;

@Data
public class OrgModel {
    private String modifytime;
    private int mdm_workflow_status;
    private String modifier;
    private int mdm_datastatus;
    private String isreport;
    private String mdm_cleanstatus;
    private String innercode;
    private Object link_fieldvalue;
    private int dr;
    private String dr_mdm_code;
    private String isseal;
    private Object mdm_parentcode;
    private String cwbm;
    private Object flow_middatastatus;
    private String unitname;
    private String fathercorp;
    private String pk_corp;
    private String id;
    private String pk_mdm;
    private Object showorder;
    private String creator;
    private String createtime;
    private String unitcode;
    private String cwmc;
    private FatherCorpEntity fathercorp_entity;
    private String datastatus;
    private int mdm_duplicate;
    private String mdm_code;
    private String unitshortname;
    private int mdm_version;
    private Object taxcode;
    private String ts;
    private String fathercorp_name;
    private boolean _checked;

    @Data
    public static class FatherCorpEntity {
        private String mdm_code;
        private String unitshortname;
        private String fathercorp;
        private String showorder;
        private String isseal;
        private String isreport;
        private String unitname;
        private String unitcode;
        private String innercode;
        private String taxcode;
        private String pk_corp;
        private String cwbm;
        private String cwmc;
    }
}