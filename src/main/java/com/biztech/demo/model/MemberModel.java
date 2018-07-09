package com.biztech.demo.model;

import com.biztech.demo.util.DateUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "member")
public class MemberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "user_id")
    private String userId;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "surname")
    private String surname;

    @Column(name = "created_date")
    private Date createdDate;

    @Override
    public String toString() {

        String tmpDate;
        try {
            tmpDate = DateUtil.DefaultDateToString(createdDate);
        } catch (Exception e) {
            tmpDate = null;
        }
        return "MemberModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", createdDate=" + tmpDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCreatedDate() {

        try {
            return DateUtil.DefaultDateToString(createdDate);
        } catch (Exception e) {
            return null;
        }
    }

//    public Date getCreatedDate() {
//        return createdDate;
//    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
