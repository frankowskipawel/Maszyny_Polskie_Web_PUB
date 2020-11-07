package pl.maszyny_polskie.service;

import pl.maszyny_polskie.entity.Part;


public interface PartService {

    void delete(Part part);
    Part update(Part part);
    Part findById(int id);

}
