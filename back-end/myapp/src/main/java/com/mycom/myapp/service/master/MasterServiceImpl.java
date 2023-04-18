package com.mycom.myapp.service.master;

import com.mycom.myapp.domain.*;
import com.mycom.myapp.domain.Class;
import com.mycom.myapp.domain.user.Master;
import com.mycom.myapp.domain.user.Teacher;
import com.mycom.myapp.repository.*;
import com.mycom.myapp.repository.user.MasterRepository;
import com.mycom.myapp.repository.user.TeacherRepository;
import com.mycom.myapp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final MasterScheduleRepository masterScheduleRepository;
    private final AlertRepository alertRepository;
    private final TeacherAcademyRepository teacherAcademyRepository;
    private final StudentAcademyRepository studentAcademyRepository;
    private final AcademyRepository academyRepository;
    private final ClassRepository classRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ClassScheduleRepository classScheduleRepository;

    // 학원장 일정 등록
    @Override
    public Long createMasterSchedule(Long masterSeq, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute) {
        Master master = masterRepository.findById(masterSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        MasterSchedule masterSchedule = MasterSchedule.createMasterSchedule(master, scheduleCode, scheduleDate, startTimeHour, startTimeMinute, endTimeHour, endTimeMinute);

        return masterScheduleRepository.save(masterSchedule).getId();
    }

    // 학원장 일정 조회
    @Override
    @Transactional(readOnly = true)
    public List<MasterSchedule> listMasterSchedule(Long masterSeq) {
        return masterScheduleRepository.findByMasterSeq(masterSeq);
    }

    // 학원장 일정 수정
    @Override
    public Long changeMasterSchedule(Long scheduleId, String scheduleCode, String scheduleDate, Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute) {
        MasterSchedule masterSchedule = masterScheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        masterSchedule.setScheduleCode(scheduleCode);
        masterSchedule.setScheduleDate(scheduleDate);
        masterSchedule.setStartTimeHour(startTimeHour);
        masterSchedule.setStartTimeMinute(startTimeMinute);
        masterSchedule.setEndTimeHour(endTimeHour);
        masterSchedule.setEndTimeMinute(endTimeMinute);

        return masterSchedule.getId();
    }

    // 학원장 일정 삭제
    @Override
    public void deleteMasterSchedule(Long scheduleId) {
        masterScheduleRepository.deleteById(scheduleId);
    }

    // 학원장이 강사 등록 신청 승인
    @Override
    public Long approveTeacherApply(Long alertId) {
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        if (!alert.getAlertCode().equals("A1")) {
            throw new IllegalStateException("wrong alarm");
        }

        alert.setApproveYn(true);

        Long fromSeq = alert.getFromSeq();

        TeacherAcademy teacherAcademy = teacherAcademyRepository.findById(fromSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        teacherAcademy.setApproveYn(true);
        teacherAcademy.setApproveDate(LocalDateTime.now());

        return teacherAcademy.getId();
    }

    // 학원장이 강사 등록 신청 거절
    @Override
    public Long dismissTeacherApply(Long alertId) {
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        if (!alert.getAlertCode().equals("A1")) {
            throw new IllegalStateException("wrong alarm");
        }

        alert.setApproveYn(false);

        Long fromSeq = alert.getFromSeq();

        teacherAcademyRepository.deleteById(fromSeq);

        return fromSeq;
    }

    // 학원장이 학생 등록 승인
    @Override
    public Long approveStudentApply(Long alertId) {
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        if (!alert.getAlertCode().equals("A2")) {
            throw new IllegalStateException("wrong alarm");
        }

        alert.setApproveYn(true);

        Long fromSeq = alert.getFromSeq();

        StudentAcademy studentAcademy = studentAcademyRepository.findById(fromSeq).orElseThrow(()-> new IllegalArgumentException("no such data"));

        studentAcademy.setApproveYn(true);
        studentAcademy.setApproveDate(LocalDateTime.now());

        return studentAcademy.getId();
    }

    // 학원장이 학생 등록 거절
    @Override
    public Long dismissStudentApply(Long alertId) {
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        if (!alert.getAlertCode().equals("A2")) {
            throw new IllegalStateException("wrong alarm");
        }

        alert.setApproveYn(false);

        Long fromseq = alert.getFromSeq();

        studentAcademyRepository.deleteById(fromseq);

        return fromseq;
    }

    @Override
    public AcademyRepository.AcademyInfoResponse getAcademyInfo(Long masterSeq) {
        return academyRepository.findBymaster(masterSeq);
    }

    // 반 목록 받아오기
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "class-list")
    public List<ClassRepository.ClassInfoResponse> getAcademyClassInfo(Long academyId) {
        return classRepository.findByacademyId(academyId);
    }

    // 학원장 반 삭제
    @Override
    @CacheEvict(value = "class-list", allEntries = true)
    public void deleteAcademyClass(Long classId) {
        classRepository.deleteById(classId);
    }

    // 학원장 강사 삭제
    @Override
    public void deleteTeacher(Long teacherSeq) {
        teacherAcademyRepository.deleteByTeacherSeq(teacherSeq);
    }

    // 학원장 학생 삭제
    @Override
    public void deleteStudent(Long studentId) {
        studentAcademyRepository.deleteByStudentId(studentId);
    }

    // 반 등록
    @Override
    @CacheEvict(value = "class-list", allEntries = true)
    public Long createClass(Long academyId, Long teacherId, String className, String subjectCode, String gradeCode, String[] day, Integer[] startTimeHour, Integer[] startTimeMinute, Integer[] endTimeHour, Integer[] endTimeMinute) {
        Academy academy = academyRepository.findById(academyId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        Class clas = Class.createClass(academy, teacher, className, subjectCode, gradeCode);

        for (int i = 0; i < day.length; i++) {
            ClassSchedule classSchedule = ClassSchedule.createClassSchedule(clas, day[i], startTimeHour[i], startTimeMinute[i], endTimeHour[i], endTimeMinute[i]);
            classScheduleRepository.save(classSchedule);
        }

        Long classId = classRepository.save(clas).getId();

        return classId;
    }

    // 학원-학생 전체 조회
    @Override
    public List<StudentRepository.StudentInfo> listAcademyStudents(Long academyId) {
        return studentRepository.findByacademyId(academyId);
    }

    // 학원-강사 전체 조회(유저)
    @Override
    public List<UserRepository.TeacherInfo> listTeacherInfo_user(Long academyId) {
        return userRepository.findByacademyId(academyId);
    }

    // 학원-강사 전체 조회(강사)
    @Override
    public List<TeacherRepository.TeacherInfo> listTeacherInfo_teacher(Long academyId) {
        return teacherRepository.findByacademyId(academyId);
    }

    @Override
    public List<MasterSchedule> listMasterScheduleDate(Long masterSeq, String date) {
        return masterScheduleRepository.findBymasterSeqAndDate(masterSeq, date);
    }

    // 학원 정보 수정
    @Override
    @CacheEvict(value = "academy", allEntries = true)
    public Long updateAcademy(Long academyId, String academyName, String zipCode, String firstAddress, String lastAddress, String phoneNumber, String introduce) {
        Academy academy = academyRepository.findById(academyId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        academy.setName(academyName);
        academy.setAddress(new Address(zipCode, firstAddress, lastAddress));
        academy.setPhoneNumber(phoneNumber);
        academy.setIntroduce(introduce);

        return academyId;
    }

    @Override
    @CacheEvict(value = "class-list", allEntries = true)
    public Long changeClassTeacher(Long classId, Long teacherSeq) {
        Class clas = classRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("no such data"));
        Teacher teacher = teacherRepository.findById(teacherSeq).orElseThrow(() -> new IllegalArgumentException("no such data"));

        clas.setTeacher(teacher);

        return clas.getId();
    }
}
