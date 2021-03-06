package com.yil.adress.service;

import com.yil.adress.dto.InteriorDoorDto;
import com.yil.adress.exception.InteriorDoorNotFoundException;
import com.yil.adress.model.InteriorDoor;
import com.yil.adress.repository.InteriorDoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InteriorDoorService {

    private final InteriorDoorRepository interiorDoorRepository;

    @Autowired
    public InteriorDoorService(InteriorDoorRepository interiorDoorRepository) {
        this.interiorDoorRepository = interiorDoorRepository;
    }

    public static InteriorDoorDto toDto(InteriorDoor entity) throws NullPointerException {
        if (entity == null)
            throw new NullPointerException("Interior door is null");
        return InteriorDoorDto.builder()
                .exteriorDoorId(entity.getExteriorDoorId())
                .id(entity.getId())
                .name(entity.getName()).build();
    }

    public InteriorDoor save(InteriorDoor interiorDoor) {
        return interiorDoorRepository.save(interiorDoor);
    }

    public InteriorDoor findById(Long id) throws InteriorDoorNotFoundException {
        return interiorDoorRepository.findById(id).orElseThrow(() -> {
            throw new InteriorDoorNotFoundException();
        });
    }

    public InteriorDoor findByIdAndDeletedTimeIsNull(Long id) throws InteriorDoorNotFoundException {
        InteriorDoor interiorDoor = interiorDoorRepository.findByIdAndDeletedTimeIsNull(id);
        if (interiorDoor == null)
            throw new InteriorDoorNotFoundException();
        return interiorDoor;
    }


    public Page<InteriorDoor> findByExteriorDoorIdAndDeletedTimeIsNull(Pageable pageable, Long exteriorDoorId) {
        return interiorDoorRepository.findByExteriorDoorIdAndDeletedTimeIsNull(pageable, exteriorDoorId);
    }

    public Page<InteriorDoor> findAllByDeletedTimeIsNull(Pageable pageable) {
        return interiorDoorRepository.findAllByDeletedTimeIsNull(pageable);
    }
}
