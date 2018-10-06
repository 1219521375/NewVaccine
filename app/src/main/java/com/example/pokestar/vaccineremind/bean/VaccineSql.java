package com.example.pokestar.vaccineremind.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by PokeStar on 2018/10/5.
 */

public class VaccineSql extends BmobObject{

    String VaccineName;//疫苗名
    String ValidityPeriod;//到期时间
    String Specification;//剂量
    String Number;//生产数量
    String Company;//生产公司
    String BatchNumber;//批次

    public String getVaccineName() {
        return VaccineName;
    }

    public void setVaccineName(String vaccineName) {
        VaccineName = vaccineName;
    }

    public String getValidityPeriod() {
        return ValidityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        ValidityPeriod = validityPeriod;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getBatchNumber() {
        return BatchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
    }
}
