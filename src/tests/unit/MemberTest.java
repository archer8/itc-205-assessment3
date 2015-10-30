package tests.unit;

import library.entities.Member;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chris on 10/10/15.
 */
public class MemberTest {


    //check later for @expected

    @Test
    public void ConstructorThrowsIfIDIsNotGreaterThanZero() throws Exception {

        try {
            Member member = new Member("something", "something", "something", "something", -22);
        } catch (Exception e) {
            return;
        }
        fail();
    }


}