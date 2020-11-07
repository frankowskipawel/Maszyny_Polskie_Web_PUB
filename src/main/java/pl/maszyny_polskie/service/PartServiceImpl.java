package pl.maszyny_polskie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Part;
import pl.maszyny_polskie.repository.PartRepository;

@Service
public class PartServiceImpl implements PartService{

    @Autowired
    private PartRepository partRepository;

    public void delete(Part part){
        partRepository.delete(part);
    }

    @Override
    public Part update(Part part) {
        return partRepository.save(part);
    }

    @Override
    public Part findById(int id) {
        return partRepository.findById(id).get();
    }
}
