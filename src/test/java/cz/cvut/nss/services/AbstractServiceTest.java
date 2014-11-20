package cz.cvut.nss.services;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jakubchalupa on 19.11.14.
 *
 * Abstract supper class for services tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public abstract class AbstractServiceTest {

}

/* Pretty usage :)

    @RunWith(SpringJUnit4ClassRunner.class)
    @WebAppConfiguration
    @ContextConfiguration("/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
        public class AppTests {
            private MockMvc mockMvc;

        @SuppressWarnings("SpringJavaAutowiringInspection")
        @Autowired
        protected WebApplicationContext wac;

        @Before
        public void setup() {
            this.mockMvc = webAppContextSetup(this.wac).build();
        }

            @Test
            public void simple() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("hello"));
            }
        }

*/
