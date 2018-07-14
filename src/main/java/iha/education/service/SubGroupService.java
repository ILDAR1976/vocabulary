package iha.education.service;

import iha.education.entity.SubGroup;

import java.util.List;


public interface SubGroupService {

    SubGroup save(SubGroup SubGroup);
    List<SubGroup> findAll();
    SubGroup findById(int id);
    SubGroup findById(Long id);
    SubGroup findByName(String name);

}
