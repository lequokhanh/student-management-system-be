package com.example.smsbe.controller;

import com.example.smsbe.dto.SchoolYearDTO;
import com.example.smsbe.dto.SchoolYearDetailDTO;
import com.example.smsbe.request.AddClassRequest;
import com.example.smsbe.request.AddSchoolYearRequest;
import com.example.smsbe.request.UpdateClassRequest;
import com.example.smsbe.response.ResponseWrapper;
import com.example.smsbe.service.SchoolYearService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/school-year")
@RequiredArgsConstructor
public class SchoolYearController {
    private final SchoolYearService schoolYearService;

    @GetMapping
    public ResponseWrapper<List<SchoolYearDTO>> findAll() {
        return new ResponseWrapper<List<SchoolYearDTO>>()
                .setStatusCode(200)
                .setMessage("Get all school years successfully")
                .setData(schoolYearService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseWrapper<SchoolYearDetailDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseWrapper<SchoolYearDetailDTO>()
                .setStatusCode(200)
                .setMessage("Get school year successfully")
                .setData(schoolYearService.findById(id));
    }

    @PostMapping
    public ResponseWrapper<SchoolYearDTO> save(@RequestBody AddSchoolYearRequest req) {
        return new ResponseWrapper<SchoolYearDTO>()
                .setStatusCode(200)
                .setMessage("Save school year successfully")
                .setData(schoolYearService.save(req));
    }

    @PutMapping("/{id}")
    public ResponseWrapper<SchoolYearDTO> update(@PathVariable("id") Integer id,
                                                 @RequestBody AddSchoolYearRequest req) {
        return new ResponseWrapper<SchoolYearDTO>()
                .setStatusCode(200)
                .setMessage("Update school year successfully")
                .setData(schoolYearService.update(id, req));
    }

    @PostMapping("/{id}/class")
    public ResponseWrapper<SchoolYearDetailDTO> addClass(@PathVariable("id") Integer id,
                                                         @RequestBody AddClassRequest req) {
        return new ResponseWrapper<SchoolYearDetailDTO>()
                .setStatusCode(200)
                .setMessage("Add class to school year successfully")
                .setData(schoolYearService.addClass(id, req));
    }

    @PutMapping("/{id}/class/{classId}")
    public ResponseWrapper<SchoolYearDetailDTO> updateClass(@PathVariable("id") Integer id,
                                                            @PathVariable("classId") Integer classId,
                                                            @RequestBody UpdateClassRequest req) {
        return new ResponseWrapper<SchoolYearDetailDTO>()
                .setStatusCode(200)
                .setMessage("Update class in school year successfully")
                .setData(schoolYearService.updateClass(id, classId, req));
    }
    @DeleteMapping("/{id}/class/{classId}")
    public ResponseWrapper<SchoolYearDetailDTO> removeClass(@PathVariable("id") Integer id,
                                                            @PathVariable("classId") Integer classId) {
        return new ResponseWrapper<SchoolYearDetailDTO>()
                .setStatusCode(200)
                .setMessage("Remove class from school year successfully")
                .setData(schoolYearService.removeClass(id, classId));
    }

}
