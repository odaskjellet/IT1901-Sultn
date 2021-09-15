import jdk.jfr.Timestamp;

public class CookbookTest {

    private Cookbook cookbook;
    private Map<Integer, Recipe> testMap = new HashMap<>();

    //Setup før testingen - trenger recipe class til dette :)
    @BeforeEach
    public void setUp() {

        testMap.put(1, carbonara);
        testMap.put(2, pizza);
        testMap.put(3, recipe);

        cookbook = new Cookbook(testMap);
    }

    @Test
    public void testMakeNewRecipe();

    @Test
    public void testDeleteRecipe() {
    };

    @Test
    public void testAddRecipe();

    @Test
    public void testEditRecipe();
}
