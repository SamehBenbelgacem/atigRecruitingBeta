package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.ObjectContainingFile;
import atig.recruiting.beta.repository.ObjectContainingFileRepository;
import atig.recruiting.beta.service.ObjectContainingFileService;
import atig.recruiting.beta.service.dto.ObjectContainingFileDTO;
import atig.recruiting.beta.service.mapper.ObjectContainingFileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.ObjectContainingFile}.
 */
@Service
@Transactional
public class ObjectContainingFileServiceImpl implements ObjectContainingFileService {

    private final Logger log = LoggerFactory.getLogger(ObjectContainingFileServiceImpl.class);

    private final ObjectContainingFileRepository objectContainingFileRepository;

    private final ObjectContainingFileMapper objectContainingFileMapper;

    public ObjectContainingFileServiceImpl(
        ObjectContainingFileRepository objectContainingFileRepository,
        ObjectContainingFileMapper objectContainingFileMapper
    ) {
        this.objectContainingFileRepository = objectContainingFileRepository;
        this.objectContainingFileMapper = objectContainingFileMapper;
    }

    @Override
    public ObjectContainingFileDTO save(ObjectContainingFileDTO objectContainingFileDTO) {
        log.debug("Request to save ObjectContainingFile : {}", objectContainingFileDTO);
        ObjectContainingFile objectContainingFile = objectContainingFileMapper.toEntity(objectContainingFileDTO);
        objectContainingFile = objectContainingFileRepository.save(objectContainingFile);
        return objectContainingFileMapper.toDto(objectContainingFile);
    }

    @Override
    public ObjectContainingFileDTO update(ObjectContainingFileDTO objectContainingFileDTO) {
        log.debug("Request to update ObjectContainingFile : {}", objectContainingFileDTO);
        ObjectContainingFile objectContainingFile = objectContainingFileMapper.toEntity(objectContainingFileDTO);
        objectContainingFile = objectContainingFileRepository.save(objectContainingFile);
        return objectContainingFileMapper.toDto(objectContainingFile);
    }

    @Override
    public Optional<ObjectContainingFileDTO> partialUpdate(ObjectContainingFileDTO objectContainingFileDTO) {
        log.debug("Request to partially update ObjectContainingFile : {}", objectContainingFileDTO);

        return objectContainingFileRepository
            .findById(objectContainingFileDTO.getId())
            .map(existingObjectContainingFile -> {
                objectContainingFileMapper.partialUpdate(existingObjectContainingFile, objectContainingFileDTO);

                return existingObjectContainingFile;
            })
            .map(objectContainingFileRepository::save)
            .map(objectContainingFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObjectContainingFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ObjectContainingFiles");
        return objectContainingFileRepository.findAll(pageable).map(objectContainingFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObjectContainingFileDTO> findOne(Long id) {
        log.debug("Request to get ObjectContainingFile : {}", id);
        return objectContainingFileRepository.findById(id).map(objectContainingFileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ObjectContainingFile : {}", id);
        objectContainingFileRepository.deleteById(id);
    }
}
