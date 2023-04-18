package com.mycom.myapp.controller;

import com.mycom.myapp.repository.NoteRepository;
import com.mycom.myapp.service.note.NoteService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(
        origins = {"http://localhost:8080", "https://i8e206.p.ssafy.io"},
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
)
public class NoteController {

    private final NoteService noteService;

    @ApiOperation(value = "학생 특이사항 조회", notes = "학생의 특이사항을 조회한다. ")
    @GetMapping("/notes/classes/{classId}/students/{studentId}")
    public ResponseEntity<ListNoteResponse> listNote(@PathVariable Long classId, @PathVariable Long studentId) {
        List<NoteRepository.NoteInfo> list = noteService.listNote(classId, studentId);

        ListNoteResponse response = new ListNoteResponse(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ListNoteResponse {
        List<NoteRepository.NoteInfo> list;
    }
}
