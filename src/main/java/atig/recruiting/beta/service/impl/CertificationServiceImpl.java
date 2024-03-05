package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.Certification;
import atig.recruiting.beta.repository.CertificationRepository;
import atig.recruiting.beta.service.CertificationService;
import atig.recruiting.beta.service.dto.CertificationDTO;
import atig.recruiting.beta.service.mapper.CertificationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.Certification}.
 */
@Service
@Transactional
public class CertificationServiceImpl implements CertificationService {

    private final Logger log = LoggerFactory.getLogger(CertificationServiceImpl.class);

    private final CertificationRepository certificationRepository;

    private final CertificationMapper certificationMapper;

    public CertificationServiceImpl(CertificationRepository certificationRepository, CertificationMapper certificationMapper) {
        this.certificationRepository = certificationRepository;
        this.certificationMapper = certificationMapper;
    }

    @Override
    public CertificationDTO save(CertificationDTO certificationDTO) {
        log.debug("Request to save Certification : {}", certificationDTO);
        Certification certification = certificationMapper.toEntity(certificationDTO);
        certification = certificationRepository.save(certification);
        return certificationMapper.toDto(certification);
    }

    @Override
    public CertificationDTO update(CertificationDTO certificationDTO) {
        log.debug("Request to update Certification : {}", certificationDTO);
        Certification certification = certificationMapper.toEntity(certificationDTO);
        certification = certificationRepository.save(certification);
        return certificationMapper.toDto(certification);
    }

    @Override
    public Optional<CertificationDTO> partialUpdate(CertificationDTO certificationDTO) {
        log.debug("Request to partially update Certification : {}", certificationDTO);

        return certificationRepository
            .findById(certificationDTO.getId())
            .map(existingCertification -> {
                certificationMapper.partialUpdate(existingCertification, certificationDTO);

                return existingCertification;
            })
            .map(certificationRepository::save)
            .map(certificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CertificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Certifications");
        return certificationRepository.findAll(pageable).map(certificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CertificationDTO> findOne(Long id) {
        log.debug("Request to get Certification : {}", id);
        return certificationRepository.findById(id).map(certificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Certification : {}", id);
        certificationRepository.deleteById(id);
    }
}
