package edu.escuelaing.arep;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
public class GithubRepositoryCreatorTest extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GithubRepositoryCreatorTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GithubRepositoryCreatorTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

}