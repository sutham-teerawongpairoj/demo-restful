package com.biztech.demo.model;

import com.biztech.demo.util.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "activity_logs")
public class ActivityModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "activity")
    private String activity;

    @Column(name = "created_date")
    private Date createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

//    public Date getCreatedDate() {
//        return createdDate;
//    }

    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public String getCreatedDate() {

        try {
            return DateUtil.DefaultDateToString(createdDate);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public String toString() {
        return "ActivityModel{" +
                "id=" + id +
                ", activity='" + activity + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
