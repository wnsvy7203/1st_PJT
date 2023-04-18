package com.mycom.myapp.service.note;

import com.mycom.myapp.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<NoteRepository.NoteInfo> listNote(Long classId, Long studentId) {
        return noteRepository.findByclassIdAndStudentId(classId, studentId);
    }
}
