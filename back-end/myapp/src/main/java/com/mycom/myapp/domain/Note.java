package com.mycom.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycom.myapp.domain.user.Parent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "note")
// 특이사항
public class Note {

    @Id @GeneratedValue
    @Column(name = "note_id")
    private Long id;

    @Column(name = "note_content")
    private String content;

    // 학생-반
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_class_id")
    @JsonIgnore
    private StudentClass studentClass;

    // 연관관계 메서드
    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
        studentClass.getNotes().add(this);
    }

    // 생성 메서드
    public static Note createNote (StudentClass studentClass, String content) {
        Note note = new Note();

        note.setStudentClass(studentClass);
        note.setContent(content);

        return note;
    }
}
