package com.vadymlotar.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class MainControllerTest {
	@Autowired
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = webAppContextSetup(ctx).build();
	}

	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(view().name("/index"));
	}

	@Configuration
	public static class TestConfiguration {

		@Bean
		public MainController homeController() {
			return new MainController();
		}

	}
}
