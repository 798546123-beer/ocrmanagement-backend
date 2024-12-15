package org.jeecg.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.Company;
import org.jeecg.modules.system.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "公司管理")
@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @ApiOperation(value = "添加公司")
    @PostMapping("/addCompany")
    @IgnoreAuth
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        // 验证必填字段
        if (company == null || company.getCompanyId() == null || company.getCompanyName() == null) {
            return ResponseEntity.badRequest()
                    .body(new HashMap<String, String>() {{
                        put("error", "Company ID and name are required");
                    }});
        }
        // 验证公司名称长度
        if (company.getCompanyName().length() > 100) {
            return ResponseEntity.badRequest()
                    .body(new HashMap<String, String>() {{
                        put("error", "Company name cannot exceed 100 characters");
                    }});
        }
        try {
            // 检查公司ID是否已存在
            Company existingCompany = companyService.getById(company.getCompanyId());
            if (existingCompany != null) {
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {{
                            put("error", "Company ID already exists");
                        }});
            }
            // 如果提供了父公司ID，检查父公司是否存在
            if (company.getFatherCompanyId() != null) {
                Company parentCompany = companyService.getById(company.getFatherCompanyId());
                if (parentCompany == null) {
                    return ResponseEntity.badRequest()
                            .body(new HashMap<String, String>() {{
                                put("error", "Parent company does not exist");
                            }});
                }
            }
            // 直接保存完整的公司信息，包括 company_id 和 father_company
            companyService.save(company);
            return ResponseEntity.ok()
                    .body(new HashMap<String, String>() {{
                        put("message", "Company created successfully");
                    }});
        } catch (Exception e) {
            log.error("Error creating company", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {{
                        put("error", "Server error occurred");
                    }});
        }
    }

    @ApiOperation(value = "查询公司信息")
    @GetMapping("/")
    @IgnoreAuth
    public ResponseEntity<?> getCompanyById(@Param("id") Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {{
                            put("error", "Company ID is required");
                        }});
            }

            Company company = companyService.getById(id);
            if (company == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new HashMap<String, String>() {{
                            put("error", "Company not found");
                        }});
            }

            // 构造符合规范的响应数据
            Map<String, Object> response = new HashMap<>();
            response.put("company_id", company.getCompanyId());
            response.put("company_name", company.getCompanyName());
            response.put("father_company_id", company.getFatherCompanyId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error querying company", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {{
                        put("error", "Server error occurred: " + e.getMessage());
                    }});
        }
    }



    @ApiOperation(value = "删除公司信息")
    @DeleteMapping("/")
    @IgnoreAuth
    public ResponseEntity<?> deleteCompanyById(@Param("id") Integer id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {{
                            put("error", "Company ID is required");
                        }});
            }

            Company existingCompany = companyService.getById(id);
            if (existingCompany == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new HashMap<String, String>() {{
                            put("error", "Company not found");
                        }});
            }

            if (companyService.removeById(id)) {
                return ResponseEntity.noContent().build();  // 返回 204 状态码
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new HashMap<String, String>() {{
                            put("error", "Failed to delete company");
                        }});
            }
        } catch (Exception e) {
            log.error("删除公司失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {{
                        put("error", "Server error occurred: " + e.getMessage());
                    }});
        }
    }

    @ApiOperation(value = "更新公司信息")
    @PutMapping("/")
    @IgnoreAuth
    public ResponseEntity<?> updateCompany(
            @Param("id") Integer id,
            @Param("companyName") String companyName,
            @Param("fatherCompanyId") Integer fatherCompanyId) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {{
                            put("error", "Company ID is required");
                        }});
            }

            if (companyName == null) {
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {{
                            put("error", "Company name is required");
                        }});
            }

            if (companyName.length() > 100) {
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {{
                            put("error", "Company name cannot exceed 100 characters");
                        }});
            }

            // 检查公司是否存在
            Company existingCompany = companyService.getById(id);
            if (existingCompany == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new HashMap<String, String>() {{
                            put("error", "Company not found");
                        }});
            }

            // 创建更新对象
            Company company = new Company();
            company.setCompanyId(id);
            company.setCompanyName(companyName);
            company.setFatherCompanyId(fatherCompanyId);

            if (companyService.updateById(company)) {
                return ResponseEntity.ok()
                        .body(new HashMap<String, String>() {{
                            put("message", "Company updated successfully");
                        }});
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {{
                        put("error", "Failed to update company");
                    }});
        } catch (Exception e) {
            log.error("Error updating company", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {{
                        put("error", "Server error occurred: " + e.getMessage());
                    }});
        }
    }

    @ApiOperation(value = "分页查询公司列表")
    @GetMapping("/getCompanyList")
    @IgnoreAuth
    public ResponseEntity<?> getCompanyList(
            @ApiParam(value = "当前页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(value = "每页显示的记录数", defaultValue = "10") @RequestParam(defaultValue = "10") Integer size) {

        // 校验参数
        if (page <= 0 || size <= 0) {
            return ResponseEntity.badRequest()
                    .body(new HashMap<String, String>() {{
                        put("error", "Invalid page or size parameter");
                    }});
        }

        try {
            // 创建分页对象
            Page<Company> pageParam = new Page<>(page, size);
            IPage<Company> pageResult = companyService.page(pageParam);

            // 构造响应数据
            Map<String, Object> result = new HashMap<>();
            result.put("total", pageResult.getTotal());
            result.put("page", pageResult.getCurrent());
            result.put("size", pageResult.getSize());
            result.put("data", pageResult.getRecords());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error querying company list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {{
                        put("error", "Server error occurred");
                    }});
        }
    }

}