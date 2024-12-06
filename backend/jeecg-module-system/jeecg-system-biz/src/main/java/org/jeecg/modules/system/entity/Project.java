package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "project_name", nullable = false, length = 100)
    private String projectName;

    @Column(name = "project_part", nullable = false, length = 100)
    private String projectPart;

    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @Column(name = "project_head_id", nullable = false)
    private Integer projectHeadId;

    @Column(name = "sub_conductor", length = 100)
    private String subConductor;

    @Column(name = "sub_head", length = 100)
    private String subHead;

    // Getters and Setters
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPart() {
        return projectPart;
    }

    public void setProjectPart(String projectPart) {
        this.projectPart = projectPart;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectHeadId() {
        return projectHeadId;
    }

    public void setProjectHeadId(Integer projectHeadId) {
        this.projectHeadId = projectHeadId;
    }

    public String getSubConductor() {
        return subConductor;
    }

    public void setSubConductor(String subConductor) {
        this.subConductor = subConductor;
    }

    public String getSubHead() {
        return subHead;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }
}
