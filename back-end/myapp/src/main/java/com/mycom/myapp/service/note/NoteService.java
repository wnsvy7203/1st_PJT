package com.mycom.myapp.service.note;

import com.mycom.myapp.repository.NoteRepository;

import java.util.List;

public interface NoteService {

    public List<NoteRepository.NoteInfo> listNote(Long classId, Long studentId);
}
