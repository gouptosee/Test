package test.test;

/**
 * Created by admin on 2017/5/19.
 */

public class ClassTest {

    private String name;
    private String age;

    public ClassTest() {
    }

    public ClassTest(String name, String age) {
        this.name = name;
        this.age = age;
    }


    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    private void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
