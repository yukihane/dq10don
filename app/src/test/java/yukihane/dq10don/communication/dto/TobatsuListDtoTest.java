package yukihane.dq10don.communication.dto;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by yuki on 15/07/08.
 */
public class TobatsuListDtoTest {

    private static File file;
    private static File notPreparedFile;
    private ObjectMapper mapper;

    @BeforeClass
    public static void beforeClass() throws Exception {
        file = new File(TobatsuListDtoTest.class.getClassLoader().getResource("dummy_response/tobatsulist.json").toURI());
        notPreparedFile = new File(TobatsuListDtoTest.class.getClassLoader().getResource("dummy_response/tobatsulist_not_prepared.json").toURI());
    }

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testCreate() throws IOException {
        TobatsuListDto res = mapper.readValue(file, TobatsuListDto.class);
        assertNotNull(res);
    }
}