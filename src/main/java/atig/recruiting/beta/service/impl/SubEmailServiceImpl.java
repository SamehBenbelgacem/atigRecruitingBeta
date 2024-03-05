package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.SubEmail;
import atig.recruiting.beta.repository.SubEmailRepository;
import atig.recruiting.beta.service.SubEmailService;
import atig.recruiting.beta.service.dto.SubEmailDTO;
import atig.recruiting.beta.service.mapper.SubEmailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.SubEmail}.
 */
@Service
@Transactional
public class SubEmailServiceImpl implements SubEmailService {

    private final Logger log = LoggerFactory.getLogger(SubEmailServiceImpl.class);

    private final SubEmailRepository subEmailRepository;

    private final SubEmailMapper subEmailMapper;

    public SubEmailServiceImpl(SubEmailRepository subEmailRepository, SubEmailMapper subEmailMapper) {
        this.subEmailRepository = subEmailRepository;
        this.subEmailMapper = subEmailMapper;
    }

    @Override
    public SubEmailDTO save(SubEmailDTO subEmailDTO) {
        log.debug("Request to save SubEmail : {}", subEmailDTO);
        SubEmail subEmail = subEmailMapper.toEntity(subEmailDTO);
        subEmail = subEmailRepository.save(subEmail);
        return subEmailMapper.toDto(subEmail);
    }

    @Override
    public SubEmailDTO update(SubEmailDTO subEmailDTO) {
        log.debug("Request to update SubEmail : {}", subEmailDTO);
        SubEmail subEmail = subEmailMapper.toEntity(subEmailDTO);
        subEmail = subEmailRepository.save(subEmail);
        return subEmailMapper.toDto(subEmail);
    }

    @Override
    public Optional<SubEmailDTO> partialUpdate(SubEmailDTO subEmailDTO) {
        log.debug("Request to partially update SubEmail : {}", subEmailDTO);

        return subEmailRepository
            .findById(subEmailDTO.getId())
            .map(existingSubEmail -> {
                subEmailMapper.partialUpdate(existingSubEmail, subEmailDTO);

                return existingSubEmail;
            })
            .map(subEmailRepository::save)
            .map(subEmailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubEmailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubEmails");
        return subEmailRepository.findAll(pageable).map(subEmailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubEmailDTO> findOne(Long id) {
        log.debug("Request to get SubEmail : {}", id);
        return subEmailRepository.findById(id).map(subEmailMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubEmail : {}", id);
        subEmailRepository.deleteById(id);
    }
}
