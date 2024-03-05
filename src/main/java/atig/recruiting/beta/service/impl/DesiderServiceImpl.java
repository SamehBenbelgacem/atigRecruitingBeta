package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.Desider;
import atig.recruiting.beta.repository.DesiderRepository;
import atig.recruiting.beta.service.DesiderService;
import atig.recruiting.beta.service.dto.DesiderDTO;
import atig.recruiting.beta.service.mapper.DesiderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.Desider}.
 */
@Service
@Transactional
public class DesiderServiceImpl implements DesiderService {

    private final Logger log = LoggerFactory.getLogger(DesiderServiceImpl.class);

    private final DesiderRepository desiderRepository;

    private final DesiderMapper desiderMapper;

    public DesiderServiceImpl(DesiderRepository desiderRepository, DesiderMapper desiderMapper) {
        this.desiderRepository = desiderRepository;
        this.desiderMapper = desiderMapper;
    }

    @Override
    public DesiderDTO save(DesiderDTO desiderDTO) {
        log.debug("Request to save Desider : {}", desiderDTO);
        Desider desider = desiderMapper.toEntity(desiderDTO);
        desider = desiderRepository.save(desider);
        return desiderMapper.toDto(desider);
    }

    @Override
    public DesiderDTO update(DesiderDTO desiderDTO) {
        log.debug("Request to update Desider : {}", desiderDTO);
        Desider desider = desiderMapper.toEntity(desiderDTO);
        desider = desiderRepository.save(desider);
        return desiderMapper.toDto(desider);
    }

    @Override
    public Optional<DesiderDTO> partialUpdate(DesiderDTO desiderDTO) {
        log.debug("Request to partially update Desider : {}", desiderDTO);

        return desiderRepository
            .findById(desiderDTO.getId())
            .map(existingDesider -> {
                desiderMapper.partialUpdate(existingDesider, desiderDTO);

                return existingDesider;
            })
            .map(desiderRepository::save)
            .map(desiderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DesiderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Desiders");
        return desiderRepository.findAll(pageable).map(desiderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DesiderDTO> findOne(Long id) {
        log.debug("Request to get Desider : {}", id);
        return desiderRepository.findById(id).map(desiderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Desider : {}", id);
        desiderRepository.deleteById(id);
    }
}
