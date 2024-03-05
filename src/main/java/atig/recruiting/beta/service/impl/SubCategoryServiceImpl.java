package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.SubCategory;
import atig.recruiting.beta.repository.SubCategoryRepository;
import atig.recruiting.beta.service.SubCategoryService;
import atig.recruiting.beta.service.dto.SubCategoryDTO;
import atig.recruiting.beta.service.mapper.SubCategoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.SubCategory}.
 */
@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {

    private final Logger log = LoggerFactory.getLogger(SubCategoryServiceImpl.class);

    private final SubCategoryRepository subCategoryRepository;

    private final SubCategoryMapper subCategoryMapper;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategoryMapper subCategoryMapper) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryMapper = subCategoryMapper;
    }

    @Override
    public SubCategoryDTO save(SubCategoryDTO subCategoryDTO) {
        log.debug("Request to save SubCategory : {}", subCategoryDTO);
        SubCategory subCategory = subCategoryMapper.toEntity(subCategoryDTO);
        subCategory = subCategoryRepository.save(subCategory);
        return subCategoryMapper.toDto(subCategory);
    }

    @Override
    public SubCategoryDTO update(SubCategoryDTO subCategoryDTO) {
        log.debug("Request to update SubCategory : {}", subCategoryDTO);
        SubCategory subCategory = subCategoryMapper.toEntity(subCategoryDTO);
        subCategory = subCategoryRepository.save(subCategory);
        return subCategoryMapper.toDto(subCategory);
    }

    @Override
    public Optional<SubCategoryDTO> partialUpdate(SubCategoryDTO subCategoryDTO) {
        log.debug("Request to partially update SubCategory : {}", subCategoryDTO);

        return subCategoryRepository
            .findById(subCategoryDTO.getId())
            .map(existingSubCategory -> {
                subCategoryMapper.partialUpdate(existingSubCategory, subCategoryDTO);

                return existingSubCategory;
            })
            .map(subCategoryRepository::save)
            .map(subCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubCategories");
        return subCategoryRepository.findAll(pageable).map(subCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubCategoryDTO> findOne(Long id) {
        log.debug("Request to get SubCategory : {}", id);
        return subCategoryRepository.findById(id).map(subCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubCategory : {}", id);
        subCategoryRepository.deleteById(id);
    }
}
