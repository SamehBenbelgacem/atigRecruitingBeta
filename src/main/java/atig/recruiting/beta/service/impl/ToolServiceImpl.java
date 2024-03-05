package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.Tool;
import atig.recruiting.beta.repository.ToolRepository;
import atig.recruiting.beta.service.ToolService;
import atig.recruiting.beta.service.dto.ToolDTO;
import atig.recruiting.beta.service.mapper.ToolMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.Tool}.
 */
@Service
@Transactional
public class ToolServiceImpl implements ToolService {

    private final Logger log = LoggerFactory.getLogger(ToolServiceImpl.class);

    private final ToolRepository toolRepository;

    private final ToolMapper toolMapper;

    public ToolServiceImpl(ToolRepository toolRepository, ToolMapper toolMapper) {
        this.toolRepository = toolRepository;
        this.toolMapper = toolMapper;
    }

    @Override
    public ToolDTO save(ToolDTO toolDTO) {
        log.debug("Request to save Tool : {}", toolDTO);
        Tool tool = toolMapper.toEntity(toolDTO);
        tool = toolRepository.save(tool);
        return toolMapper.toDto(tool);
    }

    @Override
    public ToolDTO update(ToolDTO toolDTO) {
        log.debug("Request to update Tool : {}", toolDTO);
        Tool tool = toolMapper.toEntity(toolDTO);
        tool = toolRepository.save(tool);
        return toolMapper.toDto(tool);
    }

    @Override
    public Optional<ToolDTO> partialUpdate(ToolDTO toolDTO) {
        log.debug("Request to partially update Tool : {}", toolDTO);

        return toolRepository
            .findById(toolDTO.getId())
            .map(existingTool -> {
                toolMapper.partialUpdate(existingTool, toolDTO);

                return existingTool;
            })
            .map(toolRepository::save)
            .map(toolMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ToolDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tools");
        return toolRepository.findAll(pageable).map(toolMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ToolDTO> findOne(Long id) {
        log.debug("Request to get Tool : {}", id);
        return toolRepository.findById(id).map(toolMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tool : {}", id);
        toolRepository.deleteById(id);
    }
}
