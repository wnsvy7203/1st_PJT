package com.mycom.myapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "sido_code")
// 시도
public class Sido {

    @Id
    @Column(name = "code")
    private String sidoCode;

    @Column(name = "name")
    private String sidoName;

    @OneToMany(mappedBy = "sido")
    private List<Gugun> guguns = new ArrayList<>();
}
