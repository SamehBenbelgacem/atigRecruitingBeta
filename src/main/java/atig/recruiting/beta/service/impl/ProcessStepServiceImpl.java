package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.ProcessStep;
import atig.recruiting.beta.repository.ProcessStepRepository;
import atig.recruiting.beta.service.ProcessStepService;
import atig.recruiting.beta.service.dto.ProcessStepDTO;
import atig.recruiting.beta.service.mapper.ProcessStepMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.ProcessStep}.
 */
@Service
@Transactional
public class ProcessStepServiceImpl implements ProcessStepService {

    private final Logger log = LoggerFactory.getLogger(ProcessStepServiceImpl.class);

    private final ProcessStepRepository processStepRepository;

    private final ProcessStepMapper processStepMapper;

    public ProcessStepServiceImpl(ProcessStepRepository processStepRepository, ProcessStepMapper processStepMapper) {
        this.processStepRepository = processStepRepository;
        this.processStepMapper = processStepMapper;
    }

    @Override
    public ProcessStepDTO save(ProcessStepDTO processStepDTO) {
        log.debug("Request to save ProcessStep : {}", processStepDTO);
        ProcessStep processStep = processStepMapper.toEntity(processStepDTO);
        processStep = processStepRepository.save(processStep);
        return processStepMapper.toDto(processStep);
    }

    @Override
    public ProcessStepDTO update(ProcessStepDTO processStepDTO) {
        log.debug("Request to update ProcessStep : {}", processStepDTO);
        ProcessStep processStep = processStepMapper.toEntity(processStepDTO);
        processStep = processStepRepository.save(processStep);
        return processStepMapper.toDto(processStep);
    }

    @Override
    public Optional<ProcessStepDTO> partialUpdate(ProcessStepDTO processStepDTO) {
        log.debug("Request to partially update ProcessStep : {}", processStepDTO);

        return processStepRepository
            .findById(processStepDTO.getId())
            .map(existingProcessStep -> {
                processStepMapper.partialUpdate(existingProcessStep, processStepDTO);

                return existingProcessStep;
            })
            .map(processStepRepository::save)
            .map(processStepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessSteps");
        return processStepRepository.findAll(pageable).map(processStepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessStepDTO> findOne(Long id) {
        log.debug("Request to get ProcessStep : {}", id);
        return processStepRepository.findById(id).map(processStepMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessStep : {}", id);
        processStepRepository.deleteById(id);
    }
}
