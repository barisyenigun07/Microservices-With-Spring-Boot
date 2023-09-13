package com.barisyenigun.school.service;

import com.barisyenigun.school.client.StudentClient;
import com.barisyenigun.school.dto.FullSchoolResponse;
import com.barisyenigun.school.entity.School;
import com.barisyenigun.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final StudentClient studentClient;

    public void saveSchool(School school){
        schoolRepository.save(school);
    }

    public List<School> findAllSchools() {
        return schoolRepository.findAll();
    }

    public FullSchoolResponse findSchoolsWithStudents(Long schoolId) {
        var school = schoolRepository.findById(schoolId).orElse(School.builder()
                                                                .name("NOT_FOUND")
                                                                .email("NOT_FOUND")
                                                                .build());
        var students = studentClient.findAllStudentsBySchool(schoolId);
        return FullSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
