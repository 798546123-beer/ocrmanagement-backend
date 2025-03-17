package com.henu.ocr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionModel {
    private Integer permissionId;
    private String page;
    private String pageName;
}
