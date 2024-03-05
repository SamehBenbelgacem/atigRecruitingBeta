package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.CandidateAdditionalInfos;
import atig.recruiting.beta.repository.CandidateAdditionalInfosRepository;
import atig.recruiting.beta.service.CandidateAdditionalInfosService;
import atig.recruiting.beta.service.dto.CandidateAdditionalInfosDTO;
import atig.recruiting.beta.service.mapper.CandidateAdditionalInfosMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.CandidateAdditionalInfos}.
 */
@Service
@Transactional
public class CandidateAdditionalInfosServiceImpl implements CandidateAdditionalInfosService {

    private final Logger log = LoggerFactory.getLogger(CandidateAdditionalInfosServiceImpl.class);

    private final CandidateAdditionalInfosRepository candidateAdditionalInfosRepository;

    private final CandidateAdditionalInfosMapper candidateAdditionalInfosMapper;

    public CandidateAdditionalInfosServiceImpl(
        CandidateAdditionalInfosRepository candidateAdditionalInfosRepository,
        CandidateAdditionalInfosMapper candidateAdditionalInfosMapper
    ) {
        this.candidateAdditionalInfosRepository = candidateAdditionalInfosRepository;
        this.candidateAdditionalInfosMapper = candidateAdditionalInfosMapper;
    }

    @Override
    public CandidateAdditionalInfosDTO save(CandidateAdditionalInfosDTO candidateAdditionalInfosDTO) {
        log.debug("Request to save CandidateAdditionalInfos : {}", candidateAdditionalInfosDTO);
        CandidateAdditionalInfos candidateAdditionalInfos = candidateAdditionalInfosMapper.toEntity(candidateAdditionalInfosDTO);
        candidateAdditionalInfos = candidateAdditionalInfosRepository.save(candidateAdditionalInfos);
        return candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);
    }

    @Override
    public CandidateAdditionalInfosDTO update(CandidateAdditionalInfosDTO candidateAdditionalInfosDTO) {
        log.debug("Request to update CandidateAdditionalInfos : {}", candidateAdditionalInfosDTO);
        CandidateAdditionalInfos candidateAdditionalInfos = candidateAdditionalInfosMapper.toEntity(candidateAdditionalInfosDTO);
        candidateAdditionalInfos = candidateAdditionalInfosRepository.save(candidateAdditionalInfos);
        return candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);
    }

    @Override
    public Optional<CandidateAdditionalInfosDTO> partialUpdate(CandidateAdditionalInfosDTO candidateAdditionalInfosDTO) {
        log.debug("Request to partially update CandidateAdditionalInfos : {}", candidateAdditionalInfosDTO);

        return candidateAdditionalInfosRepository
            .findById(candidateAdditionalInfosDTO.getId())
            .map(existingCandidateAdditionalInfos -> {
                candidateAdditionalInfosMapper.partialUpdate(existingCandidateAdditionalInfos, candidateAdditionalInfosDTO);

                return existingCandidateAdditionalInfos;
            })
            .map(candidateAdditionalInfosRepository::save)
            .map(candidateAdditionalInfosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidateAdditionalInfosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CandidateAdditionalInfos");
        return candidateAdditionalInfosRepository.findAll(pageable).map(candidateAdditionalInfosMapper::toDto);
    }

    /**
     *  Get all the candidateAdditionalInfos where Candidate is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CandidateAdditionalInfosDTO> findAllWhereCandidateIsNull() {
        log.debug("Request to get all candidateAdditionalInfos where Candidate is null");
        return StreamSupport
            .stream(candidateAdditionalInfosRepository.findAll().spliterator(), false)
            .filter(candidateAdditionalInfos -> candidateAdditionalInfos.getCandidate() == null)
            .map(candidateAdditionalInfosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CandidateAdditionalInfosDTO> findOne(Long id) {
        log.debug("Request to get CandidateAdditionalInfos : {}", id);
        return candidateAdditionalInfosRepository.findById(id).map(candidateAdditionalInfosMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CandidateAdditionalInfos : {}", id);
        candidateAdditionalInfosRepository.deleteById(id);
    }
}
