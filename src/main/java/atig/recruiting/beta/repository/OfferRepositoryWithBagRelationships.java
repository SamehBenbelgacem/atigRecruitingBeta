package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Offer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface OfferRepositoryWithBagRelationships {
    Optional<Offer> fetchBagRelationships(Optional<Offer> offer);

    List<Offer> fetchBagRelationships(List<Offer> offers);

    Page<Offer> fetchBagRelationships(Page<Offer> offers);
}
