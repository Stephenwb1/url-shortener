package com.stephenwb.url_shortener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerApplicationTests {

	@Autowired
	public MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void postRequestReturnsValidResponses() throws Exception {
		mockMvc.perform(post("/api/urls")
				.contentType(MediaType.TEXT_PLAIN)
				.content("https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void postRequestReturnsInvalidResponses() throws Exception {
		mockMvc.perform(post("/api/urls")
				.contentType(MediaType.TEXT_PLAIN)
				.content("https://www.youtu4w9WgXcQ"))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	void getRequestReturnsValidResponses() throws Exception {
		String shortCode = mockMvc.perform(post("/api/urls")
				.contentType(MediaType.TEXT_PLAIN)
				.content("https://www.youtu4w9WgXcQ")).andReturn().getResponse().getContentAsString();
		mockMvc.perform(get("/r/" + shortCode))
				.andExpect(status().isFound()).andDo(print());
	}

	@Test
	void getRequestReturnsInvalidResponses() throws Exception {
		String shortCode = mockMvc.perform(post("/api/urls")
				.contentType(MediaType.TEXT_PLAIN)
				.content("https://www.youtu4w9WgXcQ")).andReturn().getResponse().getContentAsString();
		mockMvc.perform(get("/r/" + shortCode + "a"))
				.andExpect(status().isNotFound()).andDo(print());
	}
}
