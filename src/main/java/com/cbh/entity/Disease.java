package com.cbh.entity;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import java.util.List;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "disease")
@RooDbManaged(automaticallyDelete = true)
public class Disease {

    transient private int orderDis=0;

    public int getOrderDis() {
        return orderDis;
    }

    public void setOrderDis(int orderDis) {
        this.orderDis = orderDis;
    }

    public static List<Disease> findAllLikeDiseases(String memberDisease) {
        String jpaQuery = "SELECT o FROM Disease o where o.symptom like :memberDisease ";
        return entityManager().createQuery(jpaQuery, Disease.class).setParameter("memberDisease", "%"+memberDisease+"%").getResultList();
    }
}
