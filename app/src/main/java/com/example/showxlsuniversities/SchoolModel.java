package com.example.showxlsuniversities;

/**
 * @ClassName SchoolModel
 * @Description TODO
 * @Author SeanLim
 * @Date 2021-9-17 10:04
 * @E-mail linlin.1016@qq.com
 * @Version 1.0
 */
public class SchoolModel {
    private String SchoolId;
    private String SchoolName;
    private String SchoolCode;
    private String SchoolAdminId;

    public String getSchoolAdminId() {
        return SchoolAdminId;
    }

    public void setSchoolAdminId(String schoolAdminId) {
        SchoolAdminId = schoolAdminId;
    }

    public String getSchoolId() {
        return this.SchoolId;
    }

    public String getSchoolName() {
        return this.SchoolName;
    }

    public String getSchoolCode() {
        return this.SchoolCode;
    }

    public void setSchoolId(String paramString) {
        this.SchoolId = paramString;
    }

    public void setSchoolName(String paramString) {
        this.SchoolName = paramString;
    }

    public void setSchoolCode(String paramString) {
        this.SchoolCode = paramString;
    }
}
