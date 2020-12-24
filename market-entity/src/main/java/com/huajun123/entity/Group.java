package com.huajun123.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="contact_group")
public class Group {
    @Id
    private Long guid;
    private String name;
    private String creator;
    private String importtime;
    private String updatetime;
    private String updator;
    private int total;
    private String contacts;
    private String con;
}
