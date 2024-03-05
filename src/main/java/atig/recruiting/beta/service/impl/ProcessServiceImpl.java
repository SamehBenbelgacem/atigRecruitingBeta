package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.Process;
import atig.recruiting.beta.repository.ProcessRepository;
import atig.recruiting.beta.service.ProcessService;
import atig.recruiting.beta.service.dto.ProcessDTO;
import atig.recruiting.beta.service.mapper.ProcessMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.Process}.
 */
@Service
@Transactional
public class ProcessServiceImpl implements ProcessService {

    private final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);

    private final ProcessRepository processRepository;

    private final ProcessMapper processMapper;

    public ProcessServiceImpl(ProcessRepository processRepository, ProcessMapper processMapper) {
        this.processRepository = processRepository;
        this.processMapper = processMapper;
    }

    @Override
    public ProcessDTO save(ProcessDTO processDTO) {
        log.debug("Request to save Process : {}", processDTO);
        Process process = processMapper.toEntity(processDTO);
        process = processRepository.save(process);
        return processMapper.toDto(process);
    }

    @Override
    public ProcessDTO update(ProcessDTO processDTO) {
        log.debug("Request to update Process : {}", processDTO);
        Process process = processMapper.toEntity(processDTO);
        process = processRepository.save(process);
        return processMapper.toDto(process);
    }

    @Override
    public Optional<ProcessDTO> partialUpdate(ProcessDTO processDTO) {
        log.debug("Request to partially update Process : {}", processDTO);

        return processRepository
            .findById(processDTO.getId())
            .map(existingProcess -> {
                processMapper.partialUpdate(existingProcess, processDTO);

                return existingProcess;
            })
            .map(processRepository::save)
            .map(processMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Processes");
        return processRepository.findAll(pageable).map(processMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessDTO> findOne(Long id) {
        log.debug("Request to get Process : {}", id);
        return processRepository.findById(id).map(processMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Process : {}", id);
        processRepository.deleteById(id);
    }
}
