package yukihane.dq10don.communication.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

/**
 * Created by yuki on 15/07/08.
 */
public class CharaSelectDtoTest {

    private static File successFile;
    private static File failFile;
    private ObjectMapper mapper;

    @BeforeClass
    public static void beforeClass() throws URISyntaxException {
        successFile = new File(CharaSelectDtoTest.class.getClassLoader()
                .getResource("dummy_response/characterselect_success.json").toURI());
        failFile = new File(CharaSelectDtoTest.class.getClassLoader()
                .getResource("dummy_response/characterselect_fail.json").toURI());
    }

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testCreateSuccess() throws IOException {
        CharaSelectDto res = mapper.readValue(successFile, CharaSelectDto.class);
        assertEquals(0, res.getResultCode().intValue());
        assertEquals("hogeHoge", res.getEncWebPcNo());
    }

    @Test
    public void testCreateFail() throws IOException {
        CharaSelectDto res = mapper.readValue(failFile, CharaSelectDto.class);
        assertEquals(100, res.getResultCode().intValue());
        assertNull(res.getEncWebPcNo());
    }
}