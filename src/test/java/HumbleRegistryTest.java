import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HumbleRegistryTest {

    private HumbleRegistry humbleRegistry;

    @Before
    public void before(){
        this.humbleRegistry = new HumbleRegistry(new File(HumbleRegistryTest.class.getResource("example").getFile()));
    }

    @Test
    public void shouldBeEmptyStringIfNoFolder(){
        this.humbleRegistry = new HumbleRegistry();
        assertThat(this.humbleRegistry.toJson(), is(emptyString()));
    }

    @Test
    public void shouldNotBeEmptyStringIfFolder(){
        assertThat(this.humbleRegistry.toJson(), is(not(emptyString())));
    }

    @Test
    public void shouldContainSittpuffStrings(){
        assertThat(this.humbleRegistry.toJson(), containsString("modern sittpuff"));
        assertThat(this.humbleRegistry.toJson(), containsString("antik sittpuff"));
        assertThat(this.humbleRegistry.toJson(), containsString("["));
        assertThat(this.humbleRegistry.toJson(), containsString("]"));
    }

    @Test
    public void shouldContainImageFileNames(){
        assertThat(this.humbleRegistry.toJson(), containsString("picture.jpg"));
        assertThat(this.humbleRegistry.toJson(), containsString("picture2.jpg"));
    }
}
