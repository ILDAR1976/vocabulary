package iha.education.service;

import iha.education.entity.SenseGroup;

import java.util.List;


public interface SenseGroupService {

    SenseGroup save(SenseGroup senseGroup);
    List<SenseGroup> findAll();
    SenseGroup findById(int id);
    SenseGroup findById(Long id);
    SenseGroup findByName(String name);

}
