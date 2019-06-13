import io.restassured.RestAssured;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;

public class TestsAPI {

    private static final String MD5_IMAGE_FILE = "5cca6069f68fbf739fce37e0963f21e7";

    private static final String DELAY_PATH = "/delay";
    private static final String IMAGE_PATH = "/image/png";

    private static final long DELTA = 2_000L;

    private static final long MAX = 10_000L;
    private static final String MAX_PATH = "/10";

    private static final long MIN = 0L;
    private static final String MIN_PATH = "0";

    private static final long NORMAL = 5_000L;
    private static final String NORMAL_PATH = "/5";

    private static final String OVER_MAX_PATH = "/1000000000000000000000000000";
    private static final String NEGATIVE = "/-1";

    @BeforeClass
    public static void beforeClass() {
        RestAssured.baseURI = "https://httpbin.org";
    }

    @Test
    public void testDelayOverMAX() {
        get(DELAY_PATH + MAX_PATH).then().time(lessThan(MAX + DELTA));
    }

    @Test
    public void testDelayNormal() {
        get(DELAY_PATH + NORMAL_PATH).then().time(lessThan(NORMAL + DELTA));
    }

    @Test
    public void testDelayZero() {
        get(DELAY_PATH + MIN_PATH).then().time(lessThan(MIN + DELTA));
    }

    @Test
    public void testDelayOverMax() {
        get(DELAY_PATH + OVER_MAX_PATH).then().time(lessThan(MAX + DELTA));
    }

    @Test
    public void testDelayNegative() {
        get(DELAY_PATH + NEGATIVE).then().time(lessThan(MIN + DELTA));
    }

    @Test
    public void testImage(){
        assertEquals(MD5_IMAGE_FILE, DigestUtils.md5Hex(get(IMAGE_PATH).asByteArray()));
    }

}
