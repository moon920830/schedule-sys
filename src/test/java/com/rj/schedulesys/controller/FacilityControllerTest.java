package com.rj.schedulesys.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.rj.schedulesys.view.model.FacilityViewModel;
import com.rj.sys.config.TestConfiguration;
import com.rj.sys.util.TestUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestConfiguration.class)
public class FacilityControllerTest {

	
private @Autowired WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
	}
	
	@Test
	public void test_findAll() throws Exception{
		mockMvc.perform(get("/facilities"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(10)));
	}
	
	@Test
	public void test_findOne_WithNonExistingFacility() throws Exception{
		mockMvc.perform(get("/facilities/{id}", 0))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void test_findOne_WithExistingFacility() throws Exception{
		mockMvc.perform(get("/facilities/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("Sunnyside")))
			.andExpect(jsonPath("$.address", is("7080 Samuel Morse Dr")))
			.andExpect(jsonPath("$.phoneNumber", is("(908)-189-9371")))
			.andExpect(jsonPath("$.fax", is("(983)-983-8888")));
	}
	
	@Test
	public void test_create_WithExistingFacilityName() throws Exception{
		
		FacilityViewModel viewModel = TestUtil.aNewFacilityViewModel(
				null, "Sunnyside", "Somewhere", "147-986-8754", "210-874-9865"
				);
		
		mockMvc.perform(post("/facilities")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$", is("A facility with name 'Sunnyside' already exists")));
	}
	
	@Test
	public void test_create_WithExistingFacilityPhoneNumber() throws Exception{
		
		FacilityViewModel viewModel = TestUtil.aNewFacilityViewModel(
				null, "new-name ", "Somewhere", "(908)-189-9371", "(210)-874-9865"
				);
		
		mockMvc.perform(post("/facilities")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$", is("A facility with phone number '(908)-189-9371' already exists")));
	}
	
	@Test
	public void test_create_WithNonExistingFacilityNameAndPhoneNumber() throws Exception{
		
		FacilityViewModel viewModel = TestUtil.aNewFacilityViewModel(
				null, "new-name-1", "Somewhere", "(908)-002-9321", "(210)-874-9865"
				);
		
		mockMvc.perform(post("/facilities")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$", is("Facility successfully created")));
	}
	
	@Test
	public void test_update_WithNonExistingFacilityId() throws Exception{
		
		FacilityViewModel viewModel = TestUtil.aNewFacilityViewModel(
				0L, "new name", "Somewhere", "908-002-9371", "210-874-9865"
				);
		
		mockMvc.perform(put("/facilities/{id}", 0L)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$", is("No facility found with id : 0")));
	}
	
	@Test
	public void test_update_WithNonExistingFacilityNameAndPhoneNumber() throws Exception{
		
		FacilityViewModel viewModel = TestUtil.aNewFacilityViewModel(
				10L, "new name", "Somewhere", "(908)-002-9371", "(210)-874-9865"
				);
		
		mockMvc.perform(put("/facilities/{id}", 10L)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", is("Facility successfully updated")));
	}
	
	@Test
	public void test_delete_WithNonExistingFacilityId() throws Exception{
		mockMvc.perform(delete("/facilities/{id}", 0L)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$", is("No facility found with id : 0")));
	}
	
	@Test
	public void test_delete_WithFacilityThatHasSchedulesOrStaffMembers() throws Exception{
		mockMvc.perform(delete("/facilities/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$", is("Facility with id : 1 can not be deleted")));
	}
	
	@Test
	public void test_delete_WithFacilityThatHasNoSchedulesAndNoStaffMembers() throws Exception{
		mockMvc.perform(delete("/facilities/{id}", 10)
		.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", is("Facility successfully deleted")));
	}
	
	@Test
	public void test_findAllStaffMembers_WithNonExistingFacility() throws Exception{
		mockMvc.perform(get("/facilities/{id}/staff-members", 0)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$", is("No facility found with id : 0")));
	}
	
	@Test
	public void test_findAllStaffMembers_WithExistingFacility() throws Exception{
		mockMvc.perform(get("/facilities/{id}/staff-members", 4)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(2)));
	}
	
}