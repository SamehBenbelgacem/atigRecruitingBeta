package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.Emailcredentials;
import atig.recruiting.beta.repository.EmailcredentialsRepository;
import atig.recruiting.beta.service.EmailcredentialsService;
import atig.recruiting.beta.service.dto.EmailcredentialsDTO;
import atig.recruiting.beta.service.mapper.EmailcredentialsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.Emailcredentials}.
 */
@Service
@Transactional
public class EmailcredentialsServiceImpl implements EmailcredentialsService {

    private final Logger log = LoggerFactory.getLogger(EmailcredentialsServiceImpl.class);

    private final EmailcredentialsRepository emailcredentialsRepository;

    private final EmailcredentialsMapper emailcredentialsMapper;

    public EmailcredentialsServiceImpl(
        EmailcredentialsRepository emailcredentialsRepository,
        EmailcredentialsMapper emailcredentialsMapper
    ) {
        this.emailcredentialsRepository = emailcredentialsRepository;
        this.emailcredentialsMapper = emailcredentialsMapper;
    }

    @Override
    public EmailcredentialsDTO save(EmailcredentialsDTO emailcredentialsDTO) {
        log.debug("Request to save Emailcredentials : {}", emailcredentialsDTO);
        Emailcredentials emailcredentials = emailcredentialsMapper.toEntity(emailcredentialsDTO);
        emailcredentials = emailcredentialsRepository.save(emailcredentials);
        return emailcredentialsMapper.toDto(emailcredentials);
    }

    @Override
    public EmailcredentialsDTO update(EmailcredentialsDTO emailcredentialsDTO) {
        log.debug("Request to update Emailcredentials : {}", emailcredentialsDTO);
        Emailcredentials emailcredentials = emailcredentialsMapper.toEntity(emailcredentialsDTO);
        emailcredentials = emailcredentialsRepository.save(emailcredentials);
        return emailcredentialsMapper.toDto(emailcredentials);
    }

    @Override
    public Optional<EmailcredentialsDTO> partialUpdate(EmailcredentialsDTO emailcredentialsDTO) {
        log.debug("Request to partially update Emailcredentials : {}", emailcredentialsDTO);

        return emailcredentialsRepository
            .findById(emailcredentialsDTO.getId())
            .map(existingEmailcredentials -> {
                emailcredentialsMapper.partialUpdate(existingEmailcredentials, emailcredentialsDTO);

                return existingEmailcredentials;
            })
            .map(emailcredentialsRepository::save)
            .map(emailcredentialsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailcredentialsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Emailcredentials");
        return emailcredentialsRepository.findAll(pageable).map(emailcredentialsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmailcredentialsDTO> findOne(Long id) {
        log.debug("Request to get Emailcredentials : {}", id);
        return emailcredentialsRepository.findById(id).map(emailcredentialsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Emailcredentials : {}", id);
        emailcredentialsRepository.deleteById(id);
    }
}
