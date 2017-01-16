package resources;

public class ResourceServer implements ResourceServerMBean {

    private TestResource testResource;

    public int getAge() {
        return testResource == null ? 0 : testResource.getAge();
    }

    public String getName() {
        return testResource == null ? "" : testResource.getName();
    }

    public void setTestResource(TestResource testResource) {
        this.testResource = testResource;
    }
}
