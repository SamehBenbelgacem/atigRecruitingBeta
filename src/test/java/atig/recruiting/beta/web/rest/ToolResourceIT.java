package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Experience;
import atig.recruiting.beta.domain.Tool;
import atig.recruiting.beta.repository.ToolRepository;
import atig.recruiting.beta.service.dto.ToolDTO;
import atig.recruiting.beta.service.mapper.ToolMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ToolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ToolResourceIT {

    private static final String DEFAULT_TOOL = "AAAAAAAAAA";
    private static final String UPDATED_TOOL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tools";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private ToolMapper toolMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restToolMockMvc;

    private Tool tool;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tool createEntity(EntityManager em) {
        Tool tool = new Tool().tool(DEFAULT_TOOL);
        return tool;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tool createUpdatedEntity(EntityManager em) {
        Tool tool = new Tool().tool(UPDATED_TOOL);
        return tool;
    }

    @BeforeEach
    public void initTest() {
        tool = createEntity(em);
    }

    @Test
    @Transactional
    void createTool() throws Exception {
        int databaseSizeBeforeCreate = toolRepository.findAll().size();
        // Create the Tool
        ToolDTO toolDTO = toolMapper.toDto(tool);
        restToolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(toolDTO)))
            .andExpect(status().isCreated());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeCreate + 1);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.getTool()).isEqualTo(DEFAULT_TOOL);
    }

    @Test
    @Transactional
    void createToolWithExistingId() throws Exception {
        // Create the Tool with an existing ID
        tool.setId(1L);
        ToolDTO toolDTO = toolMapper.toDto(tool);

        int databaseSizeBeforeCreate = toolRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restToolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(toolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTools() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get all the toolList
        restToolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tool.getId().intValue())))
            .andExpect(jsonPath("$.[*].tool").value(hasItem(DEFAULT_TOOL)));
    }

    @Test
    @Transactional
    void getTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get the tool
        restToolMockMvc
            .perform(get(ENTITY_API_URL_ID, tool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tool.getId().intValue()))
            .andExpect(jsonPath("$.tool").value(DEFAULT_TOOL));
    }

    @Test
    @Transactional
    void getToolsByIdFiltering() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        Long id = tool.getId();

        defaultToolShouldBeFound("id.equals=" + id);
        defaultToolShouldNotBeFound("id.notEquals=" + id);

        defaultToolShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultToolShouldNotBeFound("id.greaterThan=" + id);

        defaultToolShouldBeFound("id.lessThanOrEqual=" + id);
        defaultToolShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllToolsByToolIsEqualToSomething() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get all the toolList where tool equals to DEFAULT_TOOL
        defaultToolShouldBeFound("tool.equals=" + DEFAULT_TOOL);

        // Get all the toolList where tool equals to UPDATED_TOOL
        defaultToolShouldNotBeFound("tool.equals=" + UPDATED_TOOL);
    }

    @Test
    @Transactional
    void getAllToolsByToolIsInShouldWork() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get all the toolList where tool in DEFAULT_TOOL or UPDATED_TOOL
        defaultToolShouldBeFound("tool.in=" + DEFAULT_TOOL + "," + UPDATED_TOOL);

        // Get all the toolList where tool equals to UPDATED_TOOL
        defaultToolShouldNotBeFound("tool.in=" + UPDATED_TOOL);
    }

    @Test
    @Transactional
    void getAllToolsByToolIsNullOrNotNull() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get all the toolList where tool is not null
        defaultToolShouldBeFound("tool.specified=true");

        // Get all the toolList where tool is null
        defaultToolShouldNotBeFound("tool.specified=false");
    }

    @Test
    @Transactional
    void getAllToolsByToolContainsSomething() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get all the toolList where tool contains DEFAULT_TOOL
        defaultToolShouldBeFound("tool.contains=" + DEFAULT_TOOL);

        // Get all the toolList where tool contains UPDATED_TOOL
        defaultToolShouldNotBeFound("tool.contains=" + UPDATED_TOOL);
    }

    @Test
    @Transactional
    void getAllToolsByToolNotContainsSomething() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get all the toolList where tool does not contain DEFAULT_TOOL
        defaultToolShouldNotBeFound("tool.doesNotContain=" + DEFAULT_TOOL);

        // Get all the toolList where tool does not contain UPDATED_TOOL
        defaultToolShouldBeFound("tool.doesNotContain=" + UPDATED_TOOL);
    }

    @Test
    @Transactional
    void getAllToolsByToolExperienceIsEqualToSomething() throws Exception {
        Experience toolExperience;
        if (TestUtil.findAll(em, Experience.class).isEmpty()) {
            toolRepository.saveAndFlush(tool);
            toolExperience = ExperienceResourceIT.createEntity(em);
        } else {
            toolExperience = TestUtil.findAll(em, Experience.class).get(0);
        }
        em.persist(toolExperience);
        em.flush();
        tool.setToolExperience(toolExperience);
        toolRepository.saveAndFlush(tool);
        Long toolExperienceId = toolExperience.getId();
        // Get all the toolList where toolExperience equals to toolExperienceId
        defaultToolShouldBeFound("toolExperienceId.equals=" + toolExperienceId);

        // Get all the toolList where toolExperience equals to (toolExperienceId + 1)
        defaultToolShouldNotBeFound("toolExperienceId.equals=" + (toolExperienceId + 1));
    }

    @Test
    @Transactional
    void getAllToolsByExperienceIsEqualToSomething() throws Exception {
        Experience experience;
        if (TestUtil.findAll(em, Experience.class).isEmpty()) {
            toolRepository.saveAndFlush(tool);
            experience = ExperienceResourceIT.createEntity(em);
        } else {
            experience = TestUtil.findAll(em, Experience.class).get(0);
        }
        em.persist(experience);
        em.flush();
        tool.setExperience(experience);
        toolRepository.saveAndFlush(tool);
        Long experienceId = experience.getId();
        // Get all the toolList where experience equals to experienceId
        defaultToolShouldBeFound("experienceId.equals=" + experienceId);

        // Get all the toolList where experience equals to (experienceId + 1)
        defaultToolShouldNotBeFound("experienceId.equals=" + (experienceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultToolShouldBeFound(String filter) throws Exception {
        restToolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tool.getId().intValue())))
            .andExpect(jsonPath("$.[*].tool").value(hasItem(DEFAULT_TOOL)));

        // Check, that the count call also returns 1
        restToolMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultToolShouldNotBeFound(String filter) throws Exception {
        restToolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restToolMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTool() throws Exception {
        // Get the tool
        restToolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        int databaseSizeBeforeUpdate = toolRepository.findAll().size();

        // Update the tool
        Tool updatedTool = toolRepository.findById(tool.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTool are not directly saved in db
        em.detach(updatedTool);
        updatedTool.tool(UPDATED_TOOL);
        ToolDTO toolDTO = toolMapper.toDto(updatedTool);

        restToolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, toolDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(toolDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.getTool()).isEqualTo(UPDATED_TOOL);
    }

    @Test
    @Transactional
    void putNonExistingTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(longCount.incrementAndGet());

        // Create the Tool
        ToolDTO toolDTO = toolMapper.toDto(tool);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, toolDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(toolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(longCount.incrementAndGet());

        // Create the Tool
        ToolDTO toolDTO = toolMapper.toDto(tool);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(toolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(longCount.incrementAndGet());

        // Create the Tool
        ToolDTO toolDTO = toolMapper.toDto(tool);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(toolDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateToolWithPatch() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        int databaseSizeBeforeUpdate = toolRepository.findAll().size();

        // Update the tool using partial update
        Tool partialUpdatedTool = new Tool();
        partialUpdatedTool.setId(tool.getId());

        partialUpdatedTool.tool(UPDATED_TOOL);

        restToolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTool))
            )
            .andExpect(status().isOk());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.getTool()).isEqualTo(UPDATED_TOOL);
    }

    @Test
    @Transactional
    void fullUpdateToolWithPatch() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        int databaseSizeBeforeUpdate = toolRepository.findAll().size();

        // Update the tool using partial update
        Tool partialUpdatedTool = new Tool();
        partialUpdatedTool.setId(tool.getId());

        partialUpdatedTool.tool(UPDATED_TOOL);

        restToolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTool))
            )
            .andExpect(status().isOk());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.getTool()).isEqualTo(UPDATED_TOOL);
    }

    @Test
    @Transactional
    void patchNonExistingTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(longCount.incrementAndGet());

        // Create the Tool
        ToolDTO toolDTO = toolMapper.toDto(tool);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, toolDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(toolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(longCount.incrementAndGet());

        // Create the Tool
        ToolDTO toolDTO = toolMapper.toDto(tool);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(toolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();
        tool.setId(longCount.incrementAndGet());

        // Create the Tool
        ToolDTO toolDTO = toolMapper.toDto(tool);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(toolDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        int databaseSizeBeforeDelete = toolRepository.findAll().size();

        // Delete the tool
        restToolMockMvc
            .perform(delete(ENTITY_API_URL_ID, tool.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
