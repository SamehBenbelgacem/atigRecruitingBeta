package atig.recruiting.beta.service.impl;

import atig.recruiting.beta.domain.Offer;
import atig.recruiting.beta.repository.OfferRepository;
import atig.recruiting.beta.service.OfferService;
import atig.recruiting.beta.service.dto.OfferDTO;
import atig.recruiting.beta.service.mapper.OfferMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link atig.recruiting.beta.domain.Offer}.
 */
@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    private final OfferRepository offerRepository;

    private final OfferMapper offerMapper;

    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    @Override
    public OfferDTO save(OfferDTO offerDTO) {
        log.debug("Request to save Offer : {}", offerDTO);
        Offer offer = offerMapper.toEntity(offerDTO);
        offer = offerRepository.save(offer);
        return offerMapper.toDto(offer);
    }

    @Override
    public OfferDTO update(OfferDTO offerDTO) {
        log.debug("Request to update Offer : {}", offerDTO);
        Offer offer = offerMapper.toEntity(offerDTO);
        offer = offerRepository.save(offer);
        return offerMapper.toDto(offer);
    }

    @Override
    public Optional<OfferDTO> partialUpdate(OfferDTO offerDTO) {
        log.debug("Request to partially update Offer : {}", offerDTO);

        return offerRepository
            .findById(offerDTO.getId())
            .map(existingOffer -> {
                offerMapper.partialUpdate(existingOffer, offerDTO);

                return existingOffer;
            })
            .map(offerRepository::save)
            .map(offerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offers");
        return offerRepository.findAll(pageable).map(offerMapper::toDto);
    }

    public Page<OfferDTO> findAllWithEagerRelationships(Pageable pageable) {
        return offerRepository.findAllWithEagerRelationships(pageable).map(offerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfferDTO> findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        return offerRepository.findOneWithEagerRelationships(id).map(offerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);
        offerRepository.deleteById(id);
    }
}
