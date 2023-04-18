package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Parent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "parentacademy")
// 학부모-학원
public class ParentAcademy {

    @Id @GeneratedValue
    @Column(name = "parent_academy_id")
    private Long id;

    // 학부모
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_seq")
    @JsonIgnore
    private Parent parent;

    // 학원
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academy_id")
    @JsonIgnore
    private Academy academy;
}
