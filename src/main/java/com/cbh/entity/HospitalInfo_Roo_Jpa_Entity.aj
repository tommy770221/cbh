// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cbh.entity;

import com.cbh.entity.HospitalInfo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect HospitalInfo_Roo_Jpa_Entity {
    
    declare @type: HospitalInfo: @Entity;
    
    declare @type: HospitalInfo: @Table(name = "hospital_info");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer HospitalInfo.id;
    
    public Integer HospitalInfo.getId() {
        return this.id;
    }
    
    public void HospitalInfo.setId(Integer id) {
        this.id = id;
    }
    
}
