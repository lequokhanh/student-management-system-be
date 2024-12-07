package com.example.smsbe.service;

import com.example.smsbe.dto.SchoolYearDTO;
import com.example.smsbe.dto.SchoolYearDetailDTO;
import com.example.smsbe.request.AddClassRequest;
import com.example.smsbe.request.AddSchoolYearRequest;

import java.util.List;

public interface SchoolYearService {
    List<SchoolYearDTO> findAll();
    SchoolYearDetailDTO findById(Integer id);
    SchoolYearDTO save(AddSchoolYearRequest req);
    SchoolYearDTO update(Integer id, AddSchoolYearRequest req);
    SchoolYearDetailDTO addClass(Integer id, AddClassRequest req);
    SchoolYearDetailDTO removeClass(Integer id, Integer classId);
}
