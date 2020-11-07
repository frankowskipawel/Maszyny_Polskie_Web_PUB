package pl.maszyny_polskie.service;

import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Category;


import java.util.List;

@Service
public interface CategoryService {

    List<Category> findAll();
    Category findById(int id);
    Category save(Category category);
    void delete(Category category);
    Category findByName(String name);
}
