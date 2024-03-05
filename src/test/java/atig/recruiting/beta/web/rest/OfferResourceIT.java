package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Offer;
import atig.recruiting.beta.domain.Tag;
import atig.recruiting.beta.repository.OfferRepository;
import atig.recruiting.beta.service.OfferService;
import atig.recruiting.beta.service.dto.OfferDTO;
import atig.recruiting.beta.service.mapper.OfferMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OfferResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OfferResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/offers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OfferRepository offerRepository;

    @Mock
    private OfferRepository offerRepositoryMock;

    @Autowired
    private OfferMapper offerMapper;

    @Mock
    private OfferService offerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferMockMvc;

    private Offer offer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION).date(DEFAULT_DATE);
        return offer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createUpdatedEntity(EntityManager em) {
        Offer offer = new Offer().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).date(UPDATED_DATE);
        return offer;
    }

    @BeforeEach
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();
        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);
        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOffer.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createOfferWithExistingId() throws Exception {
        // Create the Offer with an existing ID
        offer.setId(1L);
        OfferDTO offerDTO = offerMapper.toDto(offer);

        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOffersWithEagerRelationshipsIsEnabled() throws Exception {
        when(offerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOfferMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(offerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOffersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(offerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOfferMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(offerRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc
            .perform(get(ENTITY_API_URL_ID, offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getOffersByIdFiltering() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        Long id = offer.getId();

        defaultOfferShouldBeFound("id.equals=" + id);
        defaultOfferShouldNotBeFound("id.notEquals=" + id);

        defaultOfferShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOfferShouldNotBeFound("id.greaterThan=" + id);

        defaultOfferShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOfferShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOffersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where title equals to DEFAULT_TITLE
        defaultOfferShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the offerList where title equals to UPDATED_TITLE
        defaultOfferShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllOffersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultOfferShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the offerList where title equals to UPDATED_TITLE
        defaultOfferShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllOffersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where title is not null
        defaultOfferShouldBeFound("title.specified=true");

        // Get all the offerList where title is null
        defaultOfferShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllOffersByTitleContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where title contains DEFAULT_TITLE
        defaultOfferShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the offerList where title contains UPDATED_TITLE
        defaultOfferShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllOffersByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where title does not contain DEFAULT_TITLE
        defaultOfferShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the offerList where title does not contain UPDATED_TITLE
        defaultOfferShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllOffersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where description equals to DEFAULT_DESCRIPTION
        defaultOfferShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the offerList where description equals to UPDATED_DESCRIPTION
        defaultOfferShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOffersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOfferShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the offerList where description equals to UPDATED_DESCRIPTION
        defaultOfferShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOffersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where description is not null
        defaultOfferShouldBeFound("description.specified=true");

        // Get all the offerList where description is null
        defaultOfferShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOffersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where description contains DEFAULT_DESCRIPTION
        defaultOfferShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the offerList where description contains UPDATED_DESCRIPTION
        defaultOfferShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOffersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where description does not contain DEFAULT_DESCRIPTION
        defaultOfferShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the offerList where description does not contain UPDATED_DESCRIPTION
        defaultOfferShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOffersByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where date equals to DEFAULT_DATE
        defaultOfferShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the offerList where date equals to UPDATED_DATE
        defaultOfferShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOffersByDateIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where date in DEFAULT_DATE or UPDATED_DATE
        defaultOfferShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the offerList where date equals to UPDATED_DATE
        defaultOfferShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOffersByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where date is not null
        defaultOfferShouldBeFound("date.specified=true");

        // Get all the offerList where date is null
        defaultOfferShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllOffersByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where date is greater than or equal to DEFAULT_DATE
        defaultOfferShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the offerList where date is greater than or equal to UPDATED_DATE
        defaultOfferShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOffersByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where date is less than or equal to DEFAULT_DATE
        defaultOfferShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the offerList where date is less than or equal to SMALLER_DATE
        defaultOfferShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllOffersByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where date is less than DEFAULT_DATE
        defaultOfferShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the offerList where date is less than UPDATED_DATE
        defaultOfferShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllOffersByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where date is greater than DEFAULT_DATE
        defaultOfferShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the offerList where date is greater than SMALLER_DATE
        defaultOfferShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllOffersByOfferCompanyIsEqualToSomething() throws Exception {
        Company offerCompany;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            offerRepository.saveAndFlush(offer);
            offerCompany = CompanyResourceIT.createEntity(em);
        } else {
            offerCompany = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(offerCompany);
        em.flush();
        offer.setOfferCompany(offerCompany);
        offerRepository.saveAndFlush(offer);
        Long offerCompanyId = offerCompany.getId();
        // Get all the offerList where offerCompany equals to offerCompanyId
        defaultOfferShouldBeFound("offerCompanyId.equals=" + offerCompanyId);

        // Get all the offerList where offerCompany equals to (offerCompanyId + 1)
        defaultOfferShouldNotBeFound("offerCompanyId.equals=" + (offerCompanyId + 1));
    }

    @Test
    @Transactional
    void getAllOffersByTagsIsEqualToSomething() throws Exception {
        Tag tags;
        if (TestUtil.findAll(em, Tag.class).isEmpty()) {
            offerRepository.saveAndFlush(offer);
            tags = TagResourceIT.createEntity(em);
        } else {
            tags = TestUtil.findAll(em, Tag.class).get(0);
        }
        em.persist(tags);
        em.flush();
        offer.addTags(tags);
        offerRepository.saveAndFlush(offer);
        Long tagsId = tags.getId();
        // Get all the offerList where tags equals to tagsId
        defaultOfferShouldBeFound("tagsId.equals=" + tagsId);

        // Get all the offerList where tags equals to (tagsId + 1)
        defaultOfferShouldNotBeFound("tagsId.equals=" + (tagsId + 1));
    }

    @Test
    @Transactional
    void getAllOffersByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            offerRepository.saveAndFlush(offer);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        offer.setCompany(company);
        offerRepository.saveAndFlush(offer);
        Long companyId = company.getId();
        // Get all the offerList where company equals to companyId
        defaultOfferShouldBeFound("companyId.equals=" + companyId);

        // Get all the offerList where company equals to (companyId + 1)
        defaultOfferShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOfferShouldBeFound(String filter) throws Exception {
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOfferShouldNotBeFound(String filter) throws Exception {
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOffer are not directly saved in db
        em.detach(updatedOffer);
        updatedOffer.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).date(UPDATED_DATE);
        OfferDTO offerDTO = offerMapper.toDto(updatedOffer);

        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOffer.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer.title(UPDATED_TITLE);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOffer.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).date(UPDATED_DATE);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOffer.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Delete the offer
        restOfferMockMvc
            .perform(delete(ENTITY_API_URL_ID, offer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
