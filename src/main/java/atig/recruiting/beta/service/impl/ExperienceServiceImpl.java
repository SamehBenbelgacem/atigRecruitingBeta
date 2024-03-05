package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.Experience;
import atig.recruiting.beta.repository.ExperienceRepository;
import atig.recruiting.beta.service.ExperienceService;
import atig.recruiting.beta.service.dto.ExperienceDTO;
import atig.recruiting.beta.service.mapper.ExperienceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.Experience}.
 */
@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService {

    private final Logger log = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    private final ExperienceRepository experienceRepository;

    private final ExperienceMapper experienceMapper;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    @Override
    public ExperienceDTO save(ExperienceDTO experienceDTO) {
        log.debug("Request to save Experience : {}", experienceDTO);
        Experience experience = experienceMapper.toEntity(experienceDTO);
        experience = experienceRepository.save(experience);
        return experienceMapper.toDto(experience);
    }

    @Override
    public ExperienceDTO update(ExperienceDTO experienceDTO) {
        log.debug("Request to update Experience : {}", experienceDTO);
        Experience experience = experienceMapper.toEntity(experienceDTO);
        experience = experienceRepository.save(experience);
        return experienceMapper.toDto(experience);
    }

    @Override
    public Optional<ExperienceDTO> partialUpdate(ExperienceDTO experienceDTO) {
        log.debug("Request to partially update Experience : {}", experienceDTO);

        return experienceRepository
            .findById(experienceDTO.getId())
            .map(existingExperience -> {
                experienceMapper.partialUpdate(existingExperience, experienceDTO);

                return existingExperience;
            })
            .map(experienceRepository::save)
            .map(experienceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExperienceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Experiences");
        return experienceRepository.findAll(pageable).map(experienceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExperienceDTO> findOne(Long id) {
        log.debug("Request to get Experience : {}", id);
        return experienceRepository.findById(id).map(experienceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Experience : {}", id);
        experienceRepository.deleteById(id);
    }
}
