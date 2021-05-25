package trainingxyz;
import modals.Product;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    @Test
    public void getCategories(){
        String endPoint = "http://localhost:8888/api_testing/category/read.php";
        var response = given().when().get(endPoint).then();
        response.log().body();
    }
    @Test
    public void getProductAndCheckBodyResponseInline(){
        String endPoint = "http://localhost:8888/api_testing/product/read_one.php";
        given().
                queryParam("id", 3).
                when().
                get(endPoint).
                then().
                assertThat().statusCode(200).
                body("id", equalTo("3")).
                body("name", equalTo("Grunge Skater Jeans")).
                body("description", equalTo("Our boy-cut jeans are for men and women who appreciate that skate park fashions aren’t just for skaters. Made from the softest and most flexible organic cotton denim.")).
                body("price", equalTo("68.00")).
                body("category_id", equalTo("3")).
                body("category_name", equalTo("Active Wear - Unisex"));
    }
    @Test
    public void getAllProductsAndCheckIsNotNull() {
        String endPoint = "http://localhost:8888/api_testing/product/read.php";
        given().
                when().
                get(endPoint).
                then().
                log().body().
                assertThat().
                statusCode(200).
                body("records.size()",greaterThan(0)).
                body("records.id", everyItem(notNullValue())).
                body("records.name", everyItem(notNullValue())).
                body("records.description", everyItem(notNullValue())).
                body("records.price", everyItem(notNullValue())).
                body("records.category_id", everyItem(notNullValue())).
                body("records.category_name", everyItem(notNullValue())).
                body("records.id[0]", equalTo("18"));
    }
    @Test
    public void getAllProductsAnd() {
        String endPoint = "http://localhost:8888/api_testing/product/read.php";
        given().
                when().
                get(endPoint).
                then().
                log().headers().
                assertThat().
                statusCode(200).
                header("Content-Type", equalTo("application/json; charset=UTF-8")).
                body("records.size()",greaterThan(0)).
                body("records.id", everyItem(notNullValue())).
                body("records.name", everyItem(notNullValue())).
                body("records.description", everyItem(notNullValue())).
                body("records.price", everyItem(notNullValue())).
                body("records.category_id", everyItem(notNullValue())).
                body("records.category_name", everyItem(notNullValue())).
                body("records.id[0]", equalTo("18"));

    }
    @Test
    public void createProduct(){
        String endPoint = "http://localhost:8888/api_testing/product/create.php";
        String body = """
                {
                "name":"Water Bottle",
                "description":"Blue water bottle",
                "price":12,
                "category_id:3
                }
                """;
        var response =
                given().
                body(body).
                when().
                post(endPoint).then();
        response.log().body();
    }
    @Test
    public void updateProduct(){
        String endPoint = "http://localhost:8888/api_testing/product/read_one.php";
        String body = """
                {
                "id":19,
                "name":"Water Bottle",
                "description":"Blue Water bottle."
                "price":15,
                "category_id:3
                }
                """;
        var response = given().body(body).when().put(endPoint).then();
        response.log().body();
    }
    @Test
    public void deleteProduct(){
        String endPoint = "http://localhost:8888/api_testing/product/read_one.php";
        String body = """
                {
                "id":19
                }
                """;
        var response = given().body(body).when().delete(endPoint).then();
        response.log().body();

    }
    @Test
    public void createSerializedProduct(){
        String endPoint = "http://localhost:8888/api_testing/product/read_one.php";
        Product product = new Product(
                "Watter bottle",
                "Blue water bottle. holds 64 ",
                12,
                3
        );
        var response = given().body(product).when().post(endPoint).then();
        response.log().body();
    }
    @Test
    public void getDeserializedProduct(){
        String endPoint = "http://localhost:8888/api_testing/product/read_one.php";
        // (String)"{"id":"3","name":"Grunge Skater Jeans","description":"Our boy-cut jeans are for men and women who appreciate that skate park fashions aren\u2019t just for skaters. Made from the softest and most flexible organic cotton denim.","price":"68.00","category_id":"3","category_name":"Active Wear - Unisex"}"; line: 1, column: 279] (through reference chain: modals.Product["category_name"])

        Product expectedProduct = new Product(
                3,
                "Grunge Skater Jeans",
                "Our boy-cut jeans are for men and women who appreciate that skate park fashions aren\u2019t just for skaters. Made from the softest and most flexible organic cotton denim.",
                68.00,
                3,
                "Active Wear - Unisex"
);

        Product actualProduct =
                given().
                        queryParam("id", "3").
                        when().
                        get(endPoint).
                        as(Product.class);
    assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
    }

    @Test
    public void getMultivitaminsAndAssertThat(){
        String endPoint = "http://localhost:8888/api_testing/product/read_one.php";

        given().
            queryParam("id", 18).
        when().
            get(endPoint).
        then().
                assertThat().statusCode(200).
                header("Content-Type", equalTo("application/json")).
                body("id", equalTo("18")).
                body("price", equalTo("10.00"));
    }







    @Test
    public void createSweatband(){
        String endPoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product(
                "Enes Berke",
                "Uzun lorem ipsum açıklması",
                12,
                5
        );
        String body = """
                {
                    "name":"Water Bottle",
                    "description":"Blue water bottle",
                    "price":12,
                    "category_id:3
                }
                """;
        var response = given().body(body).when().post(endPoint).then();
        response.log().body();

    }
    @Test
    public void updateSweatband(){
        String endPoint = "http://localhost:8888/api_testing/product/update.php";
        Product product = new Product(
          "Berke Enes",
          "İkinci uzun açıklama",
          99,
          2
        );
        var response = given().body(product).when().put(endPoint).then();
        response.log().body();
    }
    @Test
    public void getSweatband(){
        String endPoint = "http://localhost:8888/api_testing/product/read_one.php";

        var response =
                given().
                        queryParam("id",2).
                when().
                        get(endPoint).
                        then();

        response.log().body();
    }
    @Test
    public void deleteSweatband(){
        String endPoint = "http://localhost:8888/api_testing/product/delete.php";
        String body = """
                {
                "id":2
                }
                """;
        var response = given().body(body).when().delete(endPoint).then();
        response.log().body();
    }




}

